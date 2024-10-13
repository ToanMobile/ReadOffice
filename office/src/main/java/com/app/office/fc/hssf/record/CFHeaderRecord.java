package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.record.cf.CellRangeUtil;
import com.app.office.fc.ss.util.CellRangeAddressList;
import com.app.office.fc.ss.util.HSSFCellRangeAddress;
import com.app.office.fc.util.LittleEndianOutput;

public final class CFHeaderRecord extends StandardRecord {
    public static final short sid = 432;
    private int field_1_numcf;
    private int field_2_need_recalculation;
    private HSSFCellRangeAddress field_3_enclosing_cell_range;
    private CellRangeAddressList field_4_cell_ranges;

    public short getSid() {
        return sid;
    }

    public CFHeaderRecord() {
        this.field_4_cell_ranges = new CellRangeAddressList();
    }

    public CFHeaderRecord(HSSFCellRangeAddress[] hSSFCellRangeAddressArr, int i) {
        setCellRanges(CellRangeUtil.mergeCellRanges(hSSFCellRangeAddressArr));
        this.field_1_numcf = i;
    }

    public CFHeaderRecord(RecordInputStream recordInputStream) {
        this.field_1_numcf = recordInputStream.readShort();
        this.field_2_need_recalculation = recordInputStream.readShort();
        this.field_3_enclosing_cell_range = new HSSFCellRangeAddress(recordInputStream);
        this.field_4_cell_ranges = new CellRangeAddressList(recordInputStream);
    }

    public int getNumberOfConditionalFormats() {
        return this.field_1_numcf;
    }

    public void setNumberOfConditionalFormats(int i) {
        this.field_1_numcf = i;
    }

    public boolean getNeedRecalculation() {
        return this.field_2_need_recalculation == 1;
    }

    public void setNeedRecalculation(boolean z) {
        this.field_2_need_recalculation = z ? 1 : 0;
    }

    public HSSFCellRangeAddress getEnclosingCellRange() {
        return this.field_3_enclosing_cell_range;
    }

    public void setEnclosingCellRange(HSSFCellRangeAddress hSSFCellRangeAddress) {
        this.field_3_enclosing_cell_range = hSSFCellRangeAddress;
    }

    public void setCellRanges(HSSFCellRangeAddress[] hSSFCellRangeAddressArr) {
        if (hSSFCellRangeAddressArr != null) {
            CellRangeAddressList cellRangeAddressList = new CellRangeAddressList();
            HSSFCellRangeAddress hSSFCellRangeAddress = null;
            for (HSSFCellRangeAddress hSSFCellRangeAddress2 : hSSFCellRangeAddressArr) {
                hSSFCellRangeAddress = CellRangeUtil.createEnclosingCellRange(hSSFCellRangeAddress2, hSSFCellRangeAddress);
                cellRangeAddressList.addCellRangeAddress(hSSFCellRangeAddress2);
            }
            this.field_3_enclosing_cell_range = hSSFCellRangeAddress;
            this.field_4_cell_ranges = cellRangeAddressList;
            return;
        }
        throw new IllegalArgumentException("cellRanges must not be null");
    }

    public HSSFCellRangeAddress[] getCellRanges() {
        return this.field_4_cell_ranges.getCellRangeAddresses();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[CFHEADER]\n");
        stringBuffer.append("\t.id\t\t= ");
        stringBuffer.append(Integer.toHexString(432));
        stringBuffer.append("\n");
        stringBuffer.append("\t.numCF\t\t\t= ");
        stringBuffer.append(getNumberOfConditionalFormats());
        stringBuffer.append("\n");
        stringBuffer.append("\t.needRecalc\t   = ");
        stringBuffer.append(getNeedRecalculation());
        stringBuffer.append("\n");
        stringBuffer.append("\t.enclosingCellRange= ");
        stringBuffer.append(getEnclosingCellRange());
        stringBuffer.append("\n");
        stringBuffer.append("\t.cfranges=[");
        int i = 0;
        while (i < this.field_4_cell_ranges.countRanges()) {
            stringBuffer.append(i == 0 ? "" : ",");
            stringBuffer.append(this.field_4_cell_ranges.getCellRangeAddress(i).toString());
            i++;
        }
        stringBuffer.append("]\n");
        stringBuffer.append("[/CFHEADER]\n");
        return stringBuffer.toString();
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return this.field_4_cell_ranges.getSize() + 12;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_numcf);
        littleEndianOutput.writeShort(this.field_2_need_recalculation);
        this.field_3_enclosing_cell_range.serialize(littleEndianOutput);
        this.field_4_cell_ranges.serialize(littleEndianOutput);
    }

    public Object clone() {
        CFHeaderRecord cFHeaderRecord = new CFHeaderRecord();
        cFHeaderRecord.field_1_numcf = this.field_1_numcf;
        cFHeaderRecord.field_2_need_recalculation = this.field_2_need_recalculation;
        cFHeaderRecord.field_3_enclosing_cell_range = this.field_3_enclosing_cell_range;
        cFHeaderRecord.field_4_cell_ranges = this.field_4_cell_ranges.copy();
        return cFHeaderRecord;
    }
}
