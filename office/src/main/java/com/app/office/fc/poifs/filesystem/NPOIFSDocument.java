package com.app.office.fc.poifs.filesystem;

import com.app.office.fc.poifs.property.DocumentProperty;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Iterator;

public final class NPOIFSDocument {
    private int _block_size;
    private NPOIFSFileSystem _filesystem;
    private DocumentProperty _property;
    private NPOIFSStream _stream;

    public boolean preferArray() {
        return true;
    }

    public NPOIFSDocument(DocumentProperty documentProperty, NPOIFSFileSystem nPOIFSFileSystem) throws IOException {
        this._property = documentProperty;
        this._filesystem = nPOIFSFileSystem;
        if (documentProperty.getSize() < 4096) {
            this._stream = new NPOIFSStream(this._filesystem.getMiniStore(), documentProperty.getStartBlock());
            this._block_size = this._filesystem.getMiniStore().getBlockStoreBlockSize();
            return;
        }
        this._stream = new NPOIFSStream(this._filesystem, documentProperty.getStartBlock());
        this._block_size = this._filesystem.getBlockStoreBlockSize();
    }

    public NPOIFSDocument(String str, NPOIFSFileSystem nPOIFSFileSystem, InputStream inputStream) throws IOException {
        byte[] bArr;
        this._filesystem = nPOIFSFileSystem;
        if (inputStream instanceof ByteArrayInputStream) {
            ByteArrayInputStream byteArrayInputStream = (ByteArrayInputStream) inputStream;
            bArr = new byte[byteArrayInputStream.available()];
            byteArrayInputStream.read(bArr);
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            IOUtils.copy(inputStream, byteArrayOutputStream);
            bArr = byteArrayOutputStream.toByteArray();
        }
        if (bArr.length <= 4096) {
            this._stream = new NPOIFSStream(nPOIFSFileSystem.getMiniStore());
            this._block_size = this._filesystem.getMiniStore().getBlockStoreBlockSize();
        } else {
            this._stream = new NPOIFSStream(nPOIFSFileSystem);
            this._block_size = this._filesystem.getBlockStoreBlockSize();
        }
        this._stream.updateContents(bArr);
        DocumentProperty documentProperty = new DocumentProperty(str, bArr.length);
        this._property = documentProperty;
        documentProperty.setStartBlock(this._stream.getStartBlock());
    }

    /* access modifiers changed from: package-private */
    public int getDocumentBlockSize() {
        return this._block_size;
    }

    /* access modifiers changed from: package-private */
    public Iterator<ByteBuffer> getBlockIterator() {
        if (getSize() > 0) {
            return this._stream.getBlockIterator();
        }
        return Collections.emptyList().iterator();
    }

    public int getSize() {
        return this._property.getSize();
    }

    /* access modifiers changed from: package-private */
    public DocumentProperty getDocumentProperty() {
        return this._property;
    }

    public Object[] getViewableArray() {
        String str;
        Object[] objArr = new Object[1];
        try {
            if (getSize() > 0) {
                int size = getSize();
                byte[] bArr = new byte[size];
                Iterator<ByteBuffer> it = this._stream.iterator();
                int i = 0;
                while (it.hasNext()) {
                    int min = Math.min(this._block_size, size - i);
                    it.next().get(bArr, i, min);
                    i += min;
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                HexDump.dump(bArr, 0, (OutputStream) byteArrayOutputStream, 0);
                str = byteArrayOutputStream.toString();
            } else {
                str = "<NO DATA>";
            }
        } catch (IOException e) {
            str = e.getMessage();
        }
        objArr[0] = str;
        return objArr;
    }

    public Iterator getViewableIterator() {
        return Collections.EMPTY_LIST.iterator();
    }

    public String getShortDescription() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Document: \"");
        stringBuffer.append(this._property.getName());
        stringBuffer.append("\"");
        stringBuffer.append(" size = ");
        stringBuffer.append(getSize());
        return stringBuffer.toString();
    }
}
