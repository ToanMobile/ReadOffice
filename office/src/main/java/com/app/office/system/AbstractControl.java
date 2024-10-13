package com.app.office.system;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import com.app.office.common.ICustomDialog;
import com.app.office.common.IOfficeToPicture;
import com.app.office.common.ISlideShow;
import com.app.office.constant.MainConstant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\b\u0010\t\u001a\u00020\u0004H\u0016J\u001c\u0010\n\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\n\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0006H\u0016J\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0006H\u0016J\n\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\n\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\n\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\n\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\n\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\n\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010\"\u001a\u00020#H\u0016J\b\u0010$\u001a\u00020#H\u0016J(\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u00062\u0006\u0010'\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\u0006\u0010)\u001a\u00020\u0006H\u0016J\u0010\u0010*\u001a\u00020#2\u0006\u0010+\u001a\u00020,H\u0016¨\u0006-"}, d2 = {"Lcom/reader/office/system/AbstractControl;", "Lcom/reader/office/system/IControl;", "()V", "actionEvent", "", "actionID", "", "obj", "", "dispose", "getActionValue", "getActivity", "Landroid/app/Activity;", "getApplicationType", "", "getCurrentViewIndex", "getCustomDialog", "Lcom/reader/office/common/ICustomDialog;", "getDialog", "Landroid/app/Dialog;", "activity", "id", "getFind", "Lcom/reader/office/system/IFind;", "getMainFrame", "Lcom/reader/office/system/IMainFrame;", "getOfficeToPicture", "Lcom/reader/office/common/IOfficeToPicture;", "getReader", "Lcom/reader/office/system/IReader;", "getSlideShow", "Lcom/reader/office/common/ISlideShow;", "getView", "Landroid/view/View;", "isAutoTest", "", "isSlideShow", "layoutView", "x", "y", "w", "h", "openFile", "filePath", "", "office_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: AbstractControl.kt */
public abstract class AbstractControl implements IControl {
    public void actionEvent(int i, Object obj) {
    }

    public void dispose() {
    }

    public Object getActionValue(int i, Object obj) {
        return null;
    }

    public Activity getActivity() {
        return null;
    }

    public byte getApplicationType() {
        return -1;
    }

    public int getCurrentViewIndex() {
        return -1;
    }

    public ICustomDialog getCustomDialog() {
        return null;
    }

    public Dialog getDialog(Activity activity, int i) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        return null;
    }

    public IFind getFind() {
        return null;
    }

    public IMainFrame getMainFrame() {
        return null;
    }

    public IOfficeToPicture getOfficeToPicture() {
        return null;
    }

    public IReader getReader() {
        return null;
    }

    public ISlideShow getSlideShow() {
        return null;
    }

    public View getView() {
        return null;
    }

    public boolean isAutoTest() {
        return false;
    }

    public boolean isSlideShow() {
        return false;
    }

    public void layoutView(int i, int i2, int i3, int i4) {
    }

    public boolean openFile(String str) {
        Intrinsics.checkNotNullParameter(str, MainConstant.INTENT_FILED_FILE_PATH);
        return false;
    }
}
