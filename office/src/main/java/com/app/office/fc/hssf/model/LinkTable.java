package com.app.office.fc.hssf.model;

import com.app.office.fc.hssf.formula.ptg.ErrPtg;
import com.app.office.fc.hssf.formula.ptg.NameXPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.record.CRNCountRecord;
import com.app.office.fc.hssf.record.CRNRecord;
import com.app.office.fc.hssf.record.CountryRecord;
import com.app.office.fc.hssf.record.ExternSheetRecord;
import com.app.office.fc.hssf.record.ExternalNameRecord;
import com.app.office.fc.hssf.record.NameCommentRecord;
import com.app.office.fc.hssf.record.NameRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.SupBookRecord;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class LinkTable {
    private final List<NameRecord> _definedNames;
    private final ExternSheetRecord _externSheetRecord;
    private ExternalBookBlock[] _externalBookBlocks;
    private final int _recordCount;
    private final WorkbookRecordList _workbookRecordList;

    private static final class CRNBlock {
        private final CRNCountRecord _countRecord;
        private final CRNRecord[] _crns;

        public CRNBlock(RecordStream recordStream) {
            CRNCountRecord cRNCountRecord = (CRNCountRecord) recordStream.getNext();
            this._countRecord = cRNCountRecord;
            int numberOfCRNs = cRNCountRecord.getNumberOfCRNs();
            CRNRecord[] cRNRecordArr = new CRNRecord[numberOfCRNs];
            for (int i = 0; i < numberOfCRNs; i++) {
                cRNRecordArr[i] = (CRNRecord) recordStream.getNext();
            }
            this._crns = cRNRecordArr;
        }

        public CRNRecord[] getCrns() {
            return (CRNRecord[]) this._crns.clone();
        }
    }

    private static final class ExternalBookBlock {
        private final CRNBlock[] _crnBlocks;
        private final SupBookRecord _externalBookRecord;
        private ExternalNameRecord[] _externalNameRecords;

        public ExternalBookBlock(RecordStream recordStream) {
            this._externalBookRecord = (SupBookRecord) recordStream.getNext();
            ArrayList arrayList = new ArrayList();
            while (recordStream.peekNextClass() == ExternalNameRecord.class) {
                arrayList.add(recordStream.getNext());
            }
            ExternalNameRecord[] externalNameRecordArr = new ExternalNameRecord[arrayList.size()];
            this._externalNameRecords = externalNameRecordArr;
            arrayList.toArray(externalNameRecordArr);
            arrayList.clear();
            while (recordStream.peekNextClass() == CRNCountRecord.class) {
                arrayList.add(new CRNBlock(recordStream));
            }
            CRNBlock[] cRNBlockArr = new CRNBlock[arrayList.size()];
            this._crnBlocks = cRNBlockArr;
            arrayList.toArray(cRNBlockArr);
        }

        public ExternalBookBlock(int i) {
            this._externalBookRecord = SupBookRecord.createInternalReferences((short) i);
            this._externalNameRecords = new ExternalNameRecord[0];
            this._crnBlocks = new CRNBlock[0];
        }

        public ExternalBookBlock() {
            this._externalBookRecord = SupBookRecord.createAddInFunctions();
            this._externalNameRecords = new ExternalNameRecord[0];
            this._crnBlocks = new CRNBlock[0];
        }

        public SupBookRecord getExternalBookRecord() {
            return this._externalBookRecord;
        }

        public String getNameText(int i) {
            return this._externalNameRecords[i].getText();
        }

        public int getNameIx(int i) {
            return this._externalNameRecords[i].getIx();
        }

        public int getIndexOfName(String str) {
            int i = 0;
            while (true) {
                ExternalNameRecord[] externalNameRecordArr = this._externalNameRecords;
                if (i >= externalNameRecordArr.length) {
                    return -1;
                }
                if (externalNameRecordArr[i].getText().equalsIgnoreCase(str)) {
                    return i;
                }
                i++;
            }
        }

        public int getNumberOfNames() {
            return this._externalNameRecords.length;
        }

        public int addExternalName(ExternalNameRecord externalNameRecord) {
            ExternalNameRecord[] externalNameRecordArr = this._externalNameRecords;
            int length = externalNameRecordArr.length + 1;
            ExternalNameRecord[] externalNameRecordArr2 = new ExternalNameRecord[length];
            System.arraycopy(externalNameRecordArr, 0, externalNameRecordArr2, 0, externalNameRecordArr.length);
            externalNameRecordArr2[length - 1] = externalNameRecord;
            this._externalNameRecords = externalNameRecordArr2;
            return externalNameRecordArr2.length - 1;
        }
    }

    public LinkTable(List list, int i, WorkbookRecordList workbookRecordList, Map<String, NameCommentRecord> map) {
        this._workbookRecordList = workbookRecordList;
        RecordStream recordStream = new RecordStream(list, i);
        ArrayList arrayList = new ArrayList();
        while (recordStream.peekNextClass() == SupBookRecord.class) {
            arrayList.add(new ExternalBookBlock(recordStream));
        }
        ExternalBookBlock[] externalBookBlockArr = new ExternalBookBlock[arrayList.size()];
        this._externalBookBlocks = externalBookBlockArr;
        arrayList.toArray(externalBookBlockArr);
        arrayList.clear();
        if (this._externalBookBlocks.length <= 0) {
            this._externSheetRecord = null;
        } else if (recordStream.peekNextClass() != ExternSheetRecord.class) {
            this._externSheetRecord = null;
        } else {
            this._externSheetRecord = readExtSheetRecord(recordStream);
        }
        this._definedNames = new ArrayList();
        while (true) {
            Class<? extends Record> peekNextClass = recordStream.peekNextClass();
            if (peekNextClass == NameRecord.class) {
                this._definedNames.add((NameRecord) recordStream.getNext());
            } else if (peekNextClass == NameCommentRecord.class) {
                NameCommentRecord nameCommentRecord = (NameCommentRecord) recordStream.getNext();
                map.put(nameCommentRecord.getNameText(), nameCommentRecord);
            } else {
                int countRead = recordStream.getCountRead();
                this._recordCount = countRead;
                this._workbookRecordList.getRecords().addAll(list.subList(i, countRead + i));
                return;
            }
        }
    }

    private static ExternSheetRecord readExtSheetRecord(RecordStream recordStream) {
        ArrayList arrayList = new ArrayList(2);
        while (recordStream.peekNextClass() == ExternSheetRecord.class) {
            arrayList.add((ExternSheetRecord) recordStream.getNext());
        }
        int size = arrayList.size();
        if (size < 1) {
            throw new RuntimeException("Expected an EXTERNSHEET record but got (" + recordStream.peekNextClass().getName() + ")");
        } else if (size == 1) {
            return (ExternSheetRecord) arrayList.get(0);
        } else {
            ExternSheetRecord[] externSheetRecordArr = new ExternSheetRecord[size];
            arrayList.toArray(externSheetRecordArr);
            return ExternSheetRecord.combine(externSheetRecordArr);
        }
    }

    public LinkTable(int i, WorkbookRecordList workbookRecordList) {
        this._workbookRecordList = workbookRecordList;
        this._definedNames = new ArrayList();
        this._externalBookBlocks = new ExternalBookBlock[]{new ExternalBookBlock(i)};
        ExternSheetRecord externSheetRecord = new ExternSheetRecord();
        this._externSheetRecord = externSheetRecord;
        this._recordCount = 2;
        SupBookRecord externalBookRecord = this._externalBookBlocks[0].getExternalBookRecord();
        int findFirstRecordLocBySid = findFirstRecordLocBySid(CountryRecord.sid);
        if (findFirstRecordLocBySid >= 0) {
            int i2 = findFirstRecordLocBySid + 1;
            workbookRecordList.add(i2, externSheetRecord);
            workbookRecordList.add(i2, externalBookRecord);
            return;
        }
        throw new RuntimeException("CountryRecord not found");
    }

    public int getRecordCount() {
        return this._recordCount;
    }

    public NameRecord getSpecificBuiltinRecord(byte b, int i) {
        for (NameRecord next : this._definedNames) {
            if (next.getBuiltInName() == b && next.getSheetNumber() == i) {
                return next;
            }
        }
        return null;
    }

    public void removeBuiltinRecord(byte b, int i) {
        NameRecord specificBuiltinRecord = getSpecificBuiltinRecord(b, i);
        if (specificBuiltinRecord != null) {
            this._definedNames.remove(specificBuiltinRecord);
        }
    }

    public int getNumNames() {
        return this._definedNames.size();
    }

    public NameRecord getNameRecord(int i) {
        return this._definedNames.get(i);
    }

    public void addName(NameRecord nameRecord) {
        this._definedNames.add(nameRecord);
        int findFirstRecordLocBySid = findFirstRecordLocBySid(23);
        if (findFirstRecordLocBySid == -1) {
            findFirstRecordLocBySid = findFirstRecordLocBySid(SupBookRecord.sid);
        }
        if (findFirstRecordLocBySid == -1) {
            findFirstRecordLocBySid = findFirstRecordLocBySid(CountryRecord.sid);
        }
        this._workbookRecordList.add(findFirstRecordLocBySid + this._definedNames.size(), nameRecord);
    }

    public void removeName(int i) {
        this._definedNames.remove(i);
    }

    public boolean nameAlreadyExists(NameRecord nameRecord) {
        for (int numNames = getNumNames() - 1; numNames >= 0; numNames--) {
            NameRecord nameRecord2 = getNameRecord(numNames);
            if (nameRecord2 != nameRecord && isDuplicatedNames(nameRecord, nameRecord2)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDuplicatedNames(NameRecord nameRecord, NameRecord nameRecord2) {
        return nameRecord2.getNameText().equalsIgnoreCase(nameRecord.getNameText()) && isSameSheetNames(nameRecord, nameRecord2);
    }

    private static boolean isSameSheetNames(NameRecord nameRecord, NameRecord nameRecord2) {
        return nameRecord2.getSheetNumber() == nameRecord.getSheetNumber();
    }

    public String[] getExternalBookAndSheetName(int i) {
        SupBookRecord externalBookRecord = this._externalBookBlocks[this._externSheetRecord.getExtbookIndexFromRefIndex(i)].getExternalBookRecord();
        String str = null;
        if (!externalBookRecord.isExternalReferences()) {
            return null;
        }
        int firstSheetIndexFromRefIndex = this._externSheetRecord.getFirstSheetIndexFromRefIndex(i);
        if (firstSheetIndexFromRefIndex >= 0) {
            str = externalBookRecord.getSheetNames()[firstSheetIndexFromRefIndex];
        }
        return new String[]{externalBookRecord.getURL(), str};
    }

    public int getExternalSheetIndex(String str, String str2) {
        SupBookRecord supBookRecord;
        int i = 0;
        while (true) {
            ExternalBookBlock[] externalBookBlockArr = this._externalBookBlocks;
            if (i >= externalBookBlockArr.length) {
                supBookRecord = null;
                i = -1;
                break;
            }
            supBookRecord = externalBookBlockArr[i].getExternalBookRecord();
            if (supBookRecord.isExternalReferences() && str.equals(supBookRecord.getURL())) {
                break;
            }
            i++;
        }
        if (supBookRecord != null) {
            int sheetIndex = getSheetIndex(supBookRecord.getSheetNames(), str2);
            int refIxForSheet = this._externSheetRecord.getRefIxForSheet(i, sheetIndex);
            if (refIxForSheet >= 0) {
                return refIxForSheet;
            }
            throw new RuntimeException("ExternSheetRecord does not contain combination (" + i + ", " + sheetIndex + ")");
        }
        throw new RuntimeException("No external workbook with name '" + str + "'");
    }

    private static int getSheetIndex(String[] strArr, String str) {
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equals(str)) {
                return i;
            }
        }
        throw new RuntimeException("External workbook does not contain sheet '" + str + "'");
    }

    public int getIndexToInternalSheet(int i) {
        return this._externSheetRecord.getFirstSheetIndexFromRefIndex(i);
    }

    public int getSheetIndexFromExternSheetIndex(int i) {
        if (i >= this._externSheetRecord.getNumOfRefs()) {
            return -1;
        }
        return this._externSheetRecord.getFirstSheetIndexFromRefIndex(i);
    }

    public int checkExternSheet(int i) {
        int i2 = 0;
        while (true) {
            ExternalBookBlock[] externalBookBlockArr = this._externalBookBlocks;
            if (i2 >= externalBookBlockArr.length) {
                i2 = -1;
                break;
            } else if (externalBookBlockArr[i2].getExternalBookRecord().isInternalReferences()) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 >= 0) {
            int refIxForSheet = this._externSheetRecord.getRefIxForSheet(i2, i);
            if (refIxForSheet >= 0) {
                return refIxForSheet;
            }
            return this._externSheetRecord.addRef(i2, i, i);
        }
        throw new RuntimeException("Could not find 'internal references' EXTERNALBOOK");
    }

    private int findFirstRecordLocBySid(short s) {
        Iterator<Record> it = this._workbookRecordList.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().getSid() == s) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public String resolveNameXText(int i, int i2) {
        return this._externalBookBlocks[this._externSheetRecord.getExtbookIndexFromRefIndex(i)].getNameText(i2);
    }

    public int resolveNameXIx(int i, int i2) {
        return this._externalBookBlocks[this._externSheetRecord.getExtbookIndexFromRefIndex(i)].getNameIx(i2);
    }

    public NameXPtg getNameXPtg(String str) {
        int findRefIndexFromExtBookIndex;
        int i = 0;
        while (true) {
            ExternalBookBlock[] externalBookBlockArr = this._externalBookBlocks;
            if (i >= externalBookBlockArr.length) {
                return null;
            }
            int indexOfName = externalBookBlockArr[i].getIndexOfName(str);
            if (indexOfName >= 0 && (findRefIndexFromExtBookIndex = findRefIndexFromExtBookIndex(i)) >= 0) {
                return new NameXPtg(findRefIndexFromExtBookIndex, indexOfName);
            }
            i++;
        }
    }

    public NameXPtg addNameXPtg(String str) {
        ExternalBookBlock externalBookBlock;
        int i = 0;
        int i2 = 0;
        while (true) {
            ExternalBookBlock[] externalBookBlockArr = this._externalBookBlocks;
            if (i2 >= externalBookBlockArr.length) {
                i2 = -1;
                externalBookBlock = null;
                break;
            } else if (externalBookBlockArr[i2].getExternalBookRecord().isAddInFunctions()) {
                externalBookBlock = this._externalBookBlocks[i2];
                break;
            } else {
                i2++;
            }
        }
        if (externalBookBlock == null) {
            externalBookBlock = new ExternalBookBlock();
            ExternalBookBlock[] externalBookBlockArr2 = this._externalBookBlocks;
            int length = externalBookBlockArr2.length + 1;
            ExternalBookBlock[] externalBookBlockArr3 = new ExternalBookBlock[length];
            System.arraycopy(externalBookBlockArr2, 0, externalBookBlockArr3, 0, externalBookBlockArr2.length);
            externalBookBlockArr3[length - 1] = externalBookBlock;
            this._externalBookBlocks = externalBookBlockArr3;
            i2 = externalBookBlockArr3.length - 1;
            this._workbookRecordList.add(findFirstRecordLocBySid(23), externalBookBlock.getExternalBookRecord());
            this._externSheetRecord.addRef(this._externalBookBlocks.length - 1, -2, -2);
        }
        ExternalNameRecord externalNameRecord = new ExternalNameRecord();
        externalNameRecord.setText(str);
        externalNameRecord.setParsedExpression(new Ptg[]{ErrPtg.REF_INVALID});
        int addExternalName = externalBookBlock.addExternalName(externalNameRecord);
        Iterator<Record> it = this._workbookRecordList.iterator();
        while (it.hasNext()) {
            Record next = it.next();
            if ((next instanceof SupBookRecord) && ((SupBookRecord) next).isAddInFunctions()) {
                break;
            }
            i++;
        }
        this._workbookRecordList.add(i + externalBookBlock.getNumberOfNames(), externalNameRecord);
        return new NameXPtg(this._externSheetRecord.getRefIxForSheet(i2, -2), addExternalName);
    }

    private int findRefIndexFromExtBookIndex(int i) {
        return this._externSheetRecord.findRefIndexFromExtBookIndex(i);
    }
}
