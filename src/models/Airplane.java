package models;

public class Airplane {
    private String code;
    private String type;
    private int seats;
    private int yearA;

    public Airplane(String code, String type, int seats, int yearA) {
        this.code = code;
        this.type = type;
        this.seats = seats;
        this.yearA = yearA;
    }

    public String getCode() { return code; }
    public String getType() { return type; }
    public int getSeats() { return seats; }
    public int getYear() { return yearA; }
}

