package project.repo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import project.domain.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepositoryReservationHibernate implements Repository<Reservation, Integer>
{
    static SessionFactory sessionFactory;
    private void initialize()
    {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try{
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e)
        {
            System.err.println(e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
    public DatabaseRepositoryReservationHibernate() throws SQLException {initialize();}
    @Override
    public void add(Reservation itemToAdd)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                project.domainHibernate.Movie movie = new project.domainHibernate.Movie(itemToAdd.getMovieScreening().getMovie().getId(), itemToAdd.getMovieScreening().getMovie().getName(), itemToAdd.getMovieScreening().getMovie().getGenre(), itemToAdd.getMovieScreening().getMovie().getReview(), itemToAdd.getMovieScreening().getMovie().getDate().toString(), itemToAdd.getMovieScreening().getMovie().getInformatii(), itemToAdd.getMovieScreening().getMovie().getImagePath());
                List<project.domainHibernate.Seat> seats = new ArrayList<>();
                for(Seat seat: itemToAdd.getMovieScreening().getMovieHall().getHallConfiguration())
                {
                    seats.add(new project.domainHibernate.Seat(seat.getId(), seat.getLineNumber(), seat.getSeatnumber()));
                }
                List<project.domainHibernate.Seat> seats1 = new ArrayList<>();
                for(Seat seat: itemToAdd.getSeatsReserved())
                {
                    seats1.add(new project.domainHibernate.Seat(seat.getId(), seat.getLineNumber(), seat.getSeatnumber()));
                }
                List<project.domainHibernate.Movie> movies = new ArrayList<>();
                for(Movie movie1: itemToAdd.getUser().getWatchList())
                {
                    movies.add(new project.domainHibernate.Movie(movie1.getId(), movie1.getName(), movie1.getGenre(), movie1.getReview(),movie1.getDate().toString(), movie1.getInformatii(), movie1.getImagePath()));
                }
                project.domainHibernate.MovieHall movieHall = new project.domainHibernate.MovieHall(itemToAdd.getMovieScreening().getMovieHall().getId(), itemToAdd.getMovieScreening().getMovieHall().getNumber(), seats);
                project.domainHibernate.MovieScreening movieScreening = new project.domainHibernate.MovieScreening(itemToAdd.getMovieScreening().getId(), movie, itemToAdd.getMovieScreening().getDate().toString(), itemToAdd.getMovieScreening().getTime().toString(), movieHall, itemToAdd.getMovieScreening().getTip().toString());
                project.domainHibernate.User user = new project.domainHibernate.User(itemToAdd.getUser().getId(), itemToAdd.getUser().getEmail(), itemToAdd.getUser().getParola(), itemToAdd.getUser().getNume(), itemToAdd.getUser().getNumarTelefon().toString(), movies);
                project.domainHibernate.Reservation reservation = new project.domainHibernate.Reservation(0, movieScreening, itemToAdd.getStatus().toString(), user, seats1);
                session.save(reservation);
                tx.commit();
            }
            catch (RuntimeException e)
            {
                if(tx!=null)
                {
                    tx.rollback();
                }
            }
        }
    }
    @Override
    public List<Reservation> findAll()
    {
        List<Reservation> reservationList = new ArrayList<>();
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.Reservation> comanda = session.createQuery("FROM Reservation ", project.domainHibernate.Reservation.class);
                List<project.domainHibernate.Reservation> reservationsHibernate = comanda.getResultList();
                for(project.domainHibernate.Reservation reservation: reservationsHibernate)
                {
                    String[] numere = reservation.getUser().getTelefonNumber().split(", ");
                    numere[0] = numere[0].split("\\[")[1];
                    numere[numere.length-1] = numere[numere.length-1].split("]")[0];
                    List<Integer> numarTelefon = new ArrayList<>();
                    for (String s : numere)
                    {
                        numarTelefon.add(Integer.parseInt(s));
                    }
                    User user = new User(reservation.getUser().getEmail(), reservation.getUser().getParola(), reservation.getUser().getNume(), numarTelefon);
                    user.setId(reservation.getUser().getId());
                    List<Seat> seats = new ArrayList<>();
                    for(project.domainHibernate.Seat seat: reservation.getMovieScreening().getMovieHall().getMovieHallConfiguration())
                    {
                        Seat seat1 = new Seat(seat.getLineNumber(), seat.getSeatnumber());
                        seat1.setId(seat.getId());
                        seats.add(seat1);
                    }
                    List<Seat> seats1 = new ArrayList<>();
                    for(project.domainHibernate.Seat seat: reservation.getSeatsReserved())
                    {
                        Seat seat1 = new Seat(seat.getLineNumber(), seat.getSeatnumber());
                        seats1.add(seat1);
                    }
                    MovieHall movieHall = new MovieHall(reservation.getMovieScreening().getMovieHall().getNumber(), seats);
                    movieHall.setId(reservation.getMovieScreening().getMovieHall().getId());
                    Movie movie = new Movie(reservation.getMovieScreening().getMovie().getName(), reservation.getMovieScreening().getMovie().getGenre(), reservation.getMovieScreening().getMovie().getReview(), LocalDate.parse(reservation.getMovieScreening().getMovie().getDate()));
                    movie.setImagePath(reservation.getMovieScreening().getMovie().getImagePath());
                    movie.setInformatii(reservation.getMovieScreening().getMovie().getInformatii());
                    movie.setId(reservation.getMovieScreening().getMovie().getId());
                    MovieScreening movieScreening = new MovieScreening(movie, LocalDate.parse(reservation.getMovieScreening().getDate()), LocalTime.parse(reservation.getMovieScreening().getTime()), movieHall, Tip.valueOf(reservation.getMovieScreening().getTip()));
                    movieScreening.setId(reservation.getMovieScreening().getId());
                    Reservation reservation1 = new Reservation(movieScreening, Status.valueOf(reservation.getStatus()), user, seats1);
                    reservationList.add(reservation1);
                }
                tx.commit();
                return reservationList;
            }
            catch (RuntimeException e)
            {
                System.err.println(e);
                if(tx!=null)
                {
                    tx.rollback();
                }
            }
        }
        return null;
    }
    @Override
    public Integer findId(Reservation itemToFind)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.Reservation> comanda = session.createQuery("FROM Reservation WHERE user.id=:idUser AND movieScreening.id=:idMovieScreening", project.domainHibernate.Reservation.class);
                comanda.setParameter("idUser", itemToFind.getUser().getId());
                comanda.setParameter("idMovieScreening", itemToFind.getMovieScreening().getId());
                project.domainHibernate.Reservation reservationsHibernate = comanda.getSingleResult();
                tx.commit();
                return reservationsHibernate.getId();
            }
            catch (RuntimeException e)
            {
                System.err.println(e);
                if(tx!=null)
                {
                    tx.rollback();
                }
            }
        }
        return -1;
    }
    public List<Reservation> findReservations(User user)
    {
        List<Reservation> reservationList = new ArrayList<>();
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.Reservation> comanda = session.createQuery("FROM Reservation WHERE user.id=:id", project.domainHibernate.Reservation.class);
                comanda.setParameter("id", user.getId());
                List<project.domainHibernate.Reservation> reservationsHibernate = comanda.getResultList();
                for(project.domainHibernate.Reservation reservation: reservationsHibernate)
                {
                    String[] numere = reservation.getUser().getTelefonNumber().split(", ");
                    numere[0] = numere[0].split("\\[")[1];
                    numere[numere.length-1] = numere[numere.length-1].split("]")[0];
                    List<Integer> numarTelefon = new ArrayList<>();
                    for (String s : numere)
                    {
                        numarTelefon.add(Integer.parseInt(s));
                    }
                    User user1 = new User(reservation.getUser().getEmail(), reservation.getUser().getParola(), reservation.getUser().getNume(), numarTelefon);
                    user1.setId(reservation.getUser().getId());
                    List<Seat> seats = new ArrayList<>();
                    for(project.domainHibernate.Seat seat: reservation.getMovieScreening().getMovieHall().getMovieHallConfiguration())
                    {
                        Seat seat1 = new Seat(seat.getLineNumber(), seat.getSeatnumber());
                        seat1.setId(seat.getId());
                        seats.add(seat1);
                    }
                    List<Seat> seats1 = new ArrayList<>();
                    for(project.domainHibernate.Seat seat: reservation.getSeatsReserved())
                    {
                        Seat seat1 = new Seat(seat.getLineNumber(), seat.getSeatnumber());
                        seats1.add(seat1);
                    }
                    MovieHall movieHall = new MovieHall(reservation.getMovieScreening().getMovieHall().getNumber(), seats);
                    movieHall.setId(reservation.getMovieScreening().getMovieHall().getId());
                    Movie movie = new Movie(reservation.getMovieScreening().getMovie().getName(), reservation.getMovieScreening().getMovie().getGenre(), reservation.getMovieScreening().getMovie().getReview(), LocalDate.parse(reservation.getMovieScreening().getMovie().getDate()));
                    movie.setImagePath(reservation.getMovieScreening().getMovie().getImagePath());
                    movie.setInformatii(reservation.getMovieScreening().getMovie().getInformatii());
                    movie.setId(reservation.getMovieScreening().getMovie().getId());
                    MovieScreening movieScreening = new MovieScreening(movie, LocalDate.parse(reservation.getMovieScreening().getDate()), LocalTime.parse(reservation.getMovieScreening().getTime()), movieHall, Tip.valueOf(reservation.getMovieScreening().getTip()));
                    movieScreening.setId(reservation.getMovieScreening().getId());
                    Reservation reservation1 = new Reservation(movieScreening, Status.valueOf(reservation.getStatus()), user1, seats1);
                    reservationList.add(reservation1);
                }
                tx.commit();
                return reservationList;
            }
            catch (RuntimeException e)
            {
                System.err.println(e);
                if(tx!=null)
                {
                    tx.rollback();
                }
            }
        }
        return null;
    }
    public Integer findReservation(Reservation reservation)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.Reservation> comanda = session.createQuery("FROM Reservation WHERE user.id=:idUser AND movieScreening.id=:idMovieScreening", project.domainHibernate.Reservation.class);
                comanda.setParameter("idUser", reservation.getUser().getId());
                comanda.setParameter("idMovieScreening", reservation.getMovieScreening().getId());
                project.domainHibernate.Reservation reservationsHibernate = comanda.getSingleResult();
                tx.commit();
                return reservationsHibernate.getId();
            }
            catch (RuntimeException e)
            {
                System.err.println(e);
                if(tx!=null)
                {
                    tx.rollback();
                }
            }
        }
        return -1;
    }
    public void delete(Reservation reservation)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                project.domainHibernate.Reservation comanda = session.get(project.domainHibernate.Reservation.class, reservation.getId());
                session.delete(comanda);
                tx.commit();
            }
            catch (RuntimeException e)
            {
                System.err.println(e);
                if(tx!=null)
                {
                    tx.rollback();
                }
            }
        }
    }
    public void update(Reservation reservation, MovieScreening movieScreening)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                project.domainHibernate.Reservation comanda = session.get(project.domainHibernate.Reservation.class, reservation.getId());
                project.domainHibernate.Movie movie = new project.domainHibernate.Movie(movieScreening.getMovie().getId(), movieScreening.getMovie().getName(), movieScreening.getMovie().getGenre(), movieScreening.getMovie().getReview(), movieScreening.getMovie().getDate().toString(), movieScreening.getMovie().getInformatii(), movieScreening.getMovie().getImagePath());
                List<project.domainHibernate.Seat> seats = new ArrayList<>();
                for(Seat seat: movieScreening.getMovieHall().getHallConfiguration())
                {
                    seats.add(new project.domainHibernate.Seat(seat.getId(), seat.getLineNumber(), seat.getSeatnumber()));
                }
                project.domainHibernate.MovieHall movieHall = new project.domainHibernate.MovieHall(movieScreening.getMovieHall().getId(), movieScreening.getMovieHall().getNumber(), seats);
                project.domainHibernate.MovieScreening movieScreening1 = new project.domainHibernate.MovieScreening(movieScreening.getId(), movie, movieScreening.getDate().toString(), movieScreening.getTime().toString(), movieHall, movieScreening.getTip().toString());
                comanda.setMovieScreening(movieScreening1);
                tx.commit();
            }
            catch (RuntimeException e)
            {
                System.err.println(e);
                if(tx!=null)
                {
                    tx.rollback();
                }
            }
        }
    }
}
