package com.app.office.common.shape;

public class LineShape extends AutoShape {
    private Arrow endArrow;
    private Arrow startArrow;

    public short getType() {
        return 4;
    }

    public void createStartArrow(byte b, int i, int i2) {
        Arrow arrow = this.startArrow;
        if (arrow == null) {
            this.startArrow = new Arrow(b, i, i2);
            return;
        }
        arrow.setType(b);
        this.startArrow.setWidth(i);
        this.startArrow.setLength(i2);
    }

    public void createEndArrow(byte b, int i, int i2) {
        Arrow arrow = this.endArrow;
        if (arrow == null) {
            this.endArrow = new Arrow(b, i, i2);
            return;
        }
        arrow.setType(b);
        this.endArrow.setWidth(i);
        this.endArrow.setLength(i2);
    }

    public boolean getStartArrowhead() {
        return this.startArrow != null;
    }

    public int getStartArrowWidth() {
        Arrow arrow = this.startArrow;
        if (arrow != null) {
            return arrow.getWidth();
        }
        return -1;
    }

    public void setStartArrowWidth(int i) {
        Arrow arrow = this.startArrow;
        if (arrow != null) {
            arrow.setWidth(i);
        }
    }

    public int getStartArrowLength() {
        Arrow arrow = this.startArrow;
        if (arrow != null) {
            return arrow.getLength();
        }
        return -1;
    }

    public void setStartArrowLength(int i) {
        Arrow arrow = this.startArrow;
        if (arrow != null) {
            arrow.setLength(i);
        }
    }

    public byte getStartArrowType() {
        Arrow arrow = this.startArrow;
        if (arrow != null) {
            return arrow.getType();
        }
        return 0;
    }

    public void setStartArrowType(byte b) {
        Arrow arrow = this.startArrow;
        if (arrow != null) {
            arrow.setType(b);
        }
    }

    public boolean getEndArrowhead() {
        return this.endArrow != null;
    }

    public int getEndArrowWidth() {
        Arrow arrow = this.endArrow;
        if (arrow != null) {
            return arrow.getWidth();
        }
        return -1;
    }

    public void setEndArrowWidth(int i) {
        Arrow arrow = this.endArrow;
        if (arrow != null) {
            arrow.setWidth(i);
        }
    }

    public int getEndArrowLength() {
        Arrow arrow = this.endArrow;
        if (arrow != null) {
            return arrow.getLength();
        }
        return -1;
    }

    public void setEndArrowLength(int i) {
        Arrow arrow = this.endArrow;
        if (arrow != null) {
            arrow.setLength(i);
        }
    }

    public byte getEndArrowType() {
        Arrow arrow = this.endArrow;
        if (arrow != null) {
            return arrow.getType();
        }
        return 0;
    }

    public void setEndArrowType(byte b) {
        Arrow arrow = this.endArrow;
        if (arrow != null) {
            arrow.setType(b);
        }
    }

    public Arrow getStartArrow() {
        return this.startArrow;
    }

    public Arrow getEndArrow() {
        return this.endArrow;
    }

    public void dispose() {
        this.startArrow = null;
        this.endArrow = null;
    }
}
