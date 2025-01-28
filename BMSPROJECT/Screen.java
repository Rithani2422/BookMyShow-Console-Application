package BMSPROJECT;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Screen {
    private String name;
    private int numberOfSeats;
    private List<Show> shows;  // List to store shows for this screen
    private HashMap<Character, ArrayList<String>> seatGrid;  // To store seat grid pattern

    // Constructor
    public Screen(String name, int numberOfSeats, HashMap<Character, ArrayList<String>> seatGrid) {
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.shows = new ArrayList<>();  // Initialize the list of shows
        this.seatGrid = seatGrid;  // Initialize seat grid
    }

    // Getter for name
    public String getName() {
        return name;
    }
    // Getter for seat grid
    public HashMap<Character, ArrayList<String>> getSeatGrid() {
        return seatGrid;
    }

    // Method to add a show to the screen
    public void addShow(Show show) {
        if (show != null) {
            this.shows.add(show);  // Add the show to the list
        }
    }

    // Method to get the list of shows for the screen
    public List<Show> getShows() {
        return this.shows;
    }
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    // Setter
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}




