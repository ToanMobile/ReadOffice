package com.app.office.fc.hpsf;

import com.itextpdf.text.pdf.BaseFont;
import com.app.office.fc.codec.CharEncoding;
import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class VariantSupport extends Variant {
    public static final int[] SUPPORTED_TYPES = {0, 2, 3, 20, 5, 64, 30, 31, 71, 11};
    private static boolean logUnsupportedTypes = false;
    protected static List unsupportedMessage;

    public static void setLogUnsupportedTypes(boolean z) {
        logUnsupportedTypes = z;
    }

    public static boolean isLogUnsupportedTypes() {
        return logUnsupportedTypes;
    }

    protected static void writeUnsupportedTypeMessage(UnsupportedVariantTypeException unsupportedVariantTypeException) {
        if (isLogUnsupportedTypes()) {
            if (unsupportedMessage == null) {
                unsupportedMessage = new LinkedList();
            }
            Long valueOf = Long.valueOf(unsupportedVariantTypeException.getVariantType());
            if (!unsupportedMessage.contains(valueOf)) {
                System.err.println(unsupportedVariantTypeException.getMessage());
                unsupportedMessage.add(valueOf);
            }
        }
    }

    public boolean isSupportedType(int i) {
        int i2 = 0;
        while (true) {
            int[] iArr = SUPPORTED_TYPES;
            if (i2 >= iArr.length) {
                return false;
            }
            if (i == iArr[i2]) {
                return true;
            }
            i2++;
        }
    }

    public static Object read(byte[] bArr, int i, int i2, long j, int i3) throws ReadingNotSupportedException, UnsupportedEncodingException {
        Object obj;
        int i4 = i2 - 4;
        int i5 = (int) ((i3 == 1200 && j == 30) ? 31 : j);
        if (i5 == 0) {
            return null;
        }
        if (i5 == 5) {
            return new Double(LittleEndian.getDouble(bArr, i));
        }
        if (i5 != 11) {
            if (i5 == 20) {
                return Long.valueOf(LittleEndian.getLong(bArr, i));
            }
            if (i5 != 64) {
                int i6 = 0;
                if (i5 == 71) {
                    if (i4 < 0) {
                        i4 = LittleEndian.getInt(bArr, i);
                        i += 4;
                    }
                    obj = new byte[i4];
                    System.arraycopy(bArr, i, obj, 0, i4);
                } else if (i5 == 2) {
                    return Integer.valueOf(LittleEndian.getShort(bArr, i));
                } else {
                    if (i5 == 3) {
                        return Integer.valueOf(LittleEndian.getInt(bArr, i));
                    }
                    if (i5 == 30) {
                        int i7 = i + 4;
                        long j2 = (long) i7;
                        long uInt = LittleEndian.getUInt(bArr, i) + j2;
                        do {
                            uInt--;
                            if (bArr[(int) uInt] != 0 || j2 > uInt) {
                                int i8 = (int) ((uInt - j2) + 1);
                            }
                            uInt--;
                            break;
                        } while (j2 > uInt);
                        int i82 = (int) ((uInt - j2) + 1);
                        if (i3 != -1) {
                            obj = new String(bArr, i7, i82, codepageToEncoding(i3));
                        } else {
                            obj = new String(bArr, i7, i82);
                        }
                    } else if (i5 != 31) {
                        byte[] bArr2 = new byte[i4];
                        while (i6 < i4) {
                            bArr2[i6] = bArr[i + i6];
                            i6++;
                        }
                        throw new ReadingNotSupportedException(j, bArr2);
                    } else {
                        int i9 = i + 4;
                        long j3 = (long) i9;
                        long uInt2 = ((LittleEndian.getUInt(bArr, i) + j3) - 1) - j3;
                        StringBuffer stringBuffer = new StringBuffer((int) uInt2);
                        while (((long) i6) <= uInt2) {
                            int i10 = (i6 * 2) + i9;
                            stringBuffer.append((char) ((bArr[i10] & 255) | (bArr[i10 + 1] << 8)));
                            i6++;
                        }
                        while (stringBuffer.length() > 0 && stringBuffer.charAt(stringBuffer.length() - 1) == 0) {
                            stringBuffer.setLength(stringBuffer.length() - 1);
                        }
                        return stringBuffer.toString();
                    }
                }
                return obj;
            }
            return Util.filetimeToDate((int) LittleEndian.getUInt(bArr, i + 4), (int) LittleEndian.getUInt(bArr, i));
        } else if (LittleEndian.getUInt(bArr, i) != 0) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public static String codepageToEncoding(int i) throws UnsupportedEncodingException {
        if (i <= 0) {
            throw new UnsupportedEncodingException("Codepage number may not be " + i);
        } else if (i == 1200) {
            return "UTF-16";
        } else {
            if (i == 1201) {
                return "UTF-16BE";
            }
            if (i == 10081) {
                return "MacTurkish";
            }
            if (i == 10082) {
                return "MacCroatian";
            }
            switch (i) {
                case 37:
                    return "cp037";
                case Constants.CP_SJIS:
                    return "SJIS";
                case Constants.CP_GBK:
                    return "GBK";
                case Constants.CP_MS949:
                    return "ms949";
                case Constants.CP_JOHAB:
                    return "johab";
                case Constants.CP_MAC_ROMANIA:
                    return "MacRomania";
                case Constants.CP_MAC_UKRAINE:
                    return "MacUkraine";
                case Constants.CP_MAC_THAI:
                    return "MacThai";
                case Constants.CP_MAC_CENTRAL_EUROPE:
                    return "MacCentralEurope";
                case Constants.CP_MAC_ICELAND:
                    return "MacIceland";
                case Constants.CP_US_ACSII:
                    return CharEncoding.US_ASCII;
                case Constants.CP_KOI8_R:
                    return "KOI8-R";
                case Constants.CP_ISO_2022_KR:
                    return "ISO-2022-KR";
                case Constants.CP_EUC_JP:
                    return "EUC-JP";
                case Constants.CP_EUC_KR:
                    return "EUC-KR";
                case Constants.CP_GB2312:
                    return "GB2312";
                case Constants.CP_GB18030:
                    return "GB18030";
                default:
                    switch (i) {
                        case Constants.CP_WINDOWS_1250:
                            return "windows-1250";
                        case Constants.CP_WINDOWS_1251:
                            return "windows-1251";
                        case Constants.CP_WINDOWS_1252:
                            return "windows-1252";
                        case Constants.CP_WINDOWS_1253:
                            return "windows-1253";
                        case Constants.CP_WINDOWS_1254:
                            return "windows-1254";
                        case Constants.CP_WINDOWS_1255:
                            return "windows-1255";
                        case Constants.CP_WINDOWS_1256:
                            return "windows-1256";
                        case Constants.CP_WINDOWS_1257:
                            return "windows-1257";
                        case Constants.CP_WINDOWS_1258:
                            return "windows-1258";
                        default:
                            switch (i) {
                                case 10000:
                                    return BaseFont.MACROMAN;
                                case Constants.CP_MAC_JAPAN:
                                    return "SJIS";
                                case Constants.CP_MAC_CHINESE_TRADITIONAL:
                                    return "Big5";
                                case Constants.CP_MAC_KOREAN:
                                    return "EUC-KR";
                                case Constants.CP_MAC_ARABIC:
                                    return "MacArabic";
                                case Constants.CP_MAC_HEBREW:
                                    return "MacHebrew";
                                case Constants.CP_MAC_GREEK:
                                    return "MacGreek";
                                case Constants.CP_MAC_CYRILLIC:
                                    return "MacCyrillic";
                                case Constants.CP_MAC_CHINESE_SIMPLE:
                                    return "EUC_CN";
                                default:
                                    switch (i) {
                                        case Constants.CP_ISO_8859_1:
                                            return CharEncoding.ISO_8859_1;
                                        case Constants.CP_ISO_8859_2:
                                            return "ISO-8859-2";
                                        case Constants.CP_ISO_8859_3:
                                            return "ISO-8859-3";
                                        case Constants.CP_ISO_8859_4:
                                            return "ISO-8859-4";
                                        case Constants.CP_ISO_8859_5:
                                            return "ISO-8859-5";
                                        case Constants.CP_ISO_8859_6:
                                            return "ISO-8859-6";
                                        case Constants.CP_ISO_8859_7:
                                            return "ISO-8859-7";
                                        case Constants.CP_ISO_8859_8:
                                            return "ISO-8859-8";
                                        case Constants.CP_ISO_8859_9:
                                            return "ISO-8859-9";
                                        default:
                                            switch (i) {
                                                case Constants.CP_ISO_2022_JP1:
                                                case Constants.CP_ISO_2022_JP2:
                                                case Constants.CP_ISO_2022_JP3:
                                                    return "ISO-2022-JP";
                                                default:
                                                    switch (i) {
                                                        case Constants.CP_US_ASCII2:
                                                            return CharEncoding.US_ASCII;
                                                        case Constants.CP_UTF8:
                                                            return "UTF-8";
                                                        default:
                                                            return "cp" + i;
                                                    }
                                            }
                                    }
                            }
                    }
            }
        }
    }

    public static int write(OutputStream outputStream, long j, Object obj, int i) throws IOException, WritingNotSupportedException {
        int i2;
        byte[] bArr;
        int i3 = (int) j;
        if (i3 != 0) {
            if (i3 == 5) {
                i2 = TypeWriter.writeToStream(outputStream, ((Double) obj).doubleValue());
            } else if (i3 == 11) {
                return TypeWriter.writeUIntToStream(outputStream, ((Boolean) obj).booleanValue() ? 1 : 0);
            } else {
                if (i3 == 20) {
                    TypeWriter.writeToStream(outputStream, ((Long) obj).longValue());
                    return 8;
                } else if (i3 == 64) {
                    long dateToFileTime = Util.dateToFileTime((Date) obj);
                    return TypeWriter.writeUIntToStream(outputStream, ((long) ((int) (dateToFileTime & 4294967295L))) & 4294967295L) + 0 + TypeWriter.writeUIntToStream(outputStream, ((long) ((int) ((dateToFileTime >> 32) & 4294967295L))) & 4294967295L);
                } else if (i3 == 71) {
                    byte[] bArr2 = (byte[]) obj;
                    outputStream.write(bArr2);
                    return bArr2.length;
                } else if (i3 == 2) {
                    TypeWriter.writeToStream(outputStream, ((Integer) obj).shortValue());
                    return 2;
                } else if (i3 != 3) {
                    if (i3 == 30) {
                        if (i == -1) {
                            bArr = ((String) obj).getBytes();
                        } else {
                            bArr = ((String) obj).getBytes(codepageToEncoding(i));
                        }
                        int writeUIntToStream = TypeWriter.writeUIntToStream(outputStream, (long) (bArr.length + 1));
                        int length = bArr.length + 1;
                        byte[] bArr3 = new byte[length];
                        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
                        bArr3[length - 1] = 0;
                        outputStream.write(bArr3);
                        return writeUIntToStream + length;
                    } else if (i3 == 31) {
                        String str = (String) obj;
                        int writeUIntToStream2 = TypeWriter.writeUIntToStream(outputStream, (long) (str.length() + 1)) + 0;
                        char[] pad4 = Util.pad4(str);
                        for (int i4 = 0; i4 < pad4.length; i4++) {
                            outputStream.write((byte) (pad4[i4] & 255));
                            outputStream.write((byte) ((pad4[i4] & 65280) >> 8));
                            writeUIntToStream2 += 2;
                        }
                        outputStream.write(0);
                        outputStream.write(0);
                        return writeUIntToStream2 + 2;
                    } else if (obj instanceof byte[]) {
                        byte[] bArr4 = (byte[]) obj;
                        outputStream.write(bArr4);
                        int length2 = bArr4.length;
                        writeUnsupportedTypeMessage(new WritingNotSupportedException(j, obj));
                        return length2;
                    } else {
                        throw new WritingNotSupportedException(j, obj);
                    }
                } else if (obj instanceof Integer) {
                    i2 = TypeWriter.writeToStream(outputStream, ((Integer) obj).intValue());
                } else {
                    throw new ClassCastException("Could not cast an object to " + Integer.class.toString() + ": " + obj.getClass().toString() + ", " + obj.toString());
                }
            }
            return i2 + 0;
        }
        TypeWriter.writeUIntToStream(outputStream, 0);
        return 4;
    }
}
