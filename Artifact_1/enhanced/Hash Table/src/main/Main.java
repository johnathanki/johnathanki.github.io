package main;

import main.manager.HashTableManager;
import main.model.Course;

import java.util.Scanner;

/**
 * Entry point for the application.
 * <p>
 * This class provides a console based menu for loading, printing, and searching courses.
 *
 * @author John Kirven
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashTableManager tableManager = new HashTableManager();

        int choice = 0;
        String name;

        // Main menu loop
        while (choice != 9) {
            System.out.println("Menu:");
            System.out.println("1. Load course information.");
            System.out.println("2. Print course list.");
            System.out.println("3. Search for course.");
            System.out.println("9. Exit program.");

            choice = getMenuChoice(scanner); // TODO: Validate input
            switch (choice) {
                case 1: // Load data
                    tableManager.loadCourseData();
                    if (!tableManager.validatePrereqs()) {
                        System.err.println("Error with course data, exiting.");
                        choice = 9; // Exit on failure
                        break;
                    }

                    break;
                case 2: // Print data
                    tableManager.printCourseList();
                    break;
                case 3: // Search data
                    name = getValidCourseName(scanner); // TODO: Validate input
                    Course course = tableManager.search(name);
                    if (course != null) {
                        course.printCourseInfo();
                    } else {
                        System.err.println("Course \"" + name + "\" not found.");
                    }
                    break;
                case 9: // Exit
                    System.out.println("Goodbye!");
                    break;

                default: // Invalid input
                    System.out.println(choice + " is not a valid menu option.");
                    break;
            }
        }
    }

    /**
     * Prompts the user for a course code and validates the input.
     * A valid course code consists of 4 letters followed by 3 digits (e.g., CSCI101).
     *
     * @param scanner the Scanner to read user input
     * @return a validated course code string
     */
    private static String getValidCourseName(Scanner scanner) {
        String input;
        while (true) {
            System.out.print("Please enter a course as ABCD123: ");
            input = scanner.nextLine().trim();

            if (input.matches("^[A-Za-z]{4}\\d{3}$")) {
                return input.toUpperCase(); // Return in consistent format
            } else {
                System.out.println("Invalid course format. Please use 4 letters followed by 3 digits (e.g., CSCI101).");
            }
        }
    }

    /**
     * Prompts the user for an integer menu action and validates the input.
     * 'Valid' input is any integer.
     * @param scanner the Scanner to read user input
     * @return a validated menu action
     */
    private static int getMenuChoice(Scanner scanner) {
        int choice = -1;
        while (true) {
            System.out.print("Enter input: ");
            String input = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("\"" + input + "\" is not a valid integer.");
            }
        }
    }
}
