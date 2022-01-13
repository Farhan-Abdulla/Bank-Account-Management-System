/**
 * This interface represents the general type of Bank Account and contains the 
 * features that must all be present in each subtype of bank account.
 * @author Farhan Abdulla
 * @version 01.06.2022
 */
public interface BankAccount
{

//~ Methods ...............................................................

    /**
     * Gets the date when the account was first opened. Date is a String in
     * dd-MM-yyyy format
     * @return String representing the opening date of the account
     */
    public String getOpeningDate();

    /**
     * Gets the 8-digit account number 
     * @return Integer representing the account number
     */
    public int getAccountNumber();

    /**
     * Gets the account's balance.
     * @return The balance of the account as a double value
     */
    public double getBalance();
    
    /**
     * Returns String stating current balance in account
     * @return String representing current balance
     */
    public String checkBalance();

    /**
     * Adds money to the account balance if and only if the amount is
     * positive. Also records a deposit in transaction history
     * @param amount Double value representing the amount of money deposited
     * @return Boolean representing if conditions to deposit were met
     */
    public boolean deposit(double amount);
    
    /**
     * Subtracts money from the account balance if and only if the amount
     * does not put the balance below zero. Also records a withdrawal in
     * transaction history.
     * @param amount Double value representing the amount of money withdrawn
     * @return Boolean representing if conditions to withdraw were met
     */
    public boolean withdraw(double amount);

    /**
     * Updates history of transactions whenever money is deposited
     * or withdrawn from the account. Accepts a boolean parameter; true
     * if deposit is performed, false if withdrawal is performed.
     * Date format will be dd-MM-yyyy
     * @param deposit Boolean representing if money is being deposited
     * @param amount Contains amount of money deposited or withdrawn
     */
    public void addTransaction(boolean deposit, double amount);

    /**
     * Gets all the transactions for this day
     * @param date String containing the date 
     * @return String representing all transactions made on a
     *  Specific day
     */
    public String getTransactionForDay(String date);

    /**
     * Gets the current date in dd-MM-yyyy format as a String
     * @return String representing current date
     */
    public String getCurrentDate();

    /**
     * Gets the current interest rate of this savings account
     * @return Double representing the interest rate
     */
    public double getInterestRate();

    /**
     * Sets the interest rate of this savings account
     * @param newRate Double containing the interest rate
     */
    public void setInterestRate(double newRate);

    /**
     * Parses the date into its integer components
     * @param date String containing the date
     * @return Array of integers representing month, day, year in that order
     */
    public int[] dateToInt(String date);

    /**
     * Gets the interest accumlateed in this account
     * @return Double representing the accumlated interest
     */
    public double getAccruedInterest();

} // End of class