package entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 06/03/14
 * Time: 08:49
 * To change this template use Document | Settings | Document Templates.
 */
public class Document implements Serializable {


    private String fileName;
    private String senderName;
    private String receiverName;
    private String documentHashCode;

    public String getDocumentHashCode() {
        return documentHashCode;
    }

    public void setDocumentHashCode(String documentHashCode) {
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
