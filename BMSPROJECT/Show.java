package BMSPROJECT;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Show {
    private LocalTime startTime; // Start time of the show
    private LocalTime endTime; // End time of the show
    private double price; // Price of the ticket
    private LocalDate showDate; // Date of the show
    private String movieName; // Name of the movie
    private Screen screen; // Screen associated with the show
    private HashMap<Character, ArrayList<String>> seatArrangement; // Seat arrangement map

    // Constructor with validation checks for parameters
    public Show(LocalTime startTime, LocalTime endTime, LocalDate showDate, String movieName, Screen screen, double price, HashMap<Character, ArrayList<String>> seatArrangement) {
        // Set start time; default to 00:00 if null
        this.startTime = startTime;
        // Set end time; default to 00:00 if null
        this.endTime = endTime;
        // Set show date; default to the current date if null
        this.showDate = showDate;
        // Set movie name; default to "Untitled" if null or empty
        this.movieName = movieName;
        // Set screen; default to a "Default Screen" if null
        this.screen = screen;// Default screen if null
        // Set price; default to 100 if price is not positive
        this.price = price;
        // Set seat arrangement; use default if null
        this.seatArrangement = screen.getSeatGrid();
    }



    // Method to get detailed show information
    public String getShowDetails() {
        return "Show Details:" +
                "\nMovie Name: " + movieName +
                "\nShow Date: " + showDate +
                "\nStart Time: " + startTime +
                "\nEnd Time: " + endTime +
                "\nPrice: $" + price +
                "\nScreen: " + (screen != null ? screen.getName() : "No Screen Assigned") +  // Screen name or default message
                "\nSeats Available: " + seatArrangement; // Print seat arrangement
    }



    // Getter for price
    public double getPrice() {
        return price;
    }

    // Getter for movie name
    public String getMovieName() {
        return movieName;
    }

    // Getter for end time
    public LocalTime getEndTime() {
        return endTime;
    }

    // Getter for start time
    public LocalTime getStartTime() {
        return startTime;
    }

    // Getter for the associated screen
    public Screen getScreen() {
        return screen;
    }

    // Getter for show date
    public LocalDate getShowDate() {
        return showDate;
    }

    // Setter for end time with validation
    public void setEndTime(LocalTime endTime) {
        if (endTime != null) {
            this.endTime = endTime;
        }
    }

    // Overriding equals() to compare Show objects based on start time, end time, and date
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Show show = (Show) obj;
        return Objects.equals(startTime, show.startTime)
                && Objects.equals(endTime, show.endTime)
                && Objects.equals(showDate, show.showDate);
    }

    // Overriding hashCode() to generate hash based on start time, end time, and date for comparisons
    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, showDate);
    }

    // Overriding toString() to provide a string representation of a Show object
    @Override
    public String toString() {
        return "Show [Movie Name=" + movieName + ", Start Time=" + startTime + ", End Time=" + endTime + ", Date=" + showDate + "]";
    }
}




