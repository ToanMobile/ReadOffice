package com.app.office.fc.ddf;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.RecordFormatException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public final class EscherDggRecord extends EscherRecord {
    private static final Comparator<FileIdCluster> MY_COMP = new Comparator<FileIdCluster>() {
        public int compare(FileIdCluster fileIdCluster, FileIdCluster fileIdCluster2) {
            if (fileIdCluster.getDrawingGroupId() == fileIdCluster2.getDrawingGroupId()) {
                return 0;
            }
            return fileIdCluster.getDrawingGroupId() < fileIdCluster2.getDrawingGroupId() ? -1 : 1;
        }
    };
    public static final String RECORD_DESCRIPTION = "MsofbtDgg";
    public static final short RECORD_ID = -4090;
    private int field_1_shapeIdMax;
    private int field_3_numShapesSaved;
    private int field_4_drawingsSaved;
    private FileIdCluster[] field_5_fileIdClusters;
    private int maxDgId;

    public void dispose() {
    }

    public short getRecordId() {
        return RECORD_ID;
    }

    public String getRecordName() {
        return "Dgg";
    }

    public static class FileIdCluster {
        /* access modifiers changed from: private */
        public int field_1_drawingGroupId;
        /* access modifiers changed from: private */
        public int field_2_numShapeIdsUsed;

        public FileIdCluster(int i, int i2) {
            this.field_1_drawingGroupId = i;
            this.field_2_numShapeIdsUsed = i2;
        }

        public int getDrawingGroupId() {
            return this.field_1_drawingGroupId;
        }

        public int getNumShapeIdsUsed() {
            return this.field_2_numShapeIdsUsed;
        }

        public void incrementShapeId() {
            this.field_2_numShapeIdsUsed++;
        }
    }

    public int fillFields(byte[] bArr, int i, EscherRecordFactory escherRecordFactory) {
        int readHeader = readHeader(bArr, i);
        int i2 = i + 8;
        this.field_1_shapeIdMax = LittleEndian.getInt(bArr, i2 + 0);
        LittleEndian.getInt(bArr, i2 + 4);
        this.field_3_numShapesSaved = LittleEndian.getInt(bArr, i2 + 8);
        this.field_4_drawingsSaved = LittleEndian.getInt(bArr, i2 + 12);
        this.field_5_fileIdClusters = new FileIdCluster[((readHeader - 16) / 8)];
        int i3 = 0;
        int i4 = 16;
        while (true) {
            FileIdCluster[] fileIdClusterArr = this.field_5_fileIdClusters;
            if (i3 >= fileIdClusterArr.length) {
                break;
            }
            int i5 = i2 + i4;
            fileIdClusterArr[i3] = new FileIdCluster(LittleEndian.getInt(bArr, i5), LittleEndian.getInt(bArr, i5 + 4));
            this.maxDgId = Math.max(this.maxDgId, this.field_5_fileIdClusters[i3].getDrawingGroupId());
            i4 += 8;
            i3++;
        }
        int i6 = readHeader - i4;
        if (i6 == 0) {
            return i4 + 8 + i6;
        }
        throw new RecordFormatException("Expecting no remaining data but got " + i6 + " byte(s).");
    }

    public int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener) {
        escherSerializationListener.beforeRecordSerialize(i, getRecordId(), this);
        LittleEndian.putShort(bArr, i, getOptions());
        int i2 = i + 2;
        LittleEndian.putShort(bArr, i2, getRecordId());
        int i3 = i2 + 2;
        LittleEndian.putInt(bArr, i3, getRecordSize() - 8);
        int i4 = i3 + 4;
        LittleEndian.putInt(bArr, i4, this.field_1_shapeIdMax);
        int i5 = i4 + 4;
        LittleEndian.putInt(bArr, i5, getNumIdClusters());
        int i6 = i5 + 4;
        LittleEndian.putInt(bArr, i6, this.field_3_numShapesSaved);
        int i7 = i6 + 4;
        LittleEndian.putInt(bArr, i7, this.field_4_drawingsSaved);
        int i8 = i7 + 4;
        int i9 = 0;
        while (true) {
            FileIdCluster[] fileIdClusterArr = this.field_5_fileIdClusters;
            if (i9 < fileIdClusterArr.length) {
                LittleEndian.putInt(bArr, i8, fileIdClusterArr[i9].field_1_drawingGroupId);
                int i10 = i8 + 4;
                LittleEndian.putInt(bArr, i10, this.field_5_fileIdClusters[i9].field_2_numShapeIdsUsed);
                i8 = i10 + 4;
                i9++;
            } else {
                escherSerializationListener.afterRecordSerialize(i8, getRecordId(), getRecordSize(), this);
                return getRecordSize();
            }
        }
    }

    public int getRecordSize() {
        return (this.field_5_fileIdClusters.length * 8) + 24;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.field_5_fileIdClusters != null) {
            int i = 0;
            while (i < this.field_5_fileIdClusters.length) {
                stringBuffer.append("  DrawingGroupId");
                int i2 = i + 1;
                stringBuffer.append(i2);
                stringBuffer.append(": ");
                stringBuffer.append(this.field_5_fileIdClusters[i].field_1_drawingGroupId);
                stringBuffer.append(10);
                stringBuffer.append("  NumShapeIdsUsed");
                stringBuffer.append(i2);
                stringBuffer.append(": ");
                stringBuffer.append(this.field_5_fileIdClusters[i].field_2_numShapeIdsUsed);
                stringBuffer.append(10);
                i = i2;
            }
        }
        return getClass().getName() + ":" + 10 + "  RecordId: 0x" + HexDump.toHex((short) RECORD_ID) + 10 + "  Options: 0x" + HexDump.toHex(getOptions()) + 10 + "  ShapeIdMax: " + this.field_1_shapeIdMax + 10 + "  NumIdClusters: " + getNumIdClusters() + 10 + "  NumShapesSaved: " + this.field_3_numShapesSaved + 10 + "  DrawingsSaved: " + this.field_4_drawingsSaved + 10 + "" + stringBuffer.toString();
    }

    public int getShapeIdMax() {
        return this.field_1_shapeIdMax;
    }

    public void setShapeIdMax(int i) {
        this.field_1_shapeIdMax = i;
    }

    public int getNumIdClusters() {
        FileIdCluster[] fileIdClusterArr = this.field_5_fileIdClusters;
        if (fileIdClusterArr == null) {
            return 0;
        }
        return fileIdClusterArr.length + 1;
    }

    public int getNumShapesSaved() {
        return this.field_3_numShapesSaved;
    }

    public void setNumShapesSaved(int i) {
        this.field_3_numShapesSaved = i;
    }

    public int getDrawingsSaved() {
        return this.field_4_drawingsSaved;
    }

    public void setDrawingsSaved(int i) {
        this.field_4_drawingsSaved = i;
    }

    public int getMaxDrawingGroupId() {
        return this.maxDgId;
    }

    public void setMaxDrawingGroupId(int i) {
        this.maxDgId = i;
    }

    public FileIdCluster[] getFileIdClusters() {
        return this.field_5_fileIdClusters;
    }

    public void setFileIdClusters(FileIdCluster[] fileIdClusterArr) {
        this.field_5_fileIdClusters = fileIdClusterArr;
    }

    public void addCluster(int i, int i2) {
        addCluster(i, i2, true);
    }

    public void addCluster(int i, int i2, boolean z) {
        ArrayList arrayList = new ArrayList(Arrays.asList(this.field_5_fileIdClusters));
        arrayList.add(new FileIdCluster(i, i2));
        if (z) {
            Collections.sort(arrayList, MY_COMP);
        }
        this.maxDgId = Math.min(this.maxDgId, i);
        this.field_5_fileIdClusters = (FileIdCluster[]) arrayList.toArray(new FileIdCluster[arrayList.size()]);
    }
}
