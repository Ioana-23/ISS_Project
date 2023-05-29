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
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepositoryMovieReviewHibernate implements Repository<MovieReview, Integer> {
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
    public DatabaseRepositoryMovieReviewHibernate() throws SQLException {initialize();}
    @Override
    public void add(MovieReview itemToAdd)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<project.domainHibernate.Movie> movies = new ArrayList<>();
                for(Movie movie: itemToAdd.getUser().getWatchList())
                {
                    movies.add(new project.domainHibernate.Movie(movie.getId(), movie.getName(), movie.getGenre(), movie.getReview(), movie.getDate().toString(), movie.getInformatii(), movie.getImagePath()));
                }
                project.domainHibernate.Movie movie = new project.domainHibernate.Movie(itemToAdd.getMovie().getId(), itemToAdd.getMovie().getName(), itemToAdd.getMovie().getGenre(), itemToAdd.getMovie().getReview(), itemToAdd.getMovie().getDate().toString(), itemToAdd.getMovie().getInformatii(), itemToAdd.getMovie().getImagePath());
                project.domainHibernate.User user = new project.domainHibernate.User(itemToAdd.getUser().getId(), itemToAdd.getUser().getEmail(), itemToAdd.getUser().getParola(), itemToAdd.getUser().getNume(), itemToAdd.getUser().getNumarTelefon().toString(), movies);
                project.domainHibernate.MovieReview movieReview = new project.domainHibernate.MovieReview(0, movie, user, itemToAdd.getStar());
                session.save(movieReview);
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
    public List<MovieReview> findAll()
    {
        List<MovieReview> movieReviews = new ArrayList<>();
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.MovieReview> comanda = session.createQuery("FROM MovieReview", project.domainHibernate.MovieReview.class);
                List<project.domainHibernate.MovieReview> movieReviewsHibernate = comanda.getResultList();
                for(project.domainHibernate.MovieReview movieReview: movieReviewsHibernate)
                {
                    Movie movie = new Movie(movieReview.getMovie().getName(), movieReview.getMovie().getGenre(), movieReview.getMovie().getReview(), LocalDate.parse(movieReview.getMovie().getDate()));
                    movie.setId(movieReview.getMovie().getId());
                    movie.setInformatii(movieReview.getMovie().getInformatii());
                    movie.setImagePath(movieReview.getMovie().getImagePath());
                    String[] numere = movieReview.getUser().getTelefonNumber().split(", ");
                    numere[0] = numere[0].split("\\[")[1];
                    numere[numere.length-1] = numere[numere.length-1].split("]")[0];
                    List<Integer> numarTelefon = new ArrayList<>();
                    for (String s : numere)
                    {
                        numarTelefon.add(Integer.parseInt(s));
                    }
                    User user = new User(movieReview.getUser().getEmail(), movieReview.getUser().getParola(), movieReview.getUser().getNume(), numarTelefon);
                    user.setId(movieReview.getUser().getId());
                    MovieReview movieReview1 = new MovieReview(movie, user, movieReview.getStar());
                    movieReview1.setId(movieReview.getId());
                    movieReviews.add(movieReview1);
                }
                tx.commit();
                return movieReviews;
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
    public void updateMovieReview(MovieReview movieReview, double star)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                project.domainHibernate.MovieReview movieReview1 = session.get(project.domainHibernate.MovieReview.class, movieReview.getId());
                movieReview1.setStar(star);
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
    public MovieReview findReviewAfterIds(User user, Movie movie)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.MovieReview> comanda = session.createQuery("FROM MovieReview WHERE movie.id=:movie AND user.id=:user", project.domainHibernate.MovieReview.class);
                comanda.setParameter("movie", movie.getId());
                comanda.setParameter("user", user.getId());
                project.domainHibernate.MovieReview movieReviewsHibernate = comanda.getSingleResult();
                String[] numere = movieReviewsHibernate.getUser().getTelefonNumber().split(", ");
                numere[0] = numere[0].split("\\[")[1];
                numere[numere.length-1] = numere[numere.length-1].split("]")[0];
                List<Integer> numarTelefon = new ArrayList<>();
                for (String s : numere)
                {
                    numarTelefon.add(Integer.parseInt(s));
                }
                User user1 = new User(movieReviewsHibernate.getUser().getEmail(), movieReviewsHibernate.getUser().getParola(), movieReviewsHibernate.getUser().getNume(), numarTelefon);
                user1.setId(movieReviewsHibernate.getUser().getId());
                Movie movie1 = new Movie(movieReviewsHibernate.getMovie().getName(), movieReviewsHibernate.getMovie().getGenre(), movieReviewsHibernate.getMovie().getReview(), LocalDate.parse(movieReviewsHibernate.getMovie().getDate()));
                movie1.setId(movieReviewsHibernate.getMovie().getId());
                movie1.setInformatii(movieReviewsHibernate.getMovie().getInformatii());
                movie1.setImagePath(movieReviewsHibernate.getMovie().getImagePath());
                MovieReview movieReview = new MovieReview(movie1, user1, movieReviewsHibernate.getStar());
                movieReview.setId(movieReviewsHibernate.getId());
                tx.commit();
                return movieReview;
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
    public Integer findId(MovieReview itemToFind)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.MovieReview> comanda = session.createQuery("FROM MovieReview WHERE movie.id=:idMovie AND user.id=:idUser", project.domainHibernate.MovieReview.class);
                comanda.setParameter("idMovie", itemToFind.getMovie().getId());
                comanda.setParameter("idUser", itemToFind.getUser().getId());
                project.domainHibernate.MovieReview movieReviewsHibernate = comanda.getSingleResult();
                tx.commit();
                return movieReviewsHibernate.getId();
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
