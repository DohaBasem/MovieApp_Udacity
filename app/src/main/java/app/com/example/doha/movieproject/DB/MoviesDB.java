package app.com.example.doha.movieproject.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.com.example.doha.movieproject.Movie;

/**
 * Created by DOHA on 08/04/2016.
 */
public class MoviesDB extends SQLiteOpenHelper {
    public static final String DB_NAME="Movies.db";
    public static final int DB_VERSION=2;

  /*   static final String CREATE_MOVIES_TABLE="CREATE TABLE IF NOT EXISTS "+ MovieDBContract.MovieInfoContract.MOVIE_TABLE_NAME +"( "
            +MovieDBContract.MovieInfoContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT"+" , "
            +MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_IDS + "TEXT NOT NULL"+" , "
            +MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_NAMES+ "TEXT NOT NULL"+" , "
            +MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_POSTERS+ "TEXT NOT NULL" +" , "
            +MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_DESCRIPTIONS+"TEXT NOT NULL" +" , "
            +MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_RELEASE_DATES+"TEXT NOT NULL"+" , "
            +MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_VOTES+"TEXT"+" )";
*/
    public MoviesDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         final String CREATE_MOVIES_TABLE="CREATE TABLE IF NOT EXISTS "+ MovieDBContract.MovieInfoContract.MOVIE_TABLE_NAME +"( "
                +MovieDBContract.MovieInfoContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT"+" , "
                +MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_IDS + " TEXT NOT NULL"+" , "
                +MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_NAMES+ " TEXT NOT NULL UNIQUE "+" , "
                +MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_POSTERS+ " TEXT NOT NULL" +" , "
                +MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_DESCRIPTIONS+" TEXT NOT NULL" +" , "
                +MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_RELEASE_DATES+" TEXT NOT NULL"+" , "
                +MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_VOTES+" TEXT"+" )";

        db.execSQL(CREATE_MOVIES_TABLE);
        //db.execSQL("heey");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertToFav(SQLiteDatabase db,Movie movie){
        //After getting the selected movie from the grid and favoriting it
        final String ADD_MOVIE_TO_FAV_QUERY="INSERT INTO "+MovieDBContract.MovieInfoContract.MOVIE_TABLE_NAME+"("+ MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_IDS+","+MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_NAMES
                +"," +MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_POSTERS+","+MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_DESCRIPTIONS+","
                +MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_RELEASE_DATES+","+MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_VOTES+")"+
                "VALUES ( '"+movie.getId()+"','"+movie.getName()+"','"+movie.getPoster()+"','"+movie.getDesc()+"','"+movie.getRelease()+"','"+movie.getVote_count()+"')";
    db.execSQL(ADD_MOVIE_TO_FAV_QUERY);
    }
    public void removeFromFav(SQLiteDatabase db,Movie movie){
        final String REMOVE_FROM_FAV="DELETE FROM "+ MovieDBContract.MovieInfoContract.MOVIE_TABLE_NAME +" WHERE "+ MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_NAMES +" = '"+ movie.getName()+" '";
        db.execSQL(REMOVE_FROM_FAV);

    }
    public Cursor getAllFavorites(SQLiteDatabase db){
        final String GET_ALL_FAV="SELECT * FROM "+MovieDBContract.MovieInfoContract.MOVIE_TABLE_NAME;
        Cursor cursor=db.rawQuery(GET_ALL_FAV,null);
        return cursor;

    }

}
