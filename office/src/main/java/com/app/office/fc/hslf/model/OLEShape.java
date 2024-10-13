package com.app.office.fc.hslf.model;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.hslf.record.ExEmbed;
import com.app.office.fc.hslf.record.ExObjList;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.hslf.usermodel.ObjectData;

public final class OLEShape extends Picture {
    protected ExEmbed _exEmbed;

    public OLEShape(int i) {
        super(i);
    }

    public OLEShape(int i, Shape shape) {
        super(i, shape);
    }

    protected OLEShape(EscherContainerRecord escherContainerRecord, Shape shape) {
        super(escherContainerRecord, shape);
    }

    public int getObjectID() {
        return ShapeKit.getEscherProperty(getSpContainer(), (short) EscherProperties.BLIP__PICTUREID);
    }

    public ObjectData getObjectData() {
        ObjectData[] embeddedObjects = getSheet().getSlideShow().getEmbeddedObjects();
        int objStgDataRef = getExEmbed().getExOleObjAtom().getObjStgDataRef();
        ObjectData objectData = null;
        for (int i = 0; i < embeddedObjects.length; i++) {
            if (embeddedObjects[i].getExOleObjStg().getPersistId() == objStgDataRef) {
                objectData = embeddedObjects[i];
            }
        }
        return objectData;
    }

    public ExEmbed getExEmbed() {
        if (this._exEmbed == null) {
            ExObjList exObjList = getSheet().getSlideShow().getDocumentRecord().getExObjList();
            if (exObjList == null) {
                return null;
            }
            int objectID = getObjectID();
            Record[] childRecords = exObjList.getChildRecords();
            for (int i = 0; i < childRecords.length; i++) {
                if (childRecords[i] instanceof ExEmbed) {
                    ExEmbed exEmbed = (ExEmbed) childRecords[i];
                    if (exEmbed.getExOleObjAtom().getObjID() == objectID) {
                        this._exEmbed = exEmbed;
                    }
                }
            }
        }
        return this._exEmbed;
    }

    public String getInstanceName() {
        return getExEmbed().getMenuName();
    }

    public String getFullName() {
        return getExEmbed().getClipboardName();
    }

    public String getProgID() {
        return getExEmbed().getProgId();
    }

    public void dispose() {
        super.dispose();
        ExEmbed exEmbed = this._exEmbed;
        if (exEmbed != null) {
            exEmbed.dispose();
            this._exEmbed = null;
        }
    }
}
