package project.domain;
import java.util.List;
import java.util.Objects;
public class User extends Entity<Integer>{
    private String email;
    private String parola;
    private String nume;
    private List<Integer> numarTelefon;
    List<Movie> watchList;
    public User() {
    }
    public User(String email, String parola, String nume, List<Integer> numarTelefon) {
        this.email = email;
        this.parola = parola;
        this.nume = nume;
        this.numarTelefon = numarTelefon;
    }

    public void setWatchList(List<Movie> watchList) {this.watchList = watchList;}
    public List<Movie> getWatchList() {return watchList;}
    public void setEmail(String email){this.email = email;}
    public String getEmail() {
        return email;
    }
    public void setParola(String parola){this.parola = parola;}
    public String getParola() {
        return parola;
    }
    public void setNume(String nume){this.nume = nume;}
    public String getNume() {
        return nume;
    }
    public List<Integer> getNumarTelefon() {
        return numarTelefon;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
