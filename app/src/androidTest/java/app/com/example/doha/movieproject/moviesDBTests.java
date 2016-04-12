package app.com.example.doha.movieproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;

import app.com.example.doha.movieproject.DB.MovieDBContract;
import app.com.example.doha.movieproject.DB.MoviesDB;

import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * Created by DOHA on 08/04/2016.
 */
@RunWith(AndroidJUnit4.class)
public class moviesDBTests {
    private MoviesDB dbHelper;
    @Before
    public void setUp() throws Exception {
        getTargetContext().deleteDatabase(MoviesDB.DB_NAME);
        dbHelper = new MoviesDB(getTargetContext());
    }
    @Test
    public void createTest(){
        SQLiteDatabase DB=dbHelper.getWritableDatabase();
        Assert.assertTrue(DB.isOpen());
        //To check if the database contains the required table
        Cursor c=DB.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        Assert.assertNotEquals("No tables Created ", null, c);
        //To test if all the columns are in the table
        c = DB.rawQuery("PRAGMA table_info(" +MovieDBContract.MovieInfoContract.MOVIE_TABLE_NAME+ ")",
               null);
        final HashSet<String> MoviesColumnHashSet = new HashSet<String>();
        MoviesColumnHashSet.add(MovieDBContract.MovieInfoContract._ID);
        MoviesColumnHashSet.add(MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_IDS);
        MoviesColumnHashSet.add(MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_NAMES);
        MoviesColumnHashSet.add(MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_POSTERS);
        MoviesColumnHashSet.add(MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_DESCRIPTIONS);
        MoviesColumnHashSet.add(MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_RELEASE_DATES);
        MoviesColumnHashSet.add(MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_VOTES);
        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            MoviesColumnHashSet.remove(columnName);
        } while(c.moveToNext());
        Assert.assertTrue("Error: The table doesn't contain all of the required columns",
                MoviesColumnHashSet.isEmpty());
        DB.close();







    }
    @Test
    public void addToFavTest(){
      // SQLiteDatabase Db=new MoviesDB(mContext).getWritableDatabase();
        SQLiteDatabase DB=dbHelper.getWritableDatabase();
        //Movie(String id,String name,String desc,String poster,String vote,String release)
        Movie movie=new Movie("123","Interstellar","Nice Movie","123456","12","12-6");
        dbHelper.insertToFav(DB,movie);
      //  assertEquals(true, Db.isOpen());

       // Db.close();
        //Log.d(DB_Created,"DB Create successfully");



    }
    @After

    public void tearDown() throws Exception {
        dbHelper.close();
    }
    /*public void testInsertData(){
        SQLiteDatabase Db=new MoviesDB(mContext).getWritableDatabase();
        ContentValues contentvalue=new ContentValues();
        contentvalue.put(MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_DESCRIPTIONS,"This is a nice movie");
        contentvalue.put(MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_NAMES, "Interstellar");
        contentvalue.put(MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_POSTERS,"abcdfe");
        contentvalue.put(MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_RELEASE_DATES,"1/7/2017");
        contentvalue.put(MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_VOTES,"35");
        contentvalue.put(MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_IDS,"12345");

        long returned=Db.insertOrThrow(MovieDBContract.MovieInfoContract.MOVIE_TABLE_NAME,null,contentvalue);
        Db.close();
        assertTrue(returned != -1);

    }*/
}
