package com.app.office.ss.control;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Message;
import android.widget.LinearLayout;
import com.app.office.common.IOfficeToPicture;
import com.app.office.common.hyperlink.Hyperlink;
import com.app.office.common.picture.PictureKit;
import com.app.office.constant.EventConstant;
import com.app.office.simpletext.control.IWord;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.model.interfacePart.IReaderListener;
import com.app.office.ss.util.ModelUtil;
import com.app.office.ss.view.SheetView;
import com.app.office.system.IControl;
import com.app.office.system.IFind;
import com.app.office.system.ReaderHandler;
import com.app.office.system.beans.AEventManage;
import com.app.office.system.beans.CalloutView.CalloutView;
import com.app.office.system.beans.CalloutView.IExportListener;
import java.io.File;

public class Spreadsheet extends LinearLayout implements IFind, IReaderListener, IExportListener {
    private boolean abortDrawing;
    private CalloutView callouts;
    /* access modifiers changed from: private */
    public IControl control;
    /* access modifiers changed from: private */
    public int currentSheetIndex;
    private String currentSheetName;
    private SSEditor editor;
    private SSEventManage eventManage;
    /* access modifiers changed from: private */
    public String fileName;
    private boolean initFinish;
    private boolean isConfigurationChanged;
    private boolean isDefaultSheetBar = true;
    private ExcelView parent;
    private int preShowSheetIndex = -1;
    private int sheetbarHeight;
    private SheetView sheetview;
    /* access modifiers changed from: private */
    public Workbook workbook;

    private void initSheetbar() {
    }

    public float getFitZoom() {
        return 0.5f;
    }

    public int getPageIndex() {
        return -1;
    }

    public void resetSearchResult() {
    }

    public Spreadsheet(Context context, String str, Workbook workbook2, IControl iControl, ExcelView excelView) {
        super(context);
        this.parent = excelView;
        this.fileName = str;
        setBackgroundColor(-1);
        this.workbook = workbook2;
        this.control = iControl;
        this.eventManage = new SSEventManage(this, iControl);
        this.editor = new SSEditor(this);
        setOnTouchListener(this.eventManage);
        setLongClickable(true);
    }

    public CalloutView getCalloutView() {
        return this.callouts;
    }

    public void initCalloutView() {
        if (this.callouts == null) {
            CalloutView calloutView = new CalloutView(getContext(), this.control, this);
            this.callouts = calloutView;
            calloutView.setIndex(this.currentSheetIndex);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
            layoutParams.leftMargin = 50;
            layoutParams.topMargin = 30;
            addView(this.callouts, layoutParams);
        }
    }

    public void exportImage() {
        this.control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
    }

    public void init() {
        int lastIndexOf = this.fileName.lastIndexOf(File.separator);
        if (lastIndexOf > 0) {
            this.fileName = this.fileName.substring(lastIndexOf + 1);
        }
        IControl iControl = this.control;
        iControl.actionEvent(1073741824, this.fileName + " : " + this.workbook.getSheet(0).getSheetName());
        if (this.sheetview == null) {
            this.sheetview = new SheetView(this, this.workbook.getSheet(0));
        }
        this.initFinish = true;
        if (this.workbook.getSheet(0).getState() != 2) {
            this.workbook.getSheet(0).setReaderListener(this);
            this.control.actionEvent(26, true);
        }
        post(new Runnable() {
            public void run() {
                Spreadsheet.this.control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
            }
        });
    }

    public void removeSheetBar() {
        this.isDefaultSheetBar = false;
    }

    public int getSheetCount() {
        return this.workbook.getSheetCount();
    }

    public void showSheet(String str) {
        Sheet sheet;
        String str2 = this.currentSheetName;
        if ((str2 == null || !str2.equals(str)) && (sheet = this.workbook.getSheet(str)) != null) {
            this.currentSheetName = str;
            this.currentSheetIndex = this.workbook.getSheetIndex(sheet);
            showSheet(sheet);
        }
    }

    public void showSheet(int i) {
        if (this.currentSheetIndex != i && i < getSheetCount()) {
            Sheet sheet = this.workbook.getSheet(i);
            this.currentSheetIndex = i;
            this.currentSheetName = sheet.getSheetName();
            this.control.actionEvent(20, (Object) null);
            CalloutView calloutView = this.callouts;
            if (calloutView != null) {
                calloutView.setIndex(this.currentSheetIndex);
            }
            showSheet(sheet);
        }
    }

    private void showSheet(Sheet sheet) {
        try {
            this.eventManage.stopFling();
            this.control.getMainFrame().setFindBackForwardState(false);
            IControl iControl = this.control;
            iControl.actionEvent(1073741824, this.fileName + " : " + sheet.getSheetName());
            this.sheetview.changeSheet(sheet);
            postInvalidate();
            if (sheet.getState() != 2) {
                sheet.setReaderListener(this);
                this.control.actionEvent(26, true);
                this.control.actionEvent(EventConstant.APP_ABORTREADING, (Object) null);
            } else {
                this.control.actionEvent(26, false);
            }
            ReaderHandler readerHandler = this.workbook.getReaderHandler();
            if (readerHandler != null) {
                Message message = new Message();
                message.what = 0;
                message.obj = Integer.valueOf(this.currentSheetIndex);
                readerHandler.handleMessage(message);
            }
        } catch (Exception e) {
            this.control.getSysKit().getErrorKit().writerLog(e);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:9|10|(1:12)(0)|13) */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        showSheet(r5.currentSheetIndex + 1);
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0031 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDraw(android.graphics.Canvas r6) {
        /*
            r5 = this;
            boolean r0 = r5.initFinish
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            com.app.office.ss.view.SheetView r0 = r5.sheetview     // Catch:{ Exception -> 0x0078 }
            r0.drawSheet(r6)     // Catch:{ Exception -> 0x0078 }
            com.app.office.system.IControl r6 = r5.control     // Catch:{ Exception -> 0x0078 }
            boolean r6 = r6.isAutoTest()     // Catch:{ Exception -> 0x0078 }
            r0 = 2
            if (r6 == 0) goto L_0x0044
            int r6 = r5.currentSheetIndex     // Catch:{ Exception -> 0x0078 }
            com.app.office.ss.model.baseModel.Workbook r1 = r5.workbook     // Catch:{ Exception -> 0x0078 }
            int r1 = r1.getSheetCount()     // Catch:{ Exception -> 0x0078 }
            r2 = 1
            int r1 = r1 - r2
            if (r6 >= r1) goto L_0x0038
        L_0x001f:
            com.app.office.ss.view.SheetView r6 = r5.sheetview     // Catch:{ Exception -> 0x0031 }
            com.app.office.ss.model.baseModel.Sheet r6 = r6.getCurrentSheet()     // Catch:{ Exception -> 0x0031 }
            short r6 = r6.getState()     // Catch:{ Exception -> 0x0031 }
            if (r6 == r0) goto L_0x0031
            r3 = 50
            java.lang.Thread.sleep(r3)     // Catch:{ Exception -> 0x0031 }
            goto L_0x001f
        L_0x0031:
            int r6 = r5.currentSheetIndex     // Catch:{ Exception -> 0x0078 }
            int r6 = r6 + r2
            r5.showSheet((int) r6)     // Catch:{ Exception -> 0x0078 }
            goto L_0x0055
        L_0x0038:
            com.app.office.system.IControl r6 = r5.control     // Catch:{ Exception -> 0x0078 }
            r1 = 22
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)     // Catch:{ Exception -> 0x0078 }
            r6.actionEvent(r1, r2)     // Catch:{ Exception -> 0x0078 }
            goto L_0x0055
        L_0x0044:
            com.app.office.system.IControl r6 = r5.control     // Catch:{ Exception -> 0x0078 }
            com.app.office.common.IOfficeToPicture r6 = r6.getOfficeToPicture()     // Catch:{ Exception -> 0x0078 }
            if (r6 == 0) goto L_0x0055
            byte r1 = r6.getModeType()     // Catch:{ Exception -> 0x0078 }
            if (r1 != 0) goto L_0x0055
            r5.toPicture(r6)     // Catch:{ Exception -> 0x0078 }
        L_0x0055:
            com.app.office.ss.view.SheetView r6 = r5.sheetview     // Catch:{ Exception -> 0x0078 }
            com.app.office.ss.model.baseModel.Sheet r6 = r6.getCurrentSheet()     // Catch:{ Exception -> 0x0078 }
            short r6 = r6.getState()     // Catch:{ Exception -> 0x0078 }
            if (r6 == r0) goto L_0x0064
            r5.invalidate()     // Catch:{ Exception -> 0x0078 }
        L_0x0064:
            int r6 = r5.preShowSheetIndex     // Catch:{ Exception -> 0x0078 }
            int r0 = r5.currentSheetIndex     // Catch:{ Exception -> 0x0078 }
            if (r6 == r0) goto L_0x0086
            com.app.office.system.IControl r6 = r5.control     // Catch:{ Exception -> 0x0078 }
            com.app.office.system.IMainFrame r6 = r6.getMainFrame()     // Catch:{ Exception -> 0x0078 }
            r6.changePage()     // Catch:{ Exception -> 0x0078 }
            int r6 = r5.currentSheetIndex     // Catch:{ Exception -> 0x0078 }
            r5.preShowSheetIndex = r6     // Catch:{ Exception -> 0x0078 }
            goto L_0x0086
        L_0x0078:
            r6 = move-exception
            com.app.office.system.IControl r0 = r5.control
            com.app.office.system.SysKit r0 = r0.getSysKit()
            com.app.office.system.ErrorUtil r0 = r0.getErrorKit()
            r0.writerLog(r6)
        L_0x0086:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.control.Spreadsheet.onDraw(android.graphics.Canvas):void");
    }

    public void createPicture() {
        IOfficeToPicture officeToPicture = this.control.getOfficeToPicture();
        if (officeToPicture != null && officeToPicture.getModeType() == 1) {
            try {
                toPicture(officeToPicture);
            } catch (Exception unused) {
            }
        }
    }

    private void toPicture(IOfficeToPicture iOfficeToPicture) {
        boolean isDrawPictrue = PictureKit.instance().isDrawPictrue();
        PictureKit.instance().setDrawPictrue(true);
        Bitmap bitmap = iOfficeToPicture.getBitmap(getWidth(), getHeight());
        if (bitmap != null) {
            Canvas canvas = new Canvas(bitmap);
            float zoom = this.sheetview.getZoom();
            if (!(bitmap.getWidth() == getWidth() && bitmap.getHeight() == getHeight())) {
                this.sheetview.setZoom(Math.min(((float) bitmap.getWidth()) / ((float) getWidth()), ((float) bitmap.getHeight()) / ((float) getHeight())) * zoom, true);
            }
            canvas.drawColor(-1);
            this.sheetview.drawSheet(canvas);
            this.control.getSysKit().getCalloutManager().drawPath(canvas, this.currentSheetIndex, zoom);
            iOfficeToPicture.callBack(bitmap);
            this.sheetview.setZoom(zoom, true);
            PictureKit.instance().setDrawPictrue(isDrawPictrue);
        }
    }

    public Bitmap getSnapshot(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        synchronized (this.sheetview) {
            Canvas canvas = new Canvas(bitmap);
            float zoom = this.sheetview.getZoom();
            if (!(bitmap.getWidth() == getWidth() && bitmap.getHeight() == getHeight())) {
                this.sheetview.setZoom(Math.min(((float) bitmap.getWidth()) / ((float) getWidth()), ((float) bitmap.getHeight()) / ((float) getHeight())) * zoom, true);
            }
            canvas.drawColor(-1);
            this.sheetview.drawSheet(canvas);
            this.sheetview.setZoom(zoom, true);
        }
        return bitmap;
    }

    public Bitmap getThumbnail(int i, int i2, float f) {
        Sheet sheet = this.workbook.getSheet(0);
        if (sheet == null || sheet.getState() != 2) {
            return null;
        }
        if (this.sheetview == null) {
            this.sheetview = new SheetView(this, this.workbook.getSheet(0));
        }
        return this.sheetview.getThumbnail(sheet, i, i2, f);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.isConfigurationChanged = true;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.isConfigurationChanged) {
            this.isConfigurationChanged = false;
            post(new Runnable() {
                public void run() {
                    Spreadsheet.this.control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
                }
            });
        }
    }

    public void computeScroll() {
        this.eventManage.computeScroll();
    }

    public IControl getControl() {
        return this.control;
    }

    public SheetView getSheetView() {
        return this.sheetview;
    }

    public Workbook getWorkbook() {
        return this.workbook;
    }

    public String getActiveCellContent() {
        return this.sheetview.getCurrentSheet().getActiveCell() != null ? ModelUtil.instance().getFormatContents(this.workbook, this.sheetview.getCurrentSheet().getActiveCell()) : "";
    }

    public Hyperlink getActiveCellHyperlink() {
        Cell activeCell = this.sheetview.getCurrentSheet().getActiveCell();
        if (activeCell == null || activeCell.getHyperLink() == null) {
            return null;
        }
        return activeCell.getHyperLink();
    }

    public boolean find(String str) {
        return this.sheetview.find(str);
    }

    public boolean findBackward() {
        return this.sheetview.findBackward();
    }

    public boolean findForward() {
        return this.sheetview.findForward();
    }

    public float getZoom() {
        if (this.sheetview == null) {
            this.sheetview = new SheetView(this, this.workbook.getSheet(0));
        }
        return this.sheetview.getZoom();
    }

    public void setZoom(float f) {
        if (this.sheetview == null) {
            this.sheetview = new SheetView(this, this.workbook.getSheet(0));
        }
        this.sheetview.setZoom(f);
    }

    public AEventManage getEventManage() {
        return this.eventManage;
    }

    public void OnReadingFinished() {
        IControl iControl = this.control;
        if (iControl != null && iControl.getMainFrame().getActivity() != null) {
            post(new Runnable() {
                public void run() {
                    Sheet sheet = Spreadsheet.this.workbook.getSheet(Spreadsheet.this.currentSheetIndex);
                    IControl access$000 = Spreadsheet.this.control;
                    access$000.actionEvent(1073741824, Spreadsheet.this.fileName + " : " + sheet.getSheetName());
                    Spreadsheet.this.control.actionEvent(26, false);
                    Spreadsheet.this.control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
                    Spreadsheet.this.postInvalidate();
                }
            });
        }
    }

    public String getFileName() {
        return this.fileName;
    }

    public void abortDrawing() {
        this.abortDrawing = true;
    }

    public void startDrawing() {
        this.abortDrawing = false;
    }

    public boolean isAbortDrawing() {
        return this.abortDrawing;
    }

    public int getCurrentSheetNumber() {
        return this.currentSheetIndex + 1;
    }

    public int getBottomBarHeight() {
        return this.parent.getBottomBarHeight();
    }

    public IWord getEditor() {
        return this.editor;
    }

    public void dispose() {
        this.parent = null;
        this.fileName = null;
        this.control = null;
        this.workbook = null;
        SheetView sheetView = this.sheetview;
        if (sheetView != null) {
            sheetView.dispose();
            this.sheetview = null;
        }
        SSEventManage sSEventManage = this.eventManage;
        if (sSEventManage != null) {
            sSEventManage.dispose();
            this.eventManage = null;
        }
        SSEditor sSEditor = this.editor;
        if (sSEditor != null) {
            sSEditor.dispose();
            this.editor = null;
        }
    }
}
