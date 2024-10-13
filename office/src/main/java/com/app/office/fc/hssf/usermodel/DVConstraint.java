package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.ss.usermodel.DataValidationConstraint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DVConstraint implements DataValidationConstraint {
    private static final DataValidationConstraint.ValidationType VT = null;
    private String[] _explicitListValues;
    private String _formula1;
    private String _formula2;
    private int _operator;
    private final int _validationType;
    private Double _value1;
    private Double _value2;

    private static Ptg[] convertDoubleFormula(String str, Double d, HSSFSheet hSSFSheet) {
        return null;
    }

    private Ptg[] createListFormula(HSSFSheet hSSFSheet) {
        return null;
    }

    public static final class FormulaPair {
        private final Ptg[] _formula1;
        private final Ptg[] _formula2;

        public FormulaPair(Ptg[] ptgArr, Ptg[] ptgArr2) {
            this._formula1 = ptgArr;
            this._formula2 = ptgArr2;
        }

        public Ptg[] getFormula1() {
            return this._formula1;
        }

        public Ptg[] getFormula2() {
            return this._formula2;
        }
    }

    private DVConstraint(int i, int i2, String str, String str2, Double d, Double d2, String[] strArr) {
        this._validationType = i;
        this._operator = i2;
        this._formula1 = str;
        this._formula2 = str2;
        this._value1 = d;
        this._value2 = d2;
        this._explicitListValues = strArr;
    }

    private DVConstraint(String str, String[] strArr) {
        this(3, 0, str, (String) null, (Double) null, (Double) null, strArr);
    }

    public static DVConstraint createNumericConstraint(int i, int i2, String str, String str2) {
        if (i != 0) {
            if (i != 1 && i != 2 && i != 6) {
                throw new IllegalArgumentException("Validation Type (" + i + ") not supported with this method");
            } else if (str != null) {
                DataValidationConstraint.OperatorType.validateSecondArg(i2, str2);
            } else {
                throw new IllegalArgumentException("expr1 must be supplied");
            }
        } else if (!(str == null && str2 == null)) {
            throw new IllegalArgumentException("expr1 and expr2 must be null for validation type 'any'");
        }
        String formulaFromTextExpression = getFormulaFromTextExpression(str);
        Double convertNumber = formulaFromTextExpression == null ? convertNumber(str) : null;
        String formulaFromTextExpression2 = getFormulaFromTextExpression(str2);
        return new DVConstraint(i, i2, formulaFromTextExpression, formulaFromTextExpression2, convertNumber, formulaFromTextExpression2 == null ? convertNumber(str2) : null, (String[]) null);
    }

    public static DVConstraint createFormulaListConstraint(String str) {
        return new DVConstraint(str, (String[]) null);
    }

    public static DVConstraint createExplicitListConstraint(String[] strArr) {
        return new DVConstraint((String) null, strArr);
    }

    public static DVConstraint createTimeConstraint(int i, String str, String str2) {
        if (str != null) {
            DataValidationConstraint.OperatorType.validateSecondArg(i, str);
            String formulaFromTextExpression = getFormulaFromTextExpression(str);
            Double convertTime = formulaFromTextExpression == null ? convertTime(str) : null;
            String formulaFromTextExpression2 = getFormulaFromTextExpression(str2);
            return new DVConstraint(5, i, formulaFromTextExpression, formulaFromTextExpression2, convertTime, formulaFromTextExpression2 == null ? convertTime(str2) : null, (String[]) null);
        }
        throw new IllegalArgumentException("expr1 must be supplied");
    }

    public static DVConstraint createDateConstraint(int i, String str, String str2, String str3) {
        SimpleDateFormat simpleDateFormat;
        if (str != null) {
            DataValidationConstraint.OperatorType.validateSecondArg(i, str2);
            Double d = null;
            if (str3 == null) {
                simpleDateFormat = null;
            } else {
                simpleDateFormat = new SimpleDateFormat(str3);
            }
            String formulaFromTextExpression = getFormulaFromTextExpression(str);
            Double convertDate = formulaFromTextExpression == null ? convertDate(str, simpleDateFormat) : null;
            String formulaFromTextExpression2 = getFormulaFromTextExpression(str2);
            if (formulaFromTextExpression2 == null) {
                d = convertDate(str2, simpleDateFormat);
            }
            return new DVConstraint(4, i, formulaFromTextExpression, formulaFromTextExpression2, convertDate, d, (String[]) null);
        }
        throw new IllegalArgumentException("expr1 must be supplied");
    }

    private static String getFormulaFromTextExpression(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() < 1) {
            throw new IllegalArgumentException("Empty string is not a valid formula/value expression");
        } else if (str.charAt(0) == '=') {
            return str.substring(1);
        } else {
            return null;
        }
    }

    private static Double convertNumber(String str) {
        if (str == null) {
            return null;
        }
        try {
            return new Double(str);
        } catch (NumberFormatException unused) {
            throw new RuntimeException("The supplied text '" + str + "' could not be parsed as a number");
        }
    }

    private static Double convertTime(String str) {
        if (str == null) {
            return null;
        }
        return new Double(HSSFDateUtil.convertTime(str));
    }

    private static Double convertDate(String str, SimpleDateFormat simpleDateFormat) {
        Date date;
        if (str == null) {
            return null;
        }
        if (simpleDateFormat == null) {
            date = HSSFDateUtil.parseYYYYMMDDDate(str);
        } else {
            try {
                date = simpleDateFormat.parse(str);
            } catch (ParseException e) {
                throw new RuntimeException("Failed to parse date '" + str + "' using specified format '" + simpleDateFormat + "'", e);
            }
        }
        return new Double(HSSFDateUtil.getExcelDate(date));
    }

    public static DVConstraint createCustomFormulaConstraint(String str) {
        if (str != null) {
            return new DVConstraint(7, 0, str, (String) null, (Double) null, (Double) null, (String[]) null);
        }
        throw new IllegalArgumentException("formula must be supplied");
    }

    public int getValidationType() {
        return this._validationType;
    }

    public boolean isListValidationType() {
        return this._validationType == 3;
    }

    public boolean isExplicitList() {
        return this._validationType == 3 && this._explicitListValues != null;
    }

    public int getOperator() {
        return this._operator;
    }

    public void setOperator(int i) {
        this._operator = i;
    }

    public String[] getExplicitListValues() {
        return this._explicitListValues;
    }

    public void setExplicitListValues(String[] strArr) {
        if (this._validationType == 3) {
            this._formula1 = null;
            this._explicitListValues = strArr;
            return;
        }
        throw new RuntimeException("Cannot setExplicitListValues on non-list constraint");
    }

    public String getFormula1() {
        return this._formula1;
    }

    public void setFormula1(String str) {
        this._value1 = null;
        this._explicitListValues = null;
        this._formula1 = str;
    }

    public String getFormula2() {
        return this._formula2;
    }

    public void setFormula2(String str) {
        this._value2 = null;
        this._formula2 = str;
    }

    public Double getValue1() {
        return this._value1;
    }

    public void setValue1(double d) {
        this._formula1 = null;
        this._value1 = new Double(d);
    }

    public Double getValue2() {
        return this._value2;
    }

    public void setValue2(double d) {
        this._formula2 = null;
        this._value2 = new Double(d);
    }

    /* access modifiers changed from: package-private */
    public FormulaPair createFormulas(HSSFSheet hSSFSheet) {
        Ptg[] ptgArr;
        Ptg[] ptgArr2;
        if (isListValidationType()) {
            ptgArr = createListFormula(hSSFSheet);
            ptgArr2 = Ptg.EMPTY_PTG_ARRAY;
        } else {
            Ptg[] convertDoubleFormula = convertDoubleFormula(this._formula1, this._value1, hSSFSheet);
            ptgArr2 = convertDoubleFormula(this._formula2, this._value2, hSSFSheet);
            ptgArr = convertDoubleFormula;
        }
        return new FormulaPair(ptgArr, ptgArr2);
    }
}
