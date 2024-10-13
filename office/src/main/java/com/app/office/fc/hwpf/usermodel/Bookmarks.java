package com.app.office.fc.hwpf.usermodel;

import java.util.List;
import java.util.Map;

public interface Bookmarks {
    POIBookmark getBookmark(int i) throws IndexOutOfBoundsException;

    int getBookmarksCount();

    Map<Integer, List<POIBookmark>> getBookmarksStartedBetween(int i, int i2);
}
