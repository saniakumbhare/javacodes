import java.util.Scanner;

public class hex2Dec {

    public static int hex2Dec (String hexString) {
        if (hexString.length() == 0) {
            return 0;
        } 
        else {
            char charac = Character.toUpperCase(hexString.charAt(hexString.length() - 1));
            int value = 0;
            if (charac >= '0' && charac <= '9') {
                value = charac - '0';

            } else if (charac >= 'A' && charac <= 'F') {
                value = 10 + (charac - 'A');
            }
            return hex2Dec(hexString.substring(0, hexString.length() - 1)) * 16 + value;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        for (int i = 0; i < 3; i++) {
            System.out.print("Enter a hexadecimal number: ");
            String str = in.nextLine();
            System.out.println("Decimal value of " + str + " is " + hex2Dec(str) + "\n");
        }
    }
}
