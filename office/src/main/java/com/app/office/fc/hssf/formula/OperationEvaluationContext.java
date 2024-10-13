package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.CollaboratingWorkbooksEnvironment;
import com.app.office.fc.hssf.formula.EvaluationWorkbook;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.NameXEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.FreeRefFunction;
import com.app.office.fc.hssf.formula.ptg.Area3DPtg;
import com.app.office.fc.hssf.formula.ptg.NameXPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.formula.ptg.Ref3DPtg;
import com.app.office.fc.ss.SpreadsheetVersion;
import com.app.office.fc.ss.util.CellReference;

public final class OperationEvaluationContext {
    public static final FreeRefFunction UDF = UserDefinedFunction.instance;
    private final WorkbookEvaluator _bookEvaluator;
    private final int _columnIndex;
    private final int _rowIndex;
    private final int _sheetIndex;
    private SheetRefEvaluator _sre;
    private final EvaluationTracker _tracker;
    private final EvaluationWorkbook _workbook;

    public OperationEvaluationContext(WorkbookEvaluator workbookEvaluator, EvaluationWorkbook evaluationWorkbook, int i, int i2, int i3, EvaluationTracker evaluationTracker) {
        this._bookEvaluator = workbookEvaluator;
        this._workbook = evaluationWorkbook;
        this._sheetIndex = i;
        this._rowIndex = i2;
        this._columnIndex = i3;
        this._tracker = evaluationTracker;
        this._sre = new SheetRefEvaluator(workbookEvaluator, evaluationTracker, i);
    }

    public EvaluationWorkbook getWorkbook() {
        return this._workbook;
    }

    public int getRowIndex() {
        return this._rowIndex;
    }

    public int getColumnIndex() {
        return this._columnIndex;
    }

    /* access modifiers changed from: package-private */
    public SheetRefEvaluator createExternSheetRefEvaluator(ExternSheetReferenceToken externSheetReferenceToken) {
        return createExternSheetRefEvaluator(externSheetReferenceToken.getExternSheetIndex());
    }

    /* access modifiers changed from: package-private */
    public SheetRefEvaluator createExternSheetRefEvaluator(int i) {
        int i2;
        WorkbookEvaluator workbookEvaluator;
        EvaluationWorkbook.ExternalSheet externalSheet = this._workbook.getExternalSheet(i);
        if (externalSheet == null) {
            i2 = this._workbook.convertFromExternSheetIndex(i);
            workbookEvaluator = this._bookEvaluator;
        } else {
            String workbookName = externalSheet.getWorkbookName();
            try {
                WorkbookEvaluator otherWorkbookEvaluator = this._bookEvaluator.getOtherWorkbookEvaluator(workbookName);
                int sheetIndex = otherWorkbookEvaluator.getSheetIndex(externalSheet.getSheetName());
                if (sheetIndex >= 0) {
                    workbookEvaluator = otherWorkbookEvaluator;
                    i2 = sheetIndex;
                } else {
                    throw new RuntimeException("Invalid sheet name '" + externalSheet.getSheetName() + "' in bool '" + workbookName + "'.");
                }
            } catch (CollaboratingWorkbooksEnvironment.WorkbookNotFoundException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return new SheetRefEvaluator(workbookEvaluator, this._tracker, i2);
    }

    private SheetRefEvaluator createExternSheetRefEvaluator(String str, String str2) {
        WorkbookEvaluator workbookEvaluator;
        if (str == null) {
            workbookEvaluator = this._bookEvaluator;
        } else if (str2 != null) {
            try {
                workbookEvaluator = this._bookEvaluator.getOtherWorkbookEvaluator(str);
            } catch (CollaboratingWorkbooksEnvironment.WorkbookNotFoundException unused) {
                return null;
            }
        } else {
            throw new IllegalArgumentException("sheetName must not be null if workbookName is provided");
        }
        int sheetIndex = str2 == null ? this._sheetIndex : workbookEvaluator.getSheetIndex(str2);
        if (sheetIndex < 0) {
            return null;
        }
        return new SheetRefEvaluator(workbookEvaluator, this._tracker, sheetIndex);
    }

    public SheetRefEvaluator getRefEvaluatorForCurrentSheet() {
        if (this._sre == null) {
            this._sre = new SheetRefEvaluator(this._bookEvaluator, this._tracker, this._sheetIndex);
        }
        return this._sre;
    }

    public ValueEval getDynamicReference(String str, String str2, String str3, String str4, boolean z) {
        short s;
        int i;
        short s2;
        int i2;
        if (z) {
            SheetRefEvaluator createExternSheetRefEvaluator = createExternSheetRefEvaluator(str, str2);
            if (createExternSheetRefEvaluator == null) {
                return ErrorEval.REF_INVALID;
            }
            SpreadsheetVersion spreadsheetVersion = ((FormulaParsingWorkbook) this._workbook).getSpreadsheetVersion();
            CellReference.NameType classifyCellReference = classifyCellReference(str3, spreadsheetVersion);
            int i3 = AnonymousClass1.$SwitchMap$com$reader$office$fc$ss$util$CellReference$NameType[classifyCellReference.ordinal()];
            if (i3 == 1) {
                return ErrorEval.REF_INVALID;
            }
            if (i3 == 2) {
                EvaluationName name = ((FormulaParsingWorkbook) this._workbook).getName(str3, this._sheetIndex);
                if (name.isRange()) {
                    return this._bookEvaluator.evaluateNameFormula(name.getNameDefinition(), this);
                }
                throw new RuntimeException("Specified name '" + str3 + "' is not a range as expected.");
            } else if (str4 == null) {
                int i4 = AnonymousClass1.$SwitchMap$com$reader$office$fc$ss$util$CellReference$NameType[classifyCellReference.ordinal()];
                if (i4 == 3 || i4 == 4) {
                    return ErrorEval.REF_INVALID;
                }
                if (i4 == 5) {
                    CellReference cellReference = new CellReference(str3);
                    return new LazyRefEval(cellReference.getRow(), cellReference.getCol(), createExternSheetRefEvaluator);
                }
                throw new IllegalStateException("Unexpected reference classification of '" + str3 + "'.");
            } else {
                CellReference.NameType classifyCellReference2 = classifyCellReference(str3, spreadsheetVersion);
                int i5 = AnonymousClass1.$SwitchMap$com$reader$office$fc$ss$util$CellReference$NameType[classifyCellReference2.ordinal()];
                if (i5 == 1) {
                    return ErrorEval.REF_INVALID;
                }
                if (i5 == 2) {
                    throw new RuntimeException("Cannot evaluate '" + str3 + "'. Indirect evaluation of defined names not supported yet");
                } else if (classifyCellReference2 != classifyCellReference) {
                    return ErrorEval.REF_INVALID;
                } else {
                    int i6 = AnonymousClass1.$SwitchMap$com$reader$office$fc$ss$util$CellReference$NameType[classifyCellReference.ordinal()];
                    if (i6 == 3) {
                        int lastRowIndex = spreadsheetVersion.getLastRowIndex();
                        i = lastRowIndex;
                        s2 = parseColRef(str3);
                        s = parseColRef(str4);
                        i2 = 0;
                    } else if (i6 == 4) {
                        int lastColumnIndex = spreadsheetVersion.getLastColumnIndex();
                        s = lastColumnIndex;
                        i2 = parseRowRef(str3);
                        i = parseRowRef(str4);
                        s2 = 0;
                    } else if (i6 == 5) {
                        CellReference cellReference2 = new CellReference(str3);
                        int row = cellReference2.getRow();
                        short col = cellReference2.getCol();
                        CellReference cellReference3 = new CellReference(str4);
                        int row2 = cellReference3.getRow();
                        s2 = col;
                        i2 = row;
                        s = cellReference3.getCol();
                        i = row2;
                    } else {
                        throw new IllegalStateException("Unexpected reference classification of '" + str3 + "'.");
                    }
                    return new LazyAreaEval(i2, s2, i, s, createExternSheetRefEvaluator);
                }
            }
        } else {
            throw new RuntimeException("R1C1 style not supported yet");
        }
    }

    /* renamed from: com.app.office.fc.hssf.formula.OperationEvaluationContext$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$reader$office$fc$ss$util$CellReference$NameType;

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.app.office.fc.ss.util.CellReference$NameType[] r0 = com.app.office.fc.ss.util.CellReference.NameType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$reader$office$fc$ss$util$CellReference$NameType = r0
                com.app.office.fc.ss.util.CellReference$NameType r1 = com.app.office.fc.ss.util.CellReference.NameType.BAD_CELL_OR_NAMED_RANGE     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$reader$office$fc$ss$util$CellReference$NameType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.app.office.fc.ss.util.CellReference$NameType r1 = com.app.office.fc.ss.util.CellReference.NameType.NAMED_RANGE     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$reader$office$fc$ss$util$CellReference$NameType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.app.office.fc.ss.util.CellReference$NameType r1 = com.app.office.fc.ss.util.CellReference.NameType.COLUMN     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$reader$office$fc$ss$util$CellReference$NameType     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.app.office.fc.ss.util.CellReference$NameType r1 = com.app.office.fc.ss.util.CellReference.NameType.ROW     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$reader$office$fc$ss$util$CellReference$NameType     // Catch:{ NoSuchFieldError -> 0x003e }
                com.app.office.fc.ss.util.CellReference$NameType r1 = com.app.office.fc.ss.util.CellReference.NameType.CELL     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.formula.OperationEvaluationContext.AnonymousClass1.<clinit>():void");
        }
    }

    private static int parseRowRef(String str) {
        return CellReference.convertColStringToIndex(str);
    }

    private static int parseColRef(String str) {
        return Integer.parseInt(str) - 1;
    }

    private static CellReference.NameType classifyCellReference(String str, SpreadsheetVersion spreadsheetVersion) {
        if (str.length() < 1) {
            return CellReference.NameType.BAD_CELL_OR_NAMED_RANGE;
        }
        return CellReference.classifyCellReference(str, spreadsheetVersion);
    }

    public FreeRefFunction findUserDefinedFunction(String str) {
        return this._bookEvaluator.findUserDefinedFunction(str);
    }

    public ValueEval getRefEval(int i, int i2) {
        return new LazyRefEval(i, i2, getRefEvaluatorForCurrentSheet());
    }

    public ValueEval getRef3DEval(int i, int i2, int i3) {
        return new LazyRefEval(i, i2, createExternSheetRefEvaluator(i3));
    }

    public ValueEval getAreaEval(int i, int i2, int i3, int i4) {
        return new LazyAreaEval(i, i2, i3, i4, getRefEvaluatorForCurrentSheet());
    }

    public ValueEval getArea3DEval(int i, int i2, int i3, int i4, int i5) {
        return new LazyAreaEval(i, i2, i3, i4, createExternSheetRefEvaluator(i5));
    }

    public ValueEval getNameXEval(NameXPtg nameXPtg) {
        EvaluationWorkbook.ExternalSheet externalSheet = this._workbook.getExternalSheet(nameXPtg.getSheetRefIndex());
        if (externalSheet == null) {
            return new NameXEval(nameXPtg);
        }
        String workbookName = externalSheet.getWorkbookName();
        EvaluationWorkbook.ExternalName externalName = this._workbook.getExternalName(nameXPtg.getSheetRefIndex(), nameXPtg.getNameIndex());
        try {
            WorkbookEvaluator otherWorkbookEvaluator = this._bookEvaluator.getOtherWorkbookEvaluator(workbookName);
            EvaluationName name = otherWorkbookEvaluator.getName(externalName.getName(), externalName.getIx() - 1);
            if (name != null && name.hasFormula()) {
                if (name.getNameDefinition().length <= 1) {
                    Ptg ptg = name.getNameDefinition()[0];
                    if (ptg instanceof Ref3DPtg) {
                        Ref3DPtg ref3DPtg = (Ref3DPtg) ptg;
                        return new LazyRefEval(ref3DPtg.getRow(), ref3DPtg.getColumn(), createExternSheetRefEvaluator(workbookName, otherWorkbookEvaluator.getSheetName(otherWorkbookEvaluator.getSheetIndexByExternIndex(ref3DPtg.getExternSheetIndex()))));
                    } else if (ptg instanceof Area3DPtg) {
                        Area3DPtg area3DPtg = (Area3DPtg) ptg;
                        return new LazyAreaEval(area3DPtg.getFirstRow(), area3DPtg.getFirstColumn(), area3DPtg.getLastRow(), area3DPtg.getLastColumn(), createExternSheetRefEvaluator(workbookName, otherWorkbookEvaluator.getSheetName(otherWorkbookEvaluator.getSheetIndexByExternIndex(area3DPtg.getExternSheetIndex()))));
                    }
                } else {
                    throw new RuntimeException("Complex name formulas not supported yet");
                }
            }
            return ErrorEval.REF_INVALID;
        } catch (CollaboratingWorkbooksEnvironment.WorkbookNotFoundException unused) {
            return ErrorEval.REF_INVALID;
        }
    }
}
