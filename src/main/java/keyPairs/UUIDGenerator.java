package keyPairs;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 22/03/14
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 */
public class UUIDGenerator {

    public  UUIDGenerator(){

    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
        return str+","+temp;
    }
}
