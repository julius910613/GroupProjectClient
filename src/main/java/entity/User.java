package entity;

import keyPairs.Keys;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.Serializable;
import java.security.*;

/**
 * Created by xwen on 3/12/14.
 */
public class User extends UserSign{



    private  byte[] publicKey;


    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(byte[] privateKey) {
        this.privateKey = privateKey;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    private  byte[] privateKey;
//    public byte[] keyPair;

//
//    public byte[] getKeyPair() {
//        return keyPair;
//    }

    //    public User(String userEmailAddress,KeyPair keyPair){
//        this.userEmailAddress=userEmailAddress;
//        this.keyPair=keyPair;
//    }
    public User(String userEmailAddress,int i){
        super.userEmailAddress=userEmailAddress;
        try {
             KeyPair keyPair=Keys.generateKeys(i);
            this.privateKey=keyPair.getPrivate().getEncoded();
            this.publicKey=keyPair.getPublic().getEncoded();
            System.out.println("key");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }
    public User(){
        
    }
//    public  KeyPair getKeyPairs() throws NoSuchProviderException, NoSuchAlgorithmException {
//        KeyPair keyPair=Keys.generateKeys(1024);
//        return keyPair;
//    }






    @Override
    public String toString() {
        return "User [email =" + userEmailAddress + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (userEmailAddress != null ? !userEmailAddress.equals(user.userEmailAddress) : user.userEmailAddress != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return userEmailAddress != null ? userEmailAddress.hashCode() : 0;
    }
}