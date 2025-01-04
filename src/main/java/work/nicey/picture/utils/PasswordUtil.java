package work.nicey.picture.utils;

import cn.hutool.crypto.digest.DigestUtil;

public class PasswordUtil {


    public static String getEncryptPassword(String userPassword) {
        // 盐值，混淆密码
        final String SALT = "asdjiowdn..)))))";
        return DigestUtil.md5Hex((SALT + userPassword));
    }

}
