package com.chat.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chat.common.core.domain.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 参数配置表 sys_config
 *
 * @author xpy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@ToString
@TableName("sys_config")
public class SysConfig extends BaseEntity {

    private static final long serialVersionUID = 835522738740393173L;
    /**
     * 参数主键
     */
    @TableId(value = "config_id", type = IdType.AUTO)
    private Long configId;

    /**
     * 参数名称
     */
    @NotBlank(message = "参数名称不能为空")
    @Size(min = 0, max = 100, message = "参数名称不能超过100个字符")
    private String configName;

    /**
     * 参数键名
     */
    @NotBlank(message = "参数键名长度不能为空")
    @Size(min = 0, max = 100, message = "参数键名长度不能超过100个字符")
    private String configKey;

    /**
     * 参数键值
     */
    @NotBlank(message = "参数键值不能为空")
    @Size(min = 0, max = 500, message = "参数键值长度不能超过500个字符")
    private String configValue;
    /**
     * 系统内置（Y是 N否）
     */
    private String configType;

    private String remark;
}
