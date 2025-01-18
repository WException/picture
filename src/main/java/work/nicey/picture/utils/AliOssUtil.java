package work.nicey.picture.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import work.nicey.picture.common.ResultUtils;
import work.nicey.picture.config.OSSClientConfig;
import work.nicey.picture.exception.BusinessException;
import work.nicey.picture.exception.ErrorCode;

import java.io.File;

@Slf4j
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

    /**
     * 上传对象
     *
     * @param key  唯一键
     * @param multipartFile 文件
     */
    public PutObjectResult putObject(String key, MultipartFile multipartFile) {
        // 上传文件
        File file = null;
        try {
            // 上传文件
            file = File.createTempFile(key, null);
            multipartFile.transferTo(file);
            return putObject(key, file);
        } catch (Exception e) {
            log.error("file upload error, filepath = " + key, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            if (file != null) {
                // 删除临时文件
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error, filepath = {}", key);
                }
            }
        }
    }
}
