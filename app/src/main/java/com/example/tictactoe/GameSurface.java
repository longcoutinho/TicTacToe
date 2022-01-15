package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.CoderResult;
import java.util.ArrayList;


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
    private DatabaseReference mDatabase;
    private int yourID = -1;
    private int turn_online = 0;
    private int updatename = 0;
    private String pausetext = "";
    private int countwon = 0;
    public ArrayList<Account> listofAccounts;
    MenuIcon home_icon, reset_icon, pause_icon;
    public GameSurface(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        // Set callback.
        this.getHolder().addCallback(this);
    }

    public void reset() {
        countwon = 0;
        Cell.listofOCells.clear();
        Cell.listofXCells.clear();
        game_Over = false;
        player1.setTurn(true);
        player2.setTurn(false);
        for (int i = 0; i < Cell.numberOfRows; i++) {
            for (int j = 0; j < Cell.numberOfCols; j++) {
                Cell.valueOfCell[i][j] = 0;
                Cell.indexOfCell[i][j] = 0;
            }
        }
        if (Home.menu_type == 3) {
                if (game_Over) mDatabase.child(InfoPage.roomID).child("player_count").setValue(1);
                mDatabase.child(InfoPage.roomID).child("turn").setValue(1);
                //listOcellonline.clear();
                //listXcellonline.clear();
                mDatabase.child(InfoPage.roomID).child("data_X").setValue(null);
                mDatabase.child(InfoPage.roomID).child("data_O").setValue(null);
                mDatabase.child(InfoPage.roomID).child("gameover").setValue(false);
        }
    }



    public void update()  {
        if (game_Pause == true) pausetext = "PAUSED";
        else pausetext = "";
        if (updatename == 1) {
            Query newq6 = mDatabase.child(InfoPage.roomID).child("player2_name");
            newq6.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //GenericTypeIndicator<ArrayList<Cell>> t = new GenericTypeIndicator<ArrayList<Cell>>() {};
                    //Toast.makeText(getContext(),yourStringArray.get(0).getName(),Toast.LENGTH_LONG).show();
                    try {
                        String name = snapshot.getValue(String.class);
                        player2.name = name;
                    } catch (NullPointerException e) {

                    }
                    //tv.setText(abc);
                    //Log.v("long", "take data");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        //Log.v("long", player2.name);
        if (((turn_online == 1 && Home.menu_type == 3) || (player1.getTurn() && Home.menu_type != 3)) && !game_Over) {
            player1.status = "TURN";
            player2.status = "";
        }
        else if (((turn_online == 2 && Home.menu_type == 3) || (player2.getTurn() && Home.menu_type != 3)) && !game_Over) {
            player2.status = "TURN";
            player1.status = "";
        }
        Log.v("long", yourID + "");
        for (int j = 0; j < Cell.listofXCells.size(); j++) {
            Cell.listofXCells.get(j).update(background.getX() - Cell.listofXCells.get(j).getStart_game_dragX() + Cell.listofXCells.get(j).getStartX(), background.getY() - Cell.listofXCells.get(j).start_game_dragY + Cell.listofXCells.get(j).getStartY());
        }
        for (int j = 0; j < Cell.listofOCells.size(); j++) {
            Cell.listofOCells.get(j).update(background.getX() - Cell.listofOCells.get(j).getStart_game_dragX() + Cell.listofOCells.get(j).getStartX(), background.getY() - Cell.listofOCells.get(j).start_game_dragY + Cell.listofOCells.get(j).getStartY());
        }

        if (game_Over == false) {
            if ((turn_online == 1 && Home.menu_type == 3)||(player1.getTurn() && Home.menu_type != 3)) {
                Bitmap normalcell = BitmapFactory.decodeResource(this.getResources(), R.drawable.x_icon);
                Bitmap lastcell = BitmapFactory.decodeResource(this.getResources(), R.drawable.o_icon_last);
                for(int i = 0; i < Cell.listofXCells.size(); i++) {
                    Cell.listofXCells.get(i).setImage(normalcell);
                }
                if (Cell.listofOCells.size() > 0) Cell.listofOCells.get(Cell.listofOCells.size() - 1).setImage(lastcell);
            }
            else if ((turn_online == 2 && Home.menu_type == 3)||(player2.getTurn() && Home.menu_type != 3)) {
                Bitmap normalcell = BitmapFactory.decodeResource(this.getResources(), R.drawable.o_icon);
                Bitmap lastcell = BitmapFactory.decodeResource(this.getResources(), R.drawable.x_icon_last);
                for(int i = 0; i < Cell.listofOCells.size(); i++) {
                    Cell.listofOCells.get(i).setImage(normalcell);
                }
                if (Cell.listofXCells.size() > 0) Cell.listofXCells.get(Cell.listofXCells.size() - 1).setImage(lastcell);
            }
        }

        if (!player1.win() && !player2.win() && Home.menu_type == 3) {
            mDatabase.child(InfoPage.roomID).child("gameover").setValue(false);
        }

        if (player1.win()) {
            //Log.v("long", "kkk");
            game_Over = true;
            if (Home.menu_type == 3) mDatabase.child(InfoPage.roomID).child("gameover").setValue(true);
            player1.status = " WIN";
            player2.status = "LOSE";
            //Log.v("long", "alo");
            Bitmap x_cell_win_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.x_icon_win);
            for(int i = 0; i < Cell.listofXCells.size(); i++) {
                if (Cell.listofXCells.get(i).won) Cell.listofXCells.get(i).setImage(x_cell_win_bitmap);
            }
            if (yourID == 1) {
                for(int i = 0; i < listofAccounts.size(); i++)
                    if (listofAccounts.get(i).getUsername().equals(LoginPage.playerusername) && countwon == 0) {
                        countwon = 1;
                        listofAccounts.get(i).incMatch_win();
                        mDatabase.child("listaccount").setValue(listofAccounts);
                        break;
                    }
            }

        }

        if (player2.win()) {
            game_Over = true;
            if (Home.menu_type == 3) mDatabase.child(InfoPage.roomID).child("gameover").setValue(true);
            player1.status = "LOSE";
            player2.status = " WIN";
            Bitmap o_cell_win_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.o_icon_win);
            for(int i = 0; i < Cell.listofOCells.size(); i++) {
                if (Cell.listofOCells.get(i).won) Cell.listofOCells.get(i).setImage(o_cell_win_bitmap);
            }

            if (yourID == 2) {
                for(int i = 0; i < listofAccounts.size(); i++)
                    if (listofAccounts.get(i).getUsername().equals(LoginPage.playerusername) && countwon == 0) {
                        countwon = 1;
                        listofAccounts.get(i).incMatch_win();
                        mDatabase.child("listaccount").setValue(listofAccounts);
                        break;
                    }
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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Bitmap xcell_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.x_icon);
        Bitmap ocell_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.o_icon);



        //mDatabase.child("player_count").setValue(1);
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
            player1.isBot = false;
            player2.isBot = false;
        }

        else if (Home.menu_type == 3) {
            id_player1_image = R.drawable.avatar_default;
            id_player2_image = R.drawable.avatar_default;
            player1.name = "Waiting...";
            player2.name = "Waiting...";
            player1.isBot = false;
            player2.isBot = false;
        }

        //cell_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.x_icon);
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
        //Bitmap ocell_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.o_icon);
        player2_icon = new Cell(this, ocell_bitmap, player2_avatar.getX() - xcell_bitmap.getWidth(), Cell.height * 3);

        Bitmap home_icon_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.home_icon);
        home_icon = new MenuIcon(this, home_icon_bitmap, player1_avatar.getX() + Avatar.width, Cell.height + 30);
        Bitmap reset_icon_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.reset_icon);
        reset_icon = new MenuIcon(this, reset_icon_bitmap, player2_avatar.getX() - xcell_bitmap.getWidth(), Cell.height + 30);
        Bitmap pause_icon_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.pause_icon);
        pause_icon = new MenuIcon(this, pause_icon_bitmap, home_icon.getX() + (reset_icon.getX() - home_icon.getX()) / 2, Cell.height + 30);


        /* Create background table of cells */
        Bitmap background_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.background);
        background = new Background(this, background_bitmap, 0, Menu.height);
        background.setX(-Cell.width * 10);
        background.setY(-Cell.height * 10 + Menu.height);
        //reset();
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
        //Log.v("long", "come");
        // Draw X cells
        for (int i = 0; i < Cell.listofXCells.size(); i++) {
            Cell.listofXCells.get(i).draw(canvas);
                //Log.v("long", Cell.listofXCells.get(i).getRow() + " ");
        }
        //Log.v("long", "drawed");
        //Log.v("long", "done");
        // Draw O cells
        for(int i = 0; i < Cell.listofOCells.size(); i++)
            Cell.listofOCells.get(i).draw(canvas);
        //Log.v("long", "done1");
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
        //Log.v("long", "Drawing!!!");

        Paint textPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint3.setColor(Color.BLACK);
        textPaint3.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 30, getResources().getDisplayMetrics()));
        textPaint3.setTextAlign(Paint.Align.LEFT);
        Paint.FontMetrics metric3 = textPaint3.getFontMetrics();
        int textHeight3 = (int) Math.ceil(metric3.descent - metric3.ascent);
        int y3 = (int)(textHeight3 - metric3.descent);
        if (Home.menu_type == 3) canvas.drawText("ID:" + InfoPage.roomID, 0 + Avatar.width, y3 + 20, textPaint3);

        Paint textPaint4 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint4.setColor(Color.RED);
        textPaint4.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 100, getResources().getDisplayMetrics()));
        textPaint4.setTextAlign(Paint.Align.LEFT);
        Paint.FontMetrics metric4 = textPaint4.getFontMetrics();
        int textHeight4 = (int) Math.ceil(metric4.descent - metric4.ascent);
        int y4 = (int)(textHeight4 - metric4.descent);
        canvas.drawText(pausetext, 20 , y4 + Menu.height + 200, textPaint4);

        home_icon.draw(canvas);
        reset_icon.draw(canvas);
        pause_icon.draw(canvas);

    }

    public void backtohome() {
        reset();
        if (Home.menu_type == 3) {
            Intent newintent = new Intent(getContext(), InfoPage.class);
            getContext().startActivity(newintent);
            if (yourID == 1) mDatabase.child(InfoPage.roomID).child("player1_name").setValue("Waiting...");
            else mDatabase.child(InfoPage.roomID).child("player2_name").setValue("Waiting...");
        }
        else {
            Intent newintent = new Intent(getContext(), Home.class);
            getContext().startActivity(newintent);
        }
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
        if (Home.menu_type == 3) {
            Bitmap xcell_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.x_icon);
            Bitmap ocell_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.o_icon);
            Query newq = mDatabase.child(InfoPage.roomID).child("data_X");
            newq.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<ArrayList<Cell>> t = new GenericTypeIndicator<ArrayList<Cell>>() {
                    };
                    ArrayList<Cell> yourArray = snapshot.getValue(t);
                    //Toast.makeText(getContext(),yourStringArray.get(0).getName(),Toast.LENGTH_LONG).show();
                    try {
                        Cell.listofXCells = new ArrayList<>();
                        for (int i = 0; i < Cell.numberOfRows; i++) {
                            for (int j = 0; j < Cell.numberOfCols; j++) {
                                Cell.valueOfCell[i][j] = 0;
                                Cell.indexOfCell[i][j] = 0;
                            }
                        }
                        for (int i = 0; i < yourArray.size(); i++) {
                            int currentrow = yourArray.get(i).getRow();
                            int currentcol = yourArray.get(i).getCol();
                            //Bitmap xcell_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.x_icon);
                            Cell newcell = new Cell(gameSurface_this, xcell_bitmap, currentcol * Cell.width + background.getX(), currentrow * Cell.height + background.getY());
                            newcell.setStartX(newcell.getX());
                            newcell.setStartY(newcell.getY());
                            newcell.setStart_game_dragX(background.getX());
                            newcell.setStart_game_dragY(background.getY());
                            newcell.setRow(currentrow);
                            newcell.setCol(currentcol);
                            Cell.listofXCells.add(newcell);
                            Cell.valueOfCell[Cell.listofXCells.get(i).getRow()][Cell.listofXCells.get(i).getCol()] = 1;
                            Cell.indexOfCell[Cell.listofXCells.get(i).getRow()][Cell.listofXCells.get(i).getCol()] = i;
                            //Log.v("long", game_Over + "");
                        }
                    } catch (NullPointerException e) {

                    }
                    //tv.setText(abc);
                    //Log.v("long", "take data");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Query newq2 = mDatabase.child(InfoPage.roomID).child("data_O");
            newq2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<ArrayList<Cell>> t = new GenericTypeIndicator<ArrayList<Cell>>() {
                    };
                    ArrayList<Cell> yourArray = snapshot.getValue(t);
                    //Toast.makeText(getContext(),yourStringArray.get(0).getName(),Toast.LENGTH_LONG).show();
                    try {
                        Cell.listofOCells = new ArrayList<>();
                        for (int i = 0; i < Cell.numberOfRows; i++) {
                            for (int j = 0; j < Cell.numberOfCols; j++) {
                                Cell.valueOfCell[i][j] = 0;
                                Cell.indexOfCell[i][j] = 0;
                            }
                        }
                        for (int i = 0; i < yourArray.size(); i++) {
                            int currentrow = yourArray.get(i).getRow();
                            int currentcol = yourArray.get(i).getCol();
                            //Bitmap xcell_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.x_icon);
                            Cell newcell = new Cell(gameSurface_this, ocell_bitmap, currentcol * Cell.width + background.getX(), currentrow * Cell.height + background.getY());
                            newcell.setStartX(newcell.getX());
                            newcell.setStartY(newcell.getY());
                            newcell.setStart_game_dragX(background.getX());
                            newcell.setStart_game_dragY(background.getY());
                            newcell.setRow(currentrow);
                            newcell.setCol(currentcol);
                            Cell.listofOCells.add(newcell);
                            Cell.valueOfCell[Cell.listofOCells.get(i).getRow()][Cell.listofOCells.get(i).getCol()] = 2;
                            Cell.indexOfCell[Cell.listofOCells.get(i).getRow()][Cell.listofOCells.get(i).getCol()] = i;
                            //Log.v("long", "take data");
                        }
                    } catch (NullPointerException e) {

                    }
                    //tv.setText(abc);
                    //Log.v("long", "take data");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Query newq3 = mDatabase.child(InfoPage.roomID).child("player_count");
            newq3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //GenericTypeIndicator<ArrayList<Cell>> t = new GenericTypeIndicator<ArrayList<Cell>>() {};
                    int player_count = snapshot.getValue(Integer.class);
                    //Toast.makeText(getContext(),yourStringArray.get(0).getName(),Toast.LENGTH_LONG).show();
                    try {
                        if (player_count == 3) updatename = 1;
                        if (player_count == 1 && yourID == -1) {
                            yourID = 1;
                            mDatabase.child(InfoPage.roomID).child("player_count").setValue(2);
                        } else if (player_count == 2 && yourID == -1) {
                            yourID = 2;
                            mDatabase.child(InfoPage.roomID).child("player_count").setValue(3);
                        }
                    } catch (NullPointerException e) {

                    }
                    //tv.setText(abc);
                    //Log.v("long", "take data");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            Query newq4 = mDatabase.child(InfoPage.roomID).child("turn");
            newq4.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //GenericTypeIndicator<ArrayList<Cell>> t = new GenericTypeIndicator<ArrayList<Cell>>() {};
                    Integer turn = snapshot.getValue(Integer.class);
                    //Toast.makeText(getContext(),yourStringArray.get(0).getName(),Toast.LENGTH_LONG).show();
                    try {
                        turn_online = turn;
                    } catch (NullPointerException e) {

                    }
                    //tv.setText(abc);
                    //Log.v("long", "take data");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            Query newq5 = mDatabase.child(InfoPage.roomID).child("player1_name");
            newq5.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //GenericTypeIndicator<ArrayList<Cell>> t = new GenericTypeIndicator<ArrayList<Cell>>() {};
                    String name = snapshot.getValue(String.class);
                    //Toast.makeText(getContext(),yourStringArray.get(0).getName(),Toast.LENGTH_LONG).show();
                    try {
                        player1.name = name;
                    } catch (NullPointerException e) {

                    }
                    //tv.setText(abc);
                    //Log.v("long", "take data");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Query newq7 = mDatabase.child(InfoPage.roomID).child("pause");
            newq7.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //GenericTypeIndicator<ArrayList<Cell>> t = new GenericTypeIndicator<ArrayList<Cell>>() {};
                    Boolean name = snapshot.getValue(Boolean.class);
                    //Toast.makeText(getContext(),yourStringArray.get(0).getName(),Toast.LENGTH_LONG).show();
                    try {
                        game_Pause = name;
                    } catch (NullPointerException e) {

                    }
                    //tv.setText(abc);
                    //Log.v("long", "take data");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            listofAccounts = new ArrayList<>();
            Query newq8 = mDatabase.child("listaccount");
            newq8.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<ArrayList<Account>> t = new GenericTypeIndicator<ArrayList<Account>>() {};
                    ArrayList<Account> yourStringArray = snapshot.getValue(t);
                    //Toast.makeText(getContext(),yourStringArray.get(0).getName(),Toast.LENGTH_LONG).show();

                    try {
                        listofAccounts.clear();
                        for(int i = 0; i < yourStringArray.size(); i++)
                        {
                            listofAccounts.add(yourStringArray.get(i));
                        }
                    }
                    catch (NullPointerException e) {

                    }

                    //Log.v("long", "take data");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Query newq9 = mDatabase.child(InfoPage.roomID).child("gameover");
            newq9.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //GenericTypeIndicator<ArrayList<Cell>> t = new GenericTypeIndicator<ArrayList<Cell>>() {};
                    Boolean name = snapshot.getValue(Boolean.class);
                    //Toast.makeText(getContext(),yourStringArray.get(0).getName(),Toast.LENGTH_LONG).show();
                    try {
                        game_Over = name;
                    } catch (NullPointerException e) {

                    }
                    //tv.setText(abc);
                    //Log.v("long", "take data");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
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
            else if (event.getAction() == MotionEvent.ACTION_UP) {
                //Log.v("long", "1 " + yourID);
                if (!dragged) {
                    if (clicked_in_background && !game_Over && !game_Pause) {
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
                            if (((player1.getTurn() && Home.menu_type != 3)||(Home.menu_type == 3 && turn_online == 1&&yourID == 1))&& !player1.isBot) {
                                if (Home.menu_type == 1 || Home.menu_type == 2) Cell.listofXCells.add(newcell);
                                else {
                                    ArrayList<Cell> newarraycell = new ArrayList<>();
                                    for(int k = 0; k < Cell.listofXCells.size(); k++)
                                        newarraycell.add(Cell.listofXCells.get(k));
                                    newarraycell.add(newcell);
                                    mDatabase.child(InfoPage.roomID).child("data_X").setValue(newarraycell);
                                }
                                if (Home.menu_type != 3) {
                                    Cell.valueOfCell[newcell.getRow()][newcell.getCol()] = 1;
                                    Cell.indexOfCell[newcell.getRow()][newcell.getCol()] = Cell.listofXCells.size() - 1;
                                }
                                swap_turn();
                                if (Home.menu_type == 3) mDatabase.child(InfoPage.roomID).child("turn").setValue(2);

                            } else if (((player2.getTurn() && Home.menu_type != 3) || (Home.menu_type == 3 && yourID == 2 && turn_online == 2)) && !player2.isBot) {
                                //Log.v("long", "2");
                                Bitmap ocell_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.o_icon);
                                newcell.setImage(ocell_bitmap);
                                if (Home.menu_type != 3) Cell.listofOCells.add(newcell);
                                else {
                                    ArrayList<Cell> newarraycell = new ArrayList<>();
                                    for(int k = 0; k < Cell.listofOCells.size(); k++)
                                        newarraycell.add(Cell.listofOCells.get(k));
                                    newarraycell.add(newcell);
                                    mDatabase.child(InfoPage.roomID).child("data_O").setValue(newarraycell);
                                }
                                if (Home.menu_type != 3) {
                                    Cell.valueOfCell[newcell.getRow()][newcell.getCol()] = 2;
                                    Cell.indexOfCell[newcell.getRow()][newcell.getCol()] = Cell.listofOCells.size() - 1;
                                }
                                swap_turn();
                                if (Home.menu_type == 3) mDatabase.child(InfoPage.roomID).child("turn").setValue(1);

                            }

                        }
                    }
                    else if (!clicked_in_background){
                        //mDatabase.child(InfoPage.roomID).child("reset").setValue(1);
                        if (event.getX() >= reset_icon.getX() && event.getX() <= reset_icon.getX() + Cell.width && event.getY() >= reset_icon.getY() && event.getY() <= reset_icon.getY() + Cell.height) reset();
                        else if (event.getX() >= home_icon.getX() && event.getX() <= home_icon.getX() + Cell.width && event.getY() >= home_icon.getY() && event.getY() <= home_icon.getY() + Cell.height) backtohome();
                        else if (event.getX() >= pause_icon.getX() && event.getX() <= pause_icon.getX() + Cell.width && event.getY() >= pause_icon.getY() && event.getY() <= pause_icon.getY() + Cell.height) {
                            if (game_Pause == true) {
                                if (Home.menu_type == 3) mDatabase.child(InfoPage.roomID).child("pause").setValue(false);
                                else game_Pause = false;
                            }
                            else {
                                if (Home.menu_type == 3) mDatabase.child(InfoPage.roomID).child("pause").setValue(true);
                                else game_Pause = true;
                            }
                        }
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
