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

public class QuestionDialog extends ADialog {
    private TextView textView;

    public QuestionDialog(IControl iControl, Context context, IDialogAction iDialogAction, Vector<Object> vector, int i, int i2, String str) {
        super(iControl, context, iDialogAction, vector, i, i2);
        init(context, str);
    }

    public void init(Context context, String str) {
        int i = getContext().getResources().getDisplayMetrics().widthPixels - 120;
        TextView textView2 = new TextView(context);
        this.textView = textView2;
        textView2.setPadding(5, 2, 5, 2);
        this.textView.setGravity(48);
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
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setGravity(17);
        linearLayout.setOrientation(0);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i / 2, -2);
        this.ok = new Button(context);
        this.ok.setText(ResConstant.BUTTON_OK);
        this.ok.setOnClickListener(this);
        linearLayout.addView(this.ok, layoutParams2);
        this.cancel = new Button(context);
        this.cancel.setText("Cancel");
        this.cancel.setOnClickListener(this);
        linearLayout.addView(this.cancel, layoutParams2);
        this.dialogFrame.addView(linearLayout);
    }

    public void onClick(View view) {
        if (view == this.ok) {
            this.action.doAction(this.dialogID, this.model);
        }
        dismiss();
    }

    public void doLayout() {
        int i = getContext().getResources().getDisplayMetrics().widthPixels;
        int i2 = this.control.getSysKit().isVertical(getContext()) ? i - 120 : i - 360;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i2, -2);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        layoutParams.topMargin = 10;
        layoutParams.bottomMargin = 10;
        this.textView.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i2 / 2, -2);
        this.ok.setLayoutParams(layoutParams2);
        this.cancel.setLayoutParams(layoutParams2);
    }

    public void onConfigurationChanged(Configuration configuration) {
        doLayout();
    }

    public void dispose() {
        super.dispose();
        this.textView = null;
    }
}
