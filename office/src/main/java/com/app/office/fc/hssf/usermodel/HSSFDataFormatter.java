package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.ss.usermodel.DataFormatter;
import java.util.Locale;

public final class HSSFDataFormatter extends DataFormatter {
    public HSSFDataFormatter(Locale locale) {
        super(locale);
    }

    public HSSFDataFormatter() {
        this(Locale.getDefault());
    }
}
