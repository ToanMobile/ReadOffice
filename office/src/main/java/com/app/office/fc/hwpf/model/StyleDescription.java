package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.usermodel.CharacterProperties;
import com.app.office.fc.hwpf.usermodel.ParagraphProperties;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.Arrays;

@Internal
public final class StyleDescription implements HDFType {
    private static final int CHARACTER_STYLE = 2;
    private static final int PARAGRAPH_STYLE = 1;
    private static BitField _baseStyle = BitFieldFactory.getInstance(65520);
    private static BitField _fAutoRedef = BitFieldFactory.getInstance(1);
    private static BitField _fHasUpe = BitFieldFactory.getInstance(16384);
    private static BitField _fHidden = BitFieldFactory.getInstance(2);
    private static BitField _fInvalHeight = BitFieldFactory.getInstance(8192);
    private static BitField _fMassCopy = BitFieldFactory.getInstance(32768);
    private static BitField _fScratch = BitFieldFactory.getInstance(4096);
    private static BitField _nextStyle = BitFieldFactory.getInstance(65520);
    private static BitField _numUPX = BitFieldFactory.getInstance(15);
    private static BitField _sti = BitFieldFactory.getInstance(4095);
    private static BitField _styleTypeCode = BitFieldFactory.getInstance(15);
    private int _baseLength;
    private short _bchUpe;
    CharacterProperties _chp;
    private short _infoShort;
    private short _infoShort2;
    private short _infoShort3;
    private short _infoShort4;
    private int _istd;
    String _name;
    ParagraphProperties _pap;
    UPX[] _upxs;

    public StyleDescription() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v12, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v13, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public StyleDescription(byte[] r8, int r9, int r10, boolean r11) {
        /*
            r7 = this;
            r7.<init>()
            r7._baseLength = r9
            int r9 = r9 + r10
            short r0 = com.app.office.fc.util.LittleEndian.getShort(r8, r10)
            r7._infoShort = r0
            r0 = 2
            int r10 = r10 + r0
            short r1 = com.app.office.fc.util.LittleEndian.getShort(r8, r10)
            r7._infoShort2 = r1
            int r10 = r10 + r0
            short r1 = com.app.office.fc.util.LittleEndian.getShort(r8, r10)
            r7._infoShort3 = r1
            int r10 = r10 + r0
            short r1 = com.app.office.fc.util.LittleEndian.getShort(r8, r10)
            r7._bchUpe = r1
            int r10 = r10 + r0
            short r10 = com.app.office.fc.util.LittleEndian.getShort(r8, r10)
            r7._infoShort4 = r10
            r10 = 1
            if (r11 == 0) goto L_0x0034
            short r11 = com.app.office.fc.util.LittleEndian.getShort(r8, r9)
            int r9 = r9 + 2
            r1 = 2
            goto L_0x0037
        L_0x0034:
            byte r11 = r8[r9]
            r1 = 1
        L_0x0037:
            java.lang.String r2 = new java.lang.String     // Catch:{ UnsupportedEncodingException -> 0x0042 }
            int r3 = r11 * r1
            java.lang.String r4 = "UTF-16LE"
            r2.<init>(r8, r9, r3, r4)     // Catch:{ UnsupportedEncodingException -> 0x0042 }
            r7._name = r2     // Catch:{ UnsupportedEncodingException -> 0x0042 }
        L_0x0042:
            int r11 = r11 + r10
            int r11 = r11 * r1
            int r11 = r11 + r9
            com.app.office.fc.util.BitField r9 = _numUPX
            short r1 = r7._infoShort3
            int r9 = r9.getValue(r1)
            com.app.office.fc.hwpf.model.UPX[] r1 = new com.app.office.fc.hwpf.model.UPX[r9]
            r7._upxs = r1
            r1 = 0
            r2 = 0
        L_0x0054:
            if (r2 >= r9) goto L_0x0072
            short r3 = com.app.office.fc.util.LittleEndian.getShort(r8, r11)
            int r11 = r11 + r0
            byte[] r4 = new byte[r3]
            java.lang.System.arraycopy(r8, r11, r4, r1, r3)
            com.app.office.fc.hwpf.model.UPX[] r5 = r7._upxs
            com.app.office.fc.hwpf.model.UPX r6 = new com.app.office.fc.hwpf.model.UPX
            r6.<init>(r4)
            r5[r2] = r6
            int r11 = r11 + r3
            int r3 = r3 % r0
            if (r3 != r10) goto L_0x006f
            int r11 = r11 + 1
        L_0x006f:
            int r2 = r2 + 1
            goto L_0x0054
        L_0x0072:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hwpf.model.StyleDescription.<init>(byte[], int, int, boolean):void");
    }

    public int getBaseStyle() {
        return _baseStyle.getValue(this._infoShort2);
    }

    public byte[] getCHPX() {
        int value = _styleTypeCode.getValue(this._infoShort2);
        if (value == 1) {
            UPX[] upxArr = this._upxs;
            if (upxArr.length > 1) {
                return upxArr[1].getUPX();
            }
            return null;
        } else if (value != 2) {
            return null;
        } else {
            return this._upxs[0].getUPX();
        }
    }

    public byte[] getPAPX() {
        if (_styleTypeCode.getValue(this._infoShort2) != 1) {
            return null;
        }
        return this._upxs[0].getUPX();
    }

    public ParagraphProperties getPAP() {
        return this._pap;
    }

    public CharacterProperties getCHP() {
        return this._chp;
    }

    /* access modifiers changed from: package-private */
    public void setPAP(ParagraphProperties paragraphProperties) {
        this._pap = paragraphProperties;
    }

    /* access modifiers changed from: package-private */
    public void setCHP(CharacterProperties characterProperties) {
        this._chp = characterProperties;
    }

    public String getName() {
        return this._name;
    }

    public byte[] toByteArray() {
        int i = 1;
        int length = this._baseLength + 2 + ((this._name.length() + 1) * 2) + this._upxs[0].size() + 2;
        while (true) {
            UPX[] upxArr = this._upxs;
            if (i >= upxArr.length) {
                break;
            }
            length = length + (upxArr[i - 1].size() % 2) + this._upxs[i].size() + 2;
            i++;
        }
        byte[] bArr = new byte[length];
        LittleEndian.putShort(bArr, 0, this._infoShort);
        LittleEndian.putShort(bArr, 2, this._infoShort2);
        LittleEndian.putShort(bArr, 4, this._infoShort3);
        LittleEndian.putShort(bArr, 6, this._bchUpe);
        LittleEndian.putShort(bArr, 8, this._infoShort4);
        int i2 = this._baseLength;
        char[] charArray = this._name.toCharArray();
        LittleEndian.putShort(bArr, this._baseLength, (short) charArray.length);
        int i3 = i2 + 2;
        for (char c : charArray) {
            LittleEndian.putShort(bArr, i3, (short) c);
            i3 += 2;
        }
        int i4 = i3 + 2;
        int i5 = 0;
        while (true) {
            UPX[] upxArr2 = this._upxs;
            if (i5 >= upxArr2.length) {
                return bArr;
            }
            short size = (short) upxArr2[i5].size();
            LittleEndian.putShort(bArr, i4, size);
            int i6 = i4 + 2;
            System.arraycopy(this._upxs[i5].getUPX(), 0, bArr, i6, size);
            i4 = i6 + size + (size % 2);
            i5++;
        }
    }

    public boolean equals(Object obj) {
        StyleDescription styleDescription = (StyleDescription) obj;
        if (styleDescription._infoShort == this._infoShort && styleDescription._infoShort2 == this._infoShort2 && styleDescription._infoShort3 == this._infoShort3 && styleDescription._bchUpe == this._bchUpe && styleDescription._infoShort4 == this._infoShort4 && this._name.equals(styleDescription._name) && Arrays.equals(this._upxs, styleDescription._upxs)) {
            return true;
        }
        return false;
    }
}
