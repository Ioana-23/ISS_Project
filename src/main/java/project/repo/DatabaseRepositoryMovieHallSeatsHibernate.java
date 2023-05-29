package project.repo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import project.domain.MovieHall;
import project.domain.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepositoryMovieHallSeatsHibernate {
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
    public DatabaseRepositoryMovieHallSeatsHibernate() throws SQLException {initialize();}
    public void addMovieHallSeat(MovieHall movieHall, Seat seat)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                project.domainHibernate.MovieHall movieHall1 = session.get(project.domainHibernate.MovieHall.class, movieHall.getId());
                List<project.domainHibernate.Seat> seats = movieHall1.getMovieHallConfiguration();
                project.domainHibernate.Seat seat1 = new project.domainHibernate.Seat(seat.getId(), seat.getLineNumber(), seat.getSeatnumber());
                seats.add(seat1);
                movieHall1.setMovieHallConfiguration(seats);
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
    public boolean movieHallSeatsExists(MovieHall movieHall, Seat seat)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.MovieHall> comanda = session.createQuery("FROM MovieHall WHERE number=:number", project.domainHibernate.MovieHall.class);
                comanda.setParameter("number", movieHall.getNumber());
                project.domainHibernate.MovieHall movieHallsHibernate = comanda.getSingleResult();
                for(project.domainHibernate.Seat seat1: movieHallsHibernate.getMovieHallConfiguration())
                {
                    if(seat1.getId() == seat.getId())
                    {
                        return true;
                    }
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
        return false;
    }
}
