package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.HWPFDocumentCore;
import com.app.office.fc.hwpf.model.CHPX;
import com.app.office.fc.hwpf.model.FileInformationBlock;
import com.app.office.fc.hwpf.model.ListTables;
import com.app.office.fc.hwpf.model.PAPX;
import com.app.office.fc.hwpf.model.PropertyNode;
import com.app.office.fc.hwpf.model.SEPX;
import com.app.office.fc.hwpf.model.StyleSheet;
import com.app.office.fc.hwpf.model.SubdocumentType;
import com.app.office.fc.hwpf.sprm.CharacterSprmCompressor;
import com.app.office.fc.hwpf.sprm.ParagraphSprmCompressor;
import com.app.office.fc.hwpf.sprm.SprmBuffer;
import com.app.office.fc.util.LittleEndian;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.NoSuchElementException;

public class Range {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int TYPE_CHARACTER = 1;
    public static final int TYPE_LISTENTRY = 4;
    public static final int TYPE_PARAGRAPH = 0;
    public static final int TYPE_SECTION = 2;
    public static final int TYPE_TABLE = 5;
    public static final int TYPE_TEXT = 3;
    public static final int TYPE_UNDEFINED = 6;
    protected int _charEnd;
    protected boolean _charRangeFound;
    protected int _charStart;
    protected List<CHPX> _characters;
    protected HWPFDocumentCore _doc;
    protected int _end;
    protected int _parEnd;
    protected boolean _parRangeFound;
    protected int _parStart;
    protected List<PAPX> _paragraphs;
    private WeakReference<Range> _parent;
    protected int _sectionEnd;
    boolean _sectionRangeFound;
    protected int _sectionStart;
    protected List<SEPX> _sections;
    protected int _start;
    protected StringBuilder _text;

    public int type() {
        return 6;
    }

    @Deprecated
    public boolean usesUnicode() {
        return true;
    }

    public Range(int i, int i2, HWPFDocumentCore hWPFDocumentCore) {
        this._start = i;
        this._end = i2;
        this._doc = hWPFDocumentCore;
        this._sections = hWPFDocumentCore.getSectionTable().getSections();
        this._paragraphs = this._doc.getParagraphTable().getParagraphs();
        this._characters = this._doc.getCharacterTable().getTextRuns();
        this._text = this._doc.getText();
        this._parent = new WeakReference<>((Object) null);
        sanityCheckStartEnd();
    }

    protected Range(int i, int i2, Range range) {
        this._start = i;
        this._end = i2;
        this._doc = range._doc;
        this._sections = range._sections;
        this._paragraphs = range._paragraphs;
        this._characters = range._characters;
        this._text = range._text;
        this._parent = new WeakReference<>(range);
        sanityCheckStartEnd();
    }

    @Deprecated
    protected Range(int i, int i2, int i3, Range range) {
        this._doc = range._doc;
        this._sections = range._sections;
        this._paragraphs = range._paragraphs;
        this._characters = range._characters;
        this._text = range._text;
        this._parent = new WeakReference<>(range);
        sanityCheckStartEnd();
    }

    private void sanityCheckStartEnd() {
        int i = this._start;
        if (i < 0) {
            throw new IllegalArgumentException("Range start must not be negative. Given " + this._start);
        } else if (this._end < i) {
            throw new IllegalArgumentException("The end (" + this._end + ") must not be before the start (" + this._start + ")");
        }
    }

    public String text() {
        return this._text.substring(this._start, this._end);
    }

    public static String stripFields(String str) {
        if (str.indexOf(19) == -1) {
            return str;
        }
        while (str.indexOf(19) > -1 && str.indexOf(21) > -1) {
            int indexOf = str.indexOf(19);
            int i = indexOf + 1;
            int indexOf2 = str.indexOf(19, i);
            int indexOf3 = str.indexOf(20, i);
            int lastIndexOf = str.lastIndexOf(21);
            if (lastIndexOf < indexOf) {
                return str;
            }
            if (indexOf2 == -1 && indexOf3 == -1) {
                return str.substring(0, indexOf) + str.substring(lastIndexOf + 1);
            } else if (indexOf3 == -1 || (indexOf3 >= indexOf2 && indexOf2 != -1)) {
                str = str.substring(0, indexOf) + str.substring(lastIndexOf + 1);
            } else {
                str = str.substring(0, indexOf) + str.substring(indexOf3 + 1, lastIndexOf) + str.substring(lastIndexOf + 1);
            }
        }
        return str;
    }

    public int numSections() {
        initSections();
        return this._sectionEnd - this._sectionStart;
    }

    public int numParagraphs() {
        initParagraphs();
        return this._parEnd - this._parStart;
    }

    public int numCharacterRuns() {
        initCharacterRuns();
        return this._charEnd - this._charStart;
    }

    public CharacterRun insertBefore(String str) {
        initAll();
        this._text.insert(this._start, str);
        this._doc.getCharacterTable().adjustForInsert(this._charStart, str.length());
        this._doc.getParagraphTable().adjustForInsert(this._parStart, str.length());
        this._doc.getSectionTable().adjustForInsert(this._sectionStart, str.length());
        adjustForInsert(str.length());
        adjustFIB(str.length());
        return getCharacterRun(0);
    }

    public CharacterRun insertAfter(String str) {
        initAll();
        this._text.insert(this._end, str);
        this._doc.getCharacterTable().adjustForInsert(this._charEnd - 1, str.length());
        this._doc.getParagraphTable().adjustForInsert(this._parEnd - 1, str.length());
        this._doc.getSectionTable().adjustForInsert(this._sectionEnd - 1, str.length());
        adjustForInsert(str.length());
        return getCharacterRun(numCharacterRuns() - 1);
    }

    @Deprecated
    public CharacterRun insertBefore(String str, CharacterProperties characterProperties) {
        initAll();
        this._doc.getCharacterTable().insert(this._charStart, this._start, new SprmBuffer(CharacterSprmCompressor.compressCharacterProperty(characterProperties, this._doc.getStyleSheet().getCharacterStyle(this._paragraphs.get(this._parStart).getIstd())), 0));
        return insertBefore(str);
    }

    @Deprecated
    public CharacterRun insertAfter(String str, CharacterProperties characterProperties) {
        initAll();
        this._doc.getCharacterTable().insert(this._charEnd, this._end, new SprmBuffer(CharacterSprmCompressor.compressCharacterProperty(characterProperties, this._doc.getStyleSheet().getCharacterStyle(this._paragraphs.get(this._parEnd - 1).getIstd())), 0));
        this._charEnd++;
        return insertAfter(str);
    }

    @Deprecated
    public Paragraph insertBefore(ParagraphProperties paragraphProperties, int i) {
        return insertBefore(paragraphProperties, i, "\r");
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public Paragraph insertBefore(ParagraphProperties paragraphProperties, int i, String str) {
        initAll();
        StyleSheet styleSheet = this._doc.getStyleSheet();
        ParagraphProperties paragraphStyle = styleSheet.getParagraphStyle(i);
        CharacterProperties characterStyle = styleSheet.getCharacterStyle(i);
        byte[] compressParagraphProperty = ParagraphSprmCompressor.compressParagraphProperty(paragraphProperties, paragraphStyle);
        byte[] bArr = new byte[(compressParagraphProperty.length + 2)];
        LittleEndian.putShort(bArr, (short) i);
        System.arraycopy(compressParagraphProperty, 0, bArr, 2, compressParagraphProperty.length);
        this._doc.getParagraphTable().insert(this._parStart, this._start, new SprmBuffer(bArr, 2));
        insertBefore(str, characterStyle);
        return getParagraph(0);
    }

    @Deprecated
    public Paragraph insertAfter(ParagraphProperties paragraphProperties, int i) {
        return insertAfter(paragraphProperties, i, "\r");
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public Paragraph insertAfter(ParagraphProperties paragraphProperties, int i, String str) {
        initAll();
        StyleSheet styleSheet = this._doc.getStyleSheet();
        ParagraphProperties paragraphStyle = styleSheet.getParagraphStyle(i);
        CharacterProperties characterStyle = styleSheet.getCharacterStyle(i);
        byte[] compressParagraphProperty = ParagraphSprmCompressor.compressParagraphProperty(paragraphProperties, paragraphStyle);
        byte[] bArr = new byte[(compressParagraphProperty.length + 2)];
        LittleEndian.putShort(bArr, (short) i);
        System.arraycopy(compressParagraphProperty, 0, bArr, 2, compressParagraphProperty.length);
        this._doc.getParagraphTable().insert(this._parEnd, this._end, new SprmBuffer(bArr, 2));
        this._parEnd++;
        insertAfter(str, characterStyle);
        return getParagraph(numParagraphs() - 1);
    }

    public void delete() {
        initAll();
        int size = this._sections.size();
        int size2 = this._characters.size();
        int size3 = this._paragraphs.size();
        for (int i = this._charStart; i < size2; i++) {
            int i2 = this._start;
            this._characters.get(i).adjustForDelete(i2, this._end - i2);
        }
        for (int i3 = this._parStart; i3 < size3; i3++) {
            int i4 = this._start;
            this._paragraphs.get(i3).adjustForDelete(i4, this._end - i4);
        }
        for (int i5 = this._sectionStart; i5 < size; i5++) {
            int i6 = this._start;
            this._sections.get(i5).adjustForDelete(i6, this._end - i6);
        }
        this._text.delete(this._start, this._end);
        Range range = (Range) this._parent.get();
        if (range != null) {
            range.adjustForInsert(-(this._end - this._start));
        }
        adjustFIB(-(this._end - this._start));
    }

    @Deprecated
    public Table insertBefore(TableProperties tableProperties, int i) {
        ParagraphProperties paragraphProperties = new ParagraphProperties();
        paragraphProperties.setFInTable(true);
        paragraphProperties.setItap(1);
        int i2 = this._end;
        short itcMac = tableProperties.getItcMac();
        for (int i3 = 0; i3 < i; i3++) {
            Paragraph insertBefore = insertBefore(paragraphProperties, 4095);
            insertBefore.insertAfter(String.valueOf(7));
            for (int i4 = 1; i4 < itcMac; i4++) {
                insertBefore = insertBefore.insertAfter(paragraphProperties, 4095);
                insertBefore.insertAfter(String.valueOf(7));
            }
            insertBefore.insertAfter(paragraphProperties, 4095, String.valueOf(7)).setTableRowEnd(tableProperties);
        }
        int i5 = this._start;
        return new Table(i5, (this._end - i2) + i5, this, 1);
    }

    public Table insertTableBefore(short s, int i) {
        ParagraphProperties paragraphProperties = new ParagraphProperties();
        paragraphProperties.setFInTable(true);
        paragraphProperties.setItap(1);
        int i2 = this._end;
        for (int i3 = 0; i3 < i; i3++) {
            Paragraph insertBefore = insertBefore(paragraphProperties, 4095);
            insertBefore.insertAfter(String.valueOf(7));
            for (int i4 = 1; i4 < s; i4++) {
                insertBefore = insertBefore.insertAfter(paragraphProperties, 4095);
                insertBefore.insertAfter(String.valueOf(7));
            }
            insertBefore.insertAfter(paragraphProperties, 4095, String.valueOf(7)).setTableRowEnd(new TableProperties(s));
        }
        int i5 = this._start;
        return new Table(i5, (this._end - i2) + i5, this, 1);
    }

    @Deprecated
    public ListEntry insertBefore(ParagraphProperties paragraphProperties, int i, int i2, int i3) {
        ListTables listTables = this._doc.getListTables();
        if (listTables.getLevel(i, i2) != null) {
            paragraphProperties.setIlfo(listTables.getOverrideIndexFromListID(i));
            paragraphProperties.setIlvl((byte) i2);
            return (ListEntry) insertBefore(paragraphProperties, i3);
        }
        throw new NoSuchElementException("The specified list and level do not exist");
    }

    @Deprecated
    public ListEntry insertAfter(ParagraphProperties paragraphProperties, int i, int i2, int i3) {
        ListTables listTables = this._doc.getListTables();
        if (listTables.getLevel(i, i2) != null) {
            paragraphProperties.setIlfo(listTables.getOverrideIndexFromListID(i));
            paragraphProperties.setIlvl((byte) i2);
            return (ListEntry) insertAfter(paragraphProperties, i3);
        }
        throw new NoSuchElementException("The specified list and level do not exist");
    }

    public void replaceText(String str, String str2, int i) {
        int startOffset = getStartOffset() + i;
        new Range(startOffset, str.length() + startOffset, this).insertBefore(str2);
        new Range(str2.length() + startOffset, startOffset + str.length() + str2.length(), this).delete();
    }

    public void replaceText(String str, String str2) {
        boolean z = true;
        while (z) {
            int indexOf = text().indexOf(str);
            if (indexOf >= 0) {
                replaceText(str, str2, indexOf);
            } else {
                z = false;
            }
        }
    }

    public CharacterRun getCharacterRun(int i) {
        short s;
        initCharacterRuns();
        int i2 = this._charStart;
        if (i + i2 < this._charEnd) {
            CHPX chpx = this._characters.get(i + i2);
            if (chpx == null) {
                return null;
            }
            if (this instanceof Paragraph) {
                s = ((Paragraph) this)._istd;
            } else {
                int[] findRange = findRange(this._paragraphs, Math.max(chpx.getStart(), this._start), Math.min(chpx.getEnd(), this._end));
                initParagraphs();
                if (Math.max(findRange[0], this._parStart) >= this._paragraphs.size()) {
                    return null;
                }
                s = this._paragraphs.get(findRange[0]).getIstd();
            }
            return new CharacterRun(chpx, this._doc.getStyleSheet(), s, this);
        }
        throw new IndexOutOfBoundsException("CHPX #" + i + " (" + (i + this._charStart) + ") not in range [" + this._charStart + "; " + this._charEnd + ")");
    }

    public CharacterRun getCharacterRunByOffset(long j) {
        CHPX chpx;
        short s;
        int size = this._characters.size();
        int i = 0;
        while (true) {
            int i2 = (size + i) / 2;
            chpx = this._characters.get(i2);
            int start = chpx.getStart();
            int end = chpx.getEnd();
            long j2 = (long) start;
            if (j >= j2 && j < ((long) end)) {
                break;
            } else if (j2 > j) {
                size = i2 - 1;
            } else if (((long) end) <= j) {
                i = i2 + 1;
            }
        }
        if (this instanceof Paragraph) {
            s = ((Paragraph) this)._istd;
        } else {
            int[] findRange = findRange(this._paragraphs, Math.max(chpx.getStart(), this._start), Math.min(chpx.getEnd(), this._end));
            initParagraphs();
            if (Math.max(findRange[0], this._parStart) >= this._paragraphs.size()) {
                return null;
            }
            s = this._paragraphs.get(findRange[0]).getIstd();
        }
        return new CharacterRun(chpx, this._doc.getStyleSheet(), s, this);
    }

    public Section getSection(int i) {
        initSections();
        if (this._sectionStart + i < this._sections.size()) {
            return new Section(this._sections.get(i + this._sectionStart), this);
        }
        return null;
    }

    public Paragraph getParagraph(int i) {
        initParagraphs();
        int i2 = this._parStart;
        if (i + i2 < this._parEnd) {
            PAPX papx = this._paragraphs.get(i2 + i);
            if (papx.getParagraphProperties(this._doc.getStyleSheet()).getIlfo() > 0) {
                return new ListEntry(papx, this, this._doc.getListTables());
            }
            if (i + this._parStart != 0 || papx.getStart() <= 0) {
                return new Paragraph(papx, this);
            }
            return new Paragraph(papx, this, 0);
        }
        throw new IndexOutOfBoundsException("Paragraph #" + i + " (" + (i + this._parStart) + ") not in range [" + this._parStart + "; " + this._parEnd + ")");
    }

    public Table getTable(Paragraph paragraph) {
        if (!paragraph.isInTable()) {
            throw new IllegalArgumentException("This paragraph doesn't belong to a table");
        } else if (paragraph._parent.get() == this) {
            paragraph.initAll();
            int tableLevel = paragraph.getTableLevel();
            int i = paragraph._parStart;
            if (i != 0) {
                Paragraph paragraph2 = new Paragraph(this._paragraphs.get(paragraph._parStart - 1), this);
                if (paragraph2.isInTable() && paragraph2.getTableLevel() == tableLevel && paragraph2._sectionEnd >= paragraph._sectionStart) {
                    throw new IllegalArgumentException("This paragraph is not the first one in the table");
                }
            }
            Range overallRange = this._doc.getOverallRange();
            int size = this._paragraphs.size();
            while (i < size - 1) {
                int i2 = i + 1;
                Paragraph paragraph3 = new Paragraph(this._paragraphs.get(i2), overallRange);
                if (!paragraph3.isInTable() || paragraph3.getTableLevel() < tableLevel) {
                    break;
                }
                i = i2;
            }
            initAll();
            if (i >= 0) {
                return new Table(paragraph.getStartOffset(), this._paragraphs.get(i).getEnd(), this, paragraph.getTableLevel());
            }
            throw new ArrayIndexOutOfBoundsException("The table's end is negative, which isn't allowed!");
        } else {
            throw new IllegalArgumentException("This paragraph is not a child of this range instance");
        }
    }

    /* access modifiers changed from: protected */
    public void initAll() {
        initCharacterRuns();
        initParagraphs();
        initSections();
    }

    private void initParagraphs() {
        if (!this._parRangeFound) {
            int[] findRange = findRange(this._paragraphs, this._start, this._end);
            this._parStart = findRange[0];
            this._parEnd = findRange[1];
            this._parRangeFound = true;
        }
    }

    private void initCharacterRuns() {
        if (!this._charRangeFound) {
            int[] findRange = findRange(this._characters, this._start, this._end);
            this._charStart = findRange[0];
            this._charEnd = findRange[1];
            this._charRangeFound = true;
        }
    }

    private void initSections() {
        if (!this._sectionRangeFound) {
            int[] findRange = findRange(this._sections, this._sectionStart, this._start, this._end);
            this._sectionStart = findRange[0];
            this._sectionEnd = findRange[1];
            this._sectionRangeFound = true;
        }
    }

    private static int binarySearchStart(List<? extends PropertyNode<?>> list, int i) {
        int i2 = 0;
        if (((PropertyNode) list.get(0)).getStart() >= i) {
            return 0;
        }
        int size = list.size() - 1;
        while (i2 <= size) {
            int i3 = (i2 + size) >>> 1;
            PropertyNode propertyNode = (PropertyNode) list.get(i3);
            if (propertyNode.getStart() < i) {
                i2 = i3 + 1;
            } else if (propertyNode.getStart() <= i) {
                return i3;
            } else {
                size = i3 - 1;
            }
        }
        return i2 - 1;
    }

    private static int binarySearchEnd(List<? extends PropertyNode<?>> list, int i, int i2) {
        if (((PropertyNode) list.get(list.size() - 1)).getEnd() <= i2) {
            return list.size() - 1;
        }
        int size = list.size() - 1;
        while (i <= size) {
            int i3 = (i + size) >>> 1;
            PropertyNode propertyNode = (PropertyNode) list.get(i3);
            if (propertyNode.getEnd() < i2) {
                i = i3 + 1;
            } else if (propertyNode.getEnd() <= i2) {
                return i3;
            } else {
                size = i3 - 1;
            }
        }
        return i;
    }

    private int[] findRange(List<? extends PropertyNode<?>> list, int i, int i2) {
        int binarySearchStart = binarySearchStart(list, i);
        while (binarySearchStart > 0 && ((PropertyNode) list.get(binarySearchStart - 1)).getStart() >= i) {
            binarySearchStart--;
        }
        int binarySearchEnd = binarySearchEnd(list, binarySearchStart, i2);
        while (binarySearchEnd < list.size() - 1 && ((PropertyNode) list.get(binarySearchEnd + 1)).getEnd() <= i2) {
            binarySearchEnd--;
        }
        if (binarySearchStart < 0 || binarySearchStart >= list.size() || binarySearchStart > binarySearchEnd || binarySearchEnd < 0 || binarySearchEnd >= list.size()) {
            throw new AssertionError();
        }
        return new int[]{binarySearchStart, binarySearchEnd + 1};
    }

    private int[] findRange(List<? extends PropertyNode<?>> list, int i, int i2, int i3) {
        if (list.size() == i) {
            return new int[]{i, i};
        }
        PropertyNode propertyNode = (PropertyNode) list.get(i);
        while (true) {
            if (propertyNode == null || (propertyNode.getEnd() <= i2 && i < list.size() - 1)) {
                i++;
                if (i >= list.size()) {
                    return new int[]{0, 0};
                }
                propertyNode = (PropertyNode) list.get(i);
            }
        }
        if (propertyNode.getStart() > i3) {
            return new int[]{0, 0};
        }
        if (propertyNode.getEnd() <= i2) {
            return new int[]{list.size(), list.size()};
        }
        int i4 = i;
        while (i4 < list.size()) {
            PropertyNode propertyNode2 = (PropertyNode) list.get(i4);
            if (propertyNode2 == null || (propertyNode2.getStart() < i3 && propertyNode2.getEnd() <= i3)) {
                i4++;
            } else if (propertyNode2.getStart() < i3) {
                return new int[]{i, i4 + 1};
            } else {
                return new int[]{i, i4};
            }
        }
        return new int[]{i, list.size()};
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this._charRangeFound = false;
        this._parRangeFound = false;
        this._sectionRangeFound = false;
    }

    /* access modifiers changed from: protected */
    public void adjustFIB(int i) {
        FileInformationBlock fileInformationBlock = this._doc.getFileInformationBlock();
        SubdocumentType[] subdocumentTypeArr = SubdocumentType.ORDERED;
        int length = subdocumentTypeArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            SubdocumentType subdocumentType = subdocumentTypeArr[i2];
            int subdocumentTextStreamLength = fileInformationBlock.getSubdocumentTextStreamLength(subdocumentType);
            i3 += subdocumentTextStreamLength;
            if (this._start > i3) {
                i2++;
            } else {
                fileInformationBlock.setSubdocumentTextStreamLength(subdocumentType, subdocumentTextStreamLength + i);
                return;
            }
        }
    }

    private void adjustForInsert(int i) {
        this._end += i;
        reset();
        Range range = (Range) this._parent.get();
        if (range != null) {
            range.adjustForInsert(i);
        }
    }

    public int getStartOffset() {
        return this._start;
    }

    public int getEndOffset() {
        return this._end;
    }

    /* access modifiers changed from: protected */
    public HWPFDocumentCore getDocument() {
        return this._doc;
    }

    public String toString() {
        return "Range from " + getStartOffset() + " to " + getEndOffset() + " (chars)";
    }

    public boolean sanityCheck() {
        int i = this._start;
        if (i < 0) {
            throw new AssertionError();
        } else if (i <= this._text.length()) {
            int i2 = this._end;
            if (i2 < 0) {
                throw new AssertionError();
            } else if (i2 > this._text.length()) {
                throw new AssertionError();
            } else if (this._start <= this._end) {
                if (this._charRangeFound) {
                    int i3 = this._charStart;
                    while (i3 < this._charEnd) {
                        CHPX chpx = this._characters.get(i3);
                        if (Math.max(this._start, chpx.getStart()) < Math.min(this._end, chpx.getEnd())) {
                            i3++;
                        } else {
                            throw new AssertionError();
                        }
                    }
                }
                if (!this._parRangeFound) {
                    return true;
                }
                int i4 = this._parStart;
                while (i4 < this._parEnd) {
                    PAPX papx = this._paragraphs.get(i4);
                    if (Math.max(this._start, papx.getStart()) < Math.min(this._end, papx.getEnd())) {
                        i4++;
                    } else {
                        throw new AssertionError();
                    }
                }
                return true;
            } else {
                throw new AssertionError();
            }
        } else {
            throw new AssertionError();
        }
    }
}
