package com.app.office.fc.sl.usermodel;

public interface Sheet extends ShapeContainer {
    Background getBackground();

    MasterSheet getMasterSheet();

    SlideShow getSlideShow();
}
