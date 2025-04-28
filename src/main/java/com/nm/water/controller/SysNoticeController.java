package com.nm.water.controller;
import com.nm.water.service.SysNoticeService;
import com.nm.water.pojo.SysNotice;
import com.nm.water.system.ResponseFormat.BaseResponse;
import com.nm.water.system.ResponseFormat.ResponseResult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

@Controller
@BaseResponse
@RequestMapping("/sysNotice")
public class SysNoticeController {

    @Resource
    SysNoticeService sysNoticeService;
    
    /**
     * 查询多条数据
     *
     * @param sysNotice 查询条件
     * @return 对象列表
     */
    @RequestMapping("/queryList")
    @ResponseBody
    public ResponseResult<Object> querySysNoticeList(@RequestBody SysNotice sysNotice){
        return sysNoticeService.querySysNoticeList(sysNotice);
    }
    /**
     * 查询一条数据
     *
     * @param sysNotice 查询条件
     * @return 对象
     */
    @RequestMapping("/queryObject")
    @ResponseBody
    public ResponseResult<Object> querySysNoticeObject(@RequestBody SysNotice sysNotice){
        return sysNoticeService.querySysNoticeObject(sysNotice);
    }
    /**
     * 新增一条数据
     *
     * @param sysNotice 新增数据实体类
     * @return 新增对象
     */
    @RequestMapping("/addSysNotice")
    @ResponseBody
    public ResponseResult<Object> addSysNotice(@RequestBody SysNotice sysNotice){
        return sysNoticeService.addSysNotice(sysNotice);
    }
    /**
     * 修改一条数据
     *
     * @param sysNotice 修改数据实体类
     * @return 修改后对象
     */
    @RequestMapping("/editSysNotice")
    @ResponseBody
    public ResponseResult<Object> editSysNotice(@RequestBody SysNotice sysNotice){
        return sysNoticeService.editSysNotice(sysNotice);
    }
    
}
