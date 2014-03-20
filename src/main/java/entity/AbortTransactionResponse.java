package entity;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 20/03/14
 * Time: 09:48
 * To change this template use File | Settings | File Templates.
 */
public class AbortTransactionResponse {

    String responseMsg;

    boolean deleteFlag;

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
}
