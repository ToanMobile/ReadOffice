package com.app.office.fc;

import android.graphics.Matrix;
import androidx.core.app.FrameMetricsAggregator;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.MotionEventCompat;
import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.itextpdf.text.pdf.codec.wmf.MetaDo;
import com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail;
import com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder;
import com.app.office.common.shape.Arrow;
import com.app.office.common.shape.ShapeTypes;
import com.app.office.constant.AutoShapeConstant;
import com.app.office.fc.ddf.AbstractEscherOptRecord;
import com.app.office.fc.ddf.EscherArrayProperty;
import com.app.office.fc.ddf.EscherComplexProperty;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherProperty;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.ddf.EscherTertiaryOptRecord;
import com.app.office.fc.hslf.model.Sheet;
import com.app.office.fc.hslf.record.ColorSchemeAtom;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.StringUtil;
import com.app.office.java.awt.Color;
import com.app.office.java.awt.Rectangle;
import com.app.office.ss.model.XLSModel.AWorkbook;
import com.app.office.ss.util.ColorUtil;
import com.app.office.thirdpart.emf.EMFConstants;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.jvm.internal.ByteCompanionObject;

public class ShapeKit {
    public static final float DefaultMargin_Pixel = 4.8f;
    public static final int DefaultMargin_Twip = 72;
    public static final int EMU_PER_CENTIMETER = 360000;
    public static final int EMU_PER_INCH = 914400;
    public static final int EMU_PER_POINT = 12700;
    public static final int MASTER_DPI = 576;
    public static final byte[] SEGMENTINFO_CLOSE = {1, 96};
    public static final byte[] SEGMENTINFO_CUBICTO = {0, -83};
    public static final byte[] SEGMENTINFO_CUBICTO1 = {0, -81};
    public static final byte[] SEGMENTINFO_CUBICTO2 = {0, -77};
    public static final byte[] SEGMENTINFO_END = {0, ByteCompanionObject.MIN_VALUE};
    public static final byte[] SEGMENTINFO_ESCAPE = {1, 0};
    public static final byte[] SEGMENTINFO_ESCAPE1 = {3, 0};
    public static final byte[] SEGMENTINFO_ESCAPE2 = {1, 32};
    public static final byte[] SEGMENTINFO_LINETO = {0, -84};
    public static final byte[] SEGMENTINFO_LINETO2 = {0, -80};
    public static final byte[] SEGMENTINFO_MOVETO = {0, 64};

    public static EscherRecord getEscherChild(EscherContainerRecord escherContainerRecord, int i) {
        Iterator<EscherRecord> childIterator = escherContainerRecord.getChildIterator();
        while (childIterator.hasNext()) {
            EscherRecord next = childIterator.next();
            if (next.getRecordId() == i) {
                return next;
            }
        }
        return null;
    }

    public static EscherProperty getEscherProperty(AbstractEscherOptRecord abstractEscherOptRecord, int i) {
        if (abstractEscherOptRecord == null) {
            return null;
        }
        for (EscherProperty next : abstractEscherOptRecord.getEscherProperties()) {
            if (next.getPropertyNumber() == i) {
                return next;
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
        r1 = (com.app.office.fc.ddf.EscherSimpleProperty) getEscherProperty((com.app.office.fc.ddf.AbstractEscherOptRecord) r1, (int) r2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getEscherProperty(com.app.office.fc.ddf.EscherContainerRecord r1, short r2, int r3) {
        /*
            r0 = -4085(0xfffffffffffff00b, float:NaN)
            com.app.office.fc.ddf.EscherRecord r1 = getEscherChild(r1, r0)
            com.app.office.fc.ddf.EscherOptRecord r1 = (com.app.office.fc.ddf.EscherOptRecord) r1
            if (r1 == 0) goto L_0x0017
            com.app.office.fc.ddf.EscherProperty r1 = getEscherProperty((com.app.office.fc.ddf.AbstractEscherOptRecord) r1, (int) r2)
            com.app.office.fc.ddf.EscherSimpleProperty r1 = (com.app.office.fc.ddf.EscherSimpleProperty) r1
            if (r1 == 0) goto L_0x0017
            int r1 = r1.getPropertyValue()
            return r1
        L_0x0017:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ShapeKit.getEscherProperty(com.app.office.fc.ddf.EscherContainerRecord, short, int):int");
    }

    public static int getEscherProperty(EscherContainerRecord escherContainerRecord, short s) {
        return getEscherProperty(escherContainerRecord, s, 0);
    }

    public static int getShapeType(EscherContainerRecord escherContainerRecord) {
        EscherSpRecord escherSpRecord = (EscherSpRecord) escherContainerRecord.getChildById(EscherSpRecord.RECORD_ID);
        if (escherSpRecord != null) {
            return escherSpRecord.getOptions() >> 4;
        }
        return -1;
    }

    public static int getShapeId(EscherContainerRecord escherContainerRecord) {
        EscherSpRecord escherSpRecord = (EscherSpRecord) escherContainerRecord.getChildById(EscherSpRecord.RECORD_ID);
        if (escherSpRecord == null) {
            return 0;
        }
        return escherSpRecord.getShapeId();
    }

    public static int getMasterShapeID(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty;
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        if (escherOptRecord == null || (escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 769)) == null) {
            return 0;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    public static boolean isHidden(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty;
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        if (escherOptRecord == null || (escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 959)) == null || escherSimpleProperty.getPropertyValue() != 131074) {
            return false;
        }
        return true;
    }

    public static boolean hasLine(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), (int) FrameMetricsAggregator.EVERY_DURATION);
        if (escherSimpleProperty == null || (escherSimpleProperty.getPropertyValue() & 255) != 0) {
            return true;
        }
        return false;
    }

    public static int getLineWidth(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), 459);
        if (escherSimpleProperty == null) {
            return 1;
        }
        return escherSimpleProperty.getPropertyValue() / AutoShapeConstant.LINEWIDTH_DEFAULT;
    }

    public static int getLineDashing(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), 462);
        if (escherSimpleProperty == null) {
            return 0;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    public static int getStartArrowType(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty;
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        if (escherOptRecord == null || (escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 464)) == null) {
            return 0;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    public static int getStartArrowWidth(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty;
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        if (escherOptRecord == null || (escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 466)) == null) {
            return 1;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    public static int getStartArrowLength(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty;
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        if (escherOptRecord == null || (escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 467)) == null) {
            return 1;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    public static ArrowPathAndTail getStartArrowPathAndTail(EscherContainerRecord escherContainerRecord, Rectangle rectangle) {
        float f;
        int i;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        int i2;
        Rectangle rectangle2 = rectangle;
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__SHAPEPATH, 4));
        EscherArrayProperty escherArrayProperty = (EscherArrayProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 16709);
        if (escherArrayProperty == null) {
            escherArrayProperty = (EscherArrayProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, (int) TIFFConstants.TIFFTAG_TILEBYTECOUNTS);
        }
        EscherArrayProperty escherArrayProperty2 = (EscherArrayProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 16710);
        if (escherArrayProperty2 == null) {
            escherArrayProperty2 = (EscherArrayProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, (int) TIFFConstants.TIFFTAG_BADFAXLINES);
        }
        ArrowPathAndTail arrowPathAndTail = null;
        if (escherArrayProperty == null || escherArrayProperty2 == null) {
            return null;
        }
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 322);
        EscherSimpleProperty escherSimpleProperty2 = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, (int) TIFFConstants.TIFFTAG_TILELENGTH);
        float propertyValue = escherSimpleProperty != null ? (float) escherSimpleProperty.getPropertyValue() : 0.0f;
        float propertyValue2 = escherSimpleProperty2 != null ? (float) escherSimpleProperty2.getPropertyValue() : 0.0f;
        Matrix matrix = new Matrix();
        if (propertyValue > 0.0f && propertyValue2 > 0.0f) {
            matrix.postScale(((float) rectangle2.width) / propertyValue, ((float) rectangle2.height) / propertyValue2);
        }
        EscherSimpleProperty escherSimpleProperty3 = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 464);
        if (escherSimpleProperty3 != null && escherSimpleProperty3.getPropertyValue() > 0) {
            Arrow arrow = new Arrow((byte) escherSimpleProperty3.getPropertyValue(), getStartArrowWidth(escherContainerRecord), getStartArrowLength(escherContainerRecord));
            int round = Math.round(((float) getLineWidth(escherContainerRecord)) * 1.3333334f);
            int numberOfElementsInArray = escherArrayProperty.getNumberOfElementsInArray();
            escherArrayProperty2.getNumberOfElementsInArray();
            byte[] element = escherArrayProperty.getElement(0);
            if (element.length == 8) {
                f = (float) LittleEndian.getInt(element, 0);
                i = LittleEndian.getInt(element, 4);
            } else {
                f = (float) LittleEndian.getShort(element, 0);
                i = LittleEndian.getShort(element, 2);
            }
            float f9 = (float) i;
            float f10 = f;
            byte[] element2 = escherArrayProperty2.getElement(1);
            if (Arrays.equals(element2, SEGMENTINFO_CUBICTO) || Arrays.equals(element2, SEGMENTINFO_CUBICTO1) || Arrays.equals(element2, SEGMENTINFO_CUBICTO2) || Arrays.equals(element2, SEGMENTINFO_ESCAPE2)) {
                if (4 <= numberOfElementsInArray) {
                    byte[] element3 = escherArrayProperty.getElement(1);
                    byte[] element4 = escherArrayProperty.getElement(2);
                    byte[] element5 = escherArrayProperty.getElement(3);
                    if (element3.length == 8 && element4.length == 8 && element5.length == 8) {
                        f7 = (float) LittleEndian.getInt(element5, 0);
                        f2 = (float) LittleEndian.getInt(element3, 4);
                        f6 = (float) LittleEndian.getInt(element5, 4);
                        f4 = (float) LittleEndian.getInt(element4, 4);
                        f3 = (float) LittleEndian.getInt(element3, 0);
                        f5 = (float) LittleEndian.getInt(element4, 0);
                    } else {
                        f7 = (float) LittleEndian.getShort(element5, 0);
                        f2 = (float) LittleEndian.getShort(element3, 2);
                        f6 = (float) LittleEndian.getShort(element5, 2);
                        f4 = (float) LittleEndian.getShort(element4, 2);
                        f3 = (float) LittleEndian.getShort(element3, 0);
                        f5 = (float) LittleEndian.getShort(element4, 0);
                    }
                    arrowPathAndTail = LineArrowPathBuilder.getCubicBezArrowPath(f7, f6, f5, f4, f3, f2, f10, f9, arrow, (int) ((((float) round) * propertyValue) / ((float) rectangle2.width)));
                }
            } else if ((Arrays.equals(element2, SEGMENTINFO_LINETO) || Arrays.equals(element2, SEGMENTINFO_LINETO2) || Arrays.equals(element2, SEGMENTINFO_ESCAPE) || Arrays.equals(element2, SEGMENTINFO_ESCAPE1)) && 2 <= numberOfElementsInArray) {
                byte[] element6 = escherArrayProperty.getElement(1);
                if (element6.length == 8) {
                    f8 = (float) LittleEndian.getInt(element6, 0);
                    i2 = LittleEndian.getInt(element6, 4);
                } else {
                    f8 = (float) LittleEndian.getShort(element6, 0);
                    i2 = LittleEndian.getShort(element6, 2);
                }
                arrowPathAndTail = LineArrowPathBuilder.getDirectLineArrowPath(f8, (float) i2, f10, f9, arrow, (int) ((((float) round) * propertyValue) / ((float) rectangle2.width)));
            }
        }
        if (!(arrowPathAndTail == null || arrowPathAndTail.getArrowPath() == null)) {
            arrowPathAndTail.getArrowPath().transform(matrix);
        }
        return arrowPathAndTail;
    }

    public static int getEndArrowType(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty;
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        if (escherOptRecord == null || (escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 465)) == null || escherSimpleProperty.getPropertyValue() <= 0) {
            return 0;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    public static int getEndArrowWidth(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty;
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        if (escherOptRecord == null || (escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 468)) == null) {
            return 1;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    public static int getEndArrowLength(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty;
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        if (escherOptRecord == null || (escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 469)) == null) {
            return 1;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    private static int getRealSegmentsCount(EscherArrayProperty escherArrayProperty, EscherArrayProperty escherArrayProperty2) {
        int i;
        int numberOfElementsInArray = escherArrayProperty.getNumberOfElementsInArray();
        int numberOfElementsInArray2 = escherArrayProperty2.getNumberOfElementsInArray();
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < numberOfElementsInArray2 && i2 < numberOfElementsInArray; i4++) {
            byte[] element = escherArrayProperty2.getElement(i4);
            if (Arrays.equals(element, SEGMENTINFO_MOVETO)) {
                i2++;
            } else {
                if (Arrays.equals(element, SEGMENTINFO_CUBICTO) || Arrays.equals(element, SEGMENTINFO_CUBICTO1) || Arrays.equals(element, SEGMENTINFO_CUBICTO2) || Arrays.equals(element, SEGMENTINFO_ESCAPE2)) {
                    i = i2 + 3;
                    if (i > numberOfElementsInArray) {
                    }
                } else {
                    if (!Arrays.equals(element, SEGMENTINFO_LINETO)) {
                        if (!Arrays.equals(element, SEGMENTINFO_LINETO2) && !Arrays.equals(element, SEGMENTINFO_ESCAPE) && !Arrays.equals(element, SEGMENTINFO_ESCAPE1)) {
                        }
                    }
                    i = i2 + 1;
                    if (i > numberOfElementsInArray) {
                    }
                }
                i3++;
                i2 = i;
            }
        }
        return i3 + 1;
    }

    public static ArrowPathAndTail getEndArrowPathAndTail(EscherContainerRecord escherContainerRecord, Rectangle rectangle) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        float f11;
        float f12;
        int i;
        Rectangle rectangle2 = rectangle;
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__SHAPEPATH, 4));
        EscherArrayProperty escherArrayProperty = (EscherArrayProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 16709);
        if (escherArrayProperty == null) {
            escherArrayProperty = (EscherArrayProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, (int) TIFFConstants.TIFFTAG_TILEBYTECOUNTS);
        }
        EscherArrayProperty escherArrayProperty2 = (EscherArrayProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 16710);
        if (escherArrayProperty2 == null) {
            escherArrayProperty2 = (EscherArrayProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, (int) TIFFConstants.TIFFTAG_BADFAXLINES);
        }
        ArrowPathAndTail arrowPathAndTail = null;
        if (escherArrayProperty == null || escherArrayProperty2 == null) {
            return null;
        }
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 322);
        EscherSimpleProperty escherSimpleProperty2 = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, (int) TIFFConstants.TIFFTAG_TILELENGTH);
        float propertyValue = escherSimpleProperty != null ? (float) escherSimpleProperty.getPropertyValue() : 0.0f;
        float propertyValue2 = escherSimpleProperty2 != null ? (float) escherSimpleProperty2.getPropertyValue() : 0.0f;
        Matrix matrix = new Matrix();
        if (propertyValue > 0.0f && propertyValue2 > 0.0f) {
            matrix.postScale(((float) rectangle2.width) / propertyValue, ((float) rectangle2.height) / propertyValue2);
        }
        EscherSimpleProperty escherSimpleProperty3 = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 465);
        if (escherSimpleProperty3 != null && escherSimpleProperty3.getPropertyValue() > 0) {
            Arrow arrow = new Arrow((byte) escherSimpleProperty3.getPropertyValue(), getEndArrowWidth(escherContainerRecord), getEndArrowLength(escherContainerRecord));
            int round = Math.round(((float) getLineWidth(escherContainerRecord)) * 1.3333334f);
            int numberOfElementsInArray = escherArrayProperty.getNumberOfElementsInArray();
            int numberOfElementsInArray2 = escherArrayProperty2.getNumberOfElementsInArray();
            int i2 = 0;
            int i3 = 0;
            while (i2 < numberOfElementsInArray2 && i3 < numberOfElementsInArray) {
                byte[] element = escherArrayProperty2.getElement(i2);
                if (Arrays.equals(element, SEGMENTINFO_MOVETO)) {
                    i3++;
                } else {
                    if (Arrays.equals(element, SEGMENTINFO_CUBICTO) || Arrays.equals(element, SEGMENTINFO_CUBICTO1) || Arrays.equals(element, SEGMENTINFO_CUBICTO2) || Arrays.equals(element, SEGMENTINFO_ESCAPE2)) {
                        i = i3 + 3;
                        if (i > numberOfElementsInArray) {
                        }
                    } else {
                        if (!Arrays.equals(element, SEGMENTINFO_LINETO)) {
                            if (!Arrays.equals(element, SEGMENTINFO_LINETO2) && !Arrays.equals(element, SEGMENTINFO_ESCAPE) && !Arrays.equals(element, SEGMENTINFO_ESCAPE1)) {
                            }
                        }
                        i = i3 + 1;
                        if (i > numberOfElementsInArray) {
                        }
                    }
                    i3 = i;
                }
                i2++;
            }
            byte[] element2 = escherArrayProperty2.getElement(i2);
            while (true) {
                if ((Arrays.equals(element2, SEGMENTINFO_CLOSE) || Arrays.equals(element2, SEGMENTINFO_END)) && i2 > 0) {
                    byte[] element3 = escherArrayProperty2.getElement(i2);
                    i2--;
                    element2 = element3;
                }
            }
            if (Arrays.equals(element2, SEGMENTINFO_CUBICTO) || Arrays.equals(element2, SEGMENTINFO_CUBICTO1) || Arrays.equals(element2, SEGMENTINFO_CUBICTO2) || Arrays.equals(element2, SEGMENTINFO_ESCAPE2)) {
                byte[] element4 = escherArrayProperty.getElement(numberOfElementsInArray - 4);
                byte[] element5 = escherArrayProperty.getElement(numberOfElementsInArray - 3);
                byte[] element6 = escherArrayProperty.getElement(numberOfElementsInArray - 2);
                byte[] element7 = escherArrayProperty.getElement(numberOfElementsInArray - 1);
                if (element4.length == 8 && element5.length == 8 && element6.length == 8 && element7.length == 8) {
                    f8 = (float) LittleEndian.getInt(element4, 4);
                    f = (float) LittleEndian.getInt(element7, 4);
                    f5 = (float) LittleEndian.getInt(element5, 4);
                    f3 = (float) LittleEndian.getInt(element6, 4);
                    f2 = (float) LittleEndian.getInt(element7, 0);
                    f4 = (float) LittleEndian.getInt(element6, 0);
                    f7 = (float) LittleEndian.getInt(element4, 0);
                    f6 = (float) LittleEndian.getInt(element5, 0);
                } else {
                    f8 = (float) LittleEndian.getShort(element4, 2);
                    f = (float) LittleEndian.getShort(element7, 2);
                    f6 = (float) LittleEndian.getShort(element5, 0);
                    f5 = (float) LittleEndian.getShort(element5, 2);
                    f3 = (float) LittleEndian.getShort(element6, 2);
                    f2 = (float) LittleEndian.getShort(element7, 0);
                    f4 = (float) LittleEndian.getShort(element6, 0);
                    f7 = (float) LittleEndian.getShort(element4, 0);
                }
                arrowPathAndTail = LineArrowPathBuilder.getCubicBezArrowPath(f7, f8, f6, f5, f4, f3, f2, f, arrow, (int) ((((float) round) * propertyValue) / ((float) rectangle2.width)));
            } else if (Arrays.equals(element2, SEGMENTINFO_MOVETO) || Arrays.equals(element2, SEGMENTINFO_LINETO) || Arrays.equals(element2, SEGMENTINFO_LINETO2) || Arrays.equals(element2, SEGMENTINFO_ESCAPE) || Arrays.equals(element2, SEGMENTINFO_ESCAPE1)) {
                byte[] element8 = escherArrayProperty.getElement(numberOfElementsInArray - 2);
                byte[] element9 = escherArrayProperty.getElement(numberOfElementsInArray - 1);
                if (element8.length == 8 && element9.length == 8) {
                    f9 = (float) LittleEndian.getInt(element9, 4);
                    f12 = (float) LittleEndian.getInt(element8, 0);
                    f11 = (float) LittleEndian.getInt(element8, 4);
                    f10 = (float) LittleEndian.getInt(element9, 0);
                } else {
                    f9 = (float) LittleEndian.getShort(element9, 2);
                    f12 = (float) LittleEndian.getShort(element8, 0);
                    f11 = (float) LittleEndian.getShort(element8, 2);
                    f10 = (float) LittleEndian.getShort(element9, 0);
                }
                arrowPathAndTail = LineArrowPathBuilder.getDirectLineArrowPath(f12, f11, f10, f9, arrow, (int) ((((float) round) * propertyValue) / ((float) rectangle2.width)));
            }
        }
        if (!(arrowPathAndTail == null || arrowPathAndTail.getArrowPath() == null)) {
            arrowPathAndTail.getArrowPath().transform(matrix);
        }
        return arrowPathAndTail;
    }

    public static boolean hasBackgroundFill(EscherContainerRecord escherContainerRecord) {
        int i;
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), 447);
        if (escherSimpleProperty == null) {
            i = 0;
        } else {
            i = escherSimpleProperty.getPropertyValue();
        }
        if ((i & 16) != 0) {
            return true;
        }
        return false;
    }

    public static int getFillType(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), 384);
        if (escherSimpleProperty == null) {
            return 0;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    public static int getFillAngle(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), 395);
        if (escherSimpleProperty != null) {
            return (escherSimpleProperty.getPropertyValue() >> 16) % 360;
        }
        return 0;
    }

    public static int getFillFocus(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), 396);
        if (escherSimpleProperty != null) {
            return escherSimpleProperty.getPropertyValue();
        }
        return 0;
    }

    public static boolean isShaderPreset(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), 406);
        return escherSimpleProperty != null && escherSimpleProperty.getPropertyValue() > 0;
    }

    public static int[] getShaderColors(EscherContainerRecord escherContainerRecord) {
        int i;
        if (!isShaderPreset(escherContainerRecord)) {
            return null;
        }
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        EscherArrayProperty escherArrayProperty = (EscherArrayProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 407);
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 447);
        if (escherSimpleProperty == null) {
            i = 0;
        } else {
            i = escherSimpleProperty.getPropertyValue();
        }
        if (escherArrayProperty == null) {
            return null;
        }
        int numberOfElementsInArray = escherArrayProperty.getNumberOfElementsInArray();
        int[] iArr = new int[numberOfElementsInArray];
        for (int i2 = 0; i2 < numberOfElementsInArray; i2++) {
            byte[] element = escherArrayProperty.getElement(i2);
            if (element.length == 8) {
                if ((i & 16) == 0) {
                    iArr[i2] = -1;
                } else {
                    iArr[i2] = ColorUtil.rgb(element[0], element[1], element[2]);
                }
            }
        }
        return iArr;
    }

    public static float[] getShaderPositions(EscherContainerRecord escherContainerRecord) {
        EscherArrayProperty escherArrayProperty;
        if (!isShaderPreset(escherContainerRecord) || (escherArrayProperty = (EscherArrayProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), 407)) == null) {
            return null;
        }
        int numberOfElementsInArray = escherArrayProperty.getNumberOfElementsInArray();
        float[] fArr = new float[numberOfElementsInArray];
        for (int i = 0; i < numberOfElementsInArray; i++) {
            byte[] element = escherArrayProperty.getElement(i);
            if (element.length == 8) {
                fArr[i] = ((float) LittleEndian.getInt(element, 4)) / 65536.0f;
            }
        }
        return fArr;
    }

    public static Color getFillbackColor(EscherContainerRecord escherContainerRecord, Object obj, int i) {
        int i2;
        int i3;
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 387);
        EscherSimpleProperty escherSimpleProperty2 = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 447);
        EscherSimpleProperty escherSimpleProperty3 = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 388);
        if (escherSimpleProperty2 == null) {
            i2 = 0;
        } else {
            i2 = escherSimpleProperty2.getPropertyValue();
        }
        if (escherSimpleProperty3 == null) {
            i3 = 255;
        } else {
            i3 = (escherSimpleProperty3.getPropertyValue() >> 8) & 255;
        }
        if (escherSimpleProperty != null && ((i2 & 16) != 0 || i2 == 0)) {
            return new Color(getColorByIndex(obj, escherSimpleProperty.getPropertyValue(), i, false), i3);
        }
        if (i2 == 0 && i == 1) {
            return new Color(255, 255, 255);
        }
        return null;
    }

    public static int getRadialGradientPositionType(EscherContainerRecord escherContainerRecord) {
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 397);
        EscherSimpleProperty escherSimpleProperty2 = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 398);
        EscherSimpleProperty escherSimpleProperty3 = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 399);
        EscherSimpleProperty escherSimpleProperty4 = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, (int) EMFConstants.FW_NORMAL);
        if (escherSimpleProperty != null && escherSimpleProperty.getPropertyValue() == 65536 && escherSimpleProperty3 != null && escherSimpleProperty3.getPropertyValue() == 65536 && escherSimpleProperty2 != null && escherSimpleProperty2.getPropertyValue() == 65536 && escherSimpleProperty4 != null && escherSimpleProperty4.getPropertyValue() == 65536) {
            return 3;
        }
        if (escherSimpleProperty != null && escherSimpleProperty.getPropertyValue() == 32768 && escherSimpleProperty3 != null && escherSimpleProperty3.getPropertyValue() == 32768 && escherSimpleProperty2 != null && escherSimpleProperty2.getPropertyValue() == 32768 && escherSimpleProperty4 != null && escherSimpleProperty4.getPropertyValue() == 32768) {
            return 4;
        }
        if (escherSimpleProperty == null || escherSimpleProperty.getPropertyValue() != 65536 || escherSimpleProperty3 == null || escherSimpleProperty3.getPropertyValue() != 65536) {
            return (escherSimpleProperty2 == null || escherSimpleProperty2.getPropertyValue() != 65536 || escherSimpleProperty4 == null || escherSimpleProperty4.getPropertyValue() != 65536) ? 0 : 2;
        }
        return 1;
    }

    public static int getRotation(EscherContainerRecord escherContainerRecord) {
        return (getEscherProperty(escherContainerRecord, 4) >> 16) % 360;
    }

    public static boolean getFlipHorizontal(EscherContainerRecord escherContainerRecord) {
        return (((EscherSpRecord) escherContainerRecord.getChildById(EscherSpRecord.RECORD_ID)).getFlags() & 64) != 0;
    }

    public static boolean getFlipVertical(EscherContainerRecord escherContainerRecord) {
        return (((EscherSpRecord) escherContainerRecord.getChildById(EscherSpRecord.RECORD_ID)).getFlags() & 128) != 0;
    }

    public static Float[] getAdjustmentValue(EscherContainerRecord escherContainerRecord) {
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        if (escherOptRecord != null) {
            HashMap hashMap = new HashMap();
            int i = 0;
            for (int i2 = 0; i2 < 10; i2++) {
                EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, i2 + TIFFConstants.TIFFTAG_CLEANFAXDATA);
                if (escherSimpleProperty != null) {
                    hashMap.put(Integer.valueOf(i2), Integer.valueOf(escherSimpleProperty.getPropertyValue()));
                    if (i2 > i) {
                        i = i2;
                    }
                }
            }
            if (hashMap.size() > 0) {
                Float[] fArr = new Float[(i + 1)];
                for (int i3 = 0; i3 <= i; i3++) {
                    Integer num = (Integer) hashMap.get(Integer.valueOf(i3));
                    if (num != null) {
                        fArr[i3] = Float.valueOf(((float) num.intValue()) / 21600.0f);
                    }
                }
                return fArr;
            }
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:0x0309  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x02da  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Path[] getFreeformPath(com.app.office.fc.ddf.EscherContainerRecord r26, com.app.office.java.awt.Rectangle r27, android.graphics.PointF r28, byte r29, android.graphics.PointF r30, byte r31) {
        /*
            r0 = r27
            r1 = r29
            r2 = r31
            r3 = -4085(0xfffffffffffff00b, float:NaN)
            r4 = r26
            com.app.office.fc.ddf.EscherRecord r3 = getEscherChild(r4, r3)
            com.app.office.fc.ddf.EscherOptRecord r3 = (com.app.office.fc.ddf.EscherOptRecord) r3
            com.app.office.fc.ddf.EscherSimpleProperty r4 = new com.app.office.fc.ddf.EscherSimpleProperty
            r5 = 324(0x144, float:4.54E-43)
            r6 = 4
            r4.<init>(r5, r6)
            r3.addEscherProperty(r4)
            r4 = 16709(0x4145, float:2.3414E-41)
            com.app.office.fc.ddf.EscherProperty r4 = getEscherProperty((com.app.office.fc.ddf.AbstractEscherOptRecord) r3, (int) r4)
            com.app.office.fc.ddf.EscherArrayProperty r4 = (com.app.office.fc.ddf.EscherArrayProperty) r4
            if (r4 != 0) goto L_0x002d
            r4 = 325(0x145, float:4.55E-43)
            com.app.office.fc.ddf.EscherProperty r4 = getEscherProperty((com.app.office.fc.ddf.AbstractEscherOptRecord) r3, (int) r4)
            com.app.office.fc.ddf.EscherArrayProperty r4 = (com.app.office.fc.ddf.EscherArrayProperty) r4
        L_0x002d:
            r5 = 16710(0x4146, float:2.3416E-41)
            com.app.office.fc.ddf.EscherProperty r5 = getEscherProperty((com.app.office.fc.ddf.AbstractEscherOptRecord) r3, (int) r5)
            com.app.office.fc.ddf.EscherArrayProperty r5 = (com.app.office.fc.ddf.EscherArrayProperty) r5
            if (r5 != 0) goto L_0x003f
            r5 = 326(0x146, float:4.57E-43)
            com.app.office.fc.ddf.EscherProperty r5 = getEscherProperty((com.app.office.fc.ddf.AbstractEscherOptRecord) r3, (int) r5)
            com.app.office.fc.ddf.EscherArrayProperty r5 = (com.app.office.fc.ddf.EscherArrayProperty) r5
        L_0x003f:
            r7 = 0
            if (r4 != 0) goto L_0x0043
            return r7
        L_0x0043:
            if (r5 != 0) goto L_0x0046
            return r7
        L_0x0046:
            r7 = 322(0x142, float:4.51E-43)
            com.app.office.fc.ddf.EscherProperty r7 = getEscherProperty((com.app.office.fc.ddf.AbstractEscherOptRecord) r3, (int) r7)
            com.app.office.fc.ddf.EscherSimpleProperty r7 = (com.app.office.fc.ddf.EscherSimpleProperty) r7
            r8 = 323(0x143, float:4.53E-43)
            com.app.office.fc.ddf.EscherProperty r8 = getEscherProperty((com.app.office.fc.ddf.AbstractEscherOptRecord) r3, (int) r8)
            com.app.office.fc.ddf.EscherSimpleProperty r8 = (com.app.office.fc.ddf.EscherSimpleProperty) r8
            r9 = 0
            if (r7 == 0) goto L_0x00de
            int r10 = r7.getPropertyValue()
            r11 = 1000(0x3e8, float:1.401E-42)
            if (r10 != r11) goto L_0x00de
            if (r8 == 0) goto L_0x00de
            int r10 = r8.getPropertyValue()
            if (r10 != r11) goto L_0x00de
            r1 = 327(0x147, float:4.58E-43)
            com.app.office.fc.ddf.EscherProperty r1 = getEscherProperty((com.app.office.fc.ddf.AbstractEscherOptRecord) r3, (int) r1)
            com.app.office.fc.ddf.EscherSimpleProperty r1 = (com.app.office.fc.ddf.EscherSimpleProperty) r1
            r2 = 1148846080(0x447a0000, float:1000.0)
            if (r1 == 0) goto L_0x007c
            int r1 = r1.getPropertyValue()
            float r1 = (float) r1
            float r1 = r1 / r2
            goto L_0x007d
        L_0x007c:
            r1 = 0
        L_0x007d:
            r4 = 329(0x149, float:4.61E-43)
            com.app.office.fc.ddf.EscherProperty r3 = getEscherProperty((com.app.office.fc.ddf.AbstractEscherOptRecord) r3, (int) r4)
            com.app.office.fc.ddf.EscherSimpleProperty r3 = (com.app.office.fc.ddf.EscherSimpleProperty) r3
            if (r3 == 0) goto L_0x008e
            int r3 = r3.getPropertyValue()
            float r3 = (float) r3
            float r3 = r3 / r2
            goto L_0x008f
        L_0x008e:
            r3 = 0
        L_0x008f:
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            android.graphics.Path r4 = new android.graphics.Path
            r4.<init>()
            r4.moveTo(r9, r9)
            int r5 = r0.width
            float r5 = (float) r5
            r4.lineTo(r5, r9)
            int r5 = r0.width
            float r5 = (float) r5
            int r6 = r0.height
            float r6 = (float) r6
            float r6 = r6 * r3
            r4.lineTo(r5, r6)
            int r5 = r0.width
            float r5 = (float) r5
            float r5 = r5 * r1
            int r6 = r0.height
            float r6 = (float) r6
            float r6 = r6 * r3
            r4.lineTo(r5, r6)
            int r3 = r0.width
            float r3 = (float) r3
            float r3 = r3 * r1
            int r1 = r0.height
            float r1 = (float) r1
            r4.lineTo(r3, r1)
            int r0 = r0.height
            float r0 = (float) r0
            r4.lineTo(r9, r0)
            r4.close()
            r2.add(r4)
            int r0 = r2.size()
            android.graphics.Path[] r0 = new android.graphics.Path[r0]
            java.lang.Object[] r0 = r2.toArray(r0)
            android.graphics.Path[] r0 = (android.graphics.Path[]) r0
            return r0
        L_0x00de:
            if (r7 == 0) goto L_0x00e6
            int r3 = r7.getPropertyValue()
            float r3 = (float) r3
            goto L_0x00e7
        L_0x00e6:
            r3 = 0
        L_0x00e7:
            if (r8 == 0) goto L_0x00ef
            int r7 = r8.getPropertyValue()
            float r7 = (float) r7
            goto L_0x00f0
        L_0x00ef:
            r7 = 0
        L_0x00f0:
            android.graphics.Matrix r8 = new android.graphics.Matrix
            r8.<init>()
            int r10 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r10 <= 0) goto L_0x0108
            int r9 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r9 <= 0) goto L_0x0108
            int r9 = r0.width
            float r9 = (float) r9
            float r9 = r9 / r3
            int r10 = r0.height
            float r10 = (float) r10
            float r10 = r10 / r7
            r8.postScale(r9, r10)
        L_0x0108:
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            int r10 = r4.getNumberOfElementsInArray()
            int r11 = r5.getNumberOfElementsInArray()
            android.graphics.Path r15 = new android.graphics.Path
            r15.<init>()
            r13 = r28
            r12 = r30
            r6 = 0
            r14 = 0
        L_0x0120:
            if (r6 >= r11) goto L_0x0432
            if (r14 > r10) goto L_0x0432
            r19 = r11
            byte[] r11 = r5.getElement(r6)
            r20 = r5
            if (r14 != 0) goto L_0x0180
            if (r13 == 0) goto L_0x0180
            byte[] r5 = SEGMENTINFO_MOVETO
            boolean r5 = java.util.Arrays.equals(r11, r5)
            if (r5 == 0) goto L_0x02cb
            int r5 = r14 + 1
            byte[] r11 = r4.getElement(r14)
            int r14 = r11.length
            r17 = r5
            r5 = 8
            if (r14 != r5) goto L_0x015a
            r5 = 0
            int r14 = com.app.office.fc.util.LittleEndian.getInt(r11, r5)
            r5 = 4
            int r11 = com.app.office.fc.util.LittleEndian.getInt(r11, r5)
            float r5 = (float) r14
            float r11 = (float) r11
            float r14 = r13.x
            float r13 = r13.y
            android.graphics.PointF r5 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getReferencedPosition(r5, r11, r14, r13, r1)
            goto L_0x016e
        L_0x015a:
            r5 = 0
            short r14 = com.app.office.fc.util.LittleEndian.getShort(r11, r5)
            r5 = 2
            short r5 = com.app.office.fc.util.LittleEndian.getShort(r11, r5)
            float r11 = (float) r14
            float r5 = (float) r5
            float r14 = r13.x
            float r13 = r13.y
            android.graphics.PointF r5 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getReferencedPosition(r11, r5, r14, r13, r1)
        L_0x016e:
            float r11 = r5.x
            float r13 = r5.y
            r15.moveTo(r11, r13)
            r13 = r5
            r23 = r8
            r22 = r9
            r2 = r15
            r14 = r17
            r1 = 0
            goto L_0x0306
        L_0x0180:
            if (r12 == 0) goto L_0x02cb
            byte[] r5 = SEGMENTINFO_CUBICTO
            boolean r5 = java.util.Arrays.equals(r11, r5)
            if (r5 != 0) goto L_0x01a2
            byte[] r5 = SEGMENTINFO_CUBICTO1
            boolean r5 = java.util.Arrays.equals(r11, r5)
            if (r5 != 0) goto L_0x01a2
            byte[] r5 = SEGMENTINFO_CUBICTO2
            boolean r5 = java.util.Arrays.equals(r11, r5)
            if (r5 != 0) goto L_0x01a2
            byte[] r5 = SEGMENTINFO_ESCAPE2
            boolean r5 = java.util.Arrays.equals(r11, r5)
            if (r5 == 0) goto L_0x0252
        L_0x01a2:
            int r5 = r10 + -3
            if (r14 != r5) goto L_0x0252
            int r5 = r14 + 3
            if (r5 > r10) goto L_0x02cb
            int r5 = r14 + 1
            byte[] r11 = r4.getElement(r14)
            int r14 = r5 + 1
            byte[] r5 = r4.getElement(r5)
            int r21 = r14 + 1
            byte[] r14 = r4.getElement(r14)
            int r1 = r11.length
            r17 = r13
            r13 = 8
            if (r1 != r13) goto L_0x020a
            int r1 = r5.length
            if (r1 != r13) goto L_0x020a
            int r1 = r14.length
            if (r1 != r13) goto L_0x020a
            r1 = 0
            int r13 = com.app.office.fc.util.LittleEndian.getInt(r11, r1)
            r22 = r9
            r9 = 4
            int r11 = com.app.office.fc.util.LittleEndian.getInt(r11, r9)
            r23 = r8
            int r8 = com.app.office.fc.util.LittleEndian.getInt(r5, r1)
            int r5 = com.app.office.fc.util.LittleEndian.getInt(r5, r9)
            int r0 = com.app.office.fc.util.LittleEndian.getInt(r14, r1)
            int r14 = com.app.office.fc.util.LittleEndian.getInt(r14, r9)
            float r0 = (float) r0
            float r9 = (float) r14
            float r14 = r12.x
            float r12 = r12.y
            android.graphics.PointF r0 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getReferencedPosition(r0, r9, r14, r12, r2)
            float r13 = (float) r13
            float r14 = (float) r11
            float r8 = (float) r8
            float r5 = (float) r5
            float r9 = r0.x
            float r11 = r0.y
            r12 = r15
            r24 = r17
            r25 = r15
            r15 = r8
            r16 = r5
            r17 = r9
            r18 = r11
            r12.cubicTo(r13, r14, r15, r16, r17, r18)
            r12 = r0
            goto L_0x024a
        L_0x020a:
            r23 = r8
            r22 = r9
            r25 = r15
            r24 = r17
            r1 = 0
            short r0 = com.app.office.fc.util.LittleEndian.getShort(r11, r1)
            r8 = 2
            short r9 = com.app.office.fc.util.LittleEndian.getShort(r11, r8)
            short r11 = com.app.office.fc.util.LittleEndian.getShort(r5, r1)
            short r5 = com.app.office.fc.util.LittleEndian.getShort(r5, r8)
            short r13 = com.app.office.fc.util.LittleEndian.getShort(r14, r1)
            short r8 = com.app.office.fc.util.LittleEndian.getShort(r14, r8)
            float r13 = (float) r13
            float r8 = (float) r8
            float r14 = r12.x
            float r12 = r12.y
            android.graphics.PointF r8 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getReferencedPosition(r13, r8, r14, r12, r2)
            float r13 = (float) r0
            float r14 = (float) r9
            float r15 = (float) r11
            float r0 = (float) r5
            float r5 = r8.x
            float r9 = r8.y
            r12 = r25
            r16 = r0
            r17 = r5
            r18 = r9
            r12.cubicTo(r13, r14, r15, r16, r17, r18)
            r12 = r8
        L_0x024a:
            r14 = r21
            r13 = r24
            r2 = r25
            goto L_0x0306
        L_0x0252:
            r23 = r8
            r22 = r9
            r24 = r13
            r25 = r15
            r1 = 0
            byte[] r0 = SEGMENTINFO_LINETO
            boolean r0 = java.util.Arrays.equals(r11, r0)
            if (r0 != 0) goto L_0x027b
            byte[] r0 = SEGMENTINFO_LINETO2
            boolean r0 = java.util.Arrays.equals(r11, r0)
            if (r0 != 0) goto L_0x027b
            byte[] r0 = SEGMENTINFO_ESCAPE
            boolean r0 = java.util.Arrays.equals(r11, r0)
            if (r0 != 0) goto L_0x027b
            byte[] r0 = SEGMENTINFO_ESCAPE1
            boolean r0 = java.util.Arrays.equals(r11, r0)
            if (r0 == 0) goto L_0x02c8
        L_0x027b:
            int r0 = r10 + -1
            if (r14 != r0) goto L_0x02c8
            int r0 = r14 + 1
            if (r0 > r10) goto L_0x02c8
            byte[] r5 = r4.getElement(r14)
            int r8 = r5.length
            r9 = 8
            if (r8 != r9) goto L_0x02a9
            int r8 = com.app.office.fc.util.LittleEndian.getInt(r5, r1)
            r9 = 4
            int r5 = com.app.office.fc.util.LittleEndian.getInt(r5, r9)
            float r8 = (float) r8
            float r5 = (float) r5
            float r9 = r12.x
            float r11 = r12.y
            android.graphics.PointF r5 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getReferencedPosition(r8, r5, r9, r11, r2)
            float r8 = r5.x
            float r9 = r5.y
            r15 = r25
            r15.lineTo(r8, r9)
            goto L_0x02c5
        L_0x02a9:
            r15 = r25
            short r8 = com.app.office.fc.util.LittleEndian.getShort(r5, r1)
            r9 = 2
            short r5 = com.app.office.fc.util.LittleEndian.getShort(r5, r9)
            float r8 = (float) r8
            float r5 = (float) r5
            float r9 = r12.x
            float r11 = r12.y
            android.graphics.PointF r5 = com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder.getReferencedPosition(r8, r5, r9, r11, r2)
            float r8 = r5.x
            float r9 = r5.y
            r15.lineTo(r8, r9)
        L_0x02c5:
            r14 = r0
            r12 = r5
            goto L_0x0303
        L_0x02c8:
            r15 = r25
            goto L_0x02d2
        L_0x02cb:
            r23 = r8
            r22 = r9
            r24 = r13
            r1 = 0
        L_0x02d2:
            byte[] r0 = SEGMENTINFO_MOVETO
            boolean r0 = java.util.Arrays.equals(r11, r0)
            if (r0 == 0) goto L_0x0309
            int r0 = r14 + 1
            byte[] r5 = r4.getElement(r14)
            int r8 = r5.length
            r9 = 8
            if (r8 != r9) goto L_0x02f4
            int r8 = com.app.office.fc.util.LittleEndian.getInt(r5, r1)
            r9 = 4
            int r5 = com.app.office.fc.util.LittleEndian.getInt(r5, r9)
            float r8 = (float) r8
            float r5 = (float) r5
            r15.moveTo(r8, r5)
            goto L_0x0302
        L_0x02f4:
            short r8 = com.app.office.fc.util.LittleEndian.getShort(r5, r1)
            r9 = 2
            short r5 = com.app.office.fc.util.LittleEndian.getShort(r5, r9)
            float r8 = (float) r8
            float r5 = (float) r5
            r15.moveTo(r8, r5)
        L_0x0302:
            r14 = r0
        L_0x0303:
            r2 = r15
            r13 = r24
        L_0x0306:
            r11 = 4
            goto L_0x041f
        L_0x0309:
            byte[] r0 = SEGMENTINFO_CUBICTO
            boolean r0 = java.util.Arrays.equals(r11, r0)
            if (r0 != 0) goto L_0x038c
            byte[] r0 = SEGMENTINFO_CUBICTO1
            boolean r0 = java.util.Arrays.equals(r11, r0)
            if (r0 != 0) goto L_0x038c
            byte[] r0 = SEGMENTINFO_CUBICTO2
            boolean r0 = java.util.Arrays.equals(r11, r0)
            if (r0 != 0) goto L_0x038c
            byte[] r0 = SEGMENTINFO_ESCAPE2
            boolean r0 = java.util.Arrays.equals(r11, r0)
            if (r0 == 0) goto L_0x032a
            goto L_0x038c
        L_0x032a:
            byte[] r0 = SEGMENTINFO_LINETO
            boolean r0 = java.util.Arrays.equals(r11, r0)
            if (r0 != 0) goto L_0x0360
            byte[] r0 = SEGMENTINFO_LINETO2
            boolean r0 = java.util.Arrays.equals(r11, r0)
            if (r0 != 0) goto L_0x0360
            byte[] r0 = SEGMENTINFO_ESCAPE
            boolean r0 = java.util.Arrays.equals(r11, r0)
            if (r0 != 0) goto L_0x0360
            byte[] r0 = SEGMENTINFO_ESCAPE1
            boolean r0 = java.util.Arrays.equals(r11, r0)
            if (r0 == 0) goto L_0x034b
            goto L_0x0360
        L_0x034b:
            byte[] r0 = SEGMENTINFO_CLOSE
            boolean r0 = java.util.Arrays.equals(r11, r0)
            if (r0 == 0) goto L_0x0358
            r15.close()
            goto L_0x0417
        L_0x0358:
            byte[] r0 = SEGMENTINFO_END
            boolean r0 = java.util.Arrays.equals(r11, r0)
            goto L_0x0417
        L_0x0360:
            int r0 = r14 + 1
            if (r0 > r10) goto L_0x0417
            byte[] r5 = r4.getElement(r14)
            int r8 = r5.length
            r9 = 8
            if (r8 != r9) goto L_0x037c
            int r8 = com.app.office.fc.util.LittleEndian.getInt(r5, r1)
            r9 = 4
            int r5 = com.app.office.fc.util.LittleEndian.getInt(r5, r9)
            float r8 = (float) r8
            float r5 = (float) r5
            r15.lineTo(r8, r5)
            goto L_0x0302
        L_0x037c:
            short r8 = com.app.office.fc.util.LittleEndian.getShort(r5, r1)
            r9 = 2
            short r5 = com.app.office.fc.util.LittleEndian.getShort(r5, r9)
            float r8 = (float) r8
            float r5 = (float) r5
            r15.lineTo(r8, r5)
            goto L_0x0302
        L_0x038c:
            int r0 = r14 + 3
            if (r0 > r10) goto L_0x0417
            int r0 = r14 + 1
            byte[] r5 = r4.getElement(r14)
            int r8 = r0 + 1
            byte[] r0 = r4.getElement(r0)
            int r9 = r8 + 1
            byte[] r8 = r4.getElement(r8)
            int r11 = r5.length
            r13 = 8
            if (r11 != r13) goto L_0x03e2
            int r11 = r0.length
            if (r11 != r13) goto L_0x03e2
            int r11 = r8.length
            if (r11 != r13) goto L_0x03e2
            int r11 = com.app.office.fc.util.LittleEndian.getInt(r5, r1)
            r14 = 4
            int r5 = com.app.office.fc.util.LittleEndian.getInt(r5, r14)
            int r13 = com.app.office.fc.util.LittleEndian.getInt(r0, r1)
            int r0 = com.app.office.fc.util.LittleEndian.getInt(r0, r14)
            int r2 = com.app.office.fc.util.LittleEndian.getInt(r8, r1)
            int r8 = com.app.office.fc.util.LittleEndian.getInt(r8, r14)
            float r11 = (float) r11
            float r5 = (float) r5
            float r13 = (float) r13
            float r0 = (float) r0
            float r2 = (float) r2
            float r8 = (float) r8
            r21 = r12
            r12 = r15
            r16 = r13
            r13 = r11
            r11 = 4
            r14 = r5
            r5 = r15
            r15 = r16
            r16 = r0
            r17 = r2
            r18 = r8
            r12.cubicTo(r13, r14, r15, r16, r17, r18)
            r2 = r5
            goto L_0x0415
        L_0x03e2:
            r21 = r12
            r2 = r15
            r11 = 4
            short r12 = com.app.office.fc.util.LittleEndian.getShort(r5, r1)
            r13 = 2
            short r5 = com.app.office.fc.util.LittleEndian.getShort(r5, r13)
            short r14 = com.app.office.fc.util.LittleEndian.getShort(r0, r1)
            short r0 = com.app.office.fc.util.LittleEndian.getShort(r0, r13)
            short r15 = com.app.office.fc.util.LittleEndian.getShort(r8, r1)
            short r8 = com.app.office.fc.util.LittleEndian.getShort(r8, r13)
            float r13 = (float) r12
            float r5 = (float) r5
            float r14 = (float) r14
            float r0 = (float) r0
            float r15 = (float) r15
            float r8 = (float) r8
            r12 = r2
            r16 = r14
            r14 = r5
            r5 = r15
            r15 = r16
            r16 = r0
            r17 = r5
            r18 = r8
            r12.cubicTo(r13, r14, r15, r16, r17, r18)
        L_0x0415:
            r14 = r9
            goto L_0x041b
        L_0x0417:
            r21 = r12
            r2 = r15
            r11 = 4
        L_0x041b:
            r12 = r21
            r13 = r24
        L_0x041f:
            int r6 = r6 + 1
            r0 = r27
            r1 = r29
            r15 = r2
            r11 = r19
            r5 = r20
            r9 = r22
            r8 = r23
            r2 = r31
            goto L_0x0120
        L_0x0432:
            r23 = r8
            r22 = r9
            r2 = r15
            r1 = 0
            float r0 = java.lang.Math.abs(r3)
            r3 = 1065353216(0x3f800000, float:1.0)
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 < 0) goto L_0x044e
            float r0 = java.lang.Math.abs(r7)
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 >= 0) goto L_0x044b
            goto L_0x044e
        L_0x044b:
            r0 = r23
            goto L_0x046d
        L_0x044e:
            android.graphics.RectF r0 = new android.graphics.RectF
            r0.<init>()
            r2.computeBounds(r0, r1)
            r1 = r27
            int r3 = r1.width
            float r3 = (float) r3
            float r4 = r0.width()
            float r3 = r3 / r4
            int r1 = r1.height
            float r1 = (float) r1
            float r0 = r0.height()
            float r1 = r1 / r0
            r0 = r23
            r0.postScale(r3, r1)
        L_0x046d:
            r2.transform(r0)
            r0 = r22
            r0.add(r2)
            int r1 = r0.size()
            android.graphics.Path[] r1 = new android.graphics.Path[r1]
            java.lang.Object[] r0 = r0.toArray(r1)
            android.graphics.Path[] r0 = (android.graphics.Path[]) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ShapeKit.getFreeformPath(com.app.office.fc.ddf.EscherContainerRecord, com.app.office.java.awt.Rectangle, android.graphics.PointF, byte, android.graphics.PointF, byte):android.graphics.Path[]");
    }

    public static int getColorByIndex(Object obj, int i, int i2, boolean z) {
        int i3;
        int i4;
        if (i >= 268435952) {
            return i2 == 2 ? -1 : -16777216;
        }
        if (i >= 134217728) {
            int i5 = i % 134217728;
            if (i2 == 2) {
                Sheet sheet = (Sheet) obj;
                if (sheet == null || i5 < 0 || i5 > 7) {
                    return i;
                }
                ColorSchemeAtom colorScheme = sheet.getColorScheme();
                if (colorScheme != null) {
                    i5 = colorScheme.getColor(i5);
                }
                if (i5 > 16777215) {
                    return i;
                }
                i4 = (((i5 & 255) & 255) << 16) | -16777216 | ((((i5 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8) & 255) << 8);
                i3 = ((i5 & 16711680) >> 16) & 255;
            } else {
                if (i2 != 1) {
                    switch (i5) {
                        case 1:
                            break;
                        case 2:
                        case 9:
                            return -16776961;
                        case 3:
                            return -16711681;
                        case 4:
                        case 11:
                            return -16711936;
                        case 5:
                        case 12:
                            return -65281;
                        case 6:
                        case 13:
                            return SupportMenu.CATEGORY_MASK;
                        case 7:
                        case 14:
                            return InputDeviceCompat.SOURCE_ANY;
                        case 8:
                            break;
                        case 10:
                            return -12303292;
                        case 15:
                            return -7829368;
                        case 16:
                            return -3355444;
                        default:
                            return i;
                    }
                } else if (i <= 134217793) {
                    if (i5 >= 64) {
                        i5 = (i5 % 64) + 8;
                    }
                    AWorkbook aWorkbook = (AWorkbook) obj;
                    return aWorkbook != null ? aWorkbook.getColor(i5, z) : i;
                } else if (z) {
                    return -16777216;
                }
                return -1;
            }
        } else if (i > 16777215) {
            return i;
        } else {
            i4 = (((i & 255) & 255) << 16) | -16777216 | ((((i & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8) & 255) << 8);
            i3 = ((i & 16711680) >> 16) & 255;
        }
        return (i3 << 0) | i4;
    }

    public static Color getLineColor(EscherContainerRecord escherContainerRecord, Object obj, int i) {
        int i2;
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 448);
        EscherSimpleProperty escherSimpleProperty2 = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, (int) FrameMetricsAggregator.EVERY_DURATION);
        int i3 = 0;
        if (escherSimpleProperty2 == null) {
            i2 = 0;
        } else {
            i2 = escherSimpleProperty2.getPropertyValue();
        }
        if ((i2 & 8) != 0) {
            if (escherSimpleProperty != null) {
                return new Color(getColorByIndex(obj, escherSimpleProperty.getPropertyValue(), i, true));
            }
            return new Color(0, 0, 0);
        } else if (i == 2) {
            return null;
        } else {
            if (escherSimpleProperty == null && i != 0) {
                return null;
            }
            if (escherSimpleProperty != null) {
                i3 = escherSimpleProperty.getPropertyValue();
            }
            if (i3 >= 134217728) {
                i3 = getColorByIndex(obj, i3 % 134217728, i, true);
            }
            Color color = new Color(i3, true);
            return new Color(color.getBlue(), color.getGreen(), color.getRed());
        }
    }

    public static Color getForegroundColor(EscherContainerRecord escherContainerRecord, Object obj, int i) {
        int i2;
        int i3;
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(escherContainerRecord, -4085);
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 385);
        EscherSimpleProperty escherSimpleProperty2 = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 447);
        EscherSimpleProperty escherSimpleProperty3 = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 386);
        EscherComplexProperty escherComplexProperty = (EscherComplexProperty) getEscherProperty((AbstractEscherOptRecord) escherOptRecord, (int) MetaDo.META_SETRELABS);
        if (escherSimpleProperty2 == null) {
            i2 = 0;
        } else {
            i2 = escherSimpleProperty2.getPropertyValue();
        }
        if (escherSimpleProperty3 == null) {
            i3 = 255;
        } else {
            i3 = (escherSimpleProperty3.getPropertyValue() * 255) / 65536;
        }
        if (escherSimpleProperty != null && ((i2 & 16) != 0 || i2 == 0)) {
            return new Color(getColorByIndex(obj, escherSimpleProperty.getPropertyValue(), i, false), i3);
        }
        if ((i2 & 16) == 0 || escherComplexProperty != null) {
            return null;
        }
        return new Color(255, 255, 255, i3);
    }

    public static boolean getGroupFlipHorizontal(EscherContainerRecord escherContainerRecord) {
        EscherSpRecord escherSpRecord;
        EscherContainerRecord escherContainerRecord2 = (EscherContainerRecord) escherContainerRecord.getChild(0);
        if (escherContainerRecord2 == null || (escherSpRecord = (EscherSpRecord) escherContainerRecord2.getChildById(EscherSpRecord.RECORD_ID)) == null || (escherSpRecord.getFlags() & 64) == 0) {
            return false;
        }
        return true;
    }

    public static boolean getGroupFlipVertical(EscherContainerRecord escherContainerRecord) {
        EscherSpRecord escherSpRecord;
        EscherContainerRecord escherContainerRecord2 = (EscherContainerRecord) escherContainerRecord.getChild(0);
        if (escherContainerRecord2 == null || (escherSpRecord = (EscherSpRecord) escherContainerRecord2.getChildById(EscherSpRecord.RECORD_ID)) == null || (escherSpRecord.getFlags() & 128) == 0) {
            return false;
        }
        return true;
    }

    public static int getGroupRotation(EscherContainerRecord escherContainerRecord) {
        EscherContainerRecord escherContainerRecord2 = (EscherContainerRecord) escherContainerRecord.getChild(0);
        if (escherContainerRecord2 != null) {
            return (getEscherProperty(escherContainerRecord2, 4) >> 16) % 360;
        }
        return 0;
    }

    public static int getPosition_H(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherTertiaryOptRecord) getEscherChild(escherContainerRecord, -3806), 911);
        if (escherSimpleProperty != null) {
            return escherSimpleProperty.getPropertyValue();
        }
        return 0;
    }

    public static int getPositionRelTo_H(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherTertiaryOptRecord) getEscherChild(escherContainerRecord, -3806), 912);
        if (escherSimpleProperty != null) {
            return escherSimpleProperty.getPropertyValue();
        }
        return 2;
    }

    public static int getPosition_V(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherTertiaryOptRecord) getEscherChild(escherContainerRecord, -3806), 913);
        if (escherSimpleProperty != null) {
            return escherSimpleProperty.getPropertyValue();
        }
        return 0;
    }

    public static int getPositionRelTo_V(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherTertiaryOptRecord) getEscherChild(escherContainerRecord, -3806), 914);
        if (escherSimpleProperty != null) {
            return escherSimpleProperty.getPropertyValue();
        }
        return 2;
    }

    public static String getShapeName(EscherContainerRecord escherContainerRecord) {
        EscherComplexProperty escherComplexProperty = (EscherComplexProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), 896);
        if (escherComplexProperty != null) {
            return StringUtil.getFromUnicodeLE(escherComplexProperty.getComplexData());
        }
        return null;
    }

    public static boolean isTextboxWrapLine(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), 133);
        if (escherSimpleProperty == null || escherSimpleProperty.getPropertyValue() != 2) {
            return true;
        }
        return false;
    }

    public static float getTextboxMarginLeft(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), 129);
        if (escherSimpleProperty != null) {
            return ((float) escherSimpleProperty.getPropertyValue()) / 9525.0f;
        }
        return 9.6f;
    }

    public static float getTextboxMarginTop(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), 130);
        if (escherSimpleProperty != null) {
            return ((float) escherSimpleProperty.getPropertyValue()) / 9525.0f;
        }
        return 4.8f;
    }

    public static float getTextboxMarginRight(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), 131);
        if (escherSimpleProperty != null) {
            return ((float) escherSimpleProperty.getPropertyValue()) / 9525.0f;
        }
        return 9.6f;
    }

    public static float getTextboxMarginBottom(EscherContainerRecord escherContainerRecord) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), 132);
        if (escherSimpleProperty != null) {
            return ((float) escherSimpleProperty.getPropertyValue()) / 9525.0f;
        }
        return 4.8f;
    }

    public static String getUnicodeGeoText(EscherContainerRecord escherContainerRecord) {
        EscherComplexProperty escherComplexProperty = (EscherComplexProperty) getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) getEscherChild(escherContainerRecord, -4085), (int) ShapeTypes.ActionButtonInformation);
        if (escherComplexProperty != null) {
            return StringUtil.getFromUnicodeLE(escherComplexProperty.getComplexData());
        }
        return null;
    }
}
