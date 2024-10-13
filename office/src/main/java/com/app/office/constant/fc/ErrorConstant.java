package com.app.office.constant.fc;

import com.app.office.fc.ss.usermodel.ErrorConstants;
import java.io.PrintStream;

public class ErrorConstant {
    private static final ErrorConstant DIV_0 = new ErrorConstant(7);
    private static final ErrorConstants EC = null;
    private static final ErrorConstant NA = new ErrorConstant(42);
    private static final ErrorConstant NAME = new ErrorConstant(29);
    private static final ErrorConstant NULL = new ErrorConstant(0);
    private static final ErrorConstant NUM = new ErrorConstant(36);
    private static final ErrorConstant REF = new ErrorConstant(23);
    private static final ErrorConstant VALUE = new ErrorConstant(15);
    private final int _errorCode;

    private ErrorConstant(int i) {
        this._errorCode = i;
    }

    public int getErrorCode() {
        return this._errorCode;
    }

    public String getText() {
        if (ErrorConstants.isValidCode(this._errorCode)) {
            return ErrorConstants.getText(this._errorCode);
        }
        return "unknown error code (" + this._errorCode + ")";
    }

    public static ErrorConstant valueOf(int i) {
        if (i == 0) {
            return NULL;
        }
        if (i == 7) {
            return DIV_0;
        }
        if (i == 15) {
            return VALUE;
        }
        if (i == 23) {
            return REF;
        }
        if (i == 29) {
            return NAME;
        }
        if (i == 36) {
            return NUM;
        }
        if (i == 42) {
            return NA;
        }
        PrintStream printStream = System.err;
        printStream.println("Warning - unexpected error code (" + i + ")");
        return new ErrorConstant(i);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(64);
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [");
        stringBuffer.append(getText());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
