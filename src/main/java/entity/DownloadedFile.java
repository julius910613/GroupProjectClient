package entity;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 19/03/14
 * Time: 00:02
 * To change this template use File | Settings | File Templates.
 */
public class DownloadedFile {

    byte[] filebody;

    String fileName;

    String responseMsg;

    boolean getFile;

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public boolean isGetFile() {
        return getFile;
    }

    public void setGetFile(boolean getFile) {
        this.getFile = getFile;
    }

    public byte[] getFilebody() {
        return filebody;
    }

    public void setFilebody(byte[] filebody) {
        this.filebody = filebody;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
