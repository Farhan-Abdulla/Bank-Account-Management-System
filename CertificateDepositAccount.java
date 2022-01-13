//~ Imports ...............................................................
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Represents a Certificate of Deposit account which grows money over a fixed
 * interest rate, and has a fixed term length of where the money needs to be
 * left in the account
 * @author Farhan Abdulla
 * @version 01.12.2022
 */
public class CertificateDepositAccount
    extends CheckingAccount
{
    //~ Fields ................................................................
    private int termLength;
    private String endDate;

    //~ Constructor ...........................................................

    /**
     * Represents a Certificate of Deposit account which grows money over a fixed
     * interest rate, and has a fixed term length of where the money needs to be
     * left in the account. Default interest rate is %1
     * @param balance Double containing the opening balance of the account
     * @param months Integer containing term length
     */
    public CertificateDepositAccount(double balance, int months)
    {
        super(balance);
        this.setInterestRate(1);
        this.createNewTerm(months);
    }

    /**
     * Represents a Certificate of Deposit account which grows money over a fixed
     * interest rate, and has a fixed term length of where the money needs to be
     * left in the account. Default interest rate is %1 and term is 18 months
     * @param balance Double containing opening balance
     */
    public CertificateDepositAccount(double balance)
    {
        super(balance);
        this.setInterestRate(1);
        this.createNewTerm(18);
    }

    /**
     * Represents a Certificate of Deposit account which grows money over a fixed
     * interest rate, and has a fixed term length of where the money needs to be
     * left in the account. Default opening balance is $1000, interest rate is %1,
     * and term is 18 months
     */
    public CertificateDepositAccount()
    {
        super(1000);
        this.setInterestRate(1);
        this.createNewTerm(18);
    }

    //~ Methods ...............................................................

    /**
     * Gets the CD term length
     * @return Integer representing CD term length in months
     */
    public int getTermLength()
    {
        return this.termLength;
    }

    /**
     * Gets the end date of CD term in MM/dd/yy format
     * @return String representing CD term end date
     */
    public String getEndDate()
    {
        return this.endDate;
    }

    /**
     * Creates a new CD term for the account. An end date is calculated from
     * the new term length.
     * @param newTermLength Integer containing new CD term length
     */
    public void createNewTerm(int newTermLength)
    {
        this.termLength = newTermLength;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, newTermLength);
        this.endDate = new SimpleDateFormat().format(cal.getTime()).
            split(",")[0];
    }

    /**
     * Checks to see if CD term is over
     * @return Boolean representing whether CD term has ended is true
     */
    public boolean pastTerm()
    {
        int month = this.dateToInt(this.getEndDate())[0];
        int date = this.dateToInt(this.getEndDate())[1];;
        int year = this.dateToInt(this.getEndDate())[2];;
        Calendar today = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.set(year, month, date);
        return (this.getCurrentDate().equals(this.getEndDate()))
            || today.after(end);
    }

    /**
     * Adds money to the account balance if and only if the amount is
     * positive, and account is not under a CD term. Also records a deposit 
     * in transaction history. 
     * @param amount Double value representing the amount of money deposited
     * @return Boolean representing if conditons to deposit were met
     */
    @Override
    public boolean deposit(double amount)
    {
        if (this.pastTerm())
        {
            super.deposit(amount);
            return true;
        }
        return false;
    }

    /**
     * Subtracts money from the account balance if and only if the amount
     * does not put the balance below zero, and if the account is not under a 
     * CD term. Also records a withdrawal in transaction history.
     * @param amount Double value representing the amount of money withdrawn
     * @return Boolean representing if conditions to withdraw were met
     */
    @Override
    public boolean withdraw(double amount)
    {
        if (this.pastTerm())
        {
            super.withdraw(amount);
            return true;
        }
        return false;
    }

    /**
     * Calculates the accumulated interest of the account
     * @return Double representing interest accumalated
     */
    @Override
    public double getAccruedInterest()
    {
        return this.getBalance() * Math.pow((1 + this.getInterestRate() 
            / 36500), 365.0 * this.getTermLength() / 12 ) - this.getBalance();
    }

    /**
     * Credits interest gained from account if term has been past
     */
    public void creditBalance()
    {
        if (this.pastTerm())
        {
            this.deposit(this.getAccruedInterest());
        }
    }

} // End of class