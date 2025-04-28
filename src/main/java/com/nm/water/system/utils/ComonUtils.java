package com.nm.water.system.utils;

import com.nm.water.pojo.SysUser;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

public class ComonUtils {

    public static void CheckPath(String path){
        File folder = new File(path);
        // 文件夹路径不存在
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public static void CheckFile(String path){
        File file = new File(path);
        // 文件夹路径不存在
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static SysUser getLoginUser(HttpServletRequest request){
        return (SysUser)request.getSession().getAttribute("user");
    }


}
