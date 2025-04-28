package com.nm.water.pojo;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * WaterPressureMonitoring表实体类
 * 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaterPressureMonitoring implements Serializable {
    private static final long serialVersionUID = 512619267165661988L;

/**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

/**
     * 水压数值
     */    
    private Float pressureValue;
/**
     * 监测位置
     */    
    private String monitoringLocation;
/**
     * 监测时间
     */    
    private Date monitoringTime;
/**
     * 情况，取值为 1正常/0异常
     */    
    private Integer status;
/**
     * 管径
     */    
    private Float pipeDiameter;
/**
     * 静态阈值
     */    
    private Float staticThreshold;
/**
     * 预警阈值，范围在 0.15Mpa - 1.2Mpa
     */    
    private Float warningThreshold;
/**
     * 预警级别，取值为 0黄色/1橙色/2红色
     */    
    private Integer warningLevel;
/**
     * 有效标识
     */    
    private Integer validFlag;
/**
     * 创建人id
     */    
    private Integer creater;
/**
     * 更新人id
     */    
    private Integer updater;
/**
     * 创建时间
     */    
    private Date createTime;
/**
     * 更新时间
     */    
    private Date updateTime;
/**
     * 备注，问题说明
     */    
    private String memo;

    @TableField(exist = false)
    private Integer num;

}
