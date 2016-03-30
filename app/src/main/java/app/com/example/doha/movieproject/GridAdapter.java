package app.com.example.doha.movieproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by DOHA on 18/03/2016.
 */
//To make the grid items be displayed with my defined grid item layout
public class GridAdapter extends ArrayAdapter {

    ArrayList<Movie> Movies;
    Context AdapterContext;

    public GridAdapter(Context context, int resource,ArrayList<Movie> Movies) {
        super(context, resource, Movies);
        this.Movies=Movies;
        this.AdapterContext=context;
        inflater=LayoutInflater.from(AdapterContext);
    }
    LayoutInflater inflater;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView=convertView;


        if(itemView==null) //No recycled view ,so I will create my own view

        itemView=inflater.inflate(R.layout.new_image_item,null);

        String myUrl="http://image.tmdb.org/t/p/"+"w342"+this.Movies.get(position).getPoster();

       Log.d("URL", myUrl);
        Picasso.with(getContext()).load(myUrl).into((ImageView)itemView);

        return itemView;
    }

    @Override
    public int getCount() {

        //int count =this.ImgUrls.size();
        int count =this.Movies.size();
        return count;
    }
}
