package au.com.contractser.provider;

import android.content.SearchRecentSuggestionsProvider;

public class SearchableProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "au.com.contractser.provider.SearchableProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public SearchableProvider(){setupSuggestions(AUTHORITY, MODE);}
}
