package springBoot.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springBoot.controller.BaseController;
import springBoot.exception.TipException;
import springBoot.service.IContentService;
import springBoot.service.ILogService;
import springBoot.service.IMetaService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @Resource
    private IContentService contentService;

    @Resource
    private IMetaService metaService;

    @Resource
    private ILogService logService;

    @GetMapping(value = "")
    public String index(@RequestParam(value = "page",defaultValue = "1")int page,
                        @RequestParam(value = "limit",defaultValue = "15")int limit,
                        HttpServletRequest request){
        return "admin/article_list";
    }
}
