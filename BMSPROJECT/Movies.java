package BMSPROJECT;
import java.time.LocalDate;

public class Movies {
    private final String name; // Name of the movie
    private final String location; // Location of the movie
    private final LocalDate startDate; // Start date of the movie
    private final Theatre theatre; // Theatre where the movie is added
    private final Screen screen; // Screen where the movie is added
    private final Show show; // Show where the movie is added

    // Constructor
    public Movies(String name, String location, LocalDate startDate, Theatre theatre, Screen screen, Show show) {
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.theatre = theatre;
        this.screen = screen;
        this.show = show;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public Screen getScreen() {
        return screen;
    }

    public Show getShow() {
        return show;
    }

    // toString method for better debugging and display
    @Override
    public String toString() {
        return "Movie Details:" +
                "\nName: " + name +
                "\nLocation: " + location +
                "\nStart Date: " + startDate +
                "\nTheatre: " + theatre.getTheatreName() +
                "\nScreen: " + screen.getName() +
                "\nShow: " + show.getShowDetails();
    }
}
