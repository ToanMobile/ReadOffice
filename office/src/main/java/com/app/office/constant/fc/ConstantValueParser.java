package com.app.office.constant.fc;

import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class ConstantValueParser {
    private static final Object EMPTY_REPRESENTATION = null;
    private static final int FALSE_ENCODING = 0;
    private static final int TRUE_ENCODING = 1;
    private static final int TYPE_BOOLEAN = 4;
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_ERROR_CODE = 16;
    private static final int TYPE_NUMBER = 1;
    private static final int TYPE_STRING = 2;

    private ConstantValueParser() {
    }

    public static Object[] parse(LittleEndianInput littleEndianInput, int i) {
        Object[] objArr = new Object[i];
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = readAConstantValue(littleEndianInput);
        }
        return objArr;
    }

    private static Object readAConstantValue(LittleEndianInput littleEndianInput) {
        byte readByte = littleEndianInput.readByte();
        if (readByte == 0) {
            littleEndianInput.readLong();
            return EMPTY_REPRESENTATION;
        } else if (readByte == 1) {
            return new Double(littleEndianInput.readDouble());
        } else {
            if (readByte == 2) {
                return StringUtil.readUnicodeString(littleEndianInput);
            }
            if (readByte == 4) {
                return readBoolean(littleEndianInput);
            }
            if (readByte == 16) {
                int readUShort = littleEndianInput.readUShort();
                littleEndianInput.readUShort();
                littleEndianInput.readInt();
                return ErrorConstant.valueOf(readUShort);
            }
            throw new RuntimeException("Unknown grbit value (" + readByte + ")");
        }
    }

    private static Object readBoolean(LittleEndianInput littleEndianInput) {
        byte readLong = (byte) ((int) littleEndianInput.readLong());
        if (readLong == 0) {
            return Boolean.FALSE;
        }
        if (readLong == 1) {
            return Boolean.TRUE;
        }
        throw new RuntimeException("unexpected boolean encoding (" + readLong + ")");
    }

    public static int getEncodedSize(Object[] objArr) {
        int length = objArr.length * 1;
        for (Object encodedSize : objArr) {
            length += getEncodedSize(encodedSize);
        }
        return length;
    }

    private static int getEncodedSize(Object obj) {
        Class<?> cls;
        if (obj == EMPTY_REPRESENTATION || (cls = obj.getClass()) == Boolean.class || cls == Double.class || cls == ErrorConstant.class) {
            return 8;
        }
        return StringUtil.getEncodedSize((String) obj);
    }

    public static void encode(LittleEndianOutput littleEndianOutput, Object[] objArr) {
        for (Object encodeSingleValue : objArr) {
            encodeSingleValue(littleEndianOutput, encodeSingleValue);
        }
    }

    private static void encodeSingleValue(LittleEndianOutput littleEndianOutput, Object obj) {
        long j = 0;
        if (obj == EMPTY_REPRESENTATION) {
            littleEndianOutput.writeByte(0);
            littleEndianOutput.writeLong(0);
        } else if (obj instanceof Boolean) {
            littleEndianOutput.writeByte(4);
            if (((Boolean) obj).booleanValue()) {
                j = 1;
            }
            littleEndianOutput.writeLong(j);
        } else if (obj instanceof Double) {
            littleEndianOutput.writeByte(1);
            littleEndianOutput.writeDouble(((Double) obj).doubleValue());
        } else if (obj instanceof String) {
            littleEndianOutput.writeByte(2);
            StringUtil.writeUnicodeString(littleEndianOutput, (String) obj);
        } else if (obj instanceof ErrorConstant) {
            littleEndianOutput.writeByte(16);
            littleEndianOutput.writeLong((long) ((ErrorConstant) obj).getErrorCode());
        } else {
            throw new IllegalStateException("Unexpected value type (" + obj.getClass().getName() + "'");
        }
    }
}
