package com.app.office.mychanges.utils;

import com.app.office.mychanges.interfaces.OnDeleteCallback;
import java.io.File;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
/* compiled from: ExtensionFun.kt */
final class ExtensionFunKt$showDeleteDialog$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ File $currentFile;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ExtensionFunKt$showDeleteDialog$1(File file) {
        super(0);
        this.$currentFile = file;
    }

    public final void invoke() {
        OnDeleteCallback deleteCallback = ExtensionFunKt.getDeleteCallback();
        if (deleteCallback != null) {
            deleteCallback.onDelete(this.$currentFile);
        }
    }
}
