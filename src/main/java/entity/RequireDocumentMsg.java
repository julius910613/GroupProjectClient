package entity;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 09/03/14
 * Time: 18:07
 * To change this template use File | Settings | File Templates.
 */
public class RequireDocumentMsg {

    private String label;

    private byte[] EORofReceiver;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public byte[] getEORofReceiver() {
        return EORofReceiver;
    }

    public void setEORofReceiver(byte[] EORofReceiver) {
        this.EORofReceiver = EORofReceiver;
    }
}
