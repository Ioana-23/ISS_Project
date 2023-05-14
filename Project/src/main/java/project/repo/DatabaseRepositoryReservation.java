package project.repo;

import project.domain.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepositoryReservation implements Repository<Reservation, Pair<Integer, Integer>>{
    private Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CinemaApp", "postgres", "postgres");

    public DatabaseRepositoryReservation() throws SQLException {}
    @Override
    public void add(Reservation itemToAdd)
    {
        String comanda = "INSERT INTO public.\"Reservation\"(\"idMovieScreening\", \"status\", \"idUser\") VALUES (?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            Pair<Integer, Integer> id = findId(itemToAdd);
            statement.setInt(1, id.getValue2());
            statement.setString(2, itemToAdd.getStatus().toString());
            statement.setInt(3, id.getValue1());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }
    @Override
    public List<Reservation> findAll()
    {
        String comanda = "SELECT * FROM public.\"Reservation\"";
        List<Reservation> reservationList = new ArrayList<>();
        try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);ResultSet resultSet = statement.executeQuery(comanda))
        {
            while(resultSet.next())
            {
                int id = resultSet.getInt("id");
                int idMovie = resultSet.getInt("idMovieScreening");
                Status status = Status.valueOf(resultSet.getString("status"));
                int idUser = resultSet.getInt("idUser");
                MovieScreening movie = findMovieScreening(idMovie);
                movie.setId(idMovie);
                User user = findUser(idUser);
                user.setId(idUser);
                reservationList.add(new Reservation(movie, status, user, new ArrayList<>()));
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return reservationList;
    }
    @Override
    public Pair<Integer, Integer> findId(Reservation itemToFind) {
        Pair<Integer, Integer> id = new Pair<>(-1, -1);
        String comanda1 = "SELECT \"id\" FROM public.\"User\" WHERE \"email\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda1))
        {
            statement.setString(1, itemToFind.getUser().getEmail());
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    int id1 = resultSet.getInt("id");
                    id.setValue1(id1);
                }
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        String comanda2 = "SELECT \"id\" FROM public.\"MovieScreening\" WHERE \"id\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda2))
        {
            statement.setInt(1, itemToFind.getMovieScreening().getId());
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    int id2 = resultSet.getInt("id");
                    id.setValue2(id2);
                }
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return id;
    }
    public MovieScreening findMovieScreening(Integer id)
    {
        String comanda1 = "SELECT * FROM public.\"MovieScreening\" WHERE \"id\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda1))
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
                    Tip tip = Tip.valueOf(resultSet.getString("tip"));
                    Movie movie = findMovie(idMovie);
                    movie.setId(idMovie);
                    MovieHall movieHall = findMovieHall(idMovieHall);
                    movieHall.setId(idMovieHall);
                    return new MovieScreening(movie, date, time, movieHall, tip);
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
    public User findUser(Integer id)
    {
        String comanda1 = "SELECT * FROM public.\"User\" WHERE \"id\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda1))
        {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery())
            {
                resultSet.next();
                String email = resultSet.getString("email");
                String parola = resultSet.getString("parola");
                String nume = resultSet.getString("nume");
                String telefon = resultSet.getString("numarTelefon");
                String[] numere = telefon.split(", ");
                numere[0] = numere[0].split("\\[")[1];
                numere[numere.length-1] = numere[numere.length-1].split("\\]")[0];
                List<Integer> numarTelefon = new ArrayList<>();
                for(int i = 0; i < numere.length; i++)
                {
                    numarTelefon.add(Integer.parseInt(numere[i]));
                }
                return new User(email, parola, nume, numarTelefon);
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return null;
    }
    public MovieHall findMovieHall(Integer id)
    {
        String comanda1 = "SELECT * FROM public.\"MovieHall\" WHERE \"id\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda1))
        {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    int number = resultSet.getInt("number");
                    return new MovieHall(number, new ArrayList<>());
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
    public List<Reservation> findReservations(User user)
    {
        List<Reservation> reservationList = new ArrayList<>();
        String comanda = "SELECT * FROM public.\"Reservation\" WHERE \"idUser\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, user.getId());
            try(ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {
                    int id = resultSet.getInt("id");
                    int idMovieScreening = resultSet.getInt("idMovieScreening");
                    Status status = Status.valueOf(resultSet.getString("status"));
                    int idUser = resultSet.getInt("idUser");
                    List<Seat> seats = new ArrayList<>();
                    MovieScreening movieScreening = findMovieScreening(idMovieScreening);
                    movieScreening.setId(idMovieScreening);
                    String comanda1 = "SELECT * FROM public.\"ReservationSeats\" WHERE \"idReservation\"=?";
                    try(PreparedStatement statement1 = connection.prepareStatement(comanda1))
                    {
                        statement1.setInt(1, id);
                        try(ResultSet resultSet1 = statement1.executeQuery())
                        {
                            while(resultSet1.next())
                            {
                                int idSeat = resultSet1.getInt("idSeat");
                                String comanda2 = "SELECT * FROM public.\"Seat\" WHERE \"id\"=?";
                                try(PreparedStatement statement2 = connection.prepareStatement(comanda2))
                                {
                                    statement2.setInt(1, idSeat);
                                    try(ResultSet resultSet2 = statement2.executeQuery())
                                    {
                                        while (resultSet2.next())
                                        {
                                            int lineNumber = resultSet2.getInt("lineNumber");
                                            int seatNumber = resultSet2.getInt("seatNumber");
                                            Seat seat = new Seat(lineNumber, seatNumber);
                                            seat.setId(idSeat);
                                            seats.add(seat);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Reservation reservation = new Reservation(movieScreening, status, user, seats);
                    reservationList.add(reservation);
                }
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return reservationList;
    }
    public Integer findReservation(Reservation reservation)
    {
        String comanda = "SELECT * FROM public.\"Reservation\" WHERE \"idMovieScreening\"=? AND \"idUser\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, reservation.getMovieScreening().getId());
            statement.setInt(2, reservation.getUser().getId());
            try(ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
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
    public void delete(Reservation reservation)
    {
        String comanda1 = "DELETE FROM public.\"ReservationSeats\" WHERE \"idReservation\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda1))
        {
            statement.setInt(1, findReservation(reservation));
            statement.executeUpdate();
        }
        catch (SQLException e )
        {
            e.getCause();
            e.printStackTrace();
        }
        String comanda = "DELETE FROM public.\"Reservation\" WHERE \"idMovieScreening\"=? AND \"idUser\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, reservation.getMovieScreening().getId());
            statement.setInt(2, reservation.getUser().getId());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
    }
    public void update(Reservation reservation, MovieScreening movieScreening)
    {
        String comanda = "UPDATE public.\"Reservation\" SET \"idMovieScreening\"=? WHERE \"idMovieScreening\"=? AND \"idUser\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, movieScreening.getId());
            statement.setInt(2, reservation.getMovieScreening().getId());
            statement.setInt(3, reservation.getUser().getId());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
    }
}
