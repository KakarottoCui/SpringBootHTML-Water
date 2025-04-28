package com.nm.water.controller;
import com.nm.water.service.WaterPressureMonitoringService;
import com.nm.water.pojo.WaterPressureMonitoring;
import com.nm.water.system.ResponseFormat.BaseResponse;
import com.nm.water.system.ResponseFormat.ResponseResult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

@Controller
@BaseResponse
@RequestMapping("/waterPressureMonitoring")
public class WaterPressureMonitoringController {

    @Resource
    WaterPressureMonitoringService waterPressureMonitoringService;
    
    /**
     * 查询多条数据
     *
     * @param waterPressureMonitoring 查询条件
     * @return 对象列表
     */
    @RequestMapping("/queryList")
    @ResponseBody
    public ResponseResult<Object> queryWaterPressureMonitoringList(@RequestBody WaterPressureMonitoring waterPressureMonitoring){
        return waterPressureMonitoringService.queryWaterPressureMonitoringList(waterPressureMonitoring);
    }
    /**
     * 查询一条数据
     *
     * @param waterPressureMonitoring 查询条件
     * @return 对象
     */
    @RequestMapping("/queryObject")
    @ResponseBody
    public ResponseResult<Object> queryWaterPressureMonitoringObject(@RequestBody WaterPressureMonitoring waterPressureMonitoring){
        return waterPressureMonitoringService.queryWaterPressureMonitoringObject(waterPressureMonitoring);
    }
    /**
     * 新增一条数据
     *
     * @param waterPressureMonitoring 新增数据实体类
     * @return 新增对象
     */
    @RequestMapping("/addWaterPressureMonitoring")
    @ResponseBody
    public ResponseResult<Object> addWaterPressureMonitoring(@RequestBody WaterPressureMonitoring waterPressureMonitoring){
        return waterPressureMonitoringService.addWaterPressureMonitoring(waterPressureMonitoring);
    }
    /**
     * 修改一条数据
     *
     * @param waterPressureMonitoring 修改数据实体类
     * @return 修改后对象
     */
    @RequestMapping("/editWaterPressureMonitoring")
    @ResponseBody
    public ResponseResult<Object> editWaterPressureMonitoring(@RequestBody WaterPressureMonitoring waterPressureMonitoring){
        return waterPressureMonitoringService.editWaterPressureMonitoring(waterPressureMonitoring);
    }

    @RequestMapping("/getAllLocation")
    @ResponseBody
    public ResponseResult<Object> getAllLocation(){
        return waterPressureMonitoringService.getAllLocation();
    }


    
}
