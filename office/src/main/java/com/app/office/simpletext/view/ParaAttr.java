package com.app.office.simpletext.view;

public class ParaAttr {
    public int afterSpace;
    public int beforeSpace;
    public byte horizontalAlignment;
    public int leftIndent;
    public byte lineSpaceType;
    public float lineSpaceValue;
    public int listAlignIndent;
    public int listID;
    public byte listLevel;
    public int listTextIndent;
    public int pgBulletID;
    public int rightIndent;
    public int specialIndentValue;
    public int tabClearPosition;
    public byte verticalAlignment;

    public void dispose() {
    }

    public void reset() {
        this.leftIndent = 0;
        this.rightIndent = 0;
        this.specialIndentValue = 0;
        this.lineSpaceType = -1;
        this.lineSpaceValue = 0.0f;
        this.beforeSpace = 0;
        this.afterSpace = 0;
        this.verticalAlignment = 0;
        this.horizontalAlignment = 0;
        this.listID = -1;
        this.listLevel = -1;
        this.listTextIndent = 0;
        this.listAlignIndent = 0;
        this.pgBulletID = -1;
        this.tabClearPosition = 0;
    }
}
