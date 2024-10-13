package com.app.office.ss.sheetbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.office.R;

public class SheetButton extends LinearLayout {
    private static final int FONT_SIZE = 14;
    private static final int SHEET_BUTTON_MIN_WIDTH = 200;
    private boolean active;
    private View left;
    private View right;
    private int sheetIndex;
    private SheetbarResManager sheetbarResManager;
    private TextView textView;

    public SheetButton(Context context, String str, int i, SheetbarResManager sheetbarResManager2) {
        super(context);
        setOrientation(0);
        this.sheetIndex = i;
        this.sheetbarResManager = sheetbarResManager2;
        init(context, str);
    }

    private void init(Context context, String str) {
        View view = new View(context);
        this.left = view;
        view.setBackgroundDrawable(this.sheetbarResManager.getDrawable(4));
        addView(this.left);
        TextView textView2 = new TextView(context);
        this.textView = textView2;
        textView2.setBackgroundDrawable(this.sheetbarResManager.getDrawable(5));
        this.textView.setText(str);
        this.textView.setTextSize(14.0f);
        this.textView.setGravity(17);
        this.textView.setPadding(10, 0, 10, 0);
        this.textView.setBackgroundResource(R.drawable.bg_sheet_unselected);
        this.textView.setTextColor(Color.parseColor("#806368"));
        this.textView.setLines(1);
        addView(this.textView, new LinearLayout.LayoutParams(Math.max((int) this.textView.getPaint().measureText(str), 200), -1));
        View view2 = new View(context);
        this.right = view2;
        view2.setBackgroundDrawable(this.sheetbarResManager.getDrawable(6));
        addView(this.right);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000f, code lost:
        if (r0 != 3) goto L_0x006c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            int r0 = r5.getAction()
            java.lang.String r1 = "#806368"
            if (r0 == 0) goto L_0x003f
            r2 = 1
            if (r0 == r2) goto L_0x0012
            r2 = 2
            if (r0 == r2) goto L_0x003f
            r2 = 3
            if (r0 == r2) goto L_0x0012
            goto L_0x006c
        L_0x0012:
            boolean r0 = r4.active
            if (r0 != 0) goto L_0x006c
            android.view.View r0 = r4.left
            com.app.office.ss.sheetbar.SheetbarResManager r2 = r4.sheetbarResManager
            r3 = 4
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r3)
            r0.setBackgroundDrawable(r2)
            android.widget.TextView r0 = r4.textView
            int r2 = com.app.office.R.drawable.bg_sheet_unselected
            r0.setBackgroundResource(r2)
            android.widget.TextView r0 = r4.textView
            int r1 = android.graphics.Color.parseColor(r1)
            r0.setTextColor(r1)
            android.view.View r0 = r4.right
            com.app.office.ss.sheetbar.SheetbarResManager r1 = r4.sheetbarResManager
            r2 = 6
            android.graphics.drawable.Drawable r1 = r1.getDrawable(r2)
            r0.setBackgroundDrawable(r1)
            goto L_0x006c
        L_0x003f:
            boolean r0 = r4.active
            if (r0 != 0) goto L_0x006c
            android.view.View r0 = r4.left
            com.app.office.ss.sheetbar.SheetbarResManager r2 = r4.sheetbarResManager
            r3 = 7
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r3)
            r0.setBackgroundDrawable(r2)
            android.widget.TextView r0 = r4.textView
            int r2 = com.app.office.R.drawable.bg_sheet_unselected
            r0.setBackgroundResource(r2)
            android.widget.TextView r0 = r4.textView
            int r1 = android.graphics.Color.parseColor(r1)
            r0.setTextColor(r1)
            android.view.View r0 = r4.right
            com.app.office.ss.sheetbar.SheetbarResManager r1 = r4.sheetbarResManager
            r2 = 9
            android.graphics.drawable.Drawable r1 = r1.getDrawable(r2)
            r0.setBackgroundDrawable(r1)
        L_0x006c:
            boolean r5 = super.onTouchEvent(r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.sheetbar.SheetButton.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void changeFocus(boolean z) {
        Drawable drawable;
        int i;
        Drawable drawable2;
        this.active = z;
        View view = this.left;
        if (z) {
            drawable = this.sheetbarResManager.getDrawable(10);
        } else {
            drawable = this.sheetbarResManager.getDrawable(4);
        }
        view.setBackgroundDrawable(drawable);
        TextView textView2 = this.textView;
        if (z) {
            i = R.drawable.bg_sheet_selected;
        } else {
            i = R.drawable.bg_sheet_unselected;
        }
        textView2.setBackgroundResource(i);
        this.textView.setTextColor(Color.parseColor(z ? "#268744" : "#806368"));
        View view2 = this.right;
        if (z) {
            drawable2 = this.sheetbarResManager.getDrawable(12);
        } else {
            drawable2 = this.sheetbarResManager.getDrawable(6);
        }
        view2.setBackgroundDrawable(drawable2);
    }

    public int getSheetIndex() {
        return this.sheetIndex;
    }

    public void dispose() {
        this.sheetbarResManager = null;
        this.left = null;
        this.textView = null;
        this.right = null;
    }
}
