package springBoot.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springBoot.controller.BaseController;
import springBoot.util.MyUtils;

import javax.annotation.Resource;


/**
 * 附件管理
 *
 * @author tangj
 * @date 2018/1/31 23:14
 */
@Controller
@RequestMapping("admin/attach")
public class AttachController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(AttachController.class);

    public static final String CLASSPATH = MyUtils.getUploadFilePath();

}
