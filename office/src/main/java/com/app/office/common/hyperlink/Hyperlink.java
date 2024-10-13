package com.app.office.common.hyperlink;

public class Hyperlink {
    public static final int LINK_BOOKMARK = 5;
    public static final int LINK_DOCUMENT = 2;
    public static final int LINK_EMAIL = 3;
    public static final int LINK_FILE = 4;
    public static final int LINK_URL = 1;
    private String address;
    private int id = -1;
    private String title;
    private int type;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getLinkType() {
        return this.type;
    }

    public void setLinkType(int i) {
        this.type = i;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void dispose() {
        this.address = null;
        this.title = null;
    }
}
