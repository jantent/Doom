package springBoot.controller;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import springBoot.constant.WebConst;
import springBoot.exception.TipException;
import springBoot.modal.bo.CommentBo;
import springBoot.modal.vo.ContentVo;
import springBoot.service.ICommentService;
import springBoot.service.IContentService;
import springBoot.service.IMetaService;
import springBoot.service.ISiteService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 首页控制
 *
 * @author tangj
 * @date 2018/2/17 9:47
 */
@Controller
public class IndexController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IContentService contentService;

    @Autowired
    private ICommentService commentService;

    @Resource
    private IMetaService metaService;

    @Resource
    private ISiteService siteService;

    @GetMapping(value = "/")
    private String index(HttpServletRequest request, @RequestParam(value = "limit", defaultValue = "12") int limit) {
        return this.index(request, 1, limit);
    }

    @GetMapping(value = "page/{p}")
    public String index(HttpServletRequest request, @PathVariable int p, @RequestParam(value = "limit", defaultValue = "12") int limit) {
        p = p < 0 || p > WebConst.MAX_PAGE ? 1 : p;
        PageInfo<ContentVo> articles = contentService.getContents(p, limit);
        request.setAttribute("articles", articles);
        if (p > 1) {
            this.title(request, "第" + p + "页");
        }
        return this.render("index");
    }

    /**
     * 文章页
     * @param request
     * @param cid
     * @return
     */
    @GetMapping(value = {"article/{cid}","article/{cid}.html"})
    public String getArticle(HttpServletRequest request,@PathVariable String cid){
        ContentVo contents = contentService.getContents(cid);
        if (null == contents || "draft".equals(contents.getStatus())){
            return this.render_404();
        }
        request.setAttribute("article",contents);
        request.setAttribute("is_post",true);
        completeArticle(request,contents);
        updateArticleHit(contents.getCid(),contents.getHits());
        return this.render("page");
    }

    /**
     * 文章页（预览）
     * @param request
     * @param cid
     * @return
     */
    @GetMapping(value = {"article/{cid}/preview","article/{cid}.html"})
    public String articlePreview(HttpServletRequest request,@PathVariable String cid){
        ContentVo contents = contentService.getContents(cid);
        if (null == contents){
            return this.render_404();
        }
        request.setAttribute("article",contents);
        request.setAttribute("is_post",true);
        completeArticle(request,contents);
        updateArticleHit(contents.getCid(),contents.getHits());
        return this.render("post");
    }

    @GetMapping(value = "search/{keyword}")
    public String search(HttpServletRequest request,@PathVariable String keyword,@RequestParam(value = "limit",defaultValue = "12")int limit){

    }



    /**
     * 更新点击次数
     * @param cid
     * @param chits
     */
    @Transactional(rollbackFor = TipException.class)
    protected void updateArticleHit(Integer cid, Integer chits){
        Integer hits = cache.hget("article","hits");
        if (chits == null){
            chits = 0;
        }
        hits = null == hits?1:hits+1;
        if (hits>=WebConst.HIT_EXCEED){
            ContentVo temp = new ContentVo();
            temp.setCid(cid);
            temp.setHits(chits+hits);
            contentService.updateContentByCid(temp);
            cache.hset("article","hits",1);
        }else {
            cache.hset("article","hits",1);
        }
    }

    /**
     * 查询文章的评论信息，并补充到里面，返回前端
     * @param request
     * @param contents
     */
    private void completeArticle(HttpServletRequest request,ContentVo contents){
        if (contents.getAllowComment()){
            String cp = request.getParameter("cp");
            if (StringUtils.isBlank(cp)){
                cp = "1";
            }
            request.setAttribute("cp",cp);
            PageInfo<CommentBo> commentsPaginator = commentService.getComments(contents.getCid(),Integer.parseInt(cp),6);
            request.setAttribute("comments",commentsPaginator);
        }
    }
}
