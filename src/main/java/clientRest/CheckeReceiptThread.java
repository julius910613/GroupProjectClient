package clientRest;

import entity.User;
import keyPairs.test;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 19/03/14
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class CheckeReceiptThread implements Runnable {

    static Thread listenThread;
    static ClientRest clientRest = new ClientRest();

    User sender;


   public CheckeReceiptThread(User user){
      this.sender = user;

   }

    @Override
    public void run() {
        //To change body of implemented methods use File | Settings | File Templates.

        CheckArrivalFileThread lt = new CheckArrivalFileThread(sender);
        listenThread = new Thread(lt) ;
        listenThread.start();
       // listenThread = new Thread(lt);
         while(true){

             try {
                 if(test.uploadedFileList.size() != 0) {
                     for(int j = 0; j < test.uploadedFileList.size(); j ++){
                         if(clientRest.requestForReceipt(sender, test.uploadedFileList.get(j)) == true){
                               test.uploadedFileList.remove(j) ;
                         }
                     }

                 }

                 Thread.sleep(30000);
             } catch (InterruptedException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             }
         }



    }
}
