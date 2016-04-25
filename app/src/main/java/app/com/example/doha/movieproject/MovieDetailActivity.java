package app.com.example.doha.movieproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        //Add the fragment of detail to the container
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(savedInstanceState==null){
            //getSupportFragmentManager().beginTransaction().add(R.id.movie_detail_container,new MovieDetailActivityFragment()).commit();
            Movie SelectedMovie=(Movie)getIntent().getSerializableExtra("SelectedMovieData");


            toolbar.setTitle(SelectedMovie.getName());
            MovieDetailActivityFragment detail=MovieDetailActivityFragment.getInstance(SelectedMovie);
          getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container,detail).commit();
        }



        setSupportActionBar(toolbar);

    }

}
