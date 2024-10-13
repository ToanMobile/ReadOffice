package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.model.PictureDescriptor;
import com.app.office.fc.util.LittleEndian;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.InflaterInputStream;

public final class Picture extends PictureDescriptor {
    @Deprecated
    public static final byte[] BMP = PictureType.BMP.getSignatures()[0];
    public static final byte[] COMPRESSED1 = {-2, 120, -38};
    public static final byte[] COMPRESSED2 = {-2, 120, -100};
    @Deprecated
    public static final byte[] EMF = PictureType.EMF.getSignatures()[0];
    @Deprecated
    public static final byte[] GIF = PictureType.GIF.getSignatures()[0];
    public static final byte[] IHDR = {Field.TOA, Field.NOTEREF, Field.INCLUDETEXT, 82};
    @Deprecated
    public static final byte[] JPG = PictureType.JPEG.getSignatures()[0];
    static final int MFPMM_OFFSET = 6;
    static final int PICF_OFFSET = 0;
    static final int PICF_SHAPE_OFFSET = 14;
    static final int PICT_HEADER_OFFSET = 4;
    @Deprecated
    public static final byte[] PNG = PictureType.PNG.getSignatures()[0];
    private static final int PRELOADDATA_LENGTH = 128;
    @Deprecated
    public static final byte[] TIFF = PictureType.TIFF.getSignatures()[0];
    @Deprecated
    public static final byte[] TIFF1 = PictureType.TIFF.getSignatures()[1];
    static final int UNKNOWN_HEADER_SIZE = 73;
    @Deprecated
    public static final byte[] WMF1 = PictureType.WMF.getSignatures()[0];
    @Deprecated
    public static final byte[] WMF2 = PictureType.WMF.getSignatures()[1];
    private byte[] _dataStream;
    private byte[] content;
    private int dataBlockSize;
    private int dataBlockStartOfsset;
    private int height = -1;
    private int pictureBytesStartOffset;
    private byte[] preloadRawContent;
    private byte[] rawContent;
    private int size;
    private String tempFilePath;
    private String tempPath;
    private int width = -1;

    public Picture(String str, int i, byte[] bArr, boolean z) throws Exception {
        super(bArr, i);
        this._dataStream = bArr;
        this.dataBlockStartOfsset = i;
        int i2 = LittleEndian.getInt(bArr, i);
        this.dataBlockSize = i2;
        int pictureBytesStartOffset2 = getPictureBytesStartOffset(i, bArr, i2);
        this.pictureBytesStartOffset = pictureBytesStartOffset2;
        int i3 = this.dataBlockSize - (pictureBytesStartOffset2 - i);
        this.size = i3;
        if (i3 >= 0) {
            if (z) {
                fillImageContent();
            }
            this.tempPath = str;
            return;
        }
        throw new Exception("picture size is wrong");
    }

    public Picture(byte[] bArr) {
        this._dataStream = bArr;
        this.dataBlockStartOfsset = 0;
        this.dataBlockSize = bArr.length;
        this.pictureBytesStartOffset = 0;
        this.size = bArr.length;
    }

    private void fillWidthHeight() {
        int i = AnonymousClass1.$SwitchMap$com$reader$office$fc$hwpf$usermodel$PictureType[suggestPictureType().ordinal()];
        if (i == 1) {
            fillJPGWidthHeight();
        } else if (i == 2) {
            fillPNGWidthHeight();
        }
    }

    /* renamed from: com.app.office.fc.hwpf.usermodel.Picture$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$reader$office$fc$hwpf$usermodel$PictureType;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        static {
            /*
                com.app.office.fc.hwpf.usermodel.PictureType[] r0 = com.app.office.fc.hwpf.usermodel.PictureType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$reader$office$fc$hwpf$usermodel$PictureType = r0
                com.app.office.fc.hwpf.usermodel.PictureType r1 = com.app.office.fc.hwpf.usermodel.PictureType.JPEG     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$reader$office$fc$hwpf$usermodel$PictureType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.app.office.fc.hwpf.usermodel.PictureType r1 = com.app.office.fc.hwpf.usermodel.PictureType.PNG     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hwpf.usermodel.Picture.AnonymousClass1.<clinit>():void");
        }
    }

    public String suggestFullFileName() {
        String str;
        String suggestFileExtension = suggestFileExtension();
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(this.dataBlockStartOfsset));
        if (suggestFileExtension.length() > 0) {
            str = "." + suggestFileExtension;
        } else {
            str = "";
        }
        sb.append(str);
        return sb.toString();
    }

    public int getStartOffset() {
        return this.dataBlockStartOfsset;
    }

    public byte[] getContent() {
        fillImageContent();
        return this.content;
    }

    public byte[] getPreLoadRawContent() {
        fillPreloadRawImageContent();
        return this.preloadRawContent;
    }

    public byte[] getRawContent() {
        fillRawImageContent();
        return this.rawContent;
    }

    public int getSize() {
        return this.size;
    }

    @Deprecated
    public int getAspectRatioX() {
        return this.mx / 10;
    }

    public int getHorizontalScalingFactor() {
        return this.mx;
    }

    @Deprecated
    public int getAspectRatioY() {
        return this.my / 10;
    }

    public int getVerticalScalingFactor() {
        return this.my;
    }

    public int getDxaGoal() {
        return this.dxaGoal;
    }

    public int getDyaGoal() {
        return this.dyaGoal;
    }

    public float getDxaCropLeft() {
        return this.dxaCropLeft;
    }

    public float getDyaCropTop() {
        return this.dyaCropTop;
    }

    public float getDxaCropRight() {
        return this.dxaCropRight;
    }

    public float getDyaCropBottom() {
        return this.dyaCropBottom;
    }

    public String suggestFileExtension() {
        return suggestPictureType().getExtension();
    }

    public String getMimeType() {
        return suggestPictureType().getMime();
    }

    public PictureType suggestPictureType() {
        return PictureType.findMatchingType(getContent());
    }

    private void fillPreloadRawImageContent() {
        byte[] bArr = this.preloadRawContent;
        if (bArr == null || bArr.length <= 0) {
            byte[] bArr2 = new byte[Math.min(this.size, 128)];
            this.preloadRawContent = bArr2;
            try {
                System.arraycopy(this._dataStream, this.pictureBytesStartOffset, bArr2, 0, bArr2.length);
            } catch (Exception unused) {
            }
        }
    }

    private void fillRawImageContent() {
        byte[] bArr = this.rawContent;
        if (bArr == null || bArr.length <= 0) {
            int i = this.size;
            byte[] bArr2 = new byte[i];
            this.rawContent = bArr2;
            try {
                System.arraycopy(this._dataStream, this.pictureBytesStartOffset, bArr2, 0, i);
            } catch (Exception unused) {
            }
        }
    }

    private void fillImageContent() {
        byte[] bArr = this.content;
        if (bArr == null || bArr.length <= 0) {
            byte[] preLoadRawContent = getPreLoadRawContent();
            this.content = preLoadRawContent;
            int i = this.pictureBytesStartOffset;
            int i2 = this.size;
            if (matchSignature(preLoadRawContent, COMPRESSED1, 32) || matchSignature(preLoadRawContent, COMPRESSED2, 32)) {
                try {
                    InflaterInputStream inflaterInputStream = new InflaterInputStream(new ByteArrayInputStream(this._dataStream, this.pictureBytesStartOffset + 33, this.size - 33));
                    this.tempFilePath = this.tempPath + File.separator + String.valueOf(System.currentTimeMillis()) + ".tmp";
                    File file = new File(this.tempFilePath);
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] bArr2 = new byte[4096];
                    boolean z = false;
                    while (true) {
                        int read = inflaterInputStream.read(bArr2);
                        if (read > 0) {
                            if (!z) {
                                z = true;
                                byte[] bArr3 = new byte[read];
                                this.content = bArr3;
                                System.arraycopy(bArr2, 0, bArr3, 0, read);
                            }
                            fileOutputStream.write(bArr2, 0, read);
                        } else {
                            fileOutputStream.close();
                            return;
                        }
                    }
                } catch (Exception unused) {
                }
            } else {
                try {
                    InflaterInputStream inflaterInputStream2 = new InflaterInputStream(new ByteArrayInputStream(this._dataStream, this.pictureBytesStartOffset + 33, this.size - 33));
                    byte[] bArr4 = new byte[128];
                    inflaterInputStream2.read(bArr4);
                    String extension = PictureType.findMatchingType(bArr4).getExtension();
                    if (!com.app.office.common.picture.Picture.WMF_TYPE.equalsIgnoreCase(extension)) {
                        if (!com.app.office.common.picture.Picture.EMF_TYPE.equalsIgnoreCase(extension)) {
                            this.tempFilePath = writeTempFile(this._dataStream, i, i2);
                            inflaterInputStream2.close();
                        }
                    }
                    this.content = bArr4;
                    File file2 = new File(this.tempPath + File.separator + System.currentTimeMillis() + ".tmp");
                    file2.createNewFile();
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                    fileOutputStream2.write(bArr4);
                    byte[] bArr5 = new byte[4096];
                    while (true) {
                        int read2 = inflaterInputStream2.read(bArr5);
                        if (read2 <= 0) {
                            break;
                        }
                        fileOutputStream2.write(bArr5, 0, read2);
                    }
                    fileOutputStream2.close();
                    this.tempFilePath = file2.getAbsolutePath();
                    inflaterInputStream2.close();
                } catch (Exception unused2) {
                    this.tempFilePath = writeTempFile(this._dataStream, i, i2);
                }
            }
        }
    }

    private String writeTempFile(byte[] bArr, int i, int i2) {
        File file = new File(this.tempPath + File.separator + (String.valueOf(System.currentTimeMillis()) + ".tmp"));
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bArr, i, i2);
            fileOutputStream.close();
        } catch (Exception unused) {
        }
        return file.getAbsolutePath();
    }

    private static boolean matchSignature(byte[] bArr, byte[] bArr2, int i) {
        boolean z = i < bArr.length;
        int i2 = 0;
        while (true) {
            int i3 = i2 + i;
            if (i3 < bArr.length && i2 < bArr2.length) {
                if (bArr[i3] != bArr2[i2]) {
                    return false;
                }
                i2++;
            }
        }
        return z;
    }

    private static int getPictureBytesStartOffset(int i, byte[] bArr, int i2) {
        int i3 = i2 + i;
        int i4 = i + 4;
        int i5 = LittleEndian.getShort(bArr, i4) + 4;
        if (LittleEndian.getShort(bArr, i4 + 2) == 102) {
            i5 += LittleEndian.getUnsignedByte(bArr, i5) + 1;
        }
        int i6 = LittleEndian.getInt(bArr, i + i5) + i5;
        if (i6 < i3) {
            i5 = i6;
        }
        int i7 = i + i5 + 73;
        return i7 >= i3 ? i7 - 73 : i7;
    }

    private void fillJPGWidthHeight() {
        byte[] bArr;
        byte b;
        byte b2;
        int i = this.pictureBytesStartOffset;
        int i2 = i + 2;
        byte[] bArr2 = this._dataStream;
        byte b3 = bArr2[i2];
        byte b4 = bArr2[i2 + 1];
        int i3 = i + this.size;
        while (true) {
            int i4 = i3 - 1;
            if (i2 < i4) {
                do {
                    bArr = this._dataStream;
                    b = bArr[i2];
                    b2 = bArr[i2 + 1];
                    i2 += 2;
                    if (b == -1) {
                        break;
                    }
                } while (i2 < i4);
                if (b != -1 || i2 >= i4) {
                    i2++;
                } else if (b2 != -39 && b2 != -38) {
                    if ((b2 & 240) != 192 || b2 == -60 || b2 == -56 || b2 == -52) {
                        int i5 = i2 + 1 + 1;
                        i2 = i5 + getBigEndianShort(bArr, i5);
                    } else {
                        int i6 = i2 + 5;
                        this.height = getBigEndianShort(bArr, i6);
                        this.width = getBigEndianShort(this._dataStream, i6 + 2);
                        return;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void fillPNGWidthHeight() {
        int length = this.pictureBytesStartOffset + PNG.length + 4;
        if (matchSignature(this._dataStream, IHDR, length)) {
            int i = length + 4;
            this.width = getBigEndianInt(this._dataStream, i);
            this.height = getBigEndianInt(this._dataStream, i + 4);
        }
    }

    public int getWidth() {
        if (this.width == -1) {
            fillWidthHeight();
        }
        return this.width;
    }

    public int getHeight() {
        if (this.height == -1) {
            fillWidthHeight();
        }
        return this.height;
    }

    private static int getBigEndianInt(byte[] bArr, int i) {
        return ((bArr[i] & 255) << 24) + ((bArr[i + 1] & 255) << 16) + ((bArr[i + 2] & 255) << 8) + (bArr[i + 3] & 255);
    }

    private static int getBigEndianShort(byte[] bArr, int i) {
        return ((bArr[i] & 255) << 8) + (bArr[i + 1] & 255);
    }

    public String getTempFilePath() {
        return this.tempFilePath;
    }

    public void setTempFilePath(String str) {
        this.tempFilePath = str;
    }
}
