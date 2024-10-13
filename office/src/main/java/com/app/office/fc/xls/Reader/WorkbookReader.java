package com.app.office.fc.xls.Reader;

import android.os.Message;
import com.onesignal.outcomes.OSOutcomeConstants;
import com.app.office.fc.dom4j.Document;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.ElementHandler;
import com.app.office.fc.dom4j.ElementPath;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.PackageRelationship;
import com.app.office.fc.openxml4j.opc.PackageRelationshipCollection;
import com.app.office.fc.openxml4j.opc.PackageRelationshipTypes;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.fc.xls.SSReader;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.system.AbortReaderError;
import com.app.office.system.IControl;
import com.app.office.system.IReader;
import com.app.office.system.ReaderHandler;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WorkbookReader {
    private static final int WINDOWWIDTH = 2;
    private static WorkbookReader reader = new WorkbookReader();
    /* access modifiers changed from: private */
    public Workbook book;
    private PackageRelationshipCollection chartsheetRelCollection;
    /* access modifiers changed from: private */
    public SSReader iReader;
    /* access modifiers changed from: private */
    public Map<Integer, String> sheetIndexList;
    /* access modifiers changed from: private */
    public Map<String, String> sheetNameList;
    /* access modifiers changed from: private */
    public int tempIndex;
    private PackageRelationshipCollection worksheetRelCollection;
    private ZipPackage zipPackage;

    static /* synthetic */ int access$208(WorkbookReader workbookReader) {
        int i = workbookReader.tempIndex;
        workbookReader.tempIndex = i + 1;
        return i;
    }

    class SheetThread extends Thread {
        private IControl control;
        private WorkbookReader reader;
        private int sheetIndex;

        public SheetThread(IControl iControl, WorkbookReader workbookReader, int i) {
            this.reader = workbookReader;
            this.sheetIndex = i;
            this.control = iControl;
        }

        public void run() {
            try {
                this.reader.readSheetInSlideWindow(this.control, this.sheetIndex);
            } catch (OutOfMemoryError e) {
                this.control.getSysKit().getErrorKit().writerLog(e, true);
                this.reader.dispose();
            } catch (Exception e2) {
                this.control.getSysKit().getErrorKit().writerLog(e2, true);
                this.reader.dispose();
            } catch (Throwable th) {
                this.reader = null;
                throw th;
            }
            this.reader = null;
        }
    }

    public static WorkbookReader instance() {
        return reader;
    }

    public void read(ZipPackage zipPackage2, PackagePart packagePart, Workbook workbook, SSReader sSReader) throws Exception {
        this.zipPackage = zipPackage2;
        this.book = workbook;
        this.iReader = sSReader;
        getSheetsProp(packagePart);
        for (int i = 0; i < this.sheetIndexList.size(); i++) {
            Sheet sheet = new Sheet();
            sheet.setWorkbook(workbook);
            sheet.setSheetName(this.sheetNameList.get(this.sheetIndexList.get(Integer.valueOf(i))));
            workbook.addSheet(i, sheet);
        }
        this.worksheetRelCollection = packagePart.getRelationshipsByType(PackageRelationshipTypes.WORKSHEET_PART);
        this.chartsheetRelCollection = packagePart.getRelationshipsByType(PackageRelationshipTypes.CHARTSHEET_PART);
        AnonymousClass1WorkbookReaderHandler r6 = new ReaderHandler(sSReader.getControl(), this) {
            private IControl control;
            private WorkbookReader reader;

            {
                this.reader = r3;
                this.control = r2;
            }

            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 0) {
                    new SheetThread(this.control, this.reader, ((Integer) message.obj).intValue()).start();
                } else if (i == 1 || i == 4) {
                    WorkbookReader.this.dispose();
                    this.reader = null;
                }
            }
        };
        workbook.setReaderHandler(r6);
        Message message = new Message();
        message.what = 0;
        message.obj = 0;
        r6.handleMessage(message);
    }

    /* access modifiers changed from: private */
    public void readSheetInSlideWindow(IControl iControl, int i) throws Exception {
        int i2;
        int i3;
        synchronized (this.book) {
            this.iReader.abortCurrentReading();
            Thread.sleep(50);
            i2 = i - 2;
            int i4 = i2;
            while (true) {
                i3 = i + 2;
                if (i4 > i3) {
                    break;
                }
                if (i4 >= 0 && this.book.getSheet(i4) != null && !this.book.getSheet(i4).isAccomplished()) {
                    this.book.getSheet(i4).setState(1);
                }
                i4++;
            }
        }
        synchronized (this.book) {
            if (i >= 0) {
                try {
                    if (this.book.getSheet(i) != null && !this.book.getSheet(i).isAccomplished()) {
                        readSheet(iControl, i);
                    }
                } finally {
                }
            }
            while (i2 <= i3) {
                if (i2 >= 0 && this.book.getSheet(i2) != null && !this.book.getSheet(i2).isAccomplished()) {
                    readSheet(iControl, i2);
                }
                i2++;
            }
        }
    }

    private void readSheet(IControl iControl, int i) throws Exception {
        short s;
        PackagePart part;
        PackageRelationship relationshipByID = this.worksheetRelCollection.getRelationshipByID(this.sheetIndexList.get(Integer.valueOf(i)));
        if (relationshipByID == null) {
            relationshipByID = this.chartsheetRelCollection.getRelationshipByID(this.sheetIndexList.get(Integer.valueOf(i)));
            s = 1;
        } else {
            s = 0;
        }
        if (relationshipByID != null && (part = this.zipPackage.getPart(relationshipByID.getTargetURI())) != null) {
            this.book.getSheet(i).setSheetType(s);
            SheetReader.instance().getSheet(iControl, this.zipPackage, this.book.getSheet(i), part, this.iReader);
        }
    }

    private void getSheetsProp(PackagePart packagePart) throws Exception {
        Map<Integer, String> map = this.sheetIndexList;
        if (map != null) {
            map.clear();
        } else {
            this.sheetIndexList = new HashMap(5);
        }
        Map<String, String> map2 = this.sheetNameList;
        if (map2 != null) {
            map2.clear();
        } else {
            this.sheetNameList = new HashMap(5);
        }
        this.tempIndex = 0;
        SAXReader sAXReader = new SAXReader();
        try {
            WorkBookSaxHandler workBookSaxHandler = new WorkBookSaxHandler();
            sAXReader.addHandler("/workbook/workbookPr", workBookSaxHandler);
            sAXReader.addHandler("/workbook/sheets/sheet", workBookSaxHandler);
            InputStream inputStream = packagePart.getInputStream();
            sAXReader.read(inputStream);
            inputStream.close();
        } finally {
            sAXReader.resetHandlers();
        }
    }

    public boolean searchContent(ZipPackage zipPackage2, IReader iReader2, PackagePart packagePart, String str) throws Exception {
        if (searchContent_SheetName(packagePart, str)) {
            return true;
        }
        this.zipPackage = zipPackage2;
        this.worksheetRelCollection = packagePart.getRelationshipsByType(PackageRelationshipTypes.WORKSHEET_PART);
        for (int i = 0; i < this.worksheetRelCollection.size(); i++) {
            if (searchContent_Sheet(iReader2, this.worksheetRelCollection.getRelationship(i), str)) {
                dispose();
                return true;
            }
        }
        return false;
    }

    private boolean searchContent_SheetName(PackagePart packagePart, String str) throws Exception {
        SAXReader sAXReader = new SAXReader();
        InputStream inputStream = packagePart.getInputStream();
        Document read = sAXReader.read(inputStream);
        inputStream.close();
        Iterator elementIterator = read.getRootElement().element("sheets").elementIterator();
        while (elementIterator.hasNext()) {
            if (((Element) elementIterator.next()).attributeValue("name").toLowerCase().contains(str)) {
                return true;
            }
        }
        return false;
    }

    private boolean searchContent_Sheet(IReader iReader2, PackageRelationship packageRelationship, String str) throws Exception {
        PackagePart part = this.zipPackage.getPart(packageRelationship.getTargetURI());
        if (part != null) {
            return SheetReader.instance().searchContent(this.zipPackage, iReader2, part, str);
        }
        return false;
    }

    public void dispose() {
        this.zipPackage = null;
        this.book = null;
        this.iReader = null;
        Map<String, String> map = this.sheetNameList;
        if (map != null) {
            map.clear();
            this.sheetNameList = null;
        }
        Map<Integer, String> map2 = this.sheetIndexList;
        if (map2 != null) {
            map2.clear();
            this.sheetIndexList = null;
        }
        PackageRelationshipCollection packageRelationshipCollection = this.worksheetRelCollection;
        if (packageRelationshipCollection != null) {
            packageRelationshipCollection.clear();
            this.worksheetRelCollection = null;
        }
        PackageRelationshipCollection packageRelationshipCollection2 = this.chartsheetRelCollection;
        if (packageRelationshipCollection2 != null) {
            packageRelationshipCollection2.clear();
            this.chartsheetRelCollection = null;
        }
    }

    class WorkBookSaxHandler implements ElementHandler {
        public void onStart(ElementPath elementPath) {
        }

        WorkBookSaxHandler() {
        }

        public void onEnd(ElementPath elementPath) {
            if (!WorkbookReader.this.iReader.isAborted()) {
                Element current = elementPath.getCurrent();
                String name = current.getName();
                if (name.equals("sheet")) {
                    String attributeValue = current.attributeValue(OSOutcomeConstants.OUTCOME_ID);
                    String attributeValue2 = current.attributeValue("name");
                    WorkbookReader.this.sheetIndexList.put(Integer.valueOf(WorkbookReader.this.tempIndex), attributeValue);
                    WorkbookReader.this.sheetNameList.put(attributeValue, attributeValue2);
                    WorkbookReader.access$208(WorkbookReader.this);
                } else if (name.equals("workbookPr")) {
                    boolean z = false;
                    if (!(current.attributeValue("date1904") == null || Integer.parseInt(current.attributeValue("date1904")) == 0)) {
                        z = true;
                    }
                    WorkbookReader.this.book.setUsing1904DateWindowing(z);
                }
                current.detach();
                return;
            }
            throw new AbortReaderError("abort Reader");
        }
    }
}
