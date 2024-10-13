package com.app.office.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.app.office.BR;
import com.app.office.R;
import com.app.office.mychanges.model.DataModel2;
import com.app.office.mychanges.utils.ExtensionFunKt;

public class BottomSheatViewerTools2BindingImpl extends BottomSheatViewerTools2Binding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    /* access modifiers changed from: protected */
    public boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.view, 5);
        sparseIntArray.put(R.id.isLockBtn, 6);
        sparseIntArray.put(R.id.shareBtn, 7);
        sparseIntArray.put(R.id.rename, 8);
        sparseIntArray.put(R.id.bookamrk, 9);
        sparseIntArray.put(R.id.delete, 10);
    }

    public BottomSheatViewerTools2BindingImpl(DataBindingComponent dataBindingComponent, View view) {
        this(dataBindingComponent, view, mapBindings(dataBindingComponent, view, 11, sIncludes, sViewsWithIds));
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private BottomSheatViewerTools2BindingImpl(DataBindingComponent dataBindingComponent, View view, Object[] objArr) {
        super(dataBindingComponent, view, 0, objArr[9], objArr[1], objArr[10], objArr[6], objArr[2], objArr[4], objArr[3], objArr[8], objArr[7], objArr[5]);
        this.mDirtyFlags = -1;
        this.constraintLayout2.setTag((Object) null);
        ConstraintLayout constraintLayout = objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag((Object) null);
        this.pdfImgView.setTag((Object) null);
        this.pdfSizeTxt.setTag((Object) null);
        this.pdfTxt.setTag((Object) null);
        setRootTag(view);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2;
        }
        requestRebind();
    }

    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return false;
        }
    }

    public boolean setVariable(int i, Object obj) {
        if (BR.docModel != i) {
            return false;
        }
        setDocModel((DataModel2) obj);
        return true;
    }

    public void setDocModel(DataModel2 dataModel2) {
        this.mDocModel = dataModel2;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(BR.docModel);
        super.requestRebind();
    }

    /* access modifiers changed from: protected */
    public void executeBindings() {
        long j;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        DataModel2 dataModel2 = this.mDocModel;
        String str7 = null;
        int i = ((j & 3) > 0 ? 1 : ((j & 3) == 0 ? 0 : -1));
        if (i != 0) {
            if (dataModel2 != null) {
                str6 = dataModel2.getName();
                str5 = dataModel2.getSize();
                str4 = dataModel2.getLastModifiedTime();
                str3 = dataModel2.getType();
            } else {
                str6 = null;
                str5 = null;
                str4 = null;
                str3 = null;
            }
            String lowerCase = str6 != null ? str6.toLowerCase() : null;
            if (str3 != null) {
                str7 = str3.toUpperCase();
            }
            str = str6;
            str2 = (((("" + str7) + " | ") + str4) + " | ") + str5;
            str7 = lowerCase;
        } else {
            str2 = null;
            str = null;
        }
        if (i != 0) {
            ExtensionFunKt.setListItemCardViewBackground(this.constraintLayout2, str7);
            ExtensionFunKt.setListItemImageSrc(this.pdfImgView, str7);
            TextViewBindingAdapter.setText(this.pdfSizeTxt, str2);
            TextViewBindingAdapter.setText(this.pdfTxt, str);
        }
    }
}
