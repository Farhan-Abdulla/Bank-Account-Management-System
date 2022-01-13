//~ Imports ...............................................................
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Represents a (Roth) Individual Retirement Account, where money is grown
 * in a portfolio for retirement. The average annual yield is 7% and annual 
 * deposit cap is $6000.
 * @author Farhan Abdulla
 * @version 01.13.2022
 */
public class IndividualRetirementAccount 
    extends CheckingAccount
{
    //~ Fields ................................................................
    private Calendar fiveYears;
    private String endDate;
    private double monthlyDeposit;
    //~ Constructor ...........................................................

    /**
     * Represents a Roth IRA that takes monthly deposits and growths funds
     * generally over a long period of time in preparation for retirement.
     * @param monthlyDeposit Double containing the monthly deposit of the account
     */
    public IndividualRetirementAccount(double monthlyDeposit)
    {
        super();
        this.setInterestRate(7);
        this.fiveYears = Calendar.getInstance();
        this.fiveYears.set(Calendar.YEAR, 5);
        this.endDate = this.getDate(fiveYears);
        this.setMonthlyDeposit(monthlyDeposit);
    }

    /**
     * Represents a Roth IRA that takes monthly deposits and growths funds
     * generally over a long period of time in preparation for retirement.
     */
    public IndividualRetirementAccount()
    {
        super();
        this.setInterestRate(7);
        this.fiveYears = Calendar.getInstance();
        this.fiveYears.set(Calendar.YEAR, 5);
        this.endDate = this.getDate(fiveYears);
        this.setMonthlyDeposit(300);
    }

    //~ Methods ...............................................................
    
    /**
     * Gets the monthly deposit of the account
     * @return Double representing the monthly deposit
     */
    public double getMonthlyDeposit()
    {
        return this.monthlyDeposit;
    }

    /**
     * Sets the monthly deposit of the account. Cannot exceed $500 to stay under
     * yearly cap
     * @param amount Double containing the monthly deposit
     */
    public void setMonthlyDeposit(double amount)
    {
        if (amount > 500)
        {
            monthlyDeposit = 500;
        }
        else
        {
            this.monthlyDeposit = amount;
        }
    }

    /**
     * Gets the date from calendar object in MM/dd/yy format
     * @param cal Calendar object containing the calendar date
     * @return String representing the date retrieved
     */
    public String getDate(Calendar cal)
    {
        return new SimpleDateFormat().format(cal.getTime()).split(",")[0];
    }

    /**
     * Checks to see if 5 year term is over. End date is determined 5 years
     * after account opening date.
     * @return Boolean representing whether 5 year term has ended is true
     */
    public boolean pastTerm()
    {
        Calendar today = Calendar.getInstance();
        return (this.getDate(today).equals(this.endDate))
            || today.after(this.fiveYears);
    }

    /**
     * Subtracts money from the account balance if and only if the amount
     * does not put the balance below zero. Also records a withdrawal in
     * transaction history. If a withdraw happens within the first five year,
     * a 10% penalty is charged.
     * @param amount Double value representing the amount of money withdrawn
     * @return Boolean representing if conditions to withdraw were met
     */
    @Override
    public boolean withdraw(double amount)
    {
        if (pastTerm() && this.getBalance() - amount >= 0 && amount > 0)
        {
            super.withdraw(amount);
            return true;
        }
        else if (this.getBalance() - amount >= 0 && amount > 0)
        {
            super.withdraw(amount * 1.1);
            return true;
        }
        return false;
    }

    /**
     * Gets the accumalated appreciated balance of the IRA
     * @return Double representing the balance of the account
     */
    @Override
    public double getBalance()
    {
        double sum = 0;
        for (int i = 0; i < this.getPeriod(); i++)
        {
            int period = getPeriod() - i;
            sum += monthlyDeposit * Math.pow(1 + getInterestRate() / 36500,
                365.0 * period / 12);
        }
        return sum;
    }

    /**
     * Gets the period of the account when attempting a interest calculation
     * @return Integer representing the duration of the earliest deposit in
     * months
     */
    public int getPeriod()
    {
        int initialMonth = this.dateToInt(this.getOpeningDate())[0];
        int initialYear = this.dateToInt(this.getOpeningDate())[2];
        int finalMonth = this.dateToInt(this.getCurrentDate())[0];
        int finalYear = this.dateToInt(this.getCurrentDate())[2];
        return ((finalYear - initialYear) * 12) + (finalMonth - initialMonth);
    }

} // End of class