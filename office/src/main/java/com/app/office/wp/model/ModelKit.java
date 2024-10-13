package com.app.office.wp.model;

import com.app.office.constant.wp.WPModelConstant;

public class ModelKit {
    private static ModelKit kit = new ModelKit();

    public long getArea(long j) {
        return j & WPModelConstant.AREA_MASK;
    }

    public static ModelKit instance() {
        return kit;
    }
}
