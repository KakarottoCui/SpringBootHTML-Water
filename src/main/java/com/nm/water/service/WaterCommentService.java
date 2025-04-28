package com.nm.water.service;

import com.nm.water.mapper.WaterCommentMapper;
import com.nm.water.pojo.WaterComment;

import com.nm.water.pojo.WaterPressureMonitoring;
import com.nm.water.system.ResponseFormat.ResponseResult;
import com.nm.water.system.ResponseFormat.ResponseCode;
import com.nm.water.system.utils.SessionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nm.water.system.utils.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

@Service
public class WaterCommentService {

    @Resource
    private WaterCommentMapper waterCommentMapper;
    
    /**
     * 查询多条数据
     *
     * @param waterComment 查询条件
     * @return 对象列表
     */
    public ResponseResult<Object> queryWaterCommentList(WaterComment waterComment) {

        String searchStr = "";
        if(StringUtils.isNotEmpty(waterComment.getTitle())){
            searchStr = waterComment.getTitle();
            waterComment.setTitle(null);
        }
        QueryWrapper<WaterComment> queryWrapper = new QueryWrapper<WaterComment>(waterComment);
        if(StringUtils.isNotEmpty(searchStr)){
            queryWrapper.like("title",searchStr);
        }
        queryWrapper.orderByDesc("id");
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",waterCommentMapper.selectList(queryWrapper));
    }
    
    /**
     * 查询一条数据
     *
     * @param waterComment 查询条件
     * @return 对象
     */
    public ResponseResult<Object> queryWaterCommentObject(WaterComment waterComment) {
        QueryWrapper<WaterComment> queryWrapper = new QueryWrapper<WaterComment>(waterComment);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",waterCommentMapper.selectOne(queryWrapper));
    }
    
    /**
     * 新增一条数据
     *
     * @param waterComment 新增数据实体类
     * @return 新增对象
     */
    public ResponseResult<Object> addWaterComment(WaterComment waterComment) {
        int uid = SessionUtils.getUserId();
        waterComment.setId(null);
        waterComment.setValidFlag(1);
        waterComment.setCreateTime(new Date());
        waterComment.setUpdateTime(new Date());
        waterComment.setCreater(uid);
        waterComment.setUpdater(uid);
        waterCommentMapper.insert(waterComment);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"新增成功",waterComment);
    }
    
    /**
     * 修改一条数据
     *
     * @param waterComment 修改数据实体类
     * @return 修改后对象
     */
    public ResponseResult<Object> editWaterComment(WaterComment waterComment) {
        int uid = SessionUtils.getUserId();
        waterComment.setUpdateTime(new Date());
        waterComment.setUpdater(uid);
        waterCommentMapper.updateById(waterComment);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"修改成功",waterComment);
    }

}
