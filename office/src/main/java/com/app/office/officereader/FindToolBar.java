package com.app.office.officereader;

import android.content.Context;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import com.app.office.R;
import com.app.office.constant.EventConstant;
import com.app.office.officereader.beans.AImageButton;
import com.app.office.officereader.beans.AImageFindButton;
import com.app.office.officereader.beans.AToolsbar;
import com.app.office.system.IControl;

public class FindToolBar extends AToolsbar {
    /* access modifiers changed from: private */
    public AImageFindButton searchButton;

    public FindToolBar(Context context, IControl iControl) {
        super(context, iControl);
        init();
    }

    private void init() {
        AImageButton addButton = addButton(R.drawable.file_left, R.drawable.file_left_disable, R.string.app_searchbar_backward, EventConstant.APP_FIND_BACKWARD, false);
        addButton.getLayoutParams().width = this.buttonWidth / 2;
        addButton.setEnabled(false);
        AImageButton addButton2 = addButton(R.drawable.file_right, R.drawable.file_right_disable, R.string.app_searchbar_forward, EventConstant.APP_FIND_FORWARD, false);
        addButton2.getLayoutParams().width = this.buttonWidth / 2;
        addButton2.setEnabled(false);
        this.searchButton = new AImageFindButton(getContext(), this.control, getContext().getResources().getString(R.string.app_searchbar_find), R.drawable.file_search, R.drawable.file_search_disable, EventConstant.APP_FINDING, getResources().getDisplayMetrics().widthPixels - ((this.buttonWidth * 3) / 2), this.buttonWidth / 2, this.buttonHeight, new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                boolean z = false;
                FindToolBar.this.setEnabled(EventConstant.APP_FIND_BACKWARD, false);
                FindToolBar.this.setEnabled(EventConstant.APP_FIND_FORWARD, false);
                AImageFindButton access$000 = FindToolBar.this.searchButton;
                if (editable.length() > 0) {
                    z = true;
                }
                access$000.setFindBtnState(z);
            }
        });
        this.actionButtonIndex.put(Integer.valueOf(EventConstant.APP_FINDING), Integer.valueOf(this.toolsbarFrame.getChildCount()));
        this.toolsbarFrame.addView(this.searchButton);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.searchButton.resetEditTextWidth(getResources().getDisplayMetrics().widthPixels - ((this.buttonWidth * 3) / 2));
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0) {
            ((InputMethodManager) getContext().getSystemService("input_method")).toggleSoftInput(0, 2);
            setEnabled(EventConstant.APP_FIND_BACKWARD, false);
            setEnabled(EventConstant.APP_FIND_FORWARD, false);
            this.searchButton.reset();
        }
    }

    public void dispose() {
        super.dispose();
        this.searchButton = null;
    }
}
