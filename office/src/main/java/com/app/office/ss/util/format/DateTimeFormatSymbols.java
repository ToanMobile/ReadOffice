package com.app.office.ss.util.format;

import androidx.exifinterface.media.ExifInterface;
import java.text.DateFormatSymbols;
import java.util.Locale;

public class DateTimeFormatSymbols {
    public DateFormatSymbols formatData;
    public final String[] stdMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December", ""};
    public final String[] stdShortMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec", ""};
    public final String[] stdShortWeekdays = {"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    public final String[] stdShortestMonths = {"J", "F", "M", ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "M", "J", "J", ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, ExifInterface.LATITUDE_SOUTH, "O", "N", "D"};
    public final String[] stdWeekdays = {"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public DateTimeFormatSymbols(Locale locale) {
        this.formatData = new DateFormatSymbols(locale);
    }

    public void dispose() {
        this.formatData = null;
    }
}
