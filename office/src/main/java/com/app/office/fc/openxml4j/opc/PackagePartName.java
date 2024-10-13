package com.app.office.fc.openxml4j.opc;

import com.app.office.fc.openxml4j.exceptions.InvalidFormatException;
import com.app.office.fc.openxml4j.exceptions.OpenXML4JRuntimeException;
import java.net.URI;
import java.net.URISyntaxException;

public final class PackagePartName implements Comparable<PackagePartName> {
    private static String[] RFC3986_PCHAR_AUTHORIZED_SUP = {":", "@"};
    private static String[] RFC3986_PCHAR_SUB_DELIMS = {"!", "$", "&", "'", "(", ")", "*", "+", ",", ";", "="};
    private static String[] RFC3986_PCHAR_UNRESERVED_SUP = {"-", ".", "_", "~"};
    private boolean isRelationship;
    private URI partNameURI;

    PackagePartName(URI uri, boolean z) throws InvalidFormatException {
        if (z) {
            throwExceptionIfInvalidPartUri(uri);
        } else if (!PackagingURIHelper.PACKAGE_ROOT_URI.equals(uri)) {
            throw new OpenXML4JRuntimeException("OCP conformance must be check for ALL part name except special cases : ['/']");
        }
        this.partNameURI = uri;
        this.isRelationship = isRelationshipPartURI(uri);
    }

    PackagePartName(String str, boolean z) throws InvalidFormatException {
        try {
            URI uri = new URI(str);
            if (z) {
                throwExceptionIfInvalidPartUri(uri);
            } else if (!PackagingURIHelper.PACKAGE_ROOT_URI.equals(uri)) {
                throw new OpenXML4JRuntimeException("OCP conformance must be check for ALL part name except special cases : ['/']");
            }
            this.partNameURI = uri;
            this.isRelationship = isRelationshipPartURI(uri);
        } catch (URISyntaxException unused) {
            throw new IllegalArgumentException("partName argmument is not a valid OPC part name !");
        }
    }

    private boolean isRelationshipPartURI(URI uri) {
        if (uri != null) {
            String path = uri.getPath();
            return path.matches("^.*/" + PackagingURIHelper.RELATIONSHIP_PART_SEGMENT_NAME + "/.*\\" + PackagingURIHelper.RELATIONSHIP_PART_EXTENSION_NAME + "$");
        }
        throw new IllegalArgumentException("partUri");
    }

    public boolean isRelationshipPartURI() {
        return this.isRelationship;
    }

    private static void throwExceptionIfInvalidPartUri(URI uri) throws InvalidFormatException {
        if (uri != null) {
            throwExceptionIfEmptyURI(uri);
            throwExceptionIfAbsoluteUri(uri);
            throwExceptionIfPartNameNotStartsWithForwardSlashChar(uri);
            throwExceptionIfPartNameEndsWithForwardSlashChar(uri);
            throwExceptionIfPartNameHaveInvalidSegments(uri);
            return;
        }
        throw new IllegalArgumentException("partUri");
    }

    private static void throwExceptionIfEmptyURI(URI uri) throws InvalidFormatException {
        if (uri != null) {
            String path = uri.getPath();
            if (path.length() == 0 || (path.length() == 1 && path.charAt(0) == PackagingURIHelper.FORWARD_SLASH_CHAR)) {
                throw new InvalidFormatException("A part name shall not be empty [M1.1]: " + uri.getPath());
            }
            return;
        }
        throw new IllegalArgumentException("partURI");
    }

    private static void throwExceptionIfPartNameHaveInvalidSegments(URI uri) throws InvalidFormatException {
        if (uri != null) {
            String[] split = uri.toASCIIString().split(PackagingURIHelper.FORWARD_SLASH_STRING);
            int i = 1;
            if (split.length <= 1 || !split[0].equals("")) {
                throw new InvalidFormatException("A part name shall not have empty segments [M1.3]: " + uri.getPath());
            }
            while (i < split.length) {
                String str = split[i];
                if (str == null || "".equals(str)) {
                    throw new InvalidFormatException("A part name shall not have empty segments [M1.3]: " + uri.getPath());
                } else if (str.endsWith(".")) {
                    throw new InvalidFormatException("A segment shall not end with a dot ('.') character [M1.9]: " + uri.getPath());
                } else if (!"".equals(str.replaceAll("\\\\.", ""))) {
                    checkPCharCompliance(str);
                    i++;
                } else {
                    throw new InvalidFormatException("A segment shall include at least one non-dot character. [M1.10]: " + uri.getPath());
                }
            }
            return;
        }
        throw new IllegalArgumentException("partUri");
    }

    private static void checkPCharCompliance(String str) throws InvalidFormatException {
        boolean z;
        boolean z2;
        int i = 0;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if ((charAt < 'A' || charAt > 'Z') && ((charAt < 'a' || charAt > 'z') && (charAt < '0' || charAt > '9'))) {
                int i2 = 0;
                while (true) {
                    String[] strArr = RFC3986_PCHAR_UNRESERVED_SUP;
                    if (i2 >= strArr.length) {
                        z = true;
                        break;
                    } else if (charAt == strArr[i2].charAt(0)) {
                        z = false;
                        break;
                    } else {
                        i2++;
                    }
                }
                int i3 = 0;
                while (z) {
                    String[] strArr2 = RFC3986_PCHAR_AUTHORIZED_SUP;
                    if (i3 >= strArr2.length) {
                        break;
                    }
                    if (charAt == strArr2[i3].charAt(0)) {
                        z = false;
                    }
                    i3++;
                }
                int i4 = 0;
                while (z) {
                    String[] strArr3 = RFC3986_PCHAR_SUB_DELIMS;
                    if (i4 >= strArr3.length) {
                        break;
                    }
                    if (charAt == strArr3[i4].charAt(0)) {
                        z = false;
                    }
                    i4++;
                }
            } else {
                z = false;
            }
            if (z && charAt == '%') {
                if (str.length() - i >= 2) {
                    char parseInt = (char) Integer.parseInt(str.substring(i + 1, i + 3), 16);
                    i += 2;
                    if (parseInt == '/' || parseInt == '\\') {
                        throw new InvalidFormatException("A segment shall not contain percent-encoded forward slash ('/'), or backward slash ('') characters. [M1.7]");
                    }
                    boolean z3 = (parseInt >= 'A' && parseInt <= 'Z') || (parseInt >= 'a' && parseInt <= 'z') || (parseInt >= '0' && parseInt <= '9');
                    int i5 = 0;
                    while (true) {
                        if (z3) {
                            break;
                        }
                        String[] strArr4 = RFC3986_PCHAR_UNRESERVED_SUP;
                        if (i5 >= strArr4.length) {
                            break;
                        } else if (charAt == strArr4[i5].charAt(0)) {
                            z2 = true;
                            break;
                        } else {
                            i5++;
                        }
                    }
                    z2 = z3;
                    if (z) {
                        throw new InvalidFormatException("A segment shall not contain percent-encoded unreserved characters. [M1.8]");
                    }
                } else {
                    throw new InvalidFormatException("The segment " + str + " contain invalid encoded character !");
                }
            }
            if (!z) {
                i++;
            } else {
                throw new InvalidFormatException("A segment shall not hold any characters other than pchar characters. [M1.6]");
            }
        }
    }

    private static void throwExceptionIfPartNameNotStartsWithForwardSlashChar(URI uri) throws InvalidFormatException {
        String path = uri.getPath();
        if (path.length() > 0 && path.charAt(0) != PackagingURIHelper.FORWARD_SLASH_CHAR) {
            throw new InvalidFormatException("A part name shall start with a forward slash ('/') character [M1.4]: " + uri.getPath());
        }
    }

    private static void throwExceptionIfPartNameEndsWithForwardSlashChar(URI uri) throws InvalidFormatException {
        String path = uri.getPath();
        if (path.length() > 0 && path.charAt(path.length() - 1) == PackagingURIHelper.FORWARD_SLASH_CHAR) {
            throw new InvalidFormatException("A part name shall not have a forward slash as the last character [M1.5]: " + uri.getPath());
        }
    }

    private static void throwExceptionIfAbsoluteUri(URI uri) throws InvalidFormatException {
        if (uri.isAbsolute()) {
            throw new InvalidFormatException("Absolute URI forbidden: " + uri);
        }
    }

    public int compareTo(PackagePartName packagePartName) {
        if (packagePartName == null) {
            return -1;
        }
        return this.partNameURI.toASCIIString().toLowerCase().compareTo(packagePartName.partNameURI.toASCIIString().toLowerCase());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000c, code lost:
        r1 = r0.lastIndexOf(".");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getExtension() {
        /*
            r3 = this;
            java.net.URI r0 = r3.partNameURI
            java.lang.String r0 = r0.getPath()
            int r1 = r0.length()
            if (r1 <= 0) goto L_0x001c
            java.lang.String r1 = "."
            int r1 = r0.lastIndexOf(r1)
            r2 = -1
            if (r1 <= r2) goto L_0x001c
            int r1 = r1 + 1
            java.lang.String r0 = r0.substring(r1)
            return r0
        L_0x001c:
            java.lang.String r0 = ""
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.openxml4j.opc.PackagePartName.getExtension():java.lang.String");
    }

    public String getName() {
        return this.partNameURI.toASCIIString();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof PackagePartName)) {
            return false;
        }
        return this.partNameURI.toASCIIString().toLowerCase().equals(((PackagePartName) obj).partNameURI.toASCIIString().toLowerCase());
    }

    public int hashCode() {
        return this.partNameURI.toASCIIString().toLowerCase().hashCode();
    }

    public String toString() {
        return getName();
    }

    public URI getURI() {
        return this.partNameURI;
    }
}
