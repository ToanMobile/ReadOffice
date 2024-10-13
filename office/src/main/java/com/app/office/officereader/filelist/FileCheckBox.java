package com.app.office.officereader.filelist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import com.app.office.officereader.FileListActivity;
import com.app.office.system.IControl;

public class FileCheckBox extends CheckBox {
    private IControl control;
    private FileItem fileItem;

    public FileCheckBox(Context context, IControl iControl, FileItem fileItem2) {
        super(context);
        this.fileItem = fileItem2;
        this.control = iControl;
        setChecked(fileItem2.isCheck());
        setFocusable(false);
    }

    public FileCheckBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean performClick() {
        boolean performClick = super.performClick();
        this.fileItem.setCheck(isChecked());
        FileListActivity fileListActivity = (FileListActivity) this.control.getActivity();
        if (isChecked()) {
            fileListActivity.addSelectFileItem(this.fileItem);
        } else {
            fileListActivity.removeSelectFileItem(this.fileItem);
        }
        return performClick;
    }

    public void setFileItem(FileItem fileItem2) {
        this.fileItem = fileItem2;
        setChecked(fileItem2.isCheck());
    }

    public void dispose() {
        this.fileItem = null;
        this.control = null;
    }
}
