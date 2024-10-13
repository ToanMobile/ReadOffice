package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.CollaboratingWorkbooksEnvironment;
import com.app.office.fc.hssf.formula.eval.AreaEval;
import com.app.office.fc.hssf.formula.eval.BlankEval;
import com.app.office.fc.hssf.formula.eval.BoolEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.MissingArgEval;
import com.app.office.fc.hssf.formula.eval.NameEval;
import com.app.office.fc.hssf.formula.eval.NotImplementedException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.StringEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.Choose;
import com.app.office.fc.hssf.formula.function.FreeRefFunction;
import com.app.office.fc.hssf.formula.function.IfFunc;
import com.app.office.fc.hssf.formula.ptg.Area3DPtg;
import com.app.office.fc.hssf.formula.ptg.AreaErrPtg;
import com.app.office.fc.hssf.formula.ptg.AreaPtg;
import com.app.office.fc.hssf.formula.ptg.AttrPtg;
import com.app.office.fc.hssf.formula.ptg.BoolPtg;
import com.app.office.fc.hssf.formula.ptg.ControlPtg;
import com.app.office.fc.hssf.formula.ptg.DeletedArea3DPtg;
import com.app.office.fc.hssf.formula.ptg.DeletedRef3DPtg;
import com.app.office.fc.hssf.formula.ptg.ErrPtg;
import com.app.office.fc.hssf.formula.ptg.ExpPtg;
import com.app.office.fc.hssf.formula.ptg.FuncVarPtg;
import com.app.office.fc.hssf.formula.ptg.IntPtg;
import com.app.office.fc.hssf.formula.ptg.MemAreaPtg;
import com.app.office.fc.hssf.formula.ptg.MemErrPtg;
import com.app.office.fc.hssf.formula.ptg.MemFuncPtg;
import com.app.office.fc.hssf.formula.ptg.MissingArgPtg;
import com.app.office.fc.hssf.formula.ptg.NamePtg;
import com.app.office.fc.hssf.formula.ptg.NameXPtg;
import com.app.office.fc.hssf.formula.ptg.NumberPtg;
import com.app.office.fc.hssf.formula.ptg.OperationPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.formula.ptg.Ref3DPtg;
import com.app.office.fc.hssf.formula.ptg.RefErrorPtg;
import com.app.office.fc.hssf.formula.ptg.RefPtg;
import com.app.office.fc.hssf.formula.ptg.StringPtg;
import com.app.office.fc.hssf.formula.ptg.UnionPtg;
import com.app.office.fc.hssf.formula.ptg.UnknownPtg;
import com.app.office.fc.hssf.formula.udf.AggregatingUDFFinder;
import com.app.office.fc.hssf.formula.udf.UDFFinder;
import com.app.office.fc.hssf.usermodel.HSSFEvaluationWorkbook;
import com.app.office.fc.ss.util.CellReference;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;

public final class WorkbookEvaluator {
    private EvaluationCache _cache;
    private CollaboratingWorkbooksEnvironment _collaboratingWorkbookEnvironment;
    private final IEvaluationListener _evaluationListener;
    private final Map<String, Integer> _sheetIndexesByName;
    private final Map<EvaluationSheet, Integer> _sheetIndexesBySheet;
    private final IStabilityClassifier _stabilityClassifier;
    private final AggregatingUDFFinder _udfFinder;
    private final EvaluationWorkbook _workbook;
    private int _workbookIx;
    private EvaluationTracker tracker;

    private static boolean isDebugLogEnabled() {
        return false;
    }

    public WorkbookEvaluator(EvaluationWorkbook evaluationWorkbook, IStabilityClassifier iStabilityClassifier, UDFFinder uDFFinder) {
        this(evaluationWorkbook, (IEvaluationListener) null, iStabilityClassifier, uDFFinder);
    }

    WorkbookEvaluator(EvaluationWorkbook evaluationWorkbook, IEvaluationListener iEvaluationListener, IStabilityClassifier iStabilityClassifier, UDFFinder uDFFinder) {
        AggregatingUDFFinder aggregatingUDFFinder;
        this._workbook = evaluationWorkbook;
        this._evaluationListener = iEvaluationListener;
        this._cache = new EvaluationCache(iEvaluationListener);
        this._sheetIndexesBySheet = new IdentityHashMap();
        this._sheetIndexesByName = new IdentityHashMap();
        this._collaboratingWorkbookEnvironment = CollaboratingWorkbooksEnvironment.EMPTY;
        this._workbookIx = 0;
        this._stabilityClassifier = iStabilityClassifier;
        if (evaluationWorkbook == null) {
            aggregatingUDFFinder = null;
        } else {
            aggregatingUDFFinder = (AggregatingUDFFinder) evaluationWorkbook.getUDFFinder();
        }
        if (!(aggregatingUDFFinder == null || uDFFinder == null)) {
            aggregatingUDFFinder.add(uDFFinder);
        }
        this._udfFinder = aggregatingUDFFinder;
    }

    /* access modifiers changed from: package-private */
    public String getSheetName(int i) {
        return this._workbook.getSheetName(i);
    }

    /* access modifiers changed from: package-private */
    public EvaluationSheet getSheet(int i) {
        for (EvaluationSheet next : this._sheetIndexesBySheet.keySet()) {
            if (this._sheetIndexesBySheet.get(next).intValue() == i) {
                return next;
            }
        }
        EvaluationSheet sheet = this._workbook.getSheet(i);
        this._sheetIndexesBySheet.put(sheet, Integer.valueOf(i));
        return sheet;
    }

    /* access modifiers changed from: package-private */
    public EvaluationWorkbook getWorkbook() {
        return this._workbook;
    }

    /* access modifiers changed from: package-private */
    public EvaluationName getName(String str, int i) {
        NamePtg createPtg = this._workbook.getName(str, i).createPtg();
        if (createPtg == null) {
            return null;
        }
        return this._workbook.getName(createPtg);
    }

    private static void logDebug(String str) {
        if (isDebugLogEnabled()) {
            System.out.println(str);
        }
    }

    /* access modifiers changed from: package-private */
    public void attachToEnvironment(CollaboratingWorkbooksEnvironment collaboratingWorkbooksEnvironment, EvaluationCache evaluationCache, int i) {
        this._collaboratingWorkbookEnvironment = collaboratingWorkbooksEnvironment;
        this._cache = evaluationCache;
        this._workbookIx = i;
    }

    /* access modifiers changed from: package-private */
    public CollaboratingWorkbooksEnvironment getEnvironment() {
        return this._collaboratingWorkbookEnvironment;
    }

    /* access modifiers changed from: package-private */
    public void detachFromEnvironment() {
        this._collaboratingWorkbookEnvironment = CollaboratingWorkbooksEnvironment.EMPTY;
        this._cache = new EvaluationCache(this._evaluationListener);
        this._workbookIx = 0;
    }

    /* access modifiers changed from: package-private */
    public WorkbookEvaluator getOtherWorkbookEvaluator(String str) throws CollaboratingWorkbooksEnvironment.WorkbookNotFoundException {
        return this._collaboratingWorkbookEnvironment.getWorkbookEvaluator(str);
    }

    /* access modifiers changed from: package-private */
    public IEvaluationListener getEvaluationListener() {
        return this._evaluationListener;
    }

    public void clearAllCachedResultValues() {
        this._cache.clear();
        this._sheetIndexesBySheet.clear();
    }

    public void notifyUpdateCell(EvaluationCell evaluationCell) {
        this._cache.notifyUpdateCell(this._workbookIx, getSheetIndex(evaluationCell.getSheet()), evaluationCell);
    }

    public void notifyDeleteCell(EvaluationCell evaluationCell) {
        this._cache.notifyDeleteCell(this._workbookIx, getSheetIndex(evaluationCell.getSheet()), evaluationCell);
    }

    private int getSheetIndex(EvaluationSheet evaluationSheet) {
        Integer num = this._sheetIndexesBySheet.get(evaluationSheet);
        if (num == null) {
            int sheetIndex = this._workbook.getSheetIndex(evaluationSheet);
            if (sheetIndex >= 0) {
                num = Integer.valueOf(sheetIndex);
                this._sheetIndexesBySheet.put(evaluationSheet, num);
            } else {
                throw new RuntimeException("Specified sheet from a different book");
            }
        }
        return num.intValue();
    }

    public ValueEval evaluate(EvaluationCell evaluationCell) {
        int sheetIndex = getSheetIndex(evaluationCell.getSheet());
        if (this.tracker == null) {
            this.tracker = new EvaluationTracker(this._cache);
        }
        return evaluateAny(evaluationCell, sheetIndex, evaluationCell.getRowIndex(), evaluationCell.getColumnIndex(), this.tracker);
    }

    /* access modifiers changed from: package-private */
    public int getSheetIndex(String str) {
        Integer num = this._sheetIndexesByName.get(str);
        if (num == null) {
            int sheetIndex = this._workbook.getSheetIndex(str);
            if (sheetIndex < 0) {
                return -1;
            }
            num = Integer.valueOf(sheetIndex);
            this._sheetIndexesByName.put(str, num);
        }
        return num.intValue();
    }

    /* access modifiers changed from: package-private */
    public int getSheetIndexByExternIndex(int i) {
        return this._workbook.convertFromExternSheetIndex(i);
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:48:0x010f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.app.office.fc.hssf.formula.eval.ValueEval evaluateAny(com.app.office.fc.hssf.formula.EvaluationCell r17, int r18, int r19, int r20, com.app.office.fc.hssf.formula.EvaluationTracker r21) {
        /*
            r16 = this;
            r8 = r16
            r0 = r17
            r9 = r18
            r10 = r19
            r11 = r20
            r12 = r21
            com.app.office.fc.hssf.formula.IStabilityClassifier r1 = r8._stabilityClassifier
            if (r1 != 0) goto L_0x0012
        L_0x0010:
            r1 = 1
            goto L_0x001a
        L_0x0012:
            boolean r1 = r1.isCellFinal(r9, r10, r11)
            if (r1 != 0) goto L_0x0019
            goto L_0x0010
        L_0x0019:
            r1 = 0
        L_0x001a:
            if (r0 == 0) goto L_0x0140
            int r2 = r17.getCellType()
            r3 = 2
            if (r2 == r3) goto L_0x0025
            goto L_0x0140
        L_0x0025:
            com.app.office.fc.hssf.formula.EvaluationCache r2 = r8._cache
            com.app.office.fc.hssf.formula.FormulaCellCacheEntry r15 = r2.getOrCreateFormulaCellEntry(r0)
            if (r1 != 0) goto L_0x0033
            boolean r1 = r15.isInputSensitive()
            if (r1 == 0) goto L_0x0036
        L_0x0033:
            r12.acceptFormulaDependency(r15)
        L_0x0036:
            com.app.office.fc.hssf.formula.IEvaluationListener r7 = r8._evaluationListener
            com.app.office.fc.hssf.formula.eval.ValueEval r1 = r15.getValue()
            if (r1 != 0) goto L_0x0131
            boolean r1 = r12.startEvaluate(r15)
            if (r1 != 0) goto L_0x0047
            com.app.office.fc.hssf.formula.eval.ErrorEval r0 = com.app.office.fc.hssf.formula.eval.ErrorEval.CIRCULAR_REF_ERROR
            return r0
        L_0x0047:
            com.app.office.fc.hssf.formula.OperationEvaluationContext r6 = new com.app.office.fc.hssf.formula.OperationEvaluationContext
            com.app.office.fc.hssf.formula.EvaluationWorkbook r3 = r8._workbook
            r1 = r6
            r2 = r16
            r4 = r18
            r5 = r19
            r13 = r6
            r6 = r20
            r14 = r7
            r7 = r21
            r1.<init>(r2, r3, r4, r5, r6, r7)
            r1 = 0
            r2 = 5
            com.app.office.fc.hssf.formula.EvaluationWorkbook r3 = r8._workbook     // Catch:{ NotImplementedException -> 0x0127, Exception -> 0x010f }
            com.app.office.fc.hssf.formula.ptg.Ptg[] r3 = r3.getFormulaTokens(r0)     // Catch:{ NotImplementedException -> 0x0127, Exception -> 0x010f }
            if (r14 != 0) goto L_0x006f
            com.app.office.fc.hssf.formula.eval.ValueEval r3 = r8.evaluateFormula(r13, r3)     // Catch:{ NotImplementedException -> 0x0127, Exception -> 0x010f }
            if (r3 != 0) goto L_0x0079
            r12.endEvaluate(r15)
            return r1
        L_0x006f:
            r14.onStartEvaluate(r0, r15)     // Catch:{ NotImplementedException -> 0x0127, Exception -> 0x010f }
            com.app.office.fc.hssf.formula.eval.ValueEval r3 = r8.evaluateFormula(r13, r3)     // Catch:{ NotImplementedException -> 0x0127, Exception -> 0x010f }
            r14.onEndEvaluate(r15, r3)     // Catch:{ NotImplementedException -> 0x0127, Exception -> 0x010f }
        L_0x0079:
            r12.updateCacheResult(r3)     // Catch:{ NotImplementedException -> 0x0127, Exception -> 0x010f }
            r12.endEvaluate(r15)
            boolean r1 = isDebugLogEnabled()
            if (r1 == 0) goto L_0x00ba
            java.lang.String r1 = r8.getSheetName(r9)
            com.app.office.fc.ss.util.CellReference r4 = new com.app.office.fc.ss.util.CellReference
            r4.<init>((int) r10, (int) r11)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Evaluated "
            r5.append(r6)
            r5.append(r1)
            java.lang.String r1 = "!"
            r5.append(r1)
            java.lang.String r1 = r4.formatAsString()
            r5.append(r1)
            java.lang.String r1 = " to "
            r5.append(r1)
            java.lang.String r1 = r3.toString()
            r5.append(r1)
            java.lang.String r1 = r5.toString()
            logDebug(r1)
        L_0x00ba:
            java.lang.Object r0 = r17.getIdentityKey()
            com.app.office.ss.model.XLSModel.ACell r0 = (com.app.office.ss.model.XLSModel.ACell) r0
            boolean r1 = r3 instanceof com.app.office.fc.hssf.formula.eval.NumberEval
            if (r1 == 0) goto L_0x00d3
            r1 = r3
            com.app.office.fc.hssf.formula.eval.NumberEval r1 = (com.app.office.fc.hssf.formula.eval.NumberEval) r1
            r4 = 0
            r0.setCellType(r4, r4)
            double r1 = r1.getNumberValue()
            r0.setCellValue((double) r1)
            goto L_0x010c
        L_0x00d3:
            r4 = 0
            boolean r1 = r3 instanceof com.app.office.fc.hssf.formula.eval.BoolEval
            if (r1 == 0) goto L_0x00e7
            r1 = r3
            com.app.office.fc.hssf.formula.eval.BoolEval r1 = (com.app.office.fc.hssf.formula.eval.BoolEval) r1
            r2 = 4
            r0.setCellType(r2, r4)
            boolean r1 = r1.getBooleanValue()
            r0.setCellValue((boolean) r1)
            goto L_0x010c
        L_0x00e7:
            boolean r1 = r3 instanceof com.app.office.fc.hssf.formula.eval.StringEval
            if (r1 == 0) goto L_0x00fa
            r1 = r3
            com.app.office.fc.hssf.formula.eval.StringEval r1 = (com.app.office.fc.hssf.formula.eval.StringEval) r1
            r2 = 1
            r0.setCellType(r2, r4)
            java.lang.String r1 = r1.getStringValue()
            r0.setCellValue((java.lang.String) r1)
            goto L_0x010c
        L_0x00fa:
            boolean r1 = r3 instanceof com.app.office.fc.hssf.formula.eval.ErrorEval
            if (r1 == 0) goto L_0x010c
            r0.setCellType(r2, r4)
            r1 = r3
            com.app.office.fc.hssf.formula.eval.ErrorEval r1 = (com.app.office.fc.hssf.formula.eval.ErrorEval) r1
            int r1 = r1.getErrorCode()
            byte r1 = (byte) r1
            r0.setCellErrorValue(r1)
        L_0x010c:
            return r3
        L_0x010d:
            r0 = move-exception
            goto L_0x012d
        L_0x010f:
            java.lang.Object r0 = r17.getIdentityKey()     // Catch:{ all -> 0x010d }
            com.app.office.ss.model.XLSModel.ACell r0 = (com.app.office.ss.model.XLSModel.ACell) r0     // Catch:{ all -> 0x010d }
            r3 = 0
            r0.setCellType(r2, r3)     // Catch:{ all -> 0x010d }
            com.app.office.fc.hssf.formula.eval.ErrorEval r2 = com.app.office.fc.hssf.formula.eval.ErrorEval.NA     // Catch:{ all -> 0x010d }
            int r2 = r2.getErrorCode()     // Catch:{ all -> 0x010d }
            byte r2 = (byte) r2     // Catch:{ all -> 0x010d }
            r0.setCellErrorValue(r2)     // Catch:{ all -> 0x010d }
            r12.endEvaluate(r15)
            return r1
        L_0x0127:
            r0 = move-exception
            com.app.office.fc.hssf.formula.eval.NotImplementedException r0 = r8.addExceptionInfo(r0, r9, r10, r11)     // Catch:{ all -> 0x010d }
            throw r0     // Catch:{ all -> 0x010d }
        L_0x012d:
            r12.endEvaluate(r15)
            throw r0
        L_0x0131:
            r14 = r7
            if (r14 == 0) goto L_0x013b
            com.app.office.fc.hssf.formula.eval.ValueEval r0 = r15.getValue()
            r14.onCacheHit(r9, r10, r11, r0)
        L_0x013b:
            com.app.office.fc.hssf.formula.eval.ValueEval r0 = r15.getValue()
            return r0
        L_0x0140:
            com.app.office.fc.hssf.formula.eval.ValueEval r6 = getValueFromNonFormulaCell(r17)
            if (r1 == 0) goto L_0x0154
            int r1 = r8._workbookIx
            r0 = r21
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r6
            r0.acceptPlainValueDependency(r1, r2, r3, r4, r5)
        L_0x0154:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.formula.WorkbookEvaluator.evaluateAny(com.app.office.fc.hssf.formula.EvaluationCell, int, int, int, com.app.office.fc.hssf.formula.EvaluationTracker):com.app.office.fc.hssf.formula.eval.ValueEval");
    }

    private NotImplementedException addExceptionInfo(NotImplementedException notImplementedException, int i, int i2, int i3) {
        try {
            CellReference cellReference = new CellReference(this._workbook.getSheetName(i), i2, i3, false, false);
            return new NotImplementedException("Error evaluating cell " + cellReference.formatAsString(), notImplementedException);
        } catch (Exception e) {
            e.printStackTrace();
            return notImplementedException;
        }
    }

    static ValueEval getValueFromNonFormulaCell(EvaluationCell evaluationCell) {
        if (evaluationCell == null) {
            return BlankEval.instance;
        }
        int cellType = evaluationCell.getCellType();
        if (cellType == 0) {
            return new NumberEval(evaluationCell.getNumericCellValue());
        }
        if (cellType == 1) {
            return new StringEval(evaluationCell.getStringCellValue());
        }
        if (cellType == 3) {
            return BlankEval.instance;
        }
        if (cellType == 4) {
            return BoolEval.valueOf(evaluationCell.getBooleanCellValue());
        }
        if (cellType == 5) {
            return ErrorEval.valueOf(evaluationCell.getErrorCellValue());
        }
        throw new RuntimeException("Unexpected cell type (" + cellType + ")");
    }

    /* access modifiers changed from: package-private */
    public ValueEval evaluateFormula(OperationEvaluationContext operationEvaluationContext, Ptg[] ptgArr) {
        ValueEval valueEval;
        int i;
        int i2;
        int i3;
        ArrayList arrayList = new ArrayList();
        int length = ptgArr.length;
        int i4 = 0;
        while (i4 < length) {
            OperationPtg operationPtg = ptgArr[i4];
            if (operationPtg instanceof AttrPtg) {
                AttrPtg attrPtg = (AttrPtg) operationPtg;
                if (attrPtg.isSum()) {
                    operationPtg = FuncVarPtg.SUM;
                }
                if (attrPtg.isOptimizedChoose()) {
                    ValueEval valueEval2 = (ValueEval) arrayList.remove(arrayList.size() - 1);
                    int[] jumpTable = attrPtg.getJumpTable();
                    int length2 = jumpTable.length;
                    try {
                        int evaluateFirstArg = Choose.evaluateFirstArg(valueEval2, operationEvaluationContext.getRowIndex(), operationEvaluationContext.getColumnIndex());
                        if (evaluateFirstArg >= 1) {
                            if (evaluateFirstArg <= length2) {
                                i2 = jumpTable[evaluateFirstArg - 1];
                                i = countTokensToBeSkipped(ptgArr, i4, i2 - ((length2 * 2) + 2));
                            }
                        }
                        arrayList.add(ErrorEval.VALUE_INVALID);
                        i3 = attrPtg.getChooseFuncOffset();
                    } catch (EvaluationException e) {
                        arrayList.add(e.getErrorEval());
                        i3 = attrPtg.getChooseFuncOffset();
                    }
                    i2 = i3 + 4;
                    i = countTokensToBeSkipped(ptgArr, i4, i2 - ((length2 * 2) + 2));
                } else {
                    if (attrPtg.isOptimizedIf()) {
                        try {
                            if (!IfFunc.evaluateFirstArg((ValueEval) arrayList.remove(arrayList.size() - 1), operationEvaluationContext.getRowIndex(), operationEvaluationContext.getColumnIndex())) {
                                i4 += countTokensToBeSkipped(ptgArr, i4, attrPtg.getData());
                                int i5 = i4 + 1;
                                Ptg ptg = ptgArr[i5];
                                if ((ptgArr[i4] instanceof AttrPtg) && (ptg instanceof FuncVarPtg)) {
                                    arrayList.add(BoolEval.FALSE);
                                    i4 = i5;
                                }
                            }
                        } catch (EvaluationException e2) {
                            arrayList.add(e2.getErrorEval());
                            i4 += countTokensToBeSkipped(ptgArr, i4, attrPtg.getData());
                            i = countTokensToBeSkipped(ptgArr, i4, ptgArr[i4].getData() + 1);
                        }
                    } else if (attrPtg.isSkip()) {
                        i4 += countTokensToBeSkipped(ptgArr, i4, attrPtg.getData() + 1);
                        if (arrayList.get(arrayList.size() - 1) == MissingArgEval.instance) {
                            arrayList.remove(arrayList.size() - 1);
                            arrayList.add(BlankEval.instance);
                        }
                    }
                    i4++;
                }
                i4 += i;
                i4++;
            }
            if (!(operationPtg instanceof ControlPtg) && !(operationPtg instanceof MemFuncPtg) && !(operationPtg instanceof MemAreaPtg) && !(operationPtg instanceof MemErrPtg)) {
                if (operationPtg instanceof OperationPtg) {
                    OperationPtg operationPtg2 = operationPtg;
                    if (operationPtg2 instanceof UnionPtg) {
                        continue;
                    } else {
                        int numberOfOperands = operationPtg2.getNumberOfOperands();
                        ValueEval[] valueEvalArr = new ValueEval[numberOfOperands];
                        for (int i6 = numberOfOperands - 1; i6 >= 0; i6--) {
                            valueEvalArr[i6] = (ValueEval) arrayList.remove(arrayList.size() - 1);
                        }
                        valueEval = OperationEvaluatorFactory.evaluate(operationPtg2, valueEvalArr, operationEvaluationContext);
                    }
                } else {
                    valueEval = getEvalForPtg(operationPtg, operationEvaluationContext);
                }
                if (valueEval == null) {
                    return null;
                }
                arrayList.add(valueEval);
            }
            i4++;
        }
        ValueEval valueEval3 = (ValueEval) arrayList.remove(arrayList.size() - 1);
        if (arrayList.isEmpty()) {
            return ((valueEval3 instanceof AreaEval) || (valueEval3 instanceof RefEval)) ? valueEval3 : dereferenceResult(valueEval3, operationEvaluationContext.getRowIndex(), operationEvaluationContext.getColumnIndex());
        }
        throw new IllegalStateException("evaluation stack not empty");
    }

    private static int countTokensToBeSkipped(Ptg[] ptgArr, int i, int i2) {
        int i3 = i;
        while (i2 != 0) {
            i3++;
            i2 -= ptgArr[i3].getSize();
            if (i2 < 0) {
                throw new RuntimeException("Bad skip distance (wrong token size calculation).");
            } else if (i3 >= ptgArr.length) {
                throw new RuntimeException("Skip distance too far (ran out of formula tokens).");
            }
        }
        return i3 - i;
    }

    public static ValueEval dereferenceResult(ValueEval valueEval, int i, int i2) {
        try {
            ValueEval singleValue = OperandResolver.getSingleValue(valueEval, i, i2);
            return singleValue == BlankEval.instance ? NumberEval.ZERO : singleValue;
        } catch (EvaluationException e) {
            return e.getErrorEval();
        }
    }

    private ValueEval getEvalForPtg(Ptg ptg, OperationEvaluationContext operationEvaluationContext) {
        if (ptg instanceof NamePtg) {
            EvaluationName name = this._workbook.getName((NamePtg) ptg);
            if (name.isFunctionName()) {
                return new NameEval(name.getNameText());
            }
            if (name.hasFormula()) {
                return evaluateNameFormula(name.getNameDefinition(), operationEvaluationContext);
            }
            throw new RuntimeException("Don't now how to evalate name '" + name.getNameText() + "'");
        } else if (ptg instanceof NameXPtg) {
            EvaluationName name2 = ((HSSFEvaluationWorkbook) this._workbook).getName((NameXPtg) ptg);
            if (name2.isFunctionName()) {
                return new NameEval(name2.getNameText());
            }
            if (name2.hasFormula()) {
                return evaluateNameFormula(name2.getNameDefinition(), operationEvaluationContext);
            }
            throw new RuntimeException("Don't now how to evalate name '" + name2.getNameText() + "'");
        } else if (ptg instanceof IntPtg) {
            return new NumberEval((double) ((IntPtg) ptg).getValue());
        } else {
            if (ptg instanceof NumberPtg) {
                return new NumberEval(((NumberPtg) ptg).getValue());
            }
            if (ptg instanceof StringPtg) {
                return new StringEval(((StringPtg) ptg).getValue());
            }
            if (ptg instanceof BoolPtg) {
                return BoolEval.valueOf(((BoolPtg) ptg).getValue());
            }
            if (ptg instanceof ErrPtg) {
                return ErrorEval.valueOf(((ErrPtg) ptg).getErrorCode());
            }
            if (ptg instanceof MissingArgPtg) {
                return MissingArgEval.instance;
            }
            if ((ptg instanceof AreaErrPtg) || (ptg instanceof RefErrorPtg) || (ptg instanceof DeletedArea3DPtg) || (ptg instanceof DeletedRef3DPtg)) {
                return ErrorEval.REF_INVALID;
            }
            if (ptg instanceof Ref3DPtg) {
                Ref3DPtg ref3DPtg = (Ref3DPtg) ptg;
                return operationEvaluationContext.getRef3DEval(ref3DPtg.getRow(), ref3DPtg.getColumn(), ref3DPtg.getExternSheetIndex());
            } else if (ptg instanceof Area3DPtg) {
                Area3DPtg area3DPtg = (Area3DPtg) ptg;
                return operationEvaluationContext.getArea3DEval(area3DPtg.getFirstRow(), area3DPtg.getFirstColumn(), area3DPtg.getLastRow(), area3DPtg.getLastColumn(), area3DPtg.getExternSheetIndex());
            } else if (ptg instanceof RefPtg) {
                RefPtg refPtg = (RefPtg) ptg;
                return operationEvaluationContext.getRefEval(refPtg.getRow(), refPtg.getColumn());
            } else if (ptg instanceof AreaPtg) {
                AreaPtg areaPtg = (AreaPtg) ptg;
                return operationEvaluationContext.getAreaEval(areaPtg.getFirstRow(), areaPtg.getFirstColumn(), areaPtg.getLastRow(), areaPtg.getLastColumn());
            } else if (ptg instanceof UnknownPtg) {
                throw new RuntimeException("UnknownPtg not allowed");
            } else if (ptg instanceof ExpPtg) {
                throw new RuntimeException("ExpPtg currently not supported");
            } else {
                throw new RuntimeException("Unexpected ptg class (" + ptg.getClass().getName() + ")");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public ValueEval evaluateNameFormula(Ptg[] ptgArr, OperationEvaluationContext operationEvaluationContext) {
        if (ptgArr.length == 1) {
            return getEvalForPtg(ptgArr[0], operationEvaluationContext);
        }
        return evaluateFormula(operationEvaluationContext, ptgArr);
    }

    /* access modifiers changed from: package-private */
    public ValueEval evaluateReference(EvaluationSheet evaluationSheet, int i, int i2, int i3, EvaluationTracker evaluationTracker) {
        return evaluateAny(evaluationSheet.getCell(i2, i3), i, i2, i3, evaluationTracker);
    }

    public FreeRefFunction findUserDefinedFunction(String str) {
        return this._udfFinder.findFunction(str);
    }
}
