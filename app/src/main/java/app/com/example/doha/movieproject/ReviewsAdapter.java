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
 * Created by DOHA on 18/03/2016.
 */
//To make the grid items be displayed with my defined grid item layout
public class ReviewsAdapter extends ArrayAdapter {

    ArrayList<Review> Reviews;
    Context AdapterContext;

    public ReviewsAdapter(Context context, int resource, ArrayList Reviews) {
        super(context, resource, Reviews);
        this.Reviews=Reviews;
        this.AdapterContext=context;
        inflater=LayoutInflater.from(AdapterContext);
    }
    LayoutInflater inflater;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView=convertView;


        if(itemView==null) //No recycled view ,so I will create my own view

        itemView=inflater.inflate(R.layout.review_item,null);
      //      itemView=inflater.inflate(R.layout.grid_item,null);

        TextView authorText=(TextView)itemView.findViewById(R.id.author);
        TextView reviewText=(TextView)itemView.findViewById(R.id.content);
        authorText.setText(this.Reviews.get(position).getAuthor());
        reviewText.setText(this.Reviews.get(position).getContent());


        return itemView;
    }

    @Override
    public int getCount() {

        //int count =this.ImgUrls.size();
        Integer count =this.Reviews.size();
        Log.d("REVIEWS Count ",count.toString());
        return count;
    }
}
