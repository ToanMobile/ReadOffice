package com.app.office.fc.hssf.record.cf;

import com.app.office.fc.ss.util.HSSFCellRangeAddress;
import java.util.ArrayList;
import java.util.List;

public final class CellRangeUtil {
    public static final int ENCLOSES = 4;
    public static final int INSIDE = 3;
    public static final int NO_INTERSECTION = 1;
    public static final int OVERLAP = 2;

    private static boolean lt(int i, int i2) {
        return i != -1 && (i2 == -1 || i < i2);
    }

    private CellRangeUtil() {
    }

    public static int intersect(HSSFCellRangeAddress hSSFCellRangeAddress, HSSFCellRangeAddress hSSFCellRangeAddress2) {
        int firstRow = hSSFCellRangeAddress2.getFirstRow();
        int lastRow = hSSFCellRangeAddress2.getLastRow();
        int firstColumn = hSSFCellRangeAddress2.getFirstColumn();
        int lastColumn = hSSFCellRangeAddress2.getLastColumn();
        if (gt(hSSFCellRangeAddress.getFirstRow(), lastRow) || lt(hSSFCellRangeAddress.getLastRow(), firstRow) || gt(hSSFCellRangeAddress.getFirstColumn(), lastColumn) || lt(hSSFCellRangeAddress.getLastColumn(), firstColumn)) {
            return 1;
        }
        if (contains(hSSFCellRangeAddress, hSSFCellRangeAddress2)) {
            return 3;
        }
        return contains(hSSFCellRangeAddress2, hSSFCellRangeAddress) ? 4 : 2;
    }

    public static HSSFCellRangeAddress[] mergeCellRanges(HSSFCellRangeAddress[] hSSFCellRangeAddressArr) {
        if (hSSFCellRangeAddressArr.length < 1) {
            return hSSFCellRangeAddressArr;
        }
        ArrayList arrayList = new ArrayList();
        for (HSSFCellRangeAddress add : hSSFCellRangeAddressArr) {
            arrayList.add(add);
        }
        return toArray(mergeCellRanges((List) arrayList));
    }

    private static List mergeCellRanges(List list) {
        int i;
        while (list.size() > 1) {
            int i2 = 0;
            boolean z = false;
            while (i2 < list.size()) {
                HSSFCellRangeAddress hSSFCellRangeAddress = (HSSFCellRangeAddress) list.get(i2);
                int i3 = i2 + 1;
                int i4 = i3;
                while (i < list.size()) {
                    HSSFCellRangeAddress[] mergeRanges = mergeRanges(hSSFCellRangeAddress, (HSSFCellRangeAddress) list.get(i));
                    if (mergeRanges != null) {
                        list.set(i2, mergeRanges[0]);
                        list.remove(i);
                        i--;
                        for (int i5 = 1; i5 < mergeRanges.length; i5++) {
                            i++;
                            list.add(i, mergeRanges[i5]);
                        }
                        z = true;
                    }
                    i4 = i + 1;
                }
                i2 = i3;
            }
            if (!z) {
                break;
            }
        }
        return list;
    }

    private static HSSFCellRangeAddress[] mergeRanges(HSSFCellRangeAddress hSSFCellRangeAddress, HSSFCellRangeAddress hSSFCellRangeAddress2) {
        int intersect = intersect(hSSFCellRangeAddress, hSSFCellRangeAddress2);
        if (intersect != 1) {
            if (intersect == 2) {
                return resolveRangeOverlap(hSSFCellRangeAddress, hSSFCellRangeAddress2);
            }
            if (intersect == 3) {
                return new HSSFCellRangeAddress[]{hSSFCellRangeAddress};
            } else if (intersect == 4) {
                return new HSSFCellRangeAddress[]{hSSFCellRangeAddress2};
            } else {
                throw new RuntimeException("unexpected intersection result (" + intersect + ")");
            }
        } else if (!hasExactSharedBorder(hSSFCellRangeAddress, hSSFCellRangeAddress2)) {
            return null;
        } else {
            return new HSSFCellRangeAddress[]{createEnclosingCellRange(hSSFCellRangeAddress, hSSFCellRangeAddress2)};
        }
    }

    static HSSFCellRangeAddress[] resolveRangeOverlap(HSSFCellRangeAddress hSSFCellRangeAddress, HSSFCellRangeAddress hSSFCellRangeAddress2) {
        if (hSSFCellRangeAddress.isFullColumnRange()) {
            if (hSSFCellRangeAddress.isFullRowRange()) {
                return null;
            }
            return sliceUp(hSSFCellRangeAddress, hSSFCellRangeAddress2);
        } else if (hSSFCellRangeAddress.isFullRowRange()) {
            if (hSSFCellRangeAddress2.isFullColumnRange()) {
                return null;
            }
            return sliceUp(hSSFCellRangeAddress, hSSFCellRangeAddress2);
        } else if (hSSFCellRangeAddress2.isFullColumnRange()) {
            return sliceUp(hSSFCellRangeAddress2, hSSFCellRangeAddress);
        } else {
            if (hSSFCellRangeAddress2.isFullRowRange()) {
                return sliceUp(hSSFCellRangeAddress2, hSSFCellRangeAddress);
            }
            return sliceUp(hSSFCellRangeAddress, hSSFCellRangeAddress2);
        }
    }

    private static HSSFCellRangeAddress[] sliceUp(HSSFCellRangeAddress hSSFCellRangeAddress, HSSFCellRangeAddress hSSFCellRangeAddress2) {
        List arrayList = new ArrayList();
        arrayList.add(hSSFCellRangeAddress2);
        if (!hSSFCellRangeAddress.isFullColumnRange()) {
            arrayList = cutHorizontally(hSSFCellRangeAddress.getLastRow() + 1, cutHorizontally(hSSFCellRangeAddress.getFirstRow(), arrayList));
        }
        if (!hSSFCellRangeAddress.isFullRowRange()) {
            arrayList = cutVertically(hSSFCellRangeAddress.getLastColumn() + 1, cutVertically(hSSFCellRangeAddress.getFirstColumn(), arrayList));
        }
        HSSFCellRangeAddress[] array = toArray(arrayList);
        arrayList.clear();
        arrayList.add(hSSFCellRangeAddress);
        for (HSSFCellRangeAddress hSSFCellRangeAddress3 : array) {
            if (intersect(hSSFCellRangeAddress, hSSFCellRangeAddress3) != 4) {
                arrayList.add(hSSFCellRangeAddress3);
            }
        }
        return toArray(arrayList);
    }

    private static List cutHorizontally(int i, List list) {
        ArrayList arrayList = new ArrayList();
        HSSFCellRangeAddress[] array = toArray(list);
        for (HSSFCellRangeAddress hSSFCellRangeAddress : array) {
            if (hSSFCellRangeAddress.getFirstRow() >= i || i >= hSSFCellRangeAddress.getLastRow()) {
                arrayList.add(hSSFCellRangeAddress);
            } else {
                arrayList.add(new HSSFCellRangeAddress(hSSFCellRangeAddress.getFirstRow(), i, hSSFCellRangeAddress.getFirstColumn(), hSSFCellRangeAddress.getLastColumn()));
                arrayList.add(new HSSFCellRangeAddress(i + 1, hSSFCellRangeAddress.getLastRow(), hSSFCellRangeAddress.getFirstColumn(), hSSFCellRangeAddress.getLastColumn()));
            }
        }
        return arrayList;
    }

    private static List cutVertically(int i, List list) {
        ArrayList arrayList = new ArrayList();
        HSSFCellRangeAddress[] array = toArray(list);
        for (HSSFCellRangeAddress hSSFCellRangeAddress : array) {
            if (hSSFCellRangeAddress.getFirstColumn() >= i || i >= hSSFCellRangeAddress.getLastColumn()) {
                arrayList.add(hSSFCellRangeAddress);
            } else {
                arrayList.add(new HSSFCellRangeAddress(hSSFCellRangeAddress.getFirstRow(), hSSFCellRangeAddress.getLastRow(), hSSFCellRangeAddress.getFirstColumn(), i));
                arrayList.add(new HSSFCellRangeAddress(hSSFCellRangeAddress.getFirstRow(), hSSFCellRangeAddress.getLastRow(), i + 1, hSSFCellRangeAddress.getLastColumn()));
            }
        }
        return arrayList;
    }

    private static HSSFCellRangeAddress[] toArray(List list) {
        HSSFCellRangeAddress[] hSSFCellRangeAddressArr = new HSSFCellRangeAddress[list.size()];
        list.toArray(hSSFCellRangeAddressArr);
        return hSSFCellRangeAddressArr;
    }

    public static boolean contains(HSSFCellRangeAddress hSSFCellRangeAddress, HSSFCellRangeAddress hSSFCellRangeAddress2) {
        return le(hSSFCellRangeAddress.getFirstRow(), hSSFCellRangeAddress2.getFirstRow()) && ge(hSSFCellRangeAddress.getLastRow(), hSSFCellRangeAddress2.getLastRow()) && le(hSSFCellRangeAddress.getFirstColumn(), hSSFCellRangeAddress2.getFirstColumn()) && ge(hSSFCellRangeAddress.getLastColumn(), hSSFCellRangeAddress2.getLastColumn());
    }

    public static boolean hasExactSharedBorder(HSSFCellRangeAddress hSSFCellRangeAddress, HSSFCellRangeAddress hSSFCellRangeAddress2) {
        int firstRow = hSSFCellRangeAddress2.getFirstRow();
        int lastRow = hSSFCellRangeAddress2.getLastRow();
        int firstColumn = hSSFCellRangeAddress2.getFirstColumn();
        int lastColumn = hSSFCellRangeAddress2.getLastColumn();
        if ((hSSFCellRangeAddress.getFirstRow() <= 0 || hSSFCellRangeAddress.getFirstRow() - 1 != lastRow) && (firstRow <= 0 || firstRow - 1 != hSSFCellRangeAddress.getLastRow())) {
            if (((hSSFCellRangeAddress.getFirstColumn() > 0 && hSSFCellRangeAddress.getFirstColumn() - 1 == lastColumn) || (firstColumn > 0 && hSSFCellRangeAddress.getLastColumn() == firstColumn - 1)) && hSSFCellRangeAddress.getFirstRow() == firstRow && hSSFCellRangeAddress.getLastRow() == lastRow) {
                return true;
            }
            return false;
        } else if (hSSFCellRangeAddress.getFirstColumn() == firstColumn && hSSFCellRangeAddress.getLastColumn() == lastColumn) {
            return true;
        } else {
            return false;
        }
    }

    public static HSSFCellRangeAddress createEnclosingCellRange(HSSFCellRangeAddress hSSFCellRangeAddress, HSSFCellRangeAddress hSSFCellRangeAddress2) {
        if (hSSFCellRangeAddress2 == null) {
            return hSSFCellRangeAddress.copy();
        }
        return new HSSFCellRangeAddress(lt(hSSFCellRangeAddress2.getFirstRow(), hSSFCellRangeAddress.getFirstRow()) ? hSSFCellRangeAddress2.getFirstRow() : hSSFCellRangeAddress.getFirstRow(), gt(hSSFCellRangeAddress2.getLastRow(), hSSFCellRangeAddress.getLastRow()) ? hSSFCellRangeAddress2.getLastRow() : hSSFCellRangeAddress.getLastRow(), lt(hSSFCellRangeAddress2.getFirstColumn(), hSSFCellRangeAddress.getFirstColumn()) ? hSSFCellRangeAddress2.getFirstColumn() : hSSFCellRangeAddress.getFirstColumn(), gt(hSSFCellRangeAddress2.getLastColumn(), hSSFCellRangeAddress.getLastColumn()) ? hSSFCellRangeAddress2.getLastColumn() : hSSFCellRangeAddress.getLastColumn());
    }

    private static boolean le(int i, int i2) {
        return i == i2 || lt(i, i2);
    }

    private static boolean gt(int i, int i2) {
        return lt(i2, i);
    }

    private static boolean ge(int i, int i2) {
        return !lt(i, i2);
    }
}
