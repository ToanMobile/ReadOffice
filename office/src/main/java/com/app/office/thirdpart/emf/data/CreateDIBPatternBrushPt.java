package com.app.office.thirdpart.emf.data;

import android.graphics.Bitmap;
import com.app.office.thirdpart.emf.EMFImageLoader;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class CreateDIBPatternBrushPt extends EMFTag {
    private BitmapInfo bmi;
    /* access modifiers changed from: private */
    public Bitmap image;
    private int index;
    private int usage;

    public CreateDIBPatternBrushPt() {
        super(94, 1);
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        CreateDIBPatternBrushPt createDIBPatternBrushPt = new CreateDIBPatternBrushPt();
        createDIBPatternBrushPt.index = eMFInputStream.readDWORD();
        eMFInputStream.readByte(24);
        createDIBPatternBrushPt.bmi = new BitmapInfo(eMFInputStream);
        createDIBPatternBrushPt.usage = eMFInputStream.readDWORD();
        createDIBPatternBrushPt.image = EMFImageLoader.readImage(createDIBPatternBrushPt.bmi.getHeader(), createDIBPatternBrushPt.bmi.getHeader().getWidth(), createDIBPatternBrushPt.bmi.getHeader().getHeight(), eMFInputStream, (((i2 - 4) - 24) - 40) - 4, (BlendFunction) null);
        return createDIBPatternBrushPt;
    }

    public String toString() {
        return super.toString() + "\n  usage: " + this.usage + "\n" + this.bmi.toString();
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.storeGDIObject(this.index, new GDIObject() {
            public void render(EMFRenderer eMFRenderer) {
                if (CreateDIBPatternBrushPt.this.image != null) {
                    eMFRenderer.setBrushPaint(CreateDIBPatternBrushPt.this.image);
                }
            }
        });
    }
}
