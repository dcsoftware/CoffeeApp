package it.blqlabs.appengine.coffeeappbackend;

/**
 * Created by davide on 28/10/14.
 */
public class UserBean {

    private String userId;
    private String userEmail;
    private String userCredit;
    private String lastSeen;

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(String userCredit) {
        this.userCredit = userCredit;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString() {
        return "userId=" + userId + ",userEmail=" + userEmail + ",userCredit=" + userCredit + ",lastSeen=" + lastSeen;
    }

}
