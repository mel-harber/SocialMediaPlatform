package socialmedia;

import java.io.*;
import java.util.*;

/**
 * SocialMedia is an implementor of the SocialMediaPlatform interface.
 */
public class SocialMedia implements SocialMediaPlatform {
    /**
     * List of the platforms accounts.
     */
    private final ArrayList<Account> accountObjects = new ArrayList<>();
    /**
     * List of the platforms posts.
     */
    private final ArrayList<Post> postObjects = new ArrayList<>();
    /**
     * List of the platforms comments.
     */
    private final ArrayList<Comment> commentObjects = new ArrayList<>();
    /**
     * List of the platforms endorsements.
     */
    private final ArrayList<Endorsement> endorsementObjects = new ArrayList<>();

    @Override
    public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
        // loops through list of account handles and throws an exception if it already exists
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountHandle(), handle)) {
                throw new IllegalHandleException("Account has already been created with this handle");
            }
        }
        // check handle is valid
        if ((handle.isEmpty()) || handle.contains(" ") || handle.length() > 30) {
            throw new InvalidHandleException("Invalid Account handle entered");
        }
        // create new account
        Account account = new Account(handle);
        accountObjects.add(account);
        return account.getAccountID();
    }


    @Override
    public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
        // loops through list of account handles and throws an exception if it already exists
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountHandle(), handle)) {
                throw new IllegalHandleException("Account has already been created with this handle");
            }
        }
        // check handle is valid
        if ((handle.isEmpty()) || handle.contains(" ") || handle.length() > 30) {
            throw new InvalidHandleException("Invalid Account handle entered");
        }
        // create new account
        Account account = new Account(handle, description);
        accountObjects.add(account);
        return account.getAccountID();
    }

    @Override
    public void removeAccount(int id) throws AccountIDNotRecognisedException {
        // TODO Auto-generated method stub
        boolean found = false;
        String handle = null;
        // loop through accounts to find the handle of the account being deleted
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountID(), id)) {
                accountObjects.remove(element);
                handle = element.getAccountHandle();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AccountIDNotRecognisedException("Account ID not recognised");
        }
        // deletes all posts belonging to the account
        for (Post element : postObjects) {
            if (Objects.equals(element.getHandle(), handle)) {
                try {
                    deletePost(element.getPostID());
                } catch (PostIDNotRecognisedException ignored) {
                }
            }
        }
    }


    @Override
    public void removeAccount(String handle) throws HandleNotRecognisedException {
        // TODO Auto-generated method stub
        boolean found = false;
        // loop through accounts to remove the account with the given handle
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountHandle(), handle)) {
                accountObjects.remove(element);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new HandleNotRecognisedException("Account Handle not recognised");
        }
        // deletes all posts belonging to the account
        for (Post element : postObjects) {
            if (Objects.equals(element.getHandle(), handle)) {
                try {
                    deletePost(element.getPostID());
                } catch (PostIDNotRecognisedException ignored) {
                }
            }
        }
    }


    @Override
    public void changeAccountHandle(String oldHandle, String newHandle) throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
        // TODO Auto-generated method stub
        boolean handleFound = false;
        // check old handle is a current handle in the system
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountHandle(), oldHandle)) {
                handleFound = true;
                break;
            }
        }
        if (!handleFound) {
            throw new HandleNotRecognisedException("The old handle does not match to any account in the system.");
        }
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountHandle(), newHandle)) {
                throw new IllegalHandleException("Account has already been created with this handle");
            }
        }
        // check new handle is valid and if so set it
        if ((newHandle.isEmpty()) || newHandle.contains(" ") || newHandle.length() > 30) {
            throw new InvalidHandleException("Invalid account handle entered");
        } else {
            for (Account element : accountObjects) {
                if (Objects.equals(element.getAccountHandle(), oldHandle)) {
                    element.setAccountHandle(newHandle);
                }
            }
        }
    }

    @Override
    public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
        // TODO Auto-generated method stub
        boolean found = false;
        // set account description as the given description
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountHandle(), handle)) {
                element.setAccountDescription(description);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new HandleNotRecognisedException("Account Handle not recognised");
        }

    }

    @Override
    public String showAccount(String handle) throws HandleNotRecognisedException {
        boolean found = false;
        String id = null;
        String description = null;
        String postCount = null;
        String endorseCount = null;
        // find all account related variables
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountHandle(), handle)) {
                found = true;
                id = Integer.toString(element.getAccountID());
                description = element.getAccountDescription();
                postCount = Integer.toString(element.getAccountsPosts().size());
                endorseCount = Integer.toString(element.getAccountsEndorsementPosts().size());
                break;
            }
        }
        if (!found) {
            throw new HandleNotRecognisedException("Account Handle not recognised");
        }
        // format string containing account information
        String accountInfo = "ID: " + id + " \nHandle: " +
                handle + " \nDescription: " + description
                + " \nPost Count: " + postCount + " \nEndorse count: " +
                endorseCount;
        return accountInfo;

    }

    @Override
    public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
        // TODO Auto-generated method stub
        boolean handleFound = false;
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountHandle(), handle)) {
                handleFound = true;
                break;
            }
        }
        if (!handleFound) {
            throw new HandleNotRecognisedException("No account exists with this handle");
        }

        if ((message.isEmpty()) || message.length() > 100) {
            throw new InvalidPostException("Message entered is invalid");
        }
        // create new post
        Post post = new Post(handle, message);
        postObjects.add(post);
        int id = post.getPostID();
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountHandle(), handle)) {
                ArrayList<Integer> list_of_ids = element.getAccountsPosts();
                list_of_ids.add(id);
                element.setAccountsPosts(list_of_ids);
                break;
            }
        }
        return id;
    }

    @Override
    public int endorsePost(String handle, int id)
            throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
        // TODO Auto-generated method stub
        // checks if a given handle exists
        int originalAccountID = 0;
        String originalAccountHandle = null;
        boolean account = false;
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountHandle(), handle)) {
                account = true;
                break;
            }
        }
        if (!account) {
            throw new HandleNotRecognisedException("No account exists with this handle");
        }
        // check if the id belongs to an endorsed post
        boolean isEndorsement = false;
        for (Post element : endorsementObjects) {
            if (Objects.equals(element.getPostID(), id)) {
                isEndorsement = true;
                break;
            }
        }
        if (isEndorsement) {
            throw new NotActionablePostException("Post is an endorsement post so can not be endorsed");
        }

        // check if there is a post to be endorsed
        boolean found = false;
        String originalMessage = null;
        for (Post element : postObjects) {
            if (Objects.equals(element.getPostID(), id)) {
                originalMessage = element.getPostContent();
                originalAccountHandle = element.getHandle();
                found = true;
            }
        }
        if (!found) {
            throw new PostIDNotRecognisedException("No post created with this ID");
        }
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountHandle(), originalAccountHandle)) {
                originalAccountID = element.getAccountID();
            }
        }
        // create new endorsement
        String postContent = "EP@" + originalAccountHandle + ":" + originalMessage;
        Endorsement endorsement = new Endorsement(handle, postContent, originalAccountID, id);
        endorsementObjects.add(endorsement);
        postObjects.add(endorsement);
        int newID = endorsement.getPostID();
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountHandle(), handle)) {
                ArrayList<Integer> list_of_ids = element.getAccountsPosts();
                list_of_ids.add(newID);
                element.setAccountsPosts(list_of_ids);
            }
            if (Objects.equals(element.getAccountID(), originalAccountID)) {
                ArrayList<Integer> endorse_list = element.getAccountsEndorsementPosts();

                endorse_list.add(newID);
                element.setAccountsEndorsementPosts(endorse_list);
            }
        }
        for (Post element : postObjects) {
            if (Objects.equals(element.getPostID(), id)) {
                element.addEndorsement(newID);
            }
        }
        return newID;
    }


    @Override
    public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
            PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
        // TODO Auto-generated method stub
        // check if given handle exists
        boolean account = false;
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountHandle(), handle)) {
                account = true;
                break;
            }
        }
        if (!account) {
            throw new HandleNotRecognisedException("No account exists with this handle");
        }
        // check if the ID belongs to an endorsed post
        boolean isEndorsement = false;
        for (Post element : endorsementObjects) {
            if (Objects.equals(element.getPostID(), id)) {
                isEndorsement = true;
            }
        }
        if (isEndorsement) {
            throw new NotActionablePostException("Post is an endorsement post so can not be directly commented");
        }

        // check if there is a post to be commented
        boolean found = false;
        for (Post element : postObjects) {
            if (Objects.equals(element.getPostID(), id)) {
                found = true;
            }
        }
        if (!found) {
            throw new PostIDNotRecognisedException("No post created with this ID");
        }
        if (message.length() > 100 || message.isEmpty()) {
            throw new InvalidPostException("Comment is invalid");
        }
        // create new comment
        Comment comment = new Comment(handle, message, id);
        commentObjects.add(comment);
        postObjects.add(comment);
        int newID = comment.getPostID();
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountHandle(), handle)) {
                ArrayList<Integer> list_of_ids = element.getAccountsPosts();
                list_of_ids.add(newID);
                element.setAccountsPosts(list_of_ids);
                break;
            }
        }
        for (Post element : postObjects) {
            if (Objects.equals(element.getPostID(), id)) {
                element.addComment(newID);
                break;
            }
        }
        return newID;
    }


    @Override
    public void deletePost(int id) throws PostIDNotRecognisedException {
        // TODO Auto-generated method stub
        // create iterators for the arraylists being edited in the loop
        Iterator<Post> postIterator = postObjects.iterator();
        Iterator<Comment> commentIterator = commentObjects.iterator();
        Iterator<Endorsement> endorsementIterator = endorsementObjects.iterator();
        boolean found = false;
        String accountHandle;
        int originalAccountID;
        int originalPostId;
        while (postIterator.hasNext()) {
            Post element = postIterator.next();
            // checks if the post is an endorsement
            if (element instanceof Endorsement) {
                // removes post from post list attribute of social media class
                if (Objects.equals(element.getPostID(), id) || Objects.equals(((Endorsement) element).getOriginalPostID(), id)) {
                    accountHandle = element.getHandle();
                    originalAccountID = ((Endorsement) element).getOriginalAccountID();
                    originalPostId = ((Endorsement) element).getOriginalPostID();
                    if (Objects.equals(((Endorsement) element).getOriginalPostID(), id)) {
                        id = element.getPostID();
                    }

                    postIterator.remove();
                    while (endorsementIterator.hasNext()) {
                        Endorsement endorsementElement = endorsementIterator.next();
                        if (endorsementElement == element) {
                            endorsementIterator.remove();
                        }
                    }
                    found = true;
                    for (Post post : postObjects) {
                        if (Objects.equals(post.getPostID(), originalPostId)) {
                            ArrayList<Integer> idList = post.getEndorsementList();
                            idList.remove(Integer.valueOf(id));
                            post.setEndorsementList(idList);
                        }
                    }
                    for (Account account : accountObjects) {
                        if (Objects.equals(account.getAccountID(), originalAccountID)) {
                            ArrayList<Integer> posts = account.getAccountsEndorsementPosts();
                            posts.remove(Integer.valueOf(id));
                            account.setAccountsEndorsementPosts(posts);
                        }
                        if (Objects.equals(account.getAccountHandle(), accountHandle)) {
                            ArrayList<Integer> posts = account.getAccountsPosts();
                            posts.remove(Integer.valueOf(id));
                            account.setAccountsPosts(posts);
                        }
                    }
                }
            // check if post is a comment
            } else if (element instanceof Comment) {
                if (Objects.equals(((Comment) element).getOriginalPostID(), id)) {
                    element.setPostContent("The original content was removed from the system and is no longer available");
                    found = true;
                }
                if (Objects.equals(element.getPostID(), id)) {
                    postIterator.remove();
                    while (commentIterator.hasNext()) {
                        Comment commentElement = commentIterator.next();
                        if (commentElement == element) {
                            commentIterator.remove();
                        }
                    }
                    found = true;
                    originalPostId = ((Comment) element).getOriginalPostID();
                    for (Post post : postObjects) {
                        if (Objects.equals(post.getPostID(), originalPostId)) {
                            ArrayList<Integer> idList = post.getCommentList();
                            idList.remove(Integer.valueOf(id));
                            post.setCommentList(idList);
                        }
                    }

                    accountHandle = element.getHandle();
                    for (Account account : accountObjects) {
                        if (Objects.equals(account.getAccountHandle(), accountHandle)) {
                            ArrayList<Integer> posts = account.getAccountsPosts();
                            posts.remove(Integer.valueOf(id));
                            account.setAccountsPosts(posts);
                        }
                    }
                }
            } else {
                // deletes post from list of all posts which is an attribute in social media class
                if (Objects.equals(element.getPostID(), id)) {
                    accountHandle = element.getHandle();
                    postIterator.remove();
                    found = true;
                    for (Account account : accountObjects) {
                        if (Objects.equals(account.getAccountHandle(), accountHandle)) {
                            ArrayList<Integer> posts = account.getAccountsPosts();
                            posts.remove(Integer.valueOf(id));
                            account.setAccountsPosts(posts);
                        }
                    }
                }
            }
        }
        if (!found) {
            throw new PostIDNotRecognisedException("Post ID not recognised");
        }

    }


    @Override
    public String showIndividualPost(int id) throws PostIDNotRecognisedException {
        // TODO Auto-generated method stub
        String handle = null;
        int endorseNum = 0;
        int commentNum = 0;
        String message = null;
        boolean found = false;
        // set post related variables
        for (Post element : postObjects) {
            if (Objects.equals(element.getPostID(), id)) {
                handle = element.getHandle();
                endorseNum = element.getEndorsementList().size();
                commentNum = element.getCommentList().size();
                message = element.getPostContent();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new PostIDNotRecognisedException("No post created with this ID");
        }
        // format post information into a string
        String postInfo = "ID: " + id + "\nAccount: " + handle + "\nNo. endorsements: " + endorseNum + " | No. comments: " + commentNum + "\n" + message;
        return postInfo;
    }


    /**
     * This method returns a string building containing the id of all the children posts of the given id
     *
     * @param id postID
     * @return subPost ids of all comments
     */
    public ArrayList<Integer> getSubPosts(int id) {
        // initialises an empty list
        ArrayList<Integer> subPosts = null;
        for (Post element : postObjects) {
            if (element.getPostID() == id) {
                // adds all comments of the given post to the list
                subPosts = element.getCommentList();
                // the list is sorted
                subPosts.sort(null);
                break;
            }
        }
        return subPosts;
    }

    /**
     * This method returns a string building containing the information of the post and the information of
     * all its children
     *
     * @param id       postID
     * @param postInfo StringBuilder
     * @param indent   String
     * @return a formatted StringBuilder containing the details of the post and its
     * children.
     */
    public StringBuilder iterateChildren(int id, StringBuilder postInfo, String indent) {
        // show details of children posts
        ArrayList<Integer> postsChildren = getSubPosts(id);
        // get the list iterator for the ArrayList
        ListIterator<Integer> iterator = postsChildren.listIterator();
        boolean addIndent;
        if (postsChildren.size() > 0) {
            while (iterator.hasNext()) {
                int childID = iterator.next();
                try {
                    postInfo.append(indent).append(showIndividualPost(childID));
                    iterator.remove();
                } catch (PostIDNotRecognisedException ignored) {
                }
                if (getSubPosts(childID).size() > 0) {
                    indent = indent + ">";
                    // recursively calls the function for each child post of each post
                    iterateChildren(childID, postInfo, indent);
                    StringBuilder subString = new StringBuilder(indent);
                    subString.deleteCharAt(indent.length() - 2);
                    indent = subString.toString();
                }
            }
        }
        return postInfo;
    }


    @Override
    public StringBuilder showPostChildrenDetails(int id)
            throws PostIDNotRecognisedException, NotActionablePostException {
        // TODO Auto-generated method stub
        boolean found = false;
        boolean isEndorsement = false;
        // check if post has children
        for (Post element : postObjects) {
            if (Objects.equals(element.getPostID(), id)) {
                if (element instanceof Endorsement) {
                    isEndorsement = true;
                }
                found = true;
                break;
            }
        }
        if (!found) {
            throw new PostIDNotRecognisedException("No post created with this ID");
        }
        if (isEndorsement) {
            throw new NotActionablePostException("Post is an endorsement so doesn't have any children");
        }
        // creates string builder with the original post info
        StringBuilder postInfo = new StringBuilder();
        // calls the function to create the string containing all children post information
        try {
            postInfo.append(showIndividualPost(id));
        } catch (PostIDNotRecognisedException ignore) {
        }
        postInfo = iterateChildren(id, postInfo, "\n\n>");
        return postInfo;
    }


    @Override
    public int getNumberOfAccounts() {
        // TODO Auto-generated method stub
        return accountObjects.size();
    }

    @Override
    public int getTotalOriginalPosts() {
        int counter = 0;
        for (Post element : postObjects) {
            if (!(element instanceof Endorsement || element instanceof Comment)) {
                counter = counter + 1;
            }
        }
        return counter;
    }

    @Override
    public int getTotalEndorsmentPosts() {
        // TODO Auto-generated method stub
        return endorsementObjects.size();
    }

    @Override
    public int getTotalCommentPosts() {
        // TODO Auto-generated method stub
        return commentObjects.size();
    }

    @Override
    public int getMostEndorsedPost() {
        int endorsementCount = 0;
        String handle = null;
        for (Post element : postObjects) {
            if (element.getEndorsementList().size() > endorsementCount) {
                endorsementCount = element.getEndorsementList().size();
                handle = element.getHandle();
            }
        }
        int accountId = 0;
        for (Account element : accountObjects) {
            if (Objects.equals(element.getAccountHandle(), handle)) {
                accountId = element.getAccountID();
            }
        }
        return accountId;
    }

    @Override
    public int getMostEndorsedAccount() {
        // TODO Auto-generated method stub
        int num = 0;
        int accountID = 0;
        for (Account element : accountObjects) {
            if ((element.getAccountsEndorsementPosts().size() > num)) {
                num = element.getAccountsEndorsementPosts().size();
                accountID = element.getAccountID();
            }
        }
        return accountID;
    }

    @Override
    public void erasePlatform() {
        // TODO Auto-generated method stub
        // deletes all account objects
        accountObjects.clear();
        Account.accountCount = 0;
        postObjects.clear();
        Post.postCount = 0;
        commentObjects.clear();
        endorsementObjects.clear();
    }

    @Override
    public void savePlatform(String filename) throws IOException {
        // TODO Auto-generated method stub
        // creates an arraylist for the counter variables to be stored in
        ArrayList<Integer> counters = new ArrayList<>();
        counters.add(Account.accountCount);
        counters.add(Post.postCount);
        // file is created
        FileOutputStream fileOut = new FileOutputStream(filename);
        ObjectOutputStream save = new ObjectOutputStream(fileOut);
        // arraylist are written to the new file
        try {
            save.writeObject(accountObjects);
            save.writeObject(postObjects);
            save.writeObject(commentObjects);
            save.writeObject(endorsementObjects);
            save.writeObject(counters);
            save.close();
            fileOut.close();
        } catch (Exception IOException) {
            throw new IOException();
        }
    }

    @Override
    public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
        // TODO Auto-generated method stub
        // all arrays and counters are reset to then be replaces
        erasePlatform();
        // file is opened
        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream load = new ObjectInputStream(fileIn);
        // each object read is cast to an arraylist of the given object type
        try {
        ArrayList<Object> accountList = (ArrayList<Object>) load.readObject();
        for (Object obj : accountList) {
            if (obj instanceof Account) {
                Account account = (Account) obj;
                accountObjects.add(account);
            }
        }
        ArrayList<Object> postList = (ArrayList<Object>) load.readObject();
        for (Object obj : postList) {
            if (obj instanceof Post) {
                Post post = (Post) obj;
                postObjects.add(post);
            }
        }
        ArrayList<Object> commentList = (ArrayList<Object>) load.readObject();
        for (Object obj : commentList) {
            if (obj instanceof Comment) {
                Comment comment = (Comment) obj;
                postObjects.add(comment);
            }
        }
        ArrayList<Object> endorsementList = (ArrayList<Object>) load.readObject();
        for (Object obj : endorsementList) {
            if (obj instanceof Endorsement) {
                Endorsement endorsement = (Endorsement) obj;
                postObjects.add(endorsement);
            }
        }
        ArrayList<Object> counters = (ArrayList<Object>) load.readObject();
        Account.accountCount = (int) counters.get(0);
        Post.postCount = (int) counters.get(1);
        load.close();
        fileIn.close();
        } catch (IOException Exception) {
            throw new IOException();
        } catch (ClassNotFoundException Exception) {
            throw new ClassNotFoundException();
        }
    }

}


