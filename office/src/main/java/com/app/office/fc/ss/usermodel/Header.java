package com.app.office.fc.ss.usermodel;

public interface Header extends HeaderFooter {
    String getCenter();

    String getLeft();

    String getRight();

    void setCenter(String str);

    void setLeft(String str);

    void setRight(String str);
}
