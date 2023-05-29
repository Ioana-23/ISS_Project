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

public class DatabaseRepositoryMovieScreeningHibernate implements Repository<MovieScreening, Integer> {
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
    public DatabaseRepositoryMovieScreeningHibernate() throws SQLException {initialize();}
    @Override
    public void add(MovieScreening itemToAdd)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                project.domainHibernate.Movie movie = new project.domainHibernate.Movie(itemToAdd.getMovie().getId(), itemToAdd.getMovie().getName(), itemToAdd.getMovie().getGenre(), itemToAdd.getMovie().getReview(), itemToAdd.getMovie().getDate().toString(), itemToAdd.getMovie().getInformatii(), itemToAdd.getMovie().getImagePath());
                List<project.domainHibernate.Seat> seats = new ArrayList<>();
                for(Seat seat: itemToAdd.getMovieHall().getHallConfiguration())
                {
                    project.domainHibernate.Seat seat1 = new project.domainHibernate.Seat(seat.getId(), seat.getLineNumber(), seat.getSeatnumber());
                    seats.add(seat1);
                }
                project.domainHibernate.MovieHall movieHall = new project.domainHibernate.MovieHall(itemToAdd.getMovieHall().getId(), itemToAdd.getMovieHall().getNumber(), seats);
                project.domainHibernate.MovieScreening movieScreening = new project.domainHibernate.MovieScreening(0, movie, itemToAdd.getDate().toString(), itemToAdd.getTime().toString(), movieHall,itemToAdd.getTip().toString());
                session.save(movieScreening);
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
    public LocalDate returnFirstDate()
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.MovieScreening> comanda = session.createQuery("FROM MovieScreening ", project.domainHibernate.MovieScreening.class);
                project.domainHibernate.MovieScreening movieScreeningHibernate = comanda.getResultList().get(0);
                tx.commit();
                return LocalDate.parse(movieScreeningHibernate.getDate());
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
    public void deleteAll()
    {
        {
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
                        project.domainHibernate.Reservation reservation1 = session.get(project.domainHibernate.Reservation.class, reservation.getId());
                        session.delete(reservation1);
                    }
                    Query<project.domainHibernate.MovieScreening> movieScreeningQuery = session.createQuery("FROM MovieScreening ", project.domainHibernate.MovieScreening.class);
                    List<project.domainHibernate.MovieScreening> movieScreeningsHibernate = movieScreeningQuery.getResultList();
                    for(project.domainHibernate.MovieScreening reservation: movieScreeningsHibernate)
                    {
                        project.domainHibernate.MovieScreening reservation1 = session.get(project.domainHibernate.MovieScreening.class, reservation.getId());
                        session.delete(reservation1);
                    }
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
    @Override
    public List<MovieScreening> findAll()
    {
        List<MovieScreening> movieScreenings = new ArrayList<>();
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.MovieScreening> comanda = session.createQuery("FROM MovieScreening ", project.domainHibernate.MovieScreening.class);
                List<project.domainHibernate.MovieScreening> movieScreeningsHibernate = comanda.getResultList();
                for(project.domainHibernate.MovieScreening movieScreening: movieScreeningsHibernate)
                {
                    Movie movie = new Movie(movieScreening.getMovie().getName(), movieScreening.getMovie().getGenre(), movieScreening.getMovie().getReview(), LocalDate.parse(movieScreening.getMovie().getDate()));
                    movie.setId(movieScreening.getMovie().getId());
                    movie.setInformatii(movieScreening.getMovie().getInformatii());
                    movie.setImagePath(movieScreening.getMovie().getImagePath());
                    List<Seat> seats = new ArrayList<>();
                    for(project.domainHibernate.Seat seat: movieScreening.getMovieHall().getMovieHallConfiguration())
                    {
                        Seat seat1 = new Seat(seat.getLineNumber(), seat.getSeatnumber());
                        seat1.setId(seat.getId());
                        seats.add(seat1);
                    }
                    MovieHall movieHall = new MovieHall(movieScreening.getMovieHall().getNumber(), seats);
                    movieHall.setId(movieScreening.getMovieHall().getId());
                    MovieScreening movieScreening1 = new MovieScreening(movie, LocalDate.parse(movieScreening.getDate()), LocalTime.parse(movieScreening.getTime()), movieHall, Tip.valueOf(movieScreening.getTip()));
                    movieScreening1.setId(movieScreening.getId());
                    movieScreenings.add(movieScreening1);
                }
                tx.commit();
                return movieScreenings;
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
    public List<Seat> getOccupiedSeats(MovieScreening movieScreening)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.Reservation> comanda = session.createQuery("FROM Reservation WHERE movieScreening.id=:id", project.domainHibernate.Reservation.class);
                comanda.setParameter("id", movieScreening.getId());
                List<project.domainHibernate.Reservation> movieScreeningHibernate = comanda.getResultList();
                List<Seat> seats = new ArrayList<>();
                for(project.domainHibernate.Reservation reservation: movieScreeningHibernate)
                {
                    for(project.domainHibernate.Seat seat : reservation.getSeatsReserved())
                    {
                        Seat seat1 = new Seat(seat.getLineNumber(), seat.getSeatnumber());
                        seat1.setId(seat.getId());
                        seats.add(seat1);
                    }
                }
                tx.commit();
                return seats;
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
        return new ArrayList<>();
    }
    @Override
    public Integer findId(MovieScreening itemToFind)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.MovieScreening> comanda = session.createQuery("FROM MovieScreening WHERE date=:date AND time=:time AND movie.id=:idMovie", project.domainHibernate.MovieScreening.class);
                comanda.setParameter("idMovie", itemToFind.getMovie().getId());
                comanda.setParameter("date", itemToFind.getDate().toString());
                comanda.setParameter("time", itemToFind.getTime().toString());
                project.domainHibernate.MovieScreening movieScreeningsHibernate = comanda.getSingleResult();
                tx.commit();
                return movieScreeningsHibernate.getId();
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
    public List<MovieScreening> findFromDate(LocalDate date)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.MovieScreening> comanda = session.createQuery("FROM MovieScreening WHERE date=:date", project.domainHibernate.MovieScreening.class);
                comanda.setParameter("date", date.toString());
                List<project.domainHibernate.MovieScreening> movieScreeningsHibernate = comanda.getResultList();
                List<MovieScreening> movieScreenings = new ArrayList<>();
                for(project.domainHibernate.MovieScreening movieScreening: movieScreeningsHibernate)
                {
                    Movie movie = new Movie(movieScreening.getMovie().getName(), movieScreening.getMovie().getGenre(), movieScreening.getMovie().getReview(), LocalDate.parse(movieScreening.getMovie().getDate()));
                    movie.setId(movieScreening.getMovie().getId());
                    movie.setInformatii(movieScreening.getMovie().getInformatii());
                    movie.setImagePath(movieScreening.getMovie().getImagePath());
                    List<Seat> seats = new ArrayList<>();
                    for(project.domainHibernate.Seat seat: movieScreening.getMovieHall().getMovieHallConfiguration())
                    {
                        Seat seat1 = new Seat(seat.getLineNumber(), seat.getSeatnumber());
                        seat1.setId(seat.getId());
                        seats.add(seat1);
                    }
                    MovieHall movieHall = new MovieHall(movieScreening.getMovieHall().getNumber(), seats);
                    movieHall.setId(movieScreening.getMovieHall().getId());
                    MovieScreening movieScreening1 = new MovieScreening(movie, LocalDate.parse(movieScreening.getDate()), LocalTime.parse(movieScreening.getTime()), movieHall, Tip.valueOf(movieScreening.getTip()));
                    movieScreening1.setId(movieScreening.getId());
                    movieScreenings.add(movieScreening1);
                }
                tx.commit();
                return movieScreenings;
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
    public MovieScreening returnMovieScreening(Integer id)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.MovieScreening> comanda = session.createQuery("FROM MovieScreening WHERE id=:id", project.domainHibernate.MovieScreening.class);
                comanda.setParameter("id", id);
                project.domainHibernate.MovieScreening movieScreeningsHibernate = comanda.getSingleResult();
                Movie movie = new Movie(movieScreeningsHibernate.getMovie().getName(), movieScreeningsHibernate.getMovie().getGenre(), movieScreeningsHibernate.getMovie().getReview(), LocalDate.parse(movieScreeningsHibernate.getMovie().getDate()));
                movie.setId(movieScreeningsHibernate.getMovie().getId());
                movie.setInformatii(movieScreeningsHibernate.getMovie().getInformatii());
                movie.setImagePath(movieScreeningsHibernate.getMovie().getImagePath());
                List<Seat> seats = new ArrayList<>();
                for(project.domainHibernate.Seat seat: movieScreeningsHibernate.getMovieHall().getMovieHallConfiguration())
                {
                    Seat seat1 = new Seat(seat.getLineNumber(), seat.getSeatnumber());
                    seat1.setId(seat.getId());
                    seats.add(seat1);
                }
                MovieHall movieHall = new MovieHall(movieScreeningsHibernate.getMovieHall().getNumber(), seats);
                movieHall.setId(movieScreeningsHibernate.getMovieHall().getId());
                MovieScreening movieScreening1 = new MovieScreening(movie, LocalDate.parse(movieScreeningsHibernate.getDate()), LocalTime.parse(movieScreeningsHibernate.getTime()), movieHall, Tip.valueOf(movieScreeningsHibernate.getTip()));
                movieScreening1.setId(movieScreeningsHibernate.getId());
                tx.commit();
                return movieScreening1;
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
}
