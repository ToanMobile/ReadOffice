package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;

public final class DocumentAtom extends RecordAtom {
    private static long _type = 1001;
    private byte[] _header;
    private int firstSlideNum;
    private long handoutMasterPersist;
    private long notesMasterPersist;
    private long notesSizeX;
    private long notesSizeY;
    private byte omitTitlePlace;
    private byte[] reserved;
    private byte rightToLeft;
    private byte saveWithFonts;
    private long serverZoomFrom;
    private long serverZoomTo;
    private byte showComments;
    private int slideSizeType;
    private long slideSizeX;
    private long slideSizeY;

    public static final class SlideSize {
        public static final int A4_SIZED_PAPER = 2;
        public static final int BANNER = 5;
        public static final int CUSTOM = 6;
        public static final int LETTER_SIZED_PAPER = 1;
        public static final int ON_35MM = 3;
        public static final int ON_SCREEN = 0;
        public static final int OVERHEAD = 4;
    }

    public long getSlideSizeX() {
        return this.slideSizeX;
    }

    public long getSlideSizeY() {
        return this.slideSizeY;
    }

    public long getNotesSizeX() {
        return this.notesSizeX;
    }

    public long getNotesSizeY() {
        return this.notesSizeY;
    }

    public void setSlideSizeX(long j) {
        this.slideSizeX = j;
    }

    public void setSlideSizeY(long j) {
        this.slideSizeY = j;
    }

    public void setNotesSizeX(long j) {
        this.notesSizeX = j;
    }

    public void setNotesSizeY(long j) {
        this.notesSizeY = j;
    }

    public long getServerZoomFrom() {
        return this.serverZoomFrom;
    }

    public long getServerZoomTo() {
        return this.serverZoomTo;
    }

    public void setServerZoomFrom(long j) {
        this.serverZoomFrom = j;
    }

    public void setServerZoomTo(long j) {
        this.serverZoomTo = j;
    }

    public long getNotesMasterPersist() {
        return this.notesMasterPersist;
    }

    public long getHandoutMasterPersist() {
        return this.handoutMasterPersist;
    }

    public int getFirstSlideNum() {
        return this.firstSlideNum;
    }

    public int getSlideSizeType() {
        return this.slideSizeType;
    }

    public boolean getSaveWithFonts() {
        return this.saveWithFonts != 0;
    }

    public boolean getOmitTitlePlace() {
        return this.omitTitlePlace != 0;
    }

    public boolean getRightToLeft() {
        return this.rightToLeft != 0;
    }

    public boolean getShowComments() {
        return this.showComments != 0;
    }

    protected DocumentAtom(byte[] bArr, int i, int i2) {
        i2 = i2 < 48 ? 48 : i2;
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this.slideSizeX = (long) LittleEndian.getInt(bArr, i + 0 + 8);
        this.slideSizeY = (long) LittleEndian.getInt(bArr, i + 4 + 8);
        this.notesSizeX = (long) LittleEndian.getInt(bArr, i + 8 + 8);
        this.notesSizeY = (long) LittleEndian.getInt(bArr, i + 12 + 8);
        this.serverZoomFrom = (long) LittleEndian.getInt(bArr, i + 16 + 8);
        this.serverZoomTo = (long) LittleEndian.getInt(bArr, i + 20 + 8);
        this.notesMasterPersist = (long) LittleEndian.getInt(bArr, i + 24 + 8);
        this.handoutMasterPersist = (long) LittleEndian.getInt(bArr, i + 28 + 8);
        this.firstSlideNum = LittleEndian.getShort(bArr, i + 32 + 8);
        this.slideSizeType = LittleEndian.getShort(bArr, i + 34 + 8);
        this.saveWithFonts = bArr[i + 36 + 8];
        this.omitTitlePlace = bArr[i + 37 + 8];
        this.rightToLeft = bArr[i + 38 + 8];
        this.showComments = bArr[i + 39 + 8];
        byte[] bArr3 = new byte[((i2 - 40) - 8)];
        this.reserved = bArr3;
        System.arraycopy(bArr, i + 48, bArr3, 0, bArr3.length);
    }

    public long getRecordType() {
        return _type;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        outputStream.write(this._header);
        writeLittleEndian((int) this.slideSizeX, outputStream);
        writeLittleEndian((int) this.slideSizeY, outputStream);
        writeLittleEndian((int) this.notesSizeX, outputStream);
        writeLittleEndian((int) this.notesSizeY, outputStream);
        writeLittleEndian((int) this.serverZoomFrom, outputStream);
        writeLittleEndian((int) this.serverZoomTo, outputStream);
        writeLittleEndian((int) this.notesMasterPersist, outputStream);
        writeLittleEndian((int) this.handoutMasterPersist, outputStream);
        writeLittleEndian((short) this.firstSlideNum, outputStream);
        writeLittleEndian((short) this.slideSizeType, outputStream);
        outputStream.write(this.saveWithFonts);
        outputStream.write(this.omitTitlePlace);
        outputStream.write(this.rightToLeft);
        outputStream.write(this.showComments);
        outputStream.write(this.reserved);
    }

    public void dispose() {
        this._header = null;
        this.reserved = null;
    }
}
