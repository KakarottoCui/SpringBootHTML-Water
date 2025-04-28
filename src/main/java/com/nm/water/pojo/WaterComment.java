package com.nm.water.pojo;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * WaterComment表实体类
 * 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaterComment implements Serializable {
    private static final long serialVersionUID = -90247602468281531L;

/**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

/**
     * 标题
     */    
    private String title;
/**
     * 内容
     */    
    private String content;
/**
     * 创建人
     */    
    private Integer creater;
/**
     * 更新人
     */    
    private Integer updater;
/**
     * 有效标识
     */    
    private Integer validFlag;
/**
     * 创建时间
     */    
    private Date createTime;
/**
     * 更新时间
     */    
    private Date updateTime;
/**
     * 答复
     */    
    private String comment;
/**
     * 答复人ID
     */    
    private Integer commentId;


}
