# BookMyShow - Java Console Application

BookMyShow is a terminal-based movie ticket booking system developed in Java. It allows users to browse available movies, select showtimes, and book or cancel tickets. The application includes a **secure user login system** to protect user credentials.

---

## **Class Diagram**

<img width="351" alt="Screenshot 2025-01-28 205427" src="https://github.com/user-attachments/assets/aeca5f5a-77df-445f-a8b5-499ce1cb4e92" />

## **Execution Video**

https://github.com/user-attachments/assets/10850b08-20cc-405c-bf81-24c3b63be3a0


## Features

## **User**
1. **User Login System**:  
   - Users must log in with their credentials to access the movie booking system. Passwords are stored securely.

2. **View List of Movies**:  
   - Users can browse through a list of movies currently showing in theaters.

3. **Book Tickets**:  
   - After logging in, users can select a movie and showtime to book tickets.

4. **Cancel Bookings**:  
   - Users can view their booked tickets and cancel them if needed.

5. **Manage User Data**:  
   - The application stores user login information securely and manages bookings and cancellations efficiently.

6. **Show Available Showtimes**:  
   - For each movie, users can see the available time slots and available seats before making a ticket booking.


## **Admin**

- Add, update users,Theatres, movies and shows.

- View booking statistics.

---

## Technologies Used

1. **Java**:  
   - The primary programming language used to build this application.

2. **Object-Oriented Programming (OOP) Principles**:  
   - **Encapsulation**: Internal data is protected and can only be modified through controlled methods.(provides security) 
   - **Inheritance**: Class hierarchies share common functionalities to avoid code duplication.  
   - **Polymorphism**: Enables dynamic decision-making within the application, making it more flexible and modular.

3. **Collections Framework**:  
   - Manages lists of movies, showtimes, and bookings for efficient access and modification.

4. **Exception Handling**:  
   - Gracefully handles errors such as invalid inputs, incorrect logins, or issues with data persistence.
---

## **Prerequisites**

- Java JDK 11 or later
- Text Editor or IDE (e.g., IntelliJ IDEA, Eclipse)
- Git

---

## Installation

To get started with this project, follow these steps:

### Step 1: Clone the Repository

Clone the GitHub repository to your local machine using the following command:

```bash
git clone https://github.com/BubalanShanmugam/BookMyShow.git
```
**step 2: Compile the application:**
```bash
javac BMS.java
```
**step 3:  Run the application:**
```bash   
java BMS
```

# **Usage Instructions**

### **User Registration**:
- On first use, users will be prompted to create a new account. Choose a secure password.

### **Login**:
- Enter your username and password to access the system. If the credentials are correct, you will be granted access to the movie booking system.

### **Booking and Cancelling Tickets**:
- Once logged in, you can select a movie and showtime, then proceed to book or cancel tickets. All changes are saved to your user profile.

### **Data Persistence**:
- Your bookings and cancellations, along with your login credentials, are stored and automatically loaded during future sessions.

## **Contact**

For any queries or feedback, contact to:

- **Developer**: Rithani L R 
- **Email**: rithanirajkumar2@gmail.com
- **LinkedIn**: https://www.linkedin.com/in/rithani-rajkumar-a53356282/






