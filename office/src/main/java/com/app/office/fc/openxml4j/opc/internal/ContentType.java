package com.app.office.fc.openxml4j.opc.internal;

import com.app.office.fc.codec.CharEncoding;
import com.app.office.fc.openxml4j.exceptions.InvalidFormatException;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ContentType {
    private static final Pattern patternMediaType = Pattern.compile("^(" + "[\\x21-\\x7E&&[^\\(\\)<>@,;:\\\\/\"\\[\\]\\?={}\\x20\\x09]]" + "+)/(" + "[\\x21-\\x7E&&[^\\(\\)<>@,;:\\\\/\"\\[\\]\\?={}\\x20\\x09]]" + "+)$");
    private Hashtable<String, String> parameters;
    private String subType;
    private String type;

    public ContentType(String str) throws InvalidFormatException {
        try {
            Matcher matcher = patternMediaType.matcher(new String(str.getBytes(), CharEncoding.US_ASCII));
            if (!matcher.matches()) {
                throw new InvalidFormatException("The specified content type '" + str + "' is not compliant with RFC 2616: malformed content type.");
            } else if (matcher.groupCount() >= 2) {
                this.type = matcher.group(1);
                this.subType = matcher.group(2);
                this.parameters = new Hashtable<>(1);
                int i = 4;
                while (i <= matcher.groupCount() && matcher.group(i) != null) {
                    this.parameters.put(matcher.group(i), matcher.group(i + 1));
                    i += 2;
                }
            }
        } catch (UnsupportedEncodingException unused) {
            throw new InvalidFormatException("The specified content type is not an ASCII value.");
        }
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getType());
        stringBuffer.append(PackagingURIHelper.FORWARD_SLASH_STRING);
        stringBuffer.append(getSubType());
        return stringBuffer.toString();
    }

    public boolean equals(Object obj) {
        return !(obj instanceof ContentType) || toString().equalsIgnoreCase(obj.toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String getSubType() {
        return this.subType;
    }

    public String getType() {
        return this.type;
    }

    public String getParameters(String str) {
        return this.parameters.get(str);
    }
}
