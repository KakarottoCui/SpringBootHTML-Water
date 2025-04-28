package com.nm.water.mapper;

import com.nm.water.pojo.WaterPressureMonitoring;
import com.nm.water.pojo.WaterQualityMonitoring;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (WaterQualityMonitoring)表数据库访问层
 *
 */

@Repository
public interface WaterQualityMonitoringMapper extends BaseMapper<WaterQualityMonitoring> {

    @Select("SELECT CASE " +
            "    WHEN water_quality_category = 0 THEN 'I' " +
            "    WHEN water_quality_category = 1 THEN 'II' " +
            "    WHEN water_quality_category = 2 THEN 'III' " +
            "    WHEN water_quality_category = 3 THEN 'IV' " +
            "    WHEN water_quality_category = 4 THEN 'V' " +
            "    WHEN water_quality_category = 5 THEN '劣V' " +
            "    ELSE NULL " +
            "    END AS 'name'," +
            "    count(*) as 'num' " +
            "    FROM  water_quality_monitoring WHERE valid_flag = 1  GROUP BY water_quality_category")
    List<WaterQualityMonitoring> getQualityChart();

    @Select(" SELECT section_name FROM water_quality_monitoring WHERE valid_flag = 1 GROUP BY section_name ")
    List<WaterQualityMonitoring> getAllSection();

    @Select(" SELECT section_name,CASE " +
            "    WHEN water_quality_category = 0 THEN 5 " +
            "    WHEN water_quality_category = 1 THEN 4 " +
            "    WHEN water_quality_category = 2 THEN 3 " +
            "    WHEN water_quality_category = 3 THEN 2 " +
            "    WHEN water_quality_category = 4 THEN 1 " +
            "    WHEN water_quality_category = 5 THEN 0 " +
            "    ELSE NULL " +
            "    END AS water_quality_category,monitoring_time FROM water_quality_monitoring "+
            "    WHERE section_name = #{name} AND valid_flag = 1 AND monitoring_time BETWEEN #{begin} AND #{end} ORDER BY monitoring_time")
    List<WaterQualityMonitoring> getQualityChartByDate(@Param("begin") String begin,@Param("end") String end,@Param("name") String name);


}
