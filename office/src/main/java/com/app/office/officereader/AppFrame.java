package com.app.office.officereader;

import android.content.Context;
import android.widget.LinearLayout;

public class AppFrame extends LinearLayout {
    public void dispose() {
    }

    public AppFrame(Context context) {
        super(context);
        setOrientation(1);
        setBackgroundColor(-1);
    }
}
