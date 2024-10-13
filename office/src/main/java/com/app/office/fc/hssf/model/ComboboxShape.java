package com.app.office.fc.hssf.model;

import com.app.office.fc.ddf.EscherBoolProperty;
import com.app.office.fc.ddf.EscherClientDataRecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.hssf.record.CommonObjectDataSubRecord;
import com.app.office.fc.hssf.record.EndSubRecord;
import com.app.office.fc.hssf.record.FtCblsSubRecord;
import com.app.office.fc.hssf.record.LbsDataSubRecord;
import com.app.office.fc.hssf.record.ObjRecord;
import com.app.office.fc.hssf.usermodel.HSSFClientAnchor;
import com.app.office.fc.hssf.usermodel.HSSFSimpleShape;

public class ComboboxShape extends AbstractShape {
    private ObjRecord objRecord;
    private EscherContainerRecord spContainer;

    ComboboxShape(HSSFSimpleShape hSSFSimpleShape, int i) {
        this.spContainer = createSpContainer(hSSFSimpleShape, i);
        this.objRecord = createObjRecord(hSSFSimpleShape, i);
    }

    private ObjRecord createObjRecord(HSSFSimpleShape hSSFSimpleShape, int i) {
        ObjRecord objRecord2 = new ObjRecord();
        CommonObjectDataSubRecord commonObjectDataSubRecord = new CommonObjectDataSubRecord();
        commonObjectDataSubRecord.setObjectType(20);
        commonObjectDataSubRecord.setObjectId(getCmoObjectId(i));
        commonObjectDataSubRecord.setLocked(true);
        commonObjectDataSubRecord.setPrintable(false);
        commonObjectDataSubRecord.setAutofill(true);
        commonObjectDataSubRecord.setAutoline(false);
        FtCblsSubRecord ftCblsSubRecord = new FtCblsSubRecord();
        LbsDataSubRecord newAutoFilterInstance = LbsDataSubRecord.newAutoFilterInstance();
        EndSubRecord endSubRecord = new EndSubRecord();
        objRecord2.addSubRecord(commonObjectDataSubRecord);
        objRecord2.addSubRecord(ftCblsSubRecord);
        objRecord2.addSubRecord(newAutoFilterInstance);
        objRecord2.addSubRecord(endSubRecord);
        return objRecord2;
    }

    private EscherContainerRecord createSpContainer(HSSFSimpleShape hSSFSimpleShape, int i) {
        EscherContainerRecord escherContainerRecord = new EscherContainerRecord();
        EscherSpRecord escherSpRecord = new EscherSpRecord();
        EscherOptRecord escherOptRecord = new EscherOptRecord();
        EscherClientDataRecord escherClientDataRecord = new EscherClientDataRecord();
        escherContainerRecord.setRecordId(EscherContainerRecord.SP_CONTAINER);
        escherContainerRecord.setOptions(15);
        escherSpRecord.setRecordId(EscherSpRecord.RECORD_ID);
        escherSpRecord.setOptions(3218);
        escherSpRecord.setShapeId(i);
        escherSpRecord.setFlags(2560);
        escherOptRecord.setRecordId(EscherOptRecord.RECORD_ID);
        escherOptRecord.addEscherProperty(new EscherBoolProperty(EscherProperties.PROTECTION__LOCKAGAINSTGROUPING, 17039620));
        escherOptRecord.addEscherProperty(new EscherBoolProperty(EscherProperties.TEXT__SIZE_TEXT_TO_FIT_SHAPE, 524296));
        escherOptRecord.addEscherProperty(new EscherBoolProperty(EscherProperties.LINESTYLE__NOLINEDRAWDASH, 524288));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(959, 131072));
        HSSFClientAnchor hSSFClientAnchor = (HSSFClientAnchor) hSSFSimpleShape.getAnchor();
        hSSFClientAnchor.setAnchorType(1);
        EscherRecord createAnchor = createAnchor(hSSFClientAnchor);
        escherClientDataRecord.setRecordId(EscherClientDataRecord.RECORD_ID);
        escherClientDataRecord.setOptions(0);
        escherContainerRecord.addChildRecord(escherSpRecord);
        escherContainerRecord.addChildRecord(escherOptRecord);
        escherContainerRecord.addChildRecord(createAnchor);
        escherContainerRecord.addChildRecord(escherClientDataRecord);
        return escherContainerRecord;
    }

    public EscherContainerRecord getSpContainer() {
        return this.spContainer;
    }

    public ObjRecord getObjRecord() {
        return this.objRecord;
    }
}
