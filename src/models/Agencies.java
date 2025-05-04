package models;

public class Agencies {
    private String name;
    private String country;
    private String city;
    private String phone;

    public Agencies(String name,String country,String city,String phone)
    {
        this.name=name;
        this.country=country;
        this.city=city;
        this.phone=phone;
    }

    public String getName(){return name;}
    public String getCountry(){return country;}
    public String getCity(){return city;}
    public String getPhone(){return phone;}
}
