package cn.kungreat.flybbs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
@Setter
@Getter
public class Report {
    private Long id;

    private String name;

    private String datatype;

    private String dataversion;

    private String useides;
//飞吻
    private Byte experience;

    private String userAccount;

    private Boolean isEssence=false;

    private String portState;

    private Boolean isTop=false;

    private Byte replyNumber;

    private Byte lookNumber;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    private Boolean deleteFlag;

    private Integer classId;
    private String tableName;
    private String detailsText;
    private String alias;
    private String userImg;
    private Boolean isVip;
    private Integer vipLevel;
    private String authenticate;
    private Byte isManager;

    public String validMessage(){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(datatype)
                ||StringUtils.isEmpty(detailsText) || classId == null){
            builder.append("标题,类型,内容不能为空");
        }
        return builder.toString();
    }

    public String getTableName() {
        if(classId != null){
            switch (classId){
                case 1:
                    tableName= "report_back";
                    break;
                case 2:
                    tableName= "report_front";
                    break;
                case 3:
                    tableName= "report_data";
                    break;
                case 4:
                    tableName= "report_talk";
            }
        }
        return tableName;
    }
}