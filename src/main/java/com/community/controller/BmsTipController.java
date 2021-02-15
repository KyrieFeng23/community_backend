package com.community.controller;

import com.community.common.api.ApiResult;
import com.community.model.entity.BmsTip;
import com.community.service.IBmsTipService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController   //只需要返回Java对象即可，会自动转化为json数据
@RequestMapping("/tip")
public class BmsTipController extends BaseController{
    @Resource
    private IBmsTipService bmsTipService;

    @GetMapping("/today")
    public ApiResult<BmsTip> getRandomTip(){
        BmsTip tip = bmsTipService.getRandomTip();
        return ApiResult.success(tip);
    }


}
