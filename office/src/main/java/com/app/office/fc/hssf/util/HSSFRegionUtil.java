package com.app.office.fc.hssf.util;

import com.app.office.fc.hssf.usermodel.HSSFSheet;
import com.app.office.fc.hssf.usermodel.HSSFWorkbook;
import com.app.office.fc.ss.util.HSSFCellRangeAddress;
import com.app.office.fc.ss.util.Region;
import com.app.office.fc.ss.util.RegionUtil;

public final class HSSFRegionUtil {
    private HSSFRegionUtil() {
    }

    private static HSSFCellRangeAddress toCRA(Region region) {
        return Region.convertToCellRangeAddress(region);
    }

    public static void setBorderLeft(short s, Region region, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        setBorderLeft((int) s, toCRA(region), hSSFSheet, hSSFWorkbook);
    }

    public static void setBorderLeft(int i, HSSFCellRangeAddress hSSFCellRangeAddress, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        RegionUtil.setBorderLeft(i, hSSFCellRangeAddress, hSSFSheet, hSSFWorkbook);
    }

    public static void setLeftBorderColor(short s, Region region, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        setLeftBorderColor((int) s, toCRA(region), hSSFSheet, hSSFWorkbook);
    }

    public static void setLeftBorderColor(int i, HSSFCellRangeAddress hSSFCellRangeAddress, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        RegionUtil.setLeftBorderColor(i, hSSFCellRangeAddress, hSSFSheet, hSSFWorkbook);
    }

    public static void setBorderRight(short s, Region region, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        setBorderRight((int) s, toCRA(region), hSSFSheet, hSSFWorkbook);
    }

    public static void setBorderRight(int i, HSSFCellRangeAddress hSSFCellRangeAddress, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        RegionUtil.setBorderRight(i, hSSFCellRangeAddress, hSSFSheet, hSSFWorkbook);
    }

    public static void setRightBorderColor(short s, Region region, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        setRightBorderColor((int) s, toCRA(region), hSSFSheet, hSSFWorkbook);
    }

    public static void setRightBorderColor(int i, HSSFCellRangeAddress hSSFCellRangeAddress, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        RegionUtil.setRightBorderColor(i, hSSFCellRangeAddress, hSSFSheet, hSSFWorkbook);
    }

    public static void setBorderBottom(short s, Region region, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        setBorderBottom((int) s, toCRA(region), hSSFSheet, hSSFWorkbook);
    }

    public static void setBorderBottom(int i, HSSFCellRangeAddress hSSFCellRangeAddress, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        RegionUtil.setBorderBottom(i, hSSFCellRangeAddress, hSSFSheet, hSSFWorkbook);
    }

    public static void setBottomBorderColor(short s, Region region, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        setBottomBorderColor((int) s, toCRA(region), hSSFSheet, hSSFWorkbook);
    }

    public static void setBottomBorderColor(int i, HSSFCellRangeAddress hSSFCellRangeAddress, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        RegionUtil.setBottomBorderColor(i, hSSFCellRangeAddress, hSSFSheet, hSSFWorkbook);
    }

    public static void setBorderTop(short s, Region region, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        setBorderTop((int) s, toCRA(region), hSSFSheet, hSSFWorkbook);
    }

    public static void setBorderTop(int i, HSSFCellRangeAddress hSSFCellRangeAddress, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        RegionUtil.setBorderTop(i, hSSFCellRangeAddress, hSSFSheet, hSSFWorkbook);
    }

    public static void setTopBorderColor(short s, Region region, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        setTopBorderColor((int) s, toCRA(region), hSSFSheet, hSSFWorkbook);
    }

    public static void setTopBorderColor(int i, HSSFCellRangeAddress hSSFCellRangeAddress, HSSFSheet hSSFSheet, HSSFWorkbook hSSFWorkbook) {
        RegionUtil.setTopBorderColor(i, hSSFCellRangeAddress, hSSFSheet, hSSFWorkbook);
    }
}
