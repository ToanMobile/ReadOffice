package com.app.office.fc.poifs.filesystem;

import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.io.File;

public class POIFSDocumentPath {
    private static final POILogger log = POILogFactory.getLogger(POIFSDocumentPath.class);
    private String[] components;
    private int hashcode;

    public POIFSDocumentPath(String[] strArr) throws IllegalArgumentException {
        this.hashcode = 0;
        if (strArr == null) {
            this.components = new String[0];
            return;
        }
        this.components = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i] == null || strArr[i].length() == 0) {
                throw new IllegalArgumentException("components cannot contain null or empty strings");
            }
            this.components[i] = strArr[i];
        }
    }

    public POIFSDocumentPath() {
        this.hashcode = 0;
        this.components = new String[0];
    }

    public POIFSDocumentPath(POIFSDocumentPath pOIFSDocumentPath, String[] strArr) throws IllegalArgumentException {
        int i = 0;
        this.hashcode = 0;
        if (strArr == null) {
            this.components = new String[pOIFSDocumentPath.components.length];
        } else {
            this.components = new String[(pOIFSDocumentPath.components.length + strArr.length)];
        }
        int i2 = 0;
        while (true) {
            String[] strArr2 = pOIFSDocumentPath.components;
            if (i2 >= strArr2.length) {
                break;
            }
            this.components[i2] = strArr2[i2];
            i2++;
        }
        if (strArr != null) {
            while (i < strArr.length) {
                if (strArr[i] != null) {
                    if (strArr[i].length() == 0) {
                        POILogger pOILogger = log;
                        int i3 = POILogger.WARN;
                        pOILogger.log(i3, (Object) "Directory under " + pOIFSDocumentPath + " has an empty name, not all OLE2 readers will handle this file correctly!");
                    }
                    this.components[pOIFSDocumentPath.components.length + i] = strArr[i];
                    i++;
                } else {
                    throw new IllegalArgumentException("components cannot contain null");
                }
            }
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            if (this == obj) {
                return true;
            }
            POIFSDocumentPath pOIFSDocumentPath = (POIFSDocumentPath) obj;
            if (pOIFSDocumentPath.components.length == this.components.length) {
                int i = 0;
                while (true) {
                    String[] strArr = this.components;
                    if (i >= strArr.length) {
                        return true;
                    }
                    if (!pOIFSDocumentPath.components[i].equals(strArr[i])) {
                        break;
                    }
                    i++;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        if (this.hashcode == 0) {
            int i = 0;
            while (true) {
                String[] strArr = this.components;
                if (i >= strArr.length) {
                    break;
                }
                this.hashcode += strArr[i].hashCode();
                i++;
            }
        }
        return this.hashcode;
    }

    public int length() {
        return this.components.length;
    }

    public String getComponent(int i) throws ArrayIndexOutOfBoundsException {
        return this.components[i];
    }

    public POIFSDocumentPath getParent() {
        int length = this.components.length - 1;
        if (length < 0) {
            return null;
        }
        POIFSDocumentPath pOIFSDocumentPath = new POIFSDocumentPath((String[]) null);
        String[] strArr = new String[length];
        pOIFSDocumentPath.components = strArr;
        System.arraycopy(this.components, 0, strArr, 0, length);
        return pOIFSDocumentPath;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        int length = length();
        stringBuffer.append(File.separatorChar);
        for (int i = 0; i < length; i++) {
            stringBuffer.append(getComponent(i));
            if (i < length - 1) {
                stringBuffer.append(File.separatorChar);
            }
        }
        return stringBuffer.toString();
    }
}
