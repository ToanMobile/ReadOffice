package com.app.office.wp.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.app.office.common.BackgroundDrawer;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Border;
import com.app.office.common.borders.Borders;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.animate.IAnimation;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.AbstractView;
import com.app.office.simpletext.view.IView;
import com.app.office.wp.model.WPDocument;
import java.util.ArrayList;
import java.util.List;

public class PageView extends AbstractView {
    private TitleView footer;
    private boolean hasBreakTable;
    private TitleView header;
    private int pageBRColor;
    private int pageBorder = -1;
    private int pageNumber;
    private Paint paint;
    private List<LeafView> shapeViews;

    public short getType() {
        return 4;
    }

    public PageView(IElement iElement) {
        this.elem = iElement;
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setStrokeWidth(2.0f);
    }

    public void draw(Canvas canvas, int i, int i2, float f) {
        canvas.save();
        int i3 = ((int) (((float) this.x) * f)) + i;
        int i4 = ((int) (((float) this.y) * f)) + i2;
        float f2 = (float) i3;
        float f3 = (float) i4;
        canvas.clipRect(f2, f3, (((float) getWidth()) * f) + f2, (((float) getHeight()) * f) + f3);
        drawBackground(canvas, i3, i4, f);
        drawBorder(canvas, i3, i4, f);
        drawPaper(canvas, i3, i4, f);
        drawPageSeparated(canvas, i3, i4, f);
        TitleView titleView = this.header;
        if (titleView != null) {
            titleView.setParentView(this);
            this.header.draw(canvas, i3, i4, f);
        }
        TitleView titleView2 = this.footer;
        if (titleView2 != null) {
            titleView2.setParentView(this);
            this.footer.draw(canvas, i3, i4, f);
        }
        Canvas canvas2 = canvas;
        int i5 = i3;
        int i6 = i4;
        float f4 = f;
        drawShape(canvas2, i5, i6, f4, true);
        super.draw(canvas, i, i2, f);
        drawShape(canvas2, i5, i6, f4, false);
        canvas.restore();
    }

    public void drawForPrintMode(Canvas canvas, int i, int i2, float f) {
        int i3 = ((int) (((float) this.x) * f)) + i;
        int i4 = ((int) (((float) this.y) * f)) + i2;
        drawBackground(canvas, i3, i4, f);
        drawBorder(canvas, i3, i4, f);
        drawPageSeparated(canvas, i3, i4, f);
        TitleView titleView = this.header;
        if (titleView != null) {
            titleView.setParentView(this);
            this.header.draw(canvas, i3, i4, f);
        }
        TitleView titleView2 = this.footer;
        if (titleView2 != null) {
            titleView2.setParentView(this);
            this.footer.draw(canvas, i3, i4, f);
        }
        Canvas canvas2 = canvas;
        int i5 = i3;
        int i6 = i4;
        float f2 = f;
        drawShape(canvas2, i5, i6, f2, true);
        super.draw(canvas, i, i2, f);
        drawShape(canvas2, i5, i6, f2, false);
    }

    public void drawToImage(Canvas canvas, int i, int i2, float f) {
        int i3 = ((int) (((float) this.x) * f)) + i;
        int i4 = ((int) (((float) this.y) * f)) + i2;
        drawBackground(canvas, i3, i4, f);
        drawBorder(canvas, i3, i4, f);
        TitleView titleView = this.header;
        if (titleView != null) {
            titleView.setParentView(this);
            this.header.draw(canvas, i3, i4, f);
        }
        TitleView titleView2 = this.footer;
        if (titleView2 != null) {
            titleView2.setParentView(this);
            this.footer.draw(canvas, i3, i4, f);
        }
        Canvas canvas2 = canvas;
        int i5 = i3;
        int i6 = i4;
        float f2 = f;
        drawShape(canvas2, i5, i6, f2, true);
        super.draw(canvas, i, i2, f);
        drawShape(canvas2, i5, i6, f2, false);
    }

    private void drawBackground(Canvas canvas, int i, int i2, float f) {
        int width = ((int) (((float) getWidth()) * f)) + i;
        int height = ((int) (((float) getHeight()) * f)) + i2;
        Rect rect = new Rect(i, i2, width, height);
        BackgroundAndFill pageBackground = ((WPDocument) getDocument()).getPageBackground();
        if (pageBackground != null) {
            BackgroundDrawer.drawBackground(canvas, getControl(), this.pageNumber, pageBackground, rect, (IAnimation) null, f);
            return;
        }
        this.paint.setColor(-1);
        canvas.drawRect((float) i, (float) i2, (float) width, (float) height, this.paint);
    }

    private void drawBorder(Canvas canvas, int i, int i2, float f) {
        float f2;
        int i3;
        int i4;
        if (this.pageBorder >= 0) {
            int width = (int) (((float) getWidth()) * f);
            int height = (int) (((float) getHeight()) * f);
            Borders borders = getControl().getSysKit().getBordersManage().getBorders(this.pageBorder);
            int color = this.paint.getColor();
            if (borders != null) {
                Border leftBorder = borders.getLeftBorder();
                Border topBorder = borders.getTopBorder();
                Border rightBorder = borders.getRightBorder();
                Border bottomBorder = borders.getBottomBorder();
                int i5 = 0;
                if (leftBorder != null) {
                    this.paint.setColor(leftBorder.getColor());
                    int space = ((int) (((float) leftBorder.getSpace()) * f)) + i;
                    if (topBorder == null) {
                        i4 = 0;
                    } else {
                        i4 = (int) (((float) topBorder.getSpace()) * f);
                    }
                    int i6 = i4 + i2;
                    float f3 = (float) height;
                    if (bottomBorder != null) {
                        f3 -= ((float) bottomBorder.getSpace()) * f;
                    }
                    float f4 = (float) space;
                    canvas.drawLine(f4, (float) i6, f4, (float) (((int) f3) + i2), this.paint);
                }
                if (topBorder != null) {
                    this.paint.setColor(topBorder.getColor());
                    int space2 = ((int) (((float) topBorder.getSpace()) * f)) + i2;
                    if (leftBorder == null) {
                        i3 = 0;
                    } else {
                        i3 = (int) (((float) leftBorder.getSpace()) * f);
                    }
                    int i7 = (i3 + i) - 1;
                    float f5 = (float) width;
                    if (rightBorder != null) {
                        f5 -= ((float) rightBorder.getSpace()) * f;
                    }
                    float f6 = (float) space2;
                    canvas.drawLine((float) i7, f6, (float) (((int) f5) + i + 1), f6, this.paint);
                }
                if (rightBorder != null) {
                    this.paint.setColor(rightBorder.getColor());
                    int space3 = ((int) (((float) width) - (((float) rightBorder.getSpace()) * f))) + i;
                    if (topBorder == null) {
                        f2 = 0.0f;
                    } else {
                        f2 = ((float) topBorder.getSpace()) * f;
                    }
                    int i8 = ((int) f2) + i2;
                    float f7 = (float) height;
                    if (bottomBorder != null) {
                        f7 -= ((float) bottomBorder.getSpace()) * f;
                    }
                    float f8 = (float) space3;
                    canvas.drawLine(f8, (float) i8, f8, (float) (((int) f7) + i2), this.paint);
                }
                if (bottomBorder != null) {
                    this.paint.setColor(bottomBorder.getColor());
                    int space4 = ((int) (((float) height) - (((float) topBorder.getSpace()) * f))) + i2;
                    if (leftBorder != null) {
                        i5 = (int) (((float) leftBorder.getSpace()) * f);
                    }
                    int i9 = (i5 + i) - 1;
                    float f9 = (float) width;
                    if (rightBorder != null) {
                        f9 -= ((float) rightBorder.getSpace()) * f;
                    }
                    float f10 = (float) space4;
                    canvas.drawLine((float) i9, f10, (float) (((int) f9) + i + 1), f10, this.paint);
                }
            }
            this.paint.setColor(color);
        }
    }

    private void drawPaper(Canvas canvas, int i, int i2, float f) {
        canvas.save();
        int height = (int) (((float) getHeight()) * f);
        int width = ((int) (((float) getWidth()) * f)) + i;
        int i3 = height + i2;
        canvas.clipRect(i, i2, width + 5, i3 + 5);
        this.paint.setColor(-16777216);
        float f2 = (float) i;
        float f3 = (float) i2;
        float f4 = (float) width;
        Canvas canvas2 = canvas;
        float f5 = f2;
        float f6 = f3;
        canvas2.drawLine(f5, f6, f4, f3, this.paint);
        float f7 = (float) i3;
        float f8 = f7;
        canvas2.drawLine(f5, f6, f2, f8, this.paint);
        float f9 = f7;
        canvas.drawLine(f4, f3, f4, f9, this.paint);
        canvas.drawLine(f2, f9, f4, f8, this.paint);
        canvas.restore();
    }

    private void drawPageSeparated(Canvas canvas, int i, int i2, float f) {
        float f2 = (float) i;
        float leftIndent = (((float) getLeftIndent()) * f) + f2;
        float f3 = (float) i2;
        float topIndent = (((float) getTopIndent()) * f) + f3;
        this.paint.setColor(-7829368);
        float f4 = leftIndent - 1.0f;
        float f5 = (float) 30;
        float f6 = topIndent - f5;
        Canvas canvas2 = canvas;
        float f7 = leftIndent;
        float f8 = topIndent;
        canvas2.drawRect(f4, f6, f7, f8, this.paint);
        float f9 = leftIndent - f5;
        float f10 = topIndent - 1.0f;
        canvas2.drawRect(f9, f10, f7, f8, this.paint);
        float width = f2 + (((float) (getWidth() - getRightIndent())) * f);
        float f11 = width + 1.0f;
        Canvas canvas3 = canvas;
        float f12 = width;
        float f13 = topIndent;
        float f14 = f5;
        canvas3.drawRect(f12, f6, f11, f13, this.paint);
        float f15 = width + f14;
        canvas3.drawRect(f12, f10, f15, f13, this.paint);
        float height = f3 + (((float) (getHeight() - getBottomIndent())) * f);
        float f16 = height + f14;
        Canvas canvas4 = canvas;
        float f17 = height;
        canvas4.drawRect(f4, f17, f7, f16, this.paint);
        float f18 = height + 1.0f;
        canvas4.drawRect(f9, f17, f7, f18, this.paint);
        Canvas canvas5 = canvas;
        float f19 = width;
        float f20 = height;
        canvas5.drawRect(f19, f20, f11, f16, this.paint);
        canvas5.drawRect(f19, f20, f15, f18, this.paint);
    }

    private void drawShape(Canvas canvas, int i, int i2, float f, boolean z) {
        List<LeafView> list = this.shapeViews;
        if (list != null && list.size() != 0) {
            if (z) {
                for (LeafView next : this.shapeViews) {
                    if (next instanceof ShapeView) {
                        ShapeView shapeView = (ShapeView) next;
                        if (shapeView.isBehindDoc()) {
                            shapeView.drawForWrap(canvas, i, i2, f);
                        }
                    }
                    if (next instanceof ObjView) {
                        ObjView objView = (ObjView) next;
                        if (objView.isBehindDoc()) {
                            objView.drawForWrap(canvas, i, i2, f);
                        }
                    }
                }
                return;
            }
            for (LeafView next2 : this.shapeViews) {
                if (next2 instanceof ShapeView) {
                    ShapeView shapeView2 = (ShapeView) next2;
                    if (!shapeView2.isBehindDoc()) {
                        shapeView2.drawForWrap(canvas, i, i2, f);
                    }
                }
                if (next2 instanceof ObjView) {
                    ObjView objView2 = (ObjView) next2;
                    if (!objView2.isBehindDoc()) {
                        objView2.drawForWrap(canvas, i, i2, f);
                    }
                }
            }
        }
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        IView view = getView(j, 5, z);
        if (view != null) {
            view.modelToView(j, rectangle, z);
        }
        rectangle.x += getX();
        rectangle.y += getY();
        return rectangle;
    }

    public IView getView(long j, int i, boolean z) {
        IView iView = this.child;
        while (iView != null && !iView.contains(j, z)) {
            iView = iView.getNextView();
        }
        return (iView == null || iView.getType() == i || iView.getType() == 9) ? iView : iView.getView(j, i, z);
    }

    public long viewToModel(int i, int i2, boolean z) {
        int x = i - getX();
        int y = i2 - getY();
        IView childView = getChildView();
        if (childView != null && y > childView.getY()) {
            while (childView != null && (y < childView.getY() || y >= childView.getY() + childView.getHeight())) {
                childView = childView.getNextView();
            }
        }
        if (childView == null) {
            childView = getChildView();
        }
        if (childView != null) {
            return childView.viewToModel(x, y, z);
        }
        return -1;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int i) {
        this.pageNumber = i;
    }

    public TitleView getHeader() {
        return this.header;
    }

    public void setHeader(TitleView titleView) {
        this.header = titleView;
    }

    public TitleView getFooter() {
        return this.footer;
    }

    public void setFooter(TitleView titleView) {
        this.footer = titleView;
    }

    public boolean isHasBreakTable() {
        return this.hasBreakTable;
    }

    public void setHasBreakTable(boolean z) {
        this.hasBreakTable = z;
    }

    public void setPageBackgroundColor(int i) {
        this.pageBRColor = i;
    }

    public void setPageBorder(int i) {
        this.pageBorder = i;
    }

    public void addShapeView(LeafView leafView) {
        if (this.shapeViews == null) {
            this.shapeViews = new ArrayList();
        }
        this.shapeViews.add(leafView);
    }

    public boolean checkUpdateHeaderFooterFieldText(int i) {
        return checkUpdateHeaderFooterFieldText(this.header, i) || checkUpdateHeaderFooterFieldText(this.footer, i);
    }

    private boolean checkUpdateHeaderFooterFieldText(TitleView titleView, int i) {
        boolean z = false;
        if (titleView != null) {
            for (IView childView = titleView.getChildView(); childView != null; childView = childView.getNextView()) {
                for (IView childView2 = childView.getChildView(); childView2 != null; childView2 = childView2.getNextView()) {
                    for (IView childView3 = childView2.getChildView(); childView3 != null; childView3 = childView3.getNextView()) {
                        if (childView3 instanceof LeafView) {
                            LeafView leafView = (LeafView) childView3;
                            if (leafView.hasUpdatedFieldText()) {
                                z = true;
                                leafView.setNumPages(i);
                            }
                        }
                    }
                }
            }
        }
        return z;
    }

    public void dispose() {
        super.dispose();
        TitleView titleView = this.header;
        if (titleView != null) {
            titleView.dispose();
            this.header = null;
        }
        TitleView titleView2 = this.footer;
        if (titleView2 != null) {
            titleView2.dispose();
            this.footer = null;
        }
        List<LeafView> list = this.shapeViews;
        if (list != null) {
            list.clear();
            this.shapeViews = null;
        }
        this.paint = null;
    }
}
