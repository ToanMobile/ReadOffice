package com.app.office.ss.model.baseModel;

import com.app.office.common.hyperlink.Hyperlink;
import com.app.office.simpletext.view.STRoot;
import com.app.office.ss.model.table.SSTable;
import java.util.Map;
import java.util.TreeMap;

public class CellProperty {
    public static final short CELLPROPID_EXPANDRANGADDRESS = 2;
    public static final short CELLPROPID_HYPERLINK = 3;
    public static final short CELLPROPID_MERGEDRANGADDRESS = 1;
    public static final short CELLPROPID_NUMERICTYPE = 0;
    public static final short CELLPROPID_STROOT = 4;
    public static final short CELLPROPID_TABLEINFO = 5;
    private Map<Short, Object> props = new TreeMap();

    public void setCellProp(short s, Object obj) {
        this.props.put(Short.valueOf(s), obj);
    }

    public Object getCellProp(short s) {
        return this.props.get(Short.valueOf(s));
    }

    public short getCellNumericType() {
        Object obj = this.props.get((short) 0);
        if (obj != null) {
            return ((Short) obj).shortValue();
        }
        return -1;
    }

    public int getCellMergeRangeAddressIndex() {
        Object obj = this.props.get((short) 1);
        if (obj != null) {
            return ((Integer) obj).intValue();
        }
        return -1;
    }

    public int getExpandCellRangeAddressIndex() {
        Object obj = this.props.get((short) 2);
        if (obj != null) {
            return ((Integer) obj).intValue();
        }
        return -1;
    }

    public Hyperlink getCellHyperlink() {
        Object obj = this.props.get((short) 3);
        if (obj != null) {
            return (Hyperlink) obj;
        }
        return null;
    }

    public int getCellSTRoot() {
        Object obj = this.props.get((short) 4);
        if (obj != null) {
            return ((Integer) obj).intValue();
        }
        return -1;
    }

    public SSTable getTableInfo() {
        Object obj = this.props.get((short) 5);
        if (obj != null) {
            return (SSTable) obj;
        }
        return null;
    }

    public void removeCellSTRoot() {
        this.props.remove((short) 4);
    }

    public void dispose() {
        for (Object next : this.props.values()) {
            if (next instanceof Hyperlink) {
                ((Hyperlink) next).dispose();
            } else if (next instanceof STRoot) {
                ((STRoot) next).dispose();
            }
        }
    }
}
