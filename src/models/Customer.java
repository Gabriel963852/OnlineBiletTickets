package models;

public class Customer {
    private int id;
    private String Fname;
    private String Lname;
    private String email;
    private String password; // За логин
    private String role;

    public Customer(int id, String Fname, String Lname, String email, String password,String role) {
        this.id = id;
        this.Fname = Fname;
        this.Lname = Lname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getFname() { return Fname; }
    public void setFname(String Fname) { this.Fname = Fname; }

    public String getLname() { return Lname; }
    public void setLname(String Lname) { this.Lname = Lname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

}
