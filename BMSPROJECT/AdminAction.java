package BMSPROJECT;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;


public class AdminAction {

    public static Admin adminLogin() {
        Scanner scanner = new Scanner(System.in);
        // Prompt the admin to enter their username
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine(); // Read the entered username

        // Prompt the admin to enter their password
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine(); // Read the entered password

        // Iterate through the list of registered admins
        for (Admin admin : BMS.getAdminsList()) {
            // Check if the entered username matches any existing admin's username
            if (admin.getUsername().equals(username)) {
                // If username matches, verify the password
                if (admin.getPassword().equals(password)) {
                    System.out.println("Login successful! Welcome, " + username);
                    return admin; // Return the authenticated admin object
                } else {
                    System.out.println("Login failed! Invalid password.");
                    return new Admin(null,null); // Return null if the password is incorrect
                }
            }
        }

        // If no matching username was found, print an error message
        System.out.println("Login failed! Invalid username.");
        return null; // Return null if the username is not found
    }

    public static void adminMenu(Admin admin) {
        Scanner scanner = new Scanner(System.in);
        // Check if the admin is logged in
        if (admin == null) {
            System.out.println("Unauthorized access! Please log in first.");
            return; // Exit the method if the admin is not authenticated
        }

        // Start an infinite loop to keep showing the menu until the admin logs out
        while (true) {
            // Display the admin menu options
            System.out.println("\n========================================");
            System.out.println("Admin Menu");
            System.out.println("========================================");
            System.out.println("1. Add Movie");
            System.out.println("2. View All Movies");
            System.out.println("3. Add Theatre");
            System.out.println("4. View All Theatres");
            System.out.println("5. View Specific Theatre"); // New option added
            System.out.println("6. Logout");
            System.out.println("Today's Date: " + Utilities.getToday()); // Display the current date
            System.out.print("Enter your choice: ");

            // Read the user's menu choice
            String choice = scanner.nextLine();

            // Perform actions based on the selected menu option
            switch (choice) {
                case "1":
                    addMovie(); // Call method to add a movie
                    break;
                case "2":
                    viewAllMovies(); // Call method to view all movies
                    break;
                case "3":
                    addTheatre(); // Call method to add a theatre
                    break;
                case "4":
                    viewAllTheatres(); // Call method to view all theatres
                    break;
                case "5":
                    viewSpecificTheatre(); // Call method to view a specific theatre
                    break;
                case "6":
                    // Logout action
                    System.out.println("Logging out... Goodbye, " + admin.getUsername());
                    return; // Exit the loop and return from the method
                default:
                    System.out.println("Invalid choice! Please try again."); // Handle invalid input
            }
        }
    }

    public static void addTheatre() {
        Scanner scanner = new Scanner(System.in);
        // Prompt the admin to enter details for a new theatre
        System.out.println("\nAdding a new theatre...");
        System.out.print("Enter Theatre Name: ");
        String theatreName = scanner.nextLine(); // Read the name of the theatre

        // Check if a theatre with the same name already exists in the system
        if (BMS.getTheatreDirectory().containsKey(theatreName)) {
            System.out.println("Theatre with the name \"" + theatreName + "\" already exists.");
            return; // Exit the method if the theatre already exists
        }

        // Read the location of the theatre
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();

        // Create a new Theatre object using the entered name and location
        Theatre newTheatre = new Theatre(theatreName, location);

        // Prompt for the number of screens in the theatre
        System.out.print("Enter Number of Screens: ");
        int numberOfScreens = Integer.parseInt(scanner.nextLine()); // Convert input to integer


        // Loop through the number of screens and get details for each one
        for (int i = 1; i <= numberOfScreens; i++) {
            System.out.print("Enter Screen " + i + " Name: ");
            String screenName = scanner.nextLine(); // Read screen name

            System.out.print("Enter Number of Seats: ");
            int numberOfSeats = Integer.parseInt(scanner.nextLine()); // Read number of seats

            // Prompt the admin to enter the seat grid configuration
            System.out.print("Enter the Screen Grid like (2*5*2)*For space: ");
            String grid = scanner.nextLine();

            // Generate the seating grid based on the provided configuration
            var seatsGrid = Utilities.generateGrid(numberOfSeats, grid);

            // Check if the grid was generated successfully
            if (seatsGrid == null) {
                System.out.println("Invalid seat grid configuration. Please try again.");
                return; // Exit if the grid configuration is invalid
            }

            // Create a new Screen object with the provided details
            Screen newScreen = new Screen(screenName, numberOfSeats, seatsGrid);

            // Add the screen to the theatre
            AdminAction.addScreen(screenName, newScreen);
        }

        // Add the newly created theatre to the system's theatre directory
        BMS.getTheatreDirectory().put(theatreName, newTheatre);

        // Confirm that the theatre has been successfully added
        System.out.println("Theatre added successfully!");
    }

    // Method to add a new screen to the theatre
    public static void addScreen(String screenName, Screen screen) {
        HashMap<String, Screen> screens = new HashMap<>();
        // Check if a screen with the same name already exists
        if (screens.containsKey(screenName)) {
            System.out.println("Screen with this name already exists: " + screenName);
        } else {
            screens.put(screenName, screen); // Add the screen to the map
            System.out.println("Screen added successfully: " + screenName);
        }
    }

    public static void addMovie() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nAdding a new movie...");

        // Get the movie name from the admin
        System.out.print("Enter Movie Name: ");
        String movieName = scanner.nextLine();

        // Get the location where the movie will be screened
        System.out.print("Enter Location: ");
        String movieLocation = scanner.nextLine().trim();  // Trim spaces to avoid input issues

        // Get the movie date from the admin and parse it using a predefined date format
        System.out.print("Enter Date (yyyy-MM-dd): ");
        LocalDate movieDate = LocalDate.parse(scanner.nextLine(), Utilities.getDateFormatter());

        // Validate that the selected movie date is not in the past
        if (movieDate.isBefore(Utilities.getToday())) {
            System.out.println("Error: Movie date cannot be in the past.");
            return; // Exit if the movie date is invalid
        }

        // Find available theatres in the specified location
        ArrayList<Theatre> theatresInLocation = new ArrayList<>();
        for (Theatre theatre : BMS.getTheatreDirectory().values()) {
            // Compare location in a case-insensitive manner
            if (theatre.getLocation().equalsIgnoreCase(movieLocation)) {
                theatresInLocation.add(theatre);
            }
            else{
                System.out.println("No theatres available in this location. Add a theatre first.");
                return;
            }
        }
        // Allow the admin to select a theatre from the available options
        Theatre selectedTheatre = selectTheatre(theatresInLocation);
        if (selectedTheatre == null) return; // Exit if no theatre is selected

        // Allow the admin to select a screen within the chosen theatre
        Screen selectedScreen = selectScreen(selectedTheatre);
        if (selectedScreen == null) return; // Exit if no screen is selected

        // Check for conflicting shows in the selected screen
        if (hasTimeConflict(selectedScreen, movieDate)) {
            return; // Exit if there is a time conflict
        }

        // Get show start time from the admin and parse it using a predefined time format
        System.out.print("Enter Show Start Time (HH:mm): ");
        LocalTime startTime = LocalTime.parse(scanner.nextLine(), Utilities.getTimeFormatter());

        // Get the duration of the movie (in minutes)
        System.out.print("Enter Duration (in minutes): ");
        long duration = Long.parseLong(scanner.nextLine());

        // Get the ticket price for the show
        System.out.print("Enter Ticket Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        // Calculate the initial end time of the show with a 30-minute buffer
        LocalTime endTime = startTime.plusMinutes(duration + 30);

        // Create a new Show object with the gathered details
        Show newShow = new Show(startTime, endTime, movieDate, movieName, selectedScreen, price, createSeatGrid());

        // Give the admin an option to update the end time of the show
        System.out.print("Do you want to update the show end time? (Y/N): ");
        String updateEndTimeChoice = scanner.nextLine();
        if (updateEndTimeChoice.equalsIgnoreCase("Y")) {
            System.out.print("Enter New Show End Time (HH:mm): ");
            LocalTime newEndTime = LocalTime.parse(scanner.nextLine(), Utilities.getTimeFormatter());
            newShow.setEndTime(newEndTime); // Update the end time using the setter method
            System.out.println("Show end time updated to " + newShow.getEndTime());
        }

        // Add the newly created show to the selected screen
        selectedScreen.addShow(newShow);
        // Confirm successful addition of the movie and its show
        System.out.println("Movie and show added successfully!");
    }

    private static boolean hasTimeConflict(Screen selectedScreen, LocalDate movieDate) {
        Scanner scanner = new Scanner(System.in);
        // Get the list of shows for the selected screen
        List<Show> existingShows = selectedScreen.getShows(); // Change ArrayList to List

        // If there are no existing shows, there's no conflict
        if (existingShows.isEmpty()) {
            return false;
        }

        System.out.print("Enter Show Start Time (HH:mm): ");
        LocalTime startTime = LocalTime.parse(scanner.nextLine(), Utilities.getTimeFormatter());

        // Get the duration of the movie
        System.out.print("Enter Duration (in minutes): ");
        long duration = Long.parseLong(scanner.nextLine());

        // Calculate the end time
        LocalTime endTime = startTime.plusMinutes(duration + 30);

        // Check if the new showtime conflicts with any existing show
        for (Show existingShow : existingShows) {
            // If the new show starts before an existing show ends and ends after an existing show starts
            if (startTime.isBefore(existingShow.getEndTime()) && endTime.isAfter(existingShow.getStartTime())) {
                System.out.println("Error: The selected time conflicts with an existing show.");
                return true; // Return true if there's a time conflict
            }
        }

        return false; // Return false if no conflict is found
    }


    // Method to select a theatre from a list of theatres in a location
    private static Theatre selectTheatre(List<Theatre> theatresInLocation) {
        Scanner scanner = new Scanner(System.in);
        // Display available theatres in the given location
        System.out.println("Available Theatres:");
        for (Theatre theatre : theatresInLocation) {
            System.out.println("- " + theatre.getTheatreName()); // Print the name of each theatre
        }

        // Prompt the user to enter a theatre name
        System.out.print("Enter Theatre Name: ");
        String theatreName = scanner.nextLine(); // Capture the user's input

        // Search for the theatre in the list based on the input name
        for (Theatre theatre : theatresInLocation) {
            if (theatre.getTheatreName().equalsIgnoreCase(theatreName)) {
                return theatre; // Return the selected theatre if found
            }
        }

        // If no match is found, print an error message and return null
        System.out.println("Invalid theatre name.");
        return null;
    }

    // Method to select a screen from a given theatre
    private static Screen selectScreen(Theatre selectedTheatre) {
        Scanner scanner = new Scanner(System.in);
        // Display available screens in the selected theatre
        System.out.println("Available Screens:");
        // Loop through each screen's name in the theatre's list of screens
        for (String screenName : selectedTheatre.getScreens().keySet()) {
            System.out.println("- " + screenName); // Print the name of each screen
        }

        // Prompt the user to enter a screen name
        System.out.print("Enter Screen Name: ");
        String screenName = scanner.nextLine(); // Capture user input

        // Return the screen object based on the user's input
        return selectedTheatre.getScreens().get(screenName);
    }

    // Method to create a seat grid for a theatre
    private static HashMap<Character, ArrayList<String>> createSeatGrid() {
        // Create a HashMap to hold seat arrangement, where the key is the row letter and the value is a list of seats
        HashMap<Character, ArrayList<String>> seatArrangement = new HashMap<>();

        // Loop through rows 'A' to 'D'
        for (char row = 'A'; row <= 'D'; row++) {
            // Create an ArrayList to hold the seats in the current row
            ArrayList<String> seats = new ArrayList<>();

            // Add seat labels for each seat in the row (e.g., A1, A2, ..., A10)
            for (int i = 1; i <= 10; i++) {
                seats.add(row + String.valueOf(i)); // Generate seat label (e.g., A1, B3)
            }

            // Put the row and its corresponding seats into the seatArrangement map
            seatArrangement.put(row, seats);
        }

        // Return the seat arrangement map
        return seatArrangement;
    }

    // Method to view all theatres in the system
    public static void viewAllTheatres() {
        // Check if there are any theatres in the system
        if (BMS.getTheatreDirectory().isEmpty()) {
            System.out.println("No theatres available."); // Display message if no theatres exist
        } else {
            System.out.println("Theatres in the system:");
            // Loop through each theatre in the directory
            for (String theatreName : BMS.getTheatreDirectory().keySet()) {
                Theatre theatre = BMS.getTheatreDirectory().get(theatreName); // Get the theatre object
                System.out.println("Theatre Name: " + theatre.getTheatreName() + ", Location: " + theatre.getLocation()); // Print theatre name and location

                // Loop through each screen in the current theatre
                for (String screenName : theatre.getScreens().keySet()) {
                    Screen screen = theatre.getScreens().get(screenName); // Get the screen object
                    System.out.println("    Screen Name: " + screen.getName() + ", Seats: " + screen.getNumberOfSeats()); // Print screen details

                    // Display the seat grid for the screen
                    HashMap<Character, ArrayList<String>> seatGrid = screen.getSeatGrid(); // Get the seat grid
                    System.out.println("    Seat Grid:");
                    // Loop through each row in the seat grid
                    for (Character row : seatGrid.keySet()) {
                        ArrayList<String> seats = seatGrid.get(row); // Get the list of seats for the row
                        System.out.println("      Row " + row + ": " + String.join(", ", seats)); // Print seats in the row
                    }

                    // Get the list of shows scheduled for the current screen
                    List<Show> shows = screen.getShows();
                    // Check if there are no shows scheduled for this screen
                    if (shows.isEmpty()) {
                        System.out.println("    No shows scheduled for this screen.");
                    } else {
                        System.out.println("    Scheduled Shows:");
                        // Loop through each show and display its details
                        for (Show show : shows) {
                            System.out.println("      Movie: " + show.getMovieName() +
                                    ", Start Time: " + show.getStartTime().format(Utilities.getTimeFormatter()) +
                                    ", End Time: " + show.getEndTime().format(Utilities.getTimeFormatter()) +
                                    ", Price: $" + show.getPrice()); // Print show details
                        }
                    }
                }
            }
        }
    }


    // Method to view all movies in the system
    public static void viewAllMovies() {
        // Check if the movie directory is empty
        if (BMS.getMovieDirectory().isEmpty()) {
            System.out.println("No movies available."); // Print message if no movies are available
            return; // Exit the method early if there are no movies
        }

        // Loop through each movie list in the movie directory
        for (ArrayList<Movies> movieList : BMS.getMovieDirectory().values()) {
            // Loop through each movie in the current list
            for (Movies movie : movieList) {
                Show show = movie.getShow(); // Get the associated Show object for the movie
                Screen screen = movie.getScreen(); // Get the associated Screen object for the movie

                // Check if a screen is assigned to the movie
                if (screen == null) {
                    System.out.println("No screen assigned for movie: " + movie.getName()); // Print message if no screen is assigned
                    continue; // Skip to the next movie if no screen is assigned
                }

                // Retrieve the seat grid directly from the Screen object
                HashMap<Character, ArrayList<String>> seatGrid = screen.getSeatGrid();

                // Print movie and show details
                System.out.println("\n========================================");
                System.out.println("Movie Name: " + movie.getName()); // Display movie name
                System.out.println("Theatre: " + movie.getTheatre().getTheatreName()); // Display theatre name
                System.out.println("Location: " + movie.getLocation()); // Display the location
                System.out.println("Date: " + show.getShowDate()); // Display the show's date
                System.out.println("Start Date: " + movie.getStartDate()); // Display the start date of the movie

                // Display screen information
                if (screen != null) {
                    System.out.println("Screen: " + screen.getName()); // Display screen name
                } else {
                    System.out.println("Screen: No Screen Assigned"); // Display if no screen is assigned
                }

                // Display show time (start and end time)
                System.out.println("Show Time: " + show.getStartTime().format(Utilities.getTimeFormatter()) + " - " + show.getEndTime().format(Utilities.getTimeFormatter()));
                // Display ticket price
                System.out.println("Price: Rs." + show.getPrice());

                // Display seat arrangement (with space between seats)
                System.out.println("Seat Arrangement: ");
                printSeatGrid(seatGrid); // Print seat grid for the current screen
            }
        }
    }

    // Helper method to print seat layout in a more readable grid format
    private static void printSeatGrid(HashMap<Character, ArrayList<String>> seatGrid) {
        System.out.println("Seat Layout:");
        // Loop through each row in the seat grid
        for (Map.Entry<Character, ArrayList<String>> entry : seatGrid.entrySet()) {
            // Print row label (e.g., A, B, C)
            System.out.print(entry.getKey() + " | ");

            // Print the seats in the current row
            for (String seat : entry.getValue()) {
                System.out.print(seat + " "); // Print each seat in the row
            }
            System.out.println(); // Move to the next line after printing the row
        }
        System.out.println(); // Add an extra space after the full seat layout
    }

    // Method to view a specific theatre
    public static void viewSpecificTheatre() {
        Scanner scanner = new Scanner(System.in);
        // Prompt the user to enter a theatre name to view
        System.out.print("Enter Theatre Name to view: ");
        String theatreName = scanner.nextLine();

        // Check if the theatre exists in the system
        Theatre theatre = BMS.getTheatreDirectory().get(theatreName);

        // If the theatre doesn't exist, print an error message and exit
        if (theatre == null) {
            System.out.println("Theatre with the name \"" + theatreName + "\" not found.");
            return; // Exit the method if the theatre is not found
        }

        // Display the theatre's information
        System.out.println("\n========================================");
        System.out.println("Theatre Name: " + theatre.getTheatreName()); // Print the theatre's name
        System.out.println("Location: " + theatre.getLocation()); // Print the theatre's location
        System.out.println("Screens: ");

        // Check if the theatre has any screens
        if (theatre.getScreens().isEmpty()) {
            System.out.println("  No screens available."); // Print if there are no screens
        } else {
            // Loop through each screen in the theatre and display details
            for (String screenName : theatre.getScreens().keySet()) {
                Screen screen = theatre.getScreens().get(screenName);
                System.out.println("  Screen Name: " + screen.getName() + ", Seats: " + screen.getNumberOfSeats()); // Print screen details
                // Display the seat grid for each screen
                HashMap<Character, ArrayList<String>> seatGrid = screen.getSeatGrid();
                System.out.println("    Seat Grid:");
                // Loop through each row in the seat grid and print the seats
                for (Character row : seatGrid.keySet()) {
                    ArrayList<String> seats = seatGrid.get(row);
                    System.out.println("      Row " + row + ": " + String.join(", ", seats)); // Print the seats in each row
                }
            }
        }

        // Optionally, display the scheduled shows for each screen
        System.out.println("\nScheduled Shows:");
        // Loop through each screen in the theatre
        for (String screenName : theatre.getScreens().keySet()) {
            Screen screen = theatre.getScreens().get(screenName);
            List<Show> shows = screen.getShows();
            // Check if there are no shows scheduled for the current screen
            if (shows.isEmpty()) {
                System.out.println("  No shows scheduled for screen: " + screenName); // Print if no shows are scheduled
            } else {
                // Print the scheduled shows for the current screen
                System.out.println("  Shows for screen: " + screenName);
                for (Show show : shows) {
                    // Print details for each show (movie name, start time, end time, and price)
                    System.out.println("    Movie: " + show.getMovieName() +
                            ", Start Time: " + show.getStartTime().format(Utilities.getTimeFormatter()) +
                            ", End Time: " + show.getEndTime().format(Utilities.getTimeFormatter()) +
                            ", Price: Rs." + show.getPrice());
                }
            }
        }
    }
}


