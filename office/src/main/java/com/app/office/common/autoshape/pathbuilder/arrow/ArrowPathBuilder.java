package com.app.office.common.autoshape.pathbuilder.arrow;

import android.graphics.Rect;
import com.app.office.common.shape.AutoShape;

public class ArrowPathBuilder {
    public static Object getArrowPath(AutoShape autoShape, Rect rect) {
        if (autoShape.isAutoShape07()) {
            return LaterArrowPathBuilder.getArrowPath(autoShape, rect);
        }
        return EarlyArrowPathBuilder.getArrowPath(autoShape, rect);
    }
}
