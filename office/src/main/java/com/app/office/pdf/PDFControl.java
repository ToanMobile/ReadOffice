package com.app.office.pdf;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;
import com.app.office.common.ICustomDialog;
import com.app.office.common.IOfficeToPicture;
import com.app.office.constant.EventConstant;
import com.app.office.fc.pdf.PDFHyperlinkInfo;
import com.app.office.fc.pdf.PDFLib;
import com.app.office.java.awt.Rectangle;
import com.app.office.system.AbstractControl;
import com.app.office.system.IControl;
import com.app.office.system.IFind;
import com.app.office.system.IMainFrame;
import com.app.office.system.SysKit;
import java.util.ArrayList;

public class PDFControl extends AbstractControl {
    /* access modifiers changed from: private */
    public boolean isDispose;
    /* access modifiers changed from: private */
    public IControl mainControl;
    /* access modifiers changed from: private */
    public PDFView pdfView;

    public byte getApplicationType() {
        return 3;
    }

    public PDFControl(IControl iControl, PDFLib pDFLib, String str) {
        this.mainControl = iControl;
        this.pdfView = new PDFView(iControl.getMainFrame().getActivity().getApplicationContext(), pDFLib, this);
    }

    public void actionEvent(int i, final Object obj) {
        switch (i) {
            case 19:
                this.pdfView.init();
                return;
            case 20:
                this.pdfView.post(new Runnable() {
                    public void run() {
                        if (!PDFControl.this.isDispose) {
                            PDFControl.this.getMainFrame().updateToolsbarStatus();
                        }
                    }
                });
                return;
            case 22:
                if (isAutoTest()) {
                    getMainFrame().getActivity().onBackPressed();
                    return;
                }
                return;
            case 26:
                this.pdfView.post(new Runnable() {
                    public void run() {
                        if (!PDFControl.this.isDispose) {
                            PDFControl.this.mainControl.getMainFrame().showProgressBar(((Boolean) obj).booleanValue());
                        }
                    }
                });
                return;
            case EventConstant.APP_ZOOM_ID:
                int[] iArr = (int[]) obj;
                this.pdfView.setZoom(((float) iArr[0]) / 10000.0f, iArr[1], iArr[2]);
                return;
            case EventConstant.APP_PAGE_UP_ID:
                this.pdfView.previousPageview();
                return;
            case EventConstant.APP_PAGE_DOWN_ID:
                this.pdfView.nextPageView();
                return;
            case EventConstant.APP_PASSWORD_OK_INIT:
                this.pdfView.post(new Runnable() {
                    public void run() {
                        if (!PDFControl.this.isDispose) {
                            PDFControl.this.pdfView.passwordVerified();
                        }
                    }
                });
                return;
            case EventConstant.APP_SET_FIT_SIZE_ID:
                this.pdfView.setFitSize(((Integer) obj).intValue());
                return;
            case EventConstant.APP_INIT_CALLOUTVIEW_ID:
                this.pdfView.getListView().getCurrentPageView().initCalloutView();
                return;
            case EventConstant.PDF_SHOW_PAGE:
                int intValue = ((Integer) obj).intValue();
                if (intValue >= 0 && intValue < this.pdfView.getPageCount()) {
                    this.pdfView.showPDFPageForIndex(intValue);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public Object getActionValue(int i, Object obj) {
        int intValue;
        PDFHyperlinkInfo[] hyperlinkInfoSync;
        int[] iArr;
        switch (i) {
            case EventConstant.APP_ZOOM_ID:
                return Float.valueOf(this.pdfView.getZoom());
            case EventConstant.APP_FIT_ZOOM_ID:
                return Float.valueOf(this.pdfView.getFitZoom());
            case EventConstant.APP_COUNT_PAGES_ID:
                return Integer.valueOf(this.pdfView.getPageCount());
            case EventConstant.APP_CURRENT_PAGE_NUMBER_ID:
                return Integer.valueOf(this.pdfView.getCurrentPageNumber());
            case EventConstant.APP_THUMBNAIL_ID:
                if (obj instanceof int[]) {
                    int[] iArr2 = (int[]) obj;
                    if (iArr2.length < 2 || iArr2[1] <= 0) {
                        return null;
                    }
                    return this.pdfView.getThumbnail(iArr2[0], ((float) iArr2[1]) / 10000.0f);
                }
                break;
            case EventConstant.APP_AUTHENTICATE_PASSWORD:
                PDFView pDFView = this.pdfView;
                if (!(pDFView == null || obj == null)) {
                    return Boolean.valueOf(pDFView.getPDFLib().authenticatePasswordSync((String) obj));
                }
            case EventConstant.APP_PAGEAREA_TO_IMAGE:
                if ((obj instanceof int[]) && (iArr = (int[]) obj) != null && iArr.length == 7) {
                    return this.pdfView.pageAreaToImage(iArr[0], iArr[1], iArr[2], iArr[3], iArr[4], iArr[5], iArr[6]);
                }
            case EventConstant.APP_GET_HYPERLINK_URL_ID:
                break;
            case EventConstant.APP_GET_FIT_SIZE_STATE_ID:
                PDFView pDFView2 = this.pdfView;
                if (pDFView2 != null) {
                    return Integer.valueOf(pDFView2.getFitSizeState());
                }
                break;
            case EventConstant.APP_GET_SNAPSHOT_ID:
                PDFView pDFView3 = this.pdfView;
                if (pDFView3 != null) {
                    return pDFView3.getSanpshot((Bitmap) obj);
                }
                break;
            case EventConstant.PDF_PAGE_TO_IMAGE:
                return this.pdfView.pageToImage(((Integer) obj).intValue());
            case EventConstant.PDF_GET_PAGE_SIZE:
                Rect pageSize = this.pdfView.getPageSize(((Integer) obj).intValue() - 1);
                if (pageSize != null) {
                    return new Rectangle(0, 0, pageSize.width(), pageSize.height());
                }
                return null;
        }
        if (this.pdfView != null && obj != null && (intValue = ((Integer) obj).intValue() - 1) >= 0 && intValue < this.pdfView.getPDFLib().getPageCountSync() && (hyperlinkInfoSync = this.pdfView.getPDFLib().getHyperlinkInfoSync(intValue)) != null && hyperlinkInfoSync.length > 0) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < hyperlinkInfoSync.length; i2++) {
                if (hyperlinkInfoSync[i2] != null) {
                    arrayList.add(hyperlinkInfoSync[i2].getURL());
                }
            }
            if (arrayList.size() > 0) {
                return arrayList.toArray(new String[arrayList.size()]);
            }
        }
        return null;
    }

    public View getView() {
        return this.pdfView;
    }

    public IMainFrame getMainFrame() {
        return this.mainControl.getMainFrame();
    }

    public Activity getActivity() {
        return getMainFrame().getActivity();
    }

    public IFind getFind() {
        return this.pdfView.getFind();
    }

    public boolean isAutoTest() {
        return this.mainControl.isAutoTest();
    }

    public IOfficeToPicture getOfficeToPicture() {
        return this.mainControl.getOfficeToPicture();
    }

    public ICustomDialog getCustomDialog() {
        return this.mainControl.getCustomDialog();
    }

    public SysKit getSysKit() {
        return this.mainControl.getSysKit();
    }

    public void dispose() {
        this.isDispose = true;
        this.pdfView.dispose();
        this.pdfView = null;
        this.mainControl = null;
    }
}
