package com.app.office.pg.animate;

import android.graphics.Rect;
import com.app.office.pg.animate.IAnimation;

public class EmphanceAnimation extends Animation {
    private static final int ROTATION = 360;

    public EmphanceAnimation(ShapeAnimation shapeAnimation) {
        super(shapeAnimation);
        this.current = new IAnimation.AnimationInformation((Rect) null, 0, 0);
    }

    public EmphanceAnimation(ShapeAnimation shapeAnimation, int i) {
        super(shapeAnimation, i);
        this.current = new IAnimation.AnimationInformation((Rect) null, 0, 0);
    }

    public EmphanceAnimation(ShapeAnimation shapeAnimation, int i, int i2) {
        super(shapeAnimation, i, i2);
        this.current = new IAnimation.AnimationInformation((Rect) null, 0, 0);
    }

    public void animation(int i) {
        if (this.shapeAnim != null && this.shapeAnim.getAnimationType() == 1) {
            emphance(i * this.delay);
        }
    }

    public void start() {
        super.start();
        this.current.setAlpha(255);
        this.current.setAngle(0);
        this.current.setProgress(0.0f);
    }

    public void stop() {
        super.stop();
        if (this.current != null) {
            this.current.setAngle(0);
            this.current.setAlpha(255);
            this.current.setProgress(1.0f);
        }
    }

    private void emphance(int i) {
        if (this.current != null) {
            float f = (float) i;
            if (f < this.duration) {
                float f2 = f / this.duration;
                this.current.setProgress(f2);
                this.current.setAngle((int) (f2 * 360.0f));
                return;
            }
            this.status = 2;
            this.current.setProgress(1.0f);
            this.current.setAngle(0);
        }
    }
}
