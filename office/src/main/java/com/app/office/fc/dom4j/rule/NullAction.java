package com.app.office.fc.dom4j.rule;

import com.app.office.fc.dom4j.Node;

public class NullAction implements Action {
    public static final NullAction SINGLETON = new NullAction();

    public void run(Node node) throws Exception {
    }
}
