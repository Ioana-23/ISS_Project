package project.domain;

import java.time.LocalDate;

public class CreditProperties {
    private final String numar;
    private final LocalDate dateExpirare;
    private final int cvv;

    public CreditProperties(String numar, LocalDate dateExpirare, int cvv) {
        this.numar = numar;
        this.dateExpirare = dateExpirare;
        this.cvv = cvv;
    }

    public String getNumar() {
        return numar;
    }

    public LocalDate getDateExpirare() {
        return dateExpirare;
    }

    public int getCvv() {
        return cvv;
    }
}
