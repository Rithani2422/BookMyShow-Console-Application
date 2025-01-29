package BMSPROJECT;

import java.time.LocalDate;

public class Movies {
    private final String name; // Name of the movie
    private final String location; // Location where the movie is being shown
    private final LocalDate startDate; // Start date of the movie
    private final Theatre theatre; // Theatre where the movie is being shown
    private final Screen screen; // Screen in the theatre where the movie is being shown
    private final Show show; // Show details of the movie

    // Constructor to initialize the movie details
    public Movies(String name, String location, LocalDate startDate, Theatre theatre, Screen screen, Show show) {
        this.name = name;             // Assign the movie name
        this.location = location;     // Assign the location of the movie
        this.startDate = startDate;   // Assign the start date of the movie
        this.theatre = theatre;       // Assign the theatre object where the movie is playing
        this.screen = screen;         // Assign the screen in the theatre where the movie is playing
        this.show = show;             // Assign the show object for the movie
    }

    // Getter for movie name
    public String getName() {
        return name;
    }

    // Getter for movie location
    public String getLocation() {
        return location;
    }

    // Getter for the start date of the movie
    public LocalDate getStartDate() {
        return startDate;
    }

    // Getter for the theatre where the movie is being shown
    public Theatre getTheatre() {
        return theatre;
    }

    // Getter for the screen in the theatre
    public Screen getScreen() {
        return screen;
    }

    // Getter for the show details of the movie
    public Show getShow() {
        return show;
    }

    // toString method for better debugging and display of movie details
    @Override
    public String toString() {
        return "Movie Details:" +
                "\nName: " + name +               // Display the movie name
                "\nLocation: " + location +       // Display the location where the movie is shown
                "\nStart Date: " + startDate +    // Display the movie start date
                "\nTheatre: " + theatre.getTheatreName() + // Display the theatre name
                "\nScreen: " + screen.getName() + // Display the screen name where the movie is shown
                "\nShow: " + show.getShowDetails(); // Display show details
    }
}

