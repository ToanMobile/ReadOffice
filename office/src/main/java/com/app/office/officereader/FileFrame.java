package com.app.office.officereader;

import android.content.Context;
import android.widget.LinearLayout;

public class FileFrame extends LinearLayout {
    public void dispose() {
    }

    public FileFrame(Context context) {
        super(context);
        setOrientation(1);
    }
}
