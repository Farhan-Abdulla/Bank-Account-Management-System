//~ Imports ...............................................................
import java.util.*;

/**
 * Represents an individual account holder. Will hold operations for 
 * opening, closing, and finding an account.
 * @author Farhan Abdulla
 * @version 01.06.2022
 */
public class Customer 
{
    //~ Fields ................................................................
    private String name;
    private String customerId;
    private List<BankAccount> accounts;

    //~ Constructor ...........................................................

    /**
     * Represents a Customer that owns an account at the bank
     * @param customerId String containing customer ID of person
     * @param name String containing first and last name of person
     */
    public Customer(String customerId, String name)
    {
        this.customerId = customerId;
        this.name = name;
        this.accounts = new ArrayList<BankAccount>();
    }

    //~ Methods ...............................................................

    //Account information
    /**
     * Gets the first and last name of the person
     * @return String representing first and last name of person
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets the first and last name of the person
     * @param newName String containg first and last name of person
     */
    public void setName(String newName)
    {
        this.name = newName;
    }

    /**
     * Gets the customer Id of the account
     * @return String representing customer Id
     */
    public String getCustomerId()
    {
        return this.customerId;
    }

    /**
     * Sets the customer Id of the account
     * @param newCustomerId Contains String representing customer Id of the
     *  account
     */
    public void setCustomerId(String newCustomerId)
    {
        this.customerId = newCustomerId;
    }

    //Account operative methods
    /**
     * Creates a new checking account linked to this person
     */
    public void openAccount()
    {
        this.accounts.add(new CheckingAccount());
    }

    /**
     * Creates a new checking account linked to this person
     * @param balance Double containing opening balance of account
     */
    public void openAccount(double balance)
    {
        this.accounts.add(new CheckingAccount(balance));
    }

    /**
     * Creates a new account linked to this person. The type of account
     * to create will be indicated by the number in its parameter. By
     * default, a checkings account will be opened.
     * @param type Integer containing the type of account to make
     */
    public void openAccount(int type)
    {
        switch (type)
        {
            case 1:
                this.openAccount();
                break;
            case 2: 
                this.accounts.add(new SavingsAccount());
                break;
            case 3:
                this.accounts.add(new MoneyMarketAccount());
                break;
            case 4:
                this.accounts.add(new CertificateDepositAccount());
            case 5:
                this.accounts.add(new IndividualRetirementAccount());
                break;
            default:
                this.openAccount();
        }
    }

    /**
     * Creates a new account linked to this person. The type of account
     * to create will be indicated by the number in its parameter. By
     * default, a checkings account will be opened.
     * @param type Integer containing the type of account to make
     * @param balance Double containing opening balance of account
     */
    public void openAccount(int type, double balance, int term)
    {
        switch (type)
        {
            case 1:
                this.openAccount(balance);
                break;
            case 2: 
                this.accounts.add(new SavingsAccount(balance));
                break;
            case 3:
                this.accounts.add(new MoneyMarketAccount(balance));
                break;
            case 4:
                this.accounts.add(new CertificateDepositAccount(balance, term));
                break;
            case 5:
                this.accounts.add(new IndividualRetirementAccount(balance));
                break;
            default:
                this.openAccount(balance);
        }
    }

    /**
     * Closes an existing account linked to this person
     * @param account Account object containg account to remove
     */
    public void closeAccount(BankAccount account)
    {
        this.accounts.remove(account);
    }
    
    /**
     * Closes an existing account linked to this person
     * @param accountNumber Integer containing an account number
     */
    public void closeAccount(int accountNumber)
    {
        this.accounts.remove(this.getAccount(accountNumber));
    }

    /**
     * Gets all the open accounts under this person
     * @return List of Account objects representing all accounts under this person
     */
    public List<BankAccount> getAccounts()
    {
        return this.accounts;
    }

    /**
     * Gets the account linked to the account number or returns null
     * if no such account exists
     * @param accountNumber Integer containing account number
     * @return Account object representing Account or null
     */
    public BankAccount getAccount(int accountNumber)
    {
        for (BankAccount account : this.getAccounts())
        {
            if (account.getAccountNumber() == accountNumber)
            {
                return account;
            }
        }
        return null;
    }

    /**
     * Transfers funds from account 1 to account 2. Process will only
     * execute if transfer amount does not exceed balance in account 1.
     * Moving forward, money is withdrawn from account 1 and deposited
     * into account 2. A boolean value is returned where true indicates
     * a successful transfer.
     * @param acc1 Account object containing account to withdraw funds from
     * @param acc2 Account object containing account to deposit funds into
     * @param amount Double containing amount of money to transfer
     * @return Boolean representing whether conditions to transer were met
     */
    public boolean transferFunds(BankAccount acc1, BankAccount acc2, double amount)
    {
        if ((acc1.getBalance() - amount >= 0) && amount > 0 &&
            acc1.withdraw(amount))
        {
            acc2.deposit(amount);
            return true;
        }
        return false;
    }
    
} // End of class