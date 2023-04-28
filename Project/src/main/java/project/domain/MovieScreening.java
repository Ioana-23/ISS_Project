package project.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class MovieScreening extends Entity<Integer>{
    private Movie movie;
    private LocalDate date;
    private LocalTime time;
    private MovieHall movieHall;
    private Tip tip;
    public MovieScreening(Movie movie, LocalDate date, LocalTime time, MovieHall movieHall, Tip tip) {
        this.movie = movie;
        this.date = date;
        this.time = time;
        this.movieHall = movieHall;
        this.tip = tip;
    }

    public Tip getTip() {
        return tip;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public MovieHall getMovieHall() {
        return movieHall;
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
}
