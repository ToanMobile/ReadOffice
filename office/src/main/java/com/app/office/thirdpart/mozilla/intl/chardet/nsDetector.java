package com.app.office.thirdpart.mozilla.intl.chardet;

import kotlin.jvm.internal.ByteCompanionObject;

public class nsDetector extends nsPSMDetector implements nsICharsetDetector {
    nsICharsetDetectionObserver mObserver = null;

    public nsDetector() {
    }

    public nsDetector(int i) {
        super(i);
    }

    public void Init(nsICharsetDetectionObserver nsicharsetdetectionobserver) {
        this.mObserver = nsicharsetdetectionobserver;
    }

    public boolean DoIt(byte[] bArr, int i, boolean z) {
        if (bArr == null || z) {
            return false;
        }
        HandleData(bArr, i);
        return this.mDone;
    }

    public void Done() {
        DataEnd();
    }

    public void Report(String str) {
        nsICharsetDetectionObserver nsicharsetdetectionobserver = this.mObserver;
        if (nsicharsetdetectionobserver != null) {
            nsicharsetdetectionobserver.Notify(str);
        }
    }

    public boolean isAscii(byte[] bArr, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if ((bArr[i2] & ByteCompanionObject.MIN_VALUE) != 0) {
                return false;
            }
        }
        return true;
    }
}
