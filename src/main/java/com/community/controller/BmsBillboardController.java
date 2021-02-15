package com.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.community.common.api.ApiResult;
import com.community.model.entity.BmsBillboard;
import com.community.service.IBmsBillboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController   //只需要返回Java对象即可，会自动转化为json数据
@RequestMapping("/billboard")    //这样的话 路径/billboard/show就会对应到下面第一条/show
public class BmsBillboardController extends BaseController{
    @Resource
    private IBmsBillboardService bmsBillboardService;

    @GetMapping("/show")
    public ApiResult<BmsBillboard> getNotices(){
        List<BmsBillboard> list = bmsBillboardService.list(new
                LambdaQueryWrapper<BmsBillboard>().eq(BmsBillboard::isShow,true));
        return ApiResult.success(list.get(list.size()- 1));    //查找出表内show字段为1的数据，并返回最后一条数据
    }


}
