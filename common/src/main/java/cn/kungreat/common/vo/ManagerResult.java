package cn.kungreat.common.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ManagerResult {
    private Integer code = 0;
    private String msg ;
    private Integer count;
    private Object page;
    private List data;
}