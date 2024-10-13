package com.app.office.macro;

import com.app.office.common.ISlideShow;

public class MacroSlideShow implements ISlideShow {
    private SlideShowListener listener;

    protected MacroSlideShow(SlideShowListener slideShowListener) {
        this.listener = slideShowListener;
    }

    public void exit() {
        SlideShowListener slideShowListener = this.listener;
        if (slideShowListener != null) {
            slideShowListener.exit();
        }
    }

    public void dispose() {
        this.listener = null;
    }
}
