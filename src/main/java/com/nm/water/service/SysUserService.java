package com.nm.water.service;

import com.nm.water.mapper.SysUserMapper;

import com.nm.water.pojo.SysUser;
import com.nm.water.system.ResponseFormat.ResponseResult;
import com.nm.water.system.ResponseFormat.ResponseCode;
import com.nm.water.system.utils.SessionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    
    /**
     * 查询多条数据
     *
     * @param sysUser 查询条件
     * @return 对象列表
     */
    public ResponseResult<Object> querySysUserList(SysUser sysUser) {
        String nickName = "";
        nickName = sysUser.getNickName();
        if(nickName!=null){
            sysUser.setNickName(null);
        }
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>(sysUser);
        if(nickName!=null)
            queryWrapper.like("nick_name",nickName);
        queryWrapper.orderByDesc("uid");
        List<SysUser> sysUserList = sysUserMapper.selectList(queryWrapper);

        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",sysUserList);
    }
    
    /**
     * 查询一条数据
     *
     * @param sysUser 查询条件
     * @return 对象
     */
    public ResponseResult<Object> querySysUserObject(SysUser sysUser) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>(sysUser);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",sysUserMapper.selectOne(queryWrapper));
    }
    
    /**
     * 新增一条数据
     *
     * @param sysUser 新增数据实体类
     * @return 新增对象
     */
    public ResponseResult<Object> addSysUser(SysUser sysUser) {
        if(!checkSysUser(sysUser)){
            return new ResponseResult<Object>(ResponseCode.SERVICE_ERROR,"存在其他同昵称用户",sysUser);
        }
        Integer uid = SessionUtils.getUserId();
        sysUser.setUid(null);
        sysUser.setValidFlag(1);
        sysUser.setCreateTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUser.setCreater(uid);
        sysUser.setUpdater(uid);
        sysUser.setPassword("MTIzNDU2");
        sysUserMapper.insert(sysUser);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"新增成功",sysUser);
    }
    
    /**
     * 修改一条数据
     *
     * @param sysUser 修改数据实体类
     * @return 修改后对象
     */
    public ResponseResult<Object> editSysUser(SysUser sysUser) {
        if(!checkSysUser(sysUser) && sysUser.getUid()!=null && sysUser.getValidFlag()!=0){
            return new ResponseResult<Object>(ResponseCode.SERVICE_ERROR,"存在其他同昵称用户",sysUser);
        }
        Integer uid = 0;
        if(sysUser.getUid()!=null){
            uid = sysUser.getUid();
        }else{
            uid = SessionUtils.getUserId();
        }
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdater(uid);
        sysUserMapper.updateById(sysUser);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"修改成功",sysUser);
    }

    public boolean checkSysUser(SysUser user) {
        SysUser param = new SysUser();
        param.setNickName(user.getNickName());
        param.setValidFlag(1);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>(param);
        if(user.getUid()!=null){
            queryWrapper.ne("uid",user.getUid());
        }
        List<SysUser> sysUserList = sysUserMapper.selectList(queryWrapper);
        return sysUserList==null || sysUserList.isEmpty();
    }

}
