package entity;

/**
 * Created by xwen on 3/17/14.
 */
public class FileArrivalMsg {



    private String label;



    private byte[] EOO;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public byte[] getEOO() {
        return EOO;
    }

    public void setEOO(byte[] EOO) {
        this.EOO = EOO;
    }
}

