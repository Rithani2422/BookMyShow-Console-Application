package BMSPROJECT;

import java.util.ArrayList;

public class Tickets {
    private String theatreName;
    private String movieName;
    private String screen;
    private String location;
    private String showTimeStart;
    private double price;
    private ArrayList<String> bookedTickets;
    private ArrayList<String> availableSeats;

    // Constructor
    public Tickets(String theatreName, String movieName, String screen, String location, String showTimeStart, double price,ArrayList<String> availableSeats) {
        this.theatreName = theatreName;
        this.movieName = movieName;
        this.screen = screen;
        this.location = location;
        this.showTimeStart = showTimeStart;
        this.price = price;
        this.bookedTickets = new ArrayList<>();
        this.availableSeats = availableSeats;

    }


    // Getters and Setters
    public String getTheatreName() {
        return theatreName;
    }


    public String getMovieName() {
        return movieName;
    }


    public String getScreen() {
        return screen;
    }


    public String getLocation() {
        return location;
    }


    public String getShowTimeStart() {
        return showTimeStart;
    }


    public double getPrice() {
        return price;
    }


    public ArrayList<String> getBookedTickets() {
        return bookedTickets;
    }
    public void bookSeat(String seat) {
        bookedTickets.add(seat);  // Ensure this adds the seat to the bookedTickets
    }

}

