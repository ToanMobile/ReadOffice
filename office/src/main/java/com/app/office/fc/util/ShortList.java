package com.app.office.fc.util;

public class ShortList {
    private static final int _default_size = 128;
    private short[] _array;
    private int _limit;

    public ShortList() {
        this(128);
    }

    public ShortList(ShortList shortList) {
        this(shortList._array.length);
        short[] sArr = shortList._array;
        short[] sArr2 = this._array;
        System.arraycopy(sArr, 0, sArr2, 0, sArr2.length);
        this._limit = shortList._limit;
    }

    public ShortList(int i) {
        this._array = new short[i];
        this._limit = 0;
    }

    public void add(int i, short s) {
        int i2 = this._limit;
        if (i > i2) {
            throw new IndexOutOfBoundsException();
        } else if (i == i2) {
            add(s);
        } else {
            if (i2 == this._array.length) {
                growArray(i2 * 2);
            }
            short[] sArr = this._array;
            System.arraycopy(sArr, i, sArr, i + 1, this._limit - i);
            this._array[i] = s;
            this._limit++;
        }
    }

    public boolean add(short s) {
        int i = this._limit;
        if (i == this._array.length) {
            growArray(i * 2);
        }
        short[] sArr = this._array;
        int i2 = this._limit;
        this._limit = i2 + 1;
        sArr[i2] = s;
        return true;
    }

    public boolean addAll(ShortList shortList) {
        int i = shortList._limit;
        if (i == 0) {
            return true;
        }
        int i2 = this._limit;
        if (i2 + i > this._array.length) {
            growArray(i2 + i);
        }
        System.arraycopy(shortList._array, 0, this._array, this._limit, shortList._limit);
        this._limit += shortList._limit;
        return true;
    }

    public boolean addAll(int i, ShortList shortList) {
        int i2 = this._limit;
        if (i <= i2) {
            int i3 = shortList._limit;
            if (i3 == 0) {
                return true;
            }
            if (i2 + i3 > this._array.length) {
                growArray(i2 + i3);
            }
            short[] sArr = this._array;
            System.arraycopy(sArr, i, sArr, shortList._limit + i, this._limit - i);
            System.arraycopy(shortList._array, 0, this._array, i, shortList._limit);
            this._limit += shortList._limit;
            return true;
        }
        throw new IndexOutOfBoundsException();
    }

    public void clear() {
        this._limit = 0;
    }

    public boolean contains(short s) {
        boolean z = false;
        int i = 0;
        while (!z && i < this._limit) {
            if (this._array[i] == s) {
                z = true;
            }
            i++;
        }
        return z;
    }

    public boolean containsAll(ShortList shortList) {
        boolean z = true;
        if (this != shortList) {
            int i = 0;
            while (z && i < shortList._limit) {
                if (!contains(shortList._array[i])) {
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
            ShortList shortList = (ShortList) obj;
            if (shortList._limit == this._limit) {
                boolean z2 = true;
                int i = 0;
                while (z && i < this._limit) {
                    z2 = this._array[i] == shortList._array[i];
                    i++;
                }
            }
        }
        return z;
    }

    public short get(int i) {
        if (i < this._limit) {
            return this._array[i];
        }
        throw new IndexOutOfBoundsException();
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
    public int indexOf(short r4) {
        /*
            r3 = this;
            r0 = 0
        L_0x0001:
            int r1 = r3._limit
            if (r0 >= r1) goto L_0x000f
            short[] r2 = r3._array
            short r2 = r2[r0]
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
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.util.ShortList.indexOf(short):int");
    }

    public boolean isEmpty() {
        return this._limit == 0;
    }

    public int lastIndexOf(short s) {
        int i = this._limit - 1;
        while (i >= 0 && s != this._array[i]) {
            i--;
        }
        return i;
    }

    public short remove(int i) {
        int i2 = this._limit;
        if (i < i2) {
            short[] sArr = this._array;
            short s = sArr[i];
            System.arraycopy(sArr, i + 1, sArr, i, i2 - i);
            this._limit--;
            return s;
        }
        throw new IndexOutOfBoundsException();
    }

    public boolean removeValue(short s) {
        boolean z = false;
        int i = 0;
        while (!z) {
            int i2 = this._limit;
            if (i >= i2) {
                break;
            }
            short[] sArr = this._array;
            if (s == sArr[i]) {
                System.arraycopy(sArr, i + 1, sArr, i, i2 - i);
                this._limit--;
                z = true;
            }
            i++;
        }
        return z;
    }

    public boolean removeAll(ShortList shortList) {
        boolean z = false;
        for (int i = 0; i < shortList._limit; i++) {
            if (removeValue(shortList._array[i])) {
                z = true;
            }
        }
        return z;
    }

    public boolean retainAll(ShortList shortList) {
        int i = 0;
        boolean z = false;
        while (i < this._limit) {
            if (!shortList.contains(this._array[i])) {
                remove(i);
                z = true;
            } else {
                i++;
            }
        }
        return z;
    }

    public short set(int i, short s) {
        if (i < this._limit) {
            short[] sArr = this._array;
            short s2 = sArr[i];
            sArr[i] = s;
            return s2;
        }
        throw new IndexOutOfBoundsException();
    }

    public int size() {
        return this._limit;
    }

    public short[] toArray() {
        int i = this._limit;
        short[] sArr = new short[i];
        System.arraycopy(this._array, 0, sArr, 0, i);
        return sArr;
    }

    public short[] toArray(short[] sArr) {
        int length = sArr.length;
        int i = this._limit;
        if (length != i) {
            return toArray();
        }
        System.arraycopy(this._array, 0, sArr, 0, i);
        return sArr;
    }

    private void growArray(int i) {
        short[] sArr = this._array;
        if (i == sArr.length) {
            i++;
        }
        short[] sArr2 = new short[i];
        System.arraycopy(sArr, 0, sArr2, 0, this._limit);
        this._array = sArr2;
    }
}
