package com.app.office.system.dialog;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.office.res.ResConstant;
import com.app.office.system.IControl;
import com.app.office.system.IDialogAction;
import com.app.office.system.beans.ADialog;
import java.util.Vector;

public class MessageDialog extends ADialog {
    private TextView textView;

    public MessageDialog(IControl iControl, Context context, IDialogAction iDialogAction, Vector<Object> vector, int i, int i2, String str) {
        super(iControl, context, iDialogAction, vector, i, i2);
        init(context, str);
    }

    public void init(Context context, String str) {
        int i = getContext().getResources().getDisplayMetrics().widthPixels - 120;
        TextView textView2 = new TextView(context);
        this.textView = textView2;
        textView2.setGravity(48);
        this.textView.setPadding(5, 2, 5, 2);
        if (str != null) {
            this.textView.setText(str);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i, -2);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        layoutParams.topMargin = 10;
        layoutParams.bottomMargin = 10;
        layoutParams.gravity = 17;
        this.dialogFrame.addView(this.textView, layoutParams);
        this.ok = new Button(context);
        this.ok.setText(ResConstant.BUTTON_OK);
        this.ok.setOnClickListener(this);
        this.dialogFrame.addView(this.ok);
    }

    public void onClick(View view) {
        if (this.action != null) {
            this.action.doAction(this.dialogID, this.model);
        }
        dismiss();
    }

    public void doLayout() {
        int i = getContext().getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.control.getSysKit().isVertical(getContext()) ? i - 120 : i - 360, -2);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        layoutParams.topMargin = 10;
        layoutParams.bottomMargin = 10;
        this.textView.setLayoutParams(layoutParams);
    }

    public void onConfigurationChanged(Configuration configuration) {
        doLayout();
    }

    public void dispose() {
        super.dispose();
        this.textView = null;
    }
}
