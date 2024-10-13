package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.PropertyNode;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.util.Arrays;
import java.util.Comparator;

@Internal
public abstract class PropertyNode<T extends PropertyNode<T>> implements Comparable<T>, Cloneable {
    private static final POILogger _logger = POILogFactory.getLogger(PropertyNode.class);
    protected Object _buf;
    private int _cpEnd;
    private int _cpStart;

    public static final class EndComparator implements Comparator<PropertyNode<?>> {
        public static EndComparator instance = new EndComparator();

        public int compare(PropertyNode<?> propertyNode, PropertyNode<?> propertyNode2) {
            int end = propertyNode.getEnd();
            int end2 = propertyNode2.getEnd();
            if (end < end2) {
                return -1;
            }
            return end == end2 ? 0 : 1;
        }
    }

    public static final class StartComparator implements Comparator<PropertyNode<?>> {
        public static StartComparator instance = new StartComparator();

        public int compare(PropertyNode<?> propertyNode, PropertyNode<?> propertyNode2) {
            int start = propertyNode.getStart();
            int start2 = propertyNode2.getStart();
            if (start < start2) {
                return -1;
            }
            return start == start2 ? 0 : 1;
        }
    }

    protected PropertyNode(int i, int i2, Object obj) {
        this._cpStart = i;
        this._cpEnd = i2;
        this._buf = obj;
        if (i < 0) {
            POILogger pOILogger = _logger;
            int i3 = POILogger.WARN;
            pOILogger.log(i3, (Object) "A property claimed to start before zero, at " + this._cpStart + "! Resetting it to zero, and hoping for the best");
            this._cpStart = 0;
        }
        if (this._cpEnd < this._cpStart) {
            POILogger pOILogger2 = _logger;
            int i4 = POILogger.WARN;
            pOILogger2.log(i4, (Object) "A property claimed to end (" + this._cpEnd + ") before start! Resetting end to start, and hoping for the best");
            this._cpEnd = this._cpStart;
        }
    }

    public int getStart() {
        return this._cpStart;
    }

    public void setStart(int i) {
        this._cpStart = i;
    }

    public int getEnd() {
        return this._cpEnd;
    }

    public void setEnd(int i) {
        this._cpEnd = i;
    }

    public void adjustForDelete(int i, int i2) {
        int i3 = i + i2;
        int i4 = this._cpEnd;
        if (i4 > i) {
            int i5 = this._cpStart;
            if (i5 < i3) {
                this._cpEnd = i3 >= i4 ? i : i4 - i2;
                this._cpStart = Math.min(i, i5);
                return;
            }
            this._cpEnd = i4 - i2;
            this._cpStart = i5 - i2;
        }
    }

    /* access modifiers changed from: protected */
    public boolean limitsAreEqual(Object obj) {
        PropertyNode propertyNode = (PropertyNode) obj;
        return propertyNode.getStart() == this._cpStart && propertyNode.getEnd() == this._cpEnd;
    }

    public int hashCode() {
        return (this._cpStart * 31) + this._buf.hashCode();
    }

    public boolean equals(Object obj) {
        if (!limitsAreEqual(obj)) {
            return false;
        }
        Object obj2 = ((PropertyNode) obj)._buf;
        if (obj2 instanceof byte[]) {
            Object obj3 = this._buf;
            if (obj3 instanceof byte[]) {
                return Arrays.equals((byte[]) obj2, (byte[]) obj3);
            }
        }
        return this._buf.equals(obj2);
    }

    public T clone() throws CloneNotSupportedException {
        return (PropertyNode) super.clone();
    }

    public int compareTo(T t) {
        int end = t.getEnd();
        int i = this._cpEnd;
        if (i == end) {
            return 0;
        }
        return i < end ? -1 : 1;
    }
}
