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

public class DatabaseRepositoryMovieHallHibernate implements Repository<MovieHall, Integer> {
    public DatabaseRepositoryMovieHallHibernate() throws SQLException {initialize();}
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
    @Override
    public void add(MovieHall itemToAdd)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<project.domainHibernate.Seat> seats = new ArrayList<>();
                for(Seat seat : itemToAdd.getHallConfiguration())
                {
                    seats.add(new project.domainHibernate.Seat(seat.getId(), seat.getLineNumber(), seat.getSeatnumber()));
                }
                project.domainHibernate.MovieHall movieHall = new project.domainHibernate.MovieHall(0, itemToAdd.getNumber(), seats);
                session.save(movieHall);
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
    public List<MovieHall> findAll()
    {
        List<MovieHall> movieHalls = new ArrayList<>();
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.MovieHall> comanda = session.createQuery("FROM MovieHall ", project.domainHibernate.MovieHall.class);
                List<project.domainHibernate.MovieHall> movieHallsHibernate = comanda.getResultList();
                for(project.domainHibernate.MovieHall movieHall: movieHallsHibernate)
                {
                    List<Seat> seats = new ArrayList<>();
                    for(project.domainHibernate.Seat seat : movieHall.getMovieHallConfiguration())
                    {
                        Seat seat1 = new Seat(seat.getLineNumber(), seat.getSeatnumber());
                        seat1.setId(seat.getId());
                        seats.add(seat1);
                    }
                    MovieHall movieHall1 = new MovieHall(movieHall.getNumber(), seats);
                    movieHall1.setId(movieHall.getId());
                    movieHalls.add(movieHall1);
                }
                tx.commit();
                return movieHalls;
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
    public Integer findId(MovieHall itemToFind)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.MovieHall> comanda = session.createQuery("FROM MovieHall WHERE number=:id", project.domainHibernate.MovieHall.class);
                comanda.setParameter("id", itemToFind.getNumber());
                project.domainHibernate.MovieHall usersHibernate = comanda.getSingleResult();
                tx.commit();
                return usersHibernate.getId();
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
}
