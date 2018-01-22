package springBoot.service.impl;

import org.springframework.stereotype.Service;
import springBoot.dao.OptionVoMapper;
import springBoot.modal.vo.OptionVo;
import springBoot.modal.vo.OptionVoExample;
import springBoot.service.IOptionService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class OptionServiceImpl implements IOptionService{

    @Resource
    private OptionVoMapper optionalDao;

    @Override
    public void insertOption(OptionVo optionVo) {
        optionalDao.insertSelective(optionVo);
    }

    @Override
    public void insertOption(String name, String value) {
        OptionVo optionVo = new OptionVo();
        optionVo.setName(name);
        optionVo.setName(value);
        if (optionalDao.selectByPrimaryKey(name)==null){
            optionalDao.insertSelective(optionVo);
        }else {
            optionalDao.updateByPrimaryKeySelective(optionVo);
        }
    }

    @Override
    public List<OptionVo> getOptions() {
     return optionalDao.selectByExample(new OptionVoExample());
    }

    @Override
    public void saveOptions(Map<String, String> options) {
        if (null != options && !options.isEmpty()) {
            options.forEach(this::insertOption);
        }
    }

    @Override
    public OptionVo getOptionByName(String name) {
        return null;
    }
}
