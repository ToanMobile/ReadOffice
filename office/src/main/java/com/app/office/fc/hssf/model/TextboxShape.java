package com.app.office.fc.hssf.model;

import com.app.office.fc.ddf.EscherClientAnchorRecord;
import com.app.office.fc.ddf.EscherClientDataRecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.ddf.EscherTextboxRecord;
import com.app.office.fc.hssf.record.CommonObjectDataSubRecord;
import com.app.office.fc.hssf.record.EndSubRecord;
import com.app.office.fc.hssf.record.ObjRecord;
import com.app.office.fc.hssf.record.TextObjectRecord;
import com.app.office.fc.hssf.usermodel.HSSFTextbox;

public class TextboxShape extends AbstractShape {
    private EscherTextboxRecord escherTextbox;
    private ObjRecord objRecord;
    private EscherContainerRecord spContainer;
    private TextObjectRecord textObjectRecord;

    TextboxShape(HSSFTextbox hSSFTextbox, int i) {
        this.spContainer = createSpContainer(hSSFTextbox, i);
        this.objRecord = createObjRecord(hSSFTextbox, i);
        this.textObjectRecord = createTextObjectRecord(hSSFTextbox, i);
    }

    private ObjRecord createObjRecord(HSSFTextbox hSSFTextbox, int i) {
        ObjRecord objRecord2 = new ObjRecord();
        CommonObjectDataSubRecord commonObjectDataSubRecord = new CommonObjectDataSubRecord();
        commonObjectDataSubRecord.setObjectType((short) hSSFTextbox.getShapeType());
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

    private EscherContainerRecord createSpContainer(HSSFTextbox hSSFTextbox, int i) {
        EscherContainerRecord escherContainerRecord = new EscherContainerRecord();
        EscherSpRecord escherSpRecord = new EscherSpRecord();
        EscherOptRecord escherOptRecord = new EscherOptRecord();
        new EscherClientAnchorRecord();
        EscherClientDataRecord escherClientDataRecord = new EscherClientDataRecord();
        this.escherTextbox = new EscherTextboxRecord();
        escherContainerRecord.setRecordId(EscherContainerRecord.SP_CONTAINER);
        escherContainerRecord.setOptions(15);
        escherSpRecord.setRecordId(EscherSpRecord.RECORD_ID);
        escherSpRecord.setOptions(3234);
        escherSpRecord.setShapeId(i);
        escherSpRecord.setFlags(2560);
        escherOptRecord.setRecordId(EscherOptRecord.RECORD_ID);
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(128, 0));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(129, hSSFTextbox.getMarginLeft()));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(131, hSSFTextbox.getMarginRight()));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(132, hSSFTextbox.getMarginBottom()));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(130, hSSFTextbox.getMarginTop()));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(133, 0));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.TEXT__ANCHORTEXT, 0));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(959, 524288));
        addStandardOptions(hSSFTextbox, escherOptRecord);
        EscherRecord createAnchor = createAnchor(hSSFTextbox.getAnchor());
        escherClientDataRecord.setRecordId(EscherClientDataRecord.RECORD_ID);
        escherClientDataRecord.setOptions(0);
        this.escherTextbox.setRecordId(EscherTextboxRecord.RECORD_ID);
        this.escherTextbox.setOptions(0);
        escherContainerRecord.addChildRecord(escherSpRecord);
        escherContainerRecord.addChildRecord(escherOptRecord);
        escherContainerRecord.addChildRecord(createAnchor);
        escherContainerRecord.addChildRecord(escherClientDataRecord);
        escherContainerRecord.addChildRecord(this.escherTextbox);
        return escherContainerRecord;
    }

    private TextObjectRecord createTextObjectRecord(HSSFTextbox hSSFTextbox, int i) {
        TextObjectRecord textObjectRecord2 = new TextObjectRecord();
        textObjectRecord2.setHorizontalTextAlignment(hSSFTextbox.getHorizontalAlignment());
        textObjectRecord2.setVerticalTextAlignment(hSSFTextbox.getVerticalAlignment());
        textObjectRecord2.setTextLocked(true);
        textObjectRecord2.setTextOrientation(0);
        textObjectRecord2.setStr(hSSFTextbox.getString());
        return textObjectRecord2;
    }

    public EscherContainerRecord getSpContainer() {
        return this.spContainer;
    }

    public ObjRecord getObjRecord() {
        return this.objRecord;
    }

    public TextObjectRecord getTextObjectRecord() {
        return this.textObjectRecord;
    }

    public EscherRecord getEscherTextbox() {
        return this.escherTextbox;
    }
}
