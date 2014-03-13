package entity;

import java.io.Serializable;

/**
 * Created by xwen on 3/12/14.
 */
public class Document implements Serializable {


    private String fileName;
    private String senderName;
    private String receiverName;
    private byte[] documentHashCode;

    public byte[] getDocumentHashCode() {
        return documentHashCode;
    }

    public void setDocumentHashCode(byte[] documentHashCode) {
        this.documentHashCode = documentHashCode;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "File info [filename =" + fileName + ", receiverName=" + receiverName + ", senderName=" + senderName + "]";
    }


}