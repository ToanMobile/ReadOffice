package com.app.office.fc.ss.util;

import com.app.office.fc.ss.usermodel.CellRange;
import com.app.office.fc.ss.usermodel.ICell;
import com.app.office.fc.util.Internal;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Internal
public final class SSCellRange<K extends ICell> implements CellRange<K> {
    private final int _firstColumn;
    private final int _firstRow;
    private final K[] _flattenedArray;
    private final int _height;
    private final int _width;

    private SSCellRange(int i, int i2, int i3, int i4, K[] kArr) {
        this._firstRow = i;
        this._firstColumn = i2;
        this._height = i3;
        this._width = i4;
        this._flattenedArray = kArr;
    }

    public static <B extends ICell> SSCellRange<B> create(int i, int i2, int i3, int i4, List<B> list, Class<B> cls) {
        int size = list.size();
        if (i3 * i4 == size) {
            ICell[] iCellArr = (ICell[]) Array.newInstance(cls, size);
            list.toArray(iCellArr);
            return new SSCellRange(i, i2, i3, i4, iCellArr);
        }
        throw new IllegalArgumentException("Array size mismatch.");
    }

    public int getHeight() {
        return this._height;
    }

    public int getWidth() {
        return this._width;
    }

    public int size() {
        return this._height * this._width;
    }

    public String getReferenceText() {
        int i = this._firstRow;
        int i2 = this._firstColumn;
        return new HSSFCellRangeAddress(i, (this._height + i) - 1, i2, (this._width + i2) - 1).formatAsString();
    }

    public K getTopLeftCell() {
        return this._flattenedArray[0];
    }

    public K getCell(int i, int i2) {
        int i3;
        if (i < 0 || i >= this._height) {
            StringBuilder sb = new StringBuilder();
            sb.append("Specified row ");
            sb.append(i);
            sb.append(" is outside the allowable range (0..");
            sb.append(this._height - 1);
            sb.append(").");
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        } else if (i2 < 0 || i2 >= (i3 = this._width)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Specified colummn ");
            sb2.append(i2);
            sb2.append(" is outside the allowable range (0..");
            sb2.append(this._width - 1);
            sb2.append(").");
            throw new ArrayIndexOutOfBoundsException(sb2.toString());
        } else {
            return this._flattenedArray[(i3 * i) + i2];
        }
    }

    public K[] getFlattenedCells() {
        return (ICell[]) this._flattenedArray.clone();
    }

    public K[][] getCells() {
        Class<?> cls = this._flattenedArray.getClass();
        K[][] kArr = (ICell[][]) Array.newInstance(cls, this._height);
        Class<?> componentType = cls.getComponentType();
        for (int i = this._height - 1; i >= 0; i--) {
            int i2 = this._width;
            System.arraycopy(this._flattenedArray, i2 * i, (ICell[]) Array.newInstance(componentType, this._width), 0, i2);
        }
        return kArr;
    }

    public Iterator<K> iterator() {
        return new ArrayIterator(this._flattenedArray);
    }

    private static final class ArrayIterator<D> implements Iterator<D> {
        private final D[] _array;
        private int _index = 0;

        public ArrayIterator(D[] dArr) {
            this._array = dArr;
        }

        public boolean hasNext() {
            return this._index < this._array.length;
        }

        public D next() {
            int i = this._index;
            D[] dArr = this._array;
            if (i < dArr.length) {
                this._index = i + 1;
                return dArr[i];
            }
            throw new NoSuchElementException(String.valueOf(this._index));
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove cells from this CellRange.");
        }
    }
}
