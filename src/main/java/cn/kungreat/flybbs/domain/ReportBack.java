package cn.kungreat.flybbs.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
@Setter
@Getter
public class ReportBack {
    private Long id;

    private String name;

    private String datatype;

    private String dataversion;

    private String useides;

    private Byte experience;

    private String userAccount;

    private Boolean isEssence;

    private String portState;

    private Boolean isTop;

    private Byte replyNumber;

    private Byte lookNumber;

    private Date createTime;

    private Boolean deleteFlag;

    private String detailsText;
    //标记字段
    private Integer classId;
    private Integer portIsauth;
    public String validMessage(){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(datatype)
                ||StringUtils.isEmpty(detailsText) || classId == null){
            builder.append("标题,类型,内容不能为空");
        }
        return builder.toString();
    }

    public String getTableName(){
        String rt = "";
        switch (classId){
            case 1:
                rt= "report_back";
                break;
            case 2:
                rt= "report_back";
                break;
            case 3:
                rt= "report_back";
                break;
            case 4:
                rt= "report_back";
        }
        return rt;
    }
}