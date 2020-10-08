import java.io.*;
import java.util.Scanner;

public class CommonMethods {
    static String customerDb = "customers.txt";
    static String bookingDb = "bookings.txt";
    static String complaintDb = "complaints.txt";

    static void leave(String message) {
        System.out.println(message);
        System.exit(0);
    }

    static int generateId() {
        return 10000 + (int)(Math.random()*90000);
    }

    static String checkId(String id) {
        String message = null;
        File file = new File("customers.txt");
        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.contains(id)) {
                    message = "Klienta kods ir aktīvs";
                } else {
                    message = "Klienta kods nav atrasts!";
                }
            }
        } catch (FileNotFoundException e) {
            message = "Radusies kļūda!";
            e.printStackTrace();
        }
        System.out.println(message);
        return message;
    }

    static void updateDb(String id, String text, String db) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(db, true));
            writer.append(id).append(",").append(text);
            writer.newLine();
            writer.close();
            System.out.printf("Datubāze %s atjaunota!%n", db);
        } catch (IOException e) {
            System.out.println("Radusies kļūda!");
            e.printStackTrace();
        }
    }
}