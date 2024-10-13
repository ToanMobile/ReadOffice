package com.app.office.fc.hssf.record.crypto;

import com.app.office.fc.poifs.crypt.Decryptor;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutputStream;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public final class Biff8EncryptionKey {
    private static final int KEY_DIGEST_LENGTH = 5;
    private static final int PASSWORD_HASH_NUMBER_OF_BYTES_USED = 5;
    private static final ThreadLocal<String> _userPasswordTLS = new ThreadLocal<>();
    private final byte[] _keyDigest;

    public static Biff8EncryptionKey create(byte[] bArr) {
        return new Biff8EncryptionKey(createKeyDigest(Decryptor.DEFAULT_PASSWORD, bArr));
    }

    public static Biff8EncryptionKey create(String str, byte[] bArr) {
        return new Biff8EncryptionKey(createKeyDigest(str, bArr));
    }

    Biff8EncryptionKey(byte[] bArr) {
        if (bArr.length == 5) {
            this._keyDigest = bArr;
            return;
        }
        throw new IllegalArgumentException("Expected 5 byte key digest, but got " + HexDump.toHex(bArr));
    }

    static byte[] createKeyDigest(String str, byte[] bArr) {
        check16Bytes(bArr, "docId");
        int min = Math.min(str.length(), 16);
        byte[] bArr2 = new byte[(min * 2)];
        for (int i = 0; i < min; i++) {
            char charAt = str.charAt(i);
            int i2 = i * 2;
            bArr2[i2 + 0] = (byte) ((charAt << 0) & 255);
            bArr2[i2 + 1] = (byte) ((charAt << 8) & 255);
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr2);
            byte[] digest = instance.digest();
            instance.reset();
            for (int i3 = 0; i3 < 16; i3++) {
                instance.update(digest, 0, 5);
                instance.update(bArr, 0, bArr.length);
            }
            byte[] bArr3 = new byte[5];
            System.arraycopy(instance.digest(), 0, bArr3, 0, 5);
            return bArr3;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validate(byte[] bArr, byte[] bArr2) {
        check16Bytes(bArr, "saltData");
        check16Bytes(bArr2, "saltHash");
        RC4 createRC4 = createRC4(0);
        byte[] bArr3 = (byte[]) bArr.clone();
        createRC4.encrypt(bArr3);
        byte[] bArr4 = (byte[]) bArr2.clone();
        createRC4.encrypt(bArr4);
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr3);
            return Arrays.equals(bArr4, instance.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] xor(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        byte[] bArr3 = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr3[i] = (byte) (bArr[i] ^ bArr2[i]);
        }
        return bArr3;
    }

    private static void check16Bytes(byte[] bArr, String str) {
        if (bArr.length != 16) {
            throw new IllegalArgumentException("Expected 16 byte " + str + ", but got " + HexDump.toHex(bArr));
        }
    }

    /* access modifiers changed from: package-private */
    public RC4 createRC4(int i) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(this._keyDigest);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4);
            new LittleEndianOutputStream(byteArrayOutputStream).writeInt(i);
            instance.update(byteArrayOutputStream.toByteArray());
            return new RC4(instance.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setCurrentUserPassword(String str) {
        _userPasswordTLS.set(str);
    }

    public static String getCurrentUserPassword() {
        return _userPasswordTLS.get();
    }
}
