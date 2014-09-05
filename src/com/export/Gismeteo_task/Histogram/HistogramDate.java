package com.export.Gismeteo_task.Histogram;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class HistogramDate {

    private String date;

    private float widthBar;

    private float posX, posXcompress;

    public HistogramDate(String date, float posX, float widthBar){
        this.date = date;
        this.posX = posX;
        this.widthBar = widthBar;
    }

    public void drawDate(Canvas canvas, Paint paint, float xOffset, float posXDateNext){
        posX = xOffset - widthBar;
        if (posX <= 0)
            if (posXDateNext < widthBar * 2){
                posXcompress = posXDateNext - widthBar * 2;
                posX = posXcompress;
            }
            else{
                posX = 10;
            }
        paint.setTextSize(32);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText(date, posX + 10, 50, paint);
        canvas.restore();
    }

    public float getPosX(){
        return posX;
    }
}
