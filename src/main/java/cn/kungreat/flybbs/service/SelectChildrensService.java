package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.SelectChildrens;

import java.util.List;

public interface SelectChildrensService {
    List<SelectChildrens> selectByParentId(Integer id);
}
