package com.nm.water.controller;

import com.nm.water.pojo.SysUser;
import com.nm.water.pojo.WaterQualityMonitoring;
import com.nm.water.service.MainService;
import com.nm.water.system.ResponseFormat.BaseResponse;
import com.nm.water.system.ResponseFormat.ResponseResult;
import com.nm.water.system.SysInfo;
import org.springframework.data.annotation.Persistent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

@Controller
@BaseResponse
public class MainController {

    @Resource
    MainService mainService;

    @GetMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/water/login.html");
    }
    @GetMapping("/index")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/water/index.html");
    }
//    @GetMapping("/view/{path1}/{path2}")
//    public String login(@PathVariable String path1,@PathVariable String path2, HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws IOException {
//        return "view/"+path1+"/"+path2.replace(".html","");
//    }
    @GetMapping("/")
    public void toIndex(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.sendRedirect("/water/index.html");
    }

    @GetMapping("/getUserInfo")
    @ResponseBody
    public ResponseResult getUserInfo(String code, String iv, String enData, String userInfo){
        return mainService.getUserInfo(code,enData,iv,userInfo);
    }

    @PostMapping("/getLogin")
    @ResponseBody
    public ResponseResult getUserInfo(@RequestBody SysUser sysUser){
            return mainService.login(sysUser);
    }

    @RequestMapping("/logout")
    @ResponseBody
    public void logout(HttpServletRequest request){
        request.getSession().invalidate();
    }

    @RequestMapping("/regist")
    @ResponseBody
    public ResponseResult registUserInfo(@RequestBody SysUser data){
        return mainService.regist(data);
    }

    @RequestMapping("/changeUserInfo")
    @ResponseBody
    public ResponseResult changeUserInfo(@RequestBody SysUser data){
        return mainService.changeUserInfo(data);
    }

    @RequestMapping("/getUserData")
    @ResponseBody
    public ResponseResult getUserData(HttpServletRequest request){
        return mainService.getUserData(request);
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public ResponseResult uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
       return mainService.saveFile(file);
    }

    @RequestMapping(value = "/getFile/{type}/{path}")
    public void getPicById(@PathVariable int type,@PathVariable String path, HttpServletRequest request, HttpServletResponse response) {
        path = SysInfo.FILE_PATH+path;
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        FileInputStream objInputStream = null;
        ServletOutputStream objOutStream = null;
        String[] imgArr = path.split("\\.");
        String fileType = imgArr[imgArr.length-1];
        response.setContentType(type==0?"image/"+fileType:"audio/"+fileType);
        response.setHeader("Content-Disposition", "attachment;fileName="+new Date().getTime()+"."+fileType);
        try{
            objInputStream= new FileInputStream(path);
            objOutStream = response.getOutputStream();
            int aRead = 0;
            while ((aRead = objInputStream.read()) != -1 & objInputStream != null) {
                objOutStream.write(aRead);
            }
            objOutStream.flush();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                objOutStream.close();
            }catch (IOException e) {
            }
        }
    }

    @RequestMapping("/getUserAllInfo")
    @ResponseBody
    public ResponseResult<Object> getUserAllInfo(@RequestParam Integer uid){
        return mainService.getUserAllInfo(uid);
    }


    @RequestMapping("/getPressureChart")
    @ResponseBody
    public ResponseResult<Object> getPressureChart(){
        return mainService.getPressureChart();
    }

    @RequestMapping("/getQualityChart")
    @ResponseBody
    public ResponseResult<Object> getQualityChart(){
        return mainService.getQualityChart();
    }

    @RequestMapping("/getQualityChartByDate")
    @ResponseBody
    public ResponseResult<Object> getQualityChartByDate(@RequestBody WaterQualityMonitoring waterQualityMonitoring){
        return mainService.getQualityChartByDate(waterQualityMonitoring);
    }

    @RequestMapping("/getPressureChartByDate")
    @ResponseBody
    public ResponseResult<Object> getPressureChartByDate(@RequestBody WaterQualityMonitoring waterQualityMonitoring){
        return mainService.getPressureChartByDate(waterQualityMonitoring);
    }






}
