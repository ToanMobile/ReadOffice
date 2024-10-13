package com.app.office.fc.dom4j;

public class InvalidXPathException extends IllegalArgumentException {
    private static final long serialVersionUID = 3257009869058881592L;

    public InvalidXPathException(String str) {
        super("Invalid XPath expression: " + str);
    }

    public InvalidXPathException(String str, String str2) {
        super("Invalid XPath expression: " + str + " " + str2);
    }

    public InvalidXPathException(String str, Throwable th) {
        super("Invalid XPath expression: '" + str + "'. Caused by: " + th.getMessage());
    }
}
