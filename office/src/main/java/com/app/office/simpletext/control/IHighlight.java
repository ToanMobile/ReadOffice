package com.app.office.simpletext.control;

import android.graphics.Canvas;
import com.app.office.simpletext.view.IView;

public interface IHighlight {
    void addHighlight(long j, long j2);

    void dispose();

    void draw(Canvas canvas, IView iView, int i, int i2, long j, long j2, float f);

    long getSelectEnd();

    long getSelectStart();

    String getSelectText();

    boolean isSelectText();

    void removeHighlight();

    void setPaintHighlight(boolean z);

    void setSelectEnd(long j);

    void setSelectStart(long j);
}
