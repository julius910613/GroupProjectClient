package entity;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 20/03/14
 * Time: 09:32
 * To change this template use File | Settings | File Templates.
 */
public class AbortTransactionMsg {

    private String label;

    private User user;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
