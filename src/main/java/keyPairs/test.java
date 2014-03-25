package keyPairs;

import clientRest.CheckeReceiptThread;
import clientRest.ClientRest;
import entity.FileArrivalMsg;
import entity.User;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by xwen on 3/12/14.
 */
public class test {

    public static ArrayList<FileArrivalMsg> arrivalFileList = new ArrayList<FileArrivalMsg>();

    public static ArrayList<String> uploadedFileList = new ArrayList<String>();

    static Thread thread1;
    static Thread thread2;
    static ArrayList<FileArrivalMsg> list;

    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, SignatureException, InvalidKeyException, IllegalBlockSizeException, InterruptedException {
        ClientRest clientRest = new ClientRest();
        User user;
        User receiver;

        //String userEmail = "690081332@qq.com";

        Scanner sc = new Scanner(System.in);
        System.out.println("please input your email address");
        String userEmail = sc.nextLine();

        if (clientRest.isExist(userEmail)) {
            System.out.println("you have registered at TDS before");
            user = UserFileIO.getUserInfoFromFile(userEmail);
          //  System.out.println(user.getUserEmailAddress() + " " + user.getPublicKey().length + " " + user.getPrivateKey().length + " " + user.getSign().length);


        } else {
            System.out.println("you are a new user of TDS");
            user = new User(userEmail, 1024);
            PrivateKey privateKey = GenerateKey.generatePrivateKey(user.getPrivateKey());
            user.setSign(user.generateSign(privateKey));

            UserFileIO.generateUserInfoFile(user);
        }

        clientRest.requestForConnect(user);

        System.out.println("you have got your access permission");

        String receiverEmail = "lifanchenjulius@gmail.com";


//        if (clientRest.isExist(receiverEmail)) {
//            receiver = UserFileIO.getUserInfoFromFile(receiverEmail);
//        } else {
//            receiver = new User(receiverEmail, 1024);
//            PrivateKey privateKey = GenerateKey.generatePrivateKey(receiver.getPrivateKey());
//            receiver.setSign(receiver.generateSign(privateKey));
//
//            UserFileIO.generateUserInfoFile(receiver);
//        }
//
//        clientRest.requestForConnect(receiver);


       CheckeReceiptThread mainThread = new CheckeReceiptThread(user);

        thread1 = new Thread(mainThread);
        thread1.start();

        System.out.println("input 1 if you want to upload file");


        /*
        0: abort operation
        1: upload file
        2: download file
         */
        int index = sc.nextInt();
        while((index != 1) &&( index != 2) && (index != 0)){
            System.out.println("try again");
            index = sc.nextInt();
        }

        if(index == 1){
            try {
                clientRest.requestForUploadFile(user, receiverEmail, "1.txt");
                System.out.println("sender has sent the file");
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
        if(index == 2){
            if(arrivalFileList.size() == 0){
                System.out.println("there is no file for you now");
            }
            else{
                for (int i = 0; i < arrivalFileList.size(); i++) {
                    System.out.println(i + " " + arrivalFileList.get(i));
                }
                System.out.println("choose one file you would like to download");
                int ii = sc.nextInt();
                while(ii < 0 || ii > arrivalFileList.size() - 1) {
                    System.out.println("please input again");
                    ii = sc.nextInt();
                }

                    clientRest.requireForFile(arrivalFileList.get(ii).getLabel(), arrivalFileList.get(ii).getEOO(), user);
                    arrivalFileList.remove(ii);

            }
        }

        if(index == 0){
            if(arrivalFileList.size() == 0){
                System.out.println("there is no file for you now");
            }
            else{
                for (int i = 0; i < arrivalFileList.size(); i++) {
                    System.out.println(i + " " + arrivalFileList.get(i));
                }
                System.out.println("choose one file you would like to download");
                int ii = sc.nextInt();
                while(ii < 0 || ii > arrivalFileList.size() - 1) {
                    System.out.println("please input again");
                    ii = sc.nextInt();
                }
                clientRest.abortTransaction(arrivalFileList.get(ii).getLabel(), user);

                arrivalFileList.remove(ii);

            }
        }






     //   list = clientRest.getFlieArrivalMsg(receiver);
//
//        System.out.println(list.size());
//        if (list.size() != 0) {
//            for (int i = 0; i < list.size(); i++) {
//                System.out.println(list.get(i).getLabel() + " " + list.get(i).getEOO().length);
//            }
//        }

////
////       //Thread.sleep(1000);
        //clientRest.requireForFile(list.get(list.size() - 1).getLabel(), list.get(list.size() - 1).getEOO(), receiver);

    //    clientRest.requestForReceipt(user);

//

//

    }
}

