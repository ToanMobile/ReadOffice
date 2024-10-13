package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.model.BookmarksTables;
import com.app.office.fc.hwpf.model.GenericPropertyNode;
import com.app.office.fc.hwpf.model.PropertyNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BookmarksImpl implements Bookmarks {
    /* access modifiers changed from: private */
    public final BookmarksTables bookmarksTables;
    private Map<Integer, List<GenericPropertyNode>> sortedDescriptors = null;
    private int[] sortedStartPositions = null;

    public BookmarksImpl(BookmarksTables bookmarksTables2) {
        this.bookmarksTables = bookmarksTables2;
    }

    private POIBookmark getBookmark(GenericPropertyNode genericPropertyNode) {
        return new BookmarkImpl(genericPropertyNode);
    }

    public POIBookmark getBookmark(int i) {
        return getBookmark(this.bookmarksTables.getDescriptorFirst(i));
    }

    public List<POIBookmark> getBookmarksAt(int i) {
        updateSortedDescriptors();
        List<GenericPropertyNode> list = this.sortedDescriptors.get(Integer.valueOf(i));
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (GenericPropertyNode bookmark : list) {
            arrayList.add(getBookmark(bookmark));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public int getBookmarksCount() {
        return this.bookmarksTables.getDescriptorsFirstCount();
    }

    public Map<Integer, List<POIBookmark>> getBookmarksStartedBetween(int i, int i2) {
        updateSortedDescriptors();
        int binarySearch = Arrays.binarySearch(this.sortedStartPositions, i);
        if (binarySearch < 0) {
            binarySearch = -(binarySearch + 1);
        }
        int binarySearch2 = Arrays.binarySearch(this.sortedStartPositions, i2);
        if (binarySearch2 < 0) {
            binarySearch2 = -(binarySearch2 + 1);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        while (binarySearch < binarySearch2) {
            int i3 = this.sortedStartPositions[binarySearch];
            if (i3 >= i) {
                if (i3 >= i2) {
                    break;
                }
                List<POIBookmark> bookmarksAt = getBookmarksAt(i3);
                if (bookmarksAt != null) {
                    linkedHashMap.put(Integer.valueOf(i3), bookmarksAt);
                }
            }
            binarySearch++;
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    private void updateSortedDescriptors() {
        if (this.sortedDescriptors == null) {
            HashMap hashMap = new HashMap();
            int i = 0;
            for (int i2 = 0; i2 < this.bookmarksTables.getDescriptorsFirstCount(); i2++) {
                GenericPropertyNode descriptorFirst = this.bookmarksTables.getDescriptorFirst(i2);
                Integer valueOf = Integer.valueOf(descriptorFirst.getStart());
                List list = (List) hashMap.get(valueOf);
                if (list == null) {
                    list = new LinkedList();
                    hashMap.put(valueOf, list);
                }
                list.add(descriptorFirst);
            }
            int[] iArr = new int[hashMap.size()];
            for (Map.Entry entry : hashMap.entrySet()) {
                int i3 = i + 1;
                iArr[i] = ((Integer) entry.getKey()).intValue();
                ArrayList arrayList = new ArrayList((Collection) entry.getValue());
                Collections.sort(arrayList, PropertyNode.EndComparator.instance);
                entry.setValue(arrayList);
                i = i3;
            }
            Arrays.sort(iArr);
            this.sortedDescriptors = hashMap;
            this.sortedStartPositions = iArr;
        }
    }

    private final class BookmarkImpl implements POIBookmark {
        private final GenericPropertyNode first;

        private BookmarkImpl(GenericPropertyNode genericPropertyNode) {
            this.first = genericPropertyNode;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            BookmarkImpl bookmarkImpl = (BookmarkImpl) obj;
            GenericPropertyNode genericPropertyNode = this.first;
            if (genericPropertyNode == null) {
                if (bookmarkImpl.first != null) {
                    return false;
                }
            } else if (!genericPropertyNode.equals(bookmarkImpl.first)) {
                return false;
            }
            return true;
        }

        public int getEnd() {
            try {
                return BookmarksImpl.this.bookmarksTables.getDescriptorLim(BookmarksImpl.this.bookmarksTables.getDescriptorFirstIndex(this.first)).getStart();
            } catch (IndexOutOfBoundsException unused) {
                return this.first.getEnd();
            }
        }

        public String getName() {
            try {
                return BookmarksImpl.this.bookmarksTables.getName(BookmarksImpl.this.bookmarksTables.getDescriptorFirstIndex(this.first));
            } catch (ArrayIndexOutOfBoundsException unused) {
                return "";
            }
        }

        public int getStart() {
            return this.first.getStart();
        }

        public int hashCode() {
            GenericPropertyNode genericPropertyNode = this.first;
            return (genericPropertyNode == null ? 0 : genericPropertyNode.hashCode()) + 31;
        }

        public void setName(String str) {
            BookmarksImpl.this.bookmarksTables.setName(BookmarksImpl.this.bookmarksTables.getDescriptorFirstIndex(this.first), str);
        }

        public String toString() {
            return "Bookmark [" + getStart() + "; " + getEnd() + "): name: " + getName();
        }
    }
}
