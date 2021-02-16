package com.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.community.model.dto.RegisterDTO;
import com.community.model.entity.UmsUser;

public interface IUmsUserService extends IService<UmsUser> {

    /**
     * 注册功能
     *
     * @param dto
     * @return 注册对象
     */
    UmsUser executeRegister(RegisterDTO dto);
}
