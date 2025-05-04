package models;

public class Bookings {
    private String code;
    private String agency;
    private String airline_code;
    private String flight_number;
    private int customer_id;
    private String booking_date;
    private String flight_date;
    private double price;
    private String status;

    public Bookings(String code, String agency, String airline_code, String flight_number, int customer_id, String booking_date,String flight_date, double price, String status) {
        this.code = code;
        this.agency = agency;
        this.airline_code = airline_code;
        this.flight_number = flight_number;
        this.customer_id = customer_id;
        this.booking_date = booking_date;
        this.flight_date = flight_date;
        this.price = price;
        this.status = status;
    }

    public String getCode() { return code; }
    public String getAgencyName() { return agency; }
    public String getAirlineCode() { return airline_code; }
    public String getFlightNumber() { return flight_number; }
    public int getCustomerId() { return customer_id; }
    public String getBookingDate() {return booking_date;}
    public String getFlightDate(){return flight_date;}
    public double getPrice() { return price; }
    public String getStatus() { return status; }
}

