package app.com.example.doha.movieproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DOHA on 22/04/2016.
 */
public class TrailersAdapter extends ArrayAdapter{
    ArrayList<Trailer> Trailers;
    Context AdapterContext;

    public TrailersAdapter(Context context, int resource, ArrayList Trailers) {
        super(context, resource, Trailers);
        this.Trailers=Trailers;
        this.AdapterContext=context;
        inflater= LayoutInflater.from(AdapterContext);
    }
    LayoutInflater inflater;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView=convertView;


        if(itemView==null) //No recycled view ,so I will create my own view

            itemView=inflater.inflate(R.layout.trailer_item,null);
        //      itemView=inflater.inflate(R.layout.grid_item,null);

        TextView trailerItem=(TextView)itemView.findViewById(R.id.trailer);
        trailerItem.setText(this.Trailers.get(position).getName());

        return itemView;
    }

    @Override
    public int getCount() {

        //int count =this.ImgUrls.size();
        Integer count =this.Trailers.size();
        Log.d("REVIEWS Count ", count.toString());
        return count;
    }
}
