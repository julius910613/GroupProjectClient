package entity;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 09/03/14
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionMsg {

    private boolean accessPermission;

    private String label;
    public ConnectionMsg(){}

    public boolean isAccessPermission() {
        return accessPermission;
    }

    public void setAccessPermission(boolean accessPermission) {
        this.accessPermission = accessPermission;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
