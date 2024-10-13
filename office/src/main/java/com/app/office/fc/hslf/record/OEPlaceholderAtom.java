package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;

public final class OEPlaceholderAtom extends RecordAtom {
    public static final byte Body = 14;
    public static final byte CenteredTitle = 15;
    public static final byte ClipArt = 22;
    public static final byte Graph = 20;
    public static final byte MasterBody = 2;
    public static final byte MasterCenteredTitle = 3;
    public static final byte MasterDate = 7;
    public static final byte MasterFooter = 9;
    public static final byte MasterHeader = 10;
    public static final byte MasterNotesBody = 6;
    public static final byte MasterNotesSlideImage = 5;
    public static final byte MasterSlideNumber = 8;
    public static final byte MasterSubTitle = 4;
    public static final byte MasterTitle = 1;
    public static final byte MediaClip = 24;
    public static final byte None = 0;
    public static final byte NotesBody = 12;
    public static final byte NotesSlideImage = 11;
    public static final byte Object = 19;
    public static final byte OrganizationChart = 23;
    public static final int PLACEHOLDER_FULLSIZE = 0;
    public static final int PLACEHOLDER_HALFSIZE = 1;
    public static final int PLACEHOLDER_QUARTSIZE = 2;
    public static final byte Subtitle = 16;
    public static final byte Table = 21;
    public static final byte Title = 13;
    public static final byte VerticalTextBody = 18;
    public static final byte VerticalTextTitle = 17;
    private byte[] _header;
    private int placeholderId;
    private int placeholderSize;
    private int placementId;

    public OEPlaceholderAtom() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putUShort(bArr, 0, 0);
        LittleEndian.putUShort(this._header, 2, (int) getRecordType());
        LittleEndian.putInt(this._header, 4, 8);
        this.placementId = 0;
        this.placeholderId = 0;
        this.placeholderSize = 0;
    }

    protected OEPlaceholderAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int length = i + this._header.length;
        this.placementId = LittleEndian.getInt(bArr, length);
        int i3 = length + 4;
        this.placeholderId = LittleEndian.getUnsignedByte(bArr, i3);
        this.placeholderSize = LittleEndian.getUnsignedByte(bArr, i3 + 1);
    }

    public long getRecordType() {
        return (long) RecordTypes.OEPlaceholderAtom.typeID;
    }

    public int getPlacementId() {
        return this.placementId;
    }

    public void setPlacementId(int i) {
        this.placementId = i;
    }

    public int getPlaceholderId() {
        return this.placeholderId;
    }

    public void setPlaceholderId(byte b) {
        this.placeholderId = b;
    }

    public int getPlaceholderSize() {
        return this.placeholderSize;
    }

    public void setPlaceholderSize(byte b) {
        this.placeholderSize = b;
    }

    public void dispose() {
        this._header = null;
    }
}
