package com.app.office.system.beans;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.LinearLayout;

public class ADialogFrame extends LinearLayout {
    private ADialog dialog;

    public ADialogFrame(Context context, ADialog aDialog) {
        super(context);
        setOrientation(1);
        this.dialog = aDialog;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.dialog.onConfigurationChanged(configuration);
    }

    public void dispose() {
        this.dialog = null;
    }
}
