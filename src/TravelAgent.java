import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class TravelAgent {
    static Scanner sc = new Scanner(System.in);
    static String input;
    private static String message = "Darbs tiek saglabāts un sistēma tiek izslēgta...";

    public static void chooseAction() throws IOException {
        System.out.println("Izvēlieties darbību: 1 - Pievienot klientu; 2 - Apstrādāt pieteikumus; 3 - Skatīt sūdzības; 4 - Izveidot datubāzi; 5 - Iziet");
        input = sc.nextLine();

        switch (input) {
            case "1":
                createCustomer();
                break;
            case "2":
                checkBookings();
                break;
            case "3":
                checkComplaints();
                break;
            case "4":
                createCustomerDatabase();
                break;
            case "5":
                CommonMethods.leave(message);
            default:
                System.out.println("Kļūdaina darbība!");
                chooseAction();
        }
        sc.close();
    }

    private static void checkComplaints() throws IOException {
        System.out.print("Sūdzību datubāzē ir " + countLines(CommonMethods.complaintDb) + " ieraksti.\nSkatīt ierakstu Nr.: ");
        String line = sc.nextLine().trim();
        System.out.println(readLineFromFile(line, CommonMethods.complaintDb));
        checkNextEntry(CommonMethods.complaintDb);
    }

    private static void checkNextEntry(String db) throws IOException {
        System.out.print("Vai parādīt citu ierakstu? (Y/N): ");
        String answer = sc.nextLine().trim();
        if(answer.equalsIgnoreCase("y")) {
            if(db.equals(CommonMethods.complaintDb)) checkComplaints();
            else if(db.equals(CommonMethods.bookingDb)) checkBookings();
        }
        else if (answer.equalsIgnoreCase("n")) chooseAction();
        else System.out.println("Kļūdaina izvēle!");
        checkNextEntry(db);
    }

    private static void checkBookings() throws IOException {
        System.out.println("Rezervāciju datubāzē ir " + countLines(CommonMethods.bookingDb) + " ieraksti.\nSkatīt ierakstu Nr.: ");
        String line = sc.nextLine().trim();
        System.out.println(readLineFromFile(line, CommonMethods.bookingDb));
        checkNextEntry(CommonMethods.bookingDb);
    }

    private static void createCustomer() {
        System.out.print("Klienta vārds: ");
        String name = sc.next();
        String customerId = String.valueOf(CommonMethods.generateId());
        System.out.println("Klienta identifikācijas numurs: " + customerId);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(CommonMethods.customerDb, true));
            writer.append(name).append(",").append(customerId);
            writer.newLine();
            writer.close();
            System.out.print("Vai pievienot jaunu klientu (Y/N)? ");
            String input = sc.next();
            addNewCustomer(input);
        } catch (IOException e) {
            System.out.println("Radusies kļūda!");
            e.printStackTrace();
        }
    }

    private static void addNewCustomer(String input) throws IOException {
        if(input.equalsIgnoreCase("Y")) {
            createCustomer();
        } else if (input.equalsIgnoreCase("N")) {
            System.out.print("Vai vēlaties veikt citas darbības? (Y/N) ");
            String newInput = sc.next();
            if(newInput.equalsIgnoreCase("Y")) {
                chooseAction();
            } else if (newInput.equalsIgnoreCase("N")) {
                CommonMethods.leave(message);
            }
        }
    }

    private static void createCustomerDatabase() {
        System.out.print("Ievadiet datubāzes nosaukumu: ");
        String dbName = sc.next();
        File db = new File(dbName);
        try {
            if(db.createNewFile()) {
                System.out.println("Izveidota datubāze: " + db.getName());
            } else {
                System.out.println("Datubāze jau eksistē!");
            }
        } catch (IOException e) {
            System.out.println("Radusies kļūda!");
            e.printStackTrace();
        }
    }

    private static long countLines(String dbName) {
        Path path = Paths.get(dbName);
        long lineCount = 0;
        try (Stream<String> stream = Files.lines(path)) {
            lineCount = stream.count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }

    private static String readLineFromFile(String line, String db) throws IOException {
        return Files.readAllLines(Paths.get(db)).get(Integer.parseInt(line) - 1);
    }
}