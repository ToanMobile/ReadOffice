package com.app.office.java.util;

import com.itextpdf.xmp.XMPConst;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Set;

public class Arrays {
    private static final int INSERTIONSORT_THRESHOLD = 7;

    private Arrays() {
    }

    public static void sort(long[] jArr) {
        sort1(jArr, 0, jArr.length);
    }

    public static void sort(long[] jArr, int i, int i2) {
        rangeCheck(jArr.length, i, i2);
        sort1(jArr, i, i2 - i);
    }

    public static void sort(int[] iArr) {
        sort1(iArr, 0, iArr.length);
    }

    public static void sort(int[] iArr, int i, int i2) {
        rangeCheck(iArr.length, i, i2);
        sort1(iArr, i, i2 - i);
    }

    public static void sort(short[] sArr) {
        sort1(sArr, 0, sArr.length);
    }

    public static void sort(short[] sArr, int i, int i2) {
        rangeCheck(sArr.length, i, i2);
        sort1(sArr, i, i2 - i);
    }

    public static void sort(char[] cArr) {
        sort1(cArr, 0, cArr.length);
    }

    public static void sort(char[] cArr, int i, int i2) {
        rangeCheck(cArr.length, i, i2);
        sort1(cArr, i, i2 - i);
    }

    public static void sort(byte[] bArr) {
        sort1(bArr, 0, bArr.length);
    }

    public static void sort(byte[] bArr, int i, int i2) {
        rangeCheck(bArr.length, i, i2);
        sort1(bArr, i, i2 - i);
    }

    public static void sort(double[] dArr) {
        sort2(dArr, 0, dArr.length);
    }

    public static void sort(double[] dArr, int i, int i2) {
        rangeCheck(dArr.length, i, i2);
        sort2(dArr, i, i2);
    }

    public static void sort(float[] fArr) {
        sort2(fArr, 0, fArr.length);
    }

    public static void sort(float[] fArr, int i, int i2) {
        rangeCheck(fArr.length, i, i2);
        sort2(fArr, i, i2);
    }

    private static void sort2(double[] dArr, int i, int i2) {
        double[] dArr2 = dArr;
        int i3 = i;
        long doubleToLongBits = Double.doubleToLongBits(-0.0d);
        int i4 = i2;
        int i5 = i3;
        int i6 = 0;
        while (i5 < i4) {
            if (dArr2[i5] != dArr2[i5]) {
                double d = dArr2[i5];
                i4--;
                dArr2[i5] = dArr2[i4];
                dArr2[i4] = d;
            } else {
                if (dArr2[i5] == 0.0d && Double.doubleToLongBits(dArr2[i5]) == doubleToLongBits) {
                    dArr2[i5] = 0.0d;
                    i6++;
                }
                i5++;
            }
        }
        sort1(dArr2, i3, i4 - i3);
        if (i6 != 0) {
            int binarySearch0 = binarySearch0(dArr2, i3, i4, 0.0d);
            do {
                binarySearch0--;
                if (binarySearch0 < 0 || dArr2[binarySearch0] != 0.0d) {
                }
                binarySearch0--;
                break;
            } while (dArr2[binarySearch0] != 0.0d);
            for (int i7 = 0; i7 < i6; i7++) {
                binarySearch0++;
                dArr2[binarySearch0] = -0.0d;
            }
        }
    }

    private static void sort2(float[] fArr, int i, int i2) {
        int floatToIntBits = Float.floatToIntBits(-0.0f);
        int i3 = i;
        int i4 = 0;
        while (i3 < i2) {
            if (fArr[i3] != fArr[i3]) {
                float f = fArr[i3];
                i2--;
                fArr[i3] = fArr[i2];
                fArr[i2] = f;
            } else {
                if (fArr[i3] == 0.0f && Float.floatToIntBits(fArr[i3]) == floatToIntBits) {
                    fArr[i3] = 0.0f;
                    i4++;
                }
                i3++;
            }
        }
        sort1(fArr, i, i2 - i);
        if (i4 != 0) {
            int binarySearch0 = binarySearch0(fArr, i, i2, 0.0f);
            do {
                binarySearch0--;
                if (binarySearch0 < 0 || fArr[binarySearch0] != 0.0f) {
                }
                binarySearch0--;
                break;
            } while (fArr[binarySearch0] != 0.0f);
            for (int i5 = 0; i5 < i4; i5++) {
                binarySearch0++;
                fArr[binarySearch0] = -0.0f;
            }
        }
    }

    private static void sort1(long[] jArr, int i, int i2) {
        int i3;
        if (i2 < 7) {
            for (int i4 = i; i4 < i2 + i; i4++) {
                for (int i5 = i4; i5 > i; i5--) {
                    int i6 = i5 - 1;
                    if (jArr[i6] <= jArr[i5]) {
                        break;
                    }
                    swap(jArr, i5, i6);
                }
            }
            return;
        }
        int i7 = (i2 >> 1) + i;
        if (i2 > 7) {
            int i8 = (i + i2) - 1;
            if (i2 > 40) {
                int i9 = i2 / 8;
                int i10 = i9 * 2;
                i3 = med3(jArr, i, i + i9, i + i10);
                i7 = med3(jArr, i7 - i9, i7, i7 + i9);
                i8 = med3(jArr, i8 - i10, i8 - i9, i8);
            } else {
                i3 = i;
            }
            i7 = med3(jArr, i3, i7, i8);
        }
        long j = jArr[i7];
        int i11 = i2 + i;
        int i12 = i11 - 1;
        int i13 = i;
        int i14 = i13;
        int i15 = i12;
        while (true) {
            if (i13 > i12 || jArr[i13] > j) {
                while (i12 >= i13 && jArr[i12] >= j) {
                    if (jArr[i12] == j) {
                        swap(jArr, i12, i15);
                        i15--;
                    }
                    i12--;
                }
                if (i13 > i12) {
                    break;
                }
                swap(jArr, i13, i12);
                i13++;
                i12--;
            } else {
                if (jArr[i13] == j) {
                    swap(jArr, i14, i13);
                    i14++;
                }
                i13++;
            }
        }
        int i16 = i13 - i14;
        int min = Math.min(i14 - i, i16);
        vecswap(jArr, i, i13 - min, min);
        int i17 = i15 - i12;
        int min2 = Math.min(i17, (i11 - i15) - 1);
        vecswap(jArr, i13, i11 - min2, min2);
        if (i16 > 1) {
            sort1(jArr, i, i16);
        }
        if (i17 > 1) {
            sort1(jArr, i11 - i17, i17);
        }
    }

    private static void swap(long[] jArr, int i, int i2) {
        long j = jArr[i];
        jArr[i] = jArr[i2];
        jArr[i2] = j;
    }

    private static void vecswap(long[] jArr, int i, int i2, int i3) {
        int i4 = 0;
        while (i4 < i3) {
            swap(jArr, i, i2);
            i4++;
            i++;
            i2++;
        }
    }

    private static int med3(long[] jArr, int i, int i2, int i3) {
        if (jArr[i] < jArr[i2]) {
            if (jArr[i2] >= jArr[i3]) {
                if (jArr[i] >= jArr[i3]) {
                    return i;
                }
                return i3;
            }
        } else if (jArr[i2] <= jArr[i3]) {
            if (jArr[i] <= jArr[i3]) {
                return i;
            }
            return i3;
        }
        return i2;
    }

    private static void sort1(int[] iArr, int i, int i2) {
        int i3;
        if (i2 < 7) {
            for (int i4 = i; i4 < i2 + i; i4++) {
                for (int i5 = i4; i5 > i; i5--) {
                    int i6 = i5 - 1;
                    if (iArr[i6] <= iArr[i5]) {
                        break;
                    }
                    swap(iArr, i5, i6);
                }
            }
            return;
        }
        int i7 = (i2 >> 1) + i;
        if (i2 > 7) {
            int i8 = (i + i2) - 1;
            if (i2 > 40) {
                int i9 = i2 / 8;
                int i10 = i9 * 2;
                i3 = med3(iArr, i, i + i9, i + i10);
                i7 = med3(iArr, i7 - i9, i7, i7 + i9);
                i8 = med3(iArr, i8 - i10, i8 - i9, i8);
            } else {
                i3 = i;
            }
            i7 = med3(iArr, i3, i7, i8);
        }
        int i11 = iArr[i7];
        int i12 = i2 + i;
        int i13 = i12 - 1;
        int i14 = i;
        int i15 = i14;
        int i16 = i13;
        while (true) {
            if (i14 > i13 || iArr[i14] > i11) {
                while (i13 >= i14 && iArr[i13] >= i11) {
                    if (iArr[i13] == i11) {
                        swap(iArr, i13, i16);
                        i16--;
                    }
                    i13--;
                }
                if (i14 > i13) {
                    break;
                }
                swap(iArr, i14, i13);
                i14++;
                i13--;
            } else {
                if (iArr[i14] == i11) {
                    swap(iArr, i15, i14);
                    i15++;
                }
                i14++;
            }
        }
        int i17 = i15 - i;
        int i18 = i14 - i15;
        int min = Math.min(i17, i18);
        vecswap(iArr, i, i14 - min, min);
        int i19 = i16 - i13;
        int min2 = Math.min(i19, (i12 - i16) - 1);
        vecswap(iArr, i14, i12 - min2, min2);
        if (i18 > 1) {
            sort1(iArr, i, i18);
        }
        if (i19 > 1) {
            sort1(iArr, i12 - i19, i19);
        }
    }

    private static void swap(int[] iArr, int i, int i2) {
        int i3 = iArr[i];
        iArr[i] = iArr[i2];
        iArr[i2] = i3;
    }

    private static void vecswap(int[] iArr, int i, int i2, int i3) {
        int i4 = 0;
        while (i4 < i3) {
            swap(iArr, i, i2);
            i4++;
            i++;
            i2++;
        }
    }

    private static int med3(int[] iArr, int i, int i2, int i3) {
        if (iArr[i] < iArr[i2]) {
            if (iArr[i2] >= iArr[i3]) {
                if (iArr[i] >= iArr[i3]) {
                    return i;
                }
                return i3;
            }
        } else if (iArr[i2] <= iArr[i3]) {
            if (iArr[i] <= iArr[i3]) {
                return i;
            }
            return i3;
        }
        return i2;
    }

    private static void sort1(short[] sArr, int i, int i2) {
        int i3;
        if (i2 < 7) {
            for (int i4 = i; i4 < i2 + i; i4++) {
                for (int i5 = i4; i5 > i; i5--) {
                    int i6 = i5 - 1;
                    if (sArr[i6] <= sArr[i5]) {
                        break;
                    }
                    swap(sArr, i5, i6);
                }
            }
            return;
        }
        int i7 = (i2 >> 1) + i;
        if (i2 > 7) {
            int i8 = (i + i2) - 1;
            if (i2 > 40) {
                int i9 = i2 / 8;
                int i10 = i9 * 2;
                i3 = med3(sArr, i, i + i9, i + i10);
                i7 = med3(sArr, i7 - i9, i7, i7 + i9);
                i8 = med3(sArr, i8 - i10, i8 - i9, i8);
            } else {
                i3 = i;
            }
            i7 = med3(sArr, i3, i7, i8);
        }
        short s = sArr[i7];
        int i11 = i2 + i;
        int i12 = i11 - 1;
        int i13 = i;
        int i14 = i13;
        int i15 = i12;
        while (true) {
            if (i13 > i12 || sArr[i13] > s) {
                while (i12 >= i13 && sArr[i12] >= s) {
                    if (sArr[i12] == s) {
                        swap(sArr, i12, i15);
                        i15--;
                    }
                    i12--;
                }
                if (i13 > i12) {
                    break;
                }
                swap(sArr, i13, i12);
                i13++;
                i12--;
            } else {
                if (sArr[i13] == s) {
                    swap(sArr, i14, i13);
                    i14++;
                }
                i13++;
            }
        }
        int i16 = i14 - i;
        int i17 = i13 - i14;
        int min = Math.min(i16, i17);
        vecswap(sArr, i, i13 - min, min);
        int i18 = i15 - i12;
        int min2 = Math.min(i18, (i11 - i15) - 1);
        vecswap(sArr, i13, i11 - min2, min2);
        if (i17 > 1) {
            sort1(sArr, i, i17);
        }
        if (i18 > 1) {
            sort1(sArr, i11 - i18, i18);
        }
    }

    private static void swap(short[] sArr, int i, int i2) {
        short s = sArr[i];
        sArr[i] = sArr[i2];
        sArr[i2] = s;
    }

    private static void vecswap(short[] sArr, int i, int i2, int i3) {
        int i4 = 0;
        while (i4 < i3) {
            swap(sArr, i, i2);
            i4++;
            i++;
            i2++;
        }
    }

    private static int med3(short[] sArr, int i, int i2, int i3) {
        if (sArr[i] < sArr[i2]) {
            if (sArr[i2] >= sArr[i3]) {
                if (sArr[i] >= sArr[i3]) {
                    return i;
                }
                return i3;
            }
        } else if (sArr[i2] <= sArr[i3]) {
            if (sArr[i] <= sArr[i3]) {
                return i;
            }
            return i3;
        }
        return i2;
    }

    private static void sort1(char[] cArr, int i, int i2) {
        int i3;
        if (i2 < 7) {
            for (int i4 = i; i4 < i2 + i; i4++) {
                for (int i5 = i4; i5 > i; i5--) {
                    int i6 = i5 - 1;
                    if (cArr[i6] <= cArr[i5]) {
                        break;
                    }
                    swap(cArr, i5, i6);
                }
            }
            return;
        }
        int i7 = (i2 >> 1) + i;
        if (i2 > 7) {
            int i8 = (i + i2) - 1;
            if (i2 > 40) {
                int i9 = i2 / 8;
                int i10 = i9 * 2;
                i3 = med3(cArr, i, i + i9, i + i10);
                i7 = med3(cArr, i7 - i9, i7, i7 + i9);
                i8 = med3(cArr, i8 - i10, i8 - i9, i8);
            } else {
                i3 = i;
            }
            i7 = med3(cArr, i3, i7, i8);
        }
        char c = cArr[i7];
        int i11 = i2 + i;
        int i12 = i11 - 1;
        int i13 = i;
        int i14 = i13;
        int i15 = i12;
        while (true) {
            if (i13 > i12 || cArr[i13] > c) {
                while (i12 >= i13 && cArr[i12] >= c) {
                    if (cArr[i12] == c) {
                        swap(cArr, i12, i15);
                        i15--;
                    }
                    i12--;
                }
                if (i13 > i12) {
                    break;
                }
                swap(cArr, i13, i12);
                i13++;
                i12--;
            } else {
                if (cArr[i13] == c) {
                    swap(cArr, i14, i13);
                    i14++;
                }
                i13++;
            }
        }
        int i16 = i14 - i;
        int i17 = i13 - i14;
        int min = Math.min(i16, i17);
        vecswap(cArr, i, i13 - min, min);
        int i18 = i15 - i12;
        int min2 = Math.min(i18, (i11 - i15) - 1);
        vecswap(cArr, i13, i11 - min2, min2);
        if (i17 > 1) {
            sort1(cArr, i, i17);
        }
        if (i18 > 1) {
            sort1(cArr, i11 - i18, i18);
        }
    }

    private static void swap(char[] cArr, int i, int i2) {
        char c = cArr[i];
        cArr[i] = cArr[i2];
        cArr[i2] = c;
    }

    private static void vecswap(char[] cArr, int i, int i2, int i3) {
        int i4 = 0;
        while (i4 < i3) {
            swap(cArr, i, i2);
            i4++;
            i++;
            i2++;
        }
    }

    private static int med3(char[] cArr, int i, int i2, int i3) {
        if (cArr[i] < cArr[i2]) {
            if (cArr[i2] >= cArr[i3]) {
                if (cArr[i] >= cArr[i3]) {
                    return i;
                }
                return i3;
            }
        } else if (cArr[i2] <= cArr[i3]) {
            if (cArr[i] <= cArr[i3]) {
                return i;
            }
            return i3;
        }
        return i2;
    }

    private static void sort1(byte[] bArr, int i, int i2) {
        int i3;
        if (i2 < 7) {
            for (int i4 = i; i4 < i2 + i; i4++) {
                for (int i5 = i4; i5 > i; i5--) {
                    int i6 = i5 - 1;
                    if (bArr[i6] <= bArr[i5]) {
                        break;
                    }
                    swap(bArr, i5, i6);
                }
            }
            return;
        }
        int i7 = (i2 >> 1) + i;
        if (i2 > 7) {
            int i8 = (i + i2) - 1;
            if (i2 > 40) {
                int i9 = i2 / 8;
                int i10 = i9 * 2;
                i3 = med3(bArr, i, i + i9, i + i10);
                i7 = med3(bArr, i7 - i9, i7, i7 + i9);
                i8 = med3(bArr, i8 - i10, i8 - i9, i8);
            } else {
                i3 = i;
            }
            i7 = med3(bArr, i3, i7, i8);
        }
        byte b = bArr[i7];
        int i11 = i2 + i;
        int i12 = i11 - 1;
        int i13 = i;
        int i14 = i13;
        int i15 = i12;
        while (true) {
            if (i13 > i12 || bArr[i13] > b) {
                while (i12 >= i13 && bArr[i12] >= b) {
                    if (bArr[i12] == b) {
                        swap(bArr, i12, i15);
                        i15--;
                    }
                    i12--;
                }
                if (i13 > i12) {
                    break;
                }
                swap(bArr, i13, i12);
                i13++;
                i12--;
            } else {
                if (bArr[i13] == b) {
                    swap(bArr, i14, i13);
                    i14++;
                }
                i13++;
            }
        }
        int i16 = i14 - i;
        int i17 = i13 - i14;
        int min = Math.min(i16, i17);
        vecswap(bArr, i, i13 - min, min);
        int i18 = i15 - i12;
        int min2 = Math.min(i18, (i11 - i15) - 1);
        vecswap(bArr, i13, i11 - min2, min2);
        if (i17 > 1) {
            sort1(bArr, i, i17);
        }
        if (i18 > 1) {
            sort1(bArr, i11 - i18, i18);
        }
    }

    private static void swap(byte[] bArr, int i, int i2) {
        byte b = bArr[i];
        bArr[i] = bArr[i2];
        bArr[i2] = b;
    }

    private static void vecswap(byte[] bArr, int i, int i2, int i3) {
        int i4 = 0;
        while (i4 < i3) {
            swap(bArr, i, i2);
            i4++;
            i++;
            i2++;
        }
    }

    private static int med3(byte[] bArr, int i, int i2, int i3) {
        if (bArr[i] < bArr[i2]) {
            if (bArr[i2] >= bArr[i3]) {
                if (bArr[i] >= bArr[i3]) {
                    return i;
                }
                return i3;
            }
        } else if (bArr[i2] <= bArr[i3]) {
            if (bArr[i] <= bArr[i3]) {
                return i;
            }
            return i3;
        }
        return i2;
    }

    private static void sort1(double[] dArr, int i, int i2) {
        int i3;
        if (i2 < 7) {
            for (int i4 = i; i4 < i2 + i; i4++) {
                for (int i5 = i4; i5 > i; i5--) {
                    int i6 = i5 - 1;
                    if (dArr[i6] <= dArr[i5]) {
                        break;
                    }
                    swap(dArr, i5, i6);
                }
            }
            return;
        }
        int i7 = (i2 >> 1) + i;
        if (i2 > 7) {
            int i8 = (i + i2) - 1;
            if (i2 > 40) {
                int i9 = i2 / 8;
                int i10 = i9 * 2;
                i3 = med3(dArr, i, i + i9, i + i10);
                i7 = med3(dArr, i7 - i9, i7, i7 + i9);
                i8 = med3(dArr, i8 - i10, i8 - i9, i8);
            } else {
                i3 = i;
            }
            i7 = med3(dArr, i3, i7, i8);
        }
        double d = dArr[i7];
        int i11 = i2 + i;
        int i12 = i11 - 1;
        int i13 = i;
        int i14 = i13;
        int i15 = i12;
        while (true) {
            if (i13 > i12 || dArr[i13] > d) {
                while (i12 >= i13 && dArr[i12] >= d) {
                    if (dArr[i12] == d) {
                        swap(dArr, i12, i15);
                        i15--;
                    }
                    i12--;
                }
                if (i13 > i12) {
                    break;
                }
                swap(dArr, i13, i12);
                i13++;
                i12--;
            } else {
                if (dArr[i13] == d) {
                    swap(dArr, i14, i13);
                    i14++;
                }
                i13++;
            }
        }
        int i16 = i13 - i14;
        int min = Math.min(i14 - i, i16);
        vecswap(dArr, i, i13 - min, min);
        int i17 = i15 - i12;
        int min2 = Math.min(i17, (i11 - i15) - 1);
        vecswap(dArr, i13, i11 - min2, min2);
        if (i16 > 1) {
            sort1(dArr, i, i16);
        }
        if (i17 > 1) {
            sort1(dArr, i11 - i17, i17);
        }
    }

    private static void swap(double[] dArr, int i, int i2) {
        double d = dArr[i];
        dArr[i] = dArr[i2];
        dArr[i2] = d;
    }

    private static void vecswap(double[] dArr, int i, int i2, int i3) {
        int i4 = 0;
        while (i4 < i3) {
            swap(dArr, i, i2);
            i4++;
            i++;
            i2++;
        }
    }

    private static int med3(double[] dArr, int i, int i2, int i3) {
        if (dArr[i] < dArr[i2]) {
            if (dArr[i2] >= dArr[i3]) {
                if (dArr[i] >= dArr[i3]) {
                    return i;
                }
                return i3;
            }
        } else if (dArr[i2] <= dArr[i3]) {
            if (dArr[i] <= dArr[i3]) {
                return i;
            }
            return i3;
        }
        return i2;
    }

    private static void sort1(float[] fArr, int i, int i2) {
        int i3;
        if (i2 < 7) {
            for (int i4 = i; i4 < i2 + i; i4++) {
                for (int i5 = i4; i5 > i; i5--) {
                    int i6 = i5 - 1;
                    if (fArr[i6] <= fArr[i5]) {
                        break;
                    }
                    swap(fArr, i5, i6);
                }
            }
            return;
        }
        int i7 = (i2 >> 1) + i;
        if (i2 > 7) {
            int i8 = (i + i2) - 1;
            if (i2 > 40) {
                int i9 = i2 / 8;
                int i10 = i9 * 2;
                i3 = med3(fArr, i, i + i9, i + i10);
                i7 = med3(fArr, i7 - i9, i7, i7 + i9);
                i8 = med3(fArr, i8 - i10, i8 - i9, i8);
            } else {
                i3 = i;
            }
            i7 = med3(fArr, i3, i7, i8);
        }
        float f = fArr[i7];
        int i11 = i2 + i;
        int i12 = i11 - 1;
        int i13 = i;
        int i14 = i13;
        int i15 = i12;
        while (true) {
            if (i13 > i12 || fArr[i13] > f) {
                while (i12 >= i13 && fArr[i12] >= f) {
                    if (fArr[i12] == f) {
                        swap(fArr, i12, i15);
                        i15--;
                    }
                    i12--;
                }
                if (i13 > i12) {
                    break;
                }
                swap(fArr, i13, i12);
                i13++;
                i12--;
            } else {
                if (fArr[i13] == f) {
                    swap(fArr, i14, i13);
                    i14++;
                }
                i13++;
            }
        }
        int i16 = i14 - i;
        int i17 = i13 - i14;
        int min = Math.min(i16, i17);
        vecswap(fArr, i, i13 - min, min);
        int i18 = i15 - i12;
        int min2 = Math.min(i18, (i11 - i15) - 1);
        vecswap(fArr, i13, i11 - min2, min2);
        if (i17 > 1) {
            sort1(fArr, i, i17);
        }
        if (i18 > 1) {
            sort1(fArr, i11 - i18, i18);
        }
    }

    private static void swap(float[] fArr, int i, int i2) {
        float f = fArr[i];
        fArr[i] = fArr[i2];
        fArr[i2] = f;
    }

    private static void vecswap(float[] fArr, int i, int i2, int i3) {
        int i4 = 0;
        while (i4 < i3) {
            swap(fArr, i, i2);
            i4++;
            i++;
            i2++;
        }
    }

    private static int med3(float[] fArr, int i, int i2, int i3) {
        if (fArr[i] < fArr[i2]) {
            if (fArr[i2] >= fArr[i3]) {
                if (fArr[i] >= fArr[i3]) {
                    return i;
                }
                return i3;
            }
        } else if (fArr[i2] <= fArr[i3]) {
            if (fArr[i] <= fArr[i3]) {
                return i;
            }
            return i3;
        }
        return i2;
    }

    public static void sort(Object[] objArr) {
        mergeSort((Object[]) objArr.clone(), objArr, 0, objArr.length, 0);
    }

    public static void sort(Object[] objArr, int i, int i2) {
        rangeCheck(objArr.length, i, i2);
        mergeSort(copyOfRange((T[]) objArr, i, i2), objArr, i, i2, -i);
    }

    private static void mergeSort(Object[] objArr, Object[] objArr2, int i, int i2, int i3) {
        int i4 = i2 - i;
        if (i4 < 7) {
            for (int i5 = i; i5 < i2; i5++) {
                for (int i6 = i5; i6 > i; i6--) {
                    int i7 = i6 - 1;
                    if (objArr2[i7].compareTo(objArr2[i6]) <= 0) {
                        break;
                    }
                    swap(objArr2, i6, i7);
                }
            }
            return;
        }
        int i8 = i + i3;
        int i9 = i2 + i3;
        int i10 = (i8 + i9) >>> 1;
        int i11 = -i3;
        mergeSort(objArr2, objArr, i8, i10, i11);
        mergeSort(objArr2, objArr, i10, i9, i11);
        if (objArr[i10 - 1].compareTo(objArr[i10]) <= 0) {
            System.arraycopy(objArr, i8, objArr2, i, i4);
            return;
        }
        int i12 = i10;
        while (i < i2) {
            if (i12 >= i9 || (i8 < i10 && objArr[i8].compareTo(objArr[i12]) <= 0)) {
                objArr2[i] = objArr[i8];
                i8++;
            } else {
                objArr2[i] = objArr[i12];
                i12++;
            }
            i++;
        }
    }

    private static void swap(Object[] objArr, int i, int i2) {
        Object obj = objArr[i];
        objArr[i] = objArr[i2];
        objArr[i2] = obj;
    }

    public static <T> void sort(T[] tArr, Comparator<? super T> comparator) {
        Object[] objArr = (Object[]) tArr.clone();
        if (comparator == null) {
            mergeSort(objArr, tArr, 0, tArr.length, 0);
            return;
        }
        mergeSort(objArr, tArr, 0, tArr.length, 0, comparator);
    }

    public static <T> void sort(T[] tArr, int i, int i2, Comparator<? super T> comparator) {
        rangeCheck(tArr.length, i, i2);
        Object[] copyOfRange = copyOfRange(tArr, i, i2);
        if (comparator == null) {
            mergeSort(copyOfRange, tArr, i, i2, -i);
            return;
        }
        mergeSort(copyOfRange, tArr, i, i2, -i, comparator);
    }

    private static void mergeSort(Object[] objArr, Object[] objArr2, int i, int i2, int i3, Comparator comparator) {
        int i4 = i2 - i;
        if (i4 < 7) {
            for (int i5 = i; i5 < i2; i5++) {
                for (int i6 = i5; i6 > i; i6--) {
                    int i7 = i6 - 1;
                    if (comparator.compare(objArr2[i7], objArr2[i6]) <= 0) {
                        break;
                    }
                    swap(objArr2, i6, i7);
                }
            }
            return;
        }
        int i8 = i + i3;
        int i9 = i2 + i3;
        int i10 = (i8 + i9) >>> 1;
        Object[] objArr3 = objArr2;
        Object[] objArr4 = objArr;
        int i11 = -i3;
        Comparator comparator2 = comparator;
        mergeSort(objArr3, objArr4, i8, i10, i11, comparator2);
        mergeSort(objArr3, objArr4, i10, i9, i11, comparator2);
        if (comparator.compare(objArr[i10 - 1], objArr[i10]) <= 0) {
            System.arraycopy(objArr, i8, objArr2, i, i4);
            return;
        }
        int i12 = i10;
        while (i < i2) {
            if (i12 >= i9 || (i8 < i10 && comparator.compare(objArr[i8], objArr[i12]) <= 0)) {
                objArr2[i] = objArr[i8];
                i8++;
            } else {
                objArr2[i] = objArr[i12];
                i12++;
            }
            i++;
        }
    }

    private static void rangeCheck(int i, int i2, int i3) {
        if (i2 > i3) {
            throw new IllegalArgumentException("fromIndex(" + i2 + ") > toIndex(" + i3 + ")");
        } else if (i2 < 0) {
            throw new ArrayIndexOutOfBoundsException(i2);
        } else if (i3 > i) {
            throw new ArrayIndexOutOfBoundsException(i3);
        }
    }

    public static int binarySearch(long[] jArr, long j) {
        return binarySearch0(jArr, 0, jArr.length, j);
    }

    public static int binarySearch(long[] jArr, int i, int i2, long j) {
        rangeCheck(jArr.length, i, i2);
        return binarySearch0(jArr, i, i2, j);
    }

    private static int binarySearch0(long[] jArr, int i, int i2, long j) {
        int i3 = i2 - 1;
        while (i <= i3) {
            int i4 = (i + i3) >>> 1;
            int i5 = (jArr[i4] > j ? 1 : (jArr[i4] == j ? 0 : -1));
            if (i5 < 0) {
                i = i4 + 1;
            } else if (i5 <= 0) {
                return i4;
            } else {
                i3 = i4 - 1;
            }
        }
        return -(i + 1);
    }

    public static int binarySearch(int[] iArr, int i) {
        return binarySearch0(iArr, 0, iArr.length, i);
    }

    public static int binarySearch(int[] iArr, int i, int i2, int i3) {
        rangeCheck(iArr.length, i, i2);
        return binarySearch0(iArr, i, i2, i3);
    }

    private static int binarySearch0(int[] iArr, int i, int i2, int i3) {
        int i4 = i2 - 1;
        while (i <= i4) {
            int i5 = (i + i4) >>> 1;
            int i6 = iArr[i5];
            if (i6 < i3) {
                i = i5 + 1;
            } else if (i6 <= i3) {
                return i5;
            } else {
                i4 = i5 - 1;
            }
        }
        return -(i + 1);
    }

    public static int binarySearch(short[] sArr, short s) {
        return binarySearch0(sArr, 0, sArr.length, s);
    }

    public static int binarySearch(short[] sArr, int i, int i2, short s) {
        rangeCheck(sArr.length, i, i2);
        return binarySearch0(sArr, i, i2, s);
    }

    private static int binarySearch0(short[] sArr, int i, int i2, short s) {
        int i3 = i2 - 1;
        while (i <= i3) {
            int i4 = (i + i3) >>> 1;
            short s2 = sArr[i4];
            if (s2 < s) {
                i = i4 + 1;
            } else if (s2 <= s) {
                return i4;
            } else {
                i3 = i4 - 1;
            }
        }
        return -(i + 1);
    }

    public static int binarySearch(char[] cArr, char c) {
        return binarySearch0(cArr, 0, cArr.length, c);
    }

    public static int binarySearch(char[] cArr, int i, int i2, char c) {
        rangeCheck(cArr.length, i, i2);
        return binarySearch0(cArr, i, i2, c);
    }

    private static int binarySearch0(char[] cArr, int i, int i2, char c) {
        int i3 = i2 - 1;
        while (i <= i3) {
            int i4 = (i + i3) >>> 1;
            char c2 = cArr[i4];
            if (c2 < c) {
                i = i4 + 1;
            } else if (c2 <= c) {
                return i4;
            } else {
                i3 = i4 - 1;
            }
        }
        return -(i + 1);
    }

    public static int binarySearch(byte[] bArr, byte b) {
        return binarySearch0(bArr, 0, bArr.length, b);
    }

    public static int binarySearch(byte[] bArr, int i, int i2, byte b) {
        rangeCheck(bArr.length, i, i2);
        return binarySearch0(bArr, i, i2, b);
    }

    private static int binarySearch0(byte[] bArr, int i, int i2, byte b) {
        int i3 = i2 - 1;
        while (i <= i3) {
            int i4 = (i + i3) >>> 1;
            byte b2 = bArr[i4];
            if (b2 < b) {
                i = i4 + 1;
            } else if (b2 <= b) {
                return i4;
            } else {
                i3 = i4 - 1;
            }
        }
        return -(i + 1);
    }

    public static int binarySearch(double[] dArr, double d) {
        return binarySearch0(dArr, 0, dArr.length, d);
    }

    public static int binarySearch(double[] dArr, int i, int i2, double d) {
        rangeCheck(dArr.length, i, i2);
        return binarySearch0(dArr, i, i2, d);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0024, code lost:
        if (r7 < 0) goto L_0x0026;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int binarySearch0(double[] r8, int r9, int r10, double r11) {
        /*
            r0 = 1
            int r10 = r10 - r0
        L_0x0002:
            if (r9 > r10) goto L_0x0033
            int r1 = r9 + r10
            int r1 = r1 >>> r0
            r2 = r8[r1]
            r4 = -1
            int r5 = (r2 > r11 ? 1 : (r2 == r11 ? 0 : -1))
            if (r5 >= 0) goto L_0x000f
            goto L_0x0026
        L_0x000f:
            int r5 = (r2 > r11 ? 1 : (r2 == r11 ? 0 : -1))
            if (r5 <= 0) goto L_0x0015
        L_0x0013:
            r4 = 1
            goto L_0x0026
        L_0x0015:
            long r2 = java.lang.Double.doubleToLongBits(r2)
            long r5 = java.lang.Double.doubleToLongBits(r11)
            int r7 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r7 != 0) goto L_0x0024
            r2 = 0
            r4 = 0
            goto L_0x0026
        L_0x0024:
            if (r7 >= 0) goto L_0x0013
        L_0x0026:
            if (r4 >= 0) goto L_0x002c
            int r1 = r1 + 1
            r9 = r1
            goto L_0x0002
        L_0x002c:
            if (r4 <= 0) goto L_0x0032
            int r1 = r1 + -1
            r10 = r1
            goto L_0x0002
        L_0x0032:
            return r1
        L_0x0033:
            int r9 = r9 + r0
            int r8 = -r9
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.java.util.Arrays.binarySearch0(double[], int, int, double):int");
    }

    public static int binarySearch(float[] fArr, float f) {
        return binarySearch0(fArr, 0, fArr.length, f);
    }

    public static int binarySearch(float[] fArr, int i, int i2, float f) {
        rangeCheck(fArr.length, i, i2);
        return binarySearch0(fArr, i, i2, f);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0022, code lost:
        if (r2 < r4) goto L_0x0024;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int binarySearch0(float[] r5, int r6, int r7, float r8) {
        /*
            r0 = 1
            int r7 = r7 - r0
        L_0x0002:
            if (r6 > r7) goto L_0x0031
            int r1 = r6 + r7
            int r1 = r1 >>> r0
            r2 = r5[r1]
            r3 = -1
            int r4 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r4 >= 0) goto L_0x000f
            goto L_0x0024
        L_0x000f:
            int r4 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r4 <= 0) goto L_0x0015
        L_0x0013:
            r3 = 1
            goto L_0x0024
        L_0x0015:
            int r2 = java.lang.Float.floatToIntBits(r2)
            int r4 = java.lang.Float.floatToIntBits(r8)
            if (r2 != r4) goto L_0x0022
            r2 = 0
            r3 = 0
            goto L_0x0024
        L_0x0022:
            if (r2 >= r4) goto L_0x0013
        L_0x0024:
            if (r3 >= 0) goto L_0x002a
            int r1 = r1 + 1
            r6 = r1
            goto L_0x0002
        L_0x002a:
            if (r3 <= 0) goto L_0x0030
            int r1 = r1 + -1
            r7 = r1
            goto L_0x0002
        L_0x0030:
            return r1
        L_0x0031:
            int r6 = r6 + r0
            int r5 = -r6
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.java.util.Arrays.binarySearch0(float[], int, int, float):int");
    }

    public static int binarySearch(Object[] objArr, Object obj) {
        return binarySearch0(objArr, 0, objArr.length, obj);
    }

    public static int binarySearch(Object[] objArr, int i, int i2, Object obj) {
        rangeCheck(objArr.length, i, i2);
        return binarySearch0(objArr, i, i2, obj);
    }

    private static int binarySearch0(Object[] objArr, int i, int i2, Object obj) {
        int i3 = i2 - 1;
        while (i <= i3) {
            int i4 = (i + i3) >>> 1;
            int compareTo = objArr[i4].compareTo(obj);
            if (compareTo < 0) {
                i = i4 + 1;
            } else if (compareTo <= 0) {
                return i4;
            } else {
                i3 = i4 - 1;
            }
        }
        return -(i + 1);
    }

    public static <T> int binarySearch(T[] tArr, T t, Comparator<? super T> comparator) {
        return binarySearch0(tArr, 0, tArr.length, t, comparator);
    }

    public static <T> int binarySearch(T[] tArr, int i, int i2, T t, Comparator<? super T> comparator) {
        rangeCheck(tArr.length, i, i2);
        return binarySearch0(tArr, i, i2, t, comparator);
    }

    private static <T> int binarySearch0(T[] tArr, int i, int i2, T t, Comparator<? super T> comparator) {
        if (comparator == null) {
            return binarySearch0((Object[]) tArr, i, i2, (Object) t);
        }
        int i3 = i2 - 1;
        while (i <= i3) {
            int i4 = (i + i3) >>> 1;
            int compare = comparator.compare(tArr[i4], t);
            if (compare < 0) {
                i = i4 + 1;
            } else if (compare <= 0) {
                return i4;
            } else {
                i3 = i4 - 1;
            }
        }
        return -(i + 1);
    }

    public static boolean equals(long[] jArr, long[] jArr2) {
        int length;
        if (jArr == jArr2) {
            return true;
        }
        if (jArr == null || jArr2 == null || jArr2.length != (length = jArr.length)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (jArr[i] != jArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(int[] iArr, int[] iArr2) {
        int length;
        if (iArr == iArr2) {
            return true;
        }
        if (iArr == null || iArr2 == null || iArr2.length != (length = iArr.length)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (iArr[i] != iArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(short[] sArr, short[] sArr2) {
        int length;
        if (sArr == sArr2) {
            return true;
        }
        if (sArr == null || sArr2 == null || sArr2.length != (length = sArr.length)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (sArr[i] != sArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(char[] cArr, char[] cArr2) {
        int length;
        if (cArr == cArr2) {
            return true;
        }
        if (cArr == null || cArr2 == null || cArr2.length != (length = cArr.length)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (cArr[i] != cArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(byte[] bArr, byte[] bArr2) {
        int length;
        if (bArr == bArr2) {
            return true;
        }
        if (bArr == null || bArr2 == null || bArr2.length != (length = bArr.length)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(boolean[] zArr, boolean[] zArr2) {
        int length;
        if (zArr == zArr2) {
            return true;
        }
        if (zArr == null || zArr2 == null || zArr2.length != (length = zArr.length)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (zArr[i] != zArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(double[] dArr, double[] dArr2) {
        int length;
        if (dArr == dArr2) {
            return true;
        }
        if (dArr == null || dArr2 == null || dArr2.length != (length = dArr.length)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (Double.doubleToLongBits(dArr[i]) != Double.doubleToLongBits(dArr2[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(float[] fArr, float[] fArr2) {
        int length;
        if (fArr == fArr2) {
            return true;
        }
        if (fArr == null || fArr2 == null || fArr2.length != (length = fArr.length)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (Float.floatToIntBits(fArr[i]) != Float.floatToIntBits(fArr2[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(Object[] objArr, Object[] objArr2) {
        int length;
        if (objArr == objArr2) {
            return true;
        }
        if (objArr == null || objArr2 == null || objArr2.length != (length = objArr.length)) {
            return false;
        }
        int i = 0;
        while (i < length) {
            Object obj = objArr[i];
            Object obj2 = objArr2[i];
            if (obj == null) {
                if (obj2 == null) {
                    i++;
                }
            } else if (obj.equals(obj2)) {
                i++;
            }
            return false;
        }
        return true;
    }

    public static void fill(long[] jArr, long j) {
        fill(jArr, 0, jArr.length, j);
    }

    public static void fill(long[] jArr, int i, int i2, long j) {
        rangeCheck(jArr.length, i, i2);
        while (i < i2) {
            jArr[i] = j;
            i++;
        }
    }

    public static void fill(int[] iArr, int i) {
        fill(iArr, 0, iArr.length, i);
    }

    public static void fill(int[] iArr, int i, int i2, int i3) {
        rangeCheck(iArr.length, i, i2);
        while (i < i2) {
            iArr[i] = i3;
            i++;
        }
    }

    public static void fill(short[] sArr, short s) {
        fill(sArr, 0, sArr.length, s);
    }

    public static void fill(short[] sArr, int i, int i2, short s) {
        rangeCheck(sArr.length, i, i2);
        while (i < i2) {
            sArr[i] = s;
            i++;
        }
    }

    public static void fill(char[] cArr, char c) {
        fill(cArr, 0, cArr.length, c);
    }

    public static void fill(char[] cArr, int i, int i2, char c) {
        rangeCheck(cArr.length, i, i2);
        while (i < i2) {
            cArr[i] = c;
            i++;
        }
    }

    public static void fill(byte[] bArr, byte b) {
        fill(bArr, 0, bArr.length, b);
    }

    public static void fill(byte[] bArr, int i, int i2, byte b) {
        rangeCheck(bArr.length, i, i2);
        while (i < i2) {
            bArr[i] = b;
            i++;
        }
    }

    public static void fill(boolean[] zArr, boolean z) {
        fill(zArr, 0, zArr.length, z);
    }

    public static void fill(boolean[] zArr, int i, int i2, boolean z) {
        rangeCheck(zArr.length, i, i2);
        while (i < i2) {
            zArr[i] = z;
            i++;
        }
    }

    public static void fill(double[] dArr, double d) {
        fill(dArr, 0, dArr.length, d);
    }

    public static void fill(double[] dArr, int i, int i2, double d) {
        rangeCheck(dArr.length, i, i2);
        while (i < i2) {
            dArr[i] = d;
            i++;
        }
    }

    public static void fill(float[] fArr, float f) {
        fill(fArr, 0, fArr.length, f);
    }

    public static void fill(float[] fArr, int i, int i2, float f) {
        rangeCheck(fArr.length, i, i2);
        while (i < i2) {
            fArr[i] = f;
            i++;
        }
    }

    public static void fill(Object[] objArr, Object obj) {
        fill(objArr, 0, objArr.length, obj);
    }

    public static void fill(Object[] objArr, int i, int i2, Object obj) {
        rangeCheck(objArr.length, i, i2);
        while (i < i2) {
            objArr[i] = obj;
            i++;
        }
    }

    public static <T> T[] copyOf(T[] tArr, int i) {
        return copyOf(tArr, i, tArr.getClass());
    }

    public static <T, U> T[] copyOf(U[] uArr, int i, Class<? extends T[]> cls) {
        T[] tArr;
        if (cls == Object[].class) {
            tArr = new Object[i];
        } else {
            tArr = (Object[]) Array.newInstance(cls.getComponentType(), i);
        }
        System.arraycopy(uArr, 0, tArr, 0, Math.min(uArr.length, i));
        return tArr;
    }

    public static byte[] copyOf(byte[] bArr, int i) {
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, Math.min(bArr.length, i));
        return bArr2;
    }

    public static short[] copyOf(short[] sArr, int i) {
        short[] sArr2 = new short[i];
        System.arraycopy(sArr, 0, sArr2, 0, Math.min(sArr.length, i));
        return sArr2;
    }

    public static int[] copyOf(int[] iArr, int i) {
        int[] iArr2 = new int[i];
        System.arraycopy(iArr, 0, iArr2, 0, Math.min(iArr.length, i));
        return iArr2;
    }

    public static long[] copyOf(long[] jArr, int i) {
        long[] jArr2 = new long[i];
        System.arraycopy(jArr, 0, jArr2, 0, Math.min(jArr.length, i));
        return jArr2;
    }

    public static char[] copyOf(char[] cArr, int i) {
        char[] cArr2 = new char[i];
        System.arraycopy(cArr, 0, cArr2, 0, Math.min(cArr.length, i));
        return cArr2;
    }

    public static float[] copyOf(float[] fArr, int i) {
        float[] fArr2 = new float[i];
        System.arraycopy(fArr, 0, fArr2, 0, Math.min(fArr.length, i));
        return fArr2;
    }

    public static double[] copyOf(double[] dArr, int i) {
        double[] dArr2 = new double[i];
        System.arraycopy(dArr, 0, dArr2, 0, Math.min(dArr.length, i));
        return dArr2;
    }

    public static boolean[] copyOf(boolean[] zArr, int i) {
        boolean[] zArr2 = new boolean[i];
        System.arraycopy(zArr, 0, zArr2, 0, Math.min(zArr.length, i));
        return zArr2;
    }

    public static <T> T[] copyOfRange(T[] tArr, int i, int i2) {
        return copyOfRange(tArr, i, i2, tArr.getClass());
    }

    public static <T, U> T[] copyOfRange(U[] uArr, int i, int i2, Class<? extends T[]> cls) {
        T[] tArr;
        int i3 = i2 - i;
        if (i3 >= 0) {
            if (cls == Object[].class) {
                tArr = new Object[i3];
            } else {
                tArr = (Object[]) Array.newInstance(cls.getComponentType(), i3);
            }
            System.arraycopy(uArr, i, tArr, 0, Math.min(uArr.length - i, i3));
            return tArr;
        }
        throw new IllegalArgumentException(i + " > " + i2);
    }

    public static byte[] copyOfRange(byte[] bArr, int i, int i2) {
        int i3 = i2 - i;
        if (i3 >= 0) {
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArr, i, bArr2, 0, Math.min(bArr.length - i, i3));
            return bArr2;
        }
        throw new IllegalArgumentException(i + " > " + i2);
    }

    public static short[] copyOfRange(short[] sArr, int i, int i2) {
        int i3 = i2 - i;
        if (i3 >= 0) {
            short[] sArr2 = new short[i3];
            System.arraycopy(sArr, i, sArr2, 0, Math.min(sArr.length - i, i3));
            return sArr2;
        }
        throw new IllegalArgumentException(i + " > " + i2);
    }

    public static int[] copyOfRange(int[] iArr, int i, int i2) {
        int i3 = i2 - i;
        if (i3 >= 0) {
            int[] iArr2 = new int[i3];
            System.arraycopy(iArr, i, iArr2, 0, Math.min(iArr.length - i, i3));
            return iArr2;
        }
        throw new IllegalArgumentException(i + " > " + i2);
    }

    public static long[] copyOfRange(long[] jArr, int i, int i2) {
        int i3 = i2 - i;
        if (i3 >= 0) {
            long[] jArr2 = new long[i3];
            System.arraycopy(jArr, i, jArr2, 0, Math.min(jArr.length - i, i3));
            return jArr2;
        }
        throw new IllegalArgumentException(i + " > " + i2);
    }

    public static char[] copyOfRange(char[] cArr, int i, int i2) {
        int i3 = i2 - i;
        if (i3 >= 0) {
            char[] cArr2 = new char[i3];
            System.arraycopy(cArr, i, cArr2, 0, Math.min(cArr.length - i, i3));
            return cArr2;
        }
        throw new IllegalArgumentException(i + " > " + i2);
    }

    public static float[] copyOfRange(float[] fArr, int i, int i2) {
        int i3 = i2 - i;
        if (i3 >= 0) {
            float[] fArr2 = new float[i3];
            System.arraycopy(fArr, i, fArr2, 0, Math.min(fArr.length - i, i3));
            return fArr2;
        }
        throw new IllegalArgumentException(i + " > " + i2);
    }

    public static double[] copyOfRange(double[] dArr, int i, int i2) {
        int i3 = i2 - i;
        if (i3 >= 0) {
            double[] dArr2 = new double[i3];
            System.arraycopy(dArr, i, dArr2, 0, Math.min(dArr.length - i, i3));
            return dArr2;
        }
        throw new IllegalArgumentException(i + " > " + i2);
    }

    public static boolean[] copyOfRange(boolean[] zArr, int i, int i2) {
        int i3 = i2 - i;
        if (i3 >= 0) {
            boolean[] zArr2 = new boolean[i3];
            System.arraycopy(zArr, i, zArr2, 0, Math.min(zArr.length - i, i3));
            return zArr2;
        }
        throw new IllegalArgumentException(i + " > " + i2);
    }

    public static <T> List<T> asList(T... tArr) {
        return new ArrayList(tArr);
    }

    private static class ArrayList<E> extends AbstractList<E> implements RandomAccess, Serializable {
        private static final long serialVersionUID = -2764017481108945198L;
        private final E[] a;

        ArrayList(E[] eArr) {
            Objects.requireNonNull(eArr);
            this.a = eArr;
        }

        public int size() {
            return this.a.length;
        }

        public Object[] toArray() {
            return (Object[]) this.a.clone();
        }

        public <T> T[] toArray(T[] tArr) {
            int size = size();
            if (tArr.length < size) {
                return Arrays.copyOf(this.a, size, tArr.getClass());
            }
            System.arraycopy(this.a, 0, tArr, 0, size);
            if (tArr.length > size) {
                tArr[size] = null;
            }
            return tArr;
        }

        public E get(int i) {
            return this.a[i];
        }

        public E set(int i, E e) {
            E[] eArr = this.a;
            E e2 = eArr[i];
            eArr[i] = e;
            return e2;
        }

        public int indexOf(Object obj) {
            int i = 0;
            if (obj == null) {
                while (true) {
                    E[] eArr = this.a;
                    if (i >= eArr.length) {
                        return -1;
                    }
                    if (eArr[i] == null) {
                        return i;
                    }
                    i++;
                }
            } else {
                while (true) {
                    E[] eArr2 = this.a;
                    if (i >= eArr2.length) {
                        return -1;
                    }
                    if (obj.equals(eArr2[i])) {
                        return i;
                    }
                    i++;
                }
            }
        }

        public boolean contains(Object obj) {
            return indexOf(obj) != -1;
        }
    }

    public static int hashCode(long[] jArr) {
        if (jArr == null) {
            return 0;
        }
        int i = 1;
        for (long j : jArr) {
            i = (i * 31) + ((int) (j ^ (j >>> 32)));
        }
        return i;
    }

    public static int hashCode(int[] iArr) {
        if (iArr == null) {
            return 0;
        }
        int i = 1;
        for (int i2 : iArr) {
            i = (i * 31) + i2;
        }
        return i;
    }

    public static int hashCode(short[] sArr) {
        if (sArr == null) {
            return 0;
        }
        int i = 1;
        for (short s : sArr) {
            i = (i * 31) + s;
        }
        return i;
    }

    public static int hashCode(char[] cArr) {
        if (cArr == null) {
            return 0;
        }
        int i = 1;
        for (char c : cArr) {
            i = (i * 31) + c;
        }
        return i;
    }

    public static int hashCode(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        int i = 1;
        for (byte b : bArr) {
            i = (i * 31) + b;
        }
        return i;
    }

    public static int hashCode(boolean[] zArr) {
        if (zArr == null) {
            return 0;
        }
        int length = zArr.length;
        int i = 1;
        for (int i2 = 0; i2 < length; i2++) {
            i = (i * 31) + (zArr[i2] ? 1231 : 1237);
        }
        return i;
    }

    public static int hashCode(float[] fArr) {
        if (fArr == null) {
            return 0;
        }
        int i = 1;
        for (float floatToIntBits : fArr) {
            i = (i * 31) + Float.floatToIntBits(floatToIntBits);
        }
        return i;
    }

    public static int hashCode(double[] dArr) {
        if (dArr == null) {
            return 0;
        }
        int i = 1;
        for (double doubleToLongBits : dArr) {
            long doubleToLongBits2 = Double.doubleToLongBits(doubleToLongBits);
            i = (i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
        }
        return i;
    }

    public static int hashCode(Object[] objArr) {
        int i;
        if (objArr == null) {
            return 0;
        }
        int i2 = 1;
        for (Object obj : objArr) {
            int i3 = i2 * 31;
            if (obj == null) {
                i = 0;
            } else {
                i = obj.hashCode();
            }
            i2 = i3 + i;
        }
        return i2;
    }

    public static int deepHashCode(Object[] objArr) {
        int i;
        if (objArr == null) {
            return 0;
        }
        int length = objArr.length;
        int i2 = 1;
        for (int i3 = 0; i3 < length; i3++) {
            Object[] objArr2 = objArr[i3];
            if (objArr2 instanceof Object[]) {
                i = deepHashCode(objArr2);
            } else if (objArr2 instanceof byte[]) {
                i = hashCode((byte[]) objArr2);
            } else if (objArr2 instanceof short[]) {
                i = hashCode((short[]) objArr2);
            } else if (objArr2 instanceof int[]) {
                i = hashCode((int[]) objArr2);
            } else if (objArr2 instanceof long[]) {
                i = hashCode((long[]) objArr2);
            } else if (objArr2 instanceof char[]) {
                i = hashCode((char[]) objArr2);
            } else if (objArr2 instanceof float[]) {
                i = hashCode((float[]) objArr2);
            } else if (objArr2 instanceof double[]) {
                i = hashCode((double[]) objArr2);
            } else if (objArr2 instanceof boolean[]) {
                i = hashCode((boolean[]) objArr2);
            } else {
                i = objArr2 != null ? objArr2.hashCode() : 0;
            }
            i2 = (i2 * 31) + i;
        }
        return i2;
    }

    public static boolean deepEquals(Object[] objArr, Object[] objArr2) {
        int length;
        boolean z;
        if (objArr == objArr2) {
            return true;
        }
        if (objArr == null || objArr2 == null || objArr2.length != (length = objArr.length)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            Object[] objArr3 = objArr[i];
            Object[] objArr4 = objArr2[i];
            if (objArr3 != objArr4) {
                if (objArr3 == null) {
                    return false;
                }
                if ((objArr3 instanceof Object[]) && (objArr4 instanceof Object[])) {
                    z = deepEquals(objArr3, objArr4);
                } else if ((objArr3 instanceof byte[]) && (objArr4 instanceof byte[])) {
                    z = equals((byte[]) objArr3, (byte[]) objArr4);
                } else if ((objArr3 instanceof short[]) && (objArr4 instanceof short[])) {
                    z = equals((short[]) objArr3, (short[]) objArr4);
                } else if ((objArr3 instanceof int[]) && (objArr4 instanceof int[])) {
                    z = equals((int[]) objArr3, (int[]) objArr4);
                } else if ((objArr3 instanceof long[]) && (objArr4 instanceof long[])) {
                    z = equals((long[]) objArr3, (long[]) objArr4);
                } else if ((objArr3 instanceof char[]) && (objArr4 instanceof char[])) {
                    z = equals((char[]) objArr3, (char[]) objArr4);
                } else if ((objArr3 instanceof float[]) && (objArr4 instanceof float[])) {
                    z = equals((float[]) objArr3, (float[]) objArr4);
                } else if ((objArr3 instanceof double[]) && (objArr4 instanceof double[])) {
                    z = equals((double[]) objArr3, (double[]) objArr4);
                } else if (!(objArr3 instanceof boolean[]) || !(objArr4 instanceof boolean[])) {
                    z = objArr3.equals(objArr4);
                } else {
                    z = equals((boolean[]) objArr3, (boolean[]) objArr4);
                }
                if (!z) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String toString(long[] jArr) {
        if (jArr == null) {
            return "null";
        }
        int length = jArr.length - 1;
        if (length == -1) {
            return XMPConst.ARRAY_ITEM_NAME;
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int i = 0;
        while (true) {
            sb.append(jArr[i]);
            if (i == length) {
                sb.append(']');
                return sb.toString();
            }
            sb.append(", ");
            i++;
        }
    }

    public static String toString(int[] iArr) {
        if (iArr == null) {
            return "null";
        }
        int length = iArr.length - 1;
        if (length == -1) {
            return XMPConst.ARRAY_ITEM_NAME;
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int i = 0;
        while (true) {
            sb.append(iArr[i]);
            if (i == length) {
                sb.append(']');
                return sb.toString();
            }
            sb.append(", ");
            i++;
        }
    }

    public static String toString(short[] sArr) {
        if (sArr == null) {
            return "null";
        }
        int length = sArr.length - 1;
        if (length == -1) {
            return XMPConst.ARRAY_ITEM_NAME;
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int i = 0;
        while (true) {
            sb.append(sArr[i]);
            if (i == length) {
                sb.append(']');
                return sb.toString();
            }
            sb.append(", ");
            i++;
        }
    }

    public static String toString(char[] cArr) {
        if (cArr == null) {
            return "null";
        }
        int length = cArr.length - 1;
        if (length == -1) {
            return XMPConst.ARRAY_ITEM_NAME;
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int i = 0;
        while (true) {
            sb.append(cArr[i]);
            if (i == length) {
                sb.append(']');
                return sb.toString();
            }
            sb.append(", ");
            i++;
        }
    }

    public static String toString(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        int length = bArr.length - 1;
        if (length == -1) {
            return XMPConst.ARRAY_ITEM_NAME;
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int i = 0;
        while (true) {
            sb.append(bArr[i]);
            if (i == length) {
                sb.append(']');
                return sb.toString();
            }
            sb.append(", ");
            i++;
        }
    }

    public static String toString(boolean[] zArr) {
        if (zArr == null) {
            return "null";
        }
        int length = zArr.length - 1;
        if (length == -1) {
            return XMPConst.ARRAY_ITEM_NAME;
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int i = 0;
        while (true) {
            sb.append(zArr[i]);
            if (i == length) {
                sb.append(']');
                return sb.toString();
            }
            sb.append(", ");
            i++;
        }
    }

    public static String toString(float[] fArr) {
        if (fArr == null) {
            return "null";
        }
        int length = fArr.length - 1;
        if (length == -1) {
            return XMPConst.ARRAY_ITEM_NAME;
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int i = 0;
        while (true) {
            sb.append(fArr[i]);
            if (i == length) {
                sb.append(']');
                return sb.toString();
            }
            sb.append(", ");
            i++;
        }
    }

    public static String toString(double[] dArr) {
        if (dArr == null) {
            return "null";
        }
        int length = dArr.length - 1;
        if (length == -1) {
            return XMPConst.ARRAY_ITEM_NAME;
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int i = 0;
        while (true) {
            sb.append(dArr[i]);
            if (i == length) {
                sb.append(']');
                return sb.toString();
            }
            sb.append(", ");
            i++;
        }
    }

    public static String toString(Object[] objArr) {
        if (objArr == null) {
            return "null";
        }
        int length = objArr.length - 1;
        if (length == -1) {
            return XMPConst.ARRAY_ITEM_NAME;
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int i = 0;
        while (true) {
            sb.append(String.valueOf(objArr[i]));
            if (i == length) {
                sb.append(']');
                return sb.toString();
            }
            sb.append(", ");
            i++;
        }
    }

    public static String deepToString(Object[] objArr) {
        if (objArr == null) {
            return "null";
        }
        int length = objArr.length * 20;
        if (objArr.length != 0 && length <= 0) {
            length = Integer.MAX_VALUE;
        }
        StringBuilder sb = new StringBuilder(length);
        deepToString(objArr, sb, new HashSet());
        return sb.toString();
    }

    private static void deepToString(Object[] objArr, StringBuilder sb, Set<Object[]> set) {
        if (objArr == null) {
            sb.append("null");
            return;
        }
        set.add(objArr);
        sb.append('[');
        for (int i = 0; i < objArr.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            boolean[] zArr = objArr[i];
            if (zArr == null) {
                sb.append("null");
            } else {
                Class<?> cls = zArr.getClass();
                if (!cls.isArray()) {
                    sb.append(zArr.toString());
                } else if (cls == byte[].class) {
                    sb.append(toString((byte[]) zArr));
                } else if (cls == short[].class) {
                    sb.append(toString((short[]) zArr));
                } else if (cls == int[].class) {
                    sb.append(toString((int[]) zArr));
                } else if (cls == long[].class) {
                    sb.append(toString((long[]) zArr));
                } else if (cls == char[].class) {
                    sb.append(toString((char[]) zArr));
                } else if (cls == float[].class) {
                    sb.append(toString((float[]) zArr));
                } else if (cls == double[].class) {
                    sb.append(toString((double[]) zArr));
                } else if (cls == boolean[].class) {
                    sb.append(toString(zArr));
                } else if (set.contains(zArr)) {
                    sb.append("[...]");
                } else {
                    deepToString((Object[]) zArr, sb, set);
                }
            }
        }
        sb.append(']');
        set.remove(objArr);
    }
}
