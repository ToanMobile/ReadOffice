package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.model.FieldsDocumentPart;
import com.app.office.fc.hwpf.model.FieldsTables;
import com.app.office.fc.hwpf.model.PlexOfField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldsImpl implements Fields {
    private Map<FieldsDocumentPart, Map<Integer, FieldImpl>> _fieldsByOffset = new HashMap(FieldsDocumentPart.values().length);
    private PlexOfFieldComparator comparator = new PlexOfFieldComparator();

    private static <T> int binarySearch(List<PlexOfField> list, int i, int i2, int i3) {
        checkIndexForBinarySearch(list.size(), i, i2);
        int i4 = i2 - 1;
        int i5 = -1;
        int i6 = i;
        while (i6 <= i4) {
            i5 = (i6 + i4) >>> 1;
            int fcStart = list.get(i5).getFcStart();
            if (fcStart == i3) {
                return i5;
            }
            if (fcStart < i3) {
                i6 = i5 + 1;
            } else {
                i4 = i5 - 1;
            }
        }
        if (i5 >= 0) {
            return (-i5) - 1;
        }
        int i7 = i2;
        while (i < i2) {
            if (i3 < list.get(i).getFcStart()) {
                i7 = i;
            }
            i++;
        }
        return (-i7) - 1;
    }

    private static void checkIndexForBinarySearch(int i, int i2, int i3) {
        if (i2 > i3) {
            throw new IllegalArgumentException();
        } else if (i < i3 || i2 < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public FieldsImpl(FieldsTables fieldsTables) {
        for (FieldsDocumentPart fieldsDocumentPart : FieldsDocumentPart.values()) {
            this._fieldsByOffset.put(fieldsDocumentPart, parseFieldStructure(fieldsTables.getFieldsPLCF(fieldsDocumentPart)));
        }
    }

    public Collection<Field> getFields(FieldsDocumentPart fieldsDocumentPart) {
        Map map = this._fieldsByOffset.get(fieldsDocumentPart);
        if (map == null || map.isEmpty()) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableCollection(map.values());
    }

    public FieldImpl getFieldByStartOffset(FieldsDocumentPart fieldsDocumentPart, int i) {
        Map map = this._fieldsByOffset.get(fieldsDocumentPart);
        if (map == null || map.isEmpty()) {
            return null;
        }
        return (FieldImpl) map.get(Integer.valueOf(i));
    }

    private Map<Integer, FieldImpl> parseFieldStructure(List<PlexOfField> list) {
        if (list == null || list.isEmpty()) {
            return new HashMap();
        }
        Collections.sort(list, this.comparator);
        ArrayList<FieldImpl> arrayList = new ArrayList<>((list.size() / 3) + 1);
        parseFieldStructureImpl(list, 0, list.size(), arrayList);
        HashMap hashMap = new HashMap(arrayList.size());
        for (FieldImpl fieldImpl : arrayList) {
            hashMap.put(Integer.valueOf(fieldImpl.getFieldStartOffset()), fieldImpl);
        }
        return hashMap;
    }

    private void parseFieldStructureImpl(List<PlexOfField> list, int i, int i2, List<FieldImpl> list2) {
        while (i < i2) {
            PlexOfField plexOfField = list.get(i);
            if (plexOfField.getFld().getBoundaryType() != 19) {
                i++;
            } else {
                i++;
                int binarySearch = binarySearch(list, i, i2, plexOfField.getFcEnd());
                if (binarySearch >= 0) {
                    PlexOfField plexOfField2 = list.get(binarySearch);
                    int boundaryType = plexOfField2.getFld().getBoundaryType();
                    if (boundaryType == 20) {
                        int binarySearch2 = binarySearch(list, binarySearch, i2, plexOfField2.getFcEnd());
                        if (binarySearch2 >= 0) {
                            PlexOfField plexOfField3 = list.get(binarySearch2);
                            if (plexOfField3.getFld().getBoundaryType() == 21) {
                                list2.add(new FieldImpl(plexOfField, plexOfField2, plexOfField3));
                                if (plexOfField.getFcStart() + 1 < plexOfField2.getFcStart() - 1) {
                                    parseFieldStructureImpl(list, i, binarySearch, list2);
                                }
                                if (plexOfField2.getFcStart() + 1 < plexOfField3.getFcStart() - 1) {
                                    parseFieldStructureImpl(list, binarySearch + 1, binarySearch2, list2);
                                }
                                i = binarySearch2 + 1;
                            }
                        }
                    } else if (boundaryType == 21) {
                        list2.add(new FieldImpl(plexOfField, (PlexOfField) null, plexOfField2));
                        if (plexOfField.getFcStart() + 1 < plexOfField2.getFcStart() - 1) {
                            parseFieldStructureImpl(list, i, binarySearch, list2);
                        }
                        i = binarySearch + 1;
                    }
                }
            }
        }
    }

    private static final class PlexOfFieldComparator implements Comparator<PlexOfField> {
        private PlexOfFieldComparator() {
        }

        public int compare(PlexOfField plexOfField, PlexOfField plexOfField2) {
            int fcStart = plexOfField.getFcStart();
            int fcStart2 = plexOfField2.getFcStart();
            if (fcStart < fcStart2) {
                return -1;
            }
            return fcStart == fcStart2 ? 0 : 1;
        }
    }
}
