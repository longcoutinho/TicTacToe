package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.nio.charset.CoderResult;


public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Background background;
    private Menu menu_bar;
    private Player player1;
    private Player player2;
    private boolean dragged = false;
    private boolean clicked_in_background = false;
    private boolean game_Over = false;
    private boolean game_Pause = false;
    private float screen_width;
    private float screen_height;
    private Cell player1_icon;
    private Cell player2_icon;
    public GameSurface(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        // Set callback.
        this.getHolder().addCallback(this);
    }

    public void reset() {
        Cell.listofOCells.clear();
        Cell.listofXCells.clear();
        game_Over = false;
        player1.status = "";
        player2.status = "";
        player1.setTurn(true);
        player2.setTurn(false);
        for(int i = 0; i < Cell.numberOfRows; i++)
            for(int j = 0; j < Cell.numberOfCols; j++) {
                Cell.valueOfCell[i][j] = 0;
                Cell.indexOfCell[i][j] = 0;
            }
    }

    public void update()  {
        for (int j = 0; j < Cell.listofXCells.size(); j++) {
            Cell.listofXCells.get(j).update(background.getX() - Cell.listofXCells.get(j).getStart_game_dragX() + Cell.listofXCells.get(j).getStartX(), background.getY() - Cell.listofXCells.get(j).start_game_dragY + Cell.listofXCells.get(j).getStartY());
        }
        for (int j = 0; j < Cell.listofOCells.size(); j++) {
            Cell.listofOCells.get(j).update(background.getX() - Cell.listofOCells.get(j).getStart_game_dragX() + Cell.listofOCells.get(j).getStartX(), background.getY() - Cell.listofOCells.get(j).start_game_dragY + Cell.listofOCells.get(j).getStartY());
        }

        if (player1.win()) {
            game_Over = true;
            player1.status = " WIN";
            player2.status = "LOSE";
            Bitmap x_cell_win_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.x_icon_win);
            for(int i = 0; i < Cell.listofXCells.size(); i++) {
                if (Cell.listofXCells.get(i).won) Cell.listofXCells.get(i).setImage(x_cell_win_bitmap);
            }
        }

        if (player2.win()) {
            game_Over = true;
            player1.status = "LOSE";
            player2.status = " WIN";
            Bitmap o_cell_win_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.o_icon_win);
            for(int i = 0; i < Cell.listofOCells.size(); i++) {
                if (Cell.listofOCells.get(i).won) Cell.listofOCells.get(i).setImage(o_cell_win_bitmap);
            }
        }

        if (player1.isBot && player1.getTurn() && !game_Over) {
            Coordinates botchoice = new Coordinates(15, 15);
            if (Cell.listofOCells.size() > 0) {
                botchoice = player1.BotFill();
            }
            Bitmap xcell_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.x_icon);
            Cell newcell = new Cell(this, xcell_bitmap, botchoice.col * Cell.width + background.getX(), botchoice.row * Cell.height + background.getY());
            newcell.setStartX(newcell.getX());
            newcell.setStartY(newcell.getY());
            newcell.setStart_game_dragX(background.getX());
            newcell.setStart_game_dragY(background.getY());
            newcell.setRow(botchoice.row);
            newcell.setCol(botchoice.col);
            Cell.listofXCells.add(newcell);
            Cell.valueOfCell[newcell.getRow()][newcell.getCol()] = 1;
            Cell.indexOfCell[newcell.getRow()][newcell.getCol()] = Cell.listofXCells.size() - 1;
            swap_turn();
        }

        if (player2.isBot && player2.getTurn() && !game_Over) {
            Coordinates botchoice = player2.BotFill();
            Bitmap ocell_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.o_icon);
            Cell newcell = new Cell(this, ocell_bitmap, botchoice.col * Cell.width + background.getX(), botchoice.row * Cell.height + background.getY());
            newcell.setStartX(newcell.getX());
            newcell.setStartY(newcell.getY());
            newcell.setStart_game_dragX(background.getX());
            newcell.setStart_game_dragY(background.getY());
            newcell.setRow(botchoice.row);
            newcell.setCol(botchoice.col);
            Cell.listofOCells.add(newcell);
            Cell.valueOfCell[newcell.getRow()][newcell.getCol()] = 2;
            Cell.indexOfCell[newcell.getRow()][newcell.getCol()] = Cell.listofXCells.size() - 1;
            swap_turn();
        }
    }

    void create_objects() {
        /* Create players */
        player1 = new Player(true, 1);
        player2 = new Player(false, 2);

        // Load info from pre-screen
        int id_player1_image = 0, id_player2_image = 0;
        if (Home.menu_type == 1) {
            id_player1_image = PlayWithBot.player1_avatar_id;
            id_player2_image = PlayWithBot.player2_avatar_id;
            player1.name = PlayWithBot.player1_name;
            player2.name = PlayWithBot.player2_name;
            player1.isBot = PlayWithBot.player1isBot;
            player2.isBot = PlayWithBot.player2isBot;
        }
        else if (Home.menu_type == 2) {
            id_player1_image = TwoPlayersOffline.player1_avatar_id;
            id_player2_image = TwoPlayersOffline.player2_avatar_id;
            player1.name = TwoPlayersOffline.player1_name;
            player2.name = TwoPlayersOffline.player2_name;
        }

        Bitmap xcell_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.x_icon);
        Cell testcell = new Cell(this, xcell_bitmap, 0, 0);
        // Create menu bar
        Bitmap menubar_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.menu_bar);
        menu_bar = new Menu(this, menubar_bitmap, 0, 0);

        // Create players's info
        // Create avatar of players
        Bitmap player1_bitmap = BitmapFactory.decodeResource(this.getResources(), id_player1_image);
        Avatar player1_avatar = new Avatar(this, player1_bitmap, 0, Cell.height);
        player1.avatar = player1_avatar;
        Bitmap player2_bitmap = BitmapFactory.decodeResource(this.getResources(), id_player2_image);
        Avatar player2_avatar = new Avatar(this, player2_bitmap, screen_width - player2_bitmap.getWidth(), Cell.height);
        player2.avatar = player2_avatar;
        // Create icon of players
        player1_icon = new Cell(this, xcell_bitmap, player1_avatar.getX() + Avatar.width, Cell.height * 3);
        Bitmap ocell_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.o_icon);
        player2_icon = new Cell(this, ocell_bitmap, player2_avatar.getX() - xcell_bitmap.getWidth(), Cell.height * 3);

        /* Create background table of cells */
        Bitmap background_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.background);
        background = new Background(this, background_bitmap, 0, Menu.height);
        background.setX(-Cell.width * 10);
        background.setY(-Cell.height * 10 + Menu.height);
        reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);

        // Draw background of cells
        background.draw(canvas);

        // Draw X cells
        for(int i = 0; i < Cell.listofXCells.size(); i++)
            Cell.listofXCells.get(i).draw(canvas);

        // Draw O cells
        for(int i = 0; i < Cell.listofOCells.size(); i++)
            Cell.listofOCells.get(i).draw(canvas);

        // Draw menu bar
        menu_bar.draw(canvas);

        // draw avatar of players
        player1.avatar.draw(canvas);
        player2.avatar.draw(canvas);

        // draw icon of players
        player1_icon.draw(canvas);
        player2_icon.draw(canvas);

        // draw name of players
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));
        textPaint.setTextAlign(Paint.Align.LEFT);
        Paint.FontMetrics metric = textPaint.getFontMetrics();
        int textHeight = (int) Math.ceil(metric.descent - metric.ascent);
        int y = (int)(textHeight - metric.descent);
        canvas.drawText(player1.name, 0 + 50, y + 20, textPaint);
        canvas.drawText(player2.name, screen_width - Avatar.width + 50, y + 20, textPaint);

        // draw status of players
        Paint textPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint2.setColor(Color.RED);
        textPaint2.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 40, getResources().getDisplayMetrics()));
        textPaint2.setTextAlign(Paint.Align.LEFT);
        Paint.FontMetrics metric2 = textPaint2.getFontMetrics();
        int textHeight2 = (int) Math.ceil(metric2.descent - metric2.ascent);
        int y2 = (int)(textHeight2 - metric2.descent);
        canvas.drawText(player1.status, 0 + 20, y2 + Cell.height * 2, textPaint2);
        canvas.drawText(player2.status, screen_width - Avatar.width + 30, y2 + Cell.height * 2, textPaint2);
    }

    public void swap_turn() {
        if (player1.getTurn()) {
            player1.setTurn(false);
            player2.setTurn(true);
        }
        else {
            player1.setTurn(true);
            player2.setTurn(false);
        }
    }

    // Implements method of SurfaceHolder.Callback
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // get size of screen
        GameSurface gameSurface_this = this;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameSurface_this.screen_width = displayMetrics.widthPixels;
        gameSurface_this.screen_height = displayMetrics.heightPixels;
        create_objects();

        // set event on screen
        this.setOnTouchListener((v, event) -> {
            //Log.i("long", Menu.height + "");
            /* Doing something when click down the mouse */
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dragged = false; // reset
                if (event.getY() < Menu.height) clicked_in_background = false;
                else clicked_in_background = true;
            }

            /* Doing something when drag the mouse */
            else if (event.getAction() == MotionEvent.ACTION_MOVE && clicked_in_background && !game_Pause) {
                dragged = true;
                int historySize = event.getHistorySize();
                for  ( int  i =  1 ; i < historySize; i ++) {
                    float dragX = (event.getHistoricalX (i) - event.getHistoricalX(i - 1));
                    float dragY = (event.getHistoricalY (i) - event.getHistoricalY(i - 1));
                    //Log.i("long", dragX + " " + dragY);

                    // check the limit of drag on table of cells

                    // limited dragable left and top side table of cells
                    if ((background.getX() + dragX) > 0) dragX = - background.getX();  //left
                    if ((background.getY() + dragY) > Menu.height) dragY = Menu.height - background.getY(); //top
                    if ((background.getX() + dragX) < (gameSurface_this.screen_width - Background.width)) dragX = gameSurface_this.screen_width - Background.width - background.getX(); //right
                    if ((background.getY() + dragY) < (gameSurface_this.screen_height - Background.height + Menu.height)) dragY = gameSurface_this.screen_height - Background.height + Menu.height - background.getY();  // bottom

                    //Log.i("long", gameSurface_this.screen_width + " " + gameSurface_this.screen_height);
                    //Log.i("long", background.getY() + " " + Menu.height);
                    background.moveTo(background.getX() + dragX, background.getY() + dragY);
                }
            }
            else if (event.getAction() == MotionEvent.ACTION_UP && !game_Pause) {
                if (!dragged) {
                    if (clicked_in_background && !game_Over) {
                        //Log.i("long", game_dragX + " " + game_dragY);
                        int currentrow = (int) (event.getY() - background.getY()) / (int) Cell.height;
                        int currentcol = (int) (event.getX() - background.getX()) / (int) Cell.width;
                        if (Cell.valueOfCell[currentrow][currentcol] == 0) {
                            Bitmap xcell_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.x_icon);
                            Cell newcell = new Cell(gameSurface_this, xcell_bitmap, currentcol * Cell.width + background.getX(), currentrow * Cell.height + background.getY());
                            newcell.setStartX(newcell.getX());
                            newcell.setStartY(newcell.getY());
                            newcell.setStart_game_dragX(background.getX());
                            newcell.setStart_game_dragY(background.getY());
                            newcell.setRow(currentrow);
                            newcell.setCol(currentcol);
                            if (player1.getTurn() && !player1.isBot) {
                                Cell.listofXCells.add(newcell);
                                Cell.valueOfCell[newcell.getRow()][newcell.getCol()] = 1;
                                Cell.indexOfCell[newcell.getRow()][newcell.getCol()] = Cell.listofXCells.size() - 1;
                                swap_turn();

                            } else if (player2.getTurn() && !player2.isBot) {
                                Bitmap ocell_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.o_icon);
                                newcell.setImage(ocell_bitmap);
                                Cell.listofOCells.add(newcell);
                                Cell.valueOfCell[newcell.getRow()][newcell.getCol()] = 2;
                                Cell.indexOfCell[newcell.getRow()][newcell.getCol()] = Cell.listofOCells.size() - 1;
                                swap_turn();
                            }

                        }
                    }
                    else if (!clicked_in_background){
                        reset();
                    }
                }
            }
            return true;
        });
        this.setFocusable(true);
        //start game
        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
        //MediaPlayer music = MediaPlayer.create(getContext(), R.raw.thunglung);
        //music.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry = false;
        }
    }

}
