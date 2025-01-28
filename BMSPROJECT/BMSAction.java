package BMSPROJECT;

import java.util.Scanner;

public class BMSAction {
    public static void start() {
        Scanner scanner = new Scanner(System.in);

        BMS.getAdminsList().add(new Admin("admin1", "password123"));


        while (true) {
            System.out.println("Welcome");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Register User");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Admin loggedInAdmin = AdminAction.adminLogin(scanner);
                    if (loggedInAdmin != null) {
                        AdminAction.adminMenu(loggedInAdmin, scanner); // Redirect to admin menu
                    } else {
                        System.out.println("Invalid credentials. Please try again.");
                    }
                    break;

                case 2:
                    User loggedInUser = UserAction.userLogin(scanner);
                    if (loggedInUser != null) {
                        System.out.println("Login successful! Welcome, " + loggedInUser.getUsername() + "!");
                        UserAction.userMenu(loggedInUser, scanner);
                    } else {
                        System.out.println("Invalid credentials or user not registered. Please register first.");
                    }
                    break;

                case 3:

                    UserAction.registerUser(scanner);
                    break;

                case 4:

                    System.out.println("Exiting the system.");
                    return;

                default:
                    System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        }
    }

}