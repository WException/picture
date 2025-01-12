package work.nicey.picture.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.SignVersion;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oss.client")
@Data
public class OSSClientConfig {

    /**
     * 域名
     */
    private String host;

    /**
     * secretId
     */
    private String accessKeyId;

    /**
     * 密钥（注意不要泄露）
     */
    private String secretAccessKey;

    /**
     * 区域
     */
    private String region;

    /**
     * bucket名称
     */
    private String bucketName;

    @Bean
    public OSS oss() {
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, secretAccessKey);
        // 使用credentialsProvider初始化客户端
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        // 显式声明使用 V4 签名算法
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        // 创建OSSClient实例
        return OSSClientBuilder.create()
                .endpoint(host)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();
    }

}

