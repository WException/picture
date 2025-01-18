package work.nicey.picture.service.impl;

import com.aliyun.oss.model.PutObjectResult;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import work.nicey.picture.exception.ErrorCode;
import work.nicey.picture.exception.ThrowUtils;
import work.nicey.picture.model.dto.picture.PictureUploadRequest;
import work.nicey.picture.model.entity.Picture;
import work.nicey.picture.model.entity.User;
import work.nicey.picture.model.vo.PictureVO;
import work.nicey.picture.service.PictureService;
import work.nicey.picture.mapper.PictureMapper;
import org.springframework.stereotype.Service;
import work.nicey.picture.utils.AliOssUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
* @author Zhaoyu
* @description 针对表【picture(图片)】的数据库操作Service实现
* @createDate 2025-01-11 16:12:05
*/
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture>
    implements PictureService{

    @Autowired
    private AliOssUtil aliOssUtil;

    @Override
    public PictureVO uploadPicture(MultipartFile multipartFile, PictureUploadRequest pictureUploadRequest, User loginUser) throws IOException {
        ThrowUtils.throwIf(loginUser == null, ErrorCode.NO_AUTH_ERROR);
        // 用于判断是新增还是更新图片
        Long pictureId = null;
        if (pictureUploadRequest != null) {
            pictureId = pictureUploadRequest.getId();
        }
        // 如果是更新图片，需要校验图片是否存在
        if (pictureId != null) {
            boolean exists = this.lambdaQuery()
                    .eq(Picture::getId, pictureId)
                    .exists();
            ThrowUtils.throwIf(!exists, ErrorCode.NOT_FOUND_ERROR, "图片不存在");
        }

        String fileName = getJoinDateAndName(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        // 按照用户 id 划分目录
        String uploadPathPrefix = String.format("public/%s/%s", loginUser.getId(), fileName);
        // 得到信息，上传图片
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        PutObjectResult putObjectResult = aliOssUtil.putObject(uploadPathPrefix, multipartFile);
         // 通过MultipartFile得到InputStream，从而得到BufferedImage
        // 证明上传的文件不是图片，获取图片流失败，不进行下面的操作
        ThrowUtils.throwIf(null == bufferedImage, ErrorCode.PARAMS_ERROR, "文件不是图片格式");
        // 构造要入库的图片信息
        Picture picture = new Picture();
        picture.setUrl("https://work-nicey-picture.oss-cn-hangzhou.aliyuncs.com/" + uploadPathPrefix);
        picture.setName(fileName);
        picture.setPicSize(multipartFile.getSize());
        picture.setPicWidth(bufferedImage.getWidth());
        picture.setPicHeight(bufferedImage.getHeight());
        picture.setPicScale((double) (bufferedImage.getWidth() / bufferedImage.getHeight()));
        picture.setPicFormat(inferExtension(bufferedImage.getType()));
        picture.setUserId(loginUser.getId());
        // 如果 pictureId 不为空，表示更新，否则是新增
        if (pictureId != null) {
            // 如果是更新，需要补充 id 和编辑时间
            picture.setId(pictureId);
            picture.setEditTime(new Date());
        }
        boolean result = this.saveOrUpdate(picture);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR, "图片上传失败");
        return PictureVO.objToVo(picture);
    }


    private static String inferExtension(int type) {
        switch (type) {
            case BufferedImage.TYPE_INT_ARGB:
            case BufferedImage.TYPE_4BYTE_ABGR:
            case BufferedImage.TYPE_4BYTE_ABGR_PRE:
                return ".png"; // PNG格式通常支持透明度
            case BufferedImage.TYPE_INT_RGB:
            case BufferedImage.TYPE_3BYTE_BGR:
                return ".jpg"; // JPG是一种常见的图像格式，不支持透明度
            case BufferedImage.TYPE_BYTE_GRAY:
                return ".png"; // 灰度图像可以用PNG格式保存
            // 添加其他类型的映射
            default:
                return ".bmp"; // 默认使用BMP格式，它是一种不压缩的格式
        }
    }


    /**
     * 将当前时间拼接到文件名后。精确到毫秒
     * @param fileName 文件名
     * @return 拼接后的文件名 xxx_2024012312333.jpg
     */
    private String getJoinDateAndName(String fileName) {
        // 获取当前日期时间精确到毫秒，用于文件名拼接
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedDateTime = now.format(formatter);
        // 找到文件名和后缀名的分隔点
        int dotIndex = fileName.lastIndexOf(".");

        // 拼接新的文件名
        String newFileName;
        if (dotIndex > 0) { // 确保有后缀名
            String fileNameWithoutExtension = fileName.substring(0, dotIndex);
            String fileExtension = fileName.substring(dotIndex);
            newFileName = fileNameWithoutExtension + "_" + formattedDateTime + fileExtension;
        } else {
            // 如果没有后缀名，直接在文件名后添加时间戳
            newFileName = fileName + "_" + formattedDateTime;
        }
        return newFileName;
    }
}




