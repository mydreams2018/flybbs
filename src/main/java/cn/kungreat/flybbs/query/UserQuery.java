package cn.kungreat.flybbs.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserQuery extends Paging {
    private String groupField="origin_from";
    private String category;
    private String searchKeyword;
    private String alias;
    @JsonIgnore
    private String account;
    public void setGroupField(String groupField) {
        if("register_year".equals(groupField)){
            this.groupField = groupField;
        }
    }
}
