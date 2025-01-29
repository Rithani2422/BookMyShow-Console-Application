package BMSPROJECT;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Utilities {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm"); // Formatter for time (HH:mm)
    private static final LocalDate TODAY = LocalDate.now(); // Current date

    // Getters for formatters and current date

    // Returns the time formatter
    public static DateTimeFormatter getTimeFormatter() {
        return TIME_FORMATTER;
    }

    // Returns today's date
    public static LocalDate getToday() {
        return TODAY;
    }

    // Returns a date formatter (yyyy-MM-dd)
    public static DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public static HashMap<Character, ArrayList<String>> generateGrid(long numberofseats, String grid) {
        // Split the grid pattern by '*' to separate the seat groups
        var starremoved = grid.split("\\*");
        long sum = 0;

        // Calculate the total size of the groups in the grid
        for (String a : starremoved) {
            long temp = Long.parseLong(a);
            sum += temp; // Add the group size to the total sum
        }

        // Check if the number of seats is divisible by the total group size
        if (numberofseats % sum == 0) {
            var hashmap = new HashMap<Character, ArrayList<String>>(); // Create a map to store row labels and seat configurations
            char chara = 'A'; // Row labels start from 'A'

            while (numberofseats > 0) {
                ArrayList<String> row = new ArrayList<>(); // List to store seats for the current row

                // For each group in the pattern
                for (int i = 0; i < starremoved.length; i++) {
                    long groupSize = Long.parseLong(starremoved[i]); // Get the group size

                    // Add the seats to the row for the current group
                    for (int j = 0; j < groupSize; j++) {
                        row.add("_"); // Add a placeholder for the seat (can later be replaced with actual seat labels)
                    }

                    // Add space between groups if not the last group
                    if (i < starremoved.length - 1) {
                        row.add("<SPACE>"); // Add space to separate groups
                    }
                }

                // Add the row to the hashmap with the current row label (e.g., A, B, C...)
                hashmap.put(chara, row);
                chara++; // Increment the row label to the next letter

                // Decrease the number of seats remaining
                numberofseats -= sum; // Reduce the number of seats by the total size of groups
            }

            // Return the generated seat grid
            return hashmap;
        }

        // If the number of seats isn't valid, print an error and return null
        System.out.println("Enter the correct number of seats.");
        return null;
    }
}
