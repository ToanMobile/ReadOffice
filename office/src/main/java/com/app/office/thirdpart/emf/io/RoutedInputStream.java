package com.app.office.thirdpart.emf.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RoutedInputStream extends DecodingInputStream {
    private static final int CLOSED = 5;
    private static final int CLOSING = 4;
    private static final int ROUTED = 3;
    private static final int ROUTEFOUND = 1;
    private static final int ROUTEINFORM = 2;
    private static final int UNROUTED = 0;
    private byte[] buffer = new byte[20];
    private int eob = 0;
    private InputStream in;
    private int index = 0;
    private Map listeners = new HashMap();
    private Map routes = new HashMap();
    private int sob = -1;
    private byte[] start;
    private int state = 0;

    public RoutedInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    public int read() throws IOException {
        while (true) {
            int i = this.state;
            if (i == 1) {
                int i2 = this.sob;
                if (i2 == this.index) {
                    this.state = 2;
                } else {
                    byte[] bArr = this.buffer;
                    byte b = bArr[i2];
                    this.sob = (i2 + 1) % bArr.length;
                    return b;
                }
            } else if (i == 2) {
                this.state = 3;
                byte[] bArr2 = this.start;
                ((RouteListener) this.listeners.get(this.start)).routeFound(new Route(bArr2, (byte[]) this.routes.get(bArr2)));
                this.state = 0;
                int i3 = this.sob;
                if (i3 == this.eob) {
                    this.sob = -1;
                    this.eob = 0;
                } else {
                    byte[] bArr3 = this.buffer;
                    byte b2 = bArr3[i3];
                    this.sob = (i3 + 1) % bArr3.length;
                    return b2;
                }
            } else if (i == 3) {
                int i4 = this.sob;
                if (i4 == this.eob) {
                    int read = this.in.read();
                    if (read >= 0) {
                        return read;
                    }
                    this.state = 5;
                } else {
                    byte[] bArr4 = this.buffer;
                    byte b3 = bArr4[i4];
                    this.sob = (i4 + 1) % bArr4.length;
                    return b3;
                }
            } else if (i != 4) {
                if (i == 5) {
                    return -1;
                }
                while (true) {
                    int i5 = this.sob;
                    if (i5 != this.eob) {
                        if (i5 < 0) {
                            this.sob = 0;
                        }
                        int read2 = this.in.read();
                        if (read2 < 0) {
                            this.state = 4;
                            break;
                        }
                        byte[] bArr5 = this.buffer;
                        int i6 = this.eob;
                        bArr5[i6] = (byte) read2;
                        this.eob = (i6 + 1) % bArr5.length;
                        Iterator it = this.routes.keySet().iterator();
                        while (true) {
                            if (it.hasNext()) {
                                byte[] bArr6 = (byte[]) it.next();
                                this.start = bArr6;
                                int i7 = this.eob;
                                byte[] bArr7 = this.buffer;
                                int length = ((i7 + bArr7.length) - bArr6.length) % bArr7.length;
                                this.index = length;
                                if (equals(bArr6, bArr7, length)) {
                                    this.state = 1;
                                    break;
                                }
                            }
                        }
                    } else {
                        byte[] bArr8 = this.buffer;
                        byte b4 = bArr8[i5];
                        this.sob = (i5 + 1) % bArr8.length;
                        return b4;
                    }
                }
            } else {
                int i8 = this.sob;
                if (i8 == this.eob) {
                    this.state = 5;
                } else {
                    byte[] bArr9 = this.buffer;
                    byte b5 = bArr9[i8];
                    this.sob = (i8 + 1) % bArr9.length;
                    return b5;
                }
            }
        }
    }

    public void addRoute(String str, String str2, RouteListener routeListener) {
        addRoute(str.getBytes(), str2 == null ? null : str2.getBytes(), routeListener);
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x0010  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addRoute(byte[] r5, byte[] r6, com.app.office.thirdpart.emf.io.RouteListener r7) {
        /*
            r4 = this;
            java.util.Map r0 = r4.routes
            java.util.Set r0 = r0.keySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x000a:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0051
            java.lang.String r1 = new java.lang.String
            java.lang.Object r2 = r0.next()
            byte[] r2 = (byte[]) r2
            r1.<init>(r2)
            java.lang.String r2 = new java.lang.String
            r2.<init>(r5)
            boolean r3 = r1.startsWith(r2)
            if (r3 != 0) goto L_0x002d
            boolean r3 = r2.startsWith(r1)
            if (r3 != 0) goto L_0x002d
            goto L_0x000a
        L_0x002d:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Route '"
            r6.append(r7)
            r6.append(r2)
            java.lang.String r7 = "' cannot be added since it overlaps with '"
            r6.append(r7)
            r6.append(r1)
            java.lang.String r7 = "'."
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L_0x0051:
            java.util.Map r0 = r4.routes
            r0.put(r5, r6)
            java.util.Map r6 = r4.listeners
            r6.put(r5, r7)
            int r6 = r5.length
            byte[] r7 = r4.buffer
            int r0 = r7.length
            int r0 = r0 + -1
            if (r6 <= r0) goto L_0x006f
            int r5 = r5.length
            int r5 = r5 + 1
            byte[] r5 = new byte[r5]
            int r6 = r7.length
            r0 = 0
            java.lang.System.arraycopy(r7, r0, r5, r0, r6)
            r4.buffer = r5
        L_0x006f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.thirdpart.emf.io.RoutedInputStream.addRoute(byte[], byte[], com.app.office.thirdpart.emf.io.RouteListener):void");
    }

    /* access modifiers changed from: private */
    public static boolean equals(byte[] bArr, byte[] bArr2, int i) {
        for (int length = bArr.length - 1; length > 0; length--) {
            if (bArr2[((bArr2.length + i) + length) % bArr2.length] != bArr[length]) {
                return false;
            }
        }
        if (bArr2[(i + bArr2.length) % bArr2.length] == bArr[0]) {
            return true;
        }
        return false;
    }

    public class Route extends InputStream {
        private byte[] buffer;
        private boolean closed;
        private byte[] end;
        private int index;
        private byte[] start;

        public Route(byte[] bArr, byte[] bArr2) {
            this.start = bArr;
            this.end = bArr2;
            if (bArr2 != null) {
                this.buffer = new byte[bArr2.length];
            }
            this.index = 0;
            this.closed = false;
        }

        public int read() throws IOException {
            if (this.closed) {
                return -1;
            }
            int read = RoutedInputStream.this.read();
            if (read < 0) {
                this.closed = true;
                return read;
            }
            byte[] bArr = this.end;
            if (bArr == null) {
                return read;
            }
            byte[] bArr2 = this.buffer;
            int i = this.index;
            bArr2[i] = (byte) read;
            int length = (i + 1) % bArr2.length;
            this.index = length;
            this.closed = RoutedInputStream.equals(bArr, bArr2, length);
            return read;
        }

        public void close() throws IOException {
            do {
            } while (read() >= 0);
            this.closed = true;
        }

        public byte[] getStart() {
            return this.start;
        }

        public byte[] getEnd() {
            return this.end;
        }
    }
}
