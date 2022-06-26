package cn.kungreat.base.service.impl;

import cn.kungreat.base.domain.Permission;
import cn.kungreat.base.mapper.PermissionMapper;
import cn.kungreat.base.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<String> selectPermissions(String account) {
        return permissionMapper.selectPermissions(account);
    }

    @Override
    public List<Permission> selectAll() {
        return permissionMapper.selectAll();
    }
}
