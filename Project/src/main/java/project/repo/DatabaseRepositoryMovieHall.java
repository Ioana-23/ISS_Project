package project.repo;

import project.domain.MovieHall;
import project.domain.Pair;
import project.domain.Seat;
import project.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepositoryMovieHall implements Repository<MovieHall, Integer> {
    private Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CinemaApp", "postgres", "postgres");

    public DatabaseRepositoryMovieHall() throws SQLException {}
    @Override
    public void add(MovieHall itemToAdd)
    {
        String comanda = "INSERT INTO public.\"MovieHall\"(\"number\") VALUES (?)";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, itemToAdd.getNumber());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }
    @Override
    public List<MovieHall> findAll()
    {
        String comanda = "SELECT * FROM public.\"MovieHall\"";
        List<MovieHall> movieHallsList = new ArrayList<>();
        try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);ResultSet resultSet = statement.executeQuery(comanda))
        {
            while(resultSet.next())
            {
               int number = resultSet.getInt("number");
               int id = resultSet.getInt("id");
               List<Seat> movieHallConfiguration = new ArrayList<>();
               String comanda1 = "SELECT \"idSeat\" FROM public.\"MovieHallSeats\" WHERE \"idMovieHall\"=" + id;
               try(Statement statement1 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);ResultSet resultSet1 = statement1.executeQuery(comanda1))
               {
                   while(resultSet1.next())
                   {
                       int idSeat = resultSet1.getInt("idSeat");
                       Seat seat = findSeat(idSeat);
                       seat.setId(idSeat);
                       movieHallConfiguration.add(seat);
                   }
               }
               catch (SQLException e)
               {
                   e.getCause();
                   e.printStackTrace();
               }
               movieHallsList.add(new MovieHall(number, movieHallConfiguration));
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return movieHallsList;
    }
    @Override
    public Integer findId(MovieHall itemToFind)
    {
        String comanda = "SELECT \"id\" FROM public.\"MovieHall\" WHERE \"number\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, itemToFind.getNumber());
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    int id = resultSet.getInt("id");
                    return id;
                }
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return -1;
    }
    public Integer findId(Seat itemToFind)
    {
        String comanda = "SELECT \"id\" FROM public.\"Seat\" WHERE \"lineNumber\"=? AND \"seatNumber\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, itemToFind.getLineNumber());
            statement.setInt(2, itemToFind.getSeatnumber());
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    int id = resultSet.getInt("id");
                    return id;
                }
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return -1;
    }
    public Seat findSeat(Integer idSeat)
    {
        String comanda = "SELECT \"lineNumber\", \"seatNumber\" FROM public.\"Seat\" WHERE \"id\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, idSeat);
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    int lineNumber = resultSet.getInt("lineNumber");
                    int seatNumber = resultSet.getInt("seatNumber");
                    return new Seat(lineNumber, seatNumber);
                }
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return  null;
    }
}
