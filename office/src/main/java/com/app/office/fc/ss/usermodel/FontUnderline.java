package com.app.office.fc.ss.usermodel;

public enum FontUnderline {
    SINGLE(1),
    DOUBLE(2),
    SINGLE_ACCOUNTING(3),
    DOUBLE_ACCOUNTING(4),
    NONE(5);
    
    private static FontUnderline[] _table;
    private int value;

    static {
        int i;
        _table = new FontUnderline[6];
        for (FontUnderline fontUnderline : values()) {
            _table[fontUnderline.getValue()] = fontUnderline;
        }
    }

    private FontUnderline(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    /* renamed from: com.app.office.fc.ss.usermodel.FontUnderline$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$reader$office$fc$ss$usermodel$FontUnderline = null;

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.app.office.fc.ss.usermodel.FontUnderline[] r0 = com.app.office.fc.ss.usermodel.FontUnderline.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$reader$office$fc$ss$usermodel$FontUnderline = r0
                com.app.office.fc.ss.usermodel.FontUnderline r1 = com.app.office.fc.ss.usermodel.FontUnderline.DOUBLE     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$reader$office$fc$ss$usermodel$FontUnderline     // Catch:{ NoSuchFieldError -> 0x001d }
                com.app.office.fc.ss.usermodel.FontUnderline r1 = com.app.office.fc.ss.usermodel.FontUnderline.DOUBLE_ACCOUNTING     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$reader$office$fc$ss$usermodel$FontUnderline     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.app.office.fc.ss.usermodel.FontUnderline r1 = com.app.office.fc.ss.usermodel.FontUnderline.SINGLE_ACCOUNTING     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$reader$office$fc$ss$usermodel$FontUnderline     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.app.office.fc.ss.usermodel.FontUnderline r1 = com.app.office.fc.ss.usermodel.FontUnderline.NONE     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$reader$office$fc$ss$usermodel$FontUnderline     // Catch:{ NoSuchFieldError -> 0x003e }
                com.app.office.fc.ss.usermodel.FontUnderline r1 = com.app.office.fc.ss.usermodel.FontUnderline.SINGLE     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ss.usermodel.FontUnderline.AnonymousClass1.<clinit>():void");
        }
    }

    public byte getByteValue() {
        int i = AnonymousClass1.$SwitchMap$com$reader$office$fc$ss$usermodel$FontUnderline[ordinal()];
        if (i == 1) {
            return 2;
        }
        if (i == 2) {
            return 34;
        }
        if (i != 3) {
            return i != 4 ? (byte) 1 : 0;
        }
        return 33;
    }

    public static FontUnderline valueOf(int i) {
        return _table[i];
    }

    public static FontUnderline valueOf(byte b) {
        if (b == 1) {
            return SINGLE;
        }
        if (b == 2) {
            return DOUBLE;
        }
        if (b == 33) {
            return SINGLE_ACCOUNTING;
        }
        if (b != 34) {
            return NONE;
        }
        return DOUBLE_ACCOUNTING;
    }
}
