package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;

public abstract class AbstractExtTextOut extends EMFTag implements EMFConstants {
    private Rectangle bounds;
    private int mode;
    private float xScale;
    private float yScale;

    public abstract Text getText();

    protected AbstractExtTextOut(int i, int i2, Rectangle rectangle, int i3, float f, float f2) {
        super(i, i2);
        this.bounds = rectangle;
        this.mode = i3;
        this.xScale = f;
        this.yScale = f2;
    }

    public String toString() {
        return super.toString() + "\n  bounds: " + this.bounds + "\n  mode: " + this.mode + "\n  xScale: " + this.xScale + "\n  yScale: " + this.yScale + "\n" + getText().toString();
    }

    public void render(EMFRenderer eMFRenderer) {
        Text text = getText();
        eMFRenderer.drawOrAppendText(text.getString(), (float) text.getPos().x, (float) text.getPos().y);
    }
}
