package com.export.Gismeteo_task;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class HistogramDate {

    private String date;

    private float posXDefault;

    private float widthBar;

    private float posX;

    boolean collapse = false;

    public HistogramDate(String date, float posX, float widthBar){
        this.date = date;
        this.posX = posX;
        this.posXDefault = posX;
        this.widthBar = widthBar;
    }

    public void drawDate(Canvas canvas, Paint paint, float posX1, float posX2, float xOffset){
        if (posX != 0 & !collapse){
            posX = xOffset - widthBar;
            if (posX > 0)
                collapse = false;
        }
        if (collapse)
            posX -= Math.abs(Math.abs(posX - posX1) - widthBar * 2.5);
        if (posX <= 0){
            if (!collapse)
                posX = 0;
            if (Math.abs(posX - posX1) < widthBar * 2.5){
                posX -= Math.abs(posX - posX1) - widthBar * 2;
            collapse = true;           }
        }
        /*if (posX <= 0){
            posX = 0;*/{
            /*if (Math.abs(posX - posX1) < widthBar * 2)
                posX = xOffset - widthBar;*/
        }
        Log.i("Позици даты:", date + "   " + String.valueOf(Math.abs(posX - posX1)) + " X " + posX + " X1 " +posX1 + "    " + xOffset);
        paint.setTextSize(28);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText(date, posX, 50, paint);
        canvas.restore();
            posX+=widthBar;

    }

    public float getPosX(){
        return posX;
    }
}
