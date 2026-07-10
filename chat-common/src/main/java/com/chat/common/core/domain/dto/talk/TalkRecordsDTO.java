package com.chat.common.core.domain.dto.talk;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ：xingpeiyue
 * @date ：2023/7/10 15:22
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class TalkRecordsDTO implements Serializable {
    private static final long serialVersionUID = 1167143786434366912L;
    /**
     * 下次开始位置
     */
    private Long recordId;
    /**
     * 接收人
     */
    private String talkId;
    /**
     * 数据大小
     */
    private Integer limit;
}
