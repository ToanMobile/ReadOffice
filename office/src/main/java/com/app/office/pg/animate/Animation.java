package com.app.office.pg.animate;

import com.app.office.pg.animate.IAnimation;

public class Animation implements IAnimation {
    public static final byte AnimStatus_Animating = 1;
    public static final byte AnimStatus_End = 2;
    public static final byte AnimStatus_NotStarted = 0;
    public static final int Duration = 1200;
    private static final int FPS = 15;
    public static final byte FadeIn = 0;
    public static final byte FadeOut = 1;
    protected IAnimation.AnimationInformation begin;
    protected IAnimation.AnimationInformation current;
    protected int delay;
    protected float duration;
    protected IAnimation.AnimationInformation end;
    protected int fps;
    protected ShapeAnimation shapeAnim;
    protected byte status;

    public void animation(int i) {
    }

    public Animation(ShapeAnimation shapeAnimation) {
        this(shapeAnimation, 1200, 15);
    }

    public Animation(ShapeAnimation shapeAnimation, int i) {
        this(shapeAnimation, i, 15);
    }

    public Animation(ShapeAnimation shapeAnimation, int i, int i2) {
        this.shapeAnim = shapeAnimation;
        this.duration = (float) i;
        this.fps = i2;
        this.delay = 1000 / i2;
        this.status = 0;
    }

    public void start() {
        this.status = 1;
    }

    public void stop() {
        this.status = 2;
    }

    public IAnimation.AnimationInformation getCurrentAnimationInfor() {
        return this.current;
    }

    public ShapeAnimation getShapeAnimation() {
        return this.shapeAnim;
    }

    public void setDuration(int i) {
        this.duration = (float) i;
    }

    public int getDuration() {
        return (int) this.duration;
    }

    public int getFPS() {
        return this.fps;
    }

    public byte getAnimationStatus() {
        return this.status;
    }

    public void dispose() {
        this.shapeAnim = null;
        IAnimation.AnimationInformation animationInformation = this.begin;
        if (animationInformation != null) {
            animationInformation.dispose();
            this.begin = null;
        }
        IAnimation.AnimationInformation animationInformation2 = this.end;
        if (animationInformation2 != null) {
            animationInformation2.dispose();
            this.end = null;
        }
        IAnimation.AnimationInformation animationInformation3 = this.current;
        if (animationInformation3 != null) {
            animationInformation3.dispose();
            this.current = null;
        }
    }
}
