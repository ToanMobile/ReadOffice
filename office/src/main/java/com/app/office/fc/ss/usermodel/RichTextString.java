package com.app.office.fc.ss.usermodel;

public interface RichTextString {
    void applyFont(int i, int i2, IFont iFont);

    void applyFont(int i, int i2, short s);

    void applyFont(IFont iFont);

    void applyFont(short s);

    void clearFormatting();

    int getIndexOfFormattingRun(int i);

    String getString();

    int length();

    int numFormattingRuns();
}
