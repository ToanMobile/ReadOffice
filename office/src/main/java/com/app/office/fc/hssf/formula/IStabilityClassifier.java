package com.app.office.fc.hssf.formula;

public interface IStabilityClassifier {
    public static final IStabilityClassifier TOTALLY_IMMUTABLE = new IStabilityClassifier() {
        public boolean isCellFinal(int i, int i2, int i3) {
            return true;
        }
    };

    boolean isCellFinal(int i, int i2, int i3);
}
