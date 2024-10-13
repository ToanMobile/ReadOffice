package com.app.office.system;

public interface IFind {
    void dispose();

    boolean find(String str);

    boolean findBackward();

    boolean findForward();

    int getPageIndex();

    void resetSearchResult();
}
