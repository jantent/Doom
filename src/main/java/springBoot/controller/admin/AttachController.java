package springBoot.controller.admin;

import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springBoot.constant.WebConst;
import springBoot.controller.BaseController;
import springBoot.dto.Types;
import springBoot.exception.TipException;
import springBoot.modal.bo.RestResponseBo;
import springBoot.modal.vo.AttachVo;
import springBoot.modal.vo.UserVo;
import springBoot.service.IAttachService;
import springBoot.service.ILogService;
import springBoot.util.Commons;
import springBoot.util.MyUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

    @PostMapping(value = "upload")
    @ResponseBody
    @Transactional(rollbackFor = TipException.class)
    public RestResponseBo upload(HttpServletRequest request, @RequestParam("file")MultipartFile[] multipartFiles) throws IOException{
        UserVo users = this.user(request);
        Integer uid = users.getUid();
        List<String> errorFiles = new ArrayList<>();

        try{
            for (MultipartFile multipartFile:multipartFiles){
                String name = multipartFile.getOriginalFilename();
                if (multipartFile.getSize() <= WebConst.MAX_FILE_SIZE){
                    String fkey = MyUtils.get
                }
            }
        }catch (Exception e){
            return RestResponseBo.fail();
        }
        return RestResponseBo.ok(errorFiles);
    }
}
