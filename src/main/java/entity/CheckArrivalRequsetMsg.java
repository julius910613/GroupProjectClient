package entity;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 10/03/14
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */
public class CheckArrivalRequsetMsg {

    String label;

    byte[] signatureOfSender;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public byte[] getSignatureOfSender() {
        return signatureOfSender;
    }

    public void setSignatureOfSender(byte[] signatureOfSender) {
        this.signatureOfSender = signatureOfSender;
    }
}
