package entity;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 09/03/14
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public class UploadFilePackage {
    private Document document;

    private String label;

    private byte[] SignatureOfUser;

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

    public byte[] getSignatureOfUser() {
        return SignatureOfUser;
    }

    public void setSignatureOfUser(byte[] signatureOfUser) {
        SignatureOfUser = signatureOfUser;
    }
}
