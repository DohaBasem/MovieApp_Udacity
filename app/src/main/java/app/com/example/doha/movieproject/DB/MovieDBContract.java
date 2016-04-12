package app.com.example.doha.movieproject.DB;

import android.provider.BaseColumns;

/**
 * Created by DOHA on 08/04/2016.
 */

public class MovieDBContract {
    public static abstract class MovieInfoContract implements BaseColumns{
        public static final String MOVIE_TABLE_NAME = "movies_table";
        public static final String COLUMN_NAME_Movie_IDS = "movie_Ids";
        public static final String COLUMN_NAME_Movie_NAMES = "movie_names";
        public static final String COLUMN_NAME_Movie_DESCRIPTIONS = "movie_descs";
        public static final String COLUMN_NAME_Movie_POSTERS = "movie_posters";
        public static final String COLUMN_NAME_Movie_RELEASE_DATES = "movie_release";
        public static final String COLUMN_NAME_Movie_VOTES = "movie_votes";








    }
}
