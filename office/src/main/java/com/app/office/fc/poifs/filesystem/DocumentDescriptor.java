package com.app.office.fc.poifs.filesystem;

import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import java.util.Objects;

public class DocumentDescriptor {
    private int hashcode = 0;
    private String name;
    private POIFSDocumentPath path;

    public DocumentDescriptor(POIFSDocumentPath pOIFSDocumentPath, String str) {
        Objects.requireNonNull(pOIFSDocumentPath, "path must not be null");
        Objects.requireNonNull(str, "name must not be null");
        if (str.length() != 0) {
            this.path = pOIFSDocumentPath;
            this.name = str;
            return;
        }
        throw new IllegalArgumentException("name cannot be empty");
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            if (this == obj) {
                return true;
            }
            DocumentDescriptor documentDescriptor = (DocumentDescriptor) obj;
            if (!this.path.equals(documentDescriptor.path) || !this.name.equals(documentDescriptor.name)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.hashcode == 0) {
            this.hashcode = this.path.hashCode() ^ this.name.hashCode();
        }
        return this.hashcode;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer((this.path.length() + 1) * 40);
        for (int i = 0; i < this.path.length(); i++) {
            stringBuffer.append(this.path.getComponent(i));
            stringBuffer.append(PackagingURIHelper.FORWARD_SLASH_STRING);
        }
        stringBuffer.append(this.name);
        return stringBuffer.toString();
    }
}
