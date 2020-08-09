package cn.kungreat.flybbs.mapper;

import cn.kungreat.flybbs.domain.SelectChildrens;

import java.util.List;

public interface SelectChildrensMapper {

    List<SelectChildrens> selectByParentId(Integer id);
}