package clientRest;

import entity.FileArrivalMsg;
import entity.User;
import keyPairs.GenerateKey;

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
public class ListenThread implements Runnable {

    private User receiver;
    private ClientRest clientRest = new ClientRest();

    public  ListenThread(User user){
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
                    System.out.println("______________________________");
                    System.out.println("you got " + list.size() + "file");
                     Thread.sleep(1000);
                     clientRest.requireForFile(list.get(0).getLabel(), list.get(0).getEOO(),receiver);
                 }
                 Thread.sleep(30000);
             } catch (UnsupportedEncodingException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             } catch (InterruptedException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             } catch (NoSuchPaddingException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             } catch (NoSuchAlgorithmException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             } catch (IllegalBlockSizeException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             } catch (BadPaddingException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             } catch (SignatureException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             } catch (InvalidKeyException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             } catch (IOException e) {
                 e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
             }

         }

    }


}
