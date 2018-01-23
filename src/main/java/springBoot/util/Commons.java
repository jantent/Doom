package springBoot.util;

import org.springframework.stereotype.Component;

/**
 * @author tangj
 * @date 2018/1/21 21:56
 */
 @Component
public class Commons {
    /**
     * 在管理员页面退出登录返回到登录界面
     * @return
     */
    public static String site_login() {
        return "admin/login";
    }

    /**
     * 获取随机数
     *
     * @param max
     * @param str
     * @return
     */
    public static String random(int max, String str) {
        return UUID.random(1, max) + str;
    }
}
