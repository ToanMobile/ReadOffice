package com.app.office.macro;

import com.app.office.common.ICustomDialog;

public class MacroCustomDialog implements ICustomDialog {
    private DialogListener dailogListener;

    protected MacroCustomDialog(DialogListener dialogListener) {
        this.dailogListener = dialogListener;
    }

    public void showDialog(byte b) {
        DialogListener dialogListener = this.dailogListener;
        if (dialogListener != null) {
            dialogListener.showDialog(b);
        }
    }

    public void dismissDialog(byte b) {
        DialogListener dialogListener = this.dailogListener;
        if (dialogListener != null) {
            dialogListener.dismissDialog(b);
        }
    }

    public void dispose() {
        this.dailogListener = null;
    }
}
