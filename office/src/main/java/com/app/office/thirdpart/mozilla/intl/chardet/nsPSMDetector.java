package com.app.office.thirdpart.mozilla.intl.chardet;

public abstract class nsPSMDetector {
    public static final int ALL = 0;
    public static final int CHINESE = 2;
    public static final int JAPANESE = 1;
    public static final int KOREAN = 5;
    public static final int MAX_VERIFIERS = 16;
    public static final int NO_OF_LANGUAGES = 6;
    public static final int SIMPLIFIED_CHINESE = 3;
    public static final int TRADITIONAL_CHINESE = 4;
    int mClassItems;
    boolean mClassRunSampler;
    boolean mDone;
    int[] mItemIdx = new int[16];
    int mItems;
    boolean mRunSampler;
    nsEUCSampler mSampler = new nsEUCSampler();
    byte[] mState = new byte[16];
    nsEUCStatistics[] mStatisticsData;
    nsVerifier[] mVerifier;

    public abstract void Report(String str);

    public nsPSMDetector() {
        initVerifiers(0);
        Reset();
    }

    public nsPSMDetector(int i) {
        initVerifiers(i);
        Reset();
    }

    public nsPSMDetector(int i, nsVerifier[] nsverifierArr, nsEUCStatistics[] nseucstatisticsArr) {
        this.mClassRunSampler = nseucstatisticsArr != null;
        this.mStatisticsData = nseucstatisticsArr;
        this.mVerifier = nsverifierArr;
        this.mClassItems = i;
        Reset();
    }

    public void Reset() {
        this.mRunSampler = this.mClassRunSampler;
        this.mDone = false;
        this.mItems = this.mClassItems;
        for (int i = 0; i < this.mItems; i++) {
            this.mState[i] = 0;
            this.mItemIdx[i] = i;
        }
        this.mSampler.Reset();
    }

    /* access modifiers changed from: protected */
    public void initVerifiers(int i) {
        int i2 = i;
        boolean z = false;
        if (i2 < 0 || i2 >= 6) {
            i2 = 0;
        }
        this.mVerifier = null;
        this.mStatisticsData = null;
        if (i2 == 4) {
            this.mVerifier = new nsVerifier[]{new nsUTF8Verifier(), new nsBIG5Verifier(), new nsISO2022CNVerifier(), new nsEUCTWVerifier(), new nsCP1252Verifier(), new nsUCS2BEVerifier(), new nsUCS2LEVerifier()};
            this.mStatisticsData = new nsEUCStatistics[]{null, new Big5Statistics(), null, new EUCTWStatistics(), null, null, null};
        } else if (i2 == 5) {
            this.mVerifier = new nsVerifier[]{new nsUTF8Verifier(), new nsEUCKRVerifier(), new nsISO2022KRVerifier(), new nsCP1252Verifier(), new nsUCS2BEVerifier(), new nsUCS2LEVerifier()};
        } else if (i2 == 3) {
            this.mVerifier = new nsVerifier[]{new nsUTF8Verifier(), new nsGB2312Verifier(), new nsGB18030Verifier(), new nsISO2022CNVerifier(), new nsHZVerifier(), new nsCP1252Verifier(), new nsUCS2BEVerifier(), new nsUCS2LEVerifier()};
        } else if (i2 == 1) {
            this.mVerifier = new nsVerifier[]{new nsUTF8Verifier(), new nsSJISVerifier(), new nsEUCJPVerifier(), new nsISO2022JPVerifier(), new nsCP1252Verifier(), new nsUCS2BEVerifier(), new nsUCS2LEVerifier()};
        } else if (i2 == 2) {
            this.mVerifier = new nsVerifier[]{new nsUTF8Verifier(), new nsGB2312Verifier(), new nsGB18030Verifier(), new nsBIG5Verifier(), new nsISO2022CNVerifier(), new nsHZVerifier(), new nsEUCTWVerifier(), new nsCP1252Verifier(), new nsUCS2BEVerifier(), new nsUCS2LEVerifier()};
            this.mStatisticsData = new nsEUCStatistics[]{null, new GB2312Statistics(), null, new Big5Statistics(), null, null, new EUCTWStatistics(), null, null, null};
        } else if (i2 == 0) {
            this.mVerifier = new nsVerifier[]{new nsUTF8Verifier(), new nsSJISVerifier(), new nsEUCJPVerifier(), new nsISO2022JPVerifier(), new nsEUCKRVerifier(), new nsISO2022KRVerifier(), new nsBIG5Verifier(), new nsEUCTWVerifier(), new nsGB2312Verifier(), new nsGB18030Verifier(), new nsISO2022CNVerifier(), new nsHZVerifier(), new nsCP1252Verifier(), new nsUCS2BEVerifier(), new nsUCS2LEVerifier()};
            this.mStatisticsData = new nsEUCStatistics[]{null, null, new EUCJPStatistics(), null, new EUCKRStatistics(), null, new Big5Statistics(), new EUCTWStatistics(), new GB2312Statistics(), null, null, null, null, null, null};
        }
        if (this.mStatisticsData != null) {
            z = true;
        }
        this.mClassRunSampler = z;
        this.mClassItems = this.mVerifier.length;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x004f, code lost:
        if (r4 > 1) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0051, code lost:
        if (1 != r4) goto L_0x0062;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0053, code lost:
        Report(r8.mVerifier[r8.mItemIdx[0]].charset());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0062, code lost:
        r8.mDone = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0064, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0065, code lost:
        r2 = 0;
        r3 = 0;
        r4 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006a, code lost:
        if (r2 >= r8.mItems) goto L_0x008e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0078, code lost:
        if (r8.mVerifier[r8.mItemIdx[r2]].isUCS2() != false) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0086, code lost:
        if (r8.mVerifier[r8.mItemIdx[r2]].isUCS2() != false) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0088, code lost:
        r3 = r3 + 1;
        r4 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x008b, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008e, code lost:
        if (1 != r3) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0090, code lost:
        Report(r8.mVerifier[r8.mItemIdx[r4]].charset());
        r8.mDone = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a1, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a2, code lost:
        r1 = r1 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean HandleData(byte[] r9, int r10) {
        /*
            r8 = this;
            r0 = 0
            r1 = 0
        L_0x0002:
            if (r1 >= r10) goto L_0x00a6
            byte r2 = r9[r1]
            r3 = 0
        L_0x0007:
            int r4 = r8.mItems
            r5 = 1
            if (r3 >= r4) goto L_0x004f
            com.app.office.thirdpart.mozilla.intl.chardet.nsVerifier[] r4 = r8.mVerifier
            int[] r6 = r8.mItemIdx
            r6 = r6[r3]
            r4 = r4[r6]
            byte[] r6 = r8.mState
            byte r6 = r6[r3]
            byte r4 = com.app.office.thirdpart.mozilla.intl.chardet.nsVerifier.getNextState(r4, r2, r6)
            r6 = 2
            if (r4 != r6) goto L_0x0031
            com.app.office.thirdpart.mozilla.intl.chardet.nsVerifier[] r9 = r8.mVerifier
            int[] r10 = r8.mItemIdx
            r10 = r10[r3]
            r9 = r9[r10]
            java.lang.String r9 = r9.charset()
            r8.Report(r9)
            r8.mDone = r5
            return r5
        L_0x0031:
            if (r4 != r5) goto L_0x0047
            int r4 = r8.mItems
            int r4 = r4 - r5
            r8.mItems = r4
            if (r3 >= r4) goto L_0x0007
            int[] r5 = r8.mItemIdx
            r6 = r5[r4]
            r5[r3] = r6
            byte[] r5 = r8.mState
            byte r4 = r5[r4]
            r5[r3] = r4
            goto L_0x0007
        L_0x0047:
            byte[] r5 = r8.mState
            int r6 = r3 + 1
            r5[r3] = r4
            r3 = r6
            goto L_0x0007
        L_0x004f:
            if (r4 > r5) goto L_0x0065
            if (r5 != r4) goto L_0x0062
            com.app.office.thirdpart.mozilla.intl.chardet.nsVerifier[] r9 = r8.mVerifier
            int[] r10 = r8.mItemIdx
            r10 = r10[r0]
            r9 = r9[r10]
            java.lang.String r9 = r9.charset()
            r8.Report(r9)
        L_0x0062:
            r8.mDone = r5
            return r5
        L_0x0065:
            r2 = 0
            r3 = 0
            r4 = 0
        L_0x0068:
            int r6 = r8.mItems
            if (r2 >= r6) goto L_0x008e
            com.app.office.thirdpart.mozilla.intl.chardet.nsVerifier[] r6 = r8.mVerifier
            int[] r7 = r8.mItemIdx
            r7 = r7[r2]
            r6 = r6[r7]
            boolean r6 = r6.isUCS2()
            if (r6 != 0) goto L_0x008b
            com.app.office.thirdpart.mozilla.intl.chardet.nsVerifier[] r6 = r8.mVerifier
            int[] r7 = r8.mItemIdx
            r7 = r7[r2]
            r6 = r6[r7]
            boolean r6 = r6.isUCS2()
            if (r6 != 0) goto L_0x008b
            int r3 = r3 + 1
            r4 = r2
        L_0x008b:
            int r2 = r2 + 1
            goto L_0x0068
        L_0x008e:
            if (r5 != r3) goto L_0x00a2
            com.app.office.thirdpart.mozilla.intl.chardet.nsVerifier[] r9 = r8.mVerifier
            int[] r10 = r8.mItemIdx
            r10 = r10[r4]
            r9 = r9[r10]
            java.lang.String r9 = r9.charset()
            r8.Report(r9)
            r8.mDone = r5
            return r5
        L_0x00a2:
            int r1 = r1 + 1
            goto L_0x0002
        L_0x00a6:
            boolean r0 = r8.mRunSampler
            if (r0 == 0) goto L_0x00ad
            r8.Sample(r9, r10)
        L_0x00ad:
            boolean r9 = r8.mDone
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.thirdpart.mozilla.intl.chardet.nsPSMDetector.HandleData(byte[], int):boolean");
    }

    public void DataEnd() {
        if (!this.mDone) {
            if (this.mItems == 2) {
                if (this.mVerifier[this.mItemIdx[0]].charset().equals("GB18030")) {
                    Report(this.mVerifier[this.mItemIdx[1]].charset());
                    this.mDone = true;
                } else if (this.mVerifier[this.mItemIdx[1]].charset().equals("GB18030")) {
                    Report(this.mVerifier[this.mItemIdx[0]].charset());
                    this.mDone = true;
                }
            }
            if (this.mItems == 4 && this.mVerifier[this.mItemIdx[1]].charset().equals("Shift_JIS")) {
                Report(this.mVerifier[this.mItemIdx[1]].charset());
                this.mDone = true;
            }
            if (this.mRunSampler) {
                Sample((byte[]) null, 0, true);
            }
        }
    }

    public void Sample(byte[] bArr, int i) {
        Sample(bArr, i, false);
    }

    public void Sample(byte[] bArr, int i, boolean z) {
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < this.mItems; i4++) {
            nsEUCStatistics[] nseucstatisticsArr = this.mStatisticsData;
            int[] iArr = this.mItemIdx;
            if (nseucstatisticsArr[iArr[i4]] != null) {
                i2++;
            }
            if (!this.mVerifier[iArr[i4]].isUCS2() && !this.mVerifier[this.mItemIdx[i4]].charset().equals("GB18030")) {
                i3++;
            }
        }
        boolean z2 = i2 > 1;
        this.mRunSampler = z2;
        if (z2) {
            this.mRunSampler = this.mSampler.Sample(bArr, i);
            if (((z && this.mSampler.GetSomeData()) || this.mSampler.EnoughData()) && i2 == i3) {
                this.mSampler.CalFreq();
                int i5 = -1;
                float f = 0.0f;
                int i6 = 0;
                for (int i7 = 0; i7 < this.mItems; i7++) {
                    nsEUCStatistics[] nseucstatisticsArr2 = this.mStatisticsData;
                    int[] iArr2 = this.mItemIdx;
                    if (nseucstatisticsArr2[iArr2[i7]] != null && !this.mVerifier[iArr2[i7]].charset().equals("Big5")) {
                        float GetScore = this.mSampler.GetScore(this.mStatisticsData[this.mItemIdx[i7]].mFirstByteFreq(), this.mStatisticsData[this.mItemIdx[i7]].mFirstByteWeight(), this.mStatisticsData[this.mItemIdx[i7]].mSecondByteFreq(), this.mStatisticsData[this.mItemIdx[i7]].mSecondByteWeight());
                        int i8 = i6 + 1;
                        if (i6 == 0 || f > GetScore) {
                            i5 = i7;
                            f = GetScore;
                        }
                        i6 = i8;
                    }
                }
                if (i5 >= 0) {
                    Report(this.mVerifier[this.mItemIdx[i5]].charset());
                    this.mDone = true;
                }
            }
        }
    }

    public String[] getProbableCharsets() {
        int i = this.mItems;
        if (i <= 0) {
            return new String[]{"nomatch"};
        }
        String[] strArr = new String[i];
        for (int i2 = 0; i2 < this.mItems; i2++) {
            strArr[i2] = this.mVerifier[this.mItemIdx[i2]].charset();
        }
        return strArr;
    }
}
