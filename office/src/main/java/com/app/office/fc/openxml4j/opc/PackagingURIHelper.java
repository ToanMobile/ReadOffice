package com.app.office.fc.openxml4j.opc;

import com.itextpdf.text.pdf.PdfWriter;
import com.app.office.fc.openxml4j.exceptions.InvalidFormatException;
import com.app.office.fc.openxml4j.exceptions.InvalidOperationException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

public final class PackagingURIHelper {
    public static final PackagePartName CORE_PROPERTIES_PART_NAME;
    public static final URI CORE_PROPERTIES_URI;
    public static final char FORWARD_SLASH_CHAR = '/';
    public static final String FORWARD_SLASH_STRING = "/";
    public static final String PACKAGE_CORE_PROPERTIES_NAME = "core.xml";
    public static final String PACKAGE_PROPERTIES_SEGMENT_NAME = "docProps";
    public static final PackagePartName PACKAGE_RELATIONSHIPS_ROOT_PART_NAME;
    public static final URI PACKAGE_RELATIONSHIPS_ROOT_URI;
    public static final PackagePartName PACKAGE_ROOT_PART_NAME;
    public static final URI PACKAGE_ROOT_URI;
    public static final String RELATIONSHIP_PART_EXTENSION_NAME = ".rels";
    public static final String RELATIONSHIP_PART_SEGMENT_NAME = "_rels";
    private static final char[] hexDigits = {'0', '1', PdfWriter.VERSION_1_2, PdfWriter.VERSION_1_3, PdfWriter.VERSION_1_4, PdfWriter.VERSION_1_5, PdfWriter.VERSION_1_6, PdfWriter.VERSION_1_7, '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static URI packageRootUri;

    static {
        URI uri;
        URI uri2;
        URI uri3;
        PackagePartName packagePartName;
        PackagePartName packagePartName2;
        PackagePartName packagePartName3 = null;
        try {
            uri2 = new URI(FORWARD_SLASH_STRING);
            try {
                uri = new URI(FORWARD_SLASH_CHAR + RELATIONSHIP_PART_SEGMENT_NAME + FORWARD_SLASH_CHAR + RELATIONSHIP_PART_EXTENSION_NAME);
                try {
                    packageRootUri = new URI(FORWARD_SLASH_STRING);
                    uri3 = new URI(FORWARD_SLASH_CHAR + PACKAGE_PROPERTIES_SEGMENT_NAME + FORWARD_SLASH_CHAR + PACKAGE_CORE_PROPERTIES_NAME);
                } catch (URISyntaxException unused) {
                }
            } catch (URISyntaxException unused2) {
                uri = null;
                uri3 = null;
                PACKAGE_ROOT_URI = uri2;
                PACKAGE_RELATIONSHIPS_ROOT_URI = uri;
                CORE_PROPERTIES_URI = uri3;
                packagePartName = createPartName(uri);
                try {
                    packagePartName2 = createPartName(uri3);
                    try {
                        packagePartName3 = new PackagePartName(uri2, false);
                    } catch (InvalidFormatException unused3) {
                    }
                } catch (InvalidFormatException unused4) {
                    packagePartName2 = null;
                }
                PACKAGE_RELATIONSHIPS_ROOT_PART_NAME = packagePartName;
                CORE_PROPERTIES_PART_NAME = packagePartName2;
                PACKAGE_ROOT_PART_NAME = packagePartName3;
            }
        } catch (URISyntaxException unused5) {
            uri2 = null;
            uri = null;
            uri3 = null;
            PACKAGE_ROOT_URI = uri2;
            PACKAGE_RELATIONSHIPS_ROOT_URI = uri;
            CORE_PROPERTIES_URI = uri3;
            packagePartName = createPartName(uri);
            packagePartName2 = createPartName(uri3);
            packagePartName3 = new PackagePartName(uri2, false);
            PACKAGE_RELATIONSHIPS_ROOT_PART_NAME = packagePartName;
            CORE_PROPERTIES_PART_NAME = packagePartName2;
            PACKAGE_ROOT_PART_NAME = packagePartName3;
        }
        PACKAGE_ROOT_URI = uri2;
        PACKAGE_RELATIONSHIPS_ROOT_URI = uri;
        CORE_PROPERTIES_URI = uri3;
        try {
            packagePartName = createPartName(uri);
            packagePartName2 = createPartName(uri3);
            packagePartName3 = new PackagePartName(uri2, false);
        } catch (InvalidFormatException unused6) {
            packagePartName2 = null;
            packagePartName = null;
        }
        PACKAGE_RELATIONSHIPS_ROOT_PART_NAME = packagePartName;
        CORE_PROPERTIES_PART_NAME = packagePartName2;
        PACKAGE_ROOT_PART_NAME = packagePartName3;
    }

    public static URI getPackageRootUri() {
        return packageRootUri;
    }

    public static boolean isRelationshipPartURI(URI uri) {
        if (uri != null) {
            String path = uri.getPath();
            return path.matches(".*" + RELATIONSHIP_PART_SEGMENT_NAME + ".*" + RELATIONSHIP_PART_EXTENSION_NAME + "$");
        }
        throw new IllegalArgumentException("partUri");
    }

    public static String getFilename(URI uri) {
        if (uri == null) {
            return "";
        }
        String path = uri.getPath();
        int length = path.length();
        int i = length;
        do {
            i--;
            if (i < 0) {
                return "";
            }
        } while (path.charAt(i) != FORWARD_SLASH_CHAR);
        return path.substring(i + 1, length);
    }

    public static String getFilenameWithoutExtension(URI uri) {
        String filename = getFilename(uri);
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return filename;
        }
        return filename.substring(0, lastIndexOf);
    }

    public static URI getPath(URI uri) {
        if (uri != null) {
            String path = uri.getPath();
            int length = path.length();
            do {
                length--;
                if (length >= 0) {
                }
            } while (path.charAt(length) != FORWARD_SLASH_CHAR);
            try {
                return new URI(path.substring(0, length));
            } catch (URISyntaxException unused) {
            }
        }
        return null;
    }

    public static URI combine(URI uri, URI uri2) {
        try {
            return new URI(combine(uri.getPath(), uri2.getPath()));
        } catch (URISyntaxException unused) {
            throw new IllegalArgumentException("Prefix and suffix can't be combine !");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0093, code lost:
        if (r4.startsWith("" + r2) == false) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x00a4, code lost:
        return r3 + r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0069, code lost:
        if (r4.startsWith("" + r2) == false) goto L_0x006b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String combine(java.lang.String r3, java.lang.String r4) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = ""
            r0.append(r1)
            char r2 = FORWARD_SLASH_CHAR
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            boolean r0 = r3.endsWith(r0)
            if (r0 != 0) goto L_0x0041
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r1)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            boolean r0 = r4.startsWith(r0)
            if (r0 != 0) goto L_0x0041
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r3)
            r0.append(r2)
            r0.append(r4)
            java.lang.String r3 = r0.toString()
            return r3
        L_0x0041:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r1)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            boolean r0 = r3.endsWith(r0)
            if (r0 != 0) goto L_0x006b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r1)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            boolean r0 = r4.startsWith(r0)
            if (r0 != 0) goto L_0x0095
        L_0x006b:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r1)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            boolean r0 = r3.endsWith(r0)
            if (r0 == 0) goto L_0x00a5
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r1)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            boolean r0 = r4.startsWith(r0)
            if (r0 != 0) goto L_0x00a5
        L_0x0095:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r3)
            r0.append(r4)
            java.lang.String r3 = r0.toString()
            return r3
        L_0x00a5:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.openxml4j.opc.PackagingURIHelper.combine(java.lang.String, java.lang.String):java.lang.String");
    }

    public static URI relativizeURI(URI uri, URI uri2, boolean z) {
        StringBuilder sb = new StringBuilder();
        String[] split = uri.getPath().split(FORWARD_SLASH_STRING, -1);
        String[] split2 = uri2.getPath().split(FORWARD_SLASH_STRING, -1);
        if (split.length == 0) {
            throw new IllegalArgumentException("Can't relativize an empty source URI !");
        } else if (split2.length != 0) {
            if (uri.toString().equals(FORWARD_SLASH_STRING)) {
                String path = uri2.getPath();
                if (!z || path.length() <= 0 || path.charAt(0) != '/') {
                    return uri2;
                }
                try {
                    return new URI(path.substring(1));
                } catch (Exception unused) {
                    return null;
                }
            } else {
                int i = 0;
                int i2 = 0;
                while (i < split.length && i < split2.length && split[i].equals(split2[i])) {
                    i2++;
                    i++;
                }
                if ((i2 == 0 || i2 == 1) && split[0].equals("") && split2[0].equals("")) {
                    for (int i3 = 0; i3 < split.length - 2; i3++) {
                        sb.append("../");
                    }
                    for (int i4 = 0; i4 < split2.length; i4++) {
                        if (!split2[i4].equals("")) {
                            sb.append(split2[i4]);
                            if (i4 != split2.length - 1) {
                                sb.append(FORWARD_SLASH_STRING);
                            }
                        }
                    }
                    try {
                        return new URI(sb.toString());
                    } catch (Exception unused2) {
                        return null;
                    }
                } else {
                    if (i2 != split.length || i2 != split2.length) {
                        if (i2 == 1) {
                            sb.append(FORWARD_SLASH_STRING);
                        } else {
                            for (int i5 = i2; i5 < split.length - 1; i5++) {
                                sb.append("../");
                            }
                        }
                        while (i2 < split2.length) {
                            if (sb.length() > 0 && sb.charAt(sb.length() - 1) != '/') {
                                sb.append(FORWARD_SLASH_STRING);
                            }
                            sb.append(split2[i2]);
                            i2++;
                        }
                    } else if (uri.equals(uri2)) {
                        sb.append(split[split.length - 1]);
                    } else {
                        sb.append("");
                    }
                    String rawFragment = uri2.getRawFragment();
                    if (rawFragment != null) {
                        sb.append("#");
                        sb.append(rawFragment);
                    }
                    try {
                        return new URI(sb.toString());
                    } catch (Exception unused3) {
                        return null;
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Can't relativize an empty target URI !");
        }
    }

    public static URI relativizeURI(URI uri, URI uri2) {
        return relativizeURI(uri, uri2, false);
    }

    public static URI resolvePartUri(URI uri, URI uri2) {
        if (uri == null || uri.isAbsolute()) {
            throw new IllegalArgumentException("sourcePartUri invalid - " + uri);
        } else if (uri2 != null && !uri2.isAbsolute()) {
            return uri.resolve(uri2);
        } else {
            throw new IllegalArgumentException("targetUri invalid - " + uri2);
        }
    }

    public static URI getURIFromPath(String str) {
        try {
            return toURI(str);
        } catch (URISyntaxException unused) {
            throw new IllegalArgumentException("path");
        }
    }

    public static URI getSourcePartUriFromRelationshipPartUri(URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Must not be null");
        } else if (!isRelationshipPartURI(uri)) {
            throw new IllegalArgumentException("Must be a relationship part");
        } else if (uri.compareTo(PACKAGE_RELATIONSHIPS_ROOT_URI) == 0) {
            return PACKAGE_ROOT_URI;
        } else {
            String path = uri.getPath();
            String filenameWithoutExtension = getFilenameWithoutExtension(uri);
            String substring = path.substring(0, (path.length() - filenameWithoutExtension.length()) - RELATIONSHIP_PART_EXTENSION_NAME.length());
            return getURIFromPath(combine(substring.substring(0, (substring.length() - RELATIONSHIP_PART_SEGMENT_NAME.length()) - 1), filenameWithoutExtension));
        }
    }

    public static PackagePartName createPartName(URI uri) throws InvalidFormatException {
        if (uri != null) {
            return new PackagePartName(uri, true);
        }
        throw new IllegalArgumentException("partName");
    }

    public static PackagePartName createPartName(String str) throws InvalidFormatException {
        try {
            return createPartName(toURI(str));
        } catch (URISyntaxException e) {
            throw new InvalidFormatException(e.getMessage());
        }
    }

    public static PackagePartName createPartName(String str, PackagePart packagePart) throws InvalidFormatException {
        try {
            return createPartName(resolvePartUri(packagePart.getPartName().getURI(), new URI(str)));
        } catch (URISyntaxException e) {
            throw new InvalidFormatException(e.getMessage());
        }
    }

    public static PackagePartName createPartName(URI uri, PackagePart packagePart) throws InvalidFormatException {
        return createPartName(resolvePartUri(packagePart.getPartName().getURI(), uri));
    }

    public static boolean isValidPartName(URI uri) {
        if (uri != null) {
            try {
                createPartName(uri);
                return true;
            } catch (Exception unused) {
                return false;
            }
        } else {
            throw new IllegalArgumentException("partUri");
        }
    }

    public static String decodeURI(URI uri) {
        StringBuffer stringBuffer = new StringBuffer();
        String aSCIIString = uri.toASCIIString();
        int i = 0;
        while (i < aSCIIString.length()) {
            char charAt = aSCIIString.charAt(i);
            if (charAt != '%') {
                stringBuffer.append(charAt);
            } else if (aSCIIString.length() - i >= 2) {
                stringBuffer.append((char) Integer.parseInt(aSCIIString.substring(i + 1, i + 3), 16));
                i += 2;
            } else {
                throw new IllegalArgumentException("The uri " + aSCIIString + " contain invalid encoded character !");
            }
            i++;
        }
        return stringBuffer.toString();
    }

    public static PackagePartName getRelationshipPartName(PackagePartName packagePartName) {
        if (packagePartName == null) {
            throw new IllegalArgumentException("partName");
        } else if (PACKAGE_ROOT_URI.getPath().equals(packagePartName.getURI().getPath())) {
            return PACKAGE_RELATIONSHIPS_ROOT_PART_NAME;
        } else {
            if (!packagePartName.isRelationshipPartURI()) {
                String path = packagePartName.getURI().getPath();
                String filename = getFilename(packagePartName.getURI());
                String combine = combine(combine(path.substring(0, path.length() - filename.length()), RELATIONSHIP_PART_SEGMENT_NAME), filename);
                try {
                    return createPartName(combine + RELATIONSHIP_PART_EXTENSION_NAME);
                } catch (InvalidFormatException unused) {
                    return null;
                }
            } else {
                throw new InvalidOperationException("Can't be a relationship part");
            }
        }
    }

    public static URI toURI(String str) throws URISyntaxException {
        if (str.indexOf("\\") != -1) {
            str = str.replace('\\', FORWARD_SLASH_CHAR);
        }
        int indexOf = str.indexOf(35);
        if (indexOf != -1) {
            String substring = str.substring(0, indexOf);
            String substring2 = str.substring(indexOf + 1);
            str = substring + "#" + encode(substring2);
        }
        return new URI(str);
    }

    public static String encode(String str) {
        if (str.length() == 0) {
            return str;
        }
        try {
            ByteBuffer wrap = ByteBuffer.wrap(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            while (wrap.hasRemaining()) {
                byte b = wrap.get() & 255;
                if (isUnsafe(b)) {
                    sb.append('%');
                    char[] cArr = hexDigits;
                    sb.append(cArr[(b >> 4) & 15]);
                    sb.append(cArr[(b >> 0) & 15]);
                } else {
                    sb.append((char) b);
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isUnsafe(int i) {
        return i > 128 || " ".indexOf(i) >= 0;
    }
}
