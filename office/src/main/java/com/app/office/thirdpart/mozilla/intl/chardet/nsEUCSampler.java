package com.app.office.thirdpart.mozilla.intl.chardet;

import kotlin.jvm.internal.ByteCompanionObject;

public class nsEUCSampler {
    public int[] mFirstByteCnt = new int[94];
    public float[] mFirstByteFreq = new float[94];
    public int[] mSecondByteCnt = new int[94];
    public float[] mSecondByteFreq = new float[94];
    int mState = 0;
    int mThreshold = 200;
    int mTotal = 0;

    public nsEUCSampler() {
        Reset();
    }

    public void Reset() {
        this.mTotal = 0;
        this.mState = 0;
        for (int i = 0; i < 94; i++) {
            int[] iArr = this.mFirstByteCnt;
            this.mSecondByteCnt[i] = 0;
            iArr[i] = 0;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean EnoughData() {
        return this.mTotal > this.mThreshold;
    }

    /* access modifiers changed from: package-private */
    public boolean GetSomeData() {
        return this.mTotal > 1;
    }

    /* access modifiers changed from: package-private */
    public boolean Sample(byte[] bArr, int i) {
        if (this.mState == 1) {
            return false;
        }
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int i4 = this.mState;
            if (1 == i4) {
                break;
            }
            if (i4 != 0) {
                if (i4 != 1) {
                    if (i4 != 2) {
                        this.mState = 1;
                    } else if ((bArr[i3] & ByteCompanionObject.MIN_VALUE) == 0) {
                        this.mState = 1;
                    } else if (255 == (bArr[i3] & 255) || 161 > (bArr[i3] & 255)) {
                        this.mState = 1;
                    } else {
                        this.mTotal++;
                        int[] iArr = this.mSecondByteCnt;
                        int i5 = (bArr[i3] & 255) - 161;
                        iArr[i5] = iArr[i5] + 1;
                        this.mState = 0;
                    }
                }
            } else if ((bArr[i3] & ByteCompanionObject.MIN_VALUE) != 0) {
                if (255 == (bArr[i3] & 255) || 161 > (bArr[i3] & 255)) {
                    this.mState = 1;
                } else {
                    this.mTotal++;
                    int[] iArr2 = this.mFirstByteCnt;
                    int i6 = (255 & bArr[i3]) - 161;
                    iArr2[i6] = iArr2[i6] + 1;
                    this.mState = 2;
                }
            }
            i2++;
            i3++;
        }
        if (1 != this.mState) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void CalFreq() {
        for (int i = 0; i < 94; i++) {
            float[] fArr = this.mFirstByteFreq;
            int i2 = this.mTotal;
            fArr[i] = ((float) this.mFirstByteCnt[i]) / ((float) i2);
            this.mSecondByteFreq[i] = ((float) this.mSecondByteCnt[i]) / ((float) i2);
        }
    }

    /* access modifiers changed from: package-private */
    public float GetScore(float[] fArr, float f, float[] fArr2, float f2) {
        return (f * GetScore(fArr, this.mFirstByteFreq)) + (f2 * GetScore(fArr2, this.mSecondByteFreq));
    }

    /* access modifiers changed from: package-private */
    public float GetScore(float[] fArr, float[] fArr2) {
        float f = 0.0f;
        for (int i = 0; i < 94; i++) {
            float f2 = fArr[i] - fArr2[i];
            f += f2 * f2;
        }
        return ((float) Math.sqrt((double) f)) / 94.0f;
    }
}
