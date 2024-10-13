package com.app.office.fc.dom4j.rule;

import com.app.office.fc.dom4j.Node;

public interface Action {
    void run(Node node) throws Exception;
}
