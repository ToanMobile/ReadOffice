package com.app.office.pg.animate;

import android.graphics.Rect;

public interface IAnimation {
    void animation(int i);

    void dispose();

    byte getAnimationStatus();

    AnimationInformation getCurrentAnimationInfor();

    int getDuration();

    int getFPS();

    ShapeAnimation getShapeAnimation();

    void setDuration(int i);

    void start();

    void stop();

    public static class AnimationInformation {
        public static final int ROTATION = 720;
        private int alpha;
        private int angle;
        private Rect postion;
        private float progress;

        public AnimationInformation(Rect rect, int i, int i2) {
            if (rect != null) {
                this.postion = new Rect(rect);
            }
            this.alpha = i;
            this.angle = i2;
        }

        public void setAnimationInformation(Rect rect, int i, int i2) {
            if (rect != null) {
                this.postion = new Rect(rect);
            }
            this.alpha = i;
            this.angle = i2;
            this.progress = 0.0f;
        }

        public void setProgress(float f) {
            this.progress = f;
        }

        public float getProgress() {
            return this.progress;
        }

        public void setPostion(Rect rect) {
            if (rect != null) {
                this.postion = new Rect(rect);
            }
        }

        public Rect getPostion() {
            return this.postion;
        }

        public void setAlpha(int i) {
            this.alpha = i;
        }

        public int getAlpha() {
            return this.alpha;
        }

        public void setAngle(int i) {
            this.angle = i;
        }

        public int getAngle() {
            return this.angle;
        }

        public void dispose() {
            this.postion = null;
        }
    }
}
