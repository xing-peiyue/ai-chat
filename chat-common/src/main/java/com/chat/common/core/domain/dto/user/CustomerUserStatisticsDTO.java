package com.chat.common.core.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ：xingpeiyue
 * @date ：2023/11/17 16:52
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class CustomerUserStatisticsDTO implements Serializable {
    private static final long serialVersionUID = -1822990346364421424L;
    private String userId;
    private String userName;
    private String nickName;
    /**
     * 接入公司
     */
    private String name;
    /**
     * 已接客服数
     */
    private Long visitors;
    /**
     * 满意度
     */
    private String satisfaction;
    /**
     * 投诉数
     */
    private Long complaints;
    /**
     * 公司创建人
     */
    private String createId;
    /**
     * 归属公司
     */
    private String companyUser;
}
