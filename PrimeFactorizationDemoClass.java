public class PrimeFactorizationDemoClass {
    public static void main(String[] args) {
        ArrayStackDataStrucClass<Integer> stack = new ArrayStackDataStrucClass<>(50);

        try {
            // Pushing the given integers onto the stack
            stack.push(13780);
            stack.push(222222);
            stack.push(1234);
            stack.push(3960);

            //System.out.println("Prime factors in descending order:");

            // Popping and printing the prime factors for each number
            while (!stack.isEmptyStack()) {
                int n = stack.peek();
                printPrimeFactors(n);
                stack.pop();
            }
        } catch (StackOverflowException e) {
            System.out.println("Stack Overflow: " + e.getMessage());
        } catch (StackUnderflowException e) {
            System.out.println("Stack Underflow: " + e.getMessage());
        }
    }

    // Method to print the prime factors of a given number
    private static void printPrimeFactors(int n) {
        ArrayStackDataStrucClass<Integer> factorsStack = new ArrayStackDataStrucClass<>(50);

        for (int divisor = 2; divisor <= n; divisor++) {
            while (n % divisor == 0) {
                factorsStack.push(divisor);
                n /= divisor;
            }
        }

        System.out.print(factorsStack.peek());
        factorsStack.pop();

        while (!factorsStack.isEmptyStack()) {
            System.out.print(" * " + factorsStack.peek());
            factorsStack.pop();
        }

        System.out.println();
    }
}
