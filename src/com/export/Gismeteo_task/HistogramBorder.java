package com.export.Gismeteo_task;


import android.graphics.*;
import android.util.Log;

public class HistogramBorder {

    private int RED_START, GREEN_START, BLUE_START, RED_STOP, GREEN_STOP, BLUE_STOP;

    private float stepBorder;

    private float posX;

    private float posY;

    private float koef;

    public HistogramBorder(float posX,float posY, float stepBorder, float koef, HistogramColor colorStart, HistogramColor colorStop){
        this.RED_START = colorStart.RED;
        this.GREEN_START = colorStart.GREEN;
        this.BLUE_START = colorStart.BLUE;

        this.RED_STOP = colorStop.RED;
        this.GREEN_STOP = colorStop.GREEN;
        this.BLUE_STOP = colorStop.BLUE;

        this.posX = posX;
        this.posY = posY;
        this.stepBorder = stepBorder;
        this.koef = koef;

        Log.i("ПАРАМЕТРА БОРДЕРА", posX + "  " + posY + "  " + String.valueOf(posY + koef * stepBorder));
    }

    public void drawBorder(Canvas canvas, Paint paint/*, int posX*/){
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(6);
        paint.setShader(new LinearGradient(posX, posY, posX, posY + koef * stepBorder, Color.argb(200, RED_START, GREEN_START, BLUE_START), Color.argb(100, RED_STOP, GREEN_STOP, BLUE_STOP), Shader.TileMode.CLAMP));
        canvas.drawLine(posX, posY, posX, posY + koef * stepBorder, paint);
        canvas.restore();
    }
}
