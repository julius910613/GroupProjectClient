package entity;

import keyPairs.Keys;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;

/**
 * Created by xwen on 3/13/14.
 */
public class UserSign {
    public String userEmailAddress;
    public byte[] sign;

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

    public byte[] getSign() {
        return sign;
    }

    public byte[] generateSign(PrivateKey privateKey){
        try {
             sign= Keys.encrypt(privateKey,userEmailAddress.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return sign;
    }

    public void setSign(byte[] sign) {
        this.sign = sign;
    }
}
