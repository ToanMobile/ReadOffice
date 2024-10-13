package com.app.office.fc.ppt.reader;

import com.app.office.fc.dom4j.Element;
import com.app.office.fc.ppt.attribute.ParaAttr;
import com.app.office.fc.ppt.attribute.RunAttr;
import com.app.office.fc.ppt.attribute.SectionAttr;
import com.app.office.fc.ppt.bulletnumber.BulletNumberManage;
import com.app.office.pg.model.PGMaster;
import com.app.office.pg.model.PGStyle;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.AttributeSetImpl;
import com.app.office.simpletext.model.IAttributeSet;
import com.app.office.simpletext.model.Style;
import com.app.office.simpletext.model.StyleManage;
import com.app.office.system.IControl;

public class StyleReader {
    private static StyleReader style = new StyleReader();
    private int index;

    public static StyleReader instance() {
        return style;
    }

    public PGStyle getStyles(IControl iControl, PGMaster pGMaster, Element element, Element element2) {
        PGStyle pGStyle = new PGStyle();
        processSp(pGStyle, element);
        processStyle(iControl, pGStyle, pGMaster, element2);
        return pGStyle;
    }

    private void processSp(PGStyle pGStyle, Element element) {
        Element element2;
        if (element != null) {
            Element element3 = element.element("spPr");
            if (element3 != null) {
                pGStyle.setAnchor(ReaderKit.instance().getShapeAnchor(element3.element("xfrm"), 1.0f, 1.0f));
            }
            Element element4 = element.element("txBody");
            if (element4 != null && (element2 = element4.element("bodyPr")) != null) {
                AttributeSetImpl attributeSetImpl = new AttributeSetImpl();
                SectionAttr.instance().setSectionAttribute(element2, attributeSetImpl, (IAttributeSet) null, (IAttributeSet) null, false);
                pGStyle.setSectionAttr(attributeSetImpl);
            }
        }
    }

    private void processStyle(IControl iControl, PGStyle pGStyle, PGMaster pGMaster, Element element) {
        if (element != null) {
            Element element2 = element.element("lvl1pPr");
            if (element2 != null) {
                processStyleAttribute(iControl, pGStyle, pGMaster, element2, 1);
            }
            Element element3 = element.element("lvl2pPr");
            if (element3 != null) {
                processStyleAttribute(iControl, pGStyle, pGMaster, element3, 2);
            }
            Element element4 = element.element("lvl3pPr");
            if (element4 != null) {
                processStyleAttribute(iControl, pGStyle, pGMaster, element4, 3);
            }
            Element element5 = element.element("lvl4pPr");
            if (element5 != null) {
                processStyleAttribute(iControl, pGStyle, pGMaster, element5, 4);
            }
            Element element6 = element.element("lvl5pPr");
            if (element6 != null) {
                processStyleAttribute(iControl, pGStyle, pGMaster, element6, 5);
            }
            Element element7 = element.element("lvl6pPr");
            if (element7 != null) {
                processStyleAttribute(iControl, pGStyle, pGMaster, element7, 6);
            }
            Element element8 = element.element("lvl7pPr");
            if (element8 != null) {
                processStyleAttribute(iControl, pGStyle, pGMaster, element8, 7);
            }
            Element element9 = element.element("lvl8pPr");
            if (element9 != null) {
                processStyleAttribute(iControl, pGStyle, pGMaster, element9, 8);
            }
            Element element10 = element.element("lvl9pPr");
            if (element10 != null) {
                processStyleAttribute(iControl, pGStyle, pGMaster, element10, 9);
            }
        }
    }

    private void processStyleAttribute(IControl iControl, PGStyle pGStyle, PGMaster pGMaster, Element element, int i) {
        Element element2 = element;
        Style style2 = new Style();
        style2.setId(this.index);
        style2.setType((byte) 0);
        ParaAttr.instance().setParaAttribute(iControl, element, style2.getAttrbuteSet(), (IAttributeSet) null, -1, -1, 0, false, false);
        RunAttr.instance().setRunAttribute(pGMaster, element2.element("defRPr"), style2.getAttrbuteSet(), (IAttributeSet) null, 100, -1, false);
        RunAttr.instance().setMaxFontSize(AttrManage.instance().getFontSize(style2.getAttrbuteSet(), style2.getAttrbuteSet()));
        ParaAttr.instance().processParaWithPct(element2, style2.getAttrbuteSet());
        RunAttr.instance().resetMaxFontSize();
        StyleManage.instance().addStyle(style2);
        PGStyle pGStyle2 = pGStyle;
        pGStyle.addStyle(i, this.index);
        IControl iControl2 = iControl;
        BulletNumberManage.instance().addBulletNumber(iControl, this.index, element2);
        this.index++;
    }

    public int getStyleIndex() {
        return this.index;
    }

    public void setStyleIndex(int i) {
        this.index = i;
    }
}
