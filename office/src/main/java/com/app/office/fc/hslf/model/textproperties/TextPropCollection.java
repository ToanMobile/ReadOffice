package com.app.office.fc.hslf.model.textproperties;

import com.app.office.fc.hslf.record.StyleTextPropAtom;
import com.app.office.fc.util.LittleEndian;
import java.util.LinkedList;

public class TextPropCollection {
    private int charactersCovered;
    private int maskSpecial = 0;
    private short reservedField;
    private LinkedList<TextProp> textPropList;

    public int getSpecialMask() {
        return this.maskSpecial;
    }

    public int getCharactersCovered() {
        return this.charactersCovered;
    }

    public LinkedList<TextProp> getTextPropList() {
        return this.textPropList;
    }

    public TextProp findByName(String str) {
        for (int i = 0; i < this.textPropList.size(); i++) {
            TextProp textProp = this.textPropList.get(i);
            if (textProp.getName().equals(str)) {
                return textProp;
            }
        }
        return null;
    }

    public TextProp addWithName(String str) {
        TextProp textProp = null;
        for (int i = 0; i < StyleTextPropAtom.characterTextPropTypes.length; i++) {
            if (StyleTextPropAtom.characterTextPropTypes[i].getName().equals(str)) {
                textProp = StyleTextPropAtom.characterTextPropTypes[i];
            }
        }
        for (int i2 = 0; i2 < StyleTextPropAtom.paragraphTextPropTypes.length; i2++) {
            if (StyleTextPropAtom.paragraphTextPropTypes[i2].getName().equals(str)) {
                textProp = StyleTextPropAtom.paragraphTextPropTypes[i2];
            }
        }
        if (textProp != null) {
            TextProp textProp2 = (TextProp) textProp.clone();
            int i3 = 0;
            for (int i4 = 0; i4 < this.textPropList.size(); i4++) {
                if (textProp2.getMask() > this.textPropList.get(i4).getMask()) {
                    i3++;
                }
            }
            this.textPropList.add(i3, textProp2);
            return textProp2;
        }
        throw new IllegalArgumentException("No TextProp with name " + str + " is defined to add from");
    }

    public int buildTextPropList(int i, TextProp[] textPropArr, byte[] bArr, int i2) {
        int i3;
        int i4 = 0;
        for (int i5 = 0; i5 < textPropArr.length; i5++) {
            if ((textPropArr[i5].getMask() & i) != 0) {
                int i6 = i2 + i4;
                if (i6 >= bArr.length) {
                    this.maskSpecial |= textPropArr[i5].getMask();
                    return i4;
                }
                TextProp textProp = (TextProp) textPropArr[i5].clone();
                if (textProp.getSize() == 2) {
                    i3 = LittleEndian.getShort(bArr, i6);
                } else if (textProp.getSize() == 4) {
                    i3 = LittleEndian.getInt(bArr, i6);
                } else if (textProp.getSize() == 0) {
                    this.maskSpecial |= textPropArr[i5].getMask();
                } else {
                    i3 = 0;
                }
                if (CharFlagsTextProp.NAME.equals(textProp.getName()) && i3 < 0) {
                    i3 = 0;
                }
                textProp.setValue(i3);
                i4 += textProp.getSize();
                if ("tabStops".equals(textProp.getName())) {
                    i4 += i3 * 4;
                }
                this.textPropList.add(textProp);
            }
        }
        return i4;
    }

    public TextPropCollection(int i, short s) {
        this.charactersCovered = i;
        this.reservedField = s;
        this.textPropList = new LinkedList<>();
    }

    public TextPropCollection(int i) {
        this.charactersCovered = i;
        this.reservedField = -1;
        this.textPropList = new LinkedList<>();
    }

    public void updateTextSize(int i) {
        this.charactersCovered = i;
    }

    public short getReservedField() {
        return this.reservedField;
    }

    public void setReservedField(short s) {
        this.reservedField = s;
    }

    public void dispose() {
        if (this.textPropList != null) {
            for (int i = 0; i < this.textPropList.size(); i++) {
                this.textPropList.get(i).dispose();
            }
            this.textPropList.clear();
            this.textPropList = null;
        }
    }
}
