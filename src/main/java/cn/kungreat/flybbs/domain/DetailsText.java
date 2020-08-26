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
    private Boolean isPort;
    //标记字段
    private Integer classId;
    private String tableName;

    public String getTableName(){
        if(classId != null){
            switch (classId){
                case 1:
                    tableName= "details_text_back";
                    break;
                case 2:
                    tableName= "details_text_front";
                    break;
                case 3:
                    tableName= "details_text_data";
                    break;
                case 4:
                    tableName= "details_text_talk";
            }
        }
        return tableName;
    }
}