package com.app.office.common;

public interface ISlideShow {
    public static final byte SlideShow_Begin = 0;
    public static final byte SlideShow_Exit = 1;
    public static final byte SlideShow_NextSlide = 5;
    public static final byte SlideShow_NextStep = 3;
    public static final byte SlideShow_PreviousSlide = 4;
    public static final byte SlideShow_PreviousStep = 2;

    void exit();
}
