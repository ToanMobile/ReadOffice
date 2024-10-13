package com.app.office.common.picture;

public class Picture {
    public static final byte DIB = 7;
    public static final String DIB_TYPE = "dib";
    public static final byte EMF = 2;
    public static final String EMF_TYPE = "emf";
    public static final byte GIF = 8;
    public static final String GIF_TYPE = "gif";
    public static final byte JPEG = 5;
    public static final String JPEG_TYPE = "jpeg";
    public static final byte PICT = 4;
    public static final String PICT_TYPE = "pict";
    public static final byte PNG = 6;
    public static final String PNG_TYPE = "png";
    public static final byte WMF = 3;
    public static final String WMF_TYPE = "wmf";
    private byte[] data;
    private String tempFilePath;
    private byte type;
    private short zoomX;
    private short zoomY;

    public String getTempFilePath() {
        return this.tempFilePath;
    }

    public void setTempFilePath(String str) {
        this.tempFilePath = str;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] bArr) {
        this.data = bArr;
    }

    public byte getPictureType() {
        return this.type;
    }

    public void setPictureType(byte b) {
        this.type = b;
    }

    public void setPictureType(String str) {
        if (str.equalsIgnoreCase(EMF_TYPE)) {
            this.type = 2;
        } else if (str.equalsIgnoreCase(WMF_TYPE)) {
            this.type = 3;
        } else if (str.equalsIgnoreCase(PICT_TYPE)) {
            this.type = 4;
        } else if (str.equalsIgnoreCase("jpeg")) {
            this.type = 5;
        } else if (str.equalsIgnoreCase("png")) {
            this.type = 6;
        } else if (str.equalsIgnoreCase(DIB_TYPE)) {
            this.type = 7;
        } else if (str.equalsIgnoreCase("gif")) {
            this.type = 8;
        }
    }

    public void dispose() {
        this.tempFilePath = null;
    }

    public short getZoomX() {
        return this.zoomX;
    }

    public void setZoomX(short s) {
        this.zoomX = s;
    }

    public short getZoomY() {
        return this.zoomY;
    }

    public void setZoomY(short s) {
        this.zoomY = s;
    }
}
