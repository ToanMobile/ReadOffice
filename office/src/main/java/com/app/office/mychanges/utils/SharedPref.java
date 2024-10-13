package com.app.office.mychanges.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.itextpdf.text.pdf.PdfBoolean;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b#\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 W2\u00020\u0001:\u0001WB\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006H\u0002J\u0012\u0010\u0014\u001a\u00020\u00122\b\u0010\u0015\u001a\u0004\u0018\u00010\u0006H\u0002J\u0006\u0010\u0016\u001a\u00020\u0012J\u0010\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0006J\u0010\u0010\u001a\u001a\u00020\u00182\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006J\u0010\u0010\u001b\u001a\u00020\u001c2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006J\u0010\u0010\u001d\u001a\u00020\u001e2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006J\u0012\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0006J\u0010\u0010!\u001a\u00020\"2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006J \u0010#\u001a\u0012\u0012\u0004\u0012\u00020\u00180$j\b\u0012\u0004\u0012\u00020\u0018`%2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006J \u0010&\u001a\u0012\u0012\u0004\u0012\u00020\u001c0$j\b\u0012\u0004\u0012\u00020\u001c`%2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006J \u0010'\u001a\u0012\u0012\u0004\u0012\u00020\"0$j\b\u0012\u0004\u0012\u00020\"`%2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006J \u0010(\u001a\u0012\u0012\u0004\u0012\u00020)0$j\b\u0012\u0004\u0012\u00020)`%2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006J.\u0010*\u001a\u0012\u0012\u0004\u0012\u00020\u00010$j\b\u0012\u0004\u0012\u00020\u0001`%2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\f\u0010+\u001a\b\u0012\u0002\b\u0003\u0018\u00010,J \u0010-\u001a\u0012\u0012\u0004\u0012\u00020\u00060$j\b\u0012\u0004\u0012\u00020\u0006`%2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006J\u0010\u0010.\u001a\u00020)2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006J+\u0010/\u001a\u0002H0\"\u0004\b\u0000\u001002\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\u000e\u00101\u001a\n\u0012\u0004\u0012\u0002H0\u0018\u00010,¢\u0006\u0002\u00102J\u0012\u00103\u001a\u0004\u0018\u00010\u00062\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006J\u0018\u00104\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0015\u001a\u00020\u0018J\u0018\u00105\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0015\u001a\u00020\u001cJ\u0018\u00106\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0015\u001a\u00020\u001eJ&\u00107\u001a\u0004\u0018\u00010\u00062\b\u00108\u001a\u0004\u0018\u00010\u00062\b\u00109\u001a\u0004\u0018\u00010\u00062\b\u0010:\u001a\u0004\u0018\u00010 J\u001a\u0010;\u001a\u00020\u00182\b\u0010<\u001a\u0004\u0018\u00010\u00062\b\u0010:\u001a\u0004\u0018\u00010 J\u0018\u0010=\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0015\u001a\u00020\"J(\u0010>\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\u0016\u0010?\u001a\u0012\u0012\u0004\u0012\u00020\u00180$j\b\u0012\u0004\u0012\u00020\u0018`%J(\u0010@\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\u0016\u0010A\u001a\u0012\u0012\u0004\u0012\u00020\u001c0$j\b\u0012\u0004\u0012\u00020\u001c`%J(\u0010B\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\u0016\u0010C\u001a\u0012\u0012\u0004\u0012\u00020\"0$j\b\u0012\u0004\u0012\u00020\"`%J(\u0010D\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\u0016\u0010E\u001a\u0012\u0012\u0004\u0012\u00020)0$j\b\u0012\u0004\u0012\u00020)`%J(\u0010F\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\u0016\u0010G\u001a\u0012\u0012\u0004\u0012\u00020\u00010$j\b\u0012\u0004\u0012\u00020\u0001`%J(\u0010H\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\u0016\u0010I\u001a\u0012\u0012\u0004\u0012\u00020\u00060$j\b\u0012\u0004\u0012\u00020\u0006`%J\u0018\u0010J\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0015\u001a\u00020)J\u001a\u0010K\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\b\u0010L\u001a\u0004\u0018\u00010\u0001J\u001a\u0010M\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00062\b\u0010\u0015\u001a\u0004\u0018\u00010\u0006J\u0010\u0010N\u001a\u00020\u00122\b\u0010O\u001a\u0004\u0018\u00010PJ\u0010\u0010Q\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006J\u001c\u0010R\u001a\u00020\u00182\b\u0010<\u001a\u0004\u0018\u00010\u00062\b\u0010S\u001a\u0004\u0018\u00010 H\u0002J\u0010\u0010T\u001a\u00020\u00062\u0006\u0010U\u001a\u00020\u0006H\u0002J\u0010\u0010V\u001a\u00020\u00122\b\u0010O\u001a\u0004\u0018\u00010PR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\f\u0012\u0004\u0012\u00020\u0006\u0012\u0002\b\u00030\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0006@BX\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006X"}, d2 = {"Lcom/reader/office/mychanges/utils/SharedPref;", "", "appContext", "Landroid/content/Context;", "(Landroid/content/Context;)V", "DEFAULT_APP_IMAGEDATA_DIRECTORY", "", "all", "", "getAll", "()Ljava/util/Map;", "preferences", "Landroid/content/SharedPreferences;", "<set-?>", "savedImagePath", "getSavedImagePath", "()Ljava/lang/String;", "checkForNullKey", "", "key", "checkForNullValue", "value", "clear", "deleteImage", "", "path", "getBoolean", "getDouble", "", "getFloat", "", "getImage", "Landroid/graphics/Bitmap;", "getInt", "", "getListBoolean", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getListDouble", "getListInt", "getListLong", "", "getListObject", "mClass", "Ljava/lang/Class;", "getListString", "getLong", "getObject", "T", "classOfT", "(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;", "getString", "putBoolean", "putDouble", "putFloat", "putImage", "theFolder", "theImageName", "theBitmap", "putImageWithFullPath", "fullPath", "putInt", "putListBoolean", "boolList", "putListDouble", "doubleList", "putListInt", "intList", "putListLong", "longList", "putListObject", "objArray", "putListString", "stringList", "putLong", "putObject", "obj", "putString", "registerOnSharedPreferenceChangeListener", "listener", "Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;", "remove", "saveBitmap", "bitmap", "setupFullPath", "imageName", "unregisterOnSharedPreferenceChangeListener", "Companion", "office_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: SharedPref.kt */
public final class SharedPref {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private String DEFAULT_APP_IMAGEDATA_DIRECTORY;
    private final SharedPreferences preferences;
    private String savedImagePath = "";

    public SharedPref(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Intrinsics.checkNotNullExpressionValue(defaultSharedPreferences, "getDefaultSharedPreferences(appContext)");
        this.preferences = defaultSharedPreferences;
    }

    public final String getSavedImagePath() {
        return this.savedImagePath;
    }

    public final Bitmap getImage(String str) {
        try {
            return BitmapFactory.decodeFile(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final String putImage(String str, String str2, Bitmap bitmap) {
        if (str == null || str2 == null || bitmap == null) {
            return null;
        }
        this.DEFAULT_APP_IMAGEDATA_DIRECTORY = str;
        String str3 = setupFullPath(str2);
        if (!Intrinsics.areEqual((Object) str3, (Object) "")) {
            this.savedImagePath = str3;
            saveBitmap(str3, bitmap);
        }
        return str3;
    }

    public final boolean putImageWithFullPath(String str, Bitmap bitmap) {
        return (str == null || bitmap == null || !saveBitmap(str, bitmap)) ? false : true;
    }

    private final String setupFullPath(String str) {
        File file = new File(Environment.getExternalStorageDirectory(), this.DEFAULT_APP_IMAGEDATA_DIRECTORY);
        Companion companion = Companion;
        if (!companion.isExternalStorageReadable() || !companion.isExternalStorageWritable() || file.exists() || file.mkdirs()) {
            return file.getPath() + PackagingURIHelper.FORWARD_SLASH_CHAR + str;
        }
        Log.e("ERROR", "Failed to setup folder");
        return "";
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0051 A[SYNTHETIC, Splitter:B:31:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0061 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0069 A[SYNTHETIC, Splitter:B:43:0x0069] */
    /* JADX WARNING: Removed duplicated region for block: B:49:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean saveBitmap(java.lang.String r7, android.graphics.Bitmap r8) {
        /*
            r6 = this;
            r0 = 0
            if (r7 == 0) goto L_0x0075
            if (r8 != 0) goto L_0x0007
            goto L_0x0075
        L_0x0007:
            java.io.File r1 = new java.io.File
            r1.<init>(r7)
            boolean r7 = r1.exists()
            if (r7 == 0) goto L_0x0019
            boolean r7 = r1.delete()
            if (r7 != 0) goto L_0x0019
            return r0
        L_0x0019:
            boolean r7 = r1.createNewFile()     // Catch:{ IOException -> 0x001e }
            goto L_0x0023
        L_0x001e:
            r7 = move-exception
            r7.printStackTrace()
            r7 = 0
        L_0x0023:
            r2 = 0
            r3 = 1
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x004b }
            r4.<init>(r1)     // Catch:{ Exception -> 0x004b }
            android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ Exception -> 0x0046, all -> 0x0043 }
            r2 = 100
            r5 = r4
            java.io.OutputStream r5 = (java.io.OutputStream) r5     // Catch:{ Exception -> 0x0046, all -> 0x0043 }
            boolean r8 = r8.compress(r1, r2, r5)     // Catch:{ Exception -> 0x0046, all -> 0x0043 }
            r4.flush()     // Catch:{ IOException -> 0x003d }
            r4.close()     // Catch:{ IOException -> 0x003d }
            r1 = 1
            goto L_0x005f
        L_0x003d:
            r1 = move-exception
            r1.printStackTrace()
            r1 = 0
            goto L_0x005f
        L_0x0043:
            r7 = move-exception
            r2 = r4
            goto L_0x0067
        L_0x0046:
            r8 = move-exception
            r2 = r4
            goto L_0x004c
        L_0x0049:
            r7 = move-exception
            goto L_0x0067
        L_0x004b:
            r8 = move-exception
        L_0x004c:
            r8.printStackTrace()     // Catch:{ all -> 0x0049 }
            if (r2 == 0) goto L_0x005d
            r2.flush()     // Catch:{ IOException -> 0x0059 }
            r2.close()     // Catch:{ IOException -> 0x0059 }
            r1 = 1
            goto L_0x005e
        L_0x0059:
            r8 = move-exception
            r8.printStackTrace()
        L_0x005d:
            r1 = 0
        L_0x005e:
            r8 = 0
        L_0x005f:
            if (r7 == 0) goto L_0x0066
            if (r8 == 0) goto L_0x0066
            if (r1 == 0) goto L_0x0066
            r0 = 1
        L_0x0066:
            return r0
        L_0x0067:
            if (r2 == 0) goto L_0x0074
            r2.flush()     // Catch:{ IOException -> 0x0070 }
            r2.close()     // Catch:{ IOException -> 0x0070 }
            goto L_0x0074
        L_0x0070:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0074:
            throw r7
        L_0x0075:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.mychanges.utils.SharedPref.saveBitmap(java.lang.String, android.graphics.Bitmap):boolean");
    }

    public final int getInt(String str) {
        return this.preferences.getInt(str, 0);
    }

    public final ArrayList<Integer> getListInt(String str) {
        String[] split = TextUtils.split(this.preferences.getString(str, ""), "‚‗‚");
        ArrayList arrayList = new ArrayList(Arrays.asList(Arrays.copyOf(split, split.length)));
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            Intrinsics.checkNotNullExpressionValue(str2, "item");
            arrayList2.add(Integer.valueOf(Integer.parseInt(str2)));
        }
        return arrayList2;
    }

    public final long getLong(String str) {
        return this.preferences.getLong(str, 0);
    }

    public final float getFloat(String str) {
        return this.preferences.getFloat(str, 0.0f);
    }

    public final double getDouble(String str) {
        String string = getString(str);
        try {
            Intrinsics.checkNotNull(string);
            return Double.parseDouble(string);
        } catch (NumberFormatException unused) {
            return 0.0d;
        }
    }

    public final ArrayList<Double> getListDouble(String str) {
        String[] split = TextUtils.split(this.preferences.getString(str, ""), "‚‗‚");
        ArrayList arrayList = new ArrayList(Arrays.asList(Arrays.copyOf(split, split.length)));
        ArrayList<Double> arrayList2 = new ArrayList<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            Intrinsics.checkNotNullExpressionValue(str2, "item");
            arrayList2.add(Double.valueOf(Double.parseDouble(str2)));
        }
        return arrayList2;
    }

    public final ArrayList<Long> getListLong(String str) {
        String[] split = TextUtils.split(this.preferences.getString(str, ""), "‚‗‚");
        ArrayList arrayList = new ArrayList(Arrays.asList(Arrays.copyOf(split, split.length)));
        ArrayList<Long> arrayList2 = new ArrayList<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            Intrinsics.checkNotNullExpressionValue(str2, "item");
            arrayList2.add(Long.valueOf(Long.parseLong(str2)));
        }
        return arrayList2;
    }

    public final String getString(String str) {
        return this.preferences.getString(str, "");
    }

    public final ArrayList<String> getListString(String str) {
        String[] split = TextUtils.split(this.preferences.getString(str, ""), "‚‗‚");
        return new ArrayList<>(Arrays.asList(Arrays.copyOf(split, split.length)));
    }

    public final boolean getBoolean(String str) {
        return this.preferences.getBoolean(str, false);
    }

    public final ArrayList<Boolean> getListBoolean(String str) {
        ArrayList<String> listString = getListString(str);
        ArrayList<Boolean> arrayList = new ArrayList<>();
        Iterator<String> it = listString.iterator();
        while (it.hasNext()) {
            arrayList.add(Boolean.valueOf(Intrinsics.areEqual((Object) it.next(), (Object) PdfBoolean.TRUE)));
        }
        return arrayList;
    }

    public final ArrayList<Object> getListObject(String str, Class<?> cls) {
        Gson gson = new Gson();
        ArrayList<String> listString = getListString(str);
        ArrayList<Object> arrayList = new ArrayList<>();
        Iterator<String> it = listString.iterator();
        while (it.hasNext()) {
            arrayList.add(gson.fromJson(it.next(), cls));
        }
        return arrayList;
    }

    public final <T> T getObject(String str, Class<T> cls) {
        T fromJson = new Gson().fromJson(getString(str), cls);
        Objects.requireNonNull(fromJson);
        return fromJson;
    }

    public final void putInt(String str, int i) {
        checkForNullKey(str);
        this.preferences.edit().putInt(str, i).apply();
    }

    public final void putListInt(String str, ArrayList<Integer> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "intList");
        checkForNullKey(str);
        Object[] array = arrayList.toArray(new Integer[0]);
        Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        this.preferences.edit().putString(str, TextUtils.join("‚‗‚", (Integer[]) array)).apply();
    }

    public final void putLong(String str, long j) {
        checkForNullKey(str);
        this.preferences.edit().putLong(str, j).apply();
    }

    public final void putListLong(String str, ArrayList<Long> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "longList");
        checkForNullKey(str);
        Object[] array = arrayList.toArray(new Long[0]);
        Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        this.preferences.edit().putString(str, TextUtils.join("‚‗‚", (Long[]) array)).apply();
    }

    public final void putFloat(String str, float f) {
        checkForNullKey(str);
        this.preferences.edit().putFloat(str, f).apply();
    }

    public final void putDouble(String str, double d) {
        checkForNullKey(str);
        putString(str, String.valueOf(d));
    }

    public final void putListDouble(String str, ArrayList<Double> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "doubleList");
        checkForNullKey(str);
        Object[] array = arrayList.toArray(new Double[0]);
        Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        this.preferences.edit().putString(str, TextUtils.join("‚‗‚", (Double[]) array)).apply();
    }

    public final void putString(String str, String str2) {
        checkForNullKey(str);
        checkForNullValue(str2);
        this.preferences.edit().putString(str, str2).apply();
    }

    public final void putListString(String str, ArrayList<String> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "stringList");
        checkForNullKey(str);
        Object[] array = arrayList.toArray(new String[0]);
        Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        this.preferences.edit().putString(str, TextUtils.join("‚‗‚", (String[]) array)).apply();
    }

    public final void putBoolean(String str, boolean z) {
        checkForNullKey(str);
        this.preferences.edit().putBoolean(str, z).apply();
    }

    public final void putListBoolean(String str, ArrayList<Boolean> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "boolList");
        checkForNullKey(str);
        ArrayList arrayList2 = new ArrayList();
        Iterator<Boolean> it = arrayList.iterator();
        while (it.hasNext()) {
            Boolean next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "item");
            if (next.booleanValue()) {
                arrayList2.add(PdfBoolean.TRUE);
            } else {
                arrayList2.add(PdfBoolean.FALSE);
            }
        }
        putListString(str, arrayList2);
    }

    public final void putObject(String str, Object obj) {
        checkForNullKey(str);
        putString(str, new Gson().toJson(obj));
    }

    public final void putListObject(String str, ArrayList<Object> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "objArray");
        checkForNullKey(str);
        Gson gson = new Gson();
        ArrayList arrayList2 = new ArrayList();
        Iterator<Object> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(gson.toJson(it.next()));
        }
        putListString(str, arrayList2);
    }

    public final void remove(String str) {
        this.preferences.edit().remove(str).apply();
    }

    public final boolean deleteImage(String str) {
        return new File(str).delete();
    }

    public final void clear() {
        this.preferences.edit().clear().apply();
    }

    public final Map<String, ?> getAll() {
        Map<String, ?> all = this.preferences.getAll();
        Intrinsics.checkNotNullExpressionValue(all, "preferences.all");
        return all;
    }

    public final void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        this.preferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public final void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        this.preferences.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    private final void checkForNullKey(String str) {
        Objects.requireNonNull(str);
    }

    private final void checkForNullValue(String str) {
        Objects.requireNonNull(str);
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0005¨\u0006\u0007"}, d2 = {"Lcom/reader/office/mychanges/utils/SharedPref$Companion;", "", "()V", "isExternalStorageReadable", "", "()Z", "isExternalStorageWritable", "office_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: SharedPref.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isExternalStorageWritable() {
            return Intrinsics.areEqual((Object) "mounted", (Object) Environment.getExternalStorageState());
        }

        public final boolean isExternalStorageReadable() {
            String externalStorageState = Environment.getExternalStorageState();
            return Intrinsics.areEqual((Object) "mounted", (Object) externalStorageState) || Intrinsics.areEqual((Object) "mounted_ro", (Object) externalStorageState);
        }
    }
}
