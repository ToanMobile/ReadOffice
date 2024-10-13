package com.app.office.ss.model.drawing;

public class CellAnchor {
    public static final short ONECELLANCHOR = 0;
    public static final short TWOCELLANCHOR = 1;
    private AnchorPoint end;
    private int height;
    protected AnchorPoint start;
    private short type = 1;
    private int width;

    public CellAnchor(short s) {
        this.type = s;
    }

    public short getType() {
        return this.type;
    }

    public void setStart(AnchorPoint anchorPoint) {
        this.start = anchorPoint;
    }

    public AnchorPoint getStart() {
        return this.start;
    }

    public void setEnd(AnchorPoint anchorPoint) {
        this.end = anchorPoint;
    }

    public AnchorPoint getEnd() {
        return this.end;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void dispose() {
        AnchorPoint anchorPoint = this.start;
        if (anchorPoint != null) {
            anchorPoint.dispose();
            this.start = null;
        }
        AnchorPoint anchorPoint2 = this.end;
        if (anchorPoint2 != null) {
            anchorPoint2.dispose();
            this.end = null;
        }
    }
}
