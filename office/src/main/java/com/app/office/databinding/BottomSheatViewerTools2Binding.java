package com.app.office.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.card.MaterialCardView;
import com.app.office.R;
import com.app.office.mychanges.model.DataModel2;

public abstract class BottomSheatViewerTools2Binding extends ViewDataBinding {
    public final TextView bookamrk;
    public final MaterialCardView constraintLayout2;
    public final TextView delete;
    public final ImageView isLockBtn;
    @Bindable
    protected DataModel2 mDocModel;
    public final ImageView pdfImgView;
    public final TextView pdfSizeTxt;
    public final TextView pdfTxt;
    public final TextView rename;
    public final TextView shareBtn;
    public final View view;

    public abstract void setDocModel(DataModel2 dataModel2);

    protected BottomSheatViewerTools2Binding(Object obj, View view2, int i, TextView textView, MaterialCardView materialCardView, TextView textView2, ImageView imageView, ImageView imageView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6, View view3) {
        super(obj, view2, i);
        this.bookamrk = textView;
        this.constraintLayout2 = materialCardView;
        this.delete = textView2;
        this.isLockBtn = imageView;
        this.pdfImgView = imageView2;
        this.pdfSizeTxt = textView3;
        this.pdfTxt = textView4;
        this.rename = textView5;
        this.shareBtn = textView6;
        this.view = view3;
    }

    public DataModel2 getDocModel() {
        return this.mDocModel;
    }

    public static BottomSheatViewerTools2Binding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static BottomSheatViewerTools2Binding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (BottomSheatViewerTools2Binding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.bottom_sheat_viewer_tools_2, viewGroup, z, obj);
    }

    public static BottomSheatViewerTools2Binding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static BottomSheatViewerTools2Binding inflate(LayoutInflater layoutInflater, Object obj) {
        return (BottomSheatViewerTools2Binding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.bottom_sheat_viewer_tools_2, (ViewGroup) null, false, obj);
    }

    public static BottomSheatViewerTools2Binding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static BottomSheatViewerTools2Binding bind(View view2, Object obj) {
        return (BottomSheatViewerTools2Binding) bind(obj, view2, R.layout.bottom_sheat_viewer_tools_2);
    }
}
