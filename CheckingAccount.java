//~ Imports ...............................................................
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a Checking account. Will contain simple operations
 * such as: Checking balance, deposits, withdrawals, recording transactions,
 * and more.
 * @author Farhan Abdulla
 * @version 01.06.2022
 */
public class CheckingAccount
    implements BankAccount
{
//~ Fields ................................................................
    private double balance;
    private Map<String, String> transactionsByDate;
    private int accountNumber;
    private String openingDate;
    private double interestRate;

//~ Constructor ...........................................................
    
    /**
     * Represents a checking account. A minimum $25 is 
     * required to open a checkings account for this bank. An 8-digit 
     * account number is also generated.
     * @param balance Double representing opening deposit
     */
    public CheckingAccount(double balance)
    {
        this.balance = balance;
        this.transactionsByDate = new HashMap<String, String>();
        this.openingDate = this.getCurrentDate();
        this.accountNumber = ThreadLocalRandom.current().
            nextInt(10000000, 100000000);
    }
    /**
     * Represents a checking account. A minimum $25 is 
     * required to open a checkings account for this bank. An 8-digit 
     * account number is also generated.
     */
    public CheckingAccount()
    {
        this.balance = 25.00;
        this.transactionsByDate = new HashMap<String, String>();
        this.openingDate = this.getCurrentDate();
        this.accountNumber = ThreadLocalRandom.current().
            nextInt(10000000, 100000000);
    }

//~ Methods ...............................................................

    //Account information
    /**
     * Gets the date when the account was first opened. Date is a String in
     * MM-dd-yy format
     * @return String representing the opening date of the account
     */
    @Override
    public String getOpeningDate()
    {
        return this.openingDate;
    }

    /**
     * Gets the 8-digit account number 
     * @return Integer representing the account number
     */
    @Override
    public int getAccountNumber()
    {
        return this.accountNumber;
    }
    
    //Account operative methods
    /**
     * Gets the account's balance.
     * @return The balance of the account as a double value
     */
    @Override
    public double getBalance()
    {
        return this.balance;
    }
    
    /**
     * Returns String stating current balance in account
     * @return String representing current balance
     */
    @Override
    public String checkBalance()
    {
        return "Your current balance is: $" + this.getBalance();
    }

    /**
     * Adds money to the account balance if and only if the amount is
     * positive. Also records a deposit in transaction history
     * @param amount Double value representing the amount of money deposited
     * @return Boolean representing if conditons to deposit were met
     */
    @Override
    public boolean deposit(double amount)
    {
        if (amount > 0)
        {
            this.balance += amount;
            this.addTransaction(true, amount);
            return true;
        }
        return false;
    }
    
    /**
     * Subtracts money from the account balance if and only if the amount
     * does not put the balance below zero. Also records a withdrawal in
     * transaction history.
     * @param amount Double value representing the amount of money withdrawn
     * @return Boolean representing if conditions to withdraw were met
     */
    @Override
    public boolean withdraw(double amount)
    {
        if (this.getBalance() - amount >= 0 && amount > 0)
        {   
            this.balance -= amount;
            this.addTransaction(false, amount);
            return true;
        }
        return false;
    }

    /**
     * Updates history of transactions whenever money is deposited
     * or withdrawn from the account. Accepts a boolean parameter; true
     * if deposit is performed, false if withdrawal is performed.
     * Date format will be MM-dd-yy
     * @param deposit Boolean representing if money is being deposited
     * @param amount Contains amount of money deposited or withdrawn
     */
    @Override
    public void addTransaction(boolean deposit, double amount)
    {
        String date = this.getCurrentDate();
        if (deposit && transactionsByDate.containsKey(date))
        {
            transactionsByDate.compute(date, (k, v) -> v + ",+" + amount);
        }
        else if (!deposit && transactionsByDate.containsKey(date))
        {
            transactionsByDate.compute(date, (k, v) -> v + ",-" + amount);
        }
        else if (deposit)
        {
            transactionsByDate.put(date, "+" + amount);
        }
        else
        {
            transactionsByDate.put(date, "-" + amount);
        }
    }

    /**
     * Gets all the transactions for this day
     * @param date String containing the date 
     * @return String representing all transactions made on a Specific day
     */
    @Override
    public String getTransactionForDay(String date)
    {
        return date + "\n" + "-".repeat(10) + "\n" 
            + transactionsByDate.get(date);
    }
    
    /**
     * Gets the annual interest rate of this account.
     * @return Double representing the interest rate
     */
    @Override
    public double getInterestRate() 
    {
        
        return this.interestRate;
    }
    
    /**
     * Sets the annual interest rate of this account.
     * @param newRate Double containing the interest rate
     */
    @Override
    public void setInterestRate(double newRate) 
    {
        this.interestRate = newRate;
    }
    
    //Account utlities
    /**
     * Gets the current date in MM/dd/yy format as a String
     * @return String representing current date
     */
    @Override
    public String getCurrentDate()
    {
        Calendar cal = Calendar.getInstance();
        return new SimpleDateFormat().format(cal.getTime()).split(",")[0];
    }

    /**
     * Parses the date into its integer components
     * @param date String containing the date
     * @return Array of integers representing month, day, year in that order
     */
    @Override
    public int[] dateToInt(String date)
    {
        String[] str = date.split("/");
        int[] parts = {Integer.parseInt(str[0]), Integer.parseInt(str[0]), 
            Integer.parseInt(str[0])};
        return parts;
    }

    /**
     * Gets the interest accumlateed in this account
     * @return Double representing the accumlated interest
     */
    @Override
    public double getAccruedInterest()
    {
        return 0.0;
    }

} // End of class