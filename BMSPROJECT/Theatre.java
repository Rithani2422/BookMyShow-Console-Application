package BMSPROJECT;
import java.util.HashMap;

public class Theatre {
    private final String theatreName; // Theatre name
    private final String location; // Theatre location
    private final HashMap<String, Screen> screens = new HashMap<>(); // Screen name and screen object of theatre

    // Constructor to initialize location and name
    public Theatre (String theatreName,String location) {
        this.theatreName = theatreName;
        this.location = location;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public String getLocation() {
        return location;
    }

    public HashMap<String, Screen> getScreens() {
        return screens;
    }

    // Add a new screen to the theatre
    public void addScreen(String screenName, Screen screen) {
        if (screens.containsKey(screenName)) {
            System.out.println("Screen with this name already exists: " + screenName);
        } else {
            screens.put(screenName, screen);
            System.out.println("Screen added successfully: " + screenName);
        }
    }

    // toString method for better debugging and display
    @Override
    public String toString() {
        return "Theatre Details:" +
                "\nName: " + theatreName +
                "\nLocation: " + location +
                "\nNumber of Screens: " + screens.size();
    }
}