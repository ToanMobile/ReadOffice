package com.app.office.fc.util;

public class IntList {
    private static final int _default_size = 128;
    private int[] _array;
    private int _limit;
    private int fillval;

    public IntList() {
        this(128);
    }

    public IntList(int i) {
        this(i, 0);
    }

    public IntList(IntList intList) {
        this(intList._array.length);
        int[] iArr = intList._array;
        int[] iArr2 = this._array;
        System.arraycopy(iArr, 0, iArr2, 0, iArr2.length);
        this._limit = intList._limit;
    }

    public IntList(int i, int i2) {
        this.fillval = 0;
        this._array = new int[i];
        this._limit = 0;
    }

    private void fillArray(int i, int[] iArr, int i2) {
        while (i2 < iArr.length) {
            iArr[i2] = i;
            i2++;
        }
    }

    public void add(int i, int i2) {
        int i3 = this._limit;
        if (i > i3) {
            throw new IndexOutOfBoundsException();
        } else if (i == i3) {
            add(i2);
        } else {
            if (i3 == this._array.length) {
                growArray(i3 * 2);
            }
            int[] iArr = this._array;
            System.arraycopy(iArr, i, iArr, i + 1, this._limit - i);
            this._array[i] = i2;
            this._limit++;
        }
    }

    public boolean add(int i) {
        int i2 = this._limit;
        if (i2 == this._array.length) {
            growArray(i2 * 2);
        }
        int[] iArr = this._array;
        int i3 = this._limit;
        this._limit = i3 + 1;
        iArr[i3] = i;
        return true;
    }

    public boolean addAll(IntList intList) {
        int i = intList._limit;
        if (i == 0) {
            return true;
        }
        int i2 = this._limit;
        if (i2 + i > this._array.length) {
            growArray(i2 + i);
        }
        System.arraycopy(intList._array, 0, this._array, this._limit, intList._limit);
        this._limit += intList._limit;
        return true;
    }

    public boolean addAll(int i, IntList intList) {
        int i2 = this._limit;
        if (i <= i2) {
            int i3 = intList._limit;
            if (i3 == 0) {
                return true;
            }
            if (i2 + i3 > this._array.length) {
                growArray(i2 + i3);
            }
            int[] iArr = this._array;
            System.arraycopy(iArr, i, iArr, intList._limit + i, this._limit - i);
            System.arraycopy(intList._array, 0, this._array, i, intList._limit);
            this._limit += intList._limit;
            return true;
        }
        throw new IndexOutOfBoundsException();
    }

    public void clear() {
        this._limit = 0;
    }

    public boolean contains(int i) {
        boolean z = false;
        int i2 = 0;
        while (!z && i2 < this._limit) {
            if (this._array[i2] == i) {
                z = true;
            }
            i2++;
        }
        return z;
    }

    public boolean containsAll(IntList intList) {
        boolean z = true;
        if (this != intList) {
            int i = 0;
            while (z && i < intList._limit) {
                if (!contains(intList._array[i])) {
                    z = false;
                }
                i++;
            }
        }
        return z;
    }

    public boolean equals(Object obj) {
        boolean z = this == obj;
        if (!z && obj != null && obj.getClass() == getClass()) {
            IntList intList = (IntList) obj;
            if (intList._limit == this._limit) {
                boolean z2 = true;
                int i = 0;
                while (z && i < this._limit) {
                    z2 = this._array[i] == intList._array[i];
                    i++;
                }
            }
        }
        return z;
    }

    public int get(int i) {
        if (i < this._limit) {
            return this._array[i];
        }
        throw new IndexOutOfBoundsException(i + " not accessible in a list of length " + this._limit);
    }

    public int hashCode() {
        int i = 0;
        for (int i2 = 0; i2 < this._limit; i2++) {
            i = (i * 31) + this._array[i2];
        }
        return i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0011 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int indexOf(int r4) {
        /*
            r3 = this;
            r0 = 0
        L_0x0001:
            int r1 = r3._limit
            if (r0 >= r1) goto L_0x000f
            int[] r2 = r3._array
            r2 = r2[r0]
            if (r4 != r2) goto L_0x000c
            goto L_0x000f
        L_0x000c:
            int r0 = r0 + 1
            goto L_0x0001
        L_0x000f:
            if (r0 != r1) goto L_0x0012
            r0 = -1
        L_0x0012:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.util.IntList.indexOf(int):int");
    }

    public boolean isEmpty() {
        return this._limit == 0;
    }

    public int lastIndexOf(int i) {
        int i2 = this._limit - 1;
        while (i2 >= 0 && i != this._array[i2]) {
            i2--;
        }
        return i2;
    }

    public int remove(int i) {
        int i2 = this._limit;
        if (i < i2) {
            int[] iArr = this._array;
            int i3 = iArr[i];
            System.arraycopy(iArr, i + 1, iArr, i, i2 - i);
            this._limit--;
            return i3;
        }
        throw new IndexOutOfBoundsException();
    }

    public boolean removeValue(int i) {
        boolean z = false;
        int i2 = 0;
        while (!z) {
            int i3 = this._limit;
            if (i2 >= i3) {
                break;
            }
            int[] iArr = this._array;
            if (i == iArr[i2]) {
                int i4 = i2 + 1;
                if (i4 < i3) {
                    System.arraycopy(iArr, i4, iArr, i2, i3 - i2);
                }
                this._limit--;
                z = true;
            }
            i2++;
        }
        return z;
    }

    public boolean removeAll(IntList intList) {
        boolean z = false;
        for (int i = 0; i < intList._limit; i++) {
            if (removeValue(intList._array[i])) {
                z = true;
            }
        }
        return z;
    }

    public boolean retainAll(IntList intList) {
        int i = 0;
        boolean z = false;
        while (i < this._limit) {
            if (!intList.contains(this._array[i])) {
                remove(i);
                z = true;
            } else {
                i++;
            }
        }
        return z;
    }

    public int set(int i, int i2) {
        if (i < this._limit) {
            int[] iArr = this._array;
            int i3 = iArr[i];
            iArr[i] = i2;
            return i3;
        }
        throw new IndexOutOfBoundsException();
    }

    public int size() {
        return this._limit;
    }

    public int[] toArray() {
        int i = this._limit;
        int[] iArr = new int[i];
        System.arraycopy(this._array, 0, iArr, 0, i);
        return iArr;
    }

    public int[] toArray(int[] iArr) {
        int length = iArr.length;
        int i = this._limit;
        if (length != i) {
            return toArray();
        }
        System.arraycopy(this._array, 0, iArr, 0, i);
        return iArr;
    }

    private void growArray(int i) {
        int[] iArr = this._array;
        if (i == iArr.length) {
            i++;
        }
        int[] iArr2 = new int[i];
        int i2 = this.fillval;
        if (i2 != 0) {
            fillArray(i2, iArr2, iArr.length);
        }
        System.arraycopy(this._array, 0, iArr2, 0, this._limit);
        this._array = iArr2;
    }
}
