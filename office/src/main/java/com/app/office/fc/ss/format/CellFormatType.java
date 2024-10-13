package com.app.office.fc.ss.format;

public enum CellFormatType {
    GENERAL {
        /* access modifiers changed from: package-private */
        public boolean isSpecial(char c) {
            return false;
        }

        /* access modifiers changed from: package-private */
        public CellFormatter formatter(String str) {
            return new CellGeneralFormatter();
        }
    },
    NUMBER {
        /* access modifiers changed from: package-private */
        public boolean isSpecial(char c) {
            return false;
        }

        /* access modifiers changed from: package-private */
        public CellFormatter formatter(String str) {
            return new CellNumberFormatter(str);
        }
    },
    DATE {
        /* access modifiers changed from: package-private */
        public boolean isSpecial(char c) {
            return c == '\'' || (c <= 127 && Character.isLetter(c));
        }

        /* access modifiers changed from: package-private */
        public CellFormatter formatter(String str) {
            return new CellDateFormatter(str);
        }
    },
    ELAPSED {
        /* access modifiers changed from: package-private */
        public boolean isSpecial(char c) {
            return false;
        }

        /* access modifiers changed from: package-private */
        public CellFormatter formatter(String str) {
            return new CellElapsedFormatter(str);
        }
    },
    TEXT {
        /* access modifiers changed from: package-private */
        public boolean isSpecial(char c) {
            return false;
        }

        /* access modifiers changed from: package-private */
        public CellFormatter formatter(String str) {
            return new CellTextFormatter(str);
        }
    };

    /* access modifiers changed from: package-private */
    public abstract CellFormatter formatter(String str);

    /* access modifiers changed from: package-private */
    public abstract boolean isSpecial(char c);
}
