package app.com.example.doha.movieproject;

import java.io.Serializable;

/**
 * Created by DOHA on 28/03/2016.
 */
//Imlements serializable to pass the object instances through intents
public class Movie implements Serializable{
    String Id;
    String Name;
    String Desc;
    String Poster;
    Movie(String id,String name,String desc,String poster){
        this.Id=id;
        this.Name=name;
        this.Desc=desc;
        this.Poster=poster;

    }
}
