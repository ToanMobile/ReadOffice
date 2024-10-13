package com.app.office.fc.hssf.model;

import com.app.office.fc.ddf.EscherDgRecord;
import com.app.office.fc.ddf.EscherDggRecord;
import java.util.ArrayList;
import java.util.List;

public class DrawingManager2 {
    EscherDggRecord dgg;
    List drawingGroups = new ArrayList();

    public DrawingManager2(EscherDggRecord escherDggRecord) {
        this.dgg = escherDggRecord;
    }

    public void clearDrawingGroups() {
        this.drawingGroups.clear();
    }

    public EscherDgRecord createDgRecord() {
        EscherDgRecord escherDgRecord = new EscherDgRecord();
        escherDgRecord.setRecordId(EscherDgRecord.RECORD_ID);
        short findNewDrawingGroupId = findNewDrawingGroupId();
        escherDgRecord.setOptions((short) (findNewDrawingGroupId << 4));
        escherDgRecord.setNumShapes(0);
        escherDgRecord.setLastMSOSPID(-1);
        this.drawingGroups.add(escherDgRecord);
        this.dgg.addCluster(findNewDrawingGroupId, 0);
        EscherDggRecord escherDggRecord = this.dgg;
        escherDggRecord.setDrawingsSaved(escherDggRecord.getDrawingsSaved() + 1);
        return escherDgRecord;
    }

    public int allocateShapeId(short s) {
        return allocateShapeId(s, getDrawingGroup(s));
    }

    public int allocateShapeId(short s, EscherDgRecord escherDgRecord) {
        EscherDggRecord escherDggRecord = this.dgg;
        escherDggRecord.setNumShapesSaved(escherDggRecord.getNumShapesSaved() + 1);
        int i = 0;
        while (i < this.dgg.getFileIdClusters().length) {
            EscherDggRecord.FileIdCluster fileIdCluster = this.dgg.getFileIdClusters()[i];
            if (fileIdCluster.getDrawingGroupId() != s || fileIdCluster.getNumShapeIdsUsed() == 1024) {
                i++;
            } else {
                int numShapeIdsUsed = fileIdCluster.getNumShapeIdsUsed() + ((i + 1) * 1024);
                fileIdCluster.incrementShapeId();
                escherDgRecord.setNumShapes(escherDgRecord.getNumShapes() + 1);
                escherDgRecord.setLastMSOSPID(numShapeIdsUsed);
                if (numShapeIdsUsed >= this.dgg.getShapeIdMax()) {
                    this.dgg.setShapeIdMax(numShapeIdsUsed + 1);
                }
                return numShapeIdsUsed;
            }
        }
        this.dgg.addCluster(s, 0);
        this.dgg.getFileIdClusters()[this.dgg.getFileIdClusters().length - 1].incrementShapeId();
        escherDgRecord.setNumShapes(escherDgRecord.getNumShapes() + 1);
        int length = this.dgg.getFileIdClusters().length * 1024;
        escherDgRecord.setLastMSOSPID(length);
        if (length >= this.dgg.getShapeIdMax()) {
            this.dgg.setShapeIdMax(length + 1);
        }
        return length;
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
    public EscherDgRecord getDrawingGroup(int i) {
        return (EscherDgRecord) this.drawingGroups.get(i - 1);
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
