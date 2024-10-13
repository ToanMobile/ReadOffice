package com.app.office.fc.xls;

import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.ElementHandler;
import com.app.office.fc.dom4j.ElementPath;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.PackageRelationship;
import com.app.office.fc.openxml4j.opc.PackageRelationshipCollection;
import com.app.office.fc.openxml4j.opc.PackageRelationshipTypes;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.fc.xls.Reader.WorkbookReader;
import com.app.office.fc.xls.Reader.shared.StyleReader;
import com.app.office.fc.xls.Reader.shared.ThemeColorReader;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.model.sheetProperty.Palette;
import com.app.office.ss.util.ColorUtil;
import com.app.office.system.AbortReaderError;
import com.app.office.system.IControl;
import com.app.office.system.StopReaderError;
import java.io.File;
import java.io.InputStream;
import java.util.Iterator;

public class XLSXReader extends SSReader {
    /* access modifiers changed from: private */
    public Workbook book;
    private String filePath;
    /* access modifiers changed from: private */
    public String key;
    private PackagePart packagePart;
    /* access modifiers changed from: private */
    public boolean searched;
    /* access modifiers changed from: private */
    public int sharedStringIndex;
    private ZipPackage zipPackage;

    static /* synthetic */ int access$108(XLSXReader xLSXReader) {
        int i = xLSXReader.sharedStringIndex;
        xLSXReader.sharedStringIndex = i + 1;
        return i;
    }

    public XLSXReader(IControl iControl, String str) {
        this.control = iControl;
        this.filePath = str;
    }

    public Object getModel() throws Exception {
        this.book = new Workbook(false);
        this.zipPackage = new ZipPackage(this.filePath);
        initPackagePart();
        processWorkbook();
        return this.book;
    }

    private void initPackagePart() throws Exception {
        PackageRelationship relationship = this.zipPackage.getRelationshipsByType(PackageRelationshipTypes.CORE_DOCUMENT).getRelationship(0);
        if (relationship.getTargetURI().toString().equals("/xl/workbook.xml")) {
            this.packagePart = this.zipPackage.getPart(relationship);
            return;
        }
        throw new Exception("Format error");
    }

    private void processWorkbook() throws Exception {
        getWorkBookSharedObjects();
        WorkbookReader.instance().read(this.zipPackage, this.packagePart, this.book, this);
    }

    private void getWorkBookSharedObjects() throws Exception {
        getPaletteColor();
        getThemeColor(this.packagePart);
        getStyles(this.packagePart);
        getSharedString(this.packagePart);
    }

    private void getPaletteColor() {
        Palette palette = new Palette();
        int i = 8;
        byte[] color = palette.getColor(8);
        while (color != null) {
            int i2 = i + 1;
            this.book.addColor(i, ColorUtil.rgb(color[0], color[1], color[2]));
            color = palette.getColor(i2);
            i = i2;
        }
        palette.dispose();
    }

    private void getThemeColor(PackagePart packagePart2) throws Exception {
        if (packagePart2.getRelationshipsByType(PackageRelationshipTypes.THEME_PART).size() > 0) {
            ThemeColorReader.instance().getThemeColor(this.zipPackage.getPart(packagePart2.getRelationshipsByType(PackageRelationshipTypes.THEME_PART).getRelationship(0).getTargetURI()), this.book);
        }
    }

    private void getSharedString(PackagePart packagePart2) throws Exception {
        PackageRelationshipCollection relationshipsByType = packagePart2.getRelationshipsByType(PackageRelationshipTypes.SHAREDSTRINGS_PART);
        if (relationshipsByType.size() > 0) {
            PackagePart part = this.zipPackage.getPart(relationshipsByType.getRelationship(0).getTargetURI());
            this.sharedStringIndex = 0;
            SAXReader sAXReader = new SAXReader();
            try {
                sAXReader.addHandler("/sst/si", new SharedStringSaxHandler());
                InputStream inputStream = part.getInputStream();
                sAXReader.read(inputStream);
                inputStream.close();
            } finally {
                sAXReader.resetHandlers();
            }
        }
    }

    private void getStyles(PackagePart packagePart2) throws Exception {
        if (packagePart2.getRelationshipsByType(PackageRelationshipTypes.STYLE_PART).size() > 0) {
            StyleReader.instance().getWorkBookStyle(this.zipPackage.getPart(packagePart2.getRelationshipsByType(PackageRelationshipTypes.STYLE_PART).getRelationship(0).getTargetURI()), this.book, this);
        }
    }

    public boolean searchContent(File file, String str) throws Exception {
        boolean z;
        String lowerCase = str.toLowerCase();
        ZipPackage zipPackage2 = new ZipPackage(file.getAbsolutePath());
        this.zipPackage = zipPackage2;
        PackagePart part = this.zipPackage.getPart(zipPackage2.getRelationshipsByType(PackageRelationshipTypes.CORE_DOCUMENT).getRelationship(0));
        this.packagePart = part;
        if (searchContent_SharedString(part, lowerCase)) {
            z = true;
        } else {
            z = WorkbookReader.instance().searchContent(this.zipPackage, this, this.packagePart, lowerCase);
        }
        dispose();
        return z;
    }

    private boolean searchContent_SharedString(PackagePart packagePart2, String str) throws Exception {
        PackageRelationshipCollection relationshipsByType = packagePart2.getRelationshipsByType(PackageRelationshipTypes.SHAREDSTRINGS_PART);
        if (relationshipsByType.size() <= 0) {
            return false;
        }
        PackagePart part = this.zipPackage.getPart(relationshipsByType.getRelationship(0).getTargetURI());
        this.key = str;
        this.searched = false;
        SAXReader sAXReader = new SAXReader();
        try {
            sAXReader.addHandler("/sst/si", new SearchSharedStringSaxHandler());
            InputStream inputStream = part.getInputStream();
            sAXReader.read(inputStream);
            inputStream.close();
            sAXReader.resetHandlers();
            return this.searched;
        } catch (StopReaderError unused) {
            sAXReader.resetHandlers();
            return true;
        } catch (Throwable th) {
            sAXReader.resetHandlers();
            throw th;
        }
    }

    public void dispose() {
        super.dispose();
        this.filePath = null;
        this.book = null;
        this.zipPackage = null;
        this.packagePart = null;
        this.key = null;
    }

    class SharedStringSaxHandler implements ElementHandler {
        public void onStart(ElementPath elementPath) {
        }

        SharedStringSaxHandler() {
        }

        public void onEnd(ElementPath elementPath) {
            if (!XLSXReader.this.abortReader) {
                Element current = elementPath.getCurrent();
                if (current.getName().equals("si")) {
                    Element element = current.element("t");
                    if (element != null) {
                        XLSXReader.this.book.addSharedString(XLSXReader.this.sharedStringIndex, element.getText());
                    } else {
                        XLSXReader.this.book.addSharedString(XLSXReader.this.sharedStringIndex, current);
                    }
                    XLSXReader.access$108(XLSXReader.this);
                }
                current.detach();
                return;
            }
            throw new AbortReaderError("abort Reader");
        }
    }

    class SearchSharedStringSaxHandler implements ElementHandler {
        public void onStart(ElementPath elementPath) {
        }

        SearchSharedStringSaxHandler() {
        }

        public void onEnd(ElementPath elementPath) {
            if (!XLSXReader.this.abortReader) {
                Element current = elementPath.getCurrent();
                if (current.getName().equals("si")) {
                    Element element = current.element("t");
                    if (element == null) {
                        Iterator elementIterator = current.elementIterator("r");
                        String str = "";
                        while (elementIterator.hasNext()) {
                            str = str + ((Element) elementIterator.next()).element("t").getText();
                        }
                        if (str.toLowerCase().contains(XLSXReader.this.key)) {
                            boolean unused = XLSXReader.this.searched = true;
                        }
                    } else if (element.getText().toLowerCase().contains(XLSXReader.this.key)) {
                        boolean unused2 = XLSXReader.this.searched = true;
                    }
                }
                current.detach();
                if (XLSXReader.this.searched) {
                    throw new StopReaderError("stop");
                }
                return;
            }
            throw new AbortReaderError("abort Reader");
        }
    }
}
