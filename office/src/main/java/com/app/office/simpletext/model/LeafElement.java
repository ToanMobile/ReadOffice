package com.app.office.simpletext.model;

public class LeafElement extends AbstractElement {
    private String text;

    public short getType() {
        return 1;
    }

    public LeafElement(String str) {
        this.text = str;
    }

    public String getText(IDocument iDocument) {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
        setEndOffset(getStartOffset() + ((long) str.length()));
    }

    public void dispose() {
        super.dispose();
        this.text = null;
    }
}
