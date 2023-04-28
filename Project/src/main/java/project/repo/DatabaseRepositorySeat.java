package project.repo;

import project.domain.Genre;
import project.domain.Movie;
import project.domain.Seat;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepositorySeat implements Repository<Seat, Integer>{
    private Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CinemaApp", "postgres", "postgres");

    public DatabaseRepositorySeat() throws SQLException {}
    @Override
    public void add(Seat itemToAdd)
    {
        String comanda = "INSERT INTO public.\"Seat\"(\"lineNumber\", \"seatNumber\") VALUES (?,?)";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, itemToAdd.getLineNumber());
            statement.setInt(2, itemToAdd.getSeatnumber());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }
    @Override
    public List<Seat> findAll()
    {
        String comanda = "SELECT * FROM public.\"Seat\"";
        List<Seat> seatList = new ArrayList<>();
        try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY); ResultSet resultSet = statement.executeQuery(comanda))
        {
            while(resultSet.next())
            {
                int linenumber = resultSet.getInt("lineNumber");
                int seatNumber = resultSet.getInt("seatnumber");
                seatList.add(new Seat(linenumber, seatNumber));
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return seatList;
    }
    @Override
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
}
