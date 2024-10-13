package com.app.office.common.shape;

public class WPPictureShape extends WPAutoShape {
    private PictureShape pictureShape;

    public short getType() {
        return 0;
    }

    public boolean isWatermarkShape() {
        return false;
    }

    public void setPictureShape(PictureShape pictureShape2) {
        this.pictureShape = pictureShape2;
        if (this.rect == null) {
            this.rect = pictureShape2.getBounds();
        }
    }

    public PictureShape getPictureShape() {
        return this.pictureShape;
    }

    public void dispose() {
        PictureShape pictureShape2 = this.pictureShape;
        if (pictureShape2 != null) {
            pictureShape2.dispose();
            this.pictureShape = null;
        }
    }
}
