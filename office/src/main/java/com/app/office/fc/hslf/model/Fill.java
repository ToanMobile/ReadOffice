package com.app.office.fc.hslf.model;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.AbstractEscherOptRecord;
import com.app.office.fc.ddf.EscherBSERecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherProperty;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.hslf.usermodel.PictureData;
import com.app.office.fc.hslf.usermodel.SlideShow;
import com.app.office.java.awt.Color;
import java.util.List;

public final class Fill {
    protected Shape shape;

    public Fill(Shape shape2) {
        this.shape = shape2;
    }

    public int getFillType() {
        return ShapeKit.getFillType(this.shape.getSpContainer());
    }

    public int getFillAngle() {
        return ShapeKit.getFillAngle(this.shape.getSpContainer());
    }

    public int getFillFocus() {
        return ShapeKit.getFillFocus(this.shape.getSpContainer());
    }

    public boolean isShaderPreset() {
        return ShapeKit.isShaderPreset(this.shape.getSpContainer());
    }

    public int[] getShaderColors() {
        return ShapeKit.getShaderColors(this.shape.getSpContainer());
    }

    public float[] getShaderPositions() {
        return ShapeKit.getShaderPositions(this.shape.getSpContainer());
    }

    public int getRadialGradientPositionType() {
        return ShapeKit.getRadialGradientPositionType(this.shape.getSpContainer());
    }

    public Color getForegroundColor() {
        return ShapeKit.getForegroundColor(this.shape.getSpContainer(), this.shape.getSheet(), 2);
    }

    public Color getFillbackColor() {
        return ShapeKit.getFillbackColor(this.shape.getSpContainer(), this.shape.getSheet(), 2);
    }

    public PictureData getPictureData() {
        EscherProperty escherProperty = ShapeKit.getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) ShapeKit.getEscherChild(this.shape.getSpContainer(), -4085), 390);
        if (escherProperty != null && (escherProperty instanceof EscherSimpleProperty)) {
            EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) escherProperty;
            SlideShow slideShow = this.shape.getSheet().getSlideShow();
            PictureData[] pictureData = slideShow.getPictureData();
            EscherContainerRecord escherContainerRecord = (EscherContainerRecord) ShapeKit.getEscherChild(slideShow.getDocumentRecord().getPPDrawingGroup().getDggContainer(), -4095);
            if (escherContainerRecord != null) {
                List<EscherRecord> childRecords = escherContainerRecord.getChildRecords();
                int propertyValue = escherSimpleProperty.getPropertyValue() & 65535;
                if (propertyValue != 0) {
                    EscherBSERecord escherBSERecord = (EscherBSERecord) childRecords.get(propertyValue - 1);
                    for (int i = 0; i < pictureData.length; i++) {
                        if (pictureData[i].getOffset() == escherBSERecord.getOffset()) {
                            return pictureData[i];
                        }
                    }
                }
            }
        }
        return null;
    }

    public void setFillType(int i) {
        Shape.setEscherProperty((EscherOptRecord) ShapeKit.getEscherChild(this.shape.getSpContainer(), -4085), EscherProperties.FILL__FILLTYPE, i);
    }

    public void setForegroundColor(Color color) {
        EscherOptRecord escherOptRecord = (EscherOptRecord) ShapeKit.getEscherChild(this.shape.getSpContainer(), -4085);
        if (color == null) {
            Shape.setEscherProperty(escherOptRecord, EscherProperties.FILL__NOFILLHITTEST, 1376256);
            return;
        }
        Shape.setEscherProperty(escherOptRecord, EscherProperties.FILL__FILLCOLOR, new Color(color.getBlue(), color.getGreen(), color.getRed(), 0).getRGB());
        Shape.setEscherProperty(escherOptRecord, EscherProperties.FILL__NOFILLHITTEST, 1376273);
    }

    public void setBackgroundColor(Color color) {
        EscherOptRecord escherOptRecord = (EscherOptRecord) ShapeKit.getEscherChild(this.shape.getSpContainer(), -4085);
        if (color == null) {
            Shape.setEscherProperty(escherOptRecord, EscherProperties.FILL__FILLBACKCOLOR, -1);
        } else {
            Shape.setEscherProperty(escherOptRecord, EscherProperties.FILL__FILLBACKCOLOR, new Color(color.getBlue(), color.getGreen(), color.getRed(), 0).getRGB());
        }
    }

    public void setPictureData(int i) {
        Shape.setEscherProperty((EscherOptRecord) ShapeKit.getEscherChild(this.shape.getSpContainer(), -4085), 16774, i);
    }

    public void dispose() {
        this.shape = null;
    }
}
