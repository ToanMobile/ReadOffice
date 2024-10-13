package com.app.office.officereader.beans;

import android.content.Context;
import android.util.AttributeSet;
import com.app.office.R;
import com.app.office.constant.EventConstant;
import com.app.office.pg.control.Presentation;
import com.app.office.system.IControl;

public class PGToolsbar extends AToolsbar {
    public PGToolsbar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PGToolsbar(Context context, IControl iControl) {
        super(context, iControl);
        init();
    }

    private void init() {
        addButton(R.drawable.file_slideshow, R.drawable.file_slideshow, R.string.pg_slideshow, EventConstant.PG_SLIDESHOW_GEGIN, true);
        addButton(R.drawable.app_find, R.drawable.app_find_disable, R.string.app_toolsbar_find, 536870912, true);
        addButton(R.drawable.ppt_node, R.drawable.ppt_node_disable, R.string.pg_toolsbar_note, EventConstant.PG_NOTE_ID, true);
        addButton(R.drawable.file_share, R.drawable.file_share_disable, R.string.file_toolsbar_share, EventConstant.APP_SHARE_ID, true);
        addButton(R.drawable.app_internet_search, R.drawable.app_internet_search_disable, R.string.app_toolsbar_internet_search, EventConstant.APP_INTERNET_SEARCH_ID, true);
        addCheckButton(R.drawable.file_star_check, R.drawable.file_star_uncheck, R.drawable.file_star_disable, R.string.file_toolsbar_mark_star, R.string.file_toolsbar_unmark_star, EventConstant.FILE_MARK_STAR_ID, true);
        addButton(R.drawable.app_drawing, R.drawable.app_drawing_disable, R.string.app_toolsbar_draw, EventConstant.APP_DRAW_ID, true);
    }

    public void updateStatus() {
        Presentation presentation = (Presentation) this.control.getView();
        if (presentation.getCurrentSlide() == null || presentation.getCurrentSlide().getNotes() == null) {
            setEnabled(EventConstant.PG_NOTE_ID, false);
        } else {
            setEnabled(EventConstant.PG_NOTE_ID, true);
        }
        postInvalidate();
    }

    public void dispose() {
        super.dispose();
    }
}
