package com.app.office.mychanges.interfaces;

import java.io.File;
import kotlin.Metadata;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&¨\u0006\u0007"}, d2 = {"Lcom/reader/office/mychanges/interfaces/OnRenameCallback;", "", "onRename", "", "currentFile", "Ljava/io/File;", "newFile", "office_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: OnRenameCallback.kt */
public interface OnRenameCallback {
    void onRename(File file, File file2);
}
