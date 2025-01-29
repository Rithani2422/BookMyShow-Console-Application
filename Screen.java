package BMSPROJECT;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Screen {
    private String name; // Name of the screen
    private int numberOfSeats; // Total number of seats available in the screen
    private List<Show> shows;  // List to store shows available on this screen
    private HashMap<Character, ArrayList<String>> seatGrid;  // Stores the seat arrangement in the grid (row -> seats)

    // Constructor to initialize the screen with name, number of seats, and seat grid
    public Screen(String name, int numberOfSeats, HashMap<Character, ArrayList<String>> seatGrid) {
        this.name = name;  // Assign the screen name
        this.numberOfSeats = numberOfSeats;  // Assign the number of seats in the screen
        this.shows = new ArrayList<>();  // Initialize the shows list (empty initially)
        this.seatGrid = seatGrid;  // Initialize the seat grid with the provided data
    }

    // Getter for the screen's name
    public String getName() {
        return name;
    }

    // Getter for the seat grid (which stores the seat arrangement in rows and seats)
    public HashMap<Character, ArrayList<String>> getSeatGrid() {
        return seatGrid;
    }

    // Method to add a show to this screen's list of shows
    public void addShow(Show show) {
        if (show != null) {
            this.shows.add(show);  // Add the provided show to the list of shows for this screen
        }
    }

    // Getter for the list of shows available on this screen
    public List<Show> getShows() {
        return this.shows;
    }

    // Getter for the number of seats in this screen
    public int getNumberOfSeats() {
        return numberOfSeats;
    }
}




