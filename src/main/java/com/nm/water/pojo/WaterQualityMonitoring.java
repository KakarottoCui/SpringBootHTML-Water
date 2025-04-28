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
 * WaterQualityMonitoring表实体类
 * 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaterQualityMonitoring implements Serializable {
    private static final long serialVersionUID = 324133637233492237L;

/**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

/**
     * 省份
     */    
    private String province;
/**
     * 流域
     */    
    private String riverBasin;
/**
     * 断面名称
     */    
    private String sectionName;
/**
     * 监测时间
     */    
    private Date monitoringTime;
/**
     * 水质类别，取值为 0 - I，1-II，2-III，3-IV，4-V，5-劣V
     */    
    private Integer waterQualityCategory;
/**
     * 水温
     */    
    private Float waterTemperature;
/**
     * pH值
     */    
    private Float ph;
/**
     * 溶解氧
     */    
    private Float dissolvedOxygen;
/**
     * 浊度
     */    
    private Float turbidity;
/**
     * 高猛酸盐指数
     */    
    private Float permanganateIndex;
/**
     * 氨氮
     */    
    private Float ammoniaNitrogen;
/**
     * 站点情况，取值为 1正常/0维护
     */    
    private Integer stationStatus;
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
    private String name;
    @TableField(exist = false)
    private Integer num;
    @TableField(exist = false)
    private String begin;
    @TableField(exist = false)
    private String end;
}
