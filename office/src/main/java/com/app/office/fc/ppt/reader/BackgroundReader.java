package com.app.office.fc.ppt.reader;

import com.itextpdf.text.html.HtmlTags;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.bg.TileShader;
import com.app.office.common.pictureefftect.PictureStretchInfo;
import com.app.office.fc.ShaderKit;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.PackageRelationship;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.pg.model.PGMaster;
import com.app.office.system.IControl;

public class BackgroundReader {
    private static BackgroundReader bgReader = new BackgroundReader();

    public static BackgroundReader instance() {
        return bgReader;
    }

    public BackgroundAndFill getBackground(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, PGMaster pGMaster, Element element) throws Exception {
        if (element == null) {
            return null;
        }
        Element element2 = element.element("bgPr");
        Element element3 = element.element("bgRef");
        if (element3 == null) {
            return processBackground(iControl, zipPackage, packagePart, pGMaster, element2);
        }
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setFillType((byte) 0);
        backgroundAndFill.setForegroundColor(ReaderKit.instance().getColor(pGMaster, element3));
        return backgroundAndFill;
    }

    public BackgroundAndFill processBackground(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, PGMaster pGMaster, Element element) throws Exception {
        return processBackground(iControl, zipPackage, packagePart, pGMaster, element, false);
    }

    public BackgroundAndFill processBackground(IControl iControl, ZipPackage zipPackage, PackagePart packagePart, PGMaster pGMaster, Element element, boolean z) throws Exception {
        String attributeValue;
        PackageRelationship relationship;
        PackagePart part;
        String attributeValue2;
        Element element2;
        if (element == null) {
            return null;
        }
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        Element element3 = element.element("solidFill");
        boolean z2 = false;
        if (element3 != null) {
            backgroundAndFill.setFillType((byte) 0);
            backgroundAndFill.setForegroundColor(ReaderKit.instance().getColor(pGMaster, element3, z));
            return backgroundAndFill;
        }
        Element element4 = element.element("blipFill");
        if (element4 != null) {
            Element element5 = element4.element("blip");
            if (element5 == null || element5.attribute("embed") == null || (attributeValue = element5.attributeValue("embed")) == null || (relationship = packagePart.getRelationship(attributeValue)) == null || (part = zipPackage.getPart(relationship.getTargetURI())) == null) {
                return null;
            }
            Element element6 = element4.element("tile");
            if (element6 == null) {
                backgroundAndFill.setFillType((byte) 3);
                Element element7 = element4.element("stretch");
                if (!(element7 == null || (element2 = element7.element("fillRect")) == null)) {
                    PictureStretchInfo pictureStretchInfo = new PictureStretchInfo();
                    String attributeValue3 = element2.attributeValue("l");
                    boolean z3 = true;
                    if (attributeValue3 != null) {
                        pictureStretchInfo.setLeftOffset(Float.parseFloat(attributeValue3) / 100000.0f);
                        z2 = true;
                    }
                    String attributeValue4 = element2.attributeValue("r");
                    if (attributeValue4 != null) {
                        pictureStretchInfo.setRightOffset(Float.parseFloat(attributeValue4) / 100000.0f);
                        z2 = true;
                    }
                    String attributeValue5 = element2.attributeValue("t");
                    if (attributeValue5 != null) {
                        pictureStretchInfo.setTopOffset(Float.parseFloat(attributeValue5) / 100000.0f);
                        z2 = true;
                    }
                    String attributeValue6 = element2.attributeValue(HtmlTags.B);
                    if (attributeValue6 != null) {
                        pictureStretchInfo.setBottomOffset(Float.parseFloat(attributeValue6) / 100000.0f);
                    } else {
                        z3 = z2;
                    }
                    if (z3) {
                        backgroundAndFill.setStretch(pictureStretchInfo);
                    }
                }
                backgroundAndFill.setPictureIndex(iControl.getSysKit().getPictureManage().addPicture(part));
            } else {
                int addPicture = iControl.getSysKit().getPictureManage().addPicture(part);
                backgroundAndFill.setFillType((byte) 2);
                TileShader readTile = ShaderKit.readTile(iControl.getSysKit().getPictureManage().getPicture(addPicture), element6);
                Element element8 = element5.element("alphaModFix");
                if (!(element8 == null || (attributeValue2 = element8.attributeValue("amt")) == null)) {
                    readTile.setAlpha(Math.round((((float) Integer.parseInt(attributeValue2)) / 100000.0f) * 255.0f));
                }
                backgroundAndFill.setShader(readTile);
            }
            return backgroundAndFill;
        }
        Element element9 = element.element("gradFill");
        if (element9 == null) {
            Element element10 = element.element("fillRef");
            if (element10 != null) {
                backgroundAndFill.setFillType((byte) 0);
                backgroundAndFill.setForegroundColor(ReaderKit.instance().getColor(pGMaster, element10));
                return backgroundAndFill;
            }
            Element element11 = element.element("pattFill");
            if (element11 == null) {
                return null;
            }
            Element element12 = element11.element("bgClr");
            backgroundAndFill.setFillType((byte) 0);
            backgroundAndFill.setForegroundColor(ReaderKit.instance().getColor(pGMaster, element12));
            return backgroundAndFill;
        } else if (element9.element("gsLst") == null) {
            return null;
        } else {
            backgroundAndFill.setFillType(ShaderKit.getGradientType(element9));
            backgroundAndFill.setShader(ShaderKit.readGradient(pGMaster, element9));
            return backgroundAndFill;
        }
    }

    public int getBackgroundColor(ZipPackage zipPackage, PackagePart packagePart, PGMaster pGMaster, Element element, boolean z) throws Exception {
        if (element == null) {
            return 0;
        }
        Element element2 = element.element("solidFill");
        if (element2 != null) {
            return ReaderKit.instance().getColor(pGMaster, element2, z);
        }
        Element element3 = element.element("gradFill");
        if (element3 != null) {
            Element element4 = element3.element("gsLst");
            if (element4 != null) {
                return ReaderKit.instance().getColor(pGMaster, element4.element("gs"));
            }
            return 0;
        }
        Element element5 = element.element("fillRef");
        if (element5 != null) {
            return ReaderKit.instance().getColor(pGMaster, element5);
        }
        Element element6 = element.element("pattFill");
        if (element6 == null) {
            return 0;
        }
        return ReaderKit.instance().getColor(pGMaster, element6.element("bgClr"));
    }
}
