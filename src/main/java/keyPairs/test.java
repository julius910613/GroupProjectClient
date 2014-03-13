package keyPairs;

import clientRest.ClientRest;
import entity.User;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * Created by xwen on 3/12/14.
 */
public class test {
    public static void main(String[] args){
       // ClientRest clientRest=new ClientRest();
        User user=new User("123@gmail.com",1024);
       System.out.println(user.getPublicKey().toString());
        X509EncodedKeySpec pubString=new X509EncodedKeySpec(user.getPublicKey());
        PKCS8EncodedKeySpec priString = new PKCS8EncodedKeySpec(user.getPrivateKey());
        try {
            KeyFactory keyFactory =  KeyFactory.getInstance("RSA");
            PrivateKey privateKey=keyFactory.generatePrivate(priString);
            PublicKey publicKey=keyFactory.generatePublic(pubString);
            System.out.println(Arrays.equals(privateKey.getEncoded(),user.getPrivateKey()));
            System.out.println(Arrays.equals(publicKey.getEncoded(),user.getPublicKey()));
            user.setSign(privateKey);
            try {
                byte[] a=Keys.decrypt(publicKey,user.getSign());
                if(Arrays.equals(a,user.getUserEmailAddress().getBytes())){
                    System.out.println("2");
                }
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (SignatureException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }



        // clientRest.addUser(user);

       // System.out.println(clientRest.isExist("123@gmail.com"));

    }
}