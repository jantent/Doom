package springBoot.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springBoot.constant.WebConst;
import springBoot.dto.LogActions;
import springBoot.exception.TipException;
import springBoot.modal.bo.RestResponseBo;
import springBoot.modal.bo.StaticticsBo;
import springBoot.modal.vo.CommentVo;
import springBoot.modal.vo.ContentVo;
import springBoot.modal.vo.LogVo;
import springBoot.modal.vo.UserVo;
import springBoot.service.ILogService;
import springBoot.service.ISiteService;
import springBoot.service.IUserService;
import springBoot.util.GsonUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author tangj
 * @date 2018/1/23 11:39
 */
@Controller("adminIndexController")
@RequestMapping("/admin")
@Transactional(rollbackFor = TipException.class)
public class IndexController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Resource
    private ILogService logService;

    @Resource
    private IUserService userService;

    @Resource
    private ISiteService siteService;

    /**
     * 管理中心起始页
     *
     * @param request
     * @return
     */
    @GetMapping(value = {"", "/index"})
    public String index(HttpServletRequest request) {
        List<ContentVo> contentVos = siteService.recentContents(5);
        List<CommentVo> commentVos = siteService.recentComments(5);
        StaticticsBo staticticsBo = siteService.getStatistics();
        //取最新的15条日志
        List<LogVo> logVos = logService.getLogs(1, 5);
        request.setAttribute("comments", commentVos);
        request.setAttribute("articles", contentVos);
        request.setAttribute("statistics", staticticsBo);
        request.setAttribute("logs", logVos);
        return "admin/index";
    }

    @GetMapping(value = "profile")
    public String profile(){
        return "admin/profile";
    }

    @GetMapping(value = "logout")
    public String logout(){
        return "admin/login";
    }

    @PostMapping(value = "/profile")
    @ResponseBody
    @Transactional(rollbackFor = TipException.class)
    public RestResponseBo saveProfile(@RequestParam String screenName, @RequestParam String email, HttpServletRequest request, HttpSession session){
        UserVo users = this.user(request);
        if (StringUtils.isNotBlank(screenName) && StringUtils.isNotBlank(email)){
            UserVo temp = new UserVo();
            temp.setUid(users.getUid());
            temp.setScreenName(screenName);
            temp.setEmail(email);
            userService.updateByUid(temp);
            logService.insertLog(LogActions.UP_INFO.getAction(), GsonUtils.toJsonString(temp),request.getRemoteAddr(),this.getUid(request));

            //更新session中的数据
            UserVo original = (UserVo)session.getAttribute(WebConst.LOGIN_SESSION_KEY);
            original.setScreenName(screenName);
            original.setEmail(email);
            session.setAttribute(WebConst.LOGIN_SESSION_KEY, original);
        }
        return RestResponseBo.ok();
    }
}
