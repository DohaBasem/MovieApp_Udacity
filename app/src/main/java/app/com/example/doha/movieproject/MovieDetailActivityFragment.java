package app.com.example.doha.movieproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        Movie SelectedMovie=(Movie)getActivity().getIntent().getSerializableExtra("SelectedMovieData");
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

        return detailView;

    }
}
