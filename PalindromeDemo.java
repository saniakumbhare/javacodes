import java.util.Scanner;

public class PalindromeDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input a String for Palindrome Test: ");
        String input = scanner.nextLine();
        scanner.close();

        String cleanedInput = input.replaceAll("[^a-zA-Z]", "").toLowerCase();

        boolean isPalindrome = true;
        int left = 0;
        int right = cleanedInput.length() - 1;

        while (left < right) {
            if (cleanedInput.charAt(left) != cleanedInput.charAt(right)) {
                isPalindrome = false;
                break;
            }
            left++;
            right--;
        }

        if (isPalindrome) {
            System.out.println("The input is a palindrome.");
        } else {
            System.out.println("The input is not a palindrome.");
        }
    }
}