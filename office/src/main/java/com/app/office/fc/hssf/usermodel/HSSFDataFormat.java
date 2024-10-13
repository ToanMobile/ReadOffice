package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.model.InternalWorkbook;
import com.app.office.fc.hssf.record.FormatRecord;
import com.app.office.fc.ss.usermodel.BuiltinFormats;
import com.app.office.fc.ss.usermodel.DataFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public final class HSSFDataFormat implements DataFormat {
    private static final String[] _builtinFormats = BuiltinFormats.getAll();
    private final Vector<String> _formats = new Vector<>();
    private boolean _movedBuiltins = false;
    private final InternalWorkbook _workbook;

    HSSFDataFormat(InternalWorkbook internalWorkbook) {
        this._workbook = internalWorkbook;
        for (FormatRecord next : internalWorkbook.getFormats()) {
            ensureFormatsSize(next.getIndexCode());
            this._formats.set(next.getIndexCode(), next.getFormatString());
        }
    }

    public static List<String> getBuiltinFormats() {
        return Arrays.asList(_builtinFormats);
    }

    public static short getBuiltinFormat(String str) {
        return (short) BuiltinFormats.getBuiltinFormat(str);
    }

    public short getFormat(String str) {
        if (str.toUpperCase().equals("TEXT")) {
            str = "@";
        }
        if (!this._movedBuiltins) {
            int i = 0;
            while (true) {
                String[] strArr = _builtinFormats;
                if (i >= strArr.length) {
                    break;
                }
                ensureFormatsSize(i);
                if (this._formats.get(i) == null) {
                    this._formats.set(i, strArr[i]);
                }
                i++;
            }
            this._movedBuiltins = true;
        }
        for (int i2 = 0; i2 < this._formats.size(); i2++) {
            if (str.equals(this._formats.get(i2))) {
                return (short) i2;
            }
        }
        short format = this._workbook.getFormat(str, true);
        ensureFormatsSize(format);
        this._formats.set(format, str);
        return format;
    }

    public static String getFormatCode(InternalWorkbook internalWorkbook, short s) {
        if (s == -1) {
            return null;
        }
        for (FormatRecord next : internalWorkbook.getFormats()) {
            if (s == next.getIndexCode()) {
                return next.getFormatString();
            }
        }
        String[] strArr = _builtinFormats;
        if (strArr.length <= s || strArr[s] == null) {
            return null;
        }
        return strArr[s];
    }

    public String getFormat(short s) {
        if (this._movedBuiltins) {
            return this._formats.get(s);
        }
        String str = null;
        if (s == -1) {
            return null;
        }
        if (this._formats.size() > s) {
            str = this._formats.get(s);
        }
        String[] strArr = _builtinFormats;
        if (strArr.length <= s || strArr[s] == null || str != null) {
            return str;
        }
        return strArr[s];
    }

    public static String getBuiltinFormat(short s) {
        return BuiltinFormats.getBuiltinFormat((int) s);
    }

    public static int getNumberOfBuiltinBuiltinFormats() {
        return _builtinFormats.length;
    }

    private void ensureFormatsSize(int i) {
        if (this._formats.size() <= i) {
            this._formats.setSize(i + 1);
        }
    }
}
