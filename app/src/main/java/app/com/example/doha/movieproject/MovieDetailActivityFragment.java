package app.com.example.doha.movieproject;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import app.com.example.doha.movieproject.DB.MoviesDB;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View detailView= inflater.inflate(R.layout.fragment_movie_detail, container, false);
        final Movie SelectedMovie=(Movie)getActivity().getIntent().getSerializableExtra("SelectedMovieData");
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
                String onButtonString=favButton.getText().toString();
                MoviesDB DBHelper = new MoviesDB(getContext());
                SQLiteDatabase db = DBHelper.getWritableDatabase();
                if(onButtonString.equals("Favorite")) {

                    DBHelper.insertToFav(db, SelectedMovie);
                    Toast.makeText(getContext(), "Successfully added ", Toast.LENGTH_LONG).show();
                    favButton.setText("Unfavorite");

                }
                else
                {

                    DBHelper.removeFromFav(db,SelectedMovie);
                    favButton.setText("Favorite");
                }
            }
        });
        return detailView;

    }
}
