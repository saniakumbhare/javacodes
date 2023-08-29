import java.util.*;

public class Account{

    private int id = 0;
    private double balance = 0;
    private double annualInterestRate = 0;
    private Date dateCreated;

    public Account()
    {
        this.id = 0;
        this.balance =0;
        this.annualInterestRate = 0;
        this.dateCreated = new Date();
    }

    public Account(int id, double balance, double annualInterestRate)
    {
    this.id = id;
    this.balance = balance;
    this.annualInterestRate = annualInterestRate;
    this.dateCreated = new Date();
    }


    public void setID(int id) {
    this.id=id;
    }

    public int getID() {
    return this.id;
    }


    public void setBalance(double balance) {
    this.balance = balance;
    }


    public double getbalance(){
    return this.balance;
    }


    public void setAnnualInterestrate(double annualInterestRate) {
    this.annualInterestRate =annualInterestRate;
    }


    public double getAnnualInterestrate() {
    return this.annualInterestRate;
    }

    public Date getDateCreated() {
    return this.dateCreated;
    }

    public double getMonthlyInterestRate() {
    return (this.annualInterestRate)/12;
    }

    public void withdraw(double amount) {
    if( this.balance <amount)
    {
       System.out.println("Insufficent balance in the account");
    }
    else
    {
        this.balance -=amount;
        System.out.println("Afterwithdrawing   "+ amount +"   balance in "
                + "accountis:   " +this.balance);
    }
}

public void deposit(double amount)
{
    this.balance += amount;
    System.out.println(" Afterdepositing   " + amount  + "   balance inaccount"
            + " is:"  + this.balance);
}


public static void main(String args[]){

Account ac= new Account( 1122, 20000, 4.5);
ac.withdraw(2500);
ac.deposit(3000);

System.out.println("balance in account " + ac.getID() +"is: " +ac.getbalance());
System.out.println("Monthly Interest  is: " + (ac.getMonthlyInterestRate()  
        *  ac.getbalance()) /100);
System.out.println("Account is created on:  " +ac.getDateCreated());

    }

}
