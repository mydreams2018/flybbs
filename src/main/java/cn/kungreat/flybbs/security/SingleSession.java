package cn.kungreat.flybbs.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SingleSession {
    private SingleSession(){}

    public final static Map<String,String> single = new ConcurrentHashMap<>(1024);
}
