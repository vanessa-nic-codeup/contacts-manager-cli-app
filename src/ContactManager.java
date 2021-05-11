import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class ContactManager {
    public static Scanner sc = new Scanner(System.in);
    private String name;
    private String number;

    public ContactManager(String name, String number) {
        setName(name);
        setPhoneNumber(number);
    }

    // getters
    public static String getName(ContactManager ele) {
        return ele.name;
    }
    public static String getCategory(ContactManager ele) {
        return ele.number;
    }

    //changes movie info, setters
    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String number) {
        this.number = number;
    }

    public static void main(String[] args) throws IOException {
        String directory = "data";
        String filename = "contacts.txt";

        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);

        if (Files.notExists(dataDirectory)) {
            Files.createDirectories(dataDirectory);
        }

        if (! Files.exists(dataFile)) {
            Files.createFile(dataFile);
        }


        boolean wrongInput = true;

        System.out.print("""
                1. View contacts.
                2. Add a new contact.
                3. Search a contact by name.
                4. Delete an existing contact.
                5. Exit.
                Please enter an option (1, 2, 3, 4 or 5):
                 """);

        while (wrongInput) {

            int userInput = sc.nextInt();

            if (userInput < 1 || userInput > 5) {

                System.out.println("Invalid input, please try again: ");

            } else if (userInput == 1) {

                System.out.println("Name | Phone Number |");
                System.out.println("-----------------------");
                printContacts(dataFile); //calling the method below
                wrongInput = false;

            } else if (userInput == 2) {

                boolean answer = true;
                while (answer) {

                    System.out.println("Please enter a new contact's first and last name:");
                    String firstName = sc.next(); //this catches the first input
                    String lastName = sc.next(); // catches the second input
                    System.out.println("Please enter contact's phone number:");
                    String contactNumber = sc.next();

                    String contactName = firstName + " " + lastName;

                    Files.write(
                            Paths.get("data", "contacts.txt"),
                            Arrays.asList(contactName + " | " + contactNumber + " | "),
                            StandardOpenOption.APPEND
                    );

                    System.out.println("Would you like to enter another contact? [y/N]");
                    String response = sc.next();

                    answer = response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes");
                }

                wrongInput = false;

            } else if (userInput == 3) {

                searchContacts(dataFile);
                wrongInput = false;

            } else if (userInput == 4) {

                System.out.println("delete contacts");
                wrongInput = false;

            } else if (userInput == 5) {

                System.out.println("exit program");
                wrongInput = false;

            }
        }
    }

    //using IOException instead try/catch method:
    public static void printContacts(Path filePath) throws IOException {

        System.out.println();
        List<String> fileContents = Files.readAllLines(filePath);
        for (int i = 0; i < fileContents.size(); i++) {
            System.out.printf("%d: %s\n", i + 1, fileContents.get(i));
        }


    }

    public static void searchContacts(Path filePath) throws IOException {
        List<String> fileContents = Files.readAllLines(filePath);

        System.out.println("Please enter name you would like to search:");
        String contactName = sc.next();

        for (int i = 0; i < fileContents.size(); i++) {
            if (fileContents.get(i).contains(contactName)) {
                System.out.printf("%d: %s\n", i + 1, fileContents.get(i));
            }
        }
    }
}
