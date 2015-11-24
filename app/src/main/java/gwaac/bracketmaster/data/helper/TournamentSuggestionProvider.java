package gwaac.bracketmaster.data.helper;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Arya on 11/19/15.
 */
public class TournamentSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "gwaac.bracketmaster.data.helper.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public TournamentSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
