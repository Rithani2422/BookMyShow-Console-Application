package BMSPROJECT;

import java.util.ArrayList;

public class Tickets {
    private String theatreName; // Name of the theatre where the movie is being shown
    private String movieName; // Name of the movie being watched
    private String screen; // The screen on which the movie is being shown
    private String location; // Location of the theatre
    private String showTimeStart; // Start time of the show
    private double price; // Price per seat for the movie
    private ArrayList<String> bookedTickets; // List of seats that have been booked
    private ArrayList<String> availableSeats; // List of available seats

    // Constructor to initialize ticket details
    public Tickets(String theatreName, String movieName, String screen, String location, String showTimeStart, double price, ArrayList<String> availableSeats) {
        this.theatreName = theatreName; // Set the theatre name
        this.movieName = movieName; // Set the movie name
        this.screen = screen; // Set the screen name
        this.location = location; // Set the location of the theatre
        this.showTimeStart = showTimeStart; // Set the show start time
        this.price = price; // Set the price per seat
        this.bookedTickets = new ArrayList<>(); // Initialize the list of booked tickets
        this.availableSeats = availableSeats; // Set the available seats
    }

    // Getters and Setters for accessing the ticket details

    public String getTheatreName() {
        return theatreName; // Return the name of the theatre
    }

    public String getMovieName() {
        return movieName; // Return the name of the movie
    }

    public String getScreen() {
        return screen; // Return the screen name
    }

    public String getLocation() {
        return location; // Return the location of the theatre
    }

    public String getShowTimeStart() {
        return showTimeStart; // Return the show start time
    }

    public double getPrice() {
        return price; // Return the price of the ticket
    }

    public ArrayList<String> getBookedTickets() {
        return bookedTickets; // Return the list of booked tickets (seats)
    }

    // Method to book a seat and add it to the booked tickets list
    public void bookSeat(String seat) {
        bookedTickets.add(seat); // Add the selected seat to the list of booked tickets
    }
}


