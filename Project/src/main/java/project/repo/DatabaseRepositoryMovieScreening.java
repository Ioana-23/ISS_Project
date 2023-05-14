package project.repo;

import project.domain.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepositoryMovieScreening implements Repository<MovieScreening, Integer> {
    private Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CinemaApp", "postgres", "postgres");
    public DatabaseRepositoryMovieScreening() throws SQLException {}
    @Override
    public void add(MovieScreening itemToAdd)
    {
        String comanda = "INSERT INTO public.\"MovieScreening\"(\"idMovie\", \"date\", \"time\", \"idMovieHall\", \"tip\") VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, itemToAdd.getMovie().getId());
            statement.setDate(2, Date.valueOf(itemToAdd.getDate()));
            statement.setTime(3, Time.valueOf(itemToAdd.getTime()));
            statement.setInt(4, itemToAdd.getMovieHall().getId());
            statement.setString(5, itemToAdd.getTipString());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
    }
    public void deleteAll()
    {
        try(Statement statement = connection.createStatement())
        {
            statement.execute("DELETE FROM public.\"ReservationSeats\"");
            statement.execute("DELETE FROM public.\"Reservation\"");
            statement.execute("DELETE FROM public.\"MovieScreening\"");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public List<MovieScreening> findAll()
    {
        String comanda = "SELECT * FROM public.\"MovieScreening\"";
        List<MovieScreening> movieScreeningList = new ArrayList<>();
        try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);ResultSet resultSet = statement.executeQuery(comanda))
        {
            while(resultSet.next())
            {
                int idMovie = resultSet.getInt("idMovie");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                LocalTime time = resultSet.getTime("time").toLocalTime();
                int idMovieHall = resultSet.getInt("idMovieHall");
                int id = resultSet.getInt("id");
                Tip tip = Tip.valueOf(resultSet.getString("tip"));

                Movie movie = findMovie(idMovie);
                movie.setId(idMovie);
                MovieHall movieHall = findMovieHall(idMovieHall);
                movieHall.setId(idMovieHall);
                MovieScreening movieScreening = new MovieScreening(movie, date, time, movieHall, tip);
                movieScreening.setId(id);
                movieScreeningList.add(movieScreening);
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return movieScreeningList;
    }
    public MovieHall findMovieHall(Integer id)
    {
        String comanda = "SELECT * FROM public.\"MovieHall\" WHERE \"id\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    int number = resultSet.getInt("number");
                    List<Seat> seats = new ArrayList<>();
                    String comanda1 = "SELECT * FROM public.\"MovieHallSeats\" WHERE \"idMovieHall\"=?";
                    try(PreparedStatement statement1 = connection.prepareStatement(comanda1))
                    {
                        statement1.setInt(1, id);
                        try(ResultSet resultSet1 = statement1.executeQuery())
                        {
                            while (resultSet1.next())
                            {
                                int idSeat = resultSet1.getInt("idSeat");
                                String comanda2 = "SELECT * FROM public.\"Seat\" WHERE \"id\"=?";
                                try(PreparedStatement statement2 = connection.prepareStatement(comanda2))
                                {
                                    statement2.setInt(1, idSeat);
                                    try(ResultSet resultSet2 = statement2.executeQuery())
                                    {
                                        while(resultSet2.next())
                                        {
                                            int lineNumber = resultSet2.getInt("lineNumber");
                                            int seatNumber = resultSet2.getInt("seatNumber");
                                            Seat seat = new Seat(lineNumber, seatNumber);
                                            seat.setId(idSeat);
                                            seats.add(seat);
                                        }
                                    }
                                }
                                catch (SQLException e)
                                {
                                    e.getCause();
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    catch (SQLException e)
                    {
                        e.getCause();
                        e.printStackTrace();
                    }
                    return new MovieHall(number, seats);
                }
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return null;
    }
    public List<Seat> getOccupiedSeats(MovieScreening movieScreening)
    {
        List<Seat> occupiedSeats = new ArrayList<>();
        String comanda = "SELECT s.\"id\", s.\"lineNumber\", s.\"seatNumber\" FROM public.\"Reservation\" r INNER JOIN public.\"ReservationSeats\" rs ON r.\"id\"=rs.\"idReservation\" INNER JOIN public.\"Seat\" s ON rs.\"idSeat\"=s.\"id\" WHERE r.\"idMovieScreening\"=" + movieScreening.getId();
        try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);ResultSet resultSet = statement.executeQuery(comanda))
        {
            while(resultSet.next())
            {
                int id = resultSet.getInt("id");
                int lineNumber = resultSet.getInt("lineNumber");
                int seatNumber = resultSet.getInt("seatNumber");
                Seat seat = new Seat(lineNumber, seatNumber);
                seat.setId(id);
                occupiedSeats.add(seat);
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return occupiedSeats;
    }
    public Movie findMovie(Integer id)
    {
        String comanda = "SELECT * FROM public.\"Movie\" WHERE \"id\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    String name = resultSet.getString("name");
                    double review = resultSet.getDouble("review");
                    Genre genre = Genre.valueOf(resultSet.getString("genre"));
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    return new Movie(name, genre, review, date);
                }
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Integer findId(MovieScreening itemToFind)
    {
        String comanda = "SELECT * FROM public.\"MovieScreening\" WHERE \"idMovie\"=? AND \"date\"=? AND \"time\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, itemToFind.getMovie().getId());
            statement.setDate(2, Date.valueOf(itemToFind.getDate()));
            statement.setTime(3, Time.valueOf(itemToFind.getTime()));
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    int idMovie = resultSet.getInt("idMovie");
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    LocalTime time = resultSet.getTime("time").toLocalTime();
                    int idMovieHall = resultSet.getInt("idMovieHall");
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
    public List<MovieScreening> findFromDate(LocalDate date)
    {
        String comanda = "SELECT * FROM public.\"MovieScreening\" WHERE \"date\"=?";
        List<MovieScreening> movieScreeningList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setDate(1, Date.valueOf(date));
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    int idMovie = resultSet.getInt("idMovie");
                    LocalDate date1 = resultSet.getDate("date").toLocalDate();
                    LocalTime time = resultSet.getTime("time").toLocalTime();
                    int idMovieHall = resultSet.getInt("idMovieHall");
                    Tip tip = Tip.valueOf(resultSet.getString("tip"));
                    int id = resultSet.getInt("id");
                    Movie movie = findMovie(idMovie);
                    movie.setId(idMovie);
                    MovieHall movieHall = findMovieHall(idMovieHall);
                    movieHall.setId(idMovieHall);
                    MovieScreening movieScreening = new MovieScreening(movie, date1, time, movieHall, tip);
                    movieScreening.setId(id);
                    movieScreeningList.add(movieScreening);
                }
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return movieScreeningList;
    }
    public MovieScreening returnMovieScreening(Integer id)
    {
        String comanda = "SELECT * FROM public.\"MovieScreening\" WHERE \"id\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    int idMovie = resultSet.getInt("idMovie");
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    LocalTime time = resultSet.getTime("time").toLocalTime();
                    int idMovieHall = resultSet.getInt("idMovieHall");
                    int id1 = resultSet.getInt("id");
                    Tip tip = Tip.valueOf(resultSet.getString("tip"));

                    Movie movie = findMovie(idMovie);
                    movie.setId(idMovie);
                    MovieHall movieHall = findMovieHall(idMovieHall);
                    movieHall.setId(idMovieHall);
                    MovieScreening movieScreening = new MovieScreening(movie, date, time, movieHall, tip);
                    movieScreening.setId(id1);
                    return  movieScreening;
                }
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return null;
    }
}
