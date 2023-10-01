package socialmedia;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Post is a class to create new posts and perform post related methods within the system.
 */
public class Post implements Serializable {
    /**
     * Attribute to store the count of the number of posts that have been created
     */
    public static int postCount=0;

    /**
     * Attribute to store the ID of each post
     */
    protected int postID;

    /**
     * Attribute to store the handle of the account that the post belongs to
     */
    protected String handle;

    /**
     * Attribute to store the content of the post
     */
    protected String postContent;

    /**
     * Attribute to store the ids of the post that are comments of it
     */
    protected ArrayList<Integer> commentList = new ArrayList<>();

    /**
     * Attribute to store the ids of the post that are endorsements of it
     */
    protected ArrayList<Integer> endorsementList = new ArrayList<>();

    /**
     * Constructor method to create a new endorsement post.
     *
     * @param handle The new account that is creating the comments handle.
     * @param postContent The content of the account.
     */
    public Post(String handle,String postContent) {
        //postID incremented by one every time a new post is created to give it a unique id
        postCount++;
        postID=postCount;
        this.handle=handle;
        this.postContent=postContent;
    }

    /**
     * Get the post's Handle.
     *
     * @return post's Handle.
     */
    public String getHandle(){ return handle; }

    /**
     * Get the post's content.
     *
     * @return post's content.
     */
    public String getPostContent(){ return postContent; }

    /**
     * Get the post's ID.
     *
     * @return post's ID.
     */
    public Integer getPostID(){ return postID; }

    /**
     * Get the array list of the ids of the post's comments.
     *
     * @return post's comment list.
     */
    public ArrayList<Integer> getCommentList(){ return commentList; }

    /**
     * Get the array list of the ids of the post's endorsements.
     *
     * @return post's endorsement list.
     */
    public ArrayList<Integer> getEndorsementList(){ return endorsementList; }

    /**
     * Set the post's content.
     *
     * @param postContent content of the post
     */
    public void setPostContent(String postContent){
        this.postContent = postContent;
    }

    /**
     * Set the post's comment list.
     *
     * @param commentList list of the comments on the post
     */
    public void setCommentList(ArrayList<Integer> commentList) { this.commentList = commentList; }

    /**
     * Set the post's endorsement list.
     *
     * @param endorsementList list of the endorsements on the post
     */
    public void setEndorsementList(ArrayList<Integer> endorsementList) { this.endorsementList = endorsementList; }


    /**
     * Append list of comment IDs
     *
     * @param id comment's postID
     */
    public void addComment(int id){
        commentList.add(id);
    }

    /**
     * Append list of endorsement IDs
     *
     * @param id endorsement's postID
     */
    public void addEndorsement(int id){
        endorsementList.add(id);
    }
}

