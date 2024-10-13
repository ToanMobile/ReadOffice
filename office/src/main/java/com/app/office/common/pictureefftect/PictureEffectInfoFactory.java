package com.app.office.common.pictureefftect;

import android.graphics.Color;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.pdf.PdfBoolean;
import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperty;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.dom4j.Element;
import com.app.office.fc.hwpf.usermodel.Picture;
import com.app.office.fc.ppt.reader.ReaderKit;
import com.app.office.pg.model.PGMaster;

public class PictureEffectInfoFactory {
    public static PictureEffectInfo getPictureEffectInfor(Element element) {
        Element element2;
        String attributeValue;
        if (element == null) {
            return null;
        }
        PictureEffectInfo pictureEffectInfo = new PictureEffectInfo();
        boolean z = false;
        Element element3 = element.element("srcRect");
        boolean z2 = true;
        if (element3 != null) {
            String attributeValue2 = element3.attributeValue("l");
            float parseInt = attributeValue2 != null ? ((float) Integer.parseInt(attributeValue2)) / 100000.0f : 0.0f;
            String attributeValue3 = element3.attributeValue("t");
            float parseInt2 = attributeValue3 != null ? ((float) Integer.parseInt(attributeValue3)) / 100000.0f : 0.0f;
            String attributeValue4 = element3.attributeValue("r");
            float parseInt3 = attributeValue4 != null ? ((float) Integer.parseInt(attributeValue4)) / 100000.0f : 0.0f;
            String attributeValue5 = element3.attributeValue(HtmlTags.B);
            float parseInt4 = attributeValue5 != null ? ((float) Integer.parseInt(attributeValue5)) / 100000.0f : 0.0f;
            if (!(parseInt == 0.0f && parseInt2 == 0.0f && parseInt3 == 0.0f && parseInt4 == 0.0f)) {
                pictureEffectInfo.setPictureCroppedInfor(new PictureCroppedInfo(parseInt, parseInt2, parseInt3, parseInt4));
                z = true;
            }
        }
        Element element4 = element.element("blip");
        if (element4.element("grayscl") != null) {
            pictureEffectInfo.setGrayScale(true);
            z = true;
        }
        Element element5 = element4.element("biLevel");
        if (!(element5 == null || (attributeValue = element5.attributeValue("thresh")) == null)) {
            pictureEffectInfo.setBlackWhiteThreshold((((float) Integer.parseInt(attributeValue)) / 100000.0f) * 255.0f);
            z = true;
        }
        Element element6 = element4.element("lum");
        if (element6 != null) {
            String attributeValue6 = element6.attributeValue("bright");
            if (attributeValue6 != null) {
                pictureEffectInfo.setBrightness((((float) Integer.parseInt(attributeValue6)) / 100000.0f) * 255.0f);
                z = true;
            }
            String attributeValue7 = element6.attributeValue("contrast");
            if (attributeValue7 != null) {
                float parseInt5 = ((float) Integer.parseInt(attributeValue7)) / 100000.0f;
                if (parseInt5 > 0.0f) {
                    pictureEffectInfo.setContrast((parseInt5 * 9.0f) + 1.0f);
                } else {
                    pictureEffectInfo.setContrast(parseInt5 + 1.0f);
                }
                z = true;
            }
        }
        Element element7 = element4.element("clrChange");
        if (element7 == null || (element2 = element7.element("clrFrom")) == null) {
            z2 = z;
        } else {
            pictureEffectInfo.setTransparentColor(ReaderKit.instance().getColor((PGMaster) null, element2));
        }
        if (z2) {
            return pictureEffectInfo;
        }
        return null;
    }

    public static PictureEffectInfo getPictureEffectInfor_ImageData(Element element) {
        float f;
        float f2;
        if (element == null) {
            return null;
        }
        PictureEffectInfo pictureEffectInfo = new PictureEffectInfo();
        boolean z = false;
        String attributeValue = element.attributeValue("cropleft");
        float parseFloat = attributeValue != null ? Float.parseFloat(attributeValue) / 65535.0f : 0.0f;
        String attributeValue2 = element.attributeValue("croptop");
        float parseFloat2 = attributeValue2 != null ? Float.parseFloat(attributeValue2) / 65535.0f : 0.0f;
        String attributeValue3 = element.attributeValue("cropright");
        float parseFloat3 = attributeValue3 != null ? Float.parseFloat(attributeValue3) / 65535.0f : 0.0f;
        String attributeValue4 = element.attributeValue("cropbottom");
        float parseFloat4 = attributeValue4 != null ? Float.parseFloat(attributeValue4) / 65535.0f : 0.0f;
        boolean z2 = true;
        if (!(parseFloat == 0.0f && parseFloat2 == 0.0f && parseFloat3 == 0.0f && parseFloat4 == 0.0f)) {
            pictureEffectInfo.setPictureCroppedInfor(new PictureCroppedInfo(parseFloat, parseFloat2, parseFloat3, parseFloat4));
            z = true;
        }
        String attributeValue5 = element.attributeValue("blacklevel");
        if (attributeValue5 != null) {
            if (attributeValue5.contains("f")) {
                f2 = Float.parseFloat(attributeValue5) / 65535.0f;
            } else {
                f2 = Float.parseFloat(attributeValue5);
            }
            pictureEffectInfo.setBrightness(f2 * 2.0f * 255.0f);
            z = true;
        }
        String attributeValue6 = element.attributeValue("gain");
        if (attributeValue6 != null) {
            if (attributeValue6.contains("f")) {
                f = Float.parseFloat(attributeValue6) / 65535.0f;
            } else {
                f = Float.parseFloat(attributeValue6);
            }
            pictureEffectInfo.setContrast(f);
            z = true;
        }
        String attributeValue7 = element.attributeValue("grayscale");
        if (attributeValue7 != null && (attributeValue7.equalsIgnoreCase("t") || attributeValue7.equalsIgnoreCase(PdfBoolean.TRUE))) {
            String attributeValue8 = element.attributeValue("bilevel");
            if (attributeValue8 == null || (!attributeValue8.equalsIgnoreCase("t") && !attributeValue8.equalsIgnoreCase(PdfBoolean.TRUE))) {
                pictureEffectInfo.setGrayScale(true);
            } else {
                pictureEffectInfo.setBlackWhiteThreshold(128.0f);
            }
            z = true;
        }
        String attributeValue9 = element.attributeValue("chromakey");
        if (attributeValue9 != null) {
            pictureEffectInfo.setTransparentColor(Color.parseColor(attributeValue9));
        } else {
            z2 = z;
        }
        if (z2) {
            return pictureEffectInfo;
        }
        return null;
    }

    public static EscherProperty getEscherProperty(EscherOptRecord escherOptRecord, int i) {
        if (escherOptRecord == null) {
            return null;
        }
        for (EscherProperty next : escherOptRecord.getEscherProperties()) {
            if (next.getPropertyNumber() == i) {
                return next;
            }
        }
        return null;
    }

    public static PictureEffectInfo getPictureEffectInfor(Picture picture) {
        if (picture == null) {
            return null;
        }
        PictureEffectInfo pictureEffectInfo = new PictureEffectInfo();
        boolean z = false;
        float dxaCropLeft = picture.getDxaCropLeft();
        float dyaCropTop = picture.getDyaCropTop();
        float dxaCropRight = picture.getDxaCropRight();
        float dyaCropBottom = picture.getDyaCropBottom();
        boolean z2 = true;
        if (!(dxaCropLeft == 0.0f && dyaCropTop == 0.0f && dxaCropRight == 0.0f && dyaCropBottom == 0.0f)) {
            pictureEffectInfo.setPictureCroppedInfor(new PictureCroppedInfo(dxaCropLeft, dyaCropTop, dxaCropRight, dyaCropBottom));
            z = true;
        }
        if (picture.isSetBright()) {
            pictureEffectInfo.setBrightness(picture.getBright());
            z = true;
        }
        if (picture.isSetContrast()) {
            pictureEffectInfo.setContrast(picture.getContrast());
            z = true;
        }
        if (picture.isSetGrayScl()) {
            pictureEffectInfo.setGrayScale(true);
            z = true;
        }
        if (picture.isSetThreshold()) {
            pictureEffectInfo.setBlackWhiteThreshold(picture.getThreshold());
        } else {
            z2 = z;
        }
        if (z2) {
            return pictureEffectInfo;
        }
        return null;
    }

    public static PictureEffectInfo getPictureEffectInfor(EscherOptRecord escherOptRecord) {
        float f;
        float f2;
        float f3;
        float f4;
        if (escherOptRecord == null) {
            return null;
        }
        PictureEffectInfo pictureEffectInfo = new PictureEffectInfo();
        boolean z = false;
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty(escherOptRecord, 258);
        if (escherSimpleProperty == null) {
            f = 0.0f;
        } else {
            f = ((float) escherSimpleProperty.getPropertyValue()) / 65536.0f;
        }
        EscherSimpleProperty escherSimpleProperty2 = (EscherSimpleProperty) getEscherProperty(escherOptRecord, 256);
        if (escherSimpleProperty2 == null) {
            f2 = 0.0f;
        } else {
            f2 = ((float) escherSimpleProperty2.getPropertyValue()) / 65536.0f;
        }
        EscherSimpleProperty escherSimpleProperty3 = (EscherSimpleProperty) getEscherProperty(escherOptRecord, 259);
        if (escherSimpleProperty3 == null) {
            f3 = 0.0f;
        } else {
            f3 = ((float) escherSimpleProperty3.getPropertyValue()) / 65536.0f;
        }
        EscherSimpleProperty escherSimpleProperty4 = (EscherSimpleProperty) getEscherProperty(escherOptRecord, 257);
        if (escherSimpleProperty4 == null) {
            f4 = 0.0f;
        } else {
            f4 = ((float) escherSimpleProperty4.getPropertyValue()) / 65536.0f;
        }
        boolean z2 = true;
        if (!(f == 0.0f && f2 == 0.0f && f3 == 0.0f && f4 == 0.0f)) {
            pictureEffectInfo.setPictureCroppedInfor(new PictureCroppedInfo(f, f2, f3, f4));
            z = true;
        }
        EscherSimpleProperty escherSimpleProperty5 = (EscherSimpleProperty) getEscherProperty(escherOptRecord, TIFFConstants.TIFFTAG_PRIMARYCHROMATICITIES);
        if (escherSimpleProperty5 != null) {
            int propertyValue = escherSimpleProperty5.getPropertyValue() & 15;
            if (propertyValue == 4) {
                pictureEffectInfo.setGrayScale(true);
            } else if (propertyValue == 6) {
                pictureEffectInfo.setBlackWhiteThreshold(128.0f);
            }
            z = true;
        }
        EscherSimpleProperty escherSimpleProperty6 = (EscherSimpleProperty) getEscherProperty(escherOptRecord, TIFFConstants.TIFFTAG_CELLLENGTH);
        if (escherSimpleProperty6 != null) {
            pictureEffectInfo.setBrightness((((float) escherSimpleProperty6.getPropertyValue()) / 32768.0f) * 255.0f);
            z = true;
        }
        EscherSimpleProperty escherSimpleProperty7 = (EscherSimpleProperty) getEscherProperty(escherOptRecord, 264);
        if (escherSimpleProperty7 != null) {
            pictureEffectInfo.setContrast(Math.min(((float) escherSimpleProperty7.getPropertyValue()) / 65536.0f, 10.0f));
            z = true;
        }
        EscherSimpleProperty escherSimpleProperty8 = (EscherSimpleProperty) getEscherProperty(escherOptRecord, 263);
        if (escherSimpleProperty8 != null) {
            int propertyValue2 = escherSimpleProperty8.getPropertyValue();
            pictureEffectInfo.setTransparentColor(Color.rgb(propertyValue2 & 255, (65280 & propertyValue2) >> 8, (propertyValue2 & 16711680) >> 16));
        } else {
            z2 = z;
        }
        if (z2) {
            return pictureEffectInfo;
        }
        return null;
    }
}
