package com.app.office.fc.hslf.model;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.java.awt.Rectangle;

public final class TableCell extends TextBox {
    protected static final int DEFAULT_HEIGHT = 40;
    protected static final int DEFAULT_WIDTH = 100;
    private Line borderBottom;
    private Line borderLeft;
    private Line borderRight;
    private Line borderTop;

    protected TableCell(EscherContainerRecord escherContainerRecord, Shape shape) {
        super(escherContainerRecord, shape);
    }

    public TableCell(Shape shape) {
        super(shape);
        setShapeType(1);
    }

    /* access modifiers changed from: protected */
    public EscherContainerRecord createSpContainer(boolean z) {
        this._escherContainer = super.createSpContainer(z);
        EscherOptRecord escherOptRecord = (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085);
        setEscherProperty(escherOptRecord, 128, 0);
        setEscherProperty(escherOptRecord, EscherProperties.TEXT__SIZE_TEXT_TO_FIT_SHAPE, 131072);
        setEscherProperty(escherOptRecord, EscherProperties.FILL__NOFILLHITTEST, 1376257);
        setEscherProperty(escherOptRecord, EscherProperties.SHADOWSTYLE__SHADOWOBSURED, 131072);
        setEscherProperty(escherOptRecord, EscherProperties.PROTECTION__LOCKAGAINSTGROUPING, 262144);
        return this._escherContainer;
    }

    /* access modifiers changed from: protected */
    public void anchorBorder(int i, Line line) {
        Rectangle anchor = getAnchor();
        Rectangle rectangle = new Rectangle();
        if (i == 1) {
            rectangle.x = anchor.x;
            rectangle.y = anchor.y;
            rectangle.width = anchor.width;
            rectangle.height = 0;
        } else if (i == 2) {
            rectangle.x = anchor.x + anchor.width;
            rectangle.y = anchor.y;
            rectangle.width = 0;
            rectangle.height = anchor.height;
        } else if (i == 3) {
            rectangle.x = anchor.x;
            rectangle.y = anchor.y + anchor.height;
            rectangle.width = anchor.width;
            rectangle.height = 0;
        } else if (i == 4) {
            rectangle.x = anchor.x;
            rectangle.y = anchor.y;
            rectangle.width = 0;
            rectangle.height = anchor.height;
        } else {
            throw new IllegalArgumentException("Unknown border type: " + i);
        }
        line.setAnchor(rectangle);
    }

    public Line getBorderLeft() {
        return this.borderLeft;
    }

    public void setBorderLeft(Line line) {
        if (line != null) {
            anchorBorder(4, line);
        }
        this.borderLeft = line;
    }

    public Line getBorderRight() {
        return this.borderRight;
    }

    public void setBorderRight(Line line) {
        if (line != null) {
            anchorBorder(2, line);
        }
        this.borderRight = line;
    }

    public Line getBorderTop() {
        return this.borderTop;
    }

    public void setBorderTop(Line line) {
        if (line != null) {
            anchorBorder(1, line);
        }
        this.borderTop = line;
    }

    public Line getBorderBottom() {
        return this.borderBottom;
    }

    public void setBorderBottom(Line line) {
        if (line != null) {
            anchorBorder(3, line);
        }
        this.borderBottom = line;
    }

    public void setAnchor(Rectangle rectangle) {
        super.setAnchor(rectangle);
        Line line = this.borderTop;
        if (line != null) {
            anchorBorder(1, line);
        }
        Line line2 = this.borderRight;
        if (line2 != null) {
            anchorBorder(2, line2);
        }
        Line line3 = this.borderBottom;
        if (line3 != null) {
            anchorBorder(3, line3);
        }
        Line line4 = this.borderLeft;
        if (line4 != null) {
            anchorBorder(4, line4);
        }
    }
}
