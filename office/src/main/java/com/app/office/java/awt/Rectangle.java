package com.app.office.java.awt;

import android.graphics.Point;
import com.app.office.java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Rectangle extends Rectangle2D implements Shape, Serializable {
    private static final long serialVersionUID = -4345857070255674764L;
    public int height;
    public int width;
    public int x;
    public int y;

    private static native void initIDs();

    public Rectangle() {
        this(0, 0, 0, 0);
    }

    public Rectangle(Rectangle rectangle) {
        this(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public Rectangle(int i, int i2, int i3, int i4) {
        this.x = i;
        this.y = i2;
        this.width = i3;
        this.height = i4;
    }

    public Rectangle(int i, int i2) {
        this(0, 0, i, i2);
    }

    public Rectangle(Point point, Dimension dimension) {
        this(point.x, point.y, dimension.width, dimension.height);
    }

    public Rectangle(Point point) {
        this(point.x, point.y, 0, 0);
    }

    public Rectangle(Dimension dimension) {
        this(0, 0, dimension.width, dimension.height);
    }

    public double getX() {
        return (double) this.x;
    }

    public double getY() {
        return (double) this.y;
    }

    public double getWidth() {
        return (double) this.width;
    }

    public double getHeight() {
        return (double) this.height;
    }

    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public Rectangle2D getBounds2D() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public void setBounds(Rectangle rectangle) {
        setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void setBounds(int i, int i2, int i3, int i4) {
        reshape(i, i2, i3, i4);
    }

    public void setRect(double d, double d2, double d3, double d4) {
        int i;
        int i2;
        double d5 = d;
        double d6 = d2;
        boolean z = true;
        int i3 = -1;
        int i4 = Integer.MAX_VALUE;
        if (d5 > 4.294967294E9d) {
            i2 = -1;
            i = Integer.MAX_VALUE;
        } else {
            i = clip(d5, false);
            double d7 = d3 >= 0.0d ? d3 + (d5 - ((double) i)) : d3;
            i2 = clip(d7, d7 >= 0.0d);
        }
        if (d6 <= 4.294967294E9d) {
            i4 = clip(d6, false);
            double d8 = d4 >= 0.0d ? d4 + (d6 - ((double) i4)) : d4;
            if (d8 < 0.0d) {
                z = false;
            }
            i3 = clip(d8, z);
        }
        reshape(i, i4, i2, i3);
    }

    private static int clip(double d, boolean z) {
        if (d <= -2.147483648E9d) {
            return Integer.MIN_VALUE;
        }
        if (d >= 2.147483647E9d) {
            return Integer.MAX_VALUE;
        }
        return (int) (z ? Math.ceil(d) : Math.floor(d));
    }

    @Deprecated
    public void reshape(int i, int i2, int i3, int i4) {
        this.x = i;
        this.y = i2;
        this.width = i3;
        this.height = i4;
    }

    public Point getLocation() {
        return new Point(this.x, this.y);
    }

    public void setLocation(Point point) {
        setLocation(point.x, point.y);
    }

    public void setLocation(int i, int i2) {
        move(i, i2);
    }

    @Deprecated
    public void move(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public void translate(int i, int i2) {
        int i3 = this.x;
        int i4 = i3 + i;
        int i5 = Integer.MIN_VALUE;
        if (i < 0) {
            if (i4 > i3) {
                int i6 = this.width;
                if (i6 >= 0) {
                    this.width = i6 + (i4 - Integer.MIN_VALUE);
                }
                i4 = Integer.MIN_VALUE;
            }
        } else if (i4 < i3) {
            int i7 = this.width;
            if (i7 >= 0) {
                int i8 = i7 + (i4 - Integer.MAX_VALUE);
                this.width = i8;
                if (i8 < 0) {
                    this.width = Integer.MAX_VALUE;
                }
            }
            i4 = Integer.MAX_VALUE;
        }
        this.x = i4;
        int i9 = this.y;
        int i10 = i9 + i2;
        if (i2 < 0) {
            if (i10 > i9) {
                int i11 = this.height;
                if (i11 >= 0) {
                    this.height = i11 + (i10 - Integer.MIN_VALUE);
                }
                this.y = i5;
            }
        } else if (i10 < i9) {
            int i12 = this.height;
            if (i12 >= 0) {
                int i13 = i12 + (i10 - Integer.MAX_VALUE);
                this.height = i13;
                if (i13 < 0) {
                    this.height = Integer.MAX_VALUE;
                }
            }
            i5 = Integer.MAX_VALUE;
            this.y = i5;
        }
        i5 = i10;
        this.y = i5;
    }

    public Dimension getSize() {
        return new Dimension(this.width, this.height);
    }

    public void setSize(Dimension dimension) {
        setSize(dimension.width, dimension.height);
    }

    public void setSize(int i, int i2) {
        resize(i, i2);
    }

    @Deprecated
    public void resize(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public boolean contains(Point point) {
        return contains(point.x, point.y);
    }

    public boolean contains(int i, int i2) {
        return inside(i, i2);
    }

    public boolean contains(Rectangle rectangle) {
        return contains(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public boolean contains(int i, int i2, int i3, int i4) {
        int i5 = this.width;
        int i6 = this.height;
        if ((i5 | i6 | i3 | i4) < 0) {
            return false;
        }
        int i7 = this.x;
        int i8 = this.y;
        if (i < i7 || i2 < i8) {
            return false;
        }
        int i9 = i5 + i7;
        int i10 = i3 + i;
        if (i10 <= i) {
            if (i9 >= i7 || i10 > i9) {
                return false;
            }
        } else if (i9 >= i7 && i10 > i9) {
            return false;
        }
        int i11 = i6 + i8;
        int i12 = i4 + i2;
        if (i12 <= i2) {
            if (i11 >= i8 || i12 > i11) {
                return false;
            }
            return true;
        } else if (i11 < i8 || i12 <= i11) {
            return true;
        } else {
            return false;
        }
    }

    @Deprecated
    public boolean inside(int i, int i2) {
        int i3 = this.width;
        int i4 = this.height;
        if ((i3 | i4) < 0) {
            return false;
        }
        int i5 = this.x;
        int i6 = this.y;
        if (i < i5 || i2 < i6) {
            return false;
        }
        int i7 = i3 + i5;
        int i8 = i4 + i6;
        if (i7 >= i5 && i7 <= i) {
            return false;
        }
        if (i8 < i6 || i8 > i2) {
            return true;
        }
        return false;
    }

    public boolean intersects(Rectangle rectangle) {
        int i = this.width;
        int i2 = this.height;
        int i3 = rectangle.width;
        int i4 = rectangle.height;
        if (i3 <= 0 || i4 <= 0 || i <= 0 || i2 <= 0) {
            return false;
        }
        int i5 = this.x;
        int i6 = this.y;
        int i7 = rectangle.x;
        int i8 = rectangle.y;
        int i9 = i3 + i7;
        int i10 = i4 + i8;
        int i11 = i + i5;
        int i12 = i2 + i6;
        if (i9 >= i7 && i9 <= i5) {
            return false;
        }
        if (i10 >= i8 && i10 <= i6) {
            return false;
        }
        if (i11 >= i5 && i11 <= i7) {
            return false;
        }
        if (i12 < i6 || i12 > i8) {
            return true;
        }
        return false;
    }

    public Rectangle intersection(Rectangle rectangle) {
        int i = this.x;
        int i2 = this.y;
        int i3 = rectangle.x;
        int i4 = rectangle.y;
        long j = ((long) i) + ((long) this.width);
        long j2 = ((long) i2) + ((long) this.height);
        long j3 = ((long) i3) + ((long) rectangle.width);
        long j4 = ((long) i4) + ((long) rectangle.height);
        if (i < i3) {
            i = i3;
        }
        if (i2 < i4) {
            i2 = i4;
        }
        if (j > j3) {
            j = j3;
        }
        if (j2 > j4) {
            j2 = j4;
        }
        long j5 = j - ((long) i);
        long j6 = j2 - ((long) i2);
        if (j5 < -2147483648L) {
            j5 = -2147483648L;
        }
        if (j6 < -2147483648L) {
            j6 = -2147483648L;
        }
        return new Rectangle(i, i2, (int) j5, (int) j6);
    }

    public Rectangle union(Rectangle rectangle) {
        long j = (long) this.width;
        long j2 = (long) this.height;
        if ((j | j2) < 0) {
            return new Rectangle(rectangle);
        }
        long j3 = (long) rectangle.width;
        long j4 = (long) rectangle.height;
        if ((j3 | j4) < 0) {
            return new Rectangle(this);
        }
        int i = this.x;
        int i2 = this.y;
        long j5 = j + ((long) i);
        long j6 = j2 + ((long) i2);
        int i3 = rectangle.x;
        int i4 = rectangle.y;
        long j7 = j3 + ((long) i3);
        long j8 = j4 + ((long) i4);
        if (i > i3) {
            i = i3;
        }
        if (i2 > i4) {
            i2 = i4;
        }
        if (j5 < j7) {
            j5 = j7;
        }
        if (j6 < j8) {
            j6 = j8;
        }
        long j9 = j5 - ((long) i);
        long j10 = j6 - ((long) i2);
        if (j9 > 2147483647L) {
            j9 = 2147483647L;
        }
        if (j10 > 2147483647L) {
            j10 = 2147483647L;
        }
        return new Rectangle(i, i2, (int) j9, (int) j10);
    }

    public void add(int i, int i2) {
        int i3 = this.width;
        int i4 = this.height;
        if ((i3 | i4) < 0) {
            this.x = i;
            this.y = i2;
            this.height = 0;
            this.width = 0;
            return;
        }
        int i5 = this.x;
        int i6 = this.y;
        long j = ((long) i3) + ((long) i5);
        long j2 = ((long) i4) + ((long) i6);
        if (i5 > i) {
            i5 = i;
        }
        if (i6 > i2) {
            i6 = i2;
        }
        long j3 = (long) i;
        if (j < j3) {
            j = j3;
        }
        long j4 = (long) i2;
        if (j2 < j4) {
            j2 = j4;
        }
        long j5 = j - ((long) i5);
        long j6 = j2 - ((long) i6);
        if (j5 > 2147483647L) {
            j5 = 2147483647L;
        }
        if (j6 > 2147483647L) {
            j6 = 2147483647L;
        }
        reshape(i5, i6, (int) j5, (int) j6);
    }

    public void add(Point point) {
        add(point.x, point.y);
    }

    public void add(Rectangle rectangle) {
        long j = (long) this.width;
        long j2 = (long) this.height;
        if ((j | j2) < 0) {
            reshape(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        long j3 = (long) rectangle.width;
        long j4 = (long) rectangle.height;
        if ((j3 | j4) >= 0) {
            int i = this.x;
            int i2 = this.y;
            long j5 = j + ((long) i);
            long j6 = j2 + ((long) i2);
            int i3 = rectangle.x;
            int i4 = rectangle.y;
            long j7 = j3 + ((long) i3);
            long j8 = j4 + ((long) i4);
            if (i > i3) {
                i = i3;
            }
            if (i2 > i4) {
                i2 = i4;
            }
            if (j5 < j7) {
                j5 = j7;
            }
            if (j6 < j8) {
                j6 = j8;
            }
            long j9 = j5 - ((long) i);
            long j10 = j6 - ((long) i2);
            if (j9 > 2147483647L) {
                j9 = 2147483647L;
            }
            if (j10 > 2147483647L) {
                j10 = 2147483647L;
            }
            reshape(i, i2, (int) j9, (int) j10);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0073, code lost:
        if (r6 > 2147483647L) goto L_0x0075;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void grow(int r12, int r13) {
        /*
            r11 = this;
            int r0 = r11.x
            long r0 = (long) r0
            int r2 = r11.y
            long r2 = (long) r2
            int r4 = r11.width
            long r4 = (long) r4
            int r6 = r11.height
            long r6 = (long) r6
            long r4 = r4 + r0
            long r6 = r6 + r2
            long r8 = (long) r12
            long r0 = r0 - r8
            long r12 = (long) r13
            long r2 = r2 - r12
            long r4 = r4 + r8
            long r6 = r6 + r12
            r12 = 2147483647(0x7fffffff, double:1.060997895E-314)
            r8 = -2147483648(0xffffffff80000000, double:NaN)
            int r10 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r10 >= 0) goto L_0x0030
            long r4 = r4 - r0
            int r10 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r10 >= 0) goto L_0x0024
            r4 = r8
        L_0x0024:
            int r10 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r10 >= 0) goto L_0x002a
            r0 = r8
            goto L_0x0047
        L_0x002a:
            int r10 = (r0 > r12 ? 1 : (r0 == r12 ? 0 : -1))
            if (r10 <= 0) goto L_0x0047
            r0 = r12
            goto L_0x0047
        L_0x0030:
            int r10 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r10 >= 0) goto L_0x0036
            r0 = r8
            goto L_0x003b
        L_0x0036:
            int r10 = (r0 > r12 ? 1 : (r0 == r12 ? 0 : -1))
            if (r10 <= 0) goto L_0x003b
            r0 = r12
        L_0x003b:
            long r4 = r4 - r0
            int r10 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r10 >= 0) goto L_0x0042
            r4 = r8
            goto L_0x0047
        L_0x0042:
            int r10 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r10 <= 0) goto L_0x0047
            r4 = r12
        L_0x0047:
            int r10 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r10 >= 0) goto L_0x005f
            long r6 = r6 - r2
            int r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r10 >= 0) goto L_0x0051
            r6 = r8
        L_0x0051:
            int r10 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r10 >= 0) goto L_0x0058
            r12 = r6
            r2 = r8
            goto L_0x0075
        L_0x0058:
            int r8 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1))
            if (r8 <= 0) goto L_0x005d
            r2 = r12
        L_0x005d:
            r12 = r6
            goto L_0x0075
        L_0x005f:
            int r10 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r10 >= 0) goto L_0x0065
            r2 = r8
            goto L_0x006a
        L_0x0065:
            int r10 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1))
            if (r10 <= 0) goto L_0x006a
            r2 = r12
        L_0x006a:
            long r6 = r6 - r2
            int r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r10 >= 0) goto L_0x0071
            r12 = r8
            goto L_0x0075
        L_0x0071:
            int r8 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1))
            if (r8 <= 0) goto L_0x005d
        L_0x0075:
            int r1 = (int) r0
            int r0 = (int) r2
            int r2 = (int) r4
            int r13 = (int) r12
            r11.reshape(r1, r0, r2, r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.java.awt.Rectangle.grow(int, int):void");
    }

    public boolean isEmpty() {
        return this.width <= 0 || this.height <= 0;
    }

    public int outcode(double d, double d2) {
        int i;
        int i2 = this.width;
        if (i2 <= 0) {
            i = 5;
        } else {
            int i3 = this.x;
            i = d < ((double) i3) ? 1 : d > ((double) i3) + ((double) i2) ? 4 : 0;
        }
        int i4 = this.height;
        if (i4 <= 0) {
            return i | 10;
        }
        int i5 = this.y;
        if (d2 < ((double) i5)) {
            return i | 2;
        }
        return d2 > ((double) i5) + ((double) i4) ? i | 8 : i;
    }

    public Rectangle2D createIntersection(Rectangle2D rectangle2D) {
        if (rectangle2D instanceof Rectangle) {
            return intersection((Rectangle) rectangle2D);
        }
        Rectangle2D.Double doubleR = new Rectangle2D.Double();
        Rectangle2D.intersect(this, rectangle2D, doubleR);
        return doubleR;
    }

    public Rectangle2D createUnion(Rectangle2D rectangle2D) {
        if (rectangle2D instanceof Rectangle) {
            return union((Rectangle) rectangle2D);
        }
        Rectangle2D.Double doubleR = new Rectangle2D.Double();
        Rectangle2D.union(this, rectangle2D, doubleR);
        return doubleR;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Rectangle)) {
            return super.equals(obj);
        }
        Rectangle rectangle = (Rectangle) obj;
        return this.x == rectangle.x && this.y == rectangle.y && this.width == rectangle.width && this.height == rectangle.height;
    }

    public String toString() {
        return getClass().getName() + "[x=" + this.x + ",y=" + this.y + ",width=" + this.width + ",height=" + this.height + "]";
    }
}
