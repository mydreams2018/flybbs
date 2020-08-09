package cn.kungreat.flybbs.controller;

import cn.kungreat.flybbs.domain.SelectChildrens;
import cn.kungreat.flybbs.service.SelectChildrensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/selectChildrens")
public class SelectChildrensController {
    @Autowired
    private SelectChildrensService selectChildrensService;

    @RequestMapping(value = "/selectByParentId")
    public List<SelectChildrens> selectByParentId(Integer id){
        return selectChildrensService.selectByParentId(id);
    }
}