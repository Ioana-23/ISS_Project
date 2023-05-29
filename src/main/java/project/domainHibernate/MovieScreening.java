package project.domainHibernate;
public class MovieScreening{
    private int id;
    private Movie movie;
    private String date;
    private String time;
    private MovieHall movieHall;
    private String tip;

    public MovieScreening() {}
    public MovieScreening(int id, Movie movie, String date, String time, MovieHall movieHall, String tip) {
        this.id = id;
        this.movie = movie;
        this.date = date;
        this.time = time;
        this.movieHall = movieHall;
        this.tip = tip;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setMovieHall(MovieHall movieHall) {
        this.movieHall = movieHall;
    }
    public void setTip(String tip) {
        this.tip = tip;
    }
    public String getTip() {
        return tip;
    }
    public Movie getMovie() {
        return movie;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public String getMovieName()
    {
        return movie.getName();
    }
    public String getTipString()
    {
        return tip.toString();
    }
    public String getGenString(){return movie.getGenre().toString();}
    public Double getReview(){return movie.getReview();}
    public int getId() {return id;}
    public MovieHall getMovieHall() {return movieHall;}
    public void setId(int id) {this.id = id;}
}
