package com.app.office.simpletext.control;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.view.IView;
import com.app.office.wp.view.WPViewKit;

public class Highlight implements IHighlight {
    private boolean isPaintHighlight = true;
    private Paint paint;
    private long selectEnd = 0;
    private long selectStart = 0;
    private IWord word;

    public Highlight(IWord iWord) {
        this.word = iWord;
        Paint paint2 = new Paint(1);
        this.paint = paint2;
        paint2.setColor(-1598300673);
    }

    public void draw(Canvas canvas, IView iView, int i, int i2, long j, long j2, float f) {
        long max;
        IView view;
        int i3;
        IView iView2 = iView;
        long j3 = j;
        long j4 = j2;
        if (isSelectText()) {
            long j5 = this.selectStart;
            if (j4 > j5 && j3 <= this.selectEnd && this.isPaintHighlight && (view = iView2.getView(max, 7, false)) != null) {
                Rectangle rectangle = new Rectangle();
                this.word.modelToView((max = Math.max(j3, j5)), rectangle, false);
                long endOffset = view.getEndOffset((IDocument) null);
                long min = Math.min(j4, this.selectEnd);
                int i4 = rectangle.x;
                int width = view.getWidth();
                long j6 = endOffset;
                if (max == this.selectStart) {
                    Rectangle absoluteCoordinate = WPViewKit.instance().getAbsoluteCoordinate(view, 0, new Rectangle());
                    if (this.word.getEditType() == 2 && this.word.getTextBox() != null) {
                        absoluteCoordinate.x += this.word.getTextBox().getBounds().x;
                        absoluteCoordinate.y += this.word.getTextBox().getBounds().y;
                    }
                    width -= rectangle.x - absoluteCoordinate.x;
                }
                int layoutSpan = iView2.getLayoutSpan((byte) 1);
                IView parentView = iView.getParentView();
                if (parentView != null) {
                    if (iView.getPreView() == null) {
                        i3 = (int) (((float) i2) - (((float) parentView.getTopIndent()) * f));
                        layoutSpan += parentView.getTopIndent();
                    } else {
                        i3 = i2;
                    }
                    if (iView.getNextView() == null) {
                        layoutSpan += parentView.getBottomIndent();
                    }
                } else {
                    i3 = i2;
                }
                long j7 = j6;
                while (j7 <= min) {
                    float f2 = ((float) i4) * f;
                    float f3 = (float) i3;
                    i4 += width;
                    canvas.drawRect(f2, f3, ((float) i4) * f, f3 + (((float) layoutSpan) * f), this.paint);
                    view = view.getNextView();
                    if (view == null) {
                        break;
                    }
                    width = view.getWidth();
                    j7 = view.getEndOffset((IDocument) null);
                }
                if (j4 >= this.selectEnd) {
                    Rectangle rectangle2 = new Rectangle();
                    this.word.modelToView(this.selectEnd, rectangle2, false);
                    if (rectangle2.x > i4) {
                        float f4 = (float) i3;
                        canvas.drawRect(((float) i4) * f, f4, ((float) rectangle2.x) * f, (((float) layoutSpan) * f) + f4, this.paint);
                    }
                }
            }
        }
    }

    public String getSelectText() {
        return isSelectText() ? this.word.getDocument().getText(this.selectStart, this.selectEnd) : "";
    }

    public boolean isSelectText() {
        return this.selectStart != this.selectEnd;
    }

    public void removeHighlight() {
        this.selectStart = 0;
        this.selectEnd = 0;
    }

    public void addHighlight(long j, long j2) {
        this.selectStart = j;
        this.selectEnd = j2;
    }

    public long getSelectStart() {
        return this.selectStart;
    }

    public void setSelectStart(long j) {
        this.selectStart = j;
    }

    public long getSelectEnd() {
        return this.selectEnd;
    }

    public void setSelectEnd(long j) {
        this.selectEnd = j;
    }

    public void setPaintHighlight(boolean z) {
        this.isPaintHighlight = z;
    }

    public void dispose() {
        this.word = null;
        this.paint = null;
    }
}
