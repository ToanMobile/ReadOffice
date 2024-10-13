package com.app.office.macro;

public interface UpdateStatusListener {
    public static final byte ALLPages = -1;

    void changePage();

    void changeZoom();

    void completeLayout();

    void updateStatus();

    void updateViewImage(Integer[] numArr);
}
