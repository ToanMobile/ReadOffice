package com.app.office.fc.sl.usermodel;

public interface SimpleShape extends Shape {
    Fill getFill();

    Hyperlink getHyperlink();

    LineStyle getLineStyle();

    void setHyperlink(Hyperlink hyperlink);
}
