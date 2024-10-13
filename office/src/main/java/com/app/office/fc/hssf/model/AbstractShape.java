package com.app.office.fc.hssf.model;

import com.app.office.fc.ddf.EscherBoolProperty;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherRGBProperty;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.hssf.record.ObjRecord;
import com.app.office.fc.hssf.usermodel.HSSFAnchor;
import com.app.office.fc.hssf.usermodel.HSSFComment;
import com.app.office.fc.hssf.usermodel.HSSFPolygon;
import com.app.office.fc.hssf.usermodel.HSSFShape;
import com.app.office.fc.hssf.usermodel.HSSFSimpleShape;
import com.app.office.fc.hssf.usermodel.HSSFTextbox;

public abstract class AbstractShape {
    /* access modifiers changed from: package-private */
    public int getCmoObjectId(int i) {
        return i - 1024;
    }

    public abstract ObjRecord getObjRecord();

    public abstract EscherContainerRecord getSpContainer();

    public static AbstractShape createShape(HSSFShape hSSFShape, int i) {
        AbstractShape abstractShape;
        AbstractShape abstractShape2;
        if (hSSFShape instanceof HSSFComment) {
            abstractShape = new CommentShape((HSSFComment) hSSFShape, i);
        } else if (hSSFShape instanceof HSSFTextbox) {
            abstractShape = new TextboxShape((HSSFTextbox) hSSFShape, i);
        } else if (hSSFShape instanceof HSSFPolygon) {
            abstractShape = new PolygonShape((HSSFPolygon) hSSFShape, i);
        } else if (hSSFShape instanceof HSSFSimpleShape) {
            HSSFSimpleShape hSSFSimpleShape = (HSSFSimpleShape) hSSFShape;
            int shapeType = hSSFSimpleShape.getShapeType();
            if (shapeType == 1) {
                abstractShape2 = new LineShape(hSSFSimpleShape, i);
            } else if (shapeType == 2 || shapeType == 3) {
                abstractShape2 = new SimpleFilledShape(hSSFSimpleShape, i);
            } else if (shapeType == 8) {
                abstractShape2 = new PictureShape(hSSFSimpleShape, i);
            } else if (shapeType == 20) {
                abstractShape2 = new ComboboxShape(hSSFSimpleShape, i);
            } else {
                throw new IllegalArgumentException("Do not know how to handle this type of shape");
            }
            abstractShape = abstractShape2;
        } else {
            throw new IllegalArgumentException("Unknown shape type");
        }
        EscherSpRecord escherSpRecord = (EscherSpRecord) abstractShape.getSpContainer().getChildById(EscherSpRecord.RECORD_ID);
        if (hSSFShape.getParent() != null) {
            escherSpRecord.setFlags(escherSpRecord.getFlags() | 2);
        }
        return abstractShape;
    }

    protected AbstractShape() {
    }

    /* access modifiers changed from: protected */
    public EscherRecord createAnchor(HSSFAnchor hSSFAnchor) {
        return ConvertAnchor.createAnchor(hSSFAnchor);
    }

    /* access modifiers changed from: protected */
    public int addStandardOptions(HSSFShape hSSFShape, EscherOptRecord escherOptRecord) {
        escherOptRecord.addEscherProperty(new EscherBoolProperty(EscherProperties.TEXT__SIZE_TEXT_TO_FIT_SHAPE, 524288));
        if (hSSFShape.isNoFill()) {
            escherOptRecord.addEscherProperty(new EscherBoolProperty(EscherProperties.FILL__NOFILLHITTEST, 1114112));
        } else {
            escherOptRecord.addEscherProperty(new EscherBoolProperty(EscherProperties.FILL__NOFILLHITTEST, 65536));
        }
        escherOptRecord.addEscherProperty(new EscherRGBProperty(EscherProperties.FILL__FILLCOLOR, hSSFShape.getFillColor()));
        escherOptRecord.addEscherProperty(new EscherBoolProperty(959, 524288));
        escherOptRecord.addEscherProperty(new EscherRGBProperty(EscherProperties.LINESTYLE__COLOR, hSSFShape.getLineStyleColor()));
        int i = 5;
        if (hSSFShape.getLineWidth() != 9525) {
            escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.LINESTYLE__LINEWIDTH, hSSFShape.getLineWidth()));
            i = 6;
        }
        if (hSSFShape.getLineStyle() != 0) {
            escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.LINESTYLE__LINEDASHING, hSSFShape.getLineStyle()));
            escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.LINESTYLE__LINEENDCAPSTYLE, 0));
            if (hSSFShape.getLineStyle() == -1) {
                escherOptRecord.addEscherProperty(new EscherBoolProperty(EscherProperties.LINESTYLE__NOLINEDRAWDASH, 524288));
            } else {
                escherOptRecord.addEscherProperty(new EscherBoolProperty(EscherProperties.LINESTYLE__NOLINEDRAWDASH, 524296));
            }
            i += 3;
        }
        escherOptRecord.sortProperties();
        return i;
    }
}
