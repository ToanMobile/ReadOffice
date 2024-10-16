package com.app.office.fc.ss.usermodel;

import java.util.HashMap;
import java.util.Map;

public enum FormulaError {
    NULL(0, "#NULL!"),
    DIV0(7, "#DIV/0!"),
    VALUE(15, "#VALUE!"),
    REF(23, "#REF!"),
    NAME(29, "#NAME?"),
    NUM(36, "#NUM!"),
    NA(42, "#N/A");
    
    private static Map<Byte, FormulaError> imap;
    private static Map<String, FormulaError> smap;
    private String repr;
    private byte type;

    static {
        int i;
        smap = new HashMap();
        imap = new HashMap();
        for (FormulaError formulaError : values()) {
            imap.put(Byte.valueOf(formulaError.getCode()), formulaError);
            smap.put(formulaError.getString(), formulaError);
        }
    }

    private FormulaError(int i, String str) {
        this.type = (byte) i;
        this.repr = str;
    }

    public byte getCode() {
        return this.type;
    }

    public String getString() {
        return this.repr;
    }

    public static FormulaError forInt(byte b) {
        FormulaError formulaError = imap.get(Byte.valueOf(b));
        if (formulaError != null) {
            return formulaError;
        }
        throw new IllegalArgumentException("Unknown error type: " + b);
    }

    public static FormulaError forString(String str) {
        FormulaError formulaError = smap.get(str);
        if (formulaError != null) {
            return formulaError;
        }
        throw new IllegalArgumentException("Unknown error code: " + str);
    }
}
