package project.repo;

import project.domain.Genre;
import project.domain.Movie;
import project.domain.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepositoryMovie implements Repository<Movie, Integer>{
    private Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CinemaApp", "postgres", "postgres");
    public DatabaseRepositoryMovie() throws SQLException {}
    @Override
    public void add(Movie itemToAdd)
    {
        String comanda = "INSERT INTO public.\"Movie\"(\"name\", \"review\", \"genre\", \"date\") VALUES (?,?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setString(1, itemToAdd.getName());
            statement.setDouble(2, itemToAdd.getReview());
            statement.setString(3, itemToAdd.getGenre().toString());
            statement.setDate(4, Date.valueOf(itemToAdd.getDate().toString()));
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }
    @Override
    public List<Movie> findAll()
    {
        String comanda = "SELECT * FROM public.\"Movie\"";
        List<Movie> movieList = new ArrayList<>();
        try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY); ResultSet resultSet = statement.executeQuery(comanda))
        {
            while(resultSet.next())
            {
                String name = resultSet.getString("name");
                double review = resultSet.getDouble("review");
                Genre genre = Genre.valueOf(resultSet.getString("genre"));
                LocalDate data = resultSet.getDate("date").toLocalDate();
                movieList.add(new Movie(name, genre, review, data));
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return movieList;
    }
    @Override
    public Integer findId(Movie itemToFind)
    {
        String comanda = "SELECT \"id\" FROM public.\"Movie\" WHERE \"name\"=? AND \"date\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setString(1, itemToFind.getName());
            statement.setDate(2, Date.valueOf(itemToFind.getDate().toString()));
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
