package com.export.Gismeteo_task;


import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;
import android.support.v4.view.ViewCompat;

import java.math.BigDecimal;

public class Histogram extends View {

    private HistogramBar[] histogramBars;

    private HistogramBorder[] histogramBorders;

    private HistogramColor histogramColor = new HistogramColor();

    private Context context;

    private OverScroller scroller;

    private GestureDetector gDetector;

    private Paint paintForBorder = new Paint();

    private Paint paintForBar = new Paint();


    private int heightScreen, widthScreen;

    private int[] masTemp;

    private float widthBar, stepBar, posZero, posX, xOffset = 0;

    private PositionElementHistogram posElem = new PositionElementHistogram();

    private String orientationScreen = "";

    private boolean startInit  = false;

    public Histogram(Context context, int[] masTemp) {
        super(context);
        this.context = context;
        this.masTemp = masTemp;
        gDetector = new GestureDetector(context, new MyDetectorListener());
        scroller = new OverScroller(context);
        histogramBars = new HistogramBar[masTemp.length];
        histogramBorders = new HistogramBorder[masTemp.length];
    }

    private void init(Canvas canvas){

        calculateSizeScreen(canvas);
        getWidthForBar();
        getStepBar();
        calculateZero();
        String time = "1";
        for (int i = 0; i < masTemp.length; i++){
            histogramBars[i] = new HistogramBar((heightScreen / 2 - ((masTemp[i] - posZero) * stepBar)), String.valueOf(masTemp[i]), time, widthBar, calculateColor(masTemp[i]));
            time = String.valueOf(3 + Integer.valueOf(time));
            if (Integer.valueOf(time) > 22)
                time = "1";
            posX += widthBar;
            if (i != masTemp.length - 1)
                histogramBorders[i] = new HistogramBorder((heightScreen / 2 - ((masTemp[i] - posZero) * stepBar)), stepBar, masTemp[i] - masTemp[i + 1]);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (gDetector.onTouchEvent(event)) return true;
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        boolean needsInvalidate = false;
        if (scroller.computeScrollOffset()) {
            xOffset = scroller.getCurrX();
        }

        if (!scroller.isFinished()) {
            needsInvalidate = true;
        }

        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if (!startInit){
            init(canvas);
            startInit = true;
        }
        if (scroller.computeScrollOffset())
            xOffset = scroller.getCurrX();
        posX = -xOffset;

        for (int i = 0; i < histogramBars.length; i++){
            histogramBars[i].drawBar(canvas, paintForBar, posX);
            posX += widthBar;
            if (i != masTemp.length - 1){
                histogramBorders[i].setColorStart(histogramBars[i].getColorBar(histogramColor));
                histogramBorders[i].setColorStop(histogramBars[i + 1].getColorBar(histogramColor));
                histogramBorders[i].drawBorder(canvas, paintForBorder, posX);
            }
            canvas.restore();
        }
        canvas.save();
        canvas.translate(-xOffset, 0);
        canvas.restore();
    }

    private void calculateSizeScreen(Canvas canvas){
        heightScreen = canvas.getHeight();
        widthScreen = canvas.getWidth();
    }

    private void getWidthForBar(){
        if (orientationScreen.equals("VERTICAL"))
            widthBar = heightScreen / 4;
        else
            widthBar = widthScreen / 6;

    }

    private void getStepBar(){
        int[] masMinAndMaxTemp = getMaxAndMinTemp();
        float t =  ((masMinAndMaxTemp[0] - masMinAndMaxTemp[1]));
        stepBar = Math.abs(heightScreen / 2 / ((masMinAndMaxTemp[0] - masMinAndMaxTemp[1])))/2;

    }

    private int[] getMaxAndMinTemp(){
        int maxTemp = Math.abs(masTemp[0]);
        int minTemp = Math.abs(masTemp[0]);
        for (int aMasTemp : masTemp) {
            if (maxTemp < Math.abs(aMasTemp))
                maxTemp = Math.abs(aMasTemp);
            if (minTemp > Math.abs(aMasTemp))
                minTemp = Math.abs(aMasTemp);
        }

        return new int[]{maxTemp, minTemp};

    }

    private void calculateZero(){
        float temp = 0;
        for (int aMasTemp : masTemp){
            temp = temp + aMasTemp;
        }
        posZero =  temp / masTemp.length;
    }


    public class MyDetectorListener extends GestureDetector.SimpleOnGestureListener {

        public boolean onDown(MotionEvent e) {
            scroller.forceFinished(true);
            invalidate();

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY) {
            xOffset += distanceX;
            if (xOffset < 0)
                xOffset = 0;
            if (Math.abs(xOffset) > masTemp.length * widthBar - getMeasuredWidth())
                xOffset = masTemp.length * widthBar - getMeasuredWidth();
            invalidate();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            scroller.fling((int) xOffset, 0, (int) -velocityX, 0, 0, (int) (masTemp.length * widthBar - getMeasuredWidth()), 0, 0);
            return true;
        }
    }

    private HistogramColor calculateColor(int temp){
        HistogramColor histogramColor = new HistogramColor();
        int[] masRED, masGREEN, masBLUE;
        if (temp > 0){
            masRED = context.getResources().getIntArray(R.array.colorRED_HOT);
            masGREEN = context.getResources().getIntArray(R.array.colorGREEN_HOT);
            masBLUE = context.getResources().getIntArray(R.array.colorBLUE_HOT);
        }
        else{
            masRED = context.getResources().getIntArray(R.array.colorRED_COOL);
            masGREEN = context.getResources().getIntArray(R.array.colorGREEN_COOL);
            masBLUE = context.getResources().getIntArray(R.array.colorBLUE_COOL);
        }
        float koef = Math.abs(Float.valueOf(String.valueOf(new BigDecimal(temp % 10).divide(BigDecimal.valueOf(10)))));

        int indexColor = (int) Math.abs(Math.floor(temp / 10));
        if (koef == 0)
            histogramColor.setColor(masRED[indexColor], masGREEN[indexColor], masBLUE[indexColor]);
        else
            histogramColor.setColor((int) (masRED[indexColor] * (1-koef)+masRED[indexColor+1]*koef), (int)(masGREEN[indexColor]*(1-koef)+masGREEN[indexColor+1]*koef), (int)(masBLUE[indexColor]*(1-koef)+masBLUE[indexColor+1]*koef));

        return histogramColor;
    }
}
