package com.nm.water.system.restful;

import com.nm.water.system.SysInfo;
import com.nm.water.system.restTemp.RestTemp;

public class WxRestful {

    private static RestTemp restTemp;

    static {
        createRestTemplate();
    }

    public static RestTemp getRestTemplate(){
        if( restTemp == null ) restTemp = new RestTemp();
        return restTemp;
    }

    private static void createRestTemplate() {
        if( restTemp == null ) restTemp = new RestTemp();
    }

    public static String wxGetUserKey(String js_code){
        String url = SysInfo.WX_GET_KEY + "&appid="+SysInfo.APP_ID+"&secret="+SysInfo.APP_SECRET+"&js_code="+js_code;
        return restTemp.getForString(url);
    }

}
