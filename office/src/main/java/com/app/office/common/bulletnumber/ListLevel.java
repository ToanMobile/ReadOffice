package com.app.office.common.bulletnumber;

public class ListLevel {
    private byte align;
    private byte followChar;
    private int normalParaCount;
    private int numberFormat;
    private char[] numberText;
    private int paraCount;
    private int specialIndent;
    private int startAt;
    private int textIndent;

    public int getStartAt() {
        return this.startAt;
    }

    public void setStartAt(int i) {
        this.startAt = i;
    }

    public int getNumberFormat() {
        return this.numberFormat;
    }

    public void setNumberFormat(int i) {
        this.numberFormat = i;
    }

    public char[] getNumberText() {
        return this.numberText;
    }

    public void setNumberText(char[] cArr) {
        this.numberText = cArr;
    }

    public byte getAlign() {
        return this.align;
    }

    public void setAlign(byte b) {
        this.align = b;
    }

    public byte getFollowChar() {
        return this.followChar;
    }

    public void setFollowChar(byte b) {
        this.followChar = b;
    }

    public int getTextIndent() {
        return this.textIndent;
    }

    public void setTextIndent(int i) {
        this.textIndent = i;
    }

    public int getSpecialIndent() {
        return this.specialIndent;
    }

    public void setSpecialIndent(int i) {
        this.specialIndent = i;
    }

    public int getParaCount() {
        return this.paraCount;
    }

    public void setParaCount(int i) {
        this.paraCount = i;
    }

    public int getNormalParaCount() {
        return this.normalParaCount;
    }

    public void setNormalParaCount(int i) {
        this.normalParaCount = i;
    }

    public void dispose() {
        this.numberText = null;
    }
}
