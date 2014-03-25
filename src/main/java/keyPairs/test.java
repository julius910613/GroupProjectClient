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
    static ArrayList<FileArrivalMsg> list;

    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, SignatureException, InvalidKeyException, IllegalBlockSizeException, InterruptedException {
        ClientRest clientRest = new ClientRest();
        User user;
        User receiver;

        String userEmail = "690081332@qq.com";


        if (clientRest.isExist(userEmail)) {
            user = UserFileIO.getUserInfoFromFile(userEmail);
            System.out.println(user.getUserEmailAddress() + " " + user.getPublicKey().length + " " + user.getPrivateKey().length + " " + user.getSign().length);
        } else {
            user = new User(userEmail, 1024);
            PrivateKey privateKey = GenerateKey.generatePrivateKey(user.getPrivateKey());
            user.setSign(user.generateSign(privateKey));

            UserFileIO.generateUserInfoFile(user);
        }

        clientRest.requestForConnect(user);

        String receiverEmail = "lifanchenjulius@gmail.com";


        if (clientRest.isExist(receiverEmail)) {
            receiver = UserFileIO.getUserInfoFromFile(receiverEmail);
        } else {
            receiver = new User(receiverEmail, 1024);
            PrivateKey privateKey = GenerateKey.generatePrivateKey(receiver.getPrivateKey());
            receiver.setSign(receiver.generateSign(privateKey));

            UserFileIO.generateUserInfoFile(receiver);
        }

        clientRest.requestForConnect(receiver);


//       // MainThread mainThread = new MainThread(user, receiver);
//
////        thread = new Thread(mainThread);
////        thread.start();
////        try {
////            Thread.sleep(1000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
////        }
//
        try {
            clientRest.requestForUploadFile(user, receiverEmail, "1.txt");
            System.out.println("sender has sent the file");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        list = clientRest.getFlieArrivalMsg(receiver);
//
        System.out.println(list.size());
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).getLabel() + " " + list.get(i).getEOO().length);
            }
        }
        //clientRest.abortTransaction(list.get(list.size() - 1).getLabel(), user);
////
////       //Thread.sleep(1000);
        clientRest.requireForFile(list.get(list.size() - 1).getLabel(), list.get(list.size() - 1).getEOO(), receiver);

        clientRest.requestForReceipt(user);

//

//

    }
}

