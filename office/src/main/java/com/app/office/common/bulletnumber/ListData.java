package com.app.office.common.bulletnumber;

public class ListData {
    private boolean isNumber;
    private ListLevel[] levels;
    private short[] linkStyle;
    private short linkStyleID = -1;
    private int listID;
    private byte normalPreParaLevel;
    private byte preParaLevel;
    private byte simpleList;

    public int getListID() {
        return this.listID;
    }

    public void setListID(int i) {
        this.listID = i;
    }

    public byte getSimpleList() {
        return this.simpleList;
    }

    public void setSimpleList(byte b) {
        this.simpleList = b;
    }

    public short[] getLinkStyle() {
        return this.linkStyle;
    }

    public void setLinkStyle(short[] sArr) {
        this.linkStyle = sArr;
    }

    public boolean isNumber() {
        return this.isNumber;
    }

    public void setNumber(boolean z) {
        this.isNumber = z;
    }

    public ListLevel[] getLevels() {
        return this.levels;
    }

    public void setLevels(ListLevel[] listLevelArr) {
        this.levels = listLevelArr;
    }

    public ListLevel getLevel(int i) {
        ListLevel[] listLevelArr = this.levels;
        if (i < listLevelArr.length) {
            return listLevelArr[i];
        }
        return null;
    }

    public byte getPreParaLevel() {
        return this.preParaLevel;
    }

    public void setPreParaLevel(byte b) {
        this.preParaLevel = b;
    }

    public byte getNormalPreParaLevel() {
        return this.normalPreParaLevel;
    }

    public void setNormalPreParaLevel(byte b) {
        this.normalPreParaLevel = b;
    }

    public void resetForNormalView() {
        ListLevel[] listLevelArr = this.levels;
        if (listLevelArr != null) {
            for (ListLevel normalParaCount : listLevelArr) {
                normalParaCount.setNormalParaCount(0);
            }
        }
    }

    public short getLinkStyleID() {
        return this.linkStyleID;
    }

    public void setLinkStyleID(short s) {
        this.linkStyleID = s;
    }

    public void dispose() {
        ListLevel[] listLevelArr = this.levels;
        if (listLevelArr != null) {
            for (ListLevel dispose : listLevelArr) {
                dispose.dispose();
            }
            this.levels = null;
        }
    }
}
