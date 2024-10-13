package com.app.office.ss.model.baseModel;

import com.app.office.ss.other.ExpandedCellRangeAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowProperty {
    public static final short ROWPROPID_COMPLETED = 1;
    public static final short ROWPROPID_EXPANDEDRANGEADDRLIST = 3;
    public static final short ROWPROPID_INITEXPANDEDRANGEADDR = 2;
    public static final short ROWPROPID_ZEROHEIGHT = 0;
    private Map<Short, Object> rowPropMap = new HashMap();

    public void setRowProperty(short s, Object obj) {
        if (s != 3) {
            this.rowPropMap.put(Short.valueOf(s), obj);
            return;
        }
        List list = (List) this.rowPropMap.get((short) 3);
        if (list == null) {
            list = new ArrayList();
            this.rowPropMap.put(Short.valueOf(s), list);
        }
        list.add((ExpandedCellRangeAddress) obj);
    }

    public boolean isZeroHeight() {
        Object obj = this.rowPropMap.get((short) 0);
        if (obj != null) {
            return ((Boolean) obj).booleanValue();
        }
        return false;
    }

    public boolean isCompleted() {
        Object obj = this.rowPropMap.get((short) 1);
        if (obj != null) {
            return ((Boolean) obj).booleanValue();
        }
        return false;
    }

    public boolean isInitExpandedRangeAddr() {
        Object obj = this.rowPropMap.get((short) 2);
        if (obj != null) {
            return ((Boolean) obj).booleanValue();
        }
        return false;
    }

    public int getExpandedCellCount() {
        List list = (List) this.rowPropMap.get((short) 3);
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public ExpandedCellRangeAddress getExpandedCellRangeAddr(int i) {
        List list = (List) this.rowPropMap.get((short) 3);
        if (list == null) {
            return null;
        }
        return (ExpandedCellRangeAddress) list.get(i);
    }

    public void dispose() {
        List<ExpandedCellRangeAddress> list = (List) this.rowPropMap.get((short) 3);
        if (list != null) {
            for (ExpandedCellRangeAddress dispose : list) {
                dispose.dispose();
            }
        }
    }
}
