package models;

import java.util.Date;

public class Flights {
    private String Fnumber;
    private String airline_operator;
    private String dep_airport;
    private String arr_airport;
    private Date flight_date;
    private String flight_time;
    private int flight_duration;
    private String airplane;

    public Flights(String Fnumber, String airline_operator, String dep_airport, String arr_airport, Date flight_date, String flight_time, int flight_duration, String airplane) {
        this.Fnumber = Fnumber;
        this.airline_operator = airline_operator;
        this.dep_airport = dep_airport;
        this.arr_airport = arr_airport;
        this.flight_date = flight_date;
        this.flight_time = flight_time;
        this.flight_duration = flight_duration;
        this.airplane = airplane;
    }

    public String getFnumber() { return Fnumber; }
    public String getAirline_operator() { return airline_operator; }
    public String getDep_airport() { return dep_airport; }
    public String getArr_airport() { return arr_airport; }
    public Date getFlightDate() {return flight_date;}
    public String getFlight_time() { return flight_time; }
    public int getFlight_duration() { return flight_duration; }
    public String getAirplane() { return airplane; }
}

