package entity;

/**
 * Created by b3019229 on 18/03/14.
 */
public class TransactionRecord {

    Document document;

    String label;

    byte[] SenderSignature;

    byte[] ReceiverSignature;

    String statusOfTransaction;

    public String getStatusOfTransaction() {
        return statusOfTransaction;
    }

    public void setStatusOfTransaction(String statusOfTransaction) {
        this.statusOfTransaction = statusOfTransaction;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public  byte[] getSenderSignature() {
        return SenderSignature;
    }

    public void setSenderSignature( byte[] senderSignature) {
        SenderSignature = senderSignature;
    }

    public byte[] getReceiverSignature() {
        return ReceiverSignature;
    }

    public void setReceiverSignature(byte[] receiverSignature) {
        ReceiverSignature = receiverSignature;
    }

    @Override
    public String toString() {
        return "label" + label;
    }
}