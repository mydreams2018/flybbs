package cn.kungreat.base.service;

import cn.kungreat.base.domain.PermissionMapping;

import java.util.List;

public interface PermissionMappingService {
    int insertBatch(List<String> record,String account);

    List<PermissionMapping> selectAll(String account);
}
