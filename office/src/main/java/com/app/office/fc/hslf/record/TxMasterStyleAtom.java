package com.app.office.fc.hslf.record;

import com.app.office.fc.hslf.model.textproperties.AlignmentTextProp;
import com.app.office.fc.hslf.model.textproperties.CharFlagsTextProp;
import com.app.office.fc.hslf.model.textproperties.ParagraphFlagsTextProp;
import com.app.office.fc.hslf.model.textproperties.TextProp;
import com.app.office.fc.hslf.model.textproperties.TextPropCollection;
import com.app.office.fc.ss.util.CellUtil;
import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;

public final class TxMasterStyleAtom extends RecordAtom {
    private static final int MAX_INDENT = 5;
    private static long _type = 4003;
    private byte[] _data;
    private byte[] _header;
    private TextPropCollection[] chstyles;
    private TextPropCollection[] prstyles;

    protected TxMasterStyleAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        byte[] bArr3 = new byte[(i2 - 8)];
        this._data = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, bArr3.length);
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getRecordType() {
        return _type;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        outputStream.write(this._header);
        outputStream.write(this._data);
    }

    public TextPropCollection[] getCharacterStyles() {
        return this.chstyles;
    }

    public TextPropCollection[] getParagraphStyles() {
        return this.prstyles;
    }

    public int getTextType() {
        return LittleEndian.getShort(this._header, 0) >> 4;
    }

    /* access modifiers changed from: protected */
    public void init() {
        int textType = getTextType();
        int i = LittleEndian.getShort(this._data, 0);
        this.prstyles = new TextPropCollection[i];
        this.chstyles = new TextPropCollection[i];
        int i2 = 2;
        for (short s = 0; s < i; s = (short) (s + 1)) {
            if (textType >= 5) {
                LittleEndian.getShort(this._data, i2);
                i2 += 2;
            }
            int i3 = LittleEndian.getInt(this._data, i2);
            int i4 = i2 + 4;
            TextPropCollection textPropCollection = new TextPropCollection(0);
            int buildTextPropList = i4 + textPropCollection.buildTextPropList(i3, getParagraphProps(textType, s), this._data, i4);
            this.prstyles[s] = textPropCollection;
            int i5 = LittleEndian.getInt(this._data, buildTextPropList);
            int i6 = buildTextPropList + 4;
            TextPropCollection textPropCollection2 = new TextPropCollection(0);
            i2 = i6 + textPropCollection2.buildTextPropList(i5, getCharacterProps(textType, s), this._data, i6);
            this.chstyles[s] = textPropCollection2;
        }
    }

    /* access modifiers changed from: protected */
    public TextProp[] getParagraphProps(int i, int i2) {
        if (i2 != 0 || i >= 5) {
            return new TextProp[]{new TextProp(0, 1, "hasBullet"), new TextProp(0, 2, "hasBulletFont"), new TextProp(0, 4, "hasBulletColor"), new TextProp(0, 8, "hasBulletSize"), new ParagraphFlagsTextProp(), new TextProp(2, 128, "bullet.char"), new TextProp(2, 16, "bullet.font"), new TextProp(2, 64, "bullet.size"), new TextProp(4, 32, "bullet.color"), new AlignmentTextProp(), new TextProp(2, 4096, "linespacing"), new TextProp(2, 8192, "spacebefore"), new TextProp(2, 256, "text.offset"), new TextProp(2, 1024, "bullet.offset"), new TextProp(2, 16384, "spaceafter"), new TextProp(2, 32768, "defaultTabSize"), new TextProp(2, 1048576, "tabStops"), new TextProp(2, 65536, "fontAlign"), new TextProp(2, 917504, "wrapFlags"), new TextProp(2, 2097152, "textDirection"), new TextProp(2, 16777216, "buletScheme"), new TextProp(2, 33554432, "bulletHasScheme")};
        }
        return new TextProp[]{new ParagraphFlagsTextProp(), new TextProp(2, 128, "bullet.char"), new TextProp(2, 16, "bullet.font"), new TextProp(2, 64, "bullet.size"), new TextProp(4, 32, "bullet.color"), new TextProp(2, 3328, CellUtil.ALIGNMENT), new TextProp(2, 4096, "linespacing"), new TextProp(2, 8192, "spacebefore"), new TextProp(2, 16384, "spaceafter"), new TextProp(2, 32768, "text.offset"), new TextProp(2, 65536, "bullet.offset"), new TextProp(2, 131072, "defaulttab"), new TextProp(2, 262144, "tabStops"), new TextProp(2, 524288, "fontAlign"), new TextProp(2, 1048576, "para_unknown_1"), new TextProp(2, 2097152, "para_unknown_2")};
    }

    /* access modifiers changed from: protected */
    public TextProp[] getCharacterProps(int i, int i2) {
        if (i2 != 0 || i >= 5) {
            return StyleTextPropAtom.characterTextPropTypes;
        }
        return new TextProp[]{new CharFlagsTextProp(), new TextProp(2, 65536, "font.index"), new TextProp(2, 131072, "char_unknown_1"), new TextProp(4, 262144, "char_unknown_2"), new TextProp(2, 524288, "font.size"), new TextProp(2, 1048576, "char_unknown_3"), new TextProp(4, 2097152, "font.color"), new TextProp(2, 8388608, "char_unknown_4")};
    }

    public void dispose() {
        this._header = null;
        this._data = null;
        TextPropCollection[] textPropCollectionArr = this.prstyles;
        if (textPropCollectionArr != null) {
            for (TextPropCollection dispose : textPropCollectionArr) {
                dispose.dispose();
            }
            this.prstyles = null;
        }
        TextPropCollection[] textPropCollectionArr2 = this.chstyles;
        if (textPropCollectionArr2 != null) {
            for (TextPropCollection dispose2 : textPropCollectionArr2) {
                dispose2.dispose();
            }
            this.chstyles = null;
        }
    }
}
