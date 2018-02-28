package springBoot.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springBoot.modal.vo.ContentVo;

import javax.annotation.Resource;

/**
 * @author tangj
 * @date 2018/2/28 20:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentVoMapperTest {

    @Resource
    private ContentVoMapper contentDao;

    @Test
    public void testInsert(){
        ContentVo contentVo = new ContentVo();
        contentVo.setTitle("aaaa");
        contentDao.insert(contentVo);
    }
}
