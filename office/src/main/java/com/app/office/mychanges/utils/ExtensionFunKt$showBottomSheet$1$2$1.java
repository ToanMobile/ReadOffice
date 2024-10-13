package com.app.office.mychanges.utils;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.app.office.mychanges.bottomsheetfragment.ViewerToolsBottomSheet2;
import com.app.office.mychanges.interfaces.OnBookmarkCallback;
import com.app.office.mychanges.model.DataModel2;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"com/reader/office/mychanges/utils/ExtensionFunKt$showBottomSheet$1$2$1", "Lcom/reader/office/mychanges/bottomsheetfragment/ViewerToolsBottomSheet2$ItemClickListener;", "onItemClick", "", "item", "", "office_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ExtensionFun.kt */
public final class ExtensionFunKt$showBottomSheet$1$2$1 implements ViewerToolsBottomSheet2.ItemClickListener {
    final /* synthetic */ DataModel2 $docModel;
    final /* synthetic */ AppCompatActivity $this_showBottomSheet;
    final /* synthetic */ View $view;

    ExtensionFunKt$showBottomSheet$1$2$1(AppCompatActivity appCompatActivity, DataModel2 dataModel2, View view) {
        this.$this_showBottomSheet = appCompatActivity;
        this.$docModel = dataModel2;
        this.$view = view;
    }

    public void onItemClick(String str) {
        OnBookmarkCallback bookmarkCallback;
        Intrinsics.checkNotNullParameter(str, "item");
        switch (str.hashCode()) {
            case -1881265346:
                if (str.equals("RENAME")) {
                    ExtensionFunKt.showRenameDialog(this.$this_showBottomSheet, this.$docModel.getName(), this.$docModel.getPath());
                    return;
                }
                return;
            case -1506962122:
                if (str.equals("BOOKMARK") && (bookmarkCallback = ExtensionFunKt.getBookmarkCallback()) != null) {
                    bookmarkCallback.onBookmark(new File(this.$docModel.getPath()), this.$docModel.isBookmarked());
                    return;
                }
                return;
            case 78660461:
                if (str.equals(ViewerToolsBottomSheet2.SHARE)) {
                    ExtensionFunKt.shareDocument(this.$this_showBottomSheet, this.$docModel.getPath());
                    return;
                }
                return;
            case 2012838315:
                if (str.equals("DELETE")) {
                    ExtensionFunKt.showDeleteDialog(this.$this_showBottomSheet, this.$view, new File(this.$docModel.getPath()));
                    return;
                }
                return;
            default:
                return;
        }
    }
}
