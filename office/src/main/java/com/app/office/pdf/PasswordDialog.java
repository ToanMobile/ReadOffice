package com.app.office.pdf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.widget.EditText;
import com.app.office.common.ICustomDialog;
import com.app.office.constant.EventConstant;
import com.app.office.fc.pdf.PDFLib;
import com.app.office.res.ResKit;
import com.app.office.system.IControl;

public class PasswordDialog {
    private AlertDialog.Builder alertBuilder;
    /* access modifiers changed from: private */
    public IControl control;
    private PDFLib lib;
    /* access modifiers changed from: private */
    public EditText pwView;

    public PasswordDialog(IControl iControl, PDFLib pDFLib) {
        this.control = iControl;
        this.lib = pDFLib;
    }

    public void show() {
        if (this.control.getMainFrame().isShowPasswordDlg()) {
            this.alertBuilder = new AlertDialog.Builder(this.control.getActivity());
            requestPassword(this.lib);
            return;
        }
        ICustomDialog customDialog = this.control.getCustomDialog();
        if (customDialog != null) {
            customDialog.showDialog((byte) 0);
        }
    }

    /* access modifiers changed from: private */
    public void requestPassword(final PDFLib pDFLib) {
        EditText editText = new EditText(this.control.getActivity());
        this.pwView = editText;
        editText.setInputType(128);
        this.pwView.setTransformationMethod(new PasswordTransformationMethod());
        AlertDialog create = this.alertBuilder.create();
        create.setTitle(ResKit.instance().getLocalString("DIALOG_ENTER_PASSWORD"));
        create.setView(this.pwView);
        create.setButton(-1, ResKit.instance().getLocalString("BUTTON_OK"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (pDFLib.authenticatePasswordSync(PasswordDialog.this.pwView.getText().toString())) {
                    PasswordDialog.this.control.actionEvent(EventConstant.APP_PASSWORD_OK_INIT, (Object) null);
                } else {
                    PasswordDialog.this.requestPassword(pDFLib);
                }
            }
        });
        create.setButton(-2, ResKit.instance().getLocalString("BUTTON_CANCEL"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                PasswordDialog.this.control.getActivity().onBackPressed();
            }
        });
        create.setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i != 4) {
                    return false;
                }
                dialogInterface.dismiss();
                PasswordDialog.this.control.getActivity().onBackPressed();
                return true;
            }
        });
        create.show();
    }
}
