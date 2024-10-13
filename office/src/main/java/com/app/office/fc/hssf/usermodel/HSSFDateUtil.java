package com.app.office.fc.hssf.usermodel;

import com.app.office.ss.util.DateUtil;
import java.util.Calendar;

public class HSSFDateUtil extends DateUtil {
    protected static int absoluteDay(Calendar calendar, boolean z) {
        return DateUtil.absoluteDay(calendar, z);
    }
}
