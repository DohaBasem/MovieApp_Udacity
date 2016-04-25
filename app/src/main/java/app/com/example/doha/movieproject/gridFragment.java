package app.com.example.doha.movieproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import app.com.example.doha.movieproject.DB.MovieDBContract;
import app.com.example.doha.movieproject.DB.MoviesDB;

import static android.widget.AdapterView.OnItemClickListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class gridFragment extends Fragment {
    String BASE_URl="http://api.themoviedb.org/3/movie/popular?";
    GridView myGrid;
    boolean TwoPane;
    fetchMovieData getData=new fetchMovieData();


    public gridFragment() {
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=new MenuInflater(getActivity());
        inflater.inflate(R.menu.item_contextmenu,menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
       // setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("OPTIONSELECTE","HERE");
        switch(item.getItemId()){
            case(R.id.Popularity):
            {   this.BASE_URl="http://api.themoviedb.org/3/movie/popular?";

               // fetchMovieData popData=new fetchMovieData();
                fetchMovieData getPoPData=new fetchMovieData();

                getPoPData.execute();
                return true;
            }
            case(R.id.rating):{
                this.BASE_URl="http://api.themoviedb.org/3/movie/top_rated?";

                fetchMovieData getRateData=new fetchMovieData();
                getRateData.execute();
                return true;
            }
            case(R.id.favorites):{
                //Object of class that extends the asyncTask of fetching from DB and executing it
                DBTransactions favorites=new DBTransactions();
                favorites.execute();

            }


        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_grid, container, false);


      //  GridView myGrid =(GridView)getActivity().findViewById(R.id.myGrid);
      //  myGrid.setAdapter(moviesadapter);


        getData.execute();
        return rootView;
    }

   public class fetchMovieData extends AsyncTask<Void,Void,Void>{
       public ArrayList<Movie>Movies=new ArrayList<Movie>();


       private void getMovieFromJson(String JSONString){

           //Since the string starts with curly brackets ,so it is a JSON object
           try {
               JSONObject urlJSON=new JSONObject(JSONString);
               JSONArray resultsArray = urlJSON.getJSONArray("results");
               for(int i=0;i<resultsArray.length();i++){
                  // String urlString;
                   Movie Movie;
                   JSONObject movie=resultsArray.getJSONObject(i);

                   Movie=new Movie(movie.getString("id"),movie.getString("title"),movie.getString("overview"),movie.getString("poster_path"),movie.getString("vote_average"),movie.getString("release_date"));
                   Movies.add(Movie);
               }
           } catch (JSONException e) {
               e.printStackTrace();
           }



       }

        @Override
        protected Void doInBackground(Void... params) {
           final String LOG_TAG = fetchMovieData.class.getSimpleName();

            if (params.length == 0) {
            Log.d(LOG_TAG,"length of params is 0");
            }
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String MovieDataJsonStr = null;


            try {

                Uri builtUri = Uri.parse(BASE_URl).buildUpon()
                        //There shoulb be a better way to secure the key !!
                        .appendQueryParameter("api_key", "d6b3dbd8bd91d833b88bf50495e6d735")
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                   Log.d(LOG_TAG,"inputStream is Null");
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    Log.d(LOG_TAG,"buffer length is zero");
                }
                MovieDataJsonStr = buffer.toString();

                Log.v(LOG_TAG, "movies string: " + MovieDataJsonStr);

            }
            catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);


            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            int numMovies=25;

            getMovieFromJson(MovieDataJsonStr);


return null;
        }



       @Override
        protected void onPostExecute(Void aVoid) {
           super.onPostExecute(aVoid);
            //if (urls != null)
           if(Movies!=null)
            {

                final GridAdapter moviesadapter=new GridAdapter(getActivity(),R.layout.grid_item,this.Movies);

                // moviesadapter.clear();
                moviesadapter.notifyDataSetChanged();

                myGrid =(GridView)getActivity().findViewById(R.id.myGrid);
                myGrid.setAdapter(moviesadapter);
                myGrid.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Movie SelectedMovie = (Movie) moviesadapter.getItem(position);
                        String MovieName = SelectedMovie.getName();
                        //Toast.makeText(getActivity(),MovieName,Toast.LENGTH_LONG).show();
                        Log.d("Movie ID ", SelectedMovie.getId());
                        /*if (MainActivity.TwoPane) {

                            Log.d("Tablet","Tab");
                            MovieDetailActivityFragment detail = MovieDetailActivityFragment.getInstance(SelectedMovie);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, detail).commit();

                        } else {
                            Intent toDetail = new Intent(getActivity(), MovieDetailActivity.class).putExtra("SelectedMovieData", SelectedMovie);
                            startActivity(toDetail);
                        }*/
                        Passable AttachedAct = (Passable)getActivity();
                        AttachedAct.passSelectedMovie(SelectedMovie);
                    }
                });
                //For each Item when it is long clicked a context item appears
                myGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        return false;
                    }
                });


            }
        }
    }
    public class DBTransactions extends AsyncTask<Void,Void,Void> {

        public ArrayList<Movie>Movies=new ArrayList<Movie>();
        @Override
        protected Void doInBackground(Void... params) {
            MoviesDB DBHelper=new MoviesDB(getContext());
            //Movie Addedmovie=new Movie("123","Interstellar","Nice Movie","123456","12","12-6");
            SQLiteDatabase DBWrite=DBHelper.getWritableDatabase();
           //DBHelper.insertToFav(DBWrite,Addedmovie);
            SQLiteDatabase DB=DBHelper.getReadableDatabase();
            Cursor cursor=DBHelper.getAllFavorites(DB);

            /*while(cursor.moveToNext()){

                Log.d("Movie Name",cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_NAMES)));

            }*/
            int count=cursor.getColumnCount();
            while(cursor.moveToNext()){
                Movie movie=new Movie(null,null,null,null,null,null);
            for(int i=0;i<count;i++){

                String colName=cursor.getColumnName(i);
                switch (colName){
                    case (MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_IDS):{
                        movie.setId(cursor.getString(i));
                       break;
                    }
                    case (MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_NAMES):{
                        movie.setName(cursor.getString(i));
                        break;
                    }
                    case (MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_DESCRIPTIONS):{
                        movie.setDesc(cursor.getString(i));
                        break;
                    }
                    case (MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_POSTERS):{
                        movie.setPoster(cursor.getString(i));
                        break;
                    }
                    case (MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_RELEASE_DATES):{
                        movie.setRelease(cursor.getString(i));
                        break;
                    }
                    case (MovieDBContract.MovieInfoContract.COLUMN_NAME_Movie_VOTES):{
                        movie.setVote_count(cursor.getString(i));
                        break;
                    }

                }}
            Movies.add(movie);}
            int i=0;
            while(i<Movies.size()){
                Movies.get(i);

                Log.d("Name from list",Movies.get(i).getName());
                i++;

            }




            //Encapsulate the contents of 1 row into a movie object
            //Append the movie object to the arraylist
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(Movies!=null)
            {

                final GridAdapter moviesadapter=new GridAdapter(getActivity(),R.layout.grid_item,this.Movies);

                // moviesadapter.clear();
                moviesadapter.notifyDataSetChanged();

                myGrid =(GridView)getActivity().findViewById(R.id.myGrid);
                myGrid.setAdapter(moviesadapter);
                myGrid.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Movie SelectedMovie = (Movie) moviesadapter.getItem(position);
                        String MovieName = SelectedMovie.getId();
                        Passable AttachedActivity=(Passable)getActivity();
                        AttachedActivity.passSelectedMovie(SelectedMovie);
                      //  Toast.makeText(getActivity(),MovieName, Toast.LENGTH_LONG).show();
                        /*Intent toDetail = new Intent(getActivity(), MovieDetailActivity.class).putExtra("SelectedMovieData", SelectedMovie);
                        startActivity(toDetail);*/
                    }
                });
                //For each Item when it is long clicked a context item appears
                myGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){


                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        return false;
                    }
                });


            }
        }
    }
}
