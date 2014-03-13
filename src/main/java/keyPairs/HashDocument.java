package keyPairs;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.util.Arrays;

/**
 * Created by xwen on 3/6/14.
 */
public class HashDocument {
     static MessageDigest sha;

    {
        try {
            sha = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    static byte[] array ;
    private static final File dataDir = new File("/home/xwen/Downloads");
    private static final File document = new File(dataDir,"test");
    public static byte[] shaHash() throws IOException {
        // give hash code of a document
        FileInputStream fileInputStream=new FileInputStream(document);
        ByteArrayOutputStream outputStream =new ByteArrayOutputStream(1024);
        byte[] bytes = new byte[1024];
        int n;


        while ((n = fileInputStream.read(bytes)) != -1){
            outputStream.write(bytes,0,n);
        }

        array = outputStream.toByteArray();
        sha.update(array);
        byte[] hash1 = sha.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            sb.append(Integer.toString((array[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println(sb.toString());
        fileInputStream.close();
        outputStream.close();
        return hash1;
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, SignatureException, InvalidKeyException {
        System.out.println("-------");
        HashDocument encryAndValidate = new HashDocument();
        byte[] a =encryAndValidate.shaHash();
        //        encryAndValidate.signture(1024);
        Keys keys = new Keys();
        System.out.println("-------");
        keys.generateKeys(1024);
        byte[] bytes = keys.encrypt(Keys.getPrivKey(),a);
        byte[] d = keys.decrypt(Keys.getPubKey(),bytes);
        System.out.println(Arrays.equals(a,d)+"dd");
    }



}
