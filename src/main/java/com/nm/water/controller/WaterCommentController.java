package com.nm.water.controller;
import com.nm.water.service.WaterCommentService;
import com.nm.water.pojo.WaterComment;
import com.nm.water.system.ResponseFormat.BaseResponse;
import com.nm.water.system.ResponseFormat.ResponseResult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

@Controller
@BaseResponse
@RequestMapping("/waterComment")
public class WaterCommentController {

    @Resource
    WaterCommentService waterCommentService;
    
    /**
     * 查询多条数据
     *
     * @param waterComment 查询条件
     * @return 对象列表
     */
    @RequestMapping("/queryList")
    @ResponseBody
    public ResponseResult<Object> queryWaterCommentList(@RequestBody WaterComment waterComment){
        return waterCommentService.queryWaterCommentList(waterComment);
    }
    /**
     * 查询一条数据
     *
     * @param waterComment 查询条件
     * @return 对象
     */
    @RequestMapping("/queryObject")
    @ResponseBody
    public ResponseResult<Object> queryWaterCommentObject(@RequestBody WaterComment waterComment){
        return waterCommentService.queryWaterCommentObject(waterComment);
    }
    /**
     * 新增一条数据
     *
     * @param waterComment 新增数据实体类
     * @return 新增对象
     */
    @RequestMapping("/addWaterComment")
    @ResponseBody
    public ResponseResult<Object> addWaterComment(@RequestBody WaterComment waterComment){
        return waterCommentService.addWaterComment(waterComment);
    }
    /**
     * 修改一条数据
     *
     * @param waterComment 修改数据实体类
     * @return 修改后对象
     */
    @RequestMapping("/editWaterComment")
    @ResponseBody
    public ResponseResult<Object> editWaterComment(@RequestBody WaterComment waterComment){
        return waterCommentService.editWaterComment(waterComment);
    }
    
}
