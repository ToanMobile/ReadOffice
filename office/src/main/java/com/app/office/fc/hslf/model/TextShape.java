package com.app.office.fc.hslf.model;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.AbstractEscherOptRecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.ddf.EscherTextboxRecord;
import com.app.office.fc.hslf.record.EscherTextboxWrapper;
import com.app.office.fc.hslf.record.InteractiveInfo;
import com.app.office.fc.hslf.record.InteractiveInfoAtom;
import com.app.office.fc.hslf.record.OEPlaceholderAtom;
import com.app.office.fc.hslf.record.OutlineTextRefAtom;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.hslf.record.RecordTypes;
import com.app.office.fc.hslf.record.RoundTripHFPlaceholder12;
import com.app.office.fc.hslf.record.StyleTextPropAtom;
import com.app.office.fc.hslf.record.TextCharsAtom;
import com.app.office.fc.hslf.record.TextHeaderAtom;
import com.app.office.fc.hslf.record.TextRulerAtom;
import com.app.office.fc.hslf.record.TxInteractiveInfoAtom;
import com.app.office.fc.hslf.usermodel.RichTextRun;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.geom.Rectangle2D;

public abstract class TextShape extends SimpleShape {
    public static final int AlignCenter = 1;
    public static final int AlignJustify = 3;
    public static final int AlignLeft = 0;
    public static final int AlignRight = 2;
    public static final int AnchorBottom = 2;
    public static final int AnchorBottomBaseline = 7;
    public static final int AnchorBottomCentered = 5;
    public static final int AnchorBottomCenteredBaseline = 9;
    public static final int AnchorMiddle = 1;
    public static final int AnchorMiddleCentered = 4;
    public static final int AnchorTop = 0;
    public static final int AnchorTopBaseline = 6;
    public static final int AnchorTopCentered = 3;
    public static final int AnchorTopCenteredBaseline = 8;
    public static final int WrapByPoints = 1;
    public static final int WrapNone = 2;
    public static final int WrapSquare = 0;
    public static final int WrapThrough = 4;
    public static final int WrapTopBottom = 3;
    protected EscherTextboxWrapper _txtbox;
    protected TextRun _txtrun;

    /* access modifiers changed from: protected */
    public void setDefaultTextProperties(TextRun textRun) {
    }

    protected TextShape(EscherContainerRecord escherContainerRecord, Shape shape) {
        super(escherContainerRecord, shape);
    }

    public TextShape(Shape shape) {
        super((EscherContainerRecord) null, shape);
        this._escherContainer = createSpContainer(shape instanceof ShapeGroup);
    }

    public TextShape() {
        this((Shape) null);
    }

    public TextRun createTextRun() {
        EscherTextboxWrapper escherTextboxWrapper = getEscherTextboxWrapper();
        this._txtbox = escherTextboxWrapper;
        if (escherTextboxWrapper == null) {
            this._txtbox = new EscherTextboxWrapper();
        }
        TextRun textRun = getTextRun();
        this._txtrun = textRun;
        if (textRun == null) {
            TextHeaderAtom textHeaderAtom = new TextHeaderAtom();
            textHeaderAtom.setParentRecord(this._txtbox);
            this._txtbox.appendChildRecord(textHeaderAtom);
            TextCharsAtom textCharsAtom = new TextCharsAtom();
            this._txtbox.appendChildRecord(textCharsAtom);
            StyleTextPropAtom styleTextPropAtom = new StyleTextPropAtom(0);
            this._txtbox.appendChildRecord(styleTextPropAtom);
            TextRun textRun2 = new TextRun(textHeaderAtom, textCharsAtom, styleTextPropAtom);
            this._txtrun = textRun2;
            textRun2._records = new Record[]{textHeaderAtom, textCharsAtom, styleTextPropAtom};
            this._txtrun.setText("");
            this._escherContainer.addChildRecord(this._txtbox.getEscherRecord());
            setDefaultTextProperties(this._txtrun);
        }
        return this._txtrun;
    }

    public String getText() {
        TextRun textRun = getTextRun();
        if (textRun == null) {
            return null;
        }
        return textRun.getText();
    }

    public void setText(String str) {
        TextRun textRun = getTextRun();
        if (textRun == null) {
            textRun = createTextRun();
        }
        textRun.setText(str);
        setTextId(str.hashCode());
    }

    /* access modifiers changed from: protected */
    public void afterInsert(Sheet sheet) {
        super.afterInsert(sheet);
        EscherTextboxWrapper escherTextboxWrapper = getEscherTextboxWrapper();
        if (escherTextboxWrapper != null) {
            sheet.getPPDrawing().addTextboxWrapper(escherTextboxWrapper);
            if (getAnchor().equals(new Rectangle()) && !"".equals(getText())) {
                resizeToFitText();
            }
        }
        TextRun textRun = this._txtrun;
        if (textRun != null) {
            textRun.setShapeId(getShapeId());
            sheet.onAddTextShape(this);
        }
    }

    /* access modifiers changed from: protected */
    public EscherTextboxWrapper getEscherTextboxWrapper() {
        EscherTextboxRecord escherTextboxRecord;
        if (this._txtbox == null && (escherTextboxRecord = (EscherTextboxRecord) ShapeKit.getEscherChild(this._escherContainer, -4083)) != null) {
            this._txtbox = new EscherTextboxWrapper(escherTextboxRecord);
        }
        return this._txtbox;
    }

    public byte getMetaCharactersType() {
        EscherTextboxWrapper escherTextboxWrapper = getEscherTextboxWrapper();
        if (escherTextboxWrapper == null) {
            return -1;
        }
        Record[] childRecords = escherTextboxWrapper.getChildRecords();
        for (int i = 0; i < childRecords.length; i++) {
            if (childRecords[i] != null) {
                long recordType = childRecords[i].getRecordType();
                if (recordType == ((long) RecordTypes.SlideNumberMCAtom.typeID)) {
                    return 1;
                }
                if (recordType == ((long) RecordTypes.DateTimeMCAtom.typeID)) {
                    return 2;
                }
                if (recordType == ((long) RecordTypes.GenericDateMCAtom.typeID)) {
                    return 3;
                }
                if (recordType == ((long) RecordTypes.RTFDateTimeMCAtom.typeID)) {
                    return 5;
                }
                if (recordType == ((long) RecordTypes.FooterMCAtom.typeID)) {
                    return 4;
                }
            }
        }
        return -1;
    }

    public Rectangle2D resizeToFitText() {
        return getAnchor2D();
    }

    public int getVerticalAlignment() {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) ShapeKit.getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085), 135);
        if (escherSimpleProperty != null) {
            return escherSimpleProperty.getPropertyValue();
        }
        int runType = getTextRun().getRunType();
        MasterSheet masterSheet = getSheet().getMasterSheet();
        TextShape textShape = null;
        if (!(masterSheet == null || getPlaceholderAtom() == null)) {
            textShape = masterSheet.getPlaceholderByTextType(runType);
        }
        if (textShape != null) {
            return textShape.getVerticalAlignment();
        }
        return (runType == 0 || runType == 6) ? 1 : 0;
    }

    public void setVerticalAlignment(int i) {
        setEscherProperty(EscherProperties.TEXT__ANCHORTEXT, i);
    }

    public void setHorizontalAlignment(int i) {
        TextRun textRun = getTextRun();
        if (textRun != null) {
            textRun.getRichTextRuns()[0].setAlignment(i);
        }
    }

    public int getHorizontalAlignment() {
        TextRun textRun = getTextRun();
        if (textRun == null) {
            return -1;
        }
        return textRun.getRichTextRuns()[0].getAlignment();
    }

    public float getMarginBottom() {
        int i;
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) ShapeKit.getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085), 132);
        if (escherSimpleProperty == null) {
            i = 45720;
        } else {
            i = escherSimpleProperty.getPropertyValue();
        }
        return ((float) i) / 12700.0f;
    }

    public void setMarginBottom(float f) {
        setEscherProperty(132, (int) (f * 12700.0f));
    }

    public float getMarginLeft() {
        int i;
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) ShapeKit.getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085), 129);
        if (escherSimpleProperty == null) {
            i = 91440;
        } else {
            i = escherSimpleProperty.getPropertyValue();
        }
        return ((float) i) / 12700.0f;
    }

    public void setMarginLeft(float f) {
        setEscherProperty(129, (int) (f * 12700.0f));
    }

    public float getMarginRight() {
        int i;
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) ShapeKit.getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085), 131);
        if (escherSimpleProperty == null) {
            i = 91440;
        } else {
            i = escherSimpleProperty.getPropertyValue();
        }
        return ((float) i) / 12700.0f;
    }

    public void setMarginRight(float f) {
        setEscherProperty(131, (int) (f * 12700.0f));
    }

    public float getMarginTop() {
        int i;
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) ShapeKit.getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085), 130);
        if (escherSimpleProperty == null) {
            i = 45720;
        } else {
            i = escherSimpleProperty.getPropertyValue();
        }
        return ((float) i) / 12700.0f;
    }

    public void setMarginTop(float f) {
        setEscherProperty(130, (int) (f * 12700.0f));
    }

    public int getWordWrap() {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) ShapeKit.getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085), 133);
        if (escherSimpleProperty == null) {
            return 0;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    public void setWordWrap(int i) {
        setEscherProperty(133, i);
    }

    public int getTextId() {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) ShapeKit.getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085), 128);
        if (escherSimpleProperty == null) {
            return 0;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    public void setTextId(int i) {
        setEscherProperty(128, i);
    }

    public TextRun getTextRun() {
        if (this._txtrun == null) {
            initTextRun();
        }
        return this._txtrun;
    }

    public void setSheet(Sheet sheet) {
        this._sheet = sheet;
        TextRun textRun = getTextRun();
        if (textRun != null) {
            textRun.setSheet(this._sheet);
            RichTextRun[] richTextRuns = textRun.getRichTextRuns();
            for (RichTextRun supplySlideShow : richTextRuns) {
                supplySlideShow.supplySlideShow(this._sheet.getSlideShow());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void initTextRun() {
        EscherTextboxWrapper escherTextboxWrapper = getEscherTextboxWrapper();
        if (getSheet() != null && escherTextboxWrapper != null) {
            OutlineTextRefAtom outlineTextRefAtom = null;
            Record[] childRecords = escherTextboxWrapper.getChildRecords();
            int i = 0;
            while (true) {
                if (i >= childRecords.length) {
                    break;
                } else if (childRecords[i] instanceof OutlineTextRefAtom) {
                    outlineTextRefAtom = (OutlineTextRefAtom) childRecords[i];
                    break;
                } else {
                    i++;
                }
            }
            TextRun[] textRuns = this._sheet.getTextRuns();
            if (outlineTextRefAtom != null) {
                int textIndex = outlineTextRefAtom.getTextIndex();
                int i2 = 0;
                while (true) {
                    if (i2 < textRuns.length) {
                        if (textRuns[i2].getIndex() == textIndex && textRuns[i2].getShapeId() < 0) {
                            this._txtrun = textRuns[i2];
                            break;
                        }
                        i2++;
                    } else {
                        break;
                    }
                }
            } else {
                int shapeId = ((EscherSpRecord) this._escherContainer.getChildById(EscherSpRecord.RECORD_ID)).getShapeId();
                if (textRuns != null) {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= textRuns.length) {
                            break;
                        } else if (textRuns[i3].getShapeId() == shapeId) {
                            this._txtrun = textRuns[i3];
                            break;
                        } else {
                            i3++;
                        }
                    }
                }
            }
            if (this._txtrun != null) {
                for (int i4 = 0; i4 < childRecords.length; i4++) {
                    if (this._txtrun._ruler == null && (childRecords[i4] instanceof TextRulerAtom)) {
                        this._txtrun._ruler = (TextRulerAtom) childRecords[i4];
                    }
                    for (Record record : this._txtrun.getRecords()) {
                        if (childRecords[i4].getRecordType() == record.getRecordType()) {
                            childRecords[i4] = record;
                        }
                    }
                }
            }
        }
    }

    public OEPlaceholderAtom getPlaceholderAtom() {
        return (OEPlaceholderAtom) getClientDataRecord(RecordTypes.OEPlaceholderAtom.typeID);
    }

    public int getPlaceholderId() {
        OEPlaceholderAtom placeholderAtom = getPlaceholderAtom();
        if (placeholderAtom != null) {
            return placeholderAtom.getPlaceholderId();
        }
        RoundTripHFPlaceholder12 roundTripHFPlaceholder12 = (RoundTripHFPlaceholder12) getClientDataRecord(RecordTypes.RoundTripHFPlaceholder12.typeID);
        if (roundTripHFPlaceholder12 != null) {
            return roundTripHFPlaceholder12.getPlaceholderId();
        }
        return 0;
    }

    public void setHyperlink(int i, int i2, int i3) {
        InteractiveInfo interactiveInfo = new InteractiveInfo();
        InteractiveInfoAtom interactiveInfoAtom = interactiveInfo.getInteractiveInfoAtom();
        interactiveInfoAtom.setAction((byte) 4);
        interactiveInfoAtom.setHyperlinkType((byte) 8);
        interactiveInfoAtom.setHyperlinkID(i);
        this._txtbox.appendChildRecord(interactiveInfo);
        TxInteractiveInfoAtom txInteractiveInfoAtom = new TxInteractiveInfoAtom();
        txInteractiveInfoAtom.setStartIndex(i2);
        txInteractiveInfoAtom.setEndIndex(i3);
        this._txtbox.appendChildRecord(txInteractiveInfoAtom);
    }

    public Sheet getSheet() {
        return this._sheet;
    }

    public String getUnicodeGeoText() {
        return ShapeKit.getUnicodeGeoText(this._escherContainer);
    }

    public void dispose() {
        super.dispose();
        TextRun textRun = this._txtrun;
        if (textRun != null) {
            textRun.dispose();
            this._txtrun = null;
        }
        EscherTextboxWrapper escherTextboxWrapper = this._txtbox;
        if (escherTextboxWrapper != null) {
            escherTextboxWrapper.dispose();
            this._txtbox = null;
        }
    }
}
