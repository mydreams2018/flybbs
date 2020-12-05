package cn.kungreat.flybbs.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class UserAccumulate {

    public static int countVipLevel(int x){
        int rt = 0;
        if(x > 888){
            rt = 9;
        }else if(x > 777){
            rt = 8;
        }else if(x > 666){
            rt = 7;
        }else if(x > 555){
            rt = 6;
        }else if(x > 333){
            rt = 5;
        }else if(x > 222){
            rt = 3;
        }else if(x > 111){
            rt = 2;
        }else if(x > 0){
            rt = 1;
        }
        return rt;
    }

    public static Set hasReplyAlias(String detailsText){
        String st = detailsText;
        Set<String> rt = new HashSet<>();
        if(st.contains("@")){
            int startIndex = 0;
            for(int x = 0;x < st.length(); x++){
                if('@'==st.charAt(x)){
                    startIndex = x+1;
                }
                if(' '== st.charAt(x) && startIndex != 0){
                    String temp = st.substring(startIndex,x);
                    if(StringUtils.isNotEmpty(temp)){
                        rt.add(temp);
                    }
                    startIndex=0;
                }
            }
        }
        return rt;
    }
}
