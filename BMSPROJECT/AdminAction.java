package BMSPROJECT;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;


public class AdminAction {

    public static Admin adminLogin(Scanner scanner) {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        for (Admin admin : BMS.getAdminsList()) {
            if (admin.getUsername().equals(username)) {
                if (admin.getPassword().equals(password)) {
                    System.out.println("Login successful! Welcome, " + username);
                    return admin;
                } else {
                    System.out.println("Login failed! Invalid password.");
                    return null;
                }
            }
        }

        System.out.println("Login failed! Invalid username.");
        return null;
    }

    public static void adminMenu(Admin admin, Scanner scanner) {
        if (admin == null) {
            System.out.println("Unauthorized access! Please log in first.");
            return;
        }

        while (true) {
            System.out.println("\n========================================");
            System.out.println("Admin Menu");
            System.out.println("========================================");
            System.out.println("1. Add Movie");
            System.out.println("2. View All Movies");
            System.out.println("3. Add Theatre");
            System.out.println("4. View All Theatres");
            System.out.println("5. View Specific Theatre"); // New option
            System.out.println("6. Logout");
            System.out.println("Today's Date: " + Utilities.getToday());
            System.out.print("Enter your choice: ");


            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addMovie(scanner);
                    break;
                case "2":
                    viewAllMovies();
                    break;
                case "3":
                    addTheatre(scanner);
                    break;
                case "4":
                    viewAllTheatres();
                    break;
                case "5":
                    viewSpecificTheatre(scanner); // Handle the new option
                    break;
                case "6":
                    System.out.println("Logging out... Goodbye, " + admin.getUsername());
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    public static void addTheatre(Scanner scanner) {
        System.out.println("\nAdding a new theatre...");
        System.out.print("Enter Theatre Name: ");
        String theatreName = scanner.nextLine(); // Name of the theatre

        // Check if the theatre already exists
        if (BMS.getTheatreDirectory().containsKey(theatreName)) {
            System.out.println("Theatre with the name \"" + theatreName + "\" already exists.");
            return; // Exit the method if the theatre already exists
        }

        System.out.print("Enter Location: ");
        String location = scanner.nextLine(); // Location of the theatre

        // Ensure correct mapping from name and location when creating the Theatre object
        Theatre newTheatre = new Theatre(theatreName, location);  // Name first, then location

        // Process adding screens
        System.out.print("Enter Number of Screens: ");
        int numberOfScreens = Integer.parseInt(scanner.nextLine());

        for (int i = 1; i <= numberOfScreens; i++) {
            System.out.print("Enter Screen " + i + " Name: ");
            String screenName = scanner.nextLine();

            System.out.print("Enter Number of Seats: ");
            int numberOfSeats = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter the Screen Grid like (2*5*2)*For space:");
            String grid = scanner.nextLine();
            var seatsGrid = Utilities.generateGrid(numberOfSeats, grid);

            if (seatsGrid == null) {
                System.out.println("Invalid seat grid configuration. Please try again.");
                return;
            }

            Screen newScreen = new Screen(screenName, numberOfSeats, seatsGrid);
            newTheatre.addScreen(screenName, newScreen);
        }

        BMS.getTheatreDirectory().put(theatreName, newTheatre); // Store theatre by name
        System.out.println("Theatre added successfully!");
    }

    public static void addMovie(Scanner scanner) {
        System.out.println("\nAdding a new movie...");

        // Get the movie name
        System.out.print("Enter Movie Name: ");
        String movieName = scanner.nextLine();

        // Get the location
        System.out.print("Enter Location: ");
        String movieLocation = scanner.nextLine().trim();  // Ensure no leading/trailing spaces

        // Get the movie date
        System.out.print("Enter Date (yyyy-MM-dd): ");
        LocalDate movieDate = LocalDate.parse(scanner.nextLine(), Utilities.getDateFormatter());

        // Validate that the movie date is not in the past
        if (movieDate.isBefore(Utilities.getToday())) {
            System.out.println("Error: Movie date cannot be in the past.");
            return;
        }

        // Find theatres in the specified location
        ArrayList<Theatre> theatresInLocation = new ArrayList<>();
        for (Theatre theatre : BMS.getTheatreDirectory().values()) {
            // Ensure comparison is done with trimmed locations
            if (theatre.getLocation().equalsIgnoreCase(movieLocation)) {
                theatresInLocation.add(theatre);
            }
        }

        if (theatresInLocation.isEmpty()) {
            System.out.println("No theatres available in this location. Add a theatre first.");
            return;
        }

        // Select a theatre
        Theatre selectedTheatre = selectTheatre(scanner, theatresInLocation);
        if (selectedTheatre == null) return;

        // Select a screen
        Screen selectedScreen = selectScreen(scanner, selectedTheatre);
        if (selectedScreen == null) return;

        // Get show details
        System.out.print("Enter Show Start Time (HH:mm): ");
        LocalTime startTime = LocalTime.parse(scanner.nextLine(), Utilities.getTimeFormatter());

        System.out.print("Enter Duration (in minutes): ");
        long duration = Long.parseLong(scanner.nextLine());

        System.out.print("Enter Ticket Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        // Create the show with an initial start and end time
        LocalTime endTime = startTime.plusMinutes(duration + 30); // 30-minute buffer
        Show newShow = new Show(startTime, endTime, movieDate, movieName, selectedScreen, price, createSeatGrid());

        // Option to update the end time
        System.out.print("Do you want to update the show end time? (Y/N): ");
        String updateEndTimeChoice = scanner.nextLine();
        if (updateEndTimeChoice.equalsIgnoreCase("Y")) {
            System.out.print("Enter New Show End Time (HH:mm): ");
            LocalTime newEndTime = LocalTime.parse(scanner.nextLine(), Utilities.getTimeFormatter());
            newShow.setEndTime(newEndTime); // Use setEndTime method
            System.out.println("Show end time updated to " + newShow.getEndTime());
        }

        // Add the show to the selected screen
        selectedScreen.addShow(newShow);

        // Correctly store the movie with its location and show details
        Movies movie = new Movies(movieName, movieLocation, movieDate, selectedTheatre, selectedScreen, newShow);
        BMS.getMovieDirectory()
                .computeIfAbsent(movieName, ignored -> new ArrayList<>())
                .add(movie);

        System.out.println("Movie and show added successfully!");
    }


    private static Theatre selectTheatre(Scanner scanner, List<Theatre> theatresInLocation) {
        System.out.println("Available Theatres:");
        for (Theatre theatre : theatresInLocation) {
            System.out.println("- " + theatre.getTheatreName());
        }

        System.out.print("Enter Theatre Name: ");
        String theatreName = scanner.nextLine();

        for (Theatre theatre : theatresInLocation) {
            if (theatre.getTheatreName().equalsIgnoreCase(theatreName)) {
                return theatre;
            }
        }

        System.out.println("Invalid theatre name.");
        return null;
    }

    private static Screen selectScreen(Scanner scanner, Theatre selectedTheatre) {
        System.out.println("Available Screens:");
        for (String screenName : selectedTheatre.getScreens().keySet()) {
            System.out.println("- " + screenName);
        }

        System.out.print("Enter Screen Name: ");
        String screenName = scanner.nextLine();

        return selectedTheatre.getScreens().get(screenName);
    }

    private static HashMap<Character, ArrayList<String>> createSeatGrid() {
        HashMap<Character, ArrayList<String>> seatArrangement = new HashMap<>();
        for (char row = 'A'; row <= 'D'; row++) {
            ArrayList<String> seats = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                seats.add(row + String.valueOf(i));
            }
            seatArrangement.put(row, seats);
        }
        return seatArrangement;
    }

    public static void viewAllTheatres() {
        if (BMS.getTheatreDirectory().isEmpty()) {
            System.out.println("No theatres available.");
        } else {
            System.out.println("Theatres in the system:");
            for (String theatreName : BMS.getTheatreDirectory().keySet()) {
                Theatre theatre = BMS.getTheatreDirectory().get(theatreName);
                System.out.println("Theatre Name: " + theatre.getTheatreName() + ", Location: " + theatre.getLocation());

                // Display details for each screen and its shows
                for (String screenName : theatre.getScreens().keySet()) {
                    Screen screen = theatre.getScreens().get(screenName);
                    System.out.println("    Screen Name: " + screen.getName() + ", Seats: " + screen.getNumberOfSeats());

                    // Display the seat grid for the screen
                    HashMap<Character, ArrayList<String>> seatGrid = screen.getSeatGrid();
                    System.out.println("    Seat Grid:");
                    for (Character row : seatGrid.keySet()) {
                        ArrayList<String> seats = seatGrid.get(row);
                        System.out.println("      Row " + row + ": " + String.join(", ", seats));
                    }

                    // Using the getShows() method to list all shows on this screen
                    List<Show> shows = screen.getShows();
                    if (shows.isEmpty()) {
                        System.out.println("    No shows scheduled for this screen.");
                    } else {
                        System.out.println("    Scheduled Shows:");
                        for (Show show : shows) {
                            System.out.println("      Movie: " + show.getMovieName() +
                                    ", Start Time: " + show.getStartTime().format(Utilities.getTimeFormatter()) +
                                    ", End Time: " + show.getEndTime().format(Utilities.getTimeFormatter()) +
                                    ", Price: $" + show.getPrice());
                        }
                    }
                }
            }
        }
    }

    public static void viewAllMovies() {
        if (BMS.getMovieDirectory().isEmpty()) {
            System.out.println("No movies available.");
            return;
        }

        for (ArrayList<Movies> movieList : BMS.getMovieDirectory().values()) {
            for (Movies movie : movieList) {
                Show show = movie.getShow(); // Get the associated Show object
                Screen screen = movie.getScreen(); // Get the associated screen

                if (screen == null) {
                    System.out.println("No screen assigned for movie: " + movie.getName());
                    continue; // Skip if no screen is assigned
                }

                // Retrieve the seat grid directly from the Screen object
                HashMap<Character, ArrayList<String>> seatGrid = screen.getSeatGrid();

                System.out.println("\n========================================");
                System.out.println("Movie Name: " + movie.getName());
                System.out.println("Theatre: " + movie.getTheatre().getTheatreName());
                System.out.println("Location: " + movie.getLocation()); // Location printed after the theatre
                System.out.println("Date: " + show.getShowDate()); // Use getShowDate() to display the show's date
                System.out.println("Start Date: " + movie.getStartDate()); // Display start date using getStartDate()

                // Using the getScreen() method to display screen information
                if (screen != null) {
                    System.out.println("Screen: " + screen.getName());
                } else {
                    System.out.println("Screen: No Screen Assigned");
                }

                System.out.println("Show Time: " + show.getStartTime().format(Utilities.getTimeFormatter()) + " - " + show.getEndTime().format(Utilities.getTimeFormatter()));
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
        for (Map.Entry<Character, ArrayList<String>> entry : seatGrid.entrySet()) {
            System.out.print(entry.getKey() + " | ");  // Row label (e.g., A, B, C)

            // Print the seats for that row
            for (String seat : entry.getValue()) {
                System.out.print(seat + " "); // Print seat
            }
            System.out.println(); // Move to next line after each row
        }
        System.out.println(); // Add extra space after the full layout
    }
    // Method to view a specific theatre
    public static void viewSpecificTheatre(Scanner scanner) {
        System.out.print("Enter Theatre Name to view: ");
        String theatreName = scanner.nextLine();

        // Check if the theatre exists
        Theatre theatre = BMS.getTheatreDirectory().get(theatreName);

        if (theatre == null) {
            System.out.println("Theatre with the name \"" + theatreName + "\" not found.");
            return;
        }

        // Display theatre information
        System.out.println("\n========================================");
        System.out.println("Theatre Name: " + theatre.getTheatreName());
        System.out.println("Location: " + theatre.getLocation());
        System.out.println("Screens: ");

        // Display screens information
        if (theatre.getScreens().isEmpty()) {
            System.out.println("  No screens available.");
        } else {
            for (String screenName : theatre.getScreens().keySet()) {
                Screen screen = theatre.getScreens().get(screenName);
                System.out.println("  Screen Name: " + screen.getName() + ", Seats: " + screen.getNumberOfSeats());
                // Display seat grid
                HashMap<Character, ArrayList<String>> seatGrid = screen.getSeatGrid();
                System.out.println("    Seat Grid:");
                for (Character row : seatGrid.keySet()) {
                    ArrayList<String> seats = seatGrid.get(row);
                    System.out.println("      Row " + row + ": " + String.join(", ", seats));
                }
            }
        }

        // Optionally, you can display show details for each screen
        System.out.println("\nScheduled Shows:");
        for (String screenName : theatre.getScreens().keySet()) {
            Screen screen = theatre.getScreens().get(screenName);
            List<Show> shows = screen.getShows();
            if (shows.isEmpty()) {
                System.out.println("  No shows scheduled for screen: " + screenName);
            } else {
                System.out.println("  Shows for screen: " + screenName);
                for (Show show : shows) {
                    System.out.println("    Movie: " + show.getMovieName() +
                            ", Start Time: " + show.getStartTime().format(Utilities.getTimeFormatter()) +
                            ", End Time: " + show.getEndTime().format(Utilities.getTimeFormatter()) +
                            ", Price: $" + show.getPrice());
                }
            }
        }
    }


}




