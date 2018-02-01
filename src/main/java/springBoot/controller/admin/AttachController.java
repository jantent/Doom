package springBoot.controller.admin;

import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springBoot.constant.WebConst;
import springBoot.controller.BaseController;
import springBoot.dto.Types;
import springBoot.modal.vo.AttachVo;
import springBoot.service.IAttachService;
import springBoot.service.ILogService;
import springBoot.util.Commons;
import springBoot.util.MyUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 附件管理
 *
 * @author tangj
 * @date 2018/1/31 23:14
 */
@Controller
@RequestMapping("admin/attach")
public class AttachController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AttachController.class);

    public static final String CLASSPATH = MyUtils.getUploadFilePath();

    @Resource
    private IAttachService attachService;

    @Resource
    private ILogService logService;

    /**
     * 附件页面
     *
     * @param request
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "")
    public String index(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "12") int limit) {
        PageInfo<AttachVo> attachPagination = attachService.getAttachs(page, limit);
        request.setAttribute("attachs", attachPagination);
        request.setAttribute(Types.ATTACH_URL.getType(), Commons.site_option(Types.ATTACH_URL.getType()));
        request.setAttribute("max_file_size", WebConst.MAX_TEXT_COUNT / 1024);
        return "admin/attach";
    }

}
