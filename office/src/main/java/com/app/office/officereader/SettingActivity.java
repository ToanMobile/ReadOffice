package com.app.office.officereader;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.app.office.R;
import com.app.office.officereader.settings.SetRecentCountDialog;
import com.app.office.officereader.settings.SettingDialogAction;
import com.app.office.system.IControl;
import java.util.Vector;

public class SettingActivity extends Activity {
    public static final int SET_RECENT_FILE_COUNT = 0;
    private IControl control;
    private SettingDialogAction dialogAction;
    public ListView listView;
    private int mHeight;
    private AdapterView.OnItemClickListener onItemClickListener;
    private SettingFrame settingFrame;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.control = new SettingControl(this);
        SettingFrame settingFrame2 = new SettingFrame(this);
        this.settingFrame = settingFrame2;
        settingFrame2.post(new Runnable() {
            public void run() {
                SettingActivity.this.initListener();
                SettingActivity.this.init();
            }
        });
        setTheme(this.control.getSysKit().isVertical(this) ? R.style.title_background_vertical : R.style.title_background_horizontal);
        setContentView(this.settingFrame);
        this.dialogAction = new SettingDialogAction(this.control);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = getResources().getDisplayMetrics().heightPixels;
        this.mHeight = i;
        this.mHeight = i - getWindow().findViewById(16908290).getTop();
        this.listView.setLayoutParams(new LinearLayout.LayoutParams(-1, this.mHeight));
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        dispose();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    /* access modifiers changed from: private */
    public void initListener() {
        this.onItemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                SettingActivity.this.showSettings(i);
            }
        };
    }

    public void init() {
        int i = getResources().getDisplayMetrics().heightPixels;
        this.mHeight = i;
        this.mHeight = i - getWindow().findViewById(16908290).getTop();
        ListView listView2 = new ListView(this);
        this.listView = listView2;
        listView2.setOnItemClickListener(this.onItemClickListener);
        this.listView.setAdapter(new ArrayAdapter(this, R.layout.setting_dialog_item, 16908308, getResources().getStringArray(R.array.setting_items)));
        this.settingFrame.addView(this.listView, new LinearLayout.LayoutParams(-1, this.mHeight));
    }

    public void dispose() {
        this.settingFrame = null;
        this.listView = null;
        this.onItemClickListener = null;
        SettingDialogAction settingDialogAction = this.dialogAction;
        if (settingDialogAction != null) {
            settingDialogAction.dispose();
            this.dialogAction = null;
        }
        IControl iControl = this.control;
        if (iControl != null) {
            iControl.dispose();
            this.control = null;
        }
    }

    public void showSettings(int i) {
        if (i == 0) {
            int recentMax = ((SettingControl) this.control).getRecentMax();
            String[] stringArray = getResources().getStringArray(R.array.setting_items);
            Vector vector = new Vector();
            vector.add(stringArray[i]);
            vector.add(String.valueOf(recentMax));
            new SetRecentCountDialog(this.control, this, this.dialogAction, vector, 7, R.string.sys_menu_settings).show();
        }
    }
}
