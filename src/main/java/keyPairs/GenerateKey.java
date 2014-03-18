package keyPairs;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 13/03/14
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */
public class GenerateKey {
    public static PublicKey generatePublicKey(byte[] publicByte) {
        X509EncodedKeySpec publicString = new X509EncodedKeySpec(publicByte);
        KeyFactory keyFactory;

        PublicKey publicKey = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(publicString);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return publicKey;
    }

    public static PrivateKey generatePrivateKey(byte[] privateByte) {
        PKCS8EncodedKeySpec publicString = new PKCS8EncodedKeySpec(privateByte);
        KeyFactory keyFactory;

        PrivateKey privateKey = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(publicString);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return privateKey;
    }

    public static byte[] encryptFileHashCode(PrivateKey privateKey, byte[] file) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, SignatureException, InvalidKeyException {


        return Keys.encrypt(privateKey, file);
    }

}
