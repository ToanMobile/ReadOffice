package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.record.ExtendedFormatRecord;
import com.app.office.fc.hssf.record.FontRecord;
import com.app.office.fc.hssf.record.common.UnicodeString;
import com.app.office.fc.ss.usermodel.ICell;
import com.app.office.fc.ss.usermodel.IRow;
import java.util.HashSet;
import java.util.Iterator;

public class HSSFOptimiser {
    public static void optimiseFonts(HSSFWorkbook hSSFWorkbook) {
        int numberOfFontRecords = hSSFWorkbook.getWorkbook().getNumberOfFontRecords() + 1;
        short[] sArr = new short[numberOfFontRecords];
        boolean[] zArr = new boolean[numberOfFontRecords];
        for (int i = 0; i < numberOfFontRecords; i++) {
            sArr[i] = (short) i;
            zArr[i] = false;
        }
        FontRecord[] fontRecordArr = new FontRecord[numberOfFontRecords];
        for (int i2 = 0; i2 < numberOfFontRecords; i2++) {
            if (i2 != 4) {
                fontRecordArr[i2] = hSSFWorkbook.getWorkbook().getFontRecordAt(i2);
            }
        }
        for (int i3 = 5; i3 < numberOfFontRecords; i3++) {
            int i4 = -1;
            for (int i5 = 0; i5 < i3 && i4 == -1; i5++) {
                if (i5 != 4 && hSSFWorkbook.getWorkbook().getFontRecordAt(i5).sameProperties(fontRecordArr[i3])) {
                    i4 = i5;
                }
            }
            if (i4 != -1) {
                sArr[i3] = (short) i4;
                zArr[i3] = true;
            }
        }
        for (int i6 = 5; i6 < numberOfFontRecords; i6++) {
            short s = sArr[i6];
            short s2 = s;
            for (int i7 = 0; i7 < s; i7++) {
                if (zArr[i7]) {
                    s2 = (short) (s2 - 1);
                }
            }
            sArr[i6] = s2;
        }
        for (int i8 = 5; i8 < numberOfFontRecords; i8++) {
            if (zArr[i8]) {
                hSSFWorkbook.getWorkbook().removeFontRecord(fontRecordArr[i8]);
            }
        }
        hSSFWorkbook.resetFontCache();
        for (int i9 = 0; i9 < hSSFWorkbook.getWorkbook().getNumExFormats(); i9++) {
            ExtendedFormatRecord exFormatAt = hSSFWorkbook.getWorkbook().getExFormatAt(i9);
            exFormatAt.setFontIndex(sArr[exFormatAt.getFontIndex()]);
        }
        HashSet hashSet = new HashSet();
        for (int i10 = 0; i10 < hSSFWorkbook.getNumberOfSheets(); i10++) {
            Iterator<IRow> rowIterator = hSSFWorkbook.getSheetAt(i10).rowIterator();
            while (rowIterator.hasNext()) {
                Iterator<ICell> cellIterator = ((HSSFRow) rowIterator.next()).cellIterator();
                while (cellIterator.hasNext()) {
                    HSSFCell hSSFCell = (HSSFCell) cellIterator.next();
                    if (hSSFCell.getCellType() == 1) {
                        UnicodeString rawUnicodeString = hSSFCell.getRichStringCellValue().getRawUnicodeString();
                        if (!hashSet.contains(rawUnicodeString)) {
                            for (short s3 = 5; s3 < numberOfFontRecords; s3 = (short) (s3 + 1)) {
                                if (s3 != sArr[s3]) {
                                    rawUnicodeString.swapFontUse(s3, sArr[s3]);
                                }
                            }
                            hashSet.add(rawUnicodeString);
                        }
                    }
                }
            }
        }
    }

    public static void optimiseCellStyles(HSSFWorkbook hSSFWorkbook) {
        int numExFormats = hSSFWorkbook.getWorkbook().getNumExFormats();
        short[] sArr = new short[numExFormats];
        boolean[] zArr = new boolean[numExFormats];
        for (int i = 0; i < numExFormats; i++) {
            sArr[i] = (short) i;
            zArr[i] = false;
        }
        ExtendedFormatRecord[] extendedFormatRecordArr = new ExtendedFormatRecord[numExFormats];
        for (int i2 = 0; i2 < numExFormats; i2++) {
            extendedFormatRecordArr[i2] = hSSFWorkbook.getWorkbook().getExFormatAt(i2);
        }
        for (int i3 = 21; i3 < numExFormats; i3++) {
            int i4 = -1;
            for (int i5 = 0; i5 < i3 && i4 == -1; i5++) {
                if (hSSFWorkbook.getWorkbook().getExFormatAt(i5).equals(extendedFormatRecordArr[i3])) {
                    i4 = i5;
                }
            }
            if (i4 != -1) {
                sArr[i3] = (short) i4;
                zArr[i3] = true;
            }
        }
        for (int i6 = 21; i6 < numExFormats; i6++) {
            short s = sArr[i6];
            short s2 = s;
            for (int i7 = 0; i7 < s; i7++) {
                if (zArr[i7]) {
                    s2 = (short) (s2 - 1);
                }
            }
            sArr[i6] = s2;
        }
        for (int i8 = 21; i8 < numExFormats; i8++) {
            if (zArr[i8]) {
                hSSFWorkbook.getWorkbook().removeExFormatRecord(extendedFormatRecordArr[i8]);
            }
        }
        for (int i9 = 0; i9 < hSSFWorkbook.getNumberOfSheets(); i9++) {
            Iterator<IRow> rowIterator = hSSFWorkbook.getSheetAt(i9).rowIterator();
            while (rowIterator.hasNext()) {
                Iterator<ICell> cellIterator = ((HSSFRow) rowIterator.next()).cellIterator();
                while (cellIterator.hasNext()) {
                    HSSFCell hSSFCell = (HSSFCell) cellIterator.next();
                    hSSFCell.setCellStyle(hSSFWorkbook.getCellStyleAt(sArr[hSSFCell.getCellValueRecord().getXFIndex()]));
                }
            }
        }
    }
}
