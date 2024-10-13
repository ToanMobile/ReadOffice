package com.app.office.fc.hslf.model;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.app.office.fc.hslf.record.ColorSchemeAtom;
import com.app.office.fc.hslf.record.Comment2000;
import com.app.office.fc.hslf.record.ExtendedParagraphHeaderAtom;
import com.app.office.fc.hslf.record.ExtendedPresRuleContainer;
import com.app.office.fc.hslf.record.HeadersFootersContainer;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.hslf.record.RecordContainer;
import com.app.office.fc.hslf.record.RecordTypes;
import com.app.office.fc.hslf.record.SlideAtom;
import com.app.office.fc.hslf.record.SlideListWithText;
import com.app.office.fc.hslf.record.SlideProgTagsContainer;
import com.app.office.fc.hslf.record.SlideShowSlideInfoAtom;
import com.app.office.java.awt.Rectangle;
import java.util.Vector;

public final class Slide extends Sheet {
    private SlideListWithText.SlideAtomsSet _atomSet;
    private ExtendedPresRuleContainer.ExtendedParaAtomsSet[] _extendedAtomsSets;
    private Notes _notes;
    private TextRun[] _runs;
    private int _slideNo;
    private SlideProgTagsContainer propTagsContainer;
    private SlideShowSlideInfoAtom ssSlideInfoAtom;

    public Slide(com.app.office.fc.hslf.record.Slide slide, Notes notes, SlideListWithText.SlideAtomsSet slideAtomsSet, ExtendedPresRuleContainer.ExtendedParaAtomsSet[] extendedParaAtomsSetArr, int i, int i2) {
        super(slide, i);
        this._notes = notes;
        this._atomSet = slideAtomsSet;
        this._slideNo = i2;
        this._extendedAtomsSets = extendedParaAtomsSetArr;
        TextRun[] findTextRuns = findTextRuns(getPPDrawing());
        Vector vector = new Vector();
        SlideListWithText.SlideAtomsSet slideAtomsSet2 = this._atomSet;
        if (slideAtomsSet2 != null) {
            findTextRuns(slideAtomsSet2.getSlideRecords(), vector);
        }
        this._runs = new TextRun[(vector.size() + findTextRuns.length)];
        int i3 = 0;
        while (i3 < vector.size()) {
            this._runs[i3] = (TextRun) vector.get(i3);
            this._runs[i3].setSheet(this);
            i3++;
        }
        for (TextRun textRun : findTextRuns) {
            TextRun[] textRunArr = this._runs;
            textRunArr[i3] = textRun;
            textRunArr[i3].setSheet(this);
            i3++;
        }
        if (this._extendedAtomsSets != null) {
            int i4 = 0;
            while (true) {
                TextRun[] textRunArr2 = this._runs;
                if (i4 < textRunArr2.length) {
                    if (textRunArr2[i4].getExtendedParagraphAtom() == null) {
                        int runType = this._runs[i4].getRunType();
                        int i5 = 0;
                        while (true) {
                            ExtendedPresRuleContainer.ExtendedParaAtomsSet[] extendedParaAtomsSetArr2 = this._extendedAtomsSets;
                            if (i5 < extendedParaAtomsSetArr2.length) {
                                ExtendedParagraphHeaderAtom extendedParaHeaderAtom = extendedParaAtomsSetArr2[i5].getExtendedParaHeaderAtom();
                                if (extendedParaHeaderAtom != null && extendedParaHeaderAtom.getTextType() == runType) {
                                    this._runs[i4].setExtendedParagraphAtom(this._extendedAtomsSets[i5].getExtendedParaAtom());
                                    break;
                                }
                                i5++;
                            } else {
                                break;
                            }
                        }
                    }
                    i4++;
                } else {
                    return;
                }
            }
        }
    }

    public Slide(int i, int i2, int i3) {
        super(new com.app.office.fc.hslf.record.Slide(), i);
        this._slideNo = i3;
        getSheetContainer().setSheetId(i2);
    }

    public void setNotes(Notes notes) {
        this._notes = notes;
        SlideAtom slideAtom = getSlideRecord().getSlideAtom();
        if (notes == null) {
            slideAtom.setNotesID(0);
        } else {
            slideAtom.setNotesID(notes._getSheetNumber());
        }
    }

    public void setSlideNumber(int i) {
        this._slideNo = i;
    }

    /* JADX WARNING: type inference failed for: r1v11, types: [com.app.office.fc.ddf.EscherRecord] */
    /* JADX WARNING: type inference failed for: r1v12, types: [com.app.office.fc.ddf.EscherRecord] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate() {
        /*
            r9 = this;
            com.app.office.fc.hslf.usermodel.SlideShow r0 = r9.getSlideShow()
            com.app.office.fc.hslf.record.Document r0 = r0.getDocumentRecord()
            com.app.office.fc.hslf.record.PPDrawingGroup r0 = r0.getPPDrawingGroup()
            com.app.office.fc.ddf.EscherDggRecord r0 = r0.getEscherDggRecord()
            com.app.office.fc.hslf.record.SheetContainer r1 = r9.getSheetContainer()
            com.app.office.fc.hslf.record.PPDrawing r1 = r1.getPPDrawing()
            com.app.office.fc.ddf.EscherRecord[] r1 = r1.getEscherRecords()
            r2 = 0
            r1 = r1[r2]
            com.app.office.fc.ddf.EscherContainerRecord r1 = (com.app.office.fc.ddf.EscherContainerRecord) r1
            r3 = -4088(0xfffffffffffff008, float:NaN)
            com.app.office.fc.ddf.EscherRecord r3 = com.app.office.fc.ShapeKit.getEscherChild(r1, r3)
            com.app.office.fc.ddf.EscherDgRecord r3 = (com.app.office.fc.ddf.EscherDgRecord) r3
            int r4 = r0.getMaxDrawingGroupId()
            r5 = 1
            int r4 = r4 + r5
            int r6 = r4 << 4
            short r6 = (short) r6
            r3.setOptions(r6)
            int r6 = r0.getDrawingsSaved()
            int r6 = r6 + r5
            r0.setDrawingsSaved(r6)
            r0.setMaxDrawingGroupId(r4)
            java.util.List r0 = r1.getChildContainers()
            java.util.Iterator r0 = r0.iterator()
        L_0x0048:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0083
            java.lang.Object r1 = r0.next()
            com.app.office.fc.ddf.EscherContainerRecord r1 = (com.app.office.fc.ddf.EscherContainerRecord) r1
            r4 = 0
            short r6 = r1.getRecordId()
            r7 = -4093(0xfffffffffffff003, float:NaN)
            r8 = -4086(0xfffffffffffff00a, float:NaN)
            if (r6 == r7) goto L_0x006c
            r7 = -4092(0xfffffffffffff004, float:NaN)
            if (r6 == r7) goto L_0x0064
            goto L_0x0079
        L_0x0064:
            com.app.office.fc.ddf.EscherRecord r1 = r1.getChildById(r8)
            r4 = r1
            com.app.office.fc.ddf.EscherSpRecord r4 = (com.app.office.fc.ddf.EscherSpRecord) r4
            goto L_0x0079
        L_0x006c:
            com.app.office.fc.ddf.EscherRecord r1 = r1.getChild(r2)
            com.app.office.fc.ddf.EscherContainerRecord r1 = (com.app.office.fc.ddf.EscherContainerRecord) r1
            com.app.office.fc.ddf.EscherRecord r1 = r1.getChildById(r8)
            r4 = r1
            com.app.office.fc.ddf.EscherSpRecord r4 = (com.app.office.fc.ddf.EscherSpRecord) r4
        L_0x0079:
            if (r4 == 0) goto L_0x0048
            int r1 = r9.allocateShapeId()
            r4.setShapeId(r1)
            goto L_0x0048
        L_0x0083:
            r3.setNumShapes(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hslf.model.Slide.onCreate():void");
    }

    public TextBox addTitle() {
        Placeholder placeholder = new Placeholder();
        placeholder.setShapeType(1);
        placeholder.getTextRun().setRunType(0);
        placeholder.setText("Click to edit title");
        placeholder.setAnchor(new Rectangle(54, 48, TypedValues.MotionType.TYPE_QUANTIZE_INTERPOLATOR_ID, 90));
        addShape(placeholder);
        return placeholder;
    }

    public String getTitle() {
        TextRun[] textRuns = getTextRuns();
        for (int i = 0; i < textRuns.length; i++) {
            int runType = textRuns[i].getRunType();
            if (runType == 6 || runType == 0) {
                return textRuns[i].getText();
            }
        }
        return null;
    }

    public TextRun[] getTextRuns() {
        return this._runs;
    }

    public int getSlideNumber() {
        return this._slideNo;
    }

    public com.app.office.fc.hslf.record.Slide getSlideRecord() {
        return (com.app.office.fc.hslf.record.Slide) getSheetContainer();
    }

    public Notes getNotesSheet() {
        return this._notes;
    }

    /* access modifiers changed from: protected */
    public SlideListWithText.SlideAtomsSet getSlideAtomsSet() {
        return this._atomSet;
    }

    public MasterSheet getMasterSheet() {
        SlideMaster slideMaster;
        TitleMaster[] titleMasters;
        SlideMaster[] slidesMasters = getSlideShow().getSlidesMasters();
        int masterID = getSlideRecord().getSlideAtom().getMasterID();
        int i = 0;
        while (true) {
            if (i >= slidesMasters.length) {
                slideMaster = null;
                break;
            } else if (masterID == slidesMasters[i]._getSheetNumber()) {
                slideMaster = slidesMasters[i];
                break;
            } else {
                i++;
            }
        }
        if (slideMaster != null || (titleMasters = getSlideShow().getTitleMasters()) == null) {
            return slideMaster;
        }
        for (int i2 = 0; i2 < titleMasters.length; i2++) {
            if (masterID == titleMasters[i2]._getSheetNumber()) {
                return titleMasters[i2];
            }
        }
        return slideMaster;
    }

    public void setMasterSheet(MasterSheet masterSheet) {
        getSlideRecord().getSlideAtom().setMasterID(masterSheet._getSheetNumber());
    }

    public HeadersFooters getSlideHeadersFooters() {
        HeadersFootersContainer headersFootersContainer = getSlideRecord().getHeadersFootersContainer();
        if (headersFootersContainer != null) {
            return new HeadersFooters(headersFootersContainer, (Sheet) this, false, false);
        }
        return null;
    }

    public void setFollowMasterBackground(boolean z) {
        getSlideRecord().getSlideAtom().setFollowMasterBackground(z);
    }

    public boolean getFollowMasterBackground() {
        return getSlideRecord().getSlideAtom().getFollowMasterBackground();
    }

    public void setFollowMasterObjects(boolean z) {
        getSlideRecord().getSlideAtom().setFollowMasterObjects(z);
    }

    public boolean getFollowMasterScheme() {
        return getSlideRecord().getSlideAtom().getFollowMasterScheme();
    }

    public void setFollowMasterScheme(boolean z) {
        getSlideRecord().getSlideAtom().setFollowMasterScheme(z);
    }

    public boolean getFollowMasterObjects() {
        return getSlideRecord().getSlideAtom().getFollowMasterObjects();
    }

    public Background getBackground() {
        if (getFollowMasterBackground()) {
            return getMasterSheet().getBackground();
        }
        return super.getBackground();
    }

    public ColorSchemeAtom getColorScheme() {
        if (getFollowMasterScheme()) {
            return getMasterSheet().getColorScheme();
        }
        return super.getColorScheme();
    }

    public Comment[] getComments() {
        RecordContainer recordContainer;
        RecordContainer recordContainer2;
        RecordContainer recordContainer3 = (RecordContainer) getSheetContainer().findFirstOfType((long) RecordTypes.SlideProgTagsContainer.typeID);
        if (recordContainer3 == null || (recordContainer = (RecordContainer) recordContainer3.findFirstOfType((long) RecordTypes.SlideProgBinaryTagContainer.typeID)) == null || (recordContainer2 = (RecordContainer) recordContainer.findFirstOfType((long) RecordTypes.BinaryTagDataBlob.typeID)) == null) {
            return new Comment[0];
        }
        int i = 0;
        for (Record record : recordContainer2.getChildRecords()) {
            if (record instanceof Comment2000) {
                i++;
            }
        }
        Comment[] commentArr = new Comment[i];
        for (int i2 = 0; i2 < recordContainer2.getChildRecords().length; i2++) {
            if (recordContainer2.getChildRecords()[i2] instanceof Comment2000) {
                commentArr[i2] = new Comment((Comment2000) recordContainer2.getChildRecords()[i2]);
            }
        }
        return commentArr;
    }

    /* access modifiers changed from: protected */
    public void onAddTextShape(TextShape textShape) {
        TextRun textRun = textShape.getTextRun();
        TextRun[] textRunArr = this._runs;
        if (textRunArr == null) {
            this._runs = new TextRun[]{textRun};
            return;
        }
        int length = textRunArr.length + 1;
        TextRun[] textRunArr2 = new TextRun[length];
        System.arraycopy(textRunArr, 0, textRunArr2, 0, textRunArr.length);
        textRunArr2[length - 1] = textRun;
        this._runs = textRunArr2;
    }

    public void setExtendedAtom(ExtendedPresRuleContainer.ExtendedParaAtomsSet[] extendedParaAtomsSetArr) {
        this._extendedAtomsSets = extendedParaAtomsSetArr;
    }

    public void setSlideShowSlideInfoAtom(SlideShowSlideInfoAtom slideShowSlideInfoAtom) {
        this.ssSlideInfoAtom = slideShowSlideInfoAtom;
    }

    public SlideShowSlideInfoAtom getSlideShowSlideInfoAtom() {
        return this.ssSlideInfoAtom;
    }

    public void setSlideProgTagsContainer(SlideProgTagsContainer slideProgTagsContainer) {
        this.propTagsContainer = slideProgTagsContainer;
    }

    public SlideProgTagsContainer getSlideProgTagsContainer() {
        return this.propTagsContainer;
    }

    public void dispose() {
        super.dispose();
        SlideListWithText.SlideAtomsSet slideAtomsSet = this._atomSet;
        if (slideAtomsSet != null) {
            slideAtomsSet.dispose();
            this._atomSet = null;
        }
        TextRun[] textRunArr = this._runs;
        if (textRunArr != null) {
            for (TextRun dispose : textRunArr) {
                dispose.dispose();
            }
            this._runs = null;
        }
        Notes notes = this._notes;
        if (notes != null) {
            notes.dispose();
            this._notes = null;
        }
        ExtendedPresRuleContainer.ExtendedParaAtomsSet[] extendedParaAtomsSetArr = this._extendedAtomsSets;
        if (extendedParaAtomsSetArr != null) {
            for (ExtendedPresRuleContainer.ExtendedParaAtomsSet dispose2 : extendedParaAtomsSetArr) {
                dispose2.dispose();
            }
            this._extendedAtomsSets = null;
        }
        SlideShowSlideInfoAtom slideShowSlideInfoAtom = this.ssSlideInfoAtom;
        if (slideShowSlideInfoAtom != null) {
            slideShowSlideInfoAtom.dispose();
            this.ssSlideInfoAtom = null;
        }
        SlideProgTagsContainer slideProgTagsContainer = this.propTagsContainer;
        if (slideProgTagsContainer != null) {
            slideProgTagsContainer.dispose();
            this.propTagsContainer = null;
        }
    }
}
