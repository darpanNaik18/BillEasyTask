package com.billeasytest.Database;

import android.provider.BaseColumns;

public class NowPlayingMovie {

    public static final class NowPlayingEntry implements BaseColumns {
        public static final String TABLE_NAME = "nowplaying";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PLOT_SYNOPSIS = "overview";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_RELEASE_DATE = "releasedate";

    }

}
