package app.com.example.doha.movieproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements Passable {
    static boolean TwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Check if it is 2 pane or 1 pane
        /*if(findViewById(R.id.movie_detail_container)!=null){
            TwoPane=true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new MovieDetailActivityFragment())
                        .commit();

        }
        else{
            TwoPane=false;
            }
        }*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.Popularity){


        }
        if(id==R.id.rating){}
        return super.onOptionsItemSelected(item);
    }
    public void passSelectedMovie(Movie SelectedMovie){

            //Create an instance of details fragment
            if(findViewById(R.id.movie_detail_container)!=null){
                MovieDetailActivityFragment detail=MovieDetailActivityFragment.getInstance(SelectedMovie);
                TwoPane=true;
                getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_detail_container, detail)
                            .commit();



        }
        else{
                TwoPane=false;
                Intent toDetail = new Intent(this, MovieDetailActivity.class).putExtra("SelectedMovieData", SelectedMovie);
                startActivity(toDetail);

            }



    }
}
