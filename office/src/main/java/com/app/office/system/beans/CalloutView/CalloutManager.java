package com.app.office.system.beans.CalloutView;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.internal.view.SupportMenu;
import com.app.office.common.PaintKit;
import com.app.office.system.IControl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CalloutManager {
    private int alpha = 255;
    private int color = SupportMenu.CATEGORY_MASK;
    private IControl control;
    private HashMap<Integer, List<PathInfo>> mPathMap;
    private int mode = 0;
    private int width = 10;

    public CalloutManager(IControl iControl) {
        this.control = iControl;
        this.mPathMap = new HashMap<>();
    }

    public void drawPath(Canvas canvas, int i, float f) {
        canvas.scale(f, f);
        List list = this.mPathMap.get(Integer.valueOf(i));
        Paint paint = PaintKit.instance().getPaint();
        if (list != null) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                PathInfo pathInfo = (PathInfo) list.get(i2);
                paint.setStrokeWidth((float) pathInfo.width);
                paint.setColor(pathInfo.color);
                canvas.drawPath(pathInfo.path, paint);
            }
        }
    }

    public boolean isPathEmpty() {
        return this.mPathMap.size() == 0;
    }

    public boolean isPathEmpty(int i) {
        return this.mPathMap.get(Integer.valueOf(i)) == null;
    }

    public List<PathInfo> getPath(int i, boolean z) {
        if (z && this.mPathMap.get(Integer.valueOf(i)) == null) {
            this.mPathMap.put(Integer.valueOf(i), new ArrayList());
        }
        return this.mPathMap.get(Integer.valueOf(i));
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setAlpha(int i) {
        this.alpha = i;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public void setDrawingMode(int i) {
        if (i >= 0 && i <= 2) {
            this.mode = i;
        }
    }

    public int getDrawingMode() {
        return this.mode;
    }

    public void dispose() {
        this.mPathMap.clear();
        this.mPathMap = null;
        this.control = null;
    }
}
