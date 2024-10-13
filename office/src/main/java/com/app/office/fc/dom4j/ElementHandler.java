package com.app.office.fc.dom4j;

public interface ElementHandler {
    void onEnd(ElementPath elementPath);

    void onStart(ElementPath elementPath);
}
