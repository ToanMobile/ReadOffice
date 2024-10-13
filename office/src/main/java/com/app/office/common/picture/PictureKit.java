package com.app.office.common.picture;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import com.app.office.common.pictureefftect.PictureCroppedInfo;
import com.app.office.common.pictureefftect.PictureEffectInfo;
import com.app.office.common.pictureefftect.PictureEffectUtil;
import com.app.office.pg.animate.IAnimation;
import com.app.office.system.IControl;

public class PictureKit {
    private static final String FAIL = "Fail";
    private static int VectorMaxSize = 1048576;
    private static int VectorMaxZOOM = 3;
    private static final PictureKit kit = new PictureKit();
    private boolean isDrawPictrue = true;
    private Paint paint;

    public static PictureKit instance() {
        return kit;
    }

    private PictureKit() {
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setAntiAlias(true);
    }

    public synchronized void drawPicture(Canvas canvas, IControl iControl, int i, Picture picture, float f, float f2, float f3, float f4, float f5, PictureEffectInfo pictureEffectInfo) throws OutOfMemoryError {
        try {
            drawPicture(canvas, iControl, i, picture, f, f2, f3, f4, f5, pictureEffectInfo, (IAnimation) null);
        } catch (Throwable th) {
            throw th;
        }
    }

    private void applyEffect(Paint paint2, PictureEffectInfo pictureEffectInfo) {
        if (pictureEffectInfo != null) {
            ColorMatrix colorMatrix = new ColorMatrix();
            if (pictureEffectInfo.getBlackWhiteThreshold() != null) {
                colorMatrix.set(PictureEffectUtil.getBlackWhiteArray(pictureEffectInfo.getBlackWhiteThreshold().floatValue()));
            } else if (pictureEffectInfo.isGrayScale() != null && pictureEffectInfo.isGrayScale().booleanValue()) {
                colorMatrix.set(PictureEffectUtil.getGrayScaleArray());
            }
            Float brightness = pictureEffectInfo.getBrightness();
            Float contrast = pictureEffectInfo.getContrast();
            if (brightness != null && contrast != null) {
                ColorMatrix colorMatrix2 = new ColorMatrix();
                colorMatrix2.set(PictureEffectUtil.getBrightAndContrastArray((float) brightness.intValue(), contrast.floatValue()));
                colorMatrix.preConcat(colorMatrix2);
            } else if (brightness != null) {
                ColorMatrix colorMatrix3 = new ColorMatrix();
                colorMatrix3.set(PictureEffectUtil.getBrightnessArray(brightness.intValue()));
                colorMatrix.preConcat(colorMatrix3);
            } else if (contrast != null) {
                ColorMatrix colorMatrix4 = new ColorMatrix();
                colorMatrix4.set(PictureEffectUtil.getContrastArray(contrast.floatValue()));
                colorMatrix.preConcat(colorMatrix4);
            }
            paint2.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0056, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void drawPicture(android.graphics.Canvas r16, com.app.office.system.IControl r17, int r18, com.app.office.common.picture.Picture r19, float r20, float r21, float r22, float r23, float r24, com.app.office.common.pictureefftect.PictureEffectInfo r25, com.app.office.pg.animate.IAnimation r26) throws java.lang.OutOfMemoryError {
        /*
            r15 = this;
            r0 = r19
            monitor-enter(r15)
            if (r0 == 0) goto L_0x0055
            java.lang.String r1 = r19.getTempFilePath()     // Catch:{ all -> 0x0052 }
            if (r1 == 0) goto L_0x0055
            if (r26 == 0) goto L_0x0019
            com.app.office.pg.animate.IAnimation$AnimationInformation r1 = r26.getCurrentAnimationInfor()     // Catch:{ all -> 0x0052 }
            int r1 = r1.getAlpha()     // Catch:{ all -> 0x0052 }
            if (r1 != 0) goto L_0x0019
            monitor-exit(r15)
            return
        L_0x0019:
            java.lang.String r5 = r19.getTempFilePath()     // Catch:{ all -> 0x0052 }
            byte r6 = r19.getPictureType()     // Catch:{ all -> 0x0052 }
            r7 = 0
            r1 = r15
            r2 = r16
            r3 = r17
            r4 = r18
            r8 = r20
            r9 = r21
            r10 = r22
            r11 = r23
            r12 = r24
            r13 = r25
            r14 = r26
            java.lang.String r1 = r1.drawPicture(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14)     // Catch:{ all -> 0x0052 }
            if (r1 == 0) goto L_0x0055
            java.lang.String r2 = "Fail"
            boolean r2 = r1.equalsIgnoreCase(r2)     // Catch:{ all -> 0x0052 }
            if (r2 == 0) goto L_0x004a
            r1 = 0
            r0.setTempFilePath(r1)     // Catch:{ all -> 0x0052 }
            goto L_0x0055
        L_0x004a:
            r2 = 6
            r0.setPictureType((byte) r2)     // Catch:{ all -> 0x0052 }
            r0.setTempFilePath(r1)     // Catch:{ all -> 0x0052 }
            goto L_0x0055
        L_0x0052:
            r0 = move-exception
            monitor-exit(r15)
            throw r0
        L_0x0055:
            monitor-exit(r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.picture.PictureKit.drawPicture(android.graphics.Canvas, com.app.office.system.IControl, int, com.app.office.common.picture.Picture, float, float, float, float, float, com.app.office.common.pictureefftect.PictureEffectInfo, com.app.office.pg.animate.IAnimation):void");
    }

    private boolean drawCropedPicture(Canvas canvas, float f, float f2, float f3, float f4, Bitmap bitmap, PictureCroppedInfo pictureCroppedInfo) {
        if (pictureCroppedInfo == null) {
            return true;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f5 = (float) width;
        int leftOff = (int) (pictureCroppedInfo.getLeftOff() * f5);
        float f6 = (float) height;
        int topOff = (int) (pictureCroppedInfo.getTopOff() * f6);
        int rightOff = (int) (f5 * (1.0f - pictureCroppedInfo.getRightOff()));
        int bottomOff = (int) (f6 * (1.0f - pictureCroppedInfo.getBottomOff()));
        Rect rect = new Rect(leftOff, topOff, rightOff, bottomOff);
        if (leftOff < 0) {
            leftOff = 0;
        }
        if (topOff < 0) {
            topOff = 0;
        }
        if (rightOff < width) {
            width = rightOff;
        }
        if (bottomOff < height) {
            height = bottomOff;
        }
        Rect rect2 = new Rect(leftOff, topOff, width, height);
        canvas.save();
        Matrix matrix = new Matrix();
        float width2 = f3 / ((float) rect.width());
        float height2 = f4 / ((float) rect.height());
        matrix.postScale(width2, height2);
        float f7 = ((float) rect.left) * width2;
        float f8 = ((float) rect.top) * height2;
        matrix.postTranslate(f - f7, f2 - f8);
        if (f7 >= 0.0f) {
            f7 = 0.0f;
        }
        if (f8 >= 0.0f) {
            f8 = 0.0f;
        }
        float f9 = f - f7;
        float f10 = f2 - f8;
        canvas.clipRect(f9, f10, (((float) rect2.width()) * width2) + f9, (((float) rect2.height()) * height2) + f10);
        canvas.drawBitmap(bitmap, matrix, this.paint);
        canvas.restore();
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0232, code lost:
        r2 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0235, code lost:
        r2 = r22;
        r8 = r5;
        r9 = r6;
        r10 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x023a, code lost:
        r7 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x023c, code lost:
        return FAIL;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x023d, code lost:
        r2 = r22;
        r10 = 2;
        r7 = r28;
        r8 = r29;
        r16 = r31;
        r17 = r32;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0254, code lost:
        if (r23.getSysKit().getPictureManage().hasBitmap() != false) goto L_0x0256;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x0256, code lost:
        r23.getSysKit().getPictureManage().clearBitmap();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x027d, code lost:
        return drawPicture(r22, r23, r24, r25, r26, r27, r7, r8, r30, r16, r17, r33, r34);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x027e, code lost:
        if (r12 == null) goto L_0x0280;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x0280, code lost:
        r0 = new android.graphics.BitmapFactory.Options();
        r0.inSampleSize = r10;
        r6 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x0289, code lost:
        r12.inSampleSize *= 2;
        r6 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x02aa, code lost:
        return drawPicture(r22, r23, r24, r25, r26, r6, r7, r8, r30, r16, r17, r33, r34);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0128, code lost:
        r2 = r22;
        r7 = r28;
        r8 = r29;
        r16 = r31;
        r17 = r32;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0132, code lost:
        r10 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x01a4, code lost:
        r2 = r22;
        r8 = r5;
        r7 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x01fa, code lost:
        r8 = r5;
        r9 = r6;
        r10 = 2;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x022e A[Catch:{ OutOfMemoryError -> 0x023a, Exception -> 0x023c }] */
    /* JADX WARNING: Removed duplicated region for block: B:110:? A[ExcHandler: Exception (unused java.lang.Exception), SYNTHETIC, Splitter:B:1:0x0011] */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x0256  */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x027e  */
    /* JADX WARNING: Removed duplicated region for block: B:54:? A[ExcHandler: OutOfMemoryError (unused java.lang.OutOfMemoryError), SYNTHETIC, Splitter:B:5:0x0020] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x018c A[SYNTHETIC, Splitter:B:69:0x018c] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x01d2 A[SYNTHETIC, Splitter:B:85:0x01d2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String drawPicture(android.graphics.Canvas r22, com.app.office.system.IControl r23, int r24, java.lang.String r25, byte r26, android.graphics.BitmapFactory.Options r27, float r28, float r29, float r30, float r31, float r32, com.app.office.common.pictureefftect.PictureEffectInfo r33, com.app.office.pg.animate.IAnimation r34) {
        /*
            r21 = this;
            r14 = r21
            r8 = r23
            r9 = r24
            r10 = r25
            r11 = r26
            r12 = r27
            r13 = r33
            java.lang.String r15 = "Fail"
            r7 = 2
            com.app.office.system.SysKit r0 = r23.getSysKit()     // Catch:{ OutOfMemoryError -> 0x023d, Exception -> 0x023c }
            com.app.office.common.picture.PictureManage r0 = r0.getPictureManage()     // Catch:{ OutOfMemoryError -> 0x023d, Exception -> 0x023c }
            android.graphics.Bitmap r0 = r0.getBitmap(r10)     // Catch:{ OutOfMemoryError -> 0x023d, Exception -> 0x023c }
            r6 = 0
            if (r0 != 0) goto L_0x0135
            boolean r0 = r21.isDrawPictrue()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            if (r0 != 0) goto L_0x0027
            return r6
        L_0x0027:
            com.app.office.system.SysKit r0 = r23.getSysKit()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            com.app.office.common.picture.PictureManage r0 = r0.getPictureManage()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            boolean r0 = r0.isConverting(r10)     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            if (r0 == 0) goto L_0x0041
            com.app.office.system.SysKit r0 = r23.getSysKit()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            com.app.office.common.picture.PictureManage r0 = r0.getPictureManage()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            r0.appendViewIndex(r10, r9)     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            return r6
        L_0x0041:
            r0 = 3
            r5 = -268435456(0xfffffffff0000000, float:-1.58456325E29)
            if (r11 == r0) goto L_0x00b8
            if (r11 != r7) goto L_0x0049
            goto L_0x00b8
        L_0x0049:
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            r0.<init>(r10)     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeStream(r0, r6, r12)     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            if (r0 != 0) goto L_0x00a7
            boolean r0 = r23.isSlideShow()     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            if (r0 == 0) goto L_0x0065
            com.app.office.system.SysKit r0 = r23.getSysKit()     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            com.app.office.pg.animate.AnimationManager r0 = r0.getAnimationManager()     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            r0.killAnimationTimer()     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
        L_0x0065:
            r0 = 5
            if (r11 != r0) goto L_0x007b
            com.app.office.system.SysKit r0 = r23.getSysKit()     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            com.app.office.common.picture.PictureManage r0 = r0.getPictureManage()     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            java.lang.String r1 = "jpeg"
            boolean r2 = r23.isSlideShow()     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            java.lang.String r0 = r0.convertToPng(r9, r10, r1, r2)     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            goto L_0x0092
        L_0x007b:
            r0 = 6
            if (r11 != r0) goto L_0x0091
            com.app.office.system.SysKit r0 = r23.getSysKit()     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            com.app.office.common.picture.PictureManage r0 = r0.getPictureManage()     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            java.lang.String r1 = "png"
            boolean r2 = r23.isSlideShow()     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            java.lang.String r0 = r0.convertToPng(r9, r10, r1, r2)     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            goto L_0x0092
        L_0x0091:
            r0 = r6
        L_0x0092:
            boolean r1 = r23.isSlideShow()     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            if (r1 == 0) goto L_0x00a6
            com.app.office.system.SysKit r1 = r23.getSysKit()     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            com.app.office.pg.animate.AnimationManager r1 = r1.getAnimationManager()     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            r1.restartAnimationTimer()     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
            r8.actionEvent(r5, r6)     // Catch:{ Exception -> 0x00b7, OutOfMemoryError -> 0x0128 }
        L_0x00a6:
            return r0
        L_0x00a7:
            if (r0 != 0) goto L_0x00aa
            return r15
        L_0x00aa:
            com.app.office.system.SysKit r1 = r23.getSysKit()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            com.app.office.common.picture.PictureManage r1 = r1.getPictureManage()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            r1.addBitmap(r10, r0)     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            goto L_0x0135
        L_0x00b7:
            return r15
        L_0x00b8:
            boolean r0 = r23.isSlideShow()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            if (r0 == 0) goto L_0x00c9
            com.app.office.system.SysKit r0 = r23.getSysKit()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            com.app.office.pg.animate.AnimationManager r0 = r0.getAnimationManager()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            r0.killAnimationTimer()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
        L_0x00c9:
            float r0 = r31 / r30
            int r0 = (int) r0     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            float r1 = r32 / r30
            int r1 = (int) r1     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            int r2 = r0 * r1
            int r3 = VectorMaxSize     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            if (r2 >= r3) goto L_0x00f6
            int r3 = r3 / r2
            double r2 = (double) r3     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            double r2 = java.lang.Math.sqrt(r2)     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            int r4 = VectorMaxZOOM     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            double r5 = (double) r4     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            int r18 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r18 <= 0) goto L_0x00e3
            double r2 = (double) r4     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
        L_0x00e3:
            double r4 = (double) r0     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            double r4 = r4 * r2
            long r4 = java.lang.Math.round(r4)     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            int r0 = (int) r4     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            double r4 = (double) r1     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            double r4 = r4 * r2
            long r1 = java.lang.Math.round(r4)     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            int r2 = (int) r1     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            r4 = r0
            r5 = r2
            goto L_0x00f8
        L_0x00f6:
            r4 = r0
            r5 = r1
        L_0x00f8:
            com.app.office.system.SysKit r0 = r23.getSysKit()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            com.app.office.common.picture.PictureManage r0 = r0.getPictureManage()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            boolean r6 = r23.isSlideShow()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            r1 = r24
            r2 = r26
            r3 = r25
            r7 = -268435456(0xfffffffff0000000, float:-1.58456325E29)
            r7 = 0
            java.lang.String r0 = r0.convertVectorgraphToPng(r1, r2, r3, r4, r5, r6)     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            boolean r1 = r23.isSlideShow()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            if (r1 == 0) goto L_0x0127
            com.app.office.system.SysKit r1 = r23.getSysKit()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            com.app.office.pg.animate.AnimationManager r1 = r1.getAnimationManager()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            r1.restartAnimationTimer()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            r1 = -268435456(0xfffffffff0000000, float:-1.58456325E29)
            r8.actionEvent(r1, r7)     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
        L_0x0127:
            return r0
        L_0x0128:
            r2 = r22
            r7 = r28
            r8 = r29
            r16 = r31
            r17 = r32
        L_0x0132:
            r10 = 2
            goto L_0x0248
        L_0x0135:
            r7 = r6
            if (r34 == 0) goto L_0x0180
            com.app.office.pg.animate.ShapeAnimation r1 = r34.getShapeAnimation()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            int r2 = r1.getParagraphBegin()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            int r1 = r1.getParagraphEnd()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            r3 = -2
            if (r2 != r3) goto L_0x0149
            if (r1 == r3) goto L_0x014e
        L_0x0149:
            r3 = -1
            if (r2 != r3) goto L_0x0180
            if (r1 != r3) goto L_0x0180
        L_0x014e:
            com.app.office.pg.animate.IAnimation$AnimationInformation r1 = r34.getCurrentAnimationInfor()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            int r1 = r1.getAlpha()     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            android.graphics.Paint r2 = r14.paint     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            r2.setAlpha(r1)     // Catch:{ OutOfMemoryError -> 0x0128, Exception -> 0x023c }
            float r1 = (float) r1
            r2 = 1132396544(0x437f0000, float:255.0)
            float r1 = r1 / r2
            r2 = 1056964608(0x3f000000, float:0.5)
            float r1 = r1 * r2
            r2 = 1073741824(0x40000000, float:2.0)
            float r3 = r31 / r2
            float r3 = r28 + r3
            float r4 = r32 / r2
            float r4 = r29 + r4
            float r5 = r31 * r1
            float r3 = r3 - r5
            float r5 = r32 * r1
            float r4 = r4 - r5
            float r1 = r1 * r2
            float r2 = r31 * r1
            float r1 = r1 * r32
            r17 = r1
            r16 = r2
            r6 = r3
            r5 = r4
            goto L_0x0188
        L_0x0180:
            r6 = r28
            r5 = r29
            r16 = r31
            r17 = r32
        L_0x0188:
            r1 = 0
            r2 = 1
            if (r13 == 0) goto L_0x01a9
            java.lang.Integer r3 = r33.getTransparentColor()     // Catch:{ OutOfMemoryError -> 0x01a4, Exception -> 0x023c }
            if (r3 == 0) goto L_0x01a9
            java.lang.Integer r3 = r33.getTransparentColor()     // Catch:{ OutOfMemoryError -> 0x01a4, Exception -> 0x023c }
            int r3 = r3.intValue()     // Catch:{ OutOfMemoryError -> 0x01a4, Exception -> 0x023c }
            android.graphics.Bitmap r3 = r14.createTransparentBitmapFromBitmap(r0, r3)     // Catch:{ OutOfMemoryError -> 0x01a4, Exception -> 0x023c }
            if (r3 == 0) goto L_0x01a9
            r4 = r3
            r19 = 1
            goto L_0x01ac
        L_0x01a4:
            r2 = r22
            r8 = r5
            r7 = r6
            goto L_0x0132
        L_0x01a9:
            r4 = r0
            r19 = 0
        L_0x01ac:
            if (r13 == 0) goto L_0x01c1
            java.lang.Integer r0 = r33.getAlpha()     // Catch:{ OutOfMemoryError -> 0x01a4, Exception -> 0x023c }
            if (r0 == 0) goto L_0x01c1
            android.graphics.Paint r0 = r14.paint     // Catch:{ OutOfMemoryError -> 0x01a4, Exception -> 0x023c }
            java.lang.Integer r1 = r33.getAlpha()     // Catch:{ OutOfMemoryError -> 0x01a4, Exception -> 0x023c }
            int r1 = r1.intValue()     // Catch:{ OutOfMemoryError -> 0x01a4, Exception -> 0x023c }
            r0.setAlpha(r1)     // Catch:{ OutOfMemoryError -> 0x01a4, Exception -> 0x023c }
        L_0x01c1:
            android.graphics.Paint r0 = r14.paint     // Catch:{ OutOfMemoryError -> 0x0235, Exception -> 0x023c }
            r14.applyEffect(r0, r13)     // Catch:{ OutOfMemoryError -> 0x0235, Exception -> 0x023c }
            android.graphics.Paint r0 = r14.paint     // Catch:{ OutOfMemoryError -> 0x0235, Exception -> 0x023c }
            r0.setAntiAlias(r2)     // Catch:{ OutOfMemoryError -> 0x0235, Exception -> 0x023c }
            android.graphics.Paint r0 = r14.paint     // Catch:{ OutOfMemoryError -> 0x0235, Exception -> 0x023c }
            r0.setFilterBitmap(r2)     // Catch:{ OutOfMemoryError -> 0x0235, Exception -> 0x023c }
            if (r13 == 0) goto L_0x01fe
            com.app.office.common.pictureefftect.PictureCroppedInfo r0 = r33.getPictureCroppedInfor()     // Catch:{ OutOfMemoryError -> 0x01fa, Exception -> 0x023c }
            if (r0 != 0) goto L_0x01d9
            goto L_0x01fe
        L_0x01d9:
            com.app.office.common.pictureefftect.PictureCroppedInfo r20 = r33.getPictureCroppedInfor()     // Catch:{ OutOfMemoryError -> 0x01fa, Exception -> 0x023c }
            r0 = r21
            r1 = r22
            r2 = r6
            r3 = r5
            r28 = r4
            r4 = r16
            r8 = r5
            r5 = r17
            r9 = r6
            r6 = r28
            r18 = r7
            r10 = 2
            r7 = r20
            r0.drawCropedPicture(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ OutOfMemoryError -> 0x0232, Exception -> 0x023c }
            r2 = r22
            r3 = r28
            goto L_0x0227
        L_0x01fa:
            r8 = r5
            r9 = r6
            r10 = 2
            goto L_0x0232
        L_0x01fe:
            r28 = r4
            r8 = r5
            r9 = r6
            r18 = r7
            r10 = 2
            android.graphics.Matrix r0 = new android.graphics.Matrix     // Catch:{ OutOfMemoryError -> 0x0232, Exception -> 0x023c }
            r0.<init>()     // Catch:{ OutOfMemoryError -> 0x0232, Exception -> 0x023c }
            int r1 = r28.getWidth()     // Catch:{ OutOfMemoryError -> 0x0232, Exception -> 0x023c }
            float r1 = (float) r1     // Catch:{ OutOfMemoryError -> 0x0232, Exception -> 0x023c }
            float r1 = r16 / r1
            int r2 = r28.getHeight()     // Catch:{ OutOfMemoryError -> 0x0232, Exception -> 0x023c }
            float r2 = (float) r2     // Catch:{ OutOfMemoryError -> 0x0232, Exception -> 0x023c }
            float r2 = r17 / r2
            r0.postScale(r1, r2)     // Catch:{ OutOfMemoryError -> 0x0232, Exception -> 0x023c }
            r0.postTranslate(r9, r8)     // Catch:{ OutOfMemoryError -> 0x0232, Exception -> 0x023c }
            android.graphics.Paint r1 = r14.paint     // Catch:{ OutOfMemoryError -> 0x0232, Exception -> 0x023c }
            r2 = r22
            r3 = r28
            r2.drawBitmap(r3, r0, r1)     // Catch:{ OutOfMemoryError -> 0x023a, Exception -> 0x023c }
        L_0x0227:
            android.graphics.Paint r0 = r14.paint     // Catch:{ OutOfMemoryError -> 0x023a, Exception -> 0x023c }
            r0.reset()     // Catch:{ OutOfMemoryError -> 0x023a, Exception -> 0x023c }
            if (r19 == 0) goto L_0x0231
            r3.recycle()     // Catch:{ OutOfMemoryError -> 0x023a, Exception -> 0x023c }
        L_0x0231:
            return r18
        L_0x0232:
            r2 = r22
            goto L_0x023a
        L_0x0235:
            r2 = r22
            r8 = r5
            r9 = r6
            r10 = 2
        L_0x023a:
            r7 = r9
            goto L_0x0248
        L_0x023c:
            return r15
        L_0x023d:
            r2 = r22
            r10 = 2
            r7 = r28
            r8 = r29
            r16 = r31
            r17 = r32
        L_0x0248:
            com.app.office.system.SysKit r0 = r23.getSysKit()
            com.app.office.common.picture.PictureManage r0 = r0.getPictureManage()
            boolean r0 = r0.hasBitmap()
            if (r0 == 0) goto L_0x027e
            com.app.office.system.SysKit r0 = r23.getSysKit()
            com.app.office.common.picture.PictureManage r0 = r0.getPictureManage()
            r0.clearBitmap()
            r0 = r21
            r1 = r22
            r2 = r23
            r3 = r24
            r4 = r25
            r5 = r26
            r6 = r27
            r9 = r30
            r10 = r16
            r11 = r17
            r12 = r33
            r13 = r34
            java.lang.String r0 = r0.drawPicture(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            return r0
        L_0x027e:
            if (r12 != 0) goto L_0x0289
            android.graphics.BitmapFactory$Options r0 = new android.graphics.BitmapFactory$Options
            r0.<init>()
            r0.inSampleSize = r10
            r6 = r0
            goto L_0x0290
        L_0x0289:
            int r0 = r12.inSampleSize
            int r0 = r0 * 2
            r12.inSampleSize = r0
            r6 = r12
        L_0x0290:
            r0 = r21
            r1 = r22
            r2 = r23
            r3 = r24
            r4 = r25
            r5 = r26
            r9 = r30
            r10 = r16
            r11 = r17
            r12 = r33
            r13 = r34
            java.lang.String r0 = r0.drawPicture(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.picture.PictureKit.drawPicture(android.graphics.Canvas, com.app.office.system.IControl, int, java.lang.String, byte, android.graphics.BitmapFactory$Options, float, float, float, float, float, com.app.office.common.pictureefftect.PictureEffectInfo, com.app.office.pg.animate.IAnimation):java.lang.String");
    }

    public Bitmap createTransparentBitmapFromBitmap(Bitmap bitmap, int i) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i2 = 0; i2 < height; i2++) {
            for (int i3 = 0; i3 < width; i3++) {
                int i4 = (i2 * width) + i3;
                int i5 = (iArr[i4] >> 8) & 255;
                int i6 = iArr[i4] & 255;
                int i7 = (i >> 8) & 255;
                int i8 = i & 255;
                if (Math.abs(((i >> 16) & 255) - ((iArr[i4] >> 16) & 255)) <= 10 && Math.abs(i7 - i5) <= 10 && Math.abs(i8 - i6) <= 10) {
                    iArr[i4] = 0;
                }
            }
        }
        return Bitmap.createBitmap(iArr, width, height, Bitmap.Config.ARGB_4444);
    }

    public void drawPicture(Canvas canvas, IControl iControl, Bitmap bitmap, float f, float f2, boolean z, float f3, float f4) {
        if (bitmap != null) {
            try {
                Matrix matrix = new Matrix();
                matrix.postScale(f3 / ((float) bitmap.getWidth()), f4 / ((float) bitmap.getHeight()));
                matrix.postTranslate(f, f2);
                canvas.drawBitmap(bitmap, matrix, this.paint);
            } catch (OutOfMemoryError e) {
                iControl.getSysKit().getErrorKit().writerLog(e);
            }
        }
    }

    public boolean isVectorPicture(Picture picture) {
        if (picture == null) {
            return false;
        }
        byte pictureType = picture.getPictureType();
        return pictureType == 3 || pictureType == 2;
    }

    public boolean isDrawPictrue() {
        return this.isDrawPictrue;
    }

    public void setDrawPictrue(boolean z) {
        this.isDrawPictrue = z;
    }
}
