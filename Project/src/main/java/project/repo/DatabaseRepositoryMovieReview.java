package project.repo;

import project.domain.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepositoryMovieReview implements Repository<MovieReview, Pair<Integer, Integer>> {
    private Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CinemaApp", "postgres", "postgres");

    public DatabaseRepositoryMovieReview() throws SQLException {}
    @Override
    public void add(MovieReview itemToAdd)
    {
        String comanda = "INSERT INTO public.\"MovieReview\"(\"idUser\", \"idMovie\", \"star\") VALUES(?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, itemToAdd.getUser().getId());
            statement.setInt(2, itemToAdd.getMovie().getId());
            statement.setDouble(3, itemToAdd.getStar());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
    }
    @Override
    public List<MovieReview> findAll()
    {
        String comanda = "SELECT * FROM public.\"MovieReview\"";
        List<MovieReview> movieReviewList = new ArrayList<>();
        try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY); ResultSet resultSet = statement.executeQuery(comanda))
        {
            while(resultSet.next())
            {
                int idUser = resultSet.getInt("idUser");
                int idMovie = resultSet.getInt("idMovie");
                double star = resultSet.getDouble("star");
                Movie movie = findMovie(idMovie);
                movie.setId(idMovie);
                User user = findUser(idUser);
                user.setId(idUser);
                movieReviewList.add(new MovieReview(movie, user, star));
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return movieReviewList;
    }
    @Override
    public Pair<Integer, Integer> findId(MovieReview itemToFind) {
        String comanda = "SELECT * FROM public.\"MovieReview\" WHERE \"idUser\"=? AND \"idMovie\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, itemToFind.getUser().getId());
            statement.setInt(2, itemToFind.getMovie().getId());
            try(ResultSet resultSet = statement.executeQuery())
            {
                while(resultSet.next())
                {
                    int id1 = resultSet.getInt("idUser");
                    int id2 = resultSet.getInt("idMovie");
                    return new Pair<>(id1, id2);
                }
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return new Pair<>(-1, -1);
    }
    public Movie findMovie(Integer id)
    {
        String comanda1 = "SELECT * FROM public.\"Movie\" WHERE \"id\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda1))
        {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery())
            {
                resultSet.next();
                String name = resultSet.getString("name");
                double review = resultSet.getDouble("review");
                Genre genre = Genre.valueOf(resultSet.getString("genre"));
                LocalDate date = resultSet.getDate("date").toLocalDate();
                return new Movie(name, genre, review, date);
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
    public void update(MovieReview movieReview, double star)
    {
        String comanda = "UPDATE public.\"MovieReview\" SET \"star\"=? WHERE \"idUser\"=? AND \"idMovie\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setDouble(1, star);
            statement.setInt(2, movieReview.getUser().getId());
            statement.setInt(3, movieReview.getMovie().getId());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
    }
}
