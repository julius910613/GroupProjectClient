package keyPairs;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
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
        KeyFactory keyFactory = null;

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
        KeyFactory keyFactory = null;

        PrivateKey publicKey = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePrivate(publicString);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return publicKey;
    }

}
