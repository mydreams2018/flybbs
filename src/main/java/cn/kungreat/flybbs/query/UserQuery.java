package cn.kungreat.flybbs.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserQuery extends Paging {
    private String category;
    private String searchKeyword;
    private String alias;
    @JsonIgnore
    private String account;
}
