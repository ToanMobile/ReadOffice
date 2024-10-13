package com.app.office.ss.util.format;

import androidx.room.RoomDatabase;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;

public class FractionalFormat extends Format {
    private short ONE_DIGIT = 1;
    private short THREE_DIGIT = 3;
    private short TWO_DIGIT = 2;
    private short UNITS = 4;
    private short mode = -1;
    private int units = 1;

    public Object clone() {
        return null;
    }

    public Object parseObject(String str) throws ParseException {
        return null;
    }

    public Object parseObject(String str, ParsePosition parsePosition) {
        return null;
    }

    public FractionalFormat(String str) {
        if ("# ?/?".equals(str)) {
            this.mode = this.ONE_DIGIT;
        } else if ("# ??/??".equals(str)) {
            this.mode = this.TWO_DIGIT;
        } else if ("# ???/???".equals(str)) {
            this.mode = this.THREE_DIGIT;
        } else if ("# ?/2".equals(str)) {
            this.mode = this.UNITS;
            this.units = 2;
        } else if ("# ?/4".equals(str)) {
            this.mode = this.UNITS;
            this.units = 4;
        } else if ("# ?/8".equals(str)) {
            this.mode = this.UNITS;
            this.units = 8;
        } else if ("# ??/16".equals(str)) {
            this.mode = this.UNITS;
            this.units = 16;
        } else if ("# ?/10".equals(str)) {
            this.mode = this.UNITS;
            this.units = 10;
        } else if ("# ??/100".equals(str)) {
            this.mode = this.UNITS;
            this.units = 100;
        }
    }

    private String format(double d, int i) {
        long j;
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        double d2 = d;
        long j7 = (long) d2;
        char c = d2 < 0.0d ? (char) 65535 : 1;
        double abs = Math.abs(d) - ((double) j7);
        long j8 = 0;
        if (abs > 1.0E-5d) {
            double d3 = abs;
            double d4 = 1.0E-5d;
            j3 = 0;
            long j9 = 0;
            long j10 = 1;
            while (true) {
                double d5 = 1.0d / d3;
                long j11 = (long) (d5 + d4);
                double d6 = d5 - ((double) j11);
                int i2 = (j3 > j8 ? 1 : (j3 == j8 ? 0 : -1));
                if (i2 > 0) {
                    j10 = (j11 * j10) + j9;
                }
                long j12 = j10;
                double d7 = (double) j12;
                j6 = j12;
                j2 = (long) ((d7 / abs) + 0.5d);
                j = j7;
                double abs2 = Math.abs((d7 / ((double) j2)) - abs);
                double d8 = d6;
                long j13 = (long) i;
                if (j2 > j13) {
                    if (i2 > 0) {
                        double d9 = (double) j3;
                        long j14 = (long) ((d9 / abs) + 0.5d);
                        Math.abs((d9 / ((double) j14)) - abs);
                        j2 = j14;
                    } else if (Math.abs((((double) 1) / ((double) j13)) - abs) > abs) {
                        j3 = 0;
                        j2 = 1;
                    } else {
                        j2 = j13;
                        j3 = 1;
                    }
                } else if (abs2 <= 1.0E-5d || d8 < d4) {
                    j3 = j6;
                } else {
                    d4 = 1.0E-5d / abs2;
                    j7 = j;
                    j10 = j6;
                    d3 = d8;
                    j8 = 0;
                    j9 = j3;
                    j3 = j10;
                }
            }
            j3 = j6;
        } else {
            j = j7;
            j3 = 1;
            j2 = 0;
        }
        if (j3 == j2) {
            j4 = j + 1;
            j5 = 0;
            j3 = 0;
            j2 = 0;
        } else {
            j5 = 0;
            if (j2 == 0) {
                j3 = 0;
            }
            j4 = j;
        }
        if (c < 0) {
            if (j4 == j5) {
                j3 = -j3;
            } else {
                j4 = -j4;
            }
        }
        String str = "";
        if (j4 != j5) {
            str = str.concat(String.valueOf(j4));
        }
        if (j3 == j5 || j2 == j5) {
            return str;
        }
        return str.concat(" " + j3 + PackagingURIHelper.FORWARD_SLASH_STRING + j2);
    }

    private String formatUnit(double d, int i) {
        long j = (long) d;
        long round = Math.round((d - ((double) j)) * ((double) i));
        String str = "";
        if (j != 0) {
            str = str.concat(String.valueOf(j));
        }
        if (round == 0) {
            return str;
        }
        return str.concat(" " + round + PackagingURIHelper.FORWARD_SLASH_STRING + i);
    }

    public final String format(double d) {
        short s = this.mode;
        if (s == this.ONE_DIGIT) {
            return format(d, 9);
        }
        if (s == this.TWO_DIGIT) {
            return format(d, 99);
        }
        if (s == this.THREE_DIGIT) {
            return format(d, RoomDatabase.MAX_BIND_PARAMETER_CNT);
        }
        if (s == this.UNITS) {
            return formatUnit(d, this.units);
        }
        throw new RuntimeException("Unexpected Case");
    }

    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (obj instanceof Number) {
            stringBuffer.append(format(((Number) obj).doubleValue()));
            return stringBuffer;
        }
        throw new IllegalArgumentException("Can only handle Numbers");
    }
}
