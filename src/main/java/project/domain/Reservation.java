package project.domain;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Reservation extends Entity<Integer>
{
    private final MovieScreening movieScreening;
    private final Status status;
    private final User user;
    private final List<Seat> seatsReserved;
    public Reservation(MovieScreening movie, Status status, User user, List<Seat> seatsReserved) {
        this.movieScreening = movie;
        this.status = status;
        this.user = user;
        this.seatsReserved = seatsReserved;
    }
    public MovieScreening getMovieScreening() {
        return movieScreening;
    }
    public Status getStatus() {
        return status;
    }
    public User getUser() {
        return user;
    }
    public List<Seat> getSeatsReserved() {
        return seatsReserved;
    }
    public String getMovieName(){return movieScreening.getMovieName();}
    public LocalTime getTime(){return movieScreening.getTime();}
    public LocalDate getData(){return movieScreening.getDate();}
    public List<Pair<Integer, Integer>> getSeats()
    {
        List<Pair<Integer, Integer>> lista = new ArrayList<>();
        for(int i = 0; i < seatsReserved.size(); i++)
        {
            lista.add(new Pair<>(seatsReserved.get(i).getLineNumber(), seatsReserved.get(i).getSeatnumber()));
        }
        return lista;
    }
    public Integer getMovieHall(){return movieScreening.getMovieHall().getNumber();}
}
