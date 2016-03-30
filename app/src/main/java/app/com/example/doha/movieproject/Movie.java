package app.com.example.doha.movieproject;

import java.io.Serializable;

/**
 * Created by DOHA on 28/03/2016.
 */
//Imlements serializable to pass the object instances through intents
public class Movie implements Serializable{
    private String Id;
    private String Name;
    private String Desc;
    private String Poster;
    private String vote_count;
    private String release;

    public String getDesc() {
        return Desc;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getPoster() {
        return Poster;
    }

    public String getRelease() {
        return release;
    }

    public String getVote_count() {
        return vote_count;
    }

    Movie(String id,String name,String desc,String poster,String vote,String release){
        this.Id=id;
        this.Name=name;
        this.Desc=desc;
        this.Poster=poster;

        this.vote_count=vote;
        this.release=release;
    }
}
