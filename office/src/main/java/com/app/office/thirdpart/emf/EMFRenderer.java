package com.app.office.thirdpart.emf;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import com.app.office.java.awt.Color;
import com.app.office.java.awt.Dimension;
import com.app.office.java.awt.Shape;
import com.app.office.java.awt.Stroke;
import com.app.office.java.awt.geom.AffineTransform;
import com.app.office.java.awt.geom.Area;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.java.awt.geom.IllegalPathStateException;
import com.app.office.java.awt.geom.PathIterator;
import com.app.office.simpletext.font.Font;
import com.app.office.thirdpart.emf.data.BasicStroke;
import com.app.office.thirdpart.emf.data.GDIObject;
import com.app.office.thirdpart.emf.io.Tag;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Logger;

public class EMFRenderer {
    public static double TWIP_SCALE = 0.1763888888888889d;
    private static final Logger logger = Logger.getLogger("com.app.office.thirdpart.emf");
    private int arcDirection;
    private int bkMode;
    private Point brushOrigin;
    private Paint brushPaint;
    private Stack dcStack;
    private int escapement;
    private GeneralPath figure = null;
    private GDIObject[] gdiObjects;
    private EMFHeader header;
    private Shape initialClip;
    private Matrix initialMatrix;
    private Canvas mCanvas;
    private Area mCurrClip;
    private boolean mapModeIsotropic = false;
    private AffineTransform mapModeTransform;
    private int meterLimit;
    private GeneralPath path;
    private AffineTransform pathTransform;
    private Paint penPaint;
    private Stroke penStroke;
    private int rop2;
    private int scaleMode;
    private Vector tags;
    private int textAlignMode;
    private Color textColor;
    private boolean useCreatePen;
    private Point viewportOrigin = null;
    private Dimension viewportSize = null;
    private int windingRule;
    private Point windowOrigin = null;
    private Dimension windowSize = null;

    private class DC {
        public int bkMode;
        /* access modifiers changed from: private */
        public Shape clip;
        /* access modifiers changed from: private */
        public Matrix matrix;
        public int meterLimit;
        /* access modifiers changed from: private */
        public Paint paint;
        public GeneralPath path;
        public AffineTransform pathTransform;
        public int scaleMode;
        private Stroke stroke;
        private AffineTransform transform;
        public boolean useCreatePen;
        public int windingRule;

        private DC() {
        }
    }

    public EMFRenderer(EMFInputStream eMFInputStream) throws IOException {
        double d = TWIP_SCALE;
        this.mapModeTransform = AffineTransform.getScaleInstance(d, d);
        this.gdiObjects = new GDIObject[256];
        this.penStroke = new BasicStroke();
        this.brushPaint = new Paint();
        this.penPaint = new Paint();
        this.textAlignMode = 0;
        this.textColor = Color.BLACK;
        this.windingRule = 0;
        this.bkMode = 2;
        this.useCreatePen = true;
        this.meterLimit = 10;
        this.rop2 = 13;
        this.scaleMode = 4;
        this.brushOrigin = new Point(0, 0);
        this.tags = new Vector(0);
        this.path = null;
        this.pathTransform = new AffineTransform();
        this.dcStack = new Stack();
        this.arcDirection = 1;
        this.escapement = 0;
        this.brushPaint.setColor(new Color(0, 0, 0, 0).getRGB());
        this.penPaint.setColor(Color.BLACK.getRGB());
        this.header = eMFInputStream.readHeader();
        while (true) {
            Tag readTag = eMFInputStream.readTag();
            if (readTag != null) {
                this.tags.add(readTag);
            } else {
                eMFInputStream.close();
                return;
            }
        }
    }

    public Dimension getSize() {
        return this.header.getBounds().getSize();
    }

    public void paint(Canvas canvas) {
        this.mCanvas = canvas;
        Rect clipBounds = canvas.getClipBounds();
        Matrix matrix = canvas.getMatrix();
        this.mCurrClip = new Area(createShape(new int[]{-1, clipBounds.top, clipBounds.left, -2, clipBounds.top, clipBounds.right, -2, clipBounds.bottom, clipBounds.right, -2, clipBounds.bottom, clipBounds.left}));
        this.penPaint.setAntiAlias(true);
        this.penPaint.setFilterBitmap(true);
        this.penPaint.setDither(true);
        this.initialMatrix = canvas.getMatrix();
        this.path = null;
        this.figure = null;
        this.meterLimit = 10;
        this.windingRule = 0;
        this.bkMode = 2;
        this.useCreatePen = true;
        this.scaleMode = 4;
        this.windowOrigin = null;
        this.viewportOrigin = null;
        this.windowSize = null;
        this.viewportSize = null;
        this.mapModeIsotropic = false;
        double d = TWIP_SCALE;
        this.mapModeTransform = AffineTransform.getScaleInstance(d, d);
        resetMatrix(canvas);
        this.initialClip = this.mCurrClip;
        for (int i = 0; i < this.tags.size(); i++) {
            Tag tag = (Tag) this.tags.get(i);
            if (tag instanceof EMFTag) {
                ((EMFTag) this.tags.get(i)).render(this);
            } else {
                Logger logger2 = logger;
                logger2.warning("unknown tag: " + tag);
            }
        }
        this.penPaint.setAntiAlias(true);
        this.penPaint.setFilterBitmap(true);
        this.penPaint.setDither(true);
        canvas.setMatrix(matrix);
        setClip(this.initialClip);
    }

    private void resetMatrix(Canvas canvas) {
        Matrix matrix = this.initialMatrix;
        if (matrix != null) {
            canvas.setMatrix(matrix);
        } else {
            canvas.setMatrix(new Matrix());
        }
        Dimension dimension = this.viewportSize;
        if (dimension != null && this.windowSize != null) {
            canvas.scale((float) (dimension.getWidth() / this.windowSize.getWidth()), (float) (this.viewportSize.getHeight() / this.windowSize.getHeight()));
        }
    }

    public void saveDC() {
        DC dc = new DC();
        Paint unused = dc.paint = this.penPaint;
        Matrix unused2 = dc.matrix = this.mCanvas.getMatrix();
        Shape unused3 = dc.clip = this.mCurrClip;
        dc.path = this.path;
        dc.meterLimit = this.meterLimit;
        dc.windingRule = this.windingRule;
        dc.bkMode = this.bkMode;
        dc.useCreatePen = this.useCreatePen;
        dc.scaleMode = this.scaleMode;
        this.dcStack.push(dc);
        this.mCanvas.save();
    }

    public void retoreDC() {
        if (!this.dcStack.empty()) {
            DC dc = (DC) this.dcStack.pop();
            this.meterLimit = dc.meterLimit;
            this.windingRule = dc.windingRule;
            this.path = dc.path;
            this.bkMode = dc.bkMode;
            this.useCreatePen = dc.useCreatePen;
            this.scaleMode = dc.scaleMode;
            this.pathTransform = dc.pathTransform;
            setStroke(this.penStroke);
            this.mCanvas.setMatrix(dc.matrix);
            setClip(dc.clip);
        }
        this.mCanvas.restore();
    }

    public void closeFigure() {
        GeneralPath generalPath = this.figure;
        if (generalPath != null) {
            try {
                generalPath.closePath();
                appendToPath(this.figure);
                this.figure = null;
            } catch (IllegalPathStateException unused) {
                logger.warning("no figure to close");
            }
        }
    }

    public void appendFigure() {
        GeneralPath generalPath = this.figure;
        if (generalPath != null) {
            try {
                appendToPath(generalPath);
                this.figure = null;
            } catch (IllegalPathStateException unused) {
                logger.warning("no figure to append");
            }
        }
    }

    public void fixViewportSize() {
        Dimension dimension;
        if (this.mapModeIsotropic && this.windowSize != null && (dimension = this.viewportSize) != null) {
            dimension.setSize(dimension.getWidth(), this.viewportSize.getWidth() * (this.windowSize.getHeight() / this.windowSize.getWidth()));
        }
    }

    private void fillAndDrawOrAppend(Canvas canvas, Shape shape) {
        if (!appendToPath(shape)) {
            if (!this.useCreatePen) {
                fillShape(shape);
            } else if (this.bkMode == 2) {
                fillShape(shape);
            } else {
                fillShape(shape);
            }
            drawShape(canvas, shape);
        }
    }

    private void drawOrAppend(Canvas canvas, Shape shape) {
        if (!appendToPath(shape)) {
            drawShape(canvas, shape);
        }
    }

    public void drawOrAppendText(String str, float f, float f2) {
        Paint.Style style = this.penPaint.getStyle();
        this.penPaint.setColor(this.textColor.getRGB());
        this.penPaint.setStrokeWidth(0.0f);
        if (2700 == this.escapement) {
            for (int i = 0; i < str.length(); i++) {
                this.mCanvas.drawText(String.valueOf(str.charAt(i)), f, (((float) i) * this.penPaint.getTextSize()) + f2, this.penPaint);
            }
        } else {
            if (this.textAlignMode == 0) {
                f2 += this.penPaint.getTextSize() - 3.0f;
            }
            this.mCanvas.drawText(str, f, f2, this.penPaint);
        }
        this.penPaint.setStyle(style);
    }

    private boolean appendToPath(Shape shape) {
        if (this.path == null) {
            return false;
        }
        AffineTransform affineTransform = this.pathTransform;
        if (affineTransform != null) {
            shape = affineTransform.createTransformedShape(shape);
        }
        this.path.append(shape, false);
        return true;
    }

    public void closePath() {
        GeneralPath generalPath = this.path;
        if (generalPath != null) {
            try {
                generalPath.closePath();
            } catch (IllegalPathStateException unused) {
                logger.warning("no figure to close");
            }
        }
    }

    private void getCurrentSegment(PathIterator pathIterator, Path path2) {
        float[] fArr = new float[6];
        int currentSegment = pathIterator.currentSegment(fArr);
        if (currentSegment == 0) {
            path2.moveTo(fArr[0], fArr[1]);
        } else if (currentSegment == 1) {
            path2.lineTo(fArr[0], fArr[1]);
        } else if (currentSegment == 2) {
            path2.quadTo(fArr[0], fArr[1], fArr[2], fArr[3]);
        } else if (currentSegment == 3) {
            path2.cubicTo(fArr[0], fArr[1], fArr[2], fArr[3], fArr[4], fArr[5]);
        } else if (currentSegment == 4) {
            path2.close();
        }
    }

    private Path getPath(Shape shape) {
        Path path2 = new Path();
        PathIterator pathIterator = shape.getPathIterator((AffineTransform) null);
        while (!pathIterator.isDone()) {
            getCurrentSegment(pathIterator, path2);
            pathIterator.next();
        }
        return path2;
    }

    private void setStroke(Stroke stroke) {
        BasicStroke basicStroke = (BasicStroke) stroke;
        this.penPaint.setStyle(Paint.Style.STROKE);
        this.penPaint.setStrokeWidth(basicStroke.getLineWidth());
        int endCap = basicStroke.getEndCap();
        if (endCap == 0) {
            this.penPaint.setStrokeCap(Paint.Cap.BUTT);
        } else if (endCap == 1) {
            this.penPaint.setStrokeCap(Paint.Cap.ROUND);
        } else if (endCap == 2) {
            this.penPaint.setStrokeCap(Paint.Cap.SQUARE);
        }
        int lineJoin = basicStroke.getLineJoin();
        if (lineJoin == 0) {
            this.penPaint.setStrokeJoin(Paint.Join.MITER);
        } else if (lineJoin == 1) {
            this.penPaint.setStrokeJoin(Paint.Join.ROUND);
        } else if (lineJoin == 2) {
            this.penPaint.setStrokeJoin(Paint.Join.BEVEL);
        }
        this.penPaint.setStrokeMiter(basicStroke.getMiterLimit());
    }

    private void drawShape(Canvas canvas, Shape shape) {
        setStroke(this.penStroke);
        int i = this.rop2;
        if (i == 1) {
            this.penPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            this.penPaint.setColor(Color.black.getRGB());
        } else if (i == 13) {
            this.penPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        } else if (i == 11) {
            this.penPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        } else if (i == 16) {
            this.penPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            this.penPaint.setColor(Color.white.getRGB());
        } else if (i == 4) {
            this.penPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        } else if (i == 7) {
            this.penPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        } else {
            Logger logger2 = logger;
            logger2.warning("got unsupported ROP" + this.rop2);
        }
        canvas.drawPath(getPath(shape), this.penPaint);
    }

    public void setFont(Font font) {
        Typeface typeface;
        if (font != null) {
            String name = font.getName();
            int style = font.getStyle();
            String str = "sans-serif";
            if (name == null) {
                str = "";
            } else if (name.equalsIgnoreCase("Serif") || name.equalsIgnoreCase("TimesRoman")) {
                str = "serif";
            } else if (!name.equalsIgnoreCase("SansSerif") && !name.equalsIgnoreCase("Helvetica") && (name.equalsIgnoreCase("Monospaced") || name.equalsIgnoreCase("Courier"))) {
                str = "monospace";
            }
            if (style == 0) {
                typeface = Typeface.create(str, 0);
            } else if (style == 1) {
                typeface = Typeface.create(str, 1);
            } else if (style == 2) {
                typeface = Typeface.create(str, 2);
            } else if (style != 3) {
                typeface = Typeface.DEFAULT;
            } else {
                typeface = Typeface.create(str, 3);
            }
            this.penPaint.setTextSize((float) font.getFontSize());
            this.penPaint.setTypeface(typeface);
        }
    }

    public void setEscapement(int i) {
        this.escapement = i;
    }

    public Matrix getMatrix() {
        return this.mCanvas.getMatrix();
    }

    public void transform(AffineTransform affineTransform) {
        Matrix matrix = new Matrix();
        matrix.setValues(createMatrix(affineTransform));
        this.mCanvas.concat(matrix);
    }

    public void resetTransformation() {
        resetMatrix(this.mCanvas);
    }

    public void setMatrix(Matrix matrix) {
        this.mCanvas.setMatrix(matrix);
    }

    public void setClip(Shape shape) {
        this.mCurrClip = new Area(shape);
    }

    public void clip(Shape shape) {
        this.mCanvas.clipPath(getPath(shape), Region.Op.REPLACE);
    }

    public Shape getClip() {
        return this.mCurrClip;
    }

    private Shape createShape(int[] iArr) {
        int i;
        GeneralPath generalPath = new GeneralPath();
        int i2 = 0;
        while (i2 < iArr.length && (i = iArr[i2]) != -5) {
            if (i == -4) {
                int i3 = i2 + 1;
                int i4 = i3 + 1;
                int i5 = i4 + 1;
                int i6 = i5 + 1;
                int i7 = i6 + 1;
                i2 = i7 + 1;
                generalPath.curveTo((float) iArr[i3], (float) iArr[i4], (float) iArr[i5], (float) iArr[i6], (float) iArr[i7], (float) iArr[i2]);
            } else if (i == -3) {
                int i8 = i2 + 1;
                int i9 = i8 + 1;
                int i10 = i9 + 1;
                i2 = i10 + 1;
                generalPath.quadTo((float) iArr[i8], (float) iArr[i9], (float) iArr[i10], (float) iArr[i2]);
            } else if (i == -2) {
                int i11 = i2 + 1;
                i2 = i11 + 1;
                generalPath.lineTo((float) iArr[i11], (float) iArr[i2]);
            } else if (i == -1) {
                int i12 = i2 + 1;
                i2 = i12 + 1;
                generalPath.moveTo((float) iArr[i12], (float) iArr[i2]);
            }
            i2++;
        }
        return generalPath;
    }

    public static float[] createMatrix(AffineTransform affineTransform) {
        double[] dArr = new double[9];
        affineTransform.getMatrix(dArr);
        return new float[]{(float) dArr[0], (float) dArr[2], (float) dArr[4], (float) dArr[1], (float) dArr[3], (float) dArr[5], 0.0f, 0.0f, 1.0f};
    }

    public void drawImage(Bitmap bitmap, AffineTransform affineTransform) {
        Matrix matrix = new Matrix();
        matrix.setValues(createMatrix(affineTransform));
        this.mCanvas.drawBitmap(bitmap, matrix, this.penPaint);
    }

    public void drawImage(Bitmap bitmap, int i, int i2, int i3, int i4) {
        this.mCanvas.drawBitmap(bitmap, (Rect) null, new Rect(i, i2, i3 + i, i4 + i2), (Paint) null);
    }

    public void drawShape(Shape shape) {
        drawShape(this.mCanvas, shape);
    }

    public void fillShape(Shape shape) {
        Paint.Style style = this.brushPaint.getStyle();
        this.brushPaint.setStyle(Paint.Style.FILL);
        this.mCanvas.drawPath(getPath(shape), this.brushPaint);
        this.brushPaint.setStyle(style);
    }

    public void fillAndDrawShape(Shape shape) {
        Paint.Style style = this.brushPaint.getStyle();
        this.brushPaint.setStyle(Paint.Style.FILL);
        drawShape(this.mCanvas, shape);
        this.brushPaint.setStyle(style);
    }

    public void fillAndDrawOrAppend(Shape shape) {
        fillAndDrawOrAppend(this.mCanvas, shape);
    }

    public void drawOrAppend(Shape shape) {
        drawOrAppend(this.mCanvas, shape);
    }

    public int getWindingRule() {
        return this.windingRule;
    }

    public GeneralPath getFigure() {
        return this.figure;
    }

    public void setFigure(GeneralPath generalPath) {
        this.figure = generalPath;
    }

    public GeneralPath getPath() {
        return this.path;
    }

    public void setPath(GeneralPath generalPath) {
        this.path = generalPath;
    }

    public Shape getInitialClip() {
        return this.initialClip;
    }

    public AffineTransform getPathTransform() {
        return this.pathTransform;
    }

    public void setPathTransform(AffineTransform affineTransform) {
        this.pathTransform = affineTransform;
    }

    public void setWindingRule(int i) {
        this.windingRule = i;
    }

    public void setMapModeIsotropic(boolean z) {
        this.mapModeIsotropic = z;
    }

    public AffineTransform getMapModeTransform() {
        return this.mapModeTransform;
    }

    public void setMapModeTransform(AffineTransform affineTransform) {
        this.mapModeTransform = affineTransform;
    }

    public void setWindowOrigin(Point point) {
        this.windowOrigin = point;
        if (point != null) {
            this.mCanvas.translate((float) (-point.x), (float) (-point.y));
        }
    }

    public void setViewportOrigin(Point point) {
        this.viewportOrigin = point;
        if (point != null) {
            this.mCanvas.translate((float) (-point.x), (float) (-point.y));
        }
    }

    public void setViewportSize(Dimension dimension) {
        this.viewportSize = dimension;
        fixViewportSize();
        resetTransformation();
    }

    public void setWindowSize(Dimension dimension) {
        this.windowSize = dimension;
        fixViewportSize();
        resetTransformation();
    }

    public GDIObject getGDIObject(int i) {
        return this.gdiObjects[i];
    }

    public void storeGDIObject(int i, GDIObject gDIObject) {
        this.gdiObjects[i] = gDIObject;
    }

    public void setUseCreatePen(boolean z) {
        this.useCreatePen = z;
    }

    public void setPenPaint(Color color) {
        this.penPaint.setColor(color.getRGB());
    }

    public Stroke getPenStroke() {
        return this.penStroke;
    }

    public void setPenStroke(Stroke stroke) {
        this.penStroke = stroke;
    }

    public void setBrushPaint(Color color) {
        this.brushPaint.setColor(color.getRGB());
    }

    public void setBrushPaint(Bitmap bitmap) {
        this.mCanvas.clipRect(0, 0, 16, 16);
        this.mCanvas.setBitmap(bitmap);
    }

    public float getMeterLimit() {
        return (float) this.meterLimit;
    }

    public void setMeterLimit(int i) {
        this.meterLimit = i;
    }

    public void setTextColor(Color color) {
        this.textColor = color;
    }

    public void setTextBkColor() {
        setBrushPaint(this.textColor);
    }

    public void setRop2(int i) {
        this.rop2 = i;
    }

    public void setBkMode(int i) {
        this.bkMode = i;
    }

    public int getTextAlignMode() {
        return this.textAlignMode;
    }

    public void setTextAlignMode(int i) {
        this.textAlignMode = i;
    }

    public void setScaleMode(int i) {
        this.scaleMode = i;
    }

    public Point getBrushOrigin() {
        return this.brushOrigin;
    }

    public void setBrushOrigin(Point point) {
        this.brushOrigin = point;
    }

    public void setArcDirection(int i) {
        this.arcDirection = i;
    }

    public int getArcDirection() {
        return this.arcDirection;
    }
}
