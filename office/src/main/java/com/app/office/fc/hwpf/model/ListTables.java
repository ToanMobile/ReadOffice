package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.io.HWPFOutputStream;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

@Internal
public final class ListTables {
    private static final int LIST_DATA_SIZE = 28;
    private static final int LIST_FORMAT_OVERRIDE_SIZE = 16;
    private static POILogger log = POILogFactory.getLogger(ListTables.class);
    ListMap _listMap = new ListMap();
    ArrayList<ListFormatOverride> _overrideList = new ArrayList<>();

    public ListTables() {
    }

    public ListTables(byte[] bArr, int i, int i2) {
        short s = LittleEndian.getShort(bArr, i);
        int i3 = i + 2;
        int i4 = (s * 28) + i3;
        for (int i5 = 0; i5 < s; i5++) {
            POIListData pOIListData = new POIListData(bArr, i3);
            this._listMap.put(Integer.valueOf(pOIListData.getLsid()), pOIListData);
            i3 += 28;
            int numLevels = pOIListData.numLevels();
            for (int i6 = 0; i6 < numLevels; i6++) {
                POIListLevel pOIListLevel = new POIListLevel(bArr, i4);
                pOIListData.setLevel(i6, pOIListLevel);
                i4 += pOIListLevel.getSizeInBytes();
            }
        }
        int i7 = LittleEndian.getInt(bArr, i2);
        int i8 = i2 + 4;
        int i9 = (i7 * 16) + i8;
        for (int i10 = 0; i10 < i7; i10++) {
            ListFormatOverride listFormatOverride = new ListFormatOverride(bArr, i8);
            i8 += 16;
            int numOverrides = listFormatOverride.numOverrides();
            for (int i11 = 0; i11 < numOverrides; i11++) {
                while (i9 < bArr.length && bArr[i9] == -1) {
                    i9++;
                }
                if (i9 < bArr.length) {
                    ListFormatOverrideLevel listFormatOverrideLevel = new ListFormatOverrideLevel(bArr, i9);
                    listFormatOverride.setOverride(i11, listFormatOverrideLevel);
                    i9 += listFormatOverrideLevel.getSizeInBytes();
                }
            }
            this._overrideList.add(listFormatOverride);
        }
    }

    public int addList(POIListData pOIListData, ListFormatOverride listFormatOverride) {
        int lsid = pOIListData.getLsid();
        while (this._listMap.get((Object) Integer.valueOf(lsid)) != null) {
            lsid = pOIListData.resetListID();
            listFormatOverride.setLsid(lsid);
        }
        this._listMap.put(Integer.valueOf(lsid), pOIListData);
        this._overrideList.add(listFormatOverride);
        return lsid;
    }

    public void writeListDataTo(HWPFOutputStream hWPFOutputStream) throws IOException {
        int size = this._listMap.size();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[2];
        LittleEndian.putShort(bArr, (short) size);
        hWPFOutputStream.write(bArr);
        for (Integer num : this._listMap.sortedKeys()) {
            POIListData pOIListData = this._listMap.get((Object) num);
            hWPFOutputStream.write(pOIListData.toByteArray());
            POIListLevel[] levels = pOIListData.getLevels();
            for (POIListLevel byteArray : levels) {
                byteArrayOutputStream.write(byteArray.toByteArray());
            }
        }
        hWPFOutputStream.write(byteArrayOutputStream.toByteArray());
    }

    public void writeListOverridesTo(HWPFOutputStream hWPFOutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int size = this._overrideList.size();
        byte[] bArr = new byte[4];
        LittleEndian.putInt(bArr, size);
        hWPFOutputStream.write(bArr);
        for (int i = 0; i < size; i++) {
            ListFormatOverride listFormatOverride = this._overrideList.get(i);
            hWPFOutputStream.write(listFormatOverride.toByteArray());
            ListFormatOverrideLevel[] levelOverrides = listFormatOverride.getLevelOverrides();
            for (ListFormatOverrideLevel byteArray : levelOverrides) {
                byteArrayOutputStream.write(byteArray.toByteArray());
            }
        }
        hWPFOutputStream.write(byteArrayOutputStream.toByteArray());
    }

    public ListFormatOverride getOverride(int i) {
        if (this._overrideList.size() >= i) {
            return this._overrideList.get(i - 1);
        }
        return null;
    }

    public int getOverrideCount() {
        return this._overrideList.size();
    }

    public int getOverrideIndexFromListID(int i) {
        int i2;
        int size = this._overrideList.size();
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                i2 = -1;
                break;
            } else if (this._overrideList.get(i3).getLsid() == i) {
                i2 = i3 + 1;
                break;
            } else {
                i3++;
            }
        }
        if (i2 != -1) {
            return i2;
        }
        throw new NoSuchElementException("No list found with the specified ID");
    }

    public POIListLevel getLevel(int i, int i2) {
        POIListData pOIListData = this._listMap.get((Object) Integer.valueOf(i));
        if (i2 < pOIListData.numLevels()) {
            return pOIListData.getLevels()[i2];
        }
        POILogger pOILogger = log;
        int i3 = POILogger.WARN;
        pOILogger.log(i3, (Object) "Requested level " + i2 + " which was greater than the maximum defined (" + pOIListData.numLevels() + ")");
        return null;
    }

    public POIListData getListData(int i) {
        return this._listMap.get((Object) Integer.valueOf(i));
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        ListTables listTables = (ListTables) obj;
        if (this._listMap.size() == listTables._listMap.size()) {
            for (Integer next : this._listMap.keySet()) {
                if (!this._listMap.get((Object) next).equals(listTables._listMap.get((Object) next))) {
                    return false;
                }
            }
            int size = this._overrideList.size();
            if (size == listTables._overrideList.size()) {
                for (int i = 0; i < size; i++) {
                    if (!this._overrideList.get(i).equals(listTables._overrideList.get(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private static class ListMap implements Map<Integer, POIListData> {
        private ArrayList<Integer> keyList;
        private HashMap<Integer, POIListData> parent;

        private ListMap() {
            this.keyList = new ArrayList<>();
            this.parent = new HashMap<>();
        }

        public void clear() {
            this.keyList.clear();
            this.parent.clear();
        }

        public boolean containsKey(Object obj) {
            return this.parent.containsKey(obj);
        }

        public boolean containsValue(Object obj) {
            return this.parent.containsValue(obj);
        }

        public POIListData get(Object obj) {
            return this.parent.get(obj);
        }

        public boolean isEmpty() {
            return this.parent.isEmpty();
        }

        public POIListData put(Integer num, POIListData pOIListData) {
            this.keyList.add(num);
            return this.parent.put(num, pOIListData);
        }

        public void putAll(Map<? extends Integer, ? extends POIListData> map) {
            for (Map.Entry next : map.entrySet()) {
                put((Integer) next.getKey(), (POIListData) next.getValue());
            }
        }

        public POIListData remove(Object obj) {
            this.keyList.remove(obj);
            return this.parent.remove(obj);
        }

        public int size() {
            return this.parent.size();
        }

        public Set<Map.Entry<Integer, POIListData>> entrySet() {
            throw new IllegalStateException("Use sortedKeys() + get() instead");
        }

        public List<Integer> sortedKeys() {
            return Collections.unmodifiableList(this.keyList);
        }

        public Set<Integer> keySet() {
            throw new IllegalStateException("Use sortedKeys() instead");
        }

        public Collection<POIListData> values() {
            ArrayList arrayList = new ArrayList();
            Iterator<Integer> it = this.keyList.iterator();
            while (it.hasNext()) {
                arrayList.add(this.parent.get(it.next()));
            }
            return arrayList;
        }
    }
}
