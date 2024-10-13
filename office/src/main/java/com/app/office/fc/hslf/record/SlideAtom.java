package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;

public final class SlideAtom extends RecordAtom {
    public static final int MASTER_SLIDE_ID = 0;
    public static final int USES_MASTER_SLIDE_ID = Integer.MIN_VALUE;
    private static long _type = 1007;
    private byte[] _header;
    private boolean followMasterBackground;
    private boolean followMasterObjects;
    private boolean followMasterScheme;
    private SSlideLayoutAtom layoutAtom;
    private int masterID;
    private int notesID;
    private byte[] reserved;

    public int getMasterID() {
        return this.masterID;
    }

    public void setMasterID(int i) {
        this.masterID = i;
    }

    public int getNotesID() {
        return this.notesID;
    }

    public SSlideLayoutAtom getSSlideLayoutAtom() {
        return this.layoutAtom;
    }

    public void setNotesID(int i) {
        this.notesID = i;
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

    protected SlideAtom(byte[] bArr, int i, int i2) {
        i2 = i2 < 30 ? 30 : i2;
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        byte[] bArr3 = new byte[12];
        System.arraycopy(bArr, i + 8, bArr3, 0, 12);
        this.layoutAtom = new SSlideLayoutAtom(bArr3);
        this.masterID = LittleEndian.getInt(bArr, i + 12 + 8);
        this.notesID = LittleEndian.getInt(bArr, i + 16 + 8);
        int uShort = LittleEndian.getUShort(bArr, i + 20 + 8);
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
        byte[] bArr4 = new byte[(i2 - 30)];
        this.reserved = bArr4;
        System.arraycopy(bArr, i + 30, bArr4, 0, bArr4.length);
    }

    public SlideAtom() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putUShort(bArr, 0, 2);
        LittleEndian.putUShort(this._header, 2, (int) _type);
        LittleEndian.putInt(this._header, 4, 24);
        SSlideLayoutAtom sSlideLayoutAtom = new SSlideLayoutAtom(new byte[12]);
        this.layoutAtom = sSlideLayoutAtom;
        sSlideLayoutAtom.setGeometryType(16);
        this.followMasterObjects = true;
        this.followMasterScheme = true;
        this.followMasterBackground = true;
        this.masterID = Integer.MIN_VALUE;
        this.notesID = 0;
        this.reserved = new byte[2];
    }

    public long getRecordType() {
        return _type;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        outputStream.write(this._header);
        this.layoutAtom.writeOut(outputStream);
        writeLittleEndian(this.masterID, outputStream);
        writeLittleEndian(this.notesID, outputStream);
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

    public class SSlideLayoutAtom {
        public static final int BIG_OBJECT = 15;
        public static final int BLANK_SLIDE = 16;
        public static final int FOUR_OBJECTS = 14;
        public static final int HANDOUT = 6;
        public static final int MASTER_NOTES = 4;
        public static final int MASTER_SLIDE = 3;
        public static final int NOTES_TITLE_BODY = 5;
        public static final int TITLE_2_COLUMN_BODY = 8;
        public static final int TITLE_2_COLUNM_LEFT_2_ROW_BODY = 11;
        public static final int TITLE_2_COLUNM_RIGHT_2_ROW_BODY = 10;
        public static final int TITLE_2_ROW_BODY = 9;
        public static final int TITLE_2_ROW_BOTTOM_2_COLUMN_BODY = 12;
        public static final int TITLE_2_ROW_TOP_2_COLUMN_BODY = 13;
        public static final int TITLE_BODY_SLIDE = 1;
        public static final int TITLE_MASTER_SLIDE = 2;
        public static final int TITLE_ONLY = 7;
        public static final int TITLE_SLIDE = 0;
        public static final int VERTICAL_TITLE_2_ROW_BODY_LEFT = 17;
        public static final int VERTICAL_TITLE_BODY_LEFT = 17;
        private int geometry;
        private byte[] placeholderIDs;

        public int getGeometryType() {
            return this.geometry;
        }

        public void setGeometryType(int i) {
            this.geometry = i;
        }

        public SSlideLayoutAtom(byte[] bArr) {
            if (bArr.length == 12) {
                this.geometry = LittleEndian.getInt(bArr, 0);
                byte[] bArr2 = new byte[8];
                this.placeholderIDs = bArr2;
                System.arraycopy(bArr, 4, bArr2, 0, 8);
                return;
            }
            throw new RuntimeException("SSlideLayoutAtom created with byte array not 12 bytes long - was " + bArr.length + " bytes in size");
        }

        public void writeOut(OutputStream outputStream) throws IOException {
            Record.writeLittleEndian(this.geometry, outputStream);
            outputStream.write(this.placeholderIDs);
        }

        public void dispose() {
            this.placeholderIDs = null;
        }
    }

    public void dispose() {
        this._header = null;
        SSlideLayoutAtom sSlideLayoutAtom = this.layoutAtom;
        if (sSlideLayoutAtom != null) {
            sSlideLayoutAtom.dispose();
            this.layoutAtom = null;
        }
        this.reserved = null;
    }
}
