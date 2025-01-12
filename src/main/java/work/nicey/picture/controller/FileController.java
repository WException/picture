package work.nicey.picture.controller;

import com.aliyun.oss.model.OSSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import work.nicey.picture.common.BaseResponse;
import work.nicey.picture.common.ResultUtils;
import work.nicey.picture.exception.BusinessException;
import work.nicey.picture.exception.ErrorCode;
import work.nicey.picture.utils.AliOssUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;

@RestController
@Slf4j
public class FileController {

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 测试文件上传
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/test/upload")
    public BaseResponse<String> testUploadFile(@RequestPart("file") MultipartFile multipartFile) {
        // 文件目录
        String filename = multipartFile.getOriginalFilename();
        String filepath = String.format("test/%s", filename);
        File file = null;
        try {
            // 上传文件
            file = File.createTempFile(filepath, null);
            multipartFile.transferTo(file);
            aliOssUtil.putObject(filepath, file);
            // 返回可访问地址
            return ResultUtils.success(filepath);
        } catch (Exception e) {
            log.error("file upload error, filepath = " + filepath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            if (file != null) {
                // 删除临时文件
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error, filepath = {}", filepath);
                }
            }
        }
    }

    /**
     * 测试文件下载
     *
     * @param filepath 文件路径
     * @param response 响应对象
     */
    @GetMapping("/test/download/")
    public void testDownloadFile(String filepath, HttpServletResponse response) throws IOException {
        try {
            OSSObject ossObject = aliOssUtil.getObject(filepath);
            InputStream objectContent = ossObject.getObjectContent();
            // 处理下载到的流
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // 读取文件内容到字节数组。
            byte[] readBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = objectContent.read(readBuffer)) != -1) {
                byteArrayOutputStream.write(readBuffer, 0, bytesRead);
            }
            // 获取最终的字节数组。
            byte[] fileBytes = byteArrayOutputStream.toByteArray();
            // 设置响应头
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + filepath);
            // 写入响应
            response.getOutputStream().write(fileBytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("file download error, filepath = " + filepath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "下载失败");
        }
    }

}
