package jamol.socialmedia.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "services.s3")
@Getter
@Setter
public class S3Config {
    private String endpoint;
    private String minioHost;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String region;
}

