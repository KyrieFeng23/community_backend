package com.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.common.exception.ApiAsserts;
import com.community.jwt.JwtUtil;
import com.community.mapper.UmsUserMapper;
import com.community.model.dto.LoginDTO;
import com.community.model.dto.RegisterDTO;
import com.community.model.entity.UmsUser;
import com.community.service.IUmsUserService;
import com.community.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import java.util.Date;

/**
 * escription：用户注册服务端实现类
 *
 * @author fyf
 * @since 2021/2/16 9:06 下午
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class IUmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser>
implements IUmsUserService {

    @Override
    public UmsUser executeRegister(RegisterDTO dto) {
        //查询是否有相同用户名的用户
        //LambdaQueryWrapper是mybatis-plus里的一个对象，他来设定查找条件
        LambdaQueryWrapper<UmsUser> wrapper = new LambdaQueryWrapper<>();
        //看前端传过来的用户名或者邮箱是否和数据库里的相同
        wrapper.eq(UmsUser::getUsername, dto.getName()).or().eq(UmsUser::getEmail, dto.getEmail());
        UmsUser umsUser = baseMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(umsUser)) {
            ApiAsserts.fail("账号或邮箱已存在！");
        }
        //没有则创建新用户
        UmsUser addUser = UmsUser.builder()         //加了.builder就可以使该对象以链式形式这样一直...，否则需要一直set
                .username(dto.getName())
                .alias(dto.getName())
                .password(MD5Utils.getPwd(dto.getPass()))   //MD5加密
                .email(dto.getEmail())
                .createTime(new Date())
                .status(true)
                .build();
        baseMapper.insert(addUser);                 //将该对象插入数据库

        return addUser;
    }


    @Override
    public UmsUser getUserByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<UmsUser>().eq(UmsUser::getUsername, username));
    }
    @Override
    public String executeLogin(LoginDTO dto) {
        String token = null;
        try {
            UmsUser user = getUserByUsername(dto.getUsername());
            String encodePwd = MD5Utils.getPwd(dto.getPassword());
            if(!encodePwd.equals(user.getPassword()))
            {
                throw new Exception("密码错误");
            }
            token = JwtUtil.generateToken(String.valueOf(user.getUsername()));
        } catch (Exception e) {
            log.warn("用户不存在or密码验证失败=======>{}", dto.getUsername());
        }
        return token;
    }

}
