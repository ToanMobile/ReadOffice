package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.sprm.CharacterSprmUncompressor;
import com.app.office.fc.hwpf.sprm.SprmBuffer;
import com.app.office.fc.hwpf.usermodel.CharacterProperties;
import com.app.office.fc.util.Internal;

@Internal
public final class CHPX extends BytePropertyNode<CHPX> {
    @Deprecated
    public CHPX(int i, int i2, CharIndexTranslator charIndexTranslator, byte[] bArr) {
        super(i, charIndexTranslator.lookIndexBackward(i2), charIndexTranslator, new SprmBuffer(bArr, 0));
    }

    @Deprecated
    public CHPX(int i, int i2, CharIndexTranslator charIndexTranslator, SprmBuffer sprmBuffer) {
        super(i, charIndexTranslator.lookIndexBackward(i2), charIndexTranslator, sprmBuffer);
    }

    CHPX(int i, int i2, SprmBuffer sprmBuffer) {
        super(i, i2, sprmBuffer);
    }

    public byte[] getGrpprl() {
        return ((SprmBuffer) this._buf).toByteArray();
    }

    public SprmBuffer getSprmBuf() {
        return (SprmBuffer) this._buf;
    }

    public CharacterProperties getCharacterProperties(StyleSheet styleSheet, short s) {
        if (styleSheet == null) {
            return new CharacterProperties();
        }
        CharacterProperties characterStyle = styleSheet.getCharacterStyle(s);
        if (characterStyle == null) {
            characterStyle = new CharacterProperties();
        }
        return CharacterSprmUncompressor.uncompressCHP(characterStyle, getGrpprl(), 0);
    }

    public String toString() {
        return "CHPX from " + getStart() + " to " + getEnd() + " (in bytes " + getStartBytes() + " to " + getEndBytes() + ")";
    }
}
