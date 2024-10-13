package com.app.office.mychanges.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.google.android.material.textfield.TextInputEditText;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.app.office.R;
import com.app.office.constant.MainConstant;
import com.app.office.databinding.DialogFileSave2Binding;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import com.app.office.mychanges.utils.ExtensionFunKt;
import java.io.File;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\u0016\b\u0002\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n\u0018\u00010\t¢\u0006\u0002\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lcom/reader/office/mychanges/dialogs/FileSaveDialog2;", "Landroid/app/Dialog;", "context", "Landroid/content/Context;", "filePath", "", "defaultFileName", "type", "onResult", "Lkotlin/Function1;", "", "(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", "binding", "Lcom/reader/office/databinding/DialogFileSave2Binding;", "getBinding", "()Lcom/reader/office/databinding/DialogFileSave2Binding;", "setBinding", "(Lcom/reader/office/databinding/DialogFileSave2Binding;)V", "office_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: FileSaveDialog2.kt */
public final class FileSaveDialog2 extends Dialog {
    private DialogFileSave2Binding binding;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ FileSaveDialog2(Context context, String str, String str2, String str3, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, str, (i & 4) != 0 ? "" : str2, (i & 8) != 0 ? "" : str3, (i & 16) != 0 ? null : function1);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FileSaveDialog2(Context context, String str, String str2, String str3, Function1<? super String, Unit> function1) {
        super(context, R.style.PauseDialog);
        String str4;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(str, MainConstant.INTENT_FILED_FILE_PATH);
        Intrinsics.checkNotNullParameter(str3, DublinCoreProperties.TYPE);
        requestWindowFeature(1);
        Window window = getWindow();
        Intrinsics.checkNotNull(window);
        WindowManager.LayoutParams attributes = window.getAttributes();
        Intrinsics.checkNotNullExpressionValue(attributes, "window!!.attributes");
        Window window2 = getWindow();
        Intrinsics.checkNotNull(window2);
        window2.setAttributes(attributes);
        Window window3 = getWindow();
        Intrinsics.checkNotNull(window3);
        window3.setBackgroundDrawable(new ColorDrawable(0));
        Object systemService = context.getSystemService("layout_inflater");
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.view.LayoutInflater");
        DialogFileSave2Binding inflate = DialogFileSave2Binding.inflate((LayoutInflater) systemService);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(context.getSyste…RVICE) as LayoutInflater)");
        this.binding = inflate;
        TextInputEditText textInputEditText = inflate.fileNameEditText;
        if (str2 == null) {
            str4 = null;
        } else {
            str4 = ExtensionFunKt.changeExtension(str2, "");
        }
        textInputEditText.setText(str4);
        textInputEditText.setFocusable(true);
        textInputEditText.requestFocus();
        textInputEditText.setMaxEms(10);
        textInputEditText.setFilters((InputFilter[]) new InputFilter.LengthFilter[]{new InputFilter.LengthFilter(30)});
        this.binding.fileNameEditText.addTextChangedListener(new TextWatcher(this) {
            final /* synthetic */ FileSaveDialog2 this$0;

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            {
                this.this$0 = r1;
            }

            public void afterTextChanged(Editable editable) {
                if (StringsKt.startsWith$default(String.valueOf(this.this$0.getBinding().fileNameEditText.getText()), " ", false, 2, (Object) null)) {
                    this.this$0.getBinding().fileNameEditText.setText("");
                }
            }
        });
        this.binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                FileSaveDialog2.m206_init_$lambda1(FileSaveDialog2.this, view);
            }
        });
        this.binding.processBtn.setOnClickListener(new View.OnClickListener(str3, str2, str, function1) {
            public final /* synthetic */ String f$1;
            public final /* synthetic */ String f$2;
            public final /* synthetic */ String f$3;
            public final /* synthetic */ Function1 f$4;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
                this.f$4 = r5;
            }

            public final void onClick(View view) {
                FileSaveDialog2.m207_init_$lambda2(FileSaveDialog2.this, this.f$1, this.f$2, this.f$3, this.f$4, view);
            }
        });
        setContentView(this.binding.getRoot());
    }

    public final DialogFileSave2Binding getBinding() {
        return this.binding;
    }

    public final void setBinding(DialogFileSave2Binding dialogFileSave2Binding) {
        Intrinsics.checkNotNullParameter(dialogFileSave2Binding, "<set-?>");
        this.binding = dialogFileSave2Binding;
    }

    /* access modifiers changed from: private */
    /* renamed from: _init_$lambda-1  reason: not valid java name */
    public static final void m206_init_$lambda1(FileSaveDialog2 fileSaveDialog2, View view) {
        Intrinsics.checkNotNullParameter(fileSaveDialog2, "this$0");
        fileSaveDialog2.dismiss();
    }

    /* access modifiers changed from: private */
    /* renamed from: _init_$lambda-2  reason: not valid java name */
    public static final void m207_init_$lambda2(FileSaveDialog2 fileSaveDialog2, String str, String str2, String str3, Function1 function1, View view) {
        String str4;
        String fileNameExtension;
        FileSaveDialog2 fileSaveDialog22 = fileSaveDialog2;
        String str5 = str;
        String str6 = str3;
        Function1 function12 = function1;
        View view2 = view;
        Intrinsics.checkNotNullParameter(fileSaveDialog22, "this$0");
        Intrinsics.checkNotNullParameter(str5, "$type");
        Intrinsics.checkNotNullParameter(str6, "$filePath");
        if (String.valueOf(fileSaveDialog22.binding.fileNameEditText.getText()).length() > 0) {
            if (Intrinsics.areEqual((Object) str5, (Object) "RENAME")) {
                TextInputEditText textInputEditText = fileSaveDialog22.binding.fileNameEditText;
                String valueOf = String.valueOf(fileSaveDialog22.binding.fileNameEditText.getText());
                String str7 = "";
                if (!(str2 == null || (fileNameExtension = ExtensionFunKt.getFileNameExtension(str2)) == null)) {
                    str7 = fileNameExtension;
                }
                textInputEditText.setText(ExtensionFunKt.changeExtension(valueOf, str7));
                if (!ExtensionFunKt.isValidFileName(String.valueOf(fileSaveDialog22.binding.fileNameEditText.getText()))) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(fileSaveDialog22.binding.fileNameEditText.getText());
                    sb.append(str2 == null ? null : ExtensionFunKt.getFileNameExtension(str2));
                    str4 = sb.toString();
                } else {
                    str4 = String.valueOf(fileSaveDialog22.binding.fileNameEditText.getText());
                }
            } else if (!StringsKt.contains$default((CharSequence) String.valueOf(fileSaveDialog22.binding.fileNameEditText.getText()), (CharSequence) ".pdf", false, 2, (Object) null)) {
                str4 = fileSaveDialog22.binding.fileNameEditText.getText() + ".pdf";
            } else {
                str4 = String.valueOf(fileSaveDialog22.binding.fileNameEditText.getText());
            }
            if (!new File(str6, str4).exists()) {
                String substring = str6.substring(0, StringsKt.lastIndexOf$default((CharSequence) str6, PackagingURIHelper.FORWARD_SLASH_STRING, 0, false, 6, (Object) null) + 1);
                Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
                File file = new File(substring);
                Log.d("dir", Intrinsics.stringPlus("dir", file));
                File file2 = new File(file, str4);
                Log.d("newFileDir", Intrinsics.stringPlus("dir", file2.getAbsolutePath()));
                if (!file2.exists()) {
                    fileSaveDialog2.dismiss();
                    if (function12 != null) {
                        String name = file2.getName();
                        Intrinsics.checkNotNullExpressionValue(name, "newFile.name");
                        function12.invoke(name);
                        return;
                    }
                    return;
                }
                Intrinsics.checkNotNullExpressionValue(view2, "it");
                ExtensionFunKt.snack$default(view2, "file name already exist", 0, 2, (Object) null);
                return;
            }
            Intrinsics.checkNotNullExpressionValue(view2, "it");
            ExtensionFunKt.snack$default(view2, "file name already exist", 0, 2, (Object) null);
            return;
        }
        Intrinsics.checkNotNullExpressionValue(view2, "it");
        ExtensionFunKt.snack$default(view2, "Enter name", 0, 2, (Object) null);
    }
}
