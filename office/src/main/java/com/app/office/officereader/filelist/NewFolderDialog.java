package com.app.office.officereader.filelist;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import com.app.office.R;
import com.app.office.system.IControl;
import com.app.office.system.IDialogAction;
import com.app.office.system.dialog.MessageDialog;
import java.io.File;
import java.util.Vector;

public class NewFolderDialog extends FileNameDialog {
    private TextWatcher watcher = new TextWatcher() {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            NewFolderDialog.this.ok.setEnabled(NewFolderDialog.this.isFileNameOK(charSequence.toString()));
        }
    };

    public NewFolderDialog(IControl iControl, Context context, IDialogAction iDialogAction, Vector<Object> vector, int i, int i2) {
        super(iControl, context, iDialogAction, vector, i, i2);
        initDialog();
    }

    public void initDialog() {
        this.textView.setText(R.string.dialog_folder_name);
        this.editText.addTextChangedListener(this.watcher);
    }

    public void onClick(View view) {
        File file;
        if (view != this.ok) {
            dismiss();
        } else if (this.model == null) {
            dismiss();
        } else {
            String obj = this.model.get(0).toString();
            String trim = this.editText.getText().toString().trim();
            if (obj.endsWith(File.separator)) {
                file = new File(obj + trim);
            } else {
                file = new File(obj + File.separator + trim);
            }
            Vector vector = new Vector();
            vector.add(file);
            if (!file.exists()) {
                this.action.doAction(this.dialogID, vector);
                dismiss();
                return;
            }
            new MessageDialog(this.control, getContext(), this.action, (Vector<Object>) null, 0, R.string.dialog_create_folder_error, getContext().getResources().getText(R.string.dialog_name_error).toString().replace("%s", trim)).show();
        }
    }
}
