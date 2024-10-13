package com.app.office.officereader.beans;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.app.office.R;
import com.app.office.system.IControl;

public class AImageFindButton extends LinearLayout implements GestureDetector.OnGestureListener, View.OnClickListener {
    private AImageButton btn;
    protected IControl control;
    private EditText editText;
    private boolean longPressed;

    public void onConfigurationChanged(Configuration configuration) {
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AImageFindButton(Context context, IControl iControl, String str, int i, int i2, int i3, int i4, int i5, int i6, TextWatcher textWatcher) {
        super(context);
        IControl iControl2 = iControl;
        this.control = iControl2;
        setOrientation(0);
        setVerticalGravity(17);
        EditText editText2 = new EditText(getContext());
        this.editText = editText2;
        editText2.setFreezesText(false);
        this.editText.setGravity(17);
        this.editText.setSingleLine();
        this.editText.addTextChangedListener(textWatcher);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i4 - 10, -2);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        addView(this.editText, layoutParams);
        AImageButton aImageButton = new AImageButton(context, iControl2, str, i, i2, i3);
        this.btn = aImageButton;
        aImageButton.setNormalBgResID(R.drawable.sys_toolsbar_button_bg_normal);
        this.btn.setPushBgResID(R.drawable.sys_toolsbar_button_bg_push);
        this.btn.setLayoutParams(new LinearLayout.LayoutParams(i5, i6));
        this.btn.setOnClickListener(this);
        this.btn.setEnabled(false);
        addView(this.btn);
    }

    public AImageFindButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void resetEditTextWidth(int i) {
        this.editText.getLayoutParams().width = i;
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setBackgroundResource(R.drawable.sys_toolsbar_button_bg_normal);
    }

    public void onDraw(Canvas canvas) {
        this.btn.onDraw(canvas);
    }

    public void onClick(View view) {
        if (!this.longPressed && (view instanceof AImageButton)) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
            inputMethodManager.hideSoftInputFromWindow(this.editText.getWindowToken(), 2);
            inputMethodManager.hideSoftInputFromInputMethod(this.editText.getWindowToken(), 2);
            this.control.actionEvent(((AImageButton) view).getActionID(), this.editText.getText().toString());
        }
        this.longPressed = false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.btn.onTouchEvent(motionEvent);
    }

    public boolean onDown(MotionEvent motionEvent) {
        return this.btn.onDown(motionEvent);
    }

    public void onShowPress(MotionEvent motionEvent) {
        this.btn.onShowPress(motionEvent);
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return this.btn.onSingleTapUp(motionEvent);
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return this.btn.onScroll(motionEvent, motionEvent2, f, f2);
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return this.btn.onFling(motionEvent, motionEvent2, f, f2);
    }

    public void onLongPress(MotionEvent motionEvent) {
        this.longPressed = true;
        this.btn.onLongPress(motionEvent);
    }

    public void reset() {
        this.editText.setText("");
        this.btn.setEnabled(false);
    }

    public void setFindBtnState(boolean z) {
        this.btn.setEnabled(z);
    }

    public void dispose() {
        this.control = null;
        this.editText = null;
        this.btn.dispose();
        this.btn = null;
    }
}
