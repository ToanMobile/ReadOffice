package com.app.office.thirdpart.emf.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.app.office.thirdpart.emf.EMFHeader;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class EMFUtil {
    public static Bitmap convert(String str, String str2, int i, int i2) throws Exception {
        Bitmap bitmap;
        Canvas canvas;
        EMFInputStream eMFInputStream = new EMFInputStream((InputStream) new FileInputStream(str), EMFInputStream.DEFAULT_VERSION);
        EMFHeader readHeader = eMFInputStream.readHeader();
        int i3 = readHeader.getDevice().width;
        int i4 = readHeader.getDevice().height;
        int width = (int) readHeader.getMillimeters().getWidth();
        int height = (int) readHeader.getMillimeters().getHeight();
        int width2 = (((((int) readHeader.getFrame().getWidth()) * i3) / width) / 100) + 1;
        int height2 = (((((int) readHeader.getFrame().getHeight()) * i4) / height) / 100) + 1;
        int i5 = ((readHeader.getFrame().x * i3) / width) / 100;
        int i6 = ((readHeader.getFrame().y * i4) / height) / 100;
        EMFRenderer eMFRenderer = new EMFRenderer(eMFInputStream);
        if (i * i2 < width2 * height2) {
            bitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            canvas.scale(((float) i) / ((float) width2), ((float) i2) / ((float) height2));
        } else {
            bitmap = Bitmap.createBitmap(width2, height2, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
        }
        canvas.translate((float) (-i5), (float) (-i6));
        eMFRenderer.paint(canvas);
        FileOutputStream fileOutputStream = new FileOutputStream(str2);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        fileOutputStream.close();
        return bitmap;
    }
}
