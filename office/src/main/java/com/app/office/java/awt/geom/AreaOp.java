package com.app.office.java.awt.geom;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Vector;

public abstract class AreaOp {
    public static final int CTAG_LEFT = 0;
    public static final int CTAG_RIGHT = 1;
    public static final int ETAG_ENTER = 1;
    public static final int ETAG_EXIT = -1;
    public static final int ETAG_IGNORE = 0;
    private static ChainEnd[] EmptyChainList = new ChainEnd[2];
    private static CurveLink[] EmptyLinkList = new CurveLink[2];
    public static final int RSTAG_INSIDE = 1;
    public static final int RSTAG_OUTSIDE = -1;
    private static Comparator YXTopComparator = new Comparator() {
        public int compare(Object obj, Object obj2) {
            Curve curve = ((Edge) obj).getCurve();
            Curve curve2 = ((Edge) obj2).getCurve();
            double yTop = curve.getYTop();
            double yTop2 = curve2.getYTop();
            if (yTop == yTop2) {
                yTop = curve.getXTop();
                yTop2 = curve2.getXTop();
                if (yTop == yTop2) {
                    return 0;
                }
            }
            return yTop < yTop2 ? -1 : 1;
        }
    };

    public static class AddOp extends CAGOp {
        public boolean newClassification(boolean z, boolean z2) {
            return z || z2;
        }
    }

    public static class IntOp extends CAGOp {
        public boolean newClassification(boolean z, boolean z2) {
            return z && z2;
        }
    }

    public static class SubOp extends CAGOp {
        public boolean newClassification(boolean z, boolean z2) {
            return z && !z2;
        }
    }

    public static class XorOp extends CAGOp {
        public boolean newClassification(boolean z, boolean z2) {
            return z != z2;
        }
    }

    public static boolean obstructs(double d, double d2, int i) {
        if ((i & 1) == 0) {
            if (d <= d2) {
                return true;
            }
        } else if (d < d2) {
            return true;
        }
        return false;
    }

    public abstract int classify(Edge edge);

    public abstract int getState();

    public abstract void newRow();

    public static abstract class CAGOp extends AreaOp {
        boolean inLeft;
        boolean inResult;
        boolean inRight;

        public abstract boolean newClassification(boolean z, boolean z2);

        public CAGOp() {
            super();
        }

        public void newRow() {
            this.inLeft = false;
            this.inRight = false;
            this.inResult = false;
        }

        public int classify(Edge edge) {
            if (edge.getCurveTag() == 0) {
                this.inLeft = !this.inLeft;
            } else {
                this.inRight = !this.inRight;
            }
            boolean newClassification = newClassification(this.inLeft, this.inRight);
            if (this.inResult == newClassification) {
                return 0;
            }
            this.inResult = newClassification;
            if (newClassification) {
                return 1;
            }
            return -1;
        }

        public int getState() {
            return this.inResult ? 1 : -1;
        }
    }

    public static class NZWindOp extends AreaOp {
        private int count;

        public NZWindOp() {
            super();
        }

        public void newRow() {
            this.count = 0;
        }

        public int classify(Edge edge) {
            int i = this.count;
            int i2 = i == 0 ? 1 : 0;
            int direction = i + edge.getCurve().getDirection();
            this.count = direction;
            if (direction == 0) {
                return -1;
            }
            return i2;
        }

        public int getState() {
            return this.count == 0 ? -1 : 1;
        }
    }

    public static class EOWindOp extends AreaOp {
        private boolean inside;

        public EOWindOp() {
            super();
        }

        public void newRow() {
            this.inside = false;
        }

        public int classify(Edge edge) {
            boolean z = !this.inside;
            this.inside = z;
            if (z) {
                return 1;
            }
            return -1;
        }

        public int getState() {
            return this.inside ? 1 : -1;
        }
    }

    private AreaOp() {
    }

    public Vector calculate(Vector vector, Vector vector2) {
        Vector vector3 = new Vector();
        addEdges(vector3, vector, 0);
        addEdges(vector3, vector2, 1);
        return pruneEdges(vector3);
    }

    private static void addEdges(Vector vector, Vector vector2, int i) {
        Enumeration elements = vector2.elements();
        while (elements.hasMoreElements()) {
            Curve curve = (Curve) elements.nextElement();
            if (curve.getOrder() > 0) {
                vector.add(new Edge(curve, i));
            }
        }
    }

    private Vector pruneEdges(Vector vector) {
        CurveLink curveLink;
        Vector vector2;
        Vector vector3;
        int i;
        int i2;
        double d;
        int i3;
        int i4;
        int i5;
        Vector vector4 = vector;
        int size = vector.size();
        if (size < 2) {
            return vector4;
        }
        Edge[] edgeArr = (Edge[]) vector4.toArray(new Edge[size]);
        Arrays.sort(edgeArr, YXTopComparator);
        double[] dArr = new double[2];
        Vector vector5 = new Vector();
        Vector vector6 = new Vector();
        Vector vector7 = new Vector();
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i7 < size) {
            double d2 = dArr[i6];
            int i9 = i8 - 1;
            int i10 = i9;
            while (i9 >= i7) {
                Edge edge = edgeArr[i9];
                if (edge.getCurve().getYBot() > d2) {
                    if (i10 > i9) {
                        edgeArr[i10] = edge;
                    }
                    i10--;
                }
                i9--;
            }
            i7 = i10 + 1;
            if (i7 >= i8) {
                if (i8 >= size) {
                    break;
                }
                d2 = edgeArr[i8].getCurve().getYTop();
                if (d2 > dArr[i6]) {
                    finalizeSubCurves(vector5, vector6);
                }
                dArr[i6] = d2;
            }
            while (i8 < size && edgeArr[i8].getCurve().getYTop() <= d2) {
                i8++;
            }
            dArr[1] = edgeArr[i7].getCurve().getYBot();
            if (i8 < size) {
                double yTop = edgeArr[i8].getCurve().getYTop();
                if (dArr[1] > yTop) {
                    dArr[1] = yTop;
                }
            }
            int i11 = 1;
            for (int i12 = i7; i12 < i8; i12++) {
                Edge edge2 = edgeArr[i12];
                edge2.setEquivalence(i6);
                int i13 = i12;
                while (true) {
                    if (i13 <= i7) {
                        break;
                    }
                    Edge edge3 = edgeArr[i13 - 1];
                    int compareTo = edge2.compareTo(edge3, dArr);
                    if (dArr[1] <= dArr[i6]) {
                        throw new InternalError("backstepping to " + dArr[1] + " from " + dArr[i6]);
                    } else if (compareTo < 0) {
                        edgeArr[i13] = edge3;
                        i13--;
                    } else if (compareTo == 0) {
                        int equivalence = edge3.getEquivalence();
                        if (equivalence == 0) {
                            i5 = i11 + 1;
                            edge3.setEquivalence(i11);
                        } else {
                            int i14 = equivalence;
                            i5 = i11;
                            i11 = i14;
                        }
                        edge2.setEquivalence(i11);
                        i11 = i5;
                    }
                }
                edgeArr[i13] = edge2;
            }
            newRow();
            double d3 = dArr[i6];
            double d4 = dArr[1];
            int i15 = i7;
            while (i15 < i8) {
                Edge edge4 = edgeArr[i15];
                int equivalence2 = edge4.getEquivalence();
                if (equivalence2 != 0) {
                    int i16 = i15;
                    int state = getState();
                    vector3 = vector5;
                    int i17 = state == 1 ? -1 : 1;
                    double d5 = d4;
                    Edge edge5 = null;
                    Edge edge6 = edge4;
                    while (true) {
                        classify(edge4);
                        if (edge5 == null && edge4.isActiveFor(d3, i17)) {
                            edge5 = edge4;
                        }
                        double yBot = edge4.getCurve().getYBot();
                        if (yBot > d5) {
                            edge6 = edge4;
                            d5 = yBot;
                            i3 = i17;
                        } else {
                            i3 = i17;
                        }
                        i4 = i16 + 1;
                        if (i4 >= i8) {
                            vector2 = vector6;
                            break;
                        }
                        edge4 = edgeArr[i4];
                        vector2 = vector6;
                        if (edge4.getEquivalence() != equivalence2) {
                            break;
                        }
                        i16 = i4;
                        i17 = i3;
                        vector6 = vector2;
                    }
                    i2 = i4 - 1;
                    if (getState() == state) {
                        i3 = 0;
                    } else {
                        if (edge5 == null) {
                            edge5 = edge6;
                        }
                        edge4 = edge5;
                    }
                    i = i3;
                } else {
                    vector3 = vector5;
                    vector2 = vector6;
                    i = classify(edge4);
                    i2 = i15;
                }
                if (i != 0) {
                    edge4.record(d4, i);
                    d = d4;
                    vector7.add(new CurveLink(edge4.getCurve(), d3, d, i));
                } else {
                    d = d4;
                }
                i15 = i2 + 1;
                vector5 = vector3;
                d4 = d;
                vector6 = vector2;
            }
            Vector vector8 = vector5;
            Vector vector9 = vector6;
            double d6 = d4;
            if (getState() != -1) {
                System.out.println("Still inside at end of active edge list!");
                System.out.println("num curves = " + (i8 - i7));
                System.out.println("num links = " + vector7.size());
                System.out.println("y top = " + dArr[0]);
                if (i8 < size) {
                    System.out.println("y top of next curve = " + edgeArr[i8].getCurve().getYTop());
                } else {
                    System.out.println("no more curves");
                }
                for (int i18 = i7; i18 < i8; i18++) {
                    Edge edge7 = edgeArr[i18];
                    System.out.println(edge7);
                    int equivalence3 = edge7.getEquivalence();
                    if (equivalence3 != 0) {
                        System.out.println("  was equal to " + equivalence3 + "...");
                    }
                }
            }
            vector5 = vector8;
            vector6 = vector9;
            resolveLinks(vector5, vector6, vector7);
            vector7.clear();
            i6 = 0;
            dArr[0] = d6;
        }
        finalizeSubCurves(vector5, vector6);
        Vector vector10 = new Vector();
        Enumeration elements = vector5.elements();
        while (elements.hasMoreElements()) {
            CurveLink curveLink2 = (CurveLink) elements.nextElement();
            vector10.add(curveLink2.getMoveto());
            while (true) {
                curveLink = curveLink2;
                do {
                    curveLink2 = curveLink2.getNext();
                    if (curveLink2 == null) {
                        break;
                    }
                } while (curveLink.absorb(curveLink2));
                vector10.add(curveLink.getSubCurve());
            }
            vector10.add(curveLink.getSubCurve());
        }
        return vector10;
    }

    public static void finalizeSubCurves(Vector vector, Vector vector2) {
        int size = vector2.size();
        if (size != 0) {
            if ((size & 1) == 0) {
                ChainEnd[] chainEndArr = new ChainEnd[size];
                vector2.toArray(chainEndArr);
                for (int i = 1; i < size; i += 2) {
                    CurveLink linkTo = chainEndArr[i - 1].linkTo(chainEndArr[i]);
                    if (linkTo != null) {
                        vector.add(linkTo);
                    }
                }
                vector2.clear();
                return;
            }
            throw new InternalError("Odd number of chains!");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00d5  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00db  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void resolveLinks(java.util.Vector r18, java.util.Vector r19, java.util.Vector r20) {
        /*
            r0 = r19
            int r1 = r20.size()
            if (r1 != 0) goto L_0x000b
            com.app.office.java.awt.geom.CurveLink[] r1 = EmptyLinkList
            goto L_0x0018
        L_0x000b:
            r2 = r1 & 1
            if (r2 != 0) goto L_0x011a
            int r1 = r1 + 2
            com.app.office.java.awt.geom.CurveLink[] r1 = new com.app.office.java.awt.geom.CurveLink[r1]
            r2 = r20
            r2.toArray(r1)
        L_0x0018:
            int r2 = r19.size()
            java.lang.String r3 = "Odd number of chains!"
            if (r2 != 0) goto L_0x0023
            com.app.office.java.awt.geom.ChainEnd[] r2 = EmptyChainList
            goto L_0x002e
        L_0x0023:
            r4 = r2 & 1
            if (r4 != 0) goto L_0x0114
            int r2 = r2 + 2
            com.app.office.java.awt.geom.ChainEnd[] r2 = new com.app.office.java.awt.geom.ChainEnd[r2]
            r0.toArray(r2)
        L_0x002e:
            r19.clear()
            r4 = 0
            r5 = r2[r4]
            r6 = 1
            r7 = r2[r6]
            r8 = r1[r4]
            r9 = r1[r6]
            r10 = 0
            r11 = 0
        L_0x003d:
            if (r5 != 0) goto L_0x004f
            if (r8 == 0) goto L_0x0042
            goto L_0x004f
        L_0x0042:
            int r0 = r19.size()
            r0 = r0 & r6
            if (r0 == 0) goto L_0x004e
            java.io.PrintStream r0 = java.lang.System.out
            r0.println(r3)
        L_0x004e:
            return
        L_0x004f:
            if (r8 != 0) goto L_0x0053
            r12 = 1
            goto L_0x0054
        L_0x0053:
            r12 = 0
        L_0x0054:
            if (r5 != 0) goto L_0x0058
            r13 = 1
            goto L_0x0059
        L_0x0058:
            r13 = 0
        L_0x0059:
            if (r12 != 0) goto L_0x00bb
            if (r13 != 0) goto L_0x00bb
            r12 = r10 & 1
            if (r12 != 0) goto L_0x006f
            double r12 = r5.getX()
            double r14 = r7.getX()
            int r16 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r16 != 0) goto L_0x006f
            r12 = 1
            goto L_0x0070
        L_0x006f:
            r12 = 0
        L_0x0070:
            r13 = r11 & 1
            if (r13 != 0) goto L_0x0082
            double r13 = r8.getX()
            double r15 = r9.getX()
            int r17 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r17 != 0) goto L_0x0082
            r13 = 1
            goto L_0x0083
        L_0x0082:
            r13 = 0
        L_0x0083:
            if (r12 != 0) goto L_0x00bb
            if (r13 != 0) goto L_0x00bb
            double r12 = r5.getX()
            double r14 = r8.getX()
            if (r7 == 0) goto L_0x00a3
            int r16 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r16 >= 0) goto L_0x00a3
            r16 = r5
            double r4 = r7.getX()
            boolean r4 = obstructs(r4, r14, r10)
            if (r4 == 0) goto L_0x00a5
            r4 = 1
            goto L_0x00a6
        L_0x00a3:
            r16 = r5
        L_0x00a5:
            r4 = 0
        L_0x00a6:
            if (r9 == 0) goto L_0x00b8
            int r5 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r5 >= 0) goto L_0x00b8
            double r14 = r9.getX()
            boolean r5 = obstructs(r14, r12, r11)
            if (r5 == 0) goto L_0x00b8
            r13 = 1
            goto L_0x00b9
        L_0x00b8:
            r13 = 0
        L_0x00b9:
            r12 = r4
            goto L_0x00bd
        L_0x00bb:
            r16 = r5
        L_0x00bd:
            if (r12 == 0) goto L_0x00d5
            r5 = r16
            com.app.office.java.awt.geom.CurveLink r4 = r5.linkTo(r7)
            r14 = r18
            if (r4 == 0) goto L_0x00cc
            r14.add(r4)
        L_0x00cc:
            int r10 = r10 + 2
            r5 = r2[r10]
            int r4 = r10 + 1
            r7 = r2[r4]
            goto L_0x00d9
        L_0x00d5:
            r14 = r18
            r5 = r16
        L_0x00d9:
            if (r13 == 0) goto L_0x00f7
            com.app.office.java.awt.geom.ChainEnd r4 = new com.app.office.java.awt.geom.ChainEnd
            r15 = 0
            r4.<init>(r8, r15)
            com.app.office.java.awt.geom.ChainEnd r8 = new com.app.office.java.awt.geom.ChainEnd
            r8.<init>(r9, r4)
            r4.setOtherEnd(r8)
            r0.add(r4)
            r0.add(r8)
            int r11 = r11 + 2
            r8 = r1[r11]
            int r4 = r11 + 1
            r9 = r1[r4]
        L_0x00f7:
            if (r12 != 0) goto L_0x0111
            if (r13 != 0) goto L_0x0111
            r5.addLink(r8)
            r0.add(r5)
            int r10 = r10 + 1
            int r4 = r10 + 1
            r4 = r2[r4]
            int r11 = r11 + 1
            int r5 = r11 + 1
            r5 = r1[r5]
            r8 = r9
            r9 = r5
            r5 = r7
            r7 = r4
        L_0x0111:
            r4 = 0
            goto L_0x003d
        L_0x0114:
            java.lang.InternalError r0 = new java.lang.InternalError
            r0.<init>(r3)
            throw r0
        L_0x011a:
            java.lang.InternalError r0 = new java.lang.InternalError
            java.lang.String r1 = "Odd number of new curves!"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.java.awt.geom.AreaOp.resolveLinks(java.util.Vector, java.util.Vector, java.util.Vector):void");
    }
}
