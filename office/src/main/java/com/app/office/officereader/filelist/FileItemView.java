package com.app.office.officereader.filelist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.app.office.R;
import com.app.office.common.shape.ShapeTypes;
import com.app.office.system.IControl;

public class FileItemView extends LinearLayout {
    private static final int GAP = 5;
    public static final int PUSH_COLOR = Color.rgb(ShapeTypes.ActionButtonEnd, 255, 100);
    private FileCheckBox checkBox;
    private TextView fileCreateDate;
    private TextView fileName;
    private TextView fileSize;
    private ImageView fileStar;
    private ImageView icon;
    private int mWidth = getResources().getDisplayMetrics().widthPixels;

    public FileItemView(Context context, IControl iControl, FileItemAdapter fileItemAdapter, FileItem fileItem) {
        super(context);
        setOrientation(0);
        init(iControl, fileItem, fileItemAdapter);
    }

    private void init(IControl iControl, FileItem fileItem, FileItemAdapter fileItemAdapter) {
        FileItemAdapter fileItemAdapter2 = fileItemAdapter;
        Resources resources = getResources();
        Context context = getContext();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, R.drawable.file_doc, options);
        int i = options.outWidth + 10;
        int i2 = options.outHeight + 20;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i, i2);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        layoutParams.topMargin = 5;
        layoutParams.bottomMargin = 5;
        layoutParams.gravity = 17;
        ImageView imageView = new ImageView(context);
        this.icon = imageView;
        imageView.setImageDrawable(fileItemAdapter2.getIconDrawable(fileItem.getIconType()));
        addView(this.icon, layoutParams);
        int i3 = (this.mWidth - (i * 2)) - 30;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        layoutParams.gravity = 16;
        addView(linearLayout, new LinearLayout.LayoutParams(i3, i2));
        BitmapFactory.decodeResource(resources, R.drawable.file_star, options);
        RelativeLayout relativeLayout = new RelativeLayout(context);
        int i4 = i2 / 2;
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i3, i4);
        layoutParams2.topMargin = 15;
        layoutParams2.gravity = 16;
        linearLayout.addView(relativeLayout, layoutParams2);
        TextView textView = new TextView(context);
        this.fileName = textView;
        textView.setSingleLine(true);
        this.fileName.setEllipsize(TextUtils.TruncateAt.END);
        this.fileName.setText(fileItem.getFileName());
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams((i3 - options.outWidth) - 10, -2);
        layoutParams3.gravity = 3;
        relativeLayout.addView(this.fileName, layoutParams3);
        this.fileStar = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(options.outWidth, options.outHeight);
        layoutParams4.addRule(11, -1);
        if (fileItem.getFileStar() > 0) {
            this.fileStar.setImageDrawable(fileItemAdapter2.getIconDrawable(8));
        }
        relativeLayout.addView(this.fileStar, layoutParams4);
        RelativeLayout relativeLayout2 = new RelativeLayout(context);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(i3, i4);
        layoutParams5.gravity = 16;
        linearLayout.addView(relativeLayout2, layoutParams5);
        TextView textView2 = new TextView(context);
        this.fileCreateDate = textView2;
        textView2.setSingleLine(true);
        this.fileCreateDate.setEllipsize(TextUtils.TruncateAt.END);
        this.fileCreateDate.setText(fileItemAdapter2.formatDate(fileItem.getFile().lastModified()));
        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams6.gravity = 3;
        relativeLayout2.addView(this.fileCreateDate, layoutParams6);
        this.fileSize = new TextView(context);
        RelativeLayout.LayoutParams layoutParams7 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams7.addRule(11, -1);
        this.fileSize.setText(fileItem.getFile().isDirectory() ? "" : fileItemAdapter2.formatSize(fileItem.getFile().length()));
        relativeLayout2.addView(this.fileSize, layoutParams7);
        if (fileItem.isShowCheckView()) {
            this.checkBox = new FileCheckBox(context, iControl, fileItem);
            LinearLayout.LayoutParams layoutParams8 = new LinearLayout.LayoutParams(i + 30, i2 + 10);
            layoutParams8.gravity = 16;
            layoutParams8.leftMargin = 5;
            layoutParams8.rightMargin = 5;
            addView(this.checkBox, layoutParams8);
        }
    }

    public void updateFileItem(FileItem fileItem, FileItemAdapter fileItemAdapter) {
        this.icon.setImageDrawable(fileItemAdapter.getIconDrawable(fileItem.getIconType()));
        this.fileName.setText(fileItem.getFileName());
        if (fileItem.getFileStar() > 0) {
            this.fileStar.setImageDrawable(fileItemAdapter.getIconDrawable(8));
        } else {
            this.fileStar.setImageDrawable((Drawable) null);
        }
        this.fileCreateDate.setText(fileItemAdapter.formatDate(fileItem.getFile().lastModified()));
        this.fileSize.setText(fileItem.getFile().isDirectory() ? "" : fileItemAdapter.formatSize(fileItem.getFile().length()));
        this.checkBox.setFileItem(fileItem);
    }

    public void setSelected(boolean z) {
        this.checkBox.setChecked(z);
    }

    public void dispose() {
        this.icon = null;
        this.fileName = null;
        this.fileStar = null;
        this.fileCreateDate = null;
        this.fileSize = null;
        FileCheckBox fileCheckBox = this.checkBox;
        if (fileCheckBox != null) {
            fileCheckBox.dispose();
            this.checkBox = null;
        }
    }
}
