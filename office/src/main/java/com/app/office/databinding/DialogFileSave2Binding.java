package com.app.office.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.app.office.R;

public abstract class DialogFileSave2Binding extends ViewDataBinding {
    public final MaterialButton cancelBtn;
    public final TextInputEditText fileNameEditText;
    public final ImageView imageView;
    public final MaterialButton processBtn;
    public final TextView textView;

    protected DialogFileSave2Binding(Object obj, View view, int i, MaterialButton materialButton, TextInputEditText textInputEditText, ImageView imageView2, MaterialButton materialButton2, TextView textView2) {
        super(obj, view, i);
        this.cancelBtn = materialButton;
        this.fileNameEditText = textInputEditText;
        this.imageView = imageView2;
        this.processBtn = materialButton2;
        this.textView = textView2;
    }

    public static DialogFileSave2Binding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFileSave2Binding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (DialogFileSave2Binding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.dialog_file_save_2, viewGroup, z, obj);
    }

    public static DialogFileSave2Binding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFileSave2Binding inflate(LayoutInflater layoutInflater, Object obj) {
        return (DialogFileSave2Binding) ViewDataBinding.inflateInternal(layoutInflater, R.layout.dialog_file_save_2, (ViewGroup) null, false, obj);
    }

    public static DialogFileSave2Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogFileSave2Binding bind(View view, Object obj) {
        return (DialogFileSave2Binding) bind(obj, view, R.layout.dialog_file_save_2);
    }
}
