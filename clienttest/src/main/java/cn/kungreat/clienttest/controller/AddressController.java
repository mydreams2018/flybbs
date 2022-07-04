package cn.kungreat.clienttest.controller;

import cn.kungreat.clienttest.jb.AddressInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>用户地址服务 Controller</h1>
 * */
@Api(tags = "用户地址服务")
@RestController
@RequestMapping("/address")
public class AddressController {

    // value 是简述, notes 是详细的描述信息
    @ApiOperation(value = "创建", notes = "创建用户地址信息",response =AddressInfo.class , httpMethod = "POST")
    @PostMapping("/create-address")
    public AddressInfo createAddressInfo(@RequestBody AddressInfo addressInfo) {
        return addressInfo;
    }

    @ApiOperation(value = "当前用户", notes = "获取当前登录用户地址信息", httpMethod = "GET")
    @GetMapping("/current-address")
    public AddressInfo getCurrentAddressInfo() {
        return new AddressInfo();
    }
}
