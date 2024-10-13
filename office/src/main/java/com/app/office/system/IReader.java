package com.app.office.system;

import java.io.File;

public interface IReader {
    void abortReader();

    void backReader() throws Exception;

    void dispose();

    IControl getControl();

    Object getModel() throws Exception;

    boolean isAborted();

    boolean isReaderFinish();

    boolean searchContent(File file, String str) throws Exception;
}
