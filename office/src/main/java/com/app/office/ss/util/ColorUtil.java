package com.app.office.ss.util;

import android.graphics.Color;
import androidx.core.view.MotionEventCompat;

public class ColorUtil {
    private static ColorUtil util = new ColorUtil();

    /* JADX WARNING: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001e A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int applyTint(int r4, double r5) {
        /*
            r0 = 0
            int r2 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r2 <= 0) goto L_0x000f
            double r0 = (double) r4
            int r4 = 255 - r4
            double r2 = (double) r4
            double r2 = r2 * r5
            double r0 = r0 + r2
        L_0x000d:
            int r4 = (int) r0
            goto L_0x001a
        L_0x000f:
            int r2 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r2 >= 0) goto L_0x001a
            double r0 = (double) r4
            r2 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r5 = r5 + r2
            double r0 = r0 * r5
            goto L_0x000d
        L_0x001a:
            r5 = 255(0xff, float:3.57E-43)
            if (r4 <= r5) goto L_0x0020
            r4 = 255(0xff, float:3.57E-43)
        L_0x0020:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.util.ColorUtil.applyTint(int, double):int");
    }

    public static int rgb(byte b, byte b2, byte b3) {
        return ((b << 16) & 16711680) | -16777216 | ((b2 << 8) & 65280) | (b3 & 255);
    }

    public static int rgb(int i, int i2, int i3) {
        return ((i << 16) & 16711680) | -16777216 | ((i2 << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (i3 & 255);
    }

    public static ColorUtil instance() {
        return util;
    }

    public int getColorWithTint(int i, double d) {
        return Color.rgb(applyTint(Color.red(i) & 255, d), applyTint(Color.green(i) & 255, d), applyTint(Color.blue(i) & 255, d));
    }
}
