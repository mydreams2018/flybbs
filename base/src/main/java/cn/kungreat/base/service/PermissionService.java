package cn.kungreat.base.service;

import cn.kungreat.base.domain.Permission;

import java.util.List;

public interface PermissionService {
    List<String> selectPermissions(String account);
    List<Permission> selectAll();
}
