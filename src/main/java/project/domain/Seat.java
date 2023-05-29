package project.domain;

import java.util.Objects;

public class Seat extends Entity<Integer>{
    private final int lineNumber;
    private final int seatnumber;
    public Seat(int lineNumber, int seatnumber) {
        this.lineNumber = lineNumber;
        this.seatnumber = seatnumber;
    }
    public int getLineNumber() {
        return lineNumber;
    }
    public int getSeatnumber() {
        return seatnumber;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return lineNumber == seat.lineNumber && seatnumber == seat.seatnumber;
    }
    @Override
    public int hashCode() {
        return Objects.hash(lineNumber, seatnumber);
    }
    @Override
    public String toString() {
        return "(" + lineNumber + ", " + seatnumber + ")";
    }
}
