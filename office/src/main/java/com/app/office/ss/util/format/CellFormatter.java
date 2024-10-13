package com.app.office.ss.util.format;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;

public class CellFormatter {
    private static CellFormatter cf = new CellFormatter();
    private DecimalFormat generalNumberFormat = new DecimalFormat("0");
    private Format[] textFormatter;

    public boolean useRedColor(short s, double d) {
        return (s == 6 || s == 8 || s == 38 || s == 39) && d < 0.0d;
    }

    public CellFormatter() {
        Format[] formatArr = new Format[49];
        this.textFormatter = formatArr;
        formatArr[1] = new DecimalFormat("0");
        this.textFormatter[2] = new DecimalFormat("0.00");
        this.textFormatter[3] = new DecimalFormat("#,##0");
        this.textFormatter[4] = new DecimalFormat("#,##0.00");
        this.textFormatter[5] = new DecimalFormat("$#,##0;$#,##0");
        this.textFormatter[6] = new DecimalFormat("$#,##0;$#,##0");
        this.textFormatter[7] = new DecimalFormat("$#,##0.00;$#,##0.00");
        this.textFormatter[8] = new DecimalFormat("$#,##0.00;$#,##0.00");
        this.textFormatter[9] = new DecimalFormat("0%");
        this.textFormatter[10] = new DecimalFormat("0.00%");
        this.textFormatter[11] = new DecimalFormat("0.00E0");
        this.textFormatter[12] = new FractionalFormat("# ?/?");
        this.textFormatter[13] = new FractionalFormat("# ??/??");
        this.textFormatter[14] = new SimpleDateFormat("M/d/yy");
        this.textFormatter[15] = new SimpleDateFormat("d-MMM-yy");
        this.textFormatter[16] = new SimpleDateFormat("d-MMM");
        this.textFormatter[17] = new SimpleDateFormat("MMM-yy");
        this.textFormatter[18] = new SimpleDateFormat("h:mm a");
        this.textFormatter[19] = new SimpleDateFormat("h:mm:ss a");
        this.textFormatter[20] = new SimpleDateFormat("h:mm");
        this.textFormatter[21] = new SimpleDateFormat("h:mm:ss");
        this.textFormatter[22] = new SimpleDateFormat("M/d/yy h:mm");
        this.textFormatter[38] = new DecimalFormat("#,##0;#,##0");
        this.textFormatter[39] = new DecimalFormat("#,##0.00;#,##0.00");
        this.textFormatter[40] = new DecimalFormat("#,##0.00;#,##0.00");
        this.textFormatter[45] = new SimpleDateFormat("mm:ss");
        this.textFormatter[47] = new SimpleDateFormat("mm:ss.0");
        this.textFormatter[48] = new DecimalFormat("##0.0E0");
    }

    public static CellFormatter instance() {
        return cf;
    }

    public String format(short s, Object obj) {
        if (s == 0) {
            return obj.toString();
        }
        Format[] formatArr = this.textFormatter;
        if (formatArr[s] != null) {
            return formatArr[s].format(obj);
        }
        throw new RuntimeException("Sorry. I cant handle the format code :" + Integer.toHexString(s));
    }

    public String format(short s, double d) {
        if (s > 0) {
            Format[] formatArr = this.textFormatter;
            if (s < formatArr.length) {
                if (formatArr[s] == null) {
                    return this.generalNumberFormat.format(d);
                }
                if (formatArr[s] instanceof DecimalFormat) {
                    return ((DecimalFormat) formatArr[s]).format(d);
                }
                if (formatArr[s] instanceof FractionalFormat) {
                    return ((FractionalFormat) formatArr[s]).format(d);
                }
                return String.valueOf(d);
            }
        }
        return this.generalNumberFormat.format(d);
    }

    public void dispose() {
        this.textFormatter = null;
        this.generalNumberFormat = null;
    }
}
