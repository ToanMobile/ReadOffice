package com.app.office.fc.ss.usermodel;

import com.app.office.fc.hssf.usermodel.IClientAnchor;

public interface Drawing {
    IClientAnchor createAnchor(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    Comment createCellComment(IClientAnchor iClientAnchor);

    Chart createChart(IClientAnchor iClientAnchor);

    Picture createPicture(IClientAnchor iClientAnchor, int i);
}
