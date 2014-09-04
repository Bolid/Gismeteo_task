package com.export.Gismeteo_task;


public class HistogramColor {
    public int RED, GREEN, BLUE;
    public static int convertColorRGBtoHEX(int RED, int GREEN, int BLUE){
        String hexStr = "";

        if (Integer.toHexString(RED).length() < 2)
            hexStr += "0" + Integer.toHexString(RED);
        else
            hexStr += Integer.toHexString(RED);

        if (Integer.toHexString(GREEN).length() < 2)
            hexStr += "0" + Integer.toHexString(BLUE);
        else
            hexStr += Integer.toHexString(GREEN);

        if (Integer.toHexString(BLUE).length() < 2)
            hexStr += "0" + Integer.toHexString(BLUE);
        else
            hexStr += Integer.toHexString(BLUE);

        return Integer.parseInt(hexStr, 16);
    }
}
