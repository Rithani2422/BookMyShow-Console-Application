package BMSPROJECT;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Utilities {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final LocalDate TODAY = LocalDate.now();

    // Getters for formatters and current date
    public static DateTimeFormatter getTimeFormatter() {
        return TIME_FORMATTER;
    }

    public static LocalDate getToday() {
        return TODAY;
    }

    public static DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }


    public static HashMap<Character, ArrayList<String>> generateGrid(long numberofseats, String grid) { // Method to generate the seat pattern
        var starremoved = grid.split("\\*"); // Separate the '*' (space between groups)
        long sum = 0;

        // Get the sum of the group sizes in the grid
        for (String a : starremoved) {
            long temp = Long.parseLong(a);
            sum += temp;
        }

        // Check if the number of seats is divisible by the total group size
        if (numberofseats % sum == 0) {
            var hashmap = new HashMap<Character, ArrayList<String>>(); // Map to store row and seats
            char chara = 'A'; // Row label starts from 'A'

            while (numberofseats > 0) {
                ArrayList<String> row = new ArrayList<>(); // List to store seats of the current row

                // For each group in the pattern
                for (int i = 0; i < starremoved.length; i++) {
                    long groupSize = Long.parseLong(starremoved[i]);

                    // Add the seats to the row for the current group
                    for (int j = 0; j < groupSize; j++) {
                        row.add("_"); // Placeholder for seat (could be replaced with actual labels like A1, A2, etc.)
                    }

                    // Add space between groups if not the last group
                    if (i < starremoved.length - 1) {
                        row.add("<SPACE>"); // Space between groups
                    }
                }

                // Add the row to the hashmap with the current row label
                hashmap.put(chara, row);
                chara++; // Move to the next row

                // Decrease the number of seats remaining
                numberofseats -= sum; // Reduce the total seats by the sum of group sizes
            }

            // Return the final seat grid map
            return hashmap;
        }

        // If the number of seats isn't valid, return null and print error
        System.out.println("Enter the correct number of seats.");
        return null;
    }
}
