package com.example.tictactoe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Player {
    private int id;
    private boolean turn;
    public Avatar avatar;
    public String status;
    public boolean isBot;
    public String name = "";
    public Player(boolean turn, int id) {
        this.turn = turn;
        this.id = id;
    }
    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getTurn() {
        return this.turn;
    }

    public int getId() {
        return id;
    }

    public boolean win() {
        //Log.v("long", "checking!!");
        if (id == 1) {
            for (int i = 0; i < Cell.listofXCells.size(); i++) {
                int r = Cell.listofXCells.get(i).getRow();
                int c = Cell.listofXCells.get(i).getCol();
                // check
                if (Cell.checkVerticalStraightLine(r, c, id)) return true;
                if (Cell.checkHozirontalStraightLine(r, c, id)) return true;
                if (Cell.checkRightDiagonalLine(r, c, id)) return true;
                if (Cell.checkLeftDiagonalLine(r, c, id)) return true;
            }
            return false;
        } else if (id == 2) {
            for (int i = 0; i < Cell.listofOCells.size(); i++) {
                int r = Cell.listofOCells.get(i).getRow();
                int c = Cell.listofOCells.get(i).getCol();
                // check
                if (Cell.checkVerticalStraightLine(r, c, id)) return true;
                if (Cell.checkHozirontalStraightLine(r, c, id)) return true;
                if (Cell.checkRightDiagonalLine(r, c, id)) return true;
                if (Cell.checkLeftDiagonalLine(r, c, id)) return true;
            }
            return false;
        }
        return false;
    }

    public boolean check_limit(int a, int b) {
        if (a < 0 || a >= Cell.numberOfRows) return false;
        if (b < 0 || b >= Cell.numberOfCols) return false;
        return true;
    }

    public Coordinates BotFill() {
        if (id == 1) {
            boolean check[] = new boolean[10];
            for(int i = 0; i< Cell.numberOfRows; i++)
                for(int j = 0; j < Cell.numberOfCols; j++) {
                    if (Cell.valueOfCell[i][j] == 0) {
                        // Check my-5-in-a-row
                        // reset
                        for(int k = 1; k <= 8; k++)
                            check[k] = true;
                        for(int k = 1; k <= 4; k++) {
                            if (!check_limit(i - k, j) || Cell.valueOfCell[i - k][j] != id) {
                                check[1] = false;
                                //if (i == 7 && j == 6) Log.i("long", i + " " + j + " " + k);
                            }
                            if (!check_limit(i - k, j + k) || Cell.valueOfCell[i - k][j + k] != id) {
                                check[2] = false;
                            }
                            if (!check_limit(i, j + k) || Cell.valueOfCell[i][j + k] != id) {
                                check[3] = false;
                            }
                            if (!check_limit(i + k , j + k)  || Cell.valueOfCell[i + k][j + k] != id) {
                                check[4] = false;
                            }
                            if (!check_limit(i + k, j) || Cell.valueOfCell[i + k][j] != id) {
                                check[5] = false;
                            }
                            if (!check_limit(i + k , j - k) || Cell.valueOfCell[i + k][j - k] != id) {
                                check[6] = false;
                            }
                            if (!check_limit(i, j - k) || Cell.valueOfCell[i][j - k] != id) {
                                check[7] = false;
                            }
                            if (!check_limit(i - k, j - k) || Cell.valueOfCell[i - k][j - k] != id) {
                                check[8] = false;
                            }
                        }
                        // check if one of 8 directions has true condition
                        for(int k = 1; k <= 8; k++) {
                            if (check[k]) {
                                Log.i("long", "check:" + i + " " + j + " " + k);
                                return new Coordinates(i, j);
                            }
                        }
                        // reset
                        for(int k = 1; k <= 8; k++)
                            check[k] = true;
                        for(int k = -1; k <= 3; k++) if (k!= 0){
                            if (!check_limit(i - k, j) || Cell.valueOfCell[i - k][j] != id) {
                                check[1] = false;
                                //if (i == 7 && j == 6) Log.i("long", i + " " + j + " " + k);
                            }
                            if (!check_limit(i - k, j + k) || Cell.valueOfCell[i - k][j + k] != id) {
                                check[2] = false;
                            }
                            if (!check_limit(i, j + k) || Cell.valueOfCell[i][j + k] != id) {
                                check[3] = false;
                            }
                            if (!check_limit(i + k , j + k)  || Cell.valueOfCell[i + k][j + k] != id) {
                                check[4] = false;
                            }
                            if (!check_limit(i + k, j) || Cell.valueOfCell[i + k][j] != id) {
                                check[5] = false;
                            }
                            if (!check_limit(i + k , j - k) || Cell.valueOfCell[i + k][j - k] != id) {
                                check[6] = false;
                            }
                            if (!check_limit(i, j - k) || Cell.valueOfCell[i][j - k] != id) {
                                check[7] = false;
                            }
                            if (!check_limit(i - k, j - k) || Cell.valueOfCell[i - k][j - k] != id) {
                                check[8] = false;
                            }
                        }
                        // check if one of 8 directions has true condition
                        for(int k = 1; k <= 8; k++) {
                            if (check[k]) {
                                Log.i("long", "check:" + i + " " + j + " " + k);
                                return new Coordinates(i, j);
                            }
                        }
                        // reset
                        for(int k = 1; k <= 8; k++)
                            check[k] = true;
                        for(int k = -2; k <= 2; k++) if (k!= 0){
                            if (!check_limit(i - k, j) || Cell.valueOfCell[i - k][j] != id) {
                                check[1] = false;
                                //if (i == 7 && j == 6) Log.i("long", i + " " + j + " " + k);
                            }
                            if (!check_limit(i - k, j + k) || Cell.valueOfCell[i - k][j + k] != id) {
                                check[2] = false;
                            }
                            if (!check_limit(i, j + k) || Cell.valueOfCell[i][j + k] != id) {
                                check[3] = false;
                            }
                            if (!check_limit(i + k , j + k)  || Cell.valueOfCell[i + k][j + k] != id) {
                                check[4] = false;
                            }
                            if (!check_limit(i + k, j) || Cell.valueOfCell[i + k][j] != id) {
                                check[5] = false;
                            }
                            if (!check_limit(i + k , j - k) || Cell.valueOfCell[i + k][j - k] != id) {
                                check[6] = false;
                            }
                            if (!check_limit(i, j - k) || Cell.valueOfCell[i][j - k] != id) {
                                check[7] = false;
                            }
                            if (!check_limit(i - k, j - k) || Cell.valueOfCell[i - k][j - k] != id) {
                                check[8] = false;
                            }
                        }
                        // check if one of 8 directions has true condition
                        for(int k = 1; k <= 8; k++) {
                            if (check[k]) {
                                Log.i("long", "check:" + i + " " + j + " " + k);
                                return new Coordinates(i, j);
                            }
                        }

                        // Check their-5-in-a-row
                        // reset
                        for(int k = 1; k <= 8; k++)
                            check[k] = true;
                        // check 8 directions. 1 is upward vertical. from 1 to 8 is left to right, up to down.
                        for(int k = 1; k <= 4; k++) {
                            if (!check_limit(i - k, j) || Cell.valueOfCell[i - k][j] != 3 - id) {
                                check[1] = false;
                            }
                            if (!check_limit(i - k, j + k) || Cell.valueOfCell[i - k][j + k] != 3 - id) {
                                check[2] = false;
                            }
                            if (!check_limit(i, j + k) || Cell.valueOfCell[i][j + k] != 3 - id) {
                                check[3] = false;
                            }
                            if (!check_limit(i + k , j + k)  || Cell.valueOfCell[i + k][j + k] != 3 - id) {
                                check[4] = false;
                            }
                            if (!check_limit(i + k, j) || Cell.valueOfCell[i + k][j] != 3 - id) {
                                check[5] = false;
                            }
                            if (!check_limit(i + k , j - k) || Cell.valueOfCell[i + k][j - k] != 3 - id) {
                                check[6] = false;
                            }
                            if (!check_limit(i, j - k) || Cell.valueOfCell[i][j - k] != 3 - id) {
                                check[7] = false;
                            }
                            if (!check_limit(i - k, j - k) || Cell.valueOfCell[i - k][j - k] != 3 - id) {
                                check[8] = false;
                            }
                        }
                        // check if one of 8 directions has true condition
                        for(int k = 1; k <= 8; k++) {
                            if (check[k]) {
                                //Log.i("long", "check:" + i + " " + j + " " + k);
                                return new Coordinates(i, j);
                            }
                        }
                        // reset
                        for(int k = 1; k <= 8; k++)
                            check[k] = true;
                        // check 8 directions. 1 is upward vertical. from 1 to 8 is left to right, up to down.
                        for(int k = -1; k <= 3; k++) if (k != 0) {
                            if (!check_limit(i - k, j) || Cell.valueOfCell[i - k][j] != 3 - id) {
                                check[1] = false;
                            }
                            if (!check_limit(i - k, j + k) || Cell.valueOfCell[i - k][j + k] != 3 - id) {
                                check[2] = false;
                            }
                            if (!check_limit(i, j + k) || Cell.valueOfCell[i][j + k] != 3 - id) {
                                check[3] = false;
                            }
                            if (!check_limit(i + k , j + k)  || Cell.valueOfCell[i + k][j + k] != 3 - id) {
                                check[4] = false;
                            }
                            if (!check_limit(i + k, j) || Cell.valueOfCell[i + k][j] != 3 - id) {
                                check[5] = false;
                            }
                            if (!check_limit(i + k , j - k) || Cell.valueOfCell[i + k][j - k] != 3 - id) {
                                check[6] = false;
                            }
                            if (!check_limit(i, j - k) || Cell.valueOfCell[i][j - k] != 3 - id) {
                                check[7] = false;
                            }
                            if (!check_limit(i - k, j - k) || Cell.valueOfCell[i - k][j - k] != 3 - id) {
                                check[8] = false;
                            }
                        }
                        // check if one of 8 directions has true condition
                        for(int k = 1; k <= 8; k++) {
                            if (check[k]) {

                                return new Coordinates(i, j);
                            }
                        }
                        // reset
                        for(int k = 1; k <= 8; k++)
                            check[k] = true;
                        // check 8 directions. 1 is upward vertical. from 1 to 8 is left to right, up to down.
                        for(int k = -2; k <= 2; k++) if (k != 0) {
                            if (!check_limit(i - k, j) || Cell.valueOfCell[i - k][j] != 3 - id) {
                                check[1] = false;
                            }
                            if (!check_limit(i - k, j + k) || Cell.valueOfCell[i - k][j + k] != 3 - id) {
                                check[2] = false;
                            }
                            if (!check_limit(i, j + k) || Cell.valueOfCell[i][j + k] != 3 - id) {
                                check[3] = false;
                            }
                            if (!check_limit(i + k , j + k)  || Cell.valueOfCell[i + k][j + k] != 3 - id) {
                                check[4] = false;
                            }
                            if (!check_limit(i + k, j) || Cell.valueOfCell[i + k][j] != 3 - id) {
                                check[5] = false;
                            }
                            if (!check_limit(i + k , j - k) || Cell.valueOfCell[i + k][j - k] != 3 - id) {
                                check[6] = false;
                            }
                            if (!check_limit(i, j - k) || Cell.valueOfCell[i][j - k] != 3 - id) {
                                check[7] = false;
                            }
                            if (!check_limit(i - k, j - k) || Cell.valueOfCell[i - k][j - k] != 3 - id) {
                                check[8] = false;
                            }
                        }
                        // check if one of 8 directions has true condition
                        for(int k = 1; k <= 8; k++) {
                            if (check[k]) {
                                //Log.i("long", "their-5-in-a-row");
                                return new Coordinates(i, j);
                            }
                        }
                    }
                }

            for(int i = 0; i< Cell.numberOfRows; i++)
                for(int j = 0; j < Cell.numberOfCols; j++) {
                    if (Cell.valueOfCell[i][j] == 0) {

                        // Check my-4-in-a-row-and-no-denied
                        // reset
                        for (int k = 1; k <= 8; k++)
                            check[k] = true;
                        // check 8 directions. 1 is upward vertical. from 1 to 8 is left to right, up to down.
                        for (int k = 1; k <= 3; k++) {
                            if (i - k < 0 || Cell.valueOfCell[i - k][j] != id || i - 4 < 0 || Cell.valueOfCell[i - 4][j] == 3 - id) {
                                check[1] = false;
                            }
                            if (i - k < 0 || j + k >= Cell.numberOfCols || Cell.valueOfCell[i - k][j + k] != id || i - 4 < 0 || j + 4 >= Cell.numberOfCols || Cell.valueOfCell[i - 4][j + 4] == 3 - id) {
                                check[2] = false;
                            }
                            if (j + k >= Cell.numberOfCols || Cell.valueOfCell[i][j + k] != id || j + 4 >= Cell.numberOfCols || Cell.valueOfCell[i][j + 4] == 3 - id) {
                                check[3] = false;
                            }
                            if (i + k >= Cell.numberOfRows || j + k >= Cell.numberOfCols || Cell.valueOfCell[i + k][j + k] != id || i + 4 >= Cell.numberOfRows || j + 4 >= Cell.numberOfCols || Cell.valueOfCell[i + 4][j + 4] == 3 - id) {
                                check[4] = false;
                            }
                            if (i + k >= Cell.numberOfRows || Cell.valueOfCell[i + k][j] != id || i + 4 >= Cell.numberOfRows || Cell.valueOfCell[i + k][j] == 3 - id) {
                                check[5] = false;
                            }
                            if (i + k >= Cell.numberOfRows || j - k < 0 || Cell.valueOfCell[i + k][j - k] != id || i + 4 >= Cell.numberOfRows || j - 4 < 0 || Cell.valueOfCell[i + 4][j - 4] == 3 - id) {
                                check[6] = false;
                            }
                            if (j - k < 0 || Cell.valueOfCell[i][j - k] != id || j - 4 < 0 || Cell.valueOfCell[i][j - 4] == 3 - id) {
                                check[7] = false;
                            }
                            if (i - k < 0 || j - k < 0 || Cell.valueOfCell[i - k][j - k] != id || i - 4 < 0 || j - 4 < 0 || Cell.valueOfCell[i - 4][j - 4] == 3 - id) {
                                check[8] = false;
                            }
                        }
                        // check if one of 8 directions has true condition
                        for (int k = 1; k <= 8; k++) {
                            if (check[k]) {
                                //Log.i("long", "check:" + i + " " + j + " " + k);
                                return new Coordinates(i, j);
                            }
                        }


                        // Check their-4-in-a-row-and-no-denied
                        // reset
                        for (int k = 1; k <= 8; k++)
                            check[k] = true;
                        // check 8 directions. 1 is upward vertical. from 1 to 8 is left to right, up to down.
                        for (int k = 1; k <= 3; k++) {
                            if (!check_limit(i - k, j) || Cell.valueOfCell[i - k][j] != 3 - id || !check_limit(i - 4, j) || Cell.valueOfCell[i - 4][j] == id) {
                                check[1] = false;
                            }
                            if (!check_limit(i - k, j + k) || Cell.valueOfCell[i - k][j + k] != 3 - id || !check_limit(i - 4, j + 4) || Cell.valueOfCell[i - 4][j + 4] == id) {
                                check[2] = false;
                            }
                            if (!check_limit(i, j + k) || Cell.valueOfCell[i][j + k] != 3 - id || !check_limit(i, j + 4) || Cell.valueOfCell[i][j + 4] == id) {
                                check[3] = false;
                            }
                            if (!check_limit(i + k, j + k) || Cell.valueOfCell[i + k][j + k] != 3 - id || !check_limit(i + 4, j + 4) || Cell.valueOfCell[i + 4][j + 4] == id) {
                                check[4] = false;
                            }
                            if (!check_limit(i + k, j) || Cell.valueOfCell[i + k][j] != 3 - id || !check_limit(i + k, j) || Cell.valueOfCell[i + k][j] == id) {
                                check[5] = false;
                            }
                            if (!check_limit(i + k, j - k) || Cell.valueOfCell[i + k][j - k] != 3 - id || !check_limit(i + 4, j - 4) || Cell.valueOfCell[i + 4][j - 4] == id) {
                                check[6] = false;
                            }
                            if (j - k < 0 || Cell.valueOfCell[i][j - k] != 3 - id || j - 4 < 0 || Cell.valueOfCell[i][j - 4] == id) {
                                check[7] = false;
                            }
                            if (i - k < 0 || j - k < 0 || Cell.valueOfCell[i - k][j - k] != 3 - id || i - 4 < 0 || j - 4 < 0 || Cell.valueOfCell[i - 4][j - 4] == id) {
                                check[8] = false;
                            }
                        }
                        for (int k = 1; k <= 8; k++) {
                            if (check[k]) {
                                //Log.i("long", "check:" + i + " " + j + " " + k);
                                return new Coordinates(i, j);
                            }
                        }
                    }
                }

            int lastrow = Cell.listofOCells.get(Cell.listofOCells.size() - 1).getRow();
            int lastcol = Cell.listofOCells.get(Cell.listofOCells.size() - 1).getCol();
            while(Cell.valueOfCell[lastrow][lastcol] != 0) lastcol++;
            return new Coordinates(lastrow, lastcol);

        }
        else {
            boolean check[] = new boolean[10];
            for(int i = 0; i< Cell.numberOfRows; i++)
                for(int j = 0; j < Cell.numberOfCols; j++) {
                    if (Cell.valueOfCell[i][j] == 0) {
                        // Check their-5-in-a-row
                        // reset
                        for(int k = 1; k <= 8; k++)
                            check[k] = true;
                        // check 8 directions. 1 is upward vertical. from 1 to 8 is left to right, up to down.
                        for(int k = 1; k <= 4; k++) {
                            if (!check_limit(i - k, j) || Cell.valueOfCell[i - k][j] != 3 - id) {
                                check[1] = false;
                            }
                            if (!check_limit(i - k, j + k) || Cell.valueOfCell[i - k][j + k] != 3 - id) {
                                check[2] = false;
                            }
                            if (!check_limit(i, j + k) || Cell.valueOfCell[i][j + k] != 3 - id) {
                                check[3] = false;
                            }
                            if (!check_limit(i + k , j + k)  || Cell.valueOfCell[i + k][j + k] != 3 - id) {
                                check[4] = false;
                            }
                            if (!check_limit(i + k, j) || Cell.valueOfCell[i + k][j] != 3 - id) {
                                check[5] = false;
                            }
                            if (!check_limit(i + k , j - k) || Cell.valueOfCell[i + k][j - k] != 3 - id) {
                                check[6] = false;
                            }
                            if (!check_limit(i, j - k) || Cell.valueOfCell[i][j - k] != 3 - id) {
                                check[7] = false;
                            }
                            if (!check_limit(i - k, j - k) || Cell.valueOfCell[i - k][j - k] != 3 - id) {
                                check[8] = false;
                            }
                        }
                        // check if one of 8 directions has true condition
                        for(int k = 1; k <= 8; k++) {
                            if (check[k]) {
                                //Log.i("long", "check:" + i + " " + j + " " + k);
                                return new Coordinates(i, j);
                            }
                        }
                        // reset
                        for(int k = 1; k <= 8; k++)
                            check[k] = true;
                        // check 8 directions. 1 is upward vertical. from 1 to 8 is left to right, up to down.
                        for(int k = -1; k <= 3; k++) if (k != 0) {
                            if (!check_limit(i - k, j) || Cell.valueOfCell[i - k][j] != 3 - id) {
                                check[1] = false;
                            }
                            if (!check_limit(i - k, j + k) || Cell.valueOfCell[i - k][j + k] != 3 - id) {
                                check[2] = false;
                            }
                            if (!check_limit(i, j + k) || Cell.valueOfCell[i][j + k] != 3 - id) {
                                check[3] = false;
                            }
                            if (!check_limit(i + k , j + k)  || Cell.valueOfCell[i + k][j + k] != 3 - id) {
                                check[4] = false;
                            }
                            if (!check_limit(i + k, j) || Cell.valueOfCell[i + k][j] != 3 - id) {
                                check[5] = false;
                            }
                            if (!check_limit(i + k , j - k) || Cell.valueOfCell[i + k][j - k] != 3 - id) {
                                check[6] = false;
                            }
                            if (!check_limit(i, j - k) || Cell.valueOfCell[i][j - k] != 3 - id) {
                                check[7] = false;
                            }
                            if (!check_limit(i - k, j - k) || Cell.valueOfCell[i - k][j - k] != 3 - id) {
                                check[8] = false;
                            }
                        }
                        // check if one of 8 directions has true condition
                        for(int k = 1; k <= 8; k++) {
                            if (check[k]) {

                                return new Coordinates(i, j);
                            }
                        }
                        // reset
                        for(int k = 1; k <= 8; k++)
                            check[k] = true;
                        // check 8 directions. 1 is upward vertical. from 1 to 8 is left to right, up to down.
                        for(int k = -2; k <= 2; k++) if (k != 0) {
                            if (!check_limit(i - k, j) || Cell.valueOfCell[i - k][j] != 3 - id) {
                                check[1] = false;
                            }
                            if (!check_limit(i - k, j + k) || Cell.valueOfCell[i - k][j + k] != 3 - id) {
                                check[2] = false;
                            }
                            if (!check_limit(i, j + k) || Cell.valueOfCell[i][j + k] != 3 - id) {
                                check[3] = false;
                            }
                            if (!check_limit(i + k , j + k)  || Cell.valueOfCell[i + k][j + k] != 3 - id) {
                                check[4] = false;
                            }
                            if (!check_limit(i + k, j) || Cell.valueOfCell[i + k][j] != 3 - id) {
                                check[5] = false;
                            }
                            if (!check_limit(i + k , j - k) || Cell.valueOfCell[i + k][j - k] != 3 - id) {
                                check[6] = false;
                            }
                            if (!check_limit(i, j - k) || Cell.valueOfCell[i][j - k] != 3 - id) {
                                check[7] = false;
                            }
                            if (!check_limit(i - k, j - k) || Cell.valueOfCell[i - k][j - k] != 3 - id) {
                                check[8] = false;
                            }
                        }
                        // check if one of 8 directions has true condition
                        for(int k = 1; k <= 8; k++) {
                            if (check[k]) {
                                //Log.i("long", "their-5-in-a-row");
                                return new Coordinates(i, j);
                            }
                        }


                        // Check my-5-in-a-row
                        // reset
                        for(int k = 1; k <= 8; k++)
                            check[k] = true;
                        for(int k = 1; k <= 4; k++) {
                            if (!check_limit(i - k, j) || Cell.valueOfCell[i - k][j] != id) {
                                check[1] = false;
                                //if (i == 7 && j == 6) Log.i("long", i + " " + j + " " + k);
                            }
                            if (!check_limit(i - k, j + k) || Cell.valueOfCell[i - k][j + k] != id) {
                                check[2] = false;
                            }
                            if (!check_limit(i, j + k) || Cell.valueOfCell[i][j + k] != id) {
                                check[3] = false;
                            }
                            if (!check_limit(i + k , j + k)  || Cell.valueOfCell[i + k][j + k] != id) {
                                check[4] = false;
                            }
                            if (!check_limit(i + k, j) || Cell.valueOfCell[i + k][j] != id) {
                                check[5] = false;
                            }
                            if (!check_limit(i + k , j - k) || Cell.valueOfCell[i + k][j - k] != id) {
                                check[6] = false;
                            }
                            if (!check_limit(i, j - k) || Cell.valueOfCell[i][j - k] != id) {
                                check[7] = false;
                            }
                            if (!check_limit(i - k, j - k) || Cell.valueOfCell[i - k][j - k] != id) {
                                check[8] = false;
                            }
                        }
                        // check if one of 8 directions has true condition
                        for(int k = 1; k <= 8; k++) {
                            if (check[k]) {
                                Log.i("long", "check:" + i + " " + j + " " + k);
                                return new Coordinates(i, j);
                            }
                        }
                        // reset
                        for(int k = 1; k <= 8; k++)
                            check[k] = true;
                        for(int k = -1; k <= 3; k++) if (k!= 0){
                            if (!check_limit(i - k, j) || Cell.valueOfCell[i - k][j] != id) {
                                check[1] = false;
                                //if (i == 7 && j == 6) Log.i("long", i + " " + j + " " + k);
                            }
                            if (!check_limit(i - k, j + k) || Cell.valueOfCell[i - k][j + k] != id) {
                                check[2] = false;
                            }
                            if (!check_limit(i, j + k) || Cell.valueOfCell[i][j + k] != id) {
                                check[3] = false;
                            }
                            if (!check_limit(i + k , j + k)  || Cell.valueOfCell[i + k][j + k] != id) {
                                check[4] = false;
                            }
                            if (!check_limit(i + k, j) || Cell.valueOfCell[i + k][j] != id) {
                                check[5] = false;
                            }
                            if (!check_limit(i + k , j - k) || Cell.valueOfCell[i + k][j - k] != id) {
                                check[6] = false;
                            }
                            if (!check_limit(i, j - k) || Cell.valueOfCell[i][j - k] != id) {
                                check[7] = false;
                            }
                            if (!check_limit(i - k, j - k) || Cell.valueOfCell[i - k][j - k] != id) {
                                check[8] = false;
                            }
                        }
                        // check if one of 8 directions has true condition
                        for(int k = 1; k <= 8; k++) {
                            if (check[k]) {
                                Log.i("long", "check:" + i + " " + j + " " + k);
                                return new Coordinates(i, j);
                            }
                        }
                        // reset
                        for(int k = 1; k <= 8; k++)
                            check[k] = true;
                        for(int k = -2; k <= 2; k++) if (k!= 0){
                            if (!check_limit(i - k, j) || Cell.valueOfCell[i - k][j] != id) {
                                check[1] = false;
                                //if (i == 7 && j == 6) Log.i("long", i + " " + j + " " + k);
                            }
                            if (!check_limit(i - k, j + k) || Cell.valueOfCell[i - k][j + k] != id) {
                                check[2] = false;
                            }
                            if (!check_limit(i, j + k) || Cell.valueOfCell[i][j + k] != id) {
                                check[3] = false;
                            }
                            if (!check_limit(i + k , j + k)  || Cell.valueOfCell[i + k][j + k] != id) {
                                check[4] = false;
                            }
                            if (!check_limit(i + k, j) || Cell.valueOfCell[i + k][j] != id) {
                                check[5] = false;
                            }
                            if (!check_limit(i + k , j - k) || Cell.valueOfCell[i + k][j - k] != id) {
                                check[6] = false;
                            }
                            if (!check_limit(i, j - k) || Cell.valueOfCell[i][j - k] != id) {
                                check[7] = false;
                            }
                            if (!check_limit(i - k, j - k) || Cell.valueOfCell[i - k][j - k] != id) {
                                check[8] = false;
                            }
                        }
                        // check if one of 8 directions has true condition
                        for(int k = 1; k <= 8; k++) {
                            if (check[k]) {
                                Log.i("long", "check:" + i + " " + j + " " + k);
                                return new Coordinates(i, j);
                            }
                        }
                    }
                }



            for(int i = 0; i< Cell.numberOfRows; i++)
                for(int j = 0; j < Cell.numberOfCols; j++) {
                    if (Cell.valueOfCell[i][j] == 0) {
                        // Check their-4-in-a-row-and-no-denied
                        // reset
                        for(int k = 1; k <= 8; k++)
                            check[k] = true;
                        // check 8 directions. 1 is upward vertical. from 1 to 8 is left to right, up to down.
                        for(int k = 1; k <= 3; k++) {
                            if (!check_limit(i - k, j) || Cell.valueOfCell[i - k][j] != 3 - id || !check_limit(i - 4, j) || Cell.valueOfCell[i - 4][j] == id) {
                                check[1] = false;
                            }
                            if (!check_limit(i - k, j + k) || Cell.valueOfCell[i - k][j + k] != 3 - id || !check_limit(i - 4, j + 4) || Cell.valueOfCell[i - 4][j + 4] == id) {
                                check[2] = false;
                            }
                            if (!check_limit(i, j + k) || Cell.valueOfCell[i][j + k] != 3 - id || !check_limit(i, j + 4) || Cell.valueOfCell[i][j + 4] == id) {
                                check[3] = false;
                            }
                            if (!check_limit(i + k, j + k) || Cell.valueOfCell[i + k][j + k] != 3 - id || !check_limit(i + 4, j + 4) || Cell.valueOfCell[i + 4][j + 4] == id) {
                                check[4] = false;
                            }
                            if (!check_limit(i + k, j) || Cell.valueOfCell[i + k][j] != 3 - id || !check_limit(i + k, j) || Cell.valueOfCell[i + k][j] == id) {
                                check[5] = false;
                            }
                            if (!check_limit(i + k, j - k) || Cell.valueOfCell[i + k][j - k] != 3 - id || !check_limit(i + 4, j - 4) || Cell.valueOfCell[i + 4][j - 4] == id) {
                                check[6] = false;
                            }
                            if (j - k < 0 || Cell.valueOfCell[i][j - k] != 3 - id || j - 4 < 0 || Cell.valueOfCell[i][j - 4] == id) {
                                check[7] = false;
                            }
                            if (i - k < 0 || j - k < 0 || Cell.valueOfCell[i - k][j - k] != 3 - id || i - 4 < 0 || j - 4 < 0 || Cell.valueOfCell[i - 4][j - 4] == id) {
                                check[8] = false;
                            }
                        }
                        for(int k = 1; k <= 8; k++) {
                            if (check[k]) {
                                //Log.i("long", "check:" + i + " " + j + " " + k);
                                return new Coordinates(i, j);
                            }
                        }

                        // Check my-4-in-a-row-and-no-denied
                        // reset
                        for(int k = 1; k <= 8; k++)
                            check[k] = true;
                        // check 8 directions. 1 is upward vertical. from 1 to 8 is left to right, up to down.
                        for(int k = 1; k <= 3; k++) {
                            if (i - k < 0 || Cell.valueOfCell[i - k][j] != id || i - 4 < 0 || Cell.valueOfCell[i - 4][j] == 3 - id) {
                                check[1] = false;
                            }
                            if (i - k < 0 || j + k >= Cell.numberOfCols || Cell.valueOfCell[i - k][j + k] != id || i - 4 < 0 || j + 4 >= Cell.numberOfCols || Cell.valueOfCell[i - 4][j + 4] == 3 - id) {
                                check[2] = false;
                            }
                            if (j + k >= Cell.numberOfCols || Cell.valueOfCell[i][j + k] != id || j + 4 >= Cell.numberOfCols || Cell.valueOfCell[i][j + 4] == 3 - id) {
                                check[3] = false;
                            }
                            if (i + k >= Cell.numberOfRows || j + k >= Cell.numberOfCols || Cell.valueOfCell[i + k][j + k] != id || i + 4 >= Cell.numberOfRows || j + 4 >= Cell.numberOfCols || Cell.valueOfCell[i + 4][j + 4] == 3 - id) {
                                check[4] = false;
                            }
                            if (i + k >= Cell.numberOfRows || Cell.valueOfCell[i + k][j] != id || i + 4 >= Cell.numberOfRows || Cell.valueOfCell[i + k][j] == 3 - id) {
                                check[5] = false;
                            }
                            if (i + k >= Cell.numberOfRows || j - k < 0 || Cell.valueOfCell[i + k][j - k] != id || i + 4 >= Cell.numberOfRows || j - 4 < 0 || Cell.valueOfCell[i + 4][j - 4] == 3 - id) {
                                check[6] = false;
                            }
                            if (j - k < 0 || Cell.valueOfCell[i][j - k] != id || j - 4 < 0 || Cell.valueOfCell[i][j - 4] == 3 - id) {
                                check[7] = false;
                            }
                            if (i - k < 0 || j - k < 0 || Cell.valueOfCell[i - k][j - k] != id || i - 4 < 0 || j - 4 < 0 || Cell.valueOfCell[i - 4][j - 4] == 3 - id) {
                                check[8] = false;
                            }
                        }
                        // check if one of 8 directions has true condition
                        for(int k = 1; k <= 8; k++) {
                            if (check[k]) {
                                //Log.i("long", "check:" + i + " " + j + " " + k);
                                return new Coordinates(i, j);
                            }
                        }

                        // Check their-double-3-in-a-row-and-no-denied
                        /*
                        // reset
                        for(int k = 1; k <= 8; k++)
                            check[k] = true;
                        // check 8 directions. 1 is upward vertical. from 1 to 8 is left to right, up to down.
                        for(int k = 1; k <= 2; k++) {
                            if (i - k < 0 || Cell.valueOfCell[i - k][j] != 3 - id || i - 4 < 0 || Cell.valueOfCell[i - 4][j] == id) {
                                check[1] = false;
                            }
                            if (i - k < 0 || j + k >= Cell.numberOfCols || Cell.valueOfCell[i - k][j + k] != 3 - id || i - 4 < 0 || j + 4 >= Cell.numberOfCols || Cell.valueOfCell[i - 4][j + 4] == id) {
                                check[2] = false;
                            }
                            if (j + k >= Cell.numberOfCols || Cell.valueOfCell[i][j + k] != 3 - id || j + 4 >= Cell.numberOfCols || Cell.valueOfCell[i][j + 4] == id) {
                                check[3] = false;
                            }
                            if (i + k >= Cell.numberOfRows || j + k >= Cell.numberOfCols || Cell.valueOfCell[i + k][j + k] != 3 - id || i + 4 >= Cell.numberOfRows || j + 4 >= Cell.numberOfCols || Cell.valueOfCell[i + 4][j + 4] == id) {
                                check[4] = false;
                            }
                            if (i + k >= Cell.numberOfRows || Cell.valueOfCell[i + k][j] != 3 - id || i + 4 >= Cell.numberOfRows || Cell.valueOfCell[i + k][j] == id) {
                                check[5] = false;
                            }
                            if (i + k >= Cell.numberOfRows || j - k < 0 || Cell.valueOfCell[i + k][j - k] != 3 - id || i + 4 >= Cell.numberOfRows || j - 4 < 0 || Cell.valueOfCell[i + 4][j - 4] == id) {
                                check[6] = false;
                            }
                            if (j - k < 0 || Cell.valueOfCell[i][j - k] != 3 - id || j - 4 < 0 || Cell.valueOfCell[i][j - 4] == id) {
                                check[7] = false;
                            }
                            if (i - k < 0 || j - k < 0 || Cell.valueOfCell[i - k][j - k] != 3 - id || i - 4 < 0 || j - 4 < 0 || Cell.valueOfCell[i - 4][j - 4] == id) {
                                check[8] = false;
                            }
                            // check if one of 8 directions has true condition
                            for (k = 1; k <= 8; k++) {
                                if (check[k]) {
                                    return new Coordinates(i, j);
                                }
                            }
                        }
                        */

                    }
                }

            int lastrow = Cell.listofXCells.get(Cell.listofXCells.size() - 1).getRow();
            int lastcol = Cell.listofXCells.get(Cell.listofXCells.size() - 1).getCol();
            while(Cell.valueOfCell[lastrow][lastcol] != 0) lastcol++;
            return new Coordinates(lastrow, lastcol);
        }
    }
}
