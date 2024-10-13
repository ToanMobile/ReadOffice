package com.app.office.pg.control;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.ClipboardManager;
import android.view.View;
import com.app.office.common.ICustomDialog;
import com.app.office.common.IOfficeToPicture;
import com.app.office.common.ISlideShow;
import com.app.office.common.hyperlink.Hyperlink;
import com.app.office.constant.EventConstant;
import com.app.office.java.awt.Dimension;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.dialog.NotesDialog;
import com.app.office.pg.model.PGModel;
import com.app.office.system.AbstractControl;
import com.app.office.system.IControl;
import com.app.office.system.IDialogAction;
import com.app.office.system.IFind;
import com.app.office.system.IMainFrame;
import com.app.office.system.SysKit;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class PGControl extends AbstractControl {
    /* access modifiers changed from: private */
    public boolean isDispose;
    private boolean isShowingProgressDlg;
    /* access modifiers changed from: private */
    public IControl mainControl;
    /* access modifiers changed from: private */
    public Presentation pgView;
    /* access modifiers changed from: private */
    public ProgressDialog progressDialog;

    public byte getApplicationType() {
        return 2;
    }

    public Dialog getDialog(Activity activity, int i) {
        return null;
    }

    public void layoutView(int i, int i2, int i3, int i4) {
    }

    public PGControl(IControl iControl, PGModel pGModel, String str) {
        this.mainControl = iControl;
        this.pgView = new Presentation(getMainFrame().getActivity(), pGModel, this);
    }

    public void actionEvent(int i, final Object obj) {
        switch (i) {
            case EventConstant.TEST_REPAINT_ID:
            case EventConstant.PG_REPAINT_ID:
                this.pgView.postInvalidate();
                return;
            case 19:
                this.pgView.init();
                return;
            case 20:
                this.pgView.post(new Runnable() {
                    public void run() {
                        if (!PGControl.this.isDispose && PGControl.this.getMainFrame() != null) {
                            PGControl.this.getMainFrame().updateToolsbarStatus();
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
                if (this.pgView.getParent() != null) {
                    this.pgView.post(new Runnable() {
                        public void run() {
                            if (!PGControl.this.isDispose) {
                                PGControl.this.mainControl.getMainFrame().showProgressBar(((Boolean) obj).booleanValue());
                            }
                        }
                    });
                    return;
                }
                return;
            case 27:
                if (this.pgView.getParent() != null) {
                    this.pgView.post(new Runnable() {
                        public void run() {
                            if (!PGControl.this.isDispose) {
                                PGControl.this.mainControl.getMainFrame().updateViewImages((List) obj);
                            }
                        }
                    });
                    return;
                } else {
                    new Thread() {
                        public void run() {
                            if (!PGControl.this.isDispose) {
                                PGControl.this.mainControl.getMainFrame().updateViewImages((List) obj);
                            }
                        }
                    }.start();
                    return;
                }
            case EventConstant.FILE_COPY_ID:
                ((ClipboardManager) getMainFrame().getActivity().getSystemService("clipboard")).setText(this.pgView.getSelectedText());
                return;
            case EventConstant.APP_ZOOM_ID:
                if (!this.pgView.isSlideShow()) {
                    int[] iArr = (int[]) obj;
                    this.pgView.setZoom(((float) iArr[0]) / 10000.0f, iArr[1], iArr[2]);
                    this.pgView.post(new Runnable() {
                        public void run() {
                            if (!PGControl.this.isDispose) {
                                PGControl.this.getMainFrame().changeZoom();
                            }
                        }
                    });
                    return;
                }
                return;
            case EventConstant.APP_HYPERLINK:
                String address = ((Hyperlink) obj).getAddress();
                if (address != null) {
                    try {
                        getMainFrame().getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(address)));
                        return;
                    } catch (Exception unused) {
                        return;
                    }
                } else {
                    return;
                }
            case EventConstant.APP_GENERATED_PICTURE_ID:
                this.pgView.post(new Runnable() {
                    public void run() {
                        if (!PGControl.this.isDispose) {
                            PGControl.this.pgView.createPicture();
                        }
                    }
                });
                return;
            case EventConstant.APP_PAGE_UP_ID:
                if (this.pgView.isSlideShow()) {
                    this.pgView.slideShow((byte) 4);
                    return;
                } else if (this.pgView.getCurrentIndex() > 0) {
                    Presentation presentation = this.pgView;
                    presentation.showSlide(presentation.getCurrentIndex() - 1, false);
                    return;
                } else {
                    return;
                }
            case EventConstant.APP_PAGE_DOWN_ID:
                if (this.pgView.isSlideShow()) {
                    this.pgView.slideShow((byte) 5);
                    return;
                } else if (this.pgView.getCurrentIndex() < this.pgView.getRealSlideCount() - 1) {
                    Presentation presentation2 = this.pgView;
                    presentation2.showSlide(presentation2.getCurrentIndex() + 1, false);
                    return;
                } else {
                    return;
                }
            case EventConstant.APP_COUNT_PAGES_CHANGE_ID:
                pagesCountChanged();
                return;
            case EventConstant.APP_SET_FIT_SIZE_ID:
                this.pgView.setFitSize(((Integer) obj).intValue());
                return;
            case EventConstant.APP_INIT_CALLOUTVIEW_ID:
                this.pgView.initCalloutView();
                return;
            case EventConstant.PG_NOTE_ID:
                String notes = this.pgView.getCurrentSlide().getNotes().getNotes();
                Vector vector = new Vector();
                vector.add(notes);
                new NotesDialog(this, getMainFrame().getActivity(), (IDialogAction) null, vector, 8).show();
                return;
            case EventConstant.PG_SHOW_SLIDE_ID:
                if (!this.pgView.isSlideShow()) {
                    showSlide(((Integer) obj).intValue());
                    return;
                }
                return;
            case EventConstant.PG_SLIDESHOW_GEGIN:
                getMainFrame().fullScreen(true);
                Presentation presentation3 = this.pgView;
                presentation3.beginSlideShow(obj == null ? presentation3.getCurrentIndex() + 1 : ((Integer) obj).intValue());
                return;
            case EventConstant.PG_SLIDESHOW_END:
                this.pgView.endSlideShow();
                return;
            case EventConstant.PG_SLIDESHOW_PREVIOUS:
                this.pgView.slideShow((byte) 2);
                return;
            case EventConstant.PG_SLIDESHOW_NEXT:
                this.pgView.slideShow((byte) 3);
                return;
            case EventConstant.PG_SLIDESHOW_DURATION:
                this.pgView.setAnimationDuration(((Integer) obj).intValue());
                return;
            default:
                return;
        }
    }

    private void pagesCountChanged() {
        if (this.isShowingProgressDlg && this.pgView.showLoadingSlide()) {
            this.isShowingProgressDlg = false;
            this.pgView.post(new Runnable() {
                public void run() {
                    if (!PGControl.this.getMainFrame().isShowProgressBar()) {
                        ICustomDialog customDialog = PGControl.this.mainControl.getCustomDialog();
                        if (customDialog != null) {
                            customDialog.dismissDialog((byte) 2);
                        }
                    } else if (PGControl.this.progressDialog != null) {
                        PGControl.this.progressDialog.dismiss();
                        ProgressDialog unused = PGControl.this.progressDialog = null;
                    }
                }
            });
        }
    }

    private void showSlide(int i) {
        if (i >= 0 && i < this.pgView.getSlideCount()) {
            this.isShowingProgressDlg = false;
            if (i >= this.pgView.getRealSlideCount()) {
                this.isShowingProgressDlg = true;
                IMainFrame mainFrame = getMainFrame();
                Objects.requireNonNull(mainFrame);
                if (mainFrame.isShowProgressBar()) {
                    this.pgView.postDelayed(new Runnable(this) {
                        public final /* synthetic */ PGControl f$1;

                        {
                            this.f$1 = r2;
                        }

                        public final void run() {
                            PGControl.this.lambda$showSlide$0$PGControl(this.f$1);
                        }
                    }, 200);
                } else {
                    ICustomDialog customDialog = this.mainControl.getCustomDialog();
                    if (customDialog != null) {
                        customDialog.showDialog((byte) 2);
                    }
                }
            }
            this.pgView.showSlide(i, false);
        }
    }

    public /* synthetic */ void lambda$showSlide$0$PGControl(PGControl pGControl) {
        if (this.isShowingProgressDlg) {
            ProgressDialog show = ProgressDialog.show(getActivity(), pGControl.getMainFrame().getAppName(), pGControl.getMainFrame().getLocalString("DIALOG_LOADING"), false, false, (DialogInterface.OnCancelListener) null);
            this.progressDialog = show;
            show.show();
        }
    }

    public Object getActionValue(int i, Object obj) {
        int[] iArr;
        boolean z = false;
        switch (i) {
            case EventConstant.APP_ZOOM_ID:
                return Float.valueOf(this.pgView.getZoom());
            case EventConstant.APP_FIT_ZOOM_ID:
                return Float.valueOf(this.pgView.getFitZoom());
            case EventConstant.APP_COUNT_PAGES_ID:
                return Integer.valueOf(this.pgView.getSlideCount());
            case EventConstant.APP_CURRENT_PAGE_NUMBER_ID:
                return Integer.valueOf(this.pgView.getCurrentIndex() + 1);
            case EventConstant.APP_PAGE_UP_ID:
                return Boolean.valueOf(this.pgView.hasPreviousSlide_Slideshow());
            case EventConstant.APP_PAGE_DOWN_ID:
                return Boolean.valueOf(this.pgView.hasNextSlide_Slideshow());
            case EventConstant.APP_THUMBNAIL_ID:
                if (obj instanceof int[]) {
                    int[] iArr2 = (int[]) obj;
                    if (iArr2.length < 2 || iArr2[1] <= 0) {
                        return null;
                    }
                    return this.pgView.getThumbnail(iArr2[0], ((float) iArr2[1]) / 10000.0f);
                }
                break;
            case EventConstant.APP_PAGEAREA_TO_IMAGE:
                if ((obj instanceof int[]) && (iArr = (int[]) obj) != null && iArr.length == 7) {
                    return this.pgView.slideAreaToImage(iArr[0], iArr[1], iArr[2], iArr[3], iArr[4], iArr[5], iArr[6]);
                }
            case EventConstant.APP_GET_FIT_SIZE_STATE_ID:
                Presentation presentation = this.pgView;
                if (presentation != null) {
                    return Integer.valueOf(presentation.getFitSizeState());
                }
                break;
            case EventConstant.APP_GET_REAL_PAGE_COUNT_ID:
                return Integer.valueOf(this.pgView.getRealSlideCount());
            case EventConstant.APP_GET_SNAPSHOT_ID:
                Presentation presentation2 = this.pgView;
                if (presentation2 != null) {
                    return presentation2.getSnapshot((Bitmap) obj);
                }
                break;
            case EventConstant.PG_SLIDE_TO_IMAGE:
                return this.pgView.slideToImage(((Integer) obj).intValue());
            case EventConstant.PG_GET_SLIDE_NOTE:
                return this.pgView.getSldieNote(((Integer) obj).intValue());
            case EventConstant.PG_GET_SLIDE_SIZE:
                int intValue = ((Integer) obj).intValue();
                if (intValue <= 0 || intValue > this.pgView.getSlideCount()) {
                    return null;
                }
                Dimension pageSize = this.pgView.getPageSize();
                return new Rectangle(0, 0, pageSize.width, pageSize.height);
            case EventConstant.PG_SLIDESHOW:
                return Boolean.valueOf(this.pgView.isSlideShow());
            case EventConstant.PG_SLIDESHOW_HASPREVIOUSACTION:
                return Boolean.valueOf(this.pgView.hasPreviousAction_Slideshow());
            case EventConstant.PG_SLIDESHOW_HASNEXTACTION:
                return Boolean.valueOf(this.pgView.hasNextAction_Slideshow());
            case EventConstant.PG_SLIDESHOW_SLIDEEXIST:
                if (((Integer) obj).intValue() <= this.pgView.getRealSlideCount()) {
                    z = true;
                }
                return Boolean.valueOf(z);
            case EventConstant.PG_SLIDESHOW_ANIMATIONSTEPS:
                return Integer.valueOf(this.pgView.getSlideAnimationSteps(((Integer) obj).intValue()));
            case EventConstant.PG_SLIDESHOW_SLIDESHOWTOIMAGE:
                if (obj instanceof int[]) {
                    int[] iArr3 = (int[]) obj;
                    if (iArr3.length < 2 || iArr3[1] <= 0) {
                        return null;
                    }
                    return this.pgView.getSlideshowToImage(iArr3[0], iArr3[1]);
                }
                break;
        }
        return null;
    }

    public int getCurrentViewIndex() {
        return this.pgView.getCurrentIndex() + 1;
    }

    public View getView() {
        return this.pgView;
    }

    public IMainFrame getMainFrame() {
        return this.mainControl.getMainFrame();
    }

    public Activity getActivity() {
        return this.mainControl.getMainFrame().getActivity();
    }

    public IFind getFind() {
        return this.pgView.getFind();
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

    public boolean isSlideShow() {
        return this.pgView.isSlideShow();
    }

    public ISlideShow getSlideShow() {
        return this.mainControl.getSlideShow();
    }

    public SysKit getSysKit() {
        return this.mainControl.getSysKit();
    }

    public void dispose() {
        this.isDispose = true;
        this.pgView.dispose();
        this.pgView = null;
        this.mainControl = null;
    }
}
