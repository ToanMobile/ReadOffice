package com.app.office.fc.hssf.formula;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;

public final class CollaboratingWorkbooksEnvironment {
    public static final CollaboratingWorkbooksEnvironment EMPTY = new CollaboratingWorkbooksEnvironment();
    private final WorkbookEvaluator[] _evaluators;
    private final Map<String, WorkbookEvaluator> _evaluatorsByName;
    private boolean _unhooked;

    public static final class WorkbookNotFoundException extends Exception {
        WorkbookNotFoundException(String str) {
            super(str);
        }
    }

    private CollaboratingWorkbooksEnvironment() {
        this._evaluatorsByName = Collections.emptyMap();
        this._evaluators = new WorkbookEvaluator[0];
    }

    public static void setup(String[] strArr, WorkbookEvaluator[] workbookEvaluatorArr) {
        int length = strArr.length;
        if (workbookEvaluatorArr.length != length) {
            throw new IllegalArgumentException("Number of workbook names is " + length + " but number of evaluators is " + workbookEvaluatorArr.length);
        } else if (length >= 1) {
            new CollaboratingWorkbooksEnvironment(strArr, workbookEvaluatorArr, length);
        } else {
            throw new IllegalArgumentException("Must provide at least one collaborating worbook");
        }
    }

    private CollaboratingWorkbooksEnvironment(String[] strArr, WorkbookEvaluator[] workbookEvaluatorArr, int i) {
        int i2 = (i * 3) / 2;
        HashMap hashMap = new HashMap(i2);
        IdentityHashMap identityHashMap = new IdentityHashMap(i2);
        int i3 = 0;
        while (i3 < i) {
            String str = strArr[i3];
            WorkbookEvaluator workbookEvaluator = workbookEvaluatorArr[i3];
            if (hashMap.containsKey(str)) {
                throw new IllegalArgumentException("Duplicate workbook name '" + str + "'");
            } else if (!identityHashMap.containsKey(workbookEvaluator)) {
                identityHashMap.put(workbookEvaluator, str);
                hashMap.put(str, workbookEvaluator);
                i3++;
            } else {
                throw new IllegalArgumentException("Attempted to register same workbook under names '" + ((String) identityHashMap.get(workbookEvaluator)) + "' and '" + str + "'");
            }
        }
        unhookOldEnvironments(workbookEvaluatorArr);
        hookNewEnvironment(workbookEvaluatorArr, this);
        this._unhooked = false;
        this._evaluators = workbookEvaluatorArr;
        this._evaluatorsByName = hashMap;
    }

    private static void hookNewEnvironment(WorkbookEvaluator[] workbookEvaluatorArr, CollaboratingWorkbooksEnvironment collaboratingWorkbooksEnvironment) {
        int length = workbookEvaluatorArr.length;
        IEvaluationListener evaluationListener = workbookEvaluatorArr[0].getEvaluationListener();
        int i = 0;
        while (i < length) {
            if (evaluationListener == workbookEvaluatorArr[i].getEvaluationListener()) {
                i++;
            } else {
                throw new RuntimeException("Workbook evaluators must all have the same evaluation listener");
            }
        }
        EvaluationCache evaluationCache = new EvaluationCache(evaluationListener);
        for (int i2 = 0; i2 < length; i2++) {
            workbookEvaluatorArr[i2].attachToEnvironment(collaboratingWorkbooksEnvironment, evaluationCache, i2);
        }
    }

    private void unhookOldEnvironments(WorkbookEvaluator[] workbookEvaluatorArr) {
        HashSet hashSet = new HashSet();
        for (WorkbookEvaluator environment : workbookEvaluatorArr) {
            hashSet.add(environment.getEnvironment());
        }
        int size = hashSet.size();
        CollaboratingWorkbooksEnvironment[] collaboratingWorkbooksEnvironmentArr = new CollaboratingWorkbooksEnvironment[size];
        hashSet.toArray(collaboratingWorkbooksEnvironmentArr);
        for (int i = 0; i < size; i++) {
            collaboratingWorkbooksEnvironmentArr[i].unhook();
        }
    }

    private void unhook() {
        if (this._evaluators.length >= 1) {
            int i = 0;
            while (true) {
                WorkbookEvaluator[] workbookEvaluatorArr = this._evaluators;
                if (i < workbookEvaluatorArr.length) {
                    workbookEvaluatorArr[i].detachFromEnvironment();
                    i++;
                } else {
                    this._unhooked = true;
                    return;
                }
            }
        }
    }

    public WorkbookEvaluator getWorkbookEvaluator(String str) throws WorkbookNotFoundException {
        if (!this._unhooked) {
            WorkbookEvaluator workbookEvaluator = this._evaluatorsByName.get(str);
            if (workbookEvaluator != null) {
                return workbookEvaluator;
            }
            StringBuffer stringBuffer = new StringBuffer(256);
            stringBuffer.append("Could not resolve external workbook name '");
            stringBuffer.append(str);
            stringBuffer.append("'.");
            if (this._evaluators.length >= 1) {
                stringBuffer.append(" The following workbook names are valid: (");
                int i = 0;
                for (String append : this._evaluatorsByName.keySet()) {
                    int i2 = i + 1;
                    if (i > 0) {
                        stringBuffer.append(", ");
                    }
                    stringBuffer.append("'");
                    stringBuffer.append(append);
                    stringBuffer.append("'");
                    i = i2;
                }
                stringBuffer.append(")");
            } else {
                stringBuffer.append(" Workbook environment has not been set up.");
            }
            throw new WorkbookNotFoundException(stringBuffer.toString());
        }
        throw new IllegalStateException("This environment has been unhooked");
    }
}
