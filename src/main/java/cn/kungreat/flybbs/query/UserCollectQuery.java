package cn.kungreat.flybbs.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCollectQuery extends Paging{
    private String userAccount;
    private Integer classId;

    public String getTableName(){
        switch (classId){
            case 2:
                return "report_front";
            case 3:
                return "report_data";
            case 4:
                return "report_talk";
        }
        return "report_back";
    }
}
