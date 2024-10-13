package com.app.office.fc;

import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;

public class FCKit {
    public static int BGRtoRGB(int i) {
        if (i == -1 || i == 16777215) {
            return -16777216;
        }
        int i2 = i & ViewCompat.MEASURED_SIZE_MASK;
        return -16777216 | ((i2 & 255) << 16) | (65280 & i2) | ((i2 & 16711680) >> 16);
    }

    public static int convertColor(String str) {
        if ("yellow".equals(str)) {
            return InputDeviceCompat.SOURCE_ANY;
        }
        if ("green".equals(str)) {
            return -16711936;
        }
        if ("cyan".equals(str)) {
            return -16711681;
        }
        if ("magenta".equals(str)) {
            return -65281;
        }
        if ("blue".equals(str)) {
            return -16776961;
        }
        if ("red".equals(str)) {
            return SupportMenu.CATEGORY_MASK;
        }
        if ("darkBlue".equals(str)) {
            return -16777077;
        }
        if ("darkCyan".equals(str)) {
            return -16741493;
        }
        if ("darkGreen".equals(str)) {
            return -16751616;
        }
        if ("darkMagenta".equals(str)) {
            return -8388480;
        }
        if ("darkRed".equals(str)) {
            return -7667712;
        }
        if ("darkYellow".equals(str)) {
            return -8355840;
        }
        if ("darkGray".equals(str)) {
            return -12303292;
        }
        if ("lightGray".equals(str)) {
            return -3355444;
        }
        return "black".equals(str) ? -16777216 : -1;
    }
}
