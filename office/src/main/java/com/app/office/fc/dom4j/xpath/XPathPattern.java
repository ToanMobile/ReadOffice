package com.app.office.fc.dom4j.xpath;

import com.app.office.fc.dom4j.Node;
import com.app.office.fc.dom4j.rule.Pattern;

public class XPathPattern implements Pattern {
    private Pattern pattern;
    private String text;

    public boolean matches(Node node) {
        return false;
    }

    public XPathPattern(Pattern pattern2) {
    }

    public XPathPattern(String str) {
    }

    public String getText() {
        return this.text;
    }

    public double getPriority() {
        return this.pattern.getPriority();
    }

    public Pattern[] getUnionPatterns() {
        Pattern[] unionPatterns = this.pattern.getUnionPatterns();
        if (unionPatterns == null) {
            return null;
        }
        int length = unionPatterns.length;
        XPathPattern[] xPathPatternArr = new XPathPattern[length];
        for (int i = 0; i < length; i++) {
            xPathPatternArr[i] = new XPathPattern(unionPatterns[i]);
        }
        return xPathPatternArr;
    }

    public short getMatchType() {
        return this.pattern.getMatchType();
    }

    public String getMatchesNodeName() {
        return this.pattern.getMatchesNodeName();
    }
}
