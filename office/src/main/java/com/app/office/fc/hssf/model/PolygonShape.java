package com.app.office.fc.hssf.model;

import com.app.office.fc.ddf.EscherArrayProperty;
import com.app.office.fc.ddf.EscherClientDataRecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherShapePathProperty;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.hssf.record.CommonObjectDataSubRecord;
import com.app.office.fc.hssf.record.EndSubRecord;
import com.app.office.fc.hssf.record.ObjRecord;
import com.app.office.fc.hssf.usermodel.HSSFPolygon;
import com.app.office.fc.hssf.usermodel.HSSFShape;
import com.app.office.fc.util.LittleEndian;
import kotlin.jvm.internal.ByteCompanionObject;

public class PolygonShape extends AbstractShape {
    public static final short OBJECT_TYPE_MICROSOFT_OFFICE_DRAWING = 30;
    private ObjRecord objRecord;
    private EscherContainerRecord spContainer;

    PolygonShape(HSSFPolygon hSSFPolygon, int i) {
        this.spContainer = createSpContainer(hSSFPolygon, i);
        this.objRecord = createObjRecord(hSSFPolygon, i);
    }

    private EscherContainerRecord createSpContainer(HSSFPolygon hSSFPolygon, int i) {
        EscherContainerRecord escherContainerRecord = new EscherContainerRecord();
        EscherSpRecord escherSpRecord = new EscherSpRecord();
        EscherOptRecord escherOptRecord = new EscherOptRecord();
        EscherClientDataRecord escherClientDataRecord = new EscherClientDataRecord();
        escherContainerRecord.setRecordId(EscherContainerRecord.SP_CONTAINER);
        escherContainerRecord.setOptions(15);
        escherSpRecord.setRecordId(EscherSpRecord.RECORD_ID);
        escherSpRecord.setOptions(370);
        escherSpRecord.setShapeId(i);
        if (hSSFPolygon.getParent() == null) {
            escherSpRecord.setFlags(2560);
        } else {
            escherSpRecord.setFlags(2562);
        }
        escherOptRecord.setRecordId(EscherOptRecord.RECORD_ID);
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(4, false, false, 0));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__RIGHT, false, false, hSSFPolygon.getDrawAreaWidth()));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__BOTTOM, false, false, hSSFPolygon.getDrawAreaHeight()));
        escherOptRecord.addEscherProperty(new EscherShapePathProperty(EscherProperties.GEOMETRY__SHAPEPATH, 4));
        EscherArrayProperty escherArrayProperty = new EscherArrayProperty(EscherProperties.GEOMETRY__VERTICES, false, new byte[0]);
        escherArrayProperty.setNumberOfElementsInArray(hSSFPolygon.getXPoints().length + 1);
        escherArrayProperty.setNumberOfElementsInMemory(hSSFPolygon.getXPoints().length + 1);
        escherArrayProperty.setSizeOfElements(65520);
        for (int i2 = 0; i2 < hSSFPolygon.getXPoints().length; i2++) {
            byte[] bArr = new byte[4];
            LittleEndian.putShort(bArr, 0, (short) hSSFPolygon.getXPoints()[i2]);
            LittleEndian.putShort(bArr, 2, (short) hSSFPolygon.getYPoints()[i2]);
            escherArrayProperty.setElement(i2, bArr);
        }
        int length = hSSFPolygon.getXPoints().length;
        byte[] bArr2 = new byte[4];
        LittleEndian.putShort(bArr2, 0, (short) hSSFPolygon.getXPoints()[0]);
        LittleEndian.putShort(bArr2, 2, (short) hSSFPolygon.getYPoints()[0]);
        escherArrayProperty.setElement(length, bArr2);
        escherOptRecord.addEscherProperty(escherArrayProperty);
        EscherArrayProperty escherArrayProperty2 = new EscherArrayProperty(EscherProperties.GEOMETRY__SEGMENTINFO, false, (byte[]) null);
        escherArrayProperty2.setSizeOfElements(2);
        escherArrayProperty2.setNumberOfElementsInArray((hSSFPolygon.getXPoints().length * 2) + 4);
        escherArrayProperty2.setNumberOfElementsInMemory((hSSFPolygon.getXPoints().length * 2) + 4);
        escherArrayProperty2.setElement(0, new byte[]{0, 64});
        escherArrayProperty2.setElement(1, new byte[]{0, -84});
        for (int i3 = 0; i3 < hSSFPolygon.getXPoints().length; i3++) {
            int i4 = i3 * 2;
            escherArrayProperty2.setElement(i4 + 2, new byte[]{1, 0});
            escherArrayProperty2.setElement(i4 + 3, new byte[]{0, -84});
        }
        escherArrayProperty2.setElement(escherArrayProperty2.getNumberOfElementsInArray() - 2, new byte[]{1, 96});
        escherArrayProperty2.setElement(escherArrayProperty2.getNumberOfElementsInArray() - 1, new byte[]{0, ByteCompanionObject.MIN_VALUE});
        escherOptRecord.addEscherProperty(escherArrayProperty2);
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__FILLOK, false, false, 65537));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.LINESTYLE__LINESTARTARROWHEAD, false, false, 0));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.LINESTYLE__LINEENDARROWHEAD, false, false, 0));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.LINESTYLE__LINEENDCAPSTYLE, false, false, 0));
        addStandardOptions(hSSFPolygon, escherOptRecord);
        EscherRecord createAnchor = createAnchor(hSSFPolygon.getAnchor());
        escherClientDataRecord.setRecordId(EscherClientDataRecord.RECORD_ID);
        escherClientDataRecord.setOptions(0);
        escherContainerRecord.addChildRecord(escherSpRecord);
        escherContainerRecord.addChildRecord(escherOptRecord);
        escherContainerRecord.addChildRecord(createAnchor);
        escherContainerRecord.addChildRecord(escherClientDataRecord);
        return escherContainerRecord;
    }

    private ObjRecord createObjRecord(HSSFShape hSSFShape, int i) {
        ObjRecord objRecord2 = new ObjRecord();
        CommonObjectDataSubRecord commonObjectDataSubRecord = new CommonObjectDataSubRecord();
        commonObjectDataSubRecord.setObjectType(30);
        commonObjectDataSubRecord.setObjectId(getCmoObjectId(i));
        commonObjectDataSubRecord.setLocked(true);
        commonObjectDataSubRecord.setPrintable(true);
        commonObjectDataSubRecord.setAutofill(true);
        commonObjectDataSubRecord.setAutoline(true);
        EndSubRecord endSubRecord = new EndSubRecord();
        objRecord2.addSubRecord(commonObjectDataSubRecord);
        objRecord2.addSubRecord(endSubRecord);
        return objRecord2;
    }

    public EscherContainerRecord getSpContainer() {
        return this.spContainer;
    }

    public ObjRecord getObjRecord() {
        return this.objRecord;
    }
}
