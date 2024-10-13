package com.app.office.fc.hslf.record;

import androidx.core.view.ViewCompat;
import com.app.office.fc.util.LittleEndian;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class ColorSchemeAtom extends RecordAtom {
    private static long _type = 2032;
    private byte[] _header;
    private int accentAndFollowingHyperlinkColourRGB;
    private int accentAndHyperlinkColourRGB;
    private int accentColourRGB;
    private int backgroundColourRGB;
    private int fillsColourRGB;
    private int shadowsColourRGB;
    private int textAndLinesColourRGB;
    private int titleTextColourRGB;

    public int getBackgroundColourRGB() {
        return this.backgroundColourRGB;
    }

    public void setBackgroundColourRGB(int i) {
        this.backgroundColourRGB = i;
    }

    public int getTextAndLinesColourRGB() {
        return this.textAndLinesColourRGB;
    }

    public void setTextAndLinesColourRGB(int i) {
        this.textAndLinesColourRGB = i;
    }

    public int getShadowsColourRGB() {
        return this.shadowsColourRGB;
    }

    public void setShadowsColourRGB(int i) {
        this.shadowsColourRGB = i;
    }

    public int getTitleTextColourRGB() {
        return this.titleTextColourRGB;
    }

    public void setTitleTextColourRGB(int i) {
        this.titleTextColourRGB = i;
    }

    public int getFillsColourRGB() {
        return this.fillsColourRGB;
    }

    public void setFillsColourRGB(int i) {
        this.fillsColourRGB = i;
    }

    public int getAccentColourRGB() {
        return this.accentColourRGB;
    }

    public void setAccentColourRGB(int i) {
        this.accentColourRGB = i;
    }

    public int getAccentAndHyperlinkColourRGB() {
        return this.accentAndHyperlinkColourRGB;
    }

    public void setAccentAndHyperlinkColourRGB(int i) {
        this.accentAndHyperlinkColourRGB = i;
    }

    public int getAccentAndFollowingHyperlinkColourRGB() {
        return this.accentAndFollowingHyperlinkColourRGB;
    }

    public void setAccentAndFollowingHyperlinkColourRGB(int i) {
        this.accentAndFollowingHyperlinkColourRGB = i;
    }

    protected ColorSchemeAtom(byte[] bArr, int i, int i2) {
        if (i2 >= 40 || bArr.length - i >= 40) {
            byte[] bArr2 = new byte[8];
            this._header = bArr2;
            System.arraycopy(bArr, i, bArr2, 0, 8);
            int i3 = i + 8;
            this.backgroundColourRGB = LittleEndian.getInt(bArr, i3 + 0);
            this.textAndLinesColourRGB = LittleEndian.getInt(bArr, i3 + 4);
            this.shadowsColourRGB = LittleEndian.getInt(bArr, i3 + 8);
            this.titleTextColourRGB = LittleEndian.getInt(bArr, i3 + 12);
            this.fillsColourRGB = LittleEndian.getInt(bArr, i3 + 16);
            this.accentColourRGB = LittleEndian.getInt(bArr, i3 + 20);
            this.accentAndHyperlinkColourRGB = LittleEndian.getInt(bArr, i3 + 24);
            this.accentAndFollowingHyperlinkColourRGB = LittleEndian.getInt(bArr, i3 + 28);
            return;
        }
        throw new RuntimeException("Not enough data to form a ColorSchemeAtom (always 40 bytes long) - found " + (bArr.length - i));
    }

    public ColorSchemeAtom() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putUShort(bArr, 0, 16);
        LittleEndian.putUShort(this._header, 2, (int) _type);
        LittleEndian.putInt(this._header, 4, 32);
        this.backgroundColourRGB = ViewCompat.MEASURED_SIZE_MASK;
        this.textAndLinesColourRGB = 0;
        this.shadowsColourRGB = 8421504;
        this.titleTextColourRGB = 0;
        this.fillsColourRGB = 10079232;
        this.accentColourRGB = 13382451;
        this.accentAndHyperlinkColourRGB = 16764108;
        this.accentAndFollowingHyperlinkColourRGB = 11711154;
    }

    public long getRecordType() {
        return _type;
    }

    public static byte[] splitRGB(int i) {
        byte[] bArr = new byte[3];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            writeLittleEndian(i, (OutputStream) byteArrayOutputStream);
            System.arraycopy(byteArrayOutputStream.toByteArray(), 0, bArr, 0, 3);
            return bArr;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int joinRGB(byte b, byte b2, byte b3) {
        return joinRGB(new byte[]{b, b2, b3});
    }

    public static int joinRGB(byte[] bArr) {
        if (bArr.length == 3) {
            byte[] bArr2 = new byte[4];
            System.arraycopy(bArr, 0, bArr2, 0, 3);
            bArr2[3] = 0;
            return LittleEndian.getInt(bArr2, 0);
        }
        throw new RuntimeException("joinRGB accepts a byte array of 3 values, but got one of " + bArr.length + " values!");
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        outputStream.write(this._header);
        writeLittleEndian(this.backgroundColourRGB, outputStream);
        writeLittleEndian(this.textAndLinesColourRGB, outputStream);
        writeLittleEndian(this.shadowsColourRGB, outputStream);
        writeLittleEndian(this.titleTextColourRGB, outputStream);
        writeLittleEndian(this.fillsColourRGB, outputStream);
        writeLittleEndian(this.accentColourRGB, outputStream);
        writeLittleEndian(this.accentAndHyperlinkColourRGB, outputStream);
        writeLittleEndian(this.accentAndFollowingHyperlinkColourRGB, outputStream);
    }

    public int getColor(int i) {
        return new int[]{this.backgroundColourRGB, this.textAndLinesColourRGB, this.shadowsColourRGB, this.titleTextColourRGB, this.fillsColourRGB, this.accentColourRGB, this.accentAndHyperlinkColourRGB, this.accentAndFollowingHyperlinkColourRGB}[i];
    }

    public void dispose() {
        this._header = null;
    }
}
