package com.example.tictactoe;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class GameObject {
    protected Bitmap image;
    protected float x;
    protected float y;
    public static float width;
    public static float height;
    private float startX;
    private float startY;
    public float start_game_dragX;
    public float start_game_dragY;
    public GameObject(Bitmap image, float x, float y) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }
    public float getX() {
        return this.x;
    }
    public float getY() {
        return this.y;
    }
    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }
    public void moveTo(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }
    public void setStartX(float startX) {
        this.startX = startX;
    }
    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStart_game_dragX(float start_game_dragX) {
        this.start_game_dragX = start_game_dragX;
    }

    public void setStart_game_dragY(float start_game_dragY) {
        this.start_game_dragY = start_game_dragY;
    }

    public float getStart_game_dragX() {
        return start_game_dragX;
    }

    public float getStart_game_dragY() {
        return start_game_dragY;
    }
}
