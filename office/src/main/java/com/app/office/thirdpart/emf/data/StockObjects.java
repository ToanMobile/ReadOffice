package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Color;
import com.app.office.simpletext.font.Font;

public class StockObjects {
    private static final int ANSI_FIXED_FONT = 11;
    private static final int ANSI_VAR_FONT = 12;
    private static final int BLACK_BRUSH = 4;
    private static final int BLACK_PEN = 7;
    private static final int DC_BRUSH = 18;
    private static final int DC_PEN = 19;
    private static final int DEFAULT_GUI_FONT = 17;
    private static final int DEFAULT_PALETTE = 15;
    private static final int DEVICE_DEFAULT_FONT = 14;
    private static final int DKGRAY_BRUSH = 3;
    private static final int GRAY_BRUSH = 2;
    private static final int LTGRAY_BRUSH = 1;
    private static final int NULL_BRUSH = 5;
    private static final int NULL_PEN = 8;
    private static final int OEM_FIXED_FONT = 10;
    private static final int SYSTEM_FIXED_FONT = 16;
    private static final int SYSTEM_FONT = 13;
    private static final int WHITE_BRUSH = 0;
    private static final int WHITE_PEN = 6;
    private static final GDIObject[] objects;

    static {
        GDIObject[] gDIObjectArr = new GDIObject[20];
        objects = gDIObjectArr;
        Color color = new Color(0, 0, 0, 0);
        gDIObjectArr[0] = new LogBrush32(0, Color.WHITE, 0);
        gDIObjectArr[1] = new LogBrush32(0, Color.LIGHT_GRAY, 0);
        gDIObjectArr[2] = new LogBrush32(0, Color.GRAY, 0);
        gDIObjectArr[3] = new LogBrush32(0, Color.DARK_GRAY, 0);
        gDIObjectArr[4] = new LogBrush32(0, Color.BLACK, 0);
        gDIObjectArr[5] = new LogBrush32(1, color, 0);
        gDIObjectArr[6] = new LogPen(0, 1, Color.WHITE);
        gDIObjectArr[7] = new LogPen(0, 1, Color.BLACK);
        gDIObjectArr[8] = new LogPen(5, 1, color);
        gDIObjectArr[10] = new LogFontW(new Font("Monospaced", 0, 12));
        gDIObjectArr[11] = gDIObjectArr[10];
        gDIObjectArr[12] = new LogFontW(new Font("SansSerif", 0, 12));
        gDIObjectArr[13] = new LogFontW(new Font("Dialog", 0, 12));
        gDIObjectArr[14] = gDIObjectArr[12];
        gDIObjectArr[16] = gDIObjectArr[10];
        gDIObjectArr[17] = gDIObjectArr[13];
    }

    public static GDIObject getStockObject(int i) {
        if (i < 0) {
            int i2 = i ^ Integer.MIN_VALUE;
            GDIObject[] gDIObjectArr = objects;
            if (i2 < gDIObjectArr.length) {
                GDIObject gDIObject = gDIObjectArr[i2];
                if (gDIObject != null) {
                    return gDIObject;
                }
                throw new UnsupportedOperationException("Stock object not yet supported: " + i2);
            }
            throw new IllegalArgumentException("Stock object is out of bounds: " + i2);
        }
        throw new IllegalArgumentException("Value does not represent a stock object: " + i);
    }
}
