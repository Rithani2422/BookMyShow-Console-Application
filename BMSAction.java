package BMSPROJECT;

import java.util.Scanner;

public class BMSAction {

    // Method to start the BMS application and display the main menu
    public static void start() {
        Scanner scanner = new Scanner(System.in);

        // Adding a default admin to the system
        BMS.getAdminsList().add(new Admin("admin1", "password123"));

        // Infinite loop to keep displaying the main menu until the user exits
        while (true) {
            // Display the main menu
            System.out.println("Welcome");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Register User");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            // Get the user's choice from the menu
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            // Switch based on user's choice
            switch (choice) {
                case 1:
                    // Admin login
                    Admin loggedInAdmin = AdminAction.adminLogin(scanner);
                    if (loggedInAdmin != null) {
                        // If admin login is successful, display the admin menu
                        AdminAction.adminMenu(loggedInAdmin, scanner);
                    } else {
                        // If login fails, show an error message
                        System.out.println("Invalid credentials. Please try again.");
                    }
                    break;

                case 2:
                    // User login
                    User loggedInUser = UserAction.userLogin(scanner);
                    if (loggedInUser != null) {
                        // If user login is successful, show a welcome message and display the user menu
                        System.out.println("Login successful! Welcome, " + loggedInUser.getUsername() + "!");
                        UserAction.userMenu(loggedInUser, scanner);
                    } else {
                        // If login fails or user is not registered, prompt user to register
                        System.out.println("Invalid credentials or user not registered. Please register first.");
                    }
                    break;

                case 3:
                    // Register a new user
                    UserAction.registerUser(scanner);
                    break;

                case 4:
                    // Exit the system
                    System.out.println("Exiting the system.");
                    return;  // Exit the method and terminate the program

                default:
                    // Handle invalid menu choices
                    System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        }
    }
}
