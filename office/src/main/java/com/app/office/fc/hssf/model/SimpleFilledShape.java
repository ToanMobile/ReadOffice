package com.app.office.fc.hssf.model;

import com.app.office.fc.ddf.EscherClientDataRecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.hssf.record.CommonObjectDataSubRecord;
import com.app.office.fc.hssf.record.EndSubRecord;
import com.app.office.fc.hssf.record.ObjRecord;
import com.app.office.fc.hssf.usermodel.HSSFShape;
import com.app.office.fc.hssf.usermodel.HSSFSimpleShape;

public class SimpleFilledShape extends AbstractShape {
    private ObjRecord objRecord;
    private EscherContainerRecord spContainer;

    SimpleFilledShape(HSSFSimpleShape hSSFSimpleShape, int i) {
        this.spContainer = createSpContainer(hSSFSimpleShape, i);
        this.objRecord = createObjRecord(hSSFSimpleShape, i);
    }

    private EscherContainerRecord createSpContainer(HSSFSimpleShape hSSFSimpleShape, int i) {
        EscherContainerRecord escherContainerRecord = new EscherContainerRecord();
        EscherSpRecord escherSpRecord = new EscherSpRecord();
        EscherOptRecord escherOptRecord = new EscherOptRecord();
        EscherClientDataRecord escherClientDataRecord = new EscherClientDataRecord();
        escherContainerRecord.setRecordId(EscherContainerRecord.SP_CONTAINER);
        escherContainerRecord.setOptions(15);
        escherSpRecord.setRecordId(EscherSpRecord.RECORD_ID);
        escherSpRecord.setOptions((short) ((objTypeToShapeType(hSSFSimpleShape.getShapeType()) << 4) | 2));
        escherSpRecord.setShapeId(i);
        escherSpRecord.setFlags(2560);
        escherOptRecord.setRecordId(EscherOptRecord.RECORD_ID);
        addStandardOptions(hSSFSimpleShape, escherOptRecord);
        EscherRecord createAnchor = createAnchor(hSSFSimpleShape.getAnchor());
        escherClientDataRecord.setRecordId(EscherClientDataRecord.RECORD_ID);
        escherClientDataRecord.setOptions(0);
        escherContainerRecord.addChildRecord(escherSpRecord);
        escherContainerRecord.addChildRecord(escherOptRecord);
        escherContainerRecord.addChildRecord(createAnchor);
        escherContainerRecord.addChildRecord(escherClientDataRecord);
        return escherContainerRecord;
    }

    private short objTypeToShapeType(int i) {
        if (i == 3) {
            return 3;
        }
        if (i == 2) {
            return 1;
        }
        throw new IllegalArgumentException("Unable to handle an object of this type");
    }

    private ObjRecord createObjRecord(HSSFShape hSSFShape, int i) {
        ObjRecord objRecord2 = new ObjRecord();
        CommonObjectDataSubRecord commonObjectDataSubRecord = new CommonObjectDataSubRecord();
        commonObjectDataSubRecord.setObjectType((short) ((HSSFSimpleShape) hSSFShape).getShapeType());
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
