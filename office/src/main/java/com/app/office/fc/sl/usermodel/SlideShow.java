package com.app.office.fc.sl.usermodel;

import java.io.IOException;

public interface SlideShow {
    MasterSheet createMasterSheet() throws IOException;

    Slide createSlide() throws IOException;

    MasterSheet[] getMasterSheet();

    Resources getResources();

    Slide[] getSlides();
}
