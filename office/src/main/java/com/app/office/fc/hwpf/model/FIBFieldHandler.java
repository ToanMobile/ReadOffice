package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.io.HWPFOutputStream;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Internal
public final class FIBFieldHandler {
    public static final int AUTOSAVESOURCE = 35;
    public static final int BKDEDN = 68;
    public static final int BKDFTN = 66;
    public static final int BKDMOTHER = 64;
    public static final int CLX = 33;
    public static final int CMDS = 24;
    public static final int DGGINFO = 50;
    public static final int DOCUNDO = 77;
    public static final int DOP = 31;
    private static final int FIELD_SIZE = 8;
    public static final int FORMFLDSTTBS = 45;
    public static final int GRPXSTATNOWNERS = 36;
    public static final int MODIFIED = 87;
    public static final int PGDEDN = 67;
    public static final int PGDFTN = 65;
    public static final int PGDMOTHER = 63;
    public static final int PLCASUMY = 89;
    public static final int PLCDOAHDR = 39;
    public static final int PLCFANDREF = 4;
    public static final int PLCFANDTXT = 5;
    public static final int PLCFATNBKF = 42;
    public static final int PLCFATNBKL = 43;
    public static final int PLCFBKF = 22;
    public static final int PLCFBKL = 23;
    public static final int PLCFBTECHPX = 12;
    public static final int PLCFBTELVC = 86;
    public static final int PLCFBTEPAPX = 13;
    public static final int PLCFDOAMOM = 38;
    public static final int PLCFENDREF = 46;
    public static final int PLCFENDTXT = 47;
    public static final int PLCFFLDATN = 19;
    public static final int PLCFFLDEDN = 48;
    public static final int PLCFFLDFTN = 18;
    public static final int PLCFFLDHDR = 17;
    public static final int PLCFFLDHDRTXBX = 59;
    public static final int PLCFFLDMCR = 20;
    public static final int PLCFFLDMOM = 16;
    public static final int PLCFFLDTXBX = 57;
    public static final int PLCFFNDREF = 2;
    public static final int PLCFFNDTXT = 3;
    public static final int PLCFGLSY = 10;
    public static final int PLCFGRAM = 90;
    public static final int PLCFHDD = 11;
    public static final int PLCFHDRTXBXTXT = 58;
    public static final int PLCFLST = 73;
    public static final int PLCFLVC = 88;
    public static final int PLCFPAD = 7;
    public static final int PLCFPGDEDN = 49;
    public static final int PLCFPGDFTN = 34;
    public static final int PLCFPHE = 8;
    public static final int PLCFSEA = 14;
    public static final int PLCFSED = 6;
    public static final int PLCFSPL = 55;
    public static final int PLCFTXBXBKD = 75;
    public static final int PLCFTXBXHDRBKD = 76;
    public static final int PLCFTXBXTXT = 56;
    public static final int PLCFWKB = 54;
    public static final int PLCMCR = 25;
    public static final int PLCOCX = 85;
    public static final int PLCSPAHDR = 41;
    public static final int PLCSPAMOM = 40;
    public static final int PLCUPCRGBUSE = 81;
    public static final int PLCUPCUSP = 82;
    public static final int PLFLFO = 74;
    public static final int PLGOSL = 84;
    public static final int PMS = 44;
    public static final int PRDRVR = 27;
    public static final int PRENVLAND = 29;
    public static final int PRENVPORT = 28;
    public static final int RGBUSE = 78;
    public static final int ROUTESLIP = 70;
    public static final int STSHF = 1;
    public static final int STSHFORIG = 0;
    public static final int STTBAUTOCAPTION = 53;
    public static final int STTBCAPTION = 52;
    public static final int STTBFASSOC = 32;
    public static final int STTBFATNBKMK = 37;
    public static final int STTBFBKMK = 21;
    public static final int STTBFFFN = 15;
    public static final int STTBFINTFLD = 69;
    public static final int STTBFMCR = 26;
    public static final int STTBFNM = 72;
    public static final int STTBFRMARK = 51;
    public static final int STTBFUSSR = 92;
    public static final int STTBGLSY = 9;
    public static final int STTBGLSYSTYLE = 83;
    public static final int STTBLISTNAMES = 91;
    public static final int STTBSAVEDBY = 71;
    public static final int STTBTTMBD = 61;
    public static final int STWUSER = 60;
    public static final int UNUSED = 62;
    public static final int USKF = 80;
    public static final int USP = 79;
    public static final int WSS = 30;
    private static POILogger log = POILogFactory.getLogger(FIBFieldHandler.class);
    private int[] _fields;
    private Map<Integer, UnhandledDataStructure> _unknownMap = new HashMap();

    public FIBFieldHandler(byte[] bArr, int i, byte[] bArr2, HashSet<Integer> hashSet, boolean z) {
        short s = LittleEndian.getShort(bArr, i);
        int i2 = i + 2;
        this._fields = new int[(s * 2)];
        for (int i3 = 0; i3 < s; i3++) {
            int i4 = (i3 * 8) + i2;
            int i5 = LittleEndian.getInt(bArr, i4);
            int i6 = LittleEndian.getInt(bArr, i4 + 4);
            if ((hashSet.contains(Integer.valueOf(i3)) ^ z) && i6 > 0) {
                if (i5 + i6 > bArr2.length) {
                    POILogger pOILogger = log;
                    int i7 = POILogger.WARN;
                    pOILogger.log(i7, (Object) "Unhandled data structure points to outside the buffer. offset = " + i5 + ", length = " + i6 + ", buffer length = " + bArr2.length);
                } else {
                    this._unknownMap.put(Integer.valueOf(i3), new UnhandledDataStructure(bArr2, i5, i6));
                }
            }
            int[] iArr = this._fields;
            int i8 = i3 * 2;
            iArr[i8] = i5;
            iArr[i8 + 1] = i6;
        }
    }

    public void clearFields() {
        Arrays.fill(this._fields, 0);
    }

    public int getFieldOffset(int i) {
        return this._fields[i * 2];
    }

    public int getFieldSize(int i) {
        return this._fields[(i * 2) + 1];
    }

    public void setFieldOffset(int i, int i2) {
        this._fields[i * 2] = i2;
    }

    public void setFieldSize(int i, int i2) {
        this._fields[(i * 2) + 1] = i2;
    }

    public int sizeInBytes() {
        return (this._fields.length * 4) + 2;
    }

    /* access modifiers changed from: package-private */
    public void writeTo(byte[] bArr, int i, HWPFOutputStream hWPFOutputStream) throws IOException {
        int i2;
        int length = this._fields.length / 2;
        LittleEndian.putShort(bArr, i, (short) length);
        int i3 = i + 2;
        for (int i4 = 0; i4 < length; i4++) {
            UnhandledDataStructure unhandledDataStructure = this._unknownMap.get(Integer.valueOf(i4));
            if (unhandledDataStructure != null) {
                int i5 = i4 * 2;
                this._fields[i5] = hWPFOutputStream.getOffset();
                LittleEndian.putInt(bArr, i3, hWPFOutputStream.getOffset());
                i2 = i3 + 4;
                byte[] buf = unhandledDataStructure.getBuf();
                hWPFOutputStream.write(buf);
                this._fields[i5 + 1] = buf.length;
                LittleEndian.putInt(bArr, i2, buf.length);
            } else {
                int i6 = i4 * 2;
                LittleEndian.putInt(bArr, i3, this._fields[i6]);
                i2 = i3 + 4;
                LittleEndian.putInt(bArr, i2, this._fields[i6 + 1]);
            }
            i3 = i2 + 4;
        }
    }
}
