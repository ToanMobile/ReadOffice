package com.app.office.officereader.beans;

import android.content.Context;
import android.util.AttributeSet;
import com.app.office.R;
import com.app.office.constant.EventConstant;
import com.app.office.system.IControl;

public class CalloutToolsbar extends AToolsbar {
    public CalloutToolsbar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CalloutToolsbar(Context context, IControl iControl) {
        super(context, iControl);
        init();
        this.toolsbarFrame.setBackgroundResource(R.drawable.sys_toolsbar_button_bg_normal);
    }

    private void init() {
        addButton(R.drawable.app_back, R.drawable.app_back_disable, R.string.app_toolsbar_back, EventConstant.APP_BACK_ID, true);
        addCheckButton(R.drawable.app_pen_check, R.drawable.app_pen, R.drawable.app_pen, R.string.app_toolsbar_pen_check, R.string.app_toolsbar_pen, EventConstant.APP_PEN_ID, true);
        addCheckButton(R.drawable.app_eraser_check, R.drawable.app_eraser, R.drawable.app_eraser_disable, R.string.app_toolsbar_eraser_check, R.string.app_toolsbar_eraser, EventConstant.APP_ERASER_ID, true);
        addButton(R.drawable.app_color, R.drawable.app_color_disable, R.string.app_toolsbar_color, EventConstant.APP_COLOR_ID, true);
    }
}
