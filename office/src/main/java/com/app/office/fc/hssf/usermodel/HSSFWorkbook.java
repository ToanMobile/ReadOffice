package com.app.office.fc.hssf.usermodel;

import com.app.office.common.shape.ShapeTypes;
import com.app.office.fc.POIDocument;
import com.app.office.fc.codec.DigestUtils;
import com.app.office.fc.ddf.EscherBSERecord;
import com.app.office.fc.ddf.EscherBitmapBlip;
import com.app.office.fc.ddf.EscherBlipRecord;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.hssf.OldExcelFormatException;
import com.app.office.fc.hssf.formula.FormulaShifter;
import com.app.office.fc.hssf.formula.SheetNameFormatter;
import com.app.office.fc.hssf.formula.ptg.Area3DPtg;
import com.app.office.fc.hssf.formula.ptg.MemFuncPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.formula.ptg.UnionPtg;
import com.app.office.fc.hssf.formula.udf.AggregatingUDFFinder;
import com.app.office.fc.hssf.formula.udf.UDFFinder;
import com.app.office.fc.hssf.model.InternalSheet;
import com.app.office.fc.hssf.model.InternalWorkbook;
import com.app.office.fc.hssf.model.RecordStream;
import com.app.office.fc.hssf.record.AbstractEscherHolderRecord;
import com.app.office.fc.hssf.record.DrawingGroupRecord;
import com.app.office.fc.hssf.record.EmbeddedObjectRefSubRecord;
import com.app.office.fc.hssf.record.ExtendedFormatRecord;
import com.app.office.fc.hssf.record.LabelRecord;
import com.app.office.fc.hssf.record.LabelSSTRecord;
import com.app.office.fc.hssf.record.NameRecord;
import com.app.office.fc.hssf.record.ObjRecord;
import com.app.office.fc.hssf.record.RecalcIdRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.RecordBase;
import com.app.office.fc.hssf.record.RecordFactory;
import com.app.office.fc.hssf.record.SubRecord;
import com.app.office.fc.hssf.record.UnknownRecord;
import com.app.office.fc.hssf.record.aggregates.RecordAggregate;
import com.app.office.fc.hssf.record.common.UnicodeString;
import com.app.office.fc.hssf.util.CellReference;
import com.app.office.fc.hwpf.usermodel.Field;
import com.app.office.fc.poifs.filesystem.DirectoryNode;
import com.app.office.fc.poifs.filesystem.POIFSFileSystem;
import com.app.office.fc.ss.usermodel.IRow;
import com.app.office.fc.ss.usermodel.Sheet;
import com.app.office.fc.ss.usermodel.Workbook;
import com.app.office.fc.ss.util.WorkbookUtil;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

public final class HSSFWorkbook extends POIDocument implements Workbook {
    private static final Pattern COMMA_PATTERN = Pattern.compile(",");
    public static final int INITIAL_CAPACITY = 3;
    private static final short MAX_COLUMN = 255;
    private static final int MAX_ROW = 65535;
    private static final int MAX_STYLES = 4030;
    private static final String[] WORKBOOK_DIR_ENTRY_NAMES = {"Workbook", "WORKBOOK"};
    protected List<HSSFSheet> _sheets;
    private UDFFinder _udfFinder;
    private Hashtable fonts;
    private HSSFDataFormat formatter;
    private IRow.MissingCellPolicy missingCellPolicy;
    private ArrayList<HSSFName> names;
    private HSSFPalette palette;
    private boolean preserveNodes;
    private InternalWorkbook workbook;

    public HSSFName createName() {
        return null;
    }

    public static HSSFWorkbook create(InternalWorkbook internalWorkbook) {
        return new HSSFWorkbook(internalWorkbook);
    }

    public HSSFWorkbook() {
        this(InternalWorkbook.createWorkbook());
    }

    private HSSFWorkbook(InternalWorkbook internalWorkbook) {
        super((DirectoryNode) null);
        this.missingCellPolicy = HSSFRow.RETURN_NULL_AND_BLANK;
        this._udfFinder = UDFFinder.DEFAULT;
        this.workbook = internalWorkbook;
        this._sheets = new ArrayList(3);
        this.names = new ArrayList<>(3);
    }

    public HSSFWorkbook(POIFSFileSystem pOIFSFileSystem) throws IOException {
        this(pOIFSFileSystem, false);
    }

    public HSSFWorkbook(POIFSFileSystem pOIFSFileSystem, boolean z) throws IOException {
        this(pOIFSFileSystem.getRoot(), pOIFSFileSystem, z);
    }

    private static String getWorkbookDirEntryName(DirectoryNode directoryNode) {
        String[] strArr = WORKBOOK_DIR_ENTRY_NAMES;
        int i = 0;
        while (i < strArr.length) {
            String str = strArr[i];
            try {
                directoryNode.getEntry(str);
                return str;
            } catch (FileNotFoundException unused) {
                i++;
            }
        }
        try {
            directoryNode.getEntry("Book");
            throw new OldExcelFormatException("The supplied spreadsheet seems to be Excel 5.0/7.0 (BIFF5) format. POI only supports BIFF8 format (from Excel versions 97/2000/XP/2003)");
        } catch (FileNotFoundException unused2) {
            throw new IllegalArgumentException("The supplied POIFSFileSystem does not contain a BIFF8 'Workbook' entry. Is it really an excel file?");
        }
    }

    public HSSFWorkbook(DirectoryNode directoryNode, POIFSFileSystem pOIFSFileSystem, boolean z) throws IOException {
        this(directoryNode, z);
    }

    public HSSFWorkbook(DirectoryNode directoryNode, boolean z) throws IOException {
        super(directoryNode);
        this.missingCellPolicy = HSSFRow.RETURN_NULL_AND_BLANK;
        this._udfFinder = UDFFinder.DEFAULT;
        String workbookDirEntryName = getWorkbookDirEntryName(directoryNode);
        this.preserveNodes = z;
        if (!z) {
            this.directory = null;
        }
        this._sheets = new ArrayList(3);
        this.names = new ArrayList<>(3);
        List<Record> createRecords = RecordFactory.createRecords(directoryNode.createDocumentInputStream(workbookDirEntryName));
        InternalWorkbook createWorkbook = InternalWorkbook.createWorkbook(createRecords);
        this.workbook = createWorkbook;
        setPropertiesFromWorkbook(createWorkbook);
        int numRecords = this.workbook.getNumRecords();
        convertLabelRecords(createRecords, numRecords);
        RecordStream recordStream = new RecordStream(createRecords, numRecords);
        while (recordStream.hasNext()) {
            this._sheets.add(new HSSFSheet(this, InternalSheet.createSheet(recordStream)));
        }
        for (int i = 0; i < this.workbook.getNumNames(); i++) {
        }
    }

    public HSSFWorkbook(InputStream inputStream) throws IOException {
        this(inputStream, true);
    }

    public HSSFWorkbook(InputStream inputStream, boolean z) throws IOException {
        this(new POIFSFileSystem(inputStream), z);
    }

    private void setPropertiesFromWorkbook(InternalWorkbook internalWorkbook) {
        this.workbook = internalWorkbook;
    }

    private void convertLabelRecords(List list, int i) {
        while (i < list.size()) {
            Record record = (Record) list.get(i);
            if (record.getSid() == 516) {
                LabelRecord labelRecord = (LabelRecord) record;
                list.remove(i);
                LabelSSTRecord labelSSTRecord = new LabelSSTRecord();
                int addSSTString = this.workbook.addSSTString(new UnicodeString(labelRecord.getValue()));
                labelSSTRecord.setRow(labelRecord.getRow());
                labelSSTRecord.setColumn(labelRecord.getColumn());
                labelSSTRecord.setXFIndex(labelRecord.getXFIndex());
                labelSSTRecord.setSSTIndex(addSSTString);
                list.add(i, labelSSTRecord);
            }
            i++;
        }
    }

    public IRow.MissingCellPolicy getMissingCellPolicy() {
        return this.missingCellPolicy;
    }

    public void setMissingCellPolicy(IRow.MissingCellPolicy missingCellPolicy2) {
        this.missingCellPolicy = missingCellPolicy2;
    }

    public void setSheetOrder(String str, int i) {
        int sheetIndex = getSheetIndex(str);
        List<HSSFSheet> list = this._sheets;
        list.add(i, list.remove(sheetIndex));
        this.workbook.setSheetOrder(str, i);
        FormulaShifter createForSheetShift = FormulaShifter.createForSheetShift(sheetIndex, i);
        for (HSSFSheet sheet : this._sheets) {
            sheet.getSheet().updateFormulasAfterCellShift(createForSheetShift, -1);
        }
        this.workbook.updateNamesAfterCellShift(createForSheetShift);
    }

    private void validateSheetIndex(int i) {
        int size = this._sheets.size() - 1;
        if (i < 0 || i > size) {
            throw new IllegalArgumentException("Sheet index (" + i + ") is out of range (0.." + size + ")");
        }
    }

    public void setSelectedTab(int i) {
        validateSheetIndex(i);
        int size = this._sheets.size();
        int i2 = 0;
        while (true) {
            boolean z = true;
            if (i2 < size) {
                HSSFSheet sheetAt = getSheetAt(i2);
                if (i2 != i) {
                    z = false;
                }
                sheetAt.setSelected(z);
                i2++;
            } else {
                this.workbook.getWindowOne().setNumSelectedTabs(1);
                return;
            }
        }
    }

    public void setSelectedTab(short s) {
        setSelectedTab((int) s);
    }

    public void setSelectedTabs(int[] iArr) {
        boolean z;
        for (int validateSheetIndex : iArr) {
            validateSheetIndex(validateSheetIndex);
        }
        int size = this._sheets.size();
        for (int i = 0; i < size; i++) {
            int i2 = 0;
            while (true) {
                if (i2 >= iArr.length) {
                    z = false;
                    break;
                } else if (iArr[i2] == i) {
                    z = true;
                    break;
                } else {
                    i2++;
                }
            }
            getSheetAt(i).setSelected(z);
        }
        this.workbook.getWindowOne().setNumSelectedTabs((short) iArr.length);
    }

    public void setActiveSheet(int i) {
        validateSheetIndex(i);
        int size = this._sheets.size();
        int i2 = 0;
        while (i2 < size) {
            getSheetAt(i2).setActive(i2 == i);
            i2++;
        }
        this.workbook.getWindowOne().setActiveSheetIndex(i);
    }

    public int getActiveSheetIndex() {
        return this.workbook.getWindowOne().getActiveSheetIndex();
    }

    public short getSelectedTab() {
        return (short) getActiveSheetIndex();
    }

    public void setFirstVisibleTab(int i) {
        this.workbook.getWindowOne().setFirstVisibleTab(i);
    }

    public void setDisplayedTab(short s) {
        setFirstVisibleTab(s);
    }

    public int getFirstVisibleTab() {
        return this.workbook.getWindowOne().getFirstVisibleTab();
    }

    public short getDisplayedTab() {
        return (short) getFirstVisibleTab();
    }

    public void setSheetName(int i, String str) {
        if (str == null) {
            throw new IllegalArgumentException("sheetName must not be null");
        } else if (!this.workbook.doesContainsSheetName(str, i)) {
            validateSheetIndex(i);
            this.workbook.setSheetName(i, str);
        } else {
            throw new IllegalArgumentException("The workbook already contains a sheet with this name");
        }
    }

    public String getSheetName(int i) {
        validateSheetIndex(i);
        return this.workbook.getSheetName(i);
    }

    public boolean isHidden() {
        return this.workbook.getWindowOne().getHidden();
    }

    public void setHidden(boolean z) {
        this.workbook.getWindowOne().setHidden(z);
    }

    public boolean isSheetHidden(int i) {
        validateSheetIndex(i);
        return this.workbook.isSheetHidden(i);
    }

    public boolean isSheetVeryHidden(int i) {
        validateSheetIndex(i);
        return this.workbook.isSheetVeryHidden(i);
    }

    public void setSheetHidden(int i, boolean z) {
        validateSheetIndex(i);
        this.workbook.setSheetHidden(i, z);
    }

    public void setSheetHidden(int i, int i2) {
        validateSheetIndex(i);
        WorkbookUtil.validateSheetState(i2);
        this.workbook.setSheetHidden(i, i2);
    }

    public int getSheetIndex(String str) {
        return this.workbook.getSheetIndex(str);
    }

    public int getSheetIndex(Sheet sheet) {
        for (int i = 0; i < this._sheets.size(); i++) {
            if (this._sheets.get(i) == sheet) {
                return i;
            }
        }
        return -1;
    }

    public int getExternalSheetIndex(int i) {
        return this.workbook.checkExternSheet(i);
    }

    public String findSheetNameFromExternSheet(int i) {
        return this.workbook.findSheetNameFromExternSheet(i);
    }

    public String resolveNameXText(int i, int i2) {
        return this.workbook.resolveNameXText(i, i2);
    }

    public HSSFSheet createSheet() {
        HSSFSheet hSSFSheet = new HSSFSheet(this);
        this._sheets.add(hSSFSheet);
        boolean z = true;
        this.workbook.setSheetName(this._sheets.size() - 1, "Sheet" + (this._sheets.size() - 1));
        if (this._sheets.size() != 1) {
            z = false;
        }
        hSSFSheet.setSelected(z);
        hSSFSheet.setActive(z);
        return hSSFSheet;
    }

    public HSSFSheet cloneSheet(int i) {
        validateSheetIndex(i);
        String sheetName = this.workbook.getSheetName(i);
        HSSFSheet cloneSheet = this._sheets.get(i).cloneSheet(this);
        cloneSheet.setSelected(false);
        cloneSheet.setActive(false);
        String uniqueSheetName = getUniqueSheetName(sheetName);
        int size = this._sheets.size();
        this._sheets.add(cloneSheet);
        this.workbook.setSheetName(size, uniqueSheetName);
        findExistingBuiltinNameRecordIdx(i, (byte) 13);
        this.workbook.cloneDrawings(cloneSheet.getSheet());
        return cloneSheet;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x008b A[LOOP:0: B:13:0x0034->B:20:0x008b, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x008a A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getUniqueSheetName(java.lang.String r9) {
        /*
            r8 = this;
            r0 = 40
            int r0 = r9.lastIndexOf(r0)
            r1 = 0
            java.lang.String r2 = ")"
            r3 = 2
            if (r0 <= 0) goto L_0x0033
            boolean r4 = r9.endsWith(r2)
            if (r4 == 0) goto L_0x0033
            int r4 = r0 + 1
            int r5 = r9.length()
            int r5 = r5 + -1
            java.lang.String r4 = r9.substring(r4, r5)
            java.lang.String r4 = r4.trim()     // Catch:{ NumberFormatException -> 0x0033 }
            int r4 = java.lang.Integer.parseInt(r4)     // Catch:{ NumberFormatException -> 0x0033 }
            int r4 = r4 + 1
            java.lang.String r0 = r9.substring(r1, r0)     // Catch:{ NumberFormatException -> 0x0031 }
            java.lang.String r9 = r0.trim()     // Catch:{ NumberFormatException -> 0x0031 }
            goto L_0x0034
        L_0x0031:
            goto L_0x0034
        L_0x0033:
            r4 = 2
        L_0x0034:
            int r0 = r4 + 1
            java.lang.String r4 = java.lang.Integer.toString(r4)
            int r5 = r9.length()
            int r6 = r4.length()
            int r5 = r5 + r6
            int r5 = r5 + r3
            r6 = 31
            if (r5 >= r6) goto L_0x0060
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r9)
            java.lang.String r6 = " ("
            r5.append(r6)
            r5.append(r4)
            r5.append(r2)
            java.lang.String r4 = r5.toString()
            goto L_0x0081
        L_0x0060:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            int r7 = r4.length()
            int r6 = r6 - r7
            int r6 = r6 - r3
            java.lang.String r6 = r9.substring(r1, r6)
            r5.append(r6)
            java.lang.String r6 = "("
            r5.append(r6)
            r5.append(r4)
            r5.append(r2)
            java.lang.String r4 = r5.toString()
        L_0x0081:
            com.app.office.fc.hssf.model.InternalWorkbook r5 = r8.workbook
            int r5 = r5.getSheetIndex(r4)
            r6 = -1
            if (r5 != r6) goto L_0x008b
            return r4
        L_0x008b:
            r4 = r0
            goto L_0x0034
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.usermodel.HSSFWorkbook.getUniqueSheetName(java.lang.String):java.lang.String");
    }

    public HSSFSheet createSheet(String str) {
        if (str == null) {
            throw new IllegalArgumentException("sheetName must not be null");
        } else if (!this.workbook.doesContainsSheetName(str, this._sheets.size())) {
            HSSFSheet hSSFSheet = new HSSFSheet(this);
            this.workbook.setSheetName(this._sheets.size(), str);
            this._sheets.add(hSSFSheet);
            boolean z = true;
            if (this._sheets.size() != 1) {
                z = false;
            }
            hSSFSheet.setSelected(z);
            hSSFSheet.setActive(z);
            return hSSFSheet;
        } else {
            throw new IllegalArgumentException("The workbook already contains a sheet of this name");
        }
    }

    public int getNumberOfSheets() {
        return this._sheets.size();
    }

    public int getSheetIndexFromExternSheetIndex(int i) {
        return this.workbook.getSheetIndexFromExternSheetIndex(i);
    }

    private HSSFSheet[] getSheets() {
        HSSFSheet[] hSSFSheetArr = new HSSFSheet[this._sheets.size()];
        this._sheets.toArray(hSSFSheetArr);
        return hSSFSheetArr;
    }

    public HSSFSheet getSheetAt(int i) {
        validateSheetIndex(i);
        return this._sheets.get(i);
    }

    public boolean isEmpty() {
        return this._sheets.size() == 0;
    }

    public HSSFSheet getSheet(String str) {
        HSSFSheet hSSFSheet = null;
        for (int i = 0; i < this._sheets.size(); i++) {
            if (this.workbook.getSheetName(i).equalsIgnoreCase(str)) {
                hSSFSheet = this._sheets.get(i);
            }
        }
        return hSSFSheet;
    }

    public void removeSheetAt(int i) {
        validateSheetIndex(i);
        boolean isActive = getSheetAt(i).isActive();
        boolean isSelected = getSheetAt(i).isSelected();
        this._sheets.remove(i);
        this.workbook.removeSheet(i);
        int size = this._sheets.size();
        boolean z = true;
        if (size >= 1) {
            if (i >= size) {
                i = size - 1;
            }
            if (isActive) {
                setActiveSheet(i);
            }
            if (isSelected) {
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        z = false;
                        break;
                    } else if (getSheetAt(i2).isSelected()) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (!z) {
                    setSelectedTab(i);
                }
            }
        }
    }

    public void setBackupFlag(boolean z) {
        this.workbook.getBackupRecord().setBackup(z ? (short) 1 : 0);
    }

    public boolean getBackupFlag() {
        return this.workbook.getBackupRecord().getBackup() != 0;
    }

    public void setRepeatingRowsAndColumns(int i, int i2, int i3, int i4, int i5) {
        NameRecord nameRecord;
        NameRecord nameRecord2;
        ArrayList arrayList;
        boolean z;
        ArrayList arrayList2;
        int i6 = i;
        int i7 = i2;
        int i8 = i3;
        int i9 = i4;
        int i10 = i5;
        if (i7 == -1 && i8 != -1) {
            throw new IllegalArgumentException("Invalid column range specification");
        } else if (i9 == -1 && i10 != -1) {
            throw new IllegalArgumentException("Invalid row range specification");
        } else if (i7 < -1 || i7 >= 255) {
            throw new IllegalArgumentException("Invalid column range specification");
        } else if (i8 < -1 || i8 >= 255) {
            throw new IllegalArgumentException("Invalid column range specification");
        } else if (i9 < -1 || i9 > 65535) {
            throw new IllegalArgumentException("Invalid row range specification");
        } else if (i10 < -1 || i10 > 65535) {
            throw new IllegalArgumentException("Invalid row range specification");
        } else if (i7 > i8) {
            throw new IllegalArgumentException("Invalid column range specification");
        } else if (i9 <= i10) {
            HSSFSheet sheetAt = getSheetAt(i);
            short checkExternSheet = getWorkbook().checkExternSheet(i6);
            boolean z2 = (i7 == -1 || i8 == -1 || i9 == -1 || i10 == -1) ? false : true;
            boolean z3 = i7 == -1 && i8 == -1 && i9 == -1 && i10 == -1;
            int findExistingBuiltinNameRecordIdx = findExistingBuiltinNameRecordIdx(i6, (byte) 7);
            if (!z3) {
                if (findExistingBuiltinNameRecordIdx < 0) {
                    nameRecord = this.workbook.createBuiltInName((byte) 7, i6 + 1);
                } else {
                    nameRecord = this.workbook.getNameRecord(findExistingBuiltinNameRecordIdx);
                }
                NameRecord nameRecord3 = nameRecord;
                ArrayList arrayList3 = new ArrayList();
                if (z2) {
                    arrayList3.add(new MemFuncPtg(23));
                }
                if (i7 >= 0) {
                    Area3DPtg area3DPtg = r1;
                    nameRecord2 = nameRecord3;
                    z = true;
                    Area3DPtg area3DPtg2 = new Area3DPtg(0, 65535, i2, i3, false, false, false, false, checkExternSheet);
                    arrayList = arrayList3;
                    arrayList.add(area3DPtg);
                } else {
                    arrayList = arrayList3;
                    nameRecord2 = nameRecord3;
                    z = true;
                }
                if (i9 >= 0) {
                    arrayList2 = arrayList;
                    arrayList2.add(new Area3DPtg(i4, i5, 0, 255, false, false, false, false, checkExternSheet));
                } else {
                    arrayList2 = arrayList;
                }
                if (z2) {
                    arrayList2.add(UnionPtg.instance);
                }
                Ptg[] ptgArr = new Ptg[arrayList2.size()];
                arrayList2.toArray(ptgArr);
                nameRecord2.setNameDefinition(ptgArr);
                sheetAt.getPrintSetup().setValidSettings(false);
                sheetAt.setActive(z);
            } else if (findExistingBuiltinNameRecordIdx >= 0) {
                this.workbook.removeName(findExistingBuiltinNameRecordIdx);
            }
        } else {
            throw new IllegalArgumentException("Invalid row range specification");
        }
    }

    private int findExistingBuiltinNameRecordIdx(int i, byte b) {
        int i2 = 0;
        while (i2 < this.names.size()) {
            NameRecord nameRecord = this.workbook.getNameRecord(i2);
            if (nameRecord == null) {
                throw new RuntimeException("Unable to find all defined names to iterate over");
            } else if (nameRecord.isBuiltInName() && nameRecord.getBuiltInName() == b && nameRecord.getSheetNumber() - 1 == i) {
                return i2;
            } else {
                i2++;
            }
        }
        return -1;
    }

    public HSSFFont createFont() {
        this.workbook.createNewFont();
        short numberOfFonts = (short) (getNumberOfFonts() - 1);
        if (numberOfFonts > 3) {
            numberOfFonts = (short) (numberOfFonts + 1);
        }
        if (numberOfFonts != Short.MAX_VALUE) {
            return getFontAt(numberOfFonts);
        }
        throw new IllegalArgumentException("Maximum number of fonts was exceeded");
    }

    public HSSFFont findFont(short s, short s2, short s3, String str, boolean z, boolean z2, short s4, byte b) {
        for (short s5 = 0; s5 <= getNumberOfFonts(); s5 = (short) (s5 + 1)) {
            if (s5 != 4) {
                HSSFFont fontAt = getFontAt(s5);
                if (fontAt.getBoldweight() == s && fontAt.getColor() == s2 && fontAt.getFontHeight() == s3 && fontAt.getFontName().equals(str) && fontAt.getItalic() == z && fontAt.getStrikeout() == z2 && fontAt.getTypeOffset() == s4 && fontAt.getUnderline() == b) {
                    return fontAt;
                }
            }
        }
        return null;
    }

    public short getNumberOfFonts() {
        return (short) this.workbook.getNumberOfFontRecords();
    }

    public HSSFFont getFontAt(short s) {
        if (this.fonts == null) {
            this.fonts = new Hashtable();
        }
        Short valueOf = Short.valueOf(s);
        if (this.fonts.containsKey(valueOf)) {
            return (HSSFFont) this.fonts.get(valueOf);
        }
        HSSFFont hSSFFont = new HSSFFont(s, this.workbook.getFontRecordAt(s));
        this.fonts.put(valueOf, hSSFFont);
        return hSSFFont;
    }

    /* access modifiers changed from: protected */
    public void resetFontCache() {
        this.fonts = new Hashtable();
    }

    public HSSFCellStyle createCellStyle() {
        if (this.workbook.getNumExFormats() != MAX_STYLES) {
            return new HSSFCellStyle((short) (getNumCellStyles() - 1), this.workbook.createCellXF(), this);
        }
        throw new IllegalStateException("The maximum number of cell styles was exceeded. You can define up to 4000 styles in a .xls workbook");
    }

    public short getNumCellStyles() {
        return (short) this.workbook.getNumExFormats();
    }

    public HSSFCellStyle getCellStyleAt(short s) {
        ExtendedFormatRecord exFormatAt = this.workbook.getExFormatAt(s);
        if (exFormatAt != null) {
            return new HSSFCellStyle(s, exFormatAt, this);
        }
        return null;
    }

    public void write(OutputStream outputStream) throws IOException {
        byte[] bytes = getBytes();
        POIFSFileSystem pOIFSFileSystem = new POIFSFileSystem();
        ArrayList arrayList = new ArrayList(1);
        pOIFSFileSystem.createDocument(new ByteArrayInputStream(bytes), "Workbook");
        writeProperties(pOIFSFileSystem, arrayList);
        if (this.preserveNodes) {
            arrayList.add("Workbook");
            arrayList.add("WORKBOOK");
            copyNodes(this.directory, pOIFSFileSystem.getRoot(), (List<String>) arrayList);
            pOIFSFileSystem.getRoot().setStorageClsid(this.directory.getStorageClsid());
        }
        pOIFSFileSystem.writeFilesystem(outputStream);
    }

    private static final class SheetRecordCollector implements RecordAggregate.RecordVisitor {
        private List _list = new ArrayList(128);
        private int _totalSize = 0;

        public int getTotalSize() {
            return this._totalSize;
        }

        public void visitRecord(Record record) {
            this._list.add(record);
            this._totalSize += record.getRecordSize();
        }

        public int serialize(int i, byte[] bArr) {
            int size = this._list.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                i2 += ((Record) this._list.get(i3)).serialize(i + i2, bArr);
            }
            return i2;
        }
    }

    public byte[] getBytes() {
        HSSFSheet[] sheets = getSheets();
        int i = 0;
        for (HSSFSheet sheet : sheets) {
            sheet.getSheet().preSerialize();
        }
        int size = this.workbook.getSize();
        SheetRecordCollector[] sheetRecordCollectorArr = new SheetRecordCollector[r1];
        for (int i2 = 0; i2 < r1; i2++) {
            this.workbook.setSheetBof(i2, size);
            SheetRecordCollector sheetRecordCollector = new SheetRecordCollector();
            sheets[i2].getSheet().visitContainedRecords(sheetRecordCollector, size);
            size += sheetRecordCollector.getTotalSize();
            sheetRecordCollectorArr[i2] = sheetRecordCollector;
        }
        byte[] bArr = new byte[size];
        int serialize = this.workbook.serialize(0, bArr);
        while (i < r1) {
            SheetRecordCollector sheetRecordCollector2 = sheetRecordCollectorArr[i];
            int serialize2 = sheetRecordCollector2.serialize(serialize, bArr);
            if (serialize2 == sheetRecordCollector2.getTotalSize()) {
                serialize += serialize2;
                i++;
            } else {
                throw new IllegalStateException("Actual serialized sheet size (" + serialize2 + ") differs from pre-calculated size (" + sheetRecordCollector2.getTotalSize() + ") for sheet (" + i + ")");
            }
        }
        return bArr;
    }

    public int addSSTString(String str) {
        return this.workbook.addSSTString(new UnicodeString(str));
    }

    public int getSSTUniqueStringSize() {
        return this.workbook.getSSTUniqueStringSize();
    }

    public String getSSTString(int i) {
        return this.workbook.getSSTString(i).getString();
    }

    public InternalWorkbook getWorkbook() {
        return this.workbook;
    }

    public int getNumberOfNames() {
        return this.names.size();
    }

    public HSSFName getName(String str) {
        int nameIndex = getNameIndex(str);
        if (nameIndex < 0) {
            return null;
        }
        return this.names.get(nameIndex);
    }

    public HSSFName getNameAt(int i) {
        int size = this.names.size();
        if (size < 1) {
            throw new IllegalStateException("There are no defined names in this workbook");
        } else if (i >= 0 && i <= size) {
            return this.names.get(i);
        } else {
            throw new IllegalArgumentException("Specified name index " + i + " is outside the allowable range (0.." + (size - 1) + ").");
        }
    }

    public NameRecord getNameRecord(int i) {
        return getWorkbook().getNameRecord(i);
    }

    public String getNameName(int i) {
        return getNameAt(i).getNameName();
    }

    public void setPrintArea(int i, String str) {
        int i2 = i + 1;
        if (this.workbook.getSpecificBuiltinRecord((byte) 6, i2) == null) {
            this.workbook.createBuiltInName((byte) 6, i2);
        }
        String[] split = COMMA_PATTERN.split(str);
        StringBuffer stringBuffer = new StringBuffer(32);
        for (int i3 = 0; i3 < split.length; i3++) {
            if (i3 > 0) {
                stringBuffer.append(",");
            }
            SheetNameFormatter.appendFormat(stringBuffer, getSheetName(i));
            stringBuffer.append("!");
            stringBuffer.append(split[i3]);
        }
    }

    public void setPrintArea(int i, int i2, int i3, int i4, int i5) {
        String formatAsString = new CellReference(i4, i2, true, true).formatAsString();
        CellReference cellReference = new CellReference(i5, i3, true, true);
        setPrintArea(i, formatAsString + ":" + cellReference.formatAsString());
    }

    public String getPrintArea(int i) {
        if (this.workbook.getSpecificBuiltinRecord((byte) 6, i + 1) == null) {
        }
        return null;
    }

    public void removePrintArea(int i) {
        getWorkbook().removeBuiltinRecord((byte) 6, i + 1);
    }

    public int getNameIndex(String str) {
        for (int i = 0; i < this.names.size(); i++) {
            if (getNameName(i).equalsIgnoreCase(str)) {
                return i;
            }
        }
        return -1;
    }

    public void removeName(int i) {
        this.names.remove(i);
        this.workbook.removeName(i);
    }

    public HSSFDataFormat createDataFormat() {
        if (this.formatter == null) {
            this.formatter = new HSSFDataFormat(this.workbook);
        }
        return this.formatter;
    }

    public void removeName(String str) {
        removeName(getNameIndex(str));
    }

    public HSSFPalette getCustomPalette() {
        if (this.palette == null) {
            this.palette = new HSSFPalette(this.workbook.getCustomPalette());
        }
        return this.palette;
    }

    public void insertChartRecord() {
        this.workbook.getRecords().add(this.workbook.findFirstRecordLocBySid(252), new UnknownRecord(ShapeTypes.Star5, new byte[]{15, 0, 0, -16, 82, 0, 0, 0, 0, 0, 6, -16, 24, 0, 0, 0, 1, 8, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 0, Field.MACROBUTTON, 0, 11, -16, 18, 0, 0, 0, -65, 0, 8, 0, 8, 0, -127, 1, 9, 0, 0, 8, -64, 1, 64, 0, 0, 8, 64, 0, 30, -15, 16, 0, 0, 0, 13, 0, 0, 8, 12, 0, 0, 8, 23, 0, 0, 8, -9, 0, 0, 16}));
    }

    public void dumpDrawingGroupRecords(boolean z) {
        DrawingGroupRecord drawingGroupRecord = (DrawingGroupRecord) this.workbook.findFirstRecordBySid(DrawingGroupRecord.sid);
        drawingGroupRecord.decode();
        List<EscherRecord> escherRecords = drawingGroupRecord.getEscherRecords();
        PrintWriter printWriter = new PrintWriter(System.out);
        for (EscherRecord next : escherRecords) {
            if (z) {
                System.out.println(next.toString());
            } else {
                next.display(printWriter, 0);
            }
        }
        printWriter.flush();
    }

    /* access modifiers changed from: package-private */
    public void initDrawings() {
        if (this.workbook.findDrawingGroup() != null) {
            for (int i = 0; i < getNumberOfSheets(); i++) {
                getSheetAt(i).getDrawingPatriarch();
            }
            return;
        }
        this.workbook.createDrawingGroup();
    }

    public int addPicture(byte[] bArr, int i) {
        initDrawings();
        byte[] md5 = DigestUtils.md5(bArr);
        EscherBitmapBlip escherBitmapBlip = new EscherBitmapBlip();
        escherBitmapBlip.setRecordId((short) (i - 4072));
        switch (i) {
            case 2:
                escherBitmapBlip.setOptions(15680);
                break;
            case 3:
                escherBitmapBlip.setOptions(8544);
                break;
            case 4:
                escherBitmapBlip.setOptions(21536);
                break;
            case 5:
                escherBitmapBlip.setOptions(HSSFPictureData.MSOBI_JPEG);
                break;
            case 6:
                escherBitmapBlip.setOptions(HSSFPictureData.MSOBI_PNG);
                break;
            case 7:
                escherBitmapBlip.setOptions(HSSFPictureData.MSOBI_DIB);
                break;
        }
        escherBitmapBlip.setUID(md5);
        escherBitmapBlip.setMarker((byte) -1);
        escherBitmapBlip.setPictureData(bArr);
        EscherBSERecord escherBSERecord = new EscherBSERecord();
        escherBSERecord.setRecordId(EscherBSERecord.RECORD_ID);
        escherBSERecord.setOptions((short) ((i << 4) | 2));
        byte b = (byte) i;
        escherBSERecord.setBlipTypeMacOS(b);
        escherBSERecord.setBlipTypeWin32(b);
        escherBSERecord.setUid(md5);
        escherBSERecord.setTag(255);
        escherBSERecord.setSize(bArr.length + 25);
        escherBSERecord.setRef(1);
        escherBSERecord.setOffset(0);
        escherBSERecord.setBlipRecord(escherBitmapBlip);
        return this.workbook.addBSERecord(escherBSERecord);
    }

    public List<HSSFPictureData> getAllPictures() {
        ArrayList arrayList = new ArrayList();
        for (Record next : this.workbook.getRecords()) {
            if (next instanceof AbstractEscherHolderRecord) {
                AbstractEscherHolderRecord abstractEscherHolderRecord = (AbstractEscherHolderRecord) next;
                abstractEscherHolderRecord.decode();
                searchForPictures(abstractEscherHolderRecord.getEscherRecords(), arrayList);
            }
        }
        return arrayList;
    }

    private void searchForPictures(List<EscherRecord> list, List<HSSFPictureData> list2) {
        EscherBlipRecord blipRecord;
        for (EscherRecord next : list) {
            if ((next instanceof EscherBSERecord) && (blipRecord = ((EscherBSERecord) next).getBlipRecord()) != null) {
                list2.add(new HSSFPictureData(blipRecord));
            }
            searchForPictures(next.getChildRecords(), list2);
        }
    }

    public boolean isWriteProtected() {
        return this.workbook.isWriteProtected();
    }

    public void writeProtectWorkbook(String str, String str2) {
        this.workbook.writeProtectWorkbook(str, str2);
    }

    public void unwriteProtectWorkbook() {
        this.workbook.unwriteProtectWorkbook();
    }

    public List<HSSFObjectData> getAllEmbeddedObjects() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < getNumberOfSheets(); i++) {
            getAllEmbeddedObjects(getSheetAt(i).getSheet().getRecords(), arrayList);
        }
        return arrayList;
    }

    private void getAllEmbeddedObjects(List<RecordBase> list, List<HSSFObjectData> list2) {
        for (RecordBase next : list) {
            if (next instanceof ObjRecord) {
                ObjRecord objRecord = (ObjRecord) next;
                for (SubRecord subRecord : objRecord.getSubRecords()) {
                    if (subRecord instanceof EmbeddedObjectRefSubRecord) {
                        list2.add(new HSSFObjectData(objRecord, this.directory));
                    }
                }
            }
        }
    }

    public HSSFCreationHelper getCreationHelper() {
        return new HSSFCreationHelper(this);
    }

    /* access modifiers changed from: package-private */
    public UDFFinder getUDFFinder() {
        return this._udfFinder;
    }

    public void addToolPack(UDFFinder uDFFinder) {
        ((AggregatingUDFFinder) this._udfFinder).add(uDFFinder);
    }

    public void setForceFormulaRecalculation(boolean z) {
        getWorkbook().getRecalcId().setEngineId(0);
    }

    public boolean getForceFormulaRecalculation() {
        RecalcIdRecord recalcIdRecord = (RecalcIdRecord) getWorkbook().findFirstRecordBySid(449);
        return (recalcIdRecord == null || recalcIdRecord.getEngineId() == 0) ? false : true;
    }

    public boolean isUsing1904DateWindowing() {
        return this.workbook.isUsing1904DateWindowing();
    }
}
