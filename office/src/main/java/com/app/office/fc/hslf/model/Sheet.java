package com.app.office.fc.hslf.model;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherDgRecord;
import com.app.office.fc.ddf.EscherDggRecord;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.hslf.record.CString;
import com.app.office.fc.hslf.record.ColorSchemeAtom;
import com.app.office.fc.hslf.record.EscherTextboxWrapper;
import com.app.office.fc.hslf.record.ExtendedParagraphAtom;
import com.app.office.fc.hslf.record.OEPlaceholderAtom;
import com.app.office.fc.hslf.record.PPDrawing;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.hslf.record.RecordContainer;
import com.app.office.fc.hslf.record.RecordTypes;
import com.app.office.fc.hslf.record.RoundTripHFPlaceholder12;
import com.app.office.fc.hslf.record.SheetContainer;
import com.app.office.fc.hslf.usermodel.SlideShow;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public abstract class Sheet {
    private Background _background;
    private SheetContainer _container;
    private Shape[] _shapes;
    private int _sheetNo;
    private SlideShow _slideShow;

    public abstract MasterSheet getMasterSheet();

    public abstract TextRun[] getTextRuns();

    /* access modifiers changed from: protected */
    public void onAddTextShape(TextShape textShape) {
    }

    public void onCreate() {
    }

    public Sheet(SheetContainer sheetContainer, int i) {
        this._container = sheetContainer;
        this._sheetNo = i;
    }

    public int _getSheetRefId() {
        return this._container.getSheetId();
    }

    public int _getSheetNumber() {
        return this._sheetNo;
    }

    /* access modifiers changed from: protected */
    public PPDrawing getPPDrawing() {
        return this._container.getPPDrawing();
    }

    public SlideShow getSlideShow() {
        return this._slideShow;
    }

    public SheetContainer getSheetContainer() {
        return this._container;
    }

    public void setSlideShow(SlideShow slideShow) {
        this._slideShow = slideShow;
        TextRun[] textRuns = getTextRuns();
        if (textRuns != null) {
            for (TextRun supplySlideShow : textRuns) {
                supplySlideShow.supplySlideShow(this._slideShow);
            }
        }
    }

    public static TextRun[] findTextRuns(PPDrawing pPDrawing) {
        Vector vector = new Vector();
        EscherTextboxWrapper[] textboxWrappers = pPDrawing.getTextboxWrappers();
        for (int i = 0; i < textboxWrappers.length; i++) {
            int size = vector.size();
            RecordContainer.handleParentAwareRecords(textboxWrappers[i]);
            findTextRuns(textboxWrappers[i].getChildRecords(), vector);
            if (vector.size() != size) {
                TextRun textRun = (TextRun) vector.get(vector.size() - 1);
                textRun.setShapeId(textboxWrappers[i].getShapeId());
                boolean z = false;
                for (int i2 = i - 1; i2 >= 0; i2--) {
                    if (textboxWrappers[i2].getShapeId() == 5003) {
                        Record[] childRecords = textboxWrappers[i2].getChildRecords();
                        int i3 = 0;
                        while (true) {
                            if (i3 >= childRecords.length) {
                                break;
                            } else if (childRecords[0] instanceof ExtendedParagraphAtom) {
                                textRun.setExtendedParagraphAtom((ExtendedParagraphAtom) childRecords[i3]);
                                z = true;
                                break;
                            } else {
                                i3++;
                            }
                        }
                    }
                    if (z) {
                        break;
                    }
                }
            }
        }
        int size2 = vector.size();
        TextRun[] textRunArr = new TextRun[size2];
        for (int i4 = 0; i4 < size2; i4++) {
            textRunArr[i4] = (TextRun) vector.get(i4);
        }
        return textRunArr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0080  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static void findTextRuns(com.app.office.fc.hslf.record.Record[] r9, java.util.Vector r10) {
        /*
            r0 = 0
            r1 = 0
        L_0x0002:
            int r2 = r9.length
            int r2 = r2 + -1
            if (r0 >= r2) goto L_0x00b2
            r2 = r9[r0]
            boolean r2 = r2 instanceof com.app.office.fc.hslf.record.TextHeaderAtom
            if (r2 == 0) goto L_0x00ae
            r2 = r9[r0]
            com.app.office.fc.hslf.record.TextHeaderAtom r2 = (com.app.office.fc.hslf.record.TextHeaderAtom) r2
            int r3 = r9.length
            int r3 = r3 + -2
            r4 = 0
            if (r0 >= r3) goto L_0x0024
            int r3 = r0 + 2
            r5 = r9[r3]
            boolean r5 = r5 instanceof com.app.office.fc.hslf.record.StyleTextPropAtom
            if (r5 == 0) goto L_0x0024
            r3 = r9[r3]
            com.app.office.fc.hslf.record.StyleTextPropAtom r3 = (com.app.office.fc.hslf.record.StyleTextPropAtom) r3
            goto L_0x0025
        L_0x0024:
            r3 = r4
        L_0x0025:
            int r5 = r0 + 1
            r6 = r9[r5]
            boolean r6 = r6 instanceof com.app.office.fc.hslf.record.TextCharsAtom
            if (r6 == 0) goto L_0x0038
            r4 = r9[r5]
            com.app.office.fc.hslf.record.TextCharsAtom r4 = (com.app.office.fc.hslf.record.TextCharsAtom) r4
            com.app.office.fc.hslf.model.TextRun r6 = new com.app.office.fc.hslf.model.TextRun
            r6.<init>((com.app.office.fc.hslf.record.TextHeaderAtom) r2, (com.app.office.fc.hslf.record.TextCharsAtom) r4, (com.app.office.fc.hslf.record.StyleTextPropAtom) r3)
        L_0x0036:
            r4 = r6
            goto L_0x007e
        L_0x0038:
            r6 = r9[r5]
            boolean r6 = r6 instanceof com.app.office.fc.hslf.record.TextBytesAtom
            if (r6 == 0) goto L_0x0048
            r4 = r9[r5]
            com.app.office.fc.hslf.record.TextBytesAtom r4 = (com.app.office.fc.hslf.record.TextBytesAtom) r4
            com.app.office.fc.hslf.model.TextRun r6 = new com.app.office.fc.hslf.model.TextRun
            r6.<init>((com.app.office.fc.hslf.record.TextHeaderAtom) r2, (com.app.office.fc.hslf.record.TextBytesAtom) r4, (com.app.office.fc.hslf.record.StyleTextPropAtom) r3)
            goto L_0x0036
        L_0x0048:
            r2 = r9[r5]
            long r2 = r2.getRecordType()
            r6 = 4001(0xfa1, double:1.977E-320)
            int r8 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r8 != 0) goto L_0x0055
            goto L_0x007e
        L_0x0055:
            r2 = r9[r5]
            long r2 = r2.getRecordType()
            r6 = 4010(0xfaa, double:1.981E-320)
            int r8 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r8 != 0) goto L_0x0062
            goto L_0x007e
        L_0x0062:
            java.io.PrintStream r2 = java.lang.System.err
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r6 = "Found a TextHeaderAtom not followed by a TextBytesAtom or TextCharsAtom: Followed by "
            r3.append(r6)
            r6 = r9[r5]
            long r6 = r6.getRecordType()
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            r2.println(r3)
        L_0x007e:
            if (r4 == 0) goto L_0x00ac
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r3 = r0
        L_0x0086:
            int r6 = r9.length
            if (r3 >= r6) goto L_0x009a
            if (r3 <= r0) goto L_0x0092
            r6 = r9[r3]
            boolean r6 = r6 instanceof com.app.office.fc.hslf.record.TextHeaderAtom
            if (r6 == 0) goto L_0x0092
            goto L_0x009a
        L_0x0092:
            r6 = r9[r3]
            r2.add(r6)
            int r3 = r3 + 1
            goto L_0x0086
        L_0x009a:
            int r0 = r2.size()
            com.app.office.fc.hslf.record.Record[] r0 = new com.app.office.fc.hslf.record.Record[r0]
            r2.toArray(r0)
            r4._records = r0
            r4.setIndex(r1)
            r10.add(r4)
            r0 = r5
        L_0x00ac:
            int r1 = r1 + 1
        L_0x00ae:
            int r0 = r0 + 1
            goto L_0x0002
        L_0x00b2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hslf.model.Sheet.findTextRuns(com.app.office.fc.hslf.record.Record[], java.util.Vector):void");
    }

    public Shape[] getShapes() {
        EscherContainerRecord escherContainerRecord;
        Shape[] shapeArr = this._shapes;
        if (shapeArr != null) {
            return shapeArr;
        }
        Iterator<EscherRecord> childIterator = ((EscherContainerRecord) getPPDrawing().getEscherRecords()[0]).getChildIterator();
        while (true) {
            if (!childIterator.hasNext()) {
                escherContainerRecord = null;
                break;
            }
            EscherRecord next = childIterator.next();
            if (next.getRecordId() == -4093) {
                escherContainerRecord = (EscherContainerRecord) next;
                break;
            }
        }
        if (escherContainerRecord != null) {
            ArrayList arrayList = new ArrayList();
            Iterator<EscherRecord> childIterator2 = escherContainerRecord.getChildIterator();
            if (childIterator2.hasNext()) {
                childIterator2.next();
            }
            while (childIterator2.hasNext()) {
                Shape createShape = ShapeFactory.createShape((EscherContainerRecord) childIterator2.next(), (Shape) null);
                createShape.setSheet(this);
                arrayList.add(createShape);
            }
            Shape[] shapeArr2 = new Shape[arrayList.size()];
            this._shapes = shapeArr2;
            arrayList.toArray(shapeArr2);
            return this._shapes;
        }
        throw new IllegalStateException("spgr not found");
    }

    public void addShape(Shape shape) {
        ((EscherContainerRecord) ShapeKit.getEscherChild((EscherContainerRecord) getPPDrawing().getEscherRecords()[0], -4093)).addChildRecord(shape.getSpContainer());
        shape.setSheet(this);
        shape.setShapeId(allocateShapeId());
        shape.afterInsert(this);
    }

    public int allocateShapeId() {
        EscherDggRecord escherDggRecord = this._slideShow.getDocumentRecord().getPPDrawingGroup().getEscherDggRecord();
        EscherDgRecord escherDgRecord = this._container.getPPDrawing().getEscherDgRecord();
        escherDggRecord.setNumShapesSaved(escherDggRecord.getNumShapesSaved() + 1);
        int i = 0;
        while (i < escherDggRecord.getFileIdClusters().length) {
            EscherDggRecord.FileIdCluster fileIdCluster = escherDggRecord.getFileIdClusters()[i];
            if (fileIdCluster.getDrawingGroupId() != escherDgRecord.getDrawingGroupId() || fileIdCluster.getNumShapeIdsUsed() == 1024) {
                i++;
            } else {
                int numShapeIdsUsed = fileIdCluster.getNumShapeIdsUsed() + ((i + 1) * 1024);
                fileIdCluster.incrementShapeId();
                escherDgRecord.setNumShapes(escherDgRecord.getNumShapes() + 1);
                escherDgRecord.setLastMSOSPID(numShapeIdsUsed);
                if (numShapeIdsUsed >= escherDggRecord.getShapeIdMax()) {
                    escherDggRecord.setShapeIdMax(numShapeIdsUsed + 1);
                }
                return numShapeIdsUsed;
            }
        }
        escherDggRecord.addCluster(escherDgRecord.getDrawingGroupId(), 0, false);
        escherDggRecord.getFileIdClusters()[escherDggRecord.getFileIdClusters().length - 1].incrementShapeId();
        escherDgRecord.setNumShapes(escherDgRecord.getNumShapes() + 1);
        int length = escherDggRecord.getFileIdClusters().length * 1024;
        escherDgRecord.setLastMSOSPID(length);
        if (length >= escherDggRecord.getShapeIdMax()) {
            escherDggRecord.setShapeIdMax(length + 1);
        }
        return length;
    }

    public boolean removeShape(Shape shape) {
        EscherContainerRecord escherContainerRecord;
        Iterator<EscherRecord> childIterator = ((EscherContainerRecord) getPPDrawing().getEscherRecords()[0]).getChildIterator();
        while (true) {
            if (!childIterator.hasNext()) {
                escherContainerRecord = null;
                break;
            }
            EscherRecord next = childIterator.next();
            if (next.getRecordId() == -4093) {
                escherContainerRecord = (EscherContainerRecord) next;
                break;
            }
        }
        if (escherContainerRecord == null) {
            return false;
        }
        List<EscherRecord> childRecords = escherContainerRecord.getChildRecords();
        boolean remove = childRecords.remove(shape.getSpContainer());
        escherContainerRecord.setChildRecords(childRecords);
        return remove;
    }

    public ColorSchemeAtom getColorScheme() {
        return this._container.getColorScheme();
    }

    public Background getBackground() {
        EscherContainerRecord escherContainerRecord;
        if (this._background == null) {
            Iterator<EscherRecord> childIterator = ((EscherContainerRecord) getPPDrawing().getEscherRecords()[0]).getChildIterator();
            while (true) {
                if (!childIterator.hasNext()) {
                    escherContainerRecord = null;
                    break;
                }
                EscherRecord next = childIterator.next();
                if (next.getRecordId() == -4092) {
                    escherContainerRecord = (EscherContainerRecord) next;
                    break;
                }
            }
            if (escherContainerRecord != null) {
                Background background = new Background(escherContainerRecord, (Shape) null);
                this._background = background;
                background.setSheet(this);
            }
        }
        return this._background;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000e, code lost:
        r2 = (com.app.office.fc.hslf.model.TextShape) r0[r1];
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.app.office.fc.hslf.model.TextShape getPlaceholderByTextType(int r5) {
        /*
            r4 = this;
            com.app.office.fc.hslf.model.Shape[] r0 = r4.getShapes()
            r1 = 0
        L_0x0005:
            int r2 = r0.length
            if (r1 >= r2) goto L_0x0022
            r2 = r0[r1]
            boolean r2 = r2 instanceof com.app.office.fc.hslf.model.TextShape
            if (r2 == 0) goto L_0x001f
            r2 = r0[r1]
            com.app.office.fc.hslf.model.TextShape r2 = (com.app.office.fc.hslf.model.TextShape) r2
            com.app.office.fc.hslf.model.TextRun r3 = r2.getTextRun()
            if (r3 == 0) goto L_0x001f
            int r3 = r3.getRunType()
            if (r3 != r5) goto L_0x001f
            return r2
        L_0x001f:
            int r1 = r1 + 1
            goto L_0x0005
        L_0x0022:
            r5 = 0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hslf.model.Sheet.getPlaceholderByTextType(int):com.app.office.fc.hslf.model.TextShape");
    }

    public TextShape getPlaceholder(int i) {
        int i2;
        Shape[] shapes = getShapes();
        for (int i3 = 0; i3 < shapes.length; i3++) {
            if (shapes[i3] instanceof TextShape) {
                TextShape textShape = (TextShape) shapes[i3];
                OEPlaceholderAtom placeholderAtom = textShape.getPlaceholderAtom();
                if (placeholderAtom != null) {
                    i2 = placeholderAtom.getPlaceholderId();
                } else {
                    RoundTripHFPlaceholder12 roundTripHFPlaceholder12 = (RoundTripHFPlaceholder12) textShape.getClientDataRecord(RecordTypes.RoundTripHFPlaceholder12.typeID);
                    i2 = roundTripHFPlaceholder12 != null ? roundTripHFPlaceholder12.getPlaceholderId() : 0;
                }
                if (i2 == i) {
                    return textShape;
                }
            }
        }
        return null;
    }

    public String getProgrammableTag() {
        RecordContainer recordContainer;
        CString cString;
        RecordContainer recordContainer2 = (RecordContainer) getSheetContainer().findFirstOfType((long) RecordTypes.SlideProgTagsContainer.typeID);
        if (recordContainer2 == null || (recordContainer = (RecordContainer) recordContainer2.findFirstOfType((long) RecordTypes.SlideProgBinaryTagContainer.typeID)) == null || (cString = (CString) recordContainer.findFirstOfType((long) RecordTypes.CString.typeID)) == null) {
            return null;
        }
        return cString.getText();
    }

    public void dispose() {
        this._slideShow = null;
        Background background = this._background;
        if (background != null) {
            background.dispose();
            this._background = null;
        }
        Shape[] shapeArr = this._shapes;
        if (shapeArr != null) {
            for (Shape dispose : shapeArr) {
                dispose.dispose();
            }
            this._shapes = null;
        }
        SheetContainer sheetContainer = this._container;
        if (sheetContainer != null) {
            sheetContainer.dispose();
            this._container = null;
        }
    }
}
