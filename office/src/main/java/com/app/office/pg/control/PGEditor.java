package com.app.office.pg.control;

import com.app.office.common.shape.IShape;
import com.app.office.common.shape.TextBox;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.animate.IAnimation;
import com.app.office.simpletext.control.Highlight;
import com.app.office.simpletext.control.IHighlight;
import com.app.office.simpletext.control.IWord;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.simpletext.view.STRoot;
import com.app.office.system.IControl;
import java.util.Map;

public class PGEditor implements IWord {
    private TextBox editorTextBox;
    private IHighlight highlight = new Highlight(this);
    private Map<Integer, IAnimation> paraAnimation;
    private Presentation pgView;

    public IDocument getDocument() {
        return null;
    }

    public byte getEditType() {
        return 2;
    }

    public PGEditor(Presentation presentation) {
        this.pgView = presentation;
    }

    public IHighlight getHighlight() {
        return this.highlight;
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        TextBox textBox = this.editorTextBox;
        if (textBox != null) {
            STRoot rootView = textBox.getRootView();
            if (rootView != null) {
                rootView.modelToView(j, rectangle, z);
            }
            rectangle.x += this.editorTextBox.getBounds().x;
            rectangle.y += this.editorTextBox.getBounds().y;
        }
        return rectangle;
    }

    public String getText(long j, long j2) {
        String text;
        TextBox textBox = this.editorTextBox;
        if (textBox != null) {
            SectionElement element = textBox.getElement();
            if (element.getEndOffset() - element.getStartOffset() > 0 && (text = element.getText((IDocument) null)) != null) {
                return text.substring((int) Math.max(j, element.getStartOffset()), (int) Math.min(j2, element.getEndOffset()));
            }
        }
        return null;
    }

    public long viewToModel(int i, int i2, boolean z) {
        IShape shape;
        STRoot rootView;
        Presentation presentation = this.pgView;
        if (presentation == null || (shape = presentation.getCurrentSlide().getShape(i, i2)) == null || shape.getType() != 1 || (rootView = ((TextBox) shape).getRootView()) == null) {
            return -1;
        }
        return rootView.viewToModel(i - shape.getBounds().x, i2 - shape.getBounds().y, z);
    }

    public TextBox getEditorTextBox() {
        return this.editorTextBox;
    }

    public void setEditorTextBox(TextBox textBox) {
        this.editorTextBox = textBox;
    }

    public void setShapeAnimation(Map<Integer, IAnimation> map) {
        this.paraAnimation = map;
    }

    public IAnimation getParagraphAnimation(int i) {
        Map<Integer, IAnimation> map;
        if (this.pgView == null || (map = this.paraAnimation) == null) {
            return null;
        }
        IAnimation iAnimation = map.get(Integer.valueOf(i));
        if (iAnimation == null) {
            iAnimation = this.paraAnimation.get(-2);
        }
        return iAnimation == null ? this.paraAnimation.get(-1) : iAnimation;
    }

    public IShape getTextBox() {
        return this.editorTextBox;
    }

    public void clearAnimation() {
        Map<Integer, IAnimation> map = this.paraAnimation;
        if (map != null) {
            map.clear();
        }
    }

    public IControl getControl() {
        Presentation presentation = this.pgView;
        if (presentation != null) {
            return presentation.getControl();
        }
        return null;
    }

    public Presentation getPGView() {
        return this.pgView;
    }

    public void dispose() {
        this.editorTextBox = null;
        IHighlight iHighlight = this.highlight;
        if (iHighlight != null) {
            iHighlight.dispose();
            this.highlight = null;
        }
        this.pgView = null;
        Map<Integer, IAnimation> map = this.paraAnimation;
        if (map != null) {
            map.clear();
            this.paraAnimation = null;
        }
    }
}
