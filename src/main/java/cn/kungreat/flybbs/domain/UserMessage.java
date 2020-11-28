package cn.kungreat.flybbs.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class UserMessage {
    private Long id;

    private Byte classId;

    private Long portId;

    private Long detailsId;

    private String srcAlias;

    private String receiveAlias;

    private Date receiveDate;
}