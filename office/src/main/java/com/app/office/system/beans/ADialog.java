package com.app.office.system.beans;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.app.office.system.IControl;
import com.app.office.system.IDialogAction;
import java.util.Vector;

public class ADialog extends Dialog implements View.OnClickListener {
    protected static final int GAP = 5;
    protected static final int MARGIN = 30;
    protected IDialogAction action;
    protected Button cancel;
    protected IControl control;
    protected ADialogFrame dialogFrame;
    protected int dialogID;
    /* access modifiers changed from: protected */
    public Vector<Object> model;
    /* access modifiers changed from: protected */
    public Button ok;

    public void doLayout() {
    }

    public void onClick(View view) {
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public ADialog(IControl iControl, Context context, IDialogAction iDialogAction, Vector<Object> vector, int i, int i2) {
        this(iControl, context, iDialogAction, vector, i, context.getResources().getString(i2));
    }

    public ADialog(IControl iControl, Context context, IDialogAction iDialogAction, Vector<Object> vector, int i, String str) {
        super(context);
        this.control = iControl;
        this.dialogID = i;
        this.model = vector;
        this.action = iDialogAction;
        this.dialogFrame = new ADialogFrame(context, this);
        setTitle(str);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(this.dialogFrame);
        this.dialogFrame.post(new Runnable() {
            public void run() {
                ADialog.this.doLayout();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void dismiss() {
        super.dismiss();
        dispose();
    }

    /* access modifiers changed from: protected */
    public void setSize(int i, int i2) {
        this.dialogFrame.setLayoutParams(new LinearLayout.LayoutParams(i, i2));
    }

    public void dispose() {
        this.control = null;
        Vector<Object> vector = this.model;
        if (vector != null) {
            vector.clear();
            this.model = null;
        }
        this.action = null;
        ADialogFrame aDialogFrame = this.dialogFrame;
        if (aDialogFrame != null) {
            aDialogFrame.dispose();
            this.dialogFrame = null;
        }
        this.ok = null;
        this.cancel = null;
    }
}
