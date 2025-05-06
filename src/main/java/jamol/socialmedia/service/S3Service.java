package jamol.socialmedia.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${services.s3.bucket-name}")
    private String bucketName;

    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * Faylni S3 bucket'ga yuklash
     */
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        // Faylni S3 serverga yuklash
        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, metadata);
            s3Client.putObject(putObjectRequest);
            return fileName;  // Faylning nomini qaytaramiz
        }
    }

    /**
     * Faylni olish uchun URL olish
     */
    public String getFileUrl(String fileName) {
        // Faylni olish uchun S3 URL yaratish
        return s3Client.getUrl(bucketName, fileName).toString();
    }

    /**
     * Faylni o‘chirish
     */
    public void deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
    }
}
