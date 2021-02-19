package com.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.community.common.api.ApiResult;
import com.community.model.dto.CreateTopicDTO;
import com.community.model.entity.BmsPost;
import com.community.model.entity.UmsUser;
import com.community.model.vo.PostVO;
import com.community.service.IBmsPostService;
import com.community.service.IUmsUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Map;

import static com.community.jwt.JwtUtil.USER_NAME;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/18 11:30 下午
 */
@RestController
@RequestMapping("/post")
public class BmsPostController extends BaseController {

    @Resource
    private IBmsPostService iBmsPostService;
    @Resource
    private IUmsUserService umsUserService;

    @GetMapping("/list")
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostVO> list = iBmsPostService.getList(new Page<>(pageNo, pageSize), tab);
        //返回一个page对象，是mybatis-plus自带的一个对象，根据获得的参数生成，mapper自动会有分页的功能。
        return ApiResult.success(list);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResult<BmsPost> create(@RequestHeader(value = USER_NAME) String userName
            , @RequestBody CreateTopicDTO dto) {
        UmsUser user = umsUserService.getUserByUsername(userName);
        BmsPost topic = iBmsPostService.create(dto, user);
        return ApiResult.success(topic);
    }

    @GetMapping()
    public ApiResult<Map<String, Object>> view(@RequestParam("id") String id) {
        //因为有帖子、用户、标签三个表的信息，所以用map
        Map<String, Object> map = iBmsPostService.viewTopic(id);
        return ApiResult.success(map);
    }
}
