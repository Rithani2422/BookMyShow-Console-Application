package BMSPROJECT;  // Declares the package name

public class Admin {  // Defines a public class named 'Admin'
    private String UserName;  // Private variable to store the admin's username
    private String Password;  // Private variable to store the admin's password

    // Constructor to initialize the Admin object with a username and password
    public Admin(String username, String password) {
        this.UserName = username;  // Assigns the provided username to the instance variable
        this.Password = password;  // Assigns the provided password to the instance variable
    }

    // Getter method to retrieve the username
    public String getUsername() {
        return UserName;
    }

    // Getter method to retrieve the password
    public String getPassword() {
        return Password;
    }
}
