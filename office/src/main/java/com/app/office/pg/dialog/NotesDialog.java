package com.app.office.pg.dialog;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.app.office.R;
import com.app.office.system.IControl;
import com.app.office.system.IDialogAction;
import com.app.office.system.beans.ADialog;
import java.util.Vector;

public class NotesDialog extends ADialog {
    /* access modifiers changed from: private */
    public EditText notes;
    private ScrollView scrollView;

    public NotesDialog(IControl iControl, Context context, IDialogAction iDialogAction, Vector<Object> vector, int i) {
        super(iControl, context, iDialogAction, vector, i, R.string.pg_toolsbar_note);
        init(context);
    }

    public void init(Context context) {
        EditText editText = new EditText(context);
        this.notes = editText;
        editText.setBackgroundColor(-1);
        this.notes.setTextSize(18.0f);
        this.notes.setPadding(5, 2, 5, 2);
        this.notes.setGravity(48);
        if (this.model != null) {
            this.dialogFrame.post(new Runnable() {
                public void run() {
                    NotesDialog.this.notes.setText((String) NotesDialog.this.model.get(0));
                }
            });
        }
        ScrollView scrollView2 = new ScrollView(context);
        this.scrollView = scrollView2;
        scrollView2.setFillViewport(true);
        this.scrollView.setHorizontalFadingEdgeEnabled(false);
        this.scrollView.setFadingEdgeLength(0);
        this.scrollView.addView(this.notes);
        this.dialogFrame.addView(this.scrollView);
        this.ok = new Button(context);
        this.ok.setText(R.string.sys_button_ok);
        this.ok.setOnClickListener(this);
        this.dialogFrame.addView(this.ok);
    }

    public void doLayout() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((getContext().getResources().getDisplayMetrics().widthPixels - 50) - 10, ((getContext().getResources().getDisplayMetrics().heightPixels - (getWindow().getDecorView().getHeight() - this.dialogFrame.getHeight())) - 50) - this.ok.getHeight());
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        layoutParams.bottomMargin = 5;
        this.scrollView.setLayoutParams(layoutParams);
    }

    public void onConfigurationChanged(Configuration configuration) {
        doLayout();
    }

    public void onClick(View view) {
        dismiss();
    }

    public void dispose() {
        super.dispose();
        this.scrollView = null;
        this.notes = null;
    }
}
