package cn.kungreat.flybbs.util;

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
}
