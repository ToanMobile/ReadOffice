package com.app.office.officereader.filelist;

import android.content.Context;
import android.util.AttributeSet;
import com.app.office.R;
import com.app.office.constant.EventConstant;
import com.app.office.officereader.FileListActivity;
import com.app.office.officereader.beans.AToolsbar;
import com.app.office.system.IControl;
import java.util.List;

public class FileToolsbar extends AToolsbar {
    public FileToolsbar(Context context, IControl iControl) {
        super(context, iControl);
        init();
    }

    public FileToolsbar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void init() {
        addButton(R.drawable.file_createfolder, R.drawable.file_createfolder_disable, R.string.file_toolsbar_create_folder, EventConstant.FILE_CREATE_FOLDER_ID, true);
        addButton(R.drawable.file_rename, R.drawable.file_rename_disable, R.string.file_toolsbar_rename, EventConstant.FILE_RENAME_ID, true);
        addButton(R.drawable.file_copy, R.drawable.file_copy_disable, R.string.file_toolsbar_copy, EventConstant.FILE_COPY_ID, true);
        addButton(R.drawable.file_cut, R.drawable.file_cut_disable, R.string.file_toolsbar_cut, EventConstant.FILE_CUT_ID, true);
        addButton(R.drawable.file_paste, R.drawable.file_paste_disable, R.string.file_toolsbar_paste, EventConstant.FILE_PASTE_ID, true);
        addButton(R.drawable.file_delete, R.drawable.file_delete_disable, R.string.file_toolsbar_delete, EventConstant.FILE_DELETE_ID, true);
        addButton(R.drawable.file_search, R.drawable.file_search_disable, R.string.sys_button_search, 5, true);
        addButton(R.drawable.file_share, R.drawable.file_share_disable, R.string.file_toolsbar_share, EventConstant.FILE_SHARE_ID, true);
        addButton(R.drawable.file_sort, R.drawable.file_sort_disable, R.string.file_toolsbar_sort, EventConstant.FILE_SORT_ID, true);
        addButton(R.drawable.file_star, R.drawable.file_star_disable, R.string.file_toolsbar_mark_star, EventConstant.FILE_MARK_STAR_ID, true);
    }

    public void updateStatus() {
        FileListActivity fileListActivity = (FileListActivity) this.control.getActivity();
        boolean z = true;
        if (fileListActivity.isCurrentDirectory4mnt()) {
            setEnabled(EventConstant.FILE_CREATE_FOLDER_ID, false);
            setEnabled(EventConstant.FILE_RENAME_ID, false);
            setEnabled(EventConstant.FILE_COPY_ID, false);
            setEnabled(EventConstant.FILE_CUT_ID, false);
            setEnabled(EventConstant.FILE_PASTE_ID, false);
            setEnabled(EventConstant.FILE_DELETE_ID, false);
            setEnabled(5, true);
            setEnabled(EventConstant.FILE_SHARE_ID, false);
            setEnabled(EventConstant.FILE_SORT_ID, false);
            setEnabled(EventConstant.FILE_MARK_STAR_ID, false);
            return;
        }
        List<FileItem> selectFileItem = fileListActivity.getSelectFileItem();
        int size = selectFileItem.size();
        if (fileListActivity.getListType() == 0) {
            setEnabled(EventConstant.FILE_CREATE_FOLDER_ID, true);
            setEnabled(EventConstant.FILE_RENAME_ID, size == 1);
            setEnabled(EventConstant.FILE_COPY_ID, size > 0);
            setEnabled(EventConstant.FILE_CUT_ID, size > 0);
            setEnabled(EventConstant.FILE_PASTE_ID, fileListActivity.isCopy() || fileListActivity.isCut());
            setEnabled(EventConstant.FILE_DELETE_ID, size > 0);
            setEnabled(5, fileListActivity.getCurrentDirectoryFileSize() > 0);
            boolean z2 = size > 0;
            if (z2) {
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    } else if (selectFileItem.get(i).getFile().isDirectory()) {
                        z2 = false;
                        break;
                    } else {
                        i++;
                    }
                }
            }
            setEnabled(EventConstant.FILE_SHARE_ID, z2);
            setEnabled(EventConstant.FILE_SORT_ID, fileListActivity.getCurrentDirectoryFileSize() > 1);
            if (size != 1 || !selectFileItem.get(0).getFile().isFile()) {
                z = false;
            }
            setEnabled(EventConstant.FILE_MARK_STAR_ID, z);
            return;
        }
        setEnabled(EventConstant.FILE_CREATE_FOLDER_ID, false);
        setEnabled(EventConstant.FILE_RENAME_ID, false);
        setEnabled(EventConstant.FILE_COPY_ID, false);
        setEnabled(EventConstant.FILE_CUT_ID, false);
        setEnabled(EventConstant.FILE_PASTE_ID, false);
        setEnabled(EventConstant.FILE_DELETE_ID, false);
        setEnabled(5, fileListActivity.getCurrentDirectoryFileSize() > 0);
        setEnabled(EventConstant.FILE_SHARE_ID, size > 0);
        setEnabled(EventConstant.FILE_SORT_ID, fileListActivity.getCurrentDirectoryFileSize() > 1);
        if (size != 1 || !selectFileItem.get(0).getFile().isFile()) {
            z = false;
        }
        setEnabled(EventConstant.FILE_MARK_STAR_ID, z);
    }

    public void dispose() {
        super.dispose();
    }
}
