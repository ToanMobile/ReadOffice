package com.app.office.fc.hssf.record;

import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.app.office.fc.ddf.DefaultEscherRecordFactory;
import com.app.office.fc.ddf.EscherBoolProperty;
import com.app.office.fc.ddf.EscherClientAnchorRecord;
import com.app.office.fc.ddf.EscherClientDataRecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherDgRecord;
import com.app.office.fc.ddf.EscherDggRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSerializationListener;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.ddf.EscherSpgrRecord;
import com.app.office.fc.ddf.EscherTextboxRecord;
import com.app.office.fc.hssf.model.AbstractShape;
import com.app.office.fc.hssf.model.CommentShape;
import com.app.office.fc.hssf.model.ConvertAnchor;
import com.app.office.fc.hssf.model.DrawingManager2;
import com.app.office.fc.hssf.model.TextboxShape;
import com.app.office.fc.hssf.usermodel.HSSFChart;
import com.app.office.fc.hssf.usermodel.HSSFClientAnchor;
import com.app.office.fc.hssf.usermodel.HSSFPatriarch;
import com.app.office.fc.hssf.usermodel.HSSFShape;
import com.app.office.fc.hssf.usermodel.HSSFShapeContainer;
import com.app.office.fc.hssf.usermodel.HSSFShapeFactory;
import com.app.office.fc.hssf.usermodel.HSSFShapeGroup;
import com.app.office.fc.hssf.usermodel.HSSFTextbox;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import com.app.office.ss.model.XLSModel.AWorkbook;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class EscherAggregate extends AbstractEscherHolderRecord {
    private static POILogger log = POILogFactory.getLogger(EscherAggregate.class);
    public static final short sid = 9876;
    private Map<EscherRecord, List<Record>> chartToObj = new HashMap();
    private short drawingGroupId;
    private DrawingManager2 drawingManager;
    protected HSSFPatriarch patriarch;
    private Map<EscherRecord, Record> shapeToObj = new HashMap();
    private List tailRec = new ArrayList();

    /* access modifiers changed from: protected */
    public String getRecordName() {
        return "ESCHERAGGREGATE";
    }

    public short getSid() {
        return sid;
    }

    public EscherAggregate(DrawingManager2 drawingManager2) {
        this.drawingManager = drawingManager2;
    }

    public String toString() {
        String property = System.getProperty("line.separtor");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('[');
        stringBuffer.append(getRecordName());
        stringBuffer.append(']' + property);
        for (EscherRecord obj : getEscherRecords()) {
            stringBuffer.append(obj.toString());
        }
        stringBuffer.append("[/");
        stringBuffer.append(getRecordName());
        stringBuffer.append(']' + property);
        return stringBuffer.toString();
    }

    public static int shapeContainRecords(List list, int i) {
        int i2 = 2;
        if (sid(list, i) == 236 || sid(list, i) == 60) {
            int i3 = i + 1;
            if (isObjectRecord(list, i3)) {
                Record record = (Record) list.get(i3);
                if (record instanceof ObjRecord) {
                    ObjRecord objRecord = (ObjRecord) record;
                    if ((objRecord.getSubRecords().get(0) instanceof CommonObjectDataSubRecord) && ((CommonObjectDataSubRecord) objRecord.getSubRecords().get(0)).getObjectType() == 5) {
                        ArrayList arrayList = new ArrayList();
                        int i4 = i + 2;
                        Object obj = list.get(i4);
                        while (true) {
                            Record record2 = (Record) obj;
                            if (record2.getSid() == 10) {
                                return i2 + 1;
                            }
                            arrayList.add(record2);
                            i4++;
                            i2++;
                            obj = list.get(i4);
                        }
                    }
                }
                if (list.get(i + 2) instanceof NoteRecord) {
                    return 3;
                }
                return 2;
            }
        }
        return 0;
    }

    public static int nextDrawingRecord(List list, int i) {
        int size = list.size();
        while (true) {
            i++;
            if (i >= size) {
                return -1;
            }
            Object obj = list.get(i);
            if (obj instanceof Record) {
                Record record = (Record) obj;
                if (record.getSid() == 236 || record.getSid() == 60) {
                    return i;
                }
            }
        }
        return i;
    }

    public static EscherAggregate createAggregate(List list, int i, DrawingManager2 drawingManager2) {
        int i2;
        byte[] bArr;
        int i3;
        int i4;
        final ArrayList arrayList = new ArrayList();
        AnonymousClass1 r1 = new DefaultEscherRecordFactory() {
            public EscherRecord createRecord(byte[] bArr, int i) {
                EscherRecord createRecord = super.createRecord(bArr, i);
                if (createRecord.getRecordId() == -4079 || createRecord.getRecordId() == -4083) {
                    arrayList.add(createRecord);
                }
                return createRecord;
            }
        };
        EscherAggregate escherAggregate = new EscherAggregate(drawingManager2);
        int i5 = i;
        int i6 = 0;
        while (i5 > -1 && (i3 = i5 + 1) < list.size() && (sid(list, i5) == 236 || sid(list, i5) == 60)) {
            if (isObjectRecord(list, i3)) {
                if (sid(list, i5) == 60) {
                    i4 = ((ContinueRecord) list.get(i5)).getDataSize();
                } else {
                    i4 = ((DrawingRecord) list.get(i5)).getData().length;
                }
                i6 += i4;
            }
            i5 = nextDrawingRecord(list, i5);
        }
        byte[] bArr2 = new byte[i6];
        int i7 = i;
        int i8 = 0;
        while (i7 > -1) {
            int i9 = i7 + 1;
            if (i9 >= list.size() || (sid(list, i7) != 236 && sid(list, i7) != 60)) {
                break;
            }
            if (isObjectRecord(list, i9)) {
                if (sid(list, i7) == 60) {
                    bArr = ((ContinueRecord) list.get(i7)).getData();
                } else {
                    bArr = ((DrawingRecord) list.get(i7)).getData();
                }
                if (bArr != null) {
                    System.arraycopy(bArr, 0, bArr2, i8, bArr.length);
                    i8 += bArr.length;
                }
            }
            i7 = nextDrawingRecord(list, i7);
        }
        int i10 = 0;
        while (i10 < i6) {
            try {
                EscherRecord createRecord = r1.createRecord(bArr2, i10);
                int fillFields = createRecord.fillFields(bArr2, i10, r1);
                escherAggregate.addEscherRecord(createRecord);
                i10 += fillFields;
            } catch (Exception unused) {
            }
        }
        escherAggregate.shapeToObj = new HashMap();
        int i11 = 0;
        while (i > -1) {
            int i12 = i + 1;
            if (i12 >= list.size() || (sid(list, i) != 236 && sid(list, i) != 60)) {
                break;
            } else if (!isObjectRecord(list, i12)) {
                i = nextDrawingRecord(list, i);
            } else {
                Record record = (Record) list.get(i12);
                try {
                    if (!(record instanceof ObjRecord) || !(((ObjRecord) record).getSubRecords().get(0) instanceof CommonObjectDataSubRecord)) {
                        i2 = i11 + 1;
                        escherAggregate.shapeToObj.put((EscherRecord) arrayList.get(i11), record);
                    } else if (((CommonObjectDataSubRecord) ((ObjRecord) record).getSubRecords().get(0)).getObjectType() == 5) {
                        ArrayList arrayList2 = new ArrayList();
                        int i13 = i + 2;
                        Record record2 = (Record) list.get(i13);
                        while (record2.getSid() != 10) {
                            arrayList2.add(record2);
                            i13++;
                            record2 = (Record) list.get(i13);
                        }
                        i2 = i11 + 1;
                        escherAggregate.chartToObj.put((EscherRecord) arrayList.get(i11), arrayList2);
                        i = i13 + 1;
                        i11 = i2;
                    } else {
                        i2 = i11 + 1;
                        escherAggregate.shapeToObj.put((EscherRecord) arrayList.get(i11), record);
                    }
                    i += 2;
                    i11 = i2;
                } catch (Exception unused2) {
                }
            }
        }
        return escherAggregate;
    }

    public int serialize(int i, byte[] bArr) {
        int i2;
        convertUserModelToRecords();
        List<EscherRecord> escherRecords = getEscherRecords();
        byte[] bArr2 = new byte[getEscherRecordSize(escherRecords)];
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        int i3 = 0;
        for (EscherRecord serialize : escherRecords) {
            i3 += serialize.serialize(i3, bArr2, new EscherSerializationListener() {
                public void beforeRecordSerialize(int i, short s, EscherRecord escherRecord) {
                }

                public void afterRecordSerialize(int i, short s, int i2, EscherRecord escherRecord) {
                    if (s == -4079 || s == -4083) {
                        arrayList.add(Integer.valueOf(i));
                        arrayList2.add(escherRecord);
                    }
                }
            });
        }
        arrayList2.add(0, (Object) null);
        arrayList.add(0, (Object) null);
        int i4 = i;
        for (int i5 = 1; i5 < arrayList2.size(); i5++) {
            int intValue = ((Integer) arrayList.get(i5)).intValue() - 1;
            if (i5 == 1) {
                i2 = 0;
            } else {
                i2 = ((Integer) arrayList.get(i5 - 1)).intValue();
            }
            DrawingRecord drawingRecord = new DrawingRecord();
            int i6 = (intValue - i2) + 1;
            byte[] bArr3 = new byte[i6];
            System.arraycopy(bArr2, i2, bArr3, 0, i6);
            drawingRecord.setData(bArr3);
            int serialize2 = i4 + drawingRecord.serialize(i4, bArr);
            i4 = serialize2 + this.shapeToObj.get(arrayList2.get(i5)).serialize(serialize2, bArr);
        }
        for (int i7 = 0; i7 < this.tailRec.size(); i7++) {
            i4 += ((Record) this.tailRec.get(i7)).serialize(i4, bArr);
        }
        int i8 = i4 - i;
        if (i8 == getRecordSize()) {
            return i8;
        }
        throw new RecordFormatException(i8 + " bytes written but getRecordSize() reports " + getRecordSize());
    }

    private int getEscherRecordSize(List list) {
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += ((EscherRecord) it.next()).getRecordSize();
        }
        return i;
    }

    public int getRecordSize() {
        convertUserModelToRecords();
        int escherRecordSize = getEscherRecordSize(getEscherRecords()) + (this.shapeToObj.size() * 4);
        int i = 0;
        int i2 = 0;
        for (Record recordSize : this.shapeToObj.values()) {
            i2 += recordSize.getRecordSize();
        }
        for (Record recordSize2 : this.tailRec) {
            i += recordSize2.getRecordSize();
        }
        return escherRecordSize + i2 + i;
    }

    /* access modifiers changed from: package-private */
    public Object associateShapeToObjRecord(EscherRecord escherRecord, ObjRecord objRecord) {
        return this.shapeToObj.put(escherRecord, objRecord);
    }

    public HSSFPatriarch getPatriarch() {
        return this.patriarch;
    }

    public void setPatriarch(HSSFPatriarch hSSFPatriarch) {
        this.patriarch = hSSFPatriarch;
    }

    public void convertRecordsToUserModel(AWorkbook aWorkbook) {
        EscherSpgrRecord escherSpgrRecord;
        if (this.patriarch != null) {
            List<EscherContainerRecord> list = getgetEscherContainers();
            if (list.size() != 0) {
                int i = 1;
                if (list.get(0).getChildContainers().size() > 0) {
                    List<EscherContainerRecord> childContainers = list.get(0).getChildContainers().get(0).getChildContainers();
                    if (childContainers.size() != 0) {
                        Iterator<EscherRecord> childIterator = childContainers.get(0).getChildIterator();
                        while (true) {
                            if (!childIterator.hasNext()) {
                                escherSpgrRecord = null;
                                break;
                            }
                            EscherRecord next = childIterator.next();
                            if (next instanceof EscherSpgrRecord) {
                                escherSpgrRecord = (EscherSpgrRecord) next;
                                break;
                            }
                        }
                        if (escherSpgrRecord != null) {
                            this.patriarch.setCoordinates(escherSpgrRecord.getRectX1(), escherSpgrRecord.getRectY1(), escherSpgrRecord.getRectX2(), escherSpgrRecord.getRectY2());
                        }
                        for (int i2 = 1; i2 < childContainers.size(); i2++) {
                            EscherContainerRecord escherContainerRecord = childContainers.get(i2);
                            HSSFShape createShape = HSSFShapeFactory.createShape(aWorkbook, this.shapeToObj, escherContainerRecord, (HSSFShape) null);
                            if (createShape != null) {
                                convertRecordsToUserModel(escherContainerRecord, createShape);
                                this.patriarch.addShape(createShape);
                            }
                        }
                    } else {
                        throw new IllegalStateException("No child escher containers at the point that should hold the patriach data, and one container per top level shape!");
                    }
                } else {
                    i = 0;
                }
                while (i < list.size()) {
                    EscherContainerRecord escherContainerRecord2 = list.get(i);
                    HSSFShape createShape2 = HSSFShapeFactory.createShape(aWorkbook, this.shapeToObj, escherContainerRecord2, (HSSFShape) null);
                    if (createShape2 != null) {
                        convertRecordsToUserModel(escherContainerRecord2, createShape2);
                        this.patriarch.addShape(createShape2);
                    }
                    i++;
                }
                this.drawingManager.getDgg().setFileIdClusters(new EscherDggRecord.FileIdCluster[0]);
                return;
            }
            return;
        }
        throw new IllegalStateException("Must call setPatriarch() first");
    }

    private void convertRecordsToUserModel(EscherContainerRecord escherContainerRecord, Object obj) {
        Iterator<EscherRecord> childIterator = escherContainerRecord.getChildIterator();
        while (childIterator.hasNext()) {
            EscherRecord next = childIterator.next();
            if (next instanceof EscherSpgrRecord) {
                EscherSpgrRecord escherSpgrRecord = (EscherSpgrRecord) next;
                if (obj instanceof HSSFShapeGroup) {
                    ((HSSFShapeGroup) obj).setCoordinates(escherSpgrRecord.getRectX1(), escherSpgrRecord.getRectY1(), escherSpgrRecord.getRectX2(), escherSpgrRecord.getRectY2());
                } else {
                    throw new IllegalStateException("Got top level anchor but not processing a group");
                }
            } else if (!(next instanceof EscherClientAnchorRecord)) {
                if (next instanceof EscherTextboxRecord) {
                    Record record = this.shapeToObj.get((EscherTextboxRecord) next);
                    if ((record instanceof TextObjectRecord) && (obj instanceof HSSFTextbox)) {
                        TextObjectRecord textObjectRecord = (TextObjectRecord) record;
                        HSSFTextbox hSSFTextbox = (HSSFTextbox) obj;
                        if (!hSSFTextbox.isWordArt()) {
                            hSSFTextbox.setString(textObjectRecord.getStr());
                        }
                        hSSFTextbox.setHorizontalAlignment((short) textObjectRecord.getHorizontalTextAlignment());
                        hSSFTextbox.setVerticalAlignment((short) textObjectRecord.getVerticalTextAlignment());
                    }
                } else if ((next instanceof EscherClientDataRecord) && (obj instanceof HSSFChart)) {
                    HSSFChart.convertRecordsToChart(this.chartToObj.get((EscherClientDataRecord) next), (HSSFChart) obj);
                } else if (!(next instanceof EscherSpRecord)) {
                    boolean z = next instanceof EscherOptRecord;
                }
            }
        }
    }

    public void clear() {
        clearEscherRecords();
        this.shapeToObj.clear();
        this.chartToObj.clear();
    }

    private static boolean isObjectRecord(List list, int i) {
        return sid(list, i) == 93 || sid(list, i) == 438;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: com.app.office.fc.ddf.EscherRecord} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: com.app.office.fc.ddf.EscherContainerRecord} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void convertUserModelToRecords() {
        /*
            r6 = this;
            com.app.office.fc.hssf.usermodel.HSSFPatriarch r0 = r6.patriarch
            if (r0 == 0) goto L_0x0055
            java.util.Map<com.app.office.fc.ddf.EscherRecord, com.app.office.fc.hssf.record.Record> r0 = r6.shapeToObj
            r0.clear()
            java.util.List r0 = r6.tailRec
            r0.clear()
            java.util.Map<com.app.office.fc.ddf.EscherRecord, java.util.List<com.app.office.fc.hssf.record.Record>> r0 = r6.chartToObj
            r0.clear()
            r6.clearEscherRecords()
            com.app.office.fc.hssf.usermodel.HSSFPatriarch r0 = r6.patriarch
            java.util.List r0 = r0.getChildren()
            int r0 = r0.size()
            if (r0 == 0) goto L_0x0055
            com.app.office.fc.hssf.usermodel.HSSFPatriarch r0 = r6.patriarch
            r6.convertPatriarch(r0)
            r0 = 0
            com.app.office.fc.ddf.EscherRecord r0 = r6.getEscherRecord(r0)
            com.app.office.fc.ddf.EscherContainerRecord r0 = (com.app.office.fc.ddf.EscherContainerRecord) r0
            java.util.Iterator r0 = r0.getChildIterator()
            r1 = 0
            r2 = r1
        L_0x0034:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x004c
            java.lang.Object r3 = r0.next()
            com.app.office.fc.ddf.EscherRecord r3 = (com.app.office.fc.ddf.EscherRecord) r3
            short r4 = r3.getRecordId()
            r5 = -4093(0xfffffffffffff003, float:NaN)
            if (r4 != r5) goto L_0x0034
            r2 = r3
            com.app.office.fc.ddf.EscherContainerRecord r2 = (com.app.office.fc.ddf.EscherContainerRecord) r2
            goto L_0x0034
        L_0x004c:
            com.app.office.fc.hssf.usermodel.HSSFPatriarch r0 = r6.patriarch
            java.util.Map<com.app.office.fc.ddf.EscherRecord, com.app.office.fc.hssf.record.Record> r3 = r6.shapeToObj
            r6.convertShapes(r0, r2, r3)
            r6.patriarch = r1
        L_0x0055:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.record.EscherAggregate.convertUserModelToRecords():void");
    }

    private void convertShapes(HSSFShapeContainer hSSFShapeContainer, EscherContainerRecord escherContainerRecord, Map map) {
        if (escherContainerRecord != null) {
            for (HSSFShape hSSFShape : hSSFShapeContainer.getChildren()) {
                if (hSSFShape instanceof HSSFShapeGroup) {
                    convertGroup((HSSFShapeGroup) hSSFShape, escherContainerRecord, map);
                } else {
                    AbstractShape createShape = AbstractShape.createShape(hSSFShape, this.drawingManager.allocateShapeId(this.drawingGroupId));
                    map.put(findClientData(createShape.getSpContainer()), createShape.getObjRecord());
                    if (createShape instanceof TextboxShape) {
                        TextboxShape textboxShape = (TextboxShape) createShape;
                        map.put(textboxShape.getEscherTextbox(), textboxShape.getTextObjectRecord());
                        if (createShape instanceof CommentShape) {
                            this.tailRec.add(((CommentShape) createShape).getNoteRecord());
                        }
                    }
                    escherContainerRecord.addChildRecord(createShape.getSpContainer());
                }
            }
            return;
        }
        throw new IllegalArgumentException("Parent record required");
    }

    private void convertGroup(HSSFShapeGroup hSSFShapeGroup, EscherContainerRecord escherContainerRecord, Map map) {
        EscherContainerRecord escherContainerRecord2 = new EscherContainerRecord();
        EscherContainerRecord escherContainerRecord3 = new EscherContainerRecord();
        EscherSpgrRecord escherSpgrRecord = new EscherSpgrRecord();
        EscherSpRecord escherSpRecord = new EscherSpRecord();
        EscherOptRecord escherOptRecord = new EscherOptRecord();
        EscherClientDataRecord escherClientDataRecord = new EscherClientDataRecord();
        escherContainerRecord2.setRecordId(EscherContainerRecord.SPGR_CONTAINER);
        escherContainerRecord2.setOptions(15);
        escherContainerRecord3.setRecordId(EscherContainerRecord.SP_CONTAINER);
        escherContainerRecord3.setOptions(15);
        escherSpgrRecord.setRecordId(EscherSpgrRecord.RECORD_ID);
        escherSpgrRecord.setOptions(1);
        escherSpgrRecord.setRectX1(hSSFShapeGroup.getX1());
        escherSpgrRecord.setRectY1(hSSFShapeGroup.getY1());
        escherSpgrRecord.setRectX2(hSSFShapeGroup.getX2());
        escherSpgrRecord.setRectY2(hSSFShapeGroup.getY2());
        escherSpRecord.setRecordId(EscherSpRecord.RECORD_ID);
        escherSpRecord.setOptions(2);
        int allocateShapeId = this.drawingManager.allocateShapeId(this.drawingGroupId);
        escherSpRecord.setShapeId(allocateShapeId);
        if (hSSFShapeGroup.getAnchor() instanceof HSSFClientAnchor) {
            escherSpRecord.setFlags(513);
        } else {
            escherSpRecord.setFlags(TIFFConstants.TIFFTAG_JPEGRESTARTINTERVAL);
        }
        escherOptRecord.setRecordId(EscherOptRecord.RECORD_ID);
        escherOptRecord.setOptions(35);
        escherOptRecord.addEscherProperty(new EscherBoolProperty(EscherProperties.PROTECTION__LOCKAGAINSTGROUPING, 262148));
        escherOptRecord.addEscherProperty(new EscherBoolProperty(959, 524288));
        EscherRecord createAnchor = ConvertAnchor.createAnchor(hSSFShapeGroup.getAnchor());
        escherClientDataRecord.setRecordId(EscherClientDataRecord.RECORD_ID);
        escherClientDataRecord.setOptions(0);
        escherContainerRecord2.addChildRecord(escherContainerRecord3);
        escherContainerRecord3.addChildRecord(escherSpgrRecord);
        escherContainerRecord3.addChildRecord(escherSpRecord);
        escherContainerRecord3.addChildRecord(escherOptRecord);
        escherContainerRecord3.addChildRecord(createAnchor);
        escherContainerRecord3.addChildRecord(escherClientDataRecord);
        ObjRecord objRecord = new ObjRecord();
        CommonObjectDataSubRecord commonObjectDataSubRecord = new CommonObjectDataSubRecord();
        commonObjectDataSubRecord.setObjectType(0);
        commonObjectDataSubRecord.setObjectId(allocateShapeId);
        commonObjectDataSubRecord.setLocked(true);
        commonObjectDataSubRecord.setPrintable(true);
        commonObjectDataSubRecord.setAutofill(true);
        commonObjectDataSubRecord.setAutoline(true);
        GroupMarkerSubRecord groupMarkerSubRecord = new GroupMarkerSubRecord();
        EndSubRecord endSubRecord = new EndSubRecord();
        objRecord.addSubRecord(commonObjectDataSubRecord);
        objRecord.addSubRecord(groupMarkerSubRecord);
        objRecord.addSubRecord(endSubRecord);
        map.put(escherClientDataRecord, objRecord);
        escherContainerRecord.addChildRecord(escherContainerRecord2);
        convertShapes(hSSFShapeGroup, escherContainerRecord2, map);
    }

    private EscherRecord findClientData(EscherContainerRecord escherContainerRecord) {
        Iterator<EscherRecord> childIterator = escherContainerRecord.getChildIterator();
        while (childIterator.hasNext()) {
            EscherRecord next = childIterator.next();
            if (next.getRecordId() == -4079) {
                return next;
            }
        }
        throw new IllegalArgumentException("Can not find client data record");
    }

    private void convertPatriarch(HSSFPatriarch hSSFPatriarch) {
        EscherContainerRecord escherContainerRecord = new EscherContainerRecord();
        EscherContainerRecord escherContainerRecord2 = new EscherContainerRecord();
        EscherContainerRecord escherContainerRecord3 = new EscherContainerRecord();
        EscherSpgrRecord escherSpgrRecord = new EscherSpgrRecord();
        EscherSpRecord escherSpRecord = new EscherSpRecord();
        escherContainerRecord.setRecordId(EscherContainerRecord.DG_CONTAINER);
        escherContainerRecord.setOptions(15);
        EscherDgRecord createDgRecord = this.drawingManager.createDgRecord();
        this.drawingGroupId = createDgRecord.getDrawingGroupId();
        escherContainerRecord2.setRecordId(EscherContainerRecord.SPGR_CONTAINER);
        escherContainerRecord2.setOptions(15);
        escherContainerRecord3.setRecordId(EscherContainerRecord.SP_CONTAINER);
        escherContainerRecord3.setOptions(15);
        escherSpgrRecord.setRecordId(EscherSpgrRecord.RECORD_ID);
        escherSpgrRecord.setOptions(1);
        escherSpgrRecord.setRectX1(hSSFPatriarch.getX1());
        escherSpgrRecord.setRectY1(hSSFPatriarch.getY1());
        escherSpgrRecord.setRectX2(hSSFPatriarch.getX2());
        escherSpgrRecord.setRectY2(hSSFPatriarch.getY2());
        escherSpRecord.setRecordId(EscherSpRecord.RECORD_ID);
        escherSpRecord.setOptions(2);
        escherSpRecord.setShapeId(this.drawingManager.allocateShapeId(createDgRecord.getDrawingGroupId()));
        escherSpRecord.setFlags(5);
        escherContainerRecord.addChildRecord(createDgRecord);
        escherContainerRecord.addChildRecord(escherContainerRecord2);
        escherContainerRecord2.addChildRecord(escherContainerRecord3);
        escherContainerRecord3.addChildRecord(escherSpgrRecord);
        escherContainerRecord3.addChildRecord(escherSpRecord);
        addEscherRecord(escherContainerRecord);
    }

    private static short sid(List list, int i) {
        return ((Record) list.get(i)).getSid();
    }
}
