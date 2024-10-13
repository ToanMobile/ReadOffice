package com.app.office.fc.hwpf;

import com.app.office.fc.hwpf.model.BookmarksTables;
import com.app.office.fc.hwpf.model.CHPBinTable;
import com.app.office.fc.hwpf.model.CPSplitCalculator;
import com.app.office.fc.hwpf.model.ComplexFileTable;
import com.app.office.fc.hwpf.model.EscherRecordHolder;
import com.app.office.fc.hwpf.model.FSPADocumentPart;
import com.app.office.fc.hwpf.model.FSPATable;
import com.app.office.fc.hwpf.model.FieldsTables;
import com.app.office.fc.hwpf.model.FontTable;
import com.app.office.fc.hwpf.model.ListTables;
import com.app.office.fc.hwpf.model.PAPBinTable;
import com.app.office.fc.hwpf.model.PicturesTable;
import com.app.office.fc.hwpf.model.PlcfTxbxBkd;
import com.app.office.fc.hwpf.model.SectionTable;
import com.app.office.fc.hwpf.model.ShapesTable;
import com.app.office.fc.hwpf.model.StyleSheet;
import com.app.office.fc.hwpf.model.SubdocumentType;
import com.app.office.fc.hwpf.model.TextPieceTable;
import com.app.office.fc.hwpf.usermodel.Bookmarks;
import com.app.office.fc.hwpf.usermodel.BookmarksImpl;
import com.app.office.fc.hwpf.usermodel.Fields;
import com.app.office.fc.hwpf.usermodel.FieldsImpl;
import com.app.office.fc.hwpf.usermodel.HWPFList;
import com.app.office.fc.hwpf.usermodel.OfficeDrawings;
import com.app.office.fc.hwpf.usermodel.OfficeDrawingsImpl;
import com.app.office.fc.hwpf.usermodel.Range;
import com.app.office.fc.util.Internal;
import java.io.IOException;
import java.io.InputStream;

public final class HWPFDocument extends HWPFDocumentCore {
    private static final String PROPERTY_PRESERVE_BIN_TABLES = "com.wxiwei.fc.hwpf.preserveBinTables";
    private static final String PROPERTY_PRESERVE_TEXT_TABLE = "com.wxiwei.fc.hwpf.preserveTextTable";
    private static final String STREAM_DATA = "Data";
    private static final String STREAM_TABLE_0 = "0Table";
    private static final String STREAM_TABLE_1 = "1Table";
    protected Bookmarks _bookmarks;
    protected BookmarksTables _bookmarksTables;
    protected ComplexFileTable _cft;
    protected byte[] _dataStream;
    protected EscherRecordHolder _escherRecordHolder;
    protected Fields _fields;
    protected FieldsTables _fieldsTables;
    private FSPATable _fspaHeaders;
    private FSPATable _fspaMain;
    @Deprecated
    protected ShapesTable _officeArts;
    protected OfficeDrawingsImpl _officeDrawingsHeaders;
    protected OfficeDrawingsImpl _officeDrawingsMain;
    protected PicturesTable _pictures;
    protected byte[] _tableStream;
    protected StringBuilder _text;
    protected PlcfTxbxBkd txbxBkd;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public HWPFDocument(InputStream inputStream) throws IOException {
        super(inputStream);
        CPSplitCalculator cPSplitCalculator = new CPSplitCalculator(this._fib);
        if (this._fib.getNFib() >= 106) {
            String str = this._fib.isFWhichTblStm() ? STREAM_TABLE_1 : STREAM_TABLE_0;
            try {
                this._tableStream = this.cfbFS.getPropertyRawData(str);
                this._fib.fillVariableFields(this._mainStream, this._tableStream);
                try {
                    this._dataStream = this.cfbFS.getPropertyRawData(STREAM_DATA);
                } catch (Exception unused) {
                    this._dataStream = new byte[0];
                }
                ComplexFileTable complexFileTable = new ComplexFileTable(this._mainStream, this._tableStream, this._fib.getFcClx(), 0);
                this._cft = complexFileTable;
                TextPieceTable textPieceTable = complexFileTable.getTextPieceTable();
                this._cbt = new CHPBinTable(this._mainStream, this._tableStream, this._fib.getFcPlcfbteChpx(), this._fib.getLcbPlcfbteChpx(), textPieceTable);
                this._pbt = new PAPBinTable(this._mainStream, this._tableStream, this._dataStream, this._fib.getFcPlcfbtePapx(), this._fib.getLcbPlcfbtePapx(), textPieceTable);
                this._text = textPieceTable.getText();
                this._cbt.rebuild(this._cft);
                this._pbt.rebuild(this._text, this._cft);
                this._fspaHeaders = new FSPATable(this._tableStream, this._fib, FSPADocumentPart.HEADER);
                this._fspaMain = new FSPATable(this._tableStream, this._fib, FSPADocumentPart.MAIN);
                if (this._fib.getFcDggInfo() != 0) {
                    this._escherRecordHolder = new EscherRecordHolder(this._tableStream, this._fib.getFcDggInfo(), this._fib.getLcbDggInfo());
                } else {
                    this._escherRecordHolder = new EscherRecordHolder();
                }
                this._pictures = new PicturesTable(this, this._dataStream, this._mainStream, this._fspaMain, this._escherRecordHolder);
                this._officeArts = new ShapesTable(this._tableStream, this._fib);
                this._officeDrawingsHeaders = new OfficeDrawingsImpl(this._fspaHeaders, this._escherRecordHolder, this._mainStream);
                this._officeDrawingsMain = new OfficeDrawingsImpl(this._fspaMain, this._escherRecordHolder, this._mainStream);
                this._st = new SectionTable(this._mainStream, this._tableStream, this._fib.getFcPlcfsed(), this._fib.getLcbPlcfsed(), 0, textPieceTable, cPSplitCalculator);
                this._ss = new StyleSheet(this._tableStream, this._fib.getFcStshf());
                this._ft = new FontTable(this._tableStream, this._fib.getFcSttbfffn(), this._fib.getLcbSttbfffn());
                int fcPlcfLst = this._fib.getFcPlcfLst();
                this._fib.getFcPlfLfo();
                if (!(fcPlcfLst == 0 || this._fib.getLcbPlcfLst() == 0)) {
                    this._lt = new ListTables(this._tableStream, this._fib.getFcPlcfLst(), this._fib.getFcPlfLfo());
                }
                BookmarksTables bookmarksTables = new BookmarksTables(this._tableStream, this._fib);
                this._bookmarksTables = bookmarksTables;
                this._bookmarks = new BookmarksImpl(bookmarksTables);
                FieldsTables fieldsTables = new FieldsTables(this._tableStream, this._fib);
                this._fieldsTables = fieldsTables;
                this._fields = new FieldsImpl(fieldsTables);
                this.txbxBkd = new PlcfTxbxBkd(this._tableStream, this._fib.getFcPlcfTxbxBkd(), this._fib.getLcbPlcfTxbxBkd());
            } catch (Exception unused2) {
                throw new IllegalStateException("Table Stream '" + str + "' wasn't found - Either the document is corrupt, or is Word95 (or earlier)");
            }
        } else if (this._fib.getNFib() == 0) {
            throw null;
        } else {
            throw new OldWordFileFormatException("The document is too old - Word 95 or older. Try HWPFOldDocument instead?");
        }
    }

    @Internal
    public TextPieceTable getTextTable() {
        return this._cft.getTextPieceTable();
    }

    @Internal
    public StringBuilder getText() {
        return this._text;
    }

    public Range getOverallRange() {
        return new Range(0, this._text.length(), (HWPFDocumentCore) this);
    }

    public Range getRange() {
        return getRange(SubdocumentType.MAIN);
    }

    private Range getRange(SubdocumentType subdocumentType) {
        int i = 0;
        for (SubdocumentType subdocumentType2 : SubdocumentType.ORDERED) {
            int subdocumentTextStreamLength = getFileInformationBlock().getSubdocumentTextStreamLength(subdocumentType2);
            if (subdocumentType == subdocumentType2) {
                return new Range(i, subdocumentTextStreamLength + i, (HWPFDocumentCore) this);
            }
            i += subdocumentTextStreamLength;
        }
        throw new UnsupportedOperationException("Subdocument type not supported: " + subdocumentType);
    }

    public Range getFootnoteRange() {
        return getRange(SubdocumentType.FOOTNOTE);
    }

    public Range getEndnoteRange() {
        return getRange(SubdocumentType.ENDNOTE);
    }

    public Range getCommentsRange() {
        return getRange(SubdocumentType.ANNOTATION);
    }

    public Range getMainTextboxRange() {
        return getRange(SubdocumentType.TEXTBOX);
    }

    public Range getHeaderStoryRange() {
        return getRange(SubdocumentType.HEADER);
    }

    public int characterLength() {
        return this._text.length();
    }

    public PicturesTable getPicturesTable() {
        return this._pictures;
    }

    @Internal
    public EscherRecordHolder getEscherRecordHolder() {
        return this._escherRecordHolder;
    }

    @Internal
    @Deprecated
    public ShapesTable getShapesTable() {
        return this._officeArts;
    }

    public OfficeDrawings getOfficeDrawingsHeaders() {
        return this._officeDrawingsHeaders;
    }

    public OfficeDrawings getOfficeDrawingsMain() {
        return this._officeDrawingsMain;
    }

    public Bookmarks getBookmarks() {
        return this._bookmarks;
    }

    @Internal
    @Deprecated
    public FieldsTables getFieldsTables() {
        return this._fieldsTables;
    }

    public Fields getFields() {
        return this._fields;
    }

    @Internal
    public byte[] getDataStream() {
        return this._dataStream;
    }

    @Internal
    public byte[] getTableStream() {
        return this._tableStream;
    }

    public int registerList(HWPFList hWPFList) {
        if (this._lt == null) {
            this._lt = new ListTables();
        }
        return this._lt.addList(hWPFList.getListData(), hWPFList.getOverride());
    }

    public void delete(int i, int i2) {
        new Range(i, i2 + i, (HWPFDocumentCore) this).delete();
    }

    public int getTextboxStart(int i) {
        return this.txbxBkd.getCharPosition(i);
    }

    public int getTextboxEnd(int i) {
        return this.txbxBkd.getCharPosition(i + 1);
    }
}
