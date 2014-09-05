package com.export.Gismeteo_task;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class HistogramDate {

    private String date;

    private float posXDefault;

    private float widthBar;

    private float posX, posXX=0, posXXX = 0, posStop1,  posStart, posStop;

    boolean collapse = false;

    public HistogramDate(String date, float posX, float posStart, float posStop, float widthBar){
        this.date = date;
        this.posX = posX;
        this.posXDefault = posX;
        this.widthBar = widthBar;
        this.posStart = posStart;
        this.posStop = posStop;
    }

    public void drawDate(Canvas canvas, Paint paint, float posX1, float posX2, float xOffset){
        int heightScreen = canvas.getWidth();
        posStop -= (posXX - posXXX);

        posXX = posXXX;
        posXXX = xOffset - widthBar;
        Log.i("Позици даты:", date + "   " + posStop + "   " + posXX + "   " + posXXX + "   " + xOffset + "   " + posStop +"   " + posStop1);
        if (posX < heightScreen && posX > 1)
            posX = posX * posStop / posStop1;
        else if (posX < 1)
            posX = xOffset - widthBar;
        else{
            posX = xOffset - widthBar;
        }
        posStop1 = posStop;







       /* if (posX != 0 & !collapse){
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
        }*/
        /*if (posX <= 0){
            posX = 0;{
            if (Math.abs(posX - posX1) < widthBar * 2)
                posX = xOffset - widthBar;
        }*/
        paint.setTextSize(28);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText(date, posX, 50, paint);
        canvas.restore();
            //posX+=widthBar;

    }

    public float getPosX(){
        return posX;
    }
}