package com.chat.common.core.domain.dto.talk;

import com.chat.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ：zk
 * @date ：2023/11/9 19:20
 * @version: 2.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@ToString
public class QueryTalkRecordsDTO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 116432121234366912L;

    /**
     * 发送人
     */
    private String sender;

    /**
     * 接收人
     */
    private String receiver;

    /**
     * 内容
     */
    private String content;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 所有客服
     */
    private Long deptId;

    private List<String> userIds;

}
