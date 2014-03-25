package clientRest;

import entity.FileArrivalMsg;
import entity.User;
import keyPairs.GenerateKey;
import keyPairs.test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 19/03/14
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public class CheckArrivalFileThread implements Runnable {

    private User receiver;
    private ClientRest clientRest = new ClientRest();

    public CheckArrivalFileThread(User user){
          this.receiver = user;
    }

    @Override
    public void run() {
        //To change body of implemented methods use File | Settings | File Templates.
       while (true){
             try {
               ArrayList<FileArrivalMsg> list = clientRest.getFlieArrivalMsg(receiver);
                 System.out.println(list.size());
                 if(list.size() == 0){
                    // System.out.println("no file for you");

                 }
                 else{
                     for(int i = 0; i < list.size(); i ++){
                         test.arrivalFileList.add(list.get(i));
                         System.out.println("you got a new file!");
                     }

                 }
                 Thread.sleep(30000);
             } catch (UnsupportedEncodingException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             } catch (InterruptedException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

             } catch (IOException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             }

         }

    }


}
