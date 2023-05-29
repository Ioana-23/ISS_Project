package project.domainHibernate;
import java.util.List;
public class MovieHall{
    private int id;
    private int number;
    private List<Seat> movieHallConfiguration;
    public MovieHall(int id, int number, List<Seat> movieHallConfiguration) {
        this.id = id;
        this.number = number;
        this.movieHallConfiguration = movieHallConfiguration;
    }

    public void setMovieHallConfiguration(List<Seat> movieHallConfiguration) {
        this.movieHallConfiguration = movieHallConfiguration;
    }
    public List<Seat> getMovieHallConfiguration() {
        return movieHallConfiguration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public MovieHall() {
    }

    public int getNumber() {return number;}

    public void setNumber(int number) {
        this.number = number;
    }
}
