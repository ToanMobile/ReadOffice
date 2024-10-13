package com.app.office.ss.model.table;

import androidx.core.view.ViewCompat;
import com.app.office.constant.SchemeClrConstant;
import com.app.office.ss.util.ColorUtil;
import java.util.Map;

public class TableStyleKit {
    private static String[] schemeClrName = {SchemeClrConstant.SCHEME_ACCENT1, SchemeClrConstant.SCHEME_ACCENT2, SchemeClrConstant.SCHEME_ACCENT3, SchemeClrConstant.SCHEME_ACCENT4, SchemeClrConstant.SCHEME_ACCENT5, SchemeClrConstant.SCHEME_ACCENT6};
    private SSTableStyle tableStyleDark1_7;
    private SSTableStyle tableStyleDark8;
    private SSTableStyle tableStyleDark9_11;
    private SSTableStyle tableStyleLight15_21;
    private SSTableStyle tableStyleLight1_7;
    private SSTableStyle tableStyleLight8_14;
    private SSTableStyle tableStyleMedium15_21;
    private SSTableStyle tableStyleMedium1_7;
    private SSTableStyle tableStyleMedium22_28;
    private SSTableStyle tableStyleMedium8_14;

    public void dispose() {
    }

    public SSTableStyle getTableStyle(String str, Map<String, Integer> map) {
        if (str != null) {
            try {
                if (str.length() != 0) {
                    if (str.contains("Light")) {
                        int parseInt = Integer.parseInt(str.substring(15).split(" ")[0]);
                        int i = (parseInt - 1) / 7;
                        if (i == 0) {
                            return getTableStyleLight1_7(getSchemeColor(map, parseInt));
                        }
                        if (i == 1) {
                            return getTableStyleLight8_14(getSchemeColor(map, parseInt));
                        }
                        if (i == 2) {
                            return getTableStyleLight15_21(getSchemeColor(map, parseInt));
                        }
                    } else if (str.contains("Medium")) {
                        int parseInt2 = Integer.parseInt(str.substring(16).split(" ")[0]);
                        int i2 = (parseInt2 - 1) / 7;
                        if (i2 == 0) {
                            return getTableStyleMedium1_7(getSchemeColor(map, parseInt2));
                        }
                        if (i2 == 1) {
                            return getTableStyleMedium8_14(getSchemeColor(map, parseInt2));
                        }
                        if (i2 == 2) {
                            return getTableStyleMedium15_21(getSchemeColor(map, parseInt2));
                        }
                        if (i2 == 3) {
                            return getTableStyleMedium22_28(getSchemeColor(map, parseInt2));
                        }
                    } else {
                        int parseInt3 = Integer.parseInt(str.substring(14).split(" ")[0]);
                        switch (parseInt3) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                                return getTableStyleDark1_7(getSchemeColor(map, parseInt3));
                            case 8:
                                return getTableStyleDark8();
                            case 9:
                            case 10:
                            case 11:
                                int i3 = (parseInt3 - 8) * 2;
                                return getTableStyleDark9_11(getSchemeColor(map, i3 + 1), getSchemeColor(map, i3));
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }

    private int getSchemeColor(Map<String, Integer> map, int i) {
        int i2 = i % 7;
        if (i2 == 1) {
            return -16777216;
        }
        return map.get(schemeClrName[((i2 - 2) + 7) % 7]).intValue();
    }

    private SSTableStyle getTableStyleLight1_7(int i) {
        SSTableStyle sSTableStyle = this.tableStyleLight1_7;
        if (sSTableStyle == null) {
            this.tableStyleLight1_7 = new SSTableStyle();
            SSTableCellStyle sSTableCellStyle = new SSTableCellStyle(-1);
            sSTableCellStyle.setFontColor(i);
            sSTableCellStyle.setBorderColor(i);
            this.tableStyleLight1_7.setFirstRow(sSTableCellStyle);
            this.tableStyleLight1_7.setLastRow(sSTableCellStyle);
            SSTableCellStyle sSTableCellStyle2 = new SSTableCellStyle(ColorUtil.instance().getColorWithTint(i, 0.800000011920929d));
            sSTableCellStyle2.setFontColor(i);
            this.tableStyleLight1_7.setBand1H(sSTableCellStyle2);
            this.tableStyleLight1_7.setBand1V(sSTableCellStyle2);
            SSTableCellStyle sSTableCellStyle3 = new SSTableCellStyle(-1);
            sSTableCellStyle3.setFontColor(i);
            this.tableStyleLight1_7.setBand2H(sSTableCellStyle3);
            this.tableStyleLight1_7.setBand2V(sSTableCellStyle3);
        } else {
            SSTableCellStyle firstRow = sSTableStyle.getFirstRow();
            firstRow.setFontColor(i);
            firstRow.setBorderColor(i);
            this.tableStyleLight1_7.setFirstRow(firstRow);
            this.tableStyleLight1_7.setLastRow(firstRow);
            int colorWithTint = ColorUtil.instance().getColorWithTint(i, 0.800000011920929d);
            SSTableCellStyle band1H = this.tableStyleLight1_7.getBand1H();
            band1H.setFillColor(colorWithTint);
            band1H.setFontColor(i);
            this.tableStyleLight1_7.setBand1H(band1H);
            this.tableStyleLight1_7.setBand1V(band1H);
            SSTableCellStyle band2H = this.tableStyleLight1_7.getBand2H();
            band2H.setFontColor(i);
            this.tableStyleLight1_7.setBand2H(band2H);
            this.tableStyleLight1_7.setBand2V(band2H);
        }
        return this.tableStyleLight1_7;
    }

    private SSTableStyle getTableStyleLight8_14(int i) {
        SSTableStyle sSTableStyle = this.tableStyleLight8_14;
        if (sSTableStyle == null) {
            this.tableStyleLight8_14 = new SSTableStyle();
            SSTableCellStyle sSTableCellStyle = new SSTableCellStyle(i);
            sSTableCellStyle.setFontColor(-1);
            sSTableCellStyle.setBorderColor(i);
            this.tableStyleLight8_14.setFirstRow(sSTableCellStyle);
            SSTableCellStyle sSTableCellStyle2 = new SSTableCellStyle(-1);
            sSTableCellStyle2.setBorderColor(i);
            this.tableStyleLight8_14.setLastRow(sSTableCellStyle2);
            this.tableStyleLight8_14.setBand1H(sSTableCellStyle2);
            this.tableStyleLight8_14.setBand1V(sSTableCellStyle2);
            this.tableStyleLight8_14.setBand2H(sSTableCellStyle2);
            this.tableStyleLight8_14.setBand2V(sSTableCellStyle2);
        } else {
            SSTableCellStyle firstRow = sSTableStyle.getFirstRow();
            firstRow.setFillColor(i);
            firstRow.setBorderColor(i);
            this.tableStyleLight8_14.setFirstRow(firstRow);
            SSTableCellStyle band1H = this.tableStyleLight8_14.getBand1H();
            band1H.setBorderColor(i);
            this.tableStyleLight8_14.setLastRow(band1H);
            this.tableStyleLight8_14.setBand1H(band1H);
            this.tableStyleLight8_14.setBand1V(band1H);
            this.tableStyleLight8_14.setBand2H(band1H);
            this.tableStyleLight8_14.setBand2V(band1H);
        }
        return this.tableStyleLight8_14;
    }

    private SSTableStyle getTableStyleLight15_21(int i) {
        SSTableStyle sSTableStyle = this.tableStyleLight15_21;
        if (sSTableStyle == null) {
            this.tableStyleLight15_21 = new SSTableStyle();
            SSTableCellStyle sSTableCellStyle = new SSTableCellStyle(-1);
            sSTableCellStyle.setBorderColor(i);
            this.tableStyleLight15_21.setFirstRow(sSTableCellStyle);
            this.tableStyleLight15_21.setLastRow(sSTableCellStyle);
            SSTableCellStyle sSTableCellStyle2 = new SSTableCellStyle(ColorUtil.instance().getColorWithTint(i, 0.800000011920929d));
            sSTableCellStyle2.setBorderColor(i);
            this.tableStyleLight15_21.setBand1H(sSTableCellStyle2);
            this.tableStyleLight15_21.setBand1V(sSTableCellStyle2);
            SSTableCellStyle sSTableCellStyle3 = new SSTableCellStyle(-1);
            sSTableCellStyle3.setBorderColor(i);
            this.tableStyleLight15_21.setBand2H(sSTableCellStyle3);
            this.tableStyleLight15_21.setBand2V(sSTableCellStyle3);
        } else {
            SSTableCellStyle firstRow = sSTableStyle.getFirstRow();
            firstRow.setBorderColor(i);
            this.tableStyleLight15_21.setFirstRow(firstRow);
            this.tableStyleLight15_21.setLastRow(firstRow);
            int colorWithTint = ColorUtil.instance().getColorWithTint(i, 0.800000011920929d);
            SSTableCellStyle band1H = this.tableStyleLight15_21.getBand1H();
            band1H.setFillColor(colorWithTint);
            band1H.setBorderColor(i);
            this.tableStyleLight15_21.setBand1H(band1H);
            this.tableStyleLight15_21.setBand1V(band1H);
            SSTableCellStyle band2H = this.tableStyleLight15_21.getBand2H();
            band2H.setBorderColor(i);
            this.tableStyleLight15_21.setBand2H(band2H);
            this.tableStyleLight15_21.setBand2V(band2H);
        }
        return this.tableStyleLight15_21;
    }

    private SSTableStyle getTableStyleMedium1_7(int i) {
        SSTableStyle sSTableStyle = this.tableStyleMedium1_7;
        if (sSTableStyle == null) {
            this.tableStyleMedium1_7 = new SSTableStyle();
            SSTableCellStyle sSTableCellStyle = new SSTableCellStyle(i);
            sSTableCellStyle.setFontColor(-1);
            sSTableCellStyle.setBorderColor(i);
            this.tableStyleMedium1_7.setFirstRow(sSTableCellStyle);
            SSTableCellStyle sSTableCellStyle2 = new SSTableCellStyle(ColorUtil.instance().getColorWithTint(i, 0.800000011920929d));
            sSTableCellStyle2.setBorderColor(i);
            this.tableStyleMedium1_7.setBand1H(sSTableCellStyle2);
            this.tableStyleMedium1_7.setBand1V(sSTableCellStyle2);
            SSTableCellStyle sSTableCellStyle3 = new SSTableCellStyle(-1);
            sSTableCellStyle3.setBorderColor(i);
            this.tableStyleMedium1_7.setBand2H(sSTableCellStyle3);
            this.tableStyleMedium1_7.setBand2V(sSTableCellStyle3);
        } else {
            SSTableCellStyle firstRow = sSTableStyle.getFirstRow();
            firstRow.setFillColor(i);
            firstRow.setBorderColor(i);
            firstRow.setFontColor(-1);
            this.tableStyleMedium1_7.setFirstRow(firstRow);
            int colorWithTint = ColorUtil.instance().getColorWithTint(i, 0.800000011920929d);
            SSTableCellStyle band1H = this.tableStyleMedium1_7.getBand1H();
            band1H.setFillColor(colorWithTint);
            band1H.setBorderColor(i);
            this.tableStyleMedium1_7.setBand1H(band1H);
            this.tableStyleMedium1_7.setBand1V(band1H);
            SSTableCellStyle band2H = this.tableStyleMedium1_7.getBand2H();
            band2H.setBorderColor(i);
            this.tableStyleMedium1_7.setBand2H(band2H);
            this.tableStyleMedium1_7.setBand2V(band2H);
        }
        return this.tableStyleMedium1_7;
    }

    private SSTableStyle getTableStyleMedium8_14(int i) {
        SSTableStyle sSTableStyle = this.tableStyleMedium8_14;
        if (sSTableStyle == null) {
            this.tableStyleMedium8_14 = new SSTableStyle();
            SSTableCellStyle sSTableCellStyle = new SSTableCellStyle(i);
            sSTableCellStyle.setBorderColor(-1);
            sSTableCellStyle.setFontColor(-1);
            this.tableStyleMedium8_14.setFirstRow(sSTableCellStyle);
            this.tableStyleMedium8_14.setFirstCol(sSTableCellStyle);
            this.tableStyleMedium8_14.setLastCol(sSTableCellStyle);
            this.tableStyleMedium8_14.setLastRow(sSTableCellStyle);
            SSTableCellStyle sSTableCellStyle2 = new SSTableCellStyle(ColorUtil.instance().getColorWithTint(i, 0.6000000238418579d));
            sSTableCellStyle2.setBorderColor(-1);
            this.tableStyleMedium8_14.setBand1H(sSTableCellStyle2);
            this.tableStyleMedium8_14.setBand1V(sSTableCellStyle2);
            SSTableCellStyle sSTableCellStyle3 = new SSTableCellStyle(ColorUtil.instance().getColorWithTint(i, 0.800000011920929d));
            sSTableCellStyle3.setBorderColor(-1);
            this.tableStyleMedium8_14.setBand2H(sSTableCellStyle3);
            this.tableStyleMedium8_14.setBand2V(sSTableCellStyle3);
        } else {
            SSTableCellStyle firstRow = sSTableStyle.getFirstRow();
            firstRow.setFillColor(i);
            firstRow.setBorderColor(-1);
            firstRow.setFontColor(-1);
            this.tableStyleMedium8_14.setFirstRow(firstRow);
            this.tableStyleMedium8_14.setFirstCol(firstRow);
            this.tableStyleMedium8_14.setLastCol(firstRow);
            this.tableStyleMedium8_14.setLastRow(firstRow);
            int colorWithTint = ColorUtil.instance().getColorWithTint(i, 0.6000000238418579d);
            SSTableCellStyle band1H = this.tableStyleMedium8_14.getBand1H();
            band1H.setFillColor(colorWithTint);
            band1H.setBorderColor(-1);
            this.tableStyleMedium8_14.setBand1H(band1H);
            this.tableStyleMedium8_14.setBand1V(band1H);
            int colorWithTint2 = ColorUtil.instance().getColorWithTint(i, 0.800000011920929d);
            SSTableCellStyle band2H = this.tableStyleMedium8_14.getBand2H();
            band2H.setFillColor(colorWithTint2);
            band2H.setBorderColor(-1);
            this.tableStyleMedium8_14.setBand2H(band2H);
            this.tableStyleMedium8_14.setBand2V(band2H);
        }
        return this.tableStyleMedium8_14;
    }

    private SSTableStyle getTableStyleMedium15_21(int i) {
        SSTableStyle sSTableStyle = this.tableStyleMedium15_21;
        if (sSTableStyle == null) {
            this.tableStyleMedium15_21 = new SSTableStyle();
            SSTableCellStyle sSTableCellStyle = new SSTableCellStyle(i);
            sSTableCellStyle.setFontColor(-1);
            this.tableStyleMedium15_21.setFirstRow(sSTableCellStyle);
            this.tableStyleMedium15_21.setFirstCol(sSTableCellStyle);
            this.tableStyleMedium15_21.setLastCol(sSTableCellStyle);
            SSTableCellStyle sSTableCellStyle2 = new SSTableCellStyle(-2565928);
            this.tableStyleMedium15_21.setBand1H(sSTableCellStyle2);
            this.tableStyleMedium15_21.setBand1V(sSTableCellStyle2);
            SSTableCellStyle sSTableCellStyle3 = new SSTableCellStyle(-1);
            this.tableStyleMedium15_21.setBand2H(sSTableCellStyle3);
            this.tableStyleMedium15_21.setBand2V(sSTableCellStyle3);
        } else {
            SSTableCellStyle firstRow = sSTableStyle.getFirstRow();
            firstRow.setFillColor(i);
            this.tableStyleMedium15_21.setFirstCol(firstRow);
            this.tableStyleMedium15_21.setLastCol(firstRow);
        }
        return this.tableStyleMedium15_21;
    }

    private SSTableStyle getTableStyleMedium22_28(int i) {
        if (this.tableStyleMedium22_28 == null) {
            this.tableStyleMedium22_28 = new SSTableStyle();
            SSTableCellStyle sSTableCellStyle = new SSTableCellStyle(ColorUtil.instance().getColorWithTint(i, 0.6000000238418579d));
            sSTableCellStyle.setBorderColor(i);
            this.tableStyleMedium22_28.setBand1H(sSTableCellStyle);
            this.tableStyleMedium22_28.setBand1V(sSTableCellStyle);
            SSTableCellStyle sSTableCellStyle2 = new SSTableCellStyle(ColorUtil.instance().getColorWithTint(i, 0.800000011920929d));
            sSTableCellStyle2.setBorderColor(i);
            this.tableStyleMedium22_28.setFirstRow(sSTableCellStyle2);
            this.tableStyleMedium22_28.setLastRow(sSTableCellStyle2);
            this.tableStyleMedium22_28.setBand2H(sSTableCellStyle2);
            this.tableStyleMedium22_28.setBand2V(sSTableCellStyle2);
        } else {
            int colorWithTint = ColorUtil.instance().getColorWithTint(i, 0.6000000238418579d);
            SSTableCellStyle band1H = this.tableStyleMedium22_28.getBand1H();
            band1H.setFillColor(colorWithTint);
            band1H.setBorderColor(i);
            this.tableStyleMedium22_28.setBand1H(band1H);
            this.tableStyleMedium22_28.setBand1V(band1H);
            int colorWithTint2 = ColorUtil.instance().getColorWithTint(i, 0.800000011920929d);
            SSTableCellStyle firstRow = this.tableStyleMedium22_28.getFirstRow();
            firstRow.setFillColor(colorWithTint2);
            firstRow.setBorderColor(i);
            this.tableStyleMedium22_28.setFirstRow(firstRow);
            this.tableStyleMedium22_28.setLastRow(firstRow);
            this.tableStyleMedium22_28.setBand2H(firstRow);
            this.tableStyleMedium22_28.setBand2V(firstRow);
        }
        return this.tableStyleMedium22_28;
    }

    private SSTableStyle getTableStyleDark1_7(int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (this.tableStyleDark1_7 == null) {
            this.tableStyleDark1_7 = new SSTableStyle();
            SSTableCellStyle sSTableCellStyle = new SSTableCellStyle(-16777216);
            sSTableCellStyle.setFontColor(-1);
            sSTableCellStyle.setBorderColor(-1);
            this.tableStyleDark1_7.setFirstRow(sSTableCellStyle);
            int i6 = i & ViewCompat.MEASURED_SIZE_MASK;
            if (i6 == 0) {
                i4 = ColorUtil.instance().getColorWithTint(i, 0.15000000596046448d);
            } else {
                i4 = ColorUtil.instance().getColorWithTint(i, -0.5d);
            }
            SSTableCellStyle sSTableCellStyle2 = new SSTableCellStyle(i4);
            sSTableCellStyle2.setFontColor(-1);
            sSTableCellStyle2.setBorderColor(-1);
            this.tableStyleDark1_7.setLastRow(sSTableCellStyle2);
            if (i6 == 0) {
                i5 = ColorUtil.instance().getColorWithTint(i, 0.25d);
            } else {
                i5 = ColorUtil.instance().getColorWithTint(i, -0.25d);
            }
            SSTableCellStyle sSTableCellStyle3 = new SSTableCellStyle(i5);
            sSTableCellStyle3.setFontColor(-1);
            this.tableStyleDark1_7.setBand1H(sSTableCellStyle3);
            this.tableStyleDark1_7.setBand1V(sSTableCellStyle3);
            if (i6 == 0) {
                i = ColorUtil.instance().getColorWithTint(i, 0.5d);
            }
            SSTableCellStyle sSTableCellStyle4 = new SSTableCellStyle(i);
            sSTableCellStyle4.setFontColor(-1);
            this.tableStyleDark1_7.setBand2H(sSTableCellStyle4);
            this.tableStyleDark1_7.setBand2V(sSTableCellStyle4);
        } else {
            int i7 = i & ViewCompat.MEASURED_SIZE_MASK;
            if (i7 == 0) {
                i2 = ColorUtil.instance().getColorWithTint(i, 0.15000000596046448d);
            } else {
                i2 = ColorUtil.instance().getColorWithTint(i, -0.5d);
            }
            SSTableCellStyle lastRow = this.tableStyleDark1_7.getLastRow();
            lastRow.setFillColor(i2);
            this.tableStyleDark1_7.setLastRow(lastRow);
            if (i7 == 0) {
                i3 = ColorUtil.instance().getColorWithTint(i, 0.25d);
            } else {
                i3 = ColorUtil.instance().getColorWithTint(i, -0.25d);
            }
            SSTableCellStyle band1H = this.tableStyleDark1_7.getBand1H();
            band1H.setFillColor(i3);
            this.tableStyleDark1_7.setBand1H(band1H);
            this.tableStyleDark1_7.setBand1V(band1H);
            if (i7 == 0) {
                i = ColorUtil.instance().getColorWithTint(i, 0.5d);
            }
            SSTableCellStyle band2H = this.tableStyleDark1_7.getBand2H();
            band2H.setFillColor(i);
            this.tableStyleDark1_7.setBand2H(band2H);
            this.tableStyleDark1_7.setBand2V(band2H);
        }
        return this.tableStyleDark1_7;
    }

    private SSTableStyle getTableStyleDark8() {
        if (this.tableStyleDark8 == null) {
            this.tableStyleDark8 = new SSTableStyle();
            SSTableCellStyle sSTableCellStyle = new SSTableCellStyle(-16777216);
            sSTableCellStyle.setFontColor(-1);
            this.tableStyleDark8.setFirstRow(sSTableCellStyle);
            SSTableCellStyle sSTableCellStyle2 = new SSTableCellStyle(-5921371);
            this.tableStyleDark8.setBand1H(sSTableCellStyle2);
            this.tableStyleDark8.setBand1V(sSTableCellStyle2);
            SSTableCellStyle sSTableCellStyle3 = new SSTableCellStyle(-2565928);
            this.tableStyleDark8.setBand2H(sSTableCellStyle3);
            this.tableStyleDark8.setBand2V(sSTableCellStyle3);
        }
        return this.tableStyleDark8;
    }

    private SSTableStyle getTableStyleDark9_11(int i, int i2) {
        SSTableStyle sSTableStyle = this.tableStyleDark9_11;
        if (sSTableStyle == null) {
            this.tableStyleDark9_11 = new SSTableStyle();
            SSTableCellStyle sSTableCellStyle = new SSTableCellStyle(i);
            sSTableCellStyle.setFontColor(-1);
            this.tableStyleDark9_11.setFirstRow(sSTableCellStyle);
            SSTableCellStyle sSTableCellStyle2 = new SSTableCellStyle(ColorUtil.instance().getColorWithTint(i2, 0.6000000238418579d));
            this.tableStyleDark9_11.setBand1H(sSTableCellStyle2);
            this.tableStyleDark9_11.setBand1V(sSTableCellStyle2);
            SSTableCellStyle sSTableCellStyle3 = new SSTableCellStyle(ColorUtil.instance().getColorWithTint(i2, 0.800000011920929d));
            this.tableStyleDark9_11.setBand2H(sSTableCellStyle3);
            this.tableStyleDark9_11.setBand2V(sSTableCellStyle3);
        } else {
            SSTableCellStyle firstRow = sSTableStyle.getFirstRow();
            firstRow.setFillColor(i);
            this.tableStyleDark9_11.setFirstRow(firstRow);
            int colorWithTint = ColorUtil.instance().getColorWithTint(i2, 0.6000000238418579d);
            SSTableCellStyle band1H = this.tableStyleDark9_11.getBand1H();
            band1H.setFillColor(colorWithTint);
            this.tableStyleDark9_11.setBand1H(band1H);
            this.tableStyleDark9_11.setBand1V(band1H);
            int colorWithTint2 = ColorUtil.instance().getColorWithTint(i2, 0.800000011920929d);
            SSTableCellStyle band2H = this.tableStyleDark9_11.getBand2H();
            band2H.setFillColor(colorWithTint2);
            this.tableStyleDark9_11.setBand2H(band2H);
            this.tableStyleDark9_11.setBand2V(band2H);
        }
        return this.tableStyleDark9_11;
    }
}
