package com.app.office.fc.ppt.bulletnumber;

import com.app.office.common.bulletnumber.ListKit;
import com.app.office.fc.dom4j.Element;
import com.app.office.system.IControl;
import java.util.HashMap;
import java.util.Map;

public class BulletNumberManage {
    private static BulletNumberManage kit;
    private Map<String, Integer> bulletIDs = new HashMap();
    private Map<Integer, Integer> lvlFmt = new HashMap();
    private Map<Integer, Integer> lvlNum = new HashMap();
    private Map<Integer, Integer> lvlStartAt = new HashMap();
    private Map<Integer, Integer> styleBulletIDs = new HashMap();

    private int convertedNumberFormat(int i) {
        if (i != 18) {
            if (i == 38) {
                return 39;
            }
            switch (i) {
                case 0:
                    return 4;
                case 1:
                    return 3;
                case 2:
                    return 6;
                case 4:
                    return 13;
                case 5:
                    return 8;
                case 6:
                    return 2;
                case 7:
                    return 1;
                case 8:
                    return 15;
                case 9:
                    return 10;
                case 10:
                    return 14;
                case 11:
                    return 9;
                case 12:
                    return 11;
                case 13:
                    break;
                case 14:
                    return 12;
                case 15:
                    return 7;
                default:
                    return 0;
            }
        }
        return 5;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002f, code lost:
        if (r3 != 8211) goto L_0x0035;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private char converterNumberChar(int r3) {
        /*
            r2 = this;
            r0 = 9679(0x25cf, float:1.3563E-41)
            r1 = 8226(0x2022, float:1.1527E-41)
            if (r3 == r1) goto L_0x0035
            r1 = 108(0x6c, float:1.51E-43)
            if (r3 == r1) goto L_0x0035
            r1 = 112(0x70, float:1.57E-43)
            if (r3 != r1) goto L_0x000f
            goto L_0x0035
        L_0x000f:
            r1 = 110(0x6e, float:1.54E-43)
            if (r3 == r1) goto L_0x0032
            r1 = 167(0xa7, float:2.34E-43)
            if (r3 != r1) goto L_0x0018
            goto L_0x0032
        L_0x0018:
            r1 = 117(0x75, float:1.64E-43)
            if (r3 != r1) goto L_0x001f
            r3 = 9670(0x25c6, float:1.355E-41)
            goto L_0x0037
        L_0x001f:
            r1 = 252(0xfc, float:3.53E-43)
            if (r3 != r1) goto L_0x0026
            r3 = 8730(0x221a, float:1.2233E-41)
            goto L_0x0037
        L_0x0026:
            r1 = 216(0xd8, float:3.03E-43)
            if (r3 != r1) goto L_0x002d
            r3 = 9733(0x2605, float:1.3639E-41)
            goto L_0x0037
        L_0x002d:
            r1 = 8211(0x2013, float:1.1506E-41)
            if (r3 == r1) goto L_0x0037
            goto L_0x0035
        L_0x0032:
            r3 = 9632(0x25a0, float:1.3497E-41)
            goto L_0x0037
        L_0x0035:
            r3 = 9679(0x25cf, float:1.3563E-41)
        L_0x0037:
            char r3 = (char) r3
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.bulletnumber.BulletNumberManage.converterNumberChar(int):char");
    }

    public static BulletNumberManage instance() {
        if (kit == null) {
            kit = new BulletNumberManage();
        }
        return kit;
    }

    public int getBulletID(int i) {
        Integer num = this.styleBulletIDs.get(Integer.valueOf(i));
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    public int addBulletNumber(IControl iControl, int i, Element element) {
        Integer num = this.styleBulletIDs.get(Integer.valueOf(i));
        if (num != null) {
            return num.intValue();
        }
        String bulletText = getBulletText(element);
        if (bulletText != null) {
            Integer num2 = this.bulletIDs.get(bulletText);
            if (num2 != null) {
                if (i > 0) {
                    this.styleBulletIDs.put(Integer.valueOf(i), num2);
                }
                return num2.intValue();
            }
            Integer valueOf = Integer.valueOf(iControl.getSysKit().getPGBulletText().addBulletText(bulletText));
            this.bulletIDs.put(bulletText, valueOf);
            if (i > 0) {
                this.styleBulletIDs.put(Integer.valueOf(i), valueOf);
            }
            return valueOf.intValue();
        } else if (element == null || element.element("buNone") == null) {
            return -1;
        } else {
            this.styleBulletIDs.put(Integer.valueOf(i), -2);
            return -1;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0015, code lost:
        r1 = r5.attributeValue("lvl");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getBulletText(com.app.office.fc.dom4j.Element r5) {
        /*
            r4 = this;
            if (r5 == 0) goto L_0x010f
            java.lang.String r0 = "buNone"
            com.app.office.fc.dom4j.Element r0 = r5.element((java.lang.String) r0)
            if (r0 != 0) goto L_0x010f
            r0 = 0
            if (r5 == 0) goto L_0x0026
            java.lang.String r1 = "lvl"
            com.app.office.fc.dom4j.Attribute r2 = r5.attribute((java.lang.String) r1)
            if (r2 == 0) goto L_0x0026
            java.lang.String r1 = r5.attributeValue((java.lang.String) r1)
            if (r1 == 0) goto L_0x0026
            int r2 = r1.length()
            if (r2 <= 0) goto L_0x0026
            int r1 = java.lang.Integer.parseInt(r1)
            goto L_0x0027
        L_0x0026:
            r1 = 0
        L_0x0027:
            java.lang.String r2 = "buAutoNum"
            com.app.office.fc.dom4j.Element r2 = r5.element((java.lang.String) r2)
            if (r2 == 0) goto L_0x0057
            r5 = 1
            java.lang.String r0 = "startAt"
            com.app.office.fc.dom4j.Attribute r3 = r2.attribute((java.lang.String) r0)
            if (r3 == 0) goto L_0x0048
            java.lang.String r0 = r2.attributeValue((java.lang.String) r0)
            if (r0 == 0) goto L_0x0048
            int r3 = r0.length()
            if (r3 <= 0) goto L_0x0048
            int r5 = java.lang.Integer.parseInt(r0)
        L_0x0048:
            java.lang.String r0 = "type"
            java.lang.String r0 = r2.attributeValue((java.lang.String) r0)
            int r0 = r4.convertedNumberFormat((java.lang.String) r0)
            java.lang.String r5 = r4.getText(r1, r0, r5)
            return r5
        L_0x0057:
            java.lang.String r2 = "buBlip"
            com.app.office.fc.dom4j.Element r2 = r5.element((java.lang.String) r2)
            if (r2 == 0) goto L_0x00b2
            java.lang.String r5 = "blip"
            com.app.office.fc.dom4j.Element r0 = r2.element((java.lang.String) r5)
            if (r0 == 0) goto L_0x010f
            com.app.office.fc.dom4j.Element r5 = r2.element((java.lang.String) r5)
            java.lang.String r0 = "embed"
            java.lang.String r5 = r5.attributeValue((java.lang.String) r0)
            if (r5 == 0) goto L_0x010f
            r5 = 108(0x6c, float:1.51E-43)
            char r5 = r4.converterNumberChar(r5)
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = r4.lvlFmt
            java.lang.Integer r2 = java.lang.Integer.valueOf(r1)
            java.lang.Object r0 = r0.get(r2)
            java.lang.Integer r0 = (java.lang.Integer) r0
            if (r0 == 0) goto L_0x008d
            int r2 = r0.intValue()
            if (r2 == r5) goto L_0x00ad
        L_0x008d:
            if (r0 == 0) goto L_0x00a0
            if (r1 != 0) goto L_0x00a0
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = r4.lvlFmt
            r0.clear()
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = r4.lvlStartAt
            r0.clear()
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = r4.lvlNum
            r0.clear()
        L_0x00a0:
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = r4.lvlFmt
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r5)
            r0.put(r1, r2)
        L_0x00ad:
            java.lang.String r5 = java.lang.String.valueOf(r5)
            return r5
        L_0x00b2:
            java.lang.String r2 = "buChar"
            com.app.office.fc.dom4j.Element r5 = r5.element((java.lang.String) r2)
            if (r5 == 0) goto L_0x010f
            java.lang.String r2 = "char"
            com.app.office.fc.dom4j.Attribute r3 = r5.attribute((java.lang.String) r2)
            if (r3 == 0) goto L_0x010f
            java.lang.String r5 = r5.attributeValue((java.lang.String) r2)
            if (r5 == 0) goto L_0x010f
            int r2 = r5.length()
            if (r2 <= 0) goto L_0x010f
            char r5 = r5.charAt(r0)
            char r5 = r4.converterNumberChar(r5)
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = r4.lvlFmt
            java.lang.Integer r2 = java.lang.Integer.valueOf(r1)
            java.lang.Object r0 = r0.get(r2)
            java.lang.Integer r0 = (java.lang.Integer) r0
            if (r0 == 0) goto L_0x00ea
            int r2 = r0.intValue()
            if (r2 == r5) goto L_0x010a
        L_0x00ea:
            if (r0 == 0) goto L_0x00fd
            if (r1 != 0) goto L_0x00fd
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = r4.lvlFmt
            r0.clear()
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = r4.lvlStartAt
            r0.clear()
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = r4.lvlNum
            r0.clear()
        L_0x00fd:
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = r4.lvlFmt
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r5)
            r0.put(r1, r2)
        L_0x010a:
            java.lang.String r5 = java.lang.String.valueOf(r5)
            return r5
        L_0x010f:
            r5 = 0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.bulletnumber.BulletNumberManage.getBulletText(com.app.office.fc.dom4j.Element):java.lang.String");
    }

    private String getText(int i, int i2, int i3) {
        StringBuffer stringBuffer = new StringBuffer();
        Integer num = this.lvlFmt.get(Integer.valueOf(i));
        int i4 = 1;
        if (num == null || num.intValue() != i2) {
            if (num != null && i == 0) {
                this.lvlFmt.clear();
                this.lvlStartAt.clear();
                this.lvlNum.clear();
            }
            this.lvlFmt.put(Integer.valueOf(i), Integer.valueOf(i2));
            this.lvlStartAt.put(Integer.valueOf(i), Integer.valueOf(i3));
            this.lvlNum.put(Integer.valueOf(i), Integer.valueOf(i3));
        } else {
            Integer num2 = this.lvlStartAt.get(Integer.valueOf(i));
            if (num2 == null || num2.intValue() != i3) {
                this.lvlStartAt.put(Integer.valueOf(i), Integer.valueOf(i3));
                this.lvlNum.put(Integer.valueOf(i), Integer.valueOf(i3));
            } else {
                i3 = this.lvlNum.get(Integer.valueOf(i)).intValue() + 1;
                this.lvlNum.put(Integer.valueOf(i), Integer.valueOf(i3));
            }
        }
        if (i2 == 5 || i2 == 6 || i2 == 11) {
            i4 = 0;
        } else if (!(i2 == 7 || i2 == 12)) {
            i4 = (i2 == 8 || i2 == 13) ? 2 : (i2 == 9 || i2 == 14) ? 3 : (i2 == 10 || i2 == 15) ? 4 : i2;
        }
        if (i2 >= 11 && i2 <= 15) {
            stringBuffer.append("(");
        }
        stringBuffer.append(ListKit.instance().getNumberStr(i3, i4));
        if (i2 >= 6 && i2 <= 15) {
            stringBuffer.append(")");
        } else if (i2 != 5) {
            stringBuffer.append(".");
        }
        return stringBuffer.toString();
    }

    public int addBulletNumber(IControl iControl, int i, int i2, int i3, char c) {
        String valueOf = String.valueOf(converterNumberChar(c));
        Integer num = this.bulletIDs.get(valueOf);
        if (num != null) {
            return num.intValue();
        }
        Integer valueOf2 = Integer.valueOf(iControl.getSysKit().getPGBulletText().addBulletText(valueOf));
        this.bulletIDs.put(valueOf, valueOf2);
        return valueOf2.intValue();
    }

    private int convertedNumberFormat(String str) {
        if ("arabicPeriod".equalsIgnoreCase(str)) {
            return 0;
        }
        if ("romanUcPeriod".equalsIgnoreCase(str)) {
            return 1;
        }
        if ("romanLcPeriod".equalsIgnoreCase(str)) {
            return 2;
        }
        if ("alphaUcPeriod".equalsIgnoreCase(str)) {
            return 3;
        }
        if ("alphaLcPeriod".equalsIgnoreCase(str)) {
            return 4;
        }
        if ("arabicPlain".equalsIgnoreCase(str) || "circleNumDbPlain".equalsIgnoreCase(str)) {
            return 5;
        }
        if ("arabicParenR".equalsIgnoreCase(str)) {
            return 6;
        }
        if ("romanUcParenR".equalsIgnoreCase(str)) {
            return 7;
        }
        if ("romanLcParenR".equalsIgnoreCase(str)) {
            return 8;
        }
        if ("alphaUcParenR".equalsIgnoreCase(str)) {
            return 9;
        }
        if ("alphaLcParenR".equalsIgnoreCase(str)) {
            return 10;
        }
        if ("arabicParenBoth".equalsIgnoreCase(str)) {
            return 11;
        }
        if ("romanUcParentBoth".equalsIgnoreCase(str)) {
            return 12;
        }
        if ("romanLcParenBoth".equalsIgnoreCase(str)) {
            return 13;
        }
        if ("alphaUcParenBoth".equalsIgnoreCase(str)) {
            return 14;
        }
        if ("alphaLcParenBoth".equalsIgnoreCase(str)) {
            return 15;
        }
        if ("ea1JpnChsDbPeriod".equalsIgnoreCase(str)) {
            return 39;
        }
        return 0;
    }

    public void clearData() {
        Map<Integer, Integer> map = this.lvlFmt;
        if (map != null) {
            map.clear();
        }
        Map<Integer, Integer> map2 = this.lvlStartAt;
        if (map2 != null) {
            map2.clear();
        }
        Map<Integer, Integer> map3 = this.lvlNum;
        if (map3 != null) {
            map3.clear();
        }
    }

    public void dispose() {
        Map<Integer, Integer> map = this.lvlFmt;
        if (map != null) {
            map.clear();
            this.lvlFmt = null;
        }
        Map<Integer, Integer> map2 = this.lvlStartAt;
        if (map2 != null) {
            map2.clear();
            this.lvlStartAt = null;
        }
        Map<Integer, Integer> map3 = this.lvlNum;
        if (map3 != null) {
            map3.clear();
            this.lvlNum = null;
        }
        Map<Integer, Integer> map4 = this.styleBulletIDs;
        if (map4 != null) {
            map4.clear();
            this.styleBulletIDs = null;
        }
        Map<String, Integer> map5 = this.bulletIDs;
        if (map5 != null) {
            map5.clear();
            this.bulletIDs = null;
        }
        kit = null;
    }
}
