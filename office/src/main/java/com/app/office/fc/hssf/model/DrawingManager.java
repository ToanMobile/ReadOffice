package com.app.office.fc.hssf.model;

import com.app.office.fc.ddf.EscherDgRecord;
import com.app.office.fc.ddf.EscherDggRecord;
import java.util.HashMap;
import java.util.Map;

public class DrawingManager {
    Map dgMap = new HashMap();
    EscherDggRecord dgg;

    public DrawingManager(EscherDggRecord escherDggRecord) {
        this.dgg = escherDggRecord;
    }

    public EscherDgRecord createDgRecord() {
        EscherDgRecord escherDgRecord = new EscherDgRecord();
        escherDgRecord.setRecordId(EscherDgRecord.RECORD_ID);
        short findNewDrawingGroupId = findNewDrawingGroupId();
        escherDgRecord.setOptions((short) (findNewDrawingGroupId << 4));
        escherDgRecord.setNumShapes(0);
        escherDgRecord.setLastMSOSPID(-1);
        this.dgg.addCluster(findNewDrawingGroupId, 0);
        EscherDggRecord escherDggRecord = this.dgg;
        escherDggRecord.setDrawingsSaved(escherDggRecord.getDrawingsSaved() + 1);
        this.dgMap.put(Short.valueOf(findNewDrawingGroupId), escherDgRecord);
        return escherDgRecord;
    }

    public int allocateShapeId(short s) {
        int i;
        EscherDgRecord escherDgRecord = (EscherDgRecord) this.dgMap.get(Short.valueOf(s));
        if (escherDgRecord.getLastMSOSPID() % 1024 == 1023) {
            i = findFreeSPIDBlock();
            this.dgg.addCluster(s, 1);
        } else {
            int i2 = 0;
            for (EscherDggRecord.FileIdCluster fileIdCluster : this.dgg.getFileIdClusters()) {
                if (fileIdCluster.getDrawingGroupId() == s && fileIdCluster.getNumShapeIdsUsed() != 1024) {
                    fileIdCluster.incrementShapeId();
                }
                if (escherDgRecord.getLastMSOSPID() == -1) {
                    i2 = findFreeSPIDBlock();
                } else {
                    i2 = escherDgRecord.getLastMSOSPID() + 1;
                }
            }
            i = i2;
        }
        EscherDggRecord escherDggRecord = this.dgg;
        escherDggRecord.setNumShapesSaved(escherDggRecord.getNumShapesSaved() + 1);
        if (i >= this.dgg.getShapeIdMax()) {
            this.dgg.setShapeIdMax(i + 1);
        }
        escherDgRecord.setLastMSOSPID(i);
        escherDgRecord.incrementShapeCount();
        return i;
    }

    /* access modifiers changed from: package-private */
    public short findNewDrawingGroupId() {
        short s = 1;
        while (drawingGroupExists(s)) {
            s = (short) (s + 1);
        }
        return s;
    }

    /* access modifiers changed from: package-private */
    public boolean drawingGroupExists(short s) {
        for (EscherDggRecord.FileIdCluster drawingGroupId : this.dgg.getFileIdClusters()) {
            if (drawingGroupId.getDrawingGroupId() == s) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public int findFreeSPIDBlock() {
        return ((this.dgg.getShapeIdMax() / 1024) + 1) * 1024;
    }

    public EscherDggRecord getDgg() {
        return this.dgg;
    }
}
