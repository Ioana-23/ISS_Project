package project.repo;

import project.domain.Reservation;
import project.domain.Seat;

import java.sql.*;

public class DatabaseRepositoryReservationSeats {
    private Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CinemaApp", "postgres", "postgres");

    public DatabaseRepositoryReservationSeats() throws SQLException {}
    public Integer findId(Reservation reservation)
    {
        String comanda = "SELECT \"id\" FROM public.\"Reservation\" WHERE \"idMovieScreening\"=? AND \"idUser\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, reservation.getMovieScreening().getId());
            statement.setInt(2, reservation.getUser().getId());
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
    public void addReservationSeat(Reservation reservation, Seat seat)
    {
        String comanda = "INSERT INTO public.\"ReservationSeats\"(\"idReservation\", \"idSeat\") VALUES (?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, findId(reservation));
            statement.setInt(2, seat.getId());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
    }
    public boolean reservationSeatExists(Reservation reservation, Seat seat)
    {
        String comanda = "SELECT * FROM public.\"ReservationSeats\" WHERE \"idReservation\"=? AND \"idSeat\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, findId(reservation));
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
