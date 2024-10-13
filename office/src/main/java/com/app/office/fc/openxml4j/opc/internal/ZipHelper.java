package com.app.office.fc.openxml4j.opc.internal;

import com.app.office.fc.openxml4j.opc.PackageRelationship;
import com.app.office.fc.openxml4j.opc.PackageRelationshipTypes;
import com.app.office.fc.openxml4j.opc.ZipPackage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class ZipHelper {
    private static final String FORWARD_SLASH = "/";
    public static final int READ_WRITE_FILE_BUFFER_SIZE = 8192;

    private ZipHelper() {
    }

    public static ZipEntry getCorePropertiesZipEntry(ZipPackage zipPackage) {
        PackageRelationship relationship = zipPackage.getRelationshipsByType(PackageRelationshipTypes.CORE_PROPERTIES).getRelationship(0);
        if (relationship == null) {
            return null;
        }
        return new ZipEntry(relationship.getTargetURI().getPath());
    }

    public static ZipEntry getContentTypeZipEntry(ZipPackage zipPackage) {
        Enumeration<? extends ZipEntry> entries = zipPackage.getZipArchive().getEntries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = (ZipEntry) entries.nextElement();
            if (zipEntry.getName().equals(ContentTypeManager.CONTENT_TYPES_PART_NAME)) {
                return zipEntry;
            }
        }
        return null;
    }

    public static String getOPCNameFromZipItemName(String str) {
        if (str == null) {
            throw new IllegalArgumentException("zipItemName");
        } else if (str.startsWith("/")) {
            return str;
        } else {
            return "/" + str;
        }
    }

    public static String getZipItemNameFromOPCName(String str) {
        if (str != null) {
            while (str.startsWith("/")) {
                str = str.substring(1);
            }
            return str;
        }
        throw new IllegalArgumentException("opcItemName");
    }

    public static URI getZipURIFromOPCName(String str) {
        if (str != null) {
            while (str.startsWith("/")) {
                str = str.substring(1);
            }
            try {
                return new URI(str);
            } catch (URISyntaxException unused) {
                return null;
            }
        } else {
            throw new IllegalArgumentException("opcItemName");
        }
    }

    public static ZipFile openZipFile(String str) {
        File file = new File(str);
        try {
            if (!file.exists()) {
                return null;
            }
            return new ZipFile(file);
        } catch (IOException unused) {
            return null;
        }
    }
}
