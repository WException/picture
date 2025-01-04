package work.nicey.picture.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import work.nicey.picture.model.entity.User;
import work.nicey.picture.service.UserService;
import work.nicey.picture.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Zhaoyu
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-12-30 17:03:00
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




