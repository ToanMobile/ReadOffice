package com.app.office.fc.hslf.record;

import com.app.office.fc.util.ArrayUtil;
import java.util.ArrayList;

public abstract class RecordContainer extends Record {
    protected Record[] _children;
    private Boolean changingChildRecordsLock = Boolean.TRUE;

    public boolean isAnAtom() {
        return false;
    }

    public Record[] getChildRecords() {
        return this._children;
    }

    private int findChildLocation(Record record) {
        synchronized (this.changingChildRecordsLock) {
            int i = 0;
            while (true) {
                Record[] recordArr = this._children;
                if (i >= recordArr.length) {
                    return -1;
                }
                if (recordArr[i].equals(record)) {
                    return i;
                }
                i++;
            }
        }
    }

    private void appendChild(Record record) {
        synchronized (this.changingChildRecordsLock) {
            Record[] recordArr = this._children;
            Record[] recordArr2 = new Record[(recordArr.length + 1)];
            System.arraycopy(recordArr, 0, recordArr2, 0, recordArr.length);
            recordArr2[this._children.length] = record;
            this._children = recordArr2;
        }
    }

    private void addChildAt(Record record, int i) {
        synchronized (this.changingChildRecordsLock) {
            appendChild(record);
            moveChildRecords(this._children.length - 1, i, 1);
        }
    }

    private void moveChildRecords(int i, int i2, int i3) {
        if (i != i2 && i3 != 0) {
            int i4 = i + i3;
            Record[] recordArr = this._children;
            if (i4 <= recordArr.length) {
                ArrayUtil.arrayMoveWithin(recordArr, i, i2, i3);
                return;
            }
            throw new IllegalArgumentException("Asked to move more records than there are!");
        }
    }

    public Record findFirstOfType(long j) {
        int i = 0;
        while (true) {
            Record[] recordArr = this._children;
            if (i >= recordArr.length) {
                return null;
            }
            if (recordArr[i].getRecordType() == j) {
                return this._children[i];
            }
            i++;
        }
    }

    public Record removeChild(Record record) {
        ArrayList arrayList = new ArrayList();
        Record record2 = null;
        for (Record record3 : this._children) {
            if (record3 != record) {
                arrayList.add(record3);
            } else {
                record2 = record3;
            }
        }
        this._children = (Record[]) arrayList.toArray(new Record[arrayList.size()]);
        return record2;
    }

    public void appendChildRecord(Record record) {
        synchronized (this.changingChildRecordsLock) {
            appendChild(record);
        }
    }

    public void addChildAfter(Record record, Record record2) {
        synchronized (this.changingChildRecordsLock) {
            int findChildLocation = findChildLocation(record2);
            if (findChildLocation != -1) {
                addChildAt(record, findChildLocation + 1);
            } else {
                throw new IllegalArgumentException("Asked to add a new child after another record, but that record wasn't one of our children!");
            }
        }
    }

    public void addChildBefore(Record record, Record record2) {
        synchronized (this.changingChildRecordsLock) {
            int findChildLocation = findChildLocation(record2);
            if (findChildLocation != -1) {
                addChildAt(record, findChildLocation);
            } else {
                throw new IllegalArgumentException("Asked to add a new child before another record, but that record wasn't one of our children!");
            }
        }
    }

    public void moveChildBefore(Record record, Record record2) {
        moveChildrenBefore(record, 1, record2);
    }

    public void moveChildrenBefore(Record record, int i, Record record2) {
        if (i >= 1) {
            synchronized (this.changingChildRecordsLock) {
                int findChildLocation = findChildLocation(record2);
                if (findChildLocation != -1) {
                    int findChildLocation2 = findChildLocation(record);
                    if (findChildLocation2 != -1) {
                        moveChildRecords(findChildLocation2, findChildLocation, i);
                    } else {
                        throw new IllegalArgumentException("Asked to move a record that wasn't a child!");
                    }
                } else {
                    throw new IllegalArgumentException("Asked to move children before another record, but that record wasn't one of our children!");
                }
            }
        }
    }

    public void moveChildrenAfter(Record record, int i, Record record2) {
        if (i >= 1) {
            synchronized (this.changingChildRecordsLock) {
                int findChildLocation = findChildLocation(record2);
                if (findChildLocation != -1) {
                    int i2 = findChildLocation + 1;
                    int findChildLocation2 = findChildLocation(record);
                    if (findChildLocation2 != -1) {
                        moveChildRecords(findChildLocation2, i2, i);
                    } else {
                        throw new IllegalArgumentException("Asked to move a record that wasn't a child!");
                    }
                } else {
                    throw new IllegalArgumentException("Asked to move children before another record, but that record wasn't one of our children!");
                }
            }
        }
    }

    public void setChildRecord(Record[] recordArr) {
        this._children = recordArr;
    }

    public static void handleParentAwareRecords(RecordContainer recordContainer) {
        for (Record record : recordContainer.getChildRecords()) {
            if (record instanceof ParentAwareRecord) {
                ((ParentAwareRecord) record).setParentRecord(recordContainer);
            }
            if (record instanceof RecordContainer) {
                handleParentAwareRecords((RecordContainer) record);
            }
        }
    }

    public void dispose() {
        Record[] recordArr = this._children;
        if (recordArr != null) {
            for (Record record : recordArr) {
                if (record != null) {
                    record.dispose();
                }
            }
            this._children = null;
        }
    }
}
