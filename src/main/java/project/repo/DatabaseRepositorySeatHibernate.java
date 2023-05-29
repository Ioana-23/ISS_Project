package project.repo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import project.domain.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepositorySeatHibernate implements Repository<Seat, Integer>{
    public DatabaseRepositorySeatHibernate() throws SQLException {initialize();}
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
    public void add(Seat itemToAdd)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                project.domainHibernate.Seat seat = new project.domainHibernate.Seat(0,itemToAdd.getLineNumber(), itemToAdd.getSeatnumber());
                session.save(seat);
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
    public List<Seat> findAll()
    {
        List<Seat> seats = new ArrayList<>();
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.Seat> comanda = session.createQuery("FROM Seat", project.domainHibernate.Seat.class);
                List<project.domainHibernate.Seat> seatsHibernate = comanda.getResultList();
                for(project.domainHibernate.Seat seat: seatsHibernate)
                {
                    Seat seat1 = new Seat(seat.getLineNumber(), seat.getSeatnumber());
                    seat1.setId(seat.getId());
                    seats.add(seat1);
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
        return null;
    }
    @Override
    public Integer findId(Seat itemToFind)
    {
        try(Session session = sessionFactory.openSession())
        {
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                Query<project.domainHibernate.Seat> comanda = session.createQuery("FROM Seat WHERE lineNumber=:lineNumber AND seatnumber=:seatNumber", project.domainHibernate.Seat.class);
                comanda.setParameter("lineNumber", itemToFind.getLineNumber());
                comanda.setParameter("seatNumber", itemToFind.getSeatnumber());
                project.domainHibernate.Seat seatHibernate = comanda.getSingleResult();
                tx.commit();
                return seatHibernate.getId();
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
