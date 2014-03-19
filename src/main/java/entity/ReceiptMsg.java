package entity;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 19/03/14
 * Time: 00:10
 * To change this template use File | Settings | File Templates.
 */
public class ReceiptMsg {

    byte[] EOR;

    boolean gotRecept;

    public byte[] getEOR() {
        return EOR;
    }

    public void setEOR(byte[] EOR) {
        this.EOR = EOR;
    }

    public boolean isGotRecept() {
        return gotRecept;
    }

    public void setGotRecept(boolean gotRecept) {
        this.gotRecept = gotRecept;
    }
}
