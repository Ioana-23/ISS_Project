package project.repo;

import project.domain.MovieHall;
import project.domain.Seat;

import java.sql.*;

public class DatabaseRepositoryMovieHallSeats{
    private Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CinemaApp", "postgres", "postgres");

    public DatabaseRepositoryMovieHallSeats() throws SQLException {}
    public void addMovieHallSeat(MovieHall movieHall, Seat seat)
    {
        String comanda = "INSERT INTO public.\"MovieHallSeats\"(\"idMovieHall\", \"idSeat\") VALUES (?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, movieHall.getId());
            statement.setInt(2, seat.getId());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
    }
    public boolean movieHallSeatsExists(MovieHall movieHall, Seat seat)
    {
        String comanda = "SELECT * FROM public.\"MovieHallSeats\" WHERE \"idMovieHall\"=? AND \"idSeat\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, movieHall.getId());
            statement.setInt(2, seat.getId());
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    return true;
                }
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return false;
    }
}
