package com.nm.water.service;

import com.nm.water.mapper.WaterPressureMonitoringMapper;
import com.nm.water.pojo.WaterPressureMonitoring;

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
public class WaterPressureMonitoringService {

    @Resource
    private WaterPressureMonitoringMapper waterPressureMonitoringMapper;
    
    /**
     * 查询多条数据
     *
     * @param waterPressureMonitoring 查询条件
     * @return 对象列表
     */
    public ResponseResult<Object> queryWaterPressureMonitoringList(WaterPressureMonitoring waterPressureMonitoring) {

        String searchStr = "";
        if(StringUtils.isNotEmpty(waterPressureMonitoring.getMonitoringLocation())){
            searchStr = waterPressureMonitoring.getMonitoringLocation();
            waterPressureMonitoring.setMonitoringLocation(null);
        }
        QueryWrapper<WaterPressureMonitoring> queryWrapper = new QueryWrapper<WaterPressureMonitoring>(waterPressureMonitoring);
        if(StringUtils.isNotEmpty(searchStr)){
            queryWrapper.like("monitoring_location",searchStr);
        }
        queryWrapper.orderByDesc("id");
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",waterPressureMonitoringMapper.selectList(queryWrapper));
    }
    
    /**
     * 查询一条数据
     *
     * @param waterPressureMonitoring 查询条件
     * @return 对象
     */
    public ResponseResult<Object> queryWaterPressureMonitoringObject(WaterPressureMonitoring waterPressureMonitoring) {
        QueryWrapper<WaterPressureMonitoring> queryWrapper = new QueryWrapper<WaterPressureMonitoring>(waterPressureMonitoring);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",waterPressureMonitoringMapper.selectOne(queryWrapper));
    }
    
    /**
     * 新增一条数据
     *
     * @param waterPressureMonitoring 新增数据实体类
     * @return 新增对象
     */
    public ResponseResult<Object> addWaterPressureMonitoring(WaterPressureMonitoring waterPressureMonitoring) {
        int uid = SessionUtils.getUserId();
        waterPressureMonitoring.setId(null);
        waterPressureMonitoring.setValidFlag(1);
        waterPressureMonitoring.setCreateTime(new Date());
        waterPressureMonitoring.setUpdateTime(new Date());
        waterPressureMonitoring.setCreater(uid);
        waterPressureMonitoring.setUpdater(uid);
        waterPressureMonitoringMapper.insert(waterPressureMonitoring);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"新增成功",waterPressureMonitoring);
    }
    
    /**
     * 修改一条数据
     *
     * @param waterPressureMonitoring 修改数据实体类
     * @return 修改后对象
     */
    public ResponseResult<Object> editWaterPressureMonitoring(WaterPressureMonitoring waterPressureMonitoring) {
        int uid = SessionUtils.getUserId();
        waterPressureMonitoring.setUpdateTime(new Date());
        waterPressureMonitoring.setUpdater(uid);
        waterPressureMonitoringMapper.updateById(waterPressureMonitoring);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"修改成功",waterPressureMonitoring);
    }
    public ResponseResult<Object> getAllLocation() {
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"成功",waterPressureMonitoringMapper.getAllLocation());
    }


}
