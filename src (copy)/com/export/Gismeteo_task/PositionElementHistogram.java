package com.export.Gismeteo_task;


public class PositionElementHistogram {

    private float startXBorder;
    private float startYBorder;

    private float startXBar;
    private float startYBar;

    public void setStartXBar(float value){
        startXBar = value;
    }

    public void setStartYBar(float value){
        startYBar = value;
    }

    public void setStartXBorder(float value){
        startXBorder = value;
    }

    public void setStartYBorder(float value){
        startYBorder = value;
    }

    public float getStartXBar(){
        return startXBar;
    }

    public float getStartYBar(){
        return startYBar;
    }

    public float getStartXBorder(){
        return startXBorder;
    }

    public float getStartYBorder(){
        return startYBorder;
    }
}
