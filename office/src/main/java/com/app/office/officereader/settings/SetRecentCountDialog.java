package com.app.office.officereader.settings;

import android.content.Context;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
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

public class SetRecentCountDialog extends ADialog {
    public static final int RECENT_COUNT_MAX = 20;
    public static final int RECENT_COUNT_MIN = 1;
    protected EditText editText;
    private TextWatcher watcher = new TextWatcher() {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            int parseInt;
            if (charSequence.length() <= 0 || charSequence.length() >= 3 || (parseInt = Integer.parseInt(charSequence.toString())) < 1 || parseInt > 20) {
                SetRecentCountDialog.this.ok.setEnabled(false);
            } else {
                SetRecentCountDialog.this.ok.setEnabled(true);
            }
        }
    };

    public SetRecentCountDialog(IControl iControl, Context context, IDialogAction iDialogAction, Vector<Object> vector, int i, int i2) {
        super(iControl, context, iDialogAction, vector, i, i2);
        init(context);
    }

    public void init(Context context) {
        int i = getContext().getResources().getDisplayMetrics().widthPixels - 60;
        TextView textView = new TextView(context);
        textView.setGravity(48);
        if (this.model != null) {
            textView.setText((String) this.model.get(0));
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        layoutParams.topMargin = 5;
        layoutParams.bottomMargin = 5;
        layoutParams.gravity = 17;
        this.dialogFrame.addView(textView, layoutParams);
        EditText editText2 = new EditText(context);
        this.editText = editText2;
        editText2.setGravity(48);
        if (this.model != null) {
            this.editText.setText((String) this.model.get(1));
        }
        this.editText.setSingleLine();
        this.editText.setKeyListener(new DigitsKeyListener(false, false));
        this.editText.addTextChangedListener(this.watcher);
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
        this.ok.setEnabled(true);
        linearLayout.addView(this.ok, layoutParams3);
        this.cancel = new Button(context);
        this.cancel.setText(R.string.sys_button_cancel);
        this.cancel.setOnClickListener(this);
        linearLayout.addView(this.cancel, layoutParams3);
        this.dialogFrame.addView(linearLayout);
    }

    public void onClick(View view) {
        if (view == this.ok) {
            Vector vector = new Vector();
            vector.add(Integer.valueOf(Integer.parseInt(this.editText.getText().toString())));
            this.action.doAction(this.dialogID, vector);
        }
        dismiss();
    }

    public void doLayout() {
        int i = getContext().getResources().getDisplayMetrics().widthPixels;
        int i2 = this.control.getSysKit().isVertical(getContext()) ? i - 60 : i - 240;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i2, -2);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        this.editText.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i2 / 2, -2);
        this.ok.setLayoutParams(layoutParams2);
        this.cancel.setLayoutParams(layoutParams2);
    }

    public void onConfigurationChanged(Configuration configuration) {
        doLayout();
    }

    public void dispose() {
        super.dispose();
        this.editText = null;
    }
}
