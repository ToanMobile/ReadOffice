package com.app.office.officereader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.app.office.system.IControl;

public class SysActivity extends Activity {
    private IControl control;
    private SysFrame sysFrame;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        this.control = new SysControl(this);
        SysFrame sysFrame2 = new SysFrame(this, this.control);
        this.sysFrame = sysFrame2;
        sysFrame2.post(new Runnable() {
            public void run() {
                SysActivity.this.init();
            }
        });
        setContentView(this.sysFrame);
    }

    public void init() {
        this.sysFrame.init();
    }

    public View getSysFrame() {
        return this.sysFrame;
    }

    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    public void dispose() {
        this.sysFrame.dispose();
        this.control.dispose();
    }
}
