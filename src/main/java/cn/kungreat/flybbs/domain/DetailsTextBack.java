package cn.kungreat.flybbs.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class DetailsTextBack {
    private Long id;

    private Boolean isAdoption;

    private Integer likeNumber;

    private Long portId;

    private String userAccount;

    private Date createData;

    private Boolean deleteFlag;

    private String detailsText;
    //标记字段
    private Integer classId;
    private Integer portIsauth;
    public String getTableName(){
        String rt = "";
        switch (classId){
            case 1:
                rt= "details_text_back";
                break;
            case 2:
                rt= "details_text_back";
                break;
            case 3:
                rt= "details_text_back";
                break;
            case 4:
                rt= "details_text_back";
        }
        return rt;
    }
}