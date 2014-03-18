package entity;

/**
 * Created by xwen on 3/17/14.
 */
public class FileArrivalMsg {



    private String label;



    private byte[] senderSignature;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }




    public byte[] getSenderSignature() {
        return senderSignature;
    }

    public void setSenderSignature(byte[] senderSignature) {
        this.senderSignature = senderSignature;
    }
}

