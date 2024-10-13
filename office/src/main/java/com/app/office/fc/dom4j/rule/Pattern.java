package com.app.office.fc.dom4j.rule;

import com.app.office.fc.dom4j.Node;
import com.app.office.fc.dom4j.NodeFilter;

public interface Pattern extends NodeFilter {
    public static final short ANY_NODE = 0;
    public static final double DEFAULT_PRIORITY = 0.5d;
    public static final short NONE = 9999;
    public static final short NUMBER_OF_TYPES = 14;

    short getMatchType();

    String getMatchesNodeName();

    double getPriority();

    Pattern[] getUnionPatterns();

    boolean matches(Node node);
}
