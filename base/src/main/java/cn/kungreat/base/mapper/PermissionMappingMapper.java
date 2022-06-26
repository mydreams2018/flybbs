package cn.kungreat.base.mapper;

import cn.kungreat.base.domain.PermissionMapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMappingMapper {

    int insertBatch(@Param("ps") List<String> record,@Param("account")String account);
    int deleteByAccount(String account);
    List<PermissionMapping> selectAll(String account);

}