package com.app.office.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.app.office.R;

public abstract class DialogInfoFooter2Binding extends ViewDataBinding {
    public final Button cancelBtn;
    public final Button processBtn;

    protected DialogInfoFooter2Binding(Object obj, View view, int i, Button button, Button button2) {
        super(obj, view, i);
        this.cancelBtn = button;
        this.processBtn = button2;
    }

    public static DialogInfoFooter2Binding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogInfoFooter2Binding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (DialogInfoFooter2Binding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.dialog_info_footer_2, viewGroup, z, obj);
    }

    public static DialogInfoFooter2Binding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogInfoFooter2Binding inflate(LayoutInflater layoutInflater, Object obj) {
        return (DialogInfoFooter2Binding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.dialog_info_footer_2, (ViewGroup) null, false, obj);
    }

    public static DialogInfoFooter2Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogInfoFooter2Binding bind(View view, Object obj) {
        return (DialogInfoFooter2Binding) bind(obj, view, R.layout.dialog_info_footer_2);
    }
}
