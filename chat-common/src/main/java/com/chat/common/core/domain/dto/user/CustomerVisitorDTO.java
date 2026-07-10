package com.chat.common.core.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：xingpeiyue
 * @date ：2023/11/20 14:17
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class CustomerVisitorDTO implements Serializable {
    private static final long serialVersionUID = 3810406536718082259L;
    private String userId;
    private String nickName;
    /**
     * 最后一条信息
     */
    private String lastMsg;
    /**
     * 最后的接入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;
    /**
     * 图像
     */
    private String avatar;
    /**
     * 访客
     */
    private String visitorUserId;
}
