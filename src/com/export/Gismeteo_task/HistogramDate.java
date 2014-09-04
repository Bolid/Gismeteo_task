package com.export.Gismeteo_task;

import android.graphics.Canvas;
import android.graphics.Paint;

public class HistogramDate {

    private String date;

    private float posXDefault;

    private float posX;

    public HistogramDate(String date){
        this.date = date;
    }

    public void drawDate(Canvas canvas, Paint paint, float posX1, float xOffset){

        if (Math.abs(posX - posX1) <= 10 || (posX > 10) || (posX < 10)){
            posX += xOffset;
        }
        else posX = 10;
        paint.setTextSize(28);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);


    }
}
