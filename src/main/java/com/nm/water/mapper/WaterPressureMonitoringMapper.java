package com.nm.water.mapper;

import com.nm.water.pojo.WaterPressureMonitoring;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (WaterPressureMonitoring)表数据库访问层
 *
 */

@Repository
public interface WaterPressureMonitoringMapper extends BaseMapper<WaterPressureMonitoring> {
    @Select("SELECT monitoring_location,count(*) as 'num' FROM water_pressure_monitoring WHERE valid_flag = 1 AND warning_level = 2 GROUP BY monitoring_location")
    List<WaterPressureMonitoring> getPressureChart();

    @Select("SELECT monitoring_location FROM water_pressure_monitoring WHERE valid_flag = 1 GROUP BY monitoring_location")
    List<WaterPressureMonitoring> getAllLocation();

    @Select("SELECT monitoring_location,monitoring_time,pressure_value FROM water_pressure_monitoring " +
            "    WHERE monitoring_location = #{name} AND valid_flag = 1 AND monitoring_time BETWEEN #{begin} AND #{end} ORDER BY monitoring_time")
    List<WaterPressureMonitoring> getPressureChartByDate(@Param("begin") String begin,@Param("end") String end,@Param("name") String name);





}
