package project.service;

import javafx.scene.control.Alert;
import project.domain.*;
import project.exceptions.AlreadyExistsException;
import project.exceptions.DoesNotExistExeption;
import project.repo.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Service
{
    private DatabaseRepositoryUser repoUser;
    private DatabaseRepositoryMovie repoMovie;
    private DatabaseRepositoryMovieHall repoMovieHall;
    private DatabaseRepositoryReservation repoReservation;
    private DatabaseRepositorySeat repoSeat;
    private DatabaseRepositoryMovieHallSeats repoMovieHallSeats;
    private DatabaseRepositoryReservationSeats repoReservationSeats;
    private DatabaseRepositoryMovieReview repoMovieReview;
    private DatabaseRepositoryMovieScreening repoMovieScreening;
    private Random random = new Random();
    private List<List<LocalTime>> timesPerDay = new ArrayList<>();
    public Service(DatabaseRepositoryUser repoUser, DatabaseRepositoryMovie repoMovie, DatabaseRepositoryMovieHall repoMovieHall, DatabaseRepositoryReservation repoReservation, DatabaseRepositorySeat repoSeat, DatabaseRepositoryMovieHallSeats repoMovieHallSeats, DatabaseRepositoryReservationSeats repoReservationSeats, DatabaseRepositoryMovieReview repoMovieReview, DatabaseRepositoryMovieScreening repoMovieScreening)
    {
        this.repoUser = repoUser;
        this.repoMovie = repoMovie;
        this.repoMovieHall = repoMovieHall;
        this.repoReservation = repoReservation;
        this.repoSeat = repoSeat;
        this.repoMovieHallSeats = repoMovieHallSeats;
        this.repoReservationSeats = repoReservationSeats;
        this.repoMovieReview = repoMovieReview;
        this.repoMovieScreening = repoMovieScreening;
        for(int i = 0; i < 10; i++)
        {
            timesPerDay.add(new ArrayList<>());
            if(i%2==0)
            {
                timesPerDay.get(i).add(LocalTime.parse("12:15:00"));
                timesPerDay.get(i).add(LocalTime.parse("18:30:00"));
                timesPerDay.get(i).add(LocalTime.parse("22:30:00"));
            }
            else
            {
                timesPerDay.get(i).add(LocalTime.parse("14:45:00"));
                timesPerDay.get(i).add(LocalTime.parse("19:00:00"));
                timesPerDay.get(i).add(LocalTime.parse("20:15:00"));
            }
        }
    }
    public void addMovieScreenings(List<LocalDate> dates)
    {
        repoMovieScreening.deleteAll();
        for(int k = 0; k < repoMovie.findAll().size(); k++)
        {
            for(int i = 0; i < dates.size(); i++)
            {
                int movieHall = random.nextInt(10);
                for(int j = 0; j < 3; j++)
                {
                    if(random.nextDouble()<0.3)
                    {
                        int tip = random.nextInt(3);
                        try
                        {
                            if(tip==0)
                            {
                                addMovieScreening(repoMovie.findAll().get(k).getName(), repoMovie.findAll().get(k).getGenre(), repoMovie.findAll().get(k).getReview(), repoMovie.findAll().get(k).getDate(), dates.get(i), timesPerDay.get(movieHall).get(j), Tip.DD, movieHall+1, repoMovieHall.findAll().get(movieHall).getHallConfiguration());
                            }
                            else if(tip == 1)
                            {
                                addMovieScreening(repoMovie.findAll().get(k).getName(), repoMovie.findAll().get(k).getGenre(), repoMovie.findAll().get(k).getReview(), repoMovie.findAll().get(k).getDate(), dates.get(i), timesPerDay.get(movieHall).get(j), Tip.DDD, movieHall+1, repoMovieHall.findAll().get(movieHall).getHallConfiguration());
                            }
                            else
                            {
                                addMovieScreening(repoMovie.findAll().get(k).getName(), repoMovie.findAll().get(k).getGenre(), repoMovie.findAll().get(k).getReview(), repoMovie.findAll().get(k).getDate(), dates.get(i), timesPerDay.get(movieHall).get(j), Tip.DDDDX, movieHall+1, repoMovieHall.findAll().get(movieHall).getHallConfiguration());
                            }
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    public List<User> findAllUsers()
    {
        return repoUser.findAll();
    }
    public void addUser(String email, String parola, String nume, List<Integer> numarTelefon)
    {
        try
        {
            User userToAdd = new User(email, parola, nume, numarTelefon);
            if(repoUser.findId(userToAdd)==-1)
            {
                repoUser.add(userToAdd);
            }
            else
            {
                throw new AlreadyExistsException("User with email: " + email + " already exists");
            }
        }
        catch (AlreadyExistsException e)
        {
                        Alert alert = new Alert (Alert.AlertType.WARNING, e.getMessage());
            alert.showAndWait();
        }
    }
    public Integer findUser(User userToFind)
    {
        return repoUser.findId(userToFind);
    }
    public User returnUser(Integer id)
    {
        return repoUser.returnUser(id);
    }


    public List<Movie> findAllMovies()
    {
        return repoMovie.findAll();
    }
    public void addMovie(String name, Genre genre, double review, LocalDate date)
    {
        try
        {
            Movie movieToAdd = new Movie(name, genre, review, date);
            if(repoMovie.findId(movieToAdd)==-1)
            {
                repoMovie.add(movieToAdd);
            }
            else
            {
                throw new AlreadyExistsException("Movie with name: " + name + " already exists");
            }
        }
        catch (AlreadyExistsException e)
        {
                        Alert alert = new Alert (Alert.AlertType.WARNING, e.getMessage());
            alert.showAndWait();
        }
    }
    public Integer findMovie(Movie movieToFind)
    {
        return repoMovie.findId(movieToFind);
    }


    public List<MovieHall> findAllMovieHalls()
    {
        return repoMovieHall.findAll();
    }
    public void addMovieHall(int number, List<Seat> movieHallConfiguration)
    {
        try
        {
            MovieHall movieHall = new MovieHall(number, movieHallConfiguration);
            if(repoMovieHall.findId(movieHall)==-1)
            {
                repoMovieHall.add(movieHall);
                movieHall.setId(repoMovieHall.findId(movieHall));
                for(int i = 0; i < movieHall.getHallConfiguration().size(); i++)
                {
                    Seat seat = movieHall.getHallConfiguration().get(i);
                    seat.setId(repoSeat.findId(seat));
                    repoMovieHallSeats.addMovieHallSeat(movieHall, seat);
                }
            }
            else
            {
                movieHall.setId(repoMovieHall.findId(movieHall));
                for(int i = 0; i < movieHall.getHallConfiguration().size(); i++)
                {
                    try
                    {
                        Seat seat = movieHall.getHallConfiguration().get(i);
                        seat.setId(repoSeat.findId(seat));
                        if(repoMovieHallSeats.movieHallSeatsExists(movieHall, seat))
                        {
                            throw new AlreadyExistsException("The seat with the number: (" + seat.getLineNumber() + ", " + seat.getSeatnumber() + ") in movie hall with number: " + movieHall.getNumber() + " already exists");
                        }
                        else
                        {
                            repoMovieHallSeats.addMovieHallSeat(movieHall, seat);
                        }
                    }
                    catch (AlreadyExistsException e)
                    {
                                    Alert alert = new Alert (Alert.AlertType.WARNING, e.getMessage());
            alert.showAndWait();
                    }

                }
                throw new AlreadyExistsException("Movie Hall with number: " + number + " already exists");

            }
        }
        catch (AlreadyExistsException e)
        {
                        Alert alert = new Alert (Alert.AlertType.WARNING, e.getMessage());
            alert.showAndWait();
        }

    }
    public Integer findMovieHall(MovieHall movieHallToFind)
    {
        return repoMovieHall.findId(movieHallToFind);
    }

    public void addMovieScreening(String name, Genre genre, double review, LocalDate date, LocalDate date1, LocalTime time, Tip tip,int number, List<Seat> movieHallConfiguration)
    {
        Movie movie = new Movie(name, genre, review, date);
        int idMovie = findMovie(movie);
        movie.setId(idMovie);
        MovieHall movieHall = new MovieHall(number, movieHallConfiguration);
        int idMovieHall = findMovieHall(movieHall);
        movieHall.setId(idMovieHall);
        try
        {
            MovieScreening movieScreening = new MovieScreening(movie, date1, time, movieHall, tip);
            if(repoMovieScreening.findId(movieScreening)==-1)
            {
                repoMovieScreening.add(movieScreening);
            }
            else
            {
                throw new AlreadyExistsException("The movie screening with name: " + name + " from: " + date1 + ", " + time + " already exists");
            }
        }
        catch (AlreadyExistsException e)
        {
                        Alert alert = new Alert (Alert.AlertType.WARNING, e.getMessage());
            alert.showAndWait();
        }
    }

    public void updateReservation(Reservation reservation, LocalDate date, LocalTime time)
    {
        List<MovieScreening> movieScreenings = findAllMovieScreening();
        for(int i = 0; i < movieScreenings.size(); i++)
        {
            if(movieScreenings.get(i).getMovieName().equals(reservation.getMovieName()))
            {
                if(movieScreenings.get(i).getDate().equals(date))
                {
                    if(movieScreenings.get(i).getTime().equals(time))
                    {
                        MovieScreening movieScreening = repoMovieScreening.returnMovieScreening(movieScreenings.get(i).getId());
                        repoReservation.update(reservation, movieScreening);
                    }
                }
            }
        }
    }
    public void deleteReservation(Reservation reservation)
    {
        int idMovieScreening = repoMovieScreening.findId(reservation.getMovieScreening());
        reservation.getMovieScreening().setId(idMovieScreening);
        int idUser = findUser(reservation.getUser());
        reservation.getUser().setId(idUser);
        repoReservation.delete(reservation);
    }
    public List<Reservation> findAllReservationsFromUser(User user)
    {
        return repoReservation.findReservations(user);
    }
    public List<Reservation> findAllReservations()
    {
        return repoReservation.findAll();
    }
    public void addReservation(String name, Genre genre, double review, LocalDate date, LocalDate date1, LocalTime time, int number, List<Seat> movieHallConfiguration, Tip tip, Status status, List<Seat> seatsReserved,String email, String parola, String nume, List<Integer> numarTelefon)
    {
        Movie movie = new Movie(name, genre, review, date);
        int idMovie = findMovie(movie);
        movie.setId(idMovie);
        MovieHall movieHall = new MovieHall(number, movieHallConfiguration);
        int idMovieHall = findMovieHall(movieHall);
        movieHall.setId(idMovieHall);
        MovieScreening movieScreening = new MovieScreening(movie, date1, time, movieHall, tip);
        int idMovieScreening = repoMovieScreening.findId(movieScreening);
        movieScreening.setId(idMovieScreening);
        User user = new User(email, parola, nume, numarTelefon);
        int idUser = findUser(user);
        user.setId(idUser);
        if(idMovieScreening!=-1 && idUser!=-1 && movieHall.getNumber()!=-1)
        {
            try
            {
                Reservation reservation = new Reservation(movieScreening, status, user, seatsReserved);
                if(!reservationExists(reservation))
                {
                    repoReservation.add(reservation);
                    reservation.setId(repoReservation.findId(reservation));
                    for(int i = 0; i < seatsReserved.size(); i++)
                    {
                        reservation.getSeatsReserved().get(i).setId(repoSeat.findId(reservation.getSeatsReserved().get(i)));
                        if(!repoReservationSeats.reservationSeatExists(reservation, reservation.getSeatsReserved().get(i)))
                        {
                            repoReservationSeats.addReservationSeat(reservation, reservation.getSeatsReserved().get(i));
                        }
                    }
                }
                else
                {
                    throw new AlreadyExistsException("Reservation for user with email: " + email + ", for movie: " + name + " for date: " + date + " " + time + " already exists");
                }
            }
            catch (AlreadyExistsException e)
            {
                Alert alert = new Alert (Alert.AlertType.WARNING, e.getMessage());
                alert.showAndWait();
            }
        }
    }
    public List<MovieScreening> findAllMovieScreening()
    {
        return repoMovieScreening.findAll();
    }
    public boolean reservationExists(Reservation reservation)
    {
        List<Reservation> reservationList = findAllReservations();
        for(int i = 0; i < reservationList.size(); i++)
        {
            if(reservationList.get(i).getMovieScreening().getId()==reservation.getMovieScreening().getId() && reservationList.get(i).getUser().getId()==reservation.getUser().getId())
            {
                return true;
            }
        }
        return false;
    }
    public List<MovieScreening> findMovieScreenFromDay(LocalDate date)
    {
        return repoMovieScreening.findFromDate(date);
    }
    public void addSeat(int lineNumber, int seatNumber)
    {
        try
        {
            Seat seat = new Seat(lineNumber, seatNumber);
            if(repoSeat.findId(seat)==-1)
            {
                repoSeat.add(seat);
            }
            else
            {
                throw new AlreadyExistsException("Seat with numbers: (" + lineNumber + ", " + seatNumber + ") already exists");
            }
        }
        catch (AlreadyExistsException e)
        {
                        Alert alert = new Alert (Alert.AlertType.WARNING, e.getMessage());
                alert.showAndWait();
        }
    }
    public List<Seat> getOccupiedSeats(MovieScreening movieScreening)
    {
        return repoMovieScreening.getOccupiedSeats(movieScreening);
    }
    public Integer findSeat(Seat seat)
    {
        return repoSeat.findId(seat);
    }

    public void addMovieHallSeat(int number, int lineNumber, int seatNumber)
    {
        try
        {
            MovieHall movieHall = new MovieHall(number, new ArrayList<>());
            int idMovieHall = findMovieHall(movieHall);
            if(idMovieHall==-1)
            {
                throw new DoesNotExistExeption("There is no movie hall with the number: " + number);
            }
            movieHall.setId(idMovieHall);
            Seat seat = new Seat(lineNumber, seatNumber);
            int idSeat = findSeat(seat);
            if(idSeat==-1)
            {
                throw new DoesNotExistExeption("There is no seat with the number: (" + lineNumber + ", " + seatNumber + ")");
            }
            seat.setId(idSeat);
            repoMovieHallSeats.addMovieHallSeat(movieHall, seat);
        }
        catch (DoesNotExistExeption e)
        {
            Alert alert = new Alert (Alert.AlertType.WARNING, e.getCause().toString());
            alert.showAndWait();
        }
    }

    public void addMovieReview(String name, Genre genre, double review, LocalDate date,String email, String parola, String nume, List<Integer> numarTelefon, double star)
    {
        Movie movie = new Movie(name, genre, review, date);
        int idMovie = findMovie(movie);
        movie.setId(idMovie);
        User user = new User(email, parola, nume, numarTelefon);
        int idUser = findUser(user);
        user.setId(idUser);
        MovieReview movieReview = new MovieReview(movie, user, star);
        if(repoMovieReview.findId(movieReview).getValue1()==-1)
        {
            repoMovieReview.add(movieReview);
        }
        else
        {
            repoMovieReview.update(movieReview, star);
        }
    }
}
