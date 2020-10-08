import java.io.IOException;
import java.util.Scanner;

public class Agency {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        selectRole();
    }

    private static void selectRole() throws IOException {
        System.out.println("Veiciet izvēli: 1 - Klients; 2 - Aģents; 0 - Iziet");
        String role = sc.next();
        String message = "Sistēma tiek izslēgta...";

        switch (role) {
            case "1":
                System.out.println("Klients");
                Customer.insertCustomerId();
                break;
            case "2":
                System.out.println("Aģents");
                TravelAgent.chooseAction();
                break;
            case "0":
                CommonMethods.leave(message);
                break;
            default:
                System.out.println("Kļūdaina izvēle!");
                selectRole();
        }
    }
}