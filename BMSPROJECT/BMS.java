package BMSPROJECT;
import java.util.*;

public class BMS {
    // Represents a database of admins in the system
    private static final ArrayList<Admin> adminsList = new ArrayList<>();  // Stores admin objects

    // Represents a database of users in the system
    private static final ArrayList<User> usersList = new ArrayList<>();    // Stores user objects

    // Maps theatre names to their corresponding Theatre objects
    private static final HashMap<String, Theatre> theatreDirectory = new HashMap<>();

    // Maps movie names to a list of movie objects for each name
    private static final HashMap<String, ArrayList<Movies>> movieDirectory = new HashMap<>();

    // Getter for admins list
    public static ArrayList<Admin> getAdminsList() {
        return adminsList;
    }

    // Getter for users list
    public static ArrayList<User> getUsersList() {
        return usersList;
    }

    // Getter for movie directory (movie name and list of movies)
    public static HashMap<String, ArrayList<Movies>> getMovieDirectory() {
        return movieDirectory;
    }

    // Getter for theatre directory (theatre name and corresponding Theatre object)
    public static HashMap<String, Theatre> getTheatreDirectory() {
        return theatreDirectory;
    }
}
