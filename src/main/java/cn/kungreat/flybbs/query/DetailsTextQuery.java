package cn.kungreat.flybbs.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetailsTextQuery extends Paging{
    private Long portId;
    private String orderType;
    private Integer classId;
    private String tableName;
    private Integer portIsauth;
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
