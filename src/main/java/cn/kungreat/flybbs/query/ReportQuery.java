package cn.kungreat.flybbs.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportQuery extends Paging{
    private String datatype;
    private String portState;
    private Boolean isEssence;
    private String orderType;
    private Integer classId;
    private String name;
    private Integer portIsauth;
    private String tableName;
    private Boolean isTop;
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
