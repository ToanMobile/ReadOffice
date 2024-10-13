package com.app.office.fc.poifs.crypt;

import com.app.office.fc.poifs.filesystem.DirectoryNode;
import com.app.office.fc.poifs.filesystem.DocumentInputStream;
import com.app.office.fc.poifs.filesystem.NPOIFSFileSystem;
import com.app.office.fc.poifs.filesystem.POIFSFileSystem;
import java.io.IOException;

public class EncryptionInfo {
    private final int encryptionFlags;
    private final EncryptionHeader header;
    private final EncryptionVerifier verifier;
    private final int versionMajor;
    private final int versionMinor;

    public EncryptionInfo(POIFSFileSystem pOIFSFileSystem) throws IOException {
        this(pOIFSFileSystem.getRoot());
    }

    public EncryptionInfo(NPOIFSFileSystem nPOIFSFileSystem) throws IOException {
        this(nPOIFSFileSystem.getRoot());
    }

    public EncryptionInfo(DirectoryNode directoryNode) throws IOException {
        DocumentInputStream createDocumentInputStream = directoryNode.createDocumentInputStream("EncryptionInfo");
        short readShort = createDocumentInputStream.readShort();
        this.versionMajor = readShort;
        short readShort2 = createDocumentInputStream.readShort();
        this.versionMinor = readShort2;
        int readInt = createDocumentInputStream.readInt();
        this.encryptionFlags = readInt;
        if (readShort == 4 && readShort2 == 4 && readInt == 64) {
            StringBuilder sb = new StringBuilder();
            int available = createDocumentInputStream.available();
            byte[] bArr = new byte[available];
            createDocumentInputStream.read(bArr);
            for (int i = 0; i < available; i++) {
                sb.append((char) bArr[i]);
            }
            String sb2 = sb.toString();
            this.header = new EncryptionHeader(sb2);
            this.verifier = new EncryptionVerifier(sb2);
            return;
        }
        createDocumentInputStream.readInt();
        EncryptionHeader encryptionHeader = new EncryptionHeader(createDocumentInputStream);
        this.header = encryptionHeader;
        if (encryptionHeader.getAlgorithm() == 26625) {
            this.verifier = new EncryptionVerifier(createDocumentInputStream, 20);
        } else {
            this.verifier = new EncryptionVerifier(createDocumentInputStream, 32);
        }
    }

    public int getVersionMajor() {
        return this.versionMajor;
    }

    public int getVersionMinor() {
        return this.versionMinor;
    }

    public int getEncryptionFlags() {
        return this.encryptionFlags;
    }

    public EncryptionHeader getHeader() {
        return this.header;
    }

    public EncryptionVerifier getVerifier() {
        return this.verifier;
    }
}
