package com.app.office.ss.other;

import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Row;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.util.ModelUtil;

public class FindingMgr {
    private Cell findedCell;
    private Sheet sheet;
    private String value;

    public Cell findCell(Sheet sheet2, String str) {
        String formatContents;
        String formatContents2;
        if (!(str == null || sheet2 == null)) {
            this.sheet = sheet2;
            this.value = str;
            if (str != null && str.length() > 0) {
                int activeCellRow = sheet2.getActiveCellRow();
                while (activeCellRow <= sheet2.getLastRowNum()) {
                    Row row = sheet2.getRow(activeCellRow);
                    if (row != null) {
                        for (int activeCellColumn = activeCellRow == sheet2.getActiveCellRow() ? sheet2.getActiveCellColumn() : row.getFirstCol(); activeCellColumn <= row.getLastCol(); activeCellColumn++) {
                            Cell cell = row.getCell(activeCellColumn);
                            this.findedCell = cell;
                            if (cell != null && (formatContents2 = ModelUtil.instance().getFormatContents(sheet2.getWorkbook(), this.findedCell)) != null && formatContents2.contains(str)) {
                                return this.findedCell;
                            }
                        }
                        continue;
                    }
                    activeCellRow++;
                }
                for (int firstRowNum = sheet2.getFirstRowNum(); firstRowNum <= sheet2.getActiveCellRow(); firstRowNum++) {
                    Row row2 = sheet2.getRow(firstRowNum);
                    if (row2 != null) {
                        for (int firstCol = row2.getFirstCol(); firstCol <= row2.getLastCol(); firstCol++) {
                            Cell cell2 = row2.getCell(firstCol);
                            this.findedCell = cell2;
                            if (cell2 != null && (formatContents = ModelUtil.instance().getFormatContents(sheet2.getWorkbook(), this.findedCell)) != null && formatContents.contains(str)) {
                                return this.findedCell;
                            }
                        }
                        continue;
                    }
                }
            }
        }
        return null;
    }

    public Cell findBackward() {
        String formatContents;
        Cell cell = this.findedCell;
        if (!(cell == null || this.value == null || this.sheet == null)) {
            int rowNumber = cell.getRowNumber();
            while (rowNumber >= this.sheet.getFirstRowNum()) {
                Row row = this.sheet.getRow(rowNumber);
                if (row != null) {
                    int colNumber = rowNumber == this.findedCell.getRowNumber() ? this.findedCell.getColNumber() - 1 : row.getLastCol();
                    while (colNumber >= 0) {
                        Cell cell2 = row.getCell(colNumber);
                        if (cell2 == null || (formatContents = ModelUtil.instance().getFormatContents(this.sheet.getWorkbook(), cell2)) == null || !formatContents.contains(this.value)) {
                            colNumber--;
                        } else {
                            this.findedCell = cell2;
                            return cell2;
                        }
                    }
                    continue;
                }
                rowNumber--;
            }
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0061 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.app.office.ss.model.baseModel.Cell findForward() {
        /*
            r7 = this;
            com.app.office.ss.model.baseModel.Cell r0 = r7.findedCell
            r1 = 0
            if (r0 == 0) goto L_0x0064
            java.lang.String r2 = r7.value
            if (r2 == 0) goto L_0x0064
            com.app.office.ss.model.baseModel.Sheet r2 = r7.sheet
            if (r2 != 0) goto L_0x000e
            goto L_0x0064
        L_0x000e:
            int r0 = r0.getRowNumber()
        L_0x0012:
            com.app.office.ss.model.baseModel.Sheet r2 = r7.sheet
            int r2 = r2.getLastRowNum()
            if (r0 > r2) goto L_0x0064
            com.app.office.ss.model.baseModel.Sheet r2 = r7.sheet
            com.app.office.ss.model.baseModel.Row r2 = r2.getRow(r0)
            if (r2 != 0) goto L_0x0023
            goto L_0x0061
        L_0x0023:
            com.app.office.ss.model.baseModel.Cell r3 = r7.findedCell
            int r3 = r3.getRowNumber()
            if (r0 != r3) goto L_0x0032
            com.app.office.ss.model.baseModel.Cell r3 = r7.findedCell
            int r3 = r3.getColNumber()
            goto L_0x005e
        L_0x0032:
            int r3 = r2.getFirstCol()
        L_0x0036:
            int r4 = r2.getLastCol()
            if (r3 > r4) goto L_0x0061
            com.app.office.ss.model.baseModel.Cell r4 = r2.getCell(r3)
            if (r4 != 0) goto L_0x0043
            goto L_0x005e
        L_0x0043:
            com.app.office.ss.util.ModelUtil r5 = com.app.office.ss.util.ModelUtil.instance()
            com.app.office.ss.model.baseModel.Sheet r6 = r7.sheet
            com.app.office.ss.model.baseModel.Workbook r6 = r6.getWorkbook()
            java.lang.String r5 = r5.getFormatContents(r6, r4)
            if (r5 == 0) goto L_0x005e
            java.lang.String r6 = r7.value
            boolean r5 = r5.contains(r6)
            if (r5 == 0) goto L_0x005e
            r7.findedCell = r4
            return r4
        L_0x005e:
            int r3 = r3 + 1
            goto L_0x0036
        L_0x0061:
            int r0 = r0 + 1
            goto L_0x0012
        L_0x0064:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.other.FindingMgr.findForward():com.app.office.ss.model.baseModel.Cell");
    }

    public void dispose() {
        this.sheet = null;
        this.value = null;
        this.findedCell = null;
    }
}
