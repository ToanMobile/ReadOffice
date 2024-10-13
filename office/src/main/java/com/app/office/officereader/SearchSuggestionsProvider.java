package com.app.office.officereader;

import android.content.SearchRecentSuggestionsProvider;

public class SearchSuggestionsProvider extends SearchRecentSuggestionsProvider {
    static final String AUTHORITY = "searchproviderx";
    static final int MODE = 1;

    public SearchSuggestionsProvider() {
        setupSuggestions(AUTHORITY, 1);
    }
}
