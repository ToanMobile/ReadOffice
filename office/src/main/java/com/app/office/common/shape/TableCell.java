package com.app.office.common.shape;

import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Line;
import com.app.office.java.awt.Rectanglef;

public class TableCell {
    private BackgroundAndFill bgFill;
    private Line bottom;
    private Line left;
    protected Rectanglef rect;
    private Line right;
    private TextBox textBox;
    private Line top;

    public Line getLeftLine() {
        return this.left;
    }

    public void setLeftLine(Line line) {
        this.left = line;
    }

    public Line getRightLine() {
        return this.right;
    }

    public void setRightLine(Line line) {
        this.right = line;
    }

    public Line getTopLine() {
        return this.top;
    }

    public void setTopLine(Line line) {
        this.top = line;
    }

    public Line getBottomLine() {
        return this.bottom;
    }

    public void setBottomLine(Line line) {
        this.bottom = line;
    }

    public TextBox getText() {
        return this.textBox;
    }

    public void setText(TextBox textBox2) {
        this.textBox = textBox2;
    }

    public Rectanglef getBounds() {
        return this.rect;
    }

    public void setBounds(Rectanglef rectanglef) {
        this.rect = rectanglef;
    }

    public BackgroundAndFill getBackgroundAndFill() {
        return this.bgFill;
    }

    public void setBackgroundAndFill(BackgroundAndFill backgroundAndFill) {
        this.bgFill = backgroundAndFill;
    }

    public void dispose() {
        TextBox textBox2 = this.textBox;
        if (textBox2 != null) {
            textBox2.dispose();
            this.textBox = null;
        }
        this.rect = null;
        BackgroundAndFill backgroundAndFill = this.bgFill;
        if (backgroundAndFill != null) {
            backgroundAndFill.dispose();
            this.bgFill = null;
        }
    }
}
