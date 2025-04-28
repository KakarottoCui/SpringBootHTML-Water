package com.nm.water.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nm.water.mapper.SysUserLoginMapper;
import com.nm.water.mapper.SysUserMapper;
import com.nm.water.mapper.WaterPressureMonitoringMapper;
import com.nm.water.mapper.WaterQualityMonitoringMapper;
import com.nm.water.pojo.SysUser;
import com.nm.water.pojo.SysUserLogin;
import com.nm.water.pojo.WaterQualityMonitoring;
import com.nm.water.system.ResponseFormat.ResponseCode;
import com.nm.water.system.ResponseFormat.ResponseResult;
import com.nm.water.system.SysInfo;
import com.nm.water.system.api.BaiDuUtils;
import com.nm.water.system.restful.WxRestful;
import com.nm.water.system.utils.CommonFunction;
import com.nm.water.system.utils.SessionUtils;
import com.nm.water.system.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class MainService {

    @Resource
    private SysUserMapper quUserMapper;
    @Resource
    private SysUserLoginMapper nmUserLoginMapper;
    @Resource
    private WaterPressureMonitoringMapper waterPressureMonitoringMapper;
    @Resource
    private WaterQualityMonitoringMapper waterQualityMonitoringMapper;

    public Boolean setLogin(String token, HttpServletRequest request) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        if (sysUser != null && token.equals(sysUser.getToken())) {
            return true;
        } else {
            SysUserLogin sysUserLogin = new SysUserLogin();
            sysUserLogin.setToken(token);
            sysUserLogin.setValidFlag(1);
            QueryWrapper queryWrapper = new QueryWrapper(sysUserLogin);
            sysUserLogin = nmUserLoginMapper.selectOne(queryWrapper);
            if (sysUserLogin == null) {
                SessionUtils.removeSessionAttribute("user");
                return true;
            } else {
                sysUser = new SysUser();
                sysUser.setOpenId(sysUserLogin.getOpenId());
                sysUser.setValidFlag(1);
                queryWrapper = new QueryWrapper(sysUser);
                sysUser = quUserMapper.selectOne(queryWrapper);
                if (sysUser != null) {
                    sysUser.setToken(token);
                    SessionUtils.addSessionAttribute("user", sysUser);
                    return true;
                } else {
                    sysUser = new SysUser();
                    sysUser.setValidFlag(1);
                    sysUser.setUid(sysUserLogin.getUid());
                    sysUser.setOpenId(null);
                    queryWrapper = new QueryWrapper(sysUser);
                    sysUser = quUserMapper.selectOne(queryWrapper);
                    if (sysUser != null) {
                        sysUser.setToken(token);
                        SessionUtils.addSessionAttribute("user", sysUser);
                        return true;
                    }else{
                        SessionUtils.removeSessionAttribute("user");
                        return true;
                    }
                }
            }
        }
    }


    public ResponseResult getUserInfo(String code, String enData, String iv, String userInfo) {
        String sys = WxRestful.wxGetUserKey(code);
        HashMap<String, Object> res = new HashMap<String, Object>();
        Map<String, String> keyMap = JSON.parseObject(sys, HashMap.class);
        Map<String, String> userMap = JSON.parseObject(userInfo, HashMap.class);
        SysUser sysUser = new SysUser();
        sysUser.setOpenId(keyMap.get("openid"));
        sysUser.setValidFlag(1);
        QueryWrapper queryWrapper = new QueryWrapper(sysUser);
        sysUser = quUserMapper.selectOne(queryWrapper);
        if (sysUser != null) {
            SysUserLogin sysUserLogin = new SysUserLogin();
            sysUserLogin.setValidFlag(1);
            sysUserLogin.setOpenId(keyMap.get("openid"));
            queryWrapper = new QueryWrapper(sysUserLogin);
            queryWrapper.orderByDesc("create_time");
            sysUserLogin = nmUserLoginMapper.selectOne(queryWrapper);
            String token = "";
            if (sysUserLogin == null) {
                sysUserLogin = new SysUserLogin();
                sysUserLogin.setValidFlag(1);
                sysUserLogin.setOpenId(keyMap.get("openid"));
                token = UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
                sysUserLogin.setUid(sysUser.getUid());
                sysUserLogin.setToken(token);
                sysUserLogin.setCreateTime(new Date());
                sysUserLogin.setCreater(sysUser.getUid());
                sysUserLogin.setSessionKey(keyMap.get("session_key"));
                sysUserLogin.setUpdateTime(new Date());
                sysUserLogin.setUpdater(sysUser.getUid());
                nmUserLoginMapper.insert(sysUserLogin);
            } else {
                sysUserLogin.setUpdateTime(new Date());
                token = sysUserLogin.getToken();
                nmUserLoginMapper.updateById(sysUserLogin);
            }
            sysUser.setToken(token);
            return new ResponseResult(ResponseCode.SUCCESS, sysUser);

        } else {
            sysUser = new SysUser();
            sysUser.setValidFlag(1);
            sysUser.setCreateTime(new Date());
            sysUser.setUpdateTime(new Date());
            String nick = userMap.get("nickName");
            sysUser.setNickName(nick+"-"+ StringUtils.RandomString(5));
            sysUser.setRealName("");
            sysUser.setOpenId(keyMap.get("openid"));
            sysUser.setPic(userMap.get("avatarUrl"));
            sysUser.setSessionKey("MTIzNDU2");
            sysUser.setMemo("0");
            quUserMapper.insert(sysUser);
            SysUserLogin sysUserLogin = weChatLoginInfo(sysUser,keyMap.get("openid"),keyMap.get("session_key"));
            sysUser.setToken(sysUserLogin.getToken());
            return new ResponseResult(ResponseCode.SUCCESS, sysUser);
        }
    }

    //创建微信登录信息
    public SysUserLogin weChatLoginInfo(SysUser sysUser,String openId,String session_key){
        SysUserLogin sysUserLogin = new SysUserLogin();
        sysUserLogin.setValidFlag(1);
        sysUserLogin.setOpenId(openId);
        String token = UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
        sysUserLogin.setUid(sysUser.getUid());
        sysUserLogin.setToken(token);
        sysUserLogin.setCreateTime(new Date());
        sysUserLogin.setCreater(sysUser.getUid());
        sysUserLogin.setSessionKey(session_key);
        sysUserLogin.setUpdateTime(new Date());
        sysUserLogin.setUpdater(sysUser.getUid());
        nmUserLoginMapper.insert(sysUserLogin);
        return sysUserLogin;
    }

    public ResponseResult regist(SysUser sysUser){
        sysUser.setValidFlag(1);
        SysUser param = new SysUser();
        QueryWrapper<SysUser> queryWrapper2 = new QueryWrapper<SysUser>();
        queryWrapper2.eq("valid_flag",1);
        queryWrapper2.and(wq->{
            wq.eq("nick_name",sysUser.getNickName());
        });
        param = quUserMapper.selectOne(queryWrapper2);
        if(param!=null){
            return new ResponseResult(ResponseCode.SERVICE_ERROR, "用户重名");
        }else{
            sysUser.setType(2);
            sysUser.setUpdateTime(new Date());
            sysUser.setCreateTime(new Date());
            quUserMapper.insert(sysUser);
            return new ResponseResult(ResponseCode.SUCCESS, "注册成功");
        }
    }

    public ResponseResult changeUserInfo(SysUser sysUser){
        sysUser.setValidFlag(1);
        SysUser param = new SysUser();
        QueryWrapper<SysUser> queryWrapper2 = new QueryWrapper<SysUser>();
        queryWrapper2.eq("valid_flag",1);
        queryWrapper2.and(wq->{
            wq.eq("nick_name",sysUser.getNickName());
        });
        param = quUserMapper.selectOne(queryWrapper2);
        if(param!=null && !param.getUid().equals(sysUser.getUid()) ){
            return new ResponseResult(ResponseCode.SERVICE_ERROR, "用户名重名");
        }else{
            sysUser.setUpdateTime(new Date());
            sysUser.setCreateTime(new Date());
            if(sysUser.getPic()!=null && sysUser.getPic().length() > 100){
                setPic(sysUser);
            }
            quUserMapper.updateById(sysUser);
            return new ResponseResult(ResponseCode.SUCCESS, "修改成功");
        }
    }

    public ResponseResult regist2(SysUser sysUser){
        sysUser.setValidFlag(1);
        SysUser param = new SysUser();
        QueryWrapper<SysUser> queryWrapper2 = new QueryWrapper<SysUser>();
        queryWrapper2.eq("valid_flag",1);
        param = quUserMapper.selectOne(queryWrapper2);
        if(param!=null){
            return new ResponseResult(ResponseCode.SERVICE_ERROR, "用户重名或车牌已存在");
        }else{
            sysUser.setUpdateTime(new Date());
            sysUser.setCreateTime(new Date());
            quUserMapper.insert(sysUser);
            return new ResponseResult(ResponseCode.SUCCESS, "注册成功");
        }
    }

    public ResponseResult getUserData(HttpServletRequest request){
        SysUser sysUser = SessionUtils.getUser();
        if(sysUser==null){
            return new ResponseResult(ResponseCode.SUCCESS, "查询成功");
        }



        Map<String,Object> res = new HashMap<>();

        return new ResponseResult(ResponseCode.SUCCESS, "查询成功",res);
    }

    public ResponseResult login(SysUser sysUser) {
        sysUser.setValidFlag(1);
        QueryWrapper queryWrapper = new QueryWrapper(sysUser);
        sysUser = quUserMapper.selectOne(queryWrapper);
        if(sysUser !=null){
            SysUserLogin sysUserLogin = new SysUserLogin();
            sysUserLogin.setValidFlag(1);
            sysUserLogin.setUid(sysUser.getUid());
            queryWrapper = new QueryWrapper(sysUserLogin);
            queryWrapper.orderByDesc("create_time");
            sysUserLogin = nmUserLoginMapper.selectOne(queryWrapper);
            String token = "";
            if (sysUserLogin == null) {
                sysUserLogin = new SysUserLogin();
                sysUserLogin.setValidFlag(1);
                sysUserLogin.setUid(sysUser.getUid());
                token = UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
                sysUserLogin.setUid(sysUser.getUid());
                sysUserLogin.setToken(token);
                sysUserLogin.setCreateTime(new Date());
                sysUserLogin.setCreater(sysUser.getUid());
                sysUserLogin.setUpdateTime(new Date());
                sysUserLogin.setUpdater(sysUser.getUid());
                nmUserLoginMapper.insert(sysUserLogin);
            } else {
                sysUserLogin.setUpdateTime(new Date());
                token = sysUserLogin.getToken();
                nmUserLoginMapper.updateById(sysUserLogin);
            }
            sysUser.setToken(token);
            return new ResponseResult(ResponseCode.SUCCESS, sysUser);
        }
        return new ResponseResult(ResponseCode.SERVICE_ERROR, "用户信息错误");
    }

    public ResponseResult saveFile(MultipartFile file) throws IOException {
        String[] fileTypeArr = file.getOriginalFilename().split("\\.");
        String fileType = fileTypeArr[fileTypeArr.length - 1];
        String newName = "audio-"+ UUID.randomUUID().toString()+"-"+new Date().getTime()+"."+fileType;
        file.transferTo(new File(SysInfo.FILE_PATH+newName));
        return new ResponseResult(ResponseCode.SUCCESS, "上传成功",newName);
    }

    public ResponseResult getUserAllInfo(Integer userId) {
        SysUser sysUser = new SysUser();
        sysUser.setValidFlag(1);
        sysUser.setUid(userId);
        QueryWrapper queryWrapper = new QueryWrapper(sysUser);
        sysUser = quUserMapper.selectOne(queryWrapper);
        return new ResponseResult(ResponseCode.SUCCESS,"查询成功",sysUser);
    }

    public void setPic(SysUser sysUser){
        if(StringUtils.isNotEmpty(sysUser.getPic()) && sysUser.getPic().length()>100 ){
            String fileName = CommonFunction.SaveBase64Pic(sysUser.getPic());
            sysUser.setPic(fileName);
        }
    }

    public ResponseResult getPressureChart(){
        return new ResponseResult(ResponseCode.SUCCESS,"查询成功",waterPressureMonitoringMapper.getPressureChart());
    }

    public ResponseResult getQualityChart(){
        return new ResponseResult(ResponseCode.SUCCESS,"查询成功",waterQualityMonitoringMapper.getQualityChart());
    }

    public ResponseResult getQualityChartByDate(WaterQualityMonitoring param){
        return new ResponseResult(ResponseCode.SUCCESS,"查询成功",waterQualityMonitoringMapper.getQualityChartByDate(param.getBegin(),param.getEnd(),param.getSectionName()));
    }

    public ResponseResult getPressureChartByDate(WaterQualityMonitoring param){
        return new ResponseResult(ResponseCode.SUCCESS,"查询成功",waterPressureMonitoringMapper.getPressureChartByDate(param.getBegin(),param.getEnd(),param.getSectionName()));
    }


}
