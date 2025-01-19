package work.nicey.picture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;
import work.nicey.picture.model.dto.picture.PictureQueryRequest;
import work.nicey.picture.model.dto.picture.PictureUploadRequest;
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

    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    void validPicture(Picture picture);
}
