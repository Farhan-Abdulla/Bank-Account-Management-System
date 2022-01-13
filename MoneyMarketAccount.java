//~ Imports ...............................................................

/**
 * Represents a Money Market Account that pays interest based on current 
 * interest rates in the money markets. Inherits SavingsAccount method.
 * @author Farhan Abdulla
 * @version 01.12.2022
 */
public class MoneyMarketAccount 
    extends SavingsAccount
{
    //~ Fields ................................................................
    

    //~ Constructor ...........................................................

    /**
     * Represents a Money Market deposit account that pays interest
     * @param balance Double containing the opening balance of the account
     */
    public MoneyMarketAccount(double balance)
    {
        super(balance);
        this.setInterestRate(0.3);
    }

    /**
     * Represents a Money Market deposit account that pays interest. Minimum
     * opening balance is $1000
     */
    public MoneyMarketAccount()
    {
        super(500);
        this.setInterestRate(0.3);
    }

    //~ Methods ...............................................................

    /**
     * Subtracts money from the account balance if and only if the amount
     * does not put the balance below 1000. Also records a withdrawal in
     * transaction history. Only six withdrawals can be executed per month
     * @param amount Double containing the withdraw amount
     * @return Boolean representing whether conditions for a withdraw were met
     *  or not
     */
    @Override
    public boolean withdraw(double amount)
    {
        if (this.getBalance() - amount >= 500 && this.getWithdrawCount() < 6
            && amount > 0)
        {
            super.withdraw(amount);
            return true;
        }
        return false;
    }

} // End of Class