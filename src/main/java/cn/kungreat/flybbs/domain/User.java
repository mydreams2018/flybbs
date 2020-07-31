package cn.kungreat.flybbs.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Setter
@Getter
public class User {
    private Long id;

    private String account;

    private String password;

    private String alias;

    private Long phone;

    private String img="/userImg/default.jpg";

    private Boolean isVip;

    private Byte state=1;

    private String email;

    private String description;
    private String originFrom="default";
    private Date registerTime;
    private String icon="/userImg/bbsIcon/defaultMember.gif";
    private Integer registerYear ;
    private Integer accumulatePoints=0;
    public String validMessage(){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isEmpty(account) || account.getBytes().length < 6){
            builder.append("用户名不能为空-不能小于6位数");
        }
        if(StringUtils.isEmpty(password) || password.getBytes().length < 6){
            builder.append("密码不能为空-不能小于6位数");
        }
        byte[] bytes = account.getBytes();
        byte[] ps = password.getBytes();
        for(int x =0;x<bytes.length;x++){
            if(bytes[x] > 47 && bytes[x] < 58
                    || bytes[x] > 96 && bytes[x] < 123){

            }else{
                builder.append("账户只能是小写字母和数字");
                break;
            }
        }
        for(int x =0;x < ps.length; x++){
            if(ps[x] > 47 && ps[x] < 58
                    || ps[x] > 96 && ps[x] < 123 || ps[x] > 64 && ps[x] < 91){

            }else{
                builder.append("密码只能是字母和数字");
                break;
            }
        }
        if(bytes.length > 12){
            builder.append("账户长度不能大过12位");
        }
        if(ps.length > 18){
            builder.append("密码长度不能大过18位");
        }
        return builder.toString();
    }
}