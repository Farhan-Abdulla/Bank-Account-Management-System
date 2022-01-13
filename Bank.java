//~ Imports ...............................................................
import java.util.*;

/**
 * Represents a bank that manages a series of customer bank accounts. Will perform
 * adminstrative tasks, such as: Adding, removing, and searching up customers
 * members in its records. As well as assigning unique customer IDs to each 
 * customer.
 * @author Farhan Abdulla
 * @version 01.06.2022
 */
public class Bank
{
    //~ Fields ................................................................
    private Map<String, Customer> customerById;

    //~ Constructor ...........................................................

    /**
     * Represents a bank that manages all accounts owned by its customers
     */
    public Bank()
    {
        customerById = new HashMap<String, Customer>();
    }

    //~ Methods ...............................................................

    /**
     * Maps a unique 8 character customer ID to a newly created Customer object.
     * This represents adding a new bank customer joining this bank.
     * @param name String containing the customer's first and last name
     */
    public void addCustomer(String name)
    {
        String newCustomerId = UUID.randomUUID().toString().substring(0, 6);
        customerById.put(newCustomerId, new Customer(newCustomerId, name));
        customerById.get(newCustomerId).openAccount();
    }

    /**
     * Removes mapping between a Customer object and a customer ID. This
     * represents a bank customer terminating their bank membership and it
     * effectively removes all accounts owned by that person.
     * @param customerId Contains String representing customer Id of the 
     *  Customer to remove from records
     */
    public void removeCustomer(String customerId)
    {
        customerById.remove(customerId);
    }

    /**
     * Returns the account holder using its customer ID. If the person does 
     * not have an account, null value will be returned.
     * @param name Name of the person being looked up in bank customerById
     * @return Person object value representing an account holder  or null
     */
    public Customer getCustomer(String customerId)
    {
        if (customerById.containsKey(customerId))
        {
            return customerById.get(customerId);
        }
        return null;
    }

    /**
     * Gets all the keys stored in customerById map
     * @return Set of String objects representing all the customer IDs
     */
    public Set<String> getAllCustomerId()
    {
        return this.customerById.keySet();
    }

    /**
     * Gets all the values stored in the customerById map
     * @return Collection of Customer objects representing all the customers
     */
    public Collection<Customer> getAllCustomers()
    {
        return this.customerById.values();
    }

} // End of Class