package project.domainHibernate;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Reservation
{
    private int id;
    private MovieScreening movieScreening;
    private String status;
    private User user;
    private List<Seat> seatsReserved;
    public Reservation() {}

    public Reservation(int id, MovieScreening movieScreening, String status, User user, List<Seat> seatsReserved) {
        this.id = id;
        this.movieScreening = movieScreening;
        this.status = status;
        this.user = user;
        this.seatsReserved = seatsReserved;
    }

    public void setId(int id) {this.id = id;}
    public void setMovieScreening(MovieScreening movieScreening) {this.movieScreening = movieScreening;}
    public void setStatus(String status) {this.status = status;}
    public void setUser(User user) {this.user = user;}
    public void setSeatsReserved(List<Seat> seatsReserved) {this.seatsReserved = seatsReserved;}
    public int getId() {return id;}
    public MovieScreening getMovieScreening() {
        return movieScreening;
    }
    public String getStatus() {
        return status;
    }
    public User getUser() {
        return user;
    }
    public List<Seat> getSeatsReserved() {
        return seatsReserved;
    }
    public String getMovieName(){return movieScreening.getMovieName();}
    public Integer getMovieHall(){return movieScreening.getMovieHall().getNumber();}
}
