package com.app.office.fc.hwpf.usermodel;

import com.app.office.common.picture.Picture;
import com.app.office.fc.openxml4j.opc.ContentTypes;

public enum PictureType {
    BMP("image/bmp", "bmp", new byte[][]{new byte[]{Field.SECTIONPAGES, 77}}),
    EMF("image/x-emf", Picture.EMF_TYPE, new byte[][]{new byte[]{1, 0, 0, 0}}),
    GIF(ContentTypes.IMAGE_GIF, "gif", new byte[][]{new byte[]{Field.FORMCHECKBOX, Field.TOA, Field.FORMTEXT}}),
    JPEG(ContentTypes.IMAGE_JPEG, ContentTypes.EXTENSION_JPG_1, new byte[][]{new byte[]{-1, -40}}),
    PNG(ContentTypes.IMAGE_PNG, "png", new byte[][]{new byte[]{-119, 80, 78, Field.FORMCHECKBOX, 13, 10, 26, 10}}),
    TIFF(ContentTypes.IMAGE_TIFF, "tiff", new byte[][]{new byte[]{Field.TOA, Field.TOA, 42, 0}, new byte[]{77, 77, 0, 42}}),
    UNKNOWN("image/unknown", "", new byte[0][]),
    WMF("image/x-wmf", Picture.WMF_TYPE, new byte[][]{new byte[]{-41, -51, -58, -102, 0, 0}, new byte[]{1, 0, 9, 0, 0, 3}});
    
    private String _extension;
    private String _mime;
    private byte[][] _signatures;

    public static PictureType findMatchingType(byte[] bArr) {
        for (PictureType pictureType : values()) {
            for (byte[] matchSignature : pictureType.getSignatures()) {
                if (matchSignature(bArr, matchSignature)) {
                    return pictureType;
                }
            }
        }
        return UNKNOWN;
    }

    private static boolean matchSignature(byte[] bArr, byte[] bArr2) {
        if (bArr.length < bArr2.length) {
            return false;
        }
        for (int i = 0; i < bArr2.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    private PictureType(String str, String str2, byte[][] bArr) {
        this._mime = str;
        this._extension = str2;
        this._signatures = bArr;
    }

    public String getExtension() {
        return this._extension;
    }

    public String getMime() {
        return this._mime;
    }

    public byte[][] getSignatures() {
        return this._signatures;
    }

    public boolean matchSignature(byte[] bArr) {
        for (byte[] matchSignature : getSignatures()) {
            if (matchSignature(matchSignature, bArr)) {
                return true;
            }
        }
        return false;
    }
}
