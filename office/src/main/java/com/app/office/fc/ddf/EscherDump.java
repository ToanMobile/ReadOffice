package com.app.office.fc.ddf;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.FrameMetricsAggregator;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.itextpdf.text.pdf.codec.wmf.MetaDo;
import com.app.office.common.shape.ShapeTypes;
import com.app.office.fc.ShapeKit;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.HexRead;
import com.app.office.fc.util.LittleEndian;
import com.app.office.pg.animate.IAnimation;
import com.app.office.thirdpart.emf.EMFConstants;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.zip.InflaterInputStream;
import kotlin.jvm.internal.ShortCompanionObject;

public final class EscherDump {
    public void dump(byte[] bArr, int i, int i2, PrintStream printStream) {
        DefaultEscherRecordFactory defaultEscherRecordFactory = new DefaultEscherRecordFactory();
        int i3 = i;
        while (i3 < i + i2) {
            EscherRecord createRecord = defaultEscherRecordFactory.createRecord(bArr, i3);
            int fillFields = createRecord.fillFields(bArr, i3, defaultEscherRecordFactory);
            printStream.println(createRecord.toString());
            i3 += fillFields;
        }
    }

    public void dumpOld(long j, InputStream inputStream, PrintStream printStream) throws IOException, LittleEndian.BufferUnderrunException {
        String str;
        long j2;
        InputStream inputStream2 = inputStream;
        PrintStream printStream2 = printStream;
        long j3 = j;
        while (j3 > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            short readShort = LittleEndian.readShort(inputStream);
            short readShort2 = LittleEndian.readShort(inputStream);
            int readInt = LittleEndian.readInt(inputStream);
            j3 -= 8;
            if (readShort2 != -3806) {
                switch (readShort2) {
                    case -4096:
                        str = "MsofbtDggContainer";
                        break;
                    case -4095:
                        str = "MsofbtBstoreContainer";
                        break;
                    case -4094:
                        str = "MsofbtDgContainer";
                        break;
                    case -4093:
                        str = "MsofbtSpgrContainer";
                        break;
                    case -4092:
                        str = "MsofbtSpContainer";
                        break;
                    case -4091:
                        str = "MsofbtSolverContainer";
                        break;
                    case -4090:
                        str = EscherDggRecord.RECORD_DESCRIPTION;
                        break;
                    case -4089:
                        str = EscherBSERecord.RECORD_DESCRIPTION;
                        break;
                    case -4088:
                        str = EscherDgRecord.RECORD_DESCRIPTION;
                        break;
                    case -4087:
                        str = EscherSpgrRecord.RECORD_DESCRIPTION;
                        break;
                    case -4086:
                        str = EscherSpRecord.RECORD_DESCRIPTION;
                        break;
                    case -4085:
                        str = "MsofbtOPT";
                        break;
                    case -4084:
                        str = "MsofbtTextbox";
                        break;
                    case -4083:
                        str = "MsofbtClientTextbox";
                        break;
                    case -4082:
                        str = "MsofbtAnchor";
                        break;
                    case -4081:
                        str = EscherChildAnchorRecord.RECORD_DESCRIPTION;
                        break;
                    case -4080:
                        str = EscherClientAnchorRecord.RECORD_DESCRIPTION;
                        break;
                    case -4079:
                        str = EscherClientDataRecord.RECORD_DESCRIPTION;
                        break;
                    case -4078:
                        str = "MsofbtConnectorRule";
                        break;
                    case -4077:
                        str = "MsofbtAlignRule";
                        break;
                    case -4076:
                        str = "MsofbtArcRule";
                        break;
                    case -4075:
                        str = "MsofbtClientRule";
                        break;
                    case -4074:
                        str = "MsofbtCLSID";
                        break;
                    case -4073:
                        str = "MsofbtCalloutRule";
                        break;
                    default:
                        switch (readShort2) {
                            case -3816:
                                str = "MsofbtRegroupItem";
                                break;
                            case -3815:
                                str = "MsofbtSelection";
                                break;
                            case -3814:
                                str = "MsofbtColorMRU";
                                break;
                            default:
                                switch (readShort2) {
                                    case -3811:
                                        str = "MsofbtDeletedPspl";
                                        break;
                                    case -3810:
                                        str = EscherSplitMenuColorsRecord.RECORD_DESCRIPTION;
                                        break;
                                    case -3809:
                                        str = "MsofbtOleObject";
                                        break;
                                    case -3808:
                                        str = "MsofbtColorScheme";
                                        break;
                                    default:
                                        if (readShort2 < -4072 || readShort2 > -3817) {
                                            if ((readShort & 15) != 15) {
                                                str = "UNKNOWN ID";
                                                break;
                                            } else {
                                                str = "UNKNOWN container";
                                                break;
                                            }
                                        } else {
                                            str = "MsofbtBLIP";
                                            break;
                                        }
                                }
                        }
                }
            } else {
                str = "MsofbtUDefProp";
            }
            stringBuffer.append("  ");
            stringBuffer.append(HexDump.toHex(readShort2));
            stringBuffer.append("  ");
            stringBuffer.append(str);
            stringBuffer.append(" [");
            stringBuffer.append(HexDump.toHex(readShort));
            stringBuffer.append(',');
            stringBuffer.append(HexDump.toHex(readInt));
            stringBuffer.append("]  instance: ");
            stringBuffer.append(HexDump.toHex((short) (readShort >> 4)));
            printStream2.println(stringBuffer.toString());
            if (readShort2 == -4089 && 36 <= j3 && 36 <= readInt) {
                StringBuffer stringBuffer2 = new StringBuffer("    btWin32: ");
                byte read = (byte) inputStream.read();
                stringBuffer2.append(HexDump.toHex(read));
                stringBuffer2.append(getBlipType(read));
                stringBuffer2.append("  btMacOS: ");
                byte read2 = (byte) inputStream.read();
                stringBuffer2.append(HexDump.toHex(read2));
                stringBuffer2.append(getBlipType(read2));
                printStream2.println(stringBuffer2.toString());
                printStream2.println("    rgbUid:");
                HexDump.dump(inputStream2, printStream2, 0, 16);
                printStream2.print("    tag: ");
                outHex(2, inputStream2, printStream2);
                printStream.println();
                printStream2.print("    size: ");
                outHex(4, inputStream2, printStream2);
                printStream.println();
                printStream2.print("    cRef: ");
                outHex(4, inputStream2, printStream2);
                printStream.println();
                printStream2.print("    offs: ");
                outHex(4, inputStream2, printStream2);
                printStream.println();
                printStream2.print("    usage: ");
                outHex(1, inputStream2, printStream2);
                printStream.println();
                printStream2.print("    cbName: ");
                outHex(1, inputStream2, printStream2);
                printStream.println();
                printStream2.print("    unused2: ");
                outHex(1, inputStream2, printStream2);
                printStream.println();
                printStream2.print("    unused3: ");
                outHex(1, inputStream2, printStream2);
                printStream.println();
                j3 -= 36;
                readInt = 0;
            } else if (readShort2 == -4080 && 18 <= j3 && 18 <= readInt) {
                printStream2.print("    Flag: ");
                outHex(2, inputStream2, printStream2);
                printStream.println();
                printStream2.print("    Col1: ");
                outHex(2, inputStream2, printStream2);
                printStream2.print("    dX1: ");
                outHex(2, inputStream2, printStream2);
                printStream2.print("    Row1: ");
                outHex(2, inputStream2, printStream2);
                printStream2.print("    dY1: ");
                outHex(2, inputStream2, printStream2);
                printStream.println();
                printStream2.print("    Col2: ");
                outHex(2, inputStream2, printStream2);
                printStream2.print("    dX2: ");
                outHex(2, inputStream2, printStream2);
                printStream2.print("    Row2: ");
                outHex(2, inputStream2, printStream2);
                printStream2.print("    dY2: ");
                outHex(2, inputStream2, printStream2);
                printStream.println();
                j3 -= 18;
                readInt -= 18;
            } else if (readShort2 == -4085 || readShort2 == -3806) {
                printStream2.println("    PROPID        VALUE");
                int i = 0;
                while (true) {
                    int i2 = i + 6;
                    if (readInt >= i2 && j3 >= ((long) i2)) {
                        short readShort3 = LittleEndian.readShort(inputStream);
                        int readInt2 = LittleEndian.readInt(inputStream);
                        readInt -= 6;
                        j3 -= 6;
                        printStream2.print("    ");
                        printStream2.print(HexDump.toHex(readShort3));
                        printStream2.print(" (");
                        short s = readShort3 & 16383;
                        printStream2.print(" " + s);
                        if ((readShort3 & ShortCompanionObject.MIN_VALUE) == 0) {
                            short s2 = readShort3 & 16384;
                            if (s2 != 0) {
                                printStream2.print(", fBlipID");
                            }
                            printStream2.print(")  ");
                            printStream2.print(HexDump.toHex(readInt2));
                            if (s2 == 0) {
                                printStream2.print(" (");
                                printStream2.print(dec1616(readInt2));
                                printStream2.print(')');
                                printStream2.print(" {" + propName((short) s) + "}");
                            }
                            printStream.println();
                        } else {
                            printStream2.print(", fComplex)  ");
                            printStream2.print(HexDump.toHex(readInt2));
                            printStream2.print(" - Complex prop len");
                            printStream2.println(" {" + propName((short) s) + "}");
                            i += readInt2;
                        }
                    }
                }
                while ((((long) i) & j3) > 0) {
                    int i3 = (int) j3;
                    short s3 = i > i3 ? (short) i3 : (short) i;
                    HexDump.dump(inputStream2, printStream2, 0, (int) s3);
                    i -= s3;
                    readInt -= s3;
                    j3 -= (long) s3;
                }
            } else {
                if (readShort2 == -4078) {
                    printStream2.print("    Connector rule: ");
                    printStream2.print(LittleEndian.readInt(inputStream));
                    printStream2.print("    ShapeID A: ");
                    printStream2.print(LittleEndian.readInt(inputStream));
                    printStream2.print("   ShapeID B: ");
                    printStream2.print(LittleEndian.readInt(inputStream));
                    printStream2.print("    ShapeID connector: ");
                    printStream2.print(LittleEndian.readInt(inputStream));
                    printStream2.print("   Connect pt A: ");
                    printStream2.print(LittleEndian.readInt(inputStream));
                    printStream2.print("   Connect pt B: ");
                    printStream2.println(LittleEndian.readInt(inputStream));
                    readInt -= 24;
                    j2 = 24;
                } else if (readShort2 >= -4072 && readShort2 < -3817) {
                    printStream2.println("    Secondary UID: ");
                    HexDump.dump(inputStream2, printStream2, 0, 16);
                    printStream2.println("    Cache of size: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream2.println("    Boundary top: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream2.println("    Boundary left: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream2.println("    Boundary width: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream2.println("    Boundary height: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream2.println("    X: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream2.println("    Y: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream2.println("    Cache of saved size: " + HexDump.toHex(LittleEndian.readInt(inputStream)));
                    printStream2.println("    Compression Flag: " + HexDump.toHex((byte) inputStream.read()));
                    printStream2.println("    Filter: " + HexDump.toHex((byte) inputStream.read()));
                    printStream2.println("    Data (after decompression): ");
                    int i4 = readInt + -50;
                    j3 -= 50;
                    int i5 = (int) j3;
                    int i6 = i4 > i5 ? (short) i5 : (short) i4;
                    byte[] bArr = new byte[i6];
                    int read3 = inputStream2.read(bArr);
                    while (read3 != -1 && read3 < i6) {
                        read3 += inputStream2.read(bArr, read3, i6);
                    }
                    HexDump.dump((InputStream) new InflaterInputStream(new ByteArrayInputStream(bArr)), printStream2, 0, -1);
                    readInt = i4 - i6;
                    j2 = (long) i6;
                }
                j3 -= j2;
            }
            if (!((readShort & 15) == 15) || j3 < 0) {
                if (j3 >= 0) {
                    int i7 = (int) j3;
                    short s4 = readInt > i7 ? (short) i7 : (short) readInt;
                    if (s4 != 0) {
                        HexDump.dump(inputStream2, printStream2, 0, (int) s4);
                        j3 -= (long) s4;
                    }
                } else {
                    printStream2.println(" >> OVERRUN <<");
                }
            } else if (readInt <= ((int) j3)) {
                printStream2.println("            completed within");
            } else {
                printStream2.println("            continued elsewhere");
            }
        }
    }

    private String propName(short s) {
        AnonymousClass1PropName[] r2 = new AnonymousClass1PropName[TIFFConstants.TIFFTAG_ORIENTATION];
        r2[0] = new Object(4, "transform.rotation") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[1] = new Object(119, "protection.lockrotation") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[2] = new Object(120, "protection.lockaspectratio") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[3] = new Object(121, "protection.lockposition") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[4] = new Object(122, "protection.lockagainstselect") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[5] = new Object(123, "protection.lockcropping") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[6] = new Object(124, "protection.lockvertices") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[7] = new Object(125, "protection.locktext") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[8] = new Object(126, "protection.lockadjusthandles") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[9] = new Object(127, "protection.lockagainstgrouping") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[10] = new Object(128, "text.textid") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[11] = new Object(129, "text.textleft") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[12] = new Object(130, "text.texttop") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[13] = new Object(131, "text.textright") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[14] = new Object(132, "text.textbottom") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[15] = new Object(133, "text.wraptext") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[16] = new Object(134, "text.scaletext") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[17] = new Object(135, "text.anchortext") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[18] = new Object(136, "text.textflow") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[19] = new Object(137, "text.fontrotation") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[20] = new Object(138, "text.idofnextshape") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[21] = new Object(139, "text.bidir") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[22] = new Object(187, "text.singleclickselects") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[23] = new Object(ShapeTypes.DoubleWave, "text.usehostmargins") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[24] = new Object(ShapeTypes.ActionButtonBlank, "text.rotatetextwithshape") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[25] = new Object(ShapeTypes.ActionButtonHome, "text.sizeshapetofittext") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[26] = new Object(ShapeTypes.ActionButtonHelp, "text.sizetexttofitshape") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[27] = new Object(ShapeTypes.ActionButtonInformation, "geotext.unicode") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[28] = new Object(ShapeTypes.ActionButtonForwardNext, "geotext.rtftext") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[29] = new Object(ShapeTypes.ActionButtonBackPrevious, "geotext.alignmentoncurve") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[30] = new Object(ShapeTypes.ActionButtonEnd, "geotext.defaultpointsize") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[31] = new Object(ShapeTypes.ActionButtonBeginning, "geotext.textspacing") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[32] = new Object(ShapeTypes.ActionButtonReturn, "geotext.fontfamilyname") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[33] = new Object(ShapeTypes.Funnel, "geotext.reverseroworder") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[34] = new Object(ShapeTypes.Gear6, "geotext.hastexteffect") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[35] = new Object(ShapeTypes.Gear9, "geotext.rotatecharacters") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[36] = new Object(ShapeTypes.LeftCircularArrow, "geotext.kerncharacters") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[37] = new Object(ShapeTypes.LeftRightRibbon, "geotext.tightortrack") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[38] = new Object(ShapeTypes.PieWedge, "geotext.stretchtofitshape") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[39] = new Object(ShapeTypes.SwooshArrow, "geotext.charboundingbox") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[40] = new Object(247, "geotext.scaletextonpath") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[41] = new Object(ShapeTypes.Curve, "geotext.stretchcharheight") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[42] = new Object(ShapeTypes.DirectPolygon, "geotext.nomeasurealongpath") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[43] = new Object(ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, "geotext.boldfont") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[44] = new Object(251, "geotext.italicfont") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[45] = new Object(252, "geotext.underlinefont") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[46] = new Object(253, "geotext.shadowfont") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[47] = new Object(TIFFConstants.TIFFTAG_SUBFILETYPE, "geotext.smallcapsfont") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[48] = new Object(255, "geotext.strikethroughfont") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[49] = new Object(256, "blip.cropfromtop") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[50] = new Object(257, "blip.cropfrombottom") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[51] = new Object(258, "blip.cropfromleft") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[52] = new Object(259, "blip.cropfromright") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[53] = new Object(MetaDo.META_SETROP2, "blip.bliptodisplay") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[54] = new Object(MetaDo.META_SETRELABS, "blip.blipfilename") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[55] = new Object(262, "blip.blipflags") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[56] = new Object(263, "blip.transparentcolor") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[57] = new Object(264, "blip.contrastsetting") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[58] = new Object(TIFFConstants.TIFFTAG_CELLLENGTH, "blip.brightnesssetting") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[59] = new Object(TIFFConstants.TIFFTAG_FILLORDER, "blip.gamma") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[60] = new Object(267, "blip.pictureid") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[61] = new Object(268, "blip.doublemod") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[62] = new Object(TIFFConstants.TIFFTAG_DOCUMENTNAME, "blip.picturefillmod") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[63] = new Object(TIFFConstants.TIFFTAG_IMAGEDESCRIPTION, "blip.pictureline") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[64] = new Object(TIFFConstants.TIFFTAG_MAKE, "blip.printblip") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[65] = new Object(TIFFConstants.TIFFTAG_MODEL, "blip.printblipfilename") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[66] = new Object(TIFFConstants.TIFFTAG_STRIPOFFSETS, "blip.printflags") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[67] = new Object(316, "blip.nohittestpicture") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[68] = new Object(317, "blip.picturegray") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[69] = new Object(318, "blip.picturebilevel") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[70] = new Object(TIFFConstants.TIFFTAG_PRIMARYCHROMATICITIES, "blip.pictureactive") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[71] = new Object(TIFFConstants.TIFFTAG_COLORMAP, "geometry.left") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[72] = new Object(TIFFConstants.TIFFTAG_HALFTONEHINTS, "geometry.top") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[73] = new Object(322, "geometry.right") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[74] = new Object(TIFFConstants.TIFFTAG_TILELENGTH, "geometry.bottom") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[75] = new Object(TIFFConstants.TIFFTAG_TILEOFFSETS, "geometry.shapepath") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[76] = new Object(TIFFConstants.TIFFTAG_TILEBYTECOUNTS, "geometry.vertices") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[77] = new Object(TIFFConstants.TIFFTAG_BADFAXLINES, "geometry.segmentinfo") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[78] = new Object(TIFFConstants.TIFFTAG_CLEANFAXDATA, "geometry.adjustvalue") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[79] = new Object(TIFFConstants.TIFFTAG_CONSECUTIVEBADFAXLINES, "geometry.adjust2value") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[80] = new Object(329, "geometry.adjust3value") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[81] = new Object(TIFFConstants.TIFFTAG_SUBIFD, "geometry.adjust4value") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[82] = new Object(331, "geometry.adjust5value") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[83] = new Object(TIFFConstants.TIFFTAG_INKSET, "geometry.adjust6value") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[84] = new Object(TIFFConstants.TIFFTAG_INKNAMES, "geometry.adjust7value") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[85] = new Object(TIFFConstants.TIFFTAG_NUMBEROFINKS, "geometry.adjust8value") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[86] = new Object(335, "geometry.adjust9value") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[87] = new Object(TIFFConstants.TIFFTAG_DOTRANGE, "geometry.adjust10value") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[88] = new Object(378, "geometry.shadowOK") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[89] = new Object(379, "geometry.3dok") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[90] = new Object(380, "geometry.lineok") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[91] = new Object(381, "geometry.geotextok") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[92] = new Object(382, "geometry.fillshadeshapeok") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[93] = new Object(383, "geometry.fillok") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[94] = new Object(384, "fill.filltype") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[95] = new Object(385, "fill.fillcolor") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[96] = new Object(386, "fill.fillopacity") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[97] = new Object(387, "fill.fillbackcolor") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[98] = new Object(388, "fill.backopacity") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[99] = new Object(389, "fill.crmod") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[100] = new Object(390, "fill.patterntexture") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[101] = new Object(391, "fill.blipfilename") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[102] = new Object(392, "fill.blipflags") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[103] = new Object(393, "fill.width") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[104] = new Object(394, "fill.height") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[105] = new Object(395, "fill.angle") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[106] = new Object(396, "fill.focus") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[107] = new Object(397, "fill.toleft") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[108] = new Object(398, "fill.totop") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[109] = new Object(399, "fill.toright") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[110] = new Object(EMFConstants.FW_NORMAL, "fill.tobottom") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[111] = new Object(TypedValues.CycleType.TYPE_CURVE_FIT, "fill.rectleft") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[112] = new Object(TypedValues.CycleType.TYPE_VISIBILITY, "fill.recttop") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[113] = new Object(TypedValues.CycleType.TYPE_ALPHA, "fill.rectright") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[114] = new Object(404, "fill.rectbottom") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[115] = new Object(405, "fill.dztype") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[116] = new Object(406, "fill.shadepreset") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[117] = new Object(407, "fill.shadecolors") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[118] = new Object(408, "fill.originx") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[119] = new Object(409, "fill.originy") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[120] = new Object(410, "fill.shapeoriginx") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[121] = new Object(411, "fill.shapeoriginy") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[122] = new Object(412, "fill.shadetype") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[123] = new Object(443, "fill.filled") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[124] = new Object(444, "fill.hittestfill") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[125] = new Object(445, "fill.shape") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[126] = new Object(446, "fill.userect") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[127] = new Object(447, "fill.nofillhittest") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[128] = new Object(448, "linestyle.color") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[129] = new Object(449, "linestyle.opacity") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[130] = new Object(450, "linestyle.backcolor") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[131] = new Object(451, "linestyle.crmod") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[132] = new Object(452, "linestyle.linetype") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[133] = new Object(453, "linestyle.fillblip") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[134] = new Object(454, "linestyle.fillblipname") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[135] = new Object(455, "linestyle.fillblipflags") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[136] = new Object(456, "linestyle.fillwidth") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[137] = new Object(457, "linestyle.fillheight") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[138] = new Object(458, "linestyle.filldztype") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[139] = new Object(459, "linestyle.linewidth") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[140] = new Object(460, "linestyle.linemiterlimit") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[141] = new Object(461, "linestyle.linestyle") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[142] = new Object(462, "linestyle.linedashing") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[143] = new Object(463, "linestyle.linedashstyle") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[144] = new Object(464, "linestyle.linestartarrowhead") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[145] = new Object(465, "linestyle.lineendarrowhead") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[146] = new Object(466, "linestyle.linestartarrowwidth") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[147] = new Object(467, "linestyle.lineestartarrowlength") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[148] = new Object(468, "linestyle.lineendarrowwidth") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[149] = new Object(469, "linestyle.lineendarrowlength") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[150] = new Object(470, "linestyle.linejoinstyle") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[151] = new Object(471, "linestyle.lineendcapstyle") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[152] = new Object(TypedValues.PositionType.TYPE_PERCENT_Y, "linestyle.arrowheadsok") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[153] = new Object(TypedValues.PositionType.TYPE_CURVE_FIT, "linestyle.anyline") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[154] = new Object(509, "linestyle.hitlinetest") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[155] = new Object(TypedValues.PositionType.TYPE_POSITION_TYPE, "linestyle.linefillshape") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[156] = new Object(FrameMetricsAggregator.EVERY_DURATION, "linestyle.nolinedrawdash") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[157] = new Object(512, "shadowstyle.type") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[158] = new Object(513, "shadowstyle.color") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[159] = new Object(TIFFConstants.TIFFTAG_JPEGIFBYTECOUNT, "shadowstyle.highlight") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[160] = new Object(TIFFConstants.TIFFTAG_JPEGRESTARTINTERVAL, "shadowstyle.crmod") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[161] = new Object(516, "shadowstyle.opacity") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[162] = new Object(TIFFConstants.TIFFTAG_JPEGLOSSLESSPREDICTORS, "shadowstyle.offsetx") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[163] = new Object(TIFFConstants.TIFFTAG_JPEGPOINTTRANSFORM, "shadowstyle.offsety") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[164] = new Object(TIFFConstants.TIFFTAG_JPEGQTABLES, "shadowstyle.secondoffsetx") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[165] = new Object(TIFFConstants.TIFFTAG_JPEGDCTABLES, "shadowstyle.secondoffsety") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[166] = new Object(521, "shadowstyle.scalextox") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[167] = new Object(MetaDo.META_SETTEXTJUSTIFICATION, "shadowstyle.scaleytox") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[168] = new Object(MetaDo.META_SETWINDOWORG, "shadowstyle.scalextoy") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[169] = new Object(MetaDo.META_SETWINDOWEXT, "shadowstyle.scaleytoy") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[170] = new Object(MetaDo.META_SETVIEWPORTORG, "shadowstyle.perspectivex") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[171] = new Object(MetaDo.META_SETVIEWPORTEXT, "shadowstyle.perspectivey") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[172] = new Object(MetaDo.META_OFFSETWINDOWORG, "shadowstyle.weight") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[173] = new Object(528, "shadowstyle.originx") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[174] = new Object(529, "shadowstyle.originy") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[175] = new Object(574, "shadowstyle.shadow") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[176] = new Object(575, "shadowstyle.shadowobsured") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[177] = new Object(ShapeKit.MASTER_DPI, "perspective.type") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[178] = new Object(577, "perspective.offsetx") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[179] = new Object(578, "perspective.offsety") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[180] = new Object(579, "perspective.scalextox") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[181] = new Object(580, "perspective.scaleytox") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[182] = new Object(581, "perspective.scalextoy") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[183] = new Object(582, "perspective.scaleytox") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[184] = new Object(583, "perspective.perspectivex") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[185] = new Object(584, "perspective.perspectivey") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[186] = new Object(585, "perspective.weight") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[187] = new Object(586, "perspective.originx") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[188] = new Object(587, "perspective.originy") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[189] = new Object(639, "perspective.perspectiveon") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[190] = new Object(640, "3d.specularamount") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[191] = new Object(661, "3d.diffuseamount") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[192] = new Object(662, "3d.shininess") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[193] = new Object(663, "3d.edgethickness") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[194] = new Object(664, "3d.extrudeforward") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[195] = new Object(665, "3d.extrudebackward") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[196] = new Object(Element.WRITABLE_DIRECT, "3d.extrudeplane") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[197] = new Object(667, "3d.extrusioncolor") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[198] = new Object(648, "3d.crmod") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[199] = new Object(700, "3d.3deffect") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[200] = new Object(TypedValues.TransitionType.TYPE_FROM, "3d.metallic") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[201] = new Object(TypedValues.TransitionType.TYPE_TO, "3d.useextrusioncolor") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[202] = new Object(703, "3d.lightface") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[203] = new Object(TypedValues.TransitionType.TYPE_AUTO_TRANSITION, "3dstyle.yrotationangle") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[204] = new Object(TypedValues.TransitionType.TYPE_INTERPOLATOR, "3dstyle.xrotationangle") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[205] = new Object(TypedValues.TransitionType.TYPE_STAGGERED, "3dstyle.rotationaxisx") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[206] = new Object(TypedValues.TransitionType.TYPE_TRANSITION_FLAGS, "3dstyle.rotationaxisy") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[207] = new Object(708, "3dstyle.rotationaxisz") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[208] = new Object(709, "3dstyle.rotationangle") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[209] = new Object(710, "3dstyle.rotationcenterx") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[210] = new Object(711, "3dstyle.rotationcentery") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[211] = new Object(712, "3dstyle.rotationcenterz") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[212] = new Object(713, "3dstyle.rendermode") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[213] = new Object(714, "3dstyle.tolerance") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[214] = new Object(715, "3dstyle.xviewpoint") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[215] = new Object(716, "3dstyle.yviewpoint") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[216] = new Object(717, "3dstyle.zviewpoint") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[217] = new Object(718, "3dstyle.originx") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[218] = new Object(719, "3dstyle.originy") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[219] = new Object(IAnimation.AnimationInformation.ROTATION, "3dstyle.skewangle") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[220] = new Object(721, "3dstyle.skewamount") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[221] = new Object(722, "3dstyle.ambientintensity") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[222] = new Object(723, "3dstyle.keyx") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[223] = new Object(724, "3dstyle.keyy") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[224] = new Object(725, "3dstyle.keyz") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[225] = new Object(726, "3dstyle.keyintensity") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[226] = new Object(727, "3dstyle.fillx") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[227] = new Object(728, "3dstyle.filly") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[228] = new Object(729, "3dstyle.fillz") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[229] = new Object(730, "3dstyle.fillintensity") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[230] = new Object(MetaDo.META_CREATEFONTINDIRECT, "3dstyle.constrainrotation") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[231] = new Object(MetaDo.META_CREATEBRUSHINDIRECT, "3dstyle.rotationcenterauto") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[232] = new Object(765, "3dstyle.parallel") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[233] = new Object(766, "3dstyle.keyharsh") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[234] = new Object(767, "3dstyle.fillharsh") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[235] = new Object(769, "shape.master") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[236] = new Object(771, "shape.connectorstyle") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[237] = new Object(772, "shape.blackandwhitesettings") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[238] = new Object(773, "shape.wmodepurebw") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[239] = new Object(774, "shape.wmodebw") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[240] = new Object(826, "shape.oleicon") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[241] = new Object(827, "shape.preferrelativeresize") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[242] = new Object(828, "shape.lockshapetype") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[243] = new Object(830, "shape.deleteattachedobject") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[244] = new Object(831, "shape.backgroundshape") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[245] = new Object(832, "callout.callouttype") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[246] = new Object(833, "callout.xycalloutgap") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[247] = new Object(834, "callout.calloutangle") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[248] = new Object(835, "callout.calloutdroptype") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[249] = new Object(836, "callout.calloutdropspecified") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[250] = new Object(837, "callout.calloutlengthspecified") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[251] = new Object(889, "callout.iscallout") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[252] = new Object(890, "callout.calloutaccentbar") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[253] = new Object(891, "callout.callouttextborder") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[254] = new Object(892, "callout.calloutminusx") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[255] = new Object(893, "callout.calloutminusy") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[256] = new Object(894, "callout.dropauto") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[257] = new Object(895, "callout.lengthspecified") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[258] = new Object(896, "groupshape.shapename") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[259] = new Object(897, "groupshape.description") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[260] = new Object(898, "groupshape.hyperlink") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[261] = new Object(899, "groupshape.wrappolygonvertices") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[262] = new Object(900, "groupshape.wrapdistleft") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[263] = new Object(TypedValues.Custom.TYPE_FLOAT, "groupshape.wrapdisttop") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[264] = new Object(TypedValues.Custom.TYPE_COLOR, "groupshape.wrapdistright") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[265] = new Object(TypedValues.Custom.TYPE_STRING, "groupshape.wrapdistbottom") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[266] = new Object(TypedValues.Custom.TYPE_BOOLEAN, "groupshape.regroupid") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[267] = new Object(953, "groupshape.editedwrap") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[268] = new Object(954, "groupshape.behinddocument") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[269] = new Object(955, "groupshape.ondblclicknotify") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[270] = new Object(956, "groupshape.isbutton") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[271] = new Object(957, "groupshape.1dadjustment") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[272] = new Object(958, "groupshape.hidden") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        r2[273] = new Object(959, "groupshape.print") {
            final int _id;
            final String _name;

            {
                this._id = r2;
                this._name = r3;
            }
        };
        for (int i = 0; i < 274; i++) {
            if (r2[i]._id == s) {
                return r2[i]._name;
            }
        }
        return "unknown property";
    }

    private static String getBlipType(byte b) {
        return EscherBSERecord.getBlipType(b);
    }

    private String dec1616(int i) {
        return (("" + ((short) (i >> 16))) + '.') + ((short) (i & -1));
    }

    private void outHex(int i, InputStream inputStream, PrintStream printStream) throws IOException, LittleEndian.BufferUnderrunException {
        if (i == 1) {
            printStream.print(HexDump.toHex((byte) inputStream.read()));
        } else if (i == 2) {
            printStream.print(HexDump.toHex(LittleEndian.readShort(inputStream)));
        } else if (i == 4) {
            printStream.print(HexDump.toHex(LittleEndian.readInt(inputStream)));
        } else {
            throw new IOException("Unable to output variable of that width");
        }
    }

    public static void main(String[] strArr) {
        byte[] readFromString = HexRead.readFromString("0F 00 00 F0 89 07 00 00 00 00 06 F0 18 00 00 00 05 04 00 00 02 00 00 00 05 00 00 00 01 00 00 00 01 00 00 00 05 00 00 00 4F 00 01 F0 2F 07 00 00 42 00 07 F0 B7 01 00 00 03 04 3F 14 AE 6B 0F 65 B0 48 BF 5E 94 63 80 E8 91 73 FF 00 93 01 00 00 01 00 00 00 00 00 00 00 00 00 FF FF 20 54 1C F0 8B 01 00 00 3F 14 AE 6B 0F 65 B0 48 BF 5E 94 63 80 E8 91 73 92 0E 00 00 00 00 00 00 00 00 00 00 D1 07 00 00 DD 05 00 00 4A AD 6F 00 8A C5 53 00 59 01 00 00 00 FE 78 9C E3 9B C4 00 04 AC 77 D9 2F 32 08 32 FD E7 61 F8 FF 0F C8 FD 05 C5 30 19 10 90 63 90 FA 0F 06 0C 8C 0C 5C 70 19 43 30 EB 0E FB 05 86 85 0C DB 18 58 80 72 8C 70 16 0B 83 05 56 51 29 88 C9 60 D9 69 0C 6C 20 26 23 03 C8 74 B0 A8 0E 03 07 FB 45 56 C7 A2 CC C4 1C 06 66 A0 0D 2C 40 39 5E 86 4C 06 3D A0 4E 10 D0 60 D9 C8 58 CC E8 CF B0 80 61 3A 8A 7E 0D C6 23 AC 4F E0 E2 98 B6 12 2B 06 73 9D 12 E3 52 56 59 F6 08 8A CC 52 66 A3 50 FF 96 2B 94 E9 DF 4C A1 FE 2D 3A 03 AB 9F 81 C2 F0 A3 54 BF 0F 85 EE A7 54 FF 40 FB 7F A0 E3 9F D2 F4 4F 71 FE 19 58 FF 2B 31 7F 67 36 3B 25 4F 99 1B 4E 53 A6 5F 89 25 95 E9 C4 00 C7 83 12 F3 1F 26 35 4A D3 D2 47 0E 0A C3 41 8E C9 8A 52 37 DC 15 A1 D0 0D BC 4C 06 0C 2B 28 2C 13 28 D4 EF 43 61 5A A0 58 3F 85 71 E0 4B 69 9E 64 65 FE 39 C0 E5 22 30 1D 30 27 0E 74 3A 18 60 FD 4A CC B1 2C 13 7D 07 36 2D 2A 31 85 B2 6A 0D 74 1D 1D 22 4D 99 FE 60 0A F5 9B EC 1C 58 FD 67 06 56 3F 38 0D 84 3C A5 30 0E 28 D3 AF C4 A4 CA FA 44 7A 0D 65 6E 60 7F 4D A1 1B 24 58 F7 49 AF A5 CC 0D CC DF 19 FE 03 00 F0 B1 25 4D 42 00 07 F0 E1 01 00 00 03 04 39 50 BE 98 B0 6F 57 24 31 70 5D 23 2F 9F 10 66 FF 00 BD 01 00 00 01 00 00 00 00 00 00 00 00 00 FF FF 20 54 1C F0 B5 01 00 00 39 50 BE 98 B0 6F 57 24 31 70 5D 23 2F 9F 10 66 DA 03 00 00 00 00 00 00 00 00 00 00 D1 07 00 00 DD 05 00 00 4A AD 6F 00 8A C5 53 00 83 01 00 00 00 FE 78 9C A5 52 BF 4B 42 51 14 3E F7 DC 77 7A 16 45 48 8B 3C 48 A8 16 15 0D 6C 88 D0 04 C3 40 A3 32 1C 84 96 08 21 04 A1 C5 5C A2 35 82 C0 35 6A AB 1C 6A 6B A8 24 5A 83 68 08 84 84 96 A2 86 A0 7F C2 86 5E E7 5E F5 41 E4 10 BC 03 1F E7 FB F1 CE B9 F7 F1 9E 7C 05 2E 7A 37 9B E0 45 7B 10 EC 6F 96 5F 1D 74 13 55 7E B0 6C 5D 20 60 C0 49 A2 9A BD 99 4F 50 83 1B 30 38 13 0E 33 60 A6 A7 6B B5 37 EB F4 10 FA 14 15 A0 B6 6B 37 0C 1E B3 49 73 5B A5 C2 26 48 3E C1 E0 6C 08 4A 30 C9 93 AA 02 B8 20 13 62 05 4E E1 E8 D7 7C C0 B8 14 95 5E BE B8 A7 CF 1E BE 55 2C 56 B9 78 DF 08 7E 88 4C 27 FF 7B DB FF 7A DD B7 1A 17 67 34 6A AE BA DA 35 D1 E7 72 BE FE EC 6E FE DA E5 7C 3D EC 7A DE 03 FD 50 06 0B 23 F2 0E F3 B2 A5 11 91 0D 4C B5 B5 F3 BF 94 C1 8F 24 F7 D9 6F 60 94 3B C9 9A F3 1C 6B E7 BB F0 2E 49 B2 25 2B C6 B1 EE 69 EE 15 63 4F 71 7D CE 85 CC C8 35 B9 C3 28 28 CE D0 5C 67 79 F2 4A A2 14 23 A4 38 43 73 9D 2D 69 2F C1 08 31 9F C5 5C 9B EB 7B C5 69 19 B3 B4 81 F3 DC E3 B4 8E 8B CC B3 94 53 5A E7 41 2A 63 9A AA 38 C5 3D 48 BB EC 57 59 6F 2B AD 73 1F 1D 60 92 AE 70 8C BB 8F CE 31 C1 3C 49 27 4A EB DC A4 5B 8C D1 0B 0E 73 37 E9 11 A7 99 C7 E8 41 69 B0 7F 00 96 F2 A7 E8 42 00 07 F0 B4 01 00 00 03 04 1A BA F9 D6 A9 B9 3A 03 08 61 E9 90 FF 7B 9E E6 FF 00 90 01 00 00 01 00 00 00 00 00 00 00 00 00 FF FF 20 54 1C F0 88 01 00 00 1A BA F9 D6 A9 B9 3A 03 08 61 E9 90 FF 7B 9E E6 12 0E 00 00 00 00 00 00 00 00 00 00 D1 07 00 00 DD 05 00 00 4A AD 6F 00 8A C5 53 00 56 01 00 00 00 FE 78 9C E3 13 62 00 02 D6 BB EC 17 19 04 99 FE F3 30 FC FF 07 E4 FE 82 62 98 0C 08 C8 31 48 FD 07 03 06 46 06 2E B8 8C 21 98 75 87 FD 02 C3 42 86 6D 0C 2C 40 39 46 38 8B 85 C1 02 AB A8 14 C4 64 B0 EC 34 06 36 10 93 91 01 64 3A 58 54 87 81 83 FD 22 AB 63 51 66 62 0E 03 33 D0 06 16 A0 1C 2F 43 26 83 1E 50 27 08 68 B0 6C 64 2C 66 F4 67 58 C0 30 1D 45 BF 06 E3 11 D6 27 70 71 4C 5B 89 15 83 B9 4E 89 71 29 AB 2C 7B 04 45 66 29 B3 51 A8 7F CB 15 CA F4 6F A6 50 FF 16 9D 81 D5 CF 40 61 F8 51 AA DF 87 42 F7 53 AA 7F A0 FD 3F D0 F1 4F 69 FA A7 38 FF 0C AC FF 95 98 BF 33 9B 9D 92 A7 CC 0D A7 29 D3 AF C4 92 CA 74 62 80 E3 41 89 F9 0F 93 1A A5 69 E9 23 07 85 E1 20 C7 64 45 A9 1B EE 8A 50 E8 06 5E 26 03 86 15 14 96 09 14 EA F7 A1 30 2D 50 AC 9F C2 38 F0 A5 34 4F B2 32 FF 1C E0 72 11 98 0E 98 13 07 38 1D 28 31 C7 B2 4C F4 1D D8 B4 A0 C4 14 CA AA 35 D0 75 64 88 34 65 FA 83 29 D4 6F B2 73 60 F5 9F A1 54 FF 0E CA D3 40 C8 53 0A E3 E0 09 85 6E 50 65 7D 22 BD 86 32 37 B0 BF A6 D0 0D 12 AC FB A4 D7 52 E6 06 E6 EF 0C FF 01 97 1D 12 C7 42 00 07 F0 C3 01 00 00 03 04 BA 4C B6 23 BA 8B 27 BE C8 55 59 86 24 9F 89 D4 FF 00 9F 01 00 00 01 00 00 00 00 00 00 00 00 00 FF FF 20 54 1C F0 97 01 00 00 BA 4C B6 23 BA 8B 27 BE C8 55 59 86 24 9F 89 D4 AE 0E 00 00 00 00 00 00 00 00 00 00 D1 07 00 00 DD 05 00 00 4A AD 6F 00 8A C5 53 00 65 01 00 00 00 FE 78 9C E3 5B C7 00 04 AC 77 D9 2F 32 08 32 FD E7 61 F8 FF 0F C8 FD 05 C5 30 19 10 90 63 90 FA 0F 06 0C 8C 0C 5C 70 19 43 30 EB 0E FB 05 86 85 0C DB 18 58 80 72 8C 70 16 0B 83 05 56 51 29 88 C9 60 D9 69 0C 6C 20 26 23 03 C8 74 B0 A8 0E 03 07 FB 45 56 C7 A2 CC C4 1C 06 66 A0 0D 2C 40 39 5E 86 4C 06 3D A0 4E 10 D0 60 99 C6 B8 98 D1 9F 61 01 C3 74 14 FD 1A 8C 2B D8 84 B1 88 4B A5 A5 75 03 01 50 DF 59 46 77 46 0F A8 3C A6 AB 88 15 83 B9 5E 89 B1 8B D5 97 2D 82 22 B3 94 29 D5 BF E5 CA C0 EA DF AC 43 A1 FD 14 EA 67 A0 30 FC 28 D5 EF 43 A1 FB 7D 87 B8 FF 07 3A FE 07 3A FD 53 EA 7E 0A C3 4F 89 F9 0E 73 EA 69 79 CA DC 70 8A 32 FD 4A 2C 5E 4C DF 87 7A 3C BC E0 A5 30 1E 3E 31 C5 33 AC A0 30 2F 52 A8 DF 87 C2 30 A4 54 3F A5 65 19 85 65 A9 12 D3 2B 16 0D 8A CB 13 4A F3 E3 27 E6 09 03 9D 0E 06 58 BF 12 B3 13 CB C1 01 4E 8B 4A 4C 56 AC 91 03 5D 37 86 48 53 A6 3F 98 42 FD 26 3B 07 56 FF 99 1D 14 EA A7 CC 7E 70 1A 08 79 42 61 1C 3C A5 D0 0D 9C 6C C2 32 6B 29 73 03 DB 6B CA DC C0 F8 97 F5 AD CC 1A CA DC C0 F4 83 32 37 B0 A4 30 CE FC C7 48 99 1B FE 33 32 FC 07 00 6C CC 2E 23 33 00 0B F0 12 00 00 00 BF 00 08 00 08 00 81 01 09 00 00 08 C0 01 40 00 00 08 40 00 1E F1 10 00 00 00 0D 00 00 08 0C 00 00 08 17 00 00 08 F7 00 00 10                                              ");
        new EscherDump().dump(readFromString, 0, readFromString.length, System.out);
    }

    public void dump(int i, byte[] bArr, PrintStream printStream) {
        dump(bArr, 0, i, printStream);
    }
}
