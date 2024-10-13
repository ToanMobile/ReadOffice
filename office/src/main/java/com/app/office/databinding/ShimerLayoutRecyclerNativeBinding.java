package com.app.office.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.card.MaterialCardView;
import com.app.office.R;

public abstract class ShimerLayoutRecyclerNativeBinding extends ViewDataBinding {
    public final View adBody;
    public final View adHeadline;
    public final View adLabel;
    public final MaterialCardView iconCard;
    public final MaterialCardView mediaCard;
    public final View tvActionBtnTitle;

    protected ShimerLayoutRecyclerNativeBinding(Object obj, View view, int i, View view2, View view3, View view4, MaterialCardView materialCardView, MaterialCardView materialCardView2, View view5) {
        super(obj, view, i);
        this.adBody = view2;
        this.adHeadline = view3;
        this.adLabel = view4;
        this.iconCard = materialCardView;
        this.mediaCard = materialCardView2;
        this.tvActionBtnTitle = view5;
    }

    public static ShimerLayoutRecyclerNativeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ShimerLayoutRecyclerNativeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ShimerLayoutRecyclerNativeBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.shimer_layout_recycler_native, viewGroup, z, obj);
    }

    public static ShimerLayoutRecyclerNativeBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ShimerLayoutRecyclerNativeBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ShimerLayoutRecyclerNativeBinding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.shimer_layout_recycler_native, (ViewGroup) null, false, obj);
    }

    public static ShimerLayoutRecyclerNativeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ShimerLayoutRecyclerNativeBinding bind(View view, Object obj) {
        return (ShimerLayoutRecyclerNativeBinding) bind(obj, view, R.layout.shimer_layout_recycler_native);
    }
}
