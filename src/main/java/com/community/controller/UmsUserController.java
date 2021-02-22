package com.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.community.common.api.ApiResult;
import com.community.model.dto.LoginDTO;
import com.community.model.dto.RegisterDTO;
import com.community.model.entity.BmsPost;
import com.community.model.entity.UmsUser;
import com.community.service.IBmsPostService;
import com.community.service.IUmsUserService;
import net.sf.jsqlparser.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.community.jwt.JwtUtil.USER_NAME;

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

    @Resource
    private IBmsPostService iBmsPostService;

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

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public ApiResult<UmsUser> getUser(@RequestHeader(value = USER_NAME) String userName){
        UmsUser user = iUmsUserService.getUserByUsername(userName);
        return ApiResult.success(user);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ApiResult<Object> logOut() {
        return ApiResult.success(null, "注销成功");
    }

    @GetMapping("/{username}")
    public ApiResult<Map<String, Object>> getUserByName(@PathVariable("username") String username,
                                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Map<String, Object> map = new HashMap<>(16);
        UmsUser user = iUmsUserService.getUserByUsername(username);
        Assert.notNull(user, "用户不存在");
        Page<BmsPost> page = iBmsPostService.page(new Page<>(pageNo, size),
                new LambdaQueryWrapper<BmsPost>().eq(BmsPost::getUserId, user.getId()));
        map.put("user", user);
        map.put("topics", page);
        return ApiResult.success(map);
    }
}
