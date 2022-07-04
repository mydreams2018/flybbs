package cn.kungreat.clienttest.controller;

import cn.kungreat.clienttest.feign.AuthorityFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/feign-client")
public class MyFeignClient {
    @Autowired
    private AuthorityFeignClient authorityFeignClient;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public Map echo() {
        return authorityFeignClient.getByFeign();
    }

}
