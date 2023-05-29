package project.service;

import javafx.scene.control.Alert;
import project.domain.*;
import project.exceptions.AlreadyExistsException;
import project.repo.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Service
{
    private final DatabaseRepositoryUserHibernate repoUser;
    private final DatabaseRepositoryMovieHibernate repoMovie;
    private final DatabaseRepositoryMovieHallHibernate repoMovieHall;
    private final DatabaseRepositoryReservationHibernate repoReservation;
    private final DatabaseRepositorySeatHibernate repoSeat;
    private final DatabaseRepositoryMovieHallSeatsHibernate repoMovieHallSeats;
    private final DatabaseRepositoryMovieReviewHibernate repoMovieReview;
    private final DatabaseRepositoryMovieScreeningHibernate repoMovieScreening;
    private final Random random = new Random();
    private final List<List<LocalTime>> timesPerDay = new ArrayList<>();
    public Service(DatabaseRepositoryUserHibernate repoUser, DatabaseRepositoryMovieHibernate repoMovie, DatabaseRepositoryMovieHallHibernate repoMovieHall, DatabaseRepositoryReservationHibernate repoReservation, DatabaseRepositorySeatHibernate repoSeat, DatabaseRepositoryMovieHallSeatsHibernate repoMovieHallSeats, DatabaseRepositoryMovieReviewHibernate repoMovieReview, DatabaseRepositoryMovieScreeningHibernate repoMovieScreening)
    {
        this.repoUser = repoUser;
        this.repoMovie = repoMovie;
        this.repoMovieHall = repoMovieHall;
        this.repoReservation = repoReservation;
        this.repoSeat = repoSeat;
        this.repoMovieHallSeats = repoMovieHallSeats;
        this.repoMovieReview = repoMovieReview;
        this.repoMovieScreening = repoMovieScreening;
        repoMovieHall.findAll();
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
            for (LocalDate date : dates)
            {
                int movieHall = random.nextInt(10);
                for (int j = 0; j < 3; j++)
                {
                    if (random.nextDouble() < 0.3)
                    {
                        int tip = random.nextInt(3);
                        try
                        {
                            if (tip == 0)
                            {
                                addMovieScreening(repoMovie.findAll().get(k), date, timesPerDay.get(movieHall).get(j), "DD", new MovieHall(movieHall + 1, repoMovieHall.findAll().get(movieHall).getHallConfiguration()));
                            }
                            else if (tip == 1)
                            {
                                addMovieScreening(repoMovie.findAll().get(k), date, timesPerDay.get(movieHall).get(j), "DDD", new MovieHall(movieHall + 1, repoMovieHall.findAll().get(movieHall).getHallConfiguration()));
                            }
                            else
                            {
                                addMovieScreening(repoMovie.findAll().get(k), date, timesPerDay.get(movieHall).get(j), "DDDDX", new MovieHall(movieHall + 1, repoMovieHall.findAll().get(movieHall).getHallConfiguration()));
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
    public boolean checkIfShould(List<LocalDate> date)
    {
        return !date.contains(repoMovieScreening.returnFirstDate());
    }
    public void addUser(User userToAdd)
    {
        try
        {
            if(repoUser.findId(userToAdd)==-1)
            {
                repoUser.add(userToAdd);
            }
            else
            {
                throw new AlreadyExistsException("User with email: " + userToAdd.getEmail() + " already exists");
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
        return repoUser.findId(userToFind);}
    public User returnUser(Integer id) {
        return repoUser.returnUser(id);}
    public List<Movie> findAllMovies()
    {
        return repoMovie.findAll();
    }
    public Integer findMovie(Movie movieToFind)
    {
        return repoMovie.findId(movieToFind);
    }
    public void addMovieHall(MovieHall movieHall)
    {
        try
        {
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
                throw new AlreadyExistsException("Movie Hall with number: " + movieHall.getNumber() + " already exists");

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
    public void addMovieScreening(Movie movie, LocalDate date1, LocalTime time, String tip,MovieHall movieHall)
    {
        int idMovie = findMovie(movie);
        movie.setId(idMovie);
        int idMovieHall = findMovieHall(movieHall);
        movieHall.setId(idMovieHall);
        try
        {
            MovieScreening movieScreening = new MovieScreening(movie, date1, time, movieHall, Tip.valueOf(tip));
            if(repoMovieScreening.findId(movieScreening)==-1)
            {
                repoMovieScreening.add(movieScreening);
            }
            else
            {
                throw new AlreadyExistsException("The movie screening with name: " + movie.getName() + " from: " + date1 + ", " + time + " already exists");
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
        reservation.setId(repoReservation.findId(reservation));
        for (MovieScreening screening : movieScreenings)
        {
            if (screening.getMovieName().equals(reservation.getMovieName()))
            {
                if (screening.getDate().equals(date))
                {
                    if (screening.getTime().equals(time))
                    {
                        MovieScreening movieScreening = repoMovieScreening.returnMovieScreening(screening.getId());
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
        reservation.setId(repoReservation.findId(reservation));
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
    public void addReservation(MovieScreening movie, Status status,List<Seat> seatsReserved, User user)
    {
        int idMovie = findMovie(movie.getMovie());
        movie.getMovie().setId(idMovie);
        int idMovieHall = findMovieHall(movie.getMovieHall());
        movie.getMovieHall().setId(idMovieHall);
        int idMovieScreening = repoMovieScreening.findId(movie);
        movie.setId(idMovieScreening);
        int idUser = findUser(user);
        user.setId(idUser);
        for(Seat seat: seatsReserved)
        {
            seat.setId(repoSeat.findId(seat));
        }
        if(idMovieScreening!=-1 && idUser!=-1 && movie.getMovieHall().getNumber()!=-1)
        {
            try
            {
                Reservation reservation = new Reservation(movie, status, user, seatsReserved);
                if(!reservationExists(reservation))
                {
                    repoReservation.add(reservation);
                }
                else
                {
                    throw new AlreadyExistsException("Reservation for user with email: " + user.getEmail() + ", for movie: " + movie.getMovie().getName() + " for date: " + movie.getDate() + " " + movie.getTime() + " already exists");
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
        for (Reservation value : reservationList)
        {
            if (value.getMovieScreening().getId().equals(reservation.getMovieScreening().getId()) && value.getUser().getId().equals(reservation.getUser().getId()))
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
    public List<Seat> getOccupiedSeats(MovieScreening movieScreening)
    {
        return repoMovieScreening.getOccupiedSeats(movieScreening);
    }
    public List<Movie> getWatchListForUser(User user)
    {
        return repoUser.findWatchList(user);
    }
    public void addMovieToWatchList(User user, Movie movie)
    {
        if(!repoUser.hasReview(user, movie))
        {
            user.setWatchList(new ArrayList<>(Arrays.asList(movie)));
            repoUser.updateWatchList(user);
        }
        else
        {
            Alert alert = new Alert (Alert.AlertType.WARNING, "The movie already exists in the user's watch list");
            alert.showAndWait();
        }
    }
    public void deleteMovieFromWatchList(User user, Movie movie)
    {
        repoUser.removeMovieFromWatchList(user, movie);
    }
    public void addMovieReview(Movie movie,User user, double star)
    {
        int idMovie = findMovie(movie);
        movie.setId(idMovie);
        int idUser = findUser(user);
        user.setId(idUser);
        MovieReview movieReview = new MovieReview(movie, user, star);
        movieReview.setId(repoMovieReview.findId(movieReview));
        if(repoMovieReview.findId(movieReview)==-1)
        {
            repoMovieReview.add(movieReview);
        }
        else
        {
            repoMovieReview.updateMovieReview(movieReview, star);
        }
    }
    public double findMovieReview(Movie movie, User user)
    {
        MovieReview movieReview = repoMovieReview.findReviewAfterIds(user, movie);
        if(movieReview!= null)
        {
            return movieReview.getStar();
        }
        return -1;
    }
    public void updateMovieReview(Movie movie, User user, double star)
    {
        MovieReview movieReview = new MovieReview(movie, user, star);
        movieReview.setId(repoMovieReview.findId(movieReview));
        repoMovieReview.updateMovieReview(movieReview, star);
    }
    public void updateUser(User oldUser, User newUser)
    {
        repoUser.updateUser(oldUser, newUser);
    }
}
