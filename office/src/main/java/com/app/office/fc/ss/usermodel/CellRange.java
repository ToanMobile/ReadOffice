package com.app.office.fc.ss.usermodel;

import com.app.office.fc.ss.usermodel.ICell;
import java.util.Iterator;

public interface CellRange<C extends ICell> extends Iterable<C> {
    C getCell(int i, int i2);

    C[][] getCells();

    C[] getFlattenedCells();

    int getHeight();

    String getReferenceText();

    C getTopLeftCell();

    int getWidth();

    Iterator<C> iterator();

    int size();
}
