package com.app.office.system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import com.app.office.common.bookmark.BookmarkManage;
import com.app.office.common.borders.BordersManage;
import com.app.office.common.bulletnumber.ListManage;
import com.app.office.common.hyperlink.HyperlinkManage;
import com.app.office.common.picture.PictureManage;
import com.app.office.pg.animate.AnimationManager;
import com.app.office.pg.model.PGBulletText;
import com.app.office.system.beans.CalloutView.CalloutManager;
import com.app.office.wp.control.WPShapeManage;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;

@Metadata(d1 = {"\u0000\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 Q2\u00020\u0001:\u0001QB\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020C2\b\u0010E\u001a\u0004\u0018\u00010CJ\u0006\u0010F\u001a\u00020GJ\u0010\u0010H\u001a\u00020I2\b\u0010J\u001a\u0004\u0018\u00010CJ\u0016\u0010K\u001a\u00020G2\u0006\u0010D\u001a\u00020C2\u0006\u0010L\u001a\u00020MJ\u000e\u0010N\u001a\u00020(2\u0006\u0010O\u001a\u00020PR\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\u0006X\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0010X\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0014\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0015X\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u0004R\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u000e¢\u0006\u0002\n\u0000R\u0011\u0010$\u001a\u00020#8F¢\u0006\u0006\u001a\u0004\b%\u0010&R\u0011\u0010'\u001a\u00020(8F¢\u0006\u0006\u001a\u0004\b'\u0010)R\u0011\u0010*\u001a\u00020+8F¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0010\u0010.\u001a\u0004\u0018\u00010+X\u000e¢\u0006\u0002\n\u0000R\u0011\u0010/\u001a\u0002008F¢\u0006\u0006\u001a\u0004\b1\u00102R\u0010\u00103\u001a\u0004\u0018\u000100X\u000e¢\u0006\u0002\n\u0000R\u0011\u00104\u001a\u0002058F¢\u0006\u0006\u001a\u0004\b6\u00107R\u0010\u00108\u001a\u0004\u0018\u000105X\u000e¢\u0006\u0002\n\u0000R\u0013\u00109\u001a\u0004\u0018\u00010:8F¢\u0006\u0006\u001a\u0004\b;\u0010<R\u0011\u0010=\u001a\u00020>8F¢\u0006\u0006\u001a\u0004\b?\u0010@R\u0010\u0010A\u001a\u0004\u0018\u00010>X\u000e¢\u0006\u0002\n\u0000¨\u0006R"}, d2 = {"Lcom/reader/office/system/SysKit;", "", "control", "Lcom/reader/office/system/IControl;", "(Lcom/reader/office/system/IControl;)V", "animationManager", "Lcom/reader/office/pg/animate/AnimationManager;", "getAnimationManager", "()Lcom/reader/office/pg/animate/AnimationManager;", "animationMgr", "bmKit", "Lcom/reader/office/common/bookmark/BookmarkManage;", "bookmarkManage", "getBookmarkManage", "()Lcom/reader/office/common/bookmark/BookmarkManage;", "bordersManage", "Lcom/reader/office/common/borders/BordersManage;", "getBordersManage", "()Lcom/reader/office/common/borders/BordersManage;", "brKit", "calloutManager", "Lcom/reader/office/system/beans/CalloutView/CalloutManager;", "getCalloutManager", "()Lcom/reader/office/system/beans/CalloutView/CalloutManager;", "calloutMgr", "getControl", "()Lcom/reader/office/system/IControl;", "setControl", "errorKit", "Lcom/reader/office/system/ErrorUtil;", "getErrorKit", "()Lcom/reader/office/system/ErrorUtil;", "setErrorKit", "(Lcom/reader/office/system/ErrorUtil;)V", "hmKit", "Lcom/reader/office/common/hyperlink/HyperlinkManage;", "hyperlinkManage", "getHyperlinkManage", "()Lcom/reader/office/common/hyperlink/HyperlinkManage;", "isDebug", "", "()Z", "listManage", "Lcom/reader/office/common/bulletnumber/ListManage;", "getListManage", "()Lcom/reader/office/common/bulletnumber/ListManage;", "lmKit", "pGBulletText", "Lcom/reader/office/pg/model/PGBulletText;", "getPGBulletText", "()Lcom/reader/office/pg/model/PGBulletText;", "pgLMKit", "pictureManage", "Lcom/reader/office/common/picture/PictureManage;", "getPictureManage", "()Lcom/reader/office/common/picture/PictureManage;", "pmKit", "sDPath", "Ljava/io/File;", "getSDPath", "()Ljava/io/File;", "wPShapeManage", "Lcom/reader/office/wp/control/WPShapeManage;", "getWPShapeManage", "()Lcom/reader/office/wp/control/WPShapeManage;", "wpSMKit", "charsetEncode", "", "str", "encode", "dispose", "", "getAvailableStore", "", "filePath", "internetSearch", "activity", "Landroid/app/Activity;", "isVertical", "context", "Landroid/content/Context;", "Companion", "office_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: SysKit.kt */
public final class SysKit {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static ShapeDrawable pageNumberDrawable;
    private AnimationManager animationMgr;
    private BookmarkManage bmKit;
    private BordersManage brKit;
    private CalloutManager calloutMgr;
    private IControl control;
    private ErrorUtil errorKit;
    private HyperlinkManage hmKit;
    private ListManage lmKit;
    private PGBulletText pgLMKit;
    private PictureManage pmKit;
    private WPShapeManage wpSMKit;

    public static final Drawable getPageNubmerDrawable() {
        return Companion.getPageNubmerDrawable();
    }

    @JvmStatic
    public static final boolean isValidateRect(int i, int i2, int i3, int i4, int i5, int i6) {
        return Companion.isValidateRect(i, i2, i3, i4, i5, i6);
    }

    public final boolean isDebug() {
        return false;
    }

    public SysKit(IControl iControl) {
        this.control = iControl;
    }

    public final IControl getControl() {
        return this.control;
    }

    public final void setControl(IControl iControl) {
        this.control = iControl;
    }

    public final File getSDPath() {
        if (new File("/mnt/extern_sd").exists() || new File("/mnt/usbhost1").exists()) {
            return new File("/mnt");
        }
        if (Intrinsics.areEqual((Object) "mounted", (Object) Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory();
        }
        return null;
    }

    public final long getAvailableStore(String str) {
        StatFs statFs = new StatFs(str);
        return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
    }

    public final boolean isVertical(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getResources().getConfiguration().orientation == 1;
    }

    public final String charsetEncode(String str, String str2) {
        Intrinsics.checkNotNullParameter(str, "str");
        if (Intrinsics.areEqual((Object) "", (Object) str)) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer("");
        try {
            Intrinsics.checkNotNull(str2);
            Charset forName = Charset.forName(str2);
            Intrinsics.checkNotNullExpressionValue(forName, "forName(charsetName)");
            byte[] bytes = str.getBytes(forName);
            Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
            int length = bytes.length;
            int i = 0;
            while (i < length) {
                int i2 = i + 1;
                String hexString = Integer.toHexString((byte) (bytes[i] & -1));
                Intrinsics.checkNotNullExpressionValue(hexString, "toHexString((b[n] and 0XFF.toByte()).toInt())");
                if (hexString.length() == 1) {
                    stringBuffer.append("0");
                    stringBuffer.append(hexString);
                } else {
                    stringBuffer.append(hexString);
                }
                i = i2;
            }
            String stringBuffer2 = stringBuffer.toString();
            Intrinsics.checkNotNullExpressionValue(stringBuffer2, "strBuff.toString()");
            char[] charArray = stringBuffer2.toCharArray();
            Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
            stringBuffer.delete(0, stringBuffer.length());
            for (int i3 = 0; i3 < charArray.length; i3 += 2) {
                stringBuffer.append("%");
                stringBuffer.append(charArray[i3]);
                stringBuffer.append(charArray[i3 + 1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String stringBuffer3 = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(stringBuffer3, "strBuff.toString()");
        return stringBuffer3;
    }

    public final void internetSearch(String str, Activity activity) {
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.checkNotNullParameter(activity, "activity");
        String charsetEncode = charsetEncode(str, "utf-8");
        activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(new Regex("a-a-a-a").replace((CharSequence) "http://www.google.com.hk/#hl=en&newwindow=1&safe=strict&site=&q=a-a-a-a&oq=a-a-a-a&aq=f&aqi=&aql=&gs_sm=3&gs_upl=1075l1602l0l1935l3l3l0l0l0l0l0l0ll0l0&gs_l=hp.3...1075l1602l0l1935l3l3l0l0l0l0l0l0ll0l0&bav=on.2,or.r_gc.r_pw.,cf.osb&fp=207f1fbbc21b7536&biw=1280&bih=876", charsetEncode))));
    }

    public final PictureManage getPictureManage() {
        if (this.pmKit == null) {
            this.pmKit = new PictureManage(this.control);
        }
        PictureManage pictureManage = this.pmKit;
        Objects.requireNonNull(pictureManage, "null cannot be cast to non-null type com.app.office.common.picture.PictureManage");
        return pictureManage;
    }

    public final HyperlinkManage getHyperlinkManage() {
        if (this.hmKit == null) {
            this.hmKit = new HyperlinkManage();
        }
        HyperlinkManage hyperlinkManage = this.hmKit;
        Objects.requireNonNull(hyperlinkManage, "null cannot be cast to non-null type com.app.office.common.hyperlink.HyperlinkManage");
        return hyperlinkManage;
    }

    public final ListManage getListManage() {
        if (this.lmKit == null) {
            this.lmKit = new ListManage();
        }
        ListManage listManage = this.lmKit;
        Objects.requireNonNull(listManage, "null cannot be cast to non-null type com.app.office.common.bulletnumber.ListManage");
        return listManage;
    }

    public final PGBulletText getPGBulletText() {
        if (this.pgLMKit == null) {
            this.pgLMKit = new PGBulletText();
        }
        PGBulletText pGBulletText = this.pgLMKit;
        Objects.requireNonNull(pGBulletText, "null cannot be cast to non-null type com.app.office.pg.model.PGBulletText");
        return pGBulletText;
    }

    public final BordersManage getBordersManage() {
        if (this.brKit == null) {
            this.brKit = new BordersManage();
        }
        BordersManage bordersManage = this.brKit;
        Objects.requireNonNull(bordersManage, "null cannot be cast to non-null type com.app.office.common.borders.BordersManage");
        return bordersManage;
    }

    public final WPShapeManage getWPShapeManage() {
        if (this.wpSMKit == null) {
            this.wpSMKit = new WPShapeManage();
        }
        WPShapeManage wPShapeManage = this.wpSMKit;
        Objects.requireNonNull(wPShapeManage, "null cannot be cast to non-null type com.app.office.wp.control.WPShapeManage");
        return wPShapeManage;
    }

    public final BookmarkManage getBookmarkManage() {
        if (this.bmKit == null) {
            this.bmKit = new BookmarkManage();
        }
        BookmarkManage bookmarkManage = this.bmKit;
        Objects.requireNonNull(bookmarkManage, "null cannot be cast to non-null type com.app.office.common.bookmark.BookmarkManage");
        return bookmarkManage;
    }

    public final AnimationManager getAnimationManager() {
        if (this.animationMgr == null) {
            this.animationMgr = new AnimationManager(this.control);
        }
        AnimationManager animationManager = this.animationMgr;
        Objects.requireNonNull(animationManager, "null cannot be cast to non-null type com.app.office.pg.animate.AnimationManager");
        return animationManager;
    }

    public final CalloutManager getCalloutManager() {
        if (this.calloutMgr == null) {
            this.calloutMgr = new CalloutManager(this.control);
        }
        CalloutManager calloutManager = this.calloutMgr;
        Objects.requireNonNull(calloutManager, "null cannot be cast to non-null type com.app.office.system.beans.CalloutView.CalloutManager");
        return calloutManager;
    }

    public final void dispose() {
        this.control = null;
        ErrorUtil errorUtil = this.errorKit;
        if (errorUtil != null) {
            Intrinsics.checkNotNull(errorUtil);
            errorUtil.dispose();
            this.errorKit = null;
        }
        PictureManage pictureManage = this.pmKit;
        if (pictureManage != null) {
            Intrinsics.checkNotNull(pictureManage);
            pictureManage.dispose();
            this.pmKit = null;
        }
        HyperlinkManage hyperlinkManage = this.hmKit;
        if (hyperlinkManage != null) {
            Intrinsics.checkNotNull(hyperlinkManage);
            hyperlinkManage.dispose();
            this.hmKit = null;
        }
        ListManage listManage = this.lmKit;
        if (listManage != null) {
            Intrinsics.checkNotNull(listManage);
            listManage.dispose();
            this.lmKit = null;
        }
        PGBulletText pGBulletText = this.pgLMKit;
        if (pGBulletText != null) {
            Intrinsics.checkNotNull(pGBulletText);
            pGBulletText.dispose();
            this.pgLMKit = null;
        }
        BordersManage bordersManage = this.brKit;
        if (bordersManage != null) {
            Intrinsics.checkNotNull(bordersManage);
            bordersManage.dispose();
            this.brKit = null;
        }
        WPShapeManage wPShapeManage = this.wpSMKit;
        if (wPShapeManage != null) {
            Intrinsics.checkNotNull(wPShapeManage);
            wPShapeManage.dispose();
            this.wpSMKit = null;
        }
        BookmarkManage bookmarkManage = this.bmKit;
        if (bookmarkManage != null) {
            Intrinsics.checkNotNull(bookmarkManage);
            bookmarkManage.dispose();
            this.bmKit = null;
        }
        AnimationManager animationManager = this.animationMgr;
        if (animationManager != null) {
            Intrinsics.checkNotNull(animationManager);
            animationManager.dispose();
            this.animationMgr = null;
        }
        CalloutManager calloutManager = this.calloutMgr;
        if (calloutManager != null) {
            Intrinsics.checkNotNull(calloutManager);
            calloutManager.dispose();
            this.calloutMgr = null;
        }
    }

    public final ErrorUtil getErrorKit() {
        return this.errorKit;
    }

    public final void setErrorKit(ErrorUtil errorUtil) {
        this.errorKit = errorUtil;
    }

    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ8\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0011H\u0007R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00048FX\u0004¢\u0006\f\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/reader/office/system/SysKit$Companion;", "", "()V", "pageNubmerDrawable", "Landroid/graphics/drawable/Drawable;", "getPageNubmerDrawable$annotations", "getPageNubmerDrawable", "()Landroid/graphics/drawable/Drawable;", "pageNumberDrawable", "Landroid/graphics/drawable/ShapeDrawable;", "getErrorKit", "Lcom/reader/office/system/ErrorUtil;", "sysKit", "Lcom/reader/office/system/SysKit;", "isValidateRect", "", "pageWidth", "", "pageHeight", "x", "y", "width", "height", "office_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: SysKit.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void getPageNubmerDrawable$annotations() {
        }

        @JvmStatic
        public final boolean isValidateRect(int i, int i2, int i3, int i4, int i5, int i6) {
            return i3 >= 0 && i4 >= 0 && i3 < i && i4 < i2 && i5 >= 0 && i6 >= 0 && i3 + i5 <= i && i4 + i6 <= i2;
        }

        private Companion() {
        }

        public final Drawable getPageNubmerDrawable() {
            if (SysKit.pageNumberDrawable == null) {
                SysKit.pageNumberDrawable = new ShapeDrawable(new RoundRectShape(new float[]{6.0f, 6.0f, 6.0f, 6.0f, 6.0f, 6.0f, 6.0f, 6.0f}, (RectF) null, (float[]) null));
                ShapeDrawable access$getPageNumberDrawable$cp = SysKit.pageNumberDrawable;
                Intrinsics.checkNotNull(access$getPageNumberDrawable$cp);
                access$getPageNumberDrawable$cp.getPaint().setColor(-1996519356);
            }
            return SysKit.pageNumberDrawable;
        }

        public final ErrorUtil getErrorKit(SysKit sysKit) {
            Intrinsics.checkNotNullParameter(sysKit, "sysKit");
            if (sysKit.getErrorKit() == null) {
                sysKit.setErrorKit(new ErrorUtil(sysKit));
            }
            ErrorUtil errorKit = sysKit.getErrorKit();
            Intrinsics.checkNotNull(errorKit);
            return errorKit;
        }
    }
}
