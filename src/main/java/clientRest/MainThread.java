package clientRest;

import entity.User;
import keyPairs.GenerateKey;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 19/03/14
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class MainThread implements Runnable {

    static Thread listenThread;
    static ClientRest clientRest = new ClientRest();

    User sender;


   public MainThread(User user, User user2){
      this.sender = user;

   }

    @Override
    public void run() {
        //To change body of implemented methods use File | Settings | File Templates.

        ListenThread lt = new ListenThread(sender);
        listenThread = new Thread(lt) ;
        listenThread.start();
       // listenThread = new Thread(lt);
         while(true){

             try {
                 clientRest.requestForReceipt(sender);
                 Thread.sleep(30000);
             } catch (InterruptedException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             }
         }



    }
}
