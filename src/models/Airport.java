package models;

public class Airport {
    private String code;
    private String name;
    private String country;
    private String city;

    public Airport(String code, String name, String country, String city) {
        this.code = code;
        this.name = name;
        this.country = country;
        this.city = city;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getCity() { return city; }
}

