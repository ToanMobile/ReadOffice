package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherPropertyFactory;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherSpRecord;

public final class HWPFShapeFactory {
    public static HWPFShape createShape(EscherContainerRecord escherContainerRecord, HWPFShape hWPFShape) {
        if (escherContainerRecord.getRecordId() == -4093) {
            return createShapeGroup(escherContainerRecord, hWPFShape);
        }
        return createSimpeShape(escherContainerRecord, hWPFShape);
    }

    public static HWPFShapeGroup createShapeGroup(EscherContainerRecord escherContainerRecord, HWPFShape hWPFShape) {
        HWPFShapeGroup hWPFShapeGroup;
        EscherRecord escherChild = ShapeKit.getEscherChild((EscherContainerRecord) escherContainerRecord.getChild(0), -3806);
        if (escherChild != null) {
            try {
                EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) new EscherPropertyFactory().createProperties(escherChild.serialize(), 8, escherChild.getInstance()).get(0);
                if (escherSimpleProperty.getPropertyNumber() == 927 && escherSimpleProperty.getPropertyValue() == 1) {
                    return null;
                }
                hWPFShapeGroup = new HWPFShapeGroup(escherContainerRecord, hWPFShape);
            } catch (Exception unused) {
                hWPFShapeGroup = new HWPFShapeGroup(escherContainerRecord, hWPFShape);
            }
        } else {
            hWPFShapeGroup = new HWPFShapeGroup(escherContainerRecord, hWPFShape);
        }
        return hWPFShapeGroup;
    }

    public static HWPFAutoShape createSimpeShape(EscherContainerRecord escherContainerRecord, HWPFShape hWPFShape) {
        if (((EscherSpRecord) escherContainerRecord.getChildById(EscherSpRecord.RECORD_ID)) != null) {
            return new HWPFAutoShape(escherContainerRecord, hWPFShape);
        }
        return null;
    }
}
