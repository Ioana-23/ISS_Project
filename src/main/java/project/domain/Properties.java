package project.domain;

public class Properties {
    private final int nrLocuri;
    public Properties(ReservationProperties reservationProperties, boolean taxaOchelari, int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }
    public int getNrLocuri() {
        return nrLocuri;
    }
}
