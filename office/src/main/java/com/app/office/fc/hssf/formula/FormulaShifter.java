package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.ptg.Area2DPtgBase;
import com.app.office.fc.hssf.formula.ptg.Area3DPtg;
import com.app.office.fc.hssf.formula.ptg.AreaErrPtg;
import com.app.office.fc.hssf.formula.ptg.AreaPtg;
import com.app.office.fc.hssf.formula.ptg.AreaPtgBase;
import com.app.office.fc.hssf.formula.ptg.DeletedArea3DPtg;
import com.app.office.fc.hssf.formula.ptg.DeletedRef3DPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.formula.ptg.Ref3DPtg;
import com.app.office.fc.hssf.formula.ptg.RefErrorPtg;
import com.app.office.fc.hssf.formula.ptg.RefPtg;
import com.app.office.fc.hssf.formula.ptg.RefPtgBase;

public final class FormulaShifter {
    private final int _amountToMove;
    private final int _dstSheetIndex;
    private final int _externSheetIndex;
    private final int _firstMovedIndex;
    private final int _lastMovedIndex;
    private final ShiftMode _mode;
    private final int _srcSheetIndex;

    enum ShiftMode {
        Row,
        Sheet
    }

    private FormulaShifter(int i, int i2, int i3, int i4) {
        if (i4 == 0) {
            throw new IllegalArgumentException("amountToMove must not be zero");
        } else if (i2 <= i3) {
            this._externSheetIndex = i;
            this._firstMovedIndex = i2;
            this._lastMovedIndex = i3;
            this._amountToMove = i4;
            this._mode = ShiftMode.Row;
            this._dstSheetIndex = -1;
            this._srcSheetIndex = -1;
        } else {
            throw new IllegalArgumentException("firstMovedIndex, lastMovedIndex out of order");
        }
    }

    private FormulaShifter(int i, int i2) {
        this._amountToMove = -1;
        this._lastMovedIndex = -1;
        this._firstMovedIndex = -1;
        this._externSheetIndex = -1;
        this._srcSheetIndex = i;
        this._dstSheetIndex = i2;
        this._mode = ShiftMode.Sheet;
    }

    public static FormulaShifter createForRowShift(int i, int i2, int i3, int i4) {
        return new FormulaShifter(i, i2, i3, i4);
    }

    public static FormulaShifter createForSheetShift(int i, int i2) {
        return new FormulaShifter(i, i2);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [");
        stringBuffer.append(this._firstMovedIndex);
        stringBuffer.append(this._lastMovedIndex);
        stringBuffer.append(this._amountToMove);
        return stringBuffer.toString();
    }

    public boolean adjustFormula(Ptg[] ptgArr, int i) {
        boolean z = false;
        for (int i2 = 0; i2 < ptgArr.length; i2++) {
            Ptg adjustPtg = adjustPtg(ptgArr[i2], i);
            if (adjustPtg != null) {
                ptgArr[i2] = adjustPtg;
                z = true;
            }
        }
        return z;
    }

    /* renamed from: com.app.office.fc.hssf.formula.FormulaShifter$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$reader$office$fc$hssf$formula$FormulaShifter$ShiftMode;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        static {
            /*
                com.app.office.fc.hssf.formula.FormulaShifter$ShiftMode[] r0 = com.app.office.fc.hssf.formula.FormulaShifter.ShiftMode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$reader$office$fc$hssf$formula$FormulaShifter$ShiftMode = r0
                com.app.office.fc.hssf.formula.FormulaShifter$ShiftMode r1 = com.app.office.fc.hssf.formula.FormulaShifter.ShiftMode.Row     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$reader$office$fc$hssf$formula$FormulaShifter$ShiftMode     // Catch:{ NoSuchFieldError -> 0x001d }
                com.app.office.fc.hssf.formula.FormulaShifter$ShiftMode r1 = com.app.office.fc.hssf.formula.FormulaShifter.ShiftMode.Sheet     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.formula.FormulaShifter.AnonymousClass1.<clinit>():void");
        }
    }

    private Ptg adjustPtg(Ptg ptg, int i) {
        int i2 = AnonymousClass1.$SwitchMap$com$reader$office$fc$hssf$formula$FormulaShifter$ShiftMode[this._mode.ordinal()];
        if (i2 == 1) {
            return adjustPtgDueToRowMove(ptg, i);
        }
        if (i2 == 2) {
            return adjustPtgDueToShiftMove(ptg);
        }
        throw new IllegalStateException("Unsupported shift mode: " + this._mode);
    }

    private Ptg adjustPtgDueToRowMove(Ptg ptg, int i) {
        if (ptg instanceof RefPtg) {
            if (i != this._externSheetIndex) {
                return null;
            }
            return rowMoveRefPtg((RefPtg) ptg);
        } else if (ptg instanceof Ref3DPtg) {
            Ref3DPtg ref3DPtg = (Ref3DPtg) ptg;
            if (this._externSheetIndex != ref3DPtg.getExternSheetIndex()) {
                return null;
            }
            return rowMoveRefPtg(ref3DPtg);
        } else if (ptg instanceof Area2DPtgBase) {
            if (i != this._externSheetIndex) {
                return ptg;
            }
            return rowMoveAreaPtg((Area2DPtgBase) ptg);
        } else if (!(ptg instanceof Area3DPtg)) {
            return null;
        } else {
            Area3DPtg area3DPtg = (Area3DPtg) ptg;
            if (this._externSheetIndex != area3DPtg.getExternSheetIndex()) {
                return null;
            }
            return rowMoveAreaPtg(area3DPtg);
        }
    }

    private Ptg adjustPtgDueToShiftMove(Ptg ptg) {
        if (ptg instanceof Ref3DPtg) {
            Ref3DPtg ref3DPtg = (Ref3DPtg) ptg;
            if (ref3DPtg.getExternSheetIndex() == this._srcSheetIndex) {
                ref3DPtg.setExternSheetIndex(this._dstSheetIndex);
                return ref3DPtg;
            } else if (ref3DPtg.getExternSheetIndex() == this._dstSheetIndex) {
                ref3DPtg.setExternSheetIndex(this._srcSheetIndex);
                return ref3DPtg;
            }
        }
        return null;
    }

    private Ptg rowMoveRefPtg(RefPtgBase refPtgBase) {
        int row = refPtgBase.getRow();
        int i = this._firstMovedIndex;
        if (i > row || row > this._lastMovedIndex) {
            int i2 = this._amountToMove;
            int i3 = i + i2;
            int i4 = this._lastMovedIndex + i2;
            if (i4 < row || row < i3) {
                return null;
            }
            if (i3 <= row && row <= i4) {
                return createDeletedRef(refPtgBase);
            }
            throw new IllegalStateException("Situation not covered: (" + this._firstMovedIndex + ", " + this._lastMovedIndex + ", " + this._amountToMove + ", " + row + ", " + row + ")");
        }
        refPtgBase.setRow(row + this._amountToMove);
        return refPtgBase;
    }

    private Ptg rowMoveAreaPtg(AreaPtgBase areaPtgBase) {
        int firstRow = areaPtgBase.getFirstRow();
        int lastRow = areaPtgBase.getLastRow();
        int i = this._firstMovedIndex;
        if (i > firstRow || lastRow > this._lastMovedIndex) {
            int i2 = this._amountToMove;
            int i3 = i + i2;
            int i4 = this._lastMovedIndex;
            int i5 = i4 + i2;
            if (firstRow >= i || i4 >= lastRow) {
                if (i > firstRow || firstRow > i4) {
                    if (i > lastRow || lastRow > i4) {
                        if (i5 < firstRow || lastRow < i3) {
                            return null;
                        }
                        if (i3 <= firstRow && lastRow <= i5) {
                            return createDeletedRef(areaPtgBase);
                        }
                        if (firstRow <= i3 && i5 <= lastRow) {
                            return null;
                        }
                        if (i3 < firstRow && firstRow <= i5) {
                            areaPtgBase.setFirstRow(i5 + 1);
                            return areaPtgBase;
                        } else if (i3 >= lastRow || lastRow > i5) {
                            throw new IllegalStateException("Situation not covered: (" + this._firstMovedIndex + ", " + this._lastMovedIndex + ", " + this._amountToMove + ", " + firstRow + ", " + lastRow + ")");
                        } else {
                            areaPtgBase.setLastRow(i3 - 1);
                            return areaPtgBase;
                        }
                    } else if (i2 > 0) {
                        areaPtgBase.setLastRow(lastRow + i2);
                        return areaPtgBase;
                    } else if (i5 < firstRow) {
                        return null;
                    } else {
                        int i6 = lastRow + i2;
                        if (i3 > firstRow) {
                            areaPtgBase.setLastRow(i6);
                            return areaPtgBase;
                        }
                        int i7 = i - 1;
                        if (i5 < i7) {
                            i6 = i7;
                        }
                        areaPtgBase.setFirstRow(Math.min(firstRow, i3));
                        areaPtgBase.setLastRow(i6);
                        return areaPtgBase;
                    }
                } else if (i2 < 0) {
                    areaPtgBase.setFirstRow(firstRow + i2);
                    return areaPtgBase;
                } else if (i3 > lastRow) {
                    return null;
                } else {
                    int i8 = firstRow + i2;
                    if (i5 < lastRow) {
                        areaPtgBase.setFirstRow(i8);
                        return areaPtgBase;
                    }
                    int i9 = i4 + 1;
                    if (i3 > i9) {
                        i8 = i9;
                    }
                    areaPtgBase.setFirstRow(i8);
                    areaPtgBase.setLastRow(Math.max(lastRow, i5));
                    return areaPtgBase;
                }
            } else if (i3 < firstRow && firstRow <= i5) {
                areaPtgBase.setFirstRow(i5 + 1);
                return areaPtgBase;
            } else if (i3 > lastRow || lastRow >= i5) {
                return null;
            } else {
                areaPtgBase.setLastRow(i3 - 1);
                return areaPtgBase;
            }
        } else {
            areaPtgBase.setFirstRow(firstRow + this._amountToMove);
            areaPtgBase.setLastRow(lastRow + this._amountToMove);
            return areaPtgBase;
        }
    }

    private static Ptg createDeletedRef(Ptg ptg) {
        if (ptg instanceof RefPtg) {
            return new RefErrorPtg();
        }
        if (ptg instanceof Ref3DPtg) {
            return new DeletedRef3DPtg(((Ref3DPtg) ptg).getExternSheetIndex());
        }
        if (ptg instanceof AreaPtg) {
            return new AreaErrPtg();
        }
        if (ptg instanceof Area3DPtg) {
            return new DeletedArea3DPtg(((Area3DPtg) ptg).getExternSheetIndex());
        }
        throw new IllegalArgumentException("Unexpected ref ptg class (" + ptg.getClass().getName() + ")");
    }
}
