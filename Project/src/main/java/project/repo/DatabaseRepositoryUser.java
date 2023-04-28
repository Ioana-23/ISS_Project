package project.repo;

import project.domain.User;

import java.sql.*;
import java.util.*;

public class DatabaseRepositoryUser implements Repository<User, Integer>{
    private Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CinemaApp", "postgres", "postgres");
    public DatabaseRepositoryUser() throws SQLException {}
    @Override
    public void add(User itemToAdd)
    {
        String comanda = "INSERT INTO public.\"User\"(\"email\", \"parola\", \"nume\", \"numarTelefon\") VALUES (?,?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setString(1, itemToAdd.getEmail());
            statement.setString(2, itemToAdd.getParola());
            statement.setString(3, itemToAdd.getNume());
            statement.setString(4, itemToAdd.getNumarTelefon().toString());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }
    @Override
    public List<User> findAll()
    {
        String comanda = "SELECT * FROM public.\"User\"";
        List<User> usersList = new ArrayList<>();
        try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);ResultSet resultSet = statement.executeQuery(comanda))
        {
            while(resultSet.next())
            {
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
                usersList.add(new User(email, parola, nume, numarTelefon));
            }
        }
        catch (SQLException e)
        {
            e.getCause();
            e.printStackTrace();
        }
        return usersList;
    }
    @Override
    public Integer findId(User itemToFind)
    {
        String comanda = "SELECT \"id\" FROM public.\"User\" WHERE \"email\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setString(1, itemToFind.getEmail());
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
    public User returnUser(Integer id)
    {
        String comanda = "SELECT * FROM public.\"User\" WHERE \"id\"=?";
        try(PreparedStatement statement = connection.prepareStatement(comanda))
        {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {
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
                    User user = new User(email, parola, nume, numarTelefon);
                    user.setId(id);
                    return user;
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
