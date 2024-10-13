package com.app.office.officereader.search;

import java.io.File;

public interface ISearchResult {
    void onResult(File file);

    void searchFinish();
}
