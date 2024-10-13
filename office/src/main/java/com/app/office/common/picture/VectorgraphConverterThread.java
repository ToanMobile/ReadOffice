package com.app.office.common.picture;

public class VectorgraphConverterThread extends Thread {
    private PictureConverterMgr converterMgr;
    private String destPath;
    private int picHeight;
    private int picWidth;
    private String sourPath;
    private byte type;

    public VectorgraphConverterThread(PictureConverterMgr pictureConverterMgr, byte b, String str, String str2, int i, int i2) {
        this.converterMgr = pictureConverterMgr;
        this.type = b;
        this.sourPath = str;
        this.destPath = str2;
        this.picWidth = i;
        this.picHeight = i2;
    }

    public void run() {
        this.converterMgr.convertWMF_EMF(this.type, this.sourPath, this.destPath, this.picWidth, this.picHeight, false);
    }
}
