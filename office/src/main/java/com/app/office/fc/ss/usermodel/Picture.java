package com.app.office.fc.ss.usermodel;

import com.app.office.fc.hssf.usermodel.IClientAnchor;

public interface Picture {
    PictureData getPictureData();

    IClientAnchor getPreferredSize();

    void resize();

    void resize(double d);
}
