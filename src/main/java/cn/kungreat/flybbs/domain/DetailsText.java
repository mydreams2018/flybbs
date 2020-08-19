package cn.kungreat.flybbs.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class DetailsText {
    private Long id;

    private Boolean isAdoption=false;

    private Integer likeNumber;

    private Long portId;

    private String userAccount;

    private Date createData;

    private Boolean deleteFlag;

    private String detailsText;
    //标记字段
    private Integer classId;
    private String tableName;
    public String getTableName(){
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
        return tableName;
    }
}