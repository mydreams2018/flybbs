package cn.kungreat.flybbs.util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

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

    public static Set<String> hasReplyAlias(String detailsText){
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

    public static Map<String,Object> converterMap(String st){
        Map<String,Object> mp = new HashMap<>();
        if(st != null && !st.isEmpty()){
            String trim = st.trim();
            int startIndex = 1;
            int type = 0;
            int typeMap = 0 ,typeAry =0,excuteType = 0;
            String tempKey ="";
            for(int x=1;x < trim.length()-1;x++){
                if( type ==0 &&'=' == trim.charAt(x)){
                    tempKey = trim.substring(startIndex, x);
                    startIndex = x+1;
                    while(' '==trim.charAt(startIndex)){
                        startIndex++;
                    }
                    x=startIndex;
                    if('{'==trim.charAt(startIndex)){
                        type=1;
                        typeMap++;
                        excuteType=1;
                    }else if('['==trim.charAt(startIndex)){
                        type=2;
                        typeAry++;
                        excuteType=2;
                    }
                }else if(type ==1 &&'{' == trim.charAt(x) ){
                    typeMap++;
                }else if(type ==2 &&'[' == trim.charAt(x) ){
                    typeAry++;
                }
                if(type ==0 &&',' == trim.charAt(x)){
                    if(excuteType == 1){
                        mp.put(tempKey.trim(),converterMap(trim.substring(startIndex, x)));
                    }else if(excuteType == 2){
                        mp.put(tempKey.trim(),getList(trim.substring(startIndex, x)));
                    }else{
                        mp.put(tempKey.trim(),trim.substring(startIndex, x).trim());
                    }
                    startIndex = x+1;
                }else if(type == 1 &&'}'==trim.charAt(x)){
                    typeMap--;
                    if(typeMap == 0){
                        type=0;
                    }
                }else if(type == 2 &&']'==trim.charAt(x)){
                    typeAry--;
                    if(typeAry == 0){
                        type=0;
                    }
                }
                if(x >= trim.length()-2){
                    if(excuteType == 1){
                        mp.put(tempKey.trim(),converterMap(trim.substring(startIndex,trim.length()-1)));
                    }else if(excuteType == 2){
                        mp.put(tempKey.trim(),getList(trim.substring(startIndex,trim.length()-1)));
                    }else{
                        mp.put(tempKey.trim(),trim.substring(startIndex,trim.length()-1).trim());
                    }
                    break;
                }
            }
        }
        return mp;
    }

    private static List<String> getList(String st){
        String trim = st.trim();
        String[] split = trim.substring(1,trim.length()-1).split(",");
        return Arrays.asList(split);
    }
}
