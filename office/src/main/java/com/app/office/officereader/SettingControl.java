package com.app.office.officereader;

import android.app.Activity;
import com.app.office.officereader.database.DBService;
import com.app.office.system.AbstractControl;
import com.app.office.system.SysKit;

public class SettingControl extends AbstractControl {
    private Activity activity;
    public DBService dbService;
    private SysKit sysKit;

    public SettingControl(Activity activity2) {
        this.activity = activity2;
        this.dbService = new DBService(activity2);
    }

    public void actionEvent(int i, Object obj) {
        if (i == 21 && obj != null) {
            this.dbService.changeRecentCount(((Integer) obj).intValue());
        }
    }

    public Activity getActivity() {
        return this.activity;
    }

    public int getRecentMax() {
        DBService dBService = this.dbService;
        if (dBService == null) {
            return 0;
        }
        return dBService.getRecentMax();
    }

    public SysKit getSysKit() {
        if (this.sysKit == null) {
            this.sysKit = new SysKit(this);
        }
        return this.sysKit;
    }

    public void dispose() {
        this.activity = null;
        DBService dBService = this.dbService;
        if (dBService != null) {
            dBService.dispose();
            this.dbService = null;
        }
        this.sysKit = null;
    }
}
