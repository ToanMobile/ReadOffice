package com.app.office.mychanges.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.snackbar.Snackbar;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.onesignal.OneSignalDbContract;
import com.app.office.R;
import com.app.office.constant.EventConstant;
import com.app.office.databinding.DialogInfoFooter2Binding;
import com.app.office.databinding.DialogInfoHeader2Binding;
import com.app.office.mychanges.bottomsheetfragment.ViewerToolsBottomSheet2;
import com.app.office.mychanges.dialogs.FileSaveDialog2;
import com.app.office.mychanges.interfaces.OnBookmarkCallback;
import com.app.office.mychanges.interfaces.OnDeleteCallback;
import com.app.office.mychanges.interfaces.OnRenameCallback;
import com.app.office.mychanges.model.DataModel2;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnDismissedListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(d1 = {"\u0000\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u001a\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010*2\b\u0010+\u001a\u0004\u0018\u00010*\u001a\u000e\u0010,\u001a\u00020\u00012\u0006\u0010-\u001a\u00020.\u001a\u0018\u0010/\u001a\u00020(2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u0001H\u0007\u001a\u0018\u00103\u001a\u00020(2\u0006\u00104\u001a\u0002052\u0006\u00102\u001a\u00020\u0001H\u0007\u001a \u00106\u001a\u00020(2\u0006\u00107\u001a\u0002082\u0006\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020*H\u0002\u001a \u0010<\u001a\u00020(2\u0006\u00107\u001a\u0002082\u0006\u0010=\u001a\u00020\u00012\u0006\u0010>\u001a\u00020\u0001H\u0002\u001a\u0012\u0010?\u001a\u00020\u0001*\u00020\u00012\u0006\u0010@\u001a\u00020\u0001\u001a\n\u0010A\u001a\u00020\u0001*\u00020B\u001a&\u0010C\u001a\u00020D*\u0002082\u0006\u0010E\u001a\u00020*2\b\b\u0002\u0010F\u001a\u00020G2\b\b\u0002\u0010H\u001a\u00020G\u001a\n\u0010I\u001a\u00020\u0001*\u00020\u0001\u001a\n\u0010J\u001a\u00020(*\u00020K\u001a\n\u0010L\u001a\u00020G*\u00020\u0001\u001a\n\u0010M\u001a\u00020B*\u00020.\u001a\u001a\u0010N\u001a\u00020(*\u00020K2\u0006\u0010O\u001a\u00020P2\u0006\u0010Q\u001a\u00020G\u001a\u0012\u0010R\u001a\u00020(*\u0002082\u0006\u0010S\u001a\u00020\u0001\u001a\u001a\u0010T\u001a\u00020(*\u00020U2\u0006\u00109\u001a\u00020:2\u0006\u0010V\u001a\u00020D\u001aN\u0010W\u001a\u00020(*\u00020U2\u0006\u00109\u001a\u00020:2\u0006\u0010X\u001a\u00020\u00012\u0006\u0010Y\u001a\u00020\u00012\u0006\u0010Z\u001a\u00020\u00012\u0010\b\u0002\u0010[\u001a\n\u0012\u0004\u0012\u00020(\u0018\u00010\\2\u0010\b\u0002\u0010]\u001a\n\u0012\u0004\u0012\u00020(\u0018\u00010\\\u001a\u001a\u0010^\u001a\n _*\u0004\u0018\u00010\u00010\u0001*\u0002082\u0006\u0010`\u001a\u00020.\u001a\u001c\u0010a\u001a\u00020(*\u00020:2\u0006\u0010Y\u001a\u00020\u00012\b\b\u0002\u0010b\u001a\u00020P\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\"\u001c\u0010\u000e\u001a\u0004\u0018\u00010\tX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\r\"\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\"\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0012X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0014\"\u0004\b\u0019\u0010\u0016\"\u000e\u0010\u001a\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u001b\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u001c\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u001d\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#\"\u001c\u0010$\u001a\u0004\u0018\u00010\u001fX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010!\"\u0004\b&\u0010#¨\u0006c"}, d2 = {"ALERT", "", "DOC_MODEL", "MESSAGE", "PDF", "PPT", "PPTX", "RENAME", "bookmarkCallback", "Lcom/reader/office/mychanges/interfaces/OnBookmarkCallback;", "getBookmarkCallback", "()Lcom/reader/office/mychanges/interfaces/OnBookmarkCallback;", "setBookmarkCallback", "(Lcom/reader/office/mychanges/interfaces/OnBookmarkCallback;)V", "bookmarkCompleteCallback", "getBookmarkCompleteCallback", "setBookmarkCompleteCallback", "deleteCallback", "Lcom/reader/office/mychanges/interfaces/OnDeleteCallback;", "getDeleteCallback", "()Lcom/reader/office/mychanges/interfaces/OnDeleteCallback;", "setDeleteCallback", "(Lcom/reader/office/mychanges/interfaces/OnDeleteCallback;)V", "deleteCompleteCallback", "getDeleteCompleteCallback", "setDeleteCompleteCallback", "docExtension", "docxExtension", "excelExtension", "excelWorkbookExtension", "renameCallback", "Lcom/reader/office/mychanges/interfaces/OnRenameCallback;", "getRenameCallback", "()Lcom/reader/office/mychanges/interfaces/OnRenameCallback;", "setRenameCallback", "(Lcom/reader/office/mychanges/interfaces/OnRenameCallback;)V", "renameCompleteCallback", "getRenameCompleteCallback", "setRenameCompleteCallback", "copy", "", "src", "Ljava/io/File;", "dst", "getFormattedTime", "lastModifiedTime", "", "setListItemCardViewBackground", "cardView", "Landroidx/cardview/widget/CardView;", "name", "setListItemImageSrc", "imageView", "Landroid/widget/ImageView;", "showDeleteDialog", "context", "Landroid/content/Context;", "view", "Landroid/view/View;", "currentFile", "showRenameDialog", "fileName", "filePath", "changeExtension", "newExtension", "formatToDMY", "Ljava/util/Date;", "getDataModelFromFile", "Lcom/reader/office/mychanges/model/DataModel2;", "file", "isPdfLocked", "", "isBookmark", "getFileNameExtension", "hideStatusBar", "Landroid/app/Activity;", "isValidFileName", "millisToDate", "setWindowFlag", "bits", "", "on", "shareDocument", "path", "showBottomSheet", "Landroidx/appcompat/app/AppCompatActivity;", "docModel", "showInfoDialog", "title", "message", "type", "onDismissResult", "Lkotlin/Function0;", "onResult", "sizeFormatter", "kotlin.jvm.PlatformType", "size", "snack", "duration", "office_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: ExtensionFun.kt */
public final class ExtensionFunKt {
    public static final String ALERT = "Alert";
    public static final String DOC_MODEL = "file";
    public static final String MESSAGE = "Message";
    public static final String PDF = ".pdf";
    public static final String PPT = ".ppt";
    public static final String PPTX = ".pptx";
    public static final String RENAME = "RENAME";
    private static OnBookmarkCallback bookmarkCallback = null;
    private static OnBookmarkCallback bookmarkCompleteCallback = null;
    private static OnDeleteCallback deleteCallback = null;
    private static OnDeleteCallback deleteCompleteCallback = null;
    public static final String docExtension = ".doc";
    public static final String docxExtension = ".docx";
    public static final String excelExtension = ".xls";
    public static final String excelWorkbookExtension = ".xlsx";
    private static OnRenameCallback renameCallback;
    private static OnRenameCallback renameCompleteCallback;

    public static final void hideStatusBar(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "<this>");
        setWindowFlag(activity, PagedChannelRandomAccessSource.DEFAULT_TOTAL_BUFSIZE, true);
        if (Build.VERSION.SDK_INT >= 22) {
            setWindowFlag(activity, PagedChannelRandomAccessSource.DEFAULT_TOTAL_BUFSIZE, false);
            activity.getWindow().setStatusBarColor(0);
        }
    }

    public static final void setWindowFlag(Activity activity, int i, boolean z) {
        Intrinsics.checkNotNullParameter(activity, "<this>");
        Window window = activity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (z) {
            attributes.flags = i | attributes.flags;
        } else {
            attributes.flags = (~i) & attributes.flags;
        }
        window.setAttributes(attributes);
    }

    public static final void showBottomSheet(AppCompatActivity appCompatActivity, View view, DataModel2 dataModel2) {
        Intrinsics.checkNotNullParameter(appCompatActivity, "<this>");
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(dataModel2, "docModel");
        FragmentManager supportFragmentManager = appCompatActivity.getSupportFragmentManager();
        ViewerToolsBottomSheet2.Companion companion = ViewerToolsBottomSheet2.Companion;
        Bundle bundle = new Bundle();
        bundle.putSerializable("file", dataModel2);
        ViewerToolsBottomSheet2 newInstance = companion.newInstance(bundle);
        newInstance.show(supportFragmentManager, newInstance.getTag());
        newInstance.setMListener(new ExtensionFunKt$showBottomSheet$1$2$1(appCompatActivity, dataModel2, view));
    }

    public static final void shareDocument(Context context, String str) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(str, "path");
        try {
            File file = new File(str);
            if (file.exists()) {
                Uri uriForFile = FileProvider.getUriForFile(context.getApplicationContext(), Intrinsics.stringPlus(context.getApplicationContext().getPackageName(), ".provider"), file);
                Intent intent = new Intent("android.intent.action.SEND");
                intent.addFlags(1);
                intent.setType("*/*");
                intent.putExtra("android.intent.extra.STREAM", uriForFile);
                intent.setFlags(EventConstant.FILE_CREATE_FOLDER_ID);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error", 0).show();
        }
    }

    public static /* synthetic */ DataModel2 getDataModelFromFile$default(Context context, File file, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        return getDataModelFromFile(context, file, z, z2);
    }

    public static final DataModel2 getDataModelFromFile(Context context, File file, boolean z, boolean z2) {
        Context context2 = context;
        Intrinsics.checkNotNullParameter(context2, "<this>");
        Intrinsics.checkNotNullParameter(file, "file");
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(file.getPath());
        if (fileExtensionFromUrl == null) {
            String path = file.getPath();
            Intrinsics.checkNotNullExpressionValue(path, "file.path");
            String path2 = file.getPath();
            Intrinsics.checkNotNullExpressionValue(path2, "file.path");
            fileExtensionFromUrl = path.substring(StringsKt.lastIndexOf$default((CharSequence) path2, ".", 0, false, 6, (Object) null));
            Intrinsics.checkNotNullExpressionValue(fileExtensionFromUrl, "this as java.lang.String).substring(startIndex)");
        }
        String valueOf = String.valueOf(fileExtensionFromUrl);
        if (valueOf.length() == 0) {
            String path3 = file.getPath();
            Intrinsics.checkNotNullExpressionValue(path3, "file.path");
            String path4 = file.getPath();
            Intrinsics.checkNotNullExpressionValue(path4, "file.path");
            String substring = path3.substring(StringsKt.lastIndexOf$default((CharSequence) path4, ".", 0, false, 6, (Object) null));
            Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
            valueOf = (String) StringsKt.split$default((CharSequence) substring, new String[]{"."}, false, 0, 6, (Object) null).get(1);
        }
        String str = valueOf;
        String name = file.getName();
        String sizeFormatter = sizeFormatter(context2, file.length());
        String path5 = file.getPath();
        String formattedTime = getFormattedTime(file.lastModified());
        long length = file.length();
        long lastModified = file.lastModified();
        Intrinsics.checkNotNullExpressionValue(name, "name");
        Intrinsics.checkNotNullExpressionValue(sizeFormatter, "sizeFormatter(file.length())");
        Intrinsics.checkNotNullExpressionValue(path5, "path");
        return new DataModel2(name, sizeFormatter, str, path5, formattedTime, length, lastModified, false, z2, false, z, 0, 2688, (DefaultConstructorMarker) null);
    }

    public static final String getFormattedTime(long j) {
        return formatToDMY(millisToDate(j));
    }

    public static final String formatToDMY(Date date) {
        Intrinsics.checkNotNullParameter(date, "<this>");
        String format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(date);
        Intrinsics.checkNotNullExpressionValue(format, "df.format(this)");
        return format;
    }

    public static final Date millisToDate(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        Date time = instance.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "calendar.time");
        return time;
    }

    public static final String sizeFormatter(Context context, long j) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return Formatter.formatShortFileSize(context, j);
    }

    @BindingAdapter({"setListItemimageSrc"})
    public static final void setListItemImageSrc(ImageView imageView, String str) {
        Intrinsics.checkNotNullParameter(imageView, "imageView");
        Intrinsics.checkNotNullParameter(str, "name");
        CharSequence charSequence = str;
        if (StringsKt.contains$default(charSequence, (CharSequence) ".pdf", false, 2, (Object) null) || StringsKt.contains$default(charSequence, (CharSequence) ".PDF", false, 2, (Object) null)) {
            imageView.setImageResource(R.drawable.pdf_ic);
        } else if (StringsKt.contains$default(charSequence, (CharSequence) ".xls", false, 2, (Object) null) || StringsKt.contains$default(charSequence, (CharSequence) ".xlsx", false, 2, (Object) null) || StringsKt.contains$default(charSequence, (CharSequence) ".excel", false, 2, (Object) null)) {
            imageView.setImageResource(R.drawable.excel_ic);
        } else if (StringsKt.contains$default(charSequence, (CharSequence) ".ppt", false, 2, (Object) null) || StringsKt.contains$default(charSequence, (CharSequence) ".pptx", false, 2, (Object) null) || StringsKt.contains$default(charSequence, (CharSequence) ".powerpoint", false, 2, (Object) null)) {
            imageView.setImageResource(R.drawable.ppt_ic);
        } else if (StringsKt.contains$default(charSequence, (CharSequence) ".doc", false, 2, (Object) null) || StringsKt.contains$default(charSequence, (CharSequence) ".docx", false, 2, (Object) null)) {
            imageView.setImageResource(R.drawable.word_ic);
        } else {
            imageView.setImageResource(R.drawable.all_doc_ic);
        }
    }

    @BindingAdapter({"setListItemCardViewBackground"})
    public static final void setListItemCardViewBackground(CardView cardView, String str) {
        Intrinsics.checkNotNullParameter(cardView, "cardView");
        Intrinsics.checkNotNullParameter(str, "name");
        CharSequence charSequence = str;
        if (StringsKt.contains$default(charSequence, (CharSequence) ".pdf", false, 2, (Object) null) || StringsKt.contains$default(charSequence, (CharSequence) ".PDF", false, 2, (Object) null)) {
            cardView.setCardBackgroundColor(ResourcesCompat.getColor(cardView.getResources(), R.color.listItemColorPdf, cardView.getContext().getTheme()));
        } else if (StringsKt.contains$default(charSequence, (CharSequence) ".xls", false, 2, (Object) null) || StringsKt.contains$default(charSequence, (CharSequence) ".xlsx", false, 2, (Object) null) || StringsKt.contains$default(charSequence, (CharSequence) ".excel", false, 2, (Object) null)) {
            cardView.setCardBackgroundColor(ResourcesCompat.getColor(cardView.getResources(), R.color.listItemColorExcel, cardView.getContext().getTheme()));
        } else if (StringsKt.contains$default(charSequence, (CharSequence) ".ppt", false, 2, (Object) null) || StringsKt.contains$default(charSequence, (CharSequence) ".pptx", false, 2, (Object) null) || StringsKt.contains$default(charSequence, (CharSequence) ".powerpoint", false, 2, (Object) null)) {
            cardView.setCardBackgroundColor(ResourcesCompat.getColor(cardView.getResources(), R.color.listItemColorPPT, cardView.getContext().getTheme()));
        } else if (StringsKt.contains$default(charSequence, (CharSequence) ".doc", false, 2, (Object) null) || StringsKt.contains$default(charSequence, (CharSequence) ".docx", false, 2, (Object) null)) {
            cardView.setCardBackgroundColor(ResourcesCompat.getColor(cardView.getResources(), R.color.listItemColorDoc, cardView.getContext().getTheme()));
        } else {
            cardView.setCardBackgroundColor(ResourcesCompat.getColor(cardView.getResources(), R.color.listItemColorAny, cardView.getContext().getTheme()));
        }
    }

    public static /* synthetic */ void snack$default(View view, String str, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = -1;
        }
        snack(view, str, i);
    }

    public static final void snack(View view, String str, int i) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(str, OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE);
        Snackbar make = Snackbar.make(view, (CharSequence) str, i);
        Intrinsics.checkNotNullExpressionValue(make, "make(this, message, duration)");
        make.setTextColor(ResourcesCompat.getColor(view.getContext().getResources(), R.color.white, view.getContext().getTheme()));
        make.show();
    }

    public static final boolean isValidFileName(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Locale locale = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(locale, "ROOT");
        String lowerCase = str.toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(locale)");
        if (!StringsKt.contains$default((CharSequence) lowerCase, (CharSequence) ".pdf", false, 2, (Object) null)) {
            Locale locale2 = Locale.ROOT;
            Intrinsics.checkNotNullExpressionValue(locale2, "ROOT");
            String lowerCase2 = str.toLowerCase(locale2);
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase(locale)");
            if (!StringsKt.contains$default((CharSequence) lowerCase2, (CharSequence) ".ppt", false, 2, (Object) null)) {
                Locale locale3 = Locale.ROOT;
                Intrinsics.checkNotNullExpressionValue(locale3, "ROOT");
                String lowerCase3 = str.toLowerCase(locale3);
                Intrinsics.checkNotNullExpressionValue(lowerCase3, "this as java.lang.String).toLowerCase(locale)");
                if (!StringsKt.contains$default((CharSequence) lowerCase3, (CharSequence) ".pptx", false, 2, (Object) null)) {
                    Locale locale4 = Locale.ROOT;
                    Intrinsics.checkNotNullExpressionValue(locale4, "ROOT");
                    String lowerCase4 = str.toLowerCase(locale4);
                    Intrinsics.checkNotNullExpressionValue(lowerCase4, "this as java.lang.String).toLowerCase(locale)");
                    if (!StringsKt.contains$default((CharSequence) lowerCase4, (CharSequence) ".doc", false, 2, (Object) null)) {
                        Locale locale5 = Locale.ROOT;
                        Intrinsics.checkNotNullExpressionValue(locale5, "ROOT");
                        String lowerCase5 = str.toLowerCase(locale5);
                        Intrinsics.checkNotNullExpressionValue(lowerCase5, "this as java.lang.String).toLowerCase(locale)");
                        if (!StringsKt.contains$default((CharSequence) lowerCase5, (CharSequence) ".docx", false, 2, (Object) null)) {
                            Locale locale6 = Locale.ROOT;
                            Intrinsics.checkNotNullExpressionValue(locale6, "ROOT");
                            String lowerCase6 = str.toLowerCase(locale6);
                            Intrinsics.checkNotNullExpressionValue(lowerCase6, "this as java.lang.String).toLowerCase(locale)");
                            if (!StringsKt.contains$default((CharSequence) lowerCase6, (CharSequence) ".xls", false, 2, (Object) null)) {
                                Locale locale7 = Locale.ROOT;
                                Intrinsics.checkNotNullExpressionValue(locale7, "ROOT");
                                String lowerCase7 = str.toLowerCase(locale7);
                                Intrinsics.checkNotNullExpressionValue(lowerCase7, "this as java.lang.String).toLowerCase(locale)");
                                if (!StringsKt.contains$default((CharSequence) lowerCase7, (CharSequence) ".xlsx", false, 2, (Object) null)) {
                                    Locale locale8 = Locale.ROOT;
                                    Intrinsics.checkNotNullExpressionValue(locale8, "ROOT");
                                    String lowerCase8 = str.toLowerCase(locale8);
                                    Intrinsics.checkNotNullExpressionValue(lowerCase8, "this as java.lang.String).toLowerCase(locale)");
                                    if (!StringsKt.contains$default((CharSequence) lowerCase8, (CharSequence) ".xlsx", false, 2, (Object) null)) {
                                        Locale locale9 = Locale.ROOT;
                                        Intrinsics.checkNotNullExpressionValue(locale9, "ROOT");
                                        String lowerCase9 = str.toLowerCase(locale9);
                                        Intrinsics.checkNotNullExpressionValue(lowerCase9, "this as java.lang.String).toLowerCase(locale)");
                                        if (StringsKt.contains$default((CharSequence) lowerCase9, (CharSequence) ".pdf", false, 2, (Object) null)) {
                                            return true;
                                        }
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public static final String getFileNameExtension(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        String substring = str.substring(StringsKt.lastIndexOf$default((CharSequence) str, ".", 0, false, 6, (Object) null));
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
        return substring;
    }

    public static final String changeExtension(String str, String str2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(str2, "newExtension");
        CharSequence charSequence = str;
        if (!StringsKt.contains$default(charSequence, (CharSequence) ".", false, 2, (Object) null)) {
            return Intrinsics.stringPlus(str, str2);
        }
        String substring = str.substring(0, StringsKt.lastIndexOf$default(charSequence, '.', 0, false, 6, (Object) null));
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return Intrinsics.stringPlus(substring, str2);
    }

    /* access modifiers changed from: private */
    public static final void showRenameDialog(Context context, String str, String str2) {
        new FileSaveDialog2(context, str2, str, "RENAME", new ExtensionFunKt$showRenameDialog$dialog$1(str2)).show();
    }

    /* access modifiers changed from: private */
    public static final void showDeleteDialog(Context context, View view, File file) {
        showInfoDialog$default((AppCompatActivity) context, view, "Delete File", "Are you sure you want to delete?", "Alert", (Function0) null, new ExtensionFunKt$showDeleteDialog$1(file), 16, (Object) null);
    }

    public static /* synthetic */ void showInfoDialog$default(AppCompatActivity appCompatActivity, View view, String str, String str2, String str3, Function0 function0, Function0 function02, int i, Object obj) {
        showInfoDialog(appCompatActivity, view, str, str2, str3, (i & 16) != 0 ? null : function0, (i & 32) != 0 ? null : function02);
    }

    public static final void showInfoDialog(AppCompatActivity appCompatActivity, View view, String str, String str2, String str3, Function0<Unit> function0, Function0<Unit> function02) {
        Intrinsics.checkNotNullParameter(appCompatActivity, "<this>");
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(str, "title");
        Intrinsics.checkNotNullParameter(str2, OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE);
        Intrinsics.checkNotNullParameter(str3, DublinCoreProperties.TYPE);
        Object systemService = appCompatActivity.getSystemService("layout_inflater");
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.view.LayoutInflater");
        DialogInfoHeader2Binding inflate = DialogInfoHeader2Binding.inflate((LayoutInflater) systemService);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(getSystemService…RVICE) as LayoutInflater)");
        Object systemService2 = appCompatActivity.getSystemService("layout_inflater");
        Objects.requireNonNull(systemService2, "null cannot be cast to non-null type android.view.LayoutInflater");
        DialogInfoFooter2Binding inflate2 = DialogInfoFooter2Binding.inflate((LayoutInflater) systemService2);
        Intrinsics.checkNotNullExpressionValue(inflate2, "inflate(getSystemService…RVICE) as LayoutInflater)");
        inflate.title.setText(str);
        if (Intrinsics.areEqual((Object) str3, (Object) "Message")) {
            inflate2.cancelBtn.setVisibility(8);
            inflate2.processBtn.setText("Ok");
        }
        PowerMenu build = new PowerMenu.Builder(appCompatActivity).setHeaderView(inflate.getRoot()).setFooterView(inflate2.getRoot()).addItem(new PowerMenuItem((CharSequence) str2, false)).setLifecycleOwner(appCompatActivity).setAnimation(MenuAnimation.SHOW_UP_CENTER).setMenuRadius(10.0f).setMenuShadow(10.0f).setWidth(600).setSelectedEffect(false).setOnDismissListener(new OnDismissedListener() {
            public final void onDismissed() {
                ExtensionFunKt.m209showInfoDialog$lambda3(Function0.this);
            }
        }).build();
        Intrinsics.checkNotNullExpressionValue(build, "Builder(this)\n        .s…       }\n        .build()");
        inflate2.processBtn.setOnClickListener(new View.OnClickListener(function02) {
            public final /* synthetic */ Function0 f$1;

            {
                this.f$1 = r2;
            }

            public final void onClick(View view) {
                ExtensionFunKt.m210showInfoDialog$lambda4(PowerMenu.this, this.f$1, view);
            }
        });
        inflate2.cancelBtn.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                ExtensionFunKt.m211showInfoDialog$lambda5(PowerMenu.this, view);
            }
        });
        build.showAtCenter(view);
    }

    /* access modifiers changed from: private */
    /* renamed from: showInfoDialog$lambda-3  reason: not valid java name */
    public static final void m209showInfoDialog$lambda3(Function0 function0) {
        if (function0 != null) {
            function0.invoke();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: showInfoDialog$lambda-4  reason: not valid java name */
    public static final void m210showInfoDialog$lambda4(PowerMenu powerMenu, Function0 function0, View view) {
        Intrinsics.checkNotNullParameter(powerMenu, "$powerMenu");
        if (powerMenu.isShowing()) {
            powerMenu.dismiss();
        }
        if (function0 != null) {
            function0.invoke();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: showInfoDialog$lambda-5  reason: not valid java name */
    public static final void m211showInfoDialog$lambda5(PowerMenu powerMenu, View view) {
        Intrinsics.checkNotNullParameter(powerMenu, "$powerMenu");
        if (powerMenu.isShowing()) {
            powerMenu.dismiss();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0035, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r1, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0039, code lost:
        throw r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003c, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x003d, code lost:
        kotlin.io.CloseableKt.closeFinally(r0, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0040, code lost:
        throw r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void copy(java.io.File r5, java.io.File r6) throws java.io.IOException {
        /*
            java.io.FileInputStream r0 = new java.io.FileInputStream
            r0.<init>(r5)
            java.io.InputStream r0 = (java.io.InputStream) r0
            java.io.Closeable r0 = (java.io.Closeable) r0
            r5 = r0
            java.io.InputStream r5 = (java.io.InputStream) r5     // Catch:{ all -> 0x003a }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x003a }
            r1.<init>(r6)     // Catch:{ all -> 0x003a }
            java.io.OutputStream r1 = (java.io.OutputStream) r1     // Catch:{ all -> 0x003a }
            java.io.Closeable r1 = (java.io.Closeable) r1     // Catch:{ all -> 0x003a }
            r6 = r1
            java.io.OutputStream r6 = (java.io.OutputStream) r6     // Catch:{ all -> 0x0033 }
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]     // Catch:{ all -> 0x0033 }
        L_0x001c:
            int r3 = r5.read(r2)     // Catch:{ all -> 0x0033 }
            if (r3 <= 0) goto L_0x0027
            r4 = 0
            r6.write(r2, r4, r3)     // Catch:{ all -> 0x0033 }
            goto L_0x001c
        L_0x0027:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0033 }
            r5 = 0
            kotlin.io.CloseableKt.closeFinally(r1, r5)     // Catch:{ all -> 0x003a }
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x003a }
            kotlin.io.CloseableKt.closeFinally(r0, r5)
            return
        L_0x0033:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x0035 }
        L_0x0035:
            r6 = move-exception
            kotlin.io.CloseableKt.closeFinally(r1, r5)     // Catch:{ all -> 0x003a }
            throw r6     // Catch:{ all -> 0x003a }
        L_0x003a:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x003c }
        L_0x003c:
            r6 = move-exception
            kotlin.io.CloseableKt.closeFinally(r0, r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.mychanges.utils.ExtensionFunKt.copy(java.io.File, java.io.File):void");
    }

    public static final OnRenameCallback getRenameCallback() {
        return renameCallback;
    }

    public static final void setRenameCallback(OnRenameCallback onRenameCallback) {
        renameCallback = onRenameCallback;
    }

    public static final OnRenameCallback getRenameCompleteCallback() {
        return renameCompleteCallback;
    }

    public static final void setRenameCompleteCallback(OnRenameCallback onRenameCallback) {
        renameCompleteCallback = onRenameCallback;
    }

    public static final OnDeleteCallback getDeleteCallback() {
        return deleteCallback;
    }

    public static final void setDeleteCallback(OnDeleteCallback onDeleteCallback) {
        deleteCallback = onDeleteCallback;
    }

    public static final OnDeleteCallback getDeleteCompleteCallback() {
        return deleteCompleteCallback;
    }

    public static final void setDeleteCompleteCallback(OnDeleteCallback onDeleteCallback) {
        deleteCompleteCallback = onDeleteCallback;
    }

    public static final OnBookmarkCallback getBookmarkCallback() {
        return bookmarkCallback;
    }

    public static final void setBookmarkCallback(OnBookmarkCallback onBookmarkCallback) {
        bookmarkCallback = onBookmarkCallback;
    }

    public static final OnBookmarkCallback getBookmarkCompleteCallback() {
        return bookmarkCompleteCallback;
    }

    public static final void setBookmarkCompleteCallback(OnBookmarkCallback onBookmarkCallback) {
        bookmarkCompleteCallback = onBookmarkCallback;
    }
}
