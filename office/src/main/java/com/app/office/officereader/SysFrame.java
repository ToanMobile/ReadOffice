package com.app.office.officereader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.app.office.R;
import com.app.office.constant.MainConstant;
import com.app.office.officereader.beans.AImageButton;
import com.app.office.officereader.beans.AImageTextButton;
import com.app.office.system.IControl;

public class SysFrame extends LinearLayout {
    private static final int FONT_SIZE = 32;
    private static final int GAP = 5;
    /* access modifiers changed from: private */
    public IControl control;
    private View.OnClickListener onClickListener;
    private BitmapFactory.Options opts = new BitmapFactory.Options();

    public SysFrame(Context context, IControl iControl) {
        super(context);
        setOrientation(1);
        setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        setBackgroundResource(R.drawable.sys_background);
        this.control = iControl;
        initListener();
    }

    /* access modifiers changed from: protected */
    public void init() {
        Resources resources = getResources();
        this.opts.inJustDecodeBounds = true;
        if (this.control.getSysKit().isVertical(getContext())) {
            initVertical(resources);
        } else {
            initHorizontal(resources);
        }
    }

    private void initVertical(Resources resources) {
        Activity activity = this.control.getActivity();
        int top = getResources().getDisplayMetrics().heightPixels - this.control.getActivity().getWindow().findViewById(16908290).getTop();
        BitmapFactory.decodeResource(resources, R.drawable.sys_search, this.opts);
        AImageButton aImageButton = new AImageButton(activity, this.control, "", R.drawable.sys_search, -1, 5);
        aImageButton.setPushBgResID(R.drawable.sys_search_bg_push);
        aImageButton.setOnClickListener(this.onClickListener);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.opts.outWidth, this.opts.outHeight);
        layoutParams.gravity = 5;
        addView(aImageButton, layoutParams);
        int i = top - this.opts.outHeight;
        ImageView imageView = new ImageView(activity);
        addView(imageView);
        BitmapFactory.decodeResource(activity.getResources(), R.drawable.sys_button_normal_bg_vertical, this.opts);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(this.opts.outWidth, this.opts.outHeight);
        layoutParams2.gravity = 17;
        layoutParams2.bottomMargin = 30;
        Activity activity2 = activity;
        LinearLayout.LayoutParams layoutParams3 = layoutParams2;
        addView(createButtonVertical(activity2, layoutParams3, R.drawable.sys_recent_vertical, R.string.sys_button_recently_files, 2));
        addView(createButtonVertical(activity2, layoutParams3, R.drawable.sys_mark_star_vertical, R.string.sys_button_mark_files, 3));
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(this.opts.outWidth, this.opts.outHeight);
        layoutParams4.gravity = 17;
        addView(createButtonVertical(activity, layoutParams4, R.drawable.sys_sacard_vertical, R.string.sys_button_local_storage, 4));
        int i2 = (i - (((this.opts.outHeight * 3) + 60) + 150)) - this.opts.outHeight;
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-1, this.opts.outHeight);
        int i3 = i2 / 2;
        layoutParams5.topMargin = i3;
        layoutParams5.gravity = 17;
        layoutParams5.bottomMargin = i3;
        imageView.setLayoutParams(layoutParams5);
    }

    private AImageTextButton createButtonVertical(Context context, LinearLayout.LayoutParams layoutParams, int i, int i2, int i3) {
        AImageTextButton aImageTextButton = new AImageTextButton(context, this.control, context.getString(i2), "", i, -1, i3, 3, 32);
        aImageTextButton.setLeftIndent(30);
        LinearLayout.LayoutParams layoutParams2 = layoutParams;
        aImageTextButton.setLayoutParams(layoutParams);
        aImageTextButton.setNormalBgResID(R.drawable.sys_button_normal_bg_vertical);
        aImageTextButton.setPushBgResID(R.drawable.sys_button_push_bg_vertical);
        aImageTextButton.setFocusBgResID(R.drawable.sys_button_focus_bg_vertical);
        aImageTextButton.setOnClickListener(this.onClickListener);
        return aImageTextButton;
    }

    private void initHorizontal(Resources resources) {
        Activity activity = this.control.getActivity();
        int i = getResources().getDisplayMetrics().heightPixels;
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        BitmapFactory.decodeResource(resources, R.drawable.sys_search, this.opts);
        AImageButton aImageButton = new AImageButton(activity, this.control, "", R.drawable.sys_search, -1, 5);
        aImageButton.setPushBgResID(R.drawable.sys_search_bg_push);
        aImageButton.setOnClickListener(this.onClickListener);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.opts.outWidth, this.opts.outHeight);
        layoutParams.gravity = 53;
        linearLayout.addView(aImageButton, layoutParams);
        ImageView imageView = new ImageView(activity);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(this.opts.outWidth, this.opts.outHeight);
        layoutParams2.gravity = 17;
        linearLayout.addView(imageView, layoutParams2);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, (i - 60) / 2));
        addView(linearLayout);
        BitmapFactory.decodeResource(activity.getResources(), R.drawable.sys_button_normal_bg_horizontal, this.opts);
        LinearLayout linearLayout2 = new LinearLayout(activity);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(this.opts.outWidth, this.opts.outHeight);
        layoutParams3.gravity = 17;
        layoutParams3.rightMargin = 30;
        Activity activity2 = activity;
        LinearLayout.LayoutParams layoutParams4 = layoutParams3;
        linearLayout2.addView(createButtonHorizontal(activity2, layoutParams4, R.drawable.sys_recent_horizontal, R.string.sys_button_recently_files, 2));
        linearLayout2.addView(createButtonHorizontal(activity2, layoutParams4, R.drawable.sys_mark_star_horizontal, R.string.sys_button_mark_files, 3));
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(this.opts.outWidth, this.opts.outHeight);
        layoutParams5.gravity = 17;
        linearLayout2.addView(createButtonHorizontal(activity, layoutParams5, R.drawable.sys_sacard_horizontal, R.string.sys_button_local_storage, 4));
        linearLayout2.setGravity(17);
        addView(linearLayout2);
    }

    private AImageTextButton createButtonHorizontal(Context context, LinearLayout.LayoutParams layoutParams, int i, int i2, int i3) {
        AImageTextButton aImageTextButton = new AImageTextButton(context, this.control, context.getString(i2), "", i, -1, i3, 1, 32);
        aImageTextButton.setTopIndent(30);
        LinearLayout.LayoutParams layoutParams2 = layoutParams;
        aImageTextButton.setLayoutParams(layoutParams);
        aImageTextButton.setNormalBgResID(R.drawable.sys_button_normal_bg_horizontal);
        aImageTextButton.setPushBgResID(R.drawable.sys_button_push_bg_horizontal);
        aImageTextButton.setFocusBgResID(R.drawable.sys_button_focus_bg_horizontal);
        aImageTextButton.setOnClickListener(this.onClickListener);
        return aImageTextButton;
    }

    private void initListener() {
        this.onClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                String str;
                int actionID = ((AImageButton) view).getActionID();
                if (actionID == 2) {
                    str = MainConstant.INTENT_FILED_RECENT_FILES;
                } else if (actionID == 3) {
                    str = MainConstant.INTENT_FILED_MARK_FILES;
                } else if (actionID == 4) {
                    str = MainConstant.INTENT_FILED_SDCARD_FILES;
                } else if (actionID != 5) {
                    str = "";
                } else {
                    SysFrame.this.control.actionEvent(actionID, (Object) null);
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(SysFrame.this.control.getActivity(), FileListActivity.class);
                intent.putExtra(MainConstant.INTENT_FILED_FILE_LIST_TYPE, str);
                SysFrame.this.control.getActivity().startActivity(intent);
            }
        };
    }

    public void dispose() {
        this.onClickListener = null;
        this.opts = null;
        this.control = null;
    }
}
