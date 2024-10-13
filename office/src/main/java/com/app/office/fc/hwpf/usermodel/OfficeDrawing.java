package com.app.office.fc.hwpf.usermodel;

import com.app.office.common.pictureefftect.PictureEffectInfo;
import com.app.office.system.IControl;

public interface OfficeDrawing {

    public enum HorizontalPositioning {
        ABSOLUTE,
        CENTER,
        INSIDE,
        LEFT,
        OUTSIDE,
        RIGHT
    }

    public enum HorizontalRelativeElement {
        CHAR,
        MARGIN,
        PAGE,
        TEXT
    }

    public enum VerticalPositioning {
        ABSOLUTE,
        BOTTOM,
        CENTER,
        INSIDE,
        OUTSIDE,
        TOP
    }

    public enum VerticalRelativeElement {
        LINE,
        MARGIN,
        PAGE,
        TEXT
    }

    HWPFShape getAutoShape();

    byte getHorizontalPositioning();

    byte getHorizontalRelative();

    byte[] getPictureData(IControl iControl);

    byte[] getPictureData(IControl iControl, int i);

    PictureEffectInfo getPictureEffectInfor();

    int getRectangleBottom();

    int getRectangleLeft();

    int getRectangleRight();

    int getRectangleTop();

    int getShapeId();

    String getTempFilePath(IControl iControl);

    byte getVerticalPositioning();

    byte getVerticalRelativeElement();

    int getWrap();

    boolean isAnchorLock();

    boolean isBelowText();
}
