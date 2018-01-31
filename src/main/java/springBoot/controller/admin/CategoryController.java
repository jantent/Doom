package springBoot.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springBoot.constant.WebConst;
import springBoot.controller.BaseController;
import springBoot.dto.MetaDto;
import springBoot.dto.Types;
import springBoot.service.IMetaService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 分类标签管理
 *
 * @author tangj
 * @date 2018/1/31 22:56
 */
@Controller
@RequestMapping("admin/category")
public class CategoryController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Resource
    private IMetaService metaService;

    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
        List<MetaDto> categories = metaService.getMetaList(Types.CATEGORY.getType(), null, WebConst.MAX_POSTS);
        List<MetaDto> tags = metaService.getMetaList(Types.TAG.getType(), null, WebConst.MAX_POSTS);
        request.setAttribute("categories", categories);
        request.setAttribute("tags", tags);
        return "admin/category";
    }
}
