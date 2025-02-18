package work.nicey.picture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;
import work.nicey.picture.api.aliyun.model.CreateOutPaintingTaskResponse;
import work.nicey.picture.model.dto.picture.*;
import work.nicey.picture.model.entity.User;
import work.nicey.picture.model.vo.PictureVO;
import work.nicey.picture.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
* @author Zhaoyu
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2025-01-11 16:12:05
*/
public interface PictureService extends IService<Picture> {
    /**
     * 上传图片
     *
     * @param multipartFile
     * @param pictureUploadRequest
     * @param loginUser
     * @return
     */
    PictureVO uploadPicture(MultipartFile multipartFile,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser) throws IOException;

    /**
     * 上传图片 (根据url)
     *
     * @param fileUrl
     * @param pictureUploadRequest
     * @param loginUser
     * @return
     */
    PictureVO uploadPictureByUrl(String fileUrl,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser) throws IOException;

    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    void validPicture(Picture picture);

    /**
     * 图片审核
     *
     * @param pictureReviewRequest
     * @param loginUser
     */
    void doPictureReview(PictureReviewRequest pictureReviewRequest, User loginUser);

    /**
     * 填充审核参数
     *
     * @param picture
     * @param loginUser
     */
    void fillReviewParams(Picture picture, User loginUser);

    /**
     * 删除图片
     *
     * @param pictureId
     * @param loginUser
     */
    void deletePicture(long pictureId, User loginUser);

    /**
     * 校验空间图片的权限
     *
     * @param loginUser
     * @param picture
     */
    void checkPictureAuth(User loginUser, Picture picture);

    /**
     * 编辑图片
     *
     * @param pictureEditRequest
     * @param loginUser
     */
    void editPicture(PictureEditRequest pictureEditRequest, User loginUser);

    /**
     * 创建扩图任务
     *
     * @param createPictureOutPaintingTaskRequest
     * @param loginUser
     */
    CreateOutPaintingTaskResponse createPictureOutPaintingTask(CreatePictureOutPaintingTaskRequest createPictureOutPaintingTaskRequest, User loginUser);

}
