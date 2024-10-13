package com.app.office.fc.hslf.record;

import com.app.office.fc.hslf.exceptions.CorruptPowerPointFileException;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public abstract class Record {
    protected POILogger logger = POILogFactory.getLogger((Class) getClass());

    public abstract void dispose();

    public abstract Record[] getChildRecords();

    public abstract long getRecordType();

    public abstract boolean isAnAtom();

    public static void writeLittleEndian(int i, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[4];
        LittleEndian.putInt(bArr, i);
        outputStream.write(bArr);
    }

    public static void writeLittleEndian(short s, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[2];
        LittleEndian.putShort(bArr, s);
        outputStream.write(bArr);
    }

    public static Record buildRecordAtOffset(byte[] bArr, int i) {
        long uShort = (long) LittleEndian.getUShort(bArr, i + 2);
        int uInt = (int) LittleEndian.getUInt(bArr, i + 4);
        if (uInt < 0) {
            uInt = 0;
        }
        return createRecordForType(uShort, bArr, i, uInt + 8);
    }

    public static Record buildRecordAtOffset(byte[] bArr, int i, int i2) {
        long uShort = (long) LittleEndian.getUShort(bArr, i + 2);
        int uInt = (int) LittleEndian.getUInt(bArr, i + 4);
        if (uInt < 0) {
            uInt = 0;
        }
        return createRecordForType(uShort, bArr, i, uInt + 8, i2);
    }

    public static Record[] findChildRecords(byte[] bArr, int i, int i2) {
        ArrayList arrayList = new ArrayList(5);
        int i3 = i;
        while (i3 <= (i + i2) - 8) {
            long uShort = (long) LittleEndian.getUShort(bArr, i3 + 2);
            int uInt = (int) LittleEndian.getUInt(bArr, i3 + 4);
            if (uInt < 0) {
                uInt = 0;
            }
            if (i3 == 0 && uShort == 0 && uInt == 65535) {
                throw new CorruptPowerPointFileException("Corrupt document - starts with record of type 0000 and length 0xFFFF");
            }
            Record createRecordForType = createRecordForType(uShort, bArr, i3, uInt + 8);
            if (createRecordForType != null) {
                arrayList.add(createRecordForType);
            }
            i3 = i3 + 8 + uInt;
        }
        return (Record[]) arrayList.toArray(new Record[arrayList.size()]);
    }

    public static Record createRecordForType(long j, byte[] bArr, int i, int i2) {
        if (i + i2 > bArr.length) {
            PrintStream printStream = System.err;
            printStream.println("Warning: Skipping record of type " + j + " at position " + i + " which claims to be longer than the file! (" + i2 + " vs " + (bArr.length - i) + ")");
            return null;
        }
        try {
            Class<? extends Record> recordHandlingClass = RecordTypes.recordHandlingClass((int) j);
            if (recordHandlingClass == null) {
                recordHandlingClass = RecordTypes.recordHandlingClass(RecordTypes.Unknown.typeID);
            }
            Record record = (Record) recordHandlingClass.getDeclaredConstructor(new Class[]{byte[].class, Integer.TYPE, Integer.TYPE}).newInstance(new Object[]{bArr, Integer.valueOf(i), Integer.valueOf(i2)});
            if (record instanceof PositionDependentRecord) {
                ((PositionDependentRecord) record).setLastOnDiskOffset(i);
            }
            return record;
        } catch (InstantiationException e) {
            throw new RuntimeException("Couldn't instantiate the class for type with id " + j + " on class " + null + " : " + e, e);
        } catch (InvocationTargetException e2) {
            throw new RuntimeException("Couldn't instantiate the class for type with id " + j + " on class " + null + " : " + e2 + "\nCause was : " + e2.getCause(), e2);
        } catch (IllegalAccessException e3) {
            throw new RuntimeException("Couldn't access the constructor for type with id " + j + " on class " + null + " : " + e3, e3);
        } catch (NoSuchMethodException e4) {
            throw new RuntimeException("Couldn't access the constructor for type with id " + j + " on class " + null + " : " + e4, e4);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v3, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.app.office.fc.hslf.record.Record createRecordForType(long r16, byte[] r18, int r19, int r20, int r21) {
        /*
            r1 = r16
            r0 = r18
            r3 = r19
            r4 = r20
            java.lang.String r5 = "Couldn't access the constructor for type with id "
            java.lang.String r6 = "Couldn't instantiate the class for type with id "
            java.lang.String r7 = " : "
            java.lang.String r8 = " on class "
            int r9 = r3 + r4
            int r10 = r0.length
            r11 = 0
            if (r9 <= r10) goto L_0x004c
            java.io.PrintStream r5 = java.lang.System.err
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Warning: Skipping record of type "
            r6.append(r7)
            r6.append(r1)
            java.lang.String r1 = " at position "
            r6.append(r1)
            r6.append(r3)
            java.lang.String r1 = " which claims to be longer than the file! ("
            r6.append(r1)
            r6.append(r4)
            java.lang.String r1 = " vs "
            r6.append(r1)
            int r0 = r0.length
            int r0 = r0 - r3
            r6.append(r0)
            java.lang.String r0 = ")"
            r6.append(r0)
            java.lang.String r0 = r6.toString()
            r5.println(r0)
            return r11
        L_0x004c:
            int r9 = (int) r1
            java.lang.Class r11 = com.app.office.fc.hslf.record.RecordTypes.recordHandlingClass(r9)     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            if (r11 != 0) goto L_0x005c
            com.app.office.fc.hslf.record.RecordTypes$Type r9 = com.app.office.fc.hslf.record.RecordTypes.Unknown     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            int r9 = r9.typeID     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            java.lang.Class r9 = com.app.office.fc.hslf.record.RecordTypes.recordHandlingClass(r9)     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            r11 = r9
        L_0x005c:
            r9 = 3
            java.lang.Class[] r10 = new java.lang.Class[r9]     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            java.lang.Class<byte[]> r12 = byte[].class
            r13 = 0
            r10[r13] = r12     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            java.lang.Class r12 = java.lang.Integer.TYPE     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            r14 = 1
            r10[r14] = r12     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            java.lang.Class r12 = java.lang.Integer.TYPE     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            r15 = 2
            r10[r15] = r12     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            java.lang.reflect.Constructor r10 = r11.getDeclaredConstructor(r10)     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            r9[r13] = r0     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r19)     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            r9[r14] = r0     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r20)     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            r9[r15] = r0     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            java.lang.Object r0 = r10.newInstance(r9)     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            com.app.office.fc.hslf.record.Record r0 = (com.app.office.fc.hslf.record.Record) r0     // Catch:{ InstantiationException -> 0x0107, InvocationTargetException -> 0x00d9, IllegalAccessException -> 0x00b7, NoSuchMethodException -> 0x0095 }
            boolean r1 = r0 instanceof com.app.office.fc.hslf.record.PositionDependentRecord
            if (r1 == 0) goto L_0x0094
            r1 = r0
            com.app.office.fc.hslf.record.PositionDependentRecord r1 = (com.app.office.fc.hslf.record.PositionDependentRecord) r1
            r2 = r21
            r1.setLastOnDiskOffset(r2)
        L_0x0094:
            return r0
        L_0x0095:
            r0 = move-exception
            java.lang.RuntimeException r3 = new java.lang.RuntimeException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r5)
            r4.append(r1)
            r4.append(r8)
            r4.append(r11)
            r4.append(r7)
            r4.append(r0)
            java.lang.String r1 = r4.toString()
            r3.<init>(r1, r0)
            throw r3
        L_0x00b7:
            r0 = move-exception
            java.lang.RuntimeException r3 = new java.lang.RuntimeException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r5)
            r4.append(r1)
            r4.append(r8)
            r4.append(r11)
            r4.append(r7)
            r4.append(r0)
            java.lang.String r1 = r4.toString()
            r3.<init>(r1, r0)
            throw r3
        L_0x00d9:
            r0 = move-exception
            java.lang.RuntimeException r3 = new java.lang.RuntimeException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r6)
            r4.append(r1)
            r4.append(r8)
            r4.append(r11)
            r4.append(r7)
            r4.append(r0)
            java.lang.String r1 = "\nCause was : "
            r4.append(r1)
            java.lang.Throwable r1 = r0.getCause()
            r4.append(r1)
            java.lang.String r1 = r4.toString()
            r3.<init>(r1, r0)
            throw r3
        L_0x0107:
            r0 = move-exception
            java.lang.RuntimeException r3 = new java.lang.RuntimeException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r6)
            r4.append(r1)
            r4.append(r8)
            r4.append(r11)
            r4.append(r7)
            r4.append(r0)
            java.lang.String r1 = r4.toString()
            r3.<init>(r1, r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hslf.record.Record.createRecordForType(long, byte[], int, int, int):com.app.office.fc.hslf.record.Record");
    }
}
