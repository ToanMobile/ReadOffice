package com.app.office.fc.hslf.model;

import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.hslf.record.ExHyperlink;
import com.app.office.fc.hslf.record.ExObjList;
import com.app.office.fc.hslf.record.InteractiveInfo;
import com.app.office.fc.hslf.record.InteractiveInfoAtom;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.hslf.record.TxInteractiveInfoAtom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Hyperlink {
    public static final byte LINK_FIRSTSLIDE = 2;
    public static final byte LINK_LASTSLIDE = 3;
    public static final byte LINK_NEXTSLIDE = 0;
    public static final byte LINK_NULL = -1;
    public static final byte LINK_PREVIOUSSLIDE = 1;
    public static final byte LINK_URL = 8;
    private String address;
    private int endIndex;
    private int id = -1;
    private int startIndex;
    private String title;
    private int type;

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
        if (i == 0) {
            this.title = "NEXT";
            this.address = "1,-1,NEXT";
        } else if (i == 1) {
            this.title = "PREV";
            this.address = "1,-1,PREV";
        } else if (i == 2) {
            this.title = "FIRST";
            this.address = "1,-1,FIRST";
        } else if (i != 3) {
            this.title = "";
            this.address = "";
        } else {
            this.title = "LAST";
            this.address = "1,-1,LAST";
        }
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public int getEndIndex() {
        return this.endIndex;
    }

    protected static Hyperlink[] find(TextRun textRun) {
        ArrayList arrayList = new ArrayList();
        ExObjList exObjList = textRun.getSheet().getSlideShow().getDocumentRecord().getExObjList();
        if (exObjList == null) {
            return null;
        }
        Record[] recordArr = textRun._records;
        if (recordArr != null) {
            find(recordArr, exObjList, arrayList);
        }
        if (arrayList.size() <= 0) {
            return null;
        }
        Hyperlink[] hyperlinkArr = new Hyperlink[arrayList.size()];
        arrayList.toArray(hyperlinkArr);
        return hyperlinkArr;
    }

    protected static Hyperlink find(Shape shape) {
        ArrayList arrayList = new ArrayList();
        ExObjList exObjList = shape.getSheet().getSlideShow().getDocumentRecord().getExObjList();
        if (exObjList == null) {
            return null;
        }
        Iterator<EscherRecord> childIterator = shape.getSpContainer().getChildIterator();
        while (childIterator.hasNext()) {
            EscherRecord next = childIterator.next();
            if (next.getRecordId() == -4079) {
                byte[] serialize = next.serialize();
                Record[] findChildRecords = Record.findChildRecords(serialize, 8, serialize.length - 8);
                if (findChildRecords != null) {
                    find(findChildRecords, exObjList, arrayList);
                }
            }
        }
        if (arrayList.size() == 1) {
            return (Hyperlink) arrayList.get(0);
        }
        return null;
    }

    private static void find(Record[] recordArr, ExObjList exObjList, List list) {
        int i = 0;
        while (i < recordArr.length) {
            if (recordArr[i] instanceof InteractiveInfo) {
                InteractiveInfoAtom interactiveInfoAtom = recordArr[i].getInteractiveInfoAtom();
                ExHyperlink exHyperlink = exObjList.get(interactiveInfoAtom.getHyperlinkID());
                if (exHyperlink != null) {
                    Hyperlink hyperlink = new Hyperlink();
                    hyperlink.title = exHyperlink.getLinkTitle();
                    hyperlink.address = exHyperlink.getLinkURL();
                    hyperlink.type = interactiveInfoAtom.getAction();
                    i++;
                    if (i < recordArr.length && (recordArr[i] instanceof TxInteractiveInfoAtom)) {
                        TxInteractiveInfoAtom txInteractiveInfoAtom = recordArr[i];
                        hyperlink.startIndex = txInteractiveInfoAtom.getStartIndex();
                        hyperlink.endIndex = txInteractiveInfoAtom.getEndIndex();
                    }
                    list.add(hyperlink);
                }
            }
            i++;
        }
    }
}
