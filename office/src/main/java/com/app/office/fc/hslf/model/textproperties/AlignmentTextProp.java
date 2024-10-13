package com.app.office.fc.hslf.model.textproperties;

import com.app.office.fc.ss.util.CellUtil;

public class AlignmentTextProp extends TextProp {
    public static final int CENTER = 1;
    public static final int JUSTIFY = 3;
    public static final int JUSTIFYLOW = 6;
    public static final int LEFT = 0;
    public static final int RIGHT = 2;
    public static final int THAIDISTRIBUTED = 5;

    public AlignmentTextProp() {
        super(2, 2048, CellUtil.ALIGNMENT);
    }
}
