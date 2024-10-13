package com.app.office.fc.poifs.crypt;

import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.app.office.fc.EncryptedDocumentException;
import com.app.office.fc.poifs.filesystem.DirectoryNode;
import com.app.office.fc.poifs.filesystem.NPOIFSFileSystem;
import com.app.office.fc.poifs.filesystem.POIFSFileSystem;
import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Decryptor {
    public static final String DEFAULT_PASSWORD = "VelvetSweatshop";

    public abstract InputStream getDataStream(DirectoryNode directoryNode) throws IOException, GeneralSecurityException;

    public abstract boolean verifyPassword(String str) throws GeneralSecurityException;

    public static Decryptor getInstance(EncryptionInfo encryptionInfo) {
        int versionMajor = encryptionInfo.getVersionMajor();
        int versionMinor = encryptionInfo.getVersionMinor();
        if (versionMajor == 4 && versionMinor == 4) {
            return new AgileDecryptor(encryptionInfo);
        }
        if (versionMinor == 2 && (versionMajor == 3 || versionMajor == 4)) {
            return new EcmaDecryptor(encryptionInfo);
        }
        throw new EncryptedDocumentException("Cannot process encrypted office files!");
    }

    public InputStream getDataStream(NPOIFSFileSystem nPOIFSFileSystem) throws IOException, GeneralSecurityException {
        return getDataStream(nPOIFSFileSystem.getRoot());
    }

    public InputStream getDataStream(POIFSFileSystem pOIFSFileSystem) throws IOException, GeneralSecurityException {
        return getDataStream(pOIFSFileSystem.getRoot());
    }

    protected static int getBlockSize(int i) {
        switch (i) {
            case EncryptionHeader.ALGORITHM_AES_128:
                return 16;
            case EncryptionHeader.ALGORITHM_AES_192:
                return 24;
            case EncryptionHeader.ALGORITHM_AES_256:
                return 32;
            default:
                throw new EncryptedDocumentException("Cannot process encrypted office files!");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] hashPassword(EncryptionInfo encryptionInfo, String str) throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance(DigestAlgorithms.SHA1);
        try {
            byte[] bytes = str.getBytes("UTF-16LE");
            instance.update(encryptionInfo.getVerifier().getSalt());
            byte[] digest = instance.digest(bytes);
            byte[] bArr = new byte[4];
            for (int i = 0; i < encryptionInfo.getVerifier().getSpinCount(); i++) {
                instance.reset();
                LittleEndian.putInt(bArr, i);
                instance.update(bArr);
                digest = instance.digest(digest);
            }
            return digest;
        } catch (UnsupportedEncodingException unused) {
            throw new EncryptedDocumentException("Cannot process encrypted office files!");
        }
    }
}
