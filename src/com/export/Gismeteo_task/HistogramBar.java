package com.export.Gismeteo_task;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class HistogramBar {

    private int RED, GREEN, BLUE;

    private String temperature;

    private String time;

    private float widthBar;

    private float posX;

    private float posY;

    public HistogramBar(float posY, String temperature, String time, float widthBar, HistogramColor color){
        this.RED = color.getRED();
        this.GREEN = color.getGREEN();
        this.BLUE = color.getBLUE();
        this.posY = posY;
        this.temperature = temperature;
        if (time.length() < 2)
            this.time = "0" + time;
        else
            this.time = time;
        this.widthBar = widthBar;
    }

    public void drawBar(Canvas canvas, Paint paint, float posX){
        this.posX = posX;

        paint.setColor(Color.argb(200, RED, GREEN, BLUE));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(6);

        canvas.drawLine(posX, posY, posX + widthBar, posY, paint);
        canvas.restore();

        drawTemperature(canvas, paint);
        drawTime(canvas, paint);
        posX+=widthBar;
    }

    private void drawTemperature(Canvas canvas, Paint paint){
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3);
        paint.setTextSize(28);
        paint.setTextAlign(Paint.Align.CENTER);

        if (Integer.parseInt(temperature) < 0)
            canvas.drawText("-" + temperature, posX + widthBar / 2, posY - 20, paint);
        else if (Integer.parseInt(temperature) > 0)
            canvas.drawText("+" + temperature, posX + widthBar / 2, posY - 20, paint);
        else
            canvas.drawText(temperature, posX + widthBar / 2, posY - 20, paint);
        canvas.restore();
    }

    private void drawTime(Canvas canvas, Paint paint){
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(2);
        paint.setTextSize(32);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(time, posX + widthBar / 2, 100, paint);

        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(2);
        paint.setTextSize(16);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("00", posX + widthBar / 2 + 34, 90, paint);
        canvas.restore();
    }

    public HistogramColor getColorBar(HistogramColor histogramColor){
        histogramColor.setColor(RED, GREEN, BLUE);
        return histogramColor;
    }
}
