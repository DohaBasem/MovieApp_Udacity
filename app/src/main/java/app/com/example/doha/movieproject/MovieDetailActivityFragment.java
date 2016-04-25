package app.com.example.doha.movieproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

import app.com.example.doha.movieproject.DB.MoviesDB;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {
   public Movie SelectedMovie;
    public MovieDetailActivityFragment() {
    }
    public static MovieDetailActivityFragment getInstance(Movie movie){
        MovieDetailActivityFragment detail=new MovieDetailActivityFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("SelectedMovie",movie);
        detail.setArguments(bundle);
        return detail;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View detailView= inflater.inflate(R.layout.fragment_movie_detail, container, false);
        //final Movie SelectedMovie=(Movie)getActivity().getIntent().getSerializableExtra("SelectedMovieData");
       // final Movie SelectedMovie= (Movie) getArguments().getSerializable("SelectedMovie");
        this.SelectedMovie=(Movie) getArguments().getSerializable("SelectedMovie");
        if(SelectedMovie!=null){
        String ImgUrl="http://image.tmdb.org/t/p/"+"original"+SelectedMovie.getPoster();
        ImageView ImgHolder=(ImageView)detailView.findViewById(R.id.posterInDetatil);
        TextView NameHolder=(TextView)detailView.findViewById(R.id.NameView);
        TextView DescHolder=(TextView)detailView.findViewById(R.id.DescView);
        TextView Vote=(TextView)detailView.findViewById(R.id.MovieVote);
        TextView date=(TextView)detailView.findViewById(R.id.date);

        NameHolder.setText(SelectedMovie.getName());
        DescHolder.setText(SelectedMovie.getDesc());
        Vote.setText("Average Vote : "+SelectedMovie.getVote_count());
        date.setText("Release date : "+SelectedMovie.getRelease());
        Picasso.with(getContext()).load(ImgUrl).resize(200,200).into((ImageView) ImgHolder);


        //LayoutInflater inflater =LayoutInflater.from(this);
        //View detail=inflater.inflate(R.layout.fragment_movie_detail,null);
        final Button favButton =(Button)detailView.findViewById(R.id.favoriteButton);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String onButtonString = favButton.getText().toString();
                MoviesDB DBHelper = new MoviesDB(getContext());
                SQLiteDatabase db = DBHelper.getWritableDatabase();
                if (onButtonString.equals("Favorite")) {

                    DBHelper.insertToFav(db, SelectedMovie);
                    Toast.makeText(getContext(), "Successfully added ", Toast.LENGTH_LONG).show();
                    favButton.setText("Unfavorite");

                } else {

                    DBHelper.removeFromFav(db, SelectedMovie);
                    favButton.setText("Favorite");
                }
            }
        });
        fetchMovieReviews reviews=new fetchMovieReviews();
        reviews.execute();
        fetchMovieTrailers trailers=new fetchMovieTrailers();
        trailers.execute();}
        return detailView;

    }
   /* public class fetchMovieTrailers extends AsyncTask<Void,Void,Void>{



    }*/
    public class fetchMovieReviews extends AsyncTask<Void,Void,Void> {
       public ArrayList Reviews=new ArrayList<Review>();
       private void getReviewFromJson(String JSONString){

           //Since the string starts with curly brackets ,so it is a JSON object
           try {
               JSONObject urlJSON=new JSONObject(JSONString);
               JSONArray resultsArray = urlJSON.getJSONArray("results");
               for(int i=0;i<resultsArray.length();i++){
                   // String urlString;
                   Review review;
                   JSONObject JSONreview=resultsArray.getJSONObject(i);

                   review=new Review(JSONreview.getString("id"),JSONreview.getString("author"),JSONreview.getString("content"),JSONreview.getString("url"));
                   Reviews.add(review);
               }
           } catch (JSONException e) {
               e.printStackTrace();
           }



       }
       @Override
       protected Void doInBackground(Void... params) {
          // final Movie SelectedMovie=(Movie)getActivity().getIntent().getSerializableExtra("SelectedMovieData");
           String id = SelectedMovie.getId();
           String BASE_URl="http://api.themoviedb.org/3/movie/"+id+"/reviews";

           final String LOG_TAG = fetchMovieReviews.class.getSimpleName();

           if (params.length == 0) {
               Log.d(LOG_TAG,"length of params is 0");
           }
           // These two need to be declared outside the try/catch
           // so that they can be closed in the finally block.
           HttpURLConnection urlConnection = null;
           BufferedReader reader = null;

           // Will contain the raw JSON response as a string.
           String ReviewDataJsonStr = null;


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
               ReviewDataJsonStr = buffer.toString();

               Log.v(LOG_TAG, "movies string: " + ReviewDataJsonStr);

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

          // int numMovies=25;


           //getMovieFromJson(MovieDataJsonStr);
           Log.d("el string",ReviewDataJsonStr);
            getReviewFromJson(ReviewDataJsonStr);

           return null;
       }
       @Override
       protected void onPostExecute(Void aVoid) {
           super.onPostExecute(aVoid);
           if(Reviews!=null)
           {

               final ReviewsAdapter reviewsadapter=new ReviewsAdapter(getActivity(),R.layout.review_item,this.Reviews);

               // moviesadapter.clear();
               reviewsadapter.notifyDataSetChanged();

               ListView reviewsList =(ListView)getActivity().findViewById(R.id.ReviewsList);
               reviewsList.setAdapter(reviewsadapter);
              /* reviewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Movie SelectedMovie = (Movie) moviesadapter.getItem(position);
                       String MovieName = SelectedMovie.getId();
                       //  Toast.makeText(getActivity(),MovieName, Toast.LENGTH_LONG).show();
                       Intent toDetail = new Intent(getActivity(), MovieDetailActivity.class).putExtra("SelectedMovieData", SelectedMovie);
                       startActivity(toDetail);
                   }
               });*/
               //For each Item when it is long clicked a context item appears
               /*myGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){


                   @Override
                   public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                       return false;
                   }
               });*/


           }
       }

}
    public class fetchMovieTrailers extends AsyncTask<Void,Void,Void> {
        public ArrayList Trailers=new ArrayList<Trailer>();
        private void getTrailerFromJson(String JSONString){

            //Since the string starts with curly brackets ,so it is a JSON object
            try {
                JSONObject urlJSON=new JSONObject(JSONString);
                JSONArray resultsArray = urlJSON.getJSONArray("results");
                for(int i=0;i<resultsArray.length();i++){
                    // String urlString;
                    Trailer trailer;
                    JSONObject JSONTrailer=resultsArray.getJSONObject(i);

                    trailer=new Trailer(JSONTrailer.getString("id"),JSONTrailer.getString("iso_639_1"),JSONTrailer.getString("iso_3166_1"),JSONTrailer.getString("key"),JSONTrailer.getString("name"),
                            JSONTrailer.getString("site"),JSONTrailer.getString("size"),JSONTrailer.getString("type"));
                    Trailers.add(trailer);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
        @Override
        protected Void doInBackground(Void... params) {
           // final Movie SelectedMovie=(Movie)getActivity().getIntent().getSerializableExtra("SelectedMovieData");
            String id = SelectedMovie.getId();
            String BASE_URl="http://api.themoviedb.org/3/movie/"+id+"/videos";

            final String LOG_TAG = fetchMovieTrailers.class.getSimpleName();

            if (params.length == 0) {
                Log.d(LOG_TAG,"length of params is 0");
            }
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String TrailerDataJsonStr = null;


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
                TrailerDataJsonStr = buffer.toString();

                Log.v(LOG_TAG, "movies string: " + TrailerDataJsonStr);

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

            // int numMovies=25;


            //getMovieFromJson(MovieDataJsonStr);
            getTrailerFromJson(TrailerDataJsonStr);

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(Trailers!=null)
            {

                final TrailersAdapter trailersadapter=new TrailersAdapter(getActivity(),R.layout.trailer_item,this.Trailers);

                // moviesadapter.clear();
                trailersadapter.notifyDataSetChanged();

                ListView TrailersList =(ListView)getActivity().findViewById(R.id.trailersList);
                TrailersList.setAdapter(trailersadapter);
               TrailersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Trailer SelectedTrailer = (Trailer) trailersadapter.getItem(position);
                       String key = SelectedTrailer.getKey();
                       //  Toast.makeText(getActivity(),MovieName, Toast.LENGTH_LONG).show();
                       Intent intent = new Intent(Intent.ACTION_VIEW,
                               Uri.parse("http://www.youtube.com/watch?v=" + key));
                       startActivity(intent);
                   }
               });
                //For each Item when it is long clicked a context item appears
               /*myGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){


                   @Override
                   public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                       return false;
                   }
               });*/


            }
        }

    }
}
