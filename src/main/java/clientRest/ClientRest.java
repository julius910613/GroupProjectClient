package clientRest;

import entity.ConnectionMsg;
import entity.UploadFilePackage;
import entity.User;

import keyPairs.GenerateKey;
import keyPairs.HashDocument;
import keyPairs.Keys;
import org.apache.http.entity.ContentType;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.glassfish.jersey.jackson.JacksonFeature;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.ArrayList;

import entity.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by xwen on 3/12/14.
 */
public class ClientRest {

    private static String label;

    private static final String REST_SERVICE_URL_USER = "http://localhost:8080/TTPService/requsetForConection";
    private static final String REST_SERVICE_URL = "http://localhost:8080/userService";
    private static final String REST_SERCICE_URL_UPLOADFILE = "http://localhost:8080/TTPService/uploadFile";
    private static final String REST_SERCICE_URL_UPLOADFILESERVICE = "http://localhost:8080/TTPService/uploadFileService";
    private static final String REST_SERCICE_URL_NOTICE = "http://localhost:8080/TTPService/noticeOfFileArrivalService";
    private static final String FILE_ROUTE = "H:\\CSC8109\\test.txt";
    Client client = ClientBuilder.newClient().register(JacksonFeature.class);

    public void addUser(User user) {
        if (user.equals(client.target(REST_SERVICE_URL).path("/{userEmailAddress}").resolveTemplate("userEmailAddress", user.getUserEmailAddress()).request().get(User.class))) {
            //client.target(REST_SERVICE_URL).request().post(Entity.entity())
            System.out.println("Already exits");
        } else {
            //User.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
            client.target(REST_SERVICE_URL_USER).request().post(Entity.entity(user, MediaType.APPLICATION_JSON), User.class);

            System.out.println("User has been registered");
        }
    }

    public boolean isExist(String email) {

        User user = client.target(REST_SERVICE_URL).path("/{userEmailAddress}").resolveTemplate("userEmailAddress", email).request().get(User.class);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }
//    class aaa extends ArrayList<FileArrivalMsg>{
//        public aaa(){
//            super();
//        }
//    }
    public ArrayList<FileArrivalMsg> getFlieArrivalMsg(User user) {
        ArrayList<FileArrivalMsg> arrivalMsgs = client.target(REST_SERCICE_URL_NOTICE).request().post(Entity.entity(user,MediaType.APPLICATION_JSON),ArrayList.class);
//        String arrivalMsgs = client.target(REST_SERCICE_URL_NOTICE).request().post(Entity.entity(user,MediaType.APPLICATION_JSON),String.class);

        return arrivalMsgs;
    }
    public byte[] getEOO(String label){
        byte[] b=client.target(REST_SERCICE_URL_NOTICE).request().post(Entity.entity(label, MediaType.APPLICATION_JSON_TYPE), byte[].class);
        return b;
    }
    public UploadFilePackage uploadEOR(String label) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, SignatureException, InvalidKeyException {
        //UploadFilePackage uploadFilePackage=new UploadFilePackage();
        TransactionRecord t1 = client.target(REST_SERCICE_URL_UPLOADFILESERVICE).path("/{label}").resolveTemplate("label",label).request().get(TransactionRecord.class);
        User user=client.target(REST_SERVICE_URL_USER).path("/{userEmailAddress}").resolveTemplate("userEmailAddress",t1.getDocument().getReceiverName()).request().get(User.class);
        PrivateKey privateKey = GenerateKey.generatePrivateKey(user.getPrivateKey());
        t1.setReceiverSignature(Keys.encrypt(privateKey,HashDocument.generateHash(getEOO(label))));
        client.target(REST_SERCICE_URL_UPLOADFILESERVICE).request().post(Entity.entity(t1, MediaType.APPLICATION_JSON_TYPE), TransactionRecord.class);
    }

    public void requestForConnect(User user) {

        ConnectionMsg cm = client.target(REST_SERVICE_URL_USER).request().post(Entity.entity(user, MediaType.APPLICATION_JSON), ConnectionMsg.class);
        System.out.println(cm.isAccessPermission());
        if (cm.isAccessPermission() == true) {
            label = cm.getLabel();
            //System.out.println("label"+label);

        }

    }


    public void requestForUploadFile(User user) throws Exception {

        uploadFile();
        UploadFilePackage uploadFilePackage = new UploadFilePackage();
        uploadFilePackage.setLabel("1");
        uploadFilePackage.setSignatureOfUser(GenerateKey.encryptFileHashCode(GenerateKey.generatePrivateKey(user.getPrivateKey()), HashDocument.generateFileHashcode()));
        Document document = new Document();
        document.setSenderName(user.getUserEmailAddress());
        document.setFileName("test.txt");
        document.setReceiverName("111@aa.com");
        uploadFilePackage.setDocument(document);

        String response = client.target(REST_SERCICE_URL_UPLOADFILESERVICE).request().post(Entity.entity(uploadFilePackage, MediaType.APPLICATION_JSON), String.class);

        System.out.println(response);

    }


    public void uploadFile() throws Exception {

        File file = new File(FILE_ROUTE);
        //Upload the file
        executeMultiPartRequest(REST_SERCICE_URL_UPLOADFILE, file, file.getName(), "File Uploaded :: Tulips.jpg");
    }

    public void executeMultiPartRequest(String urlString, File file, String fileName, String fileDescription) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(urlString);
        try {
            //Set various attributes
            MultipartEntity multiPartEntity = new MultipartEntity();
            multiPartEntity.addPart("fileDescription", new StringBody(fileDescription != null ? fileDescription : fileDescription));
            multiPartEntity.addPart("fileName", new StringBody(fileName != null ? fileName : file.getName()));

            FileBody fileBody = new FileBody(file, ContentType.APPLICATION_OCTET_STREAM);
            //Prepare payload
            multiPartEntity.addPart("file", fileBody);

            //Set to request body
            postRequest.setEntity(multiPartEntity);

            //Send request      UploadFilePackage
            HttpResponse response = client.execute(postRequest);

            //Verify response if any
            if (response != null) {
                System.out.println(response.getEntity().toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public byte[] sendEOR(byte[]EOO, String label, User user){
        PrivateKey privateKey=GenerateKey.generatePrivateKey(user.getPrivateKey());
        byte[] bytes=HashDocument.generateHash(EOO);
        try {
            byte[] b= GenerateKey.encryptFileHashCode(privateKey,bytes);
            return b;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;

    }


}
