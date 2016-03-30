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

    //ArrayList<String> ImgUrls;
    ArrayList<Movie> Movies;
    Context AdapterContext;

    //public GridAdapter(Context context, int resource,ArrayList<String> ImgUrls)
    public GridAdapter(Context context, int resource,ArrayList<Movie> Movies) {
        //super(context, resource, ImgUrls);
        super(context, resource, Movies);
        //this.ImgUrls=ImgUrls;
        this.Movies=Movies;
        this.AdapterContext=context;
        inflater=LayoutInflater.from(AdapterContext);
       // this.BASE_URL=BASE_URL;
    }
    LayoutInflater inflater;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView=convertView;


        if(itemView==null) //No recycled view ,so I will create my own view
        //{itemView= LayoutInflater.from(AdapterContext).inflate(R.layout.grid_item);}
        itemView=inflater.inflate(R.layout.new_image_item,null);
        //ImageView imageview=(ImageView)itemView.findViewById(R.id.poster);
        //How can I fetch the data from the array of strings executed from doInBackground ??
       // Uri.Builder builder = new Uri.Builder();
        //builder.path(" http://image.tmdb.org/t/p/").appendPath("w185").appendPath(this.ImgUrls.get(position)).build();
       // builder.authority(" http://image.tmdb.org/t/p/").appendPath("w185").appendPath(this.ImgUrls.get(position));
       //String myUrl="http://image.tmdb.org/t/p/"+"w342"+this.ImgUrls.get(position);
        String myUrl="http://image.tmdb.org/t/p/"+"w342"+this.Movies.get(position).Poster;
      //  String myUrl = builder.build().getPath().toString();
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
