package com.community.controller;

import com.community.common.api.ApiResult;
import com.community.model.entity.BmsPromotion;
import com.community.service.IBmsPromotionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/promotion")
public class BmsPromotionController extends BaseController{
    @Resource
    private IBmsPromotionService bmsPromotionService;

    @GetMapping("/all")
    public ApiResult<List<BmsPromotion>> list() {
        List<BmsPromotion> list = bmsPromotionService.list();
        return ApiResult.success(list);
    }

}
