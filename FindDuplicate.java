/*************************************************************************
 *  Compilation:  javac FindDuplicate.java
 *  Execution:    java FindDuplicate
 *
 *  @author:
 *
 * FindDuplicate that reads n integer arguments from the command line 
 * into an integer array of length n, where each value is between is 1 and n, 
 * and displays true if there are any duplicate values, false otherwise.
 *
 *  % java FindDuplicate 10 8 5 4 1 3 6 7 9
 *  false
 *
 *  % java FindDuplicate 4 5 2 1 
 *  true
 *************************************************************************/

public class FindDuplicate {

    public static void main(String[] args) {

		int []arr = new int [args.length];
		boolean dupe = false;

		for(int a = 0; a < arr.length; a++)
		arr[a]= Integer.parseInt(args[a]);

		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] == arr[j]) {
					dupe = true;
					System.out.println(dupe);
					return;
				}
				else
					dupe = false;
			}
		}
 
		System.out.println(dupe);

	}
}
