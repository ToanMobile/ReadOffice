package com.app.office.officereader;

import android.app.Activity;
import com.app.office.system.AbstractControl;
import com.app.office.system.SysKit;

public class FileListControl extends AbstractControl {
    private Activity activity;
    private SysKit sysKit;

    public FileListControl(Activity activity2) {
        this.activity = activity2;
    }

    public void actionEvent(int i, Object obj) {
        ((FileListActivity) this.activity).actionEvent(i, obj);
    }

    public Activity getActivity() {
        return this.activity;
    }

    public SysKit getSysKit() {
        if (this.sysKit == null) {
            this.sysKit = new SysKit(this);
        }
        return this.sysKit;
    }

    public void dispose() {
        this.activity = null;
        this.sysKit = null;
    }
}
