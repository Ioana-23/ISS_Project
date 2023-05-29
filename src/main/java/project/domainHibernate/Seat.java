package project.domainHibernate;

import java.util.Objects;

public class Seat{
    private int id;
    private int lineNumber;
    private int seatnumber;

    public Seat() {
    }

    public Seat(int id, int lineNumber, int seatnumber) {
        this.id = id;
        this.lineNumber = lineNumber;
        this.seatnumber = seatnumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setSeatnumber(int seatnumber) {
        this.seatnumber = seatnumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }
    public int getSeatnumber() {
        return seatnumber;
    }
    }
