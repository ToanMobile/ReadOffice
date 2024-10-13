package com.app.office.fc.xls.Reader;

import com.app.office.constant.SchemeClrConstant;
import com.app.office.ss.model.baseModel.Workbook;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemeColorUtil {
    private static List<String> schemeClrName;
    private static Map<String, Integer> schemeColor;

    public static int getThemeColor(Workbook workbook, int i) {
        init(workbook);
        if (i < 0 || i >= schemeClrName.size()) {
            return -1;
        }
        return workbook.getSchemeColor(schemeClrName.get(i));
    }

    public static Map<String, Integer> getSchemeColor(Workbook workbook) {
        init(workbook);
        return schemeColor;
    }

    private static void init(Workbook workbook) {
        if (schemeColor == null) {
            ArrayList arrayList = new ArrayList();
            schemeClrName = arrayList;
            arrayList.add(SchemeClrConstant.SCHEME_BG1);
            schemeClrName.add(SchemeClrConstant.SCHEME_TX1);
            schemeClrName.add(SchemeClrConstant.SCHEME_BG2);
            schemeClrName.add(SchemeClrConstant.SCHEME_TX2);
            schemeClrName.add(SchemeClrConstant.SCHEME_ACCENT1);
            schemeClrName.add(SchemeClrConstant.SCHEME_ACCENT2);
            schemeClrName.add(SchemeClrConstant.SCHEME_ACCENT3);
            schemeClrName.add(SchemeClrConstant.SCHEME_ACCENT4);
            schemeClrName.add(SchemeClrConstant.SCHEME_ACCENT5);
            schemeClrName.add(SchemeClrConstant.SCHEME_ACCENT6);
            schemeClrName.add(SchemeClrConstant.SCHEME_HLINK);
            schemeClrName.add(SchemeClrConstant.SCHEME_FOLHLINK);
            schemeClrName.add(SchemeClrConstant.SCHEME_DK1);
            schemeClrName.add(SchemeClrConstant.SCHEME_LT1);
            schemeClrName.add(SchemeClrConstant.SCHEME_DK2);
            schemeClrName.add(SchemeClrConstant.SCHEME_LT2);
            schemeColor = new HashMap();
        }
        schemeColor.clear();
        for (String next : schemeClrName) {
            schemeColor.put(next, Integer.valueOf(workbook.getSchemeColor(next)));
        }
    }
}
