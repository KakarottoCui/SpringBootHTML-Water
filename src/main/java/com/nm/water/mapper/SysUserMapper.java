package com.nm.water.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nm.water.pojo.SysUser;
import org.springframework.stereotype.Repository;

/**
 * 用户表(SysUser)表数据库访问层
 *
 */

@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

}
