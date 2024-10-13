package com.app.office.common.borders;

import com.app.office.common.bg.BackgroundAndFill;

public class Line extends Border {
    private BackgroundAndFill bgFill;
    private boolean dash;

    public Line() {
        setLineWidth(1);
    }

    public BackgroundAndFill getBackgroundAndFill() {
        return this.bgFill;
    }

    public void setBackgroundAndFill(BackgroundAndFill backgroundAndFill) {
        this.bgFill = backgroundAndFill;
    }

    public void setDash(boolean z) {
        this.dash = z;
    }

    public boolean isDash() {
        return this.dash;
    }

    public void dispose() {
        if (this.bgFill != null) {
            this.bgFill = null;
        }
    }
}
