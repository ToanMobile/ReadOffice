package com.app.office.common.bg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Rect;
import android.graphics.Shader;
import com.app.office.common.picture.Picture;
import com.app.office.system.IControl;

public class TileShader extends AShader {
    public static final int Flip_Both = 3;
    public static final int Flip_Horizontal = 1;
    public static final int Flip_None = 0;
    public static final int Flip_Vertical = 2;
    private int flip;
    private float horiRatio;
    private int offsetX;
    private int offsetY;
    private Picture picture;
    private float vertRatio;

    public TileShader(Picture picture2, int i, float f, float f2) {
        this.picture = picture2;
        this.flip = i;
        this.horiRatio = f;
        this.vertRatio = f2;
    }

    public TileShader(Picture picture2, int i, float f, float f2, int i2, int i3) {
        this(picture2, i, f, f2);
        this.offsetX = i2;
        this.offsetY = i3;
    }

    public Shader createShader(IControl iControl, int i, Rect rect) {
        try {
            Bitmap bitmap = getBitmap(iControl, i, this.picture, rect, (BitmapFactory.Options) null);
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, Math.round(((float) bitmap.getWidth()) * this.horiRatio), Math.round(((float) bitmap.getHeight()) * this.vertRatio), true);
            Shader.TileMode tileMode = Shader.TileMode.REPEAT;
            Shader.TileMode tileMode2 = Shader.TileMode.REPEAT;
            int i2 = this.flip;
            if (i2 == 1) {
                Shader.TileMode tileMode3 = Shader.TileMode.MIRROR;
            } else if (i2 != 2) {
                if (i2 != 3) {
                    this.shader = new BitmapShader(createScaledBitmap, tileMode, tileMode2);
                    return this.shader;
                }
                tileMode = Shader.TileMode.MIRROR;
                tileMode2 = Shader.TileMode.MIRROR;
                this.shader = new BitmapShader(createScaledBitmap, tileMode, tileMode2);
                return this.shader;
            }
            Shader.TileMode tileMode4 = Shader.TileMode.MIRROR;
            tileMode = Shader.TileMode.MIRROR;
            tileMode2 = Shader.TileMode.MIRROR;
            this.shader = new BitmapShader(createScaledBitmap, tileMode, tileMode2);
            return this.shader;
        } catch (Exception unused) {
            return null;
        }
    }

    public int getOffsetX() {
        return this.offsetX;
    }

    public void setOffsetX(int i) {
        this.offsetX = i;
    }

    public int getOffsetY() {
        return this.offsetY;
    }

    public void setOffsetY(int i) {
        this.offsetY = i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x004a A[Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }, RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x004b A[Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap getBitmap(com.app.office.system.IControl r10, int r11, com.app.office.common.picture.Picture r12, android.graphics.Rect r13, android.graphics.BitmapFactory.Options r14) {
        /*
            r0 = 2
            r1 = 0
            java.lang.String r9 = r12.getTempFilePath()     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            com.app.office.system.SysKit r2 = r10.getSysKit()     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            com.app.office.common.picture.PictureManage r2 = r2.getPictureManage()     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            android.graphics.Bitmap r2 = r2.getBitmap(r9)     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            if (r2 != 0) goto L_0x0056
            byte r4 = r12.getPictureType()     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            r2 = 3
            if (r4 == r2) goto L_0x0028
            if (r4 != r0) goto L_0x001e
            goto L_0x0028
        L_0x001e:
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            r2.<init>(r9)     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeStream(r2, r1, r14)     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            goto L_0x0048
        L_0x0028:
            com.app.office.system.SysKit r2 = r10.getSysKit()     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            com.app.office.common.picture.PictureManage r2 = r2.getPictureManage()     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            int r6 = r13.width()     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            int r7 = r13.height()     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            r8 = 1
            r3 = r11
            r5 = r9
            java.lang.String r2 = r2.convertVectorgraphToPng(r3, r4, r5, r6, r7, r8)     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            r3.<init>(r2)     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeStream(r3, r1, r14)     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
        L_0x0048:
            if (r2 != 0) goto L_0x004b
            return r1
        L_0x004b:
            com.app.office.system.SysKit r3 = r10.getSysKit()     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            com.app.office.common.picture.PictureManage r3 = r3.getPictureManage()     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
            r3.addBitmap(r9, r2)     // Catch:{ OutOfMemoryError -> 0x0058, Exception -> 0x0057 }
        L_0x0056:
            return r2
        L_0x0057:
            return r1
        L_0x0058:
            com.app.office.system.SysKit r1 = r10.getSysKit()
            com.app.office.common.picture.PictureManage r1 = r1.getPictureManage()
            boolean r1 = r1.hasBitmap()
            if (r1 == 0) goto L_0x0077
            com.app.office.system.SysKit r0 = r10.getSysKit()
            com.app.office.common.picture.PictureManage r0 = r0.getPictureManage()
            r0.clearBitmap()
            android.graphics.Bitmap r10 = getBitmap(r10, r11, r12, r13, r14)
            return r10
        L_0x0077:
            if (r14 != 0) goto L_0x0081
            android.graphics.BitmapFactory$Options r14 = new android.graphics.BitmapFactory$Options
            r14.<init>()
            r14.inSampleSize = r0
            goto L_0x0087
        L_0x0081:
            int r1 = r14.inSampleSize
            int r1 = r1 * 2
            r14.inSampleSize = r1
        L_0x0087:
            android.graphics.Bitmap r10 = getBitmap(r10, r11, r12, r13, r14)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.bg.TileShader.getBitmap(com.app.office.system.IControl, int, com.app.office.common.picture.Picture, android.graphics.Rect, android.graphics.BitmapFactory$Options):android.graphics.Bitmap");
    }
}
