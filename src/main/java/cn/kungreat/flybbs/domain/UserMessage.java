package cn.kungreat.flybbs.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
public class UserMessage {
    private Long id;

    private Integer classId;

    private Long portId;

    private Long detailsId;
    //存account
    private String srcAlias;

    private String receiveAlias;
    private Set<String> receiveAliasSet;

    private Date receiveDate;

    private Boolean authFlag;
}