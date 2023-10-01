package socialmedia;

/**
 * Comment is a class to create new comments and performs comments
 * related methods within the system. This class inherits the Post class
 */
public class Comment extends Post {
    /**
     * Attribute to store the ID of the post that is being commented on
     */
    private final int originalPostID;

    /**
     * Constructor method to create a new comment.
     *
     * @param handle The new account that is creating the comments handle.
     * @param postContent The content of the account.
     * @param originalPostID The Post ID of the post that is being commented.
     */
    public Comment(String handle, String postContent,  int originalPostID) {
        super(handle, postContent);
        this.originalPostID=originalPostID;
    }

    /**
     * Get the original post's ID.
     *
     * @return original post's ID.
     */
    public int getOriginalPostID() { return originalPostID; }

}
