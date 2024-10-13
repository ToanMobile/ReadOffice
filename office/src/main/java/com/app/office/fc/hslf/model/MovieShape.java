package com.app.office.fc.hslf.model;

import com.app.office.constant.EventConstant;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.hslf.record.AnimationInfo;
import com.app.office.fc.hslf.record.AnimationInfoAtom;
import com.app.office.fc.hslf.record.ExMCIMovie;
import com.app.office.fc.hslf.record.ExObjList;
import com.app.office.fc.hslf.record.ExVideoContainer;
import com.app.office.fc.hslf.record.OEShapeAtom;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.hslf.record.RecordTypes;

public final class MovieShape extends Picture {
    public static final int DEFAULT_MOVIE_THUMBNAIL = -1;
    public static final int MOVIE_AVI = 2;
    public static final int MOVIE_MPEG = 1;

    public MovieShape(int i, int i2) {
        super(i2, (Shape) null);
        setMovieIndex(i);
        setAutoPlay(true);
    }

    public MovieShape(int i, int i2, Shape shape) {
        super(i2, shape);
        setMovieIndex(i);
    }

    protected MovieShape(EscherContainerRecord escherContainerRecord, Shape shape) {
        super(escherContainerRecord, shape);
    }

    /* access modifiers changed from: protected */
    public EscherContainerRecord createSpContainer(int i, boolean z) {
        this._escherContainer = super.createSpContainer(i, z);
        return this._escherContainer;
    }

    public void setMovieIndex(int i) {
        ((OEShapeAtom) getClientDataRecord(RecordTypes.OEShapeAtom.typeID)).setOptions(i);
        AnimationInfo animationInfo = (AnimationInfo) getClientDataRecord(RecordTypes.AnimationInfo.typeID);
        if (animationInfo != null) {
            AnimationInfoAtom animationInfoAtom = animationInfo.getAnimationInfoAtom();
            animationInfoAtom.setDimColor(EventConstant.TXT_DIALOG_FINISH_ID);
            animationInfoAtom.setFlag(4, true);
            animationInfoAtom.setFlag(256, true);
            animationInfoAtom.setFlag(1024, true);
            animationInfoAtom.setOrderID(i + 1);
        }
    }

    public void setAutoPlay(boolean z) {
        AnimationInfo animationInfo = (AnimationInfo) getClientDataRecord(RecordTypes.AnimationInfo.typeID);
        if (animationInfo != null) {
            animationInfo.getAnimationInfoAtom().setFlag(4, z);
            updateClientData();
        }
    }

    public boolean isAutoPlay() {
        AnimationInfo animationInfo = (AnimationInfo) getClientDataRecord(RecordTypes.AnimationInfo.typeID);
        if (animationInfo != null) {
            return animationInfo.getAnimationInfoAtom().getFlag(4);
        }
        return false;
    }

    public String getPath() {
        int options = ((OEShapeAtom) getClientDataRecord(RecordTypes.OEShapeAtom.typeID)).getOptions();
        ExObjList exObjList = (ExObjList) getSheet().getSlideShow().getDocumentRecord().findFirstOfType((long) RecordTypes.ExObjList.typeID);
        if (exObjList == null) {
            return null;
        }
        Record[] childRecords = exObjList.getChildRecords();
        for (int i = 0; i < childRecords.length; i++) {
            if (childRecords[i] instanceof ExMCIMovie) {
                ExVideoContainer exVideo = ((ExMCIMovie) childRecords[i]).getExVideo();
                if (exVideo.getExMediaAtom().getObjectId() == options) {
                    return exVideo.getPathAtom().getText();
                }
            }
        }
        return null;
    }
}
