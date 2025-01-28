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
    private Screen screen; // Associated screen for the show
    private HashMap<Character, ArrayList<String>> seatArrangement; // Seat arrangement map

    // Constructor with validations
    public Show(LocalTime startTime, LocalTime endTime, LocalDate showDate, String movieName, Screen screen, double price, HashMap<Character, ArrayList<String>> seatArrangement) {
        this.startTime = startTime != null ? startTime : LocalTime.of(0, 0);
        this.endTime = endTime != null ? endTime : LocalTime.of(0, 0);
        this.showDate = showDate != null ? showDate : LocalDate.now();
        this.movieName = movieName != null && !movieName.isEmpty() ? movieName : "Untitled";
        this.screen = screen != null ? screen : new Screen("Default Screen", 50,seatArrangement);// Default screen if null
        this.price = price > 0 ? price : 100; // Default price
        this.seatArrangement = seatArrangement != null ? seatArrangement : initializeDefaultSeats();
    }

    // Helper method to initialize a default seat arrangement
    private HashMap<Character, ArrayList<String>> initializeDefaultSeats() {
        HashMap<Character, ArrayList<String>> defaultSeatArrangement = new HashMap<>();
        for (char row = 'A'; row <= 'D'; row++) { // Rows A to D
            ArrayList<String> seats = new ArrayList<>();
            for (int i = 1; i <= 10; i++) { // 10 seats per row
                seats.add(row + String.valueOf(i));
            }
            defaultSeatArrangement.put(row, seats);
        }
        return defaultSeatArrangement;
    }

    // Get detailed show information
    public String getShowDetails() {
        return "Show Details:" +
                "\nMovie Name: " + movieName +
                "\nShow Date: " + showDate +
                "\nStart Time: " + startTime +
                "\nEnd Time: " + endTime +
                "\nPrice: $" + price +
                "\nScreen: " + (screen != null ? screen.getName() : "No Screen Assigned") +
                "\nSeats Available: " + seatArrangement;
    }

    public HashMap<Character, ArrayList<String>> getSeatArrangement() {
        return seatArrangement;
    }

    public void setSeatArrangement(HashMap<Character, ArrayList<String>> seatArrangement) {
        this.seatArrangement = seatArrangement;
    }

    public double getPrice() {
        return price;
    }

    public String getMovieName() {
        return movieName;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public Screen getScreen() {
        return screen;
    }

    public LocalDate getShowDate() {
        return showDate;
    }



    public void  setEndTime(LocalTime endTime) {
        if (endTime != null) {
            this.endTime = endTime;
        }
    }


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

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, showDate);
    }

    @Override
    public String toString() {
        return "Show [Movie Name=" + movieName + ", Start Time=" + startTime + ", End Time=" + endTime + ", Date=" + showDate + "]";
    }
}


