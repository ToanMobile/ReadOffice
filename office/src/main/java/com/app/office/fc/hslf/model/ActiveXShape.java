package com.app.office.fc.hslf.model;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherClientDataRecord;
import com.app.office.fc.ddf.EscherComplexProperty;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.hslf.exceptions.HSLFException;
import com.app.office.fc.hslf.record.ExControl;
import com.app.office.fc.hslf.record.ExObjList;
import com.app.office.fc.hslf.record.OEShapeAtom;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.hslf.record.RecordTypes;
import com.app.office.fc.util.LittleEndian;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

public final class ActiveXShape extends Picture {
    public static final int DEFAULT_ACTIVEX_THUMBNAIL = -1;

    public void setProperty(String str, String str2) {
    }

    public ActiveXShape(int i, int i2) {
        super(i2, (Shape) null);
        setActiveXIndex(i);
    }

    protected ActiveXShape(EscherContainerRecord escherContainerRecord, Shape shape) {
        super(escherContainerRecord, shape);
    }

    /* access modifiers changed from: protected */
    public EscherContainerRecord createSpContainer(int i, boolean z) {
        this._escherContainer = super.createSpContainer(i, z);
        return this._escherContainer;
    }

    public void setActiveXIndex(int i) {
        Iterator<EscherRecord> childIterator = getSpContainer().getChildIterator();
        while (childIterator.hasNext()) {
            EscherRecord next = childIterator.next();
            if (next.getRecordId() == -4079) {
                LittleEndian.putInt(((EscherClientDataRecord) next).getRemainingData(), 8, i);
            }
        }
    }

    public int getControlIndex() {
        OEShapeAtom oEShapeAtom = (OEShapeAtom) getClientDataRecord(RecordTypes.OEShapeAtom.typeID);
        if (oEShapeAtom != null) {
            return oEShapeAtom.getOptions();
        }
        return -1;
    }

    public ExControl getExControl() {
        int controlIndex = getControlIndex();
        ExObjList exObjList = (ExObjList) getSheet().getSlideShow().getDocumentRecord().findFirstOfType((long) RecordTypes.ExObjList.typeID);
        if (exObjList != null) {
            Record[] childRecords = exObjList.getChildRecords();
            for (int i = 0; i < childRecords.length; i++) {
                if (childRecords[i] instanceof ExControl) {
                    ExControl exControl = (ExControl) childRecords[i];
                    if (exControl.getExOleObjAtom().getObjID() == controlIndex) {
                        return exControl;
                    }
                }
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void afterInsert(Sheet sheet) {
        ExControl exControl = getExControl();
        exControl.getExControlAtom().setSlideId(sheet._getSheetNumber());
        try {
            ((EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085)).addEscherProperty(new EscherComplexProperty(EscherProperties.GROUPSHAPE__SHAPENAME, false, ((exControl.getProgId() + "-" + getControlIndex()) + 0).getBytes("UTF-16LE")));
        } catch (UnsupportedEncodingException e) {
            throw new HSLFException((Throwable) e);
        }
    }
}
