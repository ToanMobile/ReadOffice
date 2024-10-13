package com.app.office.fc.hslf.model;

import com.itextpdf.text.pdf.codec.wmf.MetaDo;
import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.AbstractEscherOptRecord;
import com.app.office.fc.ddf.EscherBSERecord;
import com.app.office.fc.ddf.EscherComplexProperty;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.hslf.exceptions.HSLFException;
import com.app.office.fc.hslf.usermodel.PictureData;
import com.app.office.java.awt.Rectangle;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Picture extends SimpleShape {
    public static final byte DIB = 7;
    public static final int EMF = 2;
    public static final int JPEG = 5;
    public static final int PICT = 4;
    public static final int PNG = 6;
    public static final int WMF = 3;

    public void setDefaultSize() {
    }

    public Picture(int i) {
        this(i, (Shape) null);
    }

    public Picture(int i, Shape shape) {
        super((EscherContainerRecord) null, shape);
        this._escherContainer = createSpContainer(i, shape instanceof ShapeGroup);
    }

    protected Picture(EscherContainerRecord escherContainerRecord, Shape shape) {
        super(escherContainerRecord, shape);
    }

    public int getPictureIndex() {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) ShapeKit.getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085), (int) MetaDo.META_SETROP2);
        if (escherSimpleProperty == null) {
            return 0;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    /* access modifiers changed from: protected */
    public EscherContainerRecord createSpContainer(int i, boolean z) {
        this._escherContainer = super.createSpContainer(z);
        this._escherContainer.setOptions(15);
        ((EscherSpRecord) this._escherContainer.getChildById(EscherSpRecord.RECORD_ID)).setOptions(1202);
        EscherOptRecord escherOptRecord = (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085);
        setEscherProperty(escherOptRecord, EscherProperties.PROTECTION__LOCKAGAINSTGROUPING, 8388736);
        setEscherProperty(escherOptRecord, 16644, i);
        return this._escherContainer;
    }

    public PictureData getPictureData() {
        PictureData[] pictureData = getSheet().getSlideShow().getPictureData();
        EscherBSERecord escherBSERecord = getEscherBSERecord();
        if (escherBSERecord == null) {
            return null;
        }
        for (int i = 0; i < pictureData.length; i++) {
            if (pictureData[i].getOffset() == escherBSERecord.getOffset()) {
                return pictureData[i];
            }
        }
        return null;
    }

    public EscherOptRecord getEscherOptRecord() {
        return (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085);
    }

    /* access modifiers changed from: protected */
    public EscherBSERecord getEscherBSERecord() {
        EscherContainerRecord escherContainerRecord = (EscherContainerRecord) ShapeKit.getEscherChild(getSheet().getSlideShow().getDocumentRecord().getPPDrawingGroup().getDggContainer(), -4095);
        if (escherContainerRecord == null) {
            return null;
        }
        List<EscherRecord> childRecords = escherContainerRecord.getChildRecords();
        int pictureIndex = getPictureIndex();
        if (pictureIndex == 0) {
            return null;
        }
        return (EscherBSERecord) childRecords.get(pictureIndex - 1);
    }

    public String getPictureName() {
        EscherComplexProperty escherComplexProperty = (EscherComplexProperty) ShapeKit.getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085), (int) MetaDo.META_SETRELABS);
        if (escherComplexProperty == null) {
            return null;
        }
        try {
            String str = new String(escherComplexProperty.getComplexData(), "UTF-16LE");
            int indexOf = str.indexOf(0);
            return indexOf == -1 ? str : str.substring(0, indexOf);
        } catch (UnsupportedEncodingException e) {
            throw new HSLFException((Throwable) e);
        }
    }

    public void setPictureName(String str) {
        EscherOptRecord escherOptRecord = (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085);
        try {
            escherOptRecord.addEscherProperty(new EscherComplexProperty(EscherProperties.BLIP__BLIPFILENAME, false, (str + 0).getBytes("UTF-16LE")));
        } catch (UnsupportedEncodingException e) {
            throw new HSLFException((Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    public void afterInsert(Sheet sheet) {
        super.afterInsert(sheet);
        EscherBSERecord escherBSERecord = getEscherBSERecord();
        escherBSERecord.setRef(escherBSERecord.getRef() + 1);
        if (getAnchor().equals(new Rectangle())) {
            setDefaultSize();
        }
    }
}
