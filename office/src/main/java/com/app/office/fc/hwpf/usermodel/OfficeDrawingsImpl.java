package com.app.office.fc.hwpf.usermodel;

import com.itextpdf.text.pdf.codec.wmf.MetaDo;
import com.app.office.common.pictureefftect.PictureEffectInfo;
import com.app.office.common.pictureefftect.PictureEffectInfoFactory;
import com.app.office.fc.ddf.DefaultEscherRecordFactory;
import com.app.office.fc.ddf.EscherBSERecord;
import com.app.office.fc.ddf.EscherBlipRecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherMetafileBlip;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.ddf.EscherTertiaryOptRecord;
import com.app.office.fc.hwpf.model.EscherRecordHolder;
import com.app.office.fc.hwpf.model.FSPA;
import com.app.office.fc.hwpf.model.FSPATable;
import com.app.office.system.IControl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class OfficeDrawingsImpl implements OfficeDrawings {
    private final EscherRecordHolder _escherRecordHolder;
    private final FSPATable _fspaTable;
    private final byte[] _mainStream;

    public OfficeDrawingsImpl(FSPATable fSPATable, EscherRecordHolder escherRecordHolder, byte[] bArr) {
        this._fspaTable = fSPATable;
        this._escherRecordHolder = escherRecordHolder;
        this._mainStream = bArr;
    }

    public EscherBlipRecord getBitmapRecord(IControl iControl, int i) {
        List<? extends EscherContainerRecord> bStoreContainers = this._escherRecordHolder.getBStoreContainers();
        if (bStoreContainers != null && bStoreContainers.size() == 1) {
            List<EscherRecord> childRecords = ((EscherContainerRecord) bStoreContainers.get(0)).getChildRecords();
            if (childRecords.size() < i) {
                return null;
            }
            EscherRecord escherRecord = childRecords.get(i - 1);
            if (escherRecord instanceof EscherBlipRecord) {
                return (EscherBlipRecord) escherRecord;
            }
            if (escherRecord instanceof EscherBSERecord) {
                EscherBSERecord escherBSERecord = (EscherBSERecord) escherRecord;
                EscherBlipRecord blipRecord = escherBSERecord.getBlipRecord();
                if (blipRecord != null) {
                    return blipRecord;
                }
                if (escherBSERecord.getOffset() > 0) {
                    DefaultEscherRecordFactory defaultEscherRecordFactory = new DefaultEscherRecordFactory();
                    EscherRecord createRecord = defaultEscherRecordFactory.createRecord(this._mainStream, escherBSERecord.getOffset());
                    if (createRecord instanceof EscherBlipRecord) {
                        EscherBlipRecord escherBlipRecord = (EscherBlipRecord) createRecord;
                        if (escherBlipRecord instanceof EscherMetafileBlip) {
                            escherBlipRecord.fillFields(this._mainStream, escherBSERecord.getOffset(), defaultEscherRecordFactory);
                            escherBlipRecord.setTempFilePath(iControl.getSysKit().getPictureManage().writeTempFile(escherBlipRecord.getPicturedata()));
                        } else {
                            int readHeader = escherBlipRecord.readHeader(this._mainStream, escherBSERecord.getOffset());
                            int min = Math.min(64, readHeader);
                            byte[] bArr = new byte[min];
                            int offset = escherBSERecord.getOffset() + 8 + 17;
                            System.arraycopy(this._mainStream, offset, bArr, 0, min);
                            escherBlipRecord.setPictureData(bArr);
                            escherBlipRecord.setTempFilePath(iControl.getSysKit().getPictureManage().writeTempFile(this._mainStream, offset, readHeader - 17));
                        }
                        return escherBlipRecord;
                    }
                }
            }
        }
        return null;
    }

    private boolean findEscherShapeRecordContainer(EscherContainerRecord escherContainerRecord, int i) {
        if (escherContainerRecord.getRecordId() == -4093) {
            Iterator<EscherRecord> it = escherContainerRecord.getChildRecords().iterator();
            if (it.hasNext()) {
                return findEscherShapeRecordContainer((EscherContainerRecord) it.next(), i);
            }
            return false;
        }
        EscherSpRecord escherSpRecord = (EscherSpRecord) escherContainerRecord.getChildById(EscherSpRecord.RECORD_ID);
        return escherSpRecord != null && escherSpRecord.getShapeId() == i;
    }

    /* access modifiers changed from: private */
    public EscherContainerRecord getEscherShapeRecordContainer(int i) {
        for (EscherContainerRecord escherContainerRecord : this._escherRecordHolder.getSpContainers()) {
            if (escherContainerRecord.getRecordId() != -4093) {
                EscherSpRecord escherSpRecord = (EscherSpRecord) escherContainerRecord.getChildById(EscherSpRecord.RECORD_ID);
                if (escherSpRecord != null && escherSpRecord.getShapeId() == i) {
                    return escherContainerRecord;
                }
            } else if (findEscherShapeRecordContainer(escherContainerRecord, i)) {
                return escherContainerRecord;
            }
        }
        return null;
    }

    private OfficeDrawing getOfficeDrawing(FSPA fspa) {
        return new OfficeDrawingImpl(fspa, this);
    }

    public OfficeDrawing getOfficeDrawingAt(int i) {
        FSPA fspaFromCp = this._fspaTable.getFspaFromCp(i);
        if (fspaFromCp == null) {
            return null;
        }
        return getOfficeDrawing(fspaFromCp);
    }

    public Collection<OfficeDrawing> getOfficeDrawings() {
        ArrayList arrayList = new ArrayList();
        for (FSPA officeDrawing : this._fspaTable.getShapes()) {
            arrayList.add(getOfficeDrawing(officeDrawing));
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static class OfficeDrawingImpl implements OfficeDrawing {
        private EscherBlipRecord blipRecord;
        private OfficeDrawingsImpl darwings;
        private FSPA fspa;

        public OfficeDrawingImpl(FSPA fspa2, OfficeDrawingsImpl officeDrawingsImpl) {
            this.fspa = fspa2;
            this.darwings = officeDrawingsImpl;
        }

        public byte getHorizontalPositioning() {
            return (byte) getTertiaryPropertyValue(911, 0);
        }

        public byte getHorizontalRelative() {
            return (byte) getTertiaryPropertyValue(912, 2);
        }

        public byte[] getPictureData(IControl iControl) {
            EscherOptRecord escherOptRecord;
            EscherSimpleProperty escherSimpleProperty;
            EscherBlipRecord escherBlipRecord = this.blipRecord;
            if (escherBlipRecord != null) {
                return escherBlipRecord.getPicturedata();
            }
            EscherContainerRecord access$000 = this.darwings.getEscherShapeRecordContainer(getShapeId());
            if (access$000 == null || (escherOptRecord = (EscherOptRecord) access$000.getChildById(EscherOptRecord.RECORD_ID)) == null || (escherSimpleProperty = (EscherSimpleProperty) escherOptRecord.lookup(MetaDo.META_SETROP2)) == null) {
                return null;
            }
            EscherBlipRecord bitmapRecord = this.darwings.getBitmapRecord(iControl, escherSimpleProperty.getPropertyValue());
            this.blipRecord = bitmapRecord;
            if (bitmapRecord == null) {
                return null;
            }
            return bitmapRecord.getPicturedata();
        }

        public byte[] getPictureData(IControl iControl, int i) {
            if (i <= 0) {
                return null;
            }
            EscherBlipRecord bitmapRecord = this.darwings.getBitmapRecord(iControl, i);
            this.blipRecord = bitmapRecord;
            if (bitmapRecord != null) {
                return bitmapRecord.getPicturedata();
            }
            return null;
        }

        public HWPFShape getAutoShape() {
            EscherContainerRecord access$000 = this.darwings.getEscherShapeRecordContainer(getShapeId());
            if (access$000 != null) {
                return HWPFShapeFactory.createShape(access$000, (HWPFShape) null);
            }
            return null;
        }

        public int getRectangleBottom() {
            return this.fspa.getYaBottom();
        }

        public int getRectangleLeft() {
            return this.fspa.getXaLeft();
        }

        public int getRectangleRight() {
            return this.fspa.getXaRight();
        }

        public int getRectangleTop() {
            return this.fspa.getYaTop();
        }

        public int getShapeId() {
            return this.fspa.getSpid();
        }

        public int getWrap() {
            return this.fspa.getWr();
        }

        public boolean isBelowText() {
            return this.fspa.isFBelowText();
        }

        public boolean isAnchorLock() {
            return this.fspa.isFAnchorLock();
        }

        public PictureEffectInfo getPictureEffectInfor() {
            EscherContainerRecord access$000 = this.darwings.getEscherShapeRecordContainer(getShapeId());
            if (access$000 == null) {
                return null;
            }
            return PictureEffectInfoFactory.getPictureEffectInfor((EscherOptRecord) access$000.getChildById(EscherOptRecord.RECORD_ID));
        }

        private int getTertiaryPropertyValue(int i, int i2) {
            EscherTertiaryOptRecord escherTertiaryOptRecord;
            EscherSimpleProperty escherSimpleProperty;
            EscherContainerRecord access$000 = this.darwings.getEscherShapeRecordContainer(getShapeId());
            if (access$000 == null || (escherTertiaryOptRecord = (EscherTertiaryOptRecord) access$000.getChildById(EscherTertiaryOptRecord.RECORD_ID)) == null || (escherSimpleProperty = (EscherSimpleProperty) escherTertiaryOptRecord.lookup(i)) == null) {
                return i2;
            }
            return escherSimpleProperty.getPropertyValue();
        }

        public byte getVerticalPositioning() {
            return (byte) getTertiaryPropertyValue(913, 0);
        }

        public byte getVerticalRelativeElement() {
            return (byte) getTertiaryPropertyValue(914, 2);
        }

        public String getTempFilePath(IControl iControl) {
            if (this.blipRecord == null) {
                getPictureData(iControl);
            }
            EscherBlipRecord escherBlipRecord = this.blipRecord;
            if (escherBlipRecord != null) {
                return escherBlipRecord.getTempFilePath();
            }
            return null;
        }

        public String toString() {
            return "OfficeDrawingImpl: " + this.fspa.toString();
        }
    }
}
