package com.app.office.common.bookmark;

import java.util.HashMap;
import java.util.Map;

public class BookmarkManage {
    private Map<String, Bookmark> bms = new HashMap();

    public void addBookmark(Bookmark bookmark) {
        this.bms.put(bookmark.getName(), bookmark);
    }

    public Bookmark getBookmark(String str) {
        return this.bms.get(str);
    }

    public int getBookmarkCount() {
        return this.bms.size();
    }

    public void dispose() {
        Map<String, Bookmark> map = this.bms;
        if (map != null) {
            map.clear();
            this.bms = null;
        }
    }
}
