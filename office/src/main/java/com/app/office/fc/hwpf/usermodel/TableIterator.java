package com.app.office.fc.hwpf.usermodel;

public final class TableIterator {
    int _index;
    int _levelNum;
    Range _range;

    TableIterator(Range range, int i) {
        this._range = range;
        this._index = 0;
        this._levelNum = i;
    }

    public TableIterator(Range range) {
        this(range, 1);
    }

    public boolean hasNext() {
        int numParagraphs = this._range.numParagraphs();
        while (true) {
            int i = this._index;
            if (i >= numParagraphs) {
                return false;
            }
            Paragraph paragraph = this._range.getParagraph(i);
            if (paragraph.isInTable() && paragraph.getTableLevel() == this._levelNum) {
                return true;
            }
            this._index++;
        }
    }

    public Table next() {
        int i;
        int numParagraphs = this._range.numParagraphs();
        int i2 = this._index;
        while (true) {
            int i3 = this._index;
            if (i3 >= numParagraphs) {
                i = i2;
                break;
            }
            Paragraph paragraph = this._range.getParagraph(i3);
            if (!paragraph.isInTable() || paragraph.getTableLevel() < this._levelNum) {
                i = this._index;
            } else {
                this._index++;
            }
        }
        i = this._index;
        return new Table(this._range.getParagraph(i2).getStartOffset(), this._range.getParagraph(i - 1).getEndOffset(), this._range, this._levelNum);
    }
}
