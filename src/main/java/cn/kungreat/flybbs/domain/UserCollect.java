package cn.kungreat.flybbs.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class UserCollect {
    private Long id;

    private String userAccount;

    private Integer classId;

    private Long portId;

    private Date collectTime;

}