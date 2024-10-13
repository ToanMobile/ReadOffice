package com.app.office.ss.sheetbar;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.app.office.R;
import com.app.office.constant.EventConstant;
import com.app.office.system.IControl;
import java.util.Vector;

public class SheetBar extends HorizontalScrollView implements View.OnClickListener {
    private IControl control;
    private SheetButton currentSheet;
    private int minimumWidth;
    private LinearLayout sheetbarFrame;
    private int sheetbarHeight;
    private SheetbarResManager sheetbarResManager;

    public SheetBar(Context context) {
        super(context);
    }

    public SheetBar(Context context, IControl iControl, int i) {
        super(context);
        this.control = iControl;
        setVerticalFadingEdgeEnabled(false);
        setFadingEdgeLength(0);
        if (i == getResources().getDisplayMetrics().widthPixels) {
            this.minimumWidth = -1;
        } else {
            this.minimumWidth = i;
        }
        init();
    }

    public void onConfigurationChanged(Configuration configuration) {
        LinearLayout linearLayout = this.sheetbarFrame;
        int i = this.minimumWidth;
        if (i == -1) {
            i = getResources().getDisplayMetrics().widthPixels;
        }
        linearLayout.setMinimumWidth(i);
    }

    private void init() {
        Context context = getContext();
        LinearLayout linearLayout = new LinearLayout(context);
        this.sheetbarFrame = linearLayout;
        linearLayout.setGravity(80);
        this.sheetbarResManager = new SheetbarResManager(context);
        Drawable drawable = getResources().getDrawable(R.drawable.ss_sheetbar_bg);
        this.sheetbarFrame.setBackground(drawable);
        this.sheetbarFrame.setPadding(0, 10, 0, 0);
        this.sheetbarFrame.setOrientation(0);
        LinearLayout linearLayout2 = this.sheetbarFrame;
        int i = this.minimumWidth;
        if (i == -1) {
            i = getResources().getDisplayMetrics().widthPixels;
        }
        linearLayout2.setMinimumWidth(i);
        this.sheetbarHeight = drawable.getIntrinsicHeight();
        Drawable drawable2 = this.sheetbarResManager.getDrawable((short) R.drawable.ss_sheetbar_shadow_left);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        View view = new View(context);
        view.setBackground(drawable2);
        this.sheetbarFrame.addView(view, layoutParams);
        Vector vector = (Vector) this.control.getActionValue(EventConstant.SS_GET_ALL_SHEET_NAME, (Object) null);
        this.sheetbarResManager.getDrawable(4);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
        int size = vector.size();
        for (int i2 = 0; i2 < size; i2++) {
            SheetButton sheetButton = new SheetButton(context, (String) vector.get(i2), i2, this.sheetbarResManager);
            if (this.currentSheet == null) {
                this.currentSheet = sheetButton;
                sheetButton.changeFocus(true);
            }
            sheetButton.setOnClickListener(this);
            this.sheetbarFrame.addView(sheetButton, layoutParams2);
            if (i2 < size - 1) {
                View view2 = new View(context);
                view2.setBackground(this.sheetbarResManager.getDrawable(3));
                this.sheetbarFrame.addView(view2, layoutParams2);
            }
        }
        View view3 = new View(context);
        view3.setBackgroundDrawable(this.sheetbarResManager.getDrawable(2));
        this.sheetbarFrame.addView(view3, layoutParams);
        addView(this.sheetbarFrame, new FrameLayout.LayoutParams(-2, this.sheetbarHeight));
    }

    public void onClick(View view) {
        this.currentSheet.changeFocus(false);
        SheetButton sheetButton = (SheetButton) view;
        sheetButton.changeFocus(true);
        this.currentSheet = sheetButton;
        this.control.actionEvent(EventConstant.SS_SHOW_SHEET, Integer.valueOf(sheetButton.getSheetIndex()));
    }

    public void setFocusSheetButton(int i) {
        if (this.currentSheet.getSheetIndex() != i) {
            int childCount = this.sheetbarFrame.getChildCount();
            View view = null;
            int i2 = 0;
            while (true) {
                if (i2 >= childCount) {
                    break;
                }
                view = this.sheetbarFrame.getChildAt(i2);
                if (view instanceof SheetButton) {
                    SheetButton sheetButton = (SheetButton) view;
                    if (sheetButton.getSheetIndex() == i) {
                        this.currentSheet.changeFocus(false);
                        this.currentSheet = sheetButton;
                        sheetButton.changeFocus(true);
                        break;
                    }
                }
                i2++;
            }
            int width = this.control.getActivity().getWindowManager().getDefaultDisplay().getWidth();
            int width2 = this.sheetbarFrame.getWidth();
            if (width2 > width) {
                int left = view.getLeft();
                int right = left - ((width - (view.getRight() - left)) / 2);
                if (right < 0) {
                    right = 0;
                } else if (right + width > width2) {
                    right = width2 - width;
                }
                scrollTo(right, 0);
            }
        }
    }

    public int getSheetbarHeight() {
        return this.sheetbarHeight;
    }

    public void dispose() {
        this.sheetbarResManager.dispose();
        this.sheetbarResManager = null;
        this.currentSheet = null;
        LinearLayout linearLayout = this.sheetbarFrame;
        if (linearLayout != null) {
            int childCount = linearLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.sheetbarFrame.getChildAt(i);
                if (childAt instanceof SheetButton) {
                    ((SheetButton) childAt).dispose();
                }
            }
            this.sheetbarFrame = null;
        }
    }
}
