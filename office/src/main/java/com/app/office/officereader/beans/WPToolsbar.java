package com.app.office.officereader.beans;

import android.content.Context;
import android.view.View;
import com.app.office.R;
import com.app.office.constant.EventConstant;
import com.app.office.system.IControl;
import com.app.office.wp.control.Word;

public class WPToolsbar extends AToolsbar {
    public WPToolsbar(Context context, IControl iControl) {
        super(context, iControl);
        init();
    }

    private void init() {
        addButton(R.drawable.file_copy, R.drawable.file_copy_disable, R.string.file_toolsbar_copy, EventConstant.FILE_COPY_ID, true);
        addButton(R.drawable.app_find, R.drawable.app_find_disable, R.string.app_toolsbar_find, 536870912, true);
        addButton(R.drawable.wp_switch_view, R.drawable.wp_switch_view_disable, R.string.wp_toolsbar_switch_view, EventConstant.WP_SWITCH_VIEW, true);
        addButton(R.drawable.app_print_n, R.drawable.app_print_d, R.string.wp_toolsbar_print_mode, EventConstant.WP_PRINT_MODE, true);
        addButton(R.drawable.file_share, R.drawable.file_share_disable, R.string.file_toolsbar_share, EventConstant.APP_SHARE_ID, true);
        addButton(R.drawable.app_internet_search, R.drawable.app_internet_search_disable, R.string.app_toolsbar_internet_search, EventConstant.APP_INTERNET_SEARCH_ID, true);
        addCheckButton(R.drawable.file_star_check, R.drawable.file_star_uncheck, R.drawable.file_star_disable, R.string.file_toolsbar_mark_star, R.string.file_toolsbar_unmark_star, EventConstant.FILE_MARK_STAR_ID, true);
        addButton(R.drawable.app_drawing, R.drawable.app_drawing_disable, R.string.app_toolsbar_draw, EventConstant.APP_DRAW_ID, true);
    }

    public void updateStatus() {
        Word word = (Word) this.control.getView();
        setEnabled(EventConstant.APP_INTERNET_SEARCH_ID, word.getHighlight().isSelectText());
        setEnabled(EventConstant.FILE_COPY_ID, word.getHighlight().isSelectText());
        setEnabled(EventConstant.APP_DRAW_ID, word != null && word.getCurrentRootType() == 2);
    }

    public void onClick(View view) {
        if (view instanceof AImageButton) {
            int actionID = ((AImageButton) view).getActionID();
            if (actionID != 805306368) {
                this.control.actionEvent(actionID, (Object) null);
            } else {
                this.control.actionEvent(actionID, (Object) null);
            }
        }
    }

    public void dispose() {
        super.dispose();
    }
}
