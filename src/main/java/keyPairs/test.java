package keyPairs;

import clientRest.ClientRest;
import clientRest.MainThread;
import entity.FileArrivalMsg;
import entity.User;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by xwen on 3/12/14.
 */
public class test {

    static Thread thread;

    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, SignatureException, InvalidKeyException, IllegalBlockSizeException, InterruptedException {
        ClientRest clientRest=new ClientRest();



        User user = new User("123@gmail.com", 1024);

    //    System.out.println(user.getPublicKey().toString());

        PrivateKey privateKey = GenerateKey.generatePrivateKey(user.getPrivateKey());
        //PublicKey publicKey = GenerateKey.generatePublicKey(user.getPublicKey());
        user.setSign(user.generateSign(privateKey));
        byte[] a = user.getSign();
    //    System.out.println(new String(user.getSign(),"UTF-8"));

        UserFileIO.generateUserInfoFile(user);

          clientRest.requestForConnect(user);
      //  clientRest.requestForConnect(user);
          //clientRest.requestForConnect(user);
        User receiver = new User("111@aa.com", 1024);
        try {
            PrivateKey privateKey1 = GenerateKey.generatePrivateKey(receiver.getPrivateKey());
            receiver.setSign(receiver.generateSign(privateKey1));
            User testUser = UserFileIO.getUserInfoFromFile(user.getUserEmailAddress());

            System.out.println(Arrays.equals(user.getPrivateKey(), testUser.getPrivateKey()));

            clientRest.requestForConnect(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }


       // MainThread mainThread = new MainThread(user, receiver);

//        thread = new Thread(mainThread);
//        thread.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

        try {
            clientRest.requestForUploadFile(user);
            System.out.println("sender has sent the file");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }





        System.out.println("Get EOO");

//            byte[] EOO=clientRest.getEOO("1");
//            System.out.println(EOO.length);
//            byte[] b =Keys.encrypt(GenerateKey.generatePrivateKey(receiver.getPrivateKey()),HashDocument.generateHash(EOO));
//            byte[] c =Keys.decrypt(GenerateKey.generatePublicKey(receiver.getPublicKey()),b);
//        System.out.println(Arrays.equals(HashDocument.generateHash(EOO),c));


       ArrayList<FileArrivalMsg> list = clientRest.getFlieArrivalMsg(receiver);

        System.out.println(list.size());
        if(list.size() != 0){
            for(int i = 0; i < list.size(); i ++){
                System.out.println(list.get(i).getLabel() + " " + list.get(i).getEOO().length);
            }
        }


       //Thread.sleep(1000);
        clientRest.requireForFile(list.get(0).getLabel(), list.get(0).getEOO(),receiver);

        clientRest.requestForReceipt(user, "1");


        clientRest.abortTransaction("1", user);

       //






//        X509EncodedKeySpec pubString = new X509EncodedKeySpec(user.getPublicKey());
//        PKCS8EncodedKeySpec priString = new PKCS8EncodedKeySpec(user.getPrivateKey());
//        try {
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            PrivateKey privateKey = keyFactory.generatePrivate(priString);
//            PublicKey publicKey = keyFactory.generatePublic(pubString);
//            System.out.println(Arrays.equals(privateKey.getEncoded(), user.getPrivateKey()));
//            System.out.println(Arrays.equals(publicKey.getEncoded(), user.getPublicKey()));
//            user.setSign(privateKey);
//            try {
//                byte[] a = Keys.decrypt(publicKey, user.getSign());
//                if (Arrays.equals(a, user.getUserEmailAddress().getBytes())) {                                                 [
//                    System.out.println("2");
//                }
//            } catch (InvalidKeyException e) {
//                e.printStackTrace();
//            } catch (SignatureException e) {
//                e.printStackTrace();
//            } catch (NoSuchPaddingException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (BadPaddingException e) {
//                e.printStackTrace();
//            } catch (IllegalBlockSizeException e) {
//                e.printStackTrace();
//            }
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
//        }


        // clientRest.addUser(user);

        // System.out.println(clientRest.isExist("123@gmail.com"));

    }
}
