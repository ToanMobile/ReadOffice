package com.app.office.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.app.office.R;

public abstract class DialogInfoHeader2Binding extends ViewDataBinding {
    public final TextView title;

    protected DialogInfoHeader2Binding(Object obj, View view, int i, TextView textView) {
        super(obj, view, i);
        this.title = textView;
    }

    public static DialogInfoHeader2Binding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogInfoHeader2Binding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (DialogInfoHeader2Binding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.dialog_info_header_2, viewGroup, z, obj);
    }

    public static DialogInfoHeader2Binding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogInfoHeader2Binding inflate(LayoutInflater layoutInflater, Object obj) {
        return (DialogInfoHeader2Binding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.dialog_info_header_2, (ViewGroup) null, false, obj);
    }

    public static DialogInfoHeader2Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogInfoHeader2Binding bind(View view, Object obj) {
        return (DialogInfoHeader2Binding) bind(obj, view, R.layout.dialog_info_header_2);
    }
}
