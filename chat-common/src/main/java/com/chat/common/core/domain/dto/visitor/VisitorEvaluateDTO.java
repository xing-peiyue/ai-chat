package com.chat.common.core.domain.dto.visitor;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author ：xingpeiyue
 * @date ：2023/8/30 18:57
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class VisitorEvaluateDTO implements Serializable {
    private static final long serialVersionUID = -5442980678056967730L;
    /**
     * 访客的userId
     */
    private String userId;
    private String status;
    private Integer hs;
    private Integer hq;
    private Integer ht;
    private Integer hf;
    private String suggest;
}
