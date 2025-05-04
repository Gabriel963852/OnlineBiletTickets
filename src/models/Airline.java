package models;

public class Airline {
    private String code;
    private String name;
    private String country;

    public Airline(String code, String name, String country)
    {
        this.code = code;
        this.name = name;
        this.country = country;
    }

    public String getCode() {return code;}
    public String getName() {return name;}
    public String getCountry() {return country;}

    public void setName(String name){this.name=name;}
    public void setCountry(String country){this.country=country;}


}
