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
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (User user : BMS.getUsersList()) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    return user;
                } else {
                    System.out.println("Login failed! Invalid password.");
                    return null;
                }
            }
        }
        System.out.println("Login failed! Invalid username.");
        return null;
    }

    public static void registerUser(Scanner scanner) {
        System.out.print("Enter a username to register: ");
        String username = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        for (User user : BMS.getUsersList()) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists. Please try a different username.");
                return;
            }
        }

        BMS.getUsersList().add(new User(username, password, " "));
        System.out.println("Registration successful! You can now log in.");
    }

    public static void userMenu(User user, Scanner scanner) {
        if (user == null) {
            System.out.println("Unauthorized access! Please log in first.");
            return;
        }

        while (true) {
            System.out.println("\n========================================");
            System.out.println("User Menu");
            System.out.println("========================================");
            System.out.println("1. Change Location");
            System.out.println("2. Change Date");
            System.out.println("3. View Tickets");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    changeLocation(user, scanner);
                    break;
                case "2":
                    changeDate(user, scanner);
                    break;
                case "3":
                    viewTickets(user);
                    break;
                case "4":
                    System.out.println("Exiting User Menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void changeLocation(User user, Scanner scanner) {
        System.out.print("Enter the location to change: ");
        String locationToChange = scanner.nextLine();
        user.setLocation(locationToChange);
        System.out.println("Location updated successfully.");

        // Automatically display movies for the updated location
        System.out.println("Movies available in the updated location:");
        displayAllMovies(user, LocalTime.now(), scanner);
    }

    public static void changeDate(User user, Scanner scanner) {
        System.out.print("Enter the new date to change (format: yyyy-MM-dd): ");
        String dateString = scanner.nextLine();

        // Check if the date matches the expected format
        if (dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
            LocalDate newDate = LocalDate.parse(dateString); // Parse without custom formatter
            user.setDate(newDate); // Assuming `User` has a `setDate()` method
            System.out.println("Date updated successfully to: " + newDate);

            // Automatically display movies for the updated date
            System.out.println("Movies available on the updated date:");
            displayAllMovies(user, LocalTime.now(), scanner);
        } else {
            System.out.println("Invalid date format. Please enter the date in the correct format (yyyy-MM-dd).");
        }
    }

    public static void displayAllMovies(User user, LocalTime today, Scanner scanner) {
        HashSet<String> moviesInThatLocation = new HashSet<>();
        ArrayList<Movies> moviesAvailable = new ArrayList<>();

        if (BMS.getMovieDirectory().isEmpty()) {
            System.out.println("No movies found.");
            return;
        }

        System.out.println("Movies Available:");

        // Filter movies based on the user's current location and date
        for (String movie : BMS.getMovieDirectory().keySet()) {
            var movieList = BMS.getMovieDirectory().get(movie);
            for (var movieObj : movieList) {
                System.out.println("Checking Movie: " + movieObj.getName() + " | Location: " + movieObj.getLocation() +
                        " | Date: " + movieObj.getStartDate());

                // Match by user's location and the updated date
                if (movieObj.getLocation().equals(user.getLocation()) && movieObj.getStartDate().isEqual(user.getDate())) {
                    System.out.println("Matched Movie: " + movie);
                    moviesInThatLocation.add(movie);
                }
            }
        }

        if (moviesInThatLocation.isEmpty()) {
            System.out.println("No movies available in that location or date.");
            return;
        }

        while (true) {
            System.out.print("Enter the movie name to book (or type 'N' to exit): ");
            String movieChoice = scanner.nextLine();

            if (movieChoice.equalsIgnoreCase("N")) {
                System.out.println("Thank you.");
                return;
            }

            if (moviesInThatLocation.contains(movieChoice)) {
                for (var movieObj : BMS.getMovieDirectory().get(movieChoice)) {
                    if (movieObj.getLocation().equals(user.getLocation()) && movieObj.getStartDate().isEqual(user.getDate())) {
                        moviesAvailable.add(movieObj);
                    }
                }
                showMovieDetailsAndBook(moviesAvailable, movieChoice, user, scanner);
                break;
            } else {
                System.out.println("Invalid movie name. Please try again.");
            }
        }
    }

    public static void showMovieDetailsAndBook(ArrayList<Movies> moviesAvailable, String movieChoice, User user, Scanner scanner) {
        // No need to display movie details, directly ask if the user wants to book tickets
        System.out.println("\nWould you like to book tickets for " + movieChoice + "? (Y/N): ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("Y")) {
            for (Movies movie : moviesAvailable) {
                System.out.println("Booking for show: " + movie.getShow());
                Theatre theatre = new Theatre("KG", "cbe"); // Creating an instance of Theatre
                bookTickets(movie.getShow(), user, theatre.getTheatreName(), movieChoice, movie.getStartDate(), scanner);
                return;  // Exit after booking
            }
        } else {
            System.out.println("Returning to the movie menu.");
        }
    }
// Assuming seatArrangement is stored globally or passed between functions
// For this solution, let's assume we have a global static HashMap to store the seat grid



    public static void bookTickets(Show show, User user, String theatreName, String movieChoice, LocalDate showDate, Scanner scanner) {
        System.out.println("Screen Name: " + show.getScreen().getName());
        System.out.println("Available seats: " + show.getScreen().getNumberOfSeats());
        System.out.println("Price: " + show.getPrice());

        // Initialize seat arrangement if not already done
        if (seatArrangement.isEmpty()) {
            String gridPattern = "5*5*5"; // Example grid pattern
            long numberOfSeats = show.getScreen().getNumberOfSeats();
            seatArrangement = Utilities.generateGrid(numberOfSeats, gridPattern);
        }

        // Print the generated seat arrangement
        System.out.println("Seat Grid:");
        for (var seats : seatArrangement.entrySet()) {
            System.out.println(seats.getKey() + " " + seats.getValue());
        }

        System.out.print("Enter number of seats to book: ");
        long seatCount = Long.parseLong(scanner.nextLine());

        if (show.getScreen().getNumberOfSeats() < seatCount) {
            System.out.println("Insufficient seats.");
            System.out.println("Available seats: " + show.getScreen().getNumberOfSeats());
            return;
        }

        // List to track selected seats
        ArrayList<String> selectedSeats = new ArrayList<>();

        while (seatCount > 0) {
            System.out.print("Enter the seat to book (e.g., A1): ");
            String choiceSeat = scanner.nextLine();
            char row = choiceSeat.charAt(0);
            int seatNumber = Integer.parseInt(choiceSeat.substring(1)) - 1;

            // Check if the seat is available
            if (seatArrangement.get(row).get(seatNumber).equals("_")) {
                seatArrangement.get(row).set(seatNumber, "X"); // Mark the seat as booked with "X"
                selectedSeats.add(choiceSeat); // Add seat to selectedSeats list
                seatCount--;
                System.out.println("Seat selected: " + choiceSeat);
            } else {
                System.out.println("Seat already booked. Choose another seat.");
            }
        }

        // Show ticket details for confirmation
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

        // Prompt user for confirmation
        System.out.print("Confirm booking? (Y/N): ");
        String confirmation = scanner.nextLine().toUpperCase();

        if (confirmation.equals("Y")) {
            // Finalize the booking
            Tickets newTicket = new Tickets(
                    theatreName,
                    movieChoice,
                    show.getScreen().getName(),
                    user.getLocation(),
                    showDate.toString(),
                    show.getPrice(),
                    selectedSeats
            );

            // Add booked seats to the ticket
            for (String seat : selectedSeats) {
                newTicket.bookSeat(seat);
            }

            // Add the ticket to the user's list
            user.getTickets().add(newTicket);

            System.out.println("Booking confirmed!");
            System.out.println("Seats booked: " + selectedSeats);
        } else {
            // Release the selected seats
            for (String seat : selectedSeats) {
                char row = seat.charAt(0);
                int seatNumber = Integer.parseInt(seat.substring(1)) - 1;
                seatArrangement.get(row).set(seatNumber, "_"); // Mark the seat as available again
            }

            System.out.println("Booking canceled. Seats released.");
        }

        // Print updated seat grid
        System.out.println("Updated Seat Grid:");
        for (var seats : seatArrangement.entrySet()) {
            System.out.println(seats.getKey() + " " + seats.getValue());
        }
    }




    public static void viewTickets(User user) {
        if (user.getTickets().isEmpty()) {
            System.out.println("No tickets booked.");
        } else {
            System.out.println("Your Tickets:");
            for (Tickets ticket : user.getTickets()) {
                int numberOfSeats = ticket.getBookedTickets().size(); // Get the total number of seats booked
                double totalPrice = ticket.getPrice() * numberOfSeats; // Calculate the total price based on seat count

                System.out.println("========================================");
                System.out.println("Theatre: " + ticket.getTheatreName());
                System.out.println("Movie: " + ticket.getMovieName());
                System.out.println("Screen: " + ticket.getScreen());
                System.out.println("Location: " + ticket.getLocation());
                System.out.println("Show Date: " + ticket.getShowTimeStart());
                System.out.println("Price per Seat: $" + ticket.getPrice());
                System.out.println("Number of Seats: " + numberOfSeats);
                System.out.println("Total Price: $" + totalPrice); // Display the calculated total price
                System.out.println("Booked Seats: " + ticket.getBookedTickets()); // Show booked seat numbers
                System.out.println("========================================");
            }
        }
    }



}












