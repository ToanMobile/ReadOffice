package com.app.office.fc.hssf.record.common;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.cont.ContinuableRecordInput;
import com.app.office.fc.hssf.record.cont.ContinuableRecordOutput;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import com.app.office.fc.util.StringUtil;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class UnicodeString implements Comparable<UnicodeString> {
    /* access modifiers changed from: private */
    public static POILogger _logger = POILogFactory.getLogger(UnicodeString.class);
    private static final BitField extBit = BitFieldFactory.getInstance(4);
    private static final BitField highByte = BitFieldFactory.getInstance(1);
    private static final BitField richText = BitFieldFactory.getInstance(8);
    private short field_1_charCount;
    private byte field_2_optionflags;
    private String field_3_string;
    private List<FormatRun> field_4_format_runs;
    private ExtRst field_5_ext_rst;

    public static class FormatRun implements Comparable<FormatRun> {
        final short _character;
        short _fontIndex;

        public FormatRun(short s, short s2) {
            this._character = s;
            this._fontIndex = s2;
        }

        public FormatRun(LittleEndianInput littleEndianInput) {
            this(littleEndianInput.readShort(), littleEndianInput.readShort());
        }

        public short getCharacterPos() {
            return this._character;
        }

        public short getFontIndex() {
            return this._fontIndex;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof FormatRun)) {
                return false;
            }
            FormatRun formatRun = (FormatRun) obj;
            if (this._character == formatRun._character && this._fontIndex == formatRun._fontIndex) {
                return true;
            }
            return false;
        }

        public int compareTo(FormatRun formatRun) {
            short s = this._character;
            short s2 = formatRun._character;
            if (s == s2 && this._fontIndex == formatRun._fontIndex) {
                return 0;
            }
            return s == s2 ? this._fontIndex - formatRun._fontIndex : s - s2;
        }

        public String toString() {
            return "character=" + this._character + ",fontIndex=" + this._fontIndex;
        }

        public void serialize(LittleEndianOutput littleEndianOutput) {
            littleEndianOutput.writeShort(this._character);
            littleEndianOutput.writeShort(this._fontIndex);
        }
    }

    public static class ExtRst implements Comparable<ExtRst> {
        private byte[] extraData;
        private short formattingFontIndex;
        private short formattingOptions;
        private int numberOfRuns;
        private PhRun[] phRuns;
        private String phoneticText;
        private short reserved;

        private void populateEmpty() {
            this.reserved = 1;
            this.phoneticText = "";
            this.phRuns = new PhRun[0];
            this.extraData = new byte[0];
        }

        protected ExtRst() {
            populateEmpty();
        }

        protected ExtRst(LittleEndianInput littleEndianInput, int i) {
            short readShort = littleEndianInput.readShort();
            this.reserved = readShort;
            if (readShort == -1) {
                populateEmpty();
                return;
            }
            int i2 = 0;
            if (readShort != 1) {
                POILogger access$000 = UnicodeString._logger;
                int i3 = POILogger.WARN;
                access$000.log(i3, (Object) "Warning - ExtRst has wrong magic marker, expecting 1 but found " + this.reserved + " - ignoring");
                while (i2 < i - 2) {
                    littleEndianInput.readByte();
                    i2++;
                }
                populateEmpty();
                return;
            }
            short readShort2 = littleEndianInput.readShort();
            this.formattingFontIndex = littleEndianInput.readShort();
            this.formattingOptions = littleEndianInput.readShort();
            this.numberOfRuns = littleEndianInput.readUShort();
            short readShort3 = littleEndianInput.readShort();
            short readShort4 = littleEndianInput.readShort();
            if (readShort3 == 0 && readShort4 > 0) {
                readShort4 = 0;
            }
            if (readShort3 == readShort4) {
                String readUnicodeLE = StringUtil.readUnicodeLE(littleEndianInput, readShort3);
                this.phoneticText = readUnicodeLE;
                int length = ((readShort2 - 4) - 6) - (readUnicodeLE.length() * 2);
                int i4 = length / 6;
                this.phRuns = new PhRun[i4];
                int i5 = 0;
                while (true) {
                    PhRun[] phRunArr = this.phRuns;
                    if (i5 >= phRunArr.length) {
                        break;
                    }
                    phRunArr[i5] = new PhRun(littleEndianInput);
                    i5++;
                }
                int i6 = length - (i4 * 6);
                if (i6 < 0) {
                    PrintStream printStream = System.err;
                    printStream.println("Warning - ExtRst overran by " + (0 - i6) + " bytes");
                    i6 = 0;
                }
                this.extraData = new byte[i6];
                while (true) {
                    byte[] bArr = this.extraData;
                    if (i2 < bArr.length) {
                        bArr[i2] = littleEndianInput.readByte();
                        i2++;
                    } else {
                        return;
                    }
                }
            } else {
                throw new IllegalStateException("The two length fields of the Phonetic Text don't agree! " + readShort3 + " vs " + readShort4);
            }
        }

        /* access modifiers changed from: protected */
        public int getDataSize() {
            return (this.phoneticText.length() * 2) + 10 + (this.phRuns.length * 6) + this.extraData.length;
        }

        /* access modifiers changed from: protected */
        public void serialize(ContinuableRecordOutput continuableRecordOutput) {
            int dataSize = getDataSize();
            continuableRecordOutput.writeContinueIfRequired(8);
            continuableRecordOutput.writeShort(this.reserved);
            continuableRecordOutput.writeShort(dataSize);
            continuableRecordOutput.writeShort(this.formattingFontIndex);
            continuableRecordOutput.writeShort(this.formattingOptions);
            continuableRecordOutput.writeContinueIfRequired(6);
            continuableRecordOutput.writeShort(this.numberOfRuns);
            continuableRecordOutput.writeShort(this.phoneticText.length());
            continuableRecordOutput.writeShort(this.phoneticText.length());
            continuableRecordOutput.writeContinueIfRequired(this.phoneticText.length() * 2);
            StringUtil.putUnicodeLE(this.phoneticText, continuableRecordOutput);
            int i = 0;
            while (true) {
                PhRun[] phRunArr = this.phRuns;
                if (i < phRunArr.length) {
                    phRunArr[i].serialize(continuableRecordOutput);
                    i++;
                } else {
                    continuableRecordOutput.write(this.extraData);
                    return;
                }
            }
        }

        public boolean equals(Object obj) {
            if ((obj instanceof ExtRst) && compareTo((ExtRst) obj) == 0) {
                return true;
            }
            return false;
        }

        public int compareTo(ExtRst extRst) {
            int i = this.reserved - extRst.reserved;
            if (i != 0) {
                return i;
            }
            int i2 = this.formattingFontIndex - extRst.formattingFontIndex;
            if (i2 != 0) {
                return i2;
            }
            int i3 = this.formattingOptions - extRst.formattingOptions;
            if (i3 != 0) {
                return i3;
            }
            int i4 = this.numberOfRuns - extRst.numberOfRuns;
            if (i4 != 0) {
                return i4;
            }
            int compareTo = this.phoneticText.compareTo(extRst.phoneticText);
            if (compareTo != 0) {
                return compareTo;
            }
            int length = this.phRuns.length - extRst.phRuns.length;
            if (length != 0) {
                return length;
            }
            int i5 = 0;
            while (true) {
                PhRun[] phRunArr = this.phRuns;
                if (i5 < phRunArr.length) {
                    int access$300 = phRunArr[i5].phoneticTextFirstCharacterOffset - extRst.phRuns[i5].phoneticTextFirstCharacterOffset;
                    if (access$300 != 0) {
                        return access$300;
                    }
                    int access$400 = this.phRuns[i5].realTextFirstCharacterOffset - extRst.phRuns[i5].realTextFirstCharacterOffset;
                    if (access$400 != 0) {
                        return access$400;
                    }
                    int access$4002 = this.phRuns[i5].realTextFirstCharacterOffset - extRst.phRuns[i5].realTextLength;
                    if (access$4002 != 0) {
                        return access$4002;
                    }
                    i5++;
                } else {
                    int length2 = this.extraData.length - extRst.extraData.length;
                    if (length2 != 0) {
                        return length2;
                    }
                    return 0;
                }
            }
        }

        /* access modifiers changed from: protected */
        public ExtRst clone() {
            ExtRst extRst = new ExtRst();
            extRst.reserved = this.reserved;
            extRst.formattingFontIndex = this.formattingFontIndex;
            extRst.formattingOptions = this.formattingOptions;
            extRst.numberOfRuns = this.numberOfRuns;
            extRst.phoneticText = this.phoneticText;
            extRst.phRuns = new PhRun[this.phRuns.length];
            int i = 0;
            while (true) {
                PhRun[] phRunArr = extRst.phRuns;
                if (i >= phRunArr.length) {
                    return extRst;
                }
                phRunArr[i] = new PhRun(this.phRuns[i].phoneticTextFirstCharacterOffset, this.phRuns[i].realTextFirstCharacterOffset, this.phRuns[i].realTextLength);
                i++;
            }
        }

        public short getFormattingFontIndex() {
            return this.formattingFontIndex;
        }

        public short getFormattingOptions() {
            return this.formattingOptions;
        }

        public int getNumberOfRuns() {
            return this.numberOfRuns;
        }

        public String getPhoneticText() {
            return this.phoneticText;
        }

        public PhRun[] getPhRuns() {
            return this.phRuns;
        }
    }

    public static class PhRun {
        /* access modifiers changed from: private */
        public int phoneticTextFirstCharacterOffset;
        /* access modifiers changed from: private */
        public int realTextFirstCharacterOffset;
        /* access modifiers changed from: private */
        public int realTextLength;

        public PhRun(int i, int i2, int i3) {
            this.phoneticTextFirstCharacterOffset = i;
            this.realTextFirstCharacterOffset = i2;
            this.realTextLength = i3;
        }

        private PhRun(LittleEndianInput littleEndianInput) {
            this.phoneticTextFirstCharacterOffset = littleEndianInput.readUShort();
            this.realTextFirstCharacterOffset = littleEndianInput.readUShort();
            this.realTextLength = littleEndianInput.readUShort();
        }

        /* access modifiers changed from: private */
        public void serialize(ContinuableRecordOutput continuableRecordOutput) {
            continuableRecordOutput.writeContinueIfRequired(6);
            continuableRecordOutput.writeShort(this.phoneticTextFirstCharacterOffset);
            continuableRecordOutput.writeShort(this.realTextFirstCharacterOffset);
            continuableRecordOutput.writeShort(this.realTextLength);
        }
    }

    private UnicodeString() {
    }

    public UnicodeString(String str) {
        setString(str);
    }

    public int hashCode() {
        String str = this.field_3_string;
        return this.field_1_charCount + (str != null ? str.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        int size;
        ExtRst extRst;
        if (!(obj instanceof UnicodeString)) {
            return false;
        }
        UnicodeString unicodeString = (UnicodeString) obj;
        if (!(this.field_1_charCount == unicodeString.field_1_charCount && this.field_2_optionflags == unicodeString.field_2_optionflags && this.field_3_string.equals(unicodeString.field_3_string))) {
            return false;
        }
        List<FormatRun> list = this.field_4_format_runs;
        if (list == null && unicodeString.field_4_format_runs == null) {
            return true;
        }
        if ((list == null && unicodeString.field_4_format_runs != null) || ((list != null && unicodeString.field_4_format_runs == null) || (size = list.size()) != unicodeString.field_4_format_runs.size())) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!this.field_4_format_runs.get(i).equals(unicodeString.field_4_format_runs.get(i))) {
                return false;
            }
        }
        ExtRst extRst2 = this.field_5_ext_rst;
        if ((extRst2 != null || unicodeString.field_5_ext_rst != null) && (extRst2 == null || (extRst = unicodeString.field_5_ext_rst) == null || extRst2.compareTo(extRst) != 0)) {
            return false;
        }
        return true;
    }

    public UnicodeString(RecordInputStream recordInputStream) {
        this.field_1_charCount = recordInputStream.readShort();
        this.field_2_optionflags = recordInputStream.readByte();
        short readShort = isRichText() ? recordInputStream.readShort() : 0;
        int readInt = isExtendedText() ? recordInputStream.readInt() : 0;
        if ((this.field_2_optionflags & 1) != 0 ? false : true) {
            this.field_3_string = recordInputStream.readCompressedUnicode(getCharCount());
        } else {
            this.field_3_string = recordInputStream.readUnicodeLEString(getCharCount());
        }
        if (isRichText() && readShort > 0) {
            this.field_4_format_runs = new ArrayList(readShort);
            for (int i = 0; i < readShort; i++) {
                this.field_4_format_runs.add(new FormatRun(recordInputStream));
            }
        }
        if (isExtendedText() && readInt > 0) {
            ExtRst extRst = new ExtRst(new ContinuableRecordInput(recordInputStream), readInt);
            this.field_5_ext_rst = extRst;
            if (extRst.getDataSize() + 4 != readInt) {
                POILogger pOILogger = _logger;
                int i2 = POILogger.WARN;
                pOILogger.log(i2, (Object) "ExtRst was supposed to be " + readInt + " bytes long, but seems to actually be " + (this.field_5_ext_rst.getDataSize() + 4));
            }
        }
    }

    public int getCharCount() {
        short s = this.field_1_charCount;
        return s < 0 ? s + 65536 : s;
    }

    public short getCharCountShort() {
        return this.field_1_charCount;
    }

    public void setCharCount(short s) {
        this.field_1_charCount = s;
    }

    public byte getOptionFlags() {
        return this.field_2_optionflags;
    }

    public void setOptionFlags(byte b) {
        this.field_2_optionflags = b;
    }

    public String getString() {
        return this.field_3_string;
    }

    public void setString(String str) {
        this.field_3_string = str;
        setCharCount((short) str.length());
        int length = str.length();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (str.charAt(i) > 255) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (z) {
            this.field_2_optionflags = highByte.setByte(this.field_2_optionflags);
        } else {
            this.field_2_optionflags = highByte.clearByte(this.field_2_optionflags);
        }
    }

    public int getFormatRunCount() {
        List<FormatRun> list = this.field_4_format_runs;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public FormatRun getFormatRun(int i) {
        List<FormatRun> list = this.field_4_format_runs;
        if (list != null && i >= 0 && i < list.size()) {
            return this.field_4_format_runs.get(i);
        }
        return null;
    }

    private int findFormatRunAt(int i) {
        int size = this.field_4_format_runs.size();
        for (int i2 = 0; i2 < size; i2++) {
            FormatRun formatRun = this.field_4_format_runs.get(i2);
            if (formatRun._character == i) {
                return i2;
            }
            if (formatRun._character > i) {
                return -1;
            }
        }
        return -1;
    }

    public void addFormatRun(FormatRun formatRun) {
        if (this.field_4_format_runs == null) {
            this.field_4_format_runs = new ArrayList();
        }
        int findFormatRunAt = findFormatRunAt(formatRun._character);
        if (findFormatRunAt != -1) {
            this.field_4_format_runs.remove(findFormatRunAt);
        }
        this.field_4_format_runs.add(formatRun);
        Collections.sort(this.field_4_format_runs);
        this.field_2_optionflags = richText.setByte(this.field_2_optionflags);
    }

    public Iterator<FormatRun> formatIterator() {
        List<FormatRun> list = this.field_4_format_runs;
        if (list != null) {
            return list.iterator();
        }
        return null;
    }

    public void removeFormatRun(FormatRun formatRun) {
        this.field_4_format_runs.remove(formatRun);
        if (this.field_4_format_runs.size() == 0) {
            this.field_4_format_runs = null;
            this.field_2_optionflags = richText.clearByte(this.field_2_optionflags);
        }
    }

    public void clearFormatting() {
        this.field_4_format_runs = null;
        this.field_2_optionflags = richText.clearByte(this.field_2_optionflags);
    }

    public ExtRst getExtendedRst() {
        return this.field_5_ext_rst;
    }

    /* access modifiers changed from: package-private */
    public void setExtendedRst(ExtRst extRst) {
        if (extRst != null) {
            this.field_2_optionflags = extBit.setByte(this.field_2_optionflags);
        } else {
            this.field_2_optionflags = extBit.clearByte(this.field_2_optionflags);
        }
        this.field_5_ext_rst = extRst;
    }

    public void swapFontUse(short s, short s2) {
        for (FormatRun next : this.field_4_format_runs) {
            if (next._fontIndex == s) {
                next._fontIndex = s2;
            }
        }
    }

    public String toString() {
        return getString();
    }

    public String getDebugInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[UNICODESTRING]\n");
        stringBuffer.append("    .charcount       = ");
        stringBuffer.append(Integer.toHexString(getCharCount()));
        stringBuffer.append("\n");
        stringBuffer.append("    .optionflags     = ");
        stringBuffer.append(Integer.toHexString(getOptionFlags()));
        stringBuffer.append("\n");
        stringBuffer.append("    .string          = ");
        stringBuffer.append(getString());
        stringBuffer.append("\n");
        if (this.field_4_format_runs != null) {
            for (int i = 0; i < this.field_4_format_runs.size(); i++) {
                stringBuffer.append("      .format_run" + i + "          = ");
                stringBuffer.append(this.field_4_format_runs.get(i).toString());
                stringBuffer.append("\n");
            }
        }
        if (this.field_5_ext_rst != null) {
            stringBuffer.append("    .field_5_ext_rst          = ");
            stringBuffer.append("\n");
            stringBuffer.append(this.field_5_ext_rst.toString());
            stringBuffer.append("\n");
        }
        stringBuffer.append("[/UNICODESTRING]\n");
        return stringBuffer.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0007, code lost:
        r0 = r5.field_4_format_runs;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void serialize(com.app.office.fc.hssf.record.cont.ContinuableRecordOutput r6) {
        /*
            r5 = this;
            boolean r0 = r5.isRichText()
            r1 = 0
            if (r0 == 0) goto L_0x0010
            java.util.List<com.app.office.fc.hssf.record.common.UnicodeString$FormatRun> r0 = r5.field_4_format_runs
            if (r0 == 0) goto L_0x0010
            int r0 = r0.size()
            goto L_0x0011
        L_0x0010:
            r0 = 0
        L_0x0011:
            boolean r2 = r5.isExtendedText()
            r3 = 4
            if (r2 == 0) goto L_0x0022
            com.app.office.fc.hssf.record.common.UnicodeString$ExtRst r2 = r5.field_5_ext_rst
            if (r2 == 0) goto L_0x0022
            int r2 = r2.getDataSize()
            int r2 = r2 + r3
            goto L_0x0023
        L_0x0022:
            r2 = 0
        L_0x0023:
            java.lang.String r4 = r5.field_3_string
            r6.writeString(r4, r0, r2)
            if (r0 <= 0) goto L_0x0043
        L_0x002a:
            if (r1 >= r0) goto L_0x0043
            int r4 = r6.getAvailableSpace()
            if (r4 >= r3) goto L_0x0035
            r6.writeContinue()
        L_0x0035:
            java.util.List<com.app.office.fc.hssf.record.common.UnicodeString$FormatRun> r4 = r5.field_4_format_runs
            java.lang.Object r4 = r4.get(r1)
            com.app.office.fc.hssf.record.common.UnicodeString$FormatRun r4 = (com.app.office.fc.hssf.record.common.UnicodeString.FormatRun) r4
            r4.serialize(r6)
            int r1 = r1 + 1
            goto L_0x002a
        L_0x0043:
            if (r2 <= 0) goto L_0x004a
            com.app.office.fc.hssf.record.common.UnicodeString$ExtRst r0 = r5.field_5_ext_rst
            r0.serialize(r6)
        L_0x004a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.record.common.UnicodeString.serialize(com.app.office.fc.hssf.record.cont.ContinuableRecordOutput):void");
    }

    public int compareTo(UnicodeString unicodeString) {
        int compareTo = getString().compareTo(unicodeString.getString());
        if (compareTo != 0) {
            return compareTo;
        }
        List<FormatRun> list = this.field_4_format_runs;
        if (list == null && unicodeString.field_4_format_runs == null) {
            return 0;
        }
        if (list == null && unicodeString.field_4_format_runs != null) {
            return 1;
        }
        if (list != null && unicodeString.field_4_format_runs == null) {
            return -1;
        }
        int size = list.size();
        if (size != unicodeString.field_4_format_runs.size()) {
            return size - unicodeString.field_4_format_runs.size();
        }
        for (int i = 0; i < size; i++) {
            int compareTo2 = this.field_4_format_runs.get(i).compareTo(unicodeString.field_4_format_runs.get(i));
            if (compareTo2 != 0) {
                return compareTo2;
            }
        }
        ExtRst extRst = this.field_5_ext_rst;
        if (extRst == null && unicodeString.field_5_ext_rst == null) {
            return 0;
        }
        if (extRst == null && unicodeString.field_5_ext_rst != null) {
            return 1;
        }
        if (extRst != null && unicodeString.field_5_ext_rst == null) {
            return -1;
        }
        int compareTo3 = extRst.compareTo(unicodeString.field_5_ext_rst);
        if (compareTo3 != 0) {
            return compareTo3;
        }
        return 0;
    }

    private boolean isRichText() {
        return richText.isSet(getOptionFlags());
    }

    private boolean isExtendedText() {
        return extBit.isSet(getOptionFlags());
    }

    public Object clone() {
        UnicodeString unicodeString = new UnicodeString();
        unicodeString.field_1_charCount = this.field_1_charCount;
        unicodeString.field_2_optionflags = this.field_2_optionflags;
        unicodeString.field_3_string = this.field_3_string;
        if (this.field_4_format_runs != null) {
            unicodeString.field_4_format_runs = new ArrayList();
            for (FormatRun next : this.field_4_format_runs) {
                unicodeString.field_4_format_runs.add(new FormatRun(next._character, next._fontIndex));
            }
        }
        ExtRst extRst = this.field_5_ext_rst;
        if (extRst != null) {
            unicodeString.field_5_ext_rst = extRst.clone();
        }
        return unicodeString;
    }
}
