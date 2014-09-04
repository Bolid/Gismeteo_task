package com.export.Gismeteo_task;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class HistogramBar {

    private int RED, GREEN, BLUE;

    private String temperature;

    private float widthBar;

    private float posX;

    private float posY;

    public HistogramBar(float posX,float posY, String temperature, float widthBar, HistogramColor color){
        this.RED = color.RED;
        this.GREEN = color.GREEN;
        this.BLUE = color.BLUE;
        this.posX = posX;
        this.posY = posY;
        this.temperature = temperature;
        this.widthBar = widthBar;
        Log.i("ПАРАМЕТРА", posX + "  " + posY + "  " + widthBar);
    }

    public void drawBar(Canvas canvas, Paint paint/*, int posX*/){
        paint.setColor(Color.argb(200, RED, GREEN, BLUE));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(6);

        canvas.drawLine(posX, posY, posX + widthBar, posY, paint);
        canvas.restore();

        drawTemperature(canvas, paint);
    }

    private void drawTemperature(Canvas canvas, Paint paint){
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        paint.setTextSize(24);
        paint.setTextAlign(Paint.Align.CENTER);

        if (Integer.parseInt(temperature) < 0)
            canvas.drawText("-" + temperature, posX + widthBar / 2, posY - 20, paint);
        else if (Integer.parseInt(temperature) > 0)
            canvas.drawText("+" + temperature, posX + widthBar / 2, posY - 20, paint);
        else
            canvas.drawText(temperature, posX + widthBar / 2, posY - 20, paint);
        canvas.restore();
    }
}
