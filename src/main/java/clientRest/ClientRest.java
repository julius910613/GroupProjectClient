package clientRest;

import entity.ConnectionMsg;
import entity.UploadFilePackage;
import entity.User;

import keyPairs.GenerateKey;
import keyPairs.HashDocument;
import keyPairs.Keys;
import keyPairs.test;
import org.apache.commons.codec.binary.Base64;
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
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.*;

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

    private static final String REST_SERVICE_URL_USER = "http://localhost:8080/TTPService/requsetForConection";
    private static final String REST_SERVICE_URL = "http://localhost:8080/TTPService";
    private static final String REST_SERCICE_URL_UPLOADFILE = "http://localhost:8080/TTPService/uploadFile";
    private static final String REST_SERCICE_URL_UPLOADFILESERVICE = "http://localhost:8080/TTPService/uploadFileService";
    private static final String REST_SERCICE_URL_NOTICE = "http://localhost:8080/TTPService/noticeOfFileArrivalService";
    private static final String REST_SERVICE_GETEOOBYLEBEL = "http://localhost:8080/TTPService/getSignatureByLabel";
    private static final String REST_SERVICE_GETFILE = "http://localhost:8080/TTPService/requireGetDocumentService";
    private static final String REST_SERVICE_GETRECEIPT = "http://localhost:8080/TTPService/requireGetReceiptService";
    private static final String REST_SERVER_ABORT = "http://localhost:8080/TTPService/abortTransactionService";
    private static final String FILE_ROUTE = "D:\\1.txt";
    private static String label;
    private static ArrayList<String> labelList = new ArrayList<String>();
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

        Boolean user = client.target(REST_SERVICE_URL).path("/{UserEmail}").resolveTemplate("UserEmail", email).request().get(Boolean.class);
        if (user == false) {
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
    public ArrayList<FileArrivalMsg> getFlieArrivalMsg(User user) throws UnsupportedEncodingException {
        ArrayList<FileArrivalMsg> arrivalMsgs = new ArrayList<FileArrivalMsg>();
        // HashMap<String, byte[]> availableMsgs = new HashMap<String, byte[]>();

        ArrayList<String> labelList = client.target(REST_SERCICE_URL_NOTICE).request().post(Entity.entity(user, MediaType.APPLICATION_JSON), ArrayList.class);
//        String arrivalMsgs = client.target(REST_SERCICE_URL_NOTICE).request().post(Entity.entity(user,MediaType.APPLICATION_JSON),String.class);
        System.out.println(labelList.size());
        for (int i = 0; i < labelList.size(); i++) {
            byte[] b = getEOO(labelList.get(i));
            FileArrivalMsg fileArrivalMsg = new FileArrivalMsg();
            fileArrivalMsg.setLabel(labelList.get(i));
            fileArrivalMsg.setEOO(b);
            //  availableMsgs.put(labelList.get(i), b);
            arrivalMsgs.add(fileArrivalMsg);
        }


        return arrivalMsgs;
    }

    public byte[] getEOO(String label) {
        byte[] b = client.target(REST_SERVICE_GETEOOBYLEBEL).request().post(Entity.entity(label, MediaType.APPLICATION_JSON_TYPE), byte[].class);
        return b;
    }

    public UploadFilePackage uploadEOR(String label) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, SignatureException, InvalidKeyException {
        //UploadFilePackage uploadFilePackage=new UploadFilePackage();
        TransactionRecord t1 = client.target(REST_SERCICE_URL_UPLOADFILESERVICE).path("/{label}").resolveTemplate("label", label).request().get(TransactionRecord.class);
        User user = client.target(REST_SERVICE_URL_USER).path("/{userEmailAddress}").resolveTemplate("userEmailAddress", t1.getDocument().getReceiverName()).request().get(User.class);
        PrivateKey privateKey = GenerateKey.generatePrivateKey(user.getPrivateKey());
        t1.setReceiverSignature(Keys.encrypt(privateKey, HashDocument.generateHash(getEOO(label))));
        client.target(REST_SERCICE_URL_UPLOADFILESERVICE).request().post(Entity.entity(t1, MediaType.APPLICATION_JSON_TYPE), TransactionRecord.class);
        return null;
    }

    public void requireForFile(String label, byte[] senderSignature, User user) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, SignatureException, InvalidKeyException, IOException {
        RequireDocumentMsg requireDocumentMsg = new RequireDocumentMsg();
        requireDocumentMsg.setLabel(label);
        PrivateKey privateKey = GenerateKey.generatePrivateKey(user.getPrivateKey());
        byte[] hashbytes = HashDocument.generateHash(senderSignature);
        requireDocumentMsg.setEORofReceiver(Keys.encrypt(privateKey, hashbytes));
        byte[] b = Keys.decrypt(GenerateKey.generatePublicKey(user.getPublicKey()), requireDocumentMsg.getEORofReceiver());
        System.out.println(Arrays.equals(hashbytes, b));
        DownloadedFile response = client.target(REST_SERVICE_GETFILE).request().post(Entity.entity(requireDocumentMsg, MediaType.APPLICATION_JSON_TYPE), DownloadedFile.class);

        if (response.isGetFile()) {
            System.out.println("file got");
            String uploadedFileLocation = "E:\\" + response.getFileName();
            System.out.println(response.getDownloadURL());
            writeToFile(new ByteArrayInputStream(response.getFilebody()), uploadedFileLocation);
        } else {
            System.out.println(response.getResponseMsg());
        }

    }

    public void requestForConnect(User user) {

        User tmp = new User();
        tmp.setUserEmailAddress(user.getUserEmailAddress());
        tmp.setPublicKey(user.getPublicKey());
        byte[] a = Base64.decodeBase64("123");
        tmp.setPrivateKey(a);
        tmp.setSign(user.getSign());

        ConnectionMsg cm = client.target(REST_SERVICE_URL_USER).request().post(Entity.entity(tmp, MediaType.APPLICATION_JSON), ConnectionMsg.class);

        if (cm.isAccessPermission() == true) {
            label = cm.getLabel();
            System.out.println(label);
            labelList.add(label);
            //System.out.println("label"+label);

        }

    }

    public void requestForUploadFile(User user, String receiverEmail, String fileName) throws Exception {

        uploadFile();
        UploadFilePackage uploadFilePackage = new UploadFilePackage();
        uploadFilePackage.setLabel(labelList.get(labelList.size() - 1));
        uploadFilePackage.setSignatureOfUser(GenerateKey.encryptFileHashCode(GenerateKey.generatePrivateKey(user.getPrivateKey()), HashDocument.generateFileHashcode()));
        Document document = new Document();
        document.setSenderName(user.getUserEmailAddress());
        document.setFileName(fileName);
        document.setReceiverName(receiverEmail);
        uploadFilePackage.setDocument(document);

        String response = client.target(REST_SERCICE_URL_UPLOADFILESERVICE).request().post(Entity.entity(uploadFilePackage, MediaType.APPLICATION_JSON), String.class);

        test.uploadedFileList.add(uploadFilePackage.getLabel());
    }

    public boolean requestForReceipt(User user, String label) {
        CheckArrivalRequsetMsg checkArrivalRequsetMsg = new CheckArrivalRequsetMsg();
        checkArrivalRequsetMsg.setLabel(label);
        checkArrivalRequsetMsg.setSignatureOfSender(user.getSign());
        ReceiptMsg msg = client.target(REST_SERVICE_GETRECEIPT).request().post(Entity.entity(checkArrivalRequsetMsg, MediaType.APPLICATION_JSON), ReceiptMsg.class);
        if (msg.isGotRecept() == true) {
            System.out.println("receipt got");
        } else {
            System.out.println("no receipt");
        }
        return msg.isGotRecept();
    }

    private void writeToFile(InputStream uploadedInputStream,
                             String uploadedFileLocation) {

        try {
            OutputStream out = new FileOutputStream(new File(
                    uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public void uploadFile() throws Exception {

        File file = new File(FILE_ROUTE);
        //Upload the file
        executeMultiPartRequest(REST_SERCICE_URL_UPLOADFILE, file, file.getName(), "File Uploaded :: text.txt");
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

    public byte[] sendEOR(byte[] EOO, String label, User user) {
        PrivateKey privateKey = GenerateKey.generatePrivateKey(user.getPrivateKey());
        byte[] bytes = HashDocument.generateHash(EOO);
        try {
            byte[] b = GenerateKey.encryptFileHashCode(privateKey, bytes);
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

    public void abortTransaction(String label, User user) {
        AbortTransactionMsg abortTransactionMsg = new AbortTransactionMsg();
        abortTransactionMsg.setLabel(labelList.get(labelList.size() - 1));
        abortTransactionMsg.setUser(user);
        AbortTransactionResponse abortTransactionResponse = client.target(REST_SERVER_ABORT).request().post(Entity.entity(abortTransactionMsg, MediaType.APPLICATION_JSON), AbortTransactionResponse.class);

        if (abortTransactionResponse.isDeleteFlag()) {
            System.out.println("transaction has been abort");
        } else {
            System.out.println(abortTransactionResponse.getResponseMsg());
        }


    }

}
