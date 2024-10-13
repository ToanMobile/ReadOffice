package com.app.office.simpletext.view;

public interface IRoot {
    public static final int MINLAYOUTWIDTH = 5;

    void backLayout();

    boolean canBackLayout();

    ViewContainer getViewContainer();
}
