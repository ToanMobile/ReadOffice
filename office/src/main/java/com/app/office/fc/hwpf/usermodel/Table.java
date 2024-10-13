package com.app.office.fc.hwpf.usermodel;

import java.util.ArrayList;

public final class Table extends Range {
    private ArrayList<TableRow> _rows;
    private boolean _rowsFound = false;
    private int _tableLevel;

    public int type() {
        return 5;
    }

    Table(int i, int i2, Range range, int i3) {
        super(i, i2, range);
        this._tableLevel = i3;
        initRows();
    }

    public TableRow getRow(int i) {
        initRows();
        return this._rows.get(i);
    }

    public int getTableLevel() {
        return this._tableLevel;
    }

    private void initRows() {
        if (!this._rowsFound) {
            this._rows = new ArrayList<>();
            int numParagraphs = numParagraphs();
            int i = 0;
            int i2 = 0;
            while (i < numParagraphs) {
                Paragraph paragraph = getParagraph(i2);
                Paragraph paragraph2 = getParagraph(i);
                i++;
                if (paragraph2.isTableRowEnd() && paragraph2.getTableLevel() == this._tableLevel) {
                    this._rows.add(new TableRow(paragraph.getStartOffset(), paragraph2.getEndOffset(), this, this._tableLevel));
                    i2 = i;
                }
            }
            this._rowsFound = true;
        }
    }

    public int numRows() {
        initRows();
        return this._rows.size();
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this._rowsFound = false;
    }
}
