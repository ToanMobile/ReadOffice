package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.record.common.FeatFormulaErr2;
import com.app.office.fc.hssf.record.common.FeatProtection;
import com.app.office.fc.hssf.record.common.FeatSmartTag;
import com.app.office.fc.hssf.record.common.FtrHeader;
import com.app.office.fc.hssf.record.common.SharedFeature;
import com.app.office.fc.ss.util.HSSFCellRangeAddress;
import com.app.office.fc.util.LittleEndianOutput;
import java.io.PrintStream;

public final class FeatRecord extends StandardRecord {
    public static final short sid = 2152;
    private long cbFeatData;
    private HSSFCellRangeAddress[] cellRefs;
    private FtrHeader futureHeader;
    private int isf_sharedFeatureType;
    private byte reserved1;
    private long reserved2;
    private int reserved3;
    private SharedFeature sharedFeature;

    public short getSid() {
        return sid;
    }

    public FeatRecord() {
        FtrHeader ftrHeader = new FtrHeader();
        this.futureHeader = ftrHeader;
        ftrHeader.setRecordType(sid);
    }

    public FeatRecord(RecordInputStream recordInputStream) {
        this.futureHeader = new FtrHeader(recordInputStream);
        this.isf_sharedFeatureType = recordInputStream.readShort();
        this.reserved1 = recordInputStream.readByte();
        this.reserved2 = (long) recordInputStream.readInt();
        int readUShort = recordInputStream.readUShort();
        this.cbFeatData = (long) recordInputStream.readInt();
        this.reserved3 = recordInputStream.readShort();
        this.cellRefs = new HSSFCellRangeAddress[readUShort];
        int i = 0;
        while (true) {
            HSSFCellRangeAddress[] hSSFCellRangeAddressArr = this.cellRefs;
            if (i >= hSSFCellRangeAddressArr.length) {
                break;
            }
            hSSFCellRangeAddressArr[i] = new HSSFCellRangeAddress(recordInputStream);
            i++;
        }
        int i2 = this.isf_sharedFeatureType;
        if (i2 == 2) {
            this.sharedFeature = new FeatProtection(recordInputStream);
        } else if (i2 == 3) {
            this.sharedFeature = new FeatFormulaErr2(recordInputStream);
        } else if (i2 != 4) {
            PrintStream printStream = System.err;
            printStream.println("Unknown Shared Feature " + this.isf_sharedFeatureType + " found!");
        } else {
            this.sharedFeature = new FeatSmartTag(recordInputStream);
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SHARED FEATURE]\n");
        stringBuffer.append("[/SHARED FEATURE]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        this.futureHeader.serialize(littleEndianOutput);
        littleEndianOutput.writeShort(this.isf_sharedFeatureType);
        littleEndianOutput.writeByte(this.reserved1);
        littleEndianOutput.writeInt((int) this.reserved2);
        littleEndianOutput.writeShort(this.cellRefs.length);
        littleEndianOutput.writeInt((int) this.cbFeatData);
        littleEndianOutput.writeShort(this.reserved3);
        int i = 0;
        while (true) {
            HSSFCellRangeAddress[] hSSFCellRangeAddressArr = this.cellRefs;
            if (i < hSSFCellRangeAddressArr.length) {
                hSSFCellRangeAddressArr[i].serialize(littleEndianOutput);
                i++;
            } else {
                this.sharedFeature.serialize(littleEndianOutput);
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return (this.cellRefs.length * 8) + 27 + this.sharedFeature.getDataSize();
    }

    public int getIsf_sharedFeatureType() {
        return this.isf_sharedFeatureType;
    }

    public long getCbFeatData() {
        return this.cbFeatData;
    }

    public void setCbFeatData(long j) {
        this.cbFeatData = j;
    }

    public HSSFCellRangeAddress[] getCellRefs() {
        return this.cellRefs;
    }

    public void setCellRefs(HSSFCellRangeAddress[] hSSFCellRangeAddressArr) {
        this.cellRefs = hSSFCellRangeAddressArr;
    }

    public SharedFeature getSharedFeature() {
        return this.sharedFeature;
    }

    public void setSharedFeature(SharedFeature sharedFeature2) {
        this.sharedFeature = sharedFeature2;
        if (sharedFeature2 instanceof FeatProtection) {
            this.isf_sharedFeatureType = 2;
        }
        if (sharedFeature2 instanceof FeatFormulaErr2) {
            this.isf_sharedFeatureType = 3;
        }
        if (sharedFeature2 instanceof FeatSmartTag) {
            this.isf_sharedFeatureType = 4;
        }
        if (this.isf_sharedFeatureType == 3) {
            this.cbFeatData = (long) sharedFeature2.getDataSize();
        } else {
            this.cbFeatData = 0;
        }
    }

    public Object clone() {
        return cloneViaReserialise();
    }
}
