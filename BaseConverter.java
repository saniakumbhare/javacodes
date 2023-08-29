import java.util.Scanner;
public class BaseConverter {
    private ListStackDataStrucClass<Long> stack;

    public BaseConverter() {
        stack = new ListStackDataStrucClass<>();
    }

    public void inputPrompt() {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 3; i++) {
            System.out.print("Enter a number in base 10: ");
            long number = scanner.nextLong();
            System.out.print("Enter the base (2-9): ");
            int base = scanner.nextInt();

            BaseNumber baseNumber = new BaseNumber(number, base);
            convertAll(baseNumber);
            System.out.println("Converted number: " + toString());
            stack = new ListStackDataStrucClass<>();
        }

        scanner.close();
    }

    public void convertAll(BaseNumber baseNumber) {
        long number = baseNumber.getNumber();
        int base = baseNumber.getBase();

        while (number > 0) {
            long remainder = number % base;
            stack.push(remainder);
            number /= base;
        }
    }

    public String toString() {
        return stack.toString();
    }

    public void processAndPrint() {
        inputPrompt();
    }

    private class BaseNumber {
        private long number;
        private int base;

        public BaseNumber(long number, int base) {
            this.number = number;
            this.base = base;
        }

        public long getNumber() {
            return number;
        }

        public int getBase() {
            return base;
        }

        public void setNumber(long number) {
            this.number = number;
        }

        public void setBase(int base) {
            this.base = base;
        }
    }
}