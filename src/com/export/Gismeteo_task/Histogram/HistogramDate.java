package com.export.Gismeteo_task.Histogram;

import android.graphics.Canvas;
import android.graphics.Paint;

public class HistogramDate {

    private String date;

    private float widthBar;

    private float posX;

    public HistogramDate(String date, float posX, float widthBar){
        this.date = date;
        this.posX = posX;
        this.widthBar = widthBar;
    }

    public void drawDate(Canvas canvas, Paint paint, float xOffset){
        posX = xOffset - widthBar;
        paint.setTextSize(32);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText(date, posX, 50, paint);
        canvas.restore();
    }
}
