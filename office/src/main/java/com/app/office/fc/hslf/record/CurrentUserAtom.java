package com.app.office.fc.hslf.record;

import androidx.core.view.PointerIconCompat;
import com.app.office.fc.fs.filesystem.CFBFileSystem;
import com.app.office.fc.hslf.exceptions.CorruptPowerPointFileException;
import com.app.office.fc.hslf.exceptions.EncryptedPowerPointFileException;
import com.app.office.fc.hslf.exceptions.OldPowerPointFormatException;
import com.app.office.fc.hwpf.OldWordFileFormatException;
import com.app.office.fc.hwpf.usermodel.Field;
import com.app.office.fc.poifs.filesystem.POIFSFileSystem;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.StringUtil;
import java.io.IOException;
import java.io.PrintStream;

public class CurrentUserAtom {
    public static final byte[] atomHeader = {0, 0, -10, 15};
    public static final byte[] encHeaderToken = {-33, -60, -47, -13};
    public static final byte[] headerToken = {Field.SHAPE, -64, -111, -29};
    public static final byte[] ppt97FileVer = {8, 0, -13, 3, 3, 0};
    private byte[] _contents;
    private long currentEditOffset;
    private int docFinalVersion;
    private byte docMajorNo;
    private byte docMinorNo;
    private String lastEditUser;
    private long releaseVersion;

    public int getDocFinalVersion() {
        return this.docFinalVersion;
    }

    public byte getDocMajorNo() {
        return this.docMajorNo;
    }

    public byte getDocMinorNo() {
        return this.docMinorNo;
    }

    public long getReleaseVersion() {
        return this.releaseVersion;
    }

    public void setReleaseVersion(long j) {
        this.releaseVersion = j;
    }

    public long getCurrentEditOffset() {
        return this.currentEditOffset;
    }

    public void setCurrentEditOffset(long j) {
        this.currentEditOffset = j;
    }

    public String getLastEditUsername() {
        return this.lastEditUser;
    }

    public void setLastEditUsername(String str) {
        this.lastEditUser = str;
    }

    public CurrentUserAtom() {
        this._contents = new byte[0];
        this.docFinalVersion = PointerIconCompat.TYPE_NO_DROP;
        this.docMajorNo = 3;
        this.docMinorNo = 0;
        this.releaseVersion = 8;
        this.currentEditOffset = 0;
        this.lastEditUser = "Apache POI";
    }

    public CurrentUserAtom(POIFSFileSystem pOIFSFileSystem) throws IOException {
    }

    public CurrentUserAtom(CFBFileSystem cFBFileSystem) throws IOException {
        byte[] propertyRawData = cFBFileSystem.getPropertyRawData("Current User");
        this._contents = propertyRawData;
        if (propertyRawData == null || propertyRawData.length > 131072) {
            throw new CorruptPowerPointFileException("The Current User stream is implausably long. It's normally 28-200 bytes long, but was " + this._contents.length + " bytes");
        } else if (propertyRawData.length < 28) {
            if (propertyRawData.length >= 4) {
                int i = LittleEndian.getInt(propertyRawData);
                System.err.println(i);
                if (i + 4 == this._contents.length) {
                    throw new OldPowerPointFormatException("Based on the Current User stream, you seem to have supplied a PowerPoint95 file, which isn't supported");
                }
            }
            throw new CorruptPowerPointFileException("The Current User stream must be at least 28 bytes long, but was only " + this._contents.length);
        } else {
            init();
        }
    }

    public CurrentUserAtom(byte[] bArr) {
        this._contents = bArr;
        init();
    }

    private void init() {
        byte[] bArr = this._contents;
        byte b = bArr[12];
        byte[] bArr2 = encHeaderToken;
        if (b == bArr2[0] && bArr[13] == bArr2[1] && bArr[14] == bArr2[2] && bArr[15] == bArr2[3]) {
            throw new EncryptedPowerPointFileException("Cannot process encrypted office files!");
        }
        this.currentEditOffset = LittleEndian.getUInt(bArr, 16);
        this.docFinalVersion = LittleEndian.getUShort(this._contents, 22);
        byte[] bArr3 = this._contents;
        this.docMajorNo = bArr3[24];
        this.docMinorNo = bArr3[25];
        long uShort = (long) LittleEndian.getUShort(bArr3, 20);
        if (uShort > 512) {
            PrintStream printStream = System.err;
            printStream.println("Warning - invalid username length " + uShort + " found, treating as if there was no username set");
            uShort = 0;
        }
        byte[] bArr4 = this._contents;
        int i = (int) uShort;
        int i2 = i + 28;
        int i3 = i2 + 4;
        if (bArr4.length >= i3) {
            long uInt = LittleEndian.getUInt(bArr4, i2);
            this.releaseVersion = uInt;
            if (uInt == 0) {
                throw new OldWordFileFormatException("The document is too old - Word 95 or older. Try HWPFOldDocument instead?");
            }
        } else {
            this.releaseVersion = 0;
        }
        int i4 = i * 2;
        byte[] bArr5 = this._contents;
        if (bArr5.length >= i3 + i4) {
            byte[] bArr6 = new byte[i4];
            System.arraycopy(bArr5, i3, bArr6, 0, i4);
            this.lastEditUser = StringUtil.getFromUnicodeLE(bArr6);
            return;
        }
        byte[] bArr7 = new byte[i];
        System.arraycopy(bArr5, 28, bArr7, 0, i);
        this.lastEditUser = StringUtil.getFromCompressedUnicode(bArr7, 0, i);
    }

    public void dispose() {
        this._contents = null;
        this.lastEditUser = null;
    }
}
