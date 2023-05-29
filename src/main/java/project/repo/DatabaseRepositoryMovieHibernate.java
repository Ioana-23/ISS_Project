package project.repo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import project.domain.Movie;
import project.domain.User;
import project.run.Run;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepositoryMovieHibernate implements Repository<Movie, Integer>{
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
    public DatabaseRepositoryMovieHibernate() throws SQLException {initialize();}
    @Override
    public void add(Movie itemToAdd)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                project.domainHibernate.Movie movie = new project.domainHibernate.Movie(itemToAdd.getId(), itemToAdd.getName(), itemToAdd.getGenre(), itemToAdd.getReview(), itemToAdd.getDate().toString(), itemToAdd.getInformatii(), itemToAdd.getImagePath());
                session.save(movie);
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
    public List<Movie> findAll()
    {
        List<Movie> movies = new ArrayList<>();
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.Movie> comanda = session.createQuery("FROM Movie ", project.domainHibernate.Movie.class);
                List<project.domainHibernate.Movie> moviesHibernate = comanda.getResultList();
                for(project.domainHibernate.Movie movie: moviesHibernate)
                {
                    Movie movie1 = new Movie(movie.getName(), movie.getGenre(), movie.getReview(), LocalDate.parse(movie.getDate()));
                    movie1.setId(movie.getId());
                    movie1.setInformatii(movie.getInformatii());
                    movie1.setImagePath(movie.getImagePath());
                    movies.add(movie1);
                }
                tx.commit();
                return movies;
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
    public Integer findId(Movie itemToFind)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.Movie> comanda = session.createQuery("FROM Movie WHERE id=:id");
                comanda.setParameter("id", itemToFind.getId());
                project.domainHibernate.Movie movieHibernate = comanda.getSingleResult();
                tx.commit();
                return movieHibernate.getId();
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
    public Movie findMovie(Integer id)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.Movie> comanda = session.createQuery("FROM Movie WHERE id=:id", project.domainHibernate.Movie.class);
                comanda.setParameter("id", id);
                project.domainHibernate.Movie movie = comanda.getSingleResult();
                Movie movie1 = new Movie(movie.getName(), movie.getGenre(), movie.getReview(), LocalDate.parse(movie.getDate()));
                movie1.setId(movie.getId());
                movie1.setInformatii(movie.getInformatii());
                movie1.setImagePath(movie.getImagePath());
                tx.commit();
                return movie1;
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
