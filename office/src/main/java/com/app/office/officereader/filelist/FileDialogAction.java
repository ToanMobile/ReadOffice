package com.app.office.officereader.filelist;

import com.app.office.constant.EventConstant;
import com.app.office.system.FileKit;
import com.app.office.system.IControl;
import com.app.office.system.IDialogAction;
import java.io.File;
import java.util.Vector;

public class FileDialogAction implements IDialogAction {
    public IControl control;

    public FileDialogAction(IControl iControl) {
        this.control = iControl;
    }

    public void doAction(int i, Vector<Object> vector) {
        if (vector != null) {
            if (i != 2) {
                if (i == 3) {
                    ((File) vector.get(0)).renameTo((File) vector.get(1));
                    this.control.actionEvent(EventConstant.FILE_REFRESH_ID, true);
                } else if (i == 4) {
                    int size = vector.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        FileKit.instance().deleteFile((File) vector.get(i2));
                    }
                    this.control.actionEvent(EventConstant.FILE_REFRESH_ID, (Object) null);
                } else if (i == 5) {
                    File file = (File) vector.get(1);
                    File file2 = (File) vector.get(2);
                    if (!file.equals(file2)) {
                        FileKit.instance().pasteFile(file, file2);
                        if (((Boolean) vector.get(0)).booleanValue()) {
                            FileKit.instance().deleteFile(file);
                        }
                    }
                } else if (i == 6) {
                    this.control.actionEvent(EventConstant.FILE_SORT_TYPE_ID, vector);
                }
            } else if (((File) vector.get(0)).mkdir()) {
                this.control.actionEvent(EventConstant.FILE_REFRESH_ID, (Object) null);
            } else {
                this.control.actionEvent(EventConstant.FILE_CREATE_FOLDER_FAILED_ID, (Object) null);
            }
        }
    }

    public IControl getControl() {
        return this.control;
    }

    public void dispose() {
        this.control = null;
    }
}
