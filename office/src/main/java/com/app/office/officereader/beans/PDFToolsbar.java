package com.app.office.officereader.beans;

import android.content.Context;
import android.view.View;
import com.app.office.R;
import com.app.office.constant.EventConstant;
import com.app.office.system.IControl;

public class PDFToolsbar extends AToolsbar {
    public void updateStatus() {
    }

    public PDFToolsbar(Context context, IControl iControl) {
        super(context, iControl);
        init();
    }

    private void init() {
        addButton(R.drawable.app_find, R.drawable.app_find_disable, R.string.app_toolsbar_find, 536870912, true);
        addButton(R.drawable.file_share, R.drawable.file_share_disable, R.string.file_toolsbar_share, EventConstant.APP_SHARE_ID, true);
        addCheckButton(R.drawable.file_star_check, R.drawable.file_star_uncheck, R.drawable.file_star_disable, R.string.file_toolsbar_mark_star, R.string.file_toolsbar_unmark_star, EventConstant.FILE_MARK_STAR_ID, true);
        addButton(R.drawable.app_drawing, R.drawable.app_drawing_disable, R.string.app_toolsbar_draw, EventConstant.APP_DRAW_ID, true);
    }

    public void onClick(View view) {
        if (view instanceof AImageButton) {
            this.control.actionEvent(((AImageButton) view).getActionID(), (Object) null);
        }
    }

    public void dispose() {
        super.dispose();
    }
}
