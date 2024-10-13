package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.Arrays;

@Internal
public class PictureDescriptor {
    private static final int CBHEADER_OFFSET = 4;
    private static final int DXACROPLEFT_OFFSET = 36;
    private static final int DXACROPRIGHT_OFFSET = 40;
    private static final int DXAGOAL_OFFSET = 28;
    private static final int DYACROPBOTTOM_OFFSET = 42;
    private static final int DYACROPTOP_OFFSET = 38;
    private static final int DYAGOAL_OFFSET = 30;
    private static final int LCB_OFFSET = 0;
    private static final int MFP_HMF_OFFSET = 12;
    private static final int MFP_MM_OFFSET = 6;
    private static final int MFP_XEXT_OFFSET = 8;
    private static final int MFP_YEXT_OFFSET = 10;
    private static final int MX_OFFSET = 32;
    private static final int MY_OFFSET = 34;
    protected boolean bGrayScl;
    protected boolean bSetBright;
    protected boolean bSetContrast;
    protected boolean bSetGrayScl;
    protected boolean bSetThreshold;
    protected int cbHeader;
    protected float dxaCropLeft;
    protected float dxaCropRight;
    protected short dxaGoal;
    protected float dyaCropBottom;
    protected float dyaCropTop;
    protected short dyaGoal;
    protected float fBright;
    protected float fContrast;
    protected float fThreshold;
    protected int lcb;
    protected int mfp_hMF;
    protected int mfp_mm;
    protected int mfp_xExt;
    protected int mfp_yExt;
    protected short mx;
    protected short my;
    protected byte[] offset14;

    public class BlipBooleanProperties {
        public BitField fPictureBiLevel;
        public BitField fPictureGray;
        public BitField fUsefPictureBiLevel;
        public BitField fUsefPictureGray;
        int propValue;

        public BlipBooleanProperties() {
        }
    }

    public class OfficeArtRecordHeader {
        public short recFlag;
        public BitField recInstance;
        public long recLength;
        public int recType;
        public BitField recVer;

        public OfficeArtRecordHeader() {
        }
    }

    public class OfficeArtOpid {
        public BitField fBid;
        public BitField fComplex;
        public short flag;
        public BitField opid;

        public OfficeArtOpid() {
        }
    }

    public float getBright() {
        return this.fBright;
    }

    public float getContrast() {
        return this.fContrast;
    }

    public boolean isGrayScl() {
        return this.bGrayScl;
    }

    public float getThreshold() {
        return this.fThreshold;
    }

    public boolean isSetBright() {
        return this.bSetBright;
    }

    public boolean isSetContrast() {
        return this.bSetContrast;
    }

    public boolean isSetGrayScl() {
        return this.bSetGrayScl;
    }

    public boolean isSetThreshold() {
        return this.bSetThreshold;
    }

    public PictureDescriptor() {
        this.offset14 = new byte[14];
        this.dxaGoal = 0;
        this.dyaGoal = 0;
        this.dxaCropLeft = 0.0f;
        this.dyaCropTop = 0.0f;
        this.dxaCropRight = 0.0f;
        this.dyaCropBottom = 0.0f;
    }

    public PictureDescriptor(byte[] bArr, int i) {
        this.offset14 = new byte[14];
        this.dxaGoal = 0;
        this.dyaGoal = 0;
        this.dxaCropLeft = 0.0f;
        this.dyaCropTop = 0.0f;
        this.dxaCropRight = 0.0f;
        this.dyaCropBottom = 0.0f;
        this.lcb = LittleEndian.getInt(bArr, i + 0);
        this.cbHeader = LittleEndian.getUShort(bArr, i + 4);
        this.mfp_mm = LittleEndian.getUShort(bArr, i + 6);
        this.mfp_xExt = LittleEndian.getUShort(bArr, i + 8);
        this.mfp_yExt = LittleEndian.getUShort(bArr, i + 10);
        this.mfp_hMF = LittleEndian.getUShort(bArr, i + 12);
        this.offset14 = LittleEndian.getByteArray(bArr, i + 14, 14);
        this.dxaGoal = LittleEndian.getShort(bArr, i + 28);
        this.dyaGoal = LittleEndian.getShort(bArr, i + 30);
        this.mx = LittleEndian.getShort(bArr, i + 32);
        this.my = LittleEndian.getShort(bArr, i + 34);
        int i2 = i + 68;
        i2 = this.mfp_mm == 102 ? i2 + (bArr[i2] & 65535) + 1 : i2;
        OfficeArtRecordHeader readHeader = readHeader(bArr, i2);
        short recVer = getRecVer(readHeader);
        short recInstance = getRecInstance(readHeader);
        if (recVer == 15 && recInstance == 0 && readHeader.recType == 61444) {
            long j = readHeader.recLength;
            int i3 = i2 + 8;
            while (j > 0 && i3 < bArr.length) {
                OfficeArtRecordHeader readHeader2 = readHeader(bArr, i3);
                j -= readHeader2.recLength;
                int i4 = i3 + 8;
                short recVer2 = getRecVer(readHeader2);
                getRecInstance(readHeader2);
                if (recVer2 == 3 && readHeader2.recType == 61451) {
                    short recInstance2 = getRecInstance(readHeader2);
                    for (int i5 = 0; i5 < recInstance2; i5++) {
                        OfficeArtOpid readOfficeArtOpid = readOfficeArtOpid(bArr, i4);
                        short opid = getOpid(readOfficeArtOpid);
                        boolean isfBid = isfBid(readOfficeArtOpid);
                        boolean isfComplex = isfComplex(readOfficeArtOpid);
                        if (opid == 256 && !isfBid && !isfComplex) {
                            this.dyaCropTop = getRealNumFromFixedPoint(LittleEndian.getByteArray(bArr, i4 + 2, 4));
                        }
                        if (opid == 257 && !isfBid && !isfComplex) {
                            this.dyaCropBottom = getRealNumFromFixedPoint(LittleEndian.getByteArray(bArr, i4 + 2, 4));
                        }
                        if (opid == 258 && !isfBid && !isfComplex) {
                            this.dxaCropLeft = getRealNumFromFixedPoint(LittleEndian.getByteArray(bArr, i4 + 2, 4));
                        }
                        if (opid == 259 && !isfBid && !isfComplex) {
                            this.dxaCropRight = getRealNumFromFixedPoint(LittleEndian.getByteArray(bArr, i4 + 2, 4));
                        }
                        if (opid == 265 && !isfBid && !isfComplex) {
                            int i6 = LittleEndian.getInt(bArr, i4 + 2);
                            this.bSetBright = true;
                            this.fBright = (((float) i6) / 32768.0f) * 255.0f;
                        }
                        if (opid == 264 && !isfBid && !isfComplex) {
                            int i7 = LittleEndian.getInt(bArr, i4 + 2);
                            this.bSetContrast = true;
                            this.fContrast = Math.min(((float) i7) / 65536.0f, 10.0f);
                        }
                        if (opid == 319 && !isfBid && !isfComplex) {
                            BlipBooleanProperties readBlipBooleanProperties = readBlipBooleanProperties(bArr, i4 + 2);
                            if (isfUsefPictureBiLevel(readBlipBooleanProperties)) {
                                if (isfPictureBiLevel(readBlipBooleanProperties)) {
                                    this.bSetThreshold = true;
                                    this.fThreshold = 128.0f;
                                }
                            } else if (isfUsefPictureGray(readBlipBooleanProperties) && isfPictureGray(readBlipBooleanProperties)) {
                                this.bSetGrayScl = true;
                                this.bGrayScl = true;
                            }
                        }
                        i4 += 6;
                    }
                    return;
                }
                i3 = (int) (((long) i4) + readHeader2.recLength);
            }
        }
    }

    private short getRecVer(OfficeArtRecordHeader officeArtRecordHeader) {
        return (short) officeArtRecordHeader.recVer.getValue(officeArtRecordHeader.recFlag);
    }

    private short getRecInstance(OfficeArtRecordHeader officeArtRecordHeader) {
        return (short) officeArtRecordHeader.recInstance.getValue(officeArtRecordHeader.recFlag);
    }

    private OfficeArtRecordHeader readHeader(byte[] bArr, int i) {
        OfficeArtRecordHeader officeArtRecordHeader = new OfficeArtRecordHeader();
        officeArtRecordHeader.recVer = new BitField(15);
        officeArtRecordHeader.recInstance = new BitField(65520);
        int i2 = i + 4;
        if (i2 < bArr.length) {
            officeArtRecordHeader.recFlag = LittleEndian.getShort(bArr, i);
            officeArtRecordHeader.recType = LittleEndian.getUShort(bArr, i + 2);
            officeArtRecordHeader.recLength = LittleEndian.getUInt(bArr, i2);
        }
        return officeArtRecordHeader;
    }

    private OfficeArtOpid readOfficeArtOpid(byte[] bArr, int i) {
        OfficeArtOpid officeArtOpid = new OfficeArtOpid();
        officeArtOpid.opid = new BitField(16383);
        officeArtOpid.fBid = new BitField(16384);
        officeArtOpid.fComplex = new BitField(32768);
        officeArtOpid.flag = LittleEndian.getShort(bArr, i);
        return officeArtOpid;
    }

    private short getOpid(OfficeArtOpid officeArtOpid) {
        return officeArtOpid.opid.getShortValue(officeArtOpid.flag);
    }

    private boolean isfBid(OfficeArtOpid officeArtOpid) {
        return officeArtOpid.fBid.getShortValue(officeArtOpid.flag) == 1;
    }

    private boolean isfComplex(OfficeArtOpid officeArtOpid) {
        return officeArtOpid.fComplex.getShortValue(officeArtOpid.flag) == 1;
    }

    private float getRealNumFromFixedPoint(byte[] bArr) {
        return ((float) LittleEndian.getShort(bArr, 2)) + (((float) LittleEndian.getUShort(bArr, 0)) / 65536.0f);
    }

    private BlipBooleanProperties readBlipBooleanProperties(byte[] bArr, int i) {
        BlipBooleanProperties blipBooleanProperties = new BlipBooleanProperties();
        blipBooleanProperties.propValue = LittleEndian.getInt(bArr, i);
        blipBooleanProperties.fPictureBiLevel = new BitField(131072);
        blipBooleanProperties.fUsefPictureBiLevel = new BitField(2);
        blipBooleanProperties.fPictureGray = new BitField(262144);
        blipBooleanProperties.fUsefPictureGray = new BitField(4);
        return blipBooleanProperties;
    }

    private boolean isfUsefPictureBiLevel(BlipBooleanProperties blipBooleanProperties) {
        return blipBooleanProperties.fUsefPictureBiLevel.getValue(blipBooleanProperties.propValue) == 1;
    }

    private boolean isfPictureBiLevel(BlipBooleanProperties blipBooleanProperties) {
        return blipBooleanProperties.fPictureBiLevel.getValue(blipBooleanProperties.propValue) == 1;
    }

    private boolean isfUsefPictureGray(BlipBooleanProperties blipBooleanProperties) {
        return blipBooleanProperties.fUsefPictureBiLevel.getValue(blipBooleanProperties.propValue) == 1;
    }

    private boolean isfPictureGray(BlipBooleanProperties blipBooleanProperties) {
        return blipBooleanProperties.fPictureBiLevel.getValue(blipBooleanProperties.propValue) == 1;
    }

    public short getZoomX() {
        return this.mx;
    }

    public short getZoomY() {
        return this.my;
    }

    public String toString() {
        return "[PICF]\n" + "        lcb           = " + this.lcb + 10 + "        cbHeader      = " + this.cbHeader + 10 + "        mfp.mm        = " + this.mfp_mm + 10 + "        mfp.xExt      = " + this.mfp_xExt + 10 + "        mfp.yExt      = " + this.mfp_yExt + 10 + "        mfp.hMF       = " + this.mfp_hMF + 10 + "        offset14      = " + Arrays.toString(this.offset14) + 10 + "        dxaGoal       = " + this.dxaGoal + 10 + "        dyaGoal       = " + this.dyaGoal + 10 + "        dxaCropLeft   = " + this.dxaCropLeft + 10 + "        dyaCropTop    = " + this.dyaCropTop + 10 + "        dxaCropRight  = " + this.dxaCropRight + 10 + "        dyaCropBottom = " + this.dyaCropBottom + 10 + "[/PICF]";
    }
}
