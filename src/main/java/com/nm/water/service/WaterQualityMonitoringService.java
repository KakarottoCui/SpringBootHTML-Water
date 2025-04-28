package com.nm.water.service;

import com.nm.water.mapper.WaterQualityMonitoringMapper;
import com.nm.water.pojo.WaterQualityMonitoring;

import com.nm.water.system.ResponseFormat.ResponseResult;
import com.nm.water.system.ResponseFormat.ResponseCode;
import com.nm.water.system.utils.SessionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nm.water.system.utils.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

@Service
public class WaterQualityMonitoringService {

    @Resource
    private WaterQualityMonitoringMapper waterQualityMonitoringMapper;
    
    /**
     * 查询多条数据
     *
     * @param waterQualityMonitoring 查询条件
     * @return 对象列表
     */
    public ResponseResult<Object> queryWaterQualityMonitoringList(WaterQualityMonitoring waterQualityMonitoring) {
        String searchStr = "";
        if(StringUtils.isNotEmpty(waterQualityMonitoring.getSectionName())){
            searchStr = waterQualityMonitoring.getSectionName();
            waterQualityMonitoring.setSectionName(null);
        }
        QueryWrapper<WaterQualityMonitoring> queryWrapper = new QueryWrapper<WaterQualityMonitoring>(waterQualityMonitoring);
        if(StringUtils.isNotEmpty(searchStr)){
            queryWrapper.like("section_name",searchStr);
        }
        queryWrapper.orderByDesc("id");
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",waterQualityMonitoringMapper.selectList(queryWrapper));
    }
    
    /**
     * 查询一条数据
     *
     * @param waterQualityMonitoring 查询条件
     * @return 对象
     */
    public ResponseResult<Object> queryWaterQualityMonitoringObject(WaterQualityMonitoring waterQualityMonitoring) {
        QueryWrapper<WaterQualityMonitoring> queryWrapper = new QueryWrapper<WaterQualityMonitoring>(waterQualityMonitoring);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",waterQualityMonitoringMapper.selectOne(queryWrapper));
    }
    
    /**
     * 新增一条数据
     *
     * @param waterQualityMonitoring 新增数据实体类
     * @return 新增对象
     */
    public ResponseResult<Object> addWaterQualityMonitoring(WaterQualityMonitoring waterQualityMonitoring) {
        int uid = SessionUtils.getUserId();
        waterQualityMonitoring.setId(null);
        waterQualityMonitoring.setValidFlag(1);
        waterQualityMonitoring.setCreateTime(new Date());
        waterQualityMonitoring.setUpdateTime(new Date());
        waterQualityMonitoring.setCreater(uid);
        waterQualityMonitoring.setUpdater(uid);
        waterQualityMonitoringMapper.insert(waterQualityMonitoring);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"新增成功",waterQualityMonitoring);
    }
    
    /**
     * 修改一条数据
     *
     * @param waterQualityMonitoring 修改数据实体类
     * @return 修改后对象
     */
    public ResponseResult<Object> editWaterQualityMonitoring(WaterQualityMonitoring waterQualityMonitoring) {
        int uid = SessionUtils.getUserId();
        waterQualityMonitoring.setUpdateTime(new Date());
        waterQualityMonitoring.setUpdater(uid);
        waterQualityMonitoringMapper.updateById(waterQualityMonitoring);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"修改成功",waterQualityMonitoring);
    }

    public ResponseResult<Object> getAllSectionName() {
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"成功",waterQualityMonitoringMapper.getAllSection());
    }



}
