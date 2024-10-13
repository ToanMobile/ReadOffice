package com.app.office.fc.hslf.model;

import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.AbstractEscherOptRecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherPropertyFactory;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.hslf.record.InteractiveInfo;
import com.app.office.fc.hslf.record.OEShapeAtom;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.hslf.record.RecordTypes;
import java.util.Iterator;

public final class ShapeFactory {
    public static Shape createShape(EscherContainerRecord escherContainerRecord, Shape shape) {
        if (escherContainerRecord.getRecordId() == -4093) {
            return createShapeGroup(escherContainerRecord, shape);
        }
        return createSimpeShape(escherContainerRecord, shape);
    }

    public static ShapeGroup createShapeGroup(EscherContainerRecord escherContainerRecord, Shape shape) {
        EscherRecord escherChild = ShapeKit.getEscherChild((EscherContainerRecord) escherContainerRecord.getChild(0), -3806);
        if (escherChild == null) {
            return new ShapeGroup(escherContainerRecord, shape);
        }
        try {
            EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) new EscherPropertyFactory().createProperties(escherChild.serialize(), 8, escherChild.getInstance()).get(0);
            if (escherSimpleProperty.getPropertyNumber() == 927 && escherSimpleProperty.getPropertyValue() == 1) {
                return new Table(escherContainerRecord, shape);
            }
            return new ShapeGroup(escherContainerRecord, shape);
        } catch (Exception unused) {
            return new ShapeGroup(escherContainerRecord, shape);
        }
    }

    public static Shape createSimpeShape(EscherContainerRecord escherContainerRecord, Shape shape) {
        EscherSpRecord escherSpRecord = (EscherSpRecord) escherContainerRecord.getChildById(EscherSpRecord.RECORD_ID);
        int options = escherSpRecord.getOptions() >> 4;
        Shape shape2 = null;
        if (options != 0) {
            if (!(options == 20 || options == 38)) {
                if (options != 75) {
                    if (options != 100) {
                        if (options != 201) {
                            if (options != 202) {
                                switch (options) {
                                    case 32:
                                    case 33:
                                    case 34:
                                        break;
                                    default:
                                        shape2 = new AutoShape(escherContainerRecord, shape);
                                        break;
                                }
                            } else {
                                shape2 = new TextBox(escherContainerRecord, shape);
                            }
                            shape2.setShapeId(escherSpRecord.getShapeId());
                            return shape2;
                        }
                    }
                }
                InteractiveInfo interactiveInfo = (InteractiveInfo) getClientDataRecord(escherContainerRecord, RecordTypes.InteractiveInfo.typeID);
                OEShapeAtom oEShapeAtom = (OEShapeAtom) getClientDataRecord(escherContainerRecord, RecordTypes.OEShapeAtom.typeID);
                if ((interactiveInfo == null || interactiveInfo.getInteractiveInfoAtom() == null) && oEShapeAtom != null) {
                    shape2 = new OLEShape(escherContainerRecord, shape);
                }
                if (shape2 == null) {
                    shape2 = new Picture(escherContainerRecord, shape);
                }
                shape2.setShapeId(escherSpRecord.getShapeId());
                return shape2;
            }
            shape2 = new Line(escherContainerRecord, shape);
            shape2.setShapeId(escherSpRecord.getShapeId());
            return shape2;
        }
        EscherOptRecord escherOptRecord = (EscherOptRecord) ShapeKit.getEscherChild(escherContainerRecord, -4085);
        if (!(escherOptRecord == null || ShapeKit.getEscherProperty((AbstractEscherOptRecord) escherOptRecord, (int) TIFFConstants.TIFFTAG_TILEBYTECOUNTS) == null)) {
            shape2 = new Freeform(escherContainerRecord, shape);
        }
        shape2.setShapeId(escherSpRecord.getShapeId());
        return shape2;
    }

    protected static Record getClientDataRecord(EscherContainerRecord escherContainerRecord, int i) {
        Iterator<EscherRecord> childIterator = escherContainerRecord.getChildIterator();
        while (childIterator.hasNext()) {
            EscherRecord next = childIterator.next();
            if (next.getRecordId() == -4079) {
                byte[] serialize = next.serialize();
                Record[] findChildRecords = Record.findChildRecords(serialize, 8, serialize.length - 8);
                for (int i2 = 0; i2 < findChildRecords.length; i2++) {
                    if (findChildRecords[i2].getRecordType() == ((long) i)) {
                        return findChildRecords[i2];
                    }
                }
                continue;
            }
        }
        return null;
    }
}
