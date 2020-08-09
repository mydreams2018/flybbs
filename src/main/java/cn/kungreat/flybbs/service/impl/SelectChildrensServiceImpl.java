package cn.kungreat.flybbs.service.impl;

import cn.kungreat.flybbs.domain.SelectChildrens;
import cn.kungreat.flybbs.mapper.SelectChildrensMapper;
import cn.kungreat.flybbs.service.SelectChildrensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectChildrensServiceImpl implements SelectChildrensService {
    @Autowired
    private SelectChildrensMapper selectChildrensMapper;

    @Override
    public List<SelectChildrens> selectByParentId(Integer id) {
        return selectChildrensMapper.selectByParentId(id);
    }
}