package com.nm.water.service;

import com.nm.water.mapper.SysNoticeMapper;
import com.nm.water.pojo.SysNotice;

import com.nm.water.system.ResponseFormat.ResponseResult;
import com.nm.water.system.ResponseFormat.ResponseCode;
import com.nm.water.system.utils.SessionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

@Service
public class SysNoticeService {

    @Resource
    private SysNoticeMapper sysNoticeMapper;
    
    /**
     * 查询多条数据
     *
     * @param sysNotice 查询条件
     * @return 对象列表
     */
    public ResponseResult<Object> querySysNoticeList(SysNotice sysNotice) {
        QueryWrapper<SysNotice> queryWrapper = new QueryWrapper<SysNotice>(sysNotice);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",sysNoticeMapper.selectList(queryWrapper));
    }
    
    /**
     * 查询一条数据
     *
     * @param sysNotice 查询条件
     * @return 对象
     */
    public ResponseResult<Object> querySysNoticeObject(SysNotice sysNotice) {
        QueryWrapper<SysNotice> queryWrapper = new QueryWrapper<SysNotice>(sysNotice);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",sysNoticeMapper.selectOne(queryWrapper));
    }
    
    /**
     * 新增一条数据
     *
     * @param sysNotice 新增数据实体类
     * @return 新增对象
     */
    public ResponseResult<Object> addSysNotice(SysNotice sysNotice) {
        Integer uid = SessionUtils.getUserId();
        sysNotice.setId(null);
        sysNotice.setValidFlag(1);
        sysNotice.setCreateTime(new Date());
        sysNotice.setUpdateTime(new Date());
        sysNotice.setCreater(uid);
        sysNotice.setUpdater(uid);
        sysNoticeMapper.insert(sysNotice);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"新增成功",sysNotice);
    }
    
    /**
     * 修改一条数据
     *
     * @param sysNotice 修改数据实体类
     * @return 修改后对象
     */
    public ResponseResult<Object> editSysNotice(SysNotice sysNotice) {
        Integer uid = SessionUtils.getUserId();
        sysNotice.setUpdateTime(new Date());
        sysNotice.setUpdater(uid);
        sysNoticeMapper.updateById(sysNotice);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"修改成功",sysNotice);
    }

}
