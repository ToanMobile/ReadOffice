package com.app.office.fc.hssf.record.aggregates;

import com.app.office.fc.hssf.model.RecordStream;
import com.app.office.fc.hssf.record.MergeCellsRecord;
import com.app.office.fc.hssf.record.aggregates.RecordAggregate;
import com.app.office.fc.ss.util.CellRangeAddressList;
import com.app.office.fc.ss.util.HSSFCellRangeAddress;
import java.util.ArrayList;
import java.util.List;

public final class MergedCellsTable extends RecordAggregate {
    private static int MAX_MERGED_REGIONS = 1027;
    private final List _mergedRegions = new ArrayList();

    public void read(RecordStream recordStream) {
        List list = this._mergedRegions;
        while (recordStream.peekNextClass() == MergeCellsRecord.class) {
            MergeCellsRecord mergeCellsRecord = (MergeCellsRecord) recordStream.getNext();
            short numAreas = mergeCellsRecord.getNumAreas();
            for (int i = 0; i < numAreas; i++) {
                list.add(mergeCellsRecord.getAreaAt(i));
            }
        }
    }

    public int getRecordSize() {
        int size = this._mergedRegions.size();
        if (size < 1) {
            return 0;
        }
        int i = MAX_MERGED_REGIONS;
        return ((size / i) * (CellRangeAddressList.getEncodedSize(i) + 4)) + 4 + CellRangeAddressList.getEncodedSize(size % i);
    }

    public void visitContainedRecords(RecordAggregate.RecordVisitor recordVisitor) {
        int size = this._mergedRegions.size();
        if (size >= 1) {
            int i = MAX_MERGED_REGIONS;
            int i2 = size / i;
            int i3 = size % i;
            HSSFCellRangeAddress[] hSSFCellRangeAddressArr = new HSSFCellRangeAddress[size];
            this._mergedRegions.toArray(hSSFCellRangeAddressArr);
            for (int i4 = 0; i4 < i2; i4++) {
                int i5 = MAX_MERGED_REGIONS;
                recordVisitor.visitRecord(new MergeCellsRecord(hSSFCellRangeAddressArr, i4 * i5, i5));
            }
            if (i3 > 0) {
                recordVisitor.visitRecord(new MergeCellsRecord(hSSFCellRangeAddressArr, i2 * MAX_MERGED_REGIONS, i3));
            }
        }
    }

    public void addRecords(MergeCellsRecord[] mergeCellsRecordArr) {
        for (MergeCellsRecord addMergeCellsRecord : mergeCellsRecordArr) {
            addMergeCellsRecord(addMergeCellsRecord);
        }
    }

    private void addMergeCellsRecord(MergeCellsRecord mergeCellsRecord) {
        short numAreas = mergeCellsRecord.getNumAreas();
        for (int i = 0; i < numAreas; i++) {
            this._mergedRegions.add(mergeCellsRecord.getAreaAt(i));
        }
    }

    public HSSFCellRangeAddress get(int i) {
        checkIndex(i);
        return (HSSFCellRangeAddress) this._mergedRegions.get(i);
    }

    public void remove(int i) {
        checkIndex(i);
        this._mergedRegions.remove(i);
    }

    private void checkIndex(int i) {
        if (i < 0 || i >= this._mergedRegions.size()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Specified CF index ");
            sb.append(i);
            sb.append(" is outside the allowable range (0..");
            sb.append(this._mergedRegions.size() - 1);
            sb.append(")");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public void addArea(int i, int i2, int i3, int i4) {
        this._mergedRegions.add(new HSSFCellRangeAddress(i, i3, i2, i4));
    }

    public int getNumberOfMergedRegions() {
        return this._mergedRegions.size();
    }
}
