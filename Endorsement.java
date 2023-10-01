package socialmedia;

/**
 * Endorsement is a class to create new endorsements and performs endorsement
 * related methods within the system. This class inherits the Post class
 */
public class Endorsement extends Post{
    /**
     * Attribute to store the ID of the account that is being endorsed
     */
    private final int originalAccountID;

    /**
     * Attribute to store the ID of the post that is being endorsed
     */
    private final int originalPostID;

    /**
     * Constructor method to create a new endorsement post.
     *
     * @param handle The new account that is creating the comments handle.
     * @param postContent The content of the account.
     * @param originalPostID The Post ID of the post that is being commented
     * @param originalAccountID The account ID that the original post belongs to.
     */
    public Endorsement(String handle, String postContent, int originalAccountID, int originalPostID) {
        super(handle, postContent);
        this.originalAccountID=originalAccountID;
        this.originalPostID=originalPostID;
    }

    /**
     * Get the original account's ID.
     *
     * @return original account's ID.
     */
    public int getOriginalAccountID(){
        return originalAccountID;
    }

    /**
     * Get the original post's ID.
     *
     * @return original post's ID.
     */
    public int getOriginalPostID(){
        return originalPostID;
    }
}
