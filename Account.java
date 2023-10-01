package socialmedia;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Account is a class to create new accounts and perform account related methods within the system.
 */
public class Account implements Serializable {
    /**
     * Attribute to store the count of the number of accounts that have been created
     */
    public static int accountCount=0;

    /**
     * Attribute to store the ID of each account
     */
    private final int accountID;

    /**
     * Attribute to store the handle of each account
     */
    private String accountHandle;

    /**
     * Attribute to store the account description of each account
     */
    private String accountDescription;

    /**
     * Attribute to store the list of post IDs for each account
     */
    private ArrayList<Integer> accountsPosts = new ArrayList<>();

    /**
     * Attribute to store the list of post IDs for each account
     */
    private ArrayList<Integer> accountsEndorsementPosts = new ArrayList<>();

    /**
     * Constructor method to create a new account.
     *
     * @param accountHandle The account handle.
     * @param accountDescription The description of the account.
     */
    public Account(String accountHandle,String accountDescription){
        accountCount++;
        accountID = accountCount;
        this.accountHandle=accountHandle;
        this.accountDescription=accountDescription;
    }

    /**
     * Constructor method to create a new account.
     *
     * @param accountHandle The account handle.
     */
    public Account(String accountHandle){
        accountCount++;
        accountID = accountCount;
        this.accountHandle=accountHandle;
    }

    /**
     * Get the account ID.
     *
     * @return The account ID.
     */
    public int getAccountID(){
        return accountID;
    }

    /**
     * Get the account handler.
     *
     * @return The account handle.
     */
    public String getAccountHandle(){
        return accountHandle;
    }

    /**
     * Get the account description.
     *
     * @return The account description.
     */
    public String getAccountDescription(){
        return accountDescription;
    }

    /**
     * Get the account's posts.
     *
     * @return The account's posts.
     */
    public ArrayList<Integer> getAccountsPosts(){
        return accountsPosts;
    }

    /**
     * Get the account's endorsements posts.
     *
     * @return The account's endorsements posts.
     */
    public ArrayList<Integer> getAccountsEndorsementPosts(){
        return accountsEndorsementPosts;
    }


    /**
     * Set the account's handle.
     *
     * @param accountHandle  The account's handle.
     */
    public void setAccountHandle(String accountHandle){
        this.accountHandle=accountHandle;
    }

    /**
     * Set the account's description.
     *
     * @param accountDescription  The account's description.
     */
    public void setAccountDescription(String accountDescription) {
        this.accountDescription = accountDescription;
    }

    /**
     * Set the account's posts.
     *
     * @param accountsPosts  The account's post ids.
     */
    public void setAccountsPosts(ArrayList<Integer> accountsPosts) {
        this.accountsPosts = accountsPosts;
    }

    /**
     * Set the account's endorsements.
     *
     * @param accountsEndorsementPosts  The account's endorsement post ids.
     */
    public void setAccountsEndorsementPosts(ArrayList<Integer> accountsEndorsementPosts) {
        this.accountsEndorsementPosts = accountsEndorsementPosts;
    }
}
