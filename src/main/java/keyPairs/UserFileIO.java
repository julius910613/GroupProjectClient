package keyPairs;

import entity.User;
import org.apache.commons.codec.binary.Base64;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 21/03/14
 * Time: 09:24
 * To change this template use File | Settings | File Templates.
 */
public class UserFileIO {

    public static void generateUserInfoFile(User user) throws IOException {
          String path = "e://" + user.getUserEmailAddress() + ".txt";

        FileWriter fw = new FileWriter(path, true);
        BufferedWriter bw=new BufferedWriter(fw);

        bw.write(user.getUserEmailAddress());
        bw.newLine();
        String str = new String(Base64.encodeBase64(user.getPublicKey()));
        bw.write(str);
        bw.newLine();
        str = new String(Base64.encodeBase64(user.getPrivateKey()));
        bw.write(str);
        bw.newLine();
        str = new String(Base64.encodeBase64(user.getSign()));
        bw.write(str);
        bw.close();
        fw.close();
    }

    public static User getUserInfoFromFile(String fileName) {
        String path = "e://" + fileName + ".txt";

        User user = new User();
       try{
        FileReader reader = new FileReader(path);
        BufferedReader br = new BufferedReader(reader);

        String str = null;

        str = br.readLine();

        user.setUserEmailAddress(str);
        str = br.readLine() ;
       user.setPublicKey(Base64.decodeBase64(str));
           str = br.readLine() ;
           user.setPrivateKey(Base64.decodeBase64(str));

           str = br.readLine() ;
           user.setSign(Base64.decodeBase64(str));
            //System.out.println(str);


        br.close();
        reader.close();

        // write string to file

    }
    catch(FileNotFoundException e) {
        e.printStackTrace();
    }
    catch(IOException e) {
        e.printStackTrace();
    }

   return user;
}
}
