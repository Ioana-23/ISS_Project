package project.domain;

import java.util.List;
import java.util.Objects;

public class User extends Entity<Integer>{
    private String email;
    private String parola;
    private String nume;
    private List<Integer> numarTelefon;

    public User(String email, String parola, String nume, List<Integer> numarTelefon) {
        this.email = email;
        this.parola = parola;
        this.nume = nume;
        this.numarTelefon = numarTelefon;
    }

    public String getEmail() {
        return email;
    }

    public String getParola() {
        return parola;
    }

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
