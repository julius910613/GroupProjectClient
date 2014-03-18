package keyPairs;

import clientRest.ClientRest;
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
    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, SignatureException, InvalidKeyException, IllegalBlockSizeException {
        ClientRest clientRest=new ClientRest();
        User user = new User("123@gmail.com", 1024);

    //    System.out.println(user.getPublicKey().toString());

        PrivateKey privateKey = GenerateKey.generatePrivateKey(user.getPrivateKey());
        PublicKey publicKey = GenerateKey.generatePublicKey(user.getPublicKey());
        user.setSign(user.generateSign(privateKey));
        byte[] a = user.getSign();
    //    System.out.println(new String(user.getSign(),"UTF-8"));


          clientRest.requestForConnect(user);
          //clientRest.requestForConnect(user);
        try {
            clientRest.requestForUploadFile(user);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        User receiver = new User("111@aa.com", 1024);
        try {
            PrivateKey privateKey1 = GenerateKey.generatePrivateKey(receiver.getPrivateKey());
            receiver.setSign(user.generateSign(privateKey1));
            clientRest.requestForConnect(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }


            System.out.println("Get EOO");

            byte[] EOO=clientRest.getEOO("1");
            System.out.println(EOO.length);
            byte[] b =Keys.encrypt(GenerateKey.generatePrivateKey(receiver.getPrivateKey()),HashDocument.generateHash(EOO));
            byte[] c =Keys.decrypt(GenerateKey.generatePublicKey(receiver.getPublicKey()),b);
        System.out.println(Arrays.equals(HashDocument.generateHash(EOO),c));





        System.out.println("------------------------");






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
//                if (Arrays.equals(a, user.getUserEmailAddress().getBytes())) {
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
