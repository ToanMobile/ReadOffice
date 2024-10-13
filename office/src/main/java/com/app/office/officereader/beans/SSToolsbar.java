package com.app.office.officereader.beans;

import android.content.Context;
import android.util.AttributeSet;
import com.app.office.R;
import com.app.office.constant.EventConstant;
import com.app.office.ss.control.ExcelView;
import com.app.office.ss.control.Spreadsheet;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.util.ModelUtil;
import com.app.office.system.IControl;

public class SSToolsbar extends AToolsbar {
    public SSToolsbar(Context context, IControl iControl) {
        super(context, iControl);
        init();
    }

    public SSToolsbar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void init() {
        addButton(R.drawable.file_copy, R.drawable.file_copy_disable, R.string.file_toolsbar_copy, EventConstant.FILE_COPY_ID, true);
        addButton(R.drawable.app_internet_hyperlink, R.drawable.app_internet_hyperlink_disable, R.string.app_toolsbar_hyperlink, EventConstant.APP_HYPERLINK, true);
        addButton(R.drawable.app_find, R.drawable.app_find_disable, R.string.app_toolsbar_find, 536870912, true);
        addButton(R.drawable.file_share, R.drawable.file_share_disable, R.string.file_toolsbar_share, EventConstant.APP_SHARE_ID, true);
        addButton(R.drawable.app_internet_search, R.drawable.app_internet_search_disable, R.string.app_toolsbar_internet_search, EventConstant.APP_INTERNET_SEARCH_ID, true);
        addCheckButton(R.drawable.file_star_check, R.drawable.file_star_uncheck, R.drawable.file_star_disable, R.string.file_toolsbar_mark_star, R.string.file_toolsbar_unmark_star, EventConstant.FILE_MARK_STAR_ID, true);
        addButton(R.drawable.app_drawing, R.drawable.app_drawing_disable, R.string.app_toolsbar_draw, EventConstant.APP_DRAW_ID, true);
    }

    public void updateStatus() {
        Spreadsheet spreadsheet = ((ExcelView) this.control.getView()).getSpreadsheet();
        if (spreadsheet.getSheetView() != null) {
            boolean z = true;
            setEnabled(536870912, true);
            setEnabled(EventConstant.APP_SHARE_ID, true);
            setEnabled(15, true);
            Sheet currentSheet = spreadsheet.getSheetView().getCurrentSheet();
            if (currentSheet.getActiveCellType() != 0 || currentSheet.getActiveCell() == null) {
                setEnabled(EventConstant.FILE_COPY_ID, false);
                setEnabled(EventConstant.APP_HYPERLINK, false);
                setEnabled(EventConstant.APP_INTERNET_SEARCH_ID, false);
            } else {
                String formatContents = ModelUtil.instance().getFormatContents(currentSheet.getWorkbook(), currentSheet.getActiveCell());
                setEnabled(EventConstant.FILE_COPY_ID, formatContents != null && formatContents.length() > 0);
                setEnabled(EventConstant.APP_INTERNET_SEARCH_ID, formatContents != null && formatContents.length() > 0);
                if (currentSheet.getActiveCell().getHyperLink() == null || currentSheet.getActiveCell().getHyperLink().getAddress() == null) {
                    z = false;
                }
                setEnabled(EventConstant.APP_HYPERLINK, z);
            }
            postInvalidate();
        }
    }

    public void dispose() {
        super.dispose();
    }
}
