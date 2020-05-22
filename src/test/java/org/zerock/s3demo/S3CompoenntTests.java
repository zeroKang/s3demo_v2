package org.zerock.s3demo;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.zerock.s3demo.s3.S3Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class S3CompoenntTests {

    @Autowired
    private S3Component s3Component;

    @Test
    public void testLoading() {

        System.out.println(s3Component);
    }

    @Test
    public void testPutFileWithDir()throws Exception {

        String fileName = "0121.pdf";
        File file = new File("/Users/zerock/Documents/" + fileName);

        System.out.println(file);

        String bucket="bootexbucket";

        String dirName ="2020/05/22/";

        AmazonS3Client s3Client = s3Component.getS3Client();

        s3Client.putObject(
                new PutObjectRequest(bucket, dirName+fileName, file)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        String uploadedURL = s3Client.getUrl(bucket, dirName+fileName).toString();
        System.out.println(uploadedURL);

    }

    @Test
    public void testPutFile()throws Exception {

        String fileName = "0121.pdf";
        File file = new File("/Users/zerock/Documents/" + fileName);

        System.out.println(file);

        String bucket="bootexbucket";

        AmazonS3Client s3Client = s3Component.getS3Client();

        s3Client.putObject(
                new PutObjectRequest(bucket, fileName, file)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        String uploadedURL = s3Client.getUrl(bucket, fileName).toString();
        System.out.println(uploadedURL);

    }

    @Test
    public void testPutFileWithStream()throws Exception {

        String fileName = "0121.pdf";
        File file = new File("/Users/zerock/Documents/" + fileName);
        InputStream fin = new FileInputStream(file);

        System.out.println(file);

        String bucket="bootexbucket";

        String dirName ="2020/05/22/";

        String urlLink = UUID.randomUUID().toString()+"_" +fileName;

        String objectKeyName = dirName + urlLink ;

        ObjectMetadata metadata = new ObjectMetadata();

        Optional<MediaType> mimeOption = MediaTypeFactory.getMediaType(fileName);

        System.out.println("MediaType: " + mimeOption.get().toString());

        if(mimeOption.isPresent()){
            metadata.setContentType(mimeOption.get().toString());
        }


        AmazonS3Client s3Client = s3Component.getS3Client();

        PutObjectRequest objectRequest =
                new PutObjectRequest(bucket, objectKeyName, fin, metadata ).withCannedAcl(CannedAccessControlList.PublicRead);

        s3Client.putObject(objectRequest);

        String uploadedURL = s3Client.getUrl(bucket, objectKeyName).toString();
        System.out.println(uploadedURL);

    }


}
