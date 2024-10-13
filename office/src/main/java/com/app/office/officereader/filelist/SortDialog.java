package com.app.office.officereader.filelist;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.app.office.R;
import com.app.office.officereader.beans.SingleChoiceList;
import com.app.office.system.IControl;
import com.app.office.system.IDialogAction;
import com.app.office.system.beans.ADialog;
import java.util.Vector;

public class SortDialog extends ADialog {
    private SingleChoiceList singleChoiceList;
    private RadioGroup sortGroup;

    public SortDialog(IControl iControl, Context context, IDialogAction iDialogAction, Vector<Object> vector, int i, int i2, int i3) {
        super(iControl, context, iDialogAction, vector, i, i2);
        init(context, i3);
    }

    public void init(Context context, int i) {
        int i2 = context.getResources().getDisplayMetrics().widthPixels - 60;
        int intValue = this.model != null ? ((Integer) this.model.get(0)).intValue() : 0;
        SingleChoiceList singleChoiceList2 = new SingleChoiceList(context, i);
        this.singleChoiceList = singleChoiceList2;
        if (intValue >= 0) {
            singleChoiceList2.setItemChecked(intValue, true);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i2, 100);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        this.dialogFrame.addView(this.singleChoiceList, layoutParams);
        View view = new View(context);
        view.setBackgroundColor(-7829368);
        this.dialogFrame.addView(view, new LinearLayout.LayoutParams(-1, 1));
        RadioGroup radioGroup = new RadioGroup(context);
        this.sortGroup = radioGroup;
        radioGroup.setOrientation(0);
        this.sortGroup.setGravity(17);
        int i3 = i2 / 2;
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i3, -2);
        RadioButton radioButton = new RadioButton(context);
        radioButton.setText(R.string.dialog_ascending);
        this.sortGroup.addView(radioButton, layoutParams2);
        RadioButton radioButton2 = new RadioButton(context);
        radioButton2.setText(R.string.dialog_descending);
        this.sortGroup.addView(radioButton2, layoutParams2);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(i2, -2);
        layoutParams3.leftMargin = 5;
        layoutParams3.rightMargin = 5;
        this.dialogFrame.addView(this.sortGroup, layoutParams3);
        ((RadioButton) this.sortGroup.getChildAt(this.model != null ? ((Integer) this.model.get(1)).intValue() : 0)).setChecked(true);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setGravity(17);
        linearLayout.setOrientation(0);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(i3, -2);
        this.ok = new Button(context);
        this.ok.setText(R.string.sys_button_ok);
        this.ok.setOnClickListener(this);
        linearLayout.addView(this.ok, layoutParams4);
        this.cancel = new Button(context);
        this.cancel.setText(R.string.sys_button_cancel);
        this.cancel.setOnClickListener(this);
        linearLayout.addView(this.cancel, layoutParams4);
        this.dialogFrame.addView(linearLayout);
    }

    public void doLayout() {
        int i;
        int i2;
        int i3 = getContext().getResources().getDisplayMetrics().widthPixels;
        int height = getContext().getResources().getDisplayMetrics().heightPixels - (getWindow().getDecorView().getHeight() - this.dialogFrame.getHeight());
        if (this.control.getSysKit().isVertical(getContext())) {
            i2 = i3 - 60;
            i = height - 330;
        } else {
            i2 = i3 - 240;
            i = height - 60;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i2 - 10, ((i - this.sortGroup.getHeight()) - this.ok.getHeight()) - 20);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        this.singleChoiceList.setLayoutParams(layoutParams);
        int i4 = i2 / 2;
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i4, -2);
        ((RadioButton) this.sortGroup.getChildAt(0)).setLayoutParams(layoutParams2);
        ((RadioButton) this.sortGroup.getChildAt(1)).setLayoutParams(layoutParams2);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(i2, this.sortGroup.getHeight());
        layoutParams3.leftMargin = 5;
        layoutParams3.rightMargin = 5;
        layoutParams3.gravity = 17;
        this.sortGroup.setLayoutParams(layoutParams3);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(i4, -2);
        this.ok.setLayoutParams(layoutParams4);
        this.cancel.setLayoutParams(layoutParams4);
    }

    public void onConfigurationChanged(Configuration configuration) {
        doLayout();
    }

    public void onClick(View view) {
        if (view == this.ok) {
            int checkedItemPosition = this.singleChoiceList.getCheckedItemPosition();
            int checkedRadioButtonId = this.sortGroup.getCheckedRadioButtonId();
            RadioGroup radioGroup = this.sortGroup;
            int indexOfChild = radioGroup.indexOfChild((RadioButton) radioGroup.findViewById(checkedRadioButtonId));
            Vector vector = new Vector();
            vector.add(Integer.valueOf(checkedItemPosition));
            vector.add(Integer.valueOf(indexOfChild));
            this.action.doAction(this.dialogID, vector);
        }
        dismiss();
    }

    public void dispose() {
        super.dispose();
        this.singleChoiceList = null;
        this.sortGroup = null;
    }
}
