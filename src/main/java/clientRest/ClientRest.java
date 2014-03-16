package clientRest;

import entity.ConnectionMsg;
import entity.UploadFilePackage;
import entity.User;

import keyPairs.GenerateKey;
import keyPairs.HashDocument;
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
import java.security.SignatureException;

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
    private static final String FILE_ROUTE = "D:\\1.txt";
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

    public void requestForConnect(User user) {

        ConnectionMsg cm = client.target(REST_SERVICE_URL_USER).request().post(Entity.entity(user, MediaType.APPLICATION_JSON), ConnectionMsg.class);
        System.out.println(cm.isAccessPermission());
        if(cm.isAccessPermission() == true){
            label = cm.getLabel();

        }

    }

    public void requestForUploadFile(User user) throws Exception {

        uploadFile();
        UploadFilePackage uploadFilePackage = new UploadFilePackage();
        uploadFilePackage.setLabel(label);
        uploadFilePackage.setSignatureOfUser(GenerateKey.encryptFileHashCode(GenerateKey.generatePrivateKey(user.getPrivateKey()),HashDocument.generateFileHashcode()));
        Document document = new Document();
        document.setSenderName(user.getUserEmailAddress());
        document.setFileName("1.txt");
        document.setReceiverName("111@aa.com");
        uploadFilePackage.setDocument(document);

        String response =  client.target(REST_SERCICE_URL_UPLOADFILESERVICE).request().post(Entity.entity(uploadFilePackage, MediaType.APPLICATION_JSON), String.class);

        System.out.println(response);

    }



    public void uploadFile() throws Exception {

        File file = new File(FILE_ROUTE);
        //Upload the file
        executeMultiPartRequest(REST_SERCICE_URL_UPLOADFILE,file, file.getName(), "File Uploaded :: Tulips.jpg");
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


}
