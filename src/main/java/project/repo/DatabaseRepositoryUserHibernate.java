package project.repo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import project.domain.User;
import project.domainHibernate.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepositoryUserHibernate implements Repository<User, Integer>{
    public DatabaseRepositoryUserHibernate() throws SQLException {initialize();}
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
    public void add(User itemToAdd)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                project.domainHibernate.User user = new project.domainHibernate.User(0, itemToAdd.getEmail(), itemToAdd.getParola(), itemToAdd.getNume(), itemToAdd.getNumarTelefon().toString(), new ArrayList<>());
                session.save(user);
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
    public List<User> findAll()
    {
        List<User> users = new ArrayList<>();
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.User> comanda = session.createQuery("FROM User", project.domainHibernate.User.class);
                List<project.domainHibernate.User> usersHibernate = comanda.getResultList();
                for(project.domainHibernate.User user: usersHibernate)
                {
                    String[] numere = user.getTelefonNumber().split(", ");
                    numere[0] = numere[0].split("\\[")[1];
                    numere[numere.length-1] = numere[numere.length-1].split("]")[0];
                    List<Integer> numarTelefon = new ArrayList<>();
                    for (String s : numere)
                    {
                        numarTelefon.add(Integer.parseInt(s));
                    }
                    List<project.domain.Movie> movies = new ArrayList<>();
                    for(Movie movie: user.getWatchList())
                    {
                        project.domain.Movie movie1 = new project.domain.Movie(movie.getName(), movie.getGenre(), movie.getReview(), LocalDate.parse(movie.getDate()));
                        movie1.setImagePath(movie.getImagePath());
                        movie1.setInformatii(movie.getInformatii());
                        movie1.setId(movie.getId());
                        movies.add(movie1);
                    }
                    User user1 = new User(user.getEmail(), user.getParola(), user.getNume(), numarTelefon);
                    user1.setId(user.getId());
                    users.add(user1);
                }
                tx.commit();
                return users;
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
    public Integer findId(User itemToFind)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.User> comanda = session.createQuery("FROM User WHERE email=:id", project.domainHibernate.User.class);
                comanda.setParameter("id", itemToFind.getEmail());
                project.domainHibernate.User usersHibernate = comanda.getSingleResult();
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
    public User returnUser(Integer id)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.User> comanda = session.createQuery("FROM User WHERE id=:id", project.domainHibernate.User.class);
                comanda.setParameter("id", id);
                project.domainHibernate.User usersHibernate = comanda.getSingleResult();
                String[] numere = usersHibernate.getTelefonNumber().split(", ");
                numere[0] = numere[0].split("\\[")[1];
                numere[numere.length-1] = numere[numere.length-1].split("]")[0];
                List<Integer> numarTelefon = new ArrayList<>();
                for (String s : numere)
                {
                    numarTelefon.add(Integer.parseInt(s));
                }
                List<project.domain.Movie> movies = new ArrayList<>();
                for(Movie movie: usersHibernate.getWatchList())
                {
                    project.domain.Movie movie1 = new project.domain.Movie(movie.getName(), movie.getGenre(), movie.getReview(), LocalDate.parse(movie.getDate()));
                    movie1.setImagePath(movie.getImagePath());
                    movie1.setInformatii(movie.getInformatii());
                    movie1.setId(movie.getId());
                    movies.add(movie1);
                }
                User user = new User(usersHibernate.getEmail(), usersHibernate.getParola(), usersHibernate.getNume(), numarTelefon);
                user.setWatchList(movies);
                user.setId(usersHibernate.getId());
                tx.commit();
                return user;
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
    public void updateUser(User oldUser, User newUser)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                project.domainHibernate.User user = session.get(project.domainHibernate.User.class, oldUser.getId());
                user.setNume(newUser.getNume());
                user.setParola(newUser.getParola());
                user.setTelefonNumber(newUser.getNumarTelefon().toString());
                user.setEmail(newUser.getEmail());
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
    public void updateWatchList(User user)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                List<Movie> movies = new ArrayList<>();
                for(project.domain.Movie movie: user.getWatchList())
                {
                    movies.add(new Movie(movie.getId(), movie.getName(), movie.getGenre(), movie.getReview(), movie.getDate().toString(), movie.getInformatii(), movie.getImagePath()));
                }
                project.domainHibernate.User user1 = session.get(project.domainHibernate.User.class, user.getId());
                List<Movie> movie = user1.getWatchList();
                movies.addAll(movie);
                user1.setWatchList(movies);
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
    public void removeMovieFromWatchList(User user, project.domain.Movie movie)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                project.domainHibernate.User user1 = session.get(project.domainHibernate.User.class, user.getId());
                Movie movie1 = new Movie(movie.getId(), movie.getName(), movie.getGenre(), movie.getReview(), movie.getDate().toString(), movie.getInformatii(), movie.getImagePath());
                user1.getWatchList().remove(movie1);
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
    public boolean hasReview(User user, project.domain.Movie movie)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.User> comanda = session.createQuery("FROM User WHERE id=:id", project.domainHibernate.User.class);
                comanda.setParameter("id", user.getId());
                project.domainHibernate.User usersHibernate = comanda.getSingleResult();
                Movie movie1 = new Movie(movie.getId(), movie.getName(), movie.getGenre(), movie.getReview(), movie.getDate().toString(), movie.getInformatii(), movie.getImagePath());
                if(usersHibernate.getWatchList().contains(movie1))
                {
                    return true;
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
    public List<project.domain.Movie> findWatchList(User user)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                project.domainHibernate.User user1 = session.get(project.domainHibernate.User.class, user.getId());
                List<project.domain.Movie> movie = new ArrayList<>();
                for(Movie movie1: user1.getWatchList())
                {
                    project.domain.Movie movie2 = new project.domain.Movie(movie1.getName(), movie1.getGenre(), movie1.getReview(), LocalDate.parse(movie1.getDate()));
                    movie2.setId(movie1.getId());
                    movie2.setInformatii(movie1.getInformatii());
                    movie2.setImagePath(movie1.getImagePath());
                    movie.add(movie2);
                }
                tx.commit();
                return movie;
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
