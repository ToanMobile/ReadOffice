package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.hssf.record.EmbeddedObjectRefSubRecord;
import com.app.office.fc.hssf.record.ObjRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.SubRecord;
import com.app.office.ss.model.XLSModel.AWorkbook;
import java.util.Map;

public final class HSSFShapeFactory {
    public static HSSFShape createShape(AWorkbook aWorkbook, Map<EscherRecord, Record> map, EscherContainerRecord escherContainerRecord, HSSFShape hSSFShape) {
        if (escherContainerRecord.getRecordId() == -4093) {
            return createShapeGroup(aWorkbook, map, escherContainerRecord, hSSFShape);
        }
        return createSimpeShape(aWorkbook, map, escherContainerRecord, hSSFShape);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0091  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00aa A[LOOP:0: B:28:0x00a4->B:30:0x00aa, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.app.office.fc.hssf.usermodel.HSSFShapeGroup createShapeGroup(com.app.office.ss.model.XLSModel.AWorkbook r9, java.util.Map<com.app.office.fc.ddf.EscherRecord, com.app.office.fc.hssf.record.Record> r10, com.app.office.fc.ddf.EscherContainerRecord r11, com.app.office.fc.hssf.usermodel.HSSFShape r12) {
        /*
            java.util.List r11 = r11.getChildRecords()
            int r0 = r11.size()
            r1 = 0
            if (r0 <= 0) goto L_0x00ba
            r0 = 0
            java.lang.Object r2 = r11.get(r0)
            com.app.office.fc.ddf.EscherContainerRecord r2 = (com.app.office.fc.ddf.EscherContainerRecord) r2
            if (r12 != 0) goto L_0x0034
            r3 = -4080(0xfffffffffffff010, float:NaN)
            com.app.office.fc.ddf.EscherRecord r3 = com.app.office.fc.ShapeKit.getEscherChild(r2, r3)
            com.app.office.fc.ddf.EscherClientAnchorRecord r3 = (com.app.office.fc.ddf.EscherClientAnchorRecord) r3
            if (r3 == 0) goto L_0x0043
            short r4 = r3.getCol2()
            r5 = 255(0xff, float:3.57E-43)
            if (r4 > r5) goto L_0x0043
            short r4 = r3.getRow2()
            r5 = 65535(0xffff, float:9.1834E-41)
            if (r4 > r5) goto L_0x0043
            com.app.office.fc.hssf.usermodel.HSSFClientAnchor r3 = com.app.office.fc.hssf.usermodel.HSSFShape.toClientAnchor(r3)
            goto L_0x0044
        L_0x0034:
            r3 = -4081(0xfffffffffffff00f, float:NaN)
            com.app.office.fc.ddf.EscherRecord r3 = com.app.office.fc.ShapeKit.getEscherChild(r2, r3)
            com.app.office.fc.ddf.EscherChildAnchorRecord r3 = (com.app.office.fc.ddf.EscherChildAnchorRecord) r3
            if (r3 == 0) goto L_0x0043
            com.app.office.fc.hssf.usermodel.HSSFChildAnchor r3 = com.app.office.fc.hssf.usermodel.HSSFShape.toChildAnchor(r3)
            goto L_0x0044
        L_0x0043:
            r3 = r1
        L_0x0044:
            if (r3 != 0) goto L_0x004b
            com.app.office.fc.hssf.usermodel.HSSFClientAnchor r3 = new com.app.office.fc.hssf.usermodel.HSSFClientAnchor
            r3.<init>()
        L_0x004b:
            r4 = -3806(0xfffffffffffff122, float:NaN)
            com.app.office.fc.ddf.EscherRecord r4 = com.app.office.fc.ShapeKit.getEscherChild(r2, r4)
            r5 = 1
            if (r4 == 0) goto L_0x0081
            com.app.office.fc.ddf.EscherPropertyFactory r6 = new com.app.office.fc.ddf.EscherPropertyFactory
            r6.<init>()
            byte[] r7 = r4.serialize()
            r8 = 8
            short r4 = r4.getInstance()
            java.util.List r4 = r6.createProperties(r7, r8, r4)
            java.lang.Object r0 = r4.get(r0)
            com.app.office.fc.ddf.EscherSimpleProperty r0 = (com.app.office.fc.ddf.EscherSimpleProperty) r0
            short r4 = r0.getPropertyNumber()
            r6 = 927(0x39f, float:1.299E-42)
            if (r4 != r6) goto L_0x007b
            int r0 = r0.getPropertyValue()
            if (r0 == r5) goto L_0x0087
        L_0x007b:
            com.app.office.fc.hssf.usermodel.HSSFShapeGroup r1 = new com.app.office.fc.hssf.usermodel.HSSFShapeGroup
            r1.<init>(r2, r12, r3)
            goto L_0x0087
        L_0x0081:
            com.app.office.fc.hssf.usermodel.HSSFShapeGroup r0 = new com.app.office.fc.hssf.usermodel.HSSFShapeGroup
            r0.<init>(r2, r12, r3)
            r1 = r0
        L_0x0087:
            r12 = -4087(0xfffffffffffff009, float:NaN)
            com.app.office.fc.ddf.EscherRecord r12 = com.app.office.fc.ShapeKit.getEscherChild(r2, r12)
            com.app.office.fc.ddf.EscherSpgrRecord r12 = (com.app.office.fc.ddf.EscherSpgrRecord) r12
            if (r12 == 0) goto L_0x00a4
            int r0 = r12.getRectX1()
            int r2 = r12.getRectY1()
            int r3 = r12.getRectX2()
            int r12 = r12.getRectY2()
            r1.setCoordinates(r0, r2, r3, r12)
        L_0x00a4:
            int r12 = r11.size()
            if (r5 >= r12) goto L_0x00ba
            java.lang.Object r12 = r11.get(r5)
            com.app.office.fc.ddf.EscherContainerRecord r12 = (com.app.office.fc.ddf.EscherContainerRecord) r12
            com.app.office.fc.hssf.usermodel.HSSFShape r12 = createShape(r9, r10, r12, r1)
            r1.addChildShape(r12)
            int r5 = r5 + 1
            goto L_0x00a4
        L_0x00ba:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.usermodel.HSSFShapeFactory.createShapeGroup(com.app.office.ss.model.XLSModel.AWorkbook, java.util.Map, com.app.office.fc.ddf.EscherContainerRecord, com.app.office.fc.hssf.usermodel.HSSFShape):com.app.office.fc.hssf.usermodel.HSSFShapeGroup");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0040 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.app.office.fc.hssf.usermodel.HSSFShape createSimpeShape(com.app.office.ss.model.XLSModel.AWorkbook r8, java.util.Map<com.app.office.fc.ddf.EscherRecord, com.app.office.fc.hssf.record.Record> r9, com.app.office.fc.ddf.EscherContainerRecord r10, com.app.office.fc.hssf.usermodel.HSSFShape r11) {
        /*
            r0 = 0
            if (r11 != 0) goto L_0x0025
            r1 = -4080(0xfffffffffffff010, float:NaN)
            com.app.office.fc.ddf.EscherRecord r1 = com.app.office.fc.ShapeKit.getEscherChild(r10, r1)
            com.app.office.fc.ddf.EscherClientAnchorRecord r1 = (com.app.office.fc.ddf.EscherClientAnchorRecord) r1
            if (r1 == 0) goto L_0x0023
            short r2 = r1.getCol2()
            r3 = 255(0xff, float:3.57E-43)
            if (r2 > r3) goto L_0x0023
            short r2 = r1.getRow2()
            r3 = 65535(0xffff, float:9.1834E-41)
            if (r2 > r3) goto L_0x0023
            com.app.office.fc.hssf.usermodel.HSSFClientAnchor r1 = com.app.office.fc.hssf.usermodel.HSSFShape.toClientAnchor(r1)
            goto L_0x0033
        L_0x0023:
            r1 = r0
            goto L_0x0033
        L_0x0025:
            r1 = -4081(0xfffffffffffff00f, float:NaN)
            com.app.office.fc.ddf.EscherRecord r1 = com.app.office.fc.ShapeKit.getEscherChild(r10, r1)
            com.app.office.fc.ddf.EscherChildAnchorRecord r1 = (com.app.office.fc.ddf.EscherChildAnchorRecord) r1
            if (r1 == 0) goto L_0x0035
            com.app.office.fc.hssf.usermodel.HSSFChildAnchor r1 = com.app.office.fc.hssf.usermodel.HSSFShape.toChildAnchor(r1)
        L_0x0033:
            r6 = r1
            goto L_0x0036
        L_0x0035:
            r6 = r0
        L_0x0036:
            r1 = -4086(0xfffffffffffff00a, float:NaN)
            com.app.office.fc.ddf.EscherRecord r1 = r10.getChildById(r1)
            com.app.office.fc.ddf.EscherSpRecord r1 = (com.app.office.fc.ddf.EscherSpRecord) r1
            if (r1 != 0) goto L_0x0041
            return r0
        L_0x0041:
            short r1 = r1.getOptions()
            int r7 = r1 >> 4
            if (r7 == 0) goto L_0x00f2
            r1 = 20
            if (r7 == r1) goto L_0x00e8
            r1 = 38
            if (r7 == r1) goto L_0x00e8
            r1 = 75
            if (r7 == r1) goto L_0x00c1
            r1 = 100
            if (r7 == r1) goto L_0x00f2
            r1 = 201(0xc9, float:2.82E-43)
            if (r7 == r1) goto L_0x00bb
            r1 = 202(0xca, float:2.83E-43)
            if (r7 == r1) goto L_0x0075
            switch(r7) {
                case 32: goto L_0x00e8;
                case 33: goto L_0x00e8;
                case 34: goto L_0x00e8;
                default: goto L_0x0064;
            }
        L_0x0064:
            com.app.office.fc.hssf.usermodel.HSSFAutoShape r0 = new com.app.office.fc.hssf.usermodel.HSSFAutoShape
            r2 = r0
            r3 = r8
            r4 = r10
            r5 = r11
            r2.<init>(r3, r4, r5, r6, r7)
            r8 = r0
            com.app.office.fc.hssf.usermodel.HSSFAutoShape r8 = (com.app.office.fc.hssf.usermodel.HSSFAutoShape) r8
            r8.setAdjustmentValue(r10)
            goto L_0x00fb
        L_0x0075:
            if (r9 == 0) goto L_0x00c1
            int r1 = r9.size()
            if (r1 <= 0) goto L_0x00c1
            r1 = -4079(0xfffffffffffff011, float:NaN)
            com.app.office.fc.ddf.EscherRecord r1 = com.app.office.fc.ShapeKit.getEscherChild(r10, r1)
            com.app.office.fc.ddf.EscherClientDataRecord r1 = (com.app.office.fc.ddf.EscherClientDataRecord) r1
            java.lang.Object r9 = r9.get(r1)
            com.app.office.fc.hssf.record.Record r9 = (com.app.office.fc.hssf.record.Record) r9
            boolean r1 = r9 instanceof com.app.office.fc.hssf.record.ObjRecord
            if (r1 == 0) goto L_0x00fb
            com.app.office.fc.hssf.record.ObjRecord r9 = (com.app.office.fc.hssf.record.ObjRecord) r9
            java.util.List r1 = r9.getSubRecords()
            r2 = 0
            java.lang.Object r1 = r1.get(r2)
            boolean r1 = r1 instanceof com.app.office.fc.hssf.record.CommonObjectDataSubRecord
            if (r1 == 0) goto L_0x00fb
            java.util.List r9 = r9.getSubRecords()
            java.lang.Object r9 = r9.get(r2)
            com.app.office.fc.hssf.record.CommonObjectDataSubRecord r9 = (com.app.office.fc.hssf.record.CommonObjectDataSubRecord) r9
            short r9 = r9.getObjectType()
            r1 = 25
            if (r9 == r1) goto L_0x00fb
            com.app.office.fc.hssf.usermodel.HSSFAutoShape r9 = new com.app.office.fc.hssf.usermodel.HSSFAutoShape
            r2 = r9
            r3 = r8
            r4 = r10
            r5 = r11
            r2.<init>(r3, r4, r5, r6, r7)
            r0 = r9
            goto L_0x00fb
        L_0x00bb:
            com.app.office.fc.hssf.usermodel.HSSFChart r0 = new com.app.office.fc.hssf.usermodel.HSSFChart
            r0.<init>(r8, r10, r11, r6)
            goto L_0x00fb
        L_0x00c1:
            r9 = -4085(0xfffffffffffff00b, float:NaN)
            com.app.office.fc.ddf.EscherRecord r9 = com.app.office.fc.ShapeKit.getEscherChild(r10, r9)
            r7 = r9
            com.app.office.fc.ddf.EscherOptRecord r7 = (com.app.office.fc.ddf.EscherOptRecord) r7
            r9 = 260(0x104, float:3.64E-43)
            com.app.office.fc.ddf.EscherProperty r9 = r7.lookup(r9)
            com.app.office.fc.ddf.EscherSimpleProperty r9 = (com.app.office.fc.ddf.EscherSimpleProperty) r9
            com.app.office.fc.hssf.usermodel.HSSFPicture r0 = new com.app.office.fc.hssf.usermodel.HSSFPicture
            r2 = r0
            r3 = r8
            r4 = r10
            r5 = r11
            r2.<init>(r3, r4, r5, r6, r7)
            if (r9 == 0) goto L_0x00fb
            r8 = r0
            com.app.office.fc.hssf.usermodel.HSSFPicture r8 = (com.app.office.fc.hssf.usermodel.HSSFPicture) r8
            int r9 = r9.getPropertyValue()
            r8.setPictureIndex(r9)
            goto L_0x00fb
        L_0x00e8:
            com.app.office.fc.hssf.usermodel.HSSFLine r0 = new com.app.office.fc.hssf.usermodel.HSSFLine
            r2 = r0
            r3 = r8
            r4 = r10
            r5 = r11
            r2.<init>(r3, r4, r5, r6, r7)
            goto L_0x00fb
        L_0x00f2:
            com.app.office.fc.hssf.usermodel.HSSFFreeform r0 = new com.app.office.fc.hssf.usermodel.HSSFFreeform
            r2 = r0
            r3 = r8
            r4 = r10
            r5 = r11
            r2.<init>(r3, r4, r5, r6, r7)
        L_0x00fb:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.usermodel.HSSFShapeFactory.createSimpeShape(com.app.office.ss.model.XLSModel.AWorkbook, java.util.Map, com.app.office.fc.ddf.EscherContainerRecord, com.app.office.fc.hssf.usermodel.HSSFShape):com.app.office.fc.hssf.usermodel.HSSFShape");
    }

    private static boolean isEmbeddedObject(ObjRecord objRecord) {
        for (SubRecord subRecord : objRecord.getSubRecords()) {
            if (subRecord instanceof EmbeddedObjectRefSubRecord) {
                return true;
            }
        }
        return false;
    }
}
