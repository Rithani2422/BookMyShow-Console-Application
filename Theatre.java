package BMSPROJECT;
import java.util.HashMap;

public class Theatre {
    private final String theatreName; // Name of the theatre
    private final String location; // Location of the theatre
    private final HashMap<String, Screen> screens = new HashMap<>(); // A map to store screens by name, with the screen object

    // Constructor to initialize the theatre with a name and location
    public Theatre(String theatreName, String location) {
        this.theatreName = theatreName;
        this.location = location;
    }

    // Getter for theatre name
    public String getTheatreName() {
        return theatreName;
    }

    // Getter for theatre location
    public String getLocation() {
        return location;
    }

    // Getter for the list of screens in the theatre
    public HashMap<String, Screen> getScreens() {
        return screens;
    }


    // Override the toString method to display the theatre details
    @Override
    public String toString() {
        return "Theatre Details:" +
                "\nName: " + theatreName +
                "\nLocation: " + location +
                "\nNumber of Screens: " + screens.size(); // Show the number of screens in the theatre
    }
}
