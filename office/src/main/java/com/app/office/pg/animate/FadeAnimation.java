package com.app.office.pg.animate;

import android.graphics.Rect;
import com.app.office.pg.animate.IAnimation;

public class FadeAnimation extends Animation {
    public FadeAnimation(ShapeAnimation shapeAnimation) {
        super(shapeAnimation);
        initAnimationKeyPoint();
    }

    public FadeAnimation(ShapeAnimation shapeAnimation, int i) {
        super(shapeAnimation, i);
        initAnimationKeyPoint();
    }

    public FadeAnimation(ShapeAnimation shapeAnimation, int i, int i2) {
        super(shapeAnimation, i, i2);
        initAnimationKeyPoint();
    }

    private void initAnimationKeyPoint() {
        int i = 255;
        if (this.shapeAnim != null) {
            this.begin = new IAnimation.AnimationInformation((Rect) null, this.shapeAnim.getAnimationType() == 0 ? 0 : 255, 0);
            this.end = new IAnimation.AnimationInformation((Rect) null, this.shapeAnim.getAnimationType() == 0 ? 255 : 0, 0);
            if (this.shapeAnim.getAnimationType() == 0) {
                i = 0;
            }
            this.current = new IAnimation.AnimationInformation((Rect) null, i, 0);
            return;
        }
        this.begin = new IAnimation.AnimationInformation((Rect) null, 0, 0);
        this.end = new IAnimation.AnimationInformation((Rect) null, 255, 0);
        this.current = new IAnimation.AnimationInformation((Rect) null, 0, 0);
    }

    public void animation(int i) {
        if (this.shapeAnim != null && this.current != null) {
            byte animationType = this.shapeAnim.getAnimationType();
            if (animationType == 0) {
                fadeIn(i * this.delay);
            } else if (animationType == 1) {
                fadeIn(i * this.delay);
            } else if (animationType == 2) {
                fadeOut(i * this.delay);
            }
        }
    }

    public void start() {
        super.start();
        this.current.setProgress(0.0f);
    }

    public void stop() {
        super.stop();
        if (this.current != null) {
            this.current.setAngle(0);
            this.current.setProgress(1.0f);
            if (this.shapeAnim != null) {
                byte animationType = this.shapeAnim.getAnimationType();
                if (animationType == 0) {
                    this.current.setAlpha(255);
                } else if (animationType == 2) {
                    this.current.setAlpha(0);
                }
            }
        }
    }

    private void fadeIn(int i) {
        float f = (float) i;
        if (f < this.duration) {
            float f2 = f / this.duration;
            this.current.setProgress(f2);
            this.current.setAlpha((int) (f2 * 255.0f));
            return;
        }
        this.status = 2;
        this.current.setProgress(1.0f);
        this.current.setAlpha(255);
    }

    private void fadeOut(int i) {
        float f = (float) i;
        if (f < this.duration) {
            float f2 = f / this.duration;
            this.current.setProgress(f2);
            this.current.setAlpha((int) ((1.0f - f2) * 255.0f));
            return;
        }
        this.status = 2;
        this.current.setProgress(1.0f);
        this.current.setAlpha(0);
    }
}
