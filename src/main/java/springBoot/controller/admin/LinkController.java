package springBoot.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springBoot.controller.BaseController;
import springBoot.dto.Types;
import springBoot.modal.vo.MetaVo;
import springBoot.service.IMetaService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author tangj
 * @date 2018/2/1 21:32
 */
@Controller
@RequestMapping("admin/links")
public class LinkController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);

    @Resource
    private IMetaService metaService;

    @GetMapping(value = "")
    public String index(HttpServletRequest request){
        List<MetaVo> metas = metaService.getMetas(Types.LINK.getType());
        request.setAttribute("links",metas);
        return "admin/links";

    }

}
