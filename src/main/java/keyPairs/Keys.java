package keyPairs;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;

/**
 * Created by xwen on 3/6/14.
 */
public class Keys {
    private static SecureRandom sr = new SecureRandom();
    private static KeyPairGenerator kpg;
    private static KeyPair kp;

    private static PublicKey PubKey;
    private static PrivateKey PrivKey;

    public static KeyPair generateKeys(int keySize) throws NoSuchAlgorithmException, NoSuchProviderException {

        try {
            sr = SecureRandom.getInstance("SHA1PRNG");


            //KeyPairGenerator sr = KeyPairGenerator.getInstance("SHA1PRNG");
            kpg = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e1) {

            e1.printStackTrace();
        }
        try {
            kpg.initialize(keySize, sr);
        } catch (Exception e) {
            e.printStackTrace();

        }
        kp = kpg.genKeyPair();
        PubKey = kp.getPublic();
        PrivKey = kp.getPrivate();
        return kp;

//        System.out.println(PubKey.getEncoded().length);
//        System.out.println("------------");
//        System.out.println(PrivKey.getEncoded().length);

    }

    public static PublicKey getPubKey() {
        return PubKey;
    }

    public static PrivateKey getPrivKey() {
        return PrivKey;
    }

    public static byte[] encrypt(PrivateKey privKey, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privKey);
//        byte[] sbt = source.getBytes();
        byte[] epByte = cipher.doFinal(data);
//        BASE64Encoder encoder = new BASE64Encoder();
//        String epStr =  encoder.encode(epByte);
        return epByte;


    }

    public static byte[] decrypt(PublicKey pubKey, byte[] sign) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchPaddingException, IOException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        // BASE64Decoder decoder = new BASE64Decoder();
        // byte[] b1 = decoder.decodeBuffer(sign);

        byte[] b = cipher.doFinal(sign);
        return b;


    }
}
