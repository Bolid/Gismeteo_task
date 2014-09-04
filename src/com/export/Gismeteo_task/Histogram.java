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

    private final String TAG = "TESTING_PROGRAM";

    private HistogramBar[] histogramBars;

    private HistogramBorder[] histogramBorders;

    private Context context;

    private OverScroller scroller;

    private GestureDetector gDetector;

    private Paint paintForBorder = new Paint();

    private Paint paintForBar = new Paint();


    private int heightScreen, widthScreen;

    private int[] masTemp;

    private float widthBar, stepBar, posZero, xOffset = 0;

    private PositionElementHistogram posElem = new PositionElementHistogram();

    private String orientationScreen = "";

    private boolean startInit  = false;

    public Histogram(Context context, int[] masTemp) {
        super(context);
        this.context = context;
        this.masTemp = masTemp;
        gDetector = new GestureDetector(context, new MyDetectorListener());
        scroller = new OverScroller(context);
        histogramBars = new HistogramBar[24];
        histogramBorders = new HistogramBorder[24];
    }

    private void init(Canvas canvas){

        calculateSizeScreen(canvas);
        getWidthForBar();
        getStepBar();
        calculateZero();
        int posX = 0;

        for (int i = 0; i < 24; i++){
            histogramBars[i] = new HistogramBar(posX, (heightScreen / 2 - ((masTemp[i] - posZero) * stepBar)), String.valueOf(masTemp[i]), widthBar, calculateColor(masTemp[i]));
            posX += widthBar;
            if (i != 24 - 1)
                histogramBorders[i] = new HistogramBorder(posX, (heightScreen / 2 - ((masTemp[i] - posZero) * stepBar)), stepBar, masTemp[i] - masTemp[i + 1], calculateColor(masTemp[i]), calculateColor(masTemp[i + 1]));
        }
        return;

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

       // testColor(canvas);
        if (scroller.computeScrollOffset())
            xOffset = scroller.getCurrX();
        posElem.setStartXBar(-xOffset);
        posElem.setStartYBar(heightScreen / 2 - ((masTemp[0] - posZero) * stepBar));

        /*for (int i = 0; i < 24; i++){
            if (masTemp[i] > 0)
                paintBar(canvas, "+" + masTemp[i]);
            else if (masTemp[i] < 0)
                paintBar(canvas, "-" + masTemp[i]);
            else if (masTemp[i] == 0)
                paintBar(canvas, "0");
            if (i != 24 *//*masTemp.length*//* - 1){
                paintBorder(canvas, masTemp[i], masTemp[i + 1]);
            }
        }*/

        for (int i = 0; i < histogramBars.length; i++){
            histogramBars[i].drawBar(canvas, paintForBar);
            if (i != 24 - 1)
                histogramBorders[i].drawBorder(canvas, paintForBorder);
            canvas.restore();
        }
        canvas.save();
        canvas.translate(-xOffset, 0);
        canvas.restore();
    }

    private void paintBar(Canvas canvas, String temp){
        paintForBar.setStyle(Paint.Style.STROKE);
        paintForBar.setStrokeWidth(8);
        paintForBar.setColor(Color.RED);
        canvas.drawLine(posElem.getStartXBar(), posElem.getStartYBar(), posElem.getStartXBar() + widthBar, posElem.getStartYBar(), paintForBar);
        canvas.restore();

        paintForBar.setStrokeWidth(2);
        paintForBar.setTextSize(24);
        paintForBar.setTextAlign(Paint.Align.CENTER);
        paintForBar.setColor(Color.WHITE);
        canvas.drawText(temp, posElem.getStartXBar() + (widthBar / 2), posElem.getStartYBar() - 15, paintForBar);

        posElem.setStartXBorder(posElem.getStartXBar() + widthBar);
        posElem.setStartYBorder(posElem.getStartYBar());
    }

    private void paintBorder(Canvas canvas, int temp1, int temp2){
        paintForBorder.setStyle(Paint.Style.STROKE);
        paintForBorder.setStrokeWidth(2);

        paintForBorder.setShader(new LinearGradient(posElem.getStartXBorder(), posElem.getStartYBorder(), posElem.getStartXBorder(), posElem.getStartYBorder() + (temp1 - temp2) * stepBar, Color.RED, Color.GREEN, Shader.TileMode.CLAMP));
        //paintForBorder.setColor(Color.GREEN);
        canvas.drawLine(posElem.getStartXBorder(), posElem.getStartYBorder(), posElem.getStartXBorder(), posElem.getStartYBorder() + (temp1 - temp2) * stepBar, paintForBorder);

        canvas.restore();

        posElem.setStartXBar(posElem.getStartXBorder());
        posElem.setStartYBar(posElem.getStartYBorder() + (temp1 - temp2) * stepBar);
    }

    private void calculateSizeScreen(Canvas canvas){

        heightScreen = canvas.getHeight();
        widthScreen = canvas.getWidth();

        Log.i(TAG, "Размеры экрана: " + heightScreen + "  " + widthScreen);
    }

    private void getOrientation(){
        if (heightScreen > widthScreen)
            orientationScreen = "VERTICAL";
        else
            orientationScreen = "HORIZONTAL";
    }

    private void getWidthForBar(){
        if (orientationScreen.equals("VERTICAL"))
            widthBar = heightScreen / 4;
        else
            widthBar = widthScreen / 6;

    }

    private void getStepBar(){
        int[] masMinAndMaxTemp = getMaxAndMinTemp();
        stepBar = Math.abs(heightScreen / 2 / (masMinAndMaxTemp[0] - masMinAndMaxTemp[1] / masTemp.length));
        Log.i(TAG, "Шаг границы = " + stepBar);

    }

    private int[] getMaxAndMinTemp(){
        int maxTemp = masTemp[0];
        int minTemp = masTemp[0];
        for (int aMasTemp : masTemp) {
            if (maxTemp < aMasTemp)
                maxTemp = aMasTemp;
            if (minTemp > aMasTemp)
                minTemp = aMasTemp;
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
            if (Math.abs(xOffset) > 24 * widthBar - getMeasuredWidth())
                xOffset = 24 * widthBar - getMeasuredWidth();
            invalidate();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            scroller.fling((int) xOffset, 0, (int) -velocityX, 0, 0, (int) (24 * widthBar - getMeasuredWidth()), 0, 0);
            return true;
        }
    }

    private void testColor(Canvas canvas){
        int[] masRED = context.getResources().getIntArray(R.array.colorRED_HOT);
        int[] masGREEN = context.getResources().getIntArray(R.array.colorGREEN_HOT);
        int[] masBLUE = context.getResources().getIntArray(R.array.colorBLUE_HOT);

        int[] colorRed = new int[masRED.length];
        int[] colorGreen = new int[masRED.length];
        int[] colorBlue = new int[masRED.length];

        float koef;
        int posX = 20;
        int posY = 20;
        for (int i = 0; i < masRED.length; i++){
            if (i == masRED.length - 1)
                return;
            for(int j = 0; j < masRED.length; j++){
                koef = Float.valueOf(String.valueOf(new BigDecimal(j).divide(BigDecimal.valueOf(10))));
                colorRed[j] = (int) (masRED[i] * (1-koef)+masRED[i+1]*koef);
                colorGreen[j] = (int)(masGREEN[i]*(1-koef)+masGREEN[i+1]*koef);
                colorBlue[j] = (int)(masBLUE[i]*(1-koef)+masBLUE[i+1]*koef);
            }
            for (int k = 0; k < colorRed.length; k++){
                paintPoint(colorRed[k], colorGreen[k], colorBlue[k], canvas, posX, posY);
                posY += 30;
            }
            posY = 20;
            posX += 30;
        }

    }

    private void paintPoint(int RED, int GREEN, int BLUE, Canvas canvas, int posX, int posY){
        Log.i(TAG,  "Цвета: " + RED + " " + GREEN + " " + BLUE);
        paintForBorder.setARGB(100, RED, GREEN, BLUE);
        paintForBorder.setStrokeWidth(20);
        canvas.drawPoint(posX, posY, paintForBorder);
        canvas.restore();
    }

    private HistogramColor calculateColor(int temp){
        if (temp == 19)
            Log.i(TAG, "STOP");
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

        histogramColor.RED = (int) (masRED[indexColor] * (1-koef)+masRED[indexColor+1]*koef);
        histogramColor.GREEN = (int)(masGREEN[indexColor]*(1-koef)+masGREEN[indexColor+1]*koef);
        histogramColor.BLUE = (int)(masBLUE[indexColor]*(1-koef)+masBLUE[indexColor+1]*koef);

        return histogramColor;
    }
}
