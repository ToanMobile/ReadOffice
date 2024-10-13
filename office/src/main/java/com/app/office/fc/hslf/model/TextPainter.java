package com.app.office.fc.hslf.model;

import com.app.office.fc.hslf.usermodel.RichTextRun;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import kotlin.text.Typography;

public final class TextPainter {
    protected static final char DEFAULT_BULLET_CHAR = '■';
    protected TextShape _shape;
    protected POILogger logger = POILogFactory.getLogger((Class) getClass());

    public static class TextElement {
        public int _align;
        public AttributedString _bullet;
        public int _bulletOffset;
        public AttributedString _text;
        public int _textOffset;
        public float advance;
        public float ascent;
        public float descent;
        public int textEndIndex;
        public int textStartIndex;
    }

    public TextPainter(TextShape textShape) {
        this._shape = textShape;
    }

    public AttributedString getAttributedString(TextRun textRun) {
        AttributedString attributedString = new AttributedString(textRun.getText().replace(9, ' ').replace(Typography.nbsp, ' '));
        RichTextRun[] richTextRuns = textRun.getRichTextRuns();
        for (int i = 0; i < richTextRuns.length; i++) {
            int startIndex = richTextRuns[i].getStartIndex();
            int endIndex = richTextRuns[i].getEndIndex();
            if (startIndex == endIndex) {
                this.logger.log(POILogger.INFO, (Object) "Skipping RichTextRun with zero length");
            } else {
                attributedString.addAttribute(TextAttribute.FAMILY, richTextRuns[i].getFontName(), startIndex, endIndex);
                attributedString.addAttribute(TextAttribute.SIZE, new Float((float) richTextRuns[i].getFontSize()), startIndex, endIndex);
                attributedString.addAttribute(TextAttribute.FOREGROUND, richTextRuns[i].getFontColor(), startIndex, endIndex);
                if (richTextRuns[i].isBold()) {
                    attributedString.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD, startIndex, endIndex);
                }
                if (richTextRuns[i].isItalic()) {
                    attributedString.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE, startIndex, endIndex);
                }
                if (richTextRuns[i].isUnderlined()) {
                    attributedString.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, startIndex, endIndex);
                    attributedString.addAttribute(TextAttribute.INPUT_METHOD_UNDERLINE, TextAttribute.UNDERLINE_LOW_TWO_PIXEL, startIndex, endIndex);
                }
                if (richTextRuns[i].isStrikethrough()) {
                    attributedString.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON, startIndex, endIndex);
                }
                int superscript = richTextRuns[i].getSuperscript();
                if (superscript != 0) {
                    attributedString.addAttribute(TextAttribute.SUPERSCRIPT, superscript > 0 ? TextAttribute.SUPERSCRIPT_SUPER : TextAttribute.SUPERSCRIPT_SUB, startIndex, endIndex);
                }
            }
        }
        return attributedString;
    }
}
