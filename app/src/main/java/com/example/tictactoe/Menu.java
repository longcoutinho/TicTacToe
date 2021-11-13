package com.example.tictactoe;

import android.graphics.Bitmap;

public class Menu extends GameObject {
    public static float width;
    public static float height;
    private GameSurface gameSurface;
    public Menu(GameSurface gameSurface, Bitmap image, float x, float y) {
        super(image, x, y);
        this.gameSurface = gameSurface;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }
}
