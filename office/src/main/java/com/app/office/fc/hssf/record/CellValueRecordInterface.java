package com.app.office.fc.hssf.record;

public interface CellValueRecordInterface {
    short getColumn();

    int getRow();

    short getXFIndex();

    void setColumn(short s);

    void setRow(int i);

    void setXFIndex(short s);
}
