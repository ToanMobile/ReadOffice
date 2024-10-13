package com.app.office;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0012\u0010\u0007\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\b\u001a\u00020\t*\u00020\n¨\u0006\u000b"}, d2 = {"copyUriToExternalFilesDir", "", "Landroid/app/Activity;", "uri", "Landroid/net/Uri;", "fileName", "", "getFileNameByUri", "isNetworkConnected", "", "Landroid/content/Context;", "office_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: ExtenFunc.kt */
public final class ExtenFuncKt {
    public static final void copyUriToExternalFilesDir(Activity activity, Uri uri, String str) {
        Intrinsics.checkNotNullParameter(activity, "<this>");
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(str, "fileName");
        try {
            InputStream openInputStream = activity.getContentResolver().openInputStream(uri);
            String valueOf = String.valueOf(activity.getExternalCacheDir());
            if (openInputStream != null) {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(valueOf + PackagingURIHelper.FORWARD_SLASH_CHAR + str));
                BufferedInputStream bufferedInputStream = new BufferedInputStream(openInputStream);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                byte[] bArr = new byte[1024];
                for (int read = bufferedInputStream.read(bArr); read > 0; read = bufferedInputStream.read(bArr)) {
                    bufferedOutputStream.write(bArr, 0, read);
                    bufferedOutputStream.flush();
                }
                bufferedOutputStream.close();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final String getFileNameByUri(Activity activity, Uri uri) {
        Intrinsics.checkNotNullParameter(activity, "<this>");
        Intrinsics.checkNotNullParameter(uri, "uri");
        String valueOf = String.valueOf(System.currentTimeMillis());
        Cursor query = activity.getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
        if (query == null || query.getCount() <= 0) {
            return valueOf;
        }
        query.moveToFirst();
        String string = query.getString(query.getColumnIndexOrThrow("_display_name"));
        Intrinsics.checkNotNullExpressionValue(string, "cursor.getString(cursor.…diaColumns.DISPLAY_NAME))");
        query.close();
        return string;
    }

    public static final boolean isNetworkConnected(Context context) {
        NetworkInfo activeNetworkInfo;
        NetworkCapabilities networkCapabilities;
        Intrinsics.checkNotNullParameter(context, "<this>");
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (Build.VERSION.SDK_INT >= 23) {
            if (connectivityManager == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork())) == null) {
                return false;
            }
            if (!networkCapabilities.hasTransport(1) && !networkCapabilities.hasTransport(0) && !networkCapabilities.hasTransport(3)) {
                return false;
            }
        } else if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
            return false;
        } else {
            if (!(activeNetworkInfo.getType() == 1 || activeNetworkInfo.getType() == 0)) {
                return false;
            }
        }
        return true;
    }
}
