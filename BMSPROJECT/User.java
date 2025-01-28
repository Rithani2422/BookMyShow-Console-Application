package BMSPROJECT;
import java.util.ArrayList;
import java.time.LocalDate;
public class User {
    private String username;
    private String password;
    private String location;
    private LocalDate date;
    private ArrayList<Tickets> tickets;  // List of user's tickets

    // Constructor, getters, and setters

    public User(String username, String password, String location) {
        this.username = username;
        this.password = password;
        this.location = location;
        this.date = LocalDate.now();  // Default date to today's date
        this.tickets = new ArrayList<>();
    }



    public ArrayList<Tickets> getTickets() {
        return tickets;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
