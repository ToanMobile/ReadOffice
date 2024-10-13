package com.app.office.system;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import com.app.office.common.ICustomDialog;
import com.app.office.common.IOfficeToPicture;
import com.app.office.common.ISlideShow;

public interface IControl {
    void actionEvent(int i, Object obj);

    void dispose();

    Object getActionValue(int i, Object obj);

    Activity getActivity();

    byte getApplicationType();

    int getCurrentViewIndex();

    ICustomDialog getCustomDialog();

    Dialog getDialog(Activity activity, int i);

    IFind getFind();

    IMainFrame getMainFrame();

    IOfficeToPicture getOfficeToPicture();

    IReader getReader();

    ISlideShow getSlideShow();

    SysKit getSysKit();

    View getView();

    boolean isAutoTest();

    boolean isSlideShow();

    void layoutView(int i, int i2, int i3, int i4);

    boolean openFile(String str);
}
