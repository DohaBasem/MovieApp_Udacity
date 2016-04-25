package app.com.example.doha.movieproject;

/**
 * Created by DOHA on 22/04/2016.
 */
public class Review {
    Review(String id,String author,String content,String url){
        this.Id=id;
        this.author=author;
        this.content=content;
        this.Url=url;

    }
    private String Id;
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    private String content;
    private String Url;

}
