package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.PermissionMapping;

import java.util.List;

public interface PermissionMappingService {
    int insertBatch(List<String> record,String account);

    List<PermissionMapping> selectAll(String account);
}
