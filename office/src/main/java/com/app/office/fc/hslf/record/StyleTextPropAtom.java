package com.app.office.fc.hslf.record;

import com.itextpdf.text.html.HtmlTags;
import com.app.office.fc.hslf.model.textproperties.AlignmentTextProp;
import com.app.office.fc.hslf.model.textproperties.CharFlagsTextProp;
import com.app.office.fc.hslf.model.textproperties.ParagraphFlagsTextProp;
import com.app.office.fc.hslf.model.textproperties.TextProp;
import com.app.office.fc.hslf.model.textproperties.TextPropCollection;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public final class StyleTextPropAtom extends RecordAtom {
    private static long _type = 4001;
    public static TextProp[] characterTextPropTypes = {new TextProp(0, 1, HtmlTags.BOLD), new TextProp(0, 2, HtmlTags.ITALIC), new TextProp(0, 4, HtmlTags.UNDERLINE), new TextProp(0, 8, "unused1"), new TextProp(0, 16, "shadow"), new TextProp(0, 32, "fehint"), new TextProp(0, 64, "unused2"), new TextProp(0, 128, "kumi"), new TextProp(0, 256, "unused3"), new TextProp(0, 512, "emboss"), new TextProp(0, 1024, "nibble1"), new TextProp(0, 2048, "nibble2"), new TextProp(0, 4096, "nibble3"), new TextProp(0, 8192, "nibble4"), new TextProp(0, 16384, "unused4"), new TextProp(0, 32768, "unused5"), new CharFlagsTextProp(), new TextProp(2, 65536, "font.index"), new TextProp(0, 1048576, "pp10ext"), new TextProp(2, 2097152, "asian.font.index"), new TextProp(2, 4194304, "ansi.font.index"), new TextProp(2, 8388608, "symbol.font.index"), new TextProp(2, 131072, "font.size"), new TextProp(4, 262144, "font.color"), new TextProp(2, 524288, "superscript")};
    public static TextProp[] paragraphTextPropTypes = {new TextProp(0, 1, "hasBullet"), new TextProp(0, 2, "hasBulletFont"), new TextProp(0, 4, "hasBulletColor"), new TextProp(0, 8, "hasBulletSize"), new ParagraphFlagsTextProp(), new TextProp(2, 128, "bullet.char"), new TextProp(2, 16, "bullet.font"), new TextProp(2, 64, "bullet.size"), new TextProp(4, 32, "bullet.color"), new AlignmentTextProp(), new TextProp(2, 256, "text.offset"), new TextProp(2, 1024, "bullet.offset"), new TextProp(2, 4096, "linespacing"), new TextProp(2, 8192, "spacebefore"), new TextProp(2, 16384, "spaceafter"), new TextProp(2, 32768, "defaultTabSize"), new TextProp(2, 1048576, "tabStops"), new TextProp(2, 65536, "fontAlign"), new TextProp(2, 917504, "wrapFlags"), new TextProp(2, 2097152, "textDirection"), new TextProp(2, 16777216, "buletScheme"), new TextProp(2, 33554432, "bulletHasScheme")};
    private byte[] _header;
    private LinkedList<TextPropCollection> charStyles;
    private boolean initialised = false;
    private Map<Integer, Integer> paraAutoNumberIndexs = new HashMap();
    private LinkedList<TextPropCollection> paragraphStyles;
    private byte[] rawContents;
    private byte[] reserved;

    public LinkedList<TextPropCollection> getParagraphStyles() {
        return this.paragraphStyles;
    }

    public void setParagraphStyles(LinkedList<TextPropCollection> linkedList) {
        this.paragraphStyles = linkedList;
    }

    public LinkedList<TextPropCollection> getCharacterStyles() {
        return this.charStyles;
    }

    public void setCharacterStyles(LinkedList<TextPropCollection> linkedList) {
        this.charStyles = linkedList;
    }

    public int getParagraphTextLengthCovered() {
        return getCharactersCovered(this.paragraphStyles);
    }

    public int getCharacterTextLengthCovered() {
        return getCharactersCovered(this.charStyles);
    }

    private int getCharactersCovered(LinkedList<TextPropCollection> linkedList) {
        Iterator it = linkedList.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += ((TextPropCollection) it.next()).getCharactersCovered();
        }
        return i;
    }

    public StyleTextPropAtom(byte[] bArr, int i, int i2) {
        if (i2 < 18) {
            if (bArr.length - i >= 18) {
                i2 = 18;
            } else {
                throw new RuntimeException("Not enough data to form a StyleTextPropAtom (min size 18 bytes long) - found " + (bArr.length - i));
            }
        }
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        byte[] bArr3 = new byte[(i2 - 8)];
        this.rawContents = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, bArr3.length);
        this.reserved = new byte[0];
        this.paragraphStyles = new LinkedList<>();
        this.charStyles = new LinkedList<>();
    }

    public StyleTextPropAtom(int i) {
        byte[] bArr = new byte[8];
        this._header = bArr;
        this.rawContents = new byte[0];
        this.reserved = new byte[0];
        LittleEndian.putInt(bArr, 2, (short) ((int) _type));
        LittleEndian.putInt(this._header, 4, 10);
        this.paragraphStyles = new LinkedList<>();
        this.charStyles = new LinkedList<>();
        this.paragraphStyles.add(new TextPropCollection(i, 0));
        this.charStyles.add(new TextPropCollection(i));
        this.initialised = true;
    }

    public long getRecordType() {
        return _type;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        updateRawContents();
        LittleEndian.putInt(this._header, 4, this.rawContents.length + this.reserved.length);
        outputStream.write(this._header);
        outputStream.write(this.rawContents);
        outputStream.write(this.reserved);
    }

    public void setParentTextSize(int i) {
        byte[] bArr;
        TextProp findByName;
        this.paraAutoNumberIndexs.clear();
        int i2 = i;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            byte[] bArr2 = this.rawContents;
            if (i3 >= bArr2.length || i4 >= i2) {
                int i7 = i;
                int i8 = 0;
            } else {
                int i9 = LittleEndian.getInt(bArr2, i3);
                i4 += i9;
                int i10 = i3 + 4;
                short s = LittleEndian.getShort(this.rawContents, i10);
                int i11 = i10 + 2;
                int i12 = LittleEndian.getInt(this.rawContents, i11);
                int i13 = i11 + 4;
                TextPropCollection textPropCollection = new TextPropCollection(i9, s);
                i3 = i13 + textPropCollection.buildTextPropList(i12, paragraphTextPropTypes, this.rawContents, i13);
                this.paragraphStyles.add(textPropCollection);
                if (i3 < this.rawContents.length && i4 == i) {
                    i2++;
                }
                if (i5 > 0) {
                    TextProp findByName2 = textPropCollection.findByName("paragraph_flags");
                    int value = findByName2 != null ? findByName2.getValue() : 0;
                    if (value != 1) {
                        TextProp findByName3 = textPropCollection.findByName("bullet.char");
                        int value2 = findByName3 != null ? findByName3.getValue() : 0;
                        if (value != 2) {
                            if (!(value2 == 8226 || value2 == 8211)) {
                                TextPropCollection textPropCollection2 = this.paragraphStyles.get(i5 - 1);
                                if (!(textPropCollection2 == null || (findByName = textPropCollection2.findByName("bullet.char")) == null)) {
                                    value2 = findByName.getValue();
                                }
                                if (!(value2 == 8226 || value2 == 8211)) {
                                }
                            }
                        }
                    }
                    i6++;
                }
                this.paraAutoNumberIndexs.put(Integer.valueOf(i5), Integer.valueOf(i6));
                i5++;
            }
        }
        int i72 = i;
        int i82 = 0;
        while (true) {
            bArr = this.rawContents;
            if (i3 < bArr.length && i82 < i72) {
                int i14 = LittleEndian.getInt(bArr, i3);
                i82 += i14;
                int i15 = i3 + 4;
                int i16 = LittleEndian.getInt(this.rawContents, i15);
                int i17 = i15 + 4;
                TextPropCollection textPropCollection3 = new TextPropCollection(i14, -1);
                i3 = i17 + textPropCollection3.buildTextPropList(i16, characterTextPropTypes, this.rawContents, i17);
                this.charStyles.add(textPropCollection3);
                if (i3 < this.rawContents.length && i82 == i) {
                    i72++;
                }
            }
        }
        if (i3 < bArr.length) {
            byte[] bArr3 = new byte[(bArr.length - i3)];
            this.reserved = bArr3;
            System.arraycopy(bArr, i3, bArr3, 0, bArr3.length);
        }
        this.initialised = true;
    }

    private void updateRawContents() throws IOException {
        if (this.initialised) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int i = 0; i < this.paragraphStyles.size(); i++) {
                TextPropCollection textPropCollection = this.paragraphStyles.get(i);
            }
            for (int i2 = 0; i2 < this.charStyles.size(); i2++) {
                TextPropCollection textPropCollection2 = this.charStyles.get(i2);
            }
            this.rawContents = byteArrayOutputStream.toByteArray();
        }
    }

    public void setRawContents(byte[] bArr) {
        this.rawContents = bArr;
        this.reserved = new byte[0];
        this.initialised = false;
    }

    public TextPropCollection addParagraphTextPropCollection(int i) {
        TextPropCollection textPropCollection = new TextPropCollection(i, 0);
        this.paragraphStyles.add(textPropCollection);
        return textPropCollection;
    }

    public TextPropCollection addCharacterTextPropCollection(int i) {
        TextPropCollection textPropCollection = new TextPropCollection(i);
        this.charStyles.add(textPropCollection);
        return textPropCollection;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("StyleTextPropAtom:\n");
        if (!this.initialised) {
            stringBuffer.append("Uninitialised, dumping Raw Style Data\n");
        } else {
            stringBuffer.append("Paragraph properties\n");
            Iterator it = getParagraphStyles().iterator();
            while (it.hasNext()) {
                TextPropCollection textPropCollection = (TextPropCollection) it.next();
                stringBuffer.append("  chars covered: " + textPropCollection.getCharactersCovered());
                stringBuffer.append("  special mask flags: 0x" + HexDump.toHex(textPropCollection.getSpecialMask()) + "\n");
                Iterator it2 = textPropCollection.getTextPropList().iterator();
                while (it2.hasNext()) {
                    TextProp textProp = (TextProp) it2.next();
                    stringBuffer.append("    " + textProp.getName() + " = " + textProp.getValue());
                    StringBuilder sb = new StringBuilder();
                    sb.append(" (0x");
                    sb.append(HexDump.toHex(textProp.getValue()));
                    sb.append(")\n");
                    stringBuffer.append(sb.toString());
                }
                stringBuffer.append("  para bytes that would be written: \n");
                try {
                    stringBuffer.append(HexDump.dump(new ByteArrayOutputStream().toByteArray(), 0, 0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            stringBuffer.append("Character properties\n");
            Iterator it3 = getCharacterStyles().iterator();
            while (it3.hasNext()) {
                TextPropCollection textPropCollection2 = (TextPropCollection) it3.next();
                stringBuffer.append("  chars covered: " + textPropCollection2.getCharactersCovered());
                stringBuffer.append("  special mask flags: 0x" + HexDump.toHex(textPropCollection2.getSpecialMask()) + "\n");
                Iterator it4 = textPropCollection2.getTextPropList().iterator();
                while (it4.hasNext()) {
                    TextProp textProp2 = (TextProp) it4.next();
                    stringBuffer.append("    " + textProp2.getName() + " = " + textProp2.getValue());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(" (0x");
                    sb2.append(HexDump.toHex(textProp2.getValue()));
                    sb2.append(")\n");
                    stringBuffer.append(sb2.toString());
                }
                stringBuffer.append("  char bytes that would be written: \n");
                try {
                    stringBuffer.append(HexDump.dump(new ByteArrayOutputStream().toByteArray(), 0, 0));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        stringBuffer.append("  original byte stream \n");
        stringBuffer.append(HexDump.dump(this.rawContents, 0, 0));
        return stringBuffer.toString();
    }

    public int getAutoNumberIndex(int i) {
        Integer num;
        int i2 = 0;
        if (this.paragraphStyles != null) {
            int i3 = 0;
            int i4 = 0;
            while (true) {
                if (i3 >= this.paragraphStyles.size()) {
                    break;
                }
                int charactersCovered = (this.paragraphStyles.get(i3).getCharactersCovered() + i4) - 1;
                if (i >= i4 && i <= charactersCovered) {
                    i2 = i3;
                    break;
                }
                i4 = charactersCovered + 1;
                i3++;
            }
        }
        if (i2 < 0 || i2 >= this.paraAutoNumberIndexs.size() || (num = this.paraAutoNumberIndexs.get(Integer.valueOf(i2))) == null) {
            return -1;
        }
        return num.intValue();
    }

    public void dispose() {
        this._header = null;
        this.reserved = null;
        this.rawContents = null;
        Map<Integer, Integer> map = this.paraAutoNumberIndexs;
        if (map != null) {
            map.clear();
            this.paraAutoNumberIndexs = null;
        }
    }
}
