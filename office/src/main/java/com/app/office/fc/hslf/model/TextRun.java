package com.app.office.fc.hslf.model;

import com.app.office.fc.hslf.model.textproperties.AutoNumberTextProp;
import com.app.office.fc.hslf.model.textproperties.TextPropCollection;
import com.app.office.fc.hslf.record.ExtendedParagraphAtom;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.hslf.record.RecordContainer;
import com.app.office.fc.hslf.record.StyleTextPropAtom;
import com.app.office.fc.hslf.record.TextBytesAtom;
import com.app.office.fc.hslf.record.TextCharsAtom;
import com.app.office.fc.hslf.record.TextHeaderAtom;
import com.app.office.fc.hslf.record.TextRulerAtom;
import com.app.office.fc.hslf.record.TextSpecInfoAtom;
import com.app.office.fc.hslf.usermodel.RichTextRun;
import com.app.office.fc.hslf.usermodel.SlideShow;
import com.app.office.fc.util.StringUtil;
import java.util.LinkedList;

public final class TextRun {
    protected TextBytesAtom _byteAtom;
    protected TextCharsAtom _charAtom;
    protected ExtendedParagraphAtom _extendedParagraphAtom;
    protected TextHeaderAtom _headerAtom;
    protected boolean _isUnicode;
    protected Record[] _records;
    protected RichTextRun[] _rtRuns;
    protected TextRulerAtom _ruler;
    private Sheet _sheet;
    protected StyleTextPropAtom _styleAtom;
    private int shapeId;
    private SlideShow slideShow;
    private int slwtIndex;

    public TextRun(TextHeaderAtom textHeaderAtom, TextCharsAtom textCharsAtom, StyleTextPropAtom styleTextPropAtom) {
        this(textHeaderAtom, (TextBytesAtom) null, textCharsAtom, styleTextPropAtom);
    }

    public TextRun(TextHeaderAtom textHeaderAtom, TextBytesAtom textBytesAtom, StyleTextPropAtom styleTextPropAtom) {
        this(textHeaderAtom, textBytesAtom, (TextCharsAtom) null, styleTextPropAtom);
    }

    private TextRun(TextHeaderAtom textHeaderAtom, TextBytesAtom textBytesAtom, TextCharsAtom textCharsAtom, StyleTextPropAtom styleTextPropAtom) {
        this.shapeId = -1;
        this._headerAtom = textHeaderAtom;
        this._styleAtom = styleTextPropAtom;
        if (textBytesAtom != null) {
            this._byteAtom = textBytesAtom;
            this._isUnicode = false;
        } else {
            this._charAtom = textCharsAtom;
            this._isUnicode = true;
        }
        String text = getText();
        LinkedList<TextPropCollection> linkedList = new LinkedList<>();
        LinkedList<TextPropCollection> linkedList2 = new LinkedList<>();
        StyleTextPropAtom styleTextPropAtom2 = this._styleAtom;
        if (styleTextPropAtom2 != null) {
            styleTextPropAtom2.setParentTextSize(text.length());
            linkedList = this._styleAtom.getParagraphStyles();
            linkedList2 = this._styleAtom.getCharacterStyles();
        }
        buildRichTextRuns(linkedList, linkedList2, text);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a9, code lost:
        if (r3 < r4) goto L_0x00ab;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void buildRichTextRuns(java.util.LinkedList r22, java.util.LinkedList r23, java.lang.String r24) {
        /*
            r21 = this;
            r8 = r21
            int r0 = r22.size()
            r9 = 0
            r10 = 1
            if (r0 == 0) goto L_0x00f9
            int r0 = r23.size()
            if (r0 != 0) goto L_0x0012
            goto L_0x00f9
        L_0x0012:
            java.util.Vector r11 = new java.util.Vector
            r11.<init>()
            r12 = -1
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = -1
            r4 = -1
        L_0x001d:
            int r5 = r24.length()
            if (r2 > r5) goto L_0x00ed
            int r5 = r22.size()
            if (r0 >= r5) goto L_0x00ed
            int r5 = r23.size()
            if (r1 >= r5) goto L_0x00ed
            r13 = r22
            java.lang.Object r5 = r13.get(r0)
            com.app.office.fc.hslf.model.textproperties.TextPropCollection r5 = (com.app.office.fc.hslf.model.textproperties.TextPropCollection) r5
            r14 = r23
            java.lang.Object r6 = r14.get(r1)
            com.app.office.fc.hslf.model.textproperties.TextPropCollection r6 = (com.app.office.fc.hslf.model.textproperties.TextPropCollection) r6
            int r7 = r5.getCharactersCovered()
            int r15 = r6.getCharactersCovered()
            if (r3 != r12) goto L_0x004e
            if (r4 != r12) goto L_0x004e
            r16 = 1
            goto L_0x0050
        L_0x004e:
            r16 = 0
        L_0x0050:
            if (r3 != r12) goto L_0x0053
            r3 = r7
        L_0x0053:
            if (r4 != r12) goto L_0x0056
            r4 = r15
        L_0x0056:
            if (r7 != r15) goto L_0x006b
            if (r16 == 0) goto L_0x006b
            int r0 = r0 + 1
            int r1 = r1 + 1
            r16 = r1
            r3 = r15
            r7 = 0
            r17 = -1
            r18 = -1
            r19 = 0
            r15 = r0
            goto L_0x00c4
        L_0x006b:
            if (r3 >= r7) goto L_0x008a
            if (r3 != r4) goto L_0x007e
            int r0 = r0 + 1
            int r1 = r1 + 1
            r15 = r0
            r16 = r1
            r7 = 1
            r17 = -1
        L_0x0079:
            r18 = -1
            r19 = 0
            goto L_0x00c4
        L_0x007e:
            if (r3 >= r4) goto L_0x00b9
            int r0 = r0 + 1
            int r4 = r4 - r3
            r15 = r0
            r16 = r1
            r18 = r4
            r7 = 1
            goto L_0x00b4
        L_0x008a:
            if (r4 >= r15) goto L_0x00a9
            if (r3 != r4) goto L_0x009c
            int r0 = r0 + 1
            int r1 = r1 + 1
            r15 = r0
            r16 = r1
            r3 = r4
            r7 = 0
            r17 = -1
        L_0x0099:
            r18 = -1
            goto L_0x00b6
        L_0x009c:
            if (r4 >= r3) goto L_0x00ab
            int r1 = r1 + 1
            int r3 = r3 - r4
            r15 = r0
            r16 = r1
            r17 = r3
            r3 = r4
            r7 = 1
            goto L_0x0099
        L_0x00a9:
            if (r3 >= r4) goto L_0x00b9
        L_0x00ab:
            int r0 = r0 + 1
            int r4 = r4 - r3
            r15 = r0
            r16 = r1
            r18 = r4
            r7 = 0
        L_0x00b4:
            r17 = -1
        L_0x00b6:
            r19 = 1
            goto L_0x00c4
        L_0x00b9:
            int r1 = r1 + 1
            int r3 = r3 - r4
            r15 = r0
            r16 = r1
            r17 = r3
            r3 = r4
            r7 = 1
            goto L_0x0079
        L_0x00c4:
            int r4 = r2 + r3
            int r0 = r24.length()
            if (r4 <= r0) goto L_0x00ce
            int r3 = r3 + -1
        L_0x00ce:
            com.app.office.fc.hslf.usermodel.RichTextRun r1 = new com.app.office.fc.hslf.usermodel.RichTextRun
            r0 = r1
            r12 = r1
            r1 = r21
            r20 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r19
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            r11.add(r12)
            r0 = r15
            r1 = r16
            r3 = r17
            r4 = r18
            r2 = r20
            r12 = -1
            goto L_0x001d
        L_0x00ed:
            int r0 = r11.size()
            com.app.office.fc.hslf.usermodel.RichTextRun[] r0 = new com.app.office.fc.hslf.usermodel.RichTextRun[r0]
            r8._rtRuns = r0
            r11.copyInto(r0)
            goto L_0x0108
        L_0x00f9:
            com.app.office.fc.hslf.usermodel.RichTextRun[] r0 = new com.app.office.fc.hslf.usermodel.RichTextRun[r10]
            r8._rtRuns = r0
            com.app.office.fc.hslf.usermodel.RichTextRun r1 = new com.app.office.fc.hslf.usermodel.RichTextRun
            int r2 = r24.length()
            r1.<init>(r8, r9, r2)
            r0[r9] = r1
        L_0x0108:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hslf.model.TextRun.buildRichTextRuns(java.util.LinkedList, java.util.LinkedList, java.lang.String):void");
    }

    public RichTextRun appendText(String str) {
        ensureStyleAtomPresent();
        int length = getRawText().length();
        storeText(getRawText() + str);
        int paragraphTextLengthCovered = this._styleAtom.getParagraphTextLengthCovered() - length;
        int characterTextLengthCovered = this._styleAtom.getCharacterTextLengthCovered() - length;
        if (paragraphTextLengthCovered > 0) {
            TextPropCollection last = this._styleAtom.getParagraphStyles().getLast();
            last.updateTextSize(last.getCharactersCovered() - paragraphTextLengthCovered);
        }
        if (characterTextLengthCovered > 0) {
            TextPropCollection last2 = this._styleAtom.getCharacterStyles().getLast();
            last2.updateTextSize(last2.getCharactersCovered() - characterTextLengthCovered);
        }
        RichTextRun richTextRun = new RichTextRun(this, length, str.length(), this._styleAtom.addParagraphTextPropCollection(str.length() + paragraphTextLengthCovered), this._styleAtom.addCharacterTextPropCollection(str.length() + characterTextLengthCovered), false, false);
        RichTextRun[] richTextRunArr = this._rtRuns;
        int length2 = richTextRunArr.length + 1;
        RichTextRun[] richTextRunArr2 = new RichTextRun[length2];
        System.arraycopy(richTextRunArr, 0, richTextRunArr2, 0, richTextRunArr.length);
        richTextRunArr2[length2 - 1] = richTextRun;
        this._rtRuns = richTextRunArr2;
        return richTextRun;
    }

    private void storeText(String str) {
        int i = 0;
        if (str.endsWith("\r")) {
            str = str.substring(0, str.length() - 1);
        }
        if (this._isUnicode) {
            this._charAtom.setText(str);
        } else if (!StringUtil.hasMultibyte(str)) {
            byte[] bArr = new byte[str.length()];
            StringUtil.putCompressedUnicode(str, bArr, 0);
            this._byteAtom.setText(bArr);
        } else {
            TextCharsAtom textCharsAtom = new TextCharsAtom();
            this._charAtom = textCharsAtom;
            textCharsAtom.setText(str);
            Record[] childRecords = this._headerAtom.getParentRecord().getChildRecords();
            int i2 = 0;
            while (true) {
                if (i2 >= childRecords.length) {
                    break;
                } else if (childRecords[i2].equals(this._byteAtom)) {
                    childRecords[i2] = this._charAtom;
                    break;
                } else {
                    i2++;
                }
            }
            this._byteAtom = null;
            this._isUnicode = true;
        }
        if (this._records != null) {
            while (true) {
                Record[] recordArr = this._records;
                if (i < recordArr.length) {
                    if (recordArr[i] instanceof TextSpecInfoAtom) {
                        TextSpecInfoAtom textSpecInfoAtom = (TextSpecInfoAtom) recordArr[i];
                        if (str.length() + 1 != textSpecInfoAtom.getCharactersCovered()) {
                            textSpecInfoAtom.reset(str.length() + 1);
                        }
                    }
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    public void changeTextInRichTextRun(RichTextRun richTextRun, String str) {
        int i = 0;
        int i2 = -1;
        while (true) {
            RichTextRun[] richTextRunArr = this._rtRuns;
            if (i >= richTextRunArr.length) {
                break;
            }
            if (richTextRun.equals(richTextRunArr[i])) {
                i2 = i;
            }
            i++;
        }
        if (i2 != -1) {
            ensureStyleAtomPresent();
            TextPropCollection _getRawParagraphStyle = richTextRun._getRawParagraphStyle();
            TextPropCollection _getRawCharacterStyle = richTextRun._getRawCharacterStyle();
            int length = str.length();
            if (i2 == this._rtRuns.length - 1) {
                length++;
            }
            if (richTextRun._isParagraphStyleShared()) {
                _getRawParagraphStyle.updateTextSize((_getRawParagraphStyle.getCharactersCovered() - richTextRun.getLength()) + str.length());
            } else {
                _getRawParagraphStyle.updateTextSize(length);
            }
            if (richTextRun._isCharacterStyleShared()) {
                _getRawCharacterStyle.updateTextSize((_getRawCharacterStyle.getCharactersCovered() - richTextRun.getLength()) + str.length());
            } else {
                _getRawCharacterStyle.updateTextSize(length);
            }
            StringBuffer stringBuffer = new StringBuffer();
            for (int i3 = 0; i3 < this._rtRuns.length; i3++) {
                int length2 = stringBuffer.length();
                if (i3 != i2) {
                    stringBuffer.append(this._rtRuns[i3].getRawText());
                } else {
                    stringBuffer.append(str);
                }
                if (i3 > i2) {
                    this._rtRuns[i3].updateStartPosition(length2);
                }
            }
            storeText(stringBuffer.toString());
            return;
        }
        throw new IllegalArgumentException("Supplied RichTextRun wasn't from this TextRun");
    }

    public void setRawText(String str) {
        storeText(str);
        RichTextRun richTextRun = this._rtRuns[0];
        int i = 0;
        while (true) {
            RichTextRun[] richTextRunArr = this._rtRuns;
            if (i >= richTextRunArr.length) {
                break;
            }
            richTextRunArr[i] = null;
            i++;
        }
        RichTextRun[] richTextRunArr2 = new RichTextRun[1];
        this._rtRuns = richTextRunArr2;
        richTextRunArr2[0] = richTextRun;
        StyleTextPropAtom styleTextPropAtom = this._styleAtom;
        if (styleTextPropAtom != null) {
            LinkedList<TextPropCollection> paragraphStyles = styleTextPropAtom.getParagraphStyles();
            while (paragraphStyles.size() > 1) {
                paragraphStyles.removeLast();
            }
            LinkedList<TextPropCollection> characterStyles = this._styleAtom.getCharacterStyles();
            while (characterStyles.size() > 1) {
                characterStyles.removeLast();
            }
            this._rtRuns[0].setText(str);
            return;
        }
        richTextRunArr2[0] = new RichTextRun(this, 0, str.length());
    }

    public void setText(String str) {
        setRawText(normalize(str));
    }

    public void ensureStyleAtomPresent() {
        if (this._styleAtom == null) {
            this._styleAtom = new StyleTextPropAtom(getRawText().length() + 1);
            RecordContainer parentRecord = this._headerAtom.getParentRecord();
            Record record = this._byteAtom;
            if (record == null) {
                record = this._charAtom;
            }
            parentRecord.addChildAfter(this._styleAtom, record);
            RichTextRun[] richTextRunArr = this._rtRuns;
            if (richTextRunArr.length == 1) {
                richTextRunArr[0].supplyTextProps(this._styleAtom.getParagraphStyles().get(0), this._styleAtom.getCharacterStyles().get(0), false, false);
                return;
            }
            throw new IllegalStateException("Needed to add StyleTextPropAtom when had many rich text runs");
        }
    }

    public String getText() {
        return getRawText().replace(13, 10).replace(11, 11);
    }

    public String getRawText() {
        if (this._isUnicode) {
            return this._charAtom.getText();
        }
        return this._byteAtom.getText();
    }

    public RichTextRun[] getRichTextRuns() {
        return this._rtRuns;
    }

    public int getRunType() {
        return this._headerAtom.getTextType();
    }

    public void setRunType(int i) {
        this._headerAtom.setTextType(i);
    }

    public void supplySlideShow(SlideShow slideShow2) {
        this.slideShow = slideShow2;
        if (this._rtRuns != null) {
            int i = 0;
            while (true) {
                RichTextRun[] richTextRunArr = this._rtRuns;
                if (i < richTextRunArr.length) {
                    richTextRunArr[i].supplySlideShow(this.slideShow);
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    public void setSheet(Sheet sheet) {
        this._sheet = sheet;
    }

    public Sheet getSheet() {
        return this._sheet;
    }

    /* access modifiers changed from: protected */
    public int getShapeId() {
        return this.shapeId;
    }

    /* access modifiers changed from: protected */
    public void setShapeId(int i) {
        this.shapeId = i;
    }

    /* access modifiers changed from: protected */
    public int getIndex() {
        return this.slwtIndex;
    }

    /* access modifiers changed from: protected */
    public void setIndex(int i) {
        this.slwtIndex = i;
    }

    public Hyperlink[] getHyperlinks() {
        return Hyperlink.find(this);
    }

    public RichTextRun getRichTextRunAt(int i) {
        int i2 = 0;
        while (true) {
            RichTextRun[] richTextRunArr = this._rtRuns;
            if (i2 >= richTextRunArr.length) {
                return null;
            }
            int startIndex = richTextRunArr[i2].getStartIndex();
            int endIndex = this._rtRuns[i2].getEndIndex();
            if (i >= startIndex && i < endIndex) {
                return this._rtRuns[i2];
            }
            i2++;
        }
    }

    public TextRulerAtom getTextRuler() {
        if (this._ruler == null && this._records != null) {
            int i = 0;
            while (true) {
                Record[] recordArr = this._records;
                if (i >= recordArr.length) {
                    break;
                } else if (recordArr[i] instanceof TextRulerAtom) {
                    this._ruler = (TextRulerAtom) recordArr[i];
                    break;
                } else {
                    i++;
                }
            }
        }
        return this._ruler;
    }

    public TextRulerAtom createTextRuler() {
        TextRulerAtom textRuler = getTextRuler();
        this._ruler = textRuler;
        if (textRuler == null) {
            this._ruler = TextRulerAtom.getParagraphInstance();
            this._headerAtom.getParentRecord().appendChildRecord(this._ruler);
        }
        return this._ruler;
    }

    public String normalize(String str) {
        return str.replaceAll("\\r?\\n", "\r");
    }

    public Record[] getRecords() {
        return this._records;
    }

    public ExtendedParagraphAtom getExtendedParagraphAtom() {
        return this._extendedParagraphAtom;
    }

    public void setExtendedParagraphAtom(ExtendedParagraphAtom extendedParagraphAtom) {
        this._extendedParagraphAtom = extendedParagraphAtom;
    }

    public int getNumberingType(int i) {
        int autoNumberIndex;
        LinkedList<AutoNumberTextProp> extendedParagraphPropList;
        AutoNumberTextProp autoNumberTextProp;
        if (this._extendedParagraphAtom == null || (autoNumberIndex = getAutoNumberIndex(i)) < 0 || (extendedParagraphPropList = this._extendedParagraphAtom.getExtendedParagraphPropList()) == null || extendedParagraphPropList.size() <= 0 || autoNumberIndex >= extendedParagraphPropList.size() || (autoNumberTextProp = extendedParagraphPropList.get(autoNumberIndex)) == null) {
            return -1;
        }
        return autoNumberTextProp.getNumberingType();
    }

    public int getNumberingStart(int i) {
        int autoNumberIndex;
        LinkedList<AutoNumberTextProp> extendedParagraphPropList;
        AutoNumberTextProp autoNumberTextProp;
        if (this._extendedParagraphAtom == null || (autoNumberIndex = getAutoNumberIndex(i)) < 0 || (extendedParagraphPropList = this._extendedParagraphAtom.getExtendedParagraphPropList()) == null || extendedParagraphPropList.size() <= 0 || autoNumberIndex >= extendedParagraphPropList.size() || (autoNumberTextProp = extendedParagraphPropList.get(autoNumberIndex)) == null) {
            return 0;
        }
        return autoNumberTextProp.getStart();
    }

    public int getAutoNumberIndex(int i) {
        StyleTextPropAtom styleTextPropAtom;
        if (this._records == null) {
            return -1;
        }
        int i2 = 0;
        while (true) {
            Record[] recordArr = this._records;
            if (i2 >= recordArr.length) {
                return -1;
            }
            if ((recordArr[i2] instanceof StyleTextPropAtom) && (styleTextPropAtom = (StyleTextPropAtom) recordArr[i2]) != null) {
                return styleTextPropAtom.getAutoNumberIndex(i);
            }
            i2++;
        }
    }

    public void dispose() {
        this.slideShow = null;
        this._sheet = null;
        TextHeaderAtom textHeaderAtom = this._headerAtom;
        if (textHeaderAtom != null) {
            textHeaderAtom.dispose();
            this._headerAtom = null;
        }
        TextBytesAtom textBytesAtom = this._byteAtom;
        if (textBytesAtom != null) {
            textBytesAtom.dispose();
            this._byteAtom = null;
        }
        TextCharsAtom textCharsAtom = this._charAtom;
        if (textCharsAtom != null) {
            textCharsAtom.dispose();
            this._charAtom = null;
        }
        StyleTextPropAtom styleTextPropAtom = this._styleAtom;
        if (styleTextPropAtom != null) {
            styleTextPropAtom.dispose();
            this._styleAtom = null;
        }
        TextRulerAtom textRulerAtom = this._ruler;
        if (textRulerAtom != null) {
            textRulerAtom.dispose();
            this._ruler = null;
        }
        ExtendedParagraphAtom extendedParagraphAtom = this._extendedParagraphAtom;
        if (extendedParagraphAtom != null) {
            extendedParagraphAtom.dispose();
            this._extendedParagraphAtom = null;
        }
        RichTextRun[] richTextRunArr = this._rtRuns;
        if (richTextRunArr != null) {
            for (RichTextRun dispose : richTextRunArr) {
                dispose.dispose();
            }
            this._rtRuns = null;
        }
    }
}
