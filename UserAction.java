package BMSPROJECT;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;

public class UserAction {
    public static HashMap<Character, ArrayList<String>> seatArrangement = new HashMap<>();
    public static User userLogin(Scanner scanner) {
        // Prompt the user to enter a username and password
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        // Iterate over the list of users to check if the username exists
        for (User user : BMS.getUsersList()) {
            // Check if the entered username matches any existing username
            if (user.getUsername().equals(username)) {
                // If the username matches, check if the password is correct
                if (user.getPassword().equals(password)) {
                    // If both username and password are correct, return the user object
                    return user;
                } else {
                    // If the password is incorrect, print an error message and return null
                    System.out.println("Login failed! Invalid password.");
                    return null;
                }
            }
        }
        // If the username doesn't exist, print an error message and return null
        System.out.println("Login failed! Invalid username.");
        return null;
    }

    public static void registerUser(Scanner scanner) {
        // Prompt the user to enter a username and password for registration
        System.out.print("Enter a username to register: ");
        String username = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        // Iterate over the list of users to check if the username already exists
        for (User user : BMS.getUsersList()) {
            // If the username already exists, print a message and return (exit the method)
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists. Please try a different username.");
                return;
            }
        }

        // If the username doesn't exist, create a new user and add it to the list
        BMS.getUsersList().add(new User(username, password, " "));
        // Print a success message confirming the registration
        System.out.println("Registration successful! You can now log in.");
    }


    public static void userMenu(User user, Scanner scanner) {
        // Check if the user is null (not logged in), display an error message and exit the method
        if (user == null) {
            System.out.println("Unauthorized access! Please log in first.");
            return; // Exit the method if the user is not logged in
        }

        // Start an infinite loop to display the user menu repeatedly until the user chooses to exit
        while (true) {
            // Display the menu options to the user
            System.out.println("\n========================================");
            System.out.println("User Menu");
            System.out.println("========================================");
            System.out.println("1. Change Location"); // Option to change location
            System.out.println("2. Change Date"); // Option to change date
            System.out.println("3. View Tickets"); // Option to view tickets
            System.out.println("4. Exit"); // Option to exit the menu
            System.out.print("Enter your choice: ");

            // Capture the user's input choice
            String choice = scanner.nextLine();

            // Use a switch statement to handle the different menu choices
            switch (choice) {
                case "1":
                    // If the user chooses option 1, call the changeLocation method
                    changeLocation(user, scanner);
                    break;
                case "2":
                    // If the user chooses option 2, call the changeDate method
                    changeDate(user, scanner);
                    break;
                case "3":
                    // If the user chooses option 3, call the viewTickets method
                    viewTickets(user);
                    break;
                case "4":
                    // If the user chooses option 4, exit the menu
                    System.out.println("Exiting User Menu.");
                    return; // Exit the user menu
                default:
                    // If the user enters an invalid option, print an error message
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    // Method to change the user's location
    public static void changeLocation(User user, Scanner scanner) {
        // Prompt the user to enter a new location
        System.out.print("Enter the location to change: ");
        String locationToChange = scanner.nextLine(); // Capture the location input from the user

        // Update the user's location with the new location entered by the user
        user.setLocation(locationToChange);
        System.out.println("Location updated successfully."); // Inform the user that the location has been updated

        // Automatically display movies available in the updated location
        System.out.println("Movies available in the updated location:");
        displayAllMovies(user, LocalTime.now(), scanner); // Call a method to display all movies for the updated location
    }

    // Method to change the user's date
    public static void changeDate(User user, Scanner scanner) {
        // Prompt the user to enter a new date
        System.out.print("Enter the new date to change (format: yyyy-MM-dd): ");
        String dateString = scanner.nextLine(); // Capture the date input from the user

        // Validate the entered date to match the format yyyy-MM-dd
        if (dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
            // If the date is in the correct format, parse it into a LocalDate object
            LocalDate newDate = LocalDate.parse(dateString); // Parse the date string to a LocalDate object
            user.setDate(newDate); // Update the user's date with the newly parsed date
            System.out.println("Date updated successfully to: " + newDate); // Inform the user that the date has been updated

            // Automatically display movies available on the updated date
            System.out.println("Movies available on the updated date:");
            displayAllMovies(user, LocalTime.now(), scanner); // Call a method to display all movies for the updated date
        } else {
            // If the date format is incorrect, print an error message
            System.out.println("Invalid date format. Please enter the date in the correct format (yyyy-MM-dd).");
        }
    }

    // Method to display all movies available based on the user's location and date
    public static void displayAllMovies(User user, LocalTime today, Scanner scanner) {
        // HashSet to store movies that match the user's location and date
        HashSet<String> moviesInThatLocation = new HashSet<>();
        // ArrayList to store movies available for booking after location and date filtering
        ArrayList<Movies> moviesAvailable = new ArrayList<>();

        // Check if there are any movies in the directory
        if (BMS.getMovieDirectory().isEmpty()) {
            System.out.println("No movies found.");
            return;
        }

        System.out.println("Movies Available:");

        // Iterate over the movie directory to filter movies based on the user's location and date
        for (String movie : BMS.getMovieDirectory().keySet()) {
            // Get the list of movies for the current movie name
            var movieList = BMS.getMovieDirectory().get(movie);
            for (var movieObj : movieList) {
                // Print movie details to show the checking process
                System.out.println("Checking Movie: " + movieObj.getName() + " | Location: " + movieObj.getLocation() +
                        " | Date: " + movieObj.getStartDate());

                // Check if the movie's location and start date match the user's location and date
                if (movieObj.getLocation().equals(user.getLocation()) && movieObj.getStartDate().isEqual(user.getDate())) {
                    System.out.println("Matched Movie: " + movie);
                    moviesInThatLocation.add(movie);  // Add the movie to the list of available movies
                }
            }
        }

        // If no movies match the location and date, inform the user
        if (moviesInThatLocation.isEmpty()) {
            System.out.println("No movies available in that location or date.");
            return;
        }

        // Prompt the user to choose a movie for booking
        while (true) {
            System.out.print("Enter the movie name to book (or type 'N' to exit): ");
            String movieChoice = scanner.nextLine();  // Capture the user's movie choice

            // If the user enters 'N', exit the movie booking process
            if (movieChoice.equalsIgnoreCase("N")) {
                System.out.println("Thank you.");
                return;
            }

            // Check if the chosen movie is available for the selected location and date
            if (moviesInThatLocation.contains(movieChoice)) {
                // If valid, add the movie to the available movies list
                for (var movieObj : BMS.getMovieDirectory().get(movieChoice)) {
                    if (movieObj.getLocation().equals(user.getLocation()) && movieObj.getStartDate().isEqual(user.getDate())) {
                        moviesAvailable.add(movieObj);
                    }
                }
                // Show movie details and proceed with booking
                showMovieDetailsAndBook(moviesAvailable, movieChoice, user, scanner);
                break;  // Exit the loop after the booking process
            } else {
                // If the movie is not valid, prompt the user to try again
                System.out.println("Invalid movie name. Please try again.");
            }
        }
    }

    // Method to show movie details and handle the ticket booking process
    public static void showMovieDetailsAndBook(ArrayList<Movies> moviesAvailable, String movieChoice, User user, Scanner scanner) {
        // Ask the user if they want to book tickets for the selected movie
        System.out.println("\nWould you like to book tickets for " + movieChoice + "? (Y/N): ");
        String choice = scanner.nextLine();  // Capture the user's response

        // If the user wants to book, proceed with the booking process
        if (choice.equalsIgnoreCase("Y")) {
            // Iterate over the available movies and process the booking for each
            for (Movies movie : moviesAvailable) {
                System.out.println("Booking for show: " + movie.getShow());  // Show movie details for booking
                Theatre theatre = new Theatre("KG", "cbe");  // Create a new Theatre instance (assuming hardcoded data for this example)
                // Call the bookTickets method to handle the ticket booking
                bookTickets(movie.getShow(), user, theatre.getTheatreName(), movieChoice, movie.getStartDate(), scanner);
                return;  // Exit after the booking process is completed
            }
        } else {
            // If the user doesn't want to book, print a message and return to the movie menu
            System.out.println("Returning to the movie menu.");
        }
    }



    // Method to handle ticket booking for a movie show
    public static void bookTickets(Show show, User user, String theatreName, String movieChoice, LocalDate showDate, Scanner scanner) {
        // Display the screen name, available seats, and price for the show
        System.out.println("Screen Name: " + show.getScreen().getName());
        System.out.println("Available seats: " + show.getScreen().getNumberOfSeats());
        System.out.println("Price: " + show.getPrice());

        // Initialize seat arrangement if it hasn't been done already
        // This step sets up the seat grid for booking
        if (seatArrangement.isEmpty()) {
            String gridPattern = "5*5*5"; // Example grid pattern, could be customized further
            long numberOfSeats = show.getScreen().getNumberOfSeats();
            // Generate the seat grid using a utility method (assuming Utilities.generateGrid exists)
            seatArrangement = Utilities.generateGrid(numberOfSeats, gridPattern);
        }

        // Print the current seat arrangement (showing available and booked seats)
        System.out.println("Seat Grid:");
        for (var seats : seatArrangement.entrySet()) {
            System.out.println(seats.getKey() + " " + seats.getValue());
        }

        // Ask the user how many seats they want to book
        System.out.print("Enter number of seats to book: ");
        long seatCount = Long.parseLong(scanner.nextLine());  // Parse the seat count from user input

        // Check if the requested number of seats exceeds the available seats
        if (show.getScreen().getNumberOfSeats() < seatCount) {
            System.out.println("Insufficient seats.");
            System.out.println("Available seats: " + show.getScreen().getNumberOfSeats());
            return;
        }

        // List to keep track of selected seats during the booking process
        ArrayList<String> selectedSeats = new ArrayList<>();

        // Loop to allow the user to select seats one by one
        while (seatCount > 0) {
            // Prompt the user to enter a seat (e.g., A1, B2)
            System.out.print("Enter the seat to book (e.g., A1): ");
            String choiceSeat = scanner.nextLine();
            char row = choiceSeat.charAt(0);  // Extract row letter (A, B, C, etc.)
            int seatNumber = Integer.parseInt(choiceSeat.substring(1)) - 1;  // Convert seat number to zero-based index

            // Check if the seat is available (marked as "_")
            if (seatArrangement.get(row).get(seatNumber).equals("_")) {
                // If the seat is available, mark it as booked ("X") and add it to the selectedSeats list
                seatArrangement.get(row).set(seatNumber, "X");
                selectedSeats.add(choiceSeat);  // Add the selected seat to the list
                seatCount--;  // Decrement the remaining seats to book
                System.out.println("Seat selected: " + choiceSeat);
            } else {
                // If the seat is already booked, prompt the user to choose another seat
                System.out.println("Seat already booked. Choose another seat.");
            }
        }

        // Display the ticket details for confirmation before finalizing the booking
        System.out.println("\n==================== Ticket Confirmation ====================");
        System.out.println("Theatre: " + theatreName);
        System.out.println("Movie: " + movieChoice);
        System.out.println("Screen: " + show.getScreen().getName());
        System.out.println("Location: " + user.getLocation());
        System.out.println("Show Date: " + showDate);
        System.out.println("Price per Seat: $" + show.getPrice());
        System.out.println("Number of Seats: " + selectedSeats.size());
        System.out.println("Total Price: $" + (selectedSeats.size() * show.getPrice()));
        System.out.println("Selected Seats: " + selectedSeats);
        System.out.println("============================================================");

        // Prompt the user to confirm or cancel the booking
        System.out.print("Confirm booking? (Y/N): ");
        String confirmation = scanner.nextLine().toUpperCase();  // Capture confirmation input from user

        // If the user confirms, proceed with booking
        if (confirmation.equals("Y")) {
            // Create a new ticket object with the booking details
            Tickets newTicket = new Tickets(
                    theatreName,
                    movieChoice,
                    show.getScreen().getName(),
                    user.getLocation(),
                    showDate.toString(),
                    show.getPrice(),
                    selectedSeats
            );

            // Add the booked seats to the ticket (mark them as booked)
            for (String seat : selectedSeats) {
                newTicket.bookSeat(seat);  // Assuming bookSeat method marks a seat as booked in the ticket system
            }

            // Add the new ticket to the user's list of tickets
            user.getTickets().add(newTicket);

            // Confirm the booking to the user
            System.out.println("Booking confirmed!");
            System.out.println("Seats booked: " + selectedSeats);
        } else {
            // If the user cancels the booking, release the selected seats
            for (String seat : selectedSeats) {
                char row = seat.charAt(0);  // Extract the row from the seat string
                int seatNumber = Integer.parseInt(seat.substring(1)) - 1;  // Extract seat number and adjust for zero-based index
                // Mark the seat as available again ("_")
                seatArrangement.get(row).set(seatNumber, "_");
            }

            // Inform the user that the booking was canceled
            System.out.println("Booking canceled. Seats released.");
        }

        // Print the updated seat grid after the booking process
        System.out.println("Updated Seat Grid:");
        for (var seats : seatArrangement.entrySet()) {
            System.out.println(seats.getKey() + " " + seats.getValue());  // Display the updated seat arrangement
        }
    }



    // Method to display the user's booked tickets
    public static void viewTickets(User user) {
        // Check if the user has no tickets booked
        if (user.getTickets().isEmpty()) {
            System.out.println("No tickets booked.");  // Display a message if no tickets are found
        } else {
            // If the user has booked tickets, display the ticket details
            System.out.println("Your Tickets:");

            // Loop through each ticket in the user's ticket list
            for (Tickets ticket : user.getTickets()) {
                // Calculate the total number of seats booked for this ticket
                int numberOfSeats = ticket.getBookedTickets().size(); // Get the size of the booked seats list

                // Calculate the total price based on the number of seats and the price per seat
                double totalPrice = ticket.getPrice() * numberOfSeats;

                // Display the ticket details for each booking
                System.out.println("========================================");
                System.out.println("Theatre: " + ticket.getTheatreName()); // Display the theatre name
                System.out.println("Movie: " + ticket.getMovieName()); // Display the movie name
                System.out.println("Screen: " + ticket.getScreen()); // Display the screen name
                System.out.println("Location: " + ticket.getLocation()); // Display the location of the show
                System.out.println("Show Date: " + ticket.getShowTimeStart()); // Display the show date and time
                System.out.println("Price per Seat: $" + ticket.getPrice()); // Display the price per seat
                System.out.println("Number of Seats: " + numberOfSeats); // Display the number of seats booked
                System.out.println("Total Price: $" + totalPrice); // Display the calculated total price
                System.out.println("Booked Seats: " + ticket.getBookedTickets()); // Display the list of booked seat numbers
                System.out.println("========================================");
            }
        }
    }
}












