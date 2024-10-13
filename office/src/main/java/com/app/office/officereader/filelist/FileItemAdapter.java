package com.app.office.officereader.filelist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.app.office.R;
import com.app.office.constant.MainConstant;
import com.app.office.system.IControl;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class FileItemAdapter extends BaseAdapter {
    private static final int GB = 1073741824;
    public static final int ICON_TYPE_DOC = 1;
    public static final int ICON_TYPE_DOCX = 2;
    public static final int ICON_TYPE_FOLDER = 0;
    public static final int ICON_TYPE_PDF = 9;
    public static final int ICON_TYPE_PPT = 5;
    public static final int ICON_TYPE_PPTX = 6;
    public static final int ICON_TYPE_STAR = 8;
    public static final int ICON_TYPE_TXT = 7;
    public static final int ICON_TYPE_XLSX = 4;
    public static final int ICON_TYPE_XSL = 3;
    private static final int KB = 1024;
    private static final int MB = 1048576;
    private static final Calendar calendar = Calendar.getInstance();
    private static final DecimalFormat df = new DecimalFormat("#0.00");
    private static final SimpleDateFormat sdf_12 = new SimpleDateFormat("yyyy-MM-dd a hh:mm");
    private static final SimpleDateFormat sdf_24 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    private IControl control;
    private Map<Integer, Drawable> iconMap;
    private boolean is24Hour;
    private List<FileItem> mItems;

    public boolean areAllItemsSelectable() {
        return false;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public FileItemAdapter(Context context, IControl iControl) {
        this.control = iControl;
        this.is24Hour = "24".equals(Settings.System.getString(context.getContentResolver(), "time_12_24"));
        Resources resources = context.getResources();
        Hashtable hashtable = new Hashtable();
        this.iconMap = hashtable;
        hashtable.put(0, resources.getDrawable(R.drawable.file_folder));
        this.iconMap.put(1, resources.getDrawable(R.drawable.file_doc));
        this.iconMap.put(2, resources.getDrawable(R.drawable.file_docx));
        this.iconMap.put(3, resources.getDrawable(R.drawable.file_xls));
        this.iconMap.put(4, resources.getDrawable(R.drawable.file_xlsx));
        this.iconMap.put(5, resources.getDrawable(R.drawable.file_ppt));
        this.iconMap.put(6, resources.getDrawable(R.drawable.file_pptx));
        this.iconMap.put(7, resources.getDrawable(R.drawable.file_txt));
        this.iconMap.put(8, resources.getDrawable(R.drawable.file_icon_star));
        this.iconMap.put(9, resources.getDrawable(R.drawable.file_pdf));
    }

    public void addItem(FileItem fileItem) {
        this.mItems.add(fileItem);
    }

    public void setListItems(List<FileItem> list) {
        this.mItems = list;
    }

    public int getCount() {
        return this.mItems.size();
    }

    public Object getItem(int i) {
        return this.mItems.get(i);
    }

    public boolean isSelectable(int i) {
        return this.mItems.get(i).isCheck();
    }

    public boolean isEmpty() {
        return this.mItems.size() == 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        FileItem fileItem = this.mItems.get(i);
        if (fileItem == null) {
            return null;
        }
        if (view == null || view.getWidth() != viewGroup.getContext().getResources().getDisplayMetrics().widthPixels) {
            return new FileItemView(this.control.getActivity().getApplicationContext(), this.control, this, fileItem);
        }
        ((FileItemView) view).updateFileItem(fileItem, this);
        return view;
    }

    public String formatDate(long j) {
        Calendar calendar2 = calendar;
        calendar2.setTimeInMillis(j);
        return (this.is24Hour ? sdf_24 : sdf_12).format(calendar2.getTime());
    }

    public String formatSize(long j) {
        if (j == 0) {
            return "0B";
        }
        if (j >= 1073741824) {
            return "" + df.format((double) (((float) j) / 1.07374182E9f)) + "GB";
        } else if (j >= PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
            return "" + df.format((double) (((float) j) / 1048576.0f)) + "MB";
        } else if (j >= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            return "" + df.format((double) (((float) j) / 1024.0f)) + "KB";
        } else {
            return "" + j + " B";
        }
    }

    public Drawable getIconDrawable(int i) {
        return this.iconMap.get(Integer.valueOf(i));
    }

    public int getFileIconType(String str) {
        String lowerCase = str.toLowerCase();
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_DOC) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOT)) {
            return 1;
        }
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_DOCX) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_DOTM)) {
            return 2;
        }
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_XLS) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLT)) {
            return 3;
        }
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_XLSX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_XLSM)) {
            return 4;
        }
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_PPT) || lowerCase.endsWith(MainConstant.FILE_TYPE_POT)) {
            return 5;
        }
        if (lowerCase.endsWith(MainConstant.FILE_TYPE_PPTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_PPTM) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTX) || lowerCase.endsWith(MainConstant.FILE_TYPE_POTM)) {
            return 6;
        }
        if (lowerCase.endsWith("pdf")) {
            return 9;
        }
        return lowerCase.endsWith(MainConstant.FILE_TYPE_TXT) ? 7 : -1;
    }

    public void dispose() {
        this.control = null;
        List<FileItem> list = this.mItems;
        if (list != null) {
            for (FileItem dispose : list) {
                dispose.dispose();
            }
            this.mItems.clear();
            this.mItems = null;
        }
        Map<Integer, Drawable> map = this.iconMap;
        if (map != null) {
            map.clear();
            this.iconMap = null;
        }
    }
}
