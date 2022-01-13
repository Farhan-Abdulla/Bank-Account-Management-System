//~ Imports ...............................................................
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Represents a Savings bank account. It is an extended class that inherits
 * from the Checking Account class. This class will actively be utilizing
 * compound interest, and is limited to six account withdrawals per month
 * @author Farhan Abdulla
 * @version 01.12.2022
 */
public class SavingsAccount
    extends CheckingAccount
{
    //~ Fields ................................................................
    private int withdrawCount;
    private String startDate;
    private String endDate;
    private List<Double> deposits;
    private double interestRate;

    //~ Constructor ...........................................................
    /**
     * Represents a savings bank account to save and grow money long term. 
     * All initial members are defined and initialized in the super class. 
     * The default interest rate is set to 0.06 percent. There is also an 
     * unchanging withdraw limit of 6 per month in this account.
     * @param balance Double containing opening deposit
     */
    public SavingsAccount(double balance)
    {
        super(balance);
        this.setInterestRate(0.1);
        this.createNewCycle();
        this.deposits = new ArrayList<Double>();
        this.deposits.add(balance);
    }

    /**
     * Represents a savings bank account. All initial members are defined and 
     * initialized in the super class. The default interest rate is set to
     * 0.06 percent. There is also an unchanging withdraw limit of 6 per month
     * in this account.
     */
    public SavingsAccount()
    {
        super(300);
        this.setInterestRate(0.1);
        this.createNewCycle();
        this.deposits = new ArrayList<Double>();
        this.deposits.add(25.0);
    }

    //~ Methods ...............................................................
    
    /**
     * Gets the number of withdraws made in this month
     * @return Integer representing the number of withdraws made in this month
     */
    public int getWithdrawCount()
    {
        return this.withdrawCount;
    }
    
    /**
     * Sets the number of withdraws made in this month
     * @param newCount Integer containing the number of withdraws made in this
     *   month.
     */
    public void setWithdrawCount(int newCount)
    {
        this.withdrawCount = newCount;
    }

    /**
     * Gets the first day of the monthly cycle which tracks withdraw
     * count every 30 days.
     * @return String representing start date of cycle
     */
    public String getStartDate()
    {
        return this.startDate;
    }

    /**
     * Gets the day that marks the day to reset monthly cycle which track
     * withdraw count every 30 days.
     * @return String representing date of end of cycle
     */
    public String getEndDate()
    {
        return this.endDate;
    }

    /**
     * Creates a new 1 month cycle by resetting the start and end dates.
     * Start date gets set to current date, and end date gets set to the date
     * that is 30 days after the start date. Date format is MM/dd/yy
     */
    public void createNewCycle()
    {
        this.startDate = this.getCurrentDate();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        this.endDate = new SimpleDateFormat().format(cal.getTime()).
            split(",")[0];
    }
    
     /**
     * Gets the interest of the account
     * @return Double representing the interest rate of the account
     */
    public double getInterestRate()
    {
        return this.interestRate;
    }

    /**
     * Sets the interest of the account
     * @param newRate Double containing the interest rate of the account
     */
    public void setInterestRate(double newRate)
    {
        this.interestRate = newRate;
    }

    /**
     * Gets all the deposits made to this account
     * @return List of doubles, each representing every deposit
     */
    public List<Double> getDeposits()
    {
        return this.deposits;
    }

    /**
     * Checks to see if it is the end of the cycle. If true, then start and
     * end dates are reset, and monthly withdraw count is set to 0.
     * @param endDate String containing the end date of the cycle
     * @return Boolean representing whether end of cycle is true or false
     */
    public boolean endOfCycle(String endDate)
    {
        if (this.getCurrentDate().equals(endDate))
        {
            this.createNewCycle();
            this.setWithdrawCount(0);
            return true;
        }
        return false;
    }

    /**
     * Gets the balance of the account along with its accrued interest
     * @return Double representing the balance of the savings account
     */
    @Override
    public double getBalance()
    {
        return this.getAccruedInterest();
    }

    /**
     * Add news feature to deposit where every deposit is recorded into
     * deposits field, which will be accessed when calculating compound
     * interest. It is assumed that deposits will be made once a month
     * @param amount Double containing deposit amount
     * @return Boolean representing whether conditions for deposit are met
     */
    @Override
    public boolean deposit(double amount)
    {
        if (amount > 0)
        {
            this.deposits.add(amount);
            super.deposit(amount);
        }
        return false;
    }

    /**
     * Subtracts money from the account balance if and only if the amount
     * does not put the balance below 300. Also records a withdrawal in
     * transaction history. Only six withdrawals can be executed per month
     * @param amount Double containing amount to withdraw from account
     * @return Boolean representing whether conditions for withdraw are met
     */
    @Override
    public boolean withdraw(double amount)
    {
        this.endOfCycle(this.getEndDate());
        if ((this.getWithdrawCount() < 6) && (this.getBalance() - amount >= 300) 
            && amount > 0)
        {
            this.withdrawCount++;
            super.withdraw(amount);
            this.removeDeposits(amount);
            return true;
        }
        return false;
    }

    /**
     * Updates the list of deposits when a withdrawal from account is
     * executed. Will traverse through the list starting from the end 
     * to the start, until the amount of withdrawal is removed from the
     * list of deposits, or until it reaches the start
     * @param amount Double containing the amount being withdrawn
     */
    public void removeDeposits(double amount)
    {
        for (int i = deposits.size() - 1; i >= 0; i--)
        {
            if (amount > deposits.get(i))
            {
                amount -= deposits.get(i);
                deposits.remove(i);
            }
            else if (amount == deposits.get(i))
            {
                deposits.remove(i);
                break;
            }
            else
            {
                deposits.set(i, deposits.get(i) - amount);
                break;
            }
        }
    }

    /**
     * Calculates compound interest accumulated on top of balance.
     * Interest is compounded daily, while time will be in units of years
     * @param principle Double containing the principle
     * @param period Integer containing the period
     * @return Double representing the accrued interest on top of balance
     */
    public double getAccruedInterest(double principle, int period)
    {
        return principle * Math.pow((1 + this.getInterestRate() 
            / 36500), 365.0 * period / 12);
    }

    /**
     * Calculates compound interest accumulated on top of balance.
     * Proportionally sums up all the monthly deposits by determining each
     * deposits length of duration in this account. Interest is compounded daily, 
     * while time will be in units of years
     * @return Double representing the accrued interest on top of balance
     */
    @Override
    public double getAccruedInterest()
    {
        double sum = 0;
        int period = this.getPeriod();
        for (double deposit : this.getDeposits())
        {
            sum += this.getAccruedInterest(deposit, period);
            period--;
        }
        return sum;
    }

    /**
     * Gets the period of the account when attempting a interest calculation
     * @return Integer representing the duration of the earliest deposit in
     *  months
     */
    public int getPeriod()
    {
        int initialMonth = this.dateToInt(this.getOpeningDate())[0];
        int initialYear = this.dateToInt(this.getOpeningDate())[2];
        int finalMonth = this.dateToInt(this.getCurrentDate())[0];
        int finalYear = this.dateToInt(this.getCurrentDate())[2];
        return ((finalYear - initialYear) * 12) + (finalMonth - initialMonth);
    }

    /**
     * Gets the period of the account when attempting a interest calculation.
     * @param finalMonth Integer containing the final month of period
     * @param finalYear Integer containing the final year of period
     * @return Integer representing the length of the period
     */
    public int getPeriod(int finalMonth, int finalYear)
    {
        int initialMonth = this.dateToInt(this.getOpeningDate())[0];
        int initialYear = this.dateToInt(this.getOpeningDate())[2];
        return ((finalYear - initialYear) * 12) + (finalMonth - initialMonth);
    }

    /**
     * Gets the period of the account when attempting a interest calculation.
     * @param month1 Integer containing the initial month of period
     * @param year1 Integer containing the initial year of period
     * @param month2 Integer containing the final month of period
     * @param year2 Integer containing the final month of period
     * @return Integer representing the length of the period
     */
    public int getPeriod(int month1, int year1, int month2, int year2)
    {
        return (Math.abs(year2 - year1) * 12) + Math.abs(month2 - month1);
    }

} // End of class