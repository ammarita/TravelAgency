import java.util.Scanner;

public class Customer {
    private static Scanner sc = new Scanner(System.in);
    private static String customerId;

    public static void insertCustomerId() {
        System.out.print("Ievadiet savu klienta identifikācijas kodu vai NA, ja tas nav zināms: ");
        customerId = sc.nextLine().trim();

        if(customerId.matches("\\d{5}")) {
            String validation = CommonMethods.checkId(customerId);
            if (validation.equals("Klienta kods ir aktīvs")) {
                chooseAction();
            } else if (validation.equals("Klienta kods nav atrasts!")) {
                insertCustomerId();
            }
        } else if(customerId.equalsIgnoreCase("NA")) {
            customerId = String.valueOf(CommonMethods.generateId());
            System.out.print("Ievadiet savu vārdu: ");
            String name = sc.nextLine();
            CommonMethods.updateDb(customerId, name, CommonMethods.customerDb);
            System.out.printf("Izveidots jauns klients: %s ar klienta kodu %s", name, customerId);
            chooseAction();
        } else {
            System.out.println("Kodam jāsastāv no pieciem cipariem!");
            insertCustomerId();
        }
    }

    private static void chooseAction(){
        System.out.println("Lūdzu, izvēlieties darbību: 1 - ceļojuma rezervācija; 2 - sūdzības iesniegšana; 0 - iziet");
        String input = sc.nextLine();

        switch (input) {
            case "1":
                bookTrip();
                break;
            case "2":
                complain();
                break;
            case "0":
                CommonMethods.leave("Klients Nr. " + customerId + " iziet no sistēmas...");
            default:
                System.out.println("Kļūdaina izvēle!");
                chooseAction();
        }
        sc.close();
    }

    private static void complain() {
        System.out.println("Ierakstiet sūdzības tekstu: ");
        String complain = sc.nextLine();
        CommonMethods.updateDb(customerId, complain, CommonMethods.complaintDb);
        proceed("Paldies, ar jums tuvākajā laikā sazināsies aģentūras darbinieks, lai atrisinātu problemātisko situāciju!");
    }

    private static void bookTrip() {
        System.out.print("Ceļojuma vieta: ");
        String destination = sc.nextLine();
        System.out.print("Datums (DD-MM-YYYY): ");
        String date = sc.nextLine();
        String booking = destination + "," + date;
        CommonMethods.updateDb(customerId, booking, CommonMethods.bookingDb);
        proceed("Paldies, ar jums tuvākajā laikā sazināsies aģentūras darbinieks, lai precizētu ceļojuma detaļas!");
    }

    private static void proceed(String message) {
        System.out.print("Vai vēlaties turpināt? (Y/N): ");
        String answer = sc.nextLine().trim();
        if(answer.equalsIgnoreCase("y")) chooseAction();
        else if (answer.equalsIgnoreCase("n")) CommonMethods.leave(message);
        else System.out.println("Kļūdaina izvēle!");
        proceed(message);
    }
}