package com.example.tictactoe;

import android.graphics.Bitmap;

public class Background extends GameObject{
    private GameSurface gameSurface;
    public static float width;
    public static float height;
    public Background(GameSurface gameSurface, Bitmap image, float x, float y) {
        super(image, x, y);
        this.gameSurface = gameSurface;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

}
