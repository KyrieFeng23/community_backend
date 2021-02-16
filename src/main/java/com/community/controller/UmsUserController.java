package com.community.controller;

import com.community.common.api.ApiResult;
import com.community.model.dto.LoginDTO;
import com.community.model.dto.RegisterDTO;
import com.community.model.entity.UmsUser;
import com.community.service.IUmsUserService;
import net.sf.jsqlparser.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/16 9:41 下午
 */

@RestController
@RequestMapping("/ums/user")
public class UmsUserController extends BaseController{
    @Resource
    private IUmsUserService iUmsUserService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    @PostMapping("/register")
    public ApiResult<Map<String,Object>> register(@Valid @RequestBody RegisterDTO dto){ //获取数据
        UmsUser user = iUmsUserService.executeRegister(dto);
        if (ObjectUtils.isEmpty(user)){
            return ApiResult.failed("账号注册失败");
        }

        Map<String,Object> map = new HashMap<>(16);
        map.put("user",user);
        //这里先暂时返回一个map，其实前端只需要收到这个success信息即可，不需要返回的数据，返回什么都无所谓。
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
        String token = iUmsUserService.executeLogin(dto);
        if (ObjectUtils.isEmpty(token)) {
            return ApiResult.failed("账号密码错误");
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("token", token);
        //存在map里返回到客户端
        return ApiResult.success(map, "登录成功");
    }
}
