package springBoot.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import springBoot.controller.BaseController;
import springBoot.exception.TipException;

/**
 * 文章控制
 *
 * @author tangj
 * @date 2018/1/24 21:01
 */
@Controller
@RequestMapping("/admin.article")
@Transactional(rollbackFor = TipException.class)
public class ArticleController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);


}
