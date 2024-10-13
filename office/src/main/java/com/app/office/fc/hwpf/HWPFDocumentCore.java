package com.app.office.fc.hwpf;

import com.app.office.fc.EncryptedDocumentException;
import com.app.office.fc.fs.filesystem.CFBFileSystem;
import com.app.office.fc.hwpf.model.CHPBinTable;
import com.app.office.fc.hwpf.model.FileInformationBlock;
import com.app.office.fc.hwpf.model.FontTable;
import com.app.office.fc.hwpf.model.ListTables;
import com.app.office.fc.hwpf.model.PAPBinTable;
import com.app.office.fc.hwpf.model.SectionTable;
import com.app.office.fc.hwpf.model.StyleSheet;
import com.app.office.fc.hwpf.model.TextPieceTable;
import com.app.office.fc.hwpf.usermodel.ObjectPoolImpl;
import com.app.office.fc.hwpf.usermodel.ObjectsPool;
import com.app.office.fc.hwpf.usermodel.Range;
import com.app.office.fc.util.Internal;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public abstract class HWPFDocumentCore {
    protected static final String STREAM_OBJECT_POOL = "ObjectPool";
    protected static final String STREAM_WORD_DOCUMENT = "WordDocument";
    protected CHPBinTable _cbt;
    protected FileInformationBlock _fib;
    protected FontTable _ft;
    protected ListTables _lt;
    protected byte[] _mainStream;
    protected ObjectPoolImpl _objectPool;
    protected PAPBinTable _pbt;
    protected StyleSheet _ss;
    protected SectionTable _st;
    protected CFBFileSystem cfbFS;

    public abstract Range getOverallRange();

    public abstract Range getRange();

    @Internal
    public abstract StringBuilder getText();

    public abstract TextPieceTable getTextTable();

    public static CFBFileSystem verifyAndBuildPOIFS(InputStream inputStream) throws IOException {
        PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream, 6);
        byte[] bArr = new byte[6];
        pushbackInputStream.read(bArr);
        if (bArr[0] == 123 && bArr[1] == 92 && bArr[2] == 114 && bArr[3] == 116 && bArr[4] == 102) {
            throw new IllegalArgumentException("The document is really a RTF file");
        }
        pushbackInputStream.unread(bArr);
        return new CFBFileSystem(pushbackInputStream);
    }

    public HWPFDocumentCore(InputStream inputStream) throws IOException {
        CFBFileSystem verifyAndBuildPOIFS = verifyAndBuildPOIFS(inputStream);
        this.cfbFS = verifyAndBuildPOIFS;
        this._mainStream = verifyAndBuildPOIFS.getPropertyRawData(STREAM_WORD_DOCUMENT);
        FileInformationBlock fileInformationBlock = new FileInformationBlock(this._mainStream);
        this._fib = fileInformationBlock;
        if (fileInformationBlock.isFEncrypted()) {
            throw new EncryptedDocumentException("Cannot process encrypted office files!");
        }
    }

    public String getDocumentText() {
        return getText().toString();
    }

    public CHPBinTable getCharacterTable() {
        return this._cbt;
    }

    public PAPBinTable getParagraphTable() {
        return this._pbt;
    }

    public SectionTable getSectionTable() {
        return this._st;
    }

    public StyleSheet getStyleSheet() {
        return this._ss;
    }

    public ListTables getListTables() {
        return this._lt;
    }

    public FontTable getFontTable() {
        return this._ft;
    }

    public FileInformationBlock getFileInformationBlock() {
        return this._fib;
    }

    public ObjectsPool getObjectsPool() {
        return this._objectPool;
    }
}
