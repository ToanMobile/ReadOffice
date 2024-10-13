package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.io.HWPFOutputStream;
import com.app.office.fc.hwpf.sprm.CharacterSprmUncompressor;
import com.app.office.fc.hwpf.sprm.ParagraphSprmUncompressor;
import com.app.office.fc.hwpf.usermodel.CharacterProperties;
import com.app.office.fc.hwpf.usermodel.ParagraphProperties;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.io.IOException;

@Internal
public final class StyleSheet implements HDFType {
    private static final int CHP_TYPE = 2;
    private static final CharacterProperties NIL_CHP = new CharacterProperties();
    private static final ParagraphProperties NIL_PAP = new ParagraphProperties();
    public static final int NIL_STYLE = 4095;
    private static final int PAP_TYPE = 1;
    private static final int SEP_TYPE = 4;
    private static final int TAP_TYPE = 5;
    private int _cbSTDBaseInFile;
    private int _cbStshi;
    private int _flags;
    private int _istdMaxFixedWhenSaved;
    private int[] _rgftcStandardChpStsh;
    private int _stiMaxWhenSaved;
    StyleDescription[] _styleDescriptions;
    private int nVerBuiltInNamesWhenSaved;

    public StyleSheet(byte[] bArr, int i) {
        this._cbStshi = LittleEndian.getShort(bArr, i);
        int i2 = i + 2;
        int uShort = LittleEndian.getUShort(bArr, i2);
        int i3 = i2 + 2;
        this._cbSTDBaseInFile = LittleEndian.getUShort(bArr, i3);
        int i4 = i3 + 2;
        this._flags = LittleEndian.getShort(bArr, i4);
        int i5 = i4 + 2;
        this._stiMaxWhenSaved = LittleEndian.getUShort(bArr, i5);
        int i6 = i5 + 2;
        this._istdMaxFixedWhenSaved = LittleEndian.getUShort(bArr, i6);
        int i7 = i6 + 2;
        this.nVerBuiltInNamesWhenSaved = LittleEndian.getUShort(bArr, i7);
        int i8 = i7 + 2;
        int[] iArr = new int[3];
        this._rgftcStandardChpStsh = iArr;
        int i9 = 0;
        iArr[0] = LittleEndian.getShort(bArr, i8);
        int i10 = i8 + 2;
        this._rgftcStandardChpStsh[1] = LittleEndian.getShort(bArr, i10);
        this._rgftcStandardChpStsh[2] = LittleEndian.getShort(bArr, i10 + 2);
        int i11 = i2 + this._cbStshi;
        this._styleDescriptions = new StyleDescription[uShort];
        for (int i12 = 0; i12 < uShort; i12++) {
            short s = LittleEndian.getShort(bArr, i11);
            int i13 = i11 + 2;
            if (s > 0) {
                this._styleDescriptions[i12] = new StyleDescription(bArr, this._cbSTDBaseInFile, i13, true);
            }
            i11 = i13 + s;
        }
        while (true) {
            StyleDescription[] styleDescriptionArr = this._styleDescriptions;
            if (i9 < styleDescriptionArr.length) {
                if (styleDescriptionArr[i9] != null) {
                    createPap(i9);
                    createChp(i9);
                }
                i9++;
            } else {
                return;
            }
        }
    }

    public void writeTo(HWPFOutputStream hWPFOutputStream) throws IOException {
        this._cbStshi = 18;
        byte[] bArr = new byte[(18 + 2)];
        LittleEndian.putUShort(bArr, 0, (short) 18);
        LittleEndian.putUShort(bArr, 2, (short) this._styleDescriptions.length);
        LittleEndian.putUShort(bArr, 4, (short) this._cbSTDBaseInFile);
        LittleEndian.putShort(bArr, 6, (short) this._flags);
        LittleEndian.putUShort(bArr, 8, (short) this._stiMaxWhenSaved);
        LittleEndian.putUShort(bArr, 10, (short) this._istdMaxFixedWhenSaved);
        LittleEndian.putUShort(bArr, 12, (short) this.nVerBuiltInNamesWhenSaved);
        LittleEndian.putShort(bArr, 14, (short) this._rgftcStandardChpStsh[0]);
        LittleEndian.putShort(bArr, 16, (short) this._rgftcStandardChpStsh[1]);
        LittleEndian.putShort(bArr, 18, (short) this._rgftcStandardChpStsh[2]);
        hWPFOutputStream.write(bArr);
        byte[] bArr2 = new byte[2];
        int i = 0;
        while (true) {
            StyleDescription[] styleDescriptionArr = this._styleDescriptions;
            if (i < styleDescriptionArr.length) {
                if (styleDescriptionArr[i] != null) {
                    byte[] byteArray = styleDescriptionArr[i].toByteArray();
                    LittleEndian.putShort(bArr2, (short) (byteArray.length + (byteArray.length % 2)));
                    hWPFOutputStream.write(bArr2);
                    hWPFOutputStream.write(byteArray);
                    if (byteArray.length % 2 == 1) {
                        hWPFOutputStream.write(0);
                    }
                } else {
                    bArr2[0] = 0;
                    bArr2[1] = 0;
                    hWPFOutputStream.write(bArr2);
                }
                i++;
            } else {
                return;
            }
        }
    }

    public boolean equals(Object obj) {
        StyleSheet styleSheet = (StyleSheet) obj;
        if (styleSheet._cbSTDBaseInFile == this._cbSTDBaseInFile && styleSheet._flags == this._flags && styleSheet._istdMaxFixedWhenSaved == this._istdMaxFixedWhenSaved && styleSheet._stiMaxWhenSaved == this._stiMaxWhenSaved) {
            int[] iArr = styleSheet._rgftcStandardChpStsh;
            int i = iArr[0];
            int[] iArr2 = this._rgftcStandardChpStsh;
            if (i == iArr2[0] && iArr[1] == iArr2[1] && iArr[2] == iArr2[2] && styleSheet._cbStshi == this._cbStshi && styleSheet.nVerBuiltInNamesWhenSaved == this.nVerBuiltInNamesWhenSaved && styleSheet._styleDescriptions.length == this._styleDescriptions.length) {
                int i2 = 0;
                while (true) {
                    StyleDescription[] styleDescriptionArr = this._styleDescriptions;
                    if (i2 >= styleDescriptionArr.length) {
                        return true;
                    }
                    StyleDescription[] styleDescriptionArr2 = styleSheet._styleDescriptions;
                    if (styleDescriptionArr2[i2] != styleDescriptionArr[i2] && !styleDescriptionArr2[i2].equals(styleDescriptionArr[i2])) {
                        return false;
                    }
                    i2++;
                }
            }
        }
        return false;
    }

    private void createPap(int i) {
        StyleDescription styleDescription = this._styleDescriptions[i];
        ParagraphProperties pap = styleDescription.getPAP();
        byte[] papx = styleDescription.getPAPX();
        int baseStyle = styleDescription.getBaseStyle();
        if (pap == null && papx != null) {
            ParagraphProperties paragraphProperties = new ParagraphProperties();
            if (baseStyle != 4095 && (paragraphProperties = this._styleDescriptions[baseStyle].getPAP()) == null) {
                if (baseStyle != i) {
                    createPap(baseStyle);
                    paragraphProperties = this._styleDescriptions[baseStyle].getPAP();
                } else {
                    throw new IllegalStateException("Pap style " + i + " claimed to have itself as its parent, which isn't allowed");
                }
            }
            if (paragraphProperties == null) {
                paragraphProperties = new ParagraphProperties();
            }
            styleDescription.setPAP(ParagraphSprmUncompressor.uncompressPAP(paragraphProperties, papx, 2));
        }
    }

    private void createChp(int i) {
        StyleDescription styleDescription = this._styleDescriptions[i];
        CharacterProperties chp = styleDescription.getCHP();
        byte[] chpx = styleDescription.getCHPX();
        int baseStyle = styleDescription.getBaseStyle();
        if (baseStyle == i) {
            baseStyle = 4095;
        }
        if (chp == null && chpx != null) {
            CharacterProperties characterProperties = new CharacterProperties();
            if (baseStyle != 4095 && (characterProperties = this._styleDescriptions[baseStyle].getCHP()) == null) {
                createChp(baseStyle);
                characterProperties = this._styleDescriptions[baseStyle].getCHP();
            }
            styleDescription.setCHP(CharacterSprmUncompressor.uncompressCHP(characterProperties, chpx, 0));
        }
    }

    public int numStyles() {
        return this._styleDescriptions.length;
    }

    public StyleDescription getStyleDescription(int i) {
        return this._styleDescriptions[i];
    }

    public CharacterProperties getCharacterStyle(int i) {
        if (i == 4095) {
            return NIL_CHP;
        }
        StyleDescription[] styleDescriptionArr = this._styleDescriptions;
        if (i >= styleDescriptionArr.length) {
            return NIL_CHP;
        }
        return styleDescriptionArr[i] != null ? styleDescriptionArr[i].getCHP() : NIL_CHP;
    }

    public ParagraphProperties getParagraphStyle(int i) {
        if (i == 4095) {
            return NIL_PAP;
        }
        StyleDescription[] styleDescriptionArr = this._styleDescriptions;
        if (i >= styleDescriptionArr.length) {
            return NIL_PAP;
        }
        if (styleDescriptionArr[i] == null) {
            return NIL_PAP;
        }
        if (styleDescriptionArr[i].getPAP() == null) {
            return NIL_PAP;
        }
        return this._styleDescriptions[i].getPAP();
    }
}
