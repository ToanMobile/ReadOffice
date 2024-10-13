package com.app.office.mychanges.bottomsheetfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.app.office.R;
import com.app.office.databinding.BottomSheatViewerTools2Binding;
import com.app.office.mychanges.model.DataModel2;
import java.io.Serializable;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001e2\u00020\u0001:\u0002\u001e\u001fB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\u0012\u0010\u0013\u001a\u00020\u00122\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J$\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\u001c\u001a\u00020\u0012H\u0016J\b\u0010\u001d\u001a\u00020\u0012H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006 "}, d2 = {"Lcom/reader/office/mychanges/bottomsheetfragment/ViewerToolsBottomSheet2;", "Lcom/google/android/material/bottomsheet/BottomSheetDialogFragment;", "()V", "binding", "Lcom/reader/office/databinding/BottomSheatViewerTools2Binding;", "getBinding", "()Lcom/reader/office/databinding/BottomSheatViewerTools2Binding;", "setBinding", "(Lcom/reader/office/databinding/BottomSheatViewerTools2Binding;)V", "dataModel", "Lcom/reader/office/mychanges/model/DataModel2;", "mListener", "Lcom/reader/office/mychanges/bottomsheetfragment/ViewerToolsBottomSheet2$ItemClickListener;", "getMListener", "()Lcom/reader/office/mychanges/bottomsheetfragment/ViewerToolsBottomSheet2$ItemClickListener;", "setMListener", "(Lcom/reader/office/mychanges/bottomsheetfragment/ViewerToolsBottomSheet2$ItemClickListener;)V", "initViews", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDetach", "setListeners", "Companion", "ItemClickListener", "office_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ViewerToolsBottomSheet2.kt */
public final class ViewerToolsBottomSheet2 extends BottomSheetDialogFragment {
    public static final String BOOKMARK = "BOOKMARK";
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final String DELETE = "DELETE";
    public static final String RENAME = "RENAME";
    public static final String SHARE = "SAHRE";
    public BottomSheatViewerTools2Binding binding;
    private DataModel2 dataModel;
    private ItemClickListener mListener;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/reader/office/mychanges/bottomsheetfragment/ViewerToolsBottomSheet2$ItemClickListener;", "", "onItemClick", "", "item", "", "office_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: ViewerToolsBottomSheet2.kt */
    public interface ItemClickListener {
        void onItemClick(String str);
    }

    private final void initViews() {
    }

    @JvmStatic
    public static final ViewerToolsBottomSheet2 newInstance(Bundle bundle) {
        return Companion.newInstance(bundle);
    }

    public final BottomSheatViewerTools2Binding getBinding() {
        BottomSheatViewerTools2Binding bottomSheatViewerTools2Binding = this.binding;
        if (bottomSheatViewerTools2Binding != null) {
            return bottomSheatViewerTools2Binding;
        }
        Intrinsics.throwUninitializedPropertyAccessException("binding");
        return null;
    }

    public final void setBinding(BottomSheatViewerTools2Binding bottomSheatViewerTools2Binding) {
        Intrinsics.checkNotNullParameter(bottomSheatViewerTools2Binding, "<set-?>");
        this.binding = bottomSheatViewerTools2Binding;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(0, R.style.CustomBottomSheetDialogTheme);
        Bundle arguments = getArguments();
        if (arguments != null) {
            Serializable serializable = arguments.getSerializable("file");
            Objects.requireNonNull(serializable, "null cannot be cast to non-null type com.app.office.mychanges.model.DataModel2");
            this.dataModel = (DataModel2) serializable;
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(layoutInflater, "inflater");
        BottomSheatViewerTools2Binding inflate = BottomSheatViewerTools2Binding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(layoutInflater)");
        setBinding(inflate);
        getBinding().setDocModel(this.dataModel);
        initViews();
        setListeners();
        View root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return root;
    }

    private final void setListeners() {
        BottomSheatViewerTools2Binding binding2 = getBinding();
        binding2.view.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                ViewerToolsBottomSheet2.m201setListeners$lambda6$lambda1(ViewerToolsBottomSheet2.this, view);
            }
        });
        binding2.shareBtn.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                ViewerToolsBottomSheet2.m202setListeners$lambda6$lambda2(ViewerToolsBottomSheet2.this, view);
            }
        });
        binding2.rename.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                ViewerToolsBottomSheet2.m203setListeners$lambda6$lambda3(ViewerToolsBottomSheet2.this, view);
            }
        });
        binding2.bookamrk.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                ViewerToolsBottomSheet2.m204setListeners$lambda6$lambda4(ViewerToolsBottomSheet2.this, view);
            }
        });
        binding2.delete.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                ViewerToolsBottomSheet2.m205setListeners$lambda6$lambda5(ViewerToolsBottomSheet2.this, view);
            }
        });
    }

    /* access modifiers changed from: private */
    /* renamed from: setListeners$lambda-6$lambda-1  reason: not valid java name */
    public static final void m201setListeners$lambda6$lambda1(ViewerToolsBottomSheet2 viewerToolsBottomSheet2, View view) {
        Intrinsics.checkNotNullParameter(viewerToolsBottomSheet2, "this$0");
        viewerToolsBottomSheet2.dismissAllowingStateLoss();
    }

    /* access modifiers changed from: private */
    /* renamed from: setListeners$lambda-6$lambda-2  reason: not valid java name */
    public static final void m202setListeners$lambda6$lambda2(ViewerToolsBottomSheet2 viewerToolsBottomSheet2, View view) {
        Intrinsics.checkNotNullParameter(viewerToolsBottomSheet2, "this$0");
        ItemClickListener itemClickListener = viewerToolsBottomSheet2.mListener;
        if (itemClickListener != null) {
            itemClickListener.onItemClick(SHARE);
        }
        viewerToolsBottomSheet2.dismissAllowingStateLoss();
    }

    /* access modifiers changed from: private */
    /* renamed from: setListeners$lambda-6$lambda-3  reason: not valid java name */
    public static final void m203setListeners$lambda6$lambda3(ViewerToolsBottomSheet2 viewerToolsBottomSheet2, View view) {
        Intrinsics.checkNotNullParameter(viewerToolsBottomSheet2, "this$0");
        ItemClickListener itemClickListener = viewerToolsBottomSheet2.mListener;
        if (itemClickListener != null) {
            itemClickListener.onItemClick("RENAME");
        }
        viewerToolsBottomSheet2.dismissAllowingStateLoss();
    }

    /* access modifiers changed from: private */
    /* renamed from: setListeners$lambda-6$lambda-4  reason: not valid java name */
    public static final void m204setListeners$lambda6$lambda4(ViewerToolsBottomSheet2 viewerToolsBottomSheet2, View view) {
        Intrinsics.checkNotNullParameter(viewerToolsBottomSheet2, "this$0");
        ItemClickListener itemClickListener = viewerToolsBottomSheet2.mListener;
        if (itemClickListener != null) {
            itemClickListener.onItemClick("BOOKMARK");
        }
        viewerToolsBottomSheet2.dismissAllowingStateLoss();
    }

    /* access modifiers changed from: private */
    /* renamed from: setListeners$lambda-6$lambda-5  reason: not valid java name */
    public static final void m205setListeners$lambda6$lambda5(ViewerToolsBottomSheet2 viewerToolsBottomSheet2, View view) {
        Intrinsics.checkNotNullParameter(viewerToolsBottomSheet2, "this$0");
        ItemClickListener itemClickListener = viewerToolsBottomSheet2.mListener;
        if (itemClickListener != null) {
            itemClickListener.onItemClick("DELETE");
        }
        viewerToolsBottomSheet2.dismissAllowingStateLoss();
    }

    public final ItemClickListener getMListener() {
        return this.mListener;
    }

    public final void setMListener(ItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/reader/office/mychanges/bottomsheetfragment/ViewerToolsBottomSheet2$Companion;", "", "()V", "BOOKMARK", "", "DELETE", "RENAME", "SHARE", "newInstance", "Lcom/reader/office/mychanges/bottomsheetfragment/ViewerToolsBottomSheet2;", "bundle", "Landroid/os/Bundle;", "office_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: ViewerToolsBottomSheet2.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final ViewerToolsBottomSheet2 newInstance(Bundle bundle) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            ViewerToolsBottomSheet2 viewerToolsBottomSheet2 = new ViewerToolsBottomSheet2();
            viewerToolsBottomSheet2.setArguments(bundle);
            return viewerToolsBottomSheet2;
        }
    }
}
