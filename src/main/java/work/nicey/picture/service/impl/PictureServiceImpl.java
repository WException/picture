package work.nicey.picture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import work.nicey.picture.model.entity.Picture;
import work.nicey.picture.service.PictureService;
import work.nicey.picture.mapper.PictureMapper;
import org.springframework.stereotype.Service;

/**
* @author Zhaoyu
* @description 针对表【picture(图片)】的数据库操作Service实现
* @createDate 2025-01-11 16:12:05
*/
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture>
    implements PictureService{

}




