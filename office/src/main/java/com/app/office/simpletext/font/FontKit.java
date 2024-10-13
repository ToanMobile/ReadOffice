package com.app.office.simpletext.font;

import android.graphics.Paint;
import android.graphics.Typeface;
import com.app.office.common.PaintKit;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.model.table.SSTableCellStyle;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class FontKit {
    private static FontKit fontKit = new FontKit();
    private BreakIterator lineBreak = BreakIterator.getLineInstance();

    public static FontKit instance() {
        return fontKit;
    }

    public Paint getCellPaint(Cell cell, Workbook workbook, SSTableCellStyle sSTableCellStyle) {
        Paint paint = PaintKit.instance().getPaint();
        paint.setAntiAlias(true);
        Font font = workbook.getFont(cell.getCellStyle().getFontIndex());
        boolean isBold = font.isBold();
        boolean isItalic = font.isItalic();
        if (isBold && isItalic) {
            paint.setTextSkewX(-0.2f);
            paint.setFakeBoldText(true);
        } else if (isBold) {
            paint.setFakeBoldText(true);
        } else if (isItalic) {
            paint.setTextSkewX(-0.2f);
        }
        if (font.isStrikeline()) {
            paint.setStrikeThruText(true);
        }
        if (font.getUnderline() != 0) {
            paint.setUnderlineText(true);
        }
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, 0));
        paint.setTextSize((float) ((font.getFontSize() * 1.3333333730697632d) + 0.5d));
        int color = workbook.getColor(font.getColorIndex());
        if ((16777215 & color) == 0 && sSTableCellStyle != null) {
            color = sSTableCellStyle.getFontColor();
        }
        paint.setColor(color);
        return paint;
    }

    public synchronized int findBreakOffset(String str, int i) {
        this.lineBreak.setText(str);
        this.lineBreak.following(i);
        int previous = this.lineBreak.previous();
        if (previous != 0) {
            i = previous;
        }
        return i;
    }

    public List<String> breakText(String str, int i, Paint paint) {
        String[] split = str.split("\\n");
        ArrayList arrayList = new ArrayList();
        for (String wrapText : split) {
            for (String add : wrapText(wrapText, i, paint)) {
                arrayList.add(add);
            }
        }
        return arrayList;
    }

    public List<String> wrapText(String str, int i, Paint paint) {
        float f;
        String[] split = str.substring(0).split(" ");
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < split.length; i2++) {
            if (split[i2].length() == 0) {
                split[i2] = " ";
            }
        }
        int i3 = 0;
        while (i3 < split.length) {
            while (true) {
                f = (float) i;
                if (paint.measureText(split[i3]) <= f) {
                    break;
                }
                int length = split[i3].toCharArray().length;
                String substring = split[i3].substring(0, length);
                while (length > 0 && paint.measureText(substring) > f) {
                    length--;
                    substring = split[i3].substring(0, length);
                }
                arrayList.add(substring);
                split[i3] = split[i3].substring(length, split[i3].length());
            }
            String str2 = "";
            while (i3 < split.length) {
                if (paint.measureText(str2 + split[i3]) > f) {
                    break;
                }
                str2 = str2 + split[i3] + " ";
                i3++;
            }
            arrayList.add(str2.substring(0, str2.length() - 1));
        }
        disposeString(split);
        return arrayList;
    }

    private void disposeString(String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = null;
        }
    }
}
