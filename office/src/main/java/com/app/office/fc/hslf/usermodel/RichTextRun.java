package com.app.office.fc.hslf.usermodel;

import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.app.office.fc.ShapeKit;
import com.app.office.fc.hslf.model.MasterSheet;
import com.app.office.fc.hslf.model.Sheet;
import com.app.office.fc.hslf.model.TextRun;
import com.app.office.fc.hslf.model.textproperties.BitMaskTextProp;
import com.app.office.fc.hslf.model.textproperties.CharFlagsTextProp;
import com.app.office.fc.hslf.model.textproperties.ParagraphFlagsTextProp;
import com.app.office.fc.hslf.model.textproperties.TextProp;
import com.app.office.fc.hslf.model.textproperties.TextPropCollection;
import com.app.office.fc.hslf.record.ColorSchemeAtom;
import com.app.office.fc.ss.util.CellUtil;
import com.app.office.java.awt.Color;

public final class RichTextRun {
    private String _fontname;
    private TextPropCollection characterStyle;
    private int length;
    private TextPropCollection paragraphStyle;
    private TextRun parentRun;
    private boolean sharingCharacterStyle;
    private boolean sharingParagraphStyle;
    private SlideShow slideShow;
    private int startPos;

    public RichTextRun(TextRun textRun, int i, int i2) {
        this(textRun, i, i2, (TextPropCollection) null, (TextPropCollection) null, false, false);
    }

    public RichTextRun(TextRun textRun, int i, int i2, TextPropCollection textPropCollection, TextPropCollection textPropCollection2, boolean z, boolean z2) {
        this.parentRun = textRun;
        this.startPos = i;
        this.length = i2;
        this.paragraphStyle = textPropCollection;
        this.characterStyle = textPropCollection2;
        this.sharingParagraphStyle = z;
        this.sharingCharacterStyle = z2;
    }

    public void supplyTextProps(TextPropCollection textPropCollection, TextPropCollection textPropCollection2, boolean z, boolean z2) {
        if (this.paragraphStyle == null && this.characterStyle == null) {
            this.paragraphStyle = textPropCollection;
            this.characterStyle = textPropCollection2;
            this.sharingParagraphStyle = z;
            this.sharingCharacterStyle = z2;
            return;
        }
        throw new IllegalStateException("Can't call supplyTextProps if run already has some");
    }

    public void supplySlideShow(SlideShow slideShow2) {
        this.slideShow = slideShow2;
        String str = this._fontname;
        if (str != null) {
            setFontName(str);
            this._fontname = null;
        }
    }

    public int getLength() {
        return this.length;
    }

    public int getStartIndex() {
        return this.startPos;
    }

    public int getEndIndex() {
        return this.startPos + this.length;
    }

    public String getText() {
        String text = this.parentRun.getText();
        return text.substring(this.startPos, Math.min(text.length(), this.startPos + this.length));
    }

    public String getRawText() {
        String rawText = this.parentRun.getRawText();
        int i = this.startPos;
        return rawText.substring(i, this.length + i);
    }

    public void setText(String str) {
        setRawText(this.parentRun.normalize(str));
    }

    public void setRawText(String str) {
        this.length = str.length();
        this.parentRun.changeTextInRichTextRun(this, str);
    }

    public void updateStartPosition(int i) {
        this.startPos = i;
    }

    private boolean isCharFlagsTextPropVal(int i) {
        return getFlag(true, i);
    }

    /* JADX WARNING: type inference failed for: r5v3, types: [com.app.office.fc.hslf.model.textproperties.TextProp] */
    /* JADX WARNING: type inference failed for: r0v4, types: [com.app.office.fc.hslf.model.textproperties.TextProp] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean getFlag(boolean r5, int r6) {
        /*
            r4 = this;
            if (r5 == 0) goto L_0x0007
            com.app.office.fc.hslf.model.textproperties.TextPropCollection r0 = r4.characterStyle
            java.lang.String r1 = com.app.office.fc.hslf.model.textproperties.CharFlagsTextProp.NAME
            goto L_0x000b
        L_0x0007:
            com.app.office.fc.hslf.model.textproperties.TextPropCollection r0 = r4.paragraphStyle
            java.lang.String r1 = com.app.office.fc.hslf.model.textproperties.ParagraphFlagsTextProp.NAME
        L_0x000b:
            r2 = 0
            if (r0 == 0) goto L_0x0015
            com.app.office.fc.hslf.model.textproperties.TextProp r0 = r0.findByName(r1)
            r2 = r0
            com.app.office.fc.hslf.model.textproperties.BitMaskTextProp r2 = (com.app.office.fc.hslf.model.textproperties.BitMaskTextProp) r2
        L_0x0015:
            if (r2 != 0) goto L_0x0036
            com.app.office.fc.hslf.model.TextRun r0 = r4.parentRun
            com.app.office.fc.hslf.model.Sheet r0 = r0.getSheet()
            if (r0 == 0) goto L_0x0036
            com.app.office.fc.hslf.model.TextRun r3 = r4.parentRun
            int r3 = r3.getRunType()
            com.app.office.fc.hslf.model.MasterSheet r0 = r0.getMasterSheet()
            if (r0 == 0) goto L_0x0036
            int r2 = r4.getIndentLevel()
            com.app.office.fc.hslf.model.textproperties.TextProp r5 = r0.getStyleAttribute(r3, r2, r1, r5)
            r2 = r5
            com.app.office.fc.hslf.model.textproperties.BitMaskTextProp r2 = (com.app.office.fc.hslf.model.textproperties.BitMaskTextProp) r2
        L_0x0036:
            if (r2 != 0) goto L_0x003a
            r5 = 0
            goto L_0x003e
        L_0x003a:
            boolean r5 = r2.getSubValue(r6)
        L_0x003e:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hslf.usermodel.RichTextRun.getFlag(boolean, int):boolean");
    }

    private void setCharFlagsTextPropVal(int i, boolean z) {
        if (getFlag(true, i) != z) {
            setFlag(true, i, z);
        }
    }

    public void setFlag(boolean z, int i, boolean z2) {
        String str;
        TextPropCollection textPropCollection;
        if (z) {
            textPropCollection = this.characterStyle;
            str = CharFlagsTextProp.NAME;
        } else {
            textPropCollection = this.paragraphStyle;
            str = ParagraphFlagsTextProp.NAME;
        }
        if (textPropCollection == null) {
            this.parentRun.ensureStyleAtomPresent();
            textPropCollection = z ? this.characterStyle : this.paragraphStyle;
        }
        ((BitMaskTextProp) fetchOrAddTextProp(textPropCollection, str)).setSubValue(z2, i);
    }

    private TextProp fetchOrAddTextProp(TextPropCollection textPropCollection, String str) {
        TextProp findByName = textPropCollection.findByName(str);
        return findByName == null ? textPropCollection.addWithName(str) : findByName;
    }

    private int getCharTextPropVal(String str) {
        TextPropCollection textPropCollection = this.characterStyle;
        TextProp findByName = textPropCollection != null ? textPropCollection.findByName(str) : null;
        if (findByName == null) {
            Sheet sheet = this.parentRun.getSheet();
            int runType = this.parentRun.getRunType();
            MasterSheet masterSheet = sheet.getMasterSheet();
            if (masterSheet != null) {
                findByName = masterSheet.getStyleAttribute(runType, getIndentLevel(), str, true);
            }
        }
        if (findByName == null && str.equalsIgnoreCase("font.color")) {
            return Color.BLACK.getRGB();
        }
        if (findByName == null) {
            return -1;
        }
        return findByName.getValue();
    }

    private int getParaTextPropVal(String str) {
        TextPropCollection textPropCollection = this.paragraphStyle;
        TextProp findByName = textPropCollection != null ? textPropCollection.findByName(str) : null;
        if (findByName == null) {
            Sheet sheet = this.parentRun.getSheet();
            int runType = this.parentRun.getRunType();
            MasterSheet masterSheet = sheet.getMasterSheet();
            if (masterSheet != null) {
                findByName = masterSheet.getStyleAttribute(runType, getIndentLevel(), str, false);
            }
        }
        if (findByName == null) {
            return -1;
        }
        return findByName.getValue();
    }

    public void setParaTextPropVal(String str, int i) {
        if (this.paragraphStyle == null) {
            this.parentRun.ensureStyleAtomPresent();
        }
        fetchOrAddTextProp(this.paragraphStyle, str).setValue(i);
    }

    public void setCharTextPropVal(String str, int i) {
        if (this.characterStyle == null) {
            this.parentRun.ensureStyleAtomPresent();
        }
        fetchOrAddTextProp(this.characterStyle, str).setValue(i);
    }

    public boolean isBold() {
        return isCharFlagsTextPropVal(0);
    }

    public void setBold(boolean z) {
        setCharFlagsTextPropVal(0, z);
    }

    public boolean isItalic() {
        return isCharFlagsTextPropVal(1);
    }

    public void setItalic(boolean z) {
        setCharFlagsTextPropVal(1, z);
    }

    public boolean isUnderlined() {
        return isCharFlagsTextPropVal(2);
    }

    public void setUnderlined(boolean z) {
        setCharFlagsTextPropVal(2, z);
    }

    public boolean isShadowed() {
        return isCharFlagsTextPropVal(4);
    }

    public void setShadowed(boolean z) {
        setCharFlagsTextPropVal(4, z);
    }

    public boolean isEmbossed() {
        return isCharFlagsTextPropVal(9);
    }

    public void setEmbossed(boolean z) {
        setCharFlagsTextPropVal(9, z);
    }

    public boolean isStrikethrough() {
        return isCharFlagsTextPropVal(8);
    }

    public void setStrikethrough(boolean z) {
        setCharFlagsTextPropVal(8, z);
    }

    public int getSuperscript() {
        int charTextPropVal = getCharTextPropVal("superscript");
        if (charTextPropVal == -1) {
            return 0;
        }
        return charTextPropVal;
    }

    public void setSuperscript(int i) {
        setCharTextPropVal("superscript", i);
    }

    public int getFontSize() {
        return getCharTextPropVal("font.size");
    }

    public void setFontSize(int i) {
        setCharTextPropVal("font.size", i);
    }

    public int getFontIndex() {
        return getCharTextPropVal("font.index");
    }

    public void setFontIndex(int i) {
        setCharTextPropVal("font.index", i);
    }

    public void setFontName(String str) {
        SlideShow slideShow2 = this.slideShow;
        if (slideShow2 == null) {
            this._fontname = str;
        } else {
            setCharTextPropVal("font.index", slideShow2.getFontCollection().addFont(str));
        }
    }

    public String getFontName() {
        if (this.slideShow == null) {
            return this._fontname;
        }
        int charTextPropVal = getCharTextPropVal("font.index");
        if (charTextPropVal == -1) {
            return null;
        }
        return this.slideShow.getFontCollection().getFontWithId(charTextPropVal);
    }

    public Color getFontColor() {
        int charTextPropVal = getCharTextPropVal("font.color");
        int i = charTextPropVal >> 24;
        if (charTextPropVal % 16777216 == 0) {
            ColorSchemeAtom colorScheme = this.parentRun.getSheet().getColorScheme();
            if (i >= 0 && i <= 7) {
                charTextPropVal = colorScheme.getColor(i);
            }
        }
        Color color = new Color(charTextPropVal, true);
        return new Color(color.getBlue(), color.getGreen(), color.getRed());
    }

    public void setFontColor(int i) {
        setCharTextPropVal("font.color", i);
    }

    public void setFontColor(Color color) {
        setFontColor(new Color(color.getBlue(), color.getGreen(), color.getRed(), (int) TIFFConstants.TIFFTAG_SUBFILETYPE).getRGB());
    }

    public void setAlignment(int i) {
        setParaTextPropVal(CellUtil.ALIGNMENT, i);
    }

    public int getAlignment() {
        return getParaTextPropVal(CellUtil.ALIGNMENT);
    }

    public int getIndentLevel() {
        TextPropCollection textPropCollection = this.paragraphStyle;
        if (textPropCollection == null) {
            return 0;
        }
        return textPropCollection.getReservedField();
    }

    public void setIndentLevel(int i) {
        TextPropCollection textPropCollection = this.paragraphStyle;
        if (textPropCollection != null) {
            textPropCollection.setReservedField((short) i);
        }
    }

    public void setBullet(boolean z) {
        setFlag(false, 0, z);
    }

    public boolean isBullet() {
        return getFlag(false, 0);
    }

    public boolean isBulletHard() {
        return getFlag(false, 0);
    }

    public void setBulletChar(char c) {
        setParaTextPropVal("bullet.char", c);
    }

    public char getBulletChar() {
        return (char) getParaTextPropVal("bullet.char");
    }

    public void setBulletOffset(int i) {
        setParaTextPropVal("bullet.offset", (int) (((float) (i * ShapeKit.MASTER_DPI)) / 72.0f));
    }

    public int getBulletOffset() {
        return (int) ((((float) getParaTextPropVal("bullet.offset")) * 72.0f) / 576.0f);
    }

    public void setTextOffset(int i) {
        setParaTextPropVal("text.offset", (int) (((float) (i * ShapeKit.MASTER_DPI)) / 72.0f));
    }

    public int getTextOffset() {
        return (int) ((((float) getParaTextPropVal("text.offset")) * 72.0f) / 576.0f);
    }

    public void setBulletSize(int i) {
        setParaTextPropVal("bullet.size", i);
    }

    public int getBulletSize() {
        return getParaTextPropVal("bullet.size");
    }

    public void setBulletColor(Color color) {
        setParaTextPropVal("bullet.color", new Color(color.getBlue(), color.getGreen(), color.getRed(), (int) TIFFConstants.TIFFTAG_SUBFILETYPE).getRGB());
    }

    public Color getBulletColor() {
        int paraTextPropVal = getParaTextPropVal("bullet.color");
        if (paraTextPropVal == -1) {
            return getFontColor();
        }
        int i = paraTextPropVal >> 24;
        if (paraTextPropVal % 16777216 == 0) {
            ColorSchemeAtom colorScheme = this.parentRun.getSheet().getColorScheme();
            if (i >= 0 && i <= 7) {
                paraTextPropVal = colorScheme.getColor(i);
            }
        }
        Color color = new Color(paraTextPropVal, true);
        return new Color(color.getBlue(), color.getGreen(), color.getRed());
    }

    public void setBulletFont(int i) {
        setParaTextPropVal("bullet.font", i);
        setFlag(false, 1, true);
    }

    public int getBulletFont() {
        return getParaTextPropVal("bullet.font");
    }

    public void setLineSpacing(int i) {
        setParaTextPropVal("linespacing", i);
    }

    public int getLineSpacing() {
        int paraTextPropVal = getParaTextPropVal("linespacing");
        if (paraTextPropVal == -1) {
            return 0;
        }
        return paraTextPropVal;
    }

    public void setSpaceBefore(int i) {
        setParaTextPropVal("spacebefore", i);
    }

    public int getSpaceBefore() {
        int paraTextPropVal = getParaTextPropVal("spacebefore");
        if (paraTextPropVal == -1) {
            return 0;
        }
        return paraTextPropVal;
    }

    public void setSpaceAfter(int i) {
        setParaTextPropVal("spaceafter", i);
    }

    public int getSpaceAfter() {
        int paraTextPropVal = getParaTextPropVal("spaceafter");
        if (paraTextPropVal == -1) {
            return 0;
        }
        return paraTextPropVal;
    }

    public TextPropCollection _getRawParagraphStyle() {
        return this.paragraphStyle;
    }

    public TextPropCollection _getRawCharacterStyle() {
        return this.characterStyle;
    }

    public boolean _isParagraphStyleShared() {
        return this.sharingParagraphStyle;
    }

    public boolean _isCharacterStyleShared() {
        return this.sharingCharacterStyle;
    }

    public void dispose() {
        this.parentRun = null;
        this.slideShow = null;
        this._fontname = null;
        TextPropCollection textPropCollection = this.paragraphStyle;
        if (textPropCollection != null) {
            textPropCollection.dispose();
            this.paragraphStyle = null;
        }
        TextPropCollection textPropCollection2 = this.characterStyle;
        if (textPropCollection2 != null) {
            textPropCollection2.dispose();
            this.characterStyle = null;
        }
    }
}
