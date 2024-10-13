package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.util.Hashtable;

public class SlideShowSlideInfoAtom extends PositionDependentRecordAtom {
    private static long _type = 1017;
    private byte[] _header;
    private byte effectDirection;
    private byte effectType;
    private boolean fAutoAdvance;
    private boolean fCursorVisible;
    private boolean fHidden;
    private boolean fLoopSound;
    private boolean fManualAdvance;
    private boolean fSound;
    private boolean fStopSound;
    private boolean reserved1;
    private boolean reserved2;
    private boolean reserved3;
    private boolean reserved4;
    private boolean reserved5;
    private boolean reserved6;
    private byte reserved7;
    private int slideTime;
    private int soundIdRef;
    private byte speed;
    private byte[] unused;

    public void updateOtherRecordReferences(Hashtable<Integer, Integer> hashtable) {
    }

    protected SlideShowSlideInfoAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this.slideTime = LittleEndian.getInt(bArr, i + 8);
        this.soundIdRef = LittleEndian.getInt(bArr, i + 12);
        this.effectDirection = bArr[i + 16];
        this.effectType = bArr[i + 17];
        this.speed = bArr[i + 20];
    }

    public boolean isValidateTransition() {
        switch (this.effectType) {
            case 0:
                byte b = this.effectDirection;
                if (b < 0 || b > 1) {
                    return false;
                }
                return true;
            case 1:
                return true;
            case 2:
            case 3:
                byte b2 = this.effectDirection;
                if (b2 < 0 || b2 > 1) {
                    return false;
                }
                return true;
            case 4:
                byte b3 = this.effectDirection;
                if (b3 < 0 || b3 > 7) {
                    return false;
                }
                return true;
            case 5:
                if (this.effectDirection == 0) {
                    return true;
                }
                return false;
            case 6:
                if (this.effectDirection == 0) {
                    return true;
                }
                return false;
            case 7:
                byte b4 = this.effectDirection;
                if (b4 < 0 || b4 > 7) {
                    return false;
                }
                return true;
            case 8:
                byte b5 = this.effectDirection;
                if (b5 < 0 || b5 > 1) {
                    return false;
                }
                return true;
            case 9:
                byte b6 = this.effectDirection;
                if (b6 < 4 || b6 > 7) {
                    return false;
                }
                return true;
            case 10:
                byte b7 = this.effectDirection;
                if (b7 < 0 || b7 > 3) {
                    return false;
                }
                return true;
            case 11:
                byte b8 = this.effectDirection;
                if (b8 < 0 || b8 > 1) {
                    return false;
                }
                return true;
            case 13:
                byte b9 = this.effectDirection;
                if (b9 < 0 || b9 > 3) {
                    return false;
                }
                return true;
            case 17:
            case 18:
            case 19:
                if (this.effectDirection == 0) {
                    return true;
                }
                return false;
            case 20:
                byte b10 = this.effectDirection;
                if (b10 < 0 || b10 > 3) {
                    return false;
                }
                return true;
            case 21:
                byte b11 = this.effectDirection;
                if (b11 < 0 || b11 > 1) {
                    return false;
                }
                return true;
            case 22:
            case 23:
                if (this.effectDirection == 0) {
                    return true;
                }
                return false;
            case 26:
                byte b12 = this.effectDirection;
                if ((b12 < 1 || b12 > 4) && b12 != 8) {
                    return false;
                }
                return true;
            case 27:
                return this.effectDirection == 0;
            default:
                return false;
        }
    }

    public long getRecordType() {
        return _type;
    }

    public void dispose() {
        this._header = null;
    }
}
