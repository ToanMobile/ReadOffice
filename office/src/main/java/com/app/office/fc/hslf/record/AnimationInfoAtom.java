package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;

public final class AnimationInfoAtom extends RecordAtom {
    public static final int AnimateBg = 16384;
    public static final int Automatic = 4;
    public static final int Hide = 4096;
    public static final int Play = 256;
    public static final int Reverse = 1;
    public static final int Sound = 16;
    public static final int StopSound = 64;
    public static final int Synchronous = 1024;
    private byte[] _header;
    private byte[] _recdata;

    protected AnimationInfoAtom() {
        this._recdata = new byte[28];
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putShort(bArr, 0, 1);
        LittleEndian.putShort(this._header, 2, (short) ((int) getRecordType()));
        LittleEndian.putInt(this._header, 4, this._recdata.length);
    }

    protected AnimationInfoAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i2 - 8;
        byte[] bArr3 = new byte[i3];
        this._recdata = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, i3);
    }

    public long getRecordType() {
        return (long) RecordTypes.AnimationInfoAtom.typeID;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        outputStream.write(this._header);
        outputStream.write(this._recdata);
    }

    public int getDimColor() {
        return LittleEndian.getInt(this._recdata, 0);
    }

    public void setDimColor(int i) {
        LittleEndian.putInt(this._recdata, 0, i);
    }

    public int getMask() {
        return LittleEndian.getInt(this._recdata, 4);
    }

    public void setMask(int i) {
        LittleEndian.putInt(this._recdata, 4, i);
    }

    public boolean getFlag(int i) {
        return (i & getMask()) != 0;
    }

    public void setFlag(int i, boolean z) {
        int mask = getMask();
        setMask(z ? i | mask : (~i) & mask);
    }

    public int getSoundIdRef() {
        return LittleEndian.getInt(this._recdata, 8);
    }

    public void setSoundIdRef(int i) {
        LittleEndian.putInt(this._recdata, 8, i);
    }

    public int getDelayTime() {
        return LittleEndian.getInt(this._recdata, 12);
    }

    public void setDelayTime(int i) {
        LittleEndian.putInt(this._recdata, 12, i);
    }

    public int getOrderID() {
        return LittleEndian.getInt(this._recdata, 16);
    }

    public void setOrderID(int i) {
        LittleEndian.putInt(this._recdata, 16, i);
    }

    public int getSlideCount() {
        return LittleEndian.getInt(this._recdata, 18);
    }

    public void setSlideCount(int i) {
        LittleEndian.putInt(this._recdata, 18, i);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("AnimationInfoAtom\n");
        stringBuffer.append("\tDimColor: " + getDimColor() + "\n");
        int mask = getMask();
        stringBuffer.append("\tMask: " + mask + ", 0x" + Integer.toHexString(mask) + "\n");
        StringBuilder sb = new StringBuilder();
        sb.append("\t  Reverse: ");
        sb.append(getFlag(1));
        sb.append("\n");
        stringBuffer.append(sb.toString());
        stringBuffer.append("\t  Automatic: " + getFlag(4) + "\n");
        stringBuffer.append("\t  Sound: " + getFlag(16) + "\n");
        stringBuffer.append("\t  StopSound: " + getFlag(64) + "\n");
        stringBuffer.append("\t  Play: " + getFlag(256) + "\n");
        stringBuffer.append("\t  Synchronous: " + getFlag(1024) + "\n");
        stringBuffer.append("\t  Hide: " + getFlag(4096) + "\n");
        stringBuffer.append("\t  AnimateBg: " + getFlag(16384) + "\n");
        stringBuffer.append("\tSoundIdRef: " + getSoundIdRef() + "\n");
        stringBuffer.append("\tDelayTime: " + getDelayTime() + "\n");
        stringBuffer.append("\tOrderID: " + getOrderID() + "\n");
        stringBuffer.append("\tSlideCount: " + getSlideCount() + "\n");
        return stringBuffer.toString();
    }

    public void dispose() {
        this._header = null;
        this._recdata = null;
    }
}
