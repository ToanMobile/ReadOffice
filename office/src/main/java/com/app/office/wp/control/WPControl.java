package com.app.office.wp.control;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.ClipboardManager;
import android.view.MotionEvent;
import android.view.View;
import com.app.office.common.ICustomDialog;
import com.app.office.common.IOfficeToPicture;
import com.app.office.common.bookmark.Bookmark;
import com.app.office.common.hyperlink.Hyperlink;
import com.app.office.constant.EventConstant;
import com.app.office.simpletext.model.IDocument;
import com.app.office.system.AbstractControl;
import com.app.office.system.IControl;
import com.app.office.system.IFind;
import com.app.office.system.IMainFrame;
import com.app.office.system.SysKit;
import com.app.office.wp.dialog.TXTEncodingDialog;
import java.util.List;
import java.util.Vector;

public class WPControl extends AbstractControl {
    /* access modifiers changed from: private */
    public boolean isDispose;
    /* access modifiers changed from: private */
    public IControl mainControl;
    /* access modifiers changed from: private */
    public Word wpView;

    public byte getApplicationType() {
        return 0;
    }

    public void layoutView(int i, int i2, int i3, int i4) {
    }

    public WPControl(IControl iControl, IDocument iDocument, String str) {
        this.mainControl = iControl;
        this.wpView = new Word(iControl.getMainFrame().getActivity().getApplicationContext(), iDocument, str, this);
    }

    public void actionEvent(int i, final Object obj) {
        int i2 = 0;
        switch (i) {
            case EventConstant.TEST_REPAINT_ID:
                this.wpView.postInvalidate();
                return;
            case 19:
                this.wpView.init();
                return;
            case 20:
                updateStatus();
                return;
            case 22:
                this.wpView.post(new Runnable() {
                    public void run() {
                        if (!WPControl.this.isDispose) {
                            WPControl.this.mainControl.getMainFrame().showProgressBar(false);
                        }
                    }
                });
                if (isAutoTest()) {
                    getMainFrame().getActivity().onBackPressed();
                    return;
                }
                return;
            case 26:
                if (this.wpView.getParent() != null) {
                    this.wpView.post(new Runnable() {
                        public void run() {
                            if (!WPControl.this.isDispose) {
                                WPControl.this.mainControl.getMainFrame().showProgressBar(((Boolean) obj).booleanValue());
                            }
                        }
                    });
                    return;
                }
                return;
            case 27:
                if (this.wpView.getParent() != null) {
                    this.wpView.post(new Runnable() {
                        public void run() {
                            if (!WPControl.this.isDispose) {
                                WPControl.this.mainControl.getMainFrame().updateViewImages((List) obj);
                            }
                        }
                    });
                    return;
                } else {
                    new Thread() {
                        public void run() {
                            if (!WPControl.this.isDispose) {
                                WPControl.this.mainControl.getMainFrame().updateViewImages((List) obj);
                            }
                        }
                    }.start();
                    return;
                }
            case EventConstant.FILE_COPY_ID:
                ((ClipboardManager) getActivity().getSystemService("clipboard")).setText(this.wpView.getHighlight().getSelectText());
                return;
            case EventConstant.APP_INTERNET_SEARCH_ID:
                ControlKit.instance().internetSearch(this.wpView);
                return;
            case EventConstant.APP_ZOOM_ID:
                int[] iArr = (int[]) obj;
                this.wpView.setZoom(((float) iArr[0]) / 10000.0f, iArr[1], iArr[2]);
                this.wpView.post(new Runnable() {
                    public void run() {
                        if (!WPControl.this.isDispose) {
                            WPControl.this.getMainFrame().changeZoom();
                        }
                    }
                });
                return;
            case EventConstant.APP_HYPERLINK:
                Hyperlink hyperlink = (Hyperlink) obj;
                if (hyperlink != null) {
                    try {
                        if (hyperlink.getLinkType() == 5) {
                            Bookmark bookmark = getSysKit().getBookmarkManage().getBookmark(hyperlink.getAddress());
                            if (bookmark != null) {
                                ControlKit.instance().gotoOffset(this.wpView, bookmark.getStart());
                                return;
                            }
                            return;
                        }
                        getMainFrame().getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(hyperlink.getAddress())));
                        return;
                    } catch (Exception unused) {
                        return;
                    }
                } else {
                    return;
                }
            case EventConstant.APP_GENERATED_PICTURE_ID:
                exportImage();
                return;
            case EventConstant.APP_PAGE_UP_ID:
                if (this.wpView.getCurrentRootType() != 1) {
                    Word word = this.wpView;
                    word.showPage(word.getCurrentPageNumber() - 2, EventConstant.APP_PAGE_UP_ID);
                } else if (this.wpView.getEventManage() != null) {
                    this.wpView.getEventManage().onScroll((MotionEvent) null, (MotionEvent) null, 0.0f, (float) ((-this.wpView.getHeight()) + 10));
                }
                if (this.wpView.getCurrentRootType() != 2) {
                    updateStatus();
                    exportImage();
                    return;
                }
                return;
            case EventConstant.APP_PAGE_DOWN_ID:
                if (this.wpView.getCurrentRootType() != 1) {
                    Word word2 = this.wpView;
                    word2.showPage(word2.getCurrentPageNumber(), EventConstant.APP_PAGE_DOWN_ID);
                } else if (this.wpView.getEventManage() != null) {
                    this.wpView.getEventManage().onScroll((MotionEvent) null, (MotionEvent) null, 0.0f, (float) (this.wpView.getHeight() + 10));
                }
                if (this.wpView.getCurrentRootType() != 2) {
                    updateStatus();
                    exportImage();
                    return;
                }
                return;
            case EventConstant.APP_SET_FIT_SIZE_ID:
                this.wpView.setFitSize(((Integer) obj).intValue());
                return;
            case EventConstant.APP_INIT_CALLOUTVIEW_ID:
                this.wpView.getPrintWord().getListView().getCurrentPageView().initCalloutView();
                return;
            case EventConstant.WP_SELECT_TEXT_ID:
                this.wpView.getStatus().setSelectTextStatus(!this.wpView.getStatus().isSelectTextStatus());
                return;
            case EventConstant.WP_SWITCH_VIEW:
                if (obj != null) {
                    i2 = ((Integer) obj).intValue();
                } else if (this.wpView.getCurrentRootType() == 0) {
                    i2 = 1;
                }
                this.wpView.switchView(i2);
                updateStatus();
                if (i2 != 2) {
                    exportImage();
                    return;
                }
                return;
            case EventConstant.WP_SHOW_PAGE:
                this.wpView.showPage(((Integer) obj).intValue(), EventConstant.WP_SHOW_PAGE);
                if (this.wpView.getCurrentRootType() != 2) {
                    updateStatus();
                    exportImage();
                    return;
                }
                return;
            case EventConstant.WP_LAYOUT_NORMAL_VIEW:
                if (this.wpView.getCurrentRootType() == 1) {
                    this.wpView.setExportImageAfterZoom(true);
                    this.wpView.layoutNormal();
                    return;
                }
                return;
            case EventConstant.WP_PRINT_MODE:
                if (this.wpView.getCurrentRootType() != 2) {
                    this.wpView.switchView(2);
                    updateStatus();
                    return;
                }
                return;
            case EventConstant.WP_LAYOUT_COMPLETED:
                Word word3 = this.wpView;
                if (word3 != null) {
                    word3.updateFieldText();
                    if (this.wpView.getParent() == null) {
                        getMainFrame().completeLayout();
                        return;
                    } else {
                        this.wpView.post(new Runnable() {
                            public void run() {
                                WPControl.this.getMainFrame().completeLayout();
                            }
                        });
                        return;
                    }
                } else {
                    return;
                }
            default:
                return;
        }
    }

    public Object getActionValue(int i, Object obj) {
        int[] iArr;
        switch (i) {
            case EventConstant.APP_ZOOM_ID:
                return Float.valueOf(this.wpView.getZoom());
            case EventConstant.APP_FIT_ZOOM_ID:
                return Float.valueOf(this.wpView.getFitZoom());
            case EventConstant.APP_COUNT_PAGES_ID:
                return Integer.valueOf(this.wpView.getPageCount());
            case EventConstant.APP_CURRENT_PAGE_NUMBER_ID:
                return Integer.valueOf(this.wpView.getCurrentPageNumber());
            case EventConstant.APP_THUMBNAIL_ID:
                if (obj instanceof Integer) {
                    return this.wpView.getThumbnail(((float) ((Integer) obj).intValue()) / 10000.0f);
                }
                return null;
            case EventConstant.APP_PAGEAREA_TO_IMAGE:
                if (!(obj instanceof int[]) || (iArr = (int[]) obj) == null || iArr.length != 7) {
                    return null;
                }
                return this.wpView.pageAreaToImage(iArr[0], iArr[1], iArr[2], iArr[3], iArr[4], iArr[5], iArr[6]);
            case EventConstant.APP_GET_FIT_SIZE_STATE_ID:
                Word word = this.wpView;
                if (word != null) {
                    return Integer.valueOf(word.getFitSizeState());
                }
                return null;
            case EventConstant.APP_GET_SNAPSHOT_ID:
                Word word2 = this.wpView;
                if (word2 != null) {
                    return word2.getSnapshot((Bitmap) obj);
                }
                return null;
            case EventConstant.WP_SELECT_TEXT_ID:
                return Boolean.valueOf(this.wpView.getStatus().isSelectTextStatus());
            case EventConstant.WP_PAGE_TO_IMAGE:
                return this.wpView.pageToImage(((Integer) obj).intValue());
            case EventConstant.WP_GET_PAGE_SIZE:
                return this.wpView.getPageSize(((Integer) obj).intValue() - 1);
            case EventConstant.WP_GET_VIEW_MODE:
                return Integer.valueOf(this.wpView.getCurrentRootType());
            default:
                return null;
        }
    }

    private void exportImage() {
        this.wpView.post(new Runnable() {
            public void run() {
                if (!WPControl.this.isDispose) {
                    WPControl.this.wpView.createPicture();
                }
            }
        });
    }

    private void updateStatus() {
        this.wpView.post(new Runnable() {
            public void run() {
                if (!WPControl.this.isDispose) {
                    WPControl.this.getMainFrame().updateToolsbarStatus();
                }
            }
        });
    }

    public int getCurrentViewIndex() {
        return this.wpView.getCurrentPageNumber();
    }

    public View getView() {
        return this.wpView;
    }

    public Dialog getDialog(Activity activity, int i) {
        if (i != 1) {
            return null;
        }
        Vector vector = new Vector();
        vector.add(this.wpView.getFilePath());
        new TXTEncodingDialog(this, activity, this.wpView.getDialogAction(), vector, i).show();
        return null;
    }

    public IMainFrame getMainFrame() {
        return this.mainControl.getMainFrame();
    }

    public Activity getActivity() {
        return getMainFrame().getActivity();
    }

    public IFind getFind() {
        return this.wpView.getFind();
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
        this.wpView.dispose();
        this.wpView = null;
        this.mainControl = null;
    }
}
