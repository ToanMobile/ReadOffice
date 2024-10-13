package com.app.office.thirdpart.emf;

import android.graphics.Bitmap;
import androidx.core.view.MotionEventCompat;
import com.app.office.java.awt.Color;
import com.app.office.thirdpart.emf.data.BitmapInfoHeader;
import com.app.office.thirdpart.emf.data.BlendFunction;
import java.io.IOException;
import java.util.Arrays;

public class EMFImageLoader {
    public static Bitmap readImage(BitmapInfoHeader bitmapInfoHeader, int i, int i2, EMFInputStream eMFInputStream, int i3, BlendFunction blendFunction) throws IOException {
        int i4;
        int i5;
        int i6 = i;
        int i7 = i2;
        EMFInputStream eMFInputStream2 = eMFInputStream;
        int i8 = -1;
        if (bitmapInfoHeader.getBitCount() == 1) {
            int readUnsignedByte = eMFInputStream.readUnsignedByte();
            int readUnsignedByte2 = eMFInputStream.readUnsignedByte();
            int readUnsignedByte3 = eMFInputStream.readUnsignedByte();
            eMFInputStream.readUnsignedByte();
            int rgb = new Color(readUnsignedByte3, readUnsignedByte2, readUnsignedByte).getRGB();
            int readUnsignedByte4 = eMFInputStream.readUnsignedByte();
            int readUnsignedByte5 = eMFInputStream.readUnsignedByte();
            int readUnsignedByte6 = eMFInputStream.readUnsignedByte();
            eMFInputStream.readUnsignedByte();
            int rgb2 = new Color(readUnsignedByte6, readUnsignedByte5, readUnsignedByte4).getRGB();
            Bitmap createBitmap = Bitmap.createBitmap(i6, i7, Bitmap.Config.RGB_565);
            int[] readUnsignedByte7 = eMFInputStream2.readUnsignedByte(i3 - 8);
            int i9 = i6 % 8;
            if (i9 != 0) {
                i9 = 8 - i9;
            }
            int[] iArr = {1, 2, 4, 8, 16, 32, 64, 128};
            int i10 = 0;
            for (int i11 = i7 - 1; i11 > -1; i11--) {
                for (int i12 = 0; i12 < i6; i12++) {
                    int i13 = readUnsignedByte7[i10 / 8] & iArr[i10 % 8];
                    i10++;
                    if (i13 > 0) {
                        createBitmap.setPixel(i12, i11, rgb2);
                    } else {
                        createBitmap.setPixel(i12, i11, rgb);
                    }
                }
                i10 += i9;
            }
            return createBitmap;
        }
        int i14 = 2;
        if (bitmapInfoHeader.getBitCount() == 4 && bitmapInfoHeader.getCompression() == 0) {
            int clrUsed = bitmapInfoHeader.getClrUsed();
            int i15 = clrUsed * 4;
            int[] readUnsignedByte8 = eMFInputStream2.readUnsignedByte(i15);
            int i16 = i3 - i15;
            int[] iArr2 = new int[i16];
            int i17 = 0;
            while (i17 < i16 / 12) {
                int[] readUnsignedByte9 = eMFInputStream2.readUnsignedByte(10);
                eMFInputStream2.readUnsignedByte(i14);
                System.arraycopy(readUnsignedByte9, 0, iArr2, i17 * 10, 10);
                i17++;
                i14 = 2;
            }
            int[] iArr3 = new int[256];
            int i18 = 0;
            int i19 = 0;
            while (i18 < clrUsed) {
                iArr3[i18] = new Color(readUnsignedByte8[i19 + 2], readUnsignedByte8[i19 + 1], readUnsignedByte8[i19]).getRGB();
                i18++;
                i19 = i18 * 4;
            }
            if (clrUsed < 256) {
                Arrays.fill(iArr3, clrUsed, 256, 0);
            }
            Bitmap createBitmap2 = Bitmap.createBitmap(i6, i7, Bitmap.Config.ARGB_8888);
            int i20 = 0;
            for (int i21 = i7 - 1; i21 > -1; i21--) {
                for (int i22 = 0; i22 < i6 && i20 < i16; i22 += 2) {
                    createBitmap2.setPixel(i22, i21, iArr3[iArr2[i20] % 8]);
                    createBitmap2.setPixel(i22 + 1, i21, iArr3[iArr2[i20] % 8]);
                    i20++;
                }
            }
            return createBitmap2;
        } else if (bitmapInfoHeader.getBitCount() == 8 && bitmapInfoHeader.getCompression() == 0) {
            int clrUsed2 = bitmapInfoHeader.getClrUsed();
            int i23 = clrUsed2 * 4;
            int[] readUnsignedByte10 = eMFInputStream2.readUnsignedByte(i23);
            int[] readUnsignedByte11 = eMFInputStream2.readUnsignedByte(i3 - i23);
            int[] iArr4 = new int[256];
            int i24 = 0;
            int i25 = 0;
            while (i24 < clrUsed2) {
                iArr4[i24] = new Color(readUnsignedByte10[i25 + 2], readUnsignedByte10[i25 + 1], readUnsignedByte10[i25]).getRGB();
                i24++;
                i25 = i24 * 4;
            }
            if (clrUsed2 < 256) {
                Arrays.fill(iArr4, clrUsed2, 256, 0);
            }
            int i26 = i6 % 4;
            if (i26 != 0) {
                i26 = 4 - i26;
            }
            Bitmap createBitmap3 = Bitmap.createBitmap(i6, i7, Bitmap.Config.RGB_565);
            int i27 = 0;
            for (int i28 = i7 - 1; i28 > -1; i28--) {
                int i29 = 0;
                while (i29 < i6) {
                    createBitmap3.setPixel(i29, i28, iArr4[readUnsignedByte11[i27]]);
                    i29++;
                    i27++;
                }
                i27 += i26;
            }
            return createBitmap3;
        } else {
            int i30 = 16;
            if (bitmapInfoHeader.getBitCount() == 16 && bitmapInfoHeader.getCompression() == 0) {
                int[] readDWORD = eMFInputStream2.readDWORD(i3 / 4);
                int i31 = (i6 + (i6 % 2)) / 2;
                int length = (readDWORD.length / i31) / 2;
                Bitmap createBitmap4 = Bitmap.createBitmap(i31, length, Bitmap.Config.RGB_565);
                int i32 = length - 1;
                int i33 = 0;
                while (i32 > -1) {
                    int i34 = 0;
                    while (i34 < i31) {
                        int i35 = readDWORD[i33 + i31];
                        int i36 = i33 + 1;
                        int i37 = readDWORD[i33];
                        createBitmap4.setPixel(i34, i32, new Color(((float) ((i37 & 31744) + (i35 & 31744))) / 63488.0f, ((float) ((i37 & 992) + (i35 & 992))) / 1984.0f, ((float) ((i37 & 31) + (i35 & 31))) / 62.0f).getRGB());
                        i34++;
                        i33 = i36;
                    }
                    i32--;
                    i33 += i31;
                }
                return createBitmap4;
            } else if (bitmapInfoHeader.getBitCount() == 32 && bitmapInfoHeader.getCompression() == 0) {
                Bitmap createBitmap5 = Bitmap.createBitmap(i6, i7, Bitmap.Config.RGB_565);
                int i38 = i3 / 4;
                if (blendFunction != null) {
                    i5 = blendFunction.getSourceConstantAlpha();
                    i4 = blendFunction.getAlphaFormat();
                } else {
                    i5 = 255;
                    i4 = 0;
                }
                int i39 = MotionEventCompat.ACTION_POINTER_INDEX_MASK;
                if (i4 != 1) {
                    int i40 = i7 - 1;
                    int i41 = 0;
                    while (i40 > -1 && i41 < i38) {
                        int i42 = 0;
                        while (i42 < i6 && i41 < i38) {
                            int readDWORD2 = eMFInputStream.readDWORD();
                            createBitmap5.setPixel(i42, i40, new Color((readDWORD2 & 16711680) >> 16, (readDWORD2 & i39) >> 8, readDWORD2 & 255, i5).getRGB());
                            i42++;
                            i41++;
                            i39 = MotionEventCompat.ACTION_POINTER_INDEX_MASK;
                        }
                        i40--;
                        i39 = MotionEventCompat.ACTION_POINTER_INDEX_MASK;
                    }
                } else if (i5 == 255) {
                    int i43 = i7 - 1;
                    int i44 = 0;
                    while (i43 > -1 && i44 < i38) {
                        int i45 = 0;
                        while (i45 < i6 && i44 < i38) {
                            int readDWORD3 = eMFInputStream.readDWORD();
                            int i46 = (readDWORD3 & -16777216) >> 24;
                            if (i46 == -1) {
                                i46 = 255;
                            }
                            createBitmap5.setPixel(i45, i43, new Color((readDWORD3 & 16711680) >> i30, (readDWORD3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8, readDWORD3 & 255, i46).getRGB());
                            i45++;
                            i44++;
                            i30 = 16;
                        }
                        i43--;
                        i30 = 16;
                    }
                } else {
                    int i47 = i7 - 1;
                    int i48 = 0;
                    while (i47 > i8 && i48 < i38) {
                        int i49 = 0;
                        while (i49 < i6 && i48 < i38) {
                            int readDWORD4 = eMFInputStream.readDWORD();
                            int i50 = (readDWORD4 & -16777216) >> 24;
                            if (i50 == i8) {
                                i50 = 255;
                            }
                            createBitmap5.setPixel(i49, i47, new Color((readDWORD4 & 16711680) >> 16, (readDWORD4 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8, readDWORD4 & 255, (i50 * i5) / 255).getRGB());
                            i49++;
                            i48++;
                            i8 = -1;
                        }
                        i47--;
                        i8 = -1;
                    }
                }
                return createBitmap5;
            } else if (bitmapInfoHeader.getBitCount() == 32 && bitmapInfoHeader.getCompression() == 3) {
                eMFInputStream.readByte(i3);
                return null;
            } else {
                eMFInputStream.readByte(i3);
                return null;
            }
        }
    }
}
