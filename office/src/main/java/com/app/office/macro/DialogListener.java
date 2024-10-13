package com.app.office.macro;

public interface DialogListener {
    public static final byte DIALOGTYPE_ENCODE = 1;
    public static final byte DIALOGTYPE_ERROR = 3;
    public static final byte DIALOGTYPE_FIND = 4;
    public static final byte DIALOGTYPE_LOADING = 2;
    public static final byte DIALOGTYPE_PASSWORD = 0;

    void dismissDialog(byte b);

    void showDialog(byte b);
}
