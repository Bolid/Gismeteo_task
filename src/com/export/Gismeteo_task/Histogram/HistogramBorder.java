package com.export.Gismeteo_task.Histogram;


import android.graphics.*;

public class HistogramBorder {

    private int RED_START, GREEN_START, BLUE_START, RED_STOP, GREEN_STOP, BLUE_STOP;

    private float stepBorder;

    private float posY;

    private float koef;

    public HistogramBorder(float posY, float stepBorder, float koef){
        this.posY = posY;
        this.stepBorder = stepBorder;
        this.koef = koef;
    }

    public void drawBorder(Canvas canvas, Paint paint, float posX){
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(6);
        paint.setShader(new LinearGradient(posX, posY, posX, posY + koef * stepBorder, Color.argb(200, RED_START, GREEN_START, BLUE_START), Color.argb(200, RED_STOP, GREEN_STOP, BLUE_STOP), Shader.TileMode.CLAMP));
        canvas.drawLine(posX, posY, posX, posY + koef * stepBorder, paint);
        canvas.restore();
    }

    public void setColorStart(HistogramColor histogramColor){
        RED_START = histogramColor.getRED();
        GREEN_START = histogramColor.getGREEN();
        BLUE_START = histogramColor.getBLUE();
    }

    public void setColorStop(HistogramColor histogramColor){
        RED_STOP = histogramColor.getRED();
        GREEN_STOP = histogramColor.getGREEN();
        BLUE_STOP = histogramColor.getBLUE();
    }
}
