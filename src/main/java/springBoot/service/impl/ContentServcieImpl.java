package springBoot.service.impl;

import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import springBoot.constant.WebConst;
import springBoot.dao.ContentVoMapper;
import springBoot.dao.MetaVoMapper;
import springBoot.dto.Types;
import springBoot.exception.TipException;
import springBoot.modal.vo.ContentVo;
import springBoot.modal.vo.ContentVoExample;
import springBoot.service.IContentService;
import springBoot.service.IMetaService;
import springBoot.service.IRelationshipService;
import springBoot.util.DateKit;
import springBoot.util.MyUtils;

import javax.annotation.Resource;

/**
 * @author tangj
 * @date 2018/1/24 21:21
 */
@Service
public class ContentServcieImpl implements IContentService {

    @Resource
    private ContentVoMapper contentDao;

    @Resource
    private MetaVoMapper metaDao;

    @Resource
    private IRelationshipService relationshipService;

    @Resource
    private IMetaService metasService;

    @Override
    public void publish(ContentVo contents) {
        if (null == contents) {
            throw new TipException("文章对象为空");
        }
        if (StringUtils.isBlank(contents.getTitle())) {
            throw new TipException("文章标题不能为空");
        }
        if (StringUtils.isBlank(contents.getContent())) {
            throw new TipException("文章内容不能为空");
        }
        int titleLength = contents.getTitle().length();
        if (titleLength > WebConst.MAX_TITLE_COUNT) {
            throw new TipException("文章标题过长");
        }
        int contentLength = contents.getContent().length();
        if (contentLength > WebConst.MAX_TEXT_COUNT) {
            throw new TipException("文章内容过长");
        }
        if (null == contents.getAuthorId()) {
            throw new TipException("请登录后发布文章");
        }
        if (StringUtils.isNotBlank(contents.getSlug())) {
            if (contents.getSlug().length() < 5) {
                throw new TipException("路径太短了");
            }
            if (!MyUtils.isPath(contents.getSlug())) {
                throw new TipException("您输入的路径不合法");
            }
            ContentVoExample contentVoExample = new ContentVoExample();
            contentVoExample.createCriteria().andTypeEqualTo(contents.getType()).andStatusEqualTo(contents.getSlug());
            long count = contentDao.countByExample(contentVoExample);
            if (count > 0) {
                throw new TipException("该路径已经存在，请重新输入");
            }
        } else {
            contents.setSlug(null);
        }
        contents.setContent(EmojiParser.parseToAliases(contents.getContent()));

        int time = DateKit.getCurrentUnixTime();
        contents.setCreated(time);
        contents.setModified(time);
        contents.setHits(0);
        contents.setCommentsNum(0);

        String tags = contents.getTags();
        String categories = contents.getCategories();
        contentDao.insert(contents);
        Integer cid = contents.getCid();

        metasService.saveMetas(cid, tags, Types.TAG.getType());
        metasService.saveMetas(cid, categories, Types.CATEGORY.getType());
    }

    @Override
    public PageInfo<ContentVo> getContents(Integer p, Integer limit) {
        return null;
    }

    @Override
    public ContentVo getContents(String id) {
        return null;
    }

    @Override
    public void updateContentByCid(ContentVo contentVo) {

    }

    @Override
    public PageInfo<ContentVo> getArticles(Integer mid, int page, int limit) {
        return null;
    }

    @Override
    public PageInfo<ContentVo> getArticles(String keyword, Integer page, Integer limit) {
        return null;
    }

    @Override
    public PageInfo<ContentVo> getArticlesWithpage(ContentVoExample commentVoExample, Integer page, Integer limit) {
        return null;
    }

    @Override
    public void deleteByCid(Integer cid) {

    }

    @Override
    public void updateArticle(ContentVo contents) {

    }

    @Override
    public void updateCategory(String ordinal, String newCatefory) {

    }
}
