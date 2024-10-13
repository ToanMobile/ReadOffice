package com.app.office.macro;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.core.internal.view.SupportMenu;
import com.app.office.constant.EventConstant;
import com.app.office.constant.MainConstant;
import com.app.office.java.awt.Rectangle;
import com.app.office.system.FileKit;
import com.app.office.system.MainControl;
import java.util.Vector;

public class Application {
    public static final byte APPLICATION_TYPE_PDF = 3;
    public static final byte APPLICATION_TYPE_PPT = 2;
    public static final byte APPLICATION_TYPE_SS = 1;
    public static final byte APPLICATION_TYPE_TXT = 0;
    public static final byte APPLICATION_TYPE_WP = 0;
    public static final int DRAWMODE_CALLOUTDRAW = 1;
    public static final int DRAWMODE_CALLOUTERASE = 2;
    public static final int DRAWMODE_NORMAL = 0;
    public static final int MAXZOOM = 30000;
    public static final int MAXZOOM_THUMBNAIL = 5000;
    public static final byte MOVING_HORIZONTAL = 0;
    public static final byte MOVING_VERTICAL = 1;
    public static final int STANDARD_RATE = 10000;
    public static final int THUMBNAILSIZE = 1000;
    /* access modifiers changed from: private */
    public byte applicationType = -1;
    private MacroFrame frame;
    /* access modifiers changed from: private */
    public MainControl mainControl;
    private ViewGroup parent;
    protected Toast toast;

    public Application(Activity activity, ViewGroup viewGroup) {
        MacroFrame macroFrame = new MacroFrame(this, activity);
        this.frame = macroFrame;
        this.mainControl = new MainControl(macroFrame);
        this.parent = viewGroup;
    }

    public void setViewBackground(Object obj) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null && obj != null) {
            if ((obj instanceof Integer) || (obj instanceof Drawable)) {
                macroFrame.setViewBackground(obj);
            }
        }
    }

    public void addOfficeToPictureListener(OfficeToPictureListener officeToPictureListener) {
        if (officeToPictureListener != null) {
            this.mainControl.setOffictToPicture(new MacroOfficeToPicture(officeToPictureListener));
        }
    }

    public void addDialogListener(DialogListener dialogListener) {
        if (dialogListener != null) {
            this.mainControl.setCustomDialog(new MacroCustomDialog(dialogListener));
        }
    }

    public void addSlideShowListener(SlideShowListener slideShowListener) {
        if (slideShowListener != null) {
            this.mainControl.setSlideShow(new MacroSlideShow(slideShowListener));
        }
    }

    public void addOpenFileFinishListener(OpenFileFinishListener openFileFinishListener) {
        this.frame.addOpenFileFinishListener(openFileFinishListener);
    }

    public void addTouchEventListener(TouchEventListener touchEventListener) {
        this.frame.addTouchEventListener(touchEventListener);
    }

    public void addUpdateStatusListener(UpdateStatusListener updateStatusListener) {
        this.frame.addUpdateStatusListener(updateStatusListener);
    }

    public void addErrorListener(ErrorListener errorListener) {
        this.frame.addErrorListener(errorListener);
    }

    public boolean openFile(String str) {
        String lowerCase = str.toLowerCase();
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_DOC) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOCX) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOT) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTM)) {
            this.applicationType = 0;
        } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_XLS) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLSX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLT) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLSM)) {
            this.applicationType = 1;
        } else if (lowerCase.endsWith(MainConstant.FILE_TYPE_PPT) || lowerCase.endsWith(MainConstant.FILE_TYPE_PPTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_POT) || lowerCase.endsWith(MainConstant.FILE_TYPE_PPTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTM)) {
            this.applicationType = 2;
        } else if (lowerCase.endsWith("pdf")) {
            this.applicationType = 3;
        } else {
            MacroFrame macroFrame = this.frame;
            if (macroFrame == null || !macroFrame.isThumbnail()) {
                setDefaultViewMode(1);
            } else {
                setDefaultViewMode(0);
            }
            this.applicationType = 0;
        }
        this.mainControl.openFile(str);
        return true;
    }

    /* access modifiers changed from: protected */
    public void openFileFinish() {
        View view = getView();
        ViewGroup viewGroup = this.parent;
        if (viewGroup != null) {
            viewGroup.addView(view, new ViewGroup.LayoutParams(-1, -1));
        }
    }

    public byte getApplicationType() {
        return this.applicationType;
    }

    public View getView() {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null) {
            return null;
        }
        return mainControl2.getView();
    }

    public void pageDown() {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null) {
            mainControl2.actionEvent(EventConstant.APP_PAGE_DOWN_ID, (Object) null);
        }
    }

    public void pageUp() {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null) {
            mainControl2.actionEvent(EventConstant.APP_PAGE_UP_ID, (Object) null);
        }
    }

    public boolean find(String str) {
        if (this.mainControl == null || str == null || str.trim().length() == 0 || isSlideShowMode()) {
            return false;
        }
        boolean find = this.mainControl.getFind().find(str);
        if (find || !this.mainControl.getMainFrame().isShowFindDlg() || this.applicationType == 3) {
            return find;
        }
        if (this.toast == null) {
            this.toast = Toast.makeText(this.mainControl.getView().getContext(), "", 0);
        }
        this.toast.setText(this.mainControl.getMainFrame().getLocalString("DIALOG_FIND_NOT_FOUND"));
        this.toast.show();
        return find;
    }

    public boolean findBackward() {
        if (this.mainControl == null || isSlideShowMode()) {
            return false;
        }
        boolean findBackward = this.mainControl.getFind().findBackward();
        if (findBackward || !this.mainControl.getMainFrame().isShowFindDlg() || this.applicationType == 3) {
            return findBackward;
        }
        if (this.toast == null) {
            this.toast = Toast.makeText(this.mainControl.getView().getContext(), "", 0);
        }
        this.toast.setText(this.mainControl.getMainFrame().getLocalString("DIALOG_FIND_TO_BEGIN"));
        this.toast.show();
        return findBackward;
    }

    public boolean findForward() {
        if (this.mainControl == null || isSlideShowMode()) {
            return false;
        }
        boolean findForward = this.mainControl.getFind().findForward();
        if (findForward || !this.mainControl.getMainFrame().isShowFindDlg() || this.applicationType == 3) {
            return findForward;
        }
        if (this.toast == null) {
            this.toast = Toast.makeText(this.mainControl.getView().getContext(), "", 0);
        }
        this.toast.setText(this.mainControl.getMainFrame().getLocalString("DIALOG_FIND_TO_END"));
        this.toast.show();
        return findForward;
    }

    public void setTouchZoom(boolean z) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setTouchZoom(z);
        }
    }

    public void setDrawPageNumber(boolean z) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setDrawPageNumber(z);
        }
    }

    public void setShowZoomingMsg(boolean z) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setShowZoomingMsg(z);
        }
    }

    public void setPopUpErrorDlg(boolean z) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setPopUpErrorDlg(z);
        }
    }

    public void setShowPasswordDlg(boolean z) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setShowPasswordDlg(z);
        }
    }

    public void setShowFindDlg(boolean z) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setShowFindDlg(z);
        }
    }

    public void setShowProgressBar(boolean z) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setShowProgressBar(z);
        }
    }

    public void setShowTXTEncodeDlg(boolean z) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setShowTXTEncodeDlg(z);
        }
    }

    public void setTXTDefaultEncode(String str) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setTXTDefaultEncode(str);
        }
    }

    public void setAppName(String str) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setAppName(str);
        }
    }

    public void setTopBarHeight(int i) {
        ViewGroup viewGroup;
        if (this.frame != null && (viewGroup = this.parent) != null && i < viewGroup.getContext().getResources().getDisplayMetrics().heightPixels / 2) {
            this.frame.setTopBarHeight(i);
        }
    }

    public void setBottomBarHeight(int i) {
        ViewGroup viewGroup;
        if (this.frame != null && (viewGroup = this.parent) != null && i < viewGroup.getContext().getResources().getDisplayMetrics().heightPixels / 2) {
            this.frame.setBottomBarHeight(i);
        }
    }

    public void setChangePage(boolean z) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setChangePage(z);
        }
    }

    public int getZoom() {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.APP_ZOOM_ID, (Object) null)) == null) {
            return 10000;
        }
        return Math.round(((Float) actionValue).floatValue() * 10000.0f);
    }

    public int getFitZoom() {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.APP_FIT_ZOOM_ID, (Object) null)) == null) {
            return 10000;
        }
        return Math.round(((Float) actionValue).floatValue() * 10000.0f);
    }

    public void setZoom(int i, int i2, int i3) {
        if (this.mainControl != null && getView() != null && i <= 30000 && i >= getFitZoom() && i != getZoom() && !isSlideShowMode()) {
            if (i2 < 0 || i3 < 0 || i2 > getView().getWidth() || i3 > getView().getHeight()) {
                i2 = Integer.MIN_VALUE;
                i3 = Integer.MIN_VALUE;
            }
            this.mainControl.actionEvent(EventConstant.APP_ZOOM_ID, new int[]{i, i2, i3});
            getView().postInvalidate();
            ViewGroup viewGroup = this.parent;
            if (viewGroup != null) {
                viewGroup.post(new Runnable() {
                    public void run() {
                        if (Application.this.mainControl != null) {
                            try {
                                if (Application.this.applicationType == 1) {
                                    Application.this.mainControl.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
                                } else if (Application.this.applicationType == 0 && Application.this.getViewMode() != 2) {
                                    Application.this.mainControl.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
                                }
                            } catch (Exception unused) {
                            }
                        }
                    }
                });
                if (this.applicationType == 0 && this.frame.isZoomAfterLayoutForWord()) {
                    this.mainControl.actionEvent(EventConstant.WP_LAYOUT_NORMAL_VIEW, (Object) null);
                }
            }
        }
    }

    public void setFitSize(int i) {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null) {
            mainControl2.actionEvent(EventConstant.APP_SET_FIT_SIZE_ID, Integer.valueOf(i));
        }
    }

    public int getFitSizeState() {
        byte b;
        if (this.mainControl == null || (b = this.applicationType) == 1) {
            return 0;
        }
        if (b == 0 && getViewMode() != 2) {
            return 0;
        }
        Object actionValue = this.mainControl.getActionValue(EventConstant.APP_GET_FIT_SIZE_STATE_ID, (Object) null);
        if (actionValue == null) {
            return 3;
        }
        return ((Integer) actionValue).intValue();
    }

    public Bitmap getSnapshot(Bitmap bitmap) {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || bitmap == null || (actionValue = mainControl2.getActionValue(EventConstant.APP_GET_SNAPSHOT_ID, bitmap)) == null) {
            return null;
        }
        return (Bitmap) actionValue;
    }

    public int getPagesCount() {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.APP_COUNT_PAGES_ID, (Object) null)) == null) {
            return -1;
        }
        return ((Integer) actionValue).intValue();
    }

    public int getCurrentPageNumber() {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.APP_CURRENT_PAGE_NUMBER_ID, (Object) null)) == null) {
            return -1;
        }
        return ((Integer) actionValue).intValue();
    }

    public Bitmap getPageToImage(int i) {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || i < 1 || (actionValue = mainControl2.getActionValue(EventConstant.WP_PAGE_TO_IMAGE, Integer.valueOf(i))) == null) {
            return null;
        }
        return (Bitmap) actionValue;
    }

    private Bitmap getAreaToImage(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || i < 1 || (actionValue = mainControl2.getActionValue(EventConstant.APP_PAGEAREA_TO_IMAGE, new int[]{i, i2, i3, i4, i5, i6, i7})) == null) {
            return null;
        }
        return (Bitmap) actionValue;
    }

    public Bitmap getPageAreaToImage(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        if (this.applicationType != 0) {
            return null;
        }
        return getAreaToImage(i, i2, i3, i4, i5, i6, i7);
    }

    public Rectangle getPageSize(int i) {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.WP_GET_PAGE_SIZE, Integer.valueOf(i))) == null) {
            return null;
        }
        return (Rectangle) actionValue;
    }

    public int getViewMode() {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null) {
            return -1;
        }
        Object actionValue = mainControl2.getActionValue(EventConstant.WP_GET_VIEW_MODE, (Object) null);
        if (actionValue == null) {
            return 0;
        }
        return ((Integer) actionValue).intValue();
    }

    public void setDefaultViewMode(int i) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            if (i < 0 || i > 2) {
                i = 0;
            }
            macroFrame.setWordDefaultView((byte) i);
        }
    }

    public void setZoomAfterLayoutForNormalView(boolean z) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setZoomAfterLayoutForWord(z);
        }
    }

    public void showPage(int i) {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null && i >= 0) {
            mainControl2.actionEvent(EventConstant.WP_SHOW_PAGE, Integer.valueOf(i));
        }
    }

    public void switchViewMode(int i) {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null) {
            if (i < 0 || i > 2) {
                i = 0;
            }
            mainControl2.actionEvent(EventConstant.WP_SWITCH_VIEW, Integer.valueOf(i));
        }
    }

    public Vector<String> getAllSheetName() {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.SS_GET_ALL_SHEET_NAME, (Object) null)) == null) {
            return null;
        }
        return (Vector) actionValue;
    }

    public String getSheetName(int i) {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || i < 1 || (actionValue = mainControl2.getActionValue(EventConstant.SS_GET_SHEET_NAME, Integer.valueOf(i))) == null) {
            return null;
        }
        return (String) actionValue;
    }

    public int getCurrentSheetNumber() {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.APP_CURRENT_PAGE_NUMBER_ID, (Object) null)) == null) {
            return -1;
        }
        return ((Integer) actionValue).intValue();
    }

    public int getSheetsCount() {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.APP_COUNT_PAGES_ID, (Object) null)) == null) {
            return -1;
        }
        return ((Integer) actionValue).intValue();
    }

    public void showSheet(int i) {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null && i >= 0) {
            mainControl2.actionEvent(EventConstant.SS_SHOW_SHEET, Integer.valueOf(i));
        }
    }

    public void removeDefaultSheetBarForExcel() {
        this.mainControl.actionEvent(EventConstant.SS_REMOVE_SHEET_BAR, (Object) null);
    }

    public void previousSlide() {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null) {
            mainControl2.actionEvent(EventConstant.APP_PAGE_UP_ID, (Object) null);
        }
    }

    public void nextSlide() {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null) {
            mainControl2.actionEvent(EventConstant.APP_PAGE_DOWN_ID, (Object) null);
        }
    }

    public int getSlidesCount() {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.APP_COUNT_PAGES_ID, (Object) null)) == null) {
            return -1;
        }
        return ((Integer) actionValue).intValue();
    }

    public int getLoadSlidesCount() {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.APP_GET_REAL_PAGE_COUNT_ID, (Object) null)) == null) {
            return -1;
        }
        return ((Integer) actionValue).intValue();
    }

    public int getCurrentSlideNumber() {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.APP_CURRENT_PAGE_NUMBER_ID, (Object) null)) == null) {
            return -1;
        }
        return ((Integer) actionValue).intValue();
    }

    public String getSlideNote(int i) {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || i < 1 || (actionValue = mainControl2.getActionValue(EventConstant.PG_GET_SLIDE_NOTE, Integer.valueOf(i))) == null) {
            return null;
        }
        return (String) actionValue;
    }

    public Rectangle getSlideSize(int i) {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.PG_GET_SLIDE_SIZE, Integer.valueOf(i))) == null) {
            return null;
        }
        return (Rectangle) actionValue;
    }

    public void showSlide(int i) {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null && i >= 0) {
            mainControl2.actionEvent(EventConstant.PG_SHOW_SLIDE_ID, Integer.valueOf(i));
        }
    }

    public Bitmap getSlideToImage(int i) {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || i < 1 || (actionValue = mainControl2.getActionValue(EventConstant.PG_SLIDE_TO_IMAGE, Integer.valueOf(i))) == null) {
            return null;
        }
        return (Bitmap) actionValue;
    }

    public Bitmap getSlideAreaToImage(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        if (this.applicationType != 2) {
            return null;
        }
        return getAreaToImage(i, i2, i3, i4, i5, i6, i7);
    }

    public Bitmap getSlideThumbnail(int i, int i2) {
        MainControl mainControl2;
        Object actionValue;
        if (this.applicationType != 2 || (mainControl2 = this.mainControl) == null || i < 1 || i2 <= 0 || i2 > 5000 || (actionValue = mainControl2.getActionValue(EventConstant.APP_THUMBNAIL_ID, new int[]{i, i2})) == null) {
            return null;
        }
        return (Bitmap) actionValue;
    }

    public void showPDFPage(int i) {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null && i >= 0) {
            mainControl2.actionEvent(EventConstant.PDF_SHOW_PAGE, Integer.valueOf(i));
        }
    }

    public Bitmap getPDFPageToImage(int i) {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || i < 1 || (actionValue = mainControl2.getActionValue(EventConstant.PDF_PAGE_TO_IMAGE, Integer.valueOf(i))) == null) {
            return null;
        }
        return (Bitmap) actionValue;
    }

    public Bitmap getPDFPageAreaToImage(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        if (this.applicationType != 3) {
            return null;
        }
        return getAreaToImage(i, i2, i3, i4, i5, i6, i7);
    }

    public Bitmap getPDFPageThumbnail(int i, int i2) {
        MainControl mainControl2;
        Object actionValue;
        if (this.applicationType != 3 || (mainControl2 = this.mainControl) == null || i < 1 || i2 <= 0 || i2 > 5000 || (actionValue = mainControl2.getActionValue(EventConstant.APP_THUMBNAIL_ID, new int[]{i, i2})) == null) {
            return null;
        }
        return (Bitmap) actionValue;
    }

    public String[] getPDFHyperlinkURL(int i) {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || i < 1 || (actionValue = mainControl2.getActionValue(EventConstant.APP_GET_HYPERLINK_URL_ID, Integer.valueOf(i))) == null) {
            return null;
        }
        return (String[]) actionValue;
    }

    public Rectangle getPDFPageSize(int i) {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.PDF_GET_PAGE_SIZE, Integer.valueOf(i))) == null) {
            return null;
        }
        return (Rectangle) actionValue;
    }

    public void addI18NResID(String str, int i) {
        this.frame.addI18NResID(str, i);
    }

    public boolean authenticatePassword(String str) {
        Object actionValue;
        if (this.mainControl == null || str == null || str.trim().length() == 0 || (actionValue = this.mainControl.getActionValue(EventConstant.APP_AUTHENTICATE_PASSWORD, str)) == null) {
            return false;
        }
        return ((Boolean) actionValue).booleanValue();
    }

    public void passwordVerified(String str) {
        if (this.mainControl != null) {
            if (!authenticatePassword(str)) {
                this.mainControl.getSysKit().getErrorKit().writerLog(new Throwable("Password is incorrect"));
            } else {
                this.mainControl.actionEvent(EventConstant.APP_PASSWORD_OK_INIT, str);
            }
        }
    }

    public void txtEncodeDialogFinished(String str) {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null && str != null) {
            mainControl2.actionEvent(EventConstant.TXT_DIALOG_FINISH_ID, str);
        }
    }

    public void reopenTXT(String str, String str2) {
        if (this.mainControl != null && str != null && str2 != null && str.toLowerCase().endsWith(MainConstant.FILE_TYPE_TXT)) {
            setDefaultViewMode(1);
            this.applicationType = 0;
            this.mainControl.actionEvent(EventConstant.TXT_REOPNE_ID, new String[]{str, str2});
        }
    }

    public boolean isSupport(String str) {
        return FileKit.instance().isSupport(str);
    }

    public void setAnimationDuration(int i) {
        if (this.mainControl != null && !isSlideShowMode()) {
            this.mainControl.actionEvent(EventConstant.PG_SLIDESHOW_DURATION, Integer.valueOf(Math.min(1200, Math.max(i, 100))));
        }
    }

    public void beginSlideShow(int i) {
        if (this.mainControl != null) {
            int i2 = 1;
            if (i < 1 || i > getSlidesCount()) {
                i = 1;
            }
            MainControl mainControl2 = this.mainControl;
            if (i >= 1) {
                i2 = i;
            }
            mainControl2.actionEvent(EventConstant.PG_SLIDESHOW_GEGIN, Integer.valueOf(i2));
        }
    }

    public void exitSlideShow() {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null) {
            mainControl2.actionEvent(EventConstant.PG_SLIDESHOW_END, (Object) null);
        }
    }

    public boolean hasNextSlide_Slideshow() {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.APP_PAGE_DOWN_ID, (Object) null)) == null) {
            return false;
        }
        return ((Boolean) actionValue).booleanValue();
    }

    public boolean hasPreviousSlide_Slideshow() {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.APP_PAGE_UP_ID, (Object) null)) == null) {
            return false;
        }
        return ((Boolean) actionValue).booleanValue();
    }

    public boolean hasNextAction_Slideshow() {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.PG_SLIDESHOW_HASNEXTACTION, (Object) null)) == null) {
            return false;
        }
        return ((Boolean) actionValue).booleanValue();
    }

    public void nextAction_Slideshow() {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null) {
            mainControl2.actionEvent(EventConstant.PG_SLIDESHOW_NEXT, (Object) null);
        }
    }

    public boolean hasPreviousAction_Slideshow() {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || (actionValue = mainControl2.getActionValue(EventConstant.PG_SLIDESHOW_HASPREVIOUSACTION, (Object) null)) == null) {
            return false;
        }
        return ((Boolean) actionValue).booleanValue();
    }

    public void previousAction_Slideshow() {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null) {
            mainControl2.actionEvent(EventConstant.PG_SLIDESHOW_PREVIOUS, (Object) null);
        }
    }

    private boolean isSlideShowMode() {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || this.applicationType != 2 || (actionValue = mainControl2.getActionValue(EventConstant.PG_SLIDESHOW, (Object) null)) == null) {
            return false;
        }
        return ((Boolean) actionValue).booleanValue();
    }

    public boolean isSlideExist(int i) {
        Object actionValue;
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null || this.applicationType != 2 || i <= 0 || (actionValue = mainControl2.getActionValue(EventConstant.PG_SLIDESHOW_SLIDEEXIST, Integer.valueOf(i))) == null) {
            return false;
        }
        return ((Boolean) actionValue).booleanValue();
    }

    public int getSlideAnimationSteps(int i) {
        Object actionValue;
        if (!isSlideExist(i) || (actionValue = this.mainControl.getActionValue(EventConstant.PG_SLIDESHOW_ANIMATIONSTEPS, Integer.valueOf(i))) == null) {
            return -1;
        }
        return ((Integer) actionValue).intValue();
    }

    public Bitmap getSlideshowToImage(int i, int i2) {
        Object actionValue;
        if (isSlideShowMode() || i2 <= 0 || i2 > getSlideAnimationSteps(i) || (actionValue = this.mainControl.getActionValue(EventConstant.PG_SLIDESHOW_SLIDESHOWTOIMAGE, new int[]{i, i2})) == null) {
            return null;
        }
        return (Bitmap) actionValue;
    }

    public String getTemporaryDirectoryPath() {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null) {
            return null;
        }
        return mainControl2.getSysKit().getPictureManage().getPicTempPath();
    }

    public void setWriteLog(boolean z) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setWriteLog(z);
        }
    }

    public void setThumbnail(boolean z) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setThumbnail(z);
        }
    }

    public Bitmap getDocThumbnail(int i) {
        MainControl mainControl2;
        Object actionValue;
        if (this.applicationType != 0 || (mainControl2 = this.mainControl) == null || i <= 0 || i > 5000 || (actionValue = mainControl2.getActionValue(EventConstant.APP_THUMBNAIL_ID, Integer.valueOf(i))) == null) {
            return null;
        }
        return (Bitmap) actionValue;
    }

    public Bitmap getTxtThumbnail(int i) {
        MainControl mainControl2;
        Object actionValue;
        if (this.applicationType != 0 || (mainControl2 = this.mainControl) == null || i <= 0 || i > 5000 || (actionValue = mainControl2.getActionValue(EventConstant.APP_THUMBNAIL_ID, Integer.valueOf(i))) == null) {
            return null;
        }
        return (Bitmap) actionValue;
    }

    public Bitmap getXlsThumbnail(int i, int i2, int i3) {
        MainControl mainControl2;
        Object actionValue;
        if (this.applicationType != 1 || (mainControl2 = this.mainControl) == null || i < 1 || i > 1000 || i2 < 1 || i2 > 1000 || i3 <= 0 || i3 > 5000 || (actionValue = mainControl2.getActionValue(EventConstant.APP_THUMBNAIL_ID, new int[]{i, i2, i3})) == null) {
            return null;
        }
        return (Bitmap) actionValue;
    }

    public void dispose() {
        if (!(this.parent == null || getView() == null)) {
            this.parent.removeView(getView());
        }
        if (this.mainControl.getReader() != null) {
            this.mainControl.getReader().abortReader();
        }
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.dispose();
            this.frame = null;
        }
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null) {
            mainControl2.dispose();
            this.mainControl = null;
        }
        this.parent = null;
    }

    public int getCalloutLineWidth() {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null) {
            return 1;
        }
        return mainControl2.getSysKit().getCalloutManager().getWidth();
    }

    public void setCalloutLineWidth(int i) {
        MainControl mainControl2;
        if (i >= 1 && i <= 10 && (mainControl2 = this.mainControl) != null) {
            mainControl2.getSysKit().getCalloutManager().setWidth(i);
        }
    }

    public int getCalloutColor() {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null) {
            return SupportMenu.CATEGORY_MASK;
        }
        return mainControl2.getSysKit().getCalloutManager().getColor();
    }

    public void setCalloutColor(byte b, byte b2, byte b3, byte b4) {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null) {
            mainControl2.getSysKit().getCalloutManager().setColor((b << 24) | ((b2 << 16) & 16711680) | ((b3 << 8) & 65280) | (b4 & 255));
        }
    }

    public int getDrawingMode() {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 == null) {
            return 0;
        }
        return mainControl2.getSysKit().getCalloutManager().getDrawingMode();
    }

    public void setDrawingMode(int i) {
        MainControl mainControl2 = this.mainControl;
        if (mainControl2 != null && i >= 0 && i <= 2) {
            mainControl2.getSysKit().getCalloutManager().setDrawingMode(i);
            if (i == 1) {
                this.parent.post(new Runnable() {
                    public void run() {
                        Application.this.mainControl.actionEvent(EventConstant.APP_INIT_CALLOUTVIEW_ID, (Object) null);
                    }
                });
            }
        }
    }

    public boolean hasConvertingVectorgraph(int i) {
        return this.mainControl.getSysKit().getPictureManage().hasConvertingVectorgraph(i);
    }

    public void setIgnoreOriginalSize(boolean z) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setIgnoreOriginalSize(z);
        }
    }

    public boolean isIgnoreOriginalSize() {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            return macroFrame.isIgnoreOriginalSize();
        }
        return false;
    }

    public void setPageListViewMovingPosition(byte b) {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            macroFrame.setPageListViewMovingPosition(b);
        }
    }

    public byte getPageListViewMovingPosition() {
        MacroFrame macroFrame = this.frame;
        if (macroFrame != null) {
            return macroFrame.getPageListViewMovingPosition();
        }
        return 0;
    }
}
