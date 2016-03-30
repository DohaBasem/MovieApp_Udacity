package app.com.example.doha.movieproject;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class gridFragment extends Fragment {
    String BASE_URl="http://api.themoviedb.org/3/movie/popular?";
    GridView myGrid;
    public gridFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("OPTIONSELECTE","HERE");
        switch(item.getItemId()){
            case(R.id.Popularity):
            {   this.BASE_URl="http://api.themoviedb.org/3/movie/popular?";
                return true;
            }
            case(R.id.rating):{
                this.BASE_URl="http://api.themoviedb.org/3/movie/top_rated?";
                return true;
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
        fetchMovieData getData=new fetchMovieData();

        getData.execute();
        return rootView;
    }

   public class fetchMovieData extends AsyncTask<Void,Void,Void>{
       //public ArrayList<String>urls=new ArrayList<String>();
       public ArrayList<Movie>Movies=new ArrayList<Movie>();
       /*private void getImageUrlFromJson(String JSONString){

        //Since the string starts with curly brackets ,so it is a JSON object
           try {
               JSONObject urlJSON=new JSONObject(JSONString);
               JSONArray resultsArray = urlJSON.getJSONArray("results");
               for(int i=0;i<resultsArray.length();i++){
                   String urlString;
                   JSONObject movie=resultsArray.getJSONObject(i);

                   urls.add(movie.getString("poster_path"));

               }
           } catch (JSONException e) {
               e.printStackTrace();
           }



       }*/

       private void getMovieFromJson(String JSONString){

           //Since the string starts with curly brackets ,so it is a JSON object
           try {
               JSONObject urlJSON=new JSONObject(JSONString);
               JSONArray resultsArray = urlJSON.getJSONArray("results");
               for(int i=0;i<resultsArray.length();i++){
                  // String urlString;
                   Movie Movie;
                   JSONObject movie=resultsArray.getJSONObject(i);

                   Movie=new Movie(movie.getString("id"),movie.getString("title"),movie.getString("overview"),movie.getString("poster_path"));
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
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
               // final String BASE_URL ="http://api.themoviedb.org/3/movie/popular?";
                Uri builtUri = Uri.parse(BASE_URl).buildUpon()
                        //There shoulb be a better way to secure the key !!
                        .appendQueryParameter("api_key", "d6b3dbd8bd91d833b88bf50495e6d735")
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
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
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.

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
            //getImageUrlFromJson(MovieDataJsonStr);
            getMovieFromJson(MovieDataJsonStr);

            // This will only happen if there was an error getting or parsing the forecast.
return null;
        }



       @Override
        protected void onPostExecute(Void aVoid) {
           super.onPostExecute(aVoid);
            //if (urls != null)
           if(Movies!=null)
            {
               // GridAdapter moviesadapter=new GridAdapter(getActivity(),R.layout.grid_item,this.urls);
                final GridAdapter moviesadapter=new GridAdapter(getActivity(),R.layout.grid_item,this.Movies);

                // moviesadapter.clear();
                moviesadapter.notifyDataSetChanged();
                //GridView myGrid =(GridView)getActivity().findViewById(R.id.myGrid);
                myGrid =(GridView)getActivity().findViewById(R.id.myGrid);
                myGrid.setAdapter(moviesadapter);
                myGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Movie SelectedMovie= (Movie) moviesadapter.getItem(position);
                        String MovieName=SelectedMovie.Name;
                        //Toast.makeText(getActivity(),MovieName,Toast.LENGTH_LONG).show();
                        Intent toDetail=new Intent(getActivity(),MovieDetailActivity.class).putExtra("SelectedMovieData",SelectedMovie);
                        startActivity(toDetail);
                    }
                });

                /*for(String imgUrl : resulty().findt) {

                    moviesadapter.add(imgUrl);
                }*/
                // New data is back from the server.  Hooray!
            }
        }
    }
}
