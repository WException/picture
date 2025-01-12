package work.nicey.picture.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.nicey.picture.config.OSSClientConfig;

import java.io.File;

@Component
public class AliOssUtil {

    @Autowired
    private OSS ossClient;

    @Autowired
    private OSSClientConfig ossClientConfig;

    /**
     * 上传对象
     *
     * @param key  唯一键
     * @param file 文件
     */
    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossClientConfig.getBucketName(), key, file);
        return ossClient.putObject(putObjectRequest);
    }

    /**
     * 下载对象
     *
     * @param key 唯一键
     */
    public OSSObject getObject(String key) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(ossClientConfig.getBucketName(), key);
        return ossClient.getObject(getObjectRequest);
    }

}
