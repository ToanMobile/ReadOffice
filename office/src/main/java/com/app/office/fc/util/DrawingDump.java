package com.app.office.fc.util;

import com.app.office.fc.hssf.usermodel.HSSFWorkbook;
import com.app.office.fc.poifs.filesystem.POIFSFileSystem;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

public class DrawingDump {
    public static void main(String[] strArr) throws IOException {
        HSSFWorkbook hSSFWorkbook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(strArr[0])));
        System.out.println("Drawing group:");
        hSSFWorkbook.dumpDrawingGroupRecords(true);
        for (int i = 1; i <= hSSFWorkbook.getNumberOfSheets(); i++) {
            PrintStream printStream = System.out;
            printStream.println("Sheet " + i + ":");
            hSSFWorkbook.getSheetAt(i + -1).dumpDrawingRecords(true);
        }
    }
}
