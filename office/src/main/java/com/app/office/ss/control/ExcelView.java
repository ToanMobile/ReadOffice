package com.app.office.ss.control;

import android.content.Context;
import android.widget.RelativeLayout;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.sheetbar.SheetBar;
import com.app.office.ss.view.SheetView;
import com.app.office.system.IControl;

public class ExcelView extends RelativeLayout {
    private SheetBar bar;
    private IControl control;
    private boolean isDefaultSheetBar = true;
    private Spreadsheet ss;

    public ExcelView(Context context, String str, Workbook workbook, IControl iControl) {
        super(context);
        this.control = iControl;
        Spreadsheet spreadsheet = new Spreadsheet(context, str, workbook, iControl, this);
        this.ss = spreadsheet;
        addView(spreadsheet, new RelativeLayout.LayoutParams(-1, -1));
    }

    public void init() {
        this.ss.init();
        initSheetbar();
    }

    private void initSheetbar() {
        if (this.isDefaultSheetBar) {
            this.bar = new SheetBar(getContext(), this.control, getResources().getDisplayMetrics().widthPixels);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.addRule(12);
            addView(this.bar, layoutParams);
        }
    }

    public Spreadsheet getSpreadsheet() {
        return this.ss;
    }

    public void showSheet(int i) {
        this.ss.showSheet(i);
        if (this.isDefaultSheetBar) {
            this.bar.setFocusSheetButton(i);
        } else {
            this.control.getMainFrame().doActionEvent(1073741828, Integer.valueOf(i));
        }
    }

    public void showSheet(String str) {
        this.ss.showSheet(str);
        Sheet sheet = this.ss.getWorkbook().getSheet(str);
        if (sheet != null) {
            int sheetIndex = this.ss.getWorkbook().getSheetIndex(sheet);
            if (this.isDefaultSheetBar) {
                this.bar.setFocusSheetButton(sheetIndex);
            } else {
                this.control.getMainFrame().doActionEvent(1073741828, Integer.valueOf(sheetIndex));
            }
        }
    }

    public SheetView getSheetView() {
        return this.ss.getSheetView();
    }

    public void removeSheetBar() {
        this.isDefaultSheetBar = false;
        removeView(this.bar);
    }

    public int getBottomBarHeight() {
        if (this.isDefaultSheetBar) {
            return this.bar.getHeight();
        }
        return this.control.getMainFrame().getBottomBarHeight();
    }

    public int getCurrentViewIndex() {
        return this.ss.getCurrentSheetNumber();
    }

    public void dispose() {
        this.control = null;
        Spreadsheet spreadsheet = this.ss;
        if (spreadsheet != null) {
            spreadsheet.dispose();
        }
        this.bar = null;
    }
}
