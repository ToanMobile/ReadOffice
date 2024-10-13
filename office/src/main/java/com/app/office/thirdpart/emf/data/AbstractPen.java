package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Shape;
import com.app.office.java.awt.Stroke;
import com.app.office.java.awt.geom.AffineTransform;
import com.app.office.java.awt.geom.Rectangle2D;
import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFRenderer;
import java.util.logging.Logger;

public abstract class AbstractPen implements EMFConstants, GDIObject {
    private static final Logger logger = Logger.getLogger("org.freehep.graphicsio.emf");

    private boolean isInsideFrameStroke(int i) {
        return (i & 255) == 6;
    }

    private class InsideFrameStroke implements Stroke {
        private BasicStroke stroke;

        public InsideFrameStroke(float f, int i, int i2, float f2, float[] fArr, float f3) {
            this.stroke = new BasicStroke(f, i, i2, f2, fArr, f3);
        }

        public Shape createStrokedShape(Shape shape) {
            if (shape == null) {
                return null;
            }
            Rectangle2D bounds2D = shape.getBounds2D();
            float lineWidth = this.stroke.getLineWidth();
            AffineTransform affineTransform = new AffineTransform();
            if (bounds2D.getWidth() > 0.0d) {
                affineTransform.scale((bounds2D.getWidth() - ((double) lineWidth)) / bounds2D.getWidth(), 1.0d);
            }
            if (bounds2D.getHeight() > 0.0d) {
                affineTransform.scale(1.0d, (bounds2D.getHeight() - ((double) lineWidth)) / bounds2D.getHeight());
            }
            Shape createTransformedShape = affineTransform.createTransformedShape(shape);
            Rectangle2D bounds2D2 = createTransformedShape.getBounds2D();
            double d = (double) (lineWidth / 2.0f);
            return this.stroke.createStrokedShape(AffineTransform.getTranslateInstance((bounds2D.getX() - bounds2D2.getX()) + d, (bounds2D.getY() - bounds2D2.getY()) + d).createTransformedShape(createTransformedShape));
        }
    }

    /* access modifiers changed from: protected */
    public int getJoin(int i) {
        int i2 = 61440 & i;
        if (i2 == 0) {
            return 1;
        }
        if (i2 == 4096) {
            return 2;
        }
        if (i2 == 8192) {
            return 0;
        }
        Logger logger2 = logger;
        logger2.warning("got unsupported pen style " + i);
        return 1;
    }

    /* access modifiers changed from: protected */
    public int getCap(int i) {
        int i2 = i & 3840;
        if (i2 == 0) {
            return 1;
        }
        if (i2 == 256) {
            return 2;
        }
        if (i2 == 512) {
            return 0;
        }
        Logger logger2 = logger;
        logger2.warning("got unsupported pen style " + i);
        return 1;
    }

    /* access modifiers changed from: protected */
    public float[] getDash(int i, int[] iArr) {
        switch (i & 255) {
            case 0:
                return null;
            case 1:
                return new float[]{5.0f, 5.0f};
            case 2:
                return new float[]{1.0f, 2.0f};
            case 3:
                return new float[]{5.0f, 2.0f, 1.0f, 2.0f};
            case 4:
                return new float[]{5.0f, 2.0f, 1.0f, 2.0f, 1.0f, 2.0f};
            case 5:
            case 6:
                break;
            case 7:
                if (iArr != null && iArr.length > 0) {
                    float[] fArr = new float[iArr.length];
                    for (int i2 = 0; i2 < iArr.length; i2++) {
                        fArr[i2] = (float) iArr[i2];
                    }
                    return fArr;
                }
            default:
                Logger logger2 = logger;
                logger2.warning("got unsupported pen style " + i);
                return null;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public Stroke createStroke(EMFRenderer eMFRenderer, int i, int[] iArr, float f) {
        int i2 = i;
        int[] iArr2 = iArr;
        if (isInsideFrameStroke(i2)) {
            return new InsideFrameStroke(f, getCap(i2), getJoin(i2), eMFRenderer.getMeterLimit(), getDash(i2, iArr2), 0.0f);
        }
        return new BasicStroke(f, getCap(i2), getJoin(i2), eMFRenderer.getMeterLimit(), getDash(i2, iArr2), 0.0f);
    }
}
