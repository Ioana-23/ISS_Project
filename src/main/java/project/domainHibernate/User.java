package project.domainHibernate;

import java.util.List;
import java.util.Objects;

public class User{
    private int id;
    private String email;
    private String parola;
    private String nume;
    private String telefonNumber;
    private List<Movie> watchList;
    public User() {}
    public User(int id, String email, String parola, String nume, String telefonNumber, List<Movie> watchList) {
        this.id = id;
        this.email = email;
        this.parola = parola;
        this.nume = nume;
        this.telefonNumber = telefonNumber;
        this.watchList = watchList;
    }
    public String getTelefonNumber() {
        return telefonNumber;
    }
    public void setWatchList(List<Movie> wathcList) {this.watchList = wathcList;}
    public List<Movie> getWatchList() {return watchList;}
    public void setTelefonNumber(String telefonNumber) {
        this.telefonNumber = telefonNumber;
    }
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        project.domainHibernate.User user = (project.domainHibernate.User) o;
        return Objects.equals(email, user.email);
    }
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
