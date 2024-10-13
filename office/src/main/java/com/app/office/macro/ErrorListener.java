package com.app.office.macro;

public interface ErrorListener {
    public static final int BAD_FILE = 2;
    public static final int INSUFFICIENT_MEMORY = 0;
    public static final int OLD_DOCUMENT = 3;
    public static final int PARSE_ERROR = 4;
    public static final int PASSWORD_DOCUMENT = 6;
    public static final int PASSWORD_INCORRECT = 7;
    public static final int RTF_DOCUMENT = 5;
    public static final int SD_CARD_ERROR = 8;
    public static final int SD_CARD_NOSPACELEFT = 10;
    public static final int SD_CARD_WRITEDENIED = 9;
    public static final int SYSTEM_CRASH = 1;

    void error(int i);
}
