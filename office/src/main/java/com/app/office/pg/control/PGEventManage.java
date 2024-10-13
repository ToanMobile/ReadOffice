package com.app.office.pg.control;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import com.app.office.system.IControl;
import com.app.office.system.beans.AEventManage;

public class PGEventManage extends AEventManage {
    private Presentation presentation;

    public PGEventManage(Presentation presentation2, IControl iControl) {
        super(presentation2.getContext(), iControl);
        this.presentation = presentation2;
        presentation2.setOnTouchListener(this);
        presentation2.setLongClickable(true);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        super.onTouch(view, motionEvent);
        return false;
    }

    public boolean onDoubleTap(MotionEvent motionEvent) {
        super.onDoubleTap(motionEvent);
        return true;
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        super.onScroll(motionEvent, motionEvent2, f, f2);
        return true;
    }

    public void fling(int i, int i2) {
        if (!this.presentation.isSlideShow()) {
            return;
        }
        if (Math.abs(i2) >= 400 || Math.abs(i) >= 400) {
            super.fling(i, i2);
            int currentIndex = this.presentation.getCurrentIndex();
            if (Math.abs(i2) > Math.abs(i)) {
                if (i2 < 0 && currentIndex >= 0) {
                    this.presentation.slideShow((byte) 3);
                } else if (i2 > 0 && currentIndex <= this.presentation.getRealSlideCount() - 1) {
                    this.presentation.slideShow((byte) 2);
                }
            } else if (i < 0 && currentIndex >= 0) {
                this.presentation.slideShow((byte) 4);
            } else if (i > 0 && currentIndex < this.presentation.getRealSlideCount() - 1) {
                this.presentation.slideShow((byte) 5);
            }
        } else {
            this.presentation.slideShow((byte) 3);
        }
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        super.onSingleTapUp(motionEvent);
        if (motionEvent.getAction() == 1) {
            Rect slideDrawingRect = this.presentation.getSlideDrawingRect();
            if (this.presentation.isSlideShow() && slideDrawingRect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                this.presentation.slideShow((byte) 3);
            }
        }
        return true;
    }

    public void dispose() {
        super.dispose();
        this.presentation = null;
    }
}
