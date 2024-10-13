package com.app.office.fc.dom4j.io;

import com.app.office.fc.dom4j.ElementPath;

class PruningDispatchHandler extends DispatchHandler {
    PruningDispatchHandler() {
    }

    public void onEnd(ElementPath elementPath) {
        super.onEnd(elementPath);
        if (getActiveHandlerCount() == 0) {
            elementPath.getCurrent().detach();
        }
    }
}
