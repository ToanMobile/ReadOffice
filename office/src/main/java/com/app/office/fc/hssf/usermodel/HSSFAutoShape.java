package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.ss.model.XLSModel.AWorkbook;

public class HSSFAutoShape extends HSSFTextbox {
    private Float[] adjusts;

    public HSSFAutoShape(AWorkbook aWorkbook, EscherContainerRecord escherContainerRecord, HSSFShape hSSFShape, HSSFAnchor hSSFAnchor, int i) {
        super(escherContainerRecord, hSSFShape, hSSFAnchor);
        setShapeType(i);
        processLineWidth();
        processLine(escherContainerRecord, aWorkbook);
        processSimpleBackground(escherContainerRecord, aWorkbook);
        processRotationAndFlip(escherContainerRecord);
        String unicodeGeoText = ShapeKit.getUnicodeGeoText(escherContainerRecord);
        if (unicodeGeoText != null && unicodeGeoText.length() > 0) {
            setString(new HSSFRichTextString(unicodeGeoText));
            setWordArt(true);
            setNoFill(true);
            setFontColor(getFillColor());
        }
    }

    public Float[] getAdjustmentValue() {
        return this.adjusts;
    }

    public void setAdjustmentValue(EscherContainerRecord escherContainerRecord) {
        this.adjusts = ShapeKit.getAdjustmentValue(escherContainerRecord);
    }
}
