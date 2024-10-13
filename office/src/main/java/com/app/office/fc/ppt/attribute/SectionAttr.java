package com.app.office.fc.ppt.attribute;

import com.app.office.constant.wp.AttrIDConstant;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.AttributeSetImpl;
import com.app.office.simpletext.model.IAttributeSet;

public class SectionAttr {
    public static final int DEFAULT_MARGIN_LEFT_RIGHT = 144;
    public static final int DEFAULT_MARGIN_TOP_BOTTOM = 72;
    public static final int DEFAULT_TABLE_MARGIN = 30;
    private static SectionAttr kit = new SectionAttr();

    public static SectionAttr instance() {
        return kit;
    }

    public void setPageMarginLeft(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, AttrIDConstant.PAGE_LEFT_ID)) {
            AttrManage.instance().setPageMarginLeft(iAttributeSet2, AttrManage.instance().getPageMarginLeft(iAttributeSet));
        }
    }

    public void setPageMarginRight(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, AttrIDConstant.PAGE_RIGHT_ID)) {
            AttrManage.instance().setPageMarginRight(iAttributeSet2, AttrManage.instance().getPageMarginRight(iAttributeSet));
        }
    }

    public void setPageMarginTop(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, AttrIDConstant.PAGE_TOP_ID)) {
            AttrManage.instance().setPageMarginTop(iAttributeSet2, AttrManage.instance().getPageMarginTop(iAttributeSet));
        }
    }

    public void setPageMarginBottom(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, AttrIDConstant.PAGE_BOTTOM_ID)) {
            AttrManage.instance().setPageMarginBottom(iAttributeSet2, AttrManage.instance().getPageMarginBottom(iAttributeSet));
        }
    }

    public void setPageVerticalAlign(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet != null && AttrManage.instance().hasAttribute(iAttributeSet, AttrIDConstant.PAGE_VERTICAL_ID)) {
            AttrManage.instance().setPageVerticalAlign(iAttributeSet2, AttrManage.instance().getPageVerticalAlign(iAttributeSet));
        }
    }

    public IAttributeSet getDefautSectionAttr(IAttributeSet iAttributeSet, IAttributeSet iAttributeSet2) {
        if (iAttributeSet == null && iAttributeSet2 == null) {
            return null;
        }
        AttributeSetImpl attributeSetImpl = new AttributeSetImpl();
        if (iAttributeSet == null) {
            setPageMarginLeft(iAttributeSet2, attributeSetImpl);
            setPageMarginRight(iAttributeSet2, attributeSetImpl);
            setPageMarginTop(iAttributeSet2, attributeSetImpl);
            setPageMarginBottom(iAttributeSet2, attributeSetImpl);
            setPageVerticalAlign(iAttributeSet2, attributeSetImpl);
        } else if (iAttributeSet2 == null) {
            setPageMarginLeft(iAttributeSet, attributeSetImpl);
            setPageMarginRight(iAttributeSet, attributeSetImpl);
            setPageMarginTop(iAttributeSet, attributeSetImpl);
            setPageMarginBottom(iAttributeSet, attributeSetImpl);
            setPageVerticalAlign(iAttributeSet, attributeSetImpl);
        } else {
            if (AttrManage.instance().hasAttribute(iAttributeSet, AttrIDConstant.PAGE_LEFT_ID)) {
                setPageMarginLeft(iAttributeSet, attributeSetImpl);
            } else {
                setPageMarginLeft(iAttributeSet2, attributeSetImpl);
            }
            if (AttrManage.instance().hasAttribute(iAttributeSet, AttrIDConstant.PAGE_RIGHT_ID)) {
                setPageMarginRight(iAttributeSet, attributeSetImpl);
            } else {
                setPageMarginRight(iAttributeSet2, attributeSetImpl);
            }
            if (AttrManage.instance().hasAttribute(iAttributeSet, AttrIDConstant.PAGE_TOP_ID)) {
                setPageMarginTop(iAttributeSet, attributeSetImpl);
            } else {
                setPageMarginTop(iAttributeSet2, attributeSetImpl);
            }
            if (AttrManage.instance().hasAttribute(iAttributeSet, AttrIDConstant.PAGE_BOTTOM_ID)) {
                setPageMarginBottom(iAttributeSet, attributeSetImpl);
            } else {
                setPageMarginBottom(iAttributeSet2, attributeSetImpl);
            }
            if (AttrManage.instance().hasAttribute(iAttributeSet, AttrIDConstant.PAGE_VERTICAL_ID)) {
                setPageVerticalAlign(iAttributeSet, attributeSetImpl);
            } else {
                setPageVerticalAlign(iAttributeSet2, attributeSetImpl);
            }
        }
        return attributeSetImpl;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00ec, code lost:
        if (r0.equals("dist") != false) goto L_0x00d1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setSectionAttribute(com.app.office.fc.dom4j.Element r6, com.app.office.simpletext.model.IAttributeSet r7, com.app.office.simpletext.model.IAttributeSet r8, com.app.office.simpletext.model.IAttributeSet r9, boolean r10) {
        /*
            r5 = this;
            com.app.office.simpletext.model.IAttributeSet r8 = r5.getDefautSectionAttr(r8, r9)
            r9 = 0
            if (r6 == 0) goto L_0x0130
            java.lang.String r0 = "lIns"
            com.app.office.fc.dom4j.Attribute r1 = r6.attribute((java.lang.String) r0)
            r2 = 1101004800(0x41a00000, float:20.0)
            r3 = 1230978560(0x495f3e00, float:914400.0)
            r4 = 1116733440(0x42900000, float:72.0)
            if (r1 == 0) goto L_0x0035
            java.lang.String r0 = r6.attributeValue((java.lang.String) r0)
            if (r0 == 0) goto L_0x0038
            int r1 = r0.length()
            if (r1 <= 0) goto L_0x0038
            int r0 = java.lang.Integer.parseInt(r0)
            float r0 = (float) r0
            float r0 = r0 * r4
            float r0 = r0 / r3
            float r0 = r0 * r2
            int r0 = (int) r0
            com.app.office.simpletext.model.AttrManage r1 = com.app.office.simpletext.model.AttrManage.instance()
            r1.setPageMarginLeft(r7, r0)
            goto L_0x0038
        L_0x0035:
            r5.setPageMarginLeft(r8, r7)
        L_0x0038:
            java.lang.String r0 = "rIns"
            com.app.office.fc.dom4j.Attribute r1 = r6.attribute((java.lang.String) r0)
            if (r1 == 0) goto L_0x005f
            java.lang.String r0 = r6.attributeValue((java.lang.String) r0)
            if (r0 == 0) goto L_0x0062
            int r1 = r0.length()
            if (r1 <= 0) goto L_0x0062
            int r0 = java.lang.Integer.parseInt(r0)
            float r0 = (float) r0
            float r0 = r0 * r4
            float r0 = r0 / r3
            float r0 = r0 * r2
            int r0 = (int) r0
            com.app.office.simpletext.model.AttrManage r1 = com.app.office.simpletext.model.AttrManage.instance()
            r1.setPageMarginRight(r7, r0)
            goto L_0x0062
        L_0x005f:
            r5.setPageMarginRight(r8, r7)
        L_0x0062:
            java.lang.String r0 = "tIns"
            com.app.office.fc.dom4j.Attribute r1 = r6.attribute((java.lang.String) r0)
            if (r1 == 0) goto L_0x0089
            java.lang.String r0 = r6.attributeValue((java.lang.String) r0)
            if (r0 == 0) goto L_0x008c
            int r1 = r0.length()
            if (r1 <= 0) goto L_0x008c
            int r0 = java.lang.Integer.parseInt(r0)
            float r0 = (float) r0
            float r0 = r0 * r4
            float r0 = r0 / r3
            float r0 = r0 * r2
            int r0 = (int) r0
            com.app.office.simpletext.model.AttrManage r1 = com.app.office.simpletext.model.AttrManage.instance()
            r1.setPageMarginTop(r7, r0)
            goto L_0x008c
        L_0x0089:
            r5.setPageMarginTop(r8, r7)
        L_0x008c:
            java.lang.String r0 = "bIns"
            com.app.office.fc.dom4j.Attribute r1 = r6.attribute((java.lang.String) r0)
            if (r1 == 0) goto L_0x00b3
            java.lang.String r0 = r6.attributeValue((java.lang.String) r0)
            if (r0 == 0) goto L_0x00b6
            int r1 = r0.length()
            if (r1 <= 0) goto L_0x00b6
            int r0 = java.lang.Integer.parseInt(r0)
            float r0 = (float) r0
            float r0 = r0 * r4
            float r0 = r0 / r3
            float r0 = r0 * r2
            int r0 = (int) r0
            com.app.office.simpletext.model.AttrManage r1 = com.app.office.simpletext.model.AttrManage.instance()
            r1.setPageMarginBottom(r7, r0)
            goto L_0x00b6
        L_0x00b3:
            r5.setPageMarginBottom(r8, r7)
        L_0x00b6:
            java.lang.String r0 = "anchor"
            java.lang.String r0 = r6.attributeValue((java.lang.String) r0)
            r1 = 1
            if (r0 == 0) goto L_0x00f7
            java.lang.String r2 = "t"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x00c9
        L_0x00c7:
            r0 = 0
            goto L_0x00ef
        L_0x00c9:
            java.lang.String r2 = "ctr"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x00d3
        L_0x00d1:
            r0 = 1
            goto L_0x00ef
        L_0x00d3:
            java.lang.String r2 = "b"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x00dd
            r0 = 2
            goto L_0x00ef
        L_0x00dd:
            java.lang.String r2 = "just"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x00e6
            goto L_0x00d1
        L_0x00e6:
            java.lang.String r2 = "dist"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x00c7
            goto L_0x00d1
        L_0x00ef:
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            r2.setPageVerticalAlign(r7, r0)
            goto L_0x00fa
        L_0x00f7:
            r5.setPageVerticalAlign(r8, r7)
        L_0x00fa:
            java.lang.String r0 = "anchorCtr"
            java.lang.String r6 = r6.attributeValue((java.lang.String) r0)
            if (r6 == 0) goto L_0x0112
            java.lang.String r8 = "1"
            boolean r6 = r6.equals(r8)
            if (r6 == 0) goto L_0x0141
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r6.setPageHorizontalAlign(r7, r1)
            goto L_0x0141
        L_0x0112:
            if (r8 == 0) goto L_0x0141
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r0 = 8201(0x2009, float:1.1492E-41)
            boolean r6 = r6.hasAttribute(r8, r0)
            if (r6 == 0) goto L_0x0141
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.AttrManage r0 = com.app.office.simpletext.model.AttrManage.instance()
            byte r8 = r0.getPageHorizontalAlign(r8)
            r6.setPageHorizontalAlign(r7, r8)
            goto L_0x0141
        L_0x0130:
            if (r8 == 0) goto L_0x0141
            r5.setPageMarginLeft(r8, r7)
            r5.setPageMarginRight(r8, r7)
            r5.setPageMarginTop(r8, r7)
            r5.setPageMarginBottom(r8, r7)
            r5.setPageVerticalAlign(r8, r7)
        L_0x0141:
            com.app.office.fc.ppt.attribute.RunAttr r6 = com.app.office.fc.ppt.attribute.RunAttr.instance()
            boolean r6 = r6.isSlide()
            if (r6 == 0) goto L_0x01c5
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r8 = 8194(0x2002, float:1.1482E-41)
            boolean r6 = r6.hasAttribute(r7, r8)
            r8 = 144(0x90, float:2.02E-43)
            r0 = 30
            if (r6 != 0) goto L_0x016c
            if (r10 == 0) goto L_0x0165
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r6.setPageMarginLeft(r7, r0)
            goto L_0x016c
        L_0x0165:
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r6.setPageMarginLeft(r7, r8)
        L_0x016c:
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r1 = 8195(0x2003, float:1.1484E-41)
            boolean r6 = r6.hasAttribute(r7, r1)
            if (r6 != 0) goto L_0x0189
            if (r10 == 0) goto L_0x0182
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r6.setPageMarginRight(r7, r0)
            goto L_0x0189
        L_0x0182:
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r6.setPageMarginRight(r7, r8)
        L_0x0189:
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r8 = 8196(0x2004, float:1.1485E-41)
            boolean r6 = r6.hasAttribute(r7, r8)
            r8 = 72
            if (r6 != 0) goto L_0x01a8
            if (r10 == 0) goto L_0x01a1
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r6.setPageMarginTop(r7, r0)
            goto L_0x01a8
        L_0x01a1:
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r6.setPageMarginTop(r7, r8)
        L_0x01a8:
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r0 = 8197(0x2005, float:1.1486E-41)
            boolean r6 = r6.hasAttribute(r7, r0)
            if (r6 != 0) goto L_0x01c5
            if (r10 == 0) goto L_0x01be
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r6.setPageMarginBottom(r7, r9)
            goto L_0x01c5
        L_0x01be:
            com.app.office.simpletext.model.AttrManage r6 = com.app.office.simpletext.model.AttrManage.instance()
            r6.setPageMarginBottom(r7, r8)
        L_0x01c5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ppt.attribute.SectionAttr.setSectionAttribute(com.app.office.fc.dom4j.Element, com.app.office.simpletext.model.IAttributeSet, com.app.office.simpletext.model.IAttributeSet, com.app.office.simpletext.model.IAttributeSet, boolean):void");
    }
}
