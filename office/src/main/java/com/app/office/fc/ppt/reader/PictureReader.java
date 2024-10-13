package com.app.office.fc.ppt.reader;

import com.onesignal.outcomes.OSOutcomeConstants;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.dom4j.io.SAXReader;
import com.app.office.fc.openxml4j.opc.PackagePart;
import com.app.office.fc.openxml4j.opc.PackageRelationship;
import com.app.office.fc.openxml4j.opc.PackageRelationshipTypes;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import com.app.office.ss.model.drawing.AnchorPoint;
import com.app.office.ss.model.drawing.CellAnchor;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class PictureReader {
    private static PictureReader picReader = new PictureReader();
    private Map<String, CellAnchor> spIDAnchors;
    private Map<String, String> spIDs;
    private PackagePart vmlDrawingPart;

    public static PictureReader instance() {
        return picReader;
    }

    public PackagePart getOLEPart(ZipPackage zipPackage, PackagePart packagePart, String str, Boolean bool) throws Exception {
        String str2;
        PackageRelationship relationship;
        if (this.vmlDrawingPart == null) {
            Iterator<PackageRelationship> it = packagePart.getRelationshipsByType(PackageRelationshipTypes.VMLDRAWING_PART).iterator();
            while (it.hasNext()) {
                this.vmlDrawingPart = zipPackage.getPart(it.next().getTargetURI());
                getShapeIds(bool);
            }
        }
        Map<String, String> map = this.spIDs;
        if (map == null || (str2 = map.get(str)) == null || (relationship = this.vmlDrawingPart.getRelationship(str2)) == null) {
            return null;
        }
        return zipPackage.getPart(relationship.getTargetURI());
    }

    private void getShapeIds(Boolean bool) throws Exception {
        Element element;
        String text;
        String[] split;
        if (this.vmlDrawingPart != null) {
            SAXReader sAXReader = new SAXReader();
            InputStream inputStream = this.vmlDrawingPart.getInputStream();
            Element rootElement = sAXReader.read(inputStream).getRootElement();
            if (rootElement != null) {
                if (this.spIDs == null) {
                    this.spIDs = new Hashtable();
                }
                if (bool.booleanValue() && this.spIDAnchors == null) {
                    this.spIDAnchors = new Hashtable();
                }
                for (Element element2 : rootElement.elements("shape")) {
                    Element element3 = element2.element("imagedata");
                    if (element3 != null) {
                        String attributeValue = element2.attributeValue("spid");
                        if (bool.booleanValue()) {
                            if (attributeValue == null) {
                                attributeValue = element2.attributeValue(OSOutcomeConstants.OUTCOME_ID);
                            }
                            if (attributeValue != null && attributeValue.length() > 8) {
                                String substring = attributeValue.substring(8);
                                this.spIDs.put(substring, element3.attributeValue("relid"));
                                Element element4 = element2.element("ClientData");
                                if (!(element4 == null || (element = element4.element("Anchor")) == null || (text = element.getText()) == null || text.length() <= 0 || (split = text.trim().replaceAll(" ", "").split(",")) == null || split.length != 8)) {
                                    AnchorPoint anchorPoint = new AnchorPoint();
                                    anchorPoint.setColumn((short) Integer.parseInt(split[0]));
                                    anchorPoint.setDX((short) Integer.parseInt(split[1]));
                                    anchorPoint.setRow((short) Integer.parseInt(split[2]));
                                    anchorPoint.setDY((short) Integer.parseInt(split[3]));
                                    AnchorPoint anchorPoint2 = new AnchorPoint();
                                    anchorPoint2.setColumn((short) Integer.parseInt(split[4]));
                                    anchorPoint2.setDX((short) Integer.parseInt(split[5]));
                                    anchorPoint2.setRow((short) Integer.parseInt(split[6]));
                                    anchorPoint2.setDY((short) Integer.parseInt(split[7]));
                                    CellAnchor cellAnchor = new CellAnchor(1);
                                    cellAnchor.setStart(anchorPoint);
                                    cellAnchor.setEnd(anchorPoint2);
                                    this.spIDAnchors.put(substring, cellAnchor);
                                }
                            } else {
                                return;
                            }
                        } else if (attributeValue == null || attributeValue.length() <= 0) {
                            this.spIDs.put(element2.attributeValue(OSOutcomeConstants.OUTCOME_ID), element3.attributeValue("relid"));
                        } else {
                            this.spIDs.put(attributeValue, element3.attributeValue("relid"));
                        }
                    }
                }
            }
            inputStream.close();
        }
    }

    public CellAnchor getExcelShapeAnchor(String str) {
        Map<String, CellAnchor> map;
        if (str == null || (map = this.spIDAnchors) == null || map.size() <= 0) {
            return null;
        }
        return this.spIDAnchors.get(str);
    }

    public void dispose() {
        this.vmlDrawingPart = null;
        Map<String, String> map = this.spIDs;
        if (map != null) {
            map.clear();
            this.spIDs = null;
        }
        Map<String, CellAnchor> map2 = this.spIDAnchors;
        if (map2 != null) {
            for (String str : map2.keySet()) {
                this.spIDAnchors.get(str).dispose();
            }
            this.spIDAnchors.clear();
            this.spIDAnchors = null;
        }
    }
}
