package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;

public final class NotesAtom extends RecordAtom {
    private static long _type = 1009;
    private byte[] _header;
    private boolean followMasterBackground;
    private boolean followMasterObjects;
    private boolean followMasterScheme;
    private byte[] reserved;
    private int slideID;

    public int getSlideID() {
        return this.slideID;
    }

    public void setSlideID(int i) {
        this.slideID = i;
    }

    public boolean getFollowMasterObjects() {
        return this.followMasterObjects;
    }

    public boolean getFollowMasterScheme() {
        return this.followMasterScheme;
    }

    public boolean getFollowMasterBackground() {
        return this.followMasterBackground;
    }

    public void setFollowMasterObjects(boolean z) {
        this.followMasterObjects = z;
    }

    public void setFollowMasterScheme(boolean z) {
        this.followMasterScheme = z;
    }

    public void setFollowMasterBackground(boolean z) {
        this.followMasterBackground = z;
    }

    protected NotesAtom(byte[] bArr, int i, int i2) {
        i2 = i2 < 8 ? 8 : i2;
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this.slideID = LittleEndian.getInt(bArr, i + 8);
        int uShort = LittleEndian.getUShort(bArr, i + 12);
        if ((uShort & 4) == 4) {
            this.followMasterBackground = true;
        } else {
            this.followMasterBackground = false;
        }
        if ((uShort & 2) == 2) {
            this.followMasterScheme = true;
        } else {
            this.followMasterScheme = false;
        }
        if ((uShort & 1) == 1) {
            this.followMasterObjects = true;
        } else {
            this.followMasterObjects = false;
        }
        byte[] bArr3 = new byte[(i2 - 14)];
        this.reserved = bArr3;
        System.arraycopy(bArr, i + 14, bArr3, 0, bArr3.length);
    }

    public long getRecordType() {
        return _type;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        outputStream.write(this._header);
        writeLittleEndian(this.slideID, outputStream);
        short s = this.followMasterObjects ? (short) 1 : 0;
        if (this.followMasterScheme) {
            s = (short) (s + 2);
        }
        if (this.followMasterBackground) {
            s = (short) (s + 4);
        }
        writeLittleEndian(s, outputStream);
        outputStream.write(this.reserved);
    }

    public void dispose() {
        this._header = null;
        this.reserved = null;
    }
}
