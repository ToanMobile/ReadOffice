package com.app.office.common.picture;

public class PictureConverterThread extends Thread {
    private PictureConverterMgr converterMgr;
    private String destPath;
    private String sourPath;
    private String type;

    public PictureConverterThread(PictureConverterMgr pictureConverterMgr, String str, String str2, String str3) {
        this.converterMgr = pictureConverterMgr;
        this.type = str3;
        this.sourPath = str;
        this.destPath = str2;
    }

    public void run() {
        this.converterMgr.convertPNG(this.sourPath, this.destPath, this.type, false);
    }
}
