package com.app.office.common.shape;

import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Line;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.animate.IAnimation;

public class AbstractShape implements IShape {
    public static final short SHAPE_AUTOSHAPE = 2;
    public static final short SHAPE_BG_FILL = 3;
    public static final short SHAPE_CHART = 5;
    public static final short SHAPE_GROUP = 7;
    public static final short SHAPE_LINE = 4;
    public static final short SHAPE_PICTURE = 0;
    public static final short SHAPE_SMARTART = 8;
    public static final short SHAPE_TABLE = 6;
    public static final short SHAPE_TEXTBOX = 1;
    private float angle;
    private IAnimation animation;
    private BackgroundAndFill bgFill;
    private boolean flipH;
    private boolean flipV;
    private int grpSpID = -1;
    private boolean hasLine;
    private boolean hidden;
    private int id;
    private Line line;
    private IShape parent;
    private int placeHolderID;
    protected Rectangle rect;

    public Object getData() {
        return null;
    }

    public short getType() {
        return -1;
    }

    public void setData(Object obj) {
    }

    public IShape getParent() {
        return this.parent;
    }

    public void setParent(IShape iShape) {
        this.parent = iShape;
    }

    public void setGroupShapeID(int i) {
        this.grpSpID = i;
    }

    public int getGroupShapeID() {
        return this.grpSpID;
    }

    public void setShapeID(int i) {
        this.id = i;
    }

    public int getShapeID() {
        return this.id;
    }

    public Rectangle getBounds() {
        return this.rect;
    }

    public void setBounds(Rectangle rectangle) {
        this.rect = rectangle;
    }

    public BackgroundAndFill getBackgroundAndFill() {
        return this.bgFill;
    }

    public void setBackgroundAndFill(BackgroundAndFill backgroundAndFill) {
        this.bgFill = backgroundAndFill;
    }

    public boolean getFlipHorizontal() {
        return this.flipH;
    }

    public void setFlipHorizontal(boolean z) {
        this.flipH = z;
    }

    public boolean getFlipVertical() {
        return this.flipV;
    }

    public void setFlipVertical(boolean z) {
        this.flipV = z;
    }

    public float getRotation() {
        return this.angle;
    }

    public void setRotation(float f) {
        this.angle = f;
    }

    public void setHidden(boolean z) {
        this.hidden = z;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setAnimation(IAnimation iAnimation) {
        this.animation = iAnimation;
    }

    public IAnimation getAnimation() {
        return this.animation;
    }

    public boolean hasLine() {
        return this.line != null;
    }

    public void setLine(boolean z) {
        this.hasLine = z;
        if (z && this.line == null) {
            this.line = new Line();
        }
    }

    public void setLine(Line line2) {
        this.line = line2;
        if (line2 != null) {
            this.hasLine = true;
        }
    }

    public Line createLine() {
        Line line2 = new Line();
        this.line = line2;
        return line2;
    }

    public Line getLine() {
        return this.line;
    }

    public int getPlaceHolderID() {
        return this.placeHolderID;
    }

    public void setPlaceHolderID(int i) {
        this.placeHolderID = i;
    }

    public void dispose() {
        IShape iShape = this.parent;
        if (iShape != null) {
            iShape.dispose();
            this.parent = null;
        }
        this.rect = null;
        IAnimation iAnimation = this.animation;
        if (iAnimation != null) {
            iAnimation.dispose();
            this.animation = null;
        }
        BackgroundAndFill backgroundAndFill = this.bgFill;
        if (backgroundAndFill != null) {
            backgroundAndFill.dispose();
            this.bgFill = null;
        }
        Line line2 = this.line;
        if (line2 != null) {
            line2.dispose();
            this.line = null;
        }
    }
}
