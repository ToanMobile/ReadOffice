package com.app.office.fc.hslf.usermodel;

import com.app.office.fc.hslf.blip.DIB;
import com.app.office.fc.hslf.blip.EMF;
import com.app.office.fc.hslf.blip.ImagePainter;
import com.app.office.fc.hslf.blip.JPEG;
import com.app.office.fc.hslf.blip.PICT;
import com.app.office.fc.hslf.blip.PNG;
import com.app.office.fc.hslf.blip.WMF;
import com.app.office.fc.hslf.exceptions.HSLFException;
import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class PictureData {
    protected static final int CHECKSUM_SIZE = 16;
    protected static ImagePainter[] painters = new ImagePainter[8];
    protected int offset;
    protected byte[] rawdata;
    private String tempFilePath;

    public abstract byte[] getData();

    /* access modifiers changed from: protected */
    public abstract int getSignature();

    public abstract int getType();

    public byte[] getUID() {
        return new byte[16];
    }

    public abstract void setData(byte[] bArr) throws IOException;

    public byte[] getRawData() {
        return this.rawdata;
    }

    public void setRawData(byte[] bArr) {
        this.rawdata = bArr;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int i) {
        this.offset = i;
    }

    public static byte[] getChecksum(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return instance.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new HSLFException(e.getMessage());
        }
    }

    public static PictureData create(int i) {
        switch (i) {
            case 2:
                return new EMF();
            case 3:
                return new WMF();
            case 4:
                return new PICT();
            case 5:
                return new JPEG();
            case 6:
                return new PNG();
            case 7:
                return new DIB();
            default:
                throw new IllegalArgumentException("Unsupported picture type: " + i);
        }
    }

    public byte[] getHeader() {
        byte[] bArr = new byte[24];
        LittleEndian.putInt(bArr, 0, getSignature());
        return bArr;
    }

    public int getSize() {
        return getData().length;
    }

    public String getTempFilePath() {
        return this.tempFilePath;
    }

    public void setTempFilePath(String str) {
        this.tempFilePath = str;
    }

    public void dispose() {
        this.tempFilePath = null;
    }
}
