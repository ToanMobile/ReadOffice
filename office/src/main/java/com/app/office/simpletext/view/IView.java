package com.app.office.simpletext.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.control.IWord;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.IElement;
import com.app.office.system.IControl;

public interface IView {
    void appendChlidView(IView iView);

    boolean contains(int i, int i2, boolean z);

    boolean contains(long j, boolean z);

    void deleteView(IView iView, boolean z);

    void dispose();

    int doLayout(int i, int i2, int i3, int i4, long j, int i5);

    void draw(Canvas canvas, int i, int i2, float f);

    void free();

    int getBottomIndent();

    int getChildCount();

    IView getChildView();

    IWord getContainer();

    IControl getControl();

    IDocument getDocument();

    long getElemEnd(IDocument iDocument);

    long getElemStart(IDocument iDocument);

    IElement getElement();

    long getEndOffset(IDocument iDocument);

    int getHeight();

    IView getLastView();

    int getLayoutSpan(byte b);

    int getLeftIndent();

    long getNextForCoordinate(long j, int i, int i2, int i3);

    long getNextForOffset(long j, int i, int i2, int i3);

    IView getNextView();

    IView getParentView();

    IView getPreView();

    int getRightIndent();

    long getStartOffset(IDocument iDocument);

    int getTopIndent();

    short getType();

    IView getView(int i, int i2, int i3, boolean z);

    IView getView(long j, int i, boolean z);

    Rect getViewRect(int i, int i2, float f);

    int getWidth();

    int getX();

    int getY();

    void insertView(IView iView, IView iView2);

    boolean intersection(Rect rect, int i, int i2, float f);

    Rectangle modelToView(long j, Rectangle rectangle, boolean z);

    void setBottomIndent(int i);

    void setBound(int i, int i2, int i3, int i4);

    void setChildView(IView iView);

    void setElement(IElement iElement);

    void setEndOffset(long j);

    void setHeight(int i);

    void setIndent(int i, int i2, int i3, int i4);

    void setLeftIndent(int i);

    void setLocation(int i, int i2);

    void setNextView(IView iView);

    void setParentView(IView iView);

    void setPreView(IView iView);

    void setRightIndent(int i);

    void setSize(int i, int i2);

    void setStartOffset(long j);

    void setTopIndent(int i);

    void setWidth(int i);

    void setX(int i);

    void setY(int i);

    long viewToModel(int i, int i2, boolean z);
}
