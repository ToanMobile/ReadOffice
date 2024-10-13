package com.app.office.fc.hssf.model;

import com.itextpdf.text.pdf.codec.wmf.MetaDo;
import com.app.office.fc.ddf.EscherBSERecord;
import com.app.office.fc.ddf.EscherBoolProperty;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherDgRecord;
import com.app.office.fc.ddf.EscherDggRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherRGBProperty;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.ddf.EscherSplitMenuColorsRecord;
import com.app.office.fc.hssf.formula.EvaluationWorkbook;
import com.app.office.fc.hssf.formula.FormulaShifter;
import com.app.office.fc.hssf.formula.ptg.Area3DPtg;
import com.app.office.fc.hssf.formula.ptg.NameXPtg;
import com.app.office.fc.hssf.formula.ptg.OperandPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.formula.ptg.Ref3DPtg;
import com.app.office.fc.hssf.formula.udf.UDFFinder;
import com.app.office.fc.hssf.record.BOFRecord;
import com.app.office.fc.hssf.record.BackupRecord;
import com.app.office.fc.hssf.record.BookBoolRecord;
import com.app.office.fc.hssf.record.BoundSheetRecord;
import com.app.office.fc.hssf.record.CodepageRecord;
import com.app.office.fc.hssf.record.CountryRecord;
import com.app.office.fc.hssf.record.DSFRecord;
import com.app.office.fc.hssf.record.DateWindow1904Record;
import com.app.office.fc.hssf.record.DrawingGroupRecord;
import com.app.office.fc.hssf.record.EOFRecord;
import com.app.office.fc.hssf.record.EscherAggregate;
import com.app.office.fc.hssf.record.ExtSSTRecord;
import com.app.office.fc.hssf.record.ExtendedFormatRecord;
import com.app.office.fc.hssf.record.FileSharingRecord;
import com.app.office.fc.hssf.record.FnGroupCountRecord;
import com.app.office.fc.hssf.record.FontRecord;
import com.app.office.fc.hssf.record.FormatRecord;
import com.app.office.fc.hssf.record.HideObjRecord;
import com.app.office.fc.hssf.record.HyperlinkRecord;
import com.app.office.fc.hssf.record.InterfaceEndRecord;
import com.app.office.fc.hssf.record.InterfaceHdrRecord;
import com.app.office.fc.hssf.record.MMSRecord;
import com.app.office.fc.hssf.record.NameCommentRecord;
import com.app.office.fc.hssf.record.NameRecord;
import com.app.office.fc.hssf.record.PaletteRecord;
import com.app.office.fc.hssf.record.PasswordRecord;
import com.app.office.fc.hssf.record.PasswordRev4Record;
import com.app.office.fc.hssf.record.PrecisionRecord;
import com.app.office.fc.hssf.record.ProtectRecord;
import com.app.office.fc.hssf.record.ProtectionRev4Record;
import com.app.office.fc.hssf.record.RecalcIdRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.RefreshAllRecord;
import com.app.office.fc.hssf.record.SSTRecord;
import com.app.office.fc.hssf.record.StyleRecord;
import com.app.office.fc.hssf.record.TabIdRecord;
import com.app.office.fc.hssf.record.UseSelFSRecord;
import com.app.office.fc.hssf.record.WindowOneRecord;
import com.app.office.fc.hssf.record.WindowProtectRecord;
import com.app.office.fc.hssf.record.WriteAccessRecord;
import com.app.office.fc.hssf.record.WriteProtectRecord;
import com.app.office.fc.hssf.record.common.UnicodeString;
import com.app.office.fc.hssf.usermodel.HSSFFont;
import com.app.office.fc.ss.usermodel.BuiltinFormats;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import com.app.office.system.AbortReaderError;
import com.app.office.system.AbstractReader;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Internal
public final class InternalWorkbook {
    private static final short CODEPAGE = 1200;
    private static final int DEBUG = POILogger.DEBUG;
    private static final int MAX_SENSITIVE_SHEET_NAME_LEN = 31;
    private static final POILogger log = POILogFactory.getLogger(InternalWorkbook.class);
    private final List<BoundSheetRecord> boundsheets = new ArrayList();
    private final Map<String, NameCommentRecord> commentRecords = new LinkedHashMap();
    private DrawingManager2 drawingManager;
    private List<EscherBSERecord> escherBSERecords = new ArrayList();
    private FileSharingRecord fileShare;
    private final List<FormatRecord> formats = new ArrayList();
    private final List<HyperlinkRecord> hyperlinks = new ArrayList();
    private LinkTable linkTable;
    private int maxformatid = -1;
    private int numfonts = 0;
    private int numxfs = 0;
    private final WorkbookRecordList records = new WorkbookRecordList();
    protected SSTRecord sst;
    private boolean uses1904datewindowing = false;
    private WindowOneRecord windowOne;
    private WriteAccessRecord writeAccess;
    private WriteProtectRecord writeProtect;

    private InternalWorkbook() {
    }

    public static InternalWorkbook createWorkbook(List<Record> list) {
        return createWorkbook(list, (AbstractReader) null);
    }

    public static InternalWorkbook createWorkbook(List<Record> list, AbstractReader abstractReader) {
        int i;
        InternalWorkbook internalWorkbook = new InternalWorkbook();
        ArrayList arrayList = new ArrayList(list.size() / 3);
        internalWorkbook.records.setRecords(arrayList);
        int i2 = 0;
        while (true) {
            if (i2 < list.size()) {
                if (abstractReader == null || !abstractReader.isAborted()) {
                    Record record = list.get(i2);
                    if (record.getSid() == 10) {
                        arrayList.add(record);
                    } else {
                        switch (record.getSid()) {
                            case 18:
                                internalWorkbook.records.setProtpos(i2);
                                break;
                            case 23:
                                throw new RuntimeException("Extern sheet is part of LinkTable");
                            case 24:
                            case 430:
                                LinkTable linkTable2 = new LinkTable(list, i2, internalWorkbook.records, internalWorkbook.commentRecords);
                                internalWorkbook.linkTable = linkTable2;
                                i2 += linkTable2.getRecordCount() - 1;
                                continue;
                            case 34:
                                internalWorkbook.uses1904datewindowing = ((DateWindow1904Record) record).getWindowing() == 1;
                                break;
                            case 49:
                                internalWorkbook.records.setFontpos(i2);
                                internalWorkbook.numfonts++;
                                break;
                            case 61:
                                internalWorkbook.windowOne = (WindowOneRecord) record;
                                break;
                            case 64:
                                internalWorkbook.records.setBackuppos(i2);
                                break;
                            case 91:
                                internalWorkbook.fileShare = (FileSharingRecord) record;
                                break;
                            case 92:
                                internalWorkbook.writeAccess = (WriteAccessRecord) record;
                                break;
                            case 133:
                                internalWorkbook.boundsheets.add((BoundSheetRecord) record);
                                internalWorkbook.records.setBspos(i2);
                                break;
                            case 134:
                                internalWorkbook.writeProtect = (WriteProtectRecord) record;
                                break;
                            case 146:
                                internalWorkbook.records.setPalettepos(i2);
                                break;
                            case 224:
                                internalWorkbook.records.setXfpos(i2);
                                internalWorkbook.numxfs++;
                                break;
                            case 252:
                                internalWorkbook.sst = (SSTRecord) record;
                                break;
                            case 317:
                                internalWorkbook.records.setTabpos(i2);
                                break;
                            case 1054:
                                FormatRecord formatRecord = (FormatRecord) record;
                                internalWorkbook.formats.add(formatRecord);
                                if (internalWorkbook.maxformatid >= formatRecord.getIndexCode()) {
                                    i = internalWorkbook.maxformatid;
                                } else {
                                    i = formatRecord.getIndexCode();
                                }
                                internalWorkbook.maxformatid = i;
                                break;
                            case 2196:
                                NameCommentRecord nameCommentRecord = (NameCommentRecord) record;
                                internalWorkbook.commentRecords.put(nameCommentRecord.getNameText(), nameCommentRecord);
                                break;
                        }
                        arrayList.add(record);
                        i2++;
                    }
                } else {
                    throw new AbortReaderError("abort Reader");
                }
            }
        }
        while (i2 < list.size()) {
            if (abstractReader == null || !abstractReader.isAborted()) {
                Record record2 = list.get(i2);
                if (record2.getSid() == 440) {
                    internalWorkbook.hyperlinks.add((HyperlinkRecord) record2);
                }
                i2++;
            } else {
                throw new AbortReaderError("abort Reader");
            }
        }
        if (internalWorkbook.windowOne == null) {
            internalWorkbook.windowOne = createWindowOne();
        }
        return internalWorkbook;
    }

    public static InternalWorkbook createWorkbook() {
        int i;
        POILogger pOILogger = log;
        if (pOILogger.check(POILogger.DEBUG)) {
            pOILogger.log(DEBUG, (Object) "creating new workbook from scratch");
        }
        InternalWorkbook internalWorkbook = new InternalWorkbook();
        ArrayList arrayList = new ArrayList(30);
        internalWorkbook.records.setRecords(arrayList);
        List<FormatRecord> list = internalWorkbook.formats;
        arrayList.add(createBOF());
        arrayList.add(new InterfaceHdrRecord(1200));
        arrayList.add(createMMS());
        arrayList.add(InterfaceEndRecord.instance);
        arrayList.add(createWriteAccess());
        arrayList.add(createCodepage());
        arrayList.add(createDSF());
        arrayList.add(createTabId());
        internalWorkbook.records.setTabpos(arrayList.size() - 1);
        arrayList.add(createFnGroupCount());
        arrayList.add(createWindowProtect());
        arrayList.add(createProtect());
        internalWorkbook.records.setProtpos(arrayList.size() - 1);
        arrayList.add(createPassword());
        arrayList.add(createProtectionRev4());
        arrayList.add(createPasswordRev4());
        WindowOneRecord createWindowOne = createWindowOne();
        internalWorkbook.windowOne = createWindowOne;
        arrayList.add(createWindowOne);
        arrayList.add(createBackup());
        internalWorkbook.records.setBackuppos(arrayList.size() - 1);
        arrayList.add(createHideObj());
        arrayList.add(createDateWindow1904());
        arrayList.add(createPrecision());
        arrayList.add(createRefreshAll());
        arrayList.add(createBookBool());
        arrayList.add(createFont());
        arrayList.add(createFont());
        arrayList.add(createFont());
        arrayList.add(createFont());
        internalWorkbook.records.setFontpos(arrayList.size() - 1);
        internalWorkbook.numfonts = 4;
        for (int i2 = 0; i2 <= 7; i2++) {
            FormatRecord createFormat = createFormat(i2);
            if (internalWorkbook.maxformatid >= createFormat.getIndexCode()) {
                i = internalWorkbook.maxformatid;
            } else {
                i = createFormat.getIndexCode();
            }
            internalWorkbook.maxformatid = i;
            list.add(createFormat);
            arrayList.add(createFormat);
        }
        for (int i3 = 0; i3 < 21; i3++) {
            arrayList.add(createExtendedFormat(i3));
            internalWorkbook.numxfs++;
        }
        internalWorkbook.records.setXfpos(arrayList.size() - 1);
        for (int i4 = 0; i4 < 6; i4++) {
            arrayList.add(createStyle(i4));
        }
        arrayList.add(createUseSelFS());
        for (int i5 = 0; i5 < 1; i5++) {
            BoundSheetRecord createBoundSheet = createBoundSheet(i5);
            arrayList.add(createBoundSheet);
            internalWorkbook.boundsheets.add(createBoundSheet);
            internalWorkbook.records.setBspos(arrayList.size() - 1);
        }
        arrayList.add(createCountry());
        for (int i6 = 0; i6 < 1; i6++) {
            internalWorkbook.getOrCreateLinkTable().checkExternSheet(i6);
        }
        SSTRecord sSTRecord = new SSTRecord();
        internalWorkbook.sst = sSTRecord;
        arrayList.add(sSTRecord);
        arrayList.add(createExtendedSST());
        arrayList.add(EOFRecord.instance);
        POILogger pOILogger2 = log;
        if (pOILogger2.check(POILogger.DEBUG)) {
            pOILogger2.log(DEBUG, (Object) "exit create new workbook from scratch");
        }
        return internalWorkbook;
    }

    public NameRecord getSpecificBuiltinRecord(byte b, int i) {
        return getOrCreateLinkTable().getSpecificBuiltinRecord(b, i);
    }

    public void removeBuiltinRecord(byte b, int i) {
        this.linkTable.removeBuiltinRecord(b, i);
    }

    public int getNumRecords() {
        return this.records.size();
    }

    public FontRecord getFontRecordAt(int i) {
        int i2 = i > 4 ? i - 1 : i;
        if (i2 <= this.numfonts - 1) {
            WorkbookRecordList workbookRecordList = this.records;
            return (FontRecord) workbookRecordList.get((workbookRecordList.getFontpos() - (this.numfonts - 1)) + i2);
        }
        throw new ArrayIndexOutOfBoundsException("There are only " + this.numfonts + " font records, you asked for " + i);
    }

    public int getFontIndex(FontRecord fontRecord) {
        int i = 0;
        while (i <= this.numfonts) {
            WorkbookRecordList workbookRecordList = this.records;
            if (((FontRecord) workbookRecordList.get((workbookRecordList.getFontpos() - (this.numfonts - 1)) + i)) == fontRecord) {
                return i > 3 ? i + 1 : i;
            }
            i++;
        }
        throw new IllegalArgumentException("Could not find that font!");
    }

    public FontRecord createNewFont() {
        FontRecord createFont = createFont();
        WorkbookRecordList workbookRecordList = this.records;
        workbookRecordList.add(workbookRecordList.getFontpos() + 1, createFont);
        WorkbookRecordList workbookRecordList2 = this.records;
        workbookRecordList2.setFontpos(workbookRecordList2.getFontpos() + 1);
        this.numfonts++;
        return createFont;
    }

    public void removeFontRecord(FontRecord fontRecord) {
        this.records.remove((Object) fontRecord);
        this.numfonts--;
    }

    public int getNumberOfFontRecords() {
        return this.numfonts;
    }

    public void setSheetBof(int i, int i2) {
        POILogger pOILogger = log;
        if (pOILogger.check(POILogger.DEBUG)) {
            pOILogger.log(DEBUG, (Object) "setting bof for sheetnum =", (Object) Integer.valueOf(i), (Object) " at pos=", (Object) Integer.valueOf(i2));
        }
        checkSheets(i);
        getBoundSheetRec(i).setPositionOfBof(i2);
    }

    private BoundSheetRecord getBoundSheetRec(int i) {
        return this.boundsheets.get(i);
    }

    public BackupRecord getBackupRecord() {
        WorkbookRecordList workbookRecordList = this.records;
        return (BackupRecord) workbookRecordList.get(workbookRecordList.getBackuppos());
    }

    public void setSheetName(int i, String str) {
        checkSheets(i);
        if (str.length() > 31) {
            str = str.substring(0, 31);
        }
        this.boundsheets.get(i).setSheetname(str);
    }

    public boolean doesContainsSheetName(String str, int i) {
        if (str.length() > 31) {
            str = str.substring(0, 31);
        }
        for (int i2 = 0; i2 < this.boundsheets.size(); i2++) {
            BoundSheetRecord boundSheetRec = getBoundSheetRec(i2);
            if (i != i2) {
                String sheetname = boundSheetRec.getSheetname();
                if (sheetname.length() > 31) {
                    sheetname = sheetname.substring(0, 31);
                }
                if (str.equalsIgnoreCase(sheetname)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setSheetOrder(String str, int i) {
        int sheetIndex = getSheetIndex(str);
        List<BoundSheetRecord> list = this.boundsheets;
        list.add(i, list.remove(sheetIndex));
    }

    public String getSheetName(int i) {
        return getBoundSheetRec(i).getSheetname();
    }

    public boolean isSheetHidden(int i) {
        return getBoundSheetRec(i).isHidden();
    }

    public boolean isSheetVeryHidden(int i) {
        return getBoundSheetRec(i).isVeryHidden();
    }

    public void setSheetHidden(int i, boolean z) {
        getBoundSheetRec(i).setHidden(z);
    }

    public void setSheetHidden(int i, int i2) {
        BoundSheetRecord boundSheetRec = getBoundSheetRec(i);
        boolean z = false;
        boolean z2 = true;
        if (i2 != 0) {
            if (i2 == 1) {
                z = true;
            } else {
                if (i2 != 2) {
                    throw new IllegalArgumentException("Invalid hidden flag " + i2 + " given, must be 0, 1 or 2");
                }
                boundSheetRec.setHidden(z);
                boundSheetRec.setVeryHidden(z2);
            }
        }
        z2 = false;
        boundSheetRec.setHidden(z);
        boundSheetRec.setVeryHidden(z2);
    }

    public int getSheetIndex(String str) {
        for (int i = 0; i < this.boundsheets.size(); i++) {
            if (getSheetName(i).equalsIgnoreCase(str)) {
                return i;
            }
        }
        return -1;
    }

    private void checkSheets(int i) {
        if (this.boundsheets.size() <= i) {
            if (this.boundsheets.size() + 1 > i) {
                BoundSheetRecord createBoundSheet = createBoundSheet(i);
                WorkbookRecordList workbookRecordList = this.records;
                workbookRecordList.add(workbookRecordList.getBspos() + 1, createBoundSheet);
                WorkbookRecordList workbookRecordList2 = this.records;
                workbookRecordList2.setBspos(workbookRecordList2.getBspos() + 1);
                this.boundsheets.add(createBoundSheet);
                getOrCreateLinkTable().checkExternSheet(i);
                fixTabIdRecord();
                return;
            }
            throw new RuntimeException("Sheet number out of bounds!");
        } else if (this.records.getTabpos() > 0) {
            WorkbookRecordList workbookRecordList3 = this.records;
            if (((TabIdRecord) workbookRecordList3.get(workbookRecordList3.getTabpos()))._tabids.length < this.boundsheets.size()) {
                fixTabIdRecord();
            }
        }
    }

    public void removeSheet(int i) {
        if (this.boundsheets.size() > i) {
            WorkbookRecordList workbookRecordList = this.records;
            workbookRecordList.remove((workbookRecordList.getBspos() - (this.boundsheets.size() - 1)) + i);
            this.boundsheets.remove(i);
            fixTabIdRecord();
        }
        int i2 = i + 1;
        for (int i3 = 0; i3 < getNumNames(); i3++) {
            NameRecord nameRecord = getNameRecord(i3);
            if (nameRecord.getSheetNumber() == i2) {
                nameRecord.setSheetNumber(0);
            } else if (nameRecord.getSheetNumber() > i2) {
                nameRecord.setSheetNumber(nameRecord.getSheetNumber() - 1);
            }
        }
    }

    private void fixTabIdRecord() {
        WorkbookRecordList workbookRecordList = this.records;
        TabIdRecord tabIdRecord = (TabIdRecord) workbookRecordList.get(workbookRecordList.getTabpos());
        int size = this.boundsheets.size();
        short[] sArr = new short[size];
        for (short s = 0; s < size; s = (short) (s + 1)) {
            sArr[s] = s;
        }
        tabIdRecord.setTabIdArray(sArr);
    }

    public int getNumSheets() {
        POILogger pOILogger = log;
        if (pOILogger.check(POILogger.DEBUG)) {
            pOILogger.log(DEBUG, (Object) "getNumSheets=", (Object) Integer.valueOf(this.boundsheets.size()));
        }
        return this.boundsheets.size();
    }

    public int getNumExFormats() {
        POILogger pOILogger = log;
        if (pOILogger.check(POILogger.DEBUG)) {
            pOILogger.log(DEBUG, (Object) "getXF=", (Object) Integer.valueOf(this.numxfs));
        }
        return this.numxfs;
    }

    public ExtendedFormatRecord getExFormatAt(int i) {
        Record record = this.records.get((this.records.getXfpos() - (this.numxfs - 1)) + i);
        if (record instanceof ExtendedFormatRecord) {
            return (ExtendedFormatRecord) record;
        }
        return null;
    }

    public void removeExFormatRecord(ExtendedFormatRecord extendedFormatRecord) {
        this.records.remove((Object) extendedFormatRecord);
        this.numxfs--;
    }

    public ExtendedFormatRecord createCellXF() {
        ExtendedFormatRecord createExtendedFormat = createExtendedFormat();
        WorkbookRecordList workbookRecordList = this.records;
        workbookRecordList.add(workbookRecordList.getXfpos() + 1, createExtendedFormat);
        WorkbookRecordList workbookRecordList2 = this.records;
        workbookRecordList2.setXfpos(workbookRecordList2.getXfpos() + 1);
        this.numxfs++;
        return createExtendedFormat;
    }

    public StyleRecord getStyleRecord(int i) {
        for (int xfpos = this.records.getXfpos(); xfpos < this.records.size(); xfpos++) {
            Record record = this.records.get(xfpos);
            if (!(record instanceof ExtendedFormatRecord) && (record instanceof StyleRecord)) {
                StyleRecord styleRecord = (StyleRecord) record;
                if (styleRecord.getXFIndex() == i) {
                    return styleRecord;
                }
            }
        }
        return null;
    }

    public StyleRecord createStyleRecord(int i) {
        StyleRecord styleRecord = new StyleRecord();
        styleRecord.setXFIndex(i);
        int i2 = -1;
        for (int xfpos = this.records.getXfpos(); xfpos < this.records.size() && i2 == -1; xfpos++) {
            Record record = this.records.get(xfpos);
            if (!(record instanceof ExtendedFormatRecord) && !(record instanceof StyleRecord)) {
                i2 = xfpos;
            }
        }
        if (i2 != -1) {
            this.records.add(i2, styleRecord);
            return styleRecord;
        }
        throw new IllegalStateException("No XF Records found!");
    }

    public int addSSTString(UnicodeString unicodeString) {
        POILogger pOILogger = log;
        if (pOILogger.check(POILogger.DEBUG)) {
            pOILogger.log(DEBUG, (Object) "insert to sst string='", (Object) unicodeString);
        }
        if (this.sst == null) {
            insertSST();
        }
        return this.sst.addString(unicodeString);
    }

    public int getSSTUniqueStringSize() {
        return this.sst.getNumUniqueStrings();
    }

    public UnicodeString getSSTString(int i) {
        if (this.sst == null) {
            insertSST();
        }
        UnicodeString string = this.sst.getString(i);
        POILogger pOILogger = log;
        if (pOILogger.check(POILogger.DEBUG)) {
            pOILogger.log(DEBUG, (Object) "Returning SST for index=", (Object) Integer.valueOf(i), (Object) " String= ", (Object) string);
        }
        return string;
    }

    public void insertSST() {
        POILogger pOILogger = log;
        if (pOILogger.check(POILogger.DEBUG)) {
            pOILogger.log(DEBUG, (Object) "creating new SST via insertSST!");
        }
        this.sst = new SSTRecord();
        WorkbookRecordList workbookRecordList = this.records;
        workbookRecordList.add(workbookRecordList.size() - 1, createExtendedSST());
        WorkbookRecordList workbookRecordList2 = this.records;
        workbookRecordList2.add(workbookRecordList2.size() - 2, this.sst);
    }

    /*  JADX ERROR: IF instruction can be used only in fallback mode
        jadx.core.utils.exceptions.CodegenException: IF instruction can be used only in fallback mode
        	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:579)
        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:485)
        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
        	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:221)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
        	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        	at java.util.ArrayList.forEach(ArrayList.java:1259)
        	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
        	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:483)
        	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:472)
        	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
        	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
        	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:485)
        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
        	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
        	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
        	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
        	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
        	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
        */
    public int serialize(int r10, byte[] r11) {
        /*
            r9 = this;
            com.app.office.fc.util.POILogger r0 = log
            int r1 = com.app.office.fc.util.POILogger.DEBUG
            boolean r1 = r0.check(r1)
            if (r1 == 0) goto L_0x0011
            int r1 = DEBUG
            java.lang.String r2 = "Serializing Workbook with offsets"
            r0.log((int) r1, (java.lang.Object) r2)
        L_0x0011:
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
        L_0x0017:
            com.app.office.fc.hssf.model.WorkbookRecordList r6 = r9.records
            int r6 = r6.size()
            if (r2 >= r6) goto L_0x006a
            com.app.office.fc.hssf.model.WorkbookRecordList r6 = r9.records
            com.app.office.fc.hssf.record.Record r6 = r6.get(r2)
            boolean r7 = r6 instanceof com.app.office.fc.hssf.record.SSTRecord
            if (r7 == 0) goto L_0x002d
            r0 = r6
            com.app.office.fc.hssf.record.SSTRecord r0 = (com.app.office.fc.hssf.record.SSTRecord) r0
            r4 = r3
        L_0x002d:
            short r7 = r6.getSid()
            r8 = 255(0xff, float:3.57E-43)
            if (r7 != r8) goto L_0x003d
            if (r0 == 0) goto L_0x003d
            int r6 = r4 + r10
            com.app.office.fc.hssf.record.ExtSSTRecord r6 = r0.createExtSSTRecord(r6)
        L_0x003d:
            boolean r7 = r6 instanceof com.app.office.fc.hssf.record.BoundSheetRecord
            if (r7 == 0) goto L_0x0060
            if (r5 != 0) goto L_0x005e
            r5 = 0
            r6 = 0
        L_0x0045:
            java.util.List<com.app.office.fc.hssf.record.BoundSheetRecord> r7 = r9.boundsheets
            int r7 = r7.size()
            if (r5 >= r7) goto L_0x005c
            com.app.office.fc.hssf.record.BoundSheetRecord r7 = r9.getBoundSheetRec(r5)
            int r8 = r3 + r10
            int r8 = r8 + r6
            int r7 = r7.serialize(r8, r11)
            int r6 = r6 + r7
            int r5 = r5 + 1
            goto L_0x0045
        L_0x005c:
            r5 = 1
            goto L_0x0066
        L_0x005e:
            r6 = 0
            goto L_0x0066
        L_0x0060:
            int r7 = r3 + r10
            int r6 = r6.serialize(r7, r11)
        L_0x0066:
            int r3 = r3 + r6
            int r2 = r2 + 1
            goto L_0x0017
        L_0x006a:
            com.app.office.fc.util.POILogger r10 = log
            int r11 = com.app.office.fc.util.POILogger.DEBUG
            boolean r11 = r10.check(r11)
            if (r11 == 0) goto L_0x007b
            int r11 = DEBUG
            java.lang.String r0 = "Exiting serialize workbook"
            r10.log((int) r11, (java.lang.Object) r0)
        L_0x007b:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.model.InternalWorkbook.serialize(int, byte[]):int");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: com.app.office.fc.hssf.record.Record} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: com.app.office.fc.hssf.record.SSTRecord} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getSize() {
        /*
            r6 = this;
            r0 = 0
            r1 = 0
            r2 = r1
            r1 = 0
        L_0x0004:
            com.app.office.fc.hssf.model.WorkbookRecordList r3 = r6.records
            int r3 = r3.size()
            if (r0 >= r3) goto L_0x0030
            com.app.office.fc.hssf.model.WorkbookRecordList r3 = r6.records
            com.app.office.fc.hssf.record.Record r3 = r3.get(r0)
            boolean r4 = r3 instanceof com.app.office.fc.hssf.record.SSTRecord
            if (r4 == 0) goto L_0x0019
            r2 = r3
            com.app.office.fc.hssf.record.SSTRecord r2 = (com.app.office.fc.hssf.record.SSTRecord) r2
        L_0x0019:
            short r4 = r3.getSid()
            r5 = 255(0xff, float:3.57E-43)
            if (r4 != r5) goto L_0x0028
            if (r2 == 0) goto L_0x0028
            int r3 = r2.calcExtSSTRecordSize()
            goto L_0x002c
        L_0x0028:
            int r3 = r3.getRecordSize()
        L_0x002c:
            int r1 = r1 + r3
            int r0 = r0 + 1
            goto L_0x0004
        L_0x0030:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.model.InternalWorkbook.getSize():int");
    }

    private static BOFRecord createBOF() {
        BOFRecord bOFRecord = new BOFRecord();
        bOFRecord.setVersion(BOFRecord.VERSION);
        bOFRecord.setType(5);
        bOFRecord.setBuild(BOFRecord.BUILD);
        bOFRecord.setBuildYear(BOFRecord.BUILD_YEAR);
        bOFRecord.setHistoryBitMask(65);
        bOFRecord.setRequiredVersion(6);
        return bOFRecord;
    }

    private static MMSRecord createMMS() {
        MMSRecord mMSRecord = new MMSRecord();
        mMSRecord.setAddMenuCount((byte) 0);
        mMSRecord.setDelMenuCount((byte) 0);
        return mMSRecord;
    }

    private static WriteAccessRecord createWriteAccess() {
        WriteAccessRecord writeAccessRecord = new WriteAccessRecord();
        try {
            writeAccessRecord.setUsername(System.getProperty("user.name"));
        } catch (AccessControlException unused) {
            writeAccessRecord.setUsername("POI");
        }
        return writeAccessRecord;
    }

    private static CodepageRecord createCodepage() {
        CodepageRecord codepageRecord = new CodepageRecord();
        codepageRecord.setCodepage(1200);
        return codepageRecord;
    }

    private static DSFRecord createDSF() {
        return new DSFRecord(false);
    }

    private static TabIdRecord createTabId() {
        return new TabIdRecord();
    }

    private static FnGroupCountRecord createFnGroupCount() {
        FnGroupCountRecord fnGroupCountRecord = new FnGroupCountRecord();
        fnGroupCountRecord.setCount(14);
        return fnGroupCountRecord;
    }

    private static WindowProtectRecord createWindowProtect() {
        return new WindowProtectRecord(false);
    }

    private static ProtectRecord createProtect() {
        return new ProtectRecord(false);
    }

    private static PasswordRecord createPassword() {
        return new PasswordRecord(0);
    }

    private static ProtectionRev4Record createProtectionRev4() {
        return new ProtectionRev4Record(false);
    }

    private static PasswordRev4Record createPasswordRev4() {
        return new PasswordRev4Record(0);
    }

    private static WindowOneRecord createWindowOne() {
        WindowOneRecord windowOneRecord = new WindowOneRecord();
        windowOneRecord.setHorizontalHold(360);
        windowOneRecord.setVerticalHold(EscherProperties.BLIP__PICTURELINE);
        windowOneRecord.setWidth(14940);
        windowOneRecord.setHeight(9150);
        windowOneRecord.setOptions(56);
        windowOneRecord.setActiveSheetIndex(0);
        windowOneRecord.setFirstVisibleTab(0);
        windowOneRecord.setNumSelectedTabs(1);
        windowOneRecord.setTabWidthRatio(600);
        return windowOneRecord;
    }

    private static BackupRecord createBackup() {
        BackupRecord backupRecord = new BackupRecord();
        backupRecord.setBackup(0);
        return backupRecord;
    }

    private static HideObjRecord createHideObj() {
        HideObjRecord hideObjRecord = new HideObjRecord();
        hideObjRecord.setHideObj(0);
        return hideObjRecord;
    }

    private static DateWindow1904Record createDateWindow1904() {
        DateWindow1904Record dateWindow1904Record = new DateWindow1904Record();
        dateWindow1904Record.setWindowing(0);
        return dateWindow1904Record;
    }

    private static PrecisionRecord createPrecision() {
        PrecisionRecord precisionRecord = new PrecisionRecord();
        precisionRecord.setFullPrecision(true);
        return precisionRecord;
    }

    private static RefreshAllRecord createRefreshAll() {
        return new RefreshAllRecord(false);
    }

    private static BookBoolRecord createBookBool() {
        BookBoolRecord bookBoolRecord = new BookBoolRecord();
        bookBoolRecord.setSaveLinkValues(0);
        return bookBoolRecord;
    }

    private static FontRecord createFont() {
        FontRecord fontRecord = new FontRecord();
        fontRecord.setFontHeight(200);
        fontRecord.setAttributes(0);
        fontRecord.setColorPaletteIndex(Short.MAX_VALUE);
        fontRecord.setBoldWeight(400);
        fontRecord.setFontName(HSSFFont.FONT_ARIAL);
        return fontRecord;
    }

    private static FormatRecord createFormat(int i) {
        switch (i) {
            case 0:
                return new FormatRecord(5, BuiltinFormats.getBuiltinFormat(5));
            case 1:
                return new FormatRecord(6, BuiltinFormats.getBuiltinFormat(6));
            case 2:
                return new FormatRecord(7, BuiltinFormats.getBuiltinFormat(7));
            case 3:
                return new FormatRecord(8, BuiltinFormats.getBuiltinFormat(8));
            case 4:
                return new FormatRecord(42, BuiltinFormats.getBuiltinFormat(42));
            case 5:
                return new FormatRecord(41, BuiltinFormats.getBuiltinFormat(41));
            case 6:
                return new FormatRecord(44, BuiltinFormats.getBuiltinFormat(44));
            case 7:
                return new FormatRecord(43, BuiltinFormats.getBuiltinFormat(43));
            default:
                throw new IllegalArgumentException("Unexpected id " + i);
        }
    }

    private static ExtendedFormatRecord createExtendedFormat(int i) {
        ExtendedFormatRecord extendedFormatRecord = new ExtendedFormatRecord();
        switch (i) {
            case 0:
                extendedFormatRecord.setFontIndex(0);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(0);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 1:
                extendedFormatRecord.setFontIndex(1);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-3072);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 2:
                extendedFormatRecord.setFontIndex(1);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-3072);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 3:
                extendedFormatRecord.setFontIndex(2);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-3072);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 4:
                extendedFormatRecord.setFontIndex(2);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-3072);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 5:
                extendedFormatRecord.setFontIndex(0);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-3072);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 6:
                extendedFormatRecord.setFontIndex(0);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-3072);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 7:
                extendedFormatRecord.setFontIndex(0);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-3072);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 8:
                extendedFormatRecord.setFontIndex(0);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-3072);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 9:
                extendedFormatRecord.setFontIndex(0);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-3072);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 10:
                extendedFormatRecord.setFontIndex(0);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-3072);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 11:
                extendedFormatRecord.setFontIndex(0);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-3072);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 12:
                extendedFormatRecord.setFontIndex(0);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-3072);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 13:
                extendedFormatRecord.setFontIndex(0);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-3072);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 14:
                extendedFormatRecord.setFontIndex(0);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-3072);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 15:
                extendedFormatRecord.setFontIndex(0);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(1);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(0);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 16:
                extendedFormatRecord.setFontIndex(1);
                extendedFormatRecord.setFormatIndex(43);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-2048);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 17:
                extendedFormatRecord.setFontIndex(1);
                extendedFormatRecord.setFormatIndex(41);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-2048);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 18:
                extendedFormatRecord.setFontIndex(1);
                extendedFormatRecord.setFormatIndex(44);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-2048);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 19:
                extendedFormatRecord.setFontIndex(1);
                extendedFormatRecord.setFormatIndex(42);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-2048);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 20:
                extendedFormatRecord.setFontIndex(1);
                extendedFormatRecord.setFormatIndex(9);
                extendedFormatRecord.setCellOptions(-11);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(-2048);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 21:
                extendedFormatRecord.setFontIndex(5);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(1);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(2048);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 22:
                extendedFormatRecord.setFontIndex(6);
                extendedFormatRecord.setFormatIndex(0);
                extendedFormatRecord.setCellOptions(1);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(23552);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 23:
                extendedFormatRecord.setFontIndex(0);
                extendedFormatRecord.setFormatIndex(49);
                extendedFormatRecord.setCellOptions(1);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(23552);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 24:
                extendedFormatRecord.setFontIndex(0);
                extendedFormatRecord.setFormatIndex(8);
                extendedFormatRecord.setCellOptions(1);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(23552);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
            case 25:
                extendedFormatRecord.setFontIndex(6);
                extendedFormatRecord.setFormatIndex(8);
                extendedFormatRecord.setCellOptions(1);
                extendedFormatRecord.setAlignmentOptions(32);
                extendedFormatRecord.setIndentionOptions(23552);
                extendedFormatRecord.setBorderOptions(0);
                extendedFormatRecord.setPaletteOptions(0);
                extendedFormatRecord.setAdtlPaletteOptions(0);
                extendedFormatRecord.setFillPaletteOptions(8384);
                break;
        }
        return extendedFormatRecord;
    }

    private static ExtendedFormatRecord createExtendedFormat() {
        ExtendedFormatRecord extendedFormatRecord = new ExtendedFormatRecord();
        extendedFormatRecord.setFontIndex(0);
        extendedFormatRecord.setFormatIndex(0);
        extendedFormatRecord.setCellOptions(1);
        extendedFormatRecord.setAlignmentOptions(32);
        extendedFormatRecord.setIndentionOptions(0);
        extendedFormatRecord.setBorderOptions(0);
        extendedFormatRecord.setPaletteOptions(0);
        extendedFormatRecord.setAdtlPaletteOptions(0);
        extendedFormatRecord.setFillPaletteOptions(8384);
        extendedFormatRecord.setTopBorderPaletteIdx(8);
        extendedFormatRecord.setBottomBorderPaletteIdx(8);
        extendedFormatRecord.setLeftBorderPaletteIdx(8);
        extendedFormatRecord.setRightBorderPaletteIdx(8);
        return extendedFormatRecord;
    }

    private static StyleRecord createStyle(int i) {
        StyleRecord styleRecord = new StyleRecord();
        if (i == 0) {
            styleRecord.setXFIndex(16);
            styleRecord.setBuiltinStyle(3);
            styleRecord.setOutlineStyleLevel(-1);
        } else if (i == 1) {
            styleRecord.setXFIndex(17);
            styleRecord.setBuiltinStyle(6);
            styleRecord.setOutlineStyleLevel(-1);
        } else if (i == 2) {
            styleRecord.setXFIndex(18);
            styleRecord.setBuiltinStyle(4);
            styleRecord.setOutlineStyleLevel(-1);
        } else if (i == 3) {
            styleRecord.setXFIndex(19);
            styleRecord.setBuiltinStyle(7);
            styleRecord.setOutlineStyleLevel(-1);
        } else if (i == 4) {
            styleRecord.setXFIndex(0);
            styleRecord.setBuiltinStyle(0);
            styleRecord.setOutlineStyleLevel(-1);
        } else if (i == 5) {
            styleRecord.setXFIndex(20);
            styleRecord.setBuiltinStyle(5);
            styleRecord.setOutlineStyleLevel(-1);
        }
        return styleRecord;
    }

    private static PaletteRecord createPalette() {
        return new PaletteRecord();
    }

    private static UseSelFSRecord createUseSelFS() {
        return new UseSelFSRecord(false);
    }

    private static BoundSheetRecord createBoundSheet(int i) {
        return new BoundSheetRecord("Sheet" + (i + 1));
    }

    private static CountryRecord createCountry() {
        CountryRecord countryRecord = new CountryRecord();
        countryRecord.setDefaultCountry(1);
        if (Locale.getDefault().toString().equals("ru_RU")) {
            countryRecord.setCurrentCountry(7);
        } else {
            countryRecord.setCurrentCountry(1);
        }
        return countryRecord;
    }

    private static ExtSSTRecord createExtendedSST() {
        ExtSSTRecord extSSTRecord = new ExtSSTRecord();
        extSSTRecord.setNumStringsPerBucket(8);
        return extSSTRecord;
    }

    private LinkTable getOrCreateLinkTable() {
        if (this.linkTable == null) {
            this.linkTable = new LinkTable((short) getNumSheets(), this.records);
        }
        return this.linkTable;
    }

    public String findSheetNameFromExternSheet(int i) {
        int indexToInternalSheet = this.linkTable.getIndexToInternalSheet(i);
        if (indexToInternalSheet >= 0 && indexToInternalSheet < this.boundsheets.size()) {
            return getSheetName(indexToInternalSheet);
        }
        return "";
    }

    public EvaluationWorkbook.ExternalSheet getExternalSheet(int i) {
        String[] externalBookAndSheetName = this.linkTable.getExternalBookAndSheetName(i);
        if (externalBookAndSheetName == null) {
            return null;
        }
        return new EvaluationWorkbook.ExternalSheet(externalBookAndSheetName[0], externalBookAndSheetName[1]);
    }

    public EvaluationWorkbook.ExternalName getExternalName(int i, int i2) {
        String resolveNameXText = this.linkTable.resolveNameXText(i, i2);
        if (resolveNameXText == null) {
            return null;
        }
        return new EvaluationWorkbook.ExternalName(resolveNameXText, i2, this.linkTable.resolveNameXIx(i, i2));
    }

    public int getSheetIndexFromExternSheetIndex(int i) {
        return this.linkTable.getSheetIndexFromExternSheetIndex(i);
    }

    public short checkExternSheet(int i) {
        return (short) getOrCreateLinkTable().checkExternSheet(i);
    }

    public int getExternalSheetIndex(String str, String str2) {
        return getOrCreateLinkTable().getExternalSheetIndex(str, str2);
    }

    public int getNumNames() {
        LinkTable linkTable2 = this.linkTable;
        if (linkTable2 == null) {
            return 0;
        }
        return linkTable2.getNumNames();
    }

    public NameRecord getNameRecord(int i) {
        return this.linkTable.getNameRecord(i);
    }

    public NameCommentRecord getNameCommentRecord(NameRecord nameRecord) {
        return this.commentRecords.get(nameRecord.getNameText());
    }

    public NameRecord createName() {
        return addName(new NameRecord());
    }

    public NameRecord addName(NameRecord nameRecord) {
        getOrCreateLinkTable().addName(nameRecord);
        return nameRecord;
    }

    public NameRecord createBuiltInName(byte b, int i) {
        if (i < 0 || i + 1 > 32767) {
            throw new IllegalArgumentException("Sheet number [" + i + "]is not valid ");
        }
        NameRecord nameRecord = new NameRecord(b, i);
        if (!this.linkTable.nameAlreadyExists(nameRecord)) {
            addName(nameRecord);
            return nameRecord;
        }
        throw new RuntimeException("Builtin (" + b + ") already exists for sheet (" + i + ")");
    }

    public void removeName(int i) {
        if (this.linkTable.getNumNames() > i) {
            this.records.remove(findFirstRecordLocBySid(24) + i);
            this.linkTable.removeName(i);
        }
    }

    public void updateNameCommentRecordCache(NameCommentRecord nameCommentRecord) {
        if (this.commentRecords.containsValue(nameCommentRecord)) {
            Iterator<Map.Entry<String, NameCommentRecord>> it = this.commentRecords.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry next = it.next();
                if (((NameCommentRecord) next.getValue()).equals(nameCommentRecord)) {
                    this.commentRecords.remove(next.getKey());
                    break;
                }
            }
        }
        this.commentRecords.put(nameCommentRecord.getNameText(), nameCommentRecord);
    }

    public short getFormat(String str, boolean z) {
        int createFormat;
        Iterator<FormatRecord> it = this.formats.iterator();
        while (true) {
            if (it.hasNext()) {
                FormatRecord next = it.next();
                if (next.getFormatString().equals(str)) {
                    createFormat = next.getIndexCode();
                    break;
                }
            } else if (!z) {
                return -1;
            } else {
                createFormat = createFormat(str);
            }
        }
        return (short) createFormat;
    }

    public List<FormatRecord> getFormats() {
        return this.formats;
    }

    public int createFormat(String str) {
        int i = this.maxformatid;
        int i2 = 164;
        if (i >= 164) {
            i2 = i + 1;
        }
        this.maxformatid = i2;
        FormatRecord formatRecord = new FormatRecord(i2, str);
        int i3 = 0;
        while (i3 < this.records.size() && this.records.get(i3).getSid() != 1054) {
            i3++;
        }
        int size = i3 + this.formats.size();
        this.formats.add(formatRecord);
        this.records.add(size, formatRecord);
        return this.maxformatid;
    }

    public Record findFirstRecordBySid(short s) {
        Iterator<Record> it = this.records.iterator();
        while (it.hasNext()) {
            Record next = it.next();
            if (next.getSid() == s) {
                return next;
            }
        }
        return null;
    }

    public int findFirstRecordLocBySid(short s) {
        Iterator<Record> it = this.records.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().getSid() == s) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public Record findNextRecordBySid(short s, int i) {
        Iterator<Record> it = this.records.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            Record next = it.next();
            if (next.getSid() == s) {
                int i3 = i2 + 1;
                if (i2 == i) {
                    return next;
                }
                i2 = i3;
            }
        }
        return null;
    }

    public List<HyperlinkRecord> getHyperlinks() {
        return this.hyperlinks;
    }

    public List<Record> getRecords() {
        return this.records.getRecords();
    }

    public boolean isUsing1904DateWindowing() {
        return this.uses1904datewindowing;
    }

    public PaletteRecord getCustomPalette() {
        int palettepos = this.records.getPalettepos();
        if (palettepos != -1) {
            Record record = this.records.get(palettepos);
            if (record instanceof PaletteRecord) {
                return (PaletteRecord) record;
            }
            throw new RuntimeException("InternalError: Expected PaletteRecord but got a '" + record + "'");
        }
        PaletteRecord createPalette = createPalette();
        this.records.add(1, createPalette);
        this.records.setPalettepos(1);
        return createPalette;
    }

    public DrawingManager2 findDrawingGroup() {
        DrawingManager2 drawingManager2 = this.drawingManager;
        if (drawingManager2 != null) {
            return drawingManager2;
        }
        Iterator<Record> it = this.records.iterator();
        while (true) {
            EscherDggRecord escherDggRecord = null;
            if (it.hasNext()) {
                Record next = it.next();
                if (next instanceof DrawingGroupRecord) {
                    DrawingGroupRecord drawingGroupRecord = (DrawingGroupRecord) next;
                    drawingGroupRecord.processChildRecords();
                    EscherContainerRecord escherContainer = drawingGroupRecord.getEscherContainer();
                    if (escherContainer == null) {
                        continue;
                    } else {
                        Iterator<EscherRecord> childIterator = escherContainer.getChildIterator();
                        EscherContainerRecord escherContainerRecord = null;
                        while (childIterator.hasNext()) {
                            EscherRecord next2 = childIterator.next();
                            if (next2 instanceof EscherDggRecord) {
                                escherDggRecord = (EscherDggRecord) next2;
                            } else if (next2.getRecordId() == -4095) {
                                escherContainerRecord = (EscherContainerRecord) next2;
                            }
                        }
                        if (escherDggRecord != null) {
                            this.drawingManager = new DrawingManager2(escherDggRecord);
                            if (escherContainerRecord != null) {
                                for (EscherRecord next3 : escherContainerRecord.getChildRecords()) {
                                    if (next3 instanceof EscherBSERecord) {
                                        this.escherBSERecords.add((EscherBSERecord) next3);
                                    }
                                }
                            }
                            return this.drawingManager;
                        }
                    }
                }
            } else {
                int findFirstRecordLocBySid = findFirstRecordLocBySid(DrawingGroupRecord.sid);
                if (findFirstRecordLocBySid != -1) {
                    EscherContainerRecord escherContainerRecord2 = null;
                    for (EscherRecord next4 : ((DrawingGroupRecord) this.records.get(findFirstRecordLocBySid)).getEscherRecords()) {
                        if (next4 instanceof EscherDggRecord) {
                            escherDggRecord = (EscherDggRecord) next4;
                        } else if (next4.getRecordId() == -4095) {
                            escherContainerRecord2 = (EscherContainerRecord) next4;
                        }
                    }
                    if (escherDggRecord != null) {
                        this.drawingManager = new DrawingManager2(escherDggRecord);
                        if (escherContainerRecord2 != null) {
                            for (EscherRecord next5 : escherContainerRecord2.getChildRecords()) {
                                if (next5 instanceof EscherBSERecord) {
                                    this.escherBSERecords.add((EscherBSERecord) next5);
                                }
                            }
                        }
                    }
                }
                return this.drawingManager;
            }
        }
    }

    public void createDrawingGroup() {
        if (this.drawingManager == null) {
            EscherContainerRecord escherContainerRecord = new EscherContainerRecord();
            EscherDggRecord escherDggRecord = new EscherDggRecord();
            EscherOptRecord escherOptRecord = new EscherOptRecord();
            EscherSplitMenuColorsRecord escherSplitMenuColorsRecord = new EscherSplitMenuColorsRecord();
            escherContainerRecord.setRecordId(EscherContainerRecord.DGG_CONTAINER);
            escherContainerRecord.setOptions(15);
            escherDggRecord.setRecordId(EscherDggRecord.RECORD_ID);
            escherDggRecord.setOptions(0);
            escherDggRecord.setShapeIdMax(1024);
            escherDggRecord.setNumShapesSaved(0);
            escherDggRecord.setDrawingsSaved(0);
            escherDggRecord.setFileIdClusters(new EscherDggRecord.FileIdCluster[0]);
            this.drawingManager = new DrawingManager2(escherDggRecord);
            EscherContainerRecord escherContainerRecord2 = null;
            if (this.escherBSERecords.size() > 0) {
                escherContainerRecord2 = new EscherContainerRecord();
                escherContainerRecord2.setRecordId(EscherContainerRecord.BSTORE_CONTAINER);
                escherContainerRecord2.setOptions((short) (15 | (this.escherBSERecords.size() << 4)));
                for (EscherBSERecord addChildRecord : this.escherBSERecords) {
                    escherContainerRecord2.addChildRecord(addChildRecord);
                }
            }
            escherOptRecord.setRecordId(EscherOptRecord.RECORD_ID);
            escherOptRecord.setOptions(51);
            escherOptRecord.addEscherProperty(new EscherBoolProperty(EscherProperties.TEXT__SIZE_TEXT_TO_FIT_SHAPE, 524296));
            escherOptRecord.addEscherProperty(new EscherRGBProperty(EscherProperties.FILL__FILLCOLOR, 134217793));
            escherOptRecord.addEscherProperty(new EscherRGBProperty(EscherProperties.LINESTYLE__COLOR, 134217792));
            escherSplitMenuColorsRecord.setRecordId(EscherSplitMenuColorsRecord.RECORD_ID);
            escherSplitMenuColorsRecord.setOptions(64);
            escherSplitMenuColorsRecord.setColor1(134217741);
            escherSplitMenuColorsRecord.setColor2(134217740);
            escherSplitMenuColorsRecord.setColor3(134217751);
            escherSplitMenuColorsRecord.setColor4(268435703);
            escherContainerRecord.addChildRecord(escherDggRecord);
            if (escherContainerRecord2 != null) {
                escherContainerRecord.addChildRecord(escherContainerRecord2);
            }
            escherContainerRecord.addChildRecord(escherOptRecord);
            escherContainerRecord.addChildRecord(escherSplitMenuColorsRecord);
            int findFirstRecordLocBySid = findFirstRecordLocBySid(DrawingGroupRecord.sid);
            if (findFirstRecordLocBySid == -1) {
                DrawingGroupRecord drawingGroupRecord = new DrawingGroupRecord();
                drawingGroupRecord.addEscherRecord(escherContainerRecord);
                getRecords().add(findFirstRecordLocBySid(CountryRecord.sid) + 1, drawingGroupRecord);
                return;
            }
            DrawingGroupRecord drawingGroupRecord2 = new DrawingGroupRecord();
            drawingGroupRecord2.addEscherRecord(escherContainerRecord);
            getRecords().set(findFirstRecordLocBySid, drawingGroupRecord2);
        }
    }

    public WindowOneRecord getWindowOne() {
        return this.windowOne;
    }

    public EscherBSERecord getBSERecord(int i) {
        int i2 = i - 1;
        if (i2 < 0 || i2 >= this.escherBSERecords.size()) {
            return null;
        }
        return this.escherBSERecords.get(i2);
    }

    public int addBSERecord(EscherBSERecord escherBSERecord) {
        EscherContainerRecord escherContainerRecord;
        createDrawingGroup();
        this.escherBSERecords.add(escherBSERecord);
        EscherContainerRecord escherContainerRecord2 = (EscherContainerRecord) ((DrawingGroupRecord) getRecords().get(findFirstRecordLocBySid(DrawingGroupRecord.sid))).getEscherRecord(0);
        if (escherContainerRecord2.getChild(1).getRecordId() == -4095) {
            escherContainerRecord = (EscherContainerRecord) escherContainerRecord2.getChild(1);
        } else {
            EscherContainerRecord escherContainerRecord3 = new EscherContainerRecord();
            escherContainerRecord3.setRecordId(EscherContainerRecord.BSTORE_CONTAINER);
            List<EscherRecord> childRecords = escherContainerRecord2.getChildRecords();
            childRecords.add(1, escherContainerRecord3);
            escherContainerRecord2.setChildRecords(childRecords);
            escherContainerRecord = escherContainerRecord3;
        }
        escherContainerRecord.setOptions((short) ((this.escherBSERecords.size() << 4) | 15));
        escherContainerRecord.addChildRecord(escherBSERecord);
        return this.escherBSERecords.size();
    }

    public DrawingManager2 getDrawingManager() {
        return this.drawingManager;
    }

    public WriteProtectRecord getWriteProtect() {
        if (this.writeProtect == null) {
            this.writeProtect = new WriteProtectRecord();
            int i = 0;
            while (i < this.records.size() && !(this.records.get(i) instanceof BOFRecord)) {
                i++;
            }
            this.records.add(i + 1, this.writeProtect);
        }
        return this.writeProtect;
    }

    public WriteAccessRecord getWriteAccess() {
        if (this.writeAccess == null) {
            this.writeAccess = createWriteAccess();
            int i = 0;
            while (i < this.records.size() && !(this.records.get(i) instanceof InterfaceEndRecord)) {
                i++;
            }
            this.records.add(i + 1, this.writeAccess);
        }
        return this.writeAccess;
    }

    public FileSharingRecord getFileSharing() {
        if (this.fileShare == null) {
            this.fileShare = new FileSharingRecord();
            int i = 0;
            while (i < this.records.size() && !(this.records.get(i) instanceof WriteAccessRecord)) {
                i++;
            }
            this.records.add(i + 1, this.fileShare);
        }
        return this.fileShare;
    }

    public boolean isWriteProtected() {
        if (this.fileShare != null && getFileSharing().getReadOnly() == 1) {
            return true;
        }
        return false;
    }

    public void writeProtectWorkbook(String str, String str2) {
        FileSharingRecord fileSharing = getFileSharing();
        WriteAccessRecord writeAccess2 = getWriteAccess();
        getWriteProtect();
        fileSharing.setReadOnly(1);
        fileSharing.setPassword(FileSharingRecord.hashPassword(str));
        fileSharing.setUsername(str2);
        writeAccess2.setUsername(str2);
    }

    public void unwriteProtectWorkbook() {
        this.records.remove((Object) this.fileShare);
        this.records.remove((Object) this.writeProtect);
        this.fileShare = null;
        this.writeProtect = null;
    }

    public String resolveNameXText(int i, int i2) {
        return this.linkTable.resolveNameXText(i, i2);
    }

    public NameXPtg getNameXPtg(String str, UDFFinder uDFFinder) {
        LinkTable orCreateLinkTable = getOrCreateLinkTable();
        NameXPtg nameXPtg = orCreateLinkTable.getNameXPtg(str);
        return (nameXPtg != null || uDFFinder.findFunction(str) == null) ? nameXPtg : orCreateLinkTable.addNameXPtg(str);
    }

    public void cloneDrawings(InternalSheet internalSheet) {
        EscherContainerRecord escherContainer;
        EscherSimpleProperty escherSimpleProperty;
        findDrawingGroup();
        DrawingManager2 drawingManager2 = this.drawingManager;
        if (drawingManager2 != null && internalSheet.aggregateDrawingRecords(drawingManager2, false) != -1 && (escherContainer = ((EscherAggregate) internalSheet.findFirstRecordBySid(EscherAggregate.sid)).getEscherContainer()) != null) {
            EscherDggRecord dgg = this.drawingManager.getDgg();
            short findNewDrawingGroupId = this.drawingManager.findNewDrawingGroupId();
            dgg.addCluster(findNewDrawingGroupId, 0);
            dgg.setDrawingsSaved(dgg.getDrawingsSaved() + 1);
            EscherDgRecord escherDgRecord = null;
            Iterator<EscherRecord> childIterator = escherContainer.getChildIterator();
            while (childIterator.hasNext()) {
                EscherRecord next = childIterator.next();
                if (next instanceof EscherDgRecord) {
                    EscherDgRecord escherDgRecord2 = (EscherDgRecord) next;
                    escherDgRecord2.setOptions((short) (findNewDrawingGroupId << 4));
                    escherDgRecord = escherDgRecord2;
                } else if (next instanceof EscherContainerRecord) {
                    Iterator<EscherRecord> it = ((EscherContainerRecord) next).getChildRecords().iterator();
                    while (it.hasNext()) {
                        for (EscherRecord next2 : ((EscherContainerRecord) it.next()).getChildRecords()) {
                            short recordId = next2.getRecordId();
                            if (recordId == -4086) {
                                int allocateShapeId = this.drawingManager.allocateShapeId((short) findNewDrawingGroupId, escherDgRecord);
                                escherDgRecord.setNumShapes(escherDgRecord.getNumShapes() - 1);
                                ((EscherSpRecord) next2).setShapeId(allocateShapeId);
                            } else if (recordId == -4085 && (escherSimpleProperty = (EscherSimpleProperty) ((EscherOptRecord) next2).lookup(MetaDo.META_SETROP2)) != null) {
                                EscherBSERecord bSERecord = getBSERecord(escherSimpleProperty.getPropertyValue());
                                bSERecord.setRef(bSERecord.getRef() + 1);
                            }
                        }
                    }
                }
            }
        }
    }

    public NameRecord cloneFilter(int i, int i2) {
        NameRecord nameRecord = getNameRecord(i);
        short checkExternSheet = checkExternSheet(i2);
        Ptg[] nameDefinition = nameRecord.getNameDefinition();
        for (int i3 = 0; i3 < nameDefinition.length; i3++) {
            Ptg ptg = nameDefinition[i3];
            if (ptg instanceof Area3DPtg) {
                Area3DPtg area3DPtg = (Area3DPtg) ((OperandPtg) ptg).copy();
                area3DPtg.setExternSheetIndex(checkExternSheet);
                nameDefinition[i3] = area3DPtg;
            } else if (ptg instanceof Ref3DPtg) {
                Ref3DPtg ref3DPtg = (Ref3DPtg) ((OperandPtg) ptg).copy();
                ref3DPtg.setExternSheetIndex(checkExternSheet);
                nameDefinition[i3] = ref3DPtg;
            }
        }
        NameRecord createBuiltInName = createBuiltInName((byte) 13, i2 + 1);
        createBuiltInName.setNameDefinition(nameDefinition);
        createBuiltInName.setHidden(true);
        return createBuiltInName;
    }

    public void updateNamesAfterCellShift(FormulaShifter formulaShifter) {
        for (int i = 0; i < getNumNames(); i++) {
            NameRecord nameRecord = getNameRecord(i);
            Ptg[] nameDefinition = nameRecord.getNameDefinition();
            if (formulaShifter.adjustFormula(nameDefinition, nameRecord.getSheetNumber())) {
                nameRecord.setNameDefinition(nameDefinition);
            }
        }
    }

    public RecalcIdRecord getRecalcId() {
        RecalcIdRecord recalcIdRecord = (RecalcIdRecord) findFirstRecordBySid(449);
        if (recalcIdRecord != null) {
            return recalcIdRecord;
        }
        RecalcIdRecord recalcIdRecord2 = new RecalcIdRecord();
        this.records.add(findFirstRecordLocBySid(CountryRecord.sid) + 1, recalcIdRecord2);
        return recalcIdRecord2;
    }
}
