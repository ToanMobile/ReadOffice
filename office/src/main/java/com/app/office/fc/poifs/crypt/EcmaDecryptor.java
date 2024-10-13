package com.app.office.fc.poifs.crypt;

import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.app.office.fc.hwpf.usermodel.Field;
import com.app.office.fc.poifs.filesystem.DirectoryNode;
import com.app.office.fc.poifs.filesystem.DocumentInputStream;
import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

public class EcmaDecryptor extends Decryptor {
    private final EncryptionInfo info;
    private byte[] passwordHash;

    public EcmaDecryptor(EncryptionInfo encryptionInfo) {
        this.info = encryptionInfo;
    }

    private byte[] generateKey(int i) throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance(DigestAlgorithms.SHA1);
        instance.update(this.passwordHash);
        byte[] bArr = new byte[4];
        LittleEndian.putInt(bArr, i);
        byte[] digest = instance.digest(bArr);
        int keySize = this.info.getHeader().getKeySize() / 8;
        byte[] bArr2 = new byte[64];
        Arrays.fill(bArr2, Field.AUTONUM);
        for (int i2 = 0; i2 < digest.length; i2++) {
            bArr2[i2] = (byte) (bArr2[i2] ^ digest[i2]);
        }
        instance.reset();
        byte[] digest2 = instance.digest(bArr2);
        Arrays.fill(bArr2, Field.BIDIOUTLINE);
        for (int i3 = 0; i3 < digest.length; i3++) {
            bArr2[i3] = (byte) (bArr2[i3] ^ digest[i3]);
        }
        instance.reset();
        byte[] digest3 = instance.digest(bArr2);
        byte[] bArr3 = new byte[(digest2.length + digest3.length)];
        System.arraycopy(digest2, 0, bArr3, 0, digest2.length);
        System.arraycopy(digest3, 0, bArr3, digest2.length, digest3.length);
        return truncateOrPad(bArr3, keySize);
    }

    public boolean verifyPassword(String str) throws GeneralSecurityException {
        this.passwordHash = hashPassword(this.info, str);
        Cipher cipher = getCipher();
        byte[] digest = MessageDigest.getInstance(DigestAlgorithms.SHA1).digest(cipher.doFinal(this.info.getVerifier().getVerifier()));
        return Arrays.equals(digest, truncateOrPad(cipher.doFinal(this.info.getVerifier().getVerifierHash()), digest.length));
    }

    private byte[] truncateOrPad(byte[] bArr, int i) {
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, Math.min(i, bArr.length));
        if (i > bArr.length) {
            for (int length = bArr.length; length < i; length++) {
                bArr2[length] = 0;
            }
        }
        return bArr2;
    }

    private Cipher getCipher() throws GeneralSecurityException {
        byte[] generateKey = generateKey(0);
        Cipher instance = Cipher.getInstance("AES/ECB/NoPadding");
        instance.init(2, new SecretKeySpec(generateKey, "AES"));
        return instance;
    }

    public InputStream getDataStream(DirectoryNode directoryNode) throws IOException, GeneralSecurityException {
        DocumentInputStream createDocumentInputStream = directoryNode.createDocumentInputStream("EncryptedPackage");
        createDocumentInputStream.readLong();
        return new CipherInputStream(createDocumentInputStream, getCipher());
    }
}
