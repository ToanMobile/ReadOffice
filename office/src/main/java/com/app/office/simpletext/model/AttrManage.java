package com.app.office.simpletext.model;

import com.onesignal.OneSignalRemoteParams;
import com.app.office.common.bulletnumber.ListData;
import com.app.office.common.bulletnumber.ListLevel;
import com.app.office.constant.wp.AttrIDConstant;
import com.app.office.simpletext.view.CharAttr;
import com.app.office.simpletext.view.PageAttr;
import com.app.office.simpletext.view.ParaAttr;
import com.app.office.simpletext.view.TableAttr;
import com.app.office.system.IControl;

public class AttrManage {
    public static AttrManage am = new AttrManage();

    public void dispose() {
    }

    public static AttrManage instance() {
        return am;
    }

    public boolean hasAttribute(IAttributeSet iAttributeSet, short s) {
        return iAttributeSet.getAttribute(s) != Integer.MIN_VALUE;
    }

    public int getFontStyleID(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(0);
        if (attribute == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setFontStyleID(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(0, i);
    }

    public int getFontSize(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        int attribute = iAttributeSet2.getAttribute(1);
        if (attribute == Integer.MIN_VALUE && (attribute = iAttributeSet.getAttribute(1)) == Integer.MIN_VALUE) {
            return 12;
        }
        return attribute;
    }

    public void setFontSize(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(1, i);
    }

    public int getFontName(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        int attribute = iAttributeSet2.getAttribute(2);
        if (attribute == Integer.MIN_VALUE && (attribute = iAttributeSet.getAttribute(2)) == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setFontName(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(2, i);
    }

    public int getFontScale(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        int attribute = iAttributeSet2.getAttribute(14);
        if (attribute == Integer.MIN_VALUE && (attribute = iAttributeSet.getAttribute(14)) == Integer.MIN_VALUE) {
            return 100;
        }
        return attribute;
    }

    public void setFontScale(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(14, i);
    }

    public int getFontColor(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        int attribute = iAttributeSet2.getAttribute(3);
        if (attribute == Integer.MIN_VALUE && (attribute = iAttributeSet.getAttribute(3)) == Integer.MIN_VALUE) {
            return -16777216;
        }
        return attribute;
    }

    public void setFontColor(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(3, i);
    }

    public boolean getFontBold(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        int attribute = iAttributeSet2.getAttribute(4);
        if ((attribute != Integer.MIN_VALUE || (attribute = iAttributeSet.getAttribute(4)) != Integer.MIN_VALUE) && attribute == 1) {
            return true;
        }
        return false;
    }

    public void setFontBold(IAttributeSet iAttributeSet, boolean z) {
        iAttributeSet.setAttribute(4, z ? 1 : 0);
    }

    public boolean getFontItalic(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        int attribute = iAttributeSet2.getAttribute(5);
        if ((attribute != Integer.MIN_VALUE || (attribute = iAttributeSet.getAttribute(5)) != Integer.MIN_VALUE) && attribute == 1) {
            return true;
        }
        return false;
    }

    public void setFontItalic(IAttributeSet iAttributeSet, boolean z) {
        iAttributeSet.setAttribute(5, z ? 1 : 0);
    }

    public boolean getFontStrike(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        int attribute = iAttributeSet2.getAttribute(6);
        if ((attribute != Integer.MIN_VALUE || (attribute = iAttributeSet.getAttribute(6)) != Integer.MIN_VALUE) && attribute == 1) {
            return true;
        }
        return false;
    }

    public void setFontStrike(IAttributeSet iAttributeSet, boolean z) {
        iAttributeSet.setAttribute(6, z ? 1 : 0);
    }

    public boolean getFontDoubleStrike(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        int attribute = iAttributeSet2.getAttribute(7);
        if ((attribute != Integer.MIN_VALUE || (attribute = iAttributeSet.getAttribute(7)) != Integer.MIN_VALUE) && attribute == 1) {
            return true;
        }
        return false;
    }

    public void setFontDoubleStrike(IAttributeSet iAttributeSet, boolean z) {
        iAttributeSet.setAttribute(7, z ? 1 : 0);
    }

    public int getFontUnderline(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        int attribute = iAttributeSet2.getAttribute(8);
        if (attribute == Integer.MIN_VALUE && (attribute = iAttributeSet.getAttribute(8)) == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setFontUnderline(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(8, i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
        r1 = r4.getAttribute(9);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getFontUnderlineColor(com.app.office.simpletext.model.IAttributeSet r4, com.app.office.simpletext.model.IAttributeSet r5) {
        /*
            r3 = this;
            r0 = 9
            int r1 = r5.getAttribute(r0)
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r1 != r2) goto L_0x0015
            int r1 = r4.getAttribute(r0)
            if (r1 != r2) goto L_0x0015
            int r4 = r3.getFontColor(r4, r5)
            return r4
        L_0x0015:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.simpletext.model.AttrManage.getFontUnderlineColor(com.app.office.simpletext.model.IAttributeSet, com.app.office.simpletext.model.IAttributeSet):int");
    }

    public void setFontUnderlineColr(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(9, i);
    }

    public int getFontScript(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        int attribute = iAttributeSet2.getAttribute(10);
        if (attribute == Integer.MIN_VALUE && (attribute = iAttributeSet.getAttribute(10)) == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setFontScript(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(10, i);
    }

    public int getFontHighLight(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        int attribute = iAttributeSet2.getAttribute(11);
        if (attribute == Integer.MIN_VALUE && (attribute = iAttributeSet.getAttribute(11)) == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setFontHighLight(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(11, i);
    }

    public int getHperlinkID(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(12);
        if (attribute == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setHyperlinkID(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(12, i);
    }

    public int getShapeID(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(13);
        if (attribute == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setShapeID(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(13, i);
    }

    public int getFontPageNumberType(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(15);
        if (attribute == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setFontPageNumberType(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(15, i);
    }

    public int getFontEncloseChanacterType(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        int attribute = iAttributeSet2.getAttribute(16);
        if (attribute == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setEncloseChanacterType(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(16, i);
    }

    public int getParaStyleID(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PARA_STYLE_ID);
        if (attribute == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setParaStyleID(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PARA_STYLE_ID, i);
    }

    public int getParaIndentLeft(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(4097);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setParaIndentLeft(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(4097, i);
    }

    public int getParaIndentInitLeft(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(4098);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setParaIndentInitLeft(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(4098, i);
    }

    public int getParaIndentRight(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(4099);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setParaIndentRight(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(4099, i);
    }

    public int getParaBefore(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PARA_BEFORE_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setParaBefore(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PARA_BEFORE_ID, i);
    }

    public int getParaAfter(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PARA_AFTER_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setParaAfter(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PARA_AFTER_ID, i);
    }

    public int getParaSpecialIndent(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PARA_SPECIALINDENT_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setParaSpecialIndent(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PARA_SPECIALINDENT_ID, i);
    }

    public float getParaLineSpace(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PARA_LINESPACE_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 1.0f;
        }
        return ((float) attribute) / 100.0f;
    }

    public void setParaLineSpace(IAttributeSet iAttributeSet, float f) {
        iAttributeSet.setAttribute(AttrIDConstant.PARA_LINESPACE_ID, (int) (f * 100.0f));
    }

    public int getParaLineSpaceType(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(4106);
        if (attribute == Integer.MIN_VALUE) {
            return 1;
        }
        return attribute;
    }

    public void setParaLineSpaceType(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(4106, i);
    }

    public int getParaHorizontalAlign(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(4102);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setParaHorizontalAlign(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(4102, i);
    }

    public int getParaVerticalAlign(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(4103);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setParaVerticalAlign(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(4103, i);
    }

    public int getParaLevel(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PARA_LEVEL_ID);
        if (attribute == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setParaLevel(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PARA_LEVEL_ID, i);
    }

    public int getParaListLevel(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(4108);
        if (attribute == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setParaListLevel(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(4108, i);
    }

    public int getParaListID(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(4109);
        if (attribute == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setParaListID(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(4109, i);
    }

    public int getPGParaBulletID(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PARA_PG_BULLET_ID);
        if (attribute == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setPGParaBulletID(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PARA_PG_BULLET_ID, i);
    }

    public int getParaTabsClearPostion(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PARA_TABS_CLEAR_POSITION_ID);
        if (attribute == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setParaTabsClearPostion(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PARA_TABS_CLEAR_POSITION_ID, i);
    }

    public int getPageWidth(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PAGE_WIDTH_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 1000;
        }
        return attribute;
    }

    public void setPageWidth(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PAGE_WIDTH_ID, i);
    }

    public int getPageHeight(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PAGE_HEIGHT_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 1200;
        }
        return attribute;
    }

    public void setPageHeight(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PAGE_HEIGHT_ID, i);
    }

    public void setPageVerticalAlign(IAttributeSet iAttributeSet, byte b) {
        iAttributeSet.setAttribute(AttrIDConstant.PAGE_VERTICAL_ID, b);
    }

    public byte getPageVerticalAlign(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PAGE_VERTICAL_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return (byte) attribute;
    }

    public void setPageHorizontalAlign(IAttributeSet iAttributeSet, byte b) {
        iAttributeSet.setAttribute(AttrIDConstant.PAGE_HORIZONTAL_ID, b);
    }

    public byte getPageHorizontalAlign(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PAGE_HORIZONTAL_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return (byte) attribute;
    }

    public int getPageMarginLeft(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PAGE_LEFT_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 1800;
        }
        return attribute;
    }

    public void setPageMarginLeft(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PAGE_LEFT_ID, i);
    }

    public int getPageMarginRight(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PAGE_RIGHT_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 1800;
        }
        return attribute;
    }

    public void setPageMarginRight(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PAGE_RIGHT_ID, i);
    }

    public int getPageMarginTop(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PAGE_TOP_ID);
        return attribute == Integer.MIN_VALUE ? OneSignalRemoteParams.DEFAULT_INDIRECT_ATTRIBUTION_WINDOW : attribute;
    }

    public void setPageMarginTop(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PAGE_TOP_ID, i);
    }

    public int getPageMarginBottom(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PAGE_BOTTOM_ID);
        return attribute == Integer.MIN_VALUE ? OneSignalRemoteParams.DEFAULT_INDIRECT_ATTRIBUTION_WINDOW : attribute;
    }

    public void setPageMarginBottom(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PAGE_BOTTOM_ID, i);
    }

    public int getPageHeaderMargin(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PAGE_HEADER_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 850;
        }
        return attribute;
    }

    public void setPageHeaderMargin(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PAGE_HEADER_ID, i);
    }

    public int getPageFooterMargin(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PAGE_FOOTER_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 850;
        }
        return attribute;
    }

    public void setPageFooterMargin(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PAGE_FOOTER_ID, i);
    }

    public int getPageBackgroundColor(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PAGE_BACKGROUND_COLOR_ID);
        if (attribute == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setPageBackgroundColor(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PAGE_BACKGROUND_COLOR_ID, i);
    }

    public int getPageBorder(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PAGE_BORDER_ID);
        if (attribute == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setPageBorder(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PAGE_BORDER_ID, i);
    }

    public int getPageLinePitch(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PAGE_LINEPITCH_ID);
        if (attribute == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setPageLinePitch(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PAGE_LINEPITCH_ID, i);
    }

    public int getTableTopBorder(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_TOP_BORDER_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setTableTopBorder(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_TOP_BORDER_ID, i);
    }

    public int getTableTopBorderColor(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_TOP_BORDER_COLOR_ID);
        if (attribute == Integer.MIN_VALUE) {
            return -16777216;
        }
        return attribute;
    }

    public void setTableTopBorderColor(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_TOP_BORDER_COLOR_ID, i);
    }

    public int getTableLeftBorder(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_LEFT_BORDER_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setTableLeftBorder(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_LEFT_BORDER_ID, i);
    }

    public int getTableLeftBorderColor(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_LEFT_BORDER_COLOR_ID);
        if (attribute == Integer.MIN_VALUE) {
            return -16777216;
        }
        return attribute;
    }

    public void setTableLeftBorderColor(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_LEFT_BORDER_COLOR_ID, i);
    }

    public int getTableBottomBorder(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_BOTTOM_BORDER_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setTableBottomBorder(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_BOTTOM_BORDER_ID, i);
    }

    public int getTableBottomBorderColor(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_BOTTOM_BORDER_COLOR_ID);
        if (attribute == Integer.MIN_VALUE) {
            return -16777216;
        }
        return attribute;
    }

    public void setTableBottomBorderColor(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_BOTTOM_BORDER_COLOR_ID, i);
    }

    public int getTableRightBorder(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_RIGHT_BORDER_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setTableRightBorder(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_RIGHT_BORDER_ID, i);
    }

    public int getTableRightBorderColor(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_RIGHT_BORDER_COLOR_ID);
        if (attribute == Integer.MIN_VALUE) {
            return -16777216;
        }
        return attribute;
    }

    public void setTableRightBorderColor(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_RIGHT_BORDER_COLOR_ID, i);
    }

    public int getTableRowHeight(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_ROW_HEIGHT_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setTableRowHeight(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_ROW_HEIGHT_ID, i);
    }

    public int getTableCellWidth(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_CELL_WIDTH_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setTableCellWidth(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_CELL_WIDTH_ID, i);
    }

    public boolean isTableRowSplit(IAttributeSet iAttributeSet, int i) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_ROW_SPLIT_ID);
        return attribute == Integer.MIN_VALUE || attribute == 1;
    }

    public void setTableRowSplit(IAttributeSet iAttributeSet, boolean z) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_ROW_SPLIT_ID, z ? 1 : 0);
    }

    public boolean isTableHeaderRow(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_ROW_HEADER_ID);
        return attribute == Integer.MIN_VALUE || attribute == 1;
    }

    public void setTableHeaderRow(IAttributeSet iAttributeSet, boolean z) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_ROW_HEADER_ID, z ? 1 : 0);
    }

    public boolean isTableHorFirstMerged(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_CELL_HOR_FIRST_MERGED_ID);
        return attribute != Integer.MIN_VALUE && attribute == 1;
    }

    public void setTableHorFirstMerged(IAttributeSet iAttributeSet, boolean z) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_CELL_HOR_FIRST_MERGED_ID, z ? 1 : 0);
    }

    public boolean isTableHorMerged(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_CELL_HORIZONTAL_MERGED_ID);
        return attribute != Integer.MIN_VALUE && attribute == 1;
    }

    public void setTableHorMerged(IAttributeSet iAttributeSet, boolean z) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_CELL_HORIZONTAL_MERGED_ID, z ? 1 : 0);
    }

    public boolean isTableVerFirstMerged(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_CELL_VER_FIRST_MERGED_ID);
        return attribute != Integer.MIN_VALUE && attribute == 1;
    }

    public void setTableVerFirstMerged(IAttributeSet iAttributeSet, boolean z) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_CELL_VER_FIRST_MERGED_ID, z ? 1 : 0);
    }

    public boolean isTableVerMerged(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_CELL_VERTICAL_MERGED_ID);
        return attribute != Integer.MIN_VALUE && attribute == 1;
    }

    public void setTableVerMerged(IAttributeSet iAttributeSet, boolean z) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_CELL_VERTICAL_MERGED_ID, z ? 1 : 0);
    }

    public int getTableCellVerAlign(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_CELL_VERTICAL_ALIGN_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setTableCellVerAlign(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_CELL_VERTICAL_ALIGN_ID, i);
    }

    public int getTableTopMargin(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_TOP_MARGIN_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setTableTopMargin(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_TOP_MARGIN_ID, i);
    }

    public int getTableBottomMargin(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_BOTTOM_MARGIN_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setTableBottomMargin(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_BOTTOM_MARGIN_ID, i);
    }

    public int getTableLeftMargin(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_LEFT_MARGIN_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setTableLeftMargin(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_LEFT_MARGIN_ID, i);
    }

    public int getTableRightMargin(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.TABLE_RIGHT_MARGIN_ID);
        if (attribute == Integer.MIN_VALUE) {
            return 0;
        }
        return attribute;
    }

    public void setTableRightMargin(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.TABLE_RIGHT_MARGIN_ID, i);
    }

    public int getTableCellTableBackground(IAttributeSet iAttributeSet) {
        int attribute = iAttributeSet.getAttribute(AttrIDConstant.PAGE_BACKGROUND_COLOR_ID);
        if (attribute == Integer.MIN_VALUE) {
            return -1;
        }
        return attribute;
    }

    public void setTableCellBackground(IAttributeSet iAttributeSet, int i) {
        iAttributeSet.setAttribute(AttrIDConstant.PAGE_BACKGROUND_COLOR_ID, i);
    }

    public void fillPageAttr(PageAttr pageAttr, IAttributeSet iAttributeSet) {
        pageAttr.reset();
        pageAttr.verticalAlign = getPageVerticalAlign(iAttributeSet);
        pageAttr.horizontalAlign = getPageHorizontalAlign(iAttributeSet);
        pageAttr.pageWidth = (int) (((float) getPageWidth(iAttributeSet)) * 0.06666667f);
        pageAttr.pageHeight = (int) (((float) getPageHeight(iAttributeSet)) * 0.06666667f);
        pageAttr.topMargin = (int) (((float) getPageMarginTop(iAttributeSet)) * 0.06666667f);
        pageAttr.bottomMargin = (int) (((float) getPageMarginBottom(iAttributeSet)) * 0.06666667f);
        pageAttr.rightMargin = (int) (((float) getPageMarginRight(iAttributeSet)) * 0.06666667f);
        pageAttr.leftMargin = (int) (((float) getPageMarginLeft(iAttributeSet)) * 0.06666667f);
        pageAttr.headerMargin = (int) (((float) getPageHeaderMargin(iAttributeSet)) * 0.06666667f);
        pageAttr.footerMargin = (int) (((float) getPageFooterMargin(iAttributeSet)) * 0.06666667f);
        pageAttr.pageBRColor = getPageBackgroundColor(iAttributeSet);
        pageAttr.pageBorder = getPageBorder(iAttributeSet);
        pageAttr.pageLinePitch = ((float) getPageLinePitch(iAttributeSet)) * 0.06666667f;
    }

    public void fillParaAttr(IControl iControl, ParaAttr paraAttr, IAttributeSet iAttributeSet) {
        ListData listData;
        ListLevel level;
        paraAttr.reset();
        paraAttr.tabClearPosition = (int) (((float) getParaTabsClearPostion(iAttributeSet)) * 0.06666667f);
        paraAttr.leftIndent = (int) (((float) getParaIndentLeft(iAttributeSet)) * 0.06666667f);
        paraAttr.rightIndent = (int) (((float) getParaIndentRight(iAttributeSet)) * 0.06666667f);
        paraAttr.beforeSpace = (int) (((float) getParaBefore(iAttributeSet)) * 0.06666667f);
        paraAttr.afterSpace = (int) (((float) getParaAfter(iAttributeSet)) * 0.06666667f);
        paraAttr.lineSpaceType = (byte) getParaLineSpaceType(iAttributeSet);
        paraAttr.lineSpaceValue = getParaLineSpace(iAttributeSet);
        if (paraAttr.lineSpaceType == 3 || paraAttr.lineSpaceType == 4) {
            paraAttr.lineSpaceValue *= 0.06666667f;
        }
        paraAttr.horizontalAlignment = (byte) getParaHorizontalAlign(iAttributeSet);
        paraAttr.specialIndentValue = (int) (((float) getParaSpecialIndent(iAttributeSet)) * 0.06666667f);
        paraAttr.listID = getParaListID(iAttributeSet);
        paraAttr.listLevel = (byte) getParaListLevel(iAttributeSet);
        paraAttr.pgBulletID = getPGParaBulletID(iAttributeSet);
        if (paraAttr.listID >= 0 && paraAttr.listLevel >= 0 && iControl != null && (listData = iControl.getSysKit().getListManage().getListData(Integer.valueOf(paraAttr.listID))) != null && (level = listData.getLevel(paraAttr.listLevel)) != null) {
            paraAttr.listTextIndent = (int) (((float) level.getTextIndent()) * 0.06666667f);
            paraAttr.listAlignIndent = paraAttr.listTextIndent + ((int) (((float) level.getSpecialIndent()) * 0.06666667f));
            if (paraAttr.leftIndent - paraAttr.listTextIndent == 0 || paraAttr.leftIndent == 0) {
                if (paraAttr.specialIndentValue == 0) {
                    paraAttr.specialIndentValue = -(paraAttr.listTextIndent - paraAttr.listAlignIndent);
                }
                if (paraAttr.specialIndentValue < 0) {
                    paraAttr.leftIndent = paraAttr.listAlignIndent;
                    paraAttr.listAlignIndent = 0;
                } else if (paraAttr.listAlignIndent > paraAttr.specialIndentValue) {
                    paraAttr.specialIndentValue += paraAttr.listAlignIndent;
                }
            } else {
                if (paraAttr.leftIndent + paraAttr.listAlignIndent == paraAttr.listTextIndent) {
                    paraAttr.leftIndent = paraAttr.listAlignIndent;
                }
                if (paraAttr.specialIndentValue >= 0) {
                    paraAttr.listAlignIndent = paraAttr.specialIndentValue;
                } else {
                    paraAttr.listAlignIndent = 0;
                }
                if (paraAttr.specialIndentValue == 0 && paraAttr.listTextIndent - paraAttr.leftIndent > 0) {
                    paraAttr.specialIndentValue -= paraAttr.listTextIndent - paraAttr.leftIndent;
                }
            }
        }
    }

    public void fillCharAttr(CharAttr charAttr, IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        charAttr.reset();
        charAttr.fontIndex = getFontName(iAttributeSet, iAttributeSet2);
        charAttr.fontSize = getFontSize(iAttributeSet, iAttributeSet2);
        charAttr.fontScale = getFontScale(iAttributeSet, iAttributeSet2);
        charAttr.fontColor = getFontColor(iAttributeSet, iAttributeSet2);
        charAttr.isBold = getFontBold(iAttributeSet, iAttributeSet2);
        charAttr.isItalic = getFontItalic(iAttributeSet, iAttributeSet2);
        charAttr.isStrikeThrough = getFontStrike(iAttributeSet, iAttributeSet2);
        charAttr.isDoubleStrikeThrough = getFontDoubleStrike(iAttributeSet, iAttributeSet2);
        charAttr.underlineType = getFontUnderline(iAttributeSet, iAttributeSet2);
        charAttr.underlineColor = getFontUnderlineColor(iAttributeSet, iAttributeSet2);
        charAttr.subSuperScriptType = (short) getFontScript(iAttributeSet, iAttributeSet2);
        charAttr.highlightedColor = getFontHighLight(iAttributeSet, iAttributeSet2);
        charAttr.encloseType = (byte) getFontEncloseChanacterType(iAttributeSet, iAttributeSet2);
        charAttr.pageNumberType = (byte) getFontPageNumberType(iAttributeSet2);
    }

    public void fillTableAttr(TableAttr tableAttr, IAttributeSet iAttributeSet) {
        tableAttr.topMargin = 0;
        tableAttr.leftMargin = 7;
        tableAttr.rightMargin = 7;
        tableAttr.bottomMargin = 0;
        tableAttr.cellWidth = (int) (((float) getTableCellWidth(iAttributeSet)) * 0.06666667f);
        tableAttr.cellVerticalAlign = (byte) getTableCellVerAlign(iAttributeSet);
        tableAttr.cellBackground = getTableCellTableBackground(iAttributeSet);
    }
}
