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

public class FileRenameDialog extends FileNameDialog {
    private TextWatcher watcher = new TextWatcher() {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            FileRenameDialog.this.ok.setEnabled(FileRenameDialog.this.checkFileName(charSequence.toString()));
        }
    };

    public FileRenameDialog(IControl iControl, Context context, IDialogAction iDialogAction, Vector<Object> vector, int i, int i2) {
        super(iControl, context, iDialogAction, vector, i, i2);
        initDialog();
    }

    public void initDialog() {
        if (this.model != null) {
            File file = (File) this.model.get(0);
            String name = file.getName();
            if (file.isFile()) {
                this.textView.setText(R.string.dialog_file_name);
                name = name.substring(0, name.lastIndexOf(46));
            } else {
                this.textView.setText(R.string.dialog_folder_name);
            }
            this.editText.setText(name);
            this.editText.addTextChangedListener(this.watcher);
        }
    }

    public void onClick(View view) {
        String str;
        File file;
        if (view != this.ok) {
            dismiss();
        } else if (this.model == null) {
            dismiss();
        } else {
            File file2 = (File) this.model.get(0);
            if (file2.isFile()) {
                String name = file2.getName();
                str = name.substring(name.lastIndexOf(46));
            } else {
                str = "";
            }
            String parent = file2.getParent();
            String trim = this.editText.getText().toString().trim();
            if (parent.endsWith(File.separator)) {
                file = new File(parent + trim + str);
            } else {
                file = new File(parent + File.separator + trim + str);
            }
            Vector vector = new Vector();
            vector.add(file2);
            vector.add(file);
            if (!file.exists()) {
                this.action.doAction(this.dialogID, vector);
                dismiss();
                return;
            }
            new MessageDialog(this.control, getContext(), this.action, (Vector<Object>) null, 0, R.string.dialog_file_rename_error, getContext().getResources().getText(R.string.dialog_name_error).toString().replace("%s", trim)).show();
        }
    }

    public boolean checkFileName(String str) {
        if (this.model == null || !((File) this.model.get(0)).getName().equals(str)) {
            return isFileNameOK(str);
        }
        return false;
    }
}
