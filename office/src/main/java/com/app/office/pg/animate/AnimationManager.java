package com.app.office.pg.animate;

import com.app.office.constant.EventConstant;
import com.app.office.system.IControl;
import com.app.office.system.ITimerListener;
import com.app.office.system.beans.ATimer;

public class AnimationManager implements ITimerListener {
    private int actionIndex;
    private IAnimation animation;
    private IControl control;
    private ATimer timer;

    public AnimationManager(IControl iControl) {
        this.control = iControl;
    }

    public void setAnimation(IAnimation iAnimation) {
        ATimer aTimer;
        if (!(this.animation == null || (aTimer = this.timer) == null || !aTimer.isRunning())) {
            this.timer.stop();
            this.animation.stop();
        }
        this.animation = iAnimation;
    }

    public void beginAnimation(int i) {
        if (this.timer == null) {
            this.timer = new ATimer(i, this);
        }
        IAnimation iAnimation = this.animation;
        if (iAnimation != null) {
            this.actionIndex = 0;
            iAnimation.start();
            this.timer.start();
            if (this.control.getOfficeToPicture() != null) {
                this.control.getOfficeToPicture().setModeType((byte) 0);
            }
        }
    }

    public void restartAnimationTimer() {
        ATimer aTimer = this.timer;
        if (aTimer != null) {
            aTimer.restart();
        }
    }

    public void killAnimationTimer() {
        ATimer aTimer = this.timer;
        if (aTimer != null) {
            aTimer.stop();
        }
    }

    public void stopAnimation() {
        if (this.animation != null) {
            ATimer aTimer = this.timer;
            if (aTimer != null) {
                aTimer.stop();
            }
            IAnimation iAnimation = this.animation;
            if (iAnimation != null) {
                iAnimation.stop();
            }
            if (this.control.getOfficeToPicture() != null) {
                this.control.getOfficeToPicture().setModeType((byte) 1);
            }
            this.control.actionEvent(EventConstant.PG_REPAINT_ID, (Object) null);
        }
    }

    public void actionPerformed() {
        IAnimation iAnimation = this.animation;
        if (iAnimation == null || iAnimation.getAnimationStatus() == 2) {
            ATimer aTimer = this.timer;
            if (aTimer != null) {
                aTimer.stop();
            }
            if (this.control.getOfficeToPicture() != null) {
                this.control.getOfficeToPicture().setModeType((byte) 1);
            }
            this.control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
            return;
        }
        IAnimation iAnimation2 = this.animation;
        int i = this.actionIndex + 1;
        this.actionIndex = i;
        iAnimation2.animation(i);
        this.control.actionEvent(EventConstant.PG_REPAINT_ID, (Object) null);
        ATimer aTimer2 = this.timer;
        if (aTimer2 != null) {
            aTimer2.restart();
        }
    }

    public boolean hasStoped() {
        IAnimation iAnimation = this.animation;
        if (iAnimation == null || iAnimation.getAnimationStatus() == 2) {
            return true;
        }
        return false;
    }

    public void dispose() {
        this.control = null;
        this.animation = null;
        ATimer aTimer = this.timer;
        if (aTimer != null) {
            aTimer.dispose();
            this.timer = null;
        }
    }
}
