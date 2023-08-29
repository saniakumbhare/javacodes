public class GuessingGame {
    
    public static void main (String[] args){

        int n = Math.random();
        System.out.print ("Enter number: ");
        int number= StdIn.readInt();
    if (n < number){
        System.out.println("Too high");
    }
    if (n > number){
        System.out.println("Too low");
    }
    if (n == number){
        System.out.println("You win!");
    }

}
}
