package com.example.tictactoe;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Cell extends GameObject {
    public static ArrayList<Cell> listofXCells = new ArrayList<>();
    public static ArrayList<Cell> listofOCells = new ArrayList<>();
    public static int valueOfCell[][] = new int[100][100];
    public static int indexOfCell[][] = new int[100][100];
    private int row;
    private int col;
    public boolean won = false;
    public static float width;
    public static float height;
    public final static int numberOfCols = 50;
    public final static int numberOfRows = 50;
    private GameSurface gameSurface;
    public Cell() {
    }
    public Cell(GameSurface gameSurface, Bitmap image, float x, float y) {
        super(image, x , y);
        this.gameSurface = gameSurface;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public int getRow() {
        return this.row;
    }
    public int getCol() {
        return this.col;
    }
    public void update(float x, float y) {
        moveTo(x, y);
    }
    public static boolean checkVerticalStraightLine(int r, int c, int id) {
        if (r < 4) return false;
        boolean kt = true;
        for (int step = 1; step <= 4; step++) {
                if (Cell.valueOfCell[r - step][c] != id) {
                    kt = false;
                    break;
                }
        }
        if (kt) {
            for(int step = 0; step <= 4; step++) {
                if (id == 1) {
                    Cell.listofXCells.get(Cell.indexOfCell[r - step][c]).won = true;
                }
                else if (id == 2) {
                    Cell.listofOCells.get(Cell.indexOfCell[r - step][c]).won = true;
                }
            }
        }
        return kt;
    }
    public static boolean checkHozirontalStraightLine(int r, int c, int id) {
        if (c < 4) return false;
        boolean kt = true;
        for (int step = 1; step <= 4; step++) {
            if (Cell.valueOfCell[r][c - step] != id) {
                kt = false;
                break;
            }
        }
        if (kt) {
            for(int step = 0; step <= 4; step++) {
                if (id == 1) {
                    Cell.listofXCells.get(Cell.indexOfCell[r][c - step]).won = true;
                }
                else if (id == 2) {
                    Cell.listofOCells.get(Cell.indexOfCell[r][c - step]).won = true;
                }
            }
        }
        return kt;
    }
    public static boolean checkRightDiagonalLine(int r, int c, int id) {
        if (r < 4 || c > Cell.numberOfCols - 5) return false;
        boolean kt = true;
        for (int step = 1; step <= 4; step++) {
            if (Cell.valueOfCell[r - step][c + step] != id) {
                kt = false;
                break;
            }
        }
        if (kt) {
            for(int step = 0; step <= 4; step++) {
                if (id == 1) {
                    Cell.listofXCells.get(Cell.indexOfCell[r - step][c + step]).won = true;
                }
                else if (id == 2) {
                    Cell.listofOCells.get(Cell.indexOfCell[r - step][c + step]).won = true;
                }
            }
        }
        return kt;
    }
    public static boolean checkLeftDiagonalLine(int r, int c, int id) {
        if (r < 4 || c < 4) return false;
        boolean kt = true;
        for (int step = 1; step <= 4; step++) {
            if (Cell.valueOfCell[r - step][c - step] != id) {
                kt = false;
                break;
            }
        }
        if (kt) {
            for(int step = 0; step <= 4; step++) {
                if (id == 1) {
                    Cell.listofXCells.get(Cell.indexOfCell[r - step][c - step]).won = true;
                }
                else if (id == 2) {
                    Cell.listofOCells.get(Cell.indexOfCell[r - step][c - step]).won = true;
                }
            }
        }
        return kt;
    }
}
