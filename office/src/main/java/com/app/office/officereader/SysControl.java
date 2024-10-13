package com.app.office.officereader;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;
import com.app.office.system.AbstractControl;
import com.app.office.system.SysKit;

public class SysControl extends AbstractControl {
    private Activity activity;
    private SysKit sysKit;
    private Toast toast;

    public SysControl(Activity activity2) {
        this.activity = activity2;
        this.toast = Toast.makeText(activity2, "", 0);
    }

    public void actionEvent(int i, Object obj) {
        if (i == 5) {
            this.activity.onSearchRequested();
        } else if (i != 17) {
            if (i == 18) {
                this.toast.cancel();
            }
        } else if (obj != null && (obj instanceof String)) {
            this.toast.setText((String) obj);
            this.toast.show();
        }
    }

    public View getView() {
        return ((SysActivity) this.activity).getSysFrame();
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
