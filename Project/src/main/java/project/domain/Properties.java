package project.domain;

public class Properties {
    private ReservationProperties reservationProperties;
    private boolean taxaOchelari;
    private int nrLocuri;

    public Properties(ReservationProperties reservationProperties, boolean taxaOchelari, int nrLocuri) {
        this.reservationProperties = reservationProperties;
        this.taxaOchelari = taxaOchelari;
        this.nrLocuri = nrLocuri;
    }
}
