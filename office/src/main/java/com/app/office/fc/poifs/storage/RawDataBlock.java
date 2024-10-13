package com.app.office.fc.poifs.storage;

import com.itextpdf.text.html.HtmlTags;
import com.app.office.fc.util.IOUtils;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.io.IOException;
import java.io.InputStream;

public class RawDataBlock implements ListManagedBlock {
    private static POILogger log = POILogFactory.getLogger(RawDataBlock.class);
    private byte[] _data;
    private boolean _eof;
    private boolean _hasData;

    public RawDataBlock(InputStream inputStream) throws IOException {
        this(inputStream, 512);
    }

    public RawDataBlock(InputStream inputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        this._data = bArr;
        int readFully = IOUtils.readFully(inputStream, bArr);
        this._hasData = readFully > 0;
        if (readFully == -1) {
            this._eof = true;
        } else if (readFully != i) {
            this._eof = true;
            StringBuilder sb = new StringBuilder();
            sb.append(" byte");
            sb.append(readFully == 1 ? "" : HtmlTags.S);
            String sb2 = sb.toString();
            POILogger pOILogger = log;
            int i2 = POILogger.ERROR;
            pOILogger.log(i2, (Object) "Unable to read entire block; " + readFully + sb2 + " read before EOF; expected " + i + " bytes. Your document was either written by software that ignores the spec, or has been truncated!");
        } else {
            this._eof = false;
        }
    }

    public boolean eof() {
        return this._eof;
    }

    public boolean hasData() {
        return this._hasData;
    }

    public String toString() {
        return "RawDataBlock of size " + this._data.length;
    }

    public byte[] getData() throws IOException {
        if (hasData()) {
            return this._data;
        }
        throw new IOException("Cannot return empty data");
    }

    public int getBigBlockSize() {
        return this._data.length;
    }
}
