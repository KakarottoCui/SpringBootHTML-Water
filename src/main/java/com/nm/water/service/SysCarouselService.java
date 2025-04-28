package com.nm.water.service;

import com.nm.water.mapper.SysCarouselMapper;
import com.nm.water.pojo.SysCarousel;

import com.nm.water.system.ResponseFormat.ResponseResult;
import com.nm.water.system.ResponseFormat.ResponseCode;
import com.nm.water.system.utils.CommonFunction;
import com.nm.water.system.utils.SessionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nm.water.system.utils.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

@Service
public class SysCarouselService {

    @Resource
    private SysCarouselMapper sysCarouselMapper;
    
    /**
     * 查询多条数据
     *
     * @param sysCarousel 查询条件
     * @return 对象列表
     */
    public ResponseResult<Object> querySysCarouselList(SysCarousel sysCarousel) {
        QueryWrapper<SysCarousel> queryWrapper = new QueryWrapper<SysCarousel>(sysCarousel);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",sysCarouselMapper.selectList(queryWrapper));
    }
    
    /**
     * 查询一条数据
     *
     * @param sysCarousel 查询条件
     * @return 对象
     */
    public ResponseResult<Object> querySysCarouselObject(SysCarousel sysCarousel) {
        QueryWrapper<SysCarousel> queryWrapper = new QueryWrapper<SysCarousel>(sysCarousel);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",sysCarouselMapper.selectOne(queryWrapper));
    }
    
    /**
     * 新增一条数据
     *
     * @param sysCarousel 新增数据实体类
     * @return 新增对象
     */
    public ResponseResult<Object> addSysCarousel(SysCarousel sysCarousel) {
        Integer uid = SessionUtils.getUserId();
        sysCarousel.setId(null);
        sysCarousel.setValidFlag(1);
        sysCarousel.setCreateTime(new Date());
        sysCarousel.setUpdateTime(new Date());
        sysCarousel.setCreater(uid);
        sysCarousel.setUpdater(uid);
        setPic(sysCarousel);
        sysCarouselMapper.insert(sysCarousel);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"新增成功",sysCarousel);
    }
    
    /**
     * 修改一条数据
     *
     * @param sysCarousel 修改数据实体类
     * @return 修改后对象
     */
    public ResponseResult<Object> editSysCarousel(SysCarousel sysCarousel) {
        Integer uid = SessionUtils.getUserId();
        sysCarousel.setUpdateTime(new Date());
        sysCarousel.setUpdater(uid);
        setPic(sysCarousel);
        sysCarouselMapper.updateById(sysCarousel);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"修改成功",sysCarousel);
    }

    public void setPic(SysCarousel sysCarousel){
        if(StringUtils.isNotEmpty(sysCarousel.getPic()) && sysCarousel.getPic().length()>100 ){
            String fileName = CommonFunction.SaveBase64Pic(sysCarousel.getPic());
            sysCarousel.setPic(fileName);
        }
    }

}
