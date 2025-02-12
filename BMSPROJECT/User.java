package BMSPROJECT;
import java.util.ArrayList;
import java.time.LocalDate;

public class User {
    private String username; // Username of the user
    private String password; // Password for the user's account
    private String location; // Location of the user
    private LocalDate date; // The date the user registered (default is today's date)
    private ArrayList<Tickets> tickets;  // List to store the user's tickets

    // Constructor to initialize a new User object
    public User(String username, String password, String location) {
        this.username = username; // Set the username
        this.password = password; // Set the password
        this.location = location; // Set the location
        this.date = LocalDate.now();  // Set the registration date to today's date
        this.tickets = new ArrayList<>(); // Initialize an empty list for storing user's tickets
    }

    // Getters and setters

    public ArrayList<Tickets> getTickets() {
        return tickets; // Return the list of tickets booked by the user
    }

    public String getLocation() {
        return location; // Return the location of the user
    }

    public void setLocation(String location) {
        this.location = location; // Set a new location for the user
    }

    public LocalDate getDate() {
        return date; // Return the date when the user registered
    }

    public void setDate(LocalDate date) {
        this.date = date; // Set a new registration date for the user
    }

    public String getPassword() {
        return password; // Return the user's password
    }

    public String getUsername() {
        return username; // Return the username of the user
    }
}
