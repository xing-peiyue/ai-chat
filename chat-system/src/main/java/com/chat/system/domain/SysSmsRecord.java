package com.chat.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chat.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author ：xingpeiyue
 * @date ：2023/9/4 16:03
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@ToString
@TableName("sys_sms_record")
public class SysSmsRecord extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1103179512824287676L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String phone;
    private String type;
    private String text;
    private String fee;
    private String count;
    private String unit;
    private String sid;
    private String remark;
    private String status;
}
