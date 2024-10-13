package com.app.office.fc.poifs.crypt;

import android.support.v4.media.session.PlaybackStateCompat;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.app.office.fc.EncryptedDocumentException;
import com.app.office.fc.hwpf.usermodel.Field;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
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
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AgileDecryptor extends Decryptor {
    private static final byte[] kCryptoKeyBlock = {20, 110, 11, -25, -85, -84, -48, -42};
    private static final byte[] kHashedVerifierBlock = {-41, -86, 15, 109, 48, 97, Field.AUTONUMOUT, 78};
    private static final byte[] kVerifierInputBlock = {-2, -89, -46, 118, 59, Field.MERGESEQ, -98, 121};
    /* access modifiers changed from: private */
    public final EncryptionInfo _info;
    /* access modifiers changed from: private */
    public SecretKey _secretKey;

    public boolean verifyPassword(String str) throws GeneralSecurityException {
        EncryptionVerifier verifier = this._info.getVerifier();
        int algorithm = verifier.getAlgorithm();
        int cipherMode = verifier.getCipherMode();
        byte[] hashPassword = hashPassword(this._info, str);
        byte[] doFinal = getCipher(algorithm, cipherMode, new SecretKeySpec(generateKey(hashPassword, kVerifierInputBlock), "AES"), generateIv(algorithm, verifier.getSalt(), (byte[]) null)).doFinal(verifier.getVerifier());
        MessageDigest instance = MessageDigest.getInstance(DigestAlgorithms.SHA1);
        int length = verifier.getSalt().length;
        byte[] bArr = new byte[length];
        System.arraycopy(doFinal, 0, bArr, 0, length);
        byte[] digest = instance.digest(bArr);
        byte[] doFinal2 = getCipher(algorithm, cipherMode, new SecretKeySpec(generateKey(hashPassword, kHashedVerifierBlock), "AES"), generateIv(algorithm, verifier.getSalt(), (byte[]) null)).doFinal(verifier.getVerifierHash());
        int length2 = digest.length;
        byte[] bArr2 = new byte[length2];
        System.arraycopy(doFinal2, 0, bArr2, 0, length2);
        if (!Arrays.equals(bArr2, digest)) {
            return false;
        }
        byte[] doFinal3 = getCipher(algorithm, cipherMode, new SecretKeySpec(generateKey(hashPassword, kCryptoKeyBlock), "AES"), generateIv(algorithm, verifier.getSalt(), (byte[]) null)).doFinal(verifier.getEncryptedKey());
        int keySize = this._info.getHeader().getKeySize() / 8;
        byte[] bArr3 = new byte[keySize];
        System.arraycopy(doFinal3, 0, bArr3, 0, keySize);
        this._secretKey = new SecretKeySpec(bArr3, "AES");
        return true;
    }

    public InputStream getDataStream(DirectoryNode directoryNode) throws IOException, GeneralSecurityException {
        DocumentInputStream createDocumentInputStream = directoryNode.createDocumentInputStream("EncryptedPackage");
        return new ChunkedCipherInputStream(createDocumentInputStream, createDocumentInputStream.readLong());
    }

    protected AgileDecryptor(EncryptionInfo encryptionInfo) {
        this._info = encryptionInfo;
    }

    private class ChunkedCipherInputStream extends InputStream {
        private byte[] _chunk;
        private Cipher _cipher;
        private int _lastIndex = 0;
        private long _pos = 0;
        private final long _size;
        private final DocumentInputStream _stream;

        public boolean markSupported() {
            return false;
        }

        public ChunkedCipherInputStream(DocumentInputStream documentInputStream, long j) throws GeneralSecurityException {
            this._size = j;
            this._stream = documentInputStream;
            this._cipher = AgileDecryptor.this.getCipher(AgileDecryptor.this._info.getHeader().getAlgorithm(), AgileDecryptor.this._info.getHeader().getCipherMode(), AgileDecryptor.this._secretKey, AgileDecryptor.this._info.getHeader().getKeySalt());
        }

        public int read() throws IOException {
            byte[] bArr = new byte[1];
            if (read(bArr) == 1) {
                return bArr[0];
            }
            return -1;
        }

        public int read(byte[] bArr) throws IOException {
            return read(bArr, 0, bArr.length);
        }

        public int read(byte[] bArr, int i, int i2) throws IOException {
            int i3 = 0;
            while (i2 > 0) {
                if (this._chunk == null) {
                    try {
                        this._chunk = nextChunk();
                    } catch (GeneralSecurityException unused) {
                        throw new EncryptedDocumentException("Cannot process encrypted office files!");
                    }
                }
                int min = Math.min(available(), Math.min((int) (PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM - (this._pos & 4095)), i2));
                System.arraycopy(this._chunk, (int) (this._pos & 4095), bArr, i, min);
                i += min;
                i2 -= min;
                long j = this._pos + ((long) min);
                this._pos = j;
                if ((j & 4095) == 0) {
                    this._chunk = null;
                }
                i3 += min;
            }
            return i3;
        }

        public long skip(long j) throws IOException {
            long j2 = this._pos;
            long min = Math.min((long) available(), j);
            long j3 = this._pos;
            if (((j2 ^ (j3 + min)) & -4096) != 0) {
                this._chunk = null;
            }
            this._pos = j3 + min;
            return min;
        }

        public int available() throws IOException {
            return (int) (this._size - this._pos);
        }

        public void close() throws IOException {
            this._stream.close();
        }

        private byte[] nextChunk() throws GeneralSecurityException, IOException {
            int i = (int) (this._pos >> 12);
            byte[] bArr = new byte[4];
            LittleEndian.putInt(bArr, i);
            AgileDecryptor agileDecryptor = AgileDecryptor.this;
            this._cipher.init(2, AgileDecryptor.this._secretKey, new IvParameterSpec(agileDecryptor.generateIv(agileDecryptor._info.getHeader().getAlgorithm(), AgileDecryptor.this._info.getHeader().getKeySalt(), bArr)));
            int i2 = this._lastIndex;
            if (i2 != i) {
                this._stream.skip((long) ((i - i2) << 12));
            }
            byte[] bArr2 = new byte[Math.min(this._stream.available(), 4096)];
            this._stream.readFully(bArr2);
            this._lastIndex = i + 1;
            return this._cipher.doFinal(bArr2);
        }
    }

    /* access modifiers changed from: private */
    public Cipher getCipher(int i, int i2, SecretKey secretKey, byte[] bArr) throws GeneralSecurityException {
        String str = null;
        String str2 = (i == 26126 || i == 26127 || i == 26128) ? "AES" : null;
        if (i2 == 2) {
            str = "CBC";
        } else if (i2 == 3) {
            str = "CFB";
        }
        Cipher instance = Cipher.getInstance(str2 + PackagingURIHelper.FORWARD_SLASH_STRING + str + "/NoPadding");
        instance.init(2, secretKey, new IvParameterSpec(bArr));
        return instance;
    }

    private byte[] getBlock(int i, byte[] bArr) {
        int blockSize = getBlockSize(i);
        byte[] bArr2 = new byte[blockSize];
        Arrays.fill(bArr2, Field.AUTONUM);
        System.arraycopy(bArr, 0, bArr2, 0, Math.min(blockSize, bArr.length));
        return bArr2;
    }

    private byte[] generateKey(byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance(DigestAlgorithms.SHA1);
        instance.update(bArr);
        return getBlock(this._info.getVerifier().getAlgorithm(), instance.digest(bArr2));
    }

    /* access modifiers changed from: protected */
    public byte[] generateIv(int i, byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException {
        if (bArr2 == null) {
            return getBlock(i, bArr);
        }
        MessageDigest instance = MessageDigest.getInstance(DigestAlgorithms.SHA1);
        instance.update(bArr);
        return getBlock(i, instance.digest(bArr2));
    }
}
