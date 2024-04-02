package meditracker.ui;

import meditracker.dailymedication.DailyMedication;
import meditracker.library.SearchResult;
import meditracker.medication.Medication;

import java.util.List;
import java.util.Scanner;

/**
 * The Ui class handles user interface-related operations.
 * It includes methods to display welcome messages, exit messages, and read user commands.
 */
public class Ui {
    static Scanner input = new Scanner(System.in);

    /**
     * Prevents defaulting to the public constructor
     * that allows instantiation of the Ui class
     */
    private Ui() {}

    /**
     * Displays the welcome message and introduction name.
     */
    public static void showWelcomeMessage() {
        printIntroName();
        showWelcome();
    }

    /**
     * Prints the introduction name banner.
     */
    public static void printIntroName() {

        // Solution below adapted by http://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type%20Something%20
        System.out.println("                    __      ______                      __                      \n" +
                " /'\\_/`\\           /\\ \\  __/\\__  _\\                    /\\ \\                     \n" +
                "/\\      \\     __   \\_\\ \\/\\_\\/_/\\ \\/ _ __    __      ___\\ \\ \\/'\\      __   _ __  \n" +
                "\\ \\ \\__\\ \\  /'__`\\ /'_` \\/\\ \\ \\ \\ \\/\\`'__\\/'__`\\   /'___\\ \\ , <    /'__`\\/\\`'__\\\n"
                +
                " \\ \\ \\_/\\ \\/\\  __//\\ \\L\\ \\ \\ \\ \\ \\ \\ \\ \\//\\ \\L\\.\\_/\\ \\__/\\ \\ \\\\`\\ /\\" +
                "  __/\\" +
                " \\ \\/ \n" +
                "  \\ \\_\\\\ \\_\\ \\____\\ \\___,_\\ \\_\\ \\ \\_\\ \\_\\\\ \\__/.\\_\\ \\____\\\\ \\_\\ \\_\\ " +
                "\\____\\" +
                "\\ \\_\\ \n" +
                "   \\/_/ \\/_/\\/____/\\/__,_ /\\/_/  \\/_/\\/_/ \\/__/\\/_/\\/____/ \\/_/\\/_/\\/____/ \\/_/ \n" +
                "                                                                                \n" +
                "                                                                                ");
    }

    /**
     * Displays a line divider.
     */
    public static void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays the welcome message.
     */
    public static void showWelcome() {
        System.out.println("Welcome to MediTracker, your best companion to track your medicine intake.");
        System.out.println("Let's begin tracking!\n");
    }

    /**
     * Displays the exit message.
     */
    public static void showExitMessage() {
        System.out.println("Thank you for using MediTracker. Hope to see you again!");
    }

    public static void showSuccessMessage(String message) {
        System.out.print("SUCCESS: ");
        System.out.println(message);
    }

    public static void showErrorMessage(String message) {
        System.out.print("ERROR: ");
        System.out.println(message);
    }

    /**
     * Reads user input command.
     * @return The user input command as a String.
     */
    public static String readCommand() {
        return input.nextLine();
    }


    /**
     * Prints the medication list
     *
     * @param medications The list of medications
     * @param <T> Generic class for code reusability
     */
    public static <T> void printMedsList(List<T> medications) {
        for (T medication : medications) {
            int numbering = medications.indexOf(medication) + 1;
            System.out.println("\t" + numbering + ". " + medication);
        }
    }

    /**
     * Prints the sub lists of dailyMedications based on the period
     *
     * @param medsList list of medications from MedicationManager
     * @param dailyMedications subList of dailyMedication
     * @param period Specified period of the day
     */
    public static void printMedsLists(List<Medication> medsList,
                                      List<DailyMedication> dailyMedications, String period) {
        int numbering = 0;
        for (DailyMedication med: dailyMedications) {
            String name = med.getName();
            Double intakeDose;
            int index = getIndex(medsList, name);
            if (period.equals("Morning:")) {
                intakeDose = medsList.get(index).getDosageMorning();
            } else if (period.equals("Afternoon:")) {
                intakeDose = medsList.get(index).getDosageAfternoon();
            } else {
                intakeDose = medsList.get(index).getDosageEvening();
            }
            numbering++;
            System.out.println("\t" + numbering + ". " + med + " | " + intakeDose);
        }
    }

    /**
     * Gets index of the dailyMedication in the Main Medication list
     *
     * @param medsList list of medications from MedicationManager
     * @param name name of DailyMedication
     * @return index of the medication in main medication
     */
    private static int getIndex(List<Medication> medsList, String name) {
        int index = 0;
        for (Medication medication: medsList) {
            if (medication.getName().equals(name)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static void showLibraryNotFoundMessage() {
        System.out.println("Library not found! Please download the library from the website.");
    }

    public static void showNoSearchResultsMessage() {
        System.out.println("No search results found!");
    }

    public static void showSearchResults(List<SearchResult> searchResults) {
        System.out.println("Here are the search results:");

        for (int i = 0; i < searchResults.size(); i++) {
            System.out.println((i + 1) + ". " + searchResults.get(i));
        }
    }
}
