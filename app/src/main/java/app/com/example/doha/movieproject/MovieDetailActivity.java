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
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.movie_detail_container,new MovieDetailActivityFragment()).commit();


        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
