package com.nm.water.controller;
import com.nm.water.service.WaterQualityMonitoringService;
import com.nm.water.pojo.WaterQualityMonitoring;
import com.nm.water.system.ResponseFormat.BaseResponse;
import com.nm.water.system.ResponseFormat.ResponseResult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

@Controller
@BaseResponse
@RequestMapping("/waterQualityMonitoring")
public class WaterQualityMonitoringController {

    @Resource
    WaterQualityMonitoringService waterQualityMonitoringService;
    
    /**
     * 查询多条数据
     *
     * @param waterQualityMonitoring 查询条件
     * @return 对象列表
     */
    @RequestMapping("/queryList")
    @ResponseBody
    public ResponseResult<Object> queryWaterQualityMonitoringList(@RequestBody WaterQualityMonitoring waterQualityMonitoring){
        return waterQualityMonitoringService.queryWaterQualityMonitoringList(waterQualityMonitoring);
    }
    /**
     * 查询一条数据
     *
     * @param waterQualityMonitoring 查询条件
     * @return 对象
     */
    @RequestMapping("/queryObject")
    @ResponseBody
    public ResponseResult<Object> queryWaterQualityMonitoringObject(@RequestBody WaterQualityMonitoring waterQualityMonitoring){
        return waterQualityMonitoringService.queryWaterQualityMonitoringObject(waterQualityMonitoring);
    }
    /**
     * 新增一条数据
     *
     * @param waterQualityMonitoring 新增数据实体类
     * @return 新增对象
     */
    @RequestMapping("/addWaterQualityMonitoring")
    @ResponseBody
    public ResponseResult<Object> addWaterQualityMonitoring(@RequestBody WaterQualityMonitoring waterQualityMonitoring){
        return waterQualityMonitoringService.addWaterQualityMonitoring(waterQualityMonitoring);
    }
    /**
     * 修改一条数据
     *
     * @param waterQualityMonitoring 修改数据实体类
     * @return 修改后对象
     */
    @RequestMapping("/editWaterQualityMonitoring")
    @ResponseBody
    public ResponseResult<Object> editWaterQualityMonitoring(@RequestBody WaterQualityMonitoring waterQualityMonitoring){
        return waterQualityMonitoringService.editWaterQualityMonitoring(waterQualityMonitoring);
    }

    @RequestMapping("/getAllSectionName")
    @ResponseBody
    public ResponseResult<Object> getAllSectionName(){
        return waterQualityMonitoringService.getAllSectionName();
    }
    
}
