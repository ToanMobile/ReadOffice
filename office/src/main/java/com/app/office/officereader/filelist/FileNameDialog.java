package com.app.office.officereader.filelist;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.office.R;
import com.app.office.system.IControl;
import com.app.office.system.IDialogAction;
import com.app.office.system.beans.ADialog;
import java.util.Vector;

public class FileNameDialog extends ADialog {
    protected EditText editText;
    protected TextView textView;

    public void onClick(View view) {
    }

    public FileNameDialog(IControl iControl, Context context, IDialogAction iDialogAction, Vector<Object> vector, int i, int i2) {
        super(iControl, context, iDialogAction, vector, i, i2);
        init(context);
    }

    public void init(Context context) {
        int i = getContext().getResources().getDisplayMetrics().widthPixels - 60;
        TextView textView2 = new TextView(context);
        this.textView = textView2;
        textView2.setGravity(48);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        layoutParams.topMargin = 5;
        layoutParams.bottomMargin = 5;
        layoutParams.gravity = 17;
        this.dialogFrame.addView(this.textView, layoutParams);
        EditText editText2 = new EditText(context);
        this.editText = editText2;
        editText2.setGravity(48);
        this.editText.setSingleLine();
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i, -2);
        layoutParams2.leftMargin = 5;
        layoutParams2.rightMargin = 5;
        this.dialogFrame.addView(this.editText, layoutParams2);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setGravity(17);
        linearLayout.setOrientation(0);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(i / 2, -2);
        this.ok = new Button(context);
        this.ok.setText(R.string.sys_button_ok);
        this.ok.setOnClickListener(this);
        this.ok.setEnabled(false);
        linearLayout.addView(this.ok, layoutParams3);
        this.cancel = new Button(context);
        this.cancel.setText(R.string.sys_button_cancel);
        this.cancel.setOnClickListener(this);
        linearLayout.addView(this.cancel, layoutParams3);
        this.dialogFrame.addView(linearLayout);
    }

    public void doLayout() {
        int i = getContext().getResources().getDisplayMetrics().widthPixels;
        int i2 = this.control.getSysKit().isVertical(getContext()) ? i - 60 : i - 240;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        layoutParams.topMargin = 5;
        layoutParams.bottomMargin = 5;
        this.textView.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i2, -2);
        layoutParams2.leftMargin = 5;
        layoutParams2.rightMargin = 5;
        this.editText.setLayoutParams(layoutParams2);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(i2 / 2, -2);
        this.ok.setLayoutParams(layoutParams3);
        this.cancel.setLayoutParams(layoutParams3);
    }

    public void onConfigurationChanged(Configuration configuration) {
        doLayout();
    }

    public boolean isFileNameOK(String str) {
        if (str == null || str.length() < 1 || str.length() > 255) {
            return false;
        }
        for (int i = 0; i < 9; i++) {
            if (str.indexOf("\\/:*?\"<>|".charAt(i)) > -1) {
                return false;
            }
        }
        return true;
    }

    public void dispose() {
        super.dispose();
        this.editText = null;
        this.textView = null;
    }
}
