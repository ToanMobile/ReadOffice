package com.app.office.common.bg;

import android.graphics.Rect;
import android.graphics.Shader;
import com.app.office.system.IControl;

public abstract class AShader {
    protected int alpha = 255;
    protected Shader shader = null;

    public Shader getShader() {
        return this.shader;
    }

    public Shader createShader(IControl iControl, int i, Rect rect) {
        return this.shader;
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setAlpha(int i) {
        this.alpha = i;
    }

    public void dispose() {
        this.shader = null;
    }
}
