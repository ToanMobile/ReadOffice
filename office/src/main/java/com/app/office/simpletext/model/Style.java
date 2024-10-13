package com.app.office.simpletext.model;

public class Style {
    private IAttributeSet attr = new AttributeSetImpl();
    private int baseID = -1;
    private int id = -1;
    private String name;
    private byte type;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getBaseID() {
        return this.baseID;
    }

    public void setBaseID(int i) {
        this.baseID = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte b) {
        this.type = b;
    }

    public IAttributeSet getAttrbuteSet() {
        return this.attr;
    }

    public void setAttrbuteSet(IAttributeSet iAttributeSet) {
        this.attr = iAttributeSet;
    }

    public void dispose() {
        this.name = null;
        IAttributeSet iAttributeSet = this.attr;
        if (iAttributeSet != null) {
            iAttributeSet.dispose();
            this.attr = null;
        }
    }
}
