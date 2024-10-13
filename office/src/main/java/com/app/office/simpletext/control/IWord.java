package com.app.office.simpletext.control;

import com.app.office.common.shape.IShape;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.animate.IAnimation;
import com.app.office.simpletext.model.IDocument;
import com.app.office.system.IControl;

public interface IWord {
    void dispose();

    IControl getControl();

    IDocument getDocument();

    byte getEditType();

    IHighlight getHighlight();

    IAnimation getParagraphAnimation(int i);

    String getText(long j, long j2);

    IShape getTextBox();

    Rectangle modelToView(long j, Rectangle rectangle, boolean z);

    long viewToModel(int i, int i2, boolean z);
}
