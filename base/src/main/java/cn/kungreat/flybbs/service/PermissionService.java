package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.Permission;

import java.util.List;

public interface PermissionService {
    List<String> selectPermissions(String account);
    List<Permission> selectAll();
}
