package com.app.office.ss.control;

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
import com.app.office.common.hyperlink.Hyperlink;
import com.app.office.constant.EventConstant;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.util.ReferenceUtil;
import com.app.office.ss.view.SheetView;
import com.app.office.system.AbstractControl;
import com.app.office.system.IControl;
import com.app.office.system.IFind;
import com.app.office.system.IMainFrame;
import com.app.office.system.SysKit;
import java.util.List;
import java.util.Vector;

public class SSControl extends AbstractControl {
    private ExcelView excelView;
    /* access modifiers changed from: private */
    public boolean isDispose;
    /* access modifiers changed from: private */
    public IControl mainControl;
    /* access modifiers changed from: private */
    public Spreadsheet spreadSheet;

    public byte getApplicationType() {
        return 1;
    }

    public Dialog getDialog(Activity activity, int i) {
        return null;
    }

    public void layoutView(int i, int i2, int i3, int i4) {
    }

    public SSControl(IControl iControl, Workbook workbook, String str) {
        this.mainControl = iControl;
        ExcelView excelView2 = new ExcelView(getMainFrame().getActivity(), str, workbook, this);
        this.excelView = excelView2;
        this.spreadSheet = excelView2.getSpreadsheet();
    }

    public void actionEvent(int i, final Object obj) {
        int i2 = 0;
        switch (i) {
            case EventConstant.TEST_REPAINT_ID:
                this.spreadSheet.postInvalidate();
                return;
            case 19:
                this.excelView.init();
                return;
            case 22:
                if (this.mainControl.isAutoTest()) {
                    getMainFrame().getActivity().onBackPressed();
                    return;
                }
                return;
            case 26:
                if (this.spreadSheet.getParent() != null) {
                    this.spreadSheet.post(new Runnable() {
                        public void run() {
                            if (!SSControl.this.isDispose) {
                                SSControl.this.mainControl.getMainFrame().showProgressBar(((Boolean) obj).booleanValue());
                            }
                        }
                    });
                    return;
                }
                return;
            case 27:
                if (this.spreadSheet.getParent() != null) {
                    this.spreadSheet.post(new Runnable() {
                        public void run() {
                            if (!SSControl.this.isDispose) {
                                SSControl.this.mainControl.getMainFrame().updateViewImages((List) obj);
                            }
                        }
                    });
                    return;
                } else {
                    new Thread() {
                        public void run() {
                            if (!SSControl.this.isDispose) {
                                SSControl.this.mainControl.getMainFrame().updateViewImages((List) obj);
                            }
                        }
                    }.start();
                    return;
                }
            case EventConstant.FILE_COPY_ID:
                ((ClipboardManager) getMainFrame().getActivity().getSystemService("clipboard")).setText(this.spreadSheet.getActiveCellContent());
                return;
            case EventConstant.APP_INTERNET_SEARCH_ID:
                getSysKit().internetSearch(this.spreadSheet.getActiveCellContent(), getMainFrame().getActivity());
                return;
            case EventConstant.APP_ZOOM_ID:
                this.spreadSheet.setZoom(((float) ((int[]) obj)[0]) / 10000.0f);
                this.spreadSheet.post(new Runnable() {
                    public void run() {
                        if (!SSControl.this.isDispose) {
                            SSControl.this.getMainFrame().changeZoom();
                            SSControl.this.updateStatus();
                        }
                    }
                });
                return;
            case EventConstant.APP_CONTENT_SELECTED:
                updateStatus();
                return;
            case EventConstant.APP_HYPERLINK:
                Hyperlink activeCellHyperlink = this.spreadSheet.getActiveCellHyperlink();
                if (activeCellHyperlink != null) {
                    try {
                        if (activeCellHyperlink.getLinkType() == 2) {
                            String address = activeCellHyperlink.getAddress();
                            int indexOf = address.indexOf("!");
                            String replace = address.substring(0, indexOf).replace("'", "");
                            String substring = address.substring(indexOf + 1, address.length());
                            int rowIndex = ReferenceUtil.instance().getRowIndex(substring);
                            int columnIndex = ReferenceUtil.instance().getColumnIndex(substring);
                            this.spreadSheet.getWorkbook().getSheet(replace).setActiveCellRowCol(rowIndex, columnIndex);
                            this.excelView.showSheet(replace);
                            int i3 = rowIndex - 1;
                            int i4 = columnIndex - 1;
                            SheetView sheetView = this.spreadSheet.getSheetView();
                            if (i3 < 0) {
                                i3 = 0;
                            }
                            if (i4 >= 0) {
                                i2 = i4;
                            }
                            sheetView.goToCell(i3, i2);
                            getMainFrame().doActionEvent(20, (Object) null);
                            this.spreadSheet.postInvalidate();
                            return;
                        }
                        if (activeCellHyperlink.getLinkType() != 3) {
                            if (activeCellHyperlink.getLinkType() != 1) {
                                this.mainControl.actionEvent(17, "not supported hyperlink!");
                                return;
                            }
                        }
                        getMainFrame().getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(activeCellHyperlink.getAddress())));
                        return;
                    } catch (Exception unused) {
                        return;
                    }
                } else {
                    return;
                }
            case EventConstant.APP_ABORTREADING:
                if (this.mainControl.getReader() != null) {
                    this.mainControl.getReader().abortReader();
                    return;
                }
                return;
            case EventConstant.APP_GENERATED_PICTURE_ID:
                exportImage();
                return;
            case EventConstant.APP_PAGE_UP_ID:
                if (this.spreadSheet.getEventManage() != null) {
                    this.spreadSheet.getEventManage().onScroll((MotionEvent) null, (MotionEvent) null, 0.0f, (float) ((-this.spreadSheet.getHeight()) + 10));
                    exportImage();
                    this.spreadSheet.post(new Runnable() {
                        public void run() {
                            if (!SSControl.this.isDispose) {
                                SSControl.this.updateStatus();
                            }
                        }
                    });
                    return;
                }
                return;
            case EventConstant.APP_PAGE_DOWN_ID:
                if (this.spreadSheet.getEventManage() != null) {
                    this.spreadSheet.getEventManage().onScroll((MotionEvent) null, (MotionEvent) null, 0.0f, (float) (this.spreadSheet.getHeight() - 10));
                    exportImage();
                    this.spreadSheet.post(new Runnable() {
                        public void run() {
                            if (!SSControl.this.isDispose) {
                                SSControl.this.updateStatus();
                            }
                        }
                    });
                    return;
                }
                return;
            case EventConstant.APP_INIT_CALLOUTVIEW_ID:
                this.spreadSheet.initCalloutView();
                return;
            case EventConstant.SS_SHOW_SHEET:
                this.excelView.showSheet(((Integer) obj).intValue());
                return;
            case EventConstant.SS_REMOVE_SHEET_BAR:
                this.excelView.removeSheetBar();
                return;
            default:
                return;
        }
    }

    public Object getActionValue(int i, Object obj) {
        int[] iArr;
        Sheet sheet;
        switch (i) {
            case EventConstant.APP_ZOOM_ID:
                return Float.valueOf(this.spreadSheet.getZoom());
            case EventConstant.APP_FIT_ZOOM_ID:
                return Float.valueOf(this.spreadSheet.getFitZoom());
            case EventConstant.APP_COUNT_PAGES_ID:
                return Integer.valueOf(this.spreadSheet.getSheetCount());
            case EventConstant.APP_CURRENT_PAGE_NUMBER_ID:
                return Integer.valueOf(this.spreadSheet.getCurrentSheetNumber());
            case EventConstant.APP_THUMBNAIL_ID:
                if ((obj instanceof int[]) && (iArr = (int[]) obj) != null && iArr.length == 3) {
                    return this.spreadSheet.getThumbnail(iArr[0], iArr[1], ((float) iArr[2]) / 10000.0f);
                }
            case EventConstant.APP_GET_SNAPSHOT_ID:
                break;
            case EventConstant.SS_GET_ALL_SHEET_NAME:
                Vector vector = new Vector();
                Workbook workbook = this.spreadSheet.getWorkbook();
                int sheetCount = workbook.getSheetCount();
                for (int i2 = 0; i2 < sheetCount; i2++) {
                    vector.add(workbook.getSheet(i2).getSheetName());
                }
                return vector;
            case EventConstant.SS_GET_SHEET_NAME:
                int intValue = ((Integer) obj).intValue();
                if (intValue == -1 || (sheet = this.spreadSheet.getWorkbook().getSheet(intValue - 1)) == null) {
                    return null;
                }
                return sheet.getSheetName();
        }
        Spreadsheet spreadsheet = this.spreadSheet;
        if (spreadsheet != null) {
            return spreadsheet.getSnapshot((Bitmap) obj);
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void updateStatus() {
        this.spreadSheet.post(new Runnable() {
            public void run() {
                if (!SSControl.this.isDispose) {
                    SSControl.this.getMainFrame().updateToolsbarStatus();
                }
            }
        });
    }

    private void exportImage() {
        this.spreadSheet.post(new Runnable() {
            public void run() {
                if (!SSControl.this.isDispose) {
                    SSControl.this.spreadSheet.createPicture();
                }
            }
        });
    }

    public int getCurrentViewIndex() {
        return this.excelView.getCurrentViewIndex();
    }

    public View getView() {
        return this.excelView;
    }

    public IMainFrame getMainFrame() {
        return this.mainControl.getMainFrame();
    }

    public Activity getActivity() {
        return this.mainControl.getMainFrame().getActivity();
    }

    public IFind getFind() {
        return this.spreadSheet;
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
        this.mainControl = null;
        this.spreadSheet = null;
        ExcelView excelView2 = this.excelView;
        if (excelView2 == null) {
            excelView2.dispose();
            this.excelView = null;
        }
    }
}
